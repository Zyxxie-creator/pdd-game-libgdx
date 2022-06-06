package com.pddgame.game.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.pddgame.game.game.objects.CarInterface;
import com.pddgame.game.screens.DirectedGame;
import com.pddgame.game.screens.StartScreen;
import com.pddgame.game.utils.AudioManager;
import com.pddgame.game.utils.CameraHelper;
import com.pddgame.game.utils.GamePreferences;


/**
 *
 * Контроллер уровня
 * Перезапуск уровня
 * Пауза уровня
 * Номер уровня
 * Подсчет правильных ответов
 *
 */
public class WorldController extends InputAdapter implements Disposable {
        private static final String TAG = WorldController.class.getName();
        public static boolean restartLevel;
        public World b2dWorld;
        private float camMoveSpeed;
        public CameraHelper cameraHelper;
        private DirectedGame game;
        private boolean gameIsPaused;
        public boolean gameOverAccident;
        private float gameOverDelay;
        public boolean isAccident;
        public boolean isRightAnswer;
        public boolean isWrongAnswer;
        public Level level;
        public int levelNumber;
        public Polygon f279p1;
        private Array<Integer> playerSeqList;
        public GamePreferences prefs;
        private float rightAnswerPercentage;
        public String rightAnswersString;
        private Rectangle f280r1 = new Rectangle();
        public Vector3 touchPoint = new Vector3();
        public Vector3 touchCoords = new Vector3();
        int carOrder = 0;
        private Polygon overlap = new Polygon();

        @Override
        public boolean touchDragged(int i, int i2, int i3) {
        return false;
    }

    public WorldController(DirectedGame directedGame, int i) {
        this.levelNumber = i;
        this.game = directedGame;
        init();
    }

