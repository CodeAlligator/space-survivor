package spaceSurvivor;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

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
	
	private GraphicsDevice gd;
    private Graphics gScr;
    private BufferStrategy bufferStrategy;
    
    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 600;
    
    // used for full-screen exclusive mode
    private static final int NUM_BUFFERS = 2;    // used for page flipping
    
    private boolean finishedOff = false;
    
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
	
	/**
	 * Player's ship.
	 */
	PlayerShip player;
	
	/**
	 * Key listener.
	 */
	GameKeyListener gameKeyListener;
	
	/**
	 * Mouse listener.
	 */
	GameMouseListener gameMouseListener;
	
	/**
	 * Default constructor.
	 */
	public SpaceSurvivor() {
		super("Space Survivor");

        initFullScreen();
        readyForTermination();
		
		//	create listeners for keyboard and mouse
		gameKeyListener = new GameKeyListener();
		addKeyListener(gameKeyListener);
		
		gameMouseListener = new GameMouseListener();
		addMouseListener(gameMouseListener);
		
		
        //	load player's ship sprite images
        /*
         * TODO create sprites
         * playerShipImage = new ImageIcon(getClass().getResource("sprites/playerShip.gif")).getImage();
         */
        
		setLevel1();
		
		//	start the animation thread
		if (anim == null) {
            anim = new Thread(this);
            anim.start();
        }
	}
	
	public void setLevel1(){
		player = new PlayerShip();
	}
	
	private void initFullScreen()
    {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        gd = ge.getDefaultScreenDevice();

        setUndecorated(true);    // no menu bar, borders, etc. or Swing components
        setIgnoreRepaint(true);  // turn off all paint events since doing active rendering
        setResizable(false);

        if (!gd.isFullScreenSupported()) {
            System.out.println("Full-screen exclusive mode not supported");
            System.exit(0);
        }
        gd.setFullScreenWindow(this); // switch on full-screen exclusive mode

        // we can now adjust the display modes, if we wish
         setDisplayMode(800, 600, 16);
        // setDisplayMode(1280, 1024, 32);

        setBufferStrategy();
    }
	
	private void readyForTermination()
    {
        addKeyListener( new KeyAdapter() {
                // listen for esc, q, end, ctrl-c on the canvas to
                // allow a convenient exit from the full screen configuration
                public void keyPressed(KeyEvent e)
                { int keyCode = e.getKeyCode();
                    if ((keyCode == KeyEvent.VK_ESCAPE) || (keyCode == KeyEvent.VK_Q) ||
                    (keyCode == KeyEvent.VK_END) ||
                    ((keyCode == KeyEvent.VK_C) && e.isControlDown()) ) {
                        anim = null;
                    }
                }
            });

        // for shutdown tasks
        // a shutdown may not only come from the program
        Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run()
                { anim = null;
                    finishOff();
                }
            });
    }
	
	private void setDisplayMode(int width, int height, int bitDepth)
    // attempt to set the display mode to the given width, height, and bit depth
    {
        if (!gd.isDisplayChangeSupported()) {
            System.out.println("Display mode changing not supported");
            return;
        }

        DisplayMode dm = new DisplayMode(width, height, bitDepth,
                DisplayMode.REFRESH_RATE_UNKNOWN);   // any refresh rate
        try {
            gd.setDisplayMode(dm);
            System.out.println("Display mode set to: (" + width + "," +
                height + "," + bitDepth + ")");
        }
        catch (IllegalArgumentException e)
        {  System.out.println("Error setting Display mode (" + width + "," +
                height + "," + bitDepth + ")");  }

        try {  // sleep to give time for the display to be changed
            Thread.sleep(1000);  // 1 sec
        }
        catch(InterruptedException ex){}
    }  // end of setDisplayMode()

    private void setBufferStrategy()
    /* Switch on page flipping: NUM_BUFFERS == 2 so
    there will be a 'primary surface' and one 'back buffer'.
     */
    { try {
            createBufferStrategy(NUM_BUFFERS);
        }
        catch (Exception e) {
            System.out.println("Error while creating buffer strategy");
            System.exit(0);
        }
        bufferStrategy = getBufferStrategy();  // store for later
    }
	
    /* Tasks to do before terminating. Called at end of run()
    and via the shutdown hook in readyForTermination().

    The call at the end of run() is not really necessary, but
    included for safety. The flag stops the code being called
    twice.
     */
    private void finishOff()
    { // System.out.println("finishOff");
        if (!finishedOff) {
            finishedOff = true;
            restoreScreen();
            System.exit(0);
        }
    }
    
    /* Switch off full screen mode. This also resets the
    display mode if it's been changed.
     */
    private void restoreScreen(){
    	Window w = gd.getFullScreenWindow();
        if (w != null)
            w.dispose();
        gd.setFullScreenWindow(null);
    }
    
    private void screenUpdate()
    // use active rendering
    { try {
            gScr = bufferStrategy.getDrawGraphics();
            gameRender(gScr);
            gScr.dispose();
            if (!bufferStrategy.contentsLost())
                bufferStrategy.show();
            else
                System.out.println("Contents Lost");
            // Sync the display on some systems.
            // (on Linux, this fixes event queue problems)
            Toolkit.getDefaultToolkit().sync();
        }
        catch (Exception e)
        { e.printStackTrace();
            anim = null;
        }
    }
    
    public void gameRender(Graphics g)
    {
        //System.out.println("Begin paint");
        g.setColor(Color.white);
        g.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);
        //System.out.println("Begin scenery");
        /*for (int i = 0; i < scenery.length; i++)
        {
            //System.out.println("In paint loop, i="+i);
            scenery[i].paint(g, boxDebug);
        }*/
        player.draw(g);

        /*if (wonGame())
        {
            g.setColor(Color.black);
            g.setFont(font);
            g.drawString("You win!",100,100);
        }

        if (lostGame())
        {
            g.setColor(Color.black);
            g.setFont(font);
            g.drawString("You lose.",100,100);
        }*/
    }
    
	/**
	 * 
	 */
	@Override
	public void run() {
		while(anim != null){
			player.move();
			screenUpdate();
			//System.out.println("updated");
			
			//	check for player input (mouse and keyboard)
			player.setGunX(gameMouseListener.getMouseX());
			player.setGunY(gameMouseListener.getMouseY());
			player.setUpKey(gameKeyListener.isKeyUpPressed());
			player.setDownKey(gameKeyListener.isKeyDownPressed());
			player.setLeftKey(gameKeyListener.isKeyLeftPressed());
			player.setRightKey(gameKeyListener.isKeyRightPressed());
			player.setSpacebarKey(gameKeyListener.isKeySpacebarPressed());
			
            try
            {
                Thread.sleep(FRAME_DELAY);
            } catch (InterruptedException e)
            {}
        }
        finishOff();
	}
	
    public static void main(String args[])
    {
        new SpaceSurvivor();
    }
	
    public PlayerShip getShip(){
        return player;
    }
}