package com.pddgame.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.pddgame.game.game.Assets;
import com.pddgame.game.utils.AudioManager;
import com.pddgame.game.utils.GamePreferences;

/**
 *
 * Экран ввода данных и обработки их
 *
 */
public class InterstScreen extends AbstractGameScreen {
    public static boolean buildNextBtn;
    private TextureAtlas atlas;
    public boolean interstIsLoaded;
    private Image loadImg;
    private Skin skin;
    private Stack stack;
    private Stage stage;
    private float rotTime = 0.1f;
    private float loadingDelay = 1.0f;
    private boolean isBuildNextBtn = false;
    private float rotAngle = 1.0f;
    private GamePreferences prefs = GamePreferences.instance;

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    public InterstScreen(DirectedGame directedGame) {
        super(directedGame);
        directedGame.backpressed = false;
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(16384);
        float f2 = this.rotTime - f;
        this.rotTime = f2;
        if (f2 <= 0.0f) {
            Image image = this.loadImg;
            float f3 = this.rotAngle - 20.0f;
            this.rotAngle = f3;
            image.setRotation(f3);
            this.rotTime = 0.1f;
        }
        float f4 = this.loadingDelay - f;
        this.loadingDelay = f4;
        if (f4 < 0.0f && this.interstIsLoaded) {
            this.loadingDelay = 100000.0f;
        }
        if (buildNextBtn) {
            this.loadingDelay = 100000.0f;
            this.stage.clear();
            this.stage.addActor(buildTouchScreenBtn());
            buildNextBtn = false;
        }
        if (this.isBuildNextBtn && Gdx.input.isKeyJustPressed(4) && !this.game.backpressed) {
            this.game.backpressed = true;
            this.game.setScreen(new GameScreen(this.game, this.prefs.currentLvl));
        }
        this.stage.act(f);
        this.stage.draw();
        this.stage.setDebugAll(false);
    }

    @Override
    public void resize(int i, int i2) {
        this.stage.getViewport().update(i, i2, true);
    }

    @Override
    public void show() {
        this.stage = new Stage(new FitViewport(800.0f, 480.0f));
        Gdx.input.setCatchKey(4, true);
        buildNextBtn = true;
        rebuildStage();
    }

    @Override
    public void hide() {
        this.stage.dispose();
        this.skin.dispose();
        this.atlas.dispose();
        this.prefs.save();
    }

    @Override
    public InputProcessor getInputProcessor() {
        return this.stage;
    }

    public void rebuildStage() {
        TextureAtlas textureAtlas = new TextureAtlas("images/crossroad-loading.pack.atlas");
        this.atlas = textureAtlas;
        ObjectSet.ObjectSetIterator<Texture> it = textureAtlas.getTextures().iterator();
        while (it.hasNext()) {
            it.next().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        this.skin = new Skin(Gdx.files.internal("images/crossroad-loading.json"), this.atlas);
        this.stage.clear();
        Stack stack = new Stack();
        this.stack = stack;
        stack.setSize(800.0f, 480.0f);
        this.stack.add(buildLoadingTable());
        this.stack.setFillParent(true);
        this.stack.pack();
        this.stage.addActor(this.stack);
    }

    private Table buildLoadingTable() {
        Table table = new Table();
        Image image = new Image(this.skin, "loding_circle");
        this.loadImg = image;
        image.setOrigin(image.getWidth() * 0.5f, this.loadImg.getHeight() * 0.5f);
        table.add(this.loadImg);
        return table;
    }

    private Stack buildTouchScreenBtn() {
        this.isBuildNextBtn = true;
        Stack stack = new Stack();
        stack.setSize(480.0f, 800.0f);
        stack.setFillParent(true);
        Table table = new Table();
        table.setFillParent(true);
        table.columnDefaults(0).width(480.0f).height(800.0f);
        final ImageTextButton imageTextButton = new ImageTextButton(Assets.instance.touch_screen, this.skin, "touch_screen");
        imageTextButton.getLabel().setFontScale(2.0f, 2.0f);
        table.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float f, float f2) {
                AudioManager.instance.play(Assets.instance.sounds.click);
                imageTextButton.setVisible(false);
                InterstScreen.this.game.setScreen(new GameScreen(InterstScreen.this.game, InterstScreen.this.prefs.currentLvl));
            }
        });
        table.add(imageTextButton).expandX();
        stack.add(table);
        stack.pack();
        return stack;
    }
}
