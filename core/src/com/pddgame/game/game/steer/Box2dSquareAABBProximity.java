package com.pddgame.game.game.steer;

import com.badlogic.gdx.ai.steer.Proximity;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * Создание коллизии
 *
 */
public class Box2dSquareAABBProximity implements Proximity<Vector2>, QueryCallback {
    private static final AABB aabb = new AABB();
    protected float detectionRadius;
    protected Steerable<Vector2> owner;
    protected World world;
    protected Proximity.ProximityCallback<Vector2> behaviorCallback = null;
    private int neighborCount = 0;


    public static class AABB {
        float lowerX;
        float lowerY;
        float upperX;
        float upperY;
    }

    protected boolean accept(Steerable<Vector2> steerable) {
        return true;
    }

    public Box2dSquareAABBProximity(Steerable<Vector2> steerable, World world, float f) {
        this.owner = steerable;
        this.world = world;
        this.detectionRadius = f;
    }

    @Override
    public Steerable<Vector2> getOwner() {
        return this.owner;
    }

    @Override
    public void setOwner(Steerable<Vector2> steerable) {
        this.owner = steerable;
    }

    public World getWorld() {
        return this.world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public float getDetectionRadius() {
        return this.detectionRadius;
    }

    public void setDetectionRadius(float f) {
        this.detectionRadius = f;
    }

    @Override
    public int findNeighbors(Proximity.ProximityCallback<Vector2> proximityCallback) {
        this.behaviorCallback = proximityCallback;
        this.neighborCount = 0;
        prepareAABB(aabb);
        this.world.QueryAABB(this, aabb.lowerX, aabb.lowerY, aabb.upperX, aabb.upperY);
        this.behaviorCallback = null;
        return this.neighborCount;
    }


    public void prepareAABB(AABB aabb2) {
        Vector2 position = this.owner.getPosition();
        aabb2.lowerX = position.x - this.detectionRadius;
        aabb2.lowerY = position.y - this.detectionRadius;
        aabb2.upperX = position.x + this.detectionRadius;
        aabb2.upperY = position.y + this.detectionRadius;
    }

    protected Steerable<Vector2> getSteerable(Fixture fixture) {
        return (Steerable) fixture.getBody().getUserData();
    }

    @Override
    public boolean reportFixture(Fixture fixture) {
        Steerable<Vector2> steerable = getSteerable(fixture);
        if (steerable != this.owner && accept(steerable) && this.behaviorCallback.reportNeighbor(steerable)) {
            this.neighborCount++;
        }
        return true;
    }
}
