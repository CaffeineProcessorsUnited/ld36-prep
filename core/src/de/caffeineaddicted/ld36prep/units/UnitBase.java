package de.caffeineaddicted.ld36prep.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import de.caffeineaddicted.ld36prep.LD36Prep;

import java.util.ArrayList;

abstract public class UnitBase extends Entity {
    protected enum DIRECTION {
        SOUTH,
        EAST,
        WEST,
        NORTH
    }

    public static ArrayList<UnitBase> units = new ArrayList<UnitBase>();

    public UnitBase(LD36Prep game) {
        super(game);
        units.add(this);
    }

    public void destroy() {
        units.remove(this);
    }

    abstract public boolean tick(float delta);

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

    protected boolean moveDirection(DIRECTION dir, float delta) {
        float x = getX();
        float y = getY();
        float nx = x;
        float ny = y;

        switch (dir) {
            case NORTH:
                ny += delta;
                break;
            case EAST:
                nx += delta;
                break;
            case SOUTH:
                ny -= delta;
                break;
            case WEST:
                nx -= delta;
                break;
        }
        nx = MathUtils.clamp(nx, 0, Gdx.graphics.getWidth());
        ny = MathUtils.clamp(ny, 0, Gdx.graphics.getHeight());
        if (nx + getWidth() > Gdx.graphics.getWidth()) {
            nx = Gdx.graphics.getWidth() - getWidth();
        }
        if (ny + getHeight() > Gdx.graphics.getHeight()) {
            ny = Gdx.graphics.getHeight() - getHeight();
        }

        if (GetUnitsInRect(nx, ny, nx + getWidth(), ny + getHeight()).size() > 1)
            return false;
        setPosition(nx, ny);
        return true;
    }
}
