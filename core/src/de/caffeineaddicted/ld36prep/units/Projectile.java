package de.caffeineaddicted.ld36prep.units;

import com.badlogic.gdx.graphics.Texture;
import de.caffeineaddicted.ld36prep.LD36Prep;

import java.util.ArrayList;

public class Projectile extends Entity {

    public final Projectile.Type type;
    private UnitEnemy target;
    public static ArrayList<Projectile> activeProjectiles = new ArrayList<Projectile>();

    public static class Definition {
        public final float range;
        public final float damage;
        public final float speed;
        public final int w;
        public final int h;
        public final String file;

        Definition(float range, float damage, float speed, int w, int h, String file) {
            this.range = range;
            this.damage = damage;
            this.speed = speed;
            this.w = w;
            this.h = h;
            this.file = file;
        }
    }

    public static enum Type {
        FEGGIT1(new Definition(0, 5, 10, 16, 16, "projectile.png")),
        FEGGIT2(new Definition(1, 20, 10, 16, 16, "projectile.png")),
        FEGGIT3(new Definition(0, 1, 10, 16, 16, "projectile.png"));

        public final Definition defintion;

        Type(Definition defintion) {
            this.defintion = defintion;
        }
    }

    public Projectile(LD36Prep game, Projectile.Type type) {
        this(game, type, null);
    }

    public Projectile(LD36Prep game, Projectile.Type type, UnitEnemy target) {
        super(game);
        activeProjectiles.add(this);
        this.type = type;
        setTarget(target);
        setTexture(game.getAssets().get("enemy.png", Texture.class));
        update();
    }

    protected void update() {

    }

    public Definition def() {
        return type.defintion;
    }

    public void setTarget(UnitEnemy target) {
        this.target = target;
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

        translate(delta * def().speed * dirx, delta * def().speed * diry);

        if (MathUtils.intersectRect(target.getX(), target.getY(), target.getX() + target.getWidth(), target.getY() + target.getHeight(),
                getX(), getY(), getX() + getWidth(), getY() + getHeight())) {
            target.receiveDamage(def().damage);
            return true;
        }

        System.out.println("" + getX() + "," + getY());
        return false;
    }
}
