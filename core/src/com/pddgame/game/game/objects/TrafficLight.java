package com.pddgame.game.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.pddgame.game.game.Assets;


/**
 *
 * Назначение сигналов светофоров
 * Повороты светофоров
 * Назначение текстур светофоров
 * Рендеринг светофоров
 *
 */
public class TrafficLight extends AbstractGameObject {
    private final float FLASH_PERIOD = 0.4f;
    private boolean isFlashing;
    private boolean isFlashingYellow;
    private boolean isLeftSec11;
    private boolean isLeftSec12;
    private boolean isLeftSec13;
    private boolean isLeftSec21;
    private boolean isLeftSec22;
    private boolean isLeftSec23;
    private boolean isLeftSec31;
    private boolean isLeftSec32;
    private boolean isLeftSec33;
    int[] lightsConfig;
    private float period;
    private Vector2 secOrig11;
    private Vector2 secOrig12;
    private Vector2 secOrig13;
    private Vector2 secOrig21;
    private Vector2 secOrig22;
    private Vector2 secOrig23;
    private Vector2 secOrig31;
    private Vector2 secOrig33;
    private Vector2 secPos11;
    private Vector2 secPos12;
    private Vector2 secPos13;
    private Vector2 secPos21;
    private Vector2 secPos22;
    private Vector2 secPos23;
    private Vector2 secPos31;
    private Vector2 secPos33;
    private TextureRegion section11;
    private TextureRegion section12;
    private TextureRegion section13;
    private TextureRegion section21;
    private TextureRegion section22;
    private TextureRegion section23;
    private TextureRegion section31;
    private TextureRegion section32;
    private TextureRegion section33;
    private Vector2 whiteSecOrig11;
    private Vector2 whiteSecOrig12;
    private Vector2 whiteSecOrig13;
    private Vector2 whiteSecOrig22;
    private Vector2 whiteSecPos11;
    private Vector2 whiteSecPos12;
    private Vector2 whiteSecPos13;
    private Vector2 whiteSecPos22;
    private TextureRegion whiteSection11;
    private TextureRegion whiteSection12;
    private TextureRegion whiteSection13;
    private TextureRegion whiteSection22;

    @Override
    public void drawDebug(ShapeRenderer shapeRenderer) {
    }

    public TrafficLight(MapLayer mapLayer, int[] iArr, boolean z, float f) {
        MapObject mapObject = mapLayer.getObjects().get(0);
        Vector2 vector2 = new Vector2(((Float) mapObject.getProperties().get("x", Float.class)).floatValue(), ((Float) mapObject.getProperties().get("y", Float.class)).floatValue());
        this.position.set(vector2.x / 36.0f, vector2.y / 36.0f);
        this.dimension.set(0.51f, 0.51f);
        this.lightsConfig = iArr;
        this.isFlashingYellow = z;
        setRotation(f);
        init();
    }

    public TrafficLight(MapObject mapObject, int[] iArr, boolean z, float f) {
        Vector2 vector2 = new Vector2(((Float) mapObject.getProperties().get("x", Float.class)).floatValue(), ((Float) mapObject.getProperties().get("y", Float.class)).floatValue());
        this.position.set(vector2.x / 36.0f, vector2.y / 36.0f);
        this.dimension.set(0.51f, 0.51f);
        this.lightsConfig = iArr;
        this.isFlashingYellow = z;
        setRotation(f);
        init();
    }

    private void init() {
        this.section11 = initSection11();
        this.section12 = initSection12();
        this.section13 = initSection13();
        this.section21 = initSection21();
        this.section22 = initSection22();
        this.section23 = initSection23();
        this.section31 = initSection31();
        this.section32 = initSection32();
        this.section33 = initSection33();
        this.whiteSection11 = initWhiteSec11();
        this.whiteSection12 = initWhiteSec12();
        this.whiteSection13 = initWhiteSec13();
        this.whiteSection22 = initWhiteSec22();
    }

    private TextureRegion initWhiteSec11() {
        this.whiteSecPos11 = new Vector2(this.position.x - this.dimension.x, this.position.y + (this.dimension.y * 4.1f));
        this.whiteSecOrig11 = new Vector2(this.origin.x + this.dimension.x, this.origin.y - (this.dimension.y * 4.1f));
        int i = this.lightsConfig[9];
        if (i == -1) {
            return null;
        }
        if (i == 40) {
            return Assets.instance.trafficLight.whiteReg;
        }
        if (i == 0) {
            return Assets.instance.trafficLight.emptyReg;
        }
        return null;
    }

