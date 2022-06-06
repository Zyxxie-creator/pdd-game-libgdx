package com.pddgame.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.pddgame.game.game.Assets;
import com.pddgame.game.game.WorldController;
import com.pddgame.game.game.WorldRenderer;
import com.pddgame.game.utils.AudioManager;
import com.pddgame.game.utils.GamePreferences;

/**
 *
 * Игровой экран
 * Обработка действий
 * Переходы по уровню
 * Перезапуск уровня
 * Информация о уровне
 * Выход в главное меню
 *
 */
public class GameScreen extends AbstractGameScreen {
    private static final String TAG = GameScreen.class.getName();
    private final float DEBUG_REBUILD_INTERVAL;
    private TextureAtlas atlas;
    private boolean debugEnabled;
    private float debugRebuildStage;
    private StringBuilder levNumbStr;
    public int level;
    private InputMultiplexer multiplexer;
    private boolean paused;
    private GamePreferences prefs;
    private boolean showDialog;
    private Skin skin;
    private Stage stage;
    private WorldController worldController;
    private WorldRenderer worldRenderer;

    public GameScreen(DirectedGame directedGame) {
        super(directedGame);
        this.DEBUG_REBUILD_INTERVAL = 5.0f;
        this.debugEnabled = false;
    }

    public GameScreen(DirectedGame directedGame, int i) {
        super(directedGame);
        this.DEBUG_REBUILD_INTERVAL = 5.0f;
        this.debugEnabled = false;
        this.level = i;
        GamePreferences gamePreferences = GamePreferences.instance;
        this.prefs = gamePreferences;
        gamePreferences.load();
        this.showDialog = false;
    }

    @Override
    public void render(float f) {
        if (!this.paused) {
            this.worldController.update(f);
        }
        Gdx.gl.glClearColor(0.49019608f, 0.6313726f, 0.27058825f, 1.0f);
        Gdx.gl.glClear(16384);
        this.worldRenderer.render();
        if (this.debugEnabled) {
            float f2 = this.debugRebuildStage - f;
            this.debugRebuildStage = f2;
            if (f2 <= 0.0f) {
                this.debugRebuildStage = 5.0f;
                rebuildStage();
            }
        }
        this.stage.act(f);
        this.stage.draw();
        this.stage.setDebugAll(false);
        if (this.worldController.isRightAnswer && !this.showDialog) {
            buildRightAnswerDialog();
            this.showDialog = true;
        }
        if (this.worldController.isWrongAnswer && !this.showDialog) {
            buildWrongAnswerDialog();
            this.showDialog = true;
        }
        if (this.worldController.gameOverAccident && !this.showDialog) {
            buildAccidentDialog();
            this.showDialog = true;
        }
        if (!Gdx.input.isKeyJustPressed(4)) {
            return;
        }
        if (this.game.backpressed) {
            this.game.backpressed = false;
            return;
        }
        this.game.setScreen(new StartScreen(this.game));
        Gdx.app.log(TAG, "back btn pressed new StartScreen!");
        this.game.backpressed = true;
    }

    @Override
    public void resize(int i, int i2) {
        this.worldRenderer.resize(i, i2);
        this.stage.getViewport().update(i, i2, true);
    }

    @Override
    public void show() {
        this.worldController = new WorldController(this.game, this.level);
        this.worldRenderer = new WorldRenderer(this.worldController);
        Gdx.input.setCatchKey(4, true);
        this.stage = new Stage(new ExtendViewport(480.0f, 800.0f));
        this.multiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(this.multiplexer);
        this.multiplexer.addProcessor(this.stage);
        this.multiplexer.addProcessor(this.worldController);
        rebuildStage();
    }

    @Override
    public void hide() {
        this.worldController.dispose();
        this.worldRenderer.dispose();
        this.stage.dispose();
        this.skin.dispose();
        this.atlas.dispose();
    }

    @Override
    public void pause() {
        this.paused = true;
    }

