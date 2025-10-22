package fr.bebedlastreat.slkart.content;

import lombok.Data;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

@Data
public class Sound {
    public static final int MENU_MUSIC = 0;

    private final Clip[] clips = new Clip[32];
    private int index = 0;

    public Sound() {
        loadSound("/music/Jazzy_Electric_Feel.wav");
    }

    private void loadSound(String path) {
        URL url = getClass().getResource(path);
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clips[index] = clip;
            index++;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }

    }

    public void play(int i) {
        clips[i].start();
    }

    public void loop(int i) {
        clips[i].loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(int i) {
        clips[i].stop();
    }

    public void stopAll() {
        for (int i = 0; i < index; i++) {
            stop(i);
        }
    }

    public void changeVolume(int i, float volume) {
        ((FloatControl) clips[i].getControl(FloatControl.Type.MASTER_GAIN)).setValue(volume);
    }
}
