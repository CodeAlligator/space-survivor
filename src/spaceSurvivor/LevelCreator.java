package spaceSurvivor;

import spaceSurvivor.powerUp.AmmoPowerUp;
import spaceSurvivor.powerUp.PowerUp;
import spaceSurvivor.powerUp.ShieldPowerUp;
import spaceSurvivor.ship.EnemyShip;
import spaceSurvivor.ship.enemies.ConfusedEnemy;
import spaceSurvivor.ship.enemies.DefaultEnemy;
import spaceSurvivor.ship.enemies.SeekerEnemy;
import spaceSurvivor.ship.enemies.SplitEnemy;

public class LevelCreator {
	public static final int LEVEL1 = 1;
	public static final int LEVEL2 = 2;
	public static final int LEVEL3 = 3;
	public static final int LEVEL4 = 4;
	public static final int LEVEL5 = 5;
	public static final int LEVEL6 = 6;
	
	private LevelCreator() {
		
	}
	
	public static Level loadLevel(int levelNumber){
		Level level = new Level();
		level.setLevelNumber(levelNumber);
		
		int numEnemyShips;
		int numPowerUps;
		
		EnemyShip[] enemyShips;
		int[] shipEntranceTimes;	//	times are how long into level
		PowerUp[] powerUps;
		int[] powerUpStartTimes;	//	times are how long into level
		int[] powerUpStopTimes;	//	times are how long into level
		
		
		switch(levelNumber){
			case LEVEL1:
				numEnemyShips = 25;
				numPowerUps = 12;
				
				enemyShips = new EnemyShip[numEnemyShips];
				shipEntranceTimes = new int[numEnemyShips];
				powerUps = new PowerUp[numPowerUps];
				powerUpStartTimes = new int[numPowerUps];
				powerUpStopTimes = new int[numPowerUps];
				
				level.setLevelTime(60);
				
				enemyShips[0] = new DefaultEnemy();
				enemyShips[1] = new ConfusedEnemy();
				enemyShips[2] = new SeekerEnemy();
				enemyShips[3] = new SeekerEnemy();
				enemyShips[4] = new SeekerEnemy();
				enemyShips[5] = new SeekerEnemy();
				enemyShips[6] = new DefaultEnemy();
				enemyShips[7] = new ConfusedEnemy();
				enemyShips[8] = new SeekerEnemy();
				enemyShips[9] = new DefaultEnemy();
				enemyShips[10] = new ConfusedEnemy();
				enemyShips[11] = new SeekerEnemy();
				enemyShips[12] = new SeekerEnemy();
				enemyShips[13] = new SeekerEnemy();
				enemyShips[14] = new SeekerEnemy();
				enemyShips[15] = new DefaultEnemy();
				enemyShips[16] = new ConfusedEnemy();
				enemyShips[17] = new SeekerEnemy();
				enemyShips[18] = new DefaultEnemy();
				enemyShips[19] = new ConfusedEnemy();
				enemyShips[20] = new SeekerEnemy();
				enemyShips[21] = new SeekerEnemy();
				enemyShips[22] = new SeekerEnemy();
				enemyShips[23] = new SeekerEnemy();
				enemyShips[24] = new SeekerEnemy();
				
				shipEntranceTimes[0] = 1;
				shipEntranceTimes[1] = 3;
				shipEntranceTimes[2] = 5;
				shipEntranceTimes[3] = 9;
				shipEntranceTimes[4] = 13;
				shipEntranceTimes[5] = 18;
				shipEntranceTimes[6] = 20;
				shipEntranceTimes[7] = 21;
				shipEntranceTimes[8] = 23;
				shipEntranceTimes[9] = 24;
				shipEntranceTimes[10] = 25;
				shipEntranceTimes[11] = 28;
				shipEntranceTimes[12] = 30;
				shipEntranceTimes[13] = 35;
				shipEntranceTimes[14] = 36;
				shipEntranceTimes[15] = 39;
				shipEntranceTimes[16] = 40;
				shipEntranceTimes[17] = 48;
				shipEntranceTimes[18] = 49;
				shipEntranceTimes[19] = 30;
				shipEntranceTimes[20] = 30;
				shipEntranceTimes[21] = 30;
				shipEntranceTimes[22] = 30;
				shipEntranceTimes[23] = 30;
				shipEntranceTimes[24] = 30;
				
				powerUps[0] = new AmmoPowerUp();
				powerUps[1] = new AmmoPowerUp();
				powerUps[2] = new ShieldPowerUp();
				powerUps[3] = new AmmoPowerUp();
				powerUps[4] = new ShieldPowerUp();
				powerUps[5] = new ShieldPowerUp();
				powerUps[6] = new AmmoPowerUp();
				powerUps[7] = new AmmoPowerUp();
				powerUps[8] = new ShieldPowerUp();
				powerUps[9] = new AmmoPowerUp();
				powerUps[10] = new AmmoPowerUp();
				powerUps[11] = new ShieldPowerUp();
				
				powerUpStartTimes[0] = 6;
				powerUpStopTimes[0] = 15;
				powerUpStartTimes[1] = 10;
				powerUpStopTimes[1] = 19;
				powerUpStartTimes[2] = 12;
				powerUpStopTimes[2] = 18;
				powerUpStartTimes[3] = 26;
				powerUpStopTimes[3] = 35;
				powerUpStartTimes[4] = 29;
				powerUpStopTimes[4] = 36;
				powerUpStartTimes[5] = 36;
				powerUpStopTimes[5] = 47;
				powerUpStartTimes[6] = 6;
				powerUpStopTimes[6] = 15;
				powerUpStartTimes[7] = 10;
				powerUpStopTimes[7] = 19;
				powerUpStartTimes[8] = 22;
				powerUpStopTimes[8] = 38;
				powerUpStartTimes[9] = 26;
				powerUpStopTimes[9] = 35;
				powerUpStartTimes[10] = 29;
				powerUpStopTimes[10] = 41;
				powerUpStartTimes[11] = 32;
				powerUpStopTimes[11] = 42;
				
				break;
			case LEVEL2:
				numEnemyShips = 50;
				numPowerUps = 8;
				
				enemyShips = new EnemyShip[numEnemyShips];
				shipEntranceTimes = new int[numEnemyShips];
				powerUps = new PowerUp[numPowerUps];
				powerUpStartTimes = new int[numPowerUps];
				powerUpStopTimes = new int[numPowerUps];
				
				level.setLevelTime(60);
				
				enemyShips[0] = new DefaultEnemy();
				enemyShips[1] = new ConfusedEnemy();
				enemyShips[2] = new SeekerEnemy();
				enemyShips[3] = new SeekerEnemy();
				enemyShips[4] = new SeekerEnemy();
				enemyShips[5] = new SeekerEnemy();
				enemyShips[6] = new DefaultEnemy();
				enemyShips[7] = new ConfusedEnemy();
				enemyShips[8] = new SeekerEnemy();
				enemyShips[9] = new SeekerEnemy();
				enemyShips[10] = new SeekerEnemy();
				enemyShips[11] = new SeekerEnemy();
				enemyShips[12] = new DefaultEnemy();
				enemyShips[13] = new ConfusedEnemy();
				enemyShips[14] = new SeekerEnemy();
				enemyShips[15] = new DefaultEnemy();
				enemyShips[16] = new ConfusedEnemy();
				enemyShips[17] = new SeekerEnemy();
				enemyShips[18] = new DefaultEnemy();
				enemyShips[19] = new ConfusedEnemy();
				enemyShips[20] = new ConfusedEnemy();
				enemyShips[21] = new SeekerEnemy();
				enemyShips[22] = new DefaultEnemy();
				enemyShips[23] = new ConfusedEnemy();
				enemyShips[24] = new SeekerEnemy();
				enemyShips[25] = new DefaultEnemy();
				enemyShips[26] = new ConfusedEnemy();
				enemyShips[27] = new SeekerEnemy();
				enemyShips[28] = new DefaultEnemy();
				enemyShips[29] = new ConfusedEnemy();
				enemyShips[30] = new SeekerEnemy();
				enemyShips[31] = new ConfusedEnemy();
				enemyShips[32] = new SeekerEnemy();
				enemyShips[33] = new ConfusedEnemy();
				enemyShips[34] = new SeekerEnemy();
				enemyShips[35] = new ConfusedEnemy();
				enemyShips[36] = new SeekerEnemy();
				enemyShips[37] = new ConfusedEnemy();
				enemyShips[38] = new SeekerEnemy();
				enemyShips[39] = new DefaultEnemy();
				enemyShips[40] = new SeekerEnemy();
				enemyShips[41] = new ConfusedEnemy();
				enemyShips[42] = new DefaultEnemy();
				enemyShips[43] = new DefaultEnemy();
				enemyShips[44] = new ConfusedEnemy();
				enemyShips[45] = new ConfusedEnemy();
				enemyShips[46] = new SeekerEnemy();
				enemyShips[47] = new SeekerEnemy();
				enemyShips[48] = new SeekerEnemy();
				enemyShips[49] = new SeekerEnemy();
				
				shipEntranceTimes[0] = 1;
				shipEntranceTimes[1] = 3;
				shipEntranceTimes[2] = 5;
				shipEntranceTimes[3] = 9;
				shipEntranceTimes[4] = 13;
				shipEntranceTimes[5] = 18;
				shipEntranceTimes[6] = 20;
				shipEntranceTimes[7] = 21;
				shipEntranceTimes[8] = 23;
				shipEntranceTimes[9] = 24;
				shipEntranceTimes[10] = 25;
				shipEntranceTimes[11] = 26;
				shipEntranceTimes[12] = 27;
				shipEntranceTimes[13] = 28;
				shipEntranceTimes[14] = 29;
				shipEntranceTimes[15] = 30;
				shipEntranceTimes[16] = 35;
				shipEntranceTimes[17] = 36;
				shipEntranceTimes[18] = 37;
				shipEntranceTimes[19] = 38;
				shipEntranceTimes[20] = 39;
				shipEntranceTimes[21] = 40;
				shipEntranceTimes[22] = 41;
				shipEntranceTimes[23] = 42;
				shipEntranceTimes[24] = 43;
				shipEntranceTimes[25] = 44;
				shipEntranceTimes[26] = 45;
				shipEntranceTimes[27] = 46;
				shipEntranceTimes[28] = 48;
				shipEntranceTimes[29] = 50;
				shipEntranceTimes[30] = 46;
				shipEntranceTimes[31] = 30;
				shipEntranceTimes[32] = 30;
				shipEntranceTimes[33] = 30;
				shipEntranceTimes[34] = 30;
				shipEntranceTimes[35] = 31;
				shipEntranceTimes[36] = 31;
				shipEntranceTimes[37] = 31;
				shipEntranceTimes[38] = 32;
				shipEntranceTimes[39] = 32;
				shipEntranceTimes[40] = 46;
				shipEntranceTimes[41] = 30;
				shipEntranceTimes[42] = 30;
				shipEntranceTimes[43] = 30;
				shipEntranceTimes[44] = 30;
				shipEntranceTimes[45] = 31;
				shipEntranceTimes[46] = 31;
				shipEntranceTimes[47] = 31;
				shipEntranceTimes[48] = 32;
				shipEntranceTimes[49] = 32;
				
				powerUps[0] = new AmmoPowerUp();
				powerUps[1] = new AmmoPowerUp();
				powerUps[2] = new ShieldPowerUp();
				powerUps[3] = new AmmoPowerUp();
				powerUps[4] = new ShieldPowerUp();
				powerUps[5] = new ShieldPowerUp();
				powerUps[6] = new AmmoPowerUp();
				powerUps[7] = new ShieldPowerUp();
				
				powerUpStartTimes[0] = 2;
				powerUpStopTimes[0] = 10;
				powerUpStartTimes[1] = 6;
				powerUpStopTimes[1] = 22;
				powerUpStartTimes[2] = 12;
				powerUpStopTimes[2] = 18;
				powerUpStartTimes[3] = 26;
				powerUpStopTimes[3] = 35;
				powerUpStartTimes[4] = 29;
				powerUpStopTimes[4] = 36;
				powerUpStartTimes[5] = 36;
				powerUpStopTimes[5] = 47;
				powerUpStartTimes[6] = 30;
				powerUpStopTimes[6] = 45;
				powerUpStartTimes[7] = 36;
				powerUpStopTimes[7] = 50;
				
				break;
			default:
				numEnemyShips = 20;
				numPowerUps = 6;
				
				enemyShips = new EnemyShip[numEnemyShips];
				shipEntranceTimes = new int[numEnemyShips];
				powerUps = new PowerUp[numPowerUps];
				powerUpStartTimes = new int[numPowerUps];
				powerUpStopTimes = new int[numPowerUps];
				
				level.setLevelTime(60);
				
				enemyShips[0] = new DefaultEnemy();
				enemyShips[1] = new ConfusedEnemy();
				enemyShips[2] = new SeekerEnemy();
				enemyShips[3] = new DefaultEnemy();
				enemyShips[4] = new ConfusedEnemy();
				enemyShips[5] = new SeekerEnemy();
				enemyShips[6] = new DefaultEnemy();
				enemyShips[7] = new ConfusedEnemy();
				enemyShips[8] = new SeekerEnemy();
				enemyShips[9] = new DefaultEnemy();
				enemyShips[10] = new ConfusedEnemy();
				enemyShips[11] = new SeekerEnemy();
				enemyShips[12] = new DefaultEnemy();
				enemyShips[13] = new ConfusedEnemy();
				enemyShips[14] = new SeekerEnemy();
				enemyShips[15] = new DefaultEnemy();
				enemyShips[16] = new ConfusedEnemy();
				enemyShips[17] = new SeekerEnemy();
				enemyShips[18] = new DefaultEnemy();
				enemyShips[19] = new ConfusedEnemy();
				
				shipEntranceTimes[0] = 1;
				shipEntranceTimes[1] = 3;
				shipEntranceTimes[2] = 5;
				shipEntranceTimes[3] = 9;
				shipEntranceTimes[4] = 13;
				shipEntranceTimes[5] = 18;
				shipEntranceTimes[6] = 20;
				shipEntranceTimes[7] = 21;
				shipEntranceTimes[8] = 23;
				shipEntranceTimes[9] = 24;
				shipEntranceTimes[10] = 25;
				shipEntranceTimes[11] = 28;
				shipEntranceTimes[12] = 30;
				shipEntranceTimes[13] = 35;
				shipEntranceTimes[14] = 36;
				shipEntranceTimes[15] = 39;
				shipEntranceTimes[16] = 40;
				shipEntranceTimes[17] = 48;
				shipEntranceTimes[18] = 49;
				shipEntranceTimes[19] = 50;
				
				powerUps[0] = new AmmoPowerUp();
				powerUps[1] = new AmmoPowerUp();
				powerUps[2] = new ShieldPowerUp();
				powerUps[3] = new AmmoPowerUp();
				powerUps[4] = new ShieldPowerUp();
				powerUps[5] = new ShieldPowerUp();
				
				powerUpStartTimes[0] = 6;
				powerUpStopTimes[0] = 15;
				powerUpStartTimes[1] = 10;
				powerUpStopTimes[1] = 19;
				powerUpStartTimes[2] = 12;
				powerUpStopTimes[2] = 18;
				powerUpStartTimes[3] = 26;
				powerUpStopTimes[3] = 35;
				powerUpStartTimes[4] = 29;
				powerUpStopTimes[4] = 36;
				powerUpStartTimes[5] = 36;
				powerUpStopTimes[5] = 47;
				
				break;
		}
		
		level.setEnemyShips(enemyShips);
		level.setShipEntranceTimes(shipEntranceTimes);
		level.setPowerUps(powerUps);
		level.setPowerUpStartTimes(powerUpStartTimes);
		level.setPowerUpStopTimes(powerUpStopTimes);
		
		return level;
	}
}