package com.pddgame.game.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.ai.steer.behaviors.FollowPath;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.pddgame.game.game.Assets;
import com.pddgame.game.game.steer.Box2dSteeringEntity;
import com.pddgame.game.utils.AudioManager;

/**
 *
 * Назначение текстур пешехода
 * Скорость движения пешехода
 * Физика пешехода
 * Анимации пешехода
 * Звуки пешехода
 */
public class Pedestrian extends AbstractGameObject implements CarInterface {
    public FollowPath<Vector2, LinePath.LinePathParam> followPathSB;
    private boolean isCarInAccident;
    private boolean isFlashing;
    private boolean isTurnLeft;
    private boolean isTurnRight;
    private boolean isWalking;
    public LinePath<Vector2> linePath;
    private MapLayer mapLayer;
    private float markerDim;
    private float markerOrig;
    private Vector2 markerPos;
    private boolean movePedestr;
    private TextureRegion pedMarkerReg;
    private TextureRegion pedestReg;
    private float period;
    public Polygon polygon;
    private int sequenceIndex;
    private Vector2 shadDim;
    private Vector2 shadPos;
    private TextureRegion shadowReg;
    private Box2dSteeringEntity steeringEntity;
    private Sound stepSound;
    private int typeCar;
    private TextureRegion walkingReg;
    public Array<Vector2> wayPoints;
    private World world;
    private String TAG = Pedestrian.class.getSimpleName();
    public final boolean openPath = true;
    private float pathOffset = 0.1f;
    private final float FLASH_PERIOD = 0.3f;

    @Override
    public void setTurnLeft(boolean z) {
    }

    @Override
    public void setTurnRight(boolean z) {
    }

    public Pedestrian(World world, MapLayer mapLayer, int i, int i2, int i3, boolean z, boolean z2) {
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
        this.isWalking = false;
        this.isCarInAccident = false;
        this.typeCar = i;
        this.stepSound = Assets.instance.sounds.steps;
        initPedestrType(i);
        init(i);
    }

    public void init(int i) {
        this.movePedestr = false;
        BodyDef bodyDef = new BodyDef();
        this.origin.set(this.dimension.x * 0.5f, this.dimension.y * 0.5f);
        bodyDef.position.set(this.origin);
        this.body = this.world.createBody(bodyDef);
        this.body.setType(BodyDef.BodyType.DynamicBody);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(this.dimension.y * 0.3f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 5.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 1.0f;
        fixtureDef.filter.categoryBits = (short) 1;
        this.body.createFixture(fixtureDef);
        this.body.setLinearDamping(6.0f);
        this.body.setAngularDamping(15.0f);
        this.body.setUserData(this);
        Polygon polygon = new Polygon(new float[]{0.0f, 0.0f, 0.0f, this.dimension.y, this.dimension.x, this.dimension.y, this.dimension.x, 0.0f});
        this.polygon = polygon;
        polygon.setOrigin(this.origin.x, this.origin.y);
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
        box2dSteeringEntity.setMaxLinearSpeed(2.0f);
        this.steeringEntity.setMaxLinearAcceleration(70.0f);
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
        this.markerPos.set(this.position);
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
        this.shadPos.x -= 0.6f;
        this.shadPos.y -= 1.0f;
        if (!this.isWalking && this.isFlashing) {
            this.markerPos.x -= this.markerDim * 0.15f;
            this.markerPos.y -= this.markerDim * 0.25f;
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(this.shadowReg.getTexture(), this.shadPos.x, this.shadPos.y, this.origin.x, this.origin.y, this.shadDim.x, this.shadDim.y, this.scale.x, this.scale.y, 0.0f, this.shadowReg.getRegionX(), this.shadowReg.getRegionY(), this.shadowReg.getRegionWidth(), this.shadowReg.getRegionHeight(), false, false);
        if (this.isWalking) {
            TextureRegion textureRegion = (TextureRegion) this.animation.getKeyFrame(this.stateTime);
            this.walkingReg = textureRegion;
            spriteBatch.draw(textureRegion.getTexture(), this.position.x, this.position.y, this.origin.x, this.origin.y, this.dimension.x, this.dimension.y, this.scale.x, this.scale.y, this.rotation, this.walkingReg.getRegionX(), this.walkingReg.getRegionY(), this.walkingReg.getRegionWidth(), this.walkingReg.getRegionHeight(), false, true);
        } else {
            spriteBatch.draw(this.pedestReg.getTexture(), this.position.x, this.position.y, this.origin.x, this.origin.y, this.dimension.x, this.dimension.y, this.scale.x, this.scale.y, this.rotation, this.pedestReg.getRegionX(), this.pedestReg.getRegionY(), this.pedestReg.getRegionWidth(), this.pedestReg.getRegionHeight(), false, true);
        }
        if (!this.isWalking && this.isFlashing) {
            Texture texture = this.pedMarkerReg.getTexture();
            float f = this.markerPos.x;
            float f2 = this.markerPos.y;
            float f3 = this.markerOrig;
            float f4 = this.markerDim;
            spriteBatch.draw(texture, f, f2, f3, f3, f4, f4, this.scale.x, this.scale.y, this.rotation, this.pedMarkerReg.getRegionX(), this.pedMarkerReg.getRegionY(), this.pedMarkerReg.getRegionWidth(), this.pedMarkerReg.getRegionHeight(), false, false);
        }
    }

    @Override
    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.polygon(this.polygon.getTransformedVertices());
        shapeRenderer.setColor(Color.CYAN);
        shapeRenderer.rect(this.body.getPosition().x - this.origin.x, this.body.getPosition().y - this.origin.y, this.dimension.x, this.dimension.y);
        shapeRenderer.setColor(Color.RED);
        float f = this.markerPos.x - (this.markerDim * 0.2f);
        float f2 = this.markerPos.y;
        float f3 = this.markerDim;
        shapeRenderer.rect(f, f2 - (0.2f * f3), f3, f3);
    }

    @Override
    public boolean isMoveCar() {
        return this.movePedestr;
    }

    @Override
    public void setMoveCar(boolean z) {
        this.movePedestr = z;
        this.isWalking = true;
        setAnimation(this.animation);
        AudioManager.instance.play(this.stepSound);
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
        this.isWalking = false;
        this.stepSound.stop();
    }

    public boolean isTurnLeft() {
        return this.isTurnLeft;
    }

    public boolean isTurnRight() {
        return this.isTurnRight;
    }

    private void initPedestrType(int i) {
        this.dimension.set(1.3f, 0.936f);
        this.shadPos = new Vector2();
        Vector2 vector2 = new Vector2();
        this.shadDim = vector2;
        vector2.set(1.5f, 0.7f);
        this.markerPos = new Vector2();
        float f = this.dimension.x * 1.5f;
        this.markerDim = f;
        this.markerOrig = f * 0.5f;
        this.shadowReg = Assets.instance.pedestrian.ped_shadow;
        if (i == 10) {
            this.animation = Assets.instance.pedestrian.ped_1_Anim;
            this.pedestReg = Assets.instance.pedestrian.ped_1_Reg;
        }
        if (i == 11) {
            this.animation = Assets.instance.pedestrian.ped_2_Anim;
            this.pedestReg = Assets.instance.pedestrian.ped_2_Reg;
        }
        this.pedMarkerReg = Assets.instance.pedestrian.ped_marker;
    }

    private void printLog(String str) {
        Gdx.app.log(this.TAG, str);
    }
}
