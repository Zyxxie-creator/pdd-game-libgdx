package com.pddgame.game.game.steer;

import com.badlogic.gdx.math.Vector2;

/**
 *
 * Расчет прочих векторов для управления
 *
 */

public final class Box2dSteeringUtils {
    private Box2dSteeringUtils() {
    }

    public static float vectorToAngle(Vector2 vector2) {
        return (float) Math.atan2(-vector2.x, vector2.y);
    }

    public static Vector2 angleToVector(Vector2 vector2, float f) {
        double d = f;
        vector2.x = -((float) Math.sin(d));
        vector2.y = (float) Math.cos(d);
        return vector2;
    }
}
