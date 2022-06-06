package com.pddgame.game.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Движение абстрактных объектов
 * Назначение анимации
 */
public abstract class AbstractGameObject {
    public Animation animation;
    public Body body;
    public float stateTime;
    public Vector2 position = new Vector2();
    public Vector2 dimension = new Vector2(1.0f, 1.0f);
    public Vector2 origin = new Vector2();
    public Vector2 scale = new Vector2(1.0f, 1.0f);
    public float rotation = 0.0f;
    public Vector2 velocity = new Vector2();
    public Vector2 terminalVelocity = new Vector2(1.0f, 1.0f);
    public Vector2 friction = new Vector2();
    public Vector2 acceleration = new Vector2();
    public Rectangle bounds = new Rectangle();
    public Circle circle = new Circle();

    public abstract void drawDebug(ShapeRenderer shapeRenderer);

    public abstract void render(SpriteBatch spriteBatch);

    public void update(float f) {
        this.stateTime += f;
        Body body = this.body;
        if (body == null) {
            updateMotionX(f);
            updateMotionY(f);
            this.position.x += this.velocity.x * f;
            this.position.y += this.velocity.y * f;
            return;
        }
        this.position.set(body.getPosition());
        this.rotation = this.body.getAngle() * 57.295776f;
    }

    protected void updateMotionX(float f) {
        if (this.velocity.x != 0.0f) {
            if (this.velocity.x > 0.0f) {
                Vector2 vector2 = this.velocity;
                vector2.x = Math.max(vector2.x - (this.friction.x * f), 0.0f);
            } else {
                Vector2 vector22 = this.velocity;
                vector22.x = Math.min(vector22.x + (this.friction.x * f), 0.0f);
            }
        }
        this.velocity.x += this.acceleration.x * f;
        Vector2 vector23 = this.velocity;
        vector23.x = MathUtils.clamp(vector23.x, -this.terminalVelocity.x, this.terminalVelocity.x);
    }

    protected void updateMotionY(float f) {
        if (this.velocity.y != 0.0f) {
            if (this.velocity.y > 0.0f) {
                Vector2 vector2 = this.velocity;
                vector2.y = Math.max(vector2.y - (this.friction.y * f), 0.0f);
            } else {
                Vector2 vector22 = this.velocity;
                vector22.y = Math.min(vector22.y + (this.friction.y * f), 0.0f);
            }
        }
        this.velocity.y += this.acceleration.y * f;
        Vector2 vector23 = this.velocity;
        vector23.y = MathUtils.clamp(vector23.y, -this.terminalVelocity.y, this.terminalVelocity.y);
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
        this.stateTime = 0.0f;
    }
}
