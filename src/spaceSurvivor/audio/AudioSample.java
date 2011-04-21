package spaceSurvivor.audio;

import java.io.IOException;
import java.net.URL;

import sun.audio.AudioStream;

/**
 * <code>AudioSample</code> represents an audio sound.
 * @author Paul
 *
 */
public class AudioSample {
	private URL url;
	private AudioStream audioStream;
	public static final int GUN = 0;
	public static final int CRASH = 1;
	public static final int POWERUP = 2;
	
	public AudioSample(int type) throws IOException {
		switch(type){
			case GUN:
				url = getClass().getResource("gun.wav");
				audioStream = new AudioStream(url.openStream());
				break;
			case CRASH:
				url = getClass().getResource("crash.wav");
				audioStream = new AudioStream(url.openStream());
				break;
			case POWERUP:
				url = getClass().getResource("powerup.wav");
				audioStream = new AudioStream(url.openStream());
				break;
			default:
				url = getClass().getResource("gun.wav");
				break;
		}
	}

	public URL getUrl() {
		return url;
	}
	
	public AudioStream getAudioStream() {
		return audioStream;
	}
}