package com.pddgame.game.game.steer;

import com.badlogic.gdx.ai.utils.Collision;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.ai.utils.RaycastCollisionDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * Отслеживание столкновений 2д боксов
 *
 */
public class Box2dRaycastCollisionDetector implements RaycastCollisionDetector<Vector2> {
    Box2dRaycastCallback callback;
    World world;

    public Box2dRaycastCollisionDetector(World world) {
        this(world, new Box2dRaycastCallback());
    }

    public Box2dRaycastCollisionDetector(World world, Box2dRaycastCallback box2dRaycastCallback) {
        this.world = world;
        this.callback = box2dRaycastCallback;
    }

    @Override
    public boolean collides(Ray<Vector2> ray) {
        return findCollision(null, ray);
    }

    @Override
    public boolean findCollision(Collision<Vector2> collision, Ray<Vector2> ray) {
        this.callback.collided = false;
        if (!ray.start.epsilonEquals(ray.end, 1.0E-6f)) {
            this.callback.outputCollision = collision;
            this.world.rayCast(this.callback, ray.start, ray.end);
        }
        return this.callback.collided;
    }


    public static class Box2dRaycastCallback implements RayCastCallback {
        public boolean collided;
        public Collision<Vector2> outputCollision;

        @Override
        public float reportRayFixture(Fixture fixture, Vector2 vector2, Vector2 vector22, float f) {
            Collision<Vector2> collision = this.outputCollision;
            if (collision != null) {
                collision.set(vector2, vector22);
            }
            this.collided = true;
            return f;
        }
    }
}
