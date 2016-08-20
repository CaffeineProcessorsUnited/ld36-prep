package de.caffeineaddicted.ld36prep.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import de.caffeineaddicted.ld36prep.LD36Prep;
import de.caffeineaddicted.ld36prep.screens.InGameScreen;
import de.caffeineaddicted.ld36prep.util.MathUtils;

import java.util.ArrayList;

public class UnitEnemy extends UnitBase {

    public final UnitEnemy.Type type;
    private float hp;

    public static enum Type {
        FEGGIT1(10, 0.5f, 100, 32, 32, "enemy.png"),
        FEGGIT2(20, 0.25f, 200, 32, 32, "enemy.png"),
        FEGGIT3(100, 0.1f, 300, 32, 32, "enemy.png");

        public final float hp;
        public final float speed;
        public final int points;
        public final int w;
        public final int h;
        public final String file;

        Type(float hp, float speed, int points, int w, int h, String file) {
            this.hp = hp;
            this.speed = speed;
            this.points = points;
            this.w = w;
            this.h = h;
            this.file = file;
        }
    }

    public UnitEnemy(InGameScreen screen, UnitEnemy.Type type) {
        super(screen);
        this.type = type;
        setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
        setHP(def().hp);
        update();
    }

    public void update() {
        clearDrawables();
        addTexture(def().file);

        setSize(def().w, def().h);
        setSize(def().w, def().h);
    }

    public Type def() {
        return type;
    }

    @Override
    public boolean tick(float delta) {
        if (hp < 0) //Dead
            return true;

        delta *= unitToPixel(def().speed);

        for (DIRECTION dir : DIRECTION.values()) {
            if (moveDirection(dir, delta))
                break;
        }
        return false;
    }

    public void receiveDamage(float damage) {
        hp -= damage;
        if (!alive()) {
            screen.money += def().points;
            screen.score += def().points;
            destroy();
        }
    }

    public boolean alive() {
        return !(hp < 0);
    }

    public void setHP(float hp) {
        this.hp = hp;
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

        ArrayList<UnitBase> unitsInRect = GetUnitsInRect(nx, ny, nx + getWidth(), ny + getHeight());
        for (UnitBase unit : unitsInRect) {
            if (!(unit instanceof UnitEnemy))
                return false;
        }
        setPosition(nx, ny);
        return true;
    }
}