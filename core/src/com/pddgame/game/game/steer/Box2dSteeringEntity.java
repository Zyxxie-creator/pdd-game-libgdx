package com.pddgame.game.game.steer;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 *
 * Управление объектом при помощи ИИ
 *
 */
public class Box2dSteeringEntity implements Steerable<Vector2> {
    private static final SteeringAcceleration<Vector2> steeringOutput = new SteeringAcceleration<>(new Vector2());
    Body body;
    float boundingRadius;
    boolean independentFacing;
    float maxAngularAcceleration;
    float maxAngularSpeed;
    float maxLinearAcceleration;
    float maxLinearSpeed;
    protected SteeringBehavior<Vector2> steeringBehavior;
    boolean tagged = false;

    @Override
    public float getZeroLinearSpeedThreshold() {
        return 0.001f;
    }

    public Box2dSteeringEntity(Body body, boolean z, float f) {
        this.body = body;
        this.independentFacing = z;
        this.boundingRadius = f;
    }

    public Body getBody() {
        return this.body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public boolean isIndependentFacing() {
        return this.independentFacing;
    }

    public void setIndependentFacing(boolean z) {
        this.independentFacing = z;
    }

    @Override
    public Vector2 getPosition() {
        return this.body.getPosition();
    }

    @Override
    public float getOrientation() {
        return this.body.getAngle();
    }

    @Override
    public void setOrientation(float f) {
        this.body.setTransform(getPosition(), f);
    }


    @Override
    public Vector2 getLinearVelocity() {
        return this.body.getLinearVelocity();
    }

    @Override
    public float getAngularVelocity() {
        return this.body.getAngularVelocity();
    }

    @Override
    public float getBoundingRadius() {
        return this.boundingRadius;
    }

    @Override
    public boolean isTagged() {
        return this.tagged;
    }

    @Override
    public void setTagged(boolean z) {
        this.tagged = z;
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

    public SteeringBehavior<Vector2> getSteeringBehavior() {
        return this.steeringBehavior;
    }

    public void setSteeringBehavior(SteeringBehavior<Vector2> steeringBehavior) {
        this.steeringBehavior = steeringBehavior;
    }

    public void update(float f) {
        SteeringBehavior<Vector2> steeringBehavior = this.steeringBehavior;
        if (steeringBehavior != null) {
            steeringBehavior.calculateSteering(steeringOutput);
            applySteering(steeringOutput, f);
        }
    }


    protected void applySteering(SteeringAcceleration<Vector2> steeringAcceleration, float f) {
        boolean z;
        if (!steeringOutput.linear.isZero()) {
            this.body.applyForceToCenter(steeringOutput.linear, true);
            z = true;
        } else {
            z = false;
        }
        if (!isIndependentFacing()) {
            Vector2 linearVelocity = getLinearVelocity();
            if (!linearVelocity.isZero(getZeroLinearSpeedThreshold())) {
                float vectorToAngle = vectorToAngle(linearVelocity);
                this.body.setAngularVelocity((vectorToAngle - getAngularVelocity()) * f);
                this.body.setTransform(this.body.getPosition(), vectorToAngle);
            }
        } else if (steeringOutput.angular != 0.0f) {
            this.body.applyTorque(steeringOutput.angular, true);
            z = true;
        }
        if (z) {
            Vector2 linearVelocity2 = this.body.getLinearVelocity();
            float len2 = linearVelocity2.len2();
            float maxLinearSpeed = getMaxLinearSpeed();
            if (len2 > maxLinearSpeed * maxLinearSpeed) {
                this.body.setLinearVelocity(linearVelocity2.scl(maxLinearSpeed / ((float) Math.sqrt(len2))));
            }
            float maxAngularSpeed = getMaxAngularSpeed();
            if (this.body.getAngularVelocity() > maxAngularSpeed) {
                this.body.setAngularVelocity(maxAngularSpeed);
            }
        }
    }

    protected void wrapAround(float f, float f2) {
        Vector2 position = this.body.getPosition();
        f = 0.0f;
        if (position.x > f) {
            position.x = 0.0f;
            f = 0.0f;
        } else {
            f = Float.POSITIVE_INFINITY;
        }
        if (position.x < 0.0f) {
            position.x = f;
        }
        if (position.y < 0.0f) {
            position.y = f2;
            f = f2;
        }
        if (position.y > f2) {
            position.y = 0.0f;
        }
        if (f != Float.POSITIVE_INFINITY) {
            Body body = this.body;
            body.setTransform(position, body.getAngle());
        }
    }

    @Override
    public float getMaxLinearSpeed() {
        return this.maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float f) {
        this.maxLinearSpeed = f;
    }

    @Override
    public float getMaxLinearAcceleration() {
        return this.maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration(float f) {
        this.maxLinearAcceleration = f;
    }

    @Override
    public float getMaxAngularSpeed() {
        return this.maxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed(float f) {
        this.maxAngularSpeed = f;
    }

    @Override
    public float getMaxAngularAcceleration() {
        return this.maxAngularAcceleration;
    }

    @Override
    public void setMaxAngularAcceleration(float f) {
        this.maxAngularAcceleration = f;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float f) {
        throw new UnsupportedOperationException();
    }
}
