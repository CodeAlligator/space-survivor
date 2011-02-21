package spaceSurvivor.ship;
import java.awt.*;

public class PlayerShip {

    int x = 200, y = 200; // ship position
    boolean upKey = false, downKey = false,
            leftKey = false, rightKey = false;
    boolean alive = true; // ship exists? (not collided)
    final static int RADIUS = 10;
    final static int SPEED = 4;

    public PlayerShip() {
		// TODO Auto-generated constructor stub
    }

    public void move(){
        if (upKey && y-RADIUS>0)
            y -= SPEED;
	if (downKey && y+RADIUS<500)
	    y += SPEED;
        if (leftKey && x-RADIUS>0)
	    x -= SPEED;
	if (rightKey && x+RADIUS<500)
	    x += SPEED;
   }

    public void draw(Graphics g){
        g.setColor(Color.red);
        if(alive){
            g.fillOval(x-RADIUS, y-RADIUS, RADIUS*2, RADIUS*2);
            g.setColor(Color.cyan);
            g.fillOval(x-RADIUS/2, y-RADIUS/2, RADIUS, RADIUS);
        }
        // if dead, replace ship with failure text
        else g.drawString ("You Died. Sorry.", 200, 250);
    }
    
    public void setUpKey(Boolean val){
	upKey = val;
    }

    public void setDownKey(Boolean val){
	downKey = val;
    }

    public void setLeftKey(Boolean val){
	leftKey = val;
    }

    public void setRightKey(Boolean val){
	rightKey = val;
    }
}
