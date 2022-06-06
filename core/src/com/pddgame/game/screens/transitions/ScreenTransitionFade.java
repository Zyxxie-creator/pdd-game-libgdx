package com.pddgame.game.screens.transitions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;

/**
 *
 * Анимация перехода исчезанием
 *
 */
public class ScreenTransitionFade implements ScreenTransition {
    private static final ScreenTransitionFade instance = new ScreenTransitionFade();
    private float duration;

    public static ScreenTransitionFade init(float f) {
        ScreenTransitionFade screenTransitionFade = instance;
        screenTransitionFade.duration = f;
        return screenTransitionFade;
    }

    @Override
    public float getDuration() {
        return this.duration;
    }

    @Override
    public void render(SpriteBatch spriteBatch, Texture texture, Texture texture2, float f) {
        float width = texture.getWidth();
        float height = texture.getHeight();
        float apply = Interpolation.fade.apply(f);
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(16384);
        spriteBatch.begin();
        spriteBatch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        spriteBatch.draw(texture, 0.0f, 0.0f, 0.0f, 0.0f, width, height, 1.0f, 1.0f, 0.0f, 0, 0, texture.getWidth(), texture.getHeight(), false, true);
        spriteBatch.setColor(1.0f, 1.0f, 1.0f, apply);
        spriteBatch.draw(texture2, 0.0f, 0.0f, 0.0f, 0.0f, width, height, 1.0f, 1.0f, 0.0f, 0, 0, texture2.getWidth(), texture2.getHeight(), false, true);
        spriteBatch.end();
    }
}
