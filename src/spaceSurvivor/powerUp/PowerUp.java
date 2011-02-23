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
        private int life = 300; //time before it dissapears
        private boolean active = true; //if hasn't been collected or expired
	
	public void draw(Graphics g){
		//	TODO implement this method
	}

        public void update(){
            if (active){
                life--;
                if (life<0)
                    active = false;
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

        public boolean isActive(){
            return active;
        }
}
