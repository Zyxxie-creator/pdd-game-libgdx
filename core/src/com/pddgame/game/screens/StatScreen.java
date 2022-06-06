package com.pddgame.game.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.pddgame.game.game.Assets;
import com.pddgame.game.utils.AudioManager;
import com.pddgame.game.utils.GamePreferences;

/**
 *
 * Экран статистики
 * Назначение текстур
 * Установка кнопок
 * Отрисовка экрана
 *
 */
public class StatScreen extends AbstractGameScreen {
    private TextureAtlas atlas;
    private float debugRebuildStage;
    private Skin skin;
    private Stack stack;
    private Stage stage;
    private final float DEBUG_REBUILD_INTERVAL = 5.0f;
    private boolean debugEnabled = false;
    private String TAG = StartScreen.class.getName();
    private GamePreferences prefs = GamePreferences.instance;

    @Override
    public void pause() {
    }

    public StatScreen(DirectedGame directedGame) {
        super(directedGame);
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
        if (Gdx.input.isKeyPressed(4) || Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            this.game.setScreen(new StartScreen(this.game));
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
        this.stage = new Stage(new FitViewport(480.0f, 800.0f));
        Gdx.input.setCatchKey(4, true);
        rebuildStage();
    }

    @Override
    public void hide() {
        this.stage.dispose();
        this.skin.dispose();
        this.atlas.dispose();
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
        Table buildStatisticLayer = buildStatisticLayer();
        Table buildRowBackLayer = buildRowBackLayer();
        Table buildPlayControlsLayer = buildPlayControlsLayer();
        this.stage.clear();
        Stack stack = new Stack();
        this.stack = stack;
        stack.setSize(480.0f, 800.0f);
        this.stack.add(buildLogo);
        this.stack.add(buildRowBackLayer);
        this.stack.add(buildStatisticLayer);
        this.stack.add(buildPlayControlsLayer);
        this.stack.pack();
        this.stage.addActor(this.stack);

    }

    private Table buildLogo() {
        Table table = new Table();
        table.add( new Image(this.skin, "stat_logo")).padBottom(690.0f);
        return table;
    }

    private Table buildStatisticLayer() {
        Table table = new Table();
        table.bottom();
        Table table2 = new Table();
        Label label = new Label("1.", this.skin);
        Label label2 = new Label(this.prefs.date_1, this.skin);
        Label label3 = new Label(String.valueOf(this.prefs.result_1) + " %", this.skin);
        table2.add( label);
        table2.add( label2).padLeft(20.0f);
        table2.add( label3).padLeft(80.0f);
        Table table3 = new Table();
        Label label4 = new Label("2.", this.skin);
        Label label5 = new Label(this.prefs.date_2, this.skin);
        Label label6 = new Label(String.valueOf(this.prefs.result_2) + " %", this.skin);
        table3.add( label4);
        table3.add( label5).padLeft(20.0f);
        table3.add(label6).padLeft(80.0f);
        Table table4 = new Table();
        Label label7 = new Label("3.", this.skin);
        Label label8 = new Label(this.prefs.date_3, this.skin);
        Label label9 = new Label(String.valueOf(this.prefs.result_3) + " %", this.skin);
        table4.add( label7);
        table4.add( label8).padLeft(20.0f);
        table4.add( label9).padLeft(80.0f);
        Table table5 = new Table();
        Label label10 = new Label("4.", this.skin);
        Label label11 = new Label(this.prefs.date_4, this.skin);
        Label label12 = new Label(String.valueOf(this.prefs.result_4) + " %", this.skin);
        table5.add( label10);
        table5.add( label11).padLeft(20.0f);
        table5.add( label12).padLeft(80.0f);
        Table table6 = new Table();
        Label label13 = new Label("5.", this.skin);
        Label label14 = new Label(this.prefs.date_5, this.skin);
        Label label15 = new Label(String.valueOf(this.prefs.result_5) + " %", this.skin);
        table6.add( label13);
        table6.add( label14).padLeft(20.0f);
        table6.add(label15).padLeft(80.0f);
        Table table7 = new Table();
        Label label16 = new Label("6.", this.skin);
        Label label17 = new Label(this.prefs.date_6, this.skin);
        Label label18 = new Label(String.valueOf(this.prefs.result_6) + " %", this.skin);
        table7.add(label16);
        table7.add(label17).padLeft(20.0f);
        table7.add(label18).padLeft(80.0f);
        Table table8 = new Table();
        Label label19 = new Label("7.", this.skin);
        Label label20 = new Label(this.prefs.date_7, this.skin);
        Label label21 = new Label(String.valueOf(this.prefs.result_7) + " %", this.skin);
        table8.add(label19);
        table8.add( label20).padLeft(20.0f);
        table8.add( label21).padLeft(80.0f);
        table.add(table2).pad(0.0f, 30.0f, 27.0f, 0.0f).left().expandX().row();
        table.add(table3).pad(0.0f, 30.0f, 27.0f, 0.0f).left().expandX().row();
        table.add(table4).pad(0.0f, 30.0f, 27.0f, 0.0f).left().expandX().row();
        table.add(table5).pad(0.0f, 30.0f, 27.0f, 0.0f).left().expandX().row();
        table.add(table6).pad(0.0f, 30.0f, 27.0f, 0.0f).left().expandX().row();
        table.add(table7).pad(0.0f, 30.0f, 27.0f, 0.0f).left().expandX().row();
        table.add(table8).pad(0.0f, 30.0f, 212.0f, 0.0f).left().expandX().row();
        return table;
    }

    private Table buildRowBackLayer() {
        Table table = new Table();
        table.add( new Image(this.skin, "row_back")).padBottom(20.0f).row();
        table.add( new Image(this.skin, "row_back")).padBottom(20.0f).row();
        table.add( new Image(this.skin, "row_back")).padBottom(20.0f).row();
        table.add( new Image(this.skin, "row_back")).padBottom(20.0f).row();
        table.add( new Image(this.skin, "row_back")).padBottom(20.0f).row();
        table.add(new Image(this.skin, "row_back")).padBottom(20.0f).row();
        table.add(new Image(this.skin, "row_back")).padBottom(20.0f).row();
        return table;
    }

    private Table buildPlayControlsLayer() {
        Table table = new Table();
        Button button = new Button(this.skin, "close_btn");
        button.addListener(new ClickListener() { // from class: com.va.crossroad.screens.StatScreen.1
            @Override
            public void clicked(InputEvent inputEvent, float f, float f2) {
                super.clicked(inputEvent, f, f2);
                AudioManager.instance.play(Assets.instance.sounds.click);
                StatScreen.this.game.setScreen(new StartScreen(StatScreen.this.game));
            }
        });
        table.add(button).padTop(700.0f);
        return table;
    }
}
