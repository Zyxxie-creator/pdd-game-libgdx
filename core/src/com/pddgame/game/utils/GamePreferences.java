package com.pddgame.game.utils;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.MathUtils;
import java.util.Map;


/**
 *
 * Параметры для записи в cookies
 * Записть громкости звука
 * Запись громкости музыки
 * Запись результатов
 * Запись текущего уровня пользователя
 * Запись правильных ответов пользователя
 *
 */
public class GamePreferences {
    public static final String TAG = GamePreferences.class.getName();
    public static final GamePreferences instance = new GamePreferences();
    public int currentLvl;
    public String date_1;
    public String date_2;
    public String date_3;
    public String date_4;
    public String date_5;
    public String date_6;
    public String date_7;
    public boolean music;
    private Preferences prefs = Gdx.app.getPreferences("crossroad.prefs");
    public String result_1;
    public String result_2;
    public String result_3;
    public String result_4;
    public String result_5;
    public String result_6;
    public String result_7;
    public int rightAnswerCount;
    public boolean showFpsCounter;
    public boolean sound;
    public float volMusic;
    public float volSound;

    private GamePreferences() {
    }

    public void load() {
        this.sound = this.prefs.getBoolean("sound", true);
        this.music = this.prefs.getBoolean("music", true);
        this.volSound = MathUtils.clamp(this.prefs.getFloat("volSound", 0.5f), 0.0f, 1.0f);
        this.volMusic = MathUtils.clamp(this.prefs.getFloat("volMusic", 0.1f), 0.0f, 0.3f);
        this.showFpsCounter = this.prefs.getBoolean("showFpsCounter", false);
        this.currentLvl = this.prefs.getInteger("currentLvl", 1);
        this.rightAnswerCount = this.prefs.getInteger("rightAnswerCount", 0);
        this.date_1 = this.prefs.getString("date_1", "--.--.----");
        this.date_2 = this.prefs.getString("date_2", "--.--.----");
        this.date_3 = this.prefs.getString("date_3", "--.--.----");
        this.date_4 = this.prefs.getString("date_4", "--.--.----");
        this.date_5 = this.prefs.getString("date_5", "--.--.----");
        this.date_6 = this.prefs.getString("date_6", "--.--.----");
        this.date_7 = this.prefs.getString("date_7", "--.--.----");
        this.result_1 = this.prefs.getString("result_1", "-");
        this.result_2 = this.prefs.getString("result_2", "-");
        this.result_3 = this.prefs.getString("result_3", "-");
        this.result_4 = this.prefs.getString("result_4", "-");
        this.result_5 = this.prefs.getString("result_5", "-");
        this.result_6 = this.prefs.getString("result_6", "-");
        this.result_7 = this.prefs.getString("result_7", "-");
    }

    public void save() {
        this.prefs.putBoolean("sound", this.sound);
        this.prefs.putBoolean("music", this.music);
        this.prefs.putFloat("volSound", this.volSound);
        this.prefs.putFloat("volMusic", this.volMusic);
        this.prefs.putBoolean("showFpsCounter", this.showFpsCounter);
        this.prefs.putInteger("currentLvl", this.currentLvl);
        this.prefs.putInteger("rightAnswerCount", this.rightAnswerCount);
        this.prefs.putString("date_1", this.date_1);
        this.prefs.putString("date_2", this.date_2);
        this.prefs.putString("date_3", this.date_3);
        this.prefs.putString("date_4", this.date_4);
        this.prefs.putString("date_5", this.date_5);
        this.prefs.putString("date_6", this.date_6);
        this.prefs.putString("date_7", this.date_7);
        this.prefs.putString("result_1", this.result_1);
        this.prefs.putString("result_2", this.result_2);
        this.prefs.putString("result_3", this.result_3);
        this.prefs.putString("result_4", this.result_4);
        this.prefs.putString("result_5", this.result_5);
        this.prefs.putString("result_6", this.result_6);
        this.prefs.putString("result_7", this.result_7);
        this.prefs.flush();
    }

    public void saveCurrentLevel() {
        this.prefs.putInteger("currentLvl", this.currentLvl);
        this.prefs.flush();
    }

    public void printAllPrefs() {
        for (Map.Entry<String, ?> entry : this.prefs.get().entrySet()) {
            Application application = Gdx.app;
            String str = TAG;
            application.log(str, "key = " + entry.getKey() + ", value = " + entry.getValue());
        }
    }
}
