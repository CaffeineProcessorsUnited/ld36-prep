package de.caffeineaddicted.ld36prep;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.caffeineaddicted.ld36prep.input.InGameInputProcessor;
import de.caffeineaddicted.ld36prep.messages.FinishedLoadingMessage;
import de.caffeineaddicted.ld36prep.screens.BackgroundScreen;
import de.caffeineaddicted.ld36prep.screens.InGameScreen;
import de.caffeineaddicted.ld36prep.screens.LoadingScreen;
import de.caffeineaddicted.ld36prep.util.Assets;
import de.caffeineaddicted.sgl.SGLGame;
import de.caffeineaddicted.sgl.impl.input.GlobalInputProcessor;
import de.caffeineaddicted.sgl.impl.messages.DefaultMessage;
import de.caffeineaddicted.sgl.messages.Message;
import de.caffeineaddicted.sgl.ui.screens.SGLRootScreen;
import de.caffeineaddicted.sgl.ui.screens.SGLScreen;

public class LD36Prep extends SGLGame {

    private SpriteBatch batch;
    private ShapeRenderer shape;
    private Assets assets;
    private Music theme;
    private InGameInputProcessor inputProcessor;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Gdx.app.LOG_DEBUG);
        batch = new SpriteBatch();
        shape = new ShapeRenderer();
        assets = new Assets();
        super.create();
    }

	@Override
	protected void initScreens() {
        rootScreen.loadScreen(new LoadingScreen(this));
        rootScreen.loadScreen(new BackgroundScreen(this));

        InGameScreen ingamescreen = new InGameScreen(this);
        rootScreen.loadScreen(ingamescreen);
        inputProcessor = new InGameInputProcessor(this,ingamescreen);

        rootScreen.showScreen(BackgroundScreen.class, SGLRootScreen.ZINDEX.FAREST);
        rootScreen.showScreen(LoadingScreen.class, SGLRootScreen.ZINDEX.FAR);
        screenInput.addProcessor(ingamescreen,inputProcessor);
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
}
