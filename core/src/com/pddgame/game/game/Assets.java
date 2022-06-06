package com.pddgame.game.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.ObjectSet;


/**
 *
 * Подключение всех текстур
 * Назначение анимаций
 * Назначение музыки
 * Назначение звуков
 *
 */
public class Assets implements Disposable, AssetErrorListener {
    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();
    public AssetManager assetManager;
    public I18NBundle bundle;
    public String buy;
    public AssetCar car;
    public AssetFonts fonts;
    public String help_info;
    public AssetLevelDecoration levelDecoration;
    public AssetMusic music;
    public AssetPedestrian pedestrian;
    public AssetRoadSigns roadSigns;
    public AssetSounds sounds;
    public String touch_screen;
    public AssetTrafficLight trafficLight;
    public String unsuccess;

    private Assets() {
    }

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        Gdx.app.log(TAG, assetManager.toString());
        assetManager.setErrorListener(this);
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assetManager.load("images/crossroad.pack.atlas", TextureAtlas.class);
        assetManager.load("sounds/click.ogg", Sound.class);
        assetManager.load("sounds/car_moving.ogg", Sound.class);
        assetManager.load("sounds/car_accident.ogg", Sound.class);
        assetManager.load("sounds/tram_sound.ogg", Sound.class);
        assetManager.load("sounds/bicycle.ogg", Sound.class);
        assetManager.load("sounds/steps.ogg", Sound.class);
        assetManager.load("music/alarm.mp3", Music.class);
        assetManager.load("strings/strings", I18NBundle.class);
        assetManager.finishLoading();
        initStrings();
        Application application = Gdx.app;
        String str = TAG;
        application.debug(str, "# of assets loaded: " + assetManager.getAssetNames().size);
        int i = assetManager.getAssetNames().size;
        for (int i2 = 0; i2 < i; i2++) {
            Application application2 = Gdx.app;
            String str2 = TAG;
            application2.debug(str2, "asset: " + assetManager.getAssetNames().get(i2));
        }
        TextureAtlas textureAtlas = (TextureAtlas) assetManager.get("images/crossroad.pack.atlas");
        ObjectSet.ObjectSetIterator<Texture> it = textureAtlas.getTextures().iterator();
        while (it.hasNext()) {
            it.next().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        this.sounds = new AssetSounds(assetManager);
        this.music = new AssetMusic(assetManager);
        this.fonts = new AssetFonts();
        this.levelDecoration = new AssetLevelDecoration(textureAtlas);
        this.car = new AssetCar(textureAtlas);
        this.trafficLight = new AssetTrafficLight(textureAtlas);
        this.roadSigns = new AssetRoadSigns(textureAtlas);
        this.pedestrian = new AssetPedestrian(textureAtlas);

    }

    private void initStrings() {
        I18NBundle i18NBundle = (I18NBundle) this.assetManager.get("strings/strings", I18NBundle.class);
        this.bundle = i18NBundle;
        this.touch_screen = this.bundle.get("touch_screen");
        this.unsuccess = this.bundle.get("unsuccess");
        this.help_info = this.bundle.get("help_info");
    }

    @Override
    public void dispose() {
        this.assetManager.dispose();
        this.fonts.defaultSmall.dispose();
    }

    public void error(String str, Class cls, Throwable th) {
        Application application = Gdx.app;
        String str2 = TAG;
        application.error(str2, "Couldn't load asset '" + str + "'", (Exception) th);
    }

    @Override
    public void error(AssetDescriptor assetDescriptor, Throwable th) {
        Application application = Gdx.app;
        String str = TAG;
        application.error(str, "Couldn't load asset '" + assetDescriptor.fileName + "'", (Exception) th);
    }


    public class AssetFonts {
        public final BitmapFont defaultSmall;

        public AssetFonts() {
            BitmapFont bitmapFont = new BitmapFont(Gdx.files.internal("images/troika.fnt"), true);
            this.defaultSmall = bitmapFont;
            bitmapFont.getData().setScale(0.48000002f);
            this.defaultSmall.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
    }


    public class AssetSounds {
        public final Sound accident;
        public final Sound bicycle;
        public final Sound click;
        public final Sound move;
        public final Sound steps;
        public final Sound tram;

