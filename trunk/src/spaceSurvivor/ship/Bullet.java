package spaceSurvivor.ship;
import java.awt.*;
import java.awt.geom.Ellipse2D.Double;
import spaceSurvivor.Hittable;

import spaceSurvivor.SpaceSurvivor;
/**
 * <code>Bullet</code> represents a bullet that the player shot.
 * @author Andrew
 */
public class Bullet implements Hittable{
	private double x,y; //position
    private double xdir, ydir; //movement direction
    private boolean alive;
    private PlayerShip playerShip;
    public static final int NORMAL_BULLET_RADIUS = 4;
    public static final int BIG_BULLET_RADIUS = 10;
    public static int radius = NORMAL_BULLET_RADIUS;
    private boolean bigBullet = false;
    
    /**
     * Instantiates a new <code>Bullet</code> object.
     * @param player	the player's ship
     */
    public Bullet(PlayerShip player){
        playerShip = player;
        alive = false;
    }

    public void activate(){
        x = playerShip.getGunX();
        y = playerShip.getGunY();
        xdir = (x - playerShip.getPosX()) / 2;
        ydir = (y - playerShip.getPosY()) / 2;
        alive = true;
        
        if(bigBullet){
        	radius = BIG_BULLET_RADIUS;
        }
        else{
        	radius = NORMAL_BULLET_RADIUS;
        }
    }

    public void die(){
        alive = false;
    }

    public boolean isAlive(){
        return alive;
    }

    public void move(){
        if(alive){
            x += xdir;
            y += ydir;
        }
        if((y + radius < 0) || (y > SpaceSurvivor.GAME_HEIGHT + radius) || (x + radius < 0) || (x > SpaceSurvivor.GAME_WIDTH + radius))
            alive = false;
    }

    public void draw(Graphics g){
        if(alive){
            g.setColor(Color.YELLOW);
            g.fillOval((int)x - radius, (int)y - radius, radius * 2, radius * 2);
        }
    }
    
	public Double getBoundingBall() {
		return new java.awt.geom.Ellipse2D.Double(x, y, 2 * radius, 2 * radius);
	}

        public double getDX(){
            return xdir;
        }

        public double getDY(){
            return ydir;
        }

		/**
		 * @return the bigBullet
		 */
		public boolean isBigBullet() {
			return bigBullet;
		}

		/**
		 * @param bigBullet the bigBullet to set
		 */
		public void setBigBullet(boolean bigBullet) {
			this.bigBullet = bigBullet;
		}
}