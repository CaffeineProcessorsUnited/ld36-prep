package de.caffeineaddicted.ld36prep.units;

import de.caffeineaddicted.ld36prep.screens.InGameScreen;
import de.caffeineaddicted.ld36prep.util.MathUtils;

import java.util.ArrayList;

abstract public class UnitBase extends Entity {
    public static ArrayList<UnitBase> units = new ArrayList<UnitBase>();

    public UnitBase(InGameScreen screen) {
        super(screen);
        units.add(this);
    }

    public static ArrayList<UnitBase> GetUnitsInRect(float x1, float y1, float x2, float y2) {
        ArrayList<UnitBase> list = new ArrayList<UnitBase>();

        for (UnitBase unit : units) {
            if (MathUtils.intersectRect(unit.getX(), unit.getY(),
                    unit.getX() + unit.getHeight(), unit.getY() + unit.getHeight(),
                    x1, y1, x2, y2)) {
                list.add(unit);
            }
        }
        return list;
    }

    public static ArrayList<UnitBase> getUnitsInRange(float x, float y, float range) {
        ArrayList<UnitBase> list = new ArrayList<UnitBase>();
        for (UnitBase unit : units) {
            if (MathUtils.intersectCircleRect(x, y, range,
                    unit.getX(), unit.getY(),
                    unit.getX() + unit.getHeight(), unit.getY() + unit.getHeight())) {
                list.add(unit);
            }
        }
        return list;
    }

    public void destroy() {
        units.remove(this);
    }

    abstract public boolean tick(float delta);

    protected enum DIRECTION {
        SOUTH,
        EAST,
        WEST,
        NORTH
    }
}
