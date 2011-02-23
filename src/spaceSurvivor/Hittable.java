package spaceSurvivor;

import java.awt.Graphics;

/**
 * <code>Hittable</code> must be implemented by sprites that can be hit.
 * Source code of this interface based off of Hittable interface by Dr. Slattery:
 * http://www.mscs.mu.edu/~mikes/cosc3550/demos/ForestFire-FullScreen/ForestFire.java
 * @author Paul
 */
public interface Hittable {
	void paint(Graphics g, boolean debug);
	
	//Rectangle getBoundingBox();
	java.awt.geom.Ellipse2D.Double getBoundingBall();
}
