package spaceSurvivor.ship;


import java.awt.Graphics;
import java.awt.geom.Ellipse2D.Double;
import java.util.Random;
import spaceSurvivor.Hittable;
import spaceSurvivor.SpaceSurvivor;

/**
 * <code>EnemyShip</code> must be implemented by any type of an enemy ship.
 * @author Paul Schrauder
 */
public class EnemyShip implements Hittable{

    public double x,y,dx,dy,angle;
    public final static int RADIUS = 10;
    public final static int SPEED = 4;
    public static Random generator = new Random ();
    public PlayerShip p;
    public Bullet[] shots;
    public EnemyShip[] enemies;
    public boolean alive;

    public EnemyShip(){
        x = generator.nextInt(200)-100; //spawn within 100 units of edges
        if (x<0) x+=SpaceSurvivor.GAME_WIDTH; //if negative, spawn on opposite edge
        y = generator.nextInt(200)-100;
        if (y<0) y+=SpaceSurvivor.GAME_HEIGHT;
        angle = generator.nextDouble()*2*Math.PI; //random direction
        dx=SPEED*Math.cos(angle);
        dy=SPEED*Math.sin(angle);
        alive = true;
    }

    public void draw(Graphics g){}
    public void move(){}

    public void move(PlayerShip p, Bullet[] shots, EnemyShip[] enemies){
        if(alive){
            this.p = p;
            this.shots = shots;
            this.enemies = enemies;
            move();
            for(int i=0;i<enemies.length;i++){
                if (this != enemies[i] && this.isAlive() && enemies[i].isAlive())
                    if(this.collidedWithEnemy(enemies[i])){
                        angle = Math.atan2(y-enemies[i].y, x-enemies[i].x);
                        dx=SPEED*Math.cos(angle);
                        dy=SPEED*Math.sin(angle);
                }
            }
        }
    }

    public Double getBoundingBall(){
        return new java.awt.geom.Ellipse2D.Double(x, y, 2 * RADIUS, 2 * RADIUS);
    }

    public boolean collidedWithPlayer(){
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

		int thisCenterX = (int)thisBoundingBall.getCenterX();
		int thisCenterY = (int)thisBoundingBall.getCenterY();
		
		boolean hitBullet = false;
		
		for(int i = 0; i < shots.length; i++){
			int otherCenterX = (int)shots[i].getBoundingBall().getCenterX();
			int otherCenterY = (int)shots[i].getBoundingBall().getCenterY();
			
			/*
			 * underlying equation:
			 * (x1 - x2)^2 + (y1 - y2)^2 <= (r1 + r2)^2
			 */
			double xComponent = Math.pow(thisCenterX - otherCenterX, 2);
			double yComponent = Math.pow(thisCenterY - otherCenterY, 2);
			double radiiComponent = Math.pow(RADIUS + shots[i].getBoundingBall().height / 2, 2);
			
			if((xComponent + yComponent) <= radiiComponent){
				hitBullet = true;
                                shots[i].die();
			}
		}
		
		return hitBullet;
            }
            else return false;
	}

    public void die() {
        alive=false;
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

}