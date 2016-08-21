package de.caffeineaddicted.ld36prep.units.strategy;

import de.caffeineaddicted.ld36prep.units.UnitBase;
import de.caffeineaddicted.ld36prep.units.UnitTower;

import java.util.ArrayList;

/**
 * @author Malte Heinzelmann
 */
public interface TargetSelectionStrategy {
    ArrayList<UnitBase> selectTarget(ArrayList<UnitBase> units, UnitTower me);
}
