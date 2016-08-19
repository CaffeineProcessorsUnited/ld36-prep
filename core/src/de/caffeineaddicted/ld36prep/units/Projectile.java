package de.caffeineaddicted.ld36prep.units;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

public class Projectile extends Sprite {
    private UnitEnemy target;
    private float speed;
    private float damage;
    public static ArrayList<Projectile> activeProjectiles = new ArrayList<Projectile>();

    public Projectile() {
        activeProjectiles.add(this);
    }

    void setTarget(UnitEnemy target) {
        this.target = target;
    }

    void setSpeed(float speed) {
        this.speed = speed;
    }

    void setDamage(float damage) {
        this.damage = damage;
    }

    void tick(float delta) {
        float dirx = target.getOriginX() - getOriginX();
        float diry = target.getOriginY() - getOriginY();
        float norm = dirx * dirx + diry * diry;
        norm = (float) Math.sqrt(norm);
        if (Math.abs(norm) > 1e-8) {
            dirx /= norm;
            diry /= norm;
        }

        translate(delta * speed * dirx, delta * speed * diry);

        if (MathUtils.intersectRect(target.getX(), target.getY(), target.getX() + target.getWidth(), target.getY() + target.getHeight(),
                getX(), getY(), getX() + getWidth(), getY() + getHeight())) {
            target.receiveDamage(damage);
            activeProjectiles.remove(this);
        }

    }
}
