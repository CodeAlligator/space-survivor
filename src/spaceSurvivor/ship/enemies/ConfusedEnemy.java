package spaceSurvivor.ship.enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;

import spaceSurvivor.SpaceSurvivor;
import spaceSurvivor.ship.EnemyShip;

public class ConfusedEnemy extends EnemyShip {
    
    public ConfusedEnemy() {}

    @Override
    public void draw(Graphics g) {
        if(alive){
            g.setColor(Color.MAGENTA);
            g.fillOval((int)x-RADIUS, (int)y-RADIUS, RADIUS*2, RADIUS*2);
            
            //	testing code => show bounding ball
            Ellipse2D.Double bb = getBoundingBall();
            g.setColor(Color.WHITE);
            g.drawOval((int)(bb.x-bb.width/2), (int)(bb.y-bb.height/2), (int)bb.width, (int)bb.height);
        }
    }

    @Override
    public void move() {
        if(alive){
            x += dx;
            y += dy;

            //randomly change direction occasionally up to 45 degrees
            angle += generator.nextGaussian()*.4;

            dx=SPEED*Math.cos(angle);
            dy=SPEED*Math.sin(angle);


            //bounce off of edges
            if ((y < RADIUS && dy <0) || (y+RADIUS > SpaceSurvivor.GAME_HEIGHT && dy>0)){
                dy*=-1;
                angle = Math.asin(dy/SPEED);
            }
            if ((x < RADIUS && dx<0) || (x+RADIUS > SpaceSurvivor.GAME_WIDTH && dx>0)){
                dx *= -1;
                angle = Math.acos(dx/SPEED);
            }
        }
    }
	
}