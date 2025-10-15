package Controller;

import javafx.scene.media.AudioClip;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class AudioController {
    public static final String CLICK_SOUND = "/sound/button.wav";
    public static final String CLEAR_SOUND = "/sound/clear.wav";
    public static final String MATCH_SOUND = "/sound/match.wav";
    public static final String MODE_SOUND = "/sound/mode.wav";
    public static final String START_SOUND = "/sound/start.wav";
    public static final String FINISH_SOUND = "/sound/finish.wav";
    public static final String JACKPOT_SOUND = "/sound/jackpot.wav";
    private Map<String, AudioClip> soundMap;

    public AudioController(){
        soundMap = new HashMap<>();
        loadAllSounds();
    }

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

    private void loadSound(String clickSoundFile) {
        // Implementation to load sound file
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

    public void playSound(String soundFile) {
        AudioClip clip = soundMap.get(soundFile);
        if (clip != null) {
            clip.play();
        }
    }
}
