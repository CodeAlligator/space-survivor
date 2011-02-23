package spaceSurvivor.ship.enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import spaceSurvivor.SpaceSurvivor;
import spaceSurvivor.ship.EnemyShip;
import spaceSurvivor.ship.PlayerShip;

public class SeekerEnemy implements EnemyShip {
    double x,y,dx,dy, angle;
    final static int RADIUS = 10;
    final static int SPEED = 3;
    private static Random generator = new Random ();

    public SeekerEnemy() {
        x = generator.nextInt(200)-100; //spawn within 100 units of edges
        if (x<0) x+=800;                //if negative, spawn on opposite edge
        y = generator.nextInt(200)-100;
        if (y<0) y+=600;
        angle = generator.nextDouble()*2*Math.PI-Math.PI; //random direction
        dx=SPEED*Math.cos(angle);
        dy=SPEED*Math.sin(angle);
    }


    public void draw(Graphics g) {
	g.setColor(Color.RED);
        g.fillOval((int)x-RADIUS, (int)y-RADIUS, RADIUS*2, RADIUS*2);
    }

    public void move(PlayerShip p) {
        x += dx;
        y += dy;

        //change direction toward player
        double newAngle = Math.atan2(p.getPosY()-y, p.getPosX()-x);

        if(Math.abs(newAngle-angle)<Math.PI)
            if(newAngle-angle>0) angle += .03;
            else angle -= .03;
        else
            if(newAngle-angle>0) angle -= .03;
            else angle += .03;

        if (angle>Math.PI) angle -= Math.PI*2;
        if (angle<-Math.PI) angle += Math.PI*2;

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

    public void move() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
