package spaceSurvivor;

/**
 * <code>Score</code> maintains the player's score for this game.
 * @author Paul
 */
public class Score {
	/**
	 * Current score of this game.
	 */
	private int score;
	
	public Score() {
		
	}
	
	/**
	 * Resets this score to 0 points.
	 */
	public void resetScore(){
		score = 0;
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
}