package de.caffeineaddicted.ld36prep.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import de.caffeineaddicted.ld36prep.LD36Prep;
import de.caffeineaddicted.ld36prep.units.*;
import de.caffeineaddicted.sgl.ui.screens.SGLScreen;

import java.util.Iterator;

/**
 * @author Malte Heinzelmann
 */
public class InGameScreen extends SGLScreen<LD36Prep> {

    private Stage stage;
    Drawable test;

    public InGameScreen(LD36Prep game) {
        super(game);
    }

    public void create() {
        game.debug("Creating InGameScreen");
        stage = new Stage();

        UnitEnemy unit1 = new UnitEnemy(game, UnitEnemy.Type.FEGGIT1);

        UnitEnemy unit2 = new UnitEnemy(game, UnitEnemy.Type.FEGGIT2);
        unit2.translateY(-100);

        UnitTower unit3 = new UnitTower(game, UnitTower.Type.FEGGIT3);
        unit3.translate(300, 300);

    }

    public void render(float delta) {
        for (UnitBase unit : UnitBase.units) {
            unit.tick(delta);
        }

        Iterator<Projectile> projectileIterator = Projectile.activeProjectiles.iterator();
        while (projectileIterator.hasNext()) {
            Projectile projectile = projectileIterator.next();
            boolean todelete = projectile.tick(delta);
            if (todelete)
                projectileIterator.remove();
        }
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);


        game.getBatch().begin();
        game.getBatch().setColor(new Color(1, 1, 1, 1));
        for (UnitBase unit : UnitBase.units) {
            unit.draw(game.getBatch());
        }

        for (Projectile projectile : Projectile.activeProjectiles) {
            projectile.draw(game.getBatch());
        }
        game.getBatch().end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

    }
}