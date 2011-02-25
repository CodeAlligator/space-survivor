package spaceSurvivor.powerUp;

import java.awt.Graphics;
import java.awt.geom.Ellipse2D.Double;
import spaceSurvivor.Hittable;

/**
 * <code>PowerUp</code> represents a power up that is non-moving.
 * @author Paul
 * @see AmmoPowerUp
 * @see ShieldPowerUp
 */
public class PowerUp implements Hittable {
	/**
	 * X coordinate of power up.
	 */
	private double x;
	
	/**
	 * Y coordinate of power up.
	 */
	private double y;
    private int life = 1000; //time before it dissapears
    protected boolean alive; //if hasn't been collected or expired
	
	public void draw(Graphics g){
		//	TODO implement this method
	}

    public void update(){
        if (alive){
            life--;
            if (life<0)
                alive = false;
        }
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

    public boolean isAlive(){
        return alive;
    }

    public void die(){
        alive=false;
    }

    public void activate(){
        alive = true;
    }
    
    public Double getBoundingBall() {
        return new java.awt.geom.Ellipse2D.Double(x, y, 30, 30);
    }
}
