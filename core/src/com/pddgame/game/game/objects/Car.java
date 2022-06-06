package com.pddgame.game.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.ai.steer.behaviors.FollowPath;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.pddgame.game.game.Assets;
import com.pddgame.game.game.steer.Box2dSteeringEntity;
import com.pddgame.game.utils.AudioManager;

/**
 * Скорость движения авто
 * Физика движения авто
 * Тень авто
 * Отрисовка авто
 * Назначение текстур авто
 **/
public class Car extends AbstractGameObject implements CarInterface {
    private TextureRegion carAccReg;
    private TextureRegion carAlarm;
    private TextureRegion carLeftTurnReg;
    private TextureRegion carReg;
    private TextureRegion carRightTurnReg;
    public FollowPath<Vector2, LinePath.LinePathParam> followPathSB;
    private boolean isAlarmFireOn;
    private boolean isAlarmSoundOn;
    private boolean isCarInAccident;
    private boolean isFlashing;
    private boolean isTurnLeft;
    private boolean isTurnRight;
    public LinePath<Vector2> linePath;
    private MapLayer mapLayer;
    private boolean moveCar;
    private float offsetShadowX;
    private float offsetShadowY;
    private float period;
    public Polygon polygon;
    private int sequenceIndex;
    private float shadowRotation;
    private Sound sound;
    private Box2dSteeringEntity steeringEntity;
    private int typeCar;
    public Array<Vector2> wayPoints;
    private World world;
    private String TAG = Car.class.getSimpleName();
    public final boolean openPath = true;
    private float pathOffset = 1.5f;
    private final float FLASH_PERIOD = 0.3f;
    private TextureRegion shadowReg = Assets.instance.car.bicShadow;
    private Vector2 shadDim = new Vector2();
    private Vector2 shadPos = new Vector2();

    @Override
    public void drawDebug(ShapeRenderer shapeRenderer) {
    }

    public Car(World world, MapLayer mapLayer, int i, int i2, int i3, boolean z, boolean z2) {
        this.world = world;
        this.mapLayer = mapLayer;
        this.sequenceIndex = i2;
        if (i3 == -1) {
            this.isTurnLeft = true;
            this.isTurnRight = false;
        }
        if (i3 == 0) {
            this.isTurnLeft = false;
            this.isTurnRight = false;
        }
        if (i3 == 1) {
            this.isTurnLeft = false;
            this.isTurnRight = true;
        }
        this.isAlarmFireOn = z;
        this.isAlarmSoundOn = z2;
        this.isCarInAccident = false;
        this.typeCar = i;
        initCarsType(i);
        init(i);
    }

