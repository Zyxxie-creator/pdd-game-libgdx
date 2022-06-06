package com.pddgame.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.pddgame.game.game.objects.CarInterface;
import com.pddgame.game.utils.GamePreferences;


/**
 *
 * Рендер уровня
 * Отрисовка камеры
 *
 */
public class WorldRenderer implements Disposable {
    private static final boolean DEBUG_DRAW_BOX2D_WORLD = false;
    public static OrthographicCamera camera;
    public static OrthographicCamera cameraGUI;
    private SpriteBatch batch;
    private Box2DDebugRenderer box2DDebugRenderer;
    private float mapUnitScale;
    private GamePreferences prefs;
    private ShapeRenderer shapeRenderer;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private WorldController worldController;

    public WorldRenderer(WorldController worldController) {
        this.worldController = worldController;
        init();
    }

    private void init() {
        this.mapUnitScale = 0.027777778f;
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        OrthographicCamera orthographicCamera = new OrthographicCamera(5.0f, 5.0f);
        camera = orthographicCamera;
        orthographicCamera.position.set(0.0f, 0.0f, 0.0f);
        OrthogonalTiledMapRenderer orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(this.worldController.level.roadMap, this.mapUnitScale);
        this.tiledMapRenderer = orthogonalTiledMapRenderer;
        orthogonalTiledMapRenderer.setView(camera);
        camera.update();
        OrthographicCamera orthographicCamera2 = new OrthographicCamera(480.0f, 800.0f);
        cameraGUI = orthographicCamera2;
        orthographicCamera2.position.set(0.0f, 0.0f, 0.0f);
        cameraGUI.setToOrtho(true);
        cameraGUI.update();
        this.prefs = GamePreferences.instance;
        this.box2DDebugRenderer = new Box2DDebugRenderer();
    }

    public void render() {
        renderWorld(this.batch);
    }

    private void renderWorld(SpriteBatch spriteBatch) {
        this.worldController.cameraHelper.applyTo(camera);
        spriteBatch.setProjectionMatrix(camera.combined);
        this.tiledMapRenderer.setView(camera);
        this.tiledMapRenderer.render();
        spriteBatch.begin();
        this.worldController.level.render(spriteBatch);
        spriteBatch.end();
    }

    private void renderGui(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(cameraGUI.combined);
        spriteBatch.begin();
        spriteBatch.end();
    }

    private void renderDebug() {
        this.shapeRenderer.setProjectionMatrix(camera.combined);
        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        this.shapeRenderer.setColor(Color.RED);
        this.worldController.level.renderWorldDebug(this.shapeRenderer);
        this.shapeRenderer.setColor(Color.GREEN);
        this.shapeRenderer.polygon(this.worldController.f279p1.getTransformedVertices());
        this.shapeRenderer.end();
    }

    private void renderDebugUI() {
        this.shapeRenderer.setProjectionMatrix(cameraGUI.combined);
        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        this.shapeRenderer.setColor(Color.RED);
        this.worldController.level.renderDebugGun(this.shapeRenderer);
        this.shapeRenderer.end();
    }

    public void resize(int i, int i2) {
        float f = i2;
        float f2 = i;
        camera.viewportWidth = (5.0f / f) * f2;
        camera.update();
        cameraGUI.viewportHeight = 800.0f;
        cameraGUI.viewportWidth = (800.0f / f) * f2;
        cameraGUI.position.set(cameraGUI.viewportWidth / 2.0f, cameraGUI.viewportHeight / 2.0f, 0.0f);
        cameraGUI.update();
    }

    public static Vector3 unprojectCoords(Vector3 vector3, float f, float f2, float f3, float f4) {
        return camera.unproject(vector3, f, f2, f3, f4);
    }

    public static Vector3 unprojectCoords(Vector3 vector3) {
        return camera.unproject(vector3);
    }

    public static Vector3 unprojectCoordsUI(Vector3 vector3, float f, float f2, float f3, float f4) {
        return cameraGUI.unproject(vector3, f, f2, f3, f4);
    }

    public static Vector3 unprojectCoordsUI(Vector3 vector3) {
        return cameraGUI.unproject(vector3);
    }

    private void renderGuiFpsCounter(SpriteBatch spriteBatch) {
        float f = cameraGUI.viewportWidth - (cameraGUI.viewportWidth - 50.0f);
        int framesPerSecond = Gdx.graphics.getFramesPerSecond();
        BitmapFont bitmapFont = Assets.instance.fonts.defaultSmall;
        if (framesPerSecond >= 45) {
            bitmapFont.setColor(0.0f, 1.0f, 0.0f, 1.0f);
        } else if (framesPerSecond >= 30) {
            bitmapFont.setColor(1.0f, 1.0f, 0.0f, 1.0f);
        } else {
            bitmapFont.setColor(1.0f, 0.0f, 0.0f, 1.0f);
        }
        bitmapFont.draw(spriteBatch, "FPS: " + framesPerSecond, f, 160.0f);
        bitmapFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void renderPath(ShapeRenderer shapeRenderer) {
        int i = this.worldController.level.ways.size;
        for (int i2 = 0; i2 < i; i2++) {
            Array<Vector2> array = this.worldController.level.ways.get(i2);
            CarInterface carInterface = this.worldController.level.cars.get(i2);
            int i3 = 0;
            while (i3 < array.size) {
                int i4 = i3 + 1;
                int i5 = i4 % array.size;
                if (i5 != 0 || !carInterface.getLinePath().isOpen()) {
                    shapeRenderer.line(array.get(i3), array.get(i5));
                }
                shapeRenderer.circle(array.get(i3).x, array.get(i3).y, 0.2f);
                i3 = i4;
            }
            shapeRenderer.circle(carInterface.getFollowPathSB().getInternalTargetPosition().x, carInterface.getFollowPathSB().getInternalTargetPosition().y, 0.4f);
        }
    }

    @Override
    public void dispose() {
        this.batch.dispose();
        this.shapeRenderer.dispose();
    }
}
