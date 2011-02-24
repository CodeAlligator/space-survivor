package spaceSurvivor.powerUp;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;
import spaceSurvivor.SpaceSurvivor;

public class AmmoPowerUp extends PowerUp{
	/**
	 * Amount of additional ammo that player receives if he gets this power up.
	 */
	public static final int AMMO_AMMOUNT = 10;
	private static Random generator = new Random ();
	private Image ammoPic;

	public AmmoPowerUp() {
        //set random starting position away from edges
        this.setX(generator.nextInt(SpaceSurvivor.GAME_WIDTH-50)+25);
        this.setY(generator.nextInt(SpaceSurvivor.GAME_HEIGHT-50)+25);
        ammoPic = new ImageIcon(getClass().getResource("AmmoPowerUp.gif")).getImage();
	}

    @Override
    public void draw(Graphics g){
    if(this.isAlive())
        g.drawImage(ammoPic, (int)this.getX(), (int)this.getY(),null);
    }
}
