package de.caffeineaddicted.ld36prep.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.caffeineaddicted.ld36prep.LD36Prep;
import de.caffeineaddicted.ld36prep.units.Projectile;
import de.caffeineaddicted.ld36prep.units.UnitBase;
import de.caffeineaddicted.ld36prep.units.UnitEnemy;
import de.caffeineaddicted.sgl.ui.screens.SGLScreen;

/**
 * @author Malte Heinzelmann
 */
public class InGameScreen extends SGLScreen<LD36Prep> {

    private Texture texEnemy;
    private Texture texTower;

    public InGameScreen(LD36Prep game) {
        super(game);
    }

    public void create() {
        game.debug("Creating InGameScreen");
        texEnemy = new Texture("enemy.png");
        texTower = new Texture("tower.png");

        UnitBase unit = new UnitEnemy(1, 10);
        unit.setSize(32, 32);
        unit.setTexture(texEnemy);
    }

    public void render(float delta) {
        for (UnitBase unit : UnitBase.units) {
            game.warning("" + unit.getX() + "," + unit.getY());
            unit.tick(delta);
        }
        for (Projectile projectile : Projectile.activeProjectiles) {
            projectile.tick(delta);
        }

        SpriteBatch batch = game.getBatch();
        batch.begin();

        for (UnitBase unit : UnitBase.units) {
            unit.draw(batch);
        }
        for (Projectile projectile : Projectile.activeProjectiles) {
            projectile.draw(batch);
        }

        batch.end();
    }
}