    private TextureRegion initWhiteSec12() {
        this.whiteSecPos12 = new Vector2(this.position.x, this.position.y + (this.dimension.y * 4.1f));
        this.whiteSecOrig12 = new Vector2(this.origin.x, this.origin.y - (this.dimension.y * 4.1f));
        int i = this.lightsConfig[10];
        if (i == -1) {
            return null;
        }
        if (i == 40) {
            return Assets.instance.trafficLight.whiteReg;
        }
        if (i == 0) {
            return Assets.instance.trafficLight.emptyReg;
        }
        return null;
    }

    private TextureRegion initWhiteSec13() {
        this.whiteSecPos13 = new Vector2(this.position.x + this.dimension.x, this.position.y + (this.dimension.y * 4.1f));
        this.whiteSecOrig13 = new Vector2(this.origin.x - this.dimension.x, this.origin.y - (this.dimension.y * 4.1f));
        int i = this.lightsConfig[11];
        if (i == -1) {
            return null;
        }
        if (i == 40) {
            return Assets.instance.trafficLight.whiteReg;
        }
        if (i == 0) {
            return Assets.instance.trafficLight.emptyReg;
        }
        return null;
    }

    private TextureRegion initWhiteSec22() {
        this.whiteSecPos22 = new Vector2(this.position.x, this.position.y + (this.dimension.y * 3.1f));
        this.whiteSecOrig22 = new Vector2(this.origin.x, this.origin.y - (this.dimension.y * 3.1f));
        int i = this.lightsConfig[12];
        if (i == -1) {
            return null;
        }
        if (i == 40) {
            return Assets.instance.trafficLight.whiteReg;
        }
        if (i == 0) {
            return Assets.instance.trafficLight.emptyReg;
        }
        return null;
    }

    private TextureRegion initSection11() {
        this.secPos11 = new Vector2(this.position.x - this.dimension.x, this.position.y + (this.dimension.y * 2.0f));
        this.secOrig11 = new Vector2(this.origin.x + this.dimension.x, this.origin.y - (this.dimension.y * 2.0f));
        this.isLeftSec11 = true;
        int i = this.lightsConfig[0];
        if (i == -1) {
            return null;
        }
        if (i == 11) {
            return Assets.instance.trafficLight.redArrow;
        }
        if (i == 13) {
            return Assets.instance.trafficLight.redArrowContur;
        }
        if (i == 15) {
            return Assets.instance.trafficLight.redArrowOneDir;
        }
        if (i == 17) {
            return Assets.instance.trafficLight.redArrowOneDirContur;
        }
        return null;
    }

    private TextureRegion initSection12() {
        this.secPos12 = new Vector2(this.position.x, this.position.y + (this.dimension.y * 2.0f));
        this.secOrig12 = new Vector2(this.origin.x, this.origin.y - (this.dimension.y * 2.0f));
        int i = this.lightsConfig[1];
        if (i == -1) {
            return Assets.instance.trafficLight.emptyReg;
        }
        if (i == 0) {
            return Assets.instance.trafficLight.emptyReg;
        }
        if (i == 1) {
            return Assets.instance.trafficLight.redReg;
        }
        if (i == 11) {
            return Assets.instance.trafficLight.redArrow;
        }
        if (i == 13) {
            return Assets.instance.trafficLight.redArrowContur;
        }
        if (i == 15) {
            return Assets.instance.trafficLight.redArrowOneDir;
        }
        if (i == 17) {
            return Assets.instance.trafficLight.redArrowOneDirContur;
        }
        return null;
    }

    private TextureRegion initSection13() {
        this.secPos13 = new Vector2(this.position.x + this.dimension.x, this.position.y + (this.dimension.y * 2.0f));
        this.secOrig13 = new Vector2(this.origin.x - this.dimension.x, this.origin.y - (this.dimension.y * 2.0f));
        this.isLeftSec13 = false;
        int i = this.lightsConfig[2];
        if (i == -1) {
            return null;
        }
        if (i == 11) {
            return Assets.instance.trafficLight.redArrow;
        }
        if (i == 13) {
            return Assets.instance.trafficLight.redArrowContur;
        }
        if (i == 15) {
            return Assets.instance.trafficLight.redArrowOneDir;
        }
        if (i == 17) {
            return Assets.instance.trafficLight.redArrowOneDirContur;
        }
        return null;
    }

