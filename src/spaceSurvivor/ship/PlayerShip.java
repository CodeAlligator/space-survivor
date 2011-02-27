package spaceSurvivor.ship;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;

import spaceSurvivor.Hittable;
import spaceSurvivor.SpaceSurvivor;
import spaceSurvivor.Score;

/**
 * <code>PlayerShip</code> represents the user controlled space ship.
 * @author Andrew Johnson
 * COSC 3550 Assignment 4
 * Date: 2/21/2011
 */
public class PlayerShip implements Hittable{

    // ship variables
	/**
	 * This ship position.
	 */
    private int x = 200, y = 200;
    
    /**
     * key pressed by user
     */
    private boolean upKey = false, downKey = false,
            leftKey = false, rightKey = false;
    private boolean alive = true; // ship exists? (not collided)
    
    /**
     * Radius of this ship's bounding ball.
     */
    public final static int RADIUS = 20;
    
    /**
     * Speed of this ship.
     */
    public final static int SPEED = 4;

    Score score;
    Color shieldColor;
    
    //gun variables
    private int gunPointX = 100, gunPointY = 100;   //gun direction
    private double tipx,tipy;               //gun tip position
    private int[] gunPolyX = new int [4];   //gun shape x coordinates
    private int[] gunPolyY = new int [4];   //gun shape y coordinates
    public final static int GUNLEN = RADIUS+4;   //gun's length
    public final static int GUNW = 4;      //gun's width
    
    /**
     * Creates a new <code>PlayerShip</code> object.
     * @param gamescore
     */
    public PlayerShip(Score gamescore) {
    	x=SpaceSurvivor.GAME_WIDTH/2;
        y=SpaceSurvivor.GAME_HEIGHT/2;
        score = gamescore;
    }

    public void move(){
        // move ship position
        double dx=0, dy=0;
        if (upKey && y-RADIUS>0)
            dy -= 1;
		if (downKey && y+RADIUS<SpaceSurvivor.GAME_HEIGHT)
		    dy += 1;
	        if (leftKey && x-RADIUS>0)
		    dx -= 1;
		if (rightKey && x+RADIUS<SpaceSurvivor.GAME_WIDTH)
		    dx += 1;
        //if traveling at diagonal, speed is still maintained
        if(dx*dx+dy*dy>1){
            dx*=.707;
            dy*=.707;
        }

        x+=Math.round(dx*SPEED);
        y+=Math.round(dy*SPEED);

        // move gun position
        double theta;
        if (gunPointX-(double)x==0 && gunPointY>y)
            theta = Math.PI/2;
        else if(gunPointX-(double)x==0 && gunPointY<y)
            theta = -Math.PI/2;
        else
            theta = Math.atan((gunPointY-(double)y)/(gunPointX-(double)x));

        if(gunPointX<x){
            tipx = x-GUNLEN*Math.cos(theta);
            tipy = y-GUNLEN*Math.sin(theta);
        }
        else{
            tipx = x+GUNLEN*Math.cos(theta);
            tipy = y+GUNLEN*Math.sin(theta);
        }
        double xOff = GUNW*Math.sin(theta);
        double yOff = GUNW*Math.cos(theta);

        //create polygon coordinates for gun
        gunPolyX[0] = (int) (x - xOff);
        gunPolyX[1] = (int) (x + xOff);
        gunPolyX[2] = (int) (tipx + xOff);
        gunPolyX[3] = (int) (tipx - xOff);

        gunPolyY[0] = (int) (y + yOff);
        gunPolyY[1] = (int) (y - yOff);
        gunPolyY[2] = (int) (tipy - yOff);
        gunPolyY[3] = (int) (tipy + yOff);

        //update shield color
        shieldColor = new Color(255-score.getShield()*255/100, score.getShield()*255/100, 0);
   }

    public void draw(Graphics g){
        
        if(alive){
            g.setColor(shieldColor);
            g.fillOval(x-RADIUS, y-RADIUS, RADIUS*2, RADIUS*2);
            g.setColor(Color.red);
            g.fillOval(x-RADIUS+4, y-RADIUS+4, RADIUS*2-8, RADIUS*2-8);
            g.setColor(Color.ORANGE);
            g.fillPolygon(gunPolyX, gunPolyY, 4);
            g.setColor(Color.cyan);
            g.fillOval(x-RADIUS/2, y-RADIUS/2, RADIUS, RADIUS);
        }
        // if dead, replace ship with failure text
        else{
            g.setColor(Color.red);
            g.drawString ("You Died. Sorry.", 200, 250);
        }
    }
    
    @Override
	public Double getBoundingBall() {
		return new java.awt.geom.Ellipse2D.Double(x, y, 2 * RADIUS, 2 * RADIUS);
	}
	

	
	/**
	 * Determines if this object has collided with another object.
	 * @param enemyShip
	 * @return	true if collided, false otherwise
	 */
	public boolean collided(Hittable enemyShip){
        if(enemyShip.isAlive()){
			java.awt.geom.Ellipse2D.Double playerBoundingBall = getBoundingBall();
	
			//	get center of both objects
			int thisCenterX = (int)playerBoundingBall.getCenterX();
			int thisCenterY = (int)playerBoundingBall.getCenterY();
			int otherCenterX = (int)enemyShip.getBoundingBall().getCenterX();
			int otherCenterY = (int)enemyShip.getBoundingBall().getCenterY();
			
			/*
			 * underlying equation:
			 * (x1 - x2)^2 + (y1 - y2)^2 <= (r1 + r2)^2
			 */
			double xComponent = Math.pow(thisCenterX - otherCenterX, 2);
			double yComponent = Math.pow(thisCenterY - otherCenterY, 2);
			double radiiComponent = Math.pow(RADIUS + enemyShip.getBoundingBall().height / 2, 2);
			
			return (xComponent + yComponent) <= radiiComponent;
        }
        else return false;
    }
    
    public void setUpKey(Boolean val){
    	upKey = val;
    }

    public void setDownKey(Boolean val){
    	downKey = val;
    }

    public void setLeftKey(Boolean val){
    	leftKey = val;
    }

    public void setRightKey(Boolean val){
    	rightKey = val;
    }
    
    public void setGunX(int val){
    	gunPointX = val;
    }

    public void setGunY(int val){
    	gunPointY = val;
    }

    public int getPosX(){
    	return x;
    }

    public int getPosY(){
    	return y;
    }

    public double getGunX(){
    	return tipx;
    }

    public double getGunY(){
    	return tipy;
    }

    public void die(){
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }
}