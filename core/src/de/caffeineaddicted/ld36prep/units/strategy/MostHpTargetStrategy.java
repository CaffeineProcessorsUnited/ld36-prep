package de.caffeineaddicted.ld36prep.units.strategy;

import de.caffeineaddicted.ld36prep.units.UnitBase;
import de.caffeineaddicted.ld36prep.units.UnitEnemy;
import de.caffeineaddicted.ld36prep.units.UnitTower;

import java.util.ArrayList;

public class MostHpTargetStrategy implements TargetSelectionStrategy {
    @Override
    public ArrayList<UnitBase> selectTarget(ArrayList<UnitBase> units, UnitTower me) {
        ArrayList<UnitBase> ret = new ArrayList<UnitBase>();
        UnitEnemy selectedUnit = null;
        for (UnitBase unit : units) {
            if (unit instanceof UnitEnemy) {
                UnitEnemy enemy = (UnitEnemy) unit;
                if (selectedUnit == null) {
                    selectedUnit = enemy;
                }
                if (selectedUnit.getHP() < enemy.getHP()) {
                    selectedUnit = enemy;
                }
            }
        }
        if (selectedUnit != null) {
            ret.add(selectedUnit);
        }
        return ret;
    }
}