    private TextureRegion initSection21() {
        this.secPos21 = new Vector2(this.position.x - this.dimension.x, this.position.y + this.dimension.y);
        this.secOrig21 = new Vector2(this.origin.x + this.dimension.x, this.origin.y - this.dimension.y);
        this.isLeftSec21 = true;
        int i = this.lightsConfig[3];
        if (i == -1) {
            return null;
        }
        if (i == 21) {
            return Assets.instance.trafficLight.yellowArrow;
        }
        if (i == 23) {
            return Assets.instance.trafficLight.yellowArrowContur;
        }
        if (i == 25) {
            return Assets.instance.trafficLight.yellowArrowOneDir;
        }
        if (i == 27) {
            return Assets.instance.trafficLight.yellowArrowOneDirContur;
        }
        return null;
    }

    private TextureRegion initSection22() {
        this.secPos22 = new Vector2(this.position.x, this.position.y + this.dimension.y);
        this.secOrig22 = new Vector2(this.origin.x, this.origin.y - this.dimension.y);
        int i = this.lightsConfig[4];
        if (i == -1) {
            return null;
        }
        if (i == 0) {
            return Assets.instance.trafficLight.emptyReg;
        }
        if (i == 2) {
            return Assets.instance.trafficLight.yellowReg;
        }
        if (i == 21) {
            return Assets.instance.trafficLight.yellowArrow;
        }
        if (i == 23) {
            return Assets.instance.trafficLight.yellowArrowContur;
        }
        if (i == 25) {
            return Assets.instance.trafficLight.yellowArrowOneDir;
        }
        if (i == 27) {
            return Assets.instance.trafficLight.yellowArrowOneDirContur;
        }
        return null;
    }

    private TextureRegion initSection23() {
        this.secPos23 = new Vector2(this.position.x + this.dimension.x, this.position.y + this.dimension.y);
        this.secOrig23 = new Vector2(this.origin.x - this.dimension.x, this.origin.y - this.dimension.y);
        this.isLeftSec23 = false;
        int i = this.lightsConfig[5];
        if (i == -1) {
            return null;
        }
        if (i == 22) {
            return Assets.instance.trafficLight.yellowArrow;
        }
        if (i == 24) {
            return Assets.instance.trafficLight.yellowArrowContur;
        }
        if (i == 26) {
            return Assets.instance.trafficLight.yellowArrowOneDir;
        }
        if (i == 28) {
            return Assets.instance.trafficLight.yellowArrowOneDirContur;
        }
        return null;
    }

    private TextureRegion initSection31() {
        this.secPos31 = new Vector2(this.position.x - this.dimension.x, this.position.y);
        this.secOrig31 = new Vector2(this.origin.x + this.dimension.x, this.origin.y);
        this.isLeftSec31 = true;
        int i = this.lightsConfig[6];
        if (i == -1) {
            return null;
        }
        if (i == 31) {
            return Assets.instance.trafficLight.greenArrow;
        }
        if (i == 33) {
            return Assets.instance.trafficLight.greenArrowContur;
        }
        if (i == 35) {
            return Assets.instance.trafficLight.greenArrowOneDir;
        }
        if (i == 37) {
            return Assets.instance.trafficLight.greenArrowOneDirContur;
        }
        if (i == 11) {
            return Assets.instance.trafficLight.redArrow;
        }
        if (i == 13) {
            return Assets.instance.trafficLight.redArrowContur;
        }
        if (i == 15) {
            return Assets.instance.trafficLight.redArrowOneDir;
        }
        if (i == 17) {
            return Assets.instance.trafficLight.redArrowOneDirContur;
        }
        return null;
    }

    private TextureRegion initSection32() {
        int i = this.lightsConfig[7];
        if (i == -1) {
            return null;
        }
        if (i == 0) {
            return Assets.instance.trafficLight.emptyReg;
        }
        if (i == 3) {
            return Assets.instance.trafficLight.greenReg;
        }
        if (i == 32) {
            return Assets.instance.trafficLight.greenArrow;
        }
        if (i == 34) {
            return Assets.instance.trafficLight.greenArrowContur;
        }
        if (i == 33) {
            return Assets.instance.trafficLight.greenArrowContur;
        }
        if (i == 35) {
            return Assets.instance.trafficLight.greenArrowOneDir;
        }
        if (i == 37) {
            return Assets.instance.trafficLight.greenArrowOneDirContur;
        }
        return null;
    }

