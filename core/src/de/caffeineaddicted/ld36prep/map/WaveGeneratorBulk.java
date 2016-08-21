package de.caffeineaddicted.ld36prep.map;

import com.badlogic.gdx.math.Vector2;
import de.caffeineaddicted.ld36prep.screens.InGameScreen;
import de.caffeineaddicted.ld36prep.units.UnitEnemy;
import de.caffeineaddicted.ld36prep.units.UnitTower;
import de.caffeineaddicted.ld36prep.util.MathUtils;

/**
 * Created by Niels on 21.08.2016.
 */
public class WaveGeneratorBulk extends WaveGenerator {
    public WaveGeneratorBulk(InGameScreen screen, Map map) {
        super(screen, map);
    }

    @Override
    protected void spawn() {
        Vector2 pos = map.getStart();
        pos = map.gridToPos(pos);
        while (remainingSpawns >= 0) {
            UnitEnemy enemy = new UnitEnemy(screen, UnitEnemy.Type.getRandom());
            enemy.translate(pos);
            remainingSpawns--;
        }
    }
}
