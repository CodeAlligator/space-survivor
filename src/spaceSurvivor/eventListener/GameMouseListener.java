package spaceSurvivor.eventListener;

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
	private boolean leftClicked = false;
	
	private boolean rightClicked = false;
	
	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println("mouse clicked");
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
			//	left mouse button
			leftClicked = true;
		}
		else if(e.getButton() == MouseEvent.BUTTON3){
			//	right mouse button
			rightClicked = true;
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
    	//System.out.println("mouse dragged");
    	mouseMoved(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		//System.out.println("mouse moved");
		mouseX = e.getX();
        mouseY = e.getY();
	}
	
    public boolean isLeftClicked(){
        return leftClicked;
    }

    public void leftClickReset(){
        leftClicked = false;
    }
    
    public boolean isRightClicked(){
        return rightClicked;
    }

    public void rightClickReset(){
    	rightClicked = false;
    }
    
	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}
}