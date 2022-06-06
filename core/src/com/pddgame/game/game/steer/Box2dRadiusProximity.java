package com.pddgame.game.game.steer;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * Радиус 2д боксов
 *
 */
public class Box2dRadiusProximity extends Box2dSquareAABBProximity {
    public Box2dRadiusProximity(Steerable<Vector2> steerable, World world, float f) {
        super(steerable, world, f);
    }

    @Override
    protected Steerable<Vector2> getSteerable(Fixture fixture) {
        return (Steerable) fixture.getBody().getUserData();
    }

    @Override
    protected boolean accept(Steerable<Vector2> steerable) {
        float boundingRadius = this.detectionRadius + steerable.getBoundingRadius();
        return steerable.getPosition().dst2((Vector2) this.owner.getPosition()) <= boundingRadius * boundingRadius;
    }
}
