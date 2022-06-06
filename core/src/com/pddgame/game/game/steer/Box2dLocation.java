package com.pddgame.game.game.steer;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;


/**
 *
 * Расположение 2д боксов на координатах
 *
 */
public class Box2dLocation implements Location<Vector2> {
    Vector2 position = new Vector2();
    float orientation = 0.0f;


    @Override
    public Vector2 getPosition() {
        return this.position;
    }

    @Override
    public float getOrientation() {
        return this.orientation;
    }

    @Override
    public void setOrientation(float f) {
        this.orientation = f;
    }

    @Override
    public Location<Vector2> newLocation() {
        return new Box2dLocation();
    }

    public float vectorToAngle(Vector2 vector2) {
        return Box2dSteeringUtils.vectorToAngle(vector2);
    }

    public Vector2 angleToVector(Vector2 vector2, float f) {
        return Box2dSteeringUtils.angleToVector(vector2, f);
    }
}
