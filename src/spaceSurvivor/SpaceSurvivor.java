package spaceSurvivor;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import spaceSurvivor.powerUp.AmmoPowerUp;
import spaceSurvivor.powerUp.PowerUp;
import spaceSurvivor.powerUp.ShieldPowerUp;

import spaceSurvivor.ship.EnemyShip;
import spaceSurvivor.ship.PlayerShip;
import spaceSurvivor.ship.Bullet;
import spaceSurvivor.ship.enemies.ConfusedEnemy;
import spaceSurvivor.ship.enemies.DefaultEnemy;
import spaceSurvivor.ship.enemies.SeekerEnemy;

/**
 * <code>SpaceSurvivor</code> is the main (GUI) class for this game.
 * Some methods and additional source code are from Dr. Slattery:
 * http://www.mscs.mu.edu/~mikes/cosc3550/demos/ForestFire-FullScreen/ForestFire.java
 * @author Paul
 */
public class SpaceSurvivor extends JFrame implements Runnable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7729402781315095387L;

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
    public static int GAME_WIDTH = 1280;
    
    /**
     * Height of game window.
     */
    public static int GAME_HEIGHT = 1024;
    
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
	private static final int MAXENEMY = 6;
	
    //Bullet variables  ~Andrew
	/**
	 * Array of player's bullets.
	 */
    private Bullet[] shots;
    private int nextShot;
    
    /**
     * Maximum number of bullets that can be on screen at once.
     */
    private static final int MAXSHOTS = 30;

    private PowerUp[] powers;

    private Score score;

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
        for(int i = 0; i < MAXSHOTS; i++)
            shots[i] = new Bullet(player);
        nextShot = 0;

        //initialize enemies  ~Andrew
        enemyShips = new EnemyShip[MAXENEMY];
        for (int i = 0; i < 2; i++)
            enemyShips[i] = new DefaultEnemy();
        for (int i = 2; i < 4; i++)
            enemyShips[i] = new ConfusedEnemy();
        for(int i = 4; i < 6; i++)
        	enemyShips[i] = new SeekerEnemy();

                //initialize powerups
                powers = new PowerUp[4];
                for (int i=0;i<2;i++)
                    powers[i]=new AmmoPowerUp();
                for (int i=2;i<4;i++)
                    powers[i]=new ShieldPowerUp();

                //initialize score object
                score = new Score();
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
        
        /*
         * set the custom mouse
         * mouse icon provided by http://www.hscripts.com/freeimages/icons/web-basic-icons/target-clipart.php
         */
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        //Image image = toolkit.getImage("target.gif");

        //cursor image didn't work for me - but this line made it work. Also the SVN didn't get the image for me when i updated.
        Image image = new ImageIcon(getClass().getResource("target.gif")).getImage();

        Cursor c = toolkit.createCustomCursor(image , new Point(0,0), "img");
        this.setCursor(c);

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
        g.setColor(Color.BLACK);
        g.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);
        //System.out.println("Begin scenery");
        /*for (int i = 0; i < scenery.length; i++)
        {
            //System.out.println("In paint loop, i="+i);
            scenery[i].paint(g, boxDebug);
        }*/

        // Draw the bullets  ~Andrew
        for (int i = 0; i < MAXSHOTS; i++)
            shots[i].draw(g);
        
		// Draw the enemies  ~Andrew
		for (int i=0; i<MAXENEMY; i++)
			enemyShips[i].draw(g);

        //Draw the powerups
        for (int i=0; i<4; i++)
			powers[i].draw(g);
        
        player.draw(g);
        
        score.draw(g);

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
     * Determines if player ship has collided with any enemy ships.
     * @return
     */
    public boolean checkPlayerCollisions(){
    	boolean collided = false; //unnecessary?

        //collided with enemy
    	for(int i = 0; i < 6; i++){
    		if(player.collided(enemyShips[i])){
    			collided = true;
    			System.out.println("collided with " + enemyShips[i].toString());
                        enemyShips[i].die();
                        score.addScore(-5);
                        if (score.getShield()==0) player.die();
                        score.addShield(-10);
                }
    	}


        //collided with powerup
        for(int i = 0; i < 4; i++){
    		if(player.collided(powers[i])){
                        powers[i].die();
                        score.addScore(10);
                        if (powers[i].type()==1) score.addAmmo(10);
                        if (powers[i].type()==2) score.addShield(10);
                }
    	}

    	return collided;
    }
    
	/**
	 * Main animation loop of this game.
	 */
	@Override
	public void run() {
		while(anim != null){

            // move the bullets  ~Andrew
            for (int i = 0; i < MAXSHOTS; i++)
                shots[i].move();
            
            // move the enemies  ~Andrew
            for (int i = 0; i < 6; i++)
                enemyShips[i].move(player, shots);

            // update powerups (time could expire)
                         for (int i=0; i<4; i++)
                            powers[i].update();
            
			player.move();
			
			screenUpdate();
			
			//	check if player collided with any ships
			if(checkPlayerCollisions())
			{
				/*
				 * TODO add collision consequence logic here
				 * decrease score
				 * decrease shields
				 * if shields at 0, player dies
				 */
                            //moved this logic to collision method above
			}
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
                
                if (!shots[nextShot].isActive() && score.getAmmo()!=0){
                    shots[nextShot].activate();
                    nextShot = (nextShot + 1) % MAXSHOTS;
                    score.addAmmo(-1);
                    score.addScore(-1);
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
	 * Detect user's screen size and set game for this size.
	 */
	private static void detectScreenSize(){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	
		//	based on user's screen width, use the standard size
    	switch(screenSize.width) {
    	case 640:
    		GAME_WIDTH = 640;
    		GAME_HEIGHT = 480;
    		break;
    	case 800:
    		GAME_WIDTH = 800;
    		GAME_HEIGHT = 600;
    		break;
    	case 1024:
    		GAME_WIDTH = 1024;
    		GAME_HEIGHT = 768;
    		break;
    	case 1280:
    		GAME_WIDTH = 1280;
    		GAME_HEIGHT = 1024;
    		break;
    	case 1600:
    		GAME_WIDTH = 1600;
    		GAME_HEIGHT = 1200;
    		break;
    	default:
    		GAME_WIDTH = 800;
    		GAME_HEIGHT = 600;
    		break;
    	}
	}
	
	/**
	 * Gets this game started by instantiating a <code>SpaceSurvivor</code> object.
	 * Game screen size automatically detected from user's current screen resolution.
	 * Otherwise, run this application with two parameters: screen width, screen height
	 * @param args	[0]-screen width [1]-screen height
	 */
    public static void main(String args[])
    {
    	if(args.length >= 2){
    		GAME_WIDTH = Integer.getInteger(args[0]);
    		GAME_HEIGHT = Integer.getInteger(args[1]);
    	}
    	else{
    		SpaceSurvivor.detectScreenSize();
    	}
    	
        new SpaceSurvivor();
    }
}
