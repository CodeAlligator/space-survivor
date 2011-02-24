package spaceSurvivor.ship.enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;

import spaceSurvivor.ship.EnemyShip;

public class AvoiderEnemy extends EnemyShip{
	
	public AvoiderEnemy() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics g) {
        //	testing code => show bounding ball
        Ellipse2D.Double bb = getBoundingBall();
        g.setColor(Color.WHITE);
        g.drawOval((int)bb.x, (int)bb.y, (int)bb.width, (int)bb.height);
	}
	
    @Override
	public void move() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
	
}