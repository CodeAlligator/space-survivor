package spaceSurvivor.ship.enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.util.Random;
import spaceSurvivor.SpaceSurvivor;
import spaceSurvivor.ship.Bullet;
import spaceSurvivor.ship.EnemyShip;
import spaceSurvivor.ship.PlayerShip;

public class SplitEnemy extends EnemyShip {

    
    double[] minix = new double[NUMMINI];
    double[] miniy = new double[NUMMINI];
    double[] minidx = new double[NUMMINI];
    double[] minidy = new double[NUMMINI];
    final static int MINIRAD = 3;
    final static int NUMMINI = 4;
    boolean split;

    public SplitEnemy() {
        x = generator.nextInt(200)-100; //spawn within 100 units of edges
        if (x<0) x+=SpaceSurvivor.GAME_WIDTH;  //if negative, spawn on opposite edge
        y = generator.nextInt(200)-100;
        if (y<0) y+=SpaceSurvivor.GAME_HEIGHT;
        angle = generator.nextDouble()*2*Math.PI; //random direction
        dx=SPEED*Math.cos(angle);
        dy=SPEED*Math.sin(angle);
        alive = true;
        split = false;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        if (!split){
        	g.fillOval((int)x-RADIUS, (int)y-RADIUS, RADIUS*2, RADIUS*2);
            
	        //	testing code => show bounding ball
	        Ellipse2D.Double bb = getBoundingBall();
	        g.setColor(Color.WHITE);
	        g.drawOval((int)(bb.x-bb.width/2), (int)(bb.y-bb.height/2), (int)bb.width, (int)bb.height);
        }
        else{
        	for (int i=0;i<NUMMINI;i++){
        		g.fillOval((int)minix[i]-MINIRAD, (int)miniy[i]-MINIRAD, MINIRAD*2, MINIRAD*2);
        	}
        }
    }

    @Override
    public void move() {
        if (!split){
            x += dx;
            y += dy;

            //bounce off of edges
            if ((y < RADIUS && dy <0) || (y+RADIUS > SpaceSurvivor.GAME_HEIGHT && dy>0))
                dy*=-1;
            if ((x < RADIUS && dx<0) || (x+RADIUS > SpaceSurvivor.GAME_WIDTH && dx>0))
                dx *= -1;
        }
        else {
            for (int i=0;i<NUMMINI;i++){
                minix[i]+=minidx[i];
                miniy[i]+=minidy[i];
                if ((miniy[i] < MINIRAD && minidy[i] <0) || (miniy[i]+MINIRAD > SpaceSurvivor.GAME_HEIGHT && minidy[i]>0))
                    minidy[i]*=-1;
                if ((minix[i] < MINIRAD && minidx[i]<0) || (minix[i]+MINIRAD > SpaceSurvivor.GAME_WIDTH && minidx[i]>0))
                    minidx[i] *= -1;
            }
        }
    }

	
}