package spaceSurvivor.ship.enemies;

import java.awt.Color;
import java.awt.Graphics;
import spaceSurvivor.SpaceSurvivor;
import spaceSurvivor.ship.Bullet;

import spaceSurvivor.ship.EnemyShip;

/*------------------------------------------------------------------------------
These enemies try to dodge bullets near them
------------------------------------------------------------------------------*/
public class AvoiderEnemy extends EnemyShip{
	
    public AvoiderEnemy() {	}

    @Override
    public void draw(Graphics g) {
        if(isAlive()){
            g.setColor(Color.GRAY);
            g.fillOval((int)x-RADIUS, (int)y-RADIUS, RADIUS*2, RADIUS*2);
        }
    }

    @Override
    public void move() {
        if (isAlive()){
            x += dx;
            y += dy;

            //detect and avoid bullets
            Bullet closeBullet=null;
            double closeDistSquared=100000;

            for (int i=0; i<shots.length;i++){

                if(shots[i].isAlive()){
                    //distance to bullets
                    double otherCenterX = shots[i].getBoundingBall().getCenterX();
                    double otherCenterY = shots[i].getBoundingBall().getCenterY();
                    double xComponent = Math.pow(x - otherCenterX, 2);
                    double yComponent = Math.pow(y - otherCenterY, 2);

                    //if distance is smallest, save as closest bullet
                    if(xComponent + yComponent < closeDistSquared)
                        closeBullet=shots[i];
                }
            }

            //change direction away from closest bullet
            if(closeBullet != null){
                // get perpendicular angle to bullets direction
                double newAngle= Math.atan2(closeBullet.getDY(), closeBullet.getDX());
                newAngle += Math.PI/2;

                // put angle back into correct range
                if (newAngle>Math.PI) newAngle -= Math.PI*2;
                angle = newAngle;

                dx=SPEED*Math.cos(angle);
                dy=SPEED*Math.sin(angle);

                // reverse angle if heading toward bullet
                if (Math.pow(x+dx-closeBullet.getBoundingBall().getCenterX(),2)+
                    Math.pow(y+dy-closeBullet.getBoundingBall().getCenterY(),2)<
                    Math.pow(x-dx-closeBullet.getBoundingBall().getCenterX(),2)+
                    Math.pow(y-dy-closeBullet.getBoundingBall().getCenterY(),2) ){

                    dx*=-1;
                    dy*=-1;
                }


            }




            //bounce off of edges
            if ((y < RADIUS && dy <0) || (y+RADIUS > SpaceSurvivor.GAME_HEIGHT && dy>0))
                dy*=-1;
            if ((x < RADIUS && dx<0) || (x+RADIUS > SpaceSurvivor.GAME_WIDTH && dx>0))
                dx *= -1;
        }
    }
	
}