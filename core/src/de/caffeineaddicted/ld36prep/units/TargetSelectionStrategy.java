package de.caffeineaddicted.ld36prep.units;

import java.util.ArrayList;

/**
 * @author Malte Heinzelmann
 */
public interface TargetSelectionStrategy {
    int selectTarget(ArrayList<UnitBase> units, UnitTower me);
}
