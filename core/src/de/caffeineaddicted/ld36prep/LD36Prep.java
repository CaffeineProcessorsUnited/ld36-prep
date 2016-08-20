package de.caffeineaddicted.ld36prep;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.caffeineaddicted.ld36prep.messages.FinishedLoadingMessage;
import de.caffeineaddicted.ld36prep.screens.BackgroundScreen;
import de.caffeineaddicted.ld36prep.screens.InGameScreen;
import de.caffeineaddicted.ld36prep.screens.LoadingScreen;
import de.caffeineaddicted.ld36prep.util.Assets;
import de.caffeineaddicted.sgl.SGLGame;
import de.caffeineaddicted.sgl.impl.messages.DefaultMessage;
import de.caffeineaddicted.sgl.messages.Message;
import de.caffeineaddicted.sgl.ui.screens.SGLRootScreen;

public class LD36Prep extends SGLGame {
    protected static final ApplicationConfiguration applicationConfiguration
            = new ApplicationConfiguration()
            .set(ApplicationConfiguration.Attribute.WIDTH, 720)
            .set(ApplicationConfiguration.Attribute.HEIGHT, 720);

    private SpriteBatch batch;
    private ShapeRenderer shape;
    private Assets assets;
    private Music theme;
    private Camera camera;
    private Matrix4 idMatrix = new Matrix4();

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
        super.create();
    }

	@Override
	protected void initScreens() {
        rootScreen.loadScreen(new LoadingScreen(this));
        rootScreen.loadScreen(new BackgroundScreen(this));
        rootScreen.loadScreen(new InGameScreen(this));

        rootScreen.showScreen(BackgroundScreen.class, SGLRootScreen.ZINDEX.FAREST);
        rootScreen.showScreen(LoadingScreen.class, SGLRootScreen.ZINDEX.FAR);
	}

    @Override
    public Viewport createViewport() {
        return new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
            theme.play();
            //message(new ShowMainMenuMessage());
            rootScreen.hideScreen(LoadingScreen.class);
            rootScreen.showScreen(InGameScreen.class, SGLRootScreen.ZINDEX.NEAREST);
        }
        log(message.getClass().getSimpleName());
	}

    @Override
	public String getLogTag(String sub) {
		return "LD36 " + (!sub.isEmpty() ? ":" + sub : "");
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

    public static final ApplicationConfiguration getConfig() {
        if (applicationConfiguration == null) {
            throw new RuntimeException("Provide a configuration!");
        }
        return applicationConfiguration;
    }

    public Matrix4 getIdMatrix() {
        return idMatrix;
    }
}
