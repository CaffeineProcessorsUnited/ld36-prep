package de.caffeineaddicted.ld36prep.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import de.caffeineaddicted.ld36prep.LD36Prep;
import de.caffeineaddicted.ld36prep.screens.InGameScreen;

public class UnitEnemy extends UnitBase {

    public final UnitEnemy.Type type;
    private float hp;

    public static enum Type {
        FEGGIT1(10, 5, 32, 32, "enemy.png"),
        FEGGIT2(20, 2.5f, 32, 32, "enemy.png"),
        FEGGIT3(100, 1, 32, 32, "enemy.png");

        public final float hp;
        public final float speed;
        public final int w;
        public final int h;
        public final String file;

        Type(float hp, float speed, int w, int h, String file) {
            this.hp = hp;
            this.speed = speed;
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

    protected void update() {
        setTexture(screen.game.getAssets().get(def().file, Texture.class));
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

        delta *= def().speed;

        for (DIRECTION dir : DIRECTION.values()) {
            if (moveDirection(dir, delta))
                break;
        }
        return false;
    }

    public void receiveDamage(float damage) {
        hp -= damage;
        if (!alive())
            destroy();
    }

    public boolean alive() {
        return !(hp < 0);
    }

    public void setHP(float hp) {
        this.hp = hp;
    }
}
