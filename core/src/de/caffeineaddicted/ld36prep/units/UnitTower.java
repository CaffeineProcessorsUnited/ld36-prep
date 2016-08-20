package de.caffeineaddicted.ld36prep.units;

import com.badlogic.gdx.graphics.Texture;
import de.caffeineaddicted.ld36prep.LD36Prep;
import de.caffeineaddicted.ld36prep.screens.InGameScreen;
import de.caffeineaddicted.ld36prep.util.MathUtils;

import java.util.ArrayList;

public class UnitTower extends UnitBase {

    public final UnitTower.Type type;
    private int level;
    private float lastShot;
    private TargetSelectionStrategy targetStrategy;

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
        FEGGIT1(new Definition(10, Projectile.Type.FEGGIT1, 1, 100, 32, 32, "tower2.png")),
        FEGGIT2(new Definition(4, Projectile.Type.FEGGIT2, 2, 500, 32, 32, "tower3.png")),
        FEGGIT3(new Definition(60, Projectile.Type.FEGGIT3, 0.5f, 200, 32, 32, "tower1.png"),
                new Definition(60, Projectile.Type.FEGGIT3, 0.5f, 200, 32, 32, "tower2.png"),
                new Definition(60, Projectile.Type.FEGGIT3, 0.5f, 200, 32, 32, "tower3.png"));

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

    public UnitTower(InGameScreen screen, UnitTower.Type type) {
        super(screen);
        this.type = type;
        this.level = 0;
        this.lastShot = def().reload;
        this.targetStrategy = new MostBottomTargetStrategy();
        update();
    }

    protected void update() {
        super.update();
        setSize(def().w, def().h);
        clearDrawables();
        addTexture(def().file);
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
        lastShot += delta;
        if (lastShot < (def().reload)) {
            return false;
        }
        ArrayList<UnitBase> unitsInRange = getUnitsInRange(getX(), getY(), unitToPixel(def().range));
        ArrayList<UnitBase> selectedUnits = this.targetStrategy.selectTarget(unitsInRange, this);

        if (!selectedUnits.isEmpty()) {
            for(UnitBase unit:selectedUnits){
                UnitEnemy enemy = (UnitEnemy) unit;
                double angleToTarget = MathUtils.angleToPoint(getX(), getY(), enemy.getX(), enemy.getY());
                setRotation(-(float) angleToTarget);
                //screen.game.debug("in range: " + enemy.type.name());
                Projectile p = new Projectile(screen, def().projectile, enemy);
                //screen.game.debug(getX() + "," + getY() + "," + getWidth() + "," + getHeight() + "," + getCenterPoint().x + "," + getCenterPoint().y);
                p.setCenterPosition(getCenterPoint().x, getCenterPoint().y);
                lastShot = 0;
            }
        }
        return false;
    }
}
