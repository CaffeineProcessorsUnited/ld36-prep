package de.caffeineaddicted.ld36prep.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import de.caffeineaddicted.ld36prep.LD36Prep;
import de.caffeineaddicted.sgl.ui.screens.SGLScreen;

/**
 * @author Malte Heinzelmann
 */
public class BackgroundScreen extends SGLScreen<LD36Prep> {

    private Sprite background;
    private boolean dimmBackground = true;

    public BackgroundScreen(LD36Prep game) {
        super(game);
    }

    public void setDimmBackground(boolean dimmBackground) {
        this.dimmBackground = dimmBackground;
    }

    public boolean getDimmBackground() {
        return dimmBackground;
    }

    public void create() {
        game.debug("Creating BackgroundScreen");
        Texture texBackground = new Texture("badlogic.jpg");
        texBackground.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        background = new Sprite(texBackground);
        background.setSize(game.getCamera().viewportWidth, game.getCamera().viewportHeight);
    }

    public void render(float delta) {
        game.getBatch().begin();
        background.draw(game.getBatch());
        game.getBatch().end();
        if (dimmBackground) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            game.getShape().begin(ShapeRenderer.ShapeType.Filled);
            game.getShape().setColor(0f, 0f, 0f, 0.6f);
            game.getShape().rect(game.getCamera().position.x - (game.getCamera().viewportWidth / 2), game.getCamera().position.y - (game.getCamera().viewportHeight / 2), game.getCamera().viewportWidth, game.getCamera().viewportHeight);
            game.getShape().end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }

}