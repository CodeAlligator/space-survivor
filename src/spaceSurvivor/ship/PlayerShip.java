package spaceSurvivor.ship;
import java.awt.*;
import java.lang.Math.*;

/**
 * Description: The user controlled space ship
 * COSC 3550 Assignment 4
 * Author: Andrew Johnson
 * Date: 2/21/2011
 */

public class PlayerShip {

    // ship variables
    int x = 200, y = 200; // ship position
    boolean upKey = false, downKey = false,
            leftKey = false, rightKey = false;
    boolean alive = true; // ship exists? (not collided)
    final static int RADIUS = 10;
    final static int SPEED = 4;
    
    //gun variables
    int gunPointX = 100, gunPointY = 100;   //gun direction
    int[] gunPolyX = new int [4];   //gun shape x coordinates
    int[] gunPolyY = new int [4];   //gun shape y coordinates
    final static int GUNLEN = 12;   //gun's length
    final static int GUNW = 2;      //gun's width

    public PlayerShip() {
		// TODO Auto-generated constructor stub
    }

    public void move(){
        // move ship position
        if (upKey && y-RADIUS>0)
            y -= SPEED;
	if (downKey && y+RADIUS<500)
	    y += SPEED;
        if (leftKey && x-RADIUS>0)
	    x -= SPEED;
	if (rightKey && x+RADIUS<500)
	    x += SPEED;

        // move gun position
        double theta, tipx, tipy;
        if (gunPointX-(double)x==0 && gunPointY>y)
            theta = Math.PI/2;
        else if(gunPointX-(double)x==0 && gunPointY<y)
            theta = -Math.PI/2;
        else
            theta = Math.atan((gunPointY-(double)y)/(gunPointX-(double)x));

        if(gunPointX<x){
            tipx = x-GUNLEN*Math.cos(theta);
            tipy = y-GUNLEN*Math.sin(theta);
        }
        else{
            tipx = x+GUNLEN*Math.cos(theta);
            tipy = y+GUNLEN*Math.sin(theta);
        }
        double xOff = GUNW*Math.sin(theta);
        double yOff = GUNW*Math.cos(theta);

        //create polygon coordinates for gun
        gunPolyX[0] = (int) (x - xOff);
        gunPolyX[1] = (int) (x + xOff);
        gunPolyX[2] = (int) (tipx + xOff);
        gunPolyX[3] = (int) (tipx - xOff);

        gunPolyY[0] = (int) (y + yOff);
        gunPolyY[1] = (int) (y - yOff);
        gunPolyY[2] = (int) (tipy - yOff);
        gunPolyY[3] = (int) (tipy + yOff);
   }

    public void draw(Graphics g){
        g.setColor(Color.red);
        if(alive){
            g.fillOval(x-RADIUS, y-RADIUS, RADIUS*2, RADIUS*2);
            g.setColor(Color.ORANGE);
            g.fillPolygon(gunPolyX, gunPolyY, 4);
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

    public void setGunX(int val){
	gunPointX = val;
    }

    public void setGunY(int val){
	gunPointY = val;
    }
}
