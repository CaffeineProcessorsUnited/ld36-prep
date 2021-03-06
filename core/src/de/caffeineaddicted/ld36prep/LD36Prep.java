package de.caffeineaddicted.ld36prep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.caffeineaddicted.ld36prep.input.GlobalInputProcessor;
import de.caffeineaddicted.ld36prep.input.InGameInputProcessor;
import de.caffeineaddicted.ld36prep.messages.ExitGameMessage;
import de.caffeineaddicted.ld36prep.messages.FinishedLoadingMessage;
import de.caffeineaddicted.ld36prep.messages.ToggleFullscreenMessage;
import de.caffeineaddicted.ld36prep.messages.TogglePauseGameMessage;
import de.caffeineaddicted.ld36prep.screens.BackgroundScreen;
import de.caffeineaddicted.ld36prep.screens.InGameScreen;
import de.caffeineaddicted.ld36prep.screens.LoadingScreen;
import de.caffeineaddicted.ld36prep.util.Assets;
import de.caffeineaddicted.sgl.SGLGame;
import de.caffeineaddicted.sgl.impl.messages.DefaultMessage;
import de.caffeineaddicted.sgl.input.SGLScreenInputMultiplexer;
import de.caffeineaddicted.sgl.messages.Message;
import de.caffeineaddicted.sgl.ui.screens.SGLRootScreen;
import de.caffeineaddicted.sgl.ui.screens.SGLScreen;
import de.caffeineaddicted.sgl.ApplicationConfiguration;

public class LD36Prep extends SGLGame {

    private SpriteBatch batch;
    private ShapeRenderer shape;
    private Assets assets;
    private Music theme;
    private Camera camera;
    private Matrix4 idMatrix = new Matrix4();
    private InGameInputProcessor inputProcessor;
    private boolean paused;

    @Override
    public void startGame() {

    }

    @Override
    public void create() {
        Gdx.app.setLogLevel(Gdx.app.LOG_DEBUG);
        batch = new SpriteBatch();
        shape = new ShapeRenderer();
        assets = new Assets();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shape.setProjectionMatrix(camera.combined);
        multiplexer.addProcessor(new GlobalInputProcessor(this));
        super.create();
    }

    @Override
    protected void initGame() {

    }

    @Override
    protected void initScreens() {
        provide(SGLRootScreen.class).loadScreen(new LoadingScreen(this));
        provide(SGLRootScreen.class).loadScreen(new BackgroundScreen(this));

        provide(SGLRootScreen.class).loadScreen(new InGameScreen(this));

        provide(SGLRootScreen.class).showScreen(BackgroundScreen.class, SGLRootScreen.ZINDEX.FAREST);
        provide(SGLRootScreen.class).showScreen(LoadingScreen.class, SGLRootScreen.ZINDEX.FAR);
    }

    @Override
    public void pause() {
        paused = true;
        if (theme != null) theme.pause();
        provide(SGLRootScreen.class).pause();
    }

    @Override
    public void resume() {
        paused = false;
        if (theme != null) theme.play();
        provide(SGLRootScreen.class).resume();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public Viewport createViewport() {
        return new ScreenViewport(getCamera());
    }

    @Override
    public void message(Message message) {
        if (message == null) {
            message = new DefaultMessage();
        }
        if (message.getClass() == FinishedLoadingMessage.class) {
            debug("Received message after finishing loading assets");
            theme = getAssets().get("theme.ogg", Music.class);
            theme.setLooping(true);
            if (!paused)
                theme.play();
            //message(new ShowMainMenuMessage());
            provide(SGLRootScreen.class).hideScreen(LoadingScreen.class);
            provide(SGLRootScreen.class).showScreen(InGameScreen.class, SGLRootScreen.ZINDEX.NEAR);
        }
        if (message.getClass() == ToggleFullscreenMessage.class) {
            if (Gdx.graphics.isFullscreen()) {
                Gdx.graphics.setWindowedMode(
                        this.config().get(ApplicationConfiguration.Attribute.WIDTH, Integer.class),
                        this.config().get(ApplicationConfiguration.Attribute.HEIGHT, Integer.class)
                );
            } else {
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
        }
        if (message.getClass() == TogglePauseGameMessage.class) {
            SGLScreen screen = provide(SGLRootScreen.class).get(InGameScreen.class);
            if (screen.isPaused()) {
                theme.play();
                screen.resume();
            } else {
                theme.pause();
                screen.pause();
            }
        }
        if (message.getClass() == ExitGameMessage.class) {
            Gdx.app.exit();
        }
        log(message.getClass().getSimpleName());
    }

    public ApplicationConfiguration config() {
        return new ApplicationConfiguration()
                .set(ApplicationConfiguration.Attribute.WIDTH, 1280)
                .set(ApplicationConfiguration.Attribute.HEIGHT, 720);
    }

    @Override
    public String getLogTag(String sub) {
        return "LD36" + (!sub.isEmpty() ? ":" + sub : "");
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public ShapeRenderer getShape() {
        return shape;
    }

    public Assets getAssets() {
        return assets;
    }

    public Camera getCamera() {
        return camera;
    }

    public Matrix4 getIdMatrix() {
        return idMatrix;
    }
}
