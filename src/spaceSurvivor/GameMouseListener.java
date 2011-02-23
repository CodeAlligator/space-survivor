package spaceSurvivor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * <code>GameMouseListener</code> handles all mouse events.
 * @author Paul
 */
public class GameMouseListener implements MouseListener, MouseMotionListener {
	/**
	 * X coordinate of mouse location.
	 */
	private int mouseX;
	
	/**
	 * Y coordinates of mouse location.
	 */
	private int mouseY;
	
	/**
	 * whether mouse button was clicked
	 */
	private boolean clicked = false;
	
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("mouse clicked");
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println("mouse released");
		
		clicked = true;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
    	System.out.println("mouse dragged");
    	
    	mouseMoved(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		System.out.println("mouse moved");
		
		mouseX = e.getX();
        mouseY = e.getY();
	}
	
    public boolean getClicked(){
        return clicked;
    }

    public void clickReset(){
        clicked = false;
    }
    
	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}
}