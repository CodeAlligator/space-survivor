package spaceSurvivor;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * <code>Hittable</code>
 * Source code of this interface created by Dr. Slattery:
 * http://www.mscs.mu.edu/~mikes/cosc3550/demos/ForestFire-FullScreen/ForestFire.java
 * @author Paul
 */
public interface Hittable {
	void paint(Graphics g, boolean debug);
	
	Rectangle getBoundingBox();
}
