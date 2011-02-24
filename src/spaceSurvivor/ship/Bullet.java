package spaceSurvivor.ship;
import java.awt.*;
import java.awt.geom.Ellipse2D.Double;
import spaceSurvivor.Hittable;

import spaceSurvivor.SpaceSurvivor;
/**
 * <code>Bullet</code>
 * @author Andrew
 */
public class Bullet implements Hittable{
	private double x,y; //position
    private double xdir, ydir; //movement direction
    private boolean alive;
    private PlayerShip p;
    public static final int RADIUS = 4;

    public Bullet(PlayerShip player){
        p=player;
//        x=p.getGunX();
//        y=p.getGunY();
//        xdir=x-p.getPosX();
//        ydir=y-p.getPosY();
        alive=false;
    }

    public void activate(){
        x=p.getGunX();
        y=p.getGunY();
        xdir=(x-p.getPosX())/2;
        ydir=(y-p.getPosY())/2;
        alive=true;
    }

    public void die(){
        alive=false;
    }

    public boolean isAlive(){
        return alive;
    }

    public void move(){
        if(alive){
            x += xdir;
            y += ydir;
        }
        if (y+RADIUS<0 || y>SpaceSurvivor.GAME_HEIGHT+RADIUS || x+RADIUS<0 || x>SpaceSurvivor.GAME_WIDTH+RADIUS)
            alive = false;
    }

    public void draw(Graphics g){
        if(alive){
            g.setColor(Color.YELLOW);
            g.fillOval((int)x-RADIUS, (int)y-RADIUS, RADIUS*2, RADIUS*2);
        }
    }
    
	public Double getBoundingBall() {
		return new java.awt.geom.Ellipse2D.Double(x, y, 2 * RADIUS, 2 * RADIUS);
	}
}