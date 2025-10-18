package Service;

import javafx.scene.media.AudioClip;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * AudioService class to manage loading and playing sound effects.
 */
public class AudioService {
    // Sound file paths
    public static final String CLICK_SOUND = "/sound/button.wav";
    public static final String CLEAR_SOUND = "/sound/clear.wav";
    public static final String MATCH_SOUND = "/sound/match.wav";
    public static final String MODE_SOUND = "/sound/mode.wav";
    public static final String START_SOUND = "/sound/start.wav";
    public static final String FINISH_SOUND = "/sound/finish.wav";
    public static final String JACKPOT_SOUND = "/sound/jackpot.wav";
    // Map to hold loaded sounds
    private Map<String, AudioClip> soundMap;

    // Constructor to initialize and load sounds
    public AudioService(){
        soundMap = new HashMap<>();
        loadAllSounds();
    }

    // Method to load all sound files
    private void loadAllSounds() {
        // Load sound files
        loadSound(CLICK_SOUND);
        loadSound(CLEAR_SOUND);
        loadSound(MATCH_SOUND);
        loadSound(MODE_SOUND);
        loadSound(START_SOUND);
        loadSound(FINISH_SOUND);
        loadSound(JACKPOT_SOUND);
    }

    /**
     * Loads a sound file and stores it in the sound map.
     * @param clickSoundFile The path to the sound file to load.
     */
    private void loadSound(String clickSoundFile) {
        System.out.println("Loading sound: " + clickSoundFile);
        try {
            URL soundURL = getClass().getResource(clickSoundFile);
            if (soundURL == null) {
                throw new Exception("Sound file not found: " + clickSoundFile);
            }
            AudioClip clip = new AudioClip(soundURL.toExternalForm());
            soundMap.put(clickSoundFile, clip);
            System.out.println("Sound loaded successfully: " + clickSoundFile);
        } catch (Exception e){
            System.err.println("Error loading sound: " + clickSoundFile + " : "  + e.getMessage());
        }
    }

    /**
     * Plays the specified sound effect.
     * @param soundFile - The path to the sound file to play.
     */
    public void playSound(String soundFile) {
        AudioClip clip = soundMap.get(soundFile);
        if (clip != null) {
            clip.play();
        }
    }
}
