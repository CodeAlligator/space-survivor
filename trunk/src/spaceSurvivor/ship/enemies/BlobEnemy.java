package spaceSurvivor.ship.enemies;

import java.awt.Color;
import java.awt.Graphics;
import spaceSurvivor.Score;
import spaceSurvivor.SpaceSurvivor;
import spaceSurvivor.ship.Bullet;
import spaceSurvivor.ship.EnemyShip;
import spaceSurvivor.ship.PlayerShip;

/*------------------------------------------------------------------------------
These enemies grow exponentially
------------------------------------------------------------------------------*/
public class BlobEnemy extends EnemyShip {

    EnemyShip leftBlob;
    EnemyShip rightBlob;
    BlobEnemy lBlob, rBlob;
    public final static int SPEED = 1;
    int moveCount;
    double spawnAngle; //where to generate sibling blobs
    int level; //level in imaginary blob tree data structure

    public BlobEnemy() {
        x = generator.nextInt(200)-100; //spawn within 100 units of edges
        if (x<0) x+=SpaceSurvivor.GAME_WIDTH;  //if negative, spawn on opposite edge
        y = generator.nextInt(200)-100;
        if (y<0) y+=SpaceSurvivor.GAME_HEIGHT;
        angle = generator.nextDouble()*2*Math.PI; //random direction
        dx=SPEED*Math.cos(angle);
        dy=SPEED*Math.sin(angle);
        alive = false;
        leftBlob = null;
        rightBlob = null;
        moveCount = 0;
        level = 1;
    }

    @Override
    public void draw(Graphics g) {
        // draw self
        if (isAlive()){
            g.setColor(Color.GREEN);
            g.fillOval((int)x-RADIUS, (int)y-RADIUS, RADIUS*2, RADIUS*2);
        }
        //draw siblings
        if(leftBlob != null) leftBlob.draw(g);
        if(rightBlob != null) rightBlob.draw(g);
    }

    @Override
    public void move(){
        if (isAlive()){
            
            moveCount++;

            //periodically grow if level isn't too big (grows new and dead siblings)
            if (moveCount % 100 == 0 && level != 4){
                if (leftBlob == null){
                    lBlob = new BlobEnemy();
                    lBlob.setLevel(level+1);
                    leftBlob=lBlob;
                }
                if (!leftBlob.isAlive()){
                    spawnAngle = generator.nextDouble()*2*Math.PI;
                    leftBlob.activate();
                    leftBlob.setX(x+RADIUS*2*Math.cos(spawnAngle));
                    leftBlob.setY(y+RADIUS*2*Math.sin(spawnAngle));
                    leftBlob.setDX(dx);
                    leftBlob.setDY(dy);
                }

                if (rightBlob == null){
                    rBlob = new BlobEnemy();
                    rBlob.setLevel(level+1);
                    rightBlob=rBlob;
                }
                if (!rightBlob.isAlive()){
                    spawnAngle = generator.nextDouble()*2*Math.PI;
                    rightBlob.activate();
                    rightBlob.setX(x+RADIUS*2*Math.cos(spawnAngle));
                    rightBlob.setY(y+RADIUS*2*Math.sin(spawnAngle));
                    rightBlob.setDX(dx);
                    rightBlob.setDY(dy);
                }
            }

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

            // check for collisions with other enemies
            for(int i = 0; i < enemies.length; i++){
            	//	if this enemy is alive, only check against other alive enemies
                if (this != enemies[i] && this.isAlive() && enemies[i].isAlive() && !(enemies[i] instanceof BlobEnemy)){
                	if(this.collidedWithEnemy(enemies[i])){
                        angle = Math.atan2(y-enemies[i].getY(), x-enemies[i].getX());
                        dx = SPEED*Math.cos(angle);
                        dy = SPEED*Math.sin(angle);
                    }
                }
            }

            // check for collisions with player
            if(this.collidedWithPlayer()){
                audioFX.playCrash();	//	play crash SFX
                this.die();
                score.addScore(-5);
                if (score.getShield()==0) p.die();
                	score.addShield(-10);
            }

        }
        //move siblings
        if(leftBlob != null) leftBlob.move(p, shots, enemies, score);
        if(rightBlob != null) rightBlob.move(p, shots, enemies, score);
    }

    @Override
    public boolean collidedWithBullet() {
        //check sibling collisions
        if(leftBlob != null)
            if(leftBlob.collidedWithBullet())
                leftBlob.die();
        if(rightBlob != null)
            if(rightBlob.collidedWithBullet())
                rightBlob.die();

        //check own collisions
        if(alive){
            java.awt.geom.Ellipse2D.Double thisBoundingBall = getBoundingBall();

            double thisCenterX = thisBoundingBall.getCenterX();
            double thisCenterY = thisBoundingBall.getCenterY();

            boolean hitBullet = false;

            for(int i = 0; i < shots.length; i++){
                if(shots[i].isAlive()){
                    double otherCenterX = shots[i].getBoundingBall().getCenterX();
                    double otherCenterY = shots[i].getBoundingBall().getCenterY();

//                  // Sorta works, though not perfect   ~Andrew
                    // checks bullet in 4 different previous spots since it moves fast enought to skip collisions
                    double shotX,shotY;
                    for (double j=0;j<26;j+=1.0){
                        shotX = otherCenterX - (shots[i].getDX()*j/25);
                        shotY = otherCenterY - (shots[i].getDY()*j/25);

                         /*
                         * underlying equation:
                         * (x1 - x2)^2 + (y1 - y2)^2 <= (r1 + r2)^2
                         */
                        double xComponent = Math.pow(thisCenterX - shotX, 2);
                        double yComponent = Math.pow(thisCenterY - shotY, 2);
                        double radiiComponent = Math.pow(RADIUS + shots[i].getBoundingBall().height / 2, 2);

                        if((xComponent + yComponent) <= radiiComponent){
                                hitBullet = true;
                                shots[i].die();
                        }
                    }
                }
            }
            return hitBullet;
        }
        
        return false;

    }

     public void setLevel(int lev){
         level=lev;
     }

}
