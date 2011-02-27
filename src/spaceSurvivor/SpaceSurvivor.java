package spaceSurvivor;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferStrategy;
import java.util.Timer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import spaceSurvivor.audio.GameAudioPlayer;
import spaceSurvivor.eventListener.GameKeyListener;
import spaceSurvivor.eventListener.GameMouseListener;
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
	private static final long FRAMES_PER_SECOND = 60;
	
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
	private static final int MAXENEMY = 400;
	
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
    private static final int MAXPOWER = 100;

    private Score score;

    private Image background;

    /**
	 * Time allowed to complete level in seconds.
	 */
	public int timeAllowed = 30;
	
	public boolean timeUp = false;
	
	/**
	 * Timer
	 */
	private Timer timer = new Timer();
	
	/**
	 * Game timer that holds the time remaining in this level.
	 */
	private GameTimer gameTimer;
	
	private int levelTimeSeconds = 60;

        //for incrementally spawning enemies
        private int counter = 0; //frame count
        int eCount=0;       //enemy count
        int pCount=0;       //powerup count
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
	 * Whether player is currently on splash screen.
	 */
	private boolean onSplash = true;
	
	/**
	 * Used to play audio sounds in game.
	 */
	GameAudioPlayer audioFX = new GameAudioPlayer();
	
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
		
		//	start the animation thread if not yet started
		if (anim == null) {
            anim = new Thread(this);
            anim.start();
        }
	}
	
	/**
	 * Set up for level 1.
	 */
	public void setLevel1(){
		//initialize score object
	    score = new Score();
            
	    player = new PlayerShip(score);	//	initialize player ship

		background = new ImageIcon(getClass().getResource("background.gif")).getImage();

        //initialize bullets  ~Andrew
        shots = new Bullet[MAXSHOTS];
        for(int i = 0; i < MAXSHOTS; i++)
            shots[i] = new Bullet(player);
        nextShot = 0;

        //initialize enemies  ~Andrew
        enemyShips = new EnemyShip[MAXENEMY];
        for (int i = 0; i < MAXENEMY; i+=2)
            enemyShips[i] = new DefaultEnemy();
        for (int i = 1; i < MAXENEMY; i+=4)
            enemyShips[i] = new ConfusedEnemy();
        for(int i = 3; i < MAXENEMY; i+=4)
        	enemyShips[i] = new SeekerEnemy();

	    //initialize powerups
	    powers = new PowerUp[MAXPOWER];
	    for (int i=0;i<MAXPOWER;i+=2)
	        powers[i]=new AmmoPowerUp();
	    for (int i=1;i<MAXPOWER;i+=2)
	        powers[i]=new ShieldPowerUp();
	
	  
	    
	    //	initialize time thread
	    //	TODO the following three lines should be called whenever ANY level starts
	    timer.cancel();
	    timer = new Timer();
		gameTimer = new GameTimer(levelTimeSeconds);
		timer.scheduleAtFixedRate(gameTimer, 0, 1000);
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
        
        setCustomCursor();
    }
	
	/**
     * set the custom mouse
     * mouse icon provided by http://www.hscripts.com/freeimages/icons/web-basic-icons/target-clipart.php
     */
	public void setCustomCursor(){
		Toolkit toolkit = Toolkit.getDefaultToolkit();
        
        Image image = new ImageIcon(getClass().getResource("target.gif")).getImage();
        /*
         * 	cursor image is 30x30 px
         * so the hot spot should be at half of this 15,15
         */
        Cursor c = toolkit.createCustomCursor(image , new Point(15, 15), "img");
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
				if ((keyCode == KeyEvent.VK_ESCAPE) || (keyCode == KeyEvent.VK_Q) || (keyCode == KeyEvent.VK_END)){
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
        if (!finishedOff) {
        	System.out.println("finishOff");
        	
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
     * Shows the splash screen.
     * @param g
     */
    private void splashScreen(Graphics g){
    	int splashX = 200;
    	int splashY = 200;
    	int splashWidth = GAME_WIDTH - 2 * splashX;
    	int splashHeight = GAME_HEIGHT - 2 * splashY;
    	Font fontHeader = new Font("SansSerif", Font.BOLD, 20);
    	Font fontSecondary = new Font("SansSerif", Font.PLAIN, 14);
    	
		g.setColor(new Color(128, 255, 128, 56));
	    g.fillRect(splashX, splashY, splashWidth, splashHeight);
	    
	    g.setColor(Color.WHITE);
	    g.setFont(fontHeader);
		g.drawString("Space Survivor", splashX + splashWidth / 2 - 60, splashY + 50);
		
		g.setFont(fontSecondary);
		int lineSpacing = 20;
		int firstLineY = splashY + 100;
		String text1 = "Press Enter to start game.";
		g.drawString(text1, splashX + 200, firstLineY);
		
		g.drawString("Objective of game is to shoot as many enemy ships as possible before time runs out", splashX + 200, firstLineY + lineSpacing * 2);
		g.drawString("and without getting hit by enemy ships too many times.", splashX + 200, firstLineY + lineSpacing * 3);
		g.drawString("Collect power ups to increase your shield and ammo.", splashX + 200, firstLineY + lineSpacing * 4);
		g.drawString("Keep track of your shield and ammo remaining in the top of the screen.", splashX + 200, firstLineY + lineSpacing * 5);
		g.drawString("If your shield gets to 0%, you die.", splashX + 200, firstLineY + lineSpacing * 6);
		g.drawString("The color of the ring around your ship reflects the amount of shield remaining (green=good, red=bad).", splashX + 200, firstLineY + lineSpacing * 7);
		g.drawString("Game controls:", splashX + 200, firstLineY + lineSpacing * 9);
		g.drawString("W moves up", splashX + 250, firstLineY + lineSpacing * 10);
		g.drawString("A moves left", splashX + 250, firstLineY + lineSpacing * 11);
		g.drawString("S moves down", splashX + 250, firstLineY + lineSpacing * 12);
		g.drawString("D moves right", splashX + 250, firstLineY + lineSpacing * 13);
		g.drawString("Move mouse to rotate gun", splashX + 250, firstLineY + lineSpacing * 14);
		g.drawString("Left mouse click to fire gun", splashX + 250, firstLineY + lineSpacing * 15);
		g.drawString("To quit the game: Press Esc, Q, or End", splashX + 250, firstLineY + lineSpacing * 17);
		g.drawString("For testing: press B to toggle bounding ball visibility on/off", splashX + 250, firstLineY + lineSpacing * 19);
		
		onSplash = !gameKeyListener.isKeyEnterPressed();
    }
    
    /**
     * 
     * @param g
     */
    public void gameRender(Graphics g){
        //System.out.println("Begin paint");
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        g.drawImage(background, 0, 0,this);
        
        //	display splash screen at start
        if(onSplash){
        	splashScreen(g);
        	
			return;
        }
        
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
        for (int i=0; i<MAXPOWER; i++)
			powers[i].draw(g);
        
        player.draw(g);
        
        //	draw the bounding boxes if user chose to display them
        if(gameKeyListener.isKeyBPressed()){
        	if(player.isAlive()){
        		Ellipse2D.Double b = player.getBoundingBall();
                g.setColor(Color.WHITE);
                g.drawOval((int)(b.x-b.width/2), (int)(b.y-b.height/2), (int)b.width, (int)b.height);
        	}
        	
            for(int i = 0; i < enemyShips.length; i++){
            	if(enemyShips[i].isAlive()){
            		Ellipse2D.Double b = enemyShips[i].getBoundingBall();
                    g.setColor(Color.WHITE);
                    g.drawOval((int)(b.x - b.width / 2), (int)(b.y - b.height / 2), (int)b.width, (int)b.height);
            	}
            }
            
            for(int i = 0; i < shots.length; i++){
            	if(shots[i].isAlive()){
            		Ellipse2D.Double b = shots[i].getBoundingBall();
                    g.setColor(Color.WHITE);
                    g.drawOval((int)(b.x - b.width / 2), (int)(b.y - b.height / 2), (int)b.width, (int)b.height);
            	}
            }
        }
        
        score.draw(g);
        
        gameTimer.draw(g);
        
        if(gameTimer.getTime() == 0){
        	//	level over
        	g.drawString("Time is up", GAME_WIDTH / 2, GAME_HEIGHT / 2);
        }
        
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
    	for(int i = 0; i < MAXENEMY; i++){
    		if(player.collided(enemyShips[i])){
    			audioFX.playCrash();	//	play crash SFX
    			
    			collided = true;
    			System.out.println("collided with " + enemyShips[i].toString());
                enemyShips[i].die();
                score.addScore(-5);
                if (score.getShield()==0) player.die();
                	score.addShield(-10);
            }
    	}


        //collided with powerup
        for(int i = 0; i < MAXPOWER; i++){
    		if(player.collided(powers[i])){
    			audioFX.playPowerup();	//	play powerup SFX
    			
                powers[i].die();
                score.addScore(10);
                if(powers[i] instanceof AmmoPowerUp) score.addAmmo(10);
                if(powers[i] instanceof ShieldPowerUp) score.addShield(10);
            }
    	}

        //check bullet collisions
        for(int i = 0; i < MAXENEMY; i++){
            if(enemyShips[i].collidedWithBullet()){
                enemyShips[i].die();
                score.addScore(2);
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
			if(!onSplash){
				//spawn enemies incrementally
				spawn();

	            // move the bullets  ~Andrew
	            for (int i = 0; i < MAXSHOTS; i++)
	                shots[i].move();
	            
	            // move the enemies  ~Andrew
	            for (int i = 0; i < MAXENEMY; i++)
	                enemyShips[i].move(player, shots, enemyShips);

	            // update powerups (time could expire)
	                         for (int i=0; i<MAXPOWER; i++)
	                            powers[i].update();
	            
				player.move();
				
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
	                
	                if (!shots[nextShot].isAlive() && score.getAmmo()!=0){
	                    shots[nextShot].activate();
	                    nextShot = (nextShot + 1) % MAXSHOTS;
	                    score.addAmmo(-1);
	                    score.addScore(-1);
	                    audioFX.playGunShot();
	                }
	            }
			}
			
			screenUpdate();
			
            //	Pause for animation
            try
            {
                Thread.sleep(FRAME_DELAY);
            } catch (InterruptedException e)
            {}
        }
		
        finishOff();	//	not necessary but here for safety
	}

        // spawn enemies and power-ups slowly. can be replaced by multiple levels later
        private void spawn(){
            if(gameTimer.getTime() != 0){
                counter++;
                if (counter % 150 == 0 && eCount<MAXENEMY){
                    enemyShips[eCount].activate();
                    eCount++;
                }
                if (counter % 300 == 0 && pCount<MAXPOWER){
                    powers[pCount].activate();
                    pCount++;
                }
            }
        }
        


	/**
	 * Detect user's screen size and set game for this size.
	 */
	private static void detectScreenSize(){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	
		//	set game full screen size to match user's current screen size
		GAME_WIDTH = screenSize.width;
		GAME_HEIGHT = screenSize.height;
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
