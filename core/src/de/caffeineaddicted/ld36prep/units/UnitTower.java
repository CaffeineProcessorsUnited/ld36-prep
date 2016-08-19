package de.caffeineaddicted.ld36prep.units;

import com.badlogic.gdx.graphics.Texture;
import de.caffeineaddicted.ld36prep.LD36Prep;

import java.util.ArrayList;

public class UnitTower extends UnitBase {

    public float range;
    public float damage;

    public UnitTower(LD36Prep game, float range, float damage) {
        super(game);
        this.range = range;
        this.damage = damage;
    }

    @Override
    public void tick(float delta) {
        ArrayList<UnitBase> unitsInRange = getUnitsInRange(getX(), getY(), range);
        for (UnitBase unit : unitsInRange) {
            if (unit instanceof UnitEnemy) { //Is Enemy
                UnitEnemy enemy = (UnitEnemy) unit;

                Projectile p = new Projectile();
                p.setPosition(getOriginX(), getOriginY());
                p.setTarget(enemy);
                p.setSpeed(20);
                p.setDamage(damage);

                p.setSize(32, 32);
                p.setTexture(game.getAssets().get("enemy.png", Texture.class));
            }
        }
    }
}
