package spaceSurvivor.ship.enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;

import spaceSurvivor.SpaceSurvivor;
import spaceSurvivor.ship.EnemyShip;

public class SeekerEnemy extends EnemyShip {
    
    public SeekerEnemy() { }


    @Override
    public void draw(Graphics g) {
        if(alive){
            g.setColor(Color.RED);
            g.fillOval((int)x-RADIUS, (int)y-RADIUS, RADIUS*2, RADIUS*2);
        }
    }

    @Override
    public void move() {
        if(alive){
            
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
    }

    
}