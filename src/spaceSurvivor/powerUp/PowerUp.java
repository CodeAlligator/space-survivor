package spaceSurvivor.powerUp;

import java.awt.Graphics;

/**
 * <code>PowerUp</code> represents a power up that is non-moving.
 * @author Paul
 * @see AmmoPowerUp
 * @see ShieldPowerUp
 */
public class PowerUp {
	/**
	 * X coordinate of power up.
	 */
	private double x;
	
	/**
	 * Y coordinate of power up.
	 */
	private double y;
	
	public void draw(Graphics g){
		//	TODO implement this method
	}
	
	public void setX(double x) {
		this.x = x;
	}

	public double getX() {
		return x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getY() {
		return y;
	}
}
