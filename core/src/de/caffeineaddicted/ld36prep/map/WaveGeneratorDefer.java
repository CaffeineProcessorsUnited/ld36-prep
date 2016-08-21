package de.caffeineaddicted.ld36prep.map;

import com.badlogic.gdx.math.Vector2;
import de.caffeineaddicted.ld36prep.screens.InGameScreen;
import de.caffeineaddicted.ld36prep.units.UnitEnemy;

/**
 * Created by Niels on 21.08.2016.
 */
public class WaveGeneratorDefer extends WaveGenerator {
    public WaveGeneratorDefer(InGameScreen screen, Map map) {
        super(screen, map);
    }

    @Override
    protected void spawn() {
        if (remainingSpawns >= 0) {
            UnitEnemy enemy = new UnitEnemy(screen, UnitEnemy.Type.getRandom());
            Vector2 pos = map.getStart();
            pos = map.gridToPos(pos);
            enemy.translate(pos);
            remainingSpawns -= 1;
        }
    }
}
