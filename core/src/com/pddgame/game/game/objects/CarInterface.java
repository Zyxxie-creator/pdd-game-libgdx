package com.pddgame.game.game.objects;

import com.badlogic.gdx.ai.steer.behaviors.FollowPath;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Интерфейс авто
 *
 */
public interface CarInterface {
    void drawDebug(ShapeRenderer shapeRenderer);

    FollowPath<Vector2, LinePath.LinePathParam> getFollowPathSB();

    LinePath<Vector2> getLinePath();

    Polygon getPolygon();

    int getSequenceIndex();

    Array<Vector2> getWayPoints();

    boolean isCarInAccident();

    boolean isMoveCar();

    void render(SpriteBatch spriteBatch);

    void setCarInAccident(boolean z);

    void setMoveCar(boolean z);

    void setTurnLeft(boolean z);

    void setTurnRight(boolean z);

    void update(float f);
}
