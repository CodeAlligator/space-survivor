package spaceSurvivor.ship.enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import spaceSurvivor.SpaceSurvivor;
import spaceSurvivor.ship.EnemyShip;

public class SplitEnemy implements EnemyShip {

    double x,y,dx,dy;
    double[] minix = new double[NUMMINI];
    double[] miniy = new double[NUMMINI];
    double[] minidx = new double[NUMMINI];
    double[] minidy = new double[NUMMINI];
    final static int RADIUS = 10;
    final static int MINIRAD = 3;
    final static int NUMMINI = 4;
    final static int SPEED = 4;
    private static Random generator = new Random ();
    boolean split;

    public SplitEnemy() {
        x = generator.nextInt(200)-100; //spawn within 100 units of edges
        if (x<0) x+=800;                //if negative, spawn on opposite edge
        y = generator.nextInt(200)-100;
        if (y<0) y+=600;
        double angle = generator.nextDouble()*2*Math.PI; //random direction
        dx=SPEED*Math.cos(angle);
        dy=SPEED*Math.sin(angle);
        split = false;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        if (!split)
            g.fillOval((int)x-RADIUS, (int)y-RADIUS, RADIUS*2, RADIUS*2);
        else
            for (int i=0;i<NUMMINI;i++)
                g.fillOval((int)minix[i]-MINIRAD, (int)miniy[i]-MINIRAD, MINIRAD*2, MINIRAD*2);
    }

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