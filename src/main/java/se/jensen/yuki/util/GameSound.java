package se.jensen.yuki.util;

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
        try {
            File soundFile = new File(BGM_SOUND_PATH);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            // Hämta volymkontrollen
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(-15.0f);

            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Spelar applåder ljuden
     */
    public void playApplauseSound() {
        try {
            File soundFile = new File(APPLAUSE_SOUND_PATH);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Spelar tärningens ljud
     */
    public void playThrowDiceSound() {
        try {
            File soundFile = new File(DICE_SOUND_PATH);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
