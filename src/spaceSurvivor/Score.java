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
         * Also includes ammo and shield counters
	 */
	private int score, shield, ammo;
	
	public Score() {
		score = 0;
                shield = 50;
                ammo = 50;
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
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

        public int getShield() {
		return shield;
	}

        public int getAmmo() {
		return ammo;
	}

        public void addScore(int add){
            score +=add;
        }

        public void addShield(int add){
            shield +=add;
            if (shield>100) shield = 100;
            if (shield<0) shield = 0;
        }

        public void addAmmo(int add){
            ammo +=add;
        }

        public void draw(Graphics g){
            g.setColor(Color.GREEN);
            g.drawString("Shield: "+shield+"%", 10, 10);
            g.drawString("Ammo: "+ammo, SpaceSurvivor.GAME_WIDTH-70, 10);

            g.setColor(Color.ORANGE);
            g.drawString("Score: "+score, SpaceSurvivor.GAME_WIDTH/2-70, 10);

        }
}