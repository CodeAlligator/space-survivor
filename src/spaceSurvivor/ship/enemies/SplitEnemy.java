package spaceSurvivor.ship.enemies;

import java.awt.Color;
import java.awt.Graphics;
import spaceSurvivor.Score;

import spaceSurvivor.SpaceSurvivor;
import spaceSurvivor.ship.Bullet;
import spaceSurvivor.ship.EnemyShip;
import spaceSurvivor.ship.PlayerShip;

/*------------------------------------------------------------------------------
These enemies split into 4 SplitMiniEnemies when they die
------------------------------------------------------------------------------*/
public class SplitEnemy extends EnemyShip {

    EnemyShip[] minis;

    public SplitEnemy() {
        x = generator.nextInt(200)-100; //spawn within 100 units of edges
        if (x<0) x+=SpaceSurvivor.GAME_WIDTH;  //if negative, spawn on opposite edge
        y = generator.nextInt(200)-100;
        if (y<0) y+=SpaceSurvivor.GAME_HEIGHT;
        angle = generator.nextDouble()*2*Math.PI; //random direction
        dx=SPEED*Math.cos(angle);
        dy=SPEED*Math.sin(angle);
        alive = false;
        minis = new EnemyShip[4];
        for(int i=0;i<4;i++)
            minis[i]=new SplitMiniEnemy();
    }

    @Override
    public void draw(Graphics g) {
        if (isAlive()){
                g.setColor(Color.BLUE);
        	g.fillOval((int)x-RADIUS, (int)y-RADIUS, RADIUS*2, RADIUS*2);
        }
        else {
            for(int i=0;i<4;i++)
                minis[i].draw(g);
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

    @Override
    public void move(PlayerShip p, Bullet[] shots, EnemyShip[] enemies, Score score){
        if(alive){
            this.p = p;
            this.shots = shots;
            this.enemies = enemies;
            this.score = score;

            move();

            for(int i = 0; i < enemies.length; i++){
            	//	if this enemy is alive, only check against other alive enemies
                if (this != enemies[i] && this.isAlive() && enemies[i].isAlive()){
                	if(this.collidedWithEnemy(enemies[i])){
                        angle = Math.atan2(y-enemies[i].getY(), x-enemies[i].getX());
                        dx = SPEED*Math.cos(angle);
                        dy = SPEED*Math.sin(angle);
                    }
                }
            }
        }
        else {
            for(int i=0;i<4;i++)
                minis[i].move(p, shots, enemies, score);
        }
    }

    @Override
    public void die() {
        alive = false;
        for(int i=0;i<4;i++)
             minis[i].activate();
         minis[0].setX(x+20);
         minis[0].setY(y+20);
         minis[1].setX(x+20);
         minis[1].setY(y-20);
         minis[2].setX(x-20);
         minis[2].setY(y+20);
         minis[3].setX(x-20);
         minis[3].setY(y-20);
    }

    @Override
    public boolean collidedWithBullet() {
        if(alive){
            java.awt.geom.Ellipse2D.Double thisBoundingBall = getBoundingBall();

            double thisCenterX = thisBoundingBall.getCenterX();
            double thisCenterY = thisBoundingBall.getCenterY();

            boolean hitBullet = false;

            for(int i = 0; i < shots.length; i++){
                if(shots[i].isAlive()){
                    double otherCenterX = shots[i].getBoundingBall().getCenterX();
                    double otherCenterY = shots[i].getBoundingBall().getCenterY();

//                                // checks bullet in 4 different previous spots since it moves fast enought to skip collisions
//                                double shotX,shotY,shipX,shipY;
//                                for (double j=0;j<26;j+=1.0){
//                                    shotX = otherCenterX + shots[i].getDX()*j/25;
//                                    shotY = otherCenterY + shots[i].getDY()*j/25;
//                                    shipX = thisCenterX + dx*j/25;
//                                    shipY = thisCenterY + dy*j/25;

                    /*
                     * underlying equation:
                     * (x1 - x2)^2 + (y1 - y2)^2 <= (r1 + r2)^2
                     */
                    double xComponent = Math.pow(thisCenterX - otherCenterX, 2);
                    double yComponent = Math.pow(thisCenterY - otherCenterY, 2);
                    double radiiComponent = Math.pow(RADIUS + shots[i].getBoundingBall().height / 2, 2);

                    if((xComponent + yComponent) <= radiiComponent){
                            hitBullet = true;
                            shots[i].die();
                    }
                }
            }
            return hitBullet;
        }
        else{
            for(int i=0;i<4;i++){
                if(minis[i].collidedWithBullet()){
                    minis[i].die();
                }
            }
            return false;
        }
    }
}
