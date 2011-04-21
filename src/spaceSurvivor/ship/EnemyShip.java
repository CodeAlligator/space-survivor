package spaceSurvivor.ship;

import java.awt.Graphics;
import java.awt.geom.Ellipse2D.Double;
import java.util.Random;
import spaceSurvivor.Hittable;
import spaceSurvivor.Score;
import spaceSurvivor.SpaceSurvivor;
import spaceSurvivor.audio.GameAudioPlayer;

/**
 * <code>EnemyShip</code> must be extended by any enemy ship.
 * @author Paul Schrauder
 */
public class EnemyShip implements Hittable{

    protected double x,y,dx,dy,angle;
    public final static int RADIUS = 20;
    public final static int SPEED = 3;
    protected static Random generator = new Random ();
    protected PlayerShip p;
    protected Bullet[] shots;
    protected EnemyShip[] enemies;
    protected Score score;
    protected boolean alive;
    protected GameAudioPlayer audioFX = new GameAudioPlayer();
    
    public static final int HIT_PLAYER_ADD_TO_SCORE = -20;
    public static final int HIT_PLAYER_ADD_TO_SHIELD = -10;

    public EnemyShip(){
        x = generator.nextInt(200) - 100; //spawn within 100 units of edges
        if(x < 0)
        	x += SpaceSurvivor.GAME_WIDTH; //if negative, spawn on opposite edge
        y = generator.nextInt(200) - 100;
        if(y < 0)
        	y += SpaceSurvivor.GAME_HEIGHT;
        
        angle = generator.nextDouble()*2*Math.PI; //random direction
        
        dx = SPEED * Math.cos(angle);
        dy = SPEED * Math.sin(angle);
        
        alive = false;
    }

    public void draw(Graphics g){}
    public void move(){}

    public void move(PlayerShip p, Bullet[] shots, EnemyShip[] enemies, Score score){
        if(alive){
            this.p = p;
            this.shots = shots;
            this.enemies = enemies;
            this.score = score;
            
            move();

            // check for collisions with other enemies
            for(int i = 0; i < enemies.length; i++){
            	//	if this enemy is alive, only check against other alive enemies
                if (this != enemies[i] && this.isAlive() && enemies[i].isAlive()){
                	if(this.collidedWithEnemy(enemies[i])){
                        angle = Math.atan2(y-enemies[i].getY(), x-enemies[i].getX());
                        dx = SPEED*Math.cos(angle);
                        dy = SPEED*Math.sin(angle);
                    }
                }
            }

            // check for collisions with player
            if(this.collidedWithPlayer()){
                audioFX.playCrash();	//	play crash SFX
                this.die();
                score.addScore(HIT_PLAYER_ADD_TO_SCORE);
                if (score.getShield()==0) p.die();
                	score.addShield(HIT_PLAYER_ADD_TO_SHIELD);
            }

        }
    }

    public Double getBoundingBall(){
        return new java.awt.geom.Ellipse2D.Double(x, y, 2 * RADIUS, 2 * RADIUS);
    }

    public boolean collidedWithPlayer(){
    	if(p.isAlive()){
    		java.awt.geom.Ellipse2D.Double thisBoundingBall = getBoundingBall();

    		//	get center of both objects
    		int thisCenterX = (int)thisBoundingBall.getCenterX();
    		int thisCenterY = (int)thisBoundingBall.getCenterY();
    		int otherCenterX = (int)p.getBoundingBall().getCenterX();
    		int otherCenterY = (int)p.getBoundingBall().getCenterY();

    		/*
    		 * underlying equation:
    		 * (x1 - x2)^2 + (y1 - y2)^2 <= (r1 + r2)^2
    		 */
    		double xComponent = Math.pow(thisCenterX - otherCenterX, 2);
    		double yComponent = Math.pow(thisCenterY - otherCenterY, 2);
    		double radiiComponent = Math.pow(RADIUS + p.getBoundingBall().height / 2, 2);

    		return (xComponent + yComponent) <= radiiComponent;
    	}
    	else{
    		return false;
    	}
    }

    public boolean collidedWithEnemy(EnemyShip e) {
        if(alive){
			java.awt.geom.Ellipse2D.Double thisBoundingBall = getBoundingBall();
	
			//	get center of both objects
			int thisCenterX = (int)thisBoundingBall.getCenterX();
			int thisCenterY = (int)thisBoundingBall.getCenterY();
			int otherCenterX = (int)e.getBoundingBall().getCenterX();
			int otherCenterY = (int)e.getBoundingBall().getCenterY();
			
			/*
			 * underlying equation:
			 * (x1 - x2)^2 + (y1 - y2)^2 <= (r1 + r2)^2
			 */
			double xComponent = Math.pow(thisCenterX - otherCenterX, 2);
			double yComponent = Math.pow(thisCenterY - otherCenterY, 2);
			double radiiComponent = Math.pow(RADIUS + e.getBoundingBall().height / 2, 2);
			
			return (xComponent + yComponent) <= radiiComponent;
        }
        else return false;
    }
	
	
	/**
	 * Determines if this object has collided with any bullet.
	 * @return	true if collided, false otherwise
	 */
    public boolean collidedWithBullet() {
        if(alive){
            java.awt.geom.Ellipse2D.Double thisBoundingBall = getBoundingBall();

            double thisCenterX = thisBoundingBall.getCenterX();
            double thisCenterY = thisBoundingBall.getCenterY();

            boolean hitBullet = false;

            for(int i = 0; i < shots.length; i++){
                if(shots[i].isAlive()){
                    double otherCenterX = shots[i].getBoundingBall().getCenterX();
                    double otherCenterY = shots[i].getBoundingBall().getCenterY();

                    // Sorta works, though not perfect   ~Andrew
                    // checks bullet in 4 different previous spots since it moves fast enought to skip collisions
                    double shotX,shotY;
                    for (double j=0;j<26;j+=1.0){
                        shotX = otherCenterX - (shots[i].getDX()*j/25);
                        shotY = otherCenterY - (shots[i].getDY()*j/25);

                         /*
                         * underlying equation:
                         * (x1 - x2)^2 + (y1 - y2)^2 <= (r1 + r2)^2
                         */
                        double xComponent = Math.pow(thisCenterX - shotX, 2);
                        double yComponent = Math.pow(thisCenterY - shotY, 2);
                        double radiiComponent = Math.pow(RADIUS + shots[i].getBoundingBall().height / 2, 2);

                        if((xComponent + yComponent) <= radiiComponent){
                                hitBullet = true;
                                shots[i].die();
                        }

                    }
                }
            }

            return hitBullet;
        }
        else{
        	return false;
        }
    }

    public void die() {
        alive = false;
    }

    public boolean isAlive(){
        return alive;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public void setX(double xpos){
        x=xpos;
    }

    public void setY(double ypos){
        y=ypos;
    }

    public void setDX(double xdir){
        dx=xdir;
    }

    public void setDY(double ydir){
        dy=ydir;
    }

    public void activate(){
        alive = true;
    }
}