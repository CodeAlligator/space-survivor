package spaceSurvivor.ship.enemies;

import java.awt.Graphics;
import java.awt.geom.Ellipse2D.Double;

import spaceSurvivor.Hittable;
import spaceSurvivor.ship.Bullet;
import spaceSurvivor.ship.EnemyShip;
import spaceSurvivor.ship.PlayerShip;

public class AvoiderEnemy implements EnemyShip, Hittable {
	double x, y;
	int RADIUS = 10;
	PlayerShip p;
	private Bullet[] shots;
	
	public AvoiderEnemy() {
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
	public void move(PlayerShip p, Bullet[] shots) {
		this.p = p;
		this.shots = shots;
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
		double radiiComponent = Math.pow(RADIUS + p.getBoundingBall().height / 2, 2);
		
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
		double radiiComponent = Math.pow(RADIUS + e.getBoundingBall().height / 2, 2);
		
		return (xComponent + yComponent) <= radiiComponent;
	}
	
	@Override
	/**
	 * Determines if this object has collided with any bullet.
	 * @return	true if collided, false otherwise
	 */
	public boolean collidedWithBullet() {
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
			}
		}
		
		return hitBullet;
	}
}