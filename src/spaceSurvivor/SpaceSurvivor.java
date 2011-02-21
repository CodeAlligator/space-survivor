package spaceSurvivor;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import spaceSurvivor.ship.EnemyShip;
import spaceSurvivor.ship.PlayerShip;

/**
 * <code>SpaceSurvivor</code> is the main class for this game.
 * @author Paul
 *
 */
public class SpaceSurvivor extends JFrame implements Runnable{
	/**
	 * Frames per second that this game will animate.
	 */
	private static final long FRAMES_PER_SECOND = 30;
	
	/**
	 * Delay between frames in milliseconds.
	 */
	private static final long FRAME_DELAY = 1000 / FRAMES_PER_SECOND;

	/**
	 * An array of enemy ships. The particular type of each enemy ship is 
	 * determined at each level.
	 */
	private EnemyShip[] enemyShips;
	
	/**
	 * Time in current level.
	 */
	private int time;
	
	/**
	 * Animation thread.
	 */
	private Thread anim = null;
	
	/**
	 * Player's ship sprite
	 */
        private Image playerShipImage;

        PlayerShip player;
	/**
	 * Default constructor.
	 */
	public SpaceSurvivor() {
		
		//	create listeners for keyboard and mouse
		addMouseListener(new GameMouseListener());
        addKeyListener(new GameKeyListener());
		
        //	load player's ship sprite images
        /*
         * TODO create sprites
         * playerShipImage = new ImageIcon(getClass().getResource("sprites/playerShip.gif")).getImage();
         */
        
		//	start the animation thread
		if (anim == null) {
            anim = new Thread(this);
            anim.start();
        }
	}
	
	/**
	 * When running as an application, this method is called first.
	 * @param args
	 */
	public static void main(String[] args) {
		
	}
	
	/**
	 * 
	 */
	@Override
	public void run() {
		while(anim  != null)
        {
            
            try
            {
                Thread.sleep(FRAME_DELAY);
            } catch (InterruptedException e)
            {}
        }
        finishOff();
	}

	private void finishOff() {
		// TODO implement this method
	}
}