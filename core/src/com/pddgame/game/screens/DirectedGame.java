package com.pddgame.game.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.pddgame.game.screens.transitions.ScreenTransition;

/**
 *
 * Абстрактный класс адаптации размера экрана
 *
 */
public abstract class DirectedGame implements ApplicationListener {
    public boolean backpressed = false;
    private SpriteBatch batch;
    private FrameBuffer currFbo;
    private AbstractGameScreen currScreen;
    private boolean init;
    private FrameBuffer nextFbo;
    private AbstractGameScreen nextScreen;
    private ScreenTransition screenTransition;
    private float t;

    public void setScreen(AbstractGameScreen abstractGameScreen) {
        setScreen(abstractGameScreen, null);
    }

    public void setScreen(AbstractGameScreen abstractGameScreen, ScreenTransition screenTransition) {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        if (!this.init) {
            this.currFbo = new FrameBuffer(Pixmap.Format.RGB888, width, height, false);
            this.nextFbo = new FrameBuffer(Pixmap.Format.RGB888, width, height, false);
            this.batch = new SpriteBatch();
            this.init = true;
        }
        this.nextScreen = abstractGameScreen;
        abstractGameScreen.show();
        this.nextScreen.resize(width, height);
        this.nextScreen.render(0.0f);
        AbstractGameScreen abstractGameScreen2 = this.currScreen;
        if (abstractGameScreen2 != null) {
            abstractGameScreen2.pause();
        }
        this.nextScreen.pause();
        Gdx.input.setInputProcessor(null);
        this.screenTransition = screenTransition;
        this.t = 0.0f;
    }

    @Override
    public void render() {
        float min = Math.min(Gdx.graphics.getDeltaTime(), 0.016666668f);
        if (this.nextScreen == null) {
            AbstractGameScreen abstractGameScreen = this.currScreen;
            if (abstractGameScreen != null) {
                abstractGameScreen.render(min);
                return;
            }
            return;
        }
        float f = 0.0f;
        ScreenTransition screenTransition = this.screenTransition;
        if (screenTransition != null) {
            f = screenTransition.getDuration();
        }
        float min2 = Math.min(this.t + min, f);
        this.t = min2;
        if (this.screenTransition == null || min2 >= f) {
            AbstractGameScreen abstractGameScreen2 = this.currScreen;
            if (abstractGameScreen2 != null) {
                abstractGameScreen2.hide();
            }
            this.nextScreen.resume();
            Gdx.input.setInputProcessor(this.nextScreen.getInputProcessor());
            this.currScreen = this.nextScreen;
            this.nextScreen = null;
            this.screenTransition = null;
            return;
        }
        this.currFbo.begin();
        AbstractGameScreen abstractGameScreen3 = this.currScreen;
        if (abstractGameScreen3 != null) {
            abstractGameScreen3.render(min);
        }
        this.currFbo.end();
        this.nextFbo.begin();
        this.nextScreen.render(min);
        this.nextFbo.end();
        this.screenTransition.render(this.batch, this.currFbo.getColorBufferTexture(), this.nextFbo.getColorBufferTexture(), this.t / f);
    }

    @Override
    public void resize(int i, int i2) {
        AbstractGameScreen abstractGameScreen = this.currScreen;
        if (abstractGameScreen != null) {
            abstractGameScreen.resize(i, i2);
        }
        AbstractGameScreen abstractGameScreen2 = this.nextScreen;
        if (abstractGameScreen2 != null) {
            abstractGameScreen2.resize(i, i2);
        }
    }

    @Override
    public void pause() {
        AbstractGameScreen abstractGameScreen = this.currScreen;
        if (abstractGameScreen != null) {
            abstractGameScreen.pause();
        }
    }

    @Override
    public void resume() {
        AbstractGameScreen abstractGameScreen = this.currScreen;
        if (abstractGameScreen != null) {
            abstractGameScreen.resume();
        }
    }

    @Override
    public void dispose() {
        AbstractGameScreen abstractGameScreen = this.currScreen;
        if (abstractGameScreen != null) {
            abstractGameScreen.hide();
        }
        AbstractGameScreen abstractGameScreen2 = this.nextScreen;
        if (abstractGameScreen2 != null) {
            abstractGameScreen2.hide();
        }
        if (this.init) {
            this.currFbo.dispose();
            this.currScreen = null;
            this.nextFbo.dispose();
            this.nextScreen = null;
            this.batch.dispose();
            this.init = false;
        }
    }
}