    @Override
    public void resume() {
        this.paused = false;
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public InputProcessor getInputProcessor() {
        return this.multiplexer;
    }

    public void rebuildStage() {
        TextureAtlas textureAtlas = new TextureAtlas("images_ui/crossroad-game-ui.pack.atlas");
        this.atlas = textureAtlas;
        ObjectSet.ObjectSetIterator<Texture> it = textureAtlas.getTextures().iterator();
        while (it.hasNext()) {
            it.next().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        this.skin = new Skin(Gdx.files.internal("images_ui/crossroad-game-ui.json"), this.atlas);
        StringBuilder stringBuilder = new StringBuilder();
        this.levNumbStr = stringBuilder;
        StringBuilder append = stringBuilder.append(this.level);
        append.append(" из " + this.worldController.level.allLevelsCount);
        clearStage();
    }

    private void buildRightAnswerDialog() {
        AudioManager.instance.stopMusic();
        clearStage();
        Stack stack = new Stack();
        stack.setSize(480.0f, 800.0f);
        Table table = new Table();
        Image image = new Image(this.skin, "dialog_back");
        Table table2 = new Table();
        table2.add( new Image(this.skin, "right_answer_lbl")).padBottom(130.0f);
        Table table3 = new Table();
        table3.padTop(100.0f);
        Button button = new Button(this.skin, "info_btn");
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float f, float f2) {
                super.clicked(inputEvent, f, f2);
                GameScreen.this.onPlayClicked();
                GameScreen.this.buildRightAnswerDetailedDialog();
            }
        });
        table3.add(button).padRight(45.0f);
        Button button2 = new Button(this.skin, "replay_btn");
        button2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float f, float f2) {
                super.clicked(inputEvent, f, f2);
                GameScreen.this.onPlayClicked();
                GameScreen.this.restartLevel();
            }
        });
        table3.add(button2).padRight(45.0f);
        Button button3 = new Button(this.skin, "next_btn");
        button3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float f, float f2) {
                super.clicked(inputEvent, f, f2);
                GameScreen.this.onPlayClicked();
                GameScreen.this.prefs.rightAnswerCount++;
                GameScreen.this.printLog("prefs.rightAnswerCount = " + GameScreen.this.prefs.rightAnswerCount);
                GameScreen.this.prefs.save();
                GameScreen.this.startNextLvl();
            }
        });
        table3.add(button3);
        table.add( image);
        stack.add(table);
        stack.add(table2);
        stack.add(table3);
        stack.setFillParent(true);
        stack.pack();
        this.stage.addActor(stack);
    }


    public void startNextLvl() {
        int i = this.level + 1;
        this.level = i;
        if (i > this.worldController.level.allLevelsCount) {
            this.worldController.setResultsInPrefs();
            this.prefs.currentLvl = 1;
            this.prefs.saveCurrentLevel();
            buildGameOver();
            return;
        }
        this.prefs.currentLvl = this.level;
        this.prefs.saveCurrentLevel();
        this.prefs.save();
        this.game.setScreen(new GameScreen(this.game, this.prefs.currentLvl));

    }

    public void restartLevel() {
        clearStage();
        this.showDialog = false;
        WorldController.restartLevel = true;
    }

    private void buildWrongAnswerDialog() {
        AudioManager.instance.stopMusic();
        clearStage();
        Stack stack = new Stack();
        stack.setSize(480.0f, 800.0f);
        Table table = new Table();
        Image image = new Image(this.skin, "dialog_back");
        Table table2 = new Table();
        table2.add( new Image(this.skin, "wrong_answer_lbl")).padBottom(130.0f);
        Table table3 = new Table();
        table3.padTop(100.0f);
        Button button = new Button(this.skin, "info_btn");
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float f, float f2) {
                super.clicked(inputEvent, f, f2);
                GameScreen.this.onPlayClicked();
                GameScreen.this.buildRightAnswerDetailedDialog();
            }
        });
        table3.add(button).padRight(45.0f);
        Button button2 = new Button(this.skin, "replay_btn");
        button2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float f, float f2) {
                super.clicked(inputEvent, f, f2);
                GameScreen.this.onPlayClicked();
                GameScreen.this.restartLevel();
                GameScreen gameScreen = GameScreen.this;
                gameScreen.printLog("prefs.rightAnswerCount = " + GameScreen.this.prefs.rightAnswerCount);
            }
        });
        table3.add(button2).padRight(45.0f);
        Button button3 = new Button(this.skin, "next_btn");
        button3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float f, float f2) {
                super.clicked(inputEvent, f, f2);
                GameScreen.this.onPlayClicked();
                GameScreen.this.startNextLvl();
                GameScreen gameScreen = GameScreen.this;
                gameScreen.printLog("prefs.rightAnswerCount = " + GameScreen.this.prefs.rightAnswerCount);
            }
        });
        table3.add(button3);
        table.add( image);
        stack.add(table);
        stack.add(table2);
        stack.add(table3);
        stack.setFillParent(true);
        stack.pack();
        this.stage.addActor(stack);
    }

    private void buildGameOver() {
        clearStage();
        Table table = new Table();
        Stack stack = new Stack();
        stack.setSize(480.0f, 800.0f);
        stack.setFillParent(true);
        table.add( new Image(this.skin, "dialog_back"));
        Table table2 = new Table();
        Button button = new Button(this.skin, "close_btn");
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float f, float f2) {
                super.clicked(inputEvent, f, f2);
                GameScreen.this.onPlayClicked();
                GameScreen.this.game.setScreen(new StartScreen(GameScreen.this.game));
            }
        });
        table2.add(button).pad(0.0f, 400.0f, 230.0f, 0.0f);
        Table table3 = new Table();
        Label label = new Label(new StringBuilder("Вы ответили на все вопросы.\n\nПравильных ответов:\n").append(this.worldController.rightAnswersString).append(" %\nили: ").append(this.prefs.rightAnswerCount), this.skin);
        label.setWrap(true);
        table3.add( label).width(400.0f).padLeft(65.0f).padBottom(20.0f);
        stack.add(table);
        stack.add(table2);
        stack.add(table3);
        stack.pack();
        this.stage.addActor(stack);
    }


    public void buildHelpDialog() {
        clearStage();
        Table table = new Table();
        Stack stack = new Stack();
        stack.setSize(480.0f, 800.0f);
        stack.setFillParent(true);
        table.add( new Image(this.skin, "dialog_back"));
        Table table2 = new Table();
        Button button = new Button(this.skin, "close_btn");
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float f, float f2) {
                super.clicked(inputEvent, f, f2);
                GameScreen.this.onPlayClicked();
                GameScreen.this.clearStage();
                GameScreen.this.showDialog = false;
                GameScreen.this.rebuildStage();
            }
        });
        table2.add(button).pad(0.0f, 400.0f, 230.0f, 0.0f);
        Table table3 = new Table();
        Label label = new Label(Assets.instance.help_info, this.skin);
        label.setWrap(true);
        table3.add( label).width(400.0f).padLeft(85.0f).padBottom(20.0f);
        stack.add(table);
        stack.add(table2);
        stack.add(table3);
        stack.pack();
        this.stage.addActor(stack);
    }

    public void buildExitDialog() {
        clearStage();
        Table table = new Table();
        Stack stack = new Stack();
        stack.setSize(480.0f, 800.0f);
        stack.setFillParent(true);
        table.add( new Image(this.skin, "dialog_back"));
        Table table2 = new Table();
        Button button = new Button(this.skin, "close_btn");
        Button button2 = new Button(this.skin, "next_btn");
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float f, float f2) {
                super.clicked(inputEvent, f, f2);
                GameScreen.this.onPlayClicked();
                GameScreen.this.clearStage();
                GameScreen.this.showDialog = false;
                GameScreen.this.rebuildStage();
            }
        });
        button2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float f, float f2) {
                super.clicked(inputEvent, f, f2);
                GameScreen.this.onPlayClicked();
                GameScreen.this.game.setScreen(new StartScreen(GameScreen.this.game));
            }
        });
        table2.add(button).pad(0.0f, 430.0f, 220.0f, 0.0f);
        table2.add(button2).pad(0.0f, -450.0f, -150.0f, 0.0f);
        Table table3 = new Table();
        Label label = new Label("Вы точно хотите выйти в главное меню?", this.skin);
        label.setWrap(true);
        table3.add(label).width(400.0f).padLeft(85.0f).padBottom(20.0f);
        stack.add(table);
        stack.add(table2);
        stack.add(table3);
        stack.pack();
        this.stage.addActor(stack);
    }


    public void buildRightAnswerDetailedDialog() {
        clearStage();
        Table table = new Table();
        Stack stack = new Stack();
        stack.setSize(480.0f, 800.0f);
        stack.setFillParent(true);
        table.add( new Image(this.skin, "dialog_back"));
        Table table2 = new Table();
        Button button = new Button(this.skin, "close_btn");
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float f, float f2) {
                super.clicked(inputEvent, f, f2);
                GameScreen.this.onPlayClicked();
                GameScreen.this.clearStage();
                GameScreen.this.showDialog = false;
                GameScreen.this.rebuildStage();
            }
        });
        table2.add(button).pad(0.0f, 400.0f, 230.0f, 0.0f);
        Table table3 = new Table();
        StringBuilder info = this.worldController.level.gameLevels.getRightAnswerDescr();
        Label label = new Label(info, this.skin);
        label.setWrap(true);
        table3.add(label).width(300.0f).padLeft(85.0f).padBottom(20.0f);
        stack.add(table);
        stack.add(table2);
        stack.add(table3);
        stack.pack();
        this.stage.addActor(stack);


    }

    private void buildAccidentDialog() {
        AudioManager.instance.stopMusic();
        clearStage();
        Stack stack = new Stack();
        stack.setSize(480.0f, 800.0f);
        Table table = new Table();
        Image image = new Image(this.skin, "dialog_back");
        Table table2 = new Table();
        table2.add( new Image(this.skin, "accident_lbl")).padBottom(130.0f);
        Table table3 = new Table();
        table3.padTop(100.0f);
        Button button = new Button(this.skin, "replay_btn");
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float f, float f2) {
                super.clicked(inputEvent, f, f2);
                GameScreen.this.onPlayClicked();
                GameScreen.this.game.setScreen(new GameScreen(GameScreen.this.game, GameScreen.this.level));
            }
        });
        table3.add(button);
        table.add( image);
        stack.add(table);
        stack.add(table2);
        stack.add(table3);
        stack.setFillParent(true);
        stack.pack();
        this.stage.addActor(stack);
    }

    private void buildGameProgress() {
        new Table().top();
        Table table = new Table();
        table.add(new Image(this.skin, "answer_numb_back")).padBottom(760.0f);
        Table table2 = new Table();
        table2.add( new Label(this.levNumbStr, this.skin)).padBottom(760.0f);
        Stack stack = new Stack();
        stack.setSize(480.0f, 800.0f);
        stack.setFillParent(true);
        stack.addActor(table);
        stack.addActor(table2);
        this.stage.addActor(stack);
    }

    private void buildHelpBtn() {
        Stack stack = new Stack();
        stack.setSize(480.0f, 800.0f);
        stack.setFillParent(true);
        Table table = new Table();
        Button button = new Button(this.skin, "help_btn");
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float f, float f2) {
                super.clicked(inputEvent, f, f2);
                GameScreen.this.onPlayClicked();
                GameScreen.this.buildHelpDialog();
            }
        });
        table.add(button).padBottom(720.0f).padRight(400.0f);
        stack.addActor(table);
        this.stage.addActor(stack);
    }

    private void buildExitBtn() {
        Stack stack = new Stack();
        stack.setSize(480.0f, 800.0f);
        stack.setFillParent(true);
        Table table = new Table();
        Button button = new Button(this.skin, "info_btn");
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float f, float f2) {
                super.clicked(inputEvent, f, f2);
                GameScreen.this.onPlayClicked();
                GameScreen.this.buildExitDialog();
            }
        });
        table.add(button).padBottom(720.0f).padLeft(400.0f);
        stack.addActor(table);
        this.stage.addActor(stack);
    }


    public void clearStage() {
        this.stage.clear();
        buildGameProgress();
        buildHelpBtn();
        buildExitBtn();
    }


    public void onPlayClicked() {
        AudioManager.instance.play(Assets.instance.sounds.click);
    }


    public void printLog(String str) {
        Gdx.app.log(TAG, str);
    }
}
