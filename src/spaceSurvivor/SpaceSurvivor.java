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
import spaceSurvivor.ship.Bullet;

/**
 * <code>SpaceSurvivor</code> is the main (GUI) class for this game.
 * Some methods and additional source code are from Dr. Slattery:
 * http://www.mscs.mu.edu/~mikes/cosc3550/demos/ForestFire-FullScreen/ForestFire.java
 * @author Paul
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
    
    /**
     * Width of game window.
     */
    public static final int GAME_WIDTH = 800;
    
    /**
     * Height of game window.
     */
    public static final int GAME_HEIGHT = 600;
    
    /**
     * Bit depth of display.
     */
    public static final int BIT_DEPTH = 16;
    
    /**
     * Number of buffers for page flipping (used for full screen exclusive mode).
     */
    private static final int NUM_BUFFERS = 2;
    
    /**
     * Application finished.
     */
    private boolean finishedOff = false;
    
	/**
	 * An array of enemy ships. The particular type of each enemy ship is 
	 * determined at each level.
	 */
	private EnemyShip[] enemyShips;
	
    //Bullet variables  ~Andrew
	/**
	 * Array of player's bullets.
	 */
    private Bullet[] shots;
    private int nextShot;
    
    /**
     * Maximum number of bullets player has.
     */
    private static final int MAXSHOTS = 30;

	/**
	 * Time in current level.
	 */
	private int time;
	
	/**
	 * Animation thread.
	 */
	private Thread anim = null;
	
	/**
	 * Player's ship sprite.
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
		super("Space Survivor");	//	Instantiate new JFrame with title
		
        initFullScreen();
        readyForTermination();
		
        //	create listener for keyboard
		gameKeyListener = new GameKeyListener();
		addKeyListener(gameKeyListener);
		
		//	create listeners for mouse
		gameMouseListener = new GameMouseListener();
		addMouseListener(gameMouseListener);		//	handles mouse click, enter, exit, press, release
		addMouseMotionListener(gameMouseListener);	//	handles mouse drag and move
		
        //	load player's ship sprite images
        /*
         * TODO create sprites
         * playerShipImage = new ImageIcon(getClass().getResource("sprites/playerShip.gif")).getImage();
         */
        
		setLevel1();	//	set up for level 1
		
		//	start the animation thread
		if (anim == null) {
            anim = new Thread(this);
            anim.start();
        }
	}
	
	/**
	 * Set up for level 1.
	 */
	public void setLevel1(){
		player = new PlayerShip();	//	initialize player ship
		
        //initialize bullets  ~Andrew
        shots = new Bullet[MAXSHOTS];
        for(int i=0;i<MAXSHOTS;i++)
            shots[i] = new Bullet(player);
        nextShot = 0;
	}
	
	/**
	 * 
	 */
	private void initFullScreen(){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        gd = ge.getDefaultScreenDevice();

        setUndecorated(true);    // no menu bar, borders, etc. or Swing components
        setIgnoreRepaint(true);  // turn off all paint events since doing active rendering
        setResizable(false);
        
        //	If full screen mode not supported, exit application
        if (!gd.isFullScreenSupported()) {
            System.out.println("Full-screen exclusive mode not supported");
            System.exit(0);
        }
        
        gd.setFullScreenWindow(this); // switch on full-screen exclusive mode

        // we can now adjust the display modes, if we wish
         setDisplayMode(GAME_WIDTH, GAME_HEIGHT, BIT_DEPTH);
        // setDisplayMode(1280, 1024, 32);

        setBufferStrategy();
    }
	
	/**
	 * If player presses any of these keys: escape, q, end, ctrl+c, 
	 * the game will terminate.
	 */
	private void readyForTermination(){
		addKeyListener( new KeyAdapter() {
			public void keyPressed(KeyEvent e){
				int keyCode = e.getKeyCode();
				if ((keyCode == KeyEvent.VK_ESCAPE) || (keyCode == KeyEvent.VK_Q) ||
				(keyCode == KeyEvent.VK_END) || ((keyCode == KeyEvent.VK_C) && e.isControlDown()) ) {
					anim = null;
				}
			}
		});

        // for shutdown tasks
        // a shutdown may not only come from the program
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run(){
            	anim = null;
                finishOff();
            }
        });
    }
	
	/**
	 * Attempts to set the display mode to the given width, height, and bit depth.
	 * @param width
	 * @param height
	 * @param bitDepth
	 */
	private void setDisplayMode(int width, int height, int bitDepth){
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
    }
	
	/**
	 *  Switch on page flipping: NUM_BUFFERS == 2 so there will be a 'primary surface' 
	 *  and one 'back buffer'.
     */
    private void setBufferStrategy(){
    	try {
            createBufferStrategy(NUM_BUFFERS);
        }
        catch (Exception e) {
            System.out.println("Error while creating buffer strategy");
            System.exit(0);
        }
        
        bufferStrategy = getBufferStrategy();  // store for later
    }
	
    /**
     * Tasks to do before terminating. Called at end of run() and via the shutdown 
     * hook in readyForTermination(). The call at the end of run() is not really 
     * necessary, but included for safety. The flag stops the code being called twice.
     */
    private void finishOff(){
    	System.out.println("finishOff");
    	
        if (!finishedOff) {
            finishedOff = true;
            restoreScreen();
            System.exit(0);
        }
    }
    
    /**
     * Switch off full screen mode.
     * This also resets the display mode if it has been changed.
     */
    private void restoreScreen(){
    	Window w = gd.getFullScreenWindow();
        if (w != null)
            w.dispose();
        gd.setFullScreenWindow(null);
    }
    
    /**
     * use active rendering
     */
    private void screenUpdate(){
    	try {
            gScr = bufferStrategy.getDrawGraphics();
            gameRender(gScr);
            gScr.dispose();
            if (!bufferStrategy.contentsLost()){
            	bufferStrategy.show();
            }
            else{
            	System.out.println("Contents Lost");
            }
            
            // Sync the display on some systems.
            // (on Linux, this fixes event queue problems)
            Toolkit.getDefaultToolkit().sync();
        }
        catch (Exception e)
        {
        	e.printStackTrace();
            anim = null;
        }
    }
    
    /**
     * 
     * @param g
     */
    public void gameRender(Graphics g){
        //System.out.println("Begin paint");
        g.setColor(Color.white);
        g.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);
        //System.out.println("Begin scenery");
        /*for (int i = 0; i < scenery.length; i++)
        {
            //System.out.println("In paint loop, i="+i);
            scenery[i].paint(g, boxDebug);
        }*/

        // Draw the bullets  ~Andrew
        for (int i=0; i<MAXSHOTS; i++)
            shots[i].draw(g);

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
	 * Main animation loop of this game.
	 */
	@Override
	public void run() {
		while(anim != null){

            // move the bullets  ~Andrew
            for (int i=0; i<MAXSHOTS; i++)
                shots[i].move();

			player.move();	//	move the player
			
			screenUpdate();
			
			//	check for player input (mouse and keyboard)
			player.setGunX(gameMouseListener.getMouseX());
			player.setGunY(gameMouseListener.getMouseY());
			player.setUpKey(gameKeyListener.isKeyUpPressed());
			player.setDownKey(gameKeyListener.isKeyDownPressed());
			player.setLeftKey(gameKeyListener.isKeyLeftPressed());
			player.setRightKey(gameKeyListener.isKeyRightPressed());

            // create new bullets
            if (gameMouseListener.getClicked()){
                gameMouseListener.clickReset();
                if (!shots[nextShot].isActive()){
                    shots[nextShot].activate();
                    nextShot = (nextShot+1) %MAXSHOTS;
                }
            }
            
            //	Pause for animation
            try
            {
                Thread.sleep(FRAME_DELAY);
            } catch (InterruptedException e)
            {}
        }
		
        finishOff();	//	not necessary but here for safety
	}
	
	/**
	 * Gets this game started by instantiating a <code>SpaceSurvivor</code> object.
	 * @param args
	 */
    public static void main(String args[])
    {
        new SpaceSurvivor();
    }
}