        public AssetSounds(AssetManager assetManager) {
            this.click = (Sound) assetManager.get("sounds/click.ogg", Sound.class);
            this.move = (Sound) assetManager.get("sounds/car_moving.ogg", Sound.class);
            this.accident = (Sound) assetManager.get("sounds/car_accident.ogg", Sound.class);
            this.tram = (Sound) assetManager.get("sounds/tram_sound.ogg", Sound.class);
            this.bicycle = (Sound) assetManager.get("sounds/bicycle.ogg", Sound.class);
            this.steps = (Sound) assetManager.get("sounds/steps.ogg", Sound.class);
        }
    }


    public class AssetMusic {
        public final Music alarmSnd;

        public AssetMusic(AssetManager assetManager) {
            this.alarmSnd = (Music) assetManager.get("music/alarm.mp3", Music.class);
        }
    }


    public class AssetLevelDecoration {
        public AssetLevelDecoration(TextureAtlas textureAtlas) {
        }
    }


    public class AssetCar {
        public final TextureAtlas.AtlasRegion bicShadow;
        public final Animation bicycleAnim;
        public final TextureAtlas.AtlasRegion bicycle_reg;
        public final TextureAtlas.AtlasRegion bus_acc_reg;
        public final TextureAtlas.AtlasRegion bus_left_reg;
        public final TextureAtlas.AtlasRegion bus_reg;
        public final TextureAtlas.AtlasRegion bus_right_reg;
        public final TextureAtlas.AtlasRegion bus_shadow_reg;
        public final TextureAtlas.AtlasRegion car_1_acc_reg;
        public final TextureAtlas.AtlasRegion car_1_left_reg;
        public final TextureAtlas.AtlasRegion car_1_reg;
        public final TextureAtlas.AtlasRegion car_1_right_reg;
        public final TextureAtlas.AtlasRegion car_1_shadow_reg;
        public final TextureAtlas.AtlasRegion car_2_acc_reg;
        public final TextureAtlas.AtlasRegion car_2_left_reg;
        public final TextureAtlas.AtlasRegion car_2_reg;
        public final TextureAtlas.AtlasRegion car_2_right_reg;
        public final TextureAtlas.AtlasRegion car_2_shadow_reg;
        public final TextureAtlas.AtlasRegion car_3_acc_reg;
        public final TextureAtlas.AtlasRegion car_3_left_reg;
        public final TextureAtlas.AtlasRegion car_3_reg;
        public final TextureAtlas.AtlasRegion car_3_right_reg;
        public final TextureAtlas.AtlasRegion car_3_shadow_reg;
        public final TextureAtlas.AtlasRegion police_car_acc_reg;
        public final TextureAtlas.AtlasRegion police_car_alarm_reg;
        public final TextureAtlas.AtlasRegion police_car_left_reg;
        public final TextureAtlas.AtlasRegion police_car_reg;
        public final TextureAtlas.AtlasRegion police_car_right_reg;
        public final TextureAtlas.AtlasRegion tram_acc_reg;
        public final TextureAtlas.AtlasRegion tram_left_reg;
        public final TextureAtlas.AtlasRegion tram_reg;
        public final TextureAtlas.AtlasRegion tram_right_reg;
        public final TextureAtlas.AtlasRegion tram_shadow_reg;
        public final TextureAtlas.AtlasRegion truck_acc_reg;
        public final TextureAtlas.AtlasRegion truck_al_reg;
        public final TextureAtlas.AtlasRegion truck_left_reg;
        public final TextureAtlas.AtlasRegion truck_reg;
        public final TextureAtlas.AtlasRegion truck_right_reg;
        public final TextureAtlas.AtlasRegion truck_shadow_reg;

