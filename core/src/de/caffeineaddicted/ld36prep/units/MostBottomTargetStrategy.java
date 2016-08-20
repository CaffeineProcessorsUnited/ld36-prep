package de.caffeineaddicted.ld36prep.units;

import java.util.ArrayList;

/**
 * Created by Niels on 20.08.2016.
 */
public class MostBottomTargetStrategy implements TargetSelectionStrategy {
    @Override
    public ArrayList<UnitBase> selectTarget(ArrayList<UnitBase> units, UnitTower me) {
        ArrayList<UnitBase> ret = new ArrayList<UnitBase>();
        UnitBase unitt = null;
        int i = 0;
        for (UnitBase unit : units) {
            if (unit instanceof UnitEnemy) {
                if (unitt == null) {
                    unitt = unit;
                    continue;
                }
                if (unit.getY() < unitt.getY()) {
                    unitt = unit;
                }
            }
        }
        if (unitt != null)
            ret.add(unitt);
        return ret;
    }
}
