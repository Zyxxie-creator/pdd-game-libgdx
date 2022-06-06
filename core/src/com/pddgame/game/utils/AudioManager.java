package com.pddgame.game.utils;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 *
 * Менеджер звука
 * Запуск звука
 * Назначение громкости звука
 *
 */
public class AudioManager {
    public static final AudioManager instance = new AudioManager();
    public Sound loopingSnd;
    private Music playingMusic;
    private long soundId;

    private AudioManager() {
    }

    public void play(Sound sound) {
        play(sound, 0.5f);
    }

    public void play(Sound sound, float f) {
        play(sound, f, 1.0f);
    }

    public void play(Sound sound, float f, float f2) {
        play(sound, f, f2, 0.0f);
    }

    public void play(Sound sound, float f, float f2, float f3) {
        if (GamePreferences.instance.sound) {
            sound.play(GamePreferences.instance.volSound * f, f2, f3);
        }
    }

    public void play(Music music) {
        stopMusic();
        this.playingMusic = music;
        if (GamePreferences.instance.music) {
            music.setLooping(true);
            music.setVolume(GamePreferences.instance.volMusic);
            music.play();
        }
    }

    public void stopMusic() {
        Music music = this.playingMusic;
        if (music != null) {
            music.stop();
        }
    }

    public void onSettingsUpdated() {
        Music music = this.playingMusic;
        if (music != null) {
            music.setVolume(GamePreferences.instance.volMusic);
            if (!GamePreferences.instance.music) {
                this.playingMusic.pause();
            } else if (!this.playingMusic.isPlaying()) {
                this.playingMusic.play();
            }
        }
    }
}
