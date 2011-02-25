package spaceSurvivor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * <code>GameKeyListener</code> handles all keyboard events.
 * @author Paul
 */
public class GameKeyListener implements KeyListener{
	private boolean keyUpPressed = false;
	private boolean keyDownPressed = false;
	private boolean keyLeftPressed = false;
	private boolean keyRightPressed = false;
	private boolean keyBPressed = false;
	
	@Override
	public void keyPressed(KeyEvent e) {
            int c = e.getKeyCode();
            switch (c) {
            case KeyEvent.VK_W:
            	keyUpPressed = true;
            	//System.out.println("Key press: up");
                break;
            case KeyEvent.VK_A:
            	keyLeftPressed = true;
            	//System.out.println("Key press: left");
                break;
            case KeyEvent.VK_S:
            	keyDownPressed = true;
            	//System.out.println("Key press: down");
                break;
            case KeyEvent.VK_D:
            	keyRightPressed = true;
            	//System.out.println("Key press: right");
                break;
            }
	}

	@Override
	public void keyReleased(KeyEvent e) {
            int c = e.getKeyCode();
            switch (c) {
            case KeyEvent.VK_W:
            	keyUpPressed = false;
            	//System.out.println("Key release: up");
                break;
            case KeyEvent.VK_A:
            	keyLeftPressed = false;
            	//System.out.println("Key release: left");
                break;
            case KeyEvent.VK_S:
            	keyDownPressed = false;
            	//System.out.println("Key release: down");
                break;
            case KeyEvent.VK_D:
            	keyRightPressed = false;
            	//System.out.println("Key release: right");
                break;
            case KeyEvent.VK_B:
            	keyBPressed = !keyBPressed;
            	//System.out.println("Key press: b");
            	break;
            }

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public boolean isKeyUpPressed() {
		return keyUpPressed;
	}

	public boolean isKeyDownPressed() {
		return keyDownPressed;
	}

	public boolean isKeyLeftPressed() {
		return keyLeftPressed;
	}

	public boolean isKeyRightPressed() {
		return keyRightPressed;
	}

	public boolean isKeyBPressed() {
		return keyBPressed;
	}
}