package de.caffeineaddicted.ld36prep.units;

import java.util.ArrayList;

/**
 * @author Malte Heinzelmann
 */
public interface TargetSelectionStrategy {
    ArrayList<UnitBase> selectTarget(ArrayList<UnitBase> units, UnitTower me);
}