        public AssetCar(TextureAtlas textureAtlas) {
            this.car_1_reg = textureAtlas.findRegion("car", 1);
            this.car_1_left_reg = textureAtlas.findRegion("car_1_left");
            this.car_1_right_reg = textureAtlas.findRegion("car_1_right");
            this.car_1_acc_reg = textureAtlas.findRegion("car_1_accident");
            this.car_1_shadow_reg = textureAtlas.findRegion("car_1_shadow");
            this.car_2_reg = textureAtlas.findRegion("car", 2);
            this.car_2_left_reg = textureAtlas.findRegion("car_2_left");
            this.car_2_right_reg = textureAtlas.findRegion("car_2_right");
            this.car_2_acc_reg = textureAtlas.findRegion("car_2_accident");
            this.car_2_shadow_reg = textureAtlas.findRegion("car_2_shadow");
            this.car_3_reg = textureAtlas.findRegion("car", 3);
            this.car_3_left_reg = textureAtlas.findRegion("car_3_left");
            this.car_3_right_reg = textureAtlas.findRegion("car_3_right");
            this.car_3_acc_reg = textureAtlas.findRegion("car_3_accident");
            this.car_3_shadow_reg = textureAtlas.findRegion("car_3_shadow");
            this.bus_reg = textureAtlas.findRegion("bus");
            this.bus_left_reg = textureAtlas.findRegion("bus_left");
            this.bus_right_reg = textureAtlas.findRegion("bus_right");
            this.bus_acc_reg = textureAtlas.findRegion("bus_accident");
            this.bus_shadow_reg = textureAtlas.findRegion("bus_shadow");
            this.police_car_reg = textureAtlas.findRegion("police_car");
            this.police_car_left_reg = textureAtlas.findRegion("police_car_left");
            this.police_car_right_reg = textureAtlas.findRegion("police_car_right");
            this.police_car_acc_reg = textureAtlas.findRegion("police_car_accident");
            this.police_car_alarm_reg = textureAtlas.findRegion("police_car_alarm");
            this.truck_reg = textureAtlas.findRegion("truck_car");
            this.truck_left_reg = textureAtlas.findRegion("truck_car_left");
            this.truck_right_reg = textureAtlas.findRegion("truck_car_right");
            this.truck_acc_reg = textureAtlas.findRegion("truck_car_accident");
            this.truck_al_reg = textureAtlas.findRegion("truck_car_alarm");
            this.truck_shadow_reg = textureAtlas.findRegion("truck_car_shadow");
            this.tram_reg = textureAtlas.findRegion("tram");
            this.tram_left_reg = textureAtlas.findRegion("tram_left");
            this.tram_right_reg = textureAtlas.findRegion("tram_right");
            this.tram_acc_reg = textureAtlas.findRegion("tram_accident");
            this.tram_shadow_reg = textureAtlas.findRegion("tram_shadow");
            this.bicycle_reg = textureAtlas.findRegion("bicycle", 1);
            this.bicShadow = textureAtlas.findRegion("bic_shadow");
            this.bicycleAnim = new Animation(0.33333334f, textureAtlas.findRegions("bicycle"), Animation.PlayMode.LOOP);
        }
    }


    public class AssetTrafficLight {
        public final TextureAtlas.AtlasRegion emptyReg;
        public final TextureAtlas.AtlasRegion greenArrow;
        public final TextureAtlas.AtlasRegion greenArrowContur;
        public final TextureAtlas.AtlasRegion greenArrowOneDir;
        public final TextureAtlas.AtlasRegion greenArrowOneDirContur;
        public final TextureAtlas.AtlasRegion greenReg;
        public final TextureAtlas.AtlasRegion redArrow;
        public final TextureAtlas.AtlasRegion redArrowContur;
        public final TextureAtlas.AtlasRegion redArrowOneDir;
        public final TextureAtlas.AtlasRegion redArrowOneDirContur;
        public final TextureAtlas.AtlasRegion redReg;
        public final TextureAtlas.AtlasRegion whiteReg;
        public final TextureAtlas.AtlasRegion yellowArrow;
        public final TextureAtlas.AtlasRegion yellowArrowContur;
        public final TextureAtlas.AtlasRegion yellowArrowOneDir;
        public final TextureAtlas.AtlasRegion yellowArrowOneDirContur;
        public final TextureAtlas.AtlasRegion yellowReg;

        public AssetTrafficLight(TextureAtlas textureAtlas) {
            this.emptyReg = textureAtlas.findRegion("empty_section");
            this.whiteReg = textureAtlas.findRegion("white_section");
            this.redReg = textureAtlas.findRegion("red_section");
            this.redArrow = textureAtlas.findRegion("red_arrow");
            this.redArrowOneDir = textureAtlas.findRegion("red_arrow_one_dir");
            this.redArrowContur = textureAtlas.findRegion("red_arrow_contur");
            this.redArrowOneDirContur = textureAtlas.findRegion("red_arrow_one_dir_contur");
            this.yellowReg = textureAtlas.findRegion("yellow_section");
            this.yellowArrow = textureAtlas.findRegion("yellow_arrow");
            this.yellowArrowOneDir = textureAtlas.findRegion("yellow_arrow_one_dir");
            this.yellowArrowContur = textureAtlas.findRegion("yellow_arrow_contur");
            this.yellowArrowOneDirContur = textureAtlas.findRegion("yellow_arrow_one_dir_contur");
            this.greenReg = textureAtlas.findRegion("green_section");
            this.greenArrow = textureAtlas.findRegion("green_arrow");
            this.greenArrowOneDir = textureAtlas.findRegion("green_arrow_one_dir");
            this.greenArrowContur = textureAtlas.findRegion("green_arrow_contur");
            this.greenArrowOneDirContur = textureAtlas.findRegion("green_arrow_one_dir_contur");
        }
    }


    public class AssetRoadSigns {
        public final TextureAtlas.AtlasRegion circularMoveReg;
        public final TextureAtlas.AtlasRegion deadEndReg;
        public final TextureAtlas.AtlasRegion directionMainRoadReg;
        public final TextureAtlas.AtlasRegion directionMainRoadThreeReg;
        public final TextureAtlas.AtlasRegion exitOneWayMoveReg;
        public final TextureAtlas.AtlasRegion giveWayReg;
        public final TextureAtlas.AtlasRegion mainRoadReg;
        public final TextureAtlas.AtlasRegion mainRoadThreeLeftReg;
        public final TextureAtlas.AtlasRegion mainRoadThreeRightReg;
        public final TextureAtlas.AtlasRegion motorWayReg;
        public final TextureAtlas.AtlasRegion movementBandsLeftReg;
        public final TextureAtlas.AtlasRegion movementBandsReg;
        public final TextureAtlas.AtlasRegion notMainRoadThreeReg;
        public final TextureAtlas.AtlasRegion stopReg;
        public final TextureAtlas.AtlasRegion straightMoveReg;

        public AssetRoadSigns(TextureAtlas textureAtlas) {
            this.mainRoadReg = textureAtlas.findRegion("main_road");
            this.directionMainRoadReg = textureAtlas.findRegion("direction_main_road");
            this.directionMainRoadThreeReg = textureAtlas.findRegion("direction_main_road_three");
            this.mainRoadThreeLeftReg = textureAtlas.findRegion("main_road_three_left");
            this.mainRoadThreeRightReg = textureAtlas.findRegion("main_road_three_right");
            this.notMainRoadThreeReg = textureAtlas.findRegion("not_main_road_three");
            this.giveWayReg = textureAtlas.findRegion("give_way");
            this.motorWayReg = textureAtlas.findRegion("motorway");
            this.movementBandsReg = textureAtlas.findRegion("movement_bands");
            this.movementBandsLeftReg = textureAtlas.findRegion("movement_bands_left");
            this.stopReg = textureAtlas.findRegion("stop");
            this.straightMoveReg = textureAtlas.findRegion("straight_movement");
            this.circularMoveReg = textureAtlas.findRegion("circular_movement");
            this.deadEndReg = textureAtlas.findRegion("dead_end");
            this.exitOneWayMoveReg = textureAtlas.findRegion("exit_one_way_road");
        }
    }


    public class AssetPedestrian {
        public final Animation ped_1_Anim;
        public final TextureAtlas.AtlasRegion ped_1_Reg;
        public final Animation ped_2_Anim;
        public final TextureAtlas.AtlasRegion ped_2_Reg;
        public final TextureAtlas.AtlasRegion ped_marker;
        public final TextureAtlas.AtlasRegion ped_shadow;

        public AssetPedestrian(TextureAtlas textureAtlas) {
            this.ped_shadow = textureAtlas.findRegion("ped_shadow");
            Array<TextureAtlas.AtlasRegion> findRegions = textureAtlas.findRegions("ped");
            findRegions.add(textureAtlas.findRegion("ped", 2));
            this.ped_1_Anim = new Animation(0.2f, findRegions, Animation.PlayMode.LOOP);
            this.ped_1_Reg = textureAtlas.findRegion("ped", 2);
            Array<TextureAtlas.AtlasRegion> findRegions2 = textureAtlas.findRegions("pedestr");
            findRegions2.add(textureAtlas.findRegion("pedestr", 2));
            this.ped_2_Anim = new Animation(0.2f, findRegions2, Animation.PlayMode.LOOP);
            this.ped_2_Reg = textureAtlas.findRegion("pedestr", 1);
            this.ped_marker = textureAtlas.findRegion("pad_marker");
        }
    }
}
