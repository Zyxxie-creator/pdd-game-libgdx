package com.pddgame.game.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.pddgame.game.game.Assets;

/**
 *
 * Текстуры дорожных знаков
 * Отрисовка дорожных знаков
 *
 */
public class RoadSign extends AbstractGameObject {
    private boolean flipX;
    private boolean flipY;
    private TextureRegion signReg;
    private int signsType;

    @Override
    public void drawDebug(ShapeRenderer shapeRenderer) {
    }

    public RoadSign(MapObject mapObject, int i, float f) {
        Vector2 vector2 = new Vector2(((Float) mapObject.getProperties().get("x", Float.class)).floatValue(), ((Float) mapObject.getProperties().get("y", Float.class)).floatValue());
        this.position.set(vector2.x / 36.0f, vector2.y / 36.0f);
        this.signsType = i;
        setRotation(f);
        this.flipX = false;
        this.flipX = false;
        init();
    }

    public RoadSign(MapObject mapObject, int i, float f, boolean z, boolean z2) {
        Vector2 vector2 = new Vector2(((Float) mapObject.getProperties().get("x", Float.class)).floatValue(), ((Float) mapObject.getProperties().get("y", Float.class)).floatValue());
        this.position.set(vector2.x / 36.0f, vector2.y / 36.0f);
        this.signsType = i;
        this.flipX = z;
        this.flipY = z2;
        setRotation(f);
        init();
    }

    private void init() {
        int i = this.signsType;
        if (i == 0) {
            this.signReg = Assets.instance.roadSigns.mainRoadReg;
            this.dimension.set(1.4f, 1.4f);
        } else if (i == 1) {
            this.signReg = Assets.instance.roadSigns.directionMainRoadReg;
            this.dimension.set(1.17f, 1.17f);
        } else if (i == 130) {
            this.signReg = Assets.instance.roadSigns.directionMainRoadThreeReg;
            this.dimension.set(1.17f, 1.17f);
        } else if (i == 11) {
            this.signReg = Assets.instance.roadSigns.mainRoadThreeLeftReg;
            this.dimension.set(1.17f, 1.17f);
        } else if (i == 12) {
            this.signReg = Assets.instance.roadSigns.mainRoadThreeRightReg;
            this.dimension.set(1.17f, 1.17f);
        } else if (i == 13) {
            this.signReg = Assets.instance.roadSigns.notMainRoadThreeReg;
            this.dimension.set(1.17f, 1.17f);
        } else if (i == 2) {
            this.signReg = Assets.instance.roadSigns.giveWayReg;
            this.dimension.set(1.17f, 1.17f);
        } else if (i == 3) {
            this.signReg = Assets.instance.roadSigns.motorWayReg;
            this.dimension.set(1.17f, 1.73f);
        } else if (i == 4) {
            this.signReg = Assets.instance.roadSigns.movementBandsReg;
            this.dimension.set(1.56f, 1.17f);
        } else if (i == 41) {
            this.signReg = Assets.instance.roadSigns.movementBandsLeftReg;
            this.dimension.set(1.56f, 1.17f);
        } else if (i == 5) {
            this.signReg = Assets.instance.roadSigns.stopReg;
            this.dimension.set(1.17f, 1.17f);
        } else if (i == 6) {
            this.signReg = Assets.instance.roadSigns.straightMoveReg;
            this.dimension.set(1.17f, 1.17f);
        } else if (i == 7) {
            this.signReg = Assets.instance.roadSigns.circularMoveReg;
            this.dimension.set(1.17f, 1.17f);
        } else if (i == 8) {
            this.signReg = Assets.instance.roadSigns.deadEndReg;
            this.dimension.set(1.17f, 1.17f);
        } else if (i == 9) {
            this.signReg = Assets.instance.roadSigns.exitOneWayMoveReg;
            this.dimension.set(1.63f, 0.56f);
        } else {
            Gdx.app.log(RoadSign.class.getName(), "Wrong type of sign region!");
        }
        this.origin.set(this.dimension.x * 0.5f, this.dimension.y * 0.5f);
    }

    @Override
    public void update(float f) {
        super.update(f);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(this.signReg.getTexture(), this.position.x, this.position.y, this.origin.x, this.origin.y, this.dimension.x, this.dimension.y, this.scale.x, this.scale.y, this.rotation, this.signReg.getRegionX(), this.signReg.getRegionY(), this.signReg.getRegionWidth(), this.signReg.getRegionHeight(), this.flipX, this.flipY);
    }

    public void setRotation(float f) {
        this.rotation = f;
    }
}
