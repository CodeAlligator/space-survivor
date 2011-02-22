package spaceSurvivor.ship;
import java.awt.*;
/**
 *
 * @author Andrew
 */
public class bullet {
double x,y; //position
    double xdir, ydir; //movement direction
    boolean active;
    PlayerShip p;
    final static int RADIUS = 2;

    public bullet(PlayerShip player){
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
        if (y+RADIUS<0 || y>500+RADIUS || x+RADIUS<0 || x>500+RADIUS)
            active = false;
    }

    public void draw(Graphics g){
        if(active){
            g.setColor(Color.YELLOW);
            g.fillOval((int)x-RADIUS, (int)y-RADIUS, RADIUS*2, RADIUS*2);
        }
    }
}


