package de.caffeineaddicted.ld36prep.units;

import com.badlogic.gdx.Gdx;

public class UnitEnemy extends UnitBase {
    public float hp;
    public float speed;

    public UnitEnemy() {
        super();
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
}
