package de.caffeineaddicted.ld36prep.units;

import com.badlogic.gdx.graphics.Texture;
import de.caffeineaddicted.ld36prep.LD36Prep;
import de.caffeineaddicted.ld36prep.screens.InGameScreen;
import de.caffeineaddicted.ld36prep.util.MathUtils;

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

    public Projectile(InGameScreen screen, Projectile.Type type) {
        this(screen, type, null);
    }

    public Projectile(InGameScreen screen, Projectile.Type type, UnitEnemy target) {
        super(screen);
        activeProjectiles.add(this);
        this.type = type;
        setTarget(target);
        update();
    }

    protected void update() {
        super.update();
        setSize(def().w, def().h);
        clearDrawables();
        addTexture(def().file);
    }

    public Definition def() {
        return type.defintion;
    }

    public void setTarget(UnitEnemy target) {
        this.target = target;
    }

    /**
     * @param delta time delta in seconds
     * @return bool whether to be destroyed
     */
    public boolean tick(float delta) {
        if (!target.alive())
            return true;
        float dirx = target.getX() - getX();
        float diry = target.getY() - getY();
        float norm = dirx * dirx + diry * diry;
        norm = (float) Math.sqrt(norm);
        if (norm > 1e-8) {
            dirx /= norm;
            diry /= norm;
        }

        double angleToTarget = MathUtils.angleToPoint(getX(), getY(), target.getX(), target.getY());
        setRotation(-(float) angleToTarget);

        translate(delta * unitToPixel(def().speed) * dirx, delta * unitToPixel(def().speed) * diry);

        if (MathUtils.intersectRect(target.getX(), target.getY(), target.getX() + target.getWidth(), target.getY() + target.getHeight(),
                getX(), getY(), getX() + getWidth(), getY() + getHeight())) {
            target.receiveDamage(def().damage);
            return true;
        }
        return false;
    }
}
