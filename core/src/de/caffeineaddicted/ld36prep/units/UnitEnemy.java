package de.caffeineaddicted.ld36prep.units;

import com.badlogic.gdx.Gdx;

public class UnitEnemy extends UnitBase {
    private float hp;
    private float speed;

    public UnitEnemy(float hp, float speed) {
        super();

        this.hp = hp;
        this.speed = speed;
        setPosition(Gdx.graphics.getWidth() / 2, 0);
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
}
