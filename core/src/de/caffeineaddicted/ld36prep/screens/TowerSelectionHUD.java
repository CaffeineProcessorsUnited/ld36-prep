package de.caffeineaddicted.ld36prep.screens;

import com.badlogic.gdx.Screen;
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
        selectedSlice = -1;
    }

    public void update() {
        clearDrawables();
        addTexture("TowerHUD.png");
        if (selectedSlice >= 0)
            addTexture("TowerHUD_highlight_" + selectedSlice + ".png");

        setSize(64, 64);
    }


    public void setSelectedSlice(int selectedSlice) {
        this.selectedSlice = selectedSlice;
        update();
    }
}
