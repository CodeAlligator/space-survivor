package spaceSurvivor.ship.enemies;

import java.awt.Color;
import java.awt.Graphics;

import spaceSurvivor.SpaceSurvivor;
import spaceSurvivor.ship.EnemyShip;

/*------------------------------------------------------------------------------
These are only created as a result of the Split Enemy dying
------------------------------------------------------------------------------*/
public class SplitMiniEnemy extends EnemyShip{

    public final static int RADIUS = 10;

    public SplitMiniEnemy(){}

    @Override
    public void draw(Graphics g) {
        if(isAlive()){
            g.setColor(Color.BLUE);
            g.fillOval((int)x-RADIUS, (int)y-RADIUS, RADIUS*2, RADIUS*2);
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
