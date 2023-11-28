package fr.bebedlastreat.slkart.sound;

import lombok.Data;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

@Data
public class Sound {
    public static final int MUSIC = 0;
    public static final int BLOCK = 1;
    public static final int SLOW = 2;
    public static final int SPEED = 3;

    private Clip clip;
    private final URL[] soundUrl = new URL[32];

    public Sound() {
        soundUrl[0] = getClass().getResource("/sounds/music.wav");
        soundUrl[1] = getClass().getResource("/sounds/blocked.wav");
        soundUrl[2] = getClass().getResource("/sounds/slow.wav");
        soundUrl[3] = getClass().getResource("/sounds/speed.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundUrl[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}
