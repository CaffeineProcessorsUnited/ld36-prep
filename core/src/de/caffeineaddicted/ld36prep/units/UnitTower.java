package de.caffeineaddicted.ld36prep.units;

import java.util.ArrayList;

public class UnitTower extends UnitBase {

    public float range;
    public float damage;

    public UnitTower() {
        super();
    }

    @Override
    public void tick(float delta) {
        ArrayList<UnitBase> unitsInRange = getUnitsInRange(getX(), getY(), range);
        for (UnitBase unit : unitsInRange) {
            if (unit instanceof UnitEnemy) { //Is Enemy
                UnitEnemy enemy = (UnitEnemy) unit;
                enemy.hp -= damage;
                if (enemy.hp < 0) //Is Dead => Remove
                    enemy.destroy();
            }
        }
    }
}
