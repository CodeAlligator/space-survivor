package spaceSurvivor.powerUp;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;
import spaceSurvivor.SpaceSurvivor;

public class BigBulletPowerUp extends PowerUp{
	private static Random generator = new Random ();
	private Image ammoPic;

	public BigBulletPowerUp() {
        //set random starting position away from edges
        x = generator.nextInt(SpaceSurvivor.GAME_WIDTH - 50) + 25;
        y = generator.nextInt(SpaceSurvivor.GAME_HEIGHT - 50) + 25;
        ammoPic = new ImageIcon(getClass().getResource("BigBulletPowerUp.gif")).getImage();
        alive = false;
    }

    @Override
    public void draw(Graphics g){
    if(isAlive())
        g.drawImage(ammoPic, (int)x, (int)y, null);
    }
}
