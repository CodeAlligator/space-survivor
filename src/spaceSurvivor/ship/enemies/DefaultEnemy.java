package spaceSurvivor.ship.enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D.Double;
import java.util.Random;

import spaceSurvivor.Hittable;
import spaceSurvivor.SpaceSurvivor;
import spaceSurvivor.ship.EnemyShip;
import spaceSurvivor.ship.PlayerShip;

public class DefaultEnemy implements EnemyShip, Hittable {
    
    double x,y,dx,dy;
    final static int RADIUS = 10;
    final static int SPEED = 4;
    private static Random generator = new Random ();
    PlayerShip p;

    public DefaultEnemy() {
	x = generator.nextInt(200)-100; //spawn within 100 units of edges
        if (x<0) x+=800;                //if negative, spawn on opposite edge
        y = generator.nextInt(200)-100;
        if (y<0) y+=600;
        double angle = generator.nextDouble()*2*Math.PI; //random direction
        dx=SPEED*Math.cos(angle);
        dy=SPEED*Math.sin(angle);
    }

    public void draw(Graphics g) {
        g.setColor(Color.PINK);
        g.fillOval((int)x-RADIUS, (int)y-RADIUS, RADIUS*2, RADIUS*2);
    }

    public void move() {
        x += dx;
        y += dy;

        //bounce off of edges
        if ((y < RADIUS && dy <0) || (y+RADIUS > SpaceSurvivor.GAME_HEIGHT && dy>0))
            dy*=-1;
        if ((x < RADIUS && dx<0) || (x+RADIUS > SpaceSurvivor.GAME_WIDTH && dx>0))
            dx *= -1;
    }

	@Override
	public void move(PlayerShip p) {
		this.p = p;
		move();
	}

	@Override
	public Double getBoundingBall() {
		return new java.awt.geom.Ellipse2D.Double(x, y, 2 * RADIUS, 2 * RADIUS);
	}

	@Override
	public void paint(Graphics g, boolean debug) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	/**
	 * Determines if this object has collided with the player.
	 * @return	true if collided, false otherwise
	 */
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
		double radiiComponent = Math.pow(RADIUS + p.getBoundingBall().height, 2);
		
		return (xComponent + yComponent) <= radiiComponent;
    }

	@Override
	/**
	 * Determines if this object has collided with another enemy.
	 * @return	true if collided, false otherwise
	 */
	public boolean collidedWithEnemy(EnemyShip e) {
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
		double radiiComponent = Math.pow(RADIUS + e.getBoundingBall().height, 2);
		
		return (xComponent + yComponent) <= radiiComponent;
	}
}