package spaceSurvivor.ship.enemies.privateEnemies;

import java.awt.Color;
import java.awt.Graphics;
import spaceSurvivor.ship.EnemyShip;

/*------------------------------------------------------------------------------
These are only created from Builder enemies. Enemies can currently pass through them
------------------------------------------------------------------------------*/
public class WallEnemy extends EnemyShip{

    public final static int RADIUS = 10;

    public WallEnemy(){}

    @Override
    public void draw(Graphics g) {
        if(isAlive()){
            g.setColor(Color.ORANGE);
            g.fillOval((int)x-RADIUS, (int)y-RADIUS, RADIUS*2, RADIUS*2);
        }
    }

    // does not move
    @Override
    public void move() { }
}