    public void init(int i) {
        this.moveCar = false;
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(this.position);
        this.body = this.world.createBody(bodyDef);
        this.body.setType(BodyDef.BodyType.DynamicBody);
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(this.dimension.x / 2.5f, this.dimension.y / 2.1f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 15.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 1.0f;
        fixtureDef.filter.categoryBits = (short) 1;
        this.body.createFixture(fixtureDef);
        this.body.setLinearDamping(10.0f);
        this.body.setAngularDamping(15.0f);
        this.body.setUserData(this);
        polygonShape.dispose();
        this.origin.set(this.dimension.x / 2.0f, this.dimension.y / 2.0f);
        if (i == 91) {
            this.polygon = new Polygon(new float[]{0.4f, 0.0f, 0.4f, this.dimension.y, 0.8f, this.dimension.y, 0.8f, 0.0f});
        } else {
            this.polygon = new Polygon(new float[]{0.0f, 0.0f, 0.0f, this.dimension.y, this.dimension.x, this.dimension.y, this.dimension.x, 0.0f});
        }
        this.polygon.setOrigin(this.origin.x, this.origin.y);
        this.wayPoints = new Array<>();
        int count = this.mapLayer.getObjects().getCount();
        for (int i2 = 0; i2 < count; i2++) {
            MapObject mapObject = this.mapLayer.getObjects().get(i2);
            Vector2 vector2 = new Vector2(((Float) mapObject.getProperties().get("x", Float.class)).floatValue(), ((Float) mapObject.getProperties().get("y", Float.class)).floatValue());
            vector2.set(vector2.x / 36.0f, vector2.y / 36.0f);
            this.wayPoints.add(vector2);
        }
        Box2dSteeringEntity box2dSteeringEntity = new Box2dSteeringEntity(this.body, false, 3.0f);
        this.steeringEntity = box2dSteeringEntity;
        if (i == 9) {
            box2dSteeringEntity.setMaxLinearSpeed(6000.0f);
            this.steeringEntity.setMaxLinearAcceleration(10000.0f);
        } else if (i == 91) {
            box2dSteeringEntity.setMaxLinearSpeed(500.0f);
            this.steeringEntity.setMaxLinearAcceleration(1500.0f);
        } else if (i == 8) {
            box2dSteeringEntity.setMaxLinearSpeed(2000.0f);
            this.steeringEntity.setMaxLinearAcceleration(3800.0f);
        } else if (i == 4) {
            box2dSteeringEntity.setMaxLinearSpeed(2000.0f);
            this.steeringEntity.setMaxLinearAcceleration(3800.0f);
        } else {
            box2dSteeringEntity.setMaxLinearSpeed(2000.0f);
            this.steeringEntity.setMaxLinearAcceleration(3000.0f);
        }
        this.linePath = new LinePath<>(this.wayPoints, true);
        FollowPath<Vector2, LinePath.LinePathParam> arrivalTolerance = new FollowPath(this.steeringEntity, this.linePath, this.pathOffset).setPredictionTime(0.05f).setDecelerationRadius(2.1f).setArrivalTolerance(0.5f);
        this.followPathSB = arrivalTolerance;
        this.steeringEntity.setSteeringBehavior(arrivalTolerance);
        this.steeringEntity.getBody().setTransform(this.wayPoints.first().x, this.wayPoints.first().y, setCarAngleRad(new Vector2(this.wayPoints.first()), this.wayPoints.get(1)));
    }

    @Override
    public void update(float f) {
        super.update(f);
        this.shadPos.set(this.position);
        this.position.sub(this.origin);
        this.polygon.setPosition(this.position.x, this.position.y);
        this.polygon.setRotation(this.rotation);
        float f2 = this.period - f;
        this.period = f2;
        if (f2 < 0.0f) {
            this.period = 0.3f;
            if (this.isFlashing) {
                this.isFlashing = false;
            } else {
                this.isFlashing = true;
            }
        }
        if (isMoveCar() && !this.isCarInAccident) {
            this.steeringEntity.update(GdxAI.getTimepiece().getDeltaTime());
        }
        if (this.typeCar == 91) {
            this.shadowRotation = 0.0f;
        } else {
            this.shadowRotation = this.rotation;
        }
        this.shadPos.x += this.offsetShadowX;
        this.shadPos.y -= this.offsetShadowY;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(this.shadowReg.getTexture(), this.shadPos.x, this.shadPos.y, this.origin.x, this.origin.y, this.shadDim.x, this.shadDim.y, this.scale.x, this.scale.y, this.shadowRotation, this.shadowReg.getRegionX(), this.shadowReg.getRegionY(), this.shadowReg.getRegionWidth(), this.shadowReg.getRegionHeight(), false, false);
        if (this.isTurnLeft && this.isFlashing) {
            spriteBatch.draw(this.carLeftTurnReg.getTexture(), this.position.x, this.position.y, this.origin.x, this.origin.y, this.dimension.x, this.dimension.y, this.scale.x, this.scale.y, this.rotation, this.carLeftTurnReg.getRegionX(), this.carLeftTurnReg.getRegionY(), this.carLeftTurnReg.getRegionWidth(), this.carLeftTurnReg.getRegionHeight(), false, false);
        } else if (this.isTurnRight && this.isFlashing) {
            spriteBatch.draw(this.carRightTurnReg.getTexture(), this.position.x, this.position.y, this.origin.x, this.origin.y, this.dimension.x, this.dimension.y, this.scale.x, this.scale.y, this.rotation, this.carRightTurnReg.getRegionX(), this.carRightTurnReg.getRegionY(), this.carRightTurnReg.getRegionWidth(), this.carRightTurnReg.getRegionHeight(), false, false);
        } else if (this.isCarInAccident && this.isFlashing) {
            spriteBatch.draw(this.carAccReg.getTexture(), this.position.x, this.position.y, this.origin.x, this.origin.y, this.dimension.x, this.dimension.y, this.scale.x, this.scale.y, this.rotation, this.carAccReg.getRegionX(), this.carAccReg.getRegionY(), this.carAccReg.getRegionWidth(), this.carAccReg.getRegionHeight(), false, false);
        } else if (this.isAlarmFireOn && !this.isFlashing) {
            spriteBatch.draw(this.carAlarm.getTexture(), this.position.x, this.position.y, this.origin.x, this.origin.y, this.dimension.x, this.dimension.y, this.scale.x, this.scale.y, this.rotation, this.carAlarm.getRegionX(), this.carAlarm.getRegionY(), this.carAlarm.getRegionWidth(), this.carAlarm.getRegionHeight(), false, false);
        } else if (this.animation == null || !this.moveCar) {
            spriteBatch.draw(this.carReg.getTexture(), this.position.x, this.position.y, this.origin.x, this.origin.y, this.dimension.x, this.dimension.y, this.scale.x, this.scale.y, this.rotation, this.carReg.getRegionX(), this.carReg.getRegionY(), this.carReg.getRegionWidth(), this.carReg.getRegionHeight(), false, false);
        } else {
            TextureRegion textureRegion = (TextureRegion) this.animation.getKeyFrame(this.stateTime);
            this.carReg = textureRegion;
            spriteBatch.draw(textureRegion.getTexture(), this.position.x, this.position.y, this.origin.x, this.origin.y, this.dimension.x, this.dimension.y, this.scale.x, this.scale.y, this.rotation, this.carReg.getRegionX(), this.carReg.getRegionY(), this.carReg.getRegionWidth(), this.carReg.getRegionHeight(), false, false);
        }
    }

    @Override
    public boolean isMoveCar() {
        return this.moveCar;
    }

    @Override
    public void setMoveCar(boolean z) {
        this.moveCar = z;
        AudioManager.instance.play(this.sound);
    }

    private float setCarAngleRad(Vector2 vector2, Vector2 vector22) {
        return (vector2.sub(vector22).angle() + 90.0f) * 0.017453292f;
    }

    @Override
    public Array<Vector2> getWayPoints() {
        return this.wayPoints;
    }

    @Override
    public LinePath<Vector2> getLinePath() {
        return this.linePath;
    }

    @Override
    public FollowPath<Vector2, LinePath.LinePathParam> getFollowPathSB() {
        return this.followPathSB;
    }

    @Override
    public Polygon getPolygon() {
        return this.polygon;
    }

    @Override
    public int getSequenceIndex() {
        return this.sequenceIndex;
    }

    @Override
    public boolean isCarInAccident() {
        return this.isCarInAccident;
    }

    @Override
    public void setCarInAccident(boolean z) {
        this.isCarInAccident = z;
        if (z) {
            setTurnRight(false);
            setTurnLeft(false);
        }
    }

    public boolean isTurnLeft() {
        return this.isTurnLeft;
    }

    @Override
    public void setTurnLeft(boolean z) {
        this.isTurnLeft = z;
        if (z) {
            setTurnRight(false);
            setCarInAccident(false);
        }
    }

    public boolean isTurnRight() {
        return this.isTurnRight;
    }

    @Override
    public void setTurnRight(boolean z) {
        this.isTurnRight = z;
        if (z) {
            setTurnLeft(false);
            setCarInAccident(false);
        }
    }

    private void initCarsType(int i) {
        if (i == 1) {
            this.carReg = Assets.instance.car.car_1_reg;
            this.carLeftTurnReg = Assets.instance.car.car_1_left_reg;
            this.carRightTurnReg = Assets.instance.car.car_1_right_reg;
            this.carAccReg = Assets.instance.car.car_1_acc_reg;
            this.dimension.set(1.7f, 3.74f);
            this.sound = Assets.instance.sounds.move;
            this.shadDim.set(1.7f, 3.74f);
            this.shadowReg = Assets.instance.car.car_1_shadow_reg;
            this.offsetShadowX = -0.7f;
            this.offsetShadowY = 2.2f;
        }
        if (i == 2) {
            this.carReg = Assets.instance.car.car_2_reg;
            this.carLeftTurnReg = Assets.instance.car.car_2_left_reg;
            this.carRightTurnReg = Assets.instance.car.car_2_right_reg;
            this.carAccReg = Assets.instance.car.car_2_acc_reg;
            this.dimension.set(1.7f, 3.74f);
            this.sound = Assets.instance.sounds.move;
            this.shadDim.set(1.7f, 3.74f);
            this.shadowReg = Assets.instance.car.car_2_shadow_reg;
            this.offsetShadowX = -0.7f;
            this.offsetShadowY = 2.2f;
        }
        if (i == 3) {
            this.carReg = Assets.instance.car.car_3_reg;
            this.carLeftTurnReg = Assets.instance.car.car_3_left_reg;
            this.carRightTurnReg = Assets.instance.car.car_3_right_reg;
            this.carAccReg = Assets.instance.car.car_3_acc_reg;
            this.dimension.set(1.7f, 3.74f);
            this.sound = Assets.instance.sounds.move;
            this.shadDim.set(1.7f, 3.74f);
            this.shadowReg = Assets.instance.car.car_3_shadow_reg;
            this.offsetShadowX = -0.7f;
            this.offsetShadowY = 2.2f;
        }
        if (i == 4) {
            this.carReg = Assets.instance.car.bus_reg;
            this.carLeftTurnReg = Assets.instance.car.bus_left_reg;
            this.carRightTurnReg = Assets.instance.car.bus_right_reg;
            this.carAccReg = Assets.instance.car.bus_acc_reg;
            this.dimension.set(1.8f, 4.27f);
            this.sound = Assets.instance.sounds.move;
            this.shadDim.set(1.8f, 4.27f);
            this.shadowReg = Assets.instance.car.bus_shadow_reg;
            this.offsetShadowX = -0.7f;
            this.offsetShadowY = 2.8f;
        }
        if (i == 5) {
            this.carReg = Assets.instance.car.police_car_reg;
            this.carLeftTurnReg = Assets.instance.car.police_car_left_reg;
            this.carRightTurnReg = Assets.instance.car.police_car_right_reg;
            this.carAccReg = Assets.instance.car.police_car_acc_reg;
            this.carAlarm = Assets.instance.car.police_car_alarm_reg;
            this.dimension.set(1.7f, 3.74f);
            this.sound = Assets.instance.sounds.move;
            this.shadDim.set(1.7f, 3.74f);
            this.shadowReg = Assets.instance.car.car_1_shadow_reg;
            this.offsetShadowX = -0.7f;
            this.offsetShadowY = 2.2f;
        }
        if (i == 8) {
            this.carReg = Assets.instance.car.truck_reg;
            this.carLeftTurnReg = Assets.instance.car.truck_left_reg;
            this.carRightTurnReg = Assets.instance.car.truck_right_reg;
            this.carAccReg = Assets.instance.car.truck_acc_reg;
            this.carAlarm = Assets.instance.car.truck_al_reg;
            this.dimension.set(1.8f, 4.557f);
            this.sound = Assets.instance.sounds.move;
            this.shadDim.set(1.8f, 4.557f);
            this.shadowReg = Assets.instance.car.truck_shadow_reg;
            this.offsetShadowX = -0.7f;
            this.offsetShadowY = 2.8f;
        }
        if (i == 9) {
            this.carReg = Assets.instance.car.tram_reg;
            this.carLeftTurnReg = Assets.instance.car.tram_left_reg;
            this.carRightTurnReg = Assets.instance.car.tram_right_reg;
            this.carAccReg = Assets.instance.car.tram_acc_reg;
            this.dimension.set(1.8f, 10.11f);
            this.pathOffset = 4.0f;
            this.sound = Assets.instance.sounds.tram;
            this.shadDim.set(1.8f, 10.11f);
            this.shadowReg = Assets.instance.car.tram_shadow_reg;
            this.offsetShadowX = -0.5f;
            this.offsetShadowY = 6.0f;
        }
        if (i == 91) {
            this.carReg = Assets.instance.car.bicycle_reg;
            this.carLeftTurnReg = Assets.instance.car.bicycle_reg;
            this.carRightTurnReg = Assets.instance.car.bicycle_reg;
            this.carAccReg = Assets.instance.car.bicycle_reg;
            this.dimension.set(1.3f, 3.3f);
            this.pathOffset = 1.0f;
            this.sound = Assets.instance.sounds.bicycle;
            this.animation = Assets.instance.car.bicycleAnim;
            this.shadDim.set(0.4f, 3.5f);
            this.shadowReg = Assets.instance.car.bicShadow;
            this.offsetShadowX = 0.0f;
            this.offsetShadowY = 1.9f;
        }
        if (this.isAlarmSoundOn) {
            AudioManager.instance.play(Assets.instance.music.alarmSnd);
        }
    }

    private void printLog(String str) {
        Gdx.app.log(this.TAG, str);
    }
}
