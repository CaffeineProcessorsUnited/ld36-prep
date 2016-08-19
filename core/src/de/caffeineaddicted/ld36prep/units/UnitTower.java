package de.caffeineaddicted.ld36prep.units;

import java.util.ArrayList;

public class UnitTower extends UnitBase {

    public float range;
    public float damage;

    public UnitTower(float range, float damage) {
        super();
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
                p.setSpeed(1);
                p.setDamage(damage);
            }
        }
    }
}
