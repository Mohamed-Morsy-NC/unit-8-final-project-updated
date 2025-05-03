package scripts.backend;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;


public class AudioHandler {
    private Long currentFrame;
    private Clip clip;
    private AudioInputStream audioInputStream;
    private String filePath;

    public AudioHandler(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.filePath = filePath;

        this.audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

        this.clip = AudioSystem.getClip();
        clip.open(audioInputStream);

    }

    public void playAudio() {
        clip.start();
    }

    public void playAudioLooped() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
    }

    public void stopAudio() {
        clip.close();
    }

    public String getAudioStatus() {
        return "Currently Playing: " + filePath;
    }

}