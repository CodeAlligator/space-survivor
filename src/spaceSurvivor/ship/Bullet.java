package spaceSurvivor.ship;
import java.awt.*;

import spaceSurvivor.SpaceSurvivor;
/**
 * <code>Bullet</code>
 * @author Andrew
 */
public class Bullet {
	private double x,y; //position
    private double xdir, ydir; //movement direction
    private boolean active;
    private PlayerShip p;
    public static final int RADIUS = 2;

    public Bullet(PlayerShip player){
        p=player;
//        x=p.getGunX();
//        y=p.getGunY();
//        xdir=x-p.getPosX();
//        ydir=y-p.getPosY();
        active=false;
    }

    public void activate(){
        x=p.getGunX();
        y=p.getGunY();
        xdir=x-p.getPosX();
        ydir=y-p.getPosY();
        active=true;
    }

    public void deactivate(){
        active=false;
    }

    public boolean isActive(){
        return active;
    }

    public void move(){
        if(active){
            x += xdir;
            y += ydir;
        }
        if (y+RADIUS<0 || y>SpaceSurvivor.GAME_HEIGHT+RADIUS || x+RADIUS<0 || x>SpaceSurvivor.GAME_WIDTH+RADIUS)
            active = false;
    }

    public void draw(Graphics g){
        if(active){
            g.setColor(Color.YELLOW);
            g.fillOval((int)x-RADIUS, (int)y-RADIUS, RADIUS*2, RADIUS*2);
        }
    }
}