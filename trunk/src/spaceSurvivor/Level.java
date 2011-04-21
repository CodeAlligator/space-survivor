package spaceSurvivor;

import spaceSurvivor.powerUp.PowerUp;
import spaceSurvivor.ship.EnemyShip;

/**
 * <code>Level</code> has the enemy ships and power ups as well as timing for a level.
 * @author Paul
 *
 */
public class Level {
	/**
	 * The current level number.
	 */
	private int levelNumber;
	
	/**
	 * Array of enemy ships.
	 */
	private EnemyShip[] enemyShips;
	
	/**
	 * Array of when each enemy ship should enter the game.
	 */
	private int[] shipEntranceTimes;
	
	/**
	 * Array of power ups.
	 */
	private PowerUp[] powerUps;
	
	/**
	 * Array of when each power up should enter the game.
	 */
	private int[] powerUpStartTimes;
	
	/**
	 * Array of when each power up should leave the game.
	 */
	private int[] powerUpStopTimes;
	
	/**
	 * Time allowed for this level.
	 */
	private int levelTime;
	
	/**
	 * Number of bullets when level starts.
	 */
	private int ammo;
	
	/**
	 * Percent of shield when level starts.
	 */
	private int shield;
	
	/**
	 * Default constructor.
	 */
	public Level(){
		
	}
	
	/**
	 * Instantiates new <code>Level</code> object with these parameters.
	 * @param enemyShips	
	 * @param shipEntranceTimes 
	 * @param powerUps	
	 * @param powerUpStartTimes	
	 * @param powerUpStopTimes	
	 * @param levelTime	
	 * @param ammo 
	 * @param shield 
	 */
	public Level(EnemyShip[] enemyShips, int[] shipEntranceTimes, PowerUp[] powerUps, int[] powerUpStartTimes, int[] powerUpStopTimes, int levelTime, int levelNumber, int ammo, int shield) {
		this.enemyShips = enemyShips;
		this.shipEntranceTimes = shipEntranceTimes;
		this.powerUps = powerUps;
		this.powerUpStartTimes = powerUpStartTimes;
		this.powerUpStopTimes = powerUpStopTimes;
		this.levelTime = levelTime;
		this.levelNumber = levelNumber;
		this.ammo = ammo;
		this.shield = shield;
	}
	
	/**
	 * @return the enemyShips
	 */
	public EnemyShip[] getEnemyShips() {
		return enemyShips;
	}

	/**
	 * @param enemyShips the enemyShips to set
	 */
	public void setEnemyShips(EnemyShip[] enemyShips) {
		this.enemyShips = enemyShips;
	}

	/**
	 * @return the shipEntranceTimes
	 */
	public int[] getShipEntranceTimes() {
		return shipEntranceTimes;
	}

	/**
	 * @param shipEntranceTimes the shipEntranceTimes to set
	 */
	public void setShipEntranceTimes(int[] shipEntranceTimes) {
		this.shipEntranceTimes = shipEntranceTimes;
	}

	/**
	 * @return the powerUps
	 */
	public PowerUp[] getPowerUps() {
		return powerUps;
	}

	/**
	 * @param powerUps the powerUps to set
	 */
	public void setPowerUps(PowerUp[] powerUps) {
		this.powerUps = powerUps;
	}

	/**
	 * @return the powerUpStartTimes
	 */
	public int[] getPowerUpStartTimes() {
		return powerUpStartTimes;
	}

	/**
	 * @param powerUpStartTimes the powerUpStartTimes to set
	 */
	public void setPowerUpStartTimes(int[] powerUpStartTimes) {
		this.powerUpStartTimes = powerUpStartTimes;
	}

	/**
	 * @return the powerUpStopTimes
	 */
	public int[] getPowerUpStopTimes() {
		return powerUpStopTimes;
	}

	/**
	 * @param powerUpStopTimes the powerUpStopTimes to set
	 */
	public void setPowerUpStopTimes(int[] powerUpStopTimes) {
		this.powerUpStopTimes = powerUpStopTimes;
	}

	/**
	 * @return the levelTime in seconds
	 */
	public int getLevelTime() {
		return levelTime;
	}

	/**
	 * @param levelTime the levelTime to set in seconds
	 */
	public void setLevelTime(int levelTime) {
		this.levelTime = levelTime;
	}

	/**
	 * @return the levelNumber
	 */
	public int getLevelNumber() {
		return levelNumber;
	}

	/**
	 * @param levelNumber the levelNumber to set
	 */
	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}

	public int getAmmo() {
		return ammo;
	}

	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}

	public int getShield() {
		return shield;
	}

	public void setShield(int shield) {
		this.shield = shield;
	}
}