package spaceSurvivor.ship.enemies;

import java.awt.Graphics;
import spaceSurvivor.ship.Bullet;
import spaceSurvivor.ship.EnemyShip;
import spaceSurvivor.ship.PlayerShip;

public class BuilderEnemy extends EnemyShip {
	
	public BuilderEnemy() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	
    @Override
	public void move() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

	@Override
	public void move(PlayerShip p, Bullet[] shots) {
		this.p = p;
		this.shots = shots;
		move();
	}

	
}