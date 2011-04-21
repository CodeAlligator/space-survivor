package spaceSurvivor.audio;

import java.io.IOException;

import sun.audio.AudioPlayer;

/**
 * <code>GameAudioPlayer</code> handles the audio play back of the game.
 * @author Paul
 *
 */
public class GameAudioPlayer{
	AudioSample asGun;
	AudioSample asCrash;
	AudioSample asPowerup;

	public GameAudioPlayer() {
		try {
			asGun = new AudioSample(AudioSample.GUN);
			asCrash = new AudioSample(AudioSample.CRASH);
			asPowerup = new AudioSample(AudioSample.POWERUP);
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	public void playGunShot(){
		try {
			asGun = new AudioSample(AudioSample.GUN);
		} catch (IOException e) {
			e.printStackTrace();
		}
		AudioPlayer.player.start(asGun.getAudioStream());
	}

	public void stopGunShot(){
		AudioPlayer.player.stop(asGun.getAudioStream());
	}

	public void playCrash(){
		try {
			asCrash = new AudioSample(AudioSample.CRASH);
		} catch (IOException e) {
			e.printStackTrace();
		}
		AudioPlayer.player.start(asCrash.getAudioStream());
	}

	public void stopCrash(){
		AudioPlayer.player.stop(asCrash.getAudioStream());
	}
	
	public void playPowerup(){
		try {
			asPowerup = new AudioSample(AudioSample.POWERUP);
		} catch (IOException e) {
			e.printStackTrace();
		}
		AudioPlayer.player.start(asPowerup.getAudioStream());
	}

	public void stopPowerup(){
		AudioPlayer.player.stop(asPowerup.getAudioStream());
	}
	
	public static void main(String[] args){
		GameAudioPlayer g = new GameAudioPlayer();
		g.playGunShot();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//g.stopGunShot();
		g.playGunShot();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		g.playCrash();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		g.playPowerup();
		
		
		//g.stopCrash();
	}
}