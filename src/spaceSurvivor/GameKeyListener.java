package spaceSurvivor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 
 * @author Paul
 *
 */
public class GameKeyListener implements KeyListener{

	@Override
	public void keyPressed(KeyEvent e) {
            int c = e.getKeyCode();
            switch (c) {
            case KeyEvent.VK_W: SpaceSurvivor.getShip().setUpKey(true);
                break;
            case KeyEvent.VK_A: SpaceSurvivor.player.setLeftKey(true);
                break;
            case KeyEvent.VK_S: player.setDownKey(true);
                break;
            case KeyEvent.VK_D: player.setRightKey(true);
                break;
                        }

	}

	@Override
	public void keyReleased(KeyEvent e) {
            int c = e.getKeyCode();
            switch (c) {
            case KeyEvent.VK_W: player.setUpKey(false);
                break;
            case KeyEvent.VK_A: player.setLeftKey(false);
                break;
            case KeyEvent.VK_S: player.setDownKey(false);
                break;
            case KeyEvent.VK_D: player.setRightKey(false);
                break;
            }

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
