package com.pddgame.game.screens.transitions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;


/**
 *
 * Анимация перехода слайдом
 *
 */
public class ScreenTransitionSlide implements ScreenTransition {
    public static final int DOWN = 4;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int UP = 3;
    private static final ScreenTransitionSlide instance = new ScreenTransitionSlide();
    private int direction;
    private float duration;
    private Interpolation easing;
    private boolean slideOut;

    public static ScreenTransitionSlide init(float f, int i, boolean z, Interpolation interpolation) {
        instance.duration = f;
        instance.direction = i;
        instance.slideOut = z;
        instance.easing = interpolation;
        return instance;
    }

    @Override
    public float getDuration() {
        return this.duration;
    }

    @Override
    public void render(SpriteBatch spriteBatch, Texture texture, Texture texture2, float f) {
        float f2;
        float f3;
        float width = texture.getWidth();
        float height = texture.getHeight();
        float apply = this.easing != null ? this.easing.apply(f) : f;
        switch (this.direction) {
            case 1:
                f3 = (-width) * apply;
                if (!this.slideOut) {
                    f3 += width;
                }
                f2 = 0.0f;
                break;
            case 2:
                f3 = apply * width;
                if (!this.slideOut) {
                    f3 -= width;
                }
                f2 = 0.0f;
                break;
            case 3:
                f2 = apply * height;
                if (!this.slideOut) {
                    f2 -= height;
                }
                f3 = 0.0f;
                break;
            case 4:
                f2 = (-height) * apply;
                if (!this.slideOut) {
                    f2 += height;
                }
                f3 = 0.0f;
                break;
            default:
                f3 = 0.0f;
                f2 = 0.0f;
                break;
        }
        Texture texture3 = this.slideOut ? texture2 : texture;
        Texture texture4 = this.slideOut ? texture : texture2;
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(16384);
        spriteBatch.begin();
        spriteBatch.draw(texture3, 0.0f, 0.0f, 0.0f, 0.0f, width, height, 1.0f, 1.0f, 0.0f, 0, 0, texture.getWidth(), texture.getHeight(), false, true);
        spriteBatch.draw(texture4, f3, f2, 0.0f, 0.0f, width, height, 1.0f, 1.0f, 0.0f, 0, 0, texture2.getWidth(), texture2.getHeight(), false, true);
        spriteBatch.end();
    }

}
