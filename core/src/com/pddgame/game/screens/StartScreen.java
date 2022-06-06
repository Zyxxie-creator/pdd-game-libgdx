package com.pddgame.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.pddgame.game.game.Assets;
import com.pddgame.game.screens.transitions.ScreenTransitionFade;
import com.pddgame.game.utils.AudioManager;
import com.pddgame.game.utils.GamePreferences;

/**
 *
 * Главный экран
 * Назначение кнопок
 * Переходы по нажатию на кнопки
 * Отрисовка экрана
 *
 */

public class StartScreen extends AbstractGameScreen implements StartScreenUpdateInterface {
    public static StartScreenUpdateInterface ssui;
    private TextureAtlas atlas;
    private int currentLvl;
    private float debugRebuildStage;
    private GamePreferences prefs;
    private Skin skin;
    private Stack stack;
    private Stage stage;
    private final float DEBUG_REBUILD_INTERVAL = 5.0f;
    private boolean debugEnabled = false;
    private String TAG = StartScreen.class.getName();

    @Override
    public void resume() {
    }
    public void show() {
        this.stage = new Stage(new FitViewport(480.0f, 800.0f));
        Gdx.input.setCatchKey(4, false);
        rebuildStage();
    }
    public StartScreen(DirectedGame directedGame) {
        super(directedGame);
        ssui = this;
        GamePreferences gamePreferences = GamePreferences.instance;
        this.prefs = gamePreferences;
        this.currentLvl = gamePreferences.currentLvl;
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(0.941f, 0.945f, 0.851f, 1.0f);
        Gdx.gl.glClear(16384);
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
    }

    @Override
    public void resize(int i, int i2) {
        this.stage.getViewport().update(i, i2, true);
        Gdx.app.log(this.TAG, "resize()");
    }

    @Override
    public void hide() {
        this.stage.dispose();
        this.skin.dispose();
        this.atlas.dispose();
    }

    @Override
    public void pause() {
        Gdx.app.log(this.TAG, "pause()");
    }

    @Override
    public InputProcessor getInputProcessor() {
        return this.stage;
    }

    public void rebuildStage() {
        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("images_ui/crossroad-ui.pack.atlas"));
        this.atlas = textureAtlas;
        ObjectSet.ObjectSetIterator<Texture> it = textureAtlas.getTextures().iterator();
        while (it.hasNext()) {
            it.next().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        this.skin = new Skin(Gdx.files.internal("images_ui/crossroad-ui.json"), this.atlas);
        Table buildLogo = buildLogo();
        Table buildPlayControlsLayer = buildPlayControlsLayer();
        this.stage.clear();
        Stack stack = new Stack();
        this.stack = stack;
        stack.setSize(480.0f, 800.0f);
        this.stack.add(buildLogo);
        this.stack.add(buildPlayControlsLayer);
        this.stack.pack();
        this.stage.addActor(this.stack);
    }

    private Table buildLogo() {
        Table table = new Table();
        table.add(new Image(this.skin, "logo")).padBottom(498.0f);
        return table;
    }

    private Table buildPlayControlsLayer() {
        Table table = new Table();
        table.top().padTop(330.0f);
        Button button = new Button(this.skin, "play");
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float f, float f2) {
                super.clicked(inputEvent, f, f2);
                AudioManager.instance.play(Assets.instance.sounds.click);
                StartScreen.this.game.setScreen(new GameScreen(StartScreen.this.game, StartScreen.this.currentLvl), ScreenTransitionFade.init(0.2f));
            }
        });
        table.add(button).padBottom(20.0f);
        Button button2 = new Button(this.skin, "stat");
        button2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float f, float f2) {
                super.clicked(inputEvent, f, f2);
                AudioManager.instance.play(Assets.instance.sounds.click);
                StartScreen.this.game.setScreen(new StatScreen(StartScreen.this.game));
            }
        });
        table.add(button2).padBottom(20.0f).row();

        return table;
    }

    @Override
    public void updatePurchaseState() {
        Table buildLogo = buildLogo();
        Table buildPlayControlsLayer = buildPlayControlsLayer();
        this.stage.clear();
        Stack stack = new Stack();
        this.stack = stack;
        stack.setSize(480.0f, 800.0f);
        this.stack.add(buildLogo);
        this.stack.add(buildPlayControlsLayer);
        this.stack.pack();
        this.stage.addActor(this.stack);
    }
}
