package spaceSurvivor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * <code>GameMouseListener</code> handles all mouse events.
 * @author Paul
 */
public class GameMouseListener implements MouseMotionListener, MouseListener {
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
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		clicked = true;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

    public boolean getClicked(){
        return clicked;
    }

    public void clickReset(){
        clicked = false;
    }
}
