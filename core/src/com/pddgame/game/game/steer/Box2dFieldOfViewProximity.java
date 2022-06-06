package com.pddgame.game.game.steer;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.pddgame.game.game.steer.Box2dSquareAABBProximity;

/**
 *
 * Дальность отрисовки 2д боксов
 *
 */
public class Box2dFieldOfViewProximity extends Box2dSquareAABBProximity {
    float angle;
    float coneThreshold;
    private static final Vector2 toSteerable = new Vector2();
    private static final Vector2 ownerOrientation = new Vector2();

    public Box2dFieldOfViewProximity(Steerable<Vector2> steerable, World world, float f, float f2) {
        super(steerable, world, f);
        setAngle(f2);
    }

    public float getAngle() {
        return this.angle;
    }

    public void setAngle(float f) {
        this.angle = f;
        this.coneThreshold = (float) Math.cos(f * 0.5f);
    }

    @Override
    protected Steerable<Vector2> getSteerable(Fixture fixture) {
        return (Steerable) fixture.getBody().getUserData();
    }


    @Override
    public void prepareAABB(Box2dSquareAABBProximity.AABB aabb) {
        super.prepareAABB(aabb);
        this.owner.angleToVector(ownerOrientation, this.owner.getOrientation());
    }

    @Override
    protected boolean accept(Steerable<Vector2> steerable) {
        toSteerable.set(steerable.getPosition()).sub((Vector2) this.owner.getPosition());
        float boundingRadius = this.detectionRadius + steerable.getBoundingRadius();
        return toSteerable.len2() < boundingRadius * boundingRadius && ownerOrientation.dot(toSteerable) > this.coneThreshold;
    }
}
