package spaceSurvivor;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * 
 * @author Paul
 *
 */
public interface Hittable {
	void paint(Graphics g, boolean debug);
	
	Rectangle getBoundingBox();
}
