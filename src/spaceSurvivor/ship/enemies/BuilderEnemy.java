package spaceSurvivor.ship.enemies;

import java.awt.Graphics;
import java.awt.geom.Ellipse2D.Double;

import spaceSurvivor.Hittable;
import spaceSurvivor.ship.EnemyShip;
import spaceSurvivor.ship.PlayerShip;

public class BuilderEnemy implements EnemyShip, Hittable {
	int RADIUS = 10;
	double x, y;
	PlayerShip p;
	
	public BuilderEnemy() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	
	public void move() {
        throw new UnsupportedOperationException("Not supported yet.");
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