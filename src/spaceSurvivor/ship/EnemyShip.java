package spaceSurvivor.ship;

import java.awt.Graphics;
import java.awt.geom.Ellipse2D.Double;

/**
 * <code>EnemyShip</code> must be implemented by any type of an enemy ship.
 * @author Paul Schrauder
 */
public interface EnemyShip {
    public void draw(Graphics g);
    public void move();
    public void move(PlayerShip p, Bullet[] shots);
	public Double getBoundingBall();
	public boolean collidedWithPlayer();
	public boolean collidedWithEnemy(EnemyShip e);
	public boolean collidedWithBullet();
}