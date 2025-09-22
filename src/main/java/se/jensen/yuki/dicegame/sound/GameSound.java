package se.jensen.yuki.dicegame.sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class GameSound {
    /** Fil vägen för tärningens ljud */
    private static final String DICE_SOUND_PATH = "./src/main/resources/dice.wav";
    /** Fil vägen för BGM ljud */
    private static final String BGM_SOUND_PATH = "./src/main/resources/bgm.wav";
    /** Fil vägen för applåder ljud */
    private static final String APPLAUSE_SOUND_PATH = "./src/main/resources/applause.wav";

    /**
     * Spelar BGM ljuden
     */
    public void playBgm() {
        playSound(BGM_SOUND_PATH, -15.0f);
    }

    /**
     * Spelar applåder ljuden
     */
    public void playApplauseSound() {
        playSound(APPLAUSE_SOUND_PATH);
    }

    /**
     * Spelar tärningens ljud
     */
    public void playThrowDiceSound() {
        playSound(DICE_SOUND_PATH);
    }

    /**
     * Spelar ett ljud
     * @param filePath är en fil väg för ljudet
     */
    private void playSound(String filePath) {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Spelar ett ljud
     * @param filePath är en fil väg för ljudet
     * @param volume är volym värdet
     */
    private void playSound(String filePath, float volume) {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            // Hämta volymkontrollen
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(volume);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
