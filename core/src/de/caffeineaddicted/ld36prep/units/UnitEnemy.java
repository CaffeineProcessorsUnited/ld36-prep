package de.caffeineaddicted.ld36prep.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import de.caffeineaddicted.ld36prep.LD36Prep;

public class UnitEnemy extends UnitBase {
    private float hp;
    private float speed;

    public static enum Type {
        FEGGIT1(32, 32, 10, 1, 5, "enemy.png"),
        FEGGIT2(32, 32, 20, 1, 2.5f, "enemy.png"),
        FEGGIT3(32, 32, 100, 0.5f, 1, "enemy.png");

        public final int w;
        public final int h;
        public final float hp;
        public final float damage;
        public final float speed;
        public final String file;

        Type(int w, int h, float hp, float damage, float speed, String file) {
            this.w = w;
            this.h = h;
            this.hp = hp;
            this.damage = damage;
            this.speed = speed;
            this.file = file;
        }
    };

    public UnitEnemy(LD36Prep game, UnitEnemy.Type type) {
        super(game);
        setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
        setSpeed(type.speed);
        setHP(type.hp);

        setSize(type.w, type.h);
        setSize(type.w, type.h);
        setTexture(game.getAssets().get(type.file, Texture.class));
    }

    @Override
    public void tick(float delta) {
        if (hp < 0) //Dead
            return;

        delta *= speed;

        for (DIRECTION dir : DIRECTION.values()) {
            if (moveDirection(dir, delta))
                break;
        }
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

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
