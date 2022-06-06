package com.pddgame.game.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.pddgame.game.game.Assets;


/**
 *
 * Абстрактный класс игрового окна
 *
 */
public abstract class AbstractGameScreen implements Screen {
    protected DirectedGame game;

    public abstract InputProcessor getInputProcessor();

    @Override
    public abstract void hide();

    @Override
    public abstract void pause();

    @Override
    public abstract void render(float f);

    @Override
    public abstract void resize(int i, int i2);

    @Override
    public abstract void show();

    public AbstractGameScreen(DirectedGame directedGame) {
        this.game = directedGame;
    }

    @Override
    public void resume() {
        Assets.instance.init(new AssetManager());
    }

    @Override
    public void dispose() {
        Gdx.app.log("AbstractGameScreen", "dispose");
        Assets.instance.dispose();
    }
}
