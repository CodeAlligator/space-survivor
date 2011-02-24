package spaceSurvivor.ship.enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;

import spaceSurvivor.SpaceSurvivor;
import spaceSurvivor.ship.EnemyShip;

public class DefaultEnemy extends EnemyShip{
    
    public DefaultEnemy() {    }

    @Override
    public void draw(Graphics g) {
        if(isAlive()){
            g.setColor(Color.PINK);
            g.fillOval((int)x-RADIUS, (int)y-RADIUS, RADIUS*2, RADIUS*2);
            
            //	testing code => show bounding ball
            Ellipse2D.Double bb = getBoundingBall();
            g.setColor(Color.WHITE);
            g.drawOval((int)bb.x, (int)bb.y, (int)bb.width, (int)bb.height);
        }
    }

    @Override
    public void move() {
        if (isAlive()){
            x += dx;
            y += dy;

            //bounce off of edges
            if ((y < RADIUS && dy <0) || (y+RADIUS > SpaceSurvivor.GAME_HEIGHT && dy>0))
                dy*=-1;
            if ((x < RADIUS && dx<0) || (x+RADIUS > SpaceSurvivor.GAME_WIDTH && dx>0))
                dx *= -1;
        }
    }
}