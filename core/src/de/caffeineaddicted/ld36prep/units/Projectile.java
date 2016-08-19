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

    public void setTarget(UnitEnemy target) {
        this.target = target;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    /**
     * @param delta time delta in seconds
     * @return bool wheather to be destroyed
     */
    public boolean tick(float delta) {
        if (!target.alive())
            return true;
        float dirx = target.getX() - getX();
        float diry = target.getY() - getY();
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
            return true;
        }

        System.out.println("" + getX() + "," + getY());
        return false;
    }
}