        public void init() {
        restartLevel = false;
        this.gameOverAccident = false;
        this.isRightAnswer = false;
        this.isWrongAnswer = false;
        this.gameIsPaused = false;
        GamePreferences gamePreferences = GamePreferences.instance;
        this.prefs = gamePreferences;
        gamePreferences.load();
        printLog("prefs.rightAnswerCount init() = " + this.prefs.rightAnswerCount);
        CameraHelper cameraHelper = new CameraHelper();
        this.cameraHelper = cameraHelper;
        cameraHelper.setPosition(12.2f, 21.3f);
        this.cameraHelper.setZoom(6.6f);
        Polygon polygon = new Polygon(new float[]{0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f});
        this.f279p1 = polygon;
        polygon.setOrigin(0.5f, 0.5f);
        Box2D.init();
        World world = this.b2dWorld;
        if (world != null) {
            world.dispose();
        }
        World world2 = new World(new Vector2(0.0f, 0.0f), true);
        this.b2dWorld = world2;
        world2.setContactListener(new ContactListener() {
            @Override
            public void endContact(Contact contact) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse contactImpulse) {
            }

            @Override
            public void preSolve(Contact contact, Manifold manifold) {
            }

            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();
                if (fixtureA.getFilterData().categoryBits == 1) {
                    WorldController.this.printLog("collision");
                    ((CarInterface) fixtureA.getBody().getUserData()).setCarInAccident(true);
                    ((CarInterface) fixtureB.getBody().getUserData()).setCarInAccident(true);
                    AudioManager.instance.play(Assets.instance.sounds.accident);
                    WorldController.this.isAccident = true;
                }
            }
        });
        this.gameOverDelay = 2.0f;
        this.playerSeqList = new Array<>();
        this.isAccident = false;
        initLevel();
    }

        private void initLevel() {
        this.level = new Level(this.b2dWorld, this.levelNumber);
        this.carOrder = 0;
    }

        public void update(float f) {
        if (!this.gameIsPaused) {
            this.b2dWorld.step(f, 6, 2);
            this.level.update(f);
            if (this.isAccident) {
                float f2 = this.gameOverDelay;
                if (f2 > 0.0f) {
                    float f3 = f2 - f;
                    this.gameOverDelay = f3;
                    if (f3 < 0.0f) {
                        printLog("Game Over! Accident");
                        this.gameOverAccident = true;
                        setGameIsPaused(true);
                    }
                }
            } else if (this.playerSeqList.size == this.level.cars.size) {
                float f4 = this.gameOverDelay;
                if (f4 > 0.0f) {
                    float f5 = f4 - f;
                    this.gameOverDelay = f5;
                    if (f5 < 0.0f) {
                        if (checkSequenceCarStarting(this.level.rightSequenCross)) {
                            printLog("Level Complete! Right anser!");
                            this.isRightAnswer = true;
                            setGameIsPaused(true);
                        } else {
                            printLog("Game Over! Wrong anser!");
                            this.isWrongAnswer = true;
                            setGameIsPaused(true);
                        }
                    }
                }
            }
        }
        if (restartLevel) {
            init();
        }
        handleDebugInput(f);
    }

        private void handleDebugInput(float f) {
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            float f2 = 50.0f * f;
            if (Gdx.input.isKeyPressed(59)) {
                f2 *= 5.0f;
            }
            if (Gdx.input.isKeyPressed(21)) {
                moveCamera(-f2, 0.0f);
            }
            if (Gdx.input.isKeyPressed(22)) {
                moveCamera(f2, 0.0f);
            }
            if (Gdx.input.isKeyPressed(19)) {
                moveCamera(0.0f, f2);
            }
            if (Gdx.input.isKeyPressed(20)) {
                moveCamera(0.0f, -f2);
            }
            if (Gdx.input.isKeyPressed(67)) {
                this.cameraHelper.setPosition(0.0f, 0.0f);
            }
            float f3 = f * 5.0f;
            if (Gdx.input.isKeyPressed(59)) {
                f3 *= 1.0f;
            }
            if (Gdx.input.isKeyPressed(55)) {
                this.cameraHelper.addZoom(f3);
                printLog("zoom = " + this.cameraHelper.getZoom());
            }
            if (Gdx.input.isKeyPressed(56)) {
                this.cameraHelper.addZoom(-f3);
                printLog("zoom = " + this.cameraHelper.getZoom());
            }
            if (Gdx.input.isKeyPressed(76)) {
                this.cameraHelper.setZoom(1.0f);
            }
        }
    }

        private void moveCamera(float f, float f2) {
        this.cameraHelper.setPosition(f + this.cameraHelper.getPosition().x, f2 + this.cameraHelper.getPosition().y);
    }

        @Override
        public boolean keyUp(int i) {
        if (i == 46) {
            init();
            this.isAccident = false;
            this.isWrongAnswer = false;
            this.isRightAnswer = false;
            Gdx.app.debug(TAG, "Game world reseted");
        }
        if (i == 131 || i == 4) {
            AudioManager.instance.stopMusic();
            backToMenu();
        }
        return false;
    }

        @Override
        public boolean keyDown(int i) {
        if (i == 8) {
            this.level.cars.get(0).setCarInAccident(true);
        }
        if (i == 9) {
            this.level.cars.get(0).setTurnLeft(true);
        }
        if (i == 10) {
            this.level.cars.get(0).setTurnRight(true);
        }
        if (i == 51) {
            this.levelNumber = 2;
        }
        if (i == 47) {
            this.game.pause();
        }
        if (i == 29) {
            this.game.resume();
        }
        return false;
    }

        @Override
        public boolean touchDown(int i, int i2, int i3, int i4) {
        if (i3 != 0) {
            return false;
        }
        this.touchPoint.set(i, i2, 0.0f);
        printLog("screen touch = " + this.touchPoint);
        this.touchCoords = WorldRenderer.unprojectCoords(this.touchPoint);
        printLog("world touch = " + this.touchCoords);
        this.f279p1.setPosition(this.touchCoords.x - this.f279p1.getOriginX(), this.touchCoords.y - this.f279p1.getOriginY());
        touchCollisionsWithCars(this.touchCoords.x, this.touchCoords.y);
        return false;
    }

        private void touchCollisionsWithCars(float f, float f2) {
        for (int i = 0; i < this.level.cars.size; i++) {
            CarInterface carInterface = this.level.cars.get(i);
            if (Intersector.intersectPolygons(carInterface.getPolygon(), this.f279p1, this.overlap) && !carInterface.isCarInAccident() && !carInterface.isMoveCar() && !this.gameIsPaused) {
                carInterface.setMoveCar(true);
                this.playerSeqList.add(Integer.valueOf(carInterface.getSequenceIndex()));
                printLog("right sequence = " + checkSequenceCarStarting(this.level.rightSequenCross));
            }
        }
    }

        private boolean checkSequenceCarStarting(Array<Integer> array) {
        return array.equals(this.playerSeqList);
    }

        @Override
        public boolean touchUp(int i, int i2, int i3, int i4) {
        this.f279p1.setPosition(-10.0f, -10.0f);
        if (i3 != 0) {
            return true;
        }
        this.touchPoint.set(i, i2, 0.0f);
        return true;
    }

        public String getRightAnswersPercentage(float f) {
        this.rightAnswerPercentage = this.prefs.rightAnswerCount * (100.0f / f);
        String format = String.valueOf(this.rightAnswerPercentage);
        this.rightAnswersString = format;
        return format;
    }

        public void setResultsInPrefs() {
        getRightAnswersPercentage(this.level.allLevelsCount);
        GamePreferences gamePreferences = this.prefs;
        gamePreferences.date_7 = gamePreferences.date_6;
        GamePreferences gamePreferences2 = this.prefs;
        gamePreferences2.date_6 = gamePreferences2.date_5;
        GamePreferences gamePreferences3 = this.prefs;
        gamePreferences3.date_5 = gamePreferences3.date_4;
        GamePreferences gamePreferences4 = this.prefs;
        gamePreferences4.date_4 = gamePreferences4.date_3;
        GamePreferences gamePreferences5 = this.prefs;
        gamePreferences5.date_3 = gamePreferences5.date_2;
        GamePreferences gamePreferences6 = this.prefs;
        gamePreferences6.date_2 = gamePreferences6.date_1;
        this.prefs.date_1 = " ";
        GamePreferences gamePreferences7 = this.prefs;
        gamePreferences7.result_7 = gamePreferences7.result_6;
        GamePreferences gamePreferences8 = this.prefs;
        gamePreferences8.result_6 = gamePreferences8.result_5;
        GamePreferences gamePreferences9 = this.prefs;
        gamePreferences9.result_5 = gamePreferences9.result_4;
        GamePreferences gamePreferences10 = this.prefs;
        gamePreferences10.result_4 = gamePreferences10.result_3;
        GamePreferences gamePreferences11 = this.prefs;
        gamePreferences11.result_3 = gamePreferences11.result_2;
        GamePreferences gamePreferences12 = this.prefs;
        gamePreferences12.result_2 = gamePreferences12.result_1;
        this.prefs.result_1 = this.rightAnswersString;
        printLog("rightAnswersString = " + this.rightAnswersString);
        this.prefs.save();
    }

        @Override
        public void dispose() {
        World world = this.b2dWorld;
        if (world != null) {
            world.dispose();
        }
        this.prefs.save();
    }

        public void setGameIsPaused(boolean z) {
        this.gameIsPaused = z;
    }

        private void backToMenu() {
        this.game.setScreen(new StartScreen(this.game));
    }

        public void printLog(String str) {
        Gdx.app.log(TAG, str);
    }
    }