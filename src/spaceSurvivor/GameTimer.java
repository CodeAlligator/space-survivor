package spaceSurvivor;

import java.awt.Color;
import java.awt.Graphics;
import java.util.TimerTask;

public class GameTimer extends TimerTask{
	/**
	 * Current time of this game.
	 */
	private int time;
	
	/**
	 * Instantiates new <code>GameTimer</code> object with the time passed as am argument.
	 * @param time	time in seconds
	 */
	public GameTimer(int time) {
		this.setTime(time);
	}
	
	@Override
	/**
	 * Called at every interval that this TimerTask is called.
	 */
	public void run() {
		decrementTime();
	}
	
    /**
     * Draws the time to the game screen.
     * @param g
     */
    public void draw(Graphics g){
        g.setColor(Color.YELLOW);
        g.drawString("Time left: " + time + "s", SpaceSurvivor.GAME_WIDTH / 2 - 80, 30);

    }
    
    /**
     * Decreases time by 1 second. Will not get below 0.
     */
    public void decrementTime(){
    	if(time > 0){
    		time--;
    	}
    	else{
    		
    	}
    }
    
	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		if(time >= 0){
			this.time = time;
		}
		else{
			time = 0;
		}
	}
}