package spaceSurvivor.powerUp;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;
import spaceSurvivor.SpaceSurvivor;

public class ShieldPowerUp extends PowerUp {
	/**
	 * Amount of additional shield that player receives if he gets this power up.
	 */
	public static final int SHIELD_AMMOUNT = 10;
    private static Random generator = new Random();
    Image shieldPic;
	
	public ShieldPowerUp() {
        //set random starting position away from edges
        x = generator.nextInt(SpaceSurvivor.GAME_WIDTH - 50) + 25;
        y = generator.nextInt(SpaceSurvivor.GAME_HEIGHT - 50) + 25;
        shieldPic = new ImageIcon(getClass().getResource("ShieldPowerUp.gif")).getImage();
        alive = false;
    }

    @Override
    public void draw(Graphics g){
    	if(this.isAlive())
    		g.drawImage(shieldPic, (int)x, (int)y, null);
    }
}