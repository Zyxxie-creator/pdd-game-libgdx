package com.pddgame.game.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.pddgame.game.game.objects.CarInterface;
import com.pddgame.game.game.objects.RoadSign;
import com.pddgame.game.game.objects.TrafficLight;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * Логика уровней
 * Подсчет общего количества всех уровней
 * Номер уровня
 * Смена уровня
 *
 */
public class Level {
    public static final String TAG = Level.class.getName();
    public int allLevelsCount;
    private World b2World;
    public Array<CarInterface> cars;
    private int carsLength;
    public GameLevels gameLevels;
    public int levelNumber;
    public Array<Integer> rightSequenCross;
    public TiledMap roadMap;
    public Array<RoadSign> roadSigns;
    private int signsLength;
    private int trLength;
    public Array<TrafficLight> trafficLights;
    public Array<Array<Vector2>> ways;

    public void renderDebugGun(ShapeRenderer shapeRenderer) {
    }

    public Level() {
        init();
    }

    public Level(World world, int i) {
        this.b2World = world;
        this.levelNumber = i;
        init();
        Application application = Gdx.app;
        String str = TAG;
        application.log(str, "level number " + i);
    }

    private void init() {
        this.cars = new Array<>();
        this.trafficLights = new Array<>();
        this.roadSigns = new Array<>();
        this.ways = new Array<>(1);
        this.rightSequenCross = new Array<>();
        initLevel(this.levelNumber);
        this.allLevelsCount = 30;
    }

    public void update(float f) {
        for (int i = 0; i < this.carsLength; i++) {
            this.cars.get(i).update(f);
        }
        for (int i2 = 0; i2 < this.trLength; i2++) {
            this.trafficLights.get(i2).update(f);
        }
    }

    public void render(SpriteBatch spriteBatch) {
        for (int i = 0; i < this.carsLength; i++) {
            this.cars.get(i).render(spriteBatch);
        }
        for (int i2 = 0; i2 < this.trLength; i2++) {
            this.trafficLights.get(i2).render(spriteBatch);
        }
        for (int i3 = 0; i3 < this.signsLength; i3++) {
            this.roadSigns.get(i3).render(spriteBatch);
        }
    }

    public void renderWorldDebug(ShapeRenderer shapeRenderer) {
        for (int i = 0; i < this.carsLength; i++) {
            this.cars.get(i).drawDebug(shapeRenderer);
        }
    }

    private void initLevel(int i) {
        GameLevels gameLevels = new GameLevels();
        this.gameLevels = gameLevels;
        gameLevels.initLevel(this.b2World, i, this.cars, this.trafficLights, this.ways, this.rightSequenCross, this.roadSigns);
        this.carsLength = this.cars.size;
        this.trLength = this.trafficLights.size;
        this.signsLength = this.roadSigns.size;
        this.roadMap = this.gameLevels.getRoadMap();
    }


    private HashMap<Integer, Integer> initLevelsList() {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        ArrayList arrayList = new ArrayList();
        for (int i = 1; i < 91; i++) {
            arrayList.add(Integer.valueOf(i));
        }
        Collections.shuffle(arrayList);
        for (int i2 = 0; i2 < 10; i2++) {
            hashMap.put(Integer.valueOf(i2), (Integer) arrayList.get(i2));
        }
        for (int i3 = 1; i3 < arrayList.size(); i3++) {
            if (hashMap.get(Integer.valueOf(i3)) != null) {
                Application application = Gdx.app;
                String str = TAG;
                application.log(str, "levelMap.get(i) = " + hashMap.get(Integer.valueOf(i3)));
            }
        }
        Application application2 = Gdx.app;
        String str2 = TAG;
        application2.log(str2, "levelMap.size = " + hashMap.size());
        return hashMap;
    }
}
