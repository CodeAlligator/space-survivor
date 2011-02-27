package spaceSurvivor;

import java.awt.Color;
import java.awt.Font;
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
        Font font = new Font("SansSerif", Font.PLAIN, 10);
        g.setFont(font);
        int changeAtTime = 15;
        
        if(time < changeAtTime){
        	font = new Font("SansSerif", Font.PLAIN, 50 - time);
            g.setFont(font);
        	g.drawString("Time left: " + time + "s", SpaceSurvivor.GAME_WIDTH / 2 - 130 + changeAtTime - time, 50 + changeAtTime - time);
        }
        else{
        	g.drawString("Time left: " + time + "s", SpaceSurvivor.GAME_WIDTH / 2 - 80, 40);
        }
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
    
    /**
     * Gets the amount of time remaining.
     * @return	time in seconds
     */
	public int getTime() {
		return time;
	}
	
	/**
	 * Sets the amount of time remaining.
	 * @param time
	 */
	public void setTime(int time) {
		if(time >= 0){
			this.time = time;
		}
		else{
			time = 0;
		}
	}
}