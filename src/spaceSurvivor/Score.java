package spaceSurvivor;

import java.awt.Color;
import java.awt.Graphics;

/**
 * <code>Score</code> maintains the player's score for this game.
 * @author Paul
 */
public class Score {
	/**
	 * Current score of this game.
	 */
	private int score;
	
	/**
	 * Shield remaining counter.
	 */
	private int shield;
	
	/**
	 * Ammo remaining counter.
	 */
	private int ammo;

	/**
	 * Instantiates new <code>Score</code> object with score of 0, shield of 50, and ammo of 50.
	 */
	public Score() {
		this(50, 50);
	}
	
	/**
	 * Instantiates new <code>Score</code> object with the values being passed.
	 */
	public Score(int shield, int ammo) {
		this.score = 0;
        this.shield = shield;
        this.ammo = ammo;
	}
	
	/**
	 * Resets this score to 0 points.
	 */
	public void resetScore(){
		score = 0;
        shield = 0;
        ammo = 0;
	}
	
	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @return this score
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * 
	 * @return	this shield
	 */
    public int getShield() {
		return shield;
	}

    /**
     * 
     * @return	this ammo
     */
    public int getAmmo() {
		return ammo;
	}
    
    /**
     * 
     * @param add	amount to add to this score
     */
    public void addScore(int add){
        score += add;
    }
    
    /**
     * 
     * @param add	amount to add to this shield
     */
    public void addShield(int add){
        shield += add;
        if (shield > 100) shield = 100;
        if (shield < 0) shield = 0;
    }
    
    /**
     * 
     * @param add	amount to add to this ammo
     */
    public void setAmmo(int ammo){
        this.ammo = ammo;
    }
    
    /**
     * 
     * @param add	amount to add to this shield
     */
    public void setShield(int shield){
    	this.shield = shield;
        if (shield > 100) shield = 100;
        if (shield < 0) shield = 0;
    }
    
    /**
     * 
     * @param add	amount to add to this ammo
     */
    public void addAmmo(int add){
        ammo += add;
    }
    
    /**
     * Draws this score, shield, and ammo to the game screen.
     * @param g
     */
    public void draw(Graphics g){
        g.setColor(Color.GREEN);
        g.drawString("Shield: "+shield+"%", 10, 10);
        g.drawString("Ammo: "+ammo, SpaceSurvivor.GAME_WIDTH-70, 10);

        g.setColor(Color.ORANGE);
        g.drawString("Score: "+score, SpaceSurvivor.GAME_WIDTH/2-70, 10);

    }
}