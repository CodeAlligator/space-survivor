package spaceSurvivor;

/**
 * <code>Hittable</code> must be implemented by sprites that can be hit.
 * Source code of this interface based off of Hittable interface by Dr. Slattery:
 * http://www.mscs.mu.edu/~mikes/cosc3550/demos/ForestFire-FullScreen/ForestFire.java
 * @author Paul
 */
public interface Hittable {
	/**
	 * Checks whether this object is alive.
	 * @return	true if alive, false otherwise
	 */
	public boolean isAlive();
	
	/**
	 * Sets the object to die.
	 */
    public void die();
    
    /**
     * Gets the bounding ball of the object.
     * Note this is a bounding ball instead of bounding rectangle because the objects 
     * implementing this interface are mostly circular in shape.
     * @return
     */
	public java.awt.geom.Ellipse2D.Double getBoundingBall();
}