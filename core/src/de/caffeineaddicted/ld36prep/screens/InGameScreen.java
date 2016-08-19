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
import de.caffeineaddicted.ld36prep.units.UnitTower;
import de.caffeineaddicted.sgl.ui.screens.SGLScreen;

import java.util.Iterator;

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
        texEnemy = game.getAssets().get("enemy.png", Texture.class);
        texTower = game.getAssets().get("tower.png", Texture.class);

        UnitEnemy unit1 = new UnitEnemy(game, UnitEnemy.Type.FEGGIT1);

        UnitEnemy unit2 = new UnitEnemy(game, UnitEnemy.Type.FEGGIT2);
        unit2.translateY(-100);

        UnitTower unit3 = new UnitTower(game, 300, 1);
        unit3.translate(200, 300);
        unit3.setTexture(texTower);
        unit3.setSize(32, 32);
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