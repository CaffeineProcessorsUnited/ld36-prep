package de.caffeineaddicted.ld36prep.units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.caffeineaddicted.ld36prep.LD36Prep;

import java.util.ArrayList;

public class UnitTower extends UnitBase {

    public final UnitTower.Type type;
    private int level;
    private float lastShot;

    public static class Definition {
        public final float range;
        public final Projectile.Type projectile;
        public final float reload;
        public final float price;
        public final int w;
        public final int h;
        public final String file;

        Definition(float range, Projectile.Type projectile, float reload, float price, int w, int h, String file) {
            this.range = range;
            this.projectile = projectile;
            this.reload = reload;
            this.price = price;
            this.w = w;
            this.h = h;
            this.file = file;
        }
    }

    public static enum Type {
        FEGGIT1(new Definition(10, Projectile.Type.FEGGIT1, 1, 100, 32, 32, "tower.png")),
        FEGGIT2(new Definition(4, Projectile.Type.FEGGIT2, 2, 500, 32, 32, "tower.png")),
        FEGGIT3(new Definition(300, Projectile.Type.FEGGIT3, 0.5f, 200, 32, 32, "tower.png"));

        private ArrayList<Definition> levels = new ArrayList<Definition>();

        Type(Definition... ds) {
            for (Definition d: ds) {
                levels.add(d);
            }
        }

        public int maxlevel() {
            return levels.size() - 1;
        }

        public Definition get(int level) {
            return levels.get(level);
        }
    }

    public UnitTower(LD36Prep game, UnitTower.Type type) {
        super(game);
        this.type = type;
        this.level = 0;
        this.lastShot = def().reload * 100;
        update();
    }

    protected void update() {
        super.update();
        setSize(def().w, def().h);
        setTexture(game.getAssets().get(def().file, Texture.class));
    }

    public Definition def() {
        return type.get(level);
    }

    public void levelup() {
        if (level < type.maxlevel()) {
            level++;
            update();
        }
    }

    @Override
    public boolean tick(float delta) {
        ArrayList<UnitBase> unitsInRange = getUnitsInRange(getX(), getY(), def().range);
        lastShot += delta;
        for (UnitBase unit : unitsInRange) {
            if (unit instanceof UnitEnemy && (lastShot >= (def().reload * 100))) { //Is Enemy
                UnitEnemy enemy = (UnitEnemy) unit;
                game.debug("in range: " + enemy.type.name());
                Projectile p = new Projectile(game, def().projectile, enemy);
                p.setPosition(getCenterPoint().x, getCenterPoint().y);
                lastShot = 0;
            }
        }
        return false;
    }
}
