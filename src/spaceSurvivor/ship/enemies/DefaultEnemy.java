package spaceSurvivor.ship.enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import spaceSurvivor.SpaceSurvivor;
import spaceSurvivor.ship.EnemyShip;

public class DefaultEnemy implements EnemyShip {
    
    double x,y,dx,dy;
    final static int RADIUS = 10;
    final static int SPEED = 4;
    private static Random generator = new Random ();

    public DefaultEnemy() {
	x = generator.nextInt(200)-100; //spawn within 100 units of edges
        if (x<0) x+=800;                //if negative, spawn on opposite edge
        y = generator.nextInt(200)-100;
        if (y<0) y+=600;
        double angle = generator.nextDouble()*2*Math.PI; //random direction
        dx=SPEED*Math.cos(angle);
        dy=SPEED*Math.sin(angle);
    }

    public void draw(Graphics g) {
        g.setColor(Color.PINK);
        g.fillOval((int)x-RADIUS, (int)y-RADIUS, RADIUS*2, RADIUS*2);
    }

    public void move() {
        x += dx;
        y += dy;

        //bounce off of edges
        if ((y < RADIUS && dy <0) || (y+RADIUS > SpaceSurvivor.GAME_HEIGHT && dy>0))
            dy*=-1;
        if ((x < RADIUS && dx<0) || (x+RADIUS > SpaceSurvivor.GAME_WIDTH && dx>0))
            dx *= -1;
    }

}
