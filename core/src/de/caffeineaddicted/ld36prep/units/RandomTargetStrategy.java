package de.caffeineaddicted.ld36prep.units;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by niels on 20.08.16.
 */
public class RandomTargetStrategy implements TargetSelectionStrategy {
    public ArrayList<UnitBase> selectTarget(ArrayList<UnitBase> units, UnitTower me) {
        Random random = new Random();
        int i = 10;
        while (i > 0) {
            UnitBase unit = units.get(random.nextInt(units.size()));
            if (unit instanceof UnitEnemy) {
                ArrayList<UnitBase> ret = new ArrayList<UnitBase>();
                ret.add(unit);
                return ret;
            }
            --i;
        }
        return new ArrayList<UnitBase>();
    }

}
