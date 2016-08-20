package de.caffeineaddicted.ld36prep.units;

import de.caffeineaddicted.ld36prep.util.MathUtils;

import java.util.ArrayList;

/**
 * @author Malte Heinzelmann
 */
public class NearestTargetStrategy implements TargetSelectionStrategy {
    @Override
    public int selectTarget(ArrayList<UnitBase> units, UnitTower me) {
        int index = -1;
        int minindex = index;
        float distance = me.def().range + 1;
        for (UnitBase unit : units) {
            index++;
            if (unit instanceof UnitEnemy) {
                UnitEnemy enemy = (UnitEnemy) unit;
                float dist = MathUtils.distanceP2P(me, enemy);
                if (dist < distance) {
                    minindex = index;
                    distance = dist;
                }
            }
        }
        return (distance > me.def().range) ? -1 : minindex;
    }
}