    private TextureRegion initSection33() {
        this.secPos33 = new Vector2(this.position.x + this.dimension.x, this.position.y);
        this.secOrig33 = new Vector2(this.origin.x - this.dimension.x, this.origin.y);
        this.isLeftSec33 = false;
        int i = this.lightsConfig[8];
        if (i == -1) {
            return null;
        }
        if (i == 32) {
            return Assets.instance.trafficLight.greenArrow;
        }
        if (i == 34) {
            return Assets.instance.trafficLight.greenArrowContur;
        }
        if (i == 36) {
            return Assets.instance.trafficLight.greenArrowOneDir;
        }
        if (i == 38) {
            return Assets.instance.trafficLight.greenArrowOneDirContur;
        }
        if (i == 12) {
            return Assets.instance.trafficLight.redArrow;
        }
        if (i == 14) {
            return Assets.instance.trafficLight.redArrowContur;
        }
        if (i == 16) {
            return Assets.instance.trafficLight.redArrowOneDir;
        }
        if (i == 18) {
            return Assets.instance.trafficLight.redArrowOneDirContur;
        }
        return null;
    }

    @Override
    public void update(float f) {
        super.update(f);
        float f2 = this.period - f;
        this.period = f2;
        if (f2 < 0.0f) {
            this.period = 0.4f;
            if (this.isFlashing) {
                this.isFlashing = false;
            } else {
                this.isFlashing = true;
            }
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        TextureRegion textureRegion = this.section11;
        if (textureRegion != null) {
            spriteBatch.draw(textureRegion.getTexture(), this.secPos11.x, this.secPos11.y, this.secOrig11.x, this.secOrig11.y, this.dimension.x, this.dimension.y, this.scale.x, this.scale.y, this.rotation, this.section11.getRegionX(), this.section11.getRegionY(), this.section11.getRegionWidth(), this.section11.getRegionHeight(), this.isLeftSec11, false);
        }
        TextureRegion textureRegion2 = this.section12;
        if (textureRegion2 != null) {
            spriteBatch.draw(textureRegion2.getTexture(), this.secPos12.x, this.secPos12.y, this.secOrig12.x, this.secOrig12.y, this.dimension.x, this.dimension.y, this.scale.x, this.scale.y, this.rotation, this.section12.getRegionX(), this.section12.getRegionY(), this.section12.getRegionWidth(), this.section12.getRegionHeight(), this.isLeftSec12, false);
        }
        TextureRegion textureRegion3 = this.section13;
        if (textureRegion3 != null) {
            spriteBatch.draw(textureRegion3.getTexture(), this.secPos13.x, this.secPos13.y, this.secOrig13.x, this.secOrig13.y, this.dimension.x, this.dimension.y, this.scale.x, this.scale.y, this.rotation, this.section13.getRegionX(), this.section13.getRegionY(), this.section13.getRegionWidth(), this.section13.getRegionHeight(), this.isLeftSec13, false);
        }
        TextureRegion textureRegion4 = this.section21;
        if (textureRegion4 != null) {
            spriteBatch.draw(textureRegion4.getTexture(), this.secPos21.x, this.secPos21.y, this.secOrig21.x, this.secOrig21.y, this.dimension.x, this.dimension.y, this.scale.x, this.scale.y, this.rotation, this.section21.getRegionX(), this.section21.getRegionY(), this.section21.getRegionWidth(), this.section21.getRegionHeight(), this.isLeftSec21, false);
        }
        TextureRegion textureRegion5 = this.section22;
        if (textureRegion5 != null) {
            if (!this.isFlashingYellow) {
                spriteBatch.draw(textureRegion5.getTexture(), this.secPos22.x, this.secPos22.y, this.secOrig22.x, this.secOrig22.y, this.dimension.x, this.dimension.y, this.scale.x, this.scale.y, this.rotation, this.section22.getRegionX(), this.section22.getRegionY(), this.section22.getRegionWidth(), this.section22.getRegionHeight(), this.isLeftSec22, false);
            } else if (this.isFlashing) {
                TextureAtlas.AtlasRegion atlasRegion = Assets.instance.trafficLight.yellowReg;
                this.section22 = atlasRegion;
                spriteBatch.draw(atlasRegion.getTexture(), this.secPos22.x, this.secPos22.y, this.secOrig22.x, this.secOrig22.y, this.dimension.x, this.dimension.y, this.scale.x, this.scale.y, this.rotation, this.section22.getRegionX(), this.section22.getRegionY(), this.section22.getRegionWidth(), this.section22.getRegionHeight(), this.isLeftSec22, false);
            } else {
                TextureAtlas.AtlasRegion atlasRegion2 = Assets.instance.trafficLight.emptyReg;
                this.section22 = atlasRegion2;
                spriteBatch.draw(atlasRegion2.getTexture(), this.secPos22.x, this.secPos22.y, this.secOrig22.x, this.secOrig22.y, this.dimension.x, this.dimension.y, this.scale.x, this.scale.y, this.rotation, this.section22.getRegionX(), this.section22.getRegionY(), this.section22.getRegionWidth(), this.section22.getRegionHeight(), this.isLeftSec22, false);
            }
        }
        TextureRegion textureRegion6 = this.section23;
        if (textureRegion6 != null) {
            spriteBatch.draw(textureRegion6.getTexture(), this.secPos23.x, this.secPos23.y, this.secOrig23.x, this.secOrig23.y, this.dimension.x, this.dimension.y, this.scale.x, this.scale.y, this.rotation, this.section23.getRegionX(), this.section23.getRegionY(), this.section23.getRegionWidth(), this.section23.getRegionHeight(), this.isLeftSec23, false);
        }
        TextureRegion textureRegion7 = this.section31;
        if (textureRegion7 != null) {
            spriteBatch.draw(textureRegion7.getTexture(), this.secPos31.x, this.secPos31.y, this.secOrig31.x, this.secOrig31.y, this.dimension.x, this.dimension.y, this.scale.x, this.scale.y, this.rotation, this.section31.getRegionX(), this.section31.getRegionY(), this.section31.getRegionWidth(), this.section31.getRegionHeight(), this.isLeftSec31, false);
        }
        TextureRegion textureRegion8 = this.section32;
        if (textureRegion8 != null) {
            spriteBatch.draw(textureRegion8.getTexture(), this.position.x, this.position.y, this.origin.x, this.origin.y, this.dimension.x, this.dimension.y, this.scale.x, this.scale.y, this.rotation, this.section32.getRegionX(), this.section32.getRegionY(), this.section32.getRegionWidth(), this.section32.getRegionHeight(), false, false);
        }
        TextureRegion textureRegion9 = this.section33;
        if (textureRegion9 != null) {
            spriteBatch.draw(textureRegion9.getTexture(), this.secPos33.x, this.secPos33.y, this.secOrig33.x, this.secOrig33.y, this.dimension.x, this.dimension.y, this.scale.x, this.scale.y, this.rotation, this.section33.getRegionX(), this.section33.getRegionY(), this.section33.getRegionWidth(), this.section33.getRegionHeight(), this.isLeftSec33, false);
        }
        TextureRegion textureRegion10 = this.whiteSection11;
        if (textureRegion10 != null) {
            spriteBatch.draw(textureRegion10.getTexture(), this.whiteSecPos11.x, this.whiteSecPos11.y, this.whiteSecOrig11.x, this.whiteSecOrig11.y, this.dimension.x, this.dimension.y, this.scale.x, this.scale.y, this.rotation, this.whiteSection11.getRegionX(), this.whiteSection11.getRegionY(), this.whiteSection11.getRegionWidth(), this.whiteSection11.getRegionHeight(), false, false);
        }
        TextureRegion textureRegion11 = this.whiteSection12;
        if (textureRegion11 != null) {
            spriteBatch.draw(textureRegion11.getTexture(), this.whiteSecPos12.x, this.whiteSecPos12.y, this.whiteSecOrig12.x, this.whiteSecOrig12.y, this.dimension.x, this.dimension.y, this.scale.x, this.scale.y, this.rotation, this.whiteSection12.getRegionX(), this.whiteSection12.getRegionY(), this.whiteSection12.getRegionWidth(), this.whiteSection12.getRegionHeight(), false, false);
        }
        TextureRegion textureRegion12 = this.whiteSection13;
        if (textureRegion12 != null) {
            spriteBatch.draw(textureRegion12.getTexture(), this.whiteSecPos13.x, this.whiteSecPos13.y, this.whiteSecOrig13.x, this.whiteSecOrig13.y, this.dimension.x, this.dimension.y, this.scale.x, this.scale.y, this.rotation, this.whiteSection13.getRegionX(), this.whiteSection13.getRegionY(), this.whiteSection13.getRegionWidth(), this.whiteSection13.getRegionHeight(), false, false);
        }
        TextureRegion textureRegion13 = this.whiteSection22;
        if (textureRegion13 != null) {
            spriteBatch.draw(textureRegion13.getTexture(), this.whiteSecPos22.x, this.whiteSecPos22.y, this.whiteSecOrig22.x, this.whiteSecOrig22.y, this.dimension.x, this.dimension.y, this.scale.x, this.scale.y, this.rotation, this.whiteSection22.getRegionX(), this.whiteSection22.getRegionY(), this.whiteSection22.getRegionWidth(), this.whiteSection22.getRegionHeight(), false, false);
        }
    }

    public void setRotation(float f) {
        this.rotation = f;
    }
}
