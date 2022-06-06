package com.pddgame.game.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.pddgame.game.game.objects.AbstractGameObject;

/**
 *
 * Выравнивание камеры
 * Адаптация камеры под разные экраны
 * Зум камеры
 * Перемещение камеры
 *
 */
public class CameraHelper {
    private static final String TAG = CameraHelper.class.getName();
    private AbstractGameObject target;
    private final float MAX_ZOOM_IN = 0.25f;
    private final float MAX_ZOOM_OUT = 100.0f;
    private final float FOLLOW_SPEED = 4.0f;
    private Vector2 position = new Vector2();
    private float zoom = 1.0f;

    public void update(float f) {
        if (hasTarget()) {
            this.position.lerp(this.target.position, f * 4.0f);
            Vector2 vector2 = this.position;
            vector2.y = Math.max(-1.0f, vector2.y);
        }
    }

    public void setPosition(float f, float f2) {
        this.position.set(f, f2);
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public void addZoom(float f) {
        setZoom(this.zoom + f);
    }

    public void setZoom(float f) {
        this.zoom = MathUtils.clamp(f, 0.25f, 100.0f);
    }

    public float getZoom() {
        return this.zoom;
    }

    public void setTarget(AbstractGameObject abstractGameObject) {
        this.target = abstractGameObject;
    }

    public AbstractGameObject getTarget() {
        return this.target;
    }

    public boolean hasTarget() {
        return this.target != null;
    }

    public boolean hasTarget(AbstractGameObject abstractGameObject) {
        return hasTarget() && this.target.equals(abstractGameObject);
    }

    public void applyTo(OrthographicCamera orthographicCamera) {
        orthographicCamera.position.x = this.position.x;
        orthographicCamera.position.y = this.position.y;
        orthographicCamera.zoom = this.zoom;
        orthographicCamera.update();
    }
}
