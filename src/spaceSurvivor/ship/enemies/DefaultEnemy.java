package spaceSurvivor.ship.enemies;

import java.awt.Color;
import java.awt.Graphics;

import spaceSurvivor.SpaceSurvivor;
import spaceSurvivor.ship.EnemyShip;

/*------------------------------------------------------------------------------
These default enemies fly straight and bounce off of walls
------------------------------------------------------------------------------*/
public class DefaultEnemy extends EnemyShip{
    
    public DefaultEnemy() {    }

    @Override
    public void draw(Graphics g) {
        if(isAlive()){
            g.setColor(Color.PINK);
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