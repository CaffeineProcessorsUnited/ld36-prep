package de.caffeineaddicted.ld36prep.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import de.caffeineaddicted.ld36prep.LD36Prep;
import de.caffeineaddicted.ld36prep.units.Entity;

/**
 * Created by Niels on 20.08.2016.
 */
public class TowerSelectionHUD extends Entity {
    private int selectedSlice;

    public TowerSelectionHUD(InGameScreen screen) {
        super(screen);
        addTexture("TowerHUD.png");
        addTexture("TowerHUD_highlight_0.png");
        addTexture("TowerHUD_highlight_1.png");
        addTexture("TowerHUD_highlight_2.png");
        selectedSlice = -1;
    }

    public void update() {
        setSize(64, 64);
    }

    public void setSelectedSlice(int selectedSlice) {
        this.selectedSlice = selectedSlice;
        update();
    }

    @Override
    public void draw(Batch batch) {
        draw(batch, drawables.get("TowerHUD.png"));
        if (selectedSlice >= 0)
            draw(batch, drawables.get("TowerHUD_highlight_" + selectedSlice + ".png"));
    }
}
