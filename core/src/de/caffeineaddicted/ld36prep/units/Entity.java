package de.caffeineaddicted.ld36prep.units;

import com.badlogic.gdx.graphics.g2d.Sprite;
import de.caffeineaddicted.ld36prep.LD36Prep;

/**
 * @author Malte Heinzelmann
 */
public abstract class Entity extends Sprite {
    protected LD36Prep game;

    public Entity(LD36Prep game) {
        this.game = game;
    }

    protected abstract void update();
}
