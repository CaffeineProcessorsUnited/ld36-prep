package de.caffeineaddicted.ld36prep.units;

import com.badlogic.gdx.Gdx;
import de.caffeineaddicted.ld36prep.LD36Prep;

public class UnitEnemy extends UnitBase {
    private float hp;
    private float speed;

    public UnitEnemy(LD36Prep game) {
        super(game);
        setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
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
