package de.caffeineaddicted.ld36prep.input;

import com.badlogic.gdx.Input;
import de.caffeineaddicted.ld36prep.LD36Prep;
import de.caffeineaddicted.ld36prep.messages.ExitGameMessage;
import de.caffeineaddicted.ld36prep.messages.ToggleFullscreenMessage;
import de.caffeineaddicted.sgl.input.SGLInputProcessor;

/**
 * @author Malte Heinzelmann
 */
public class GlobalInputProcessor extends SGLInputProcessor {
    LD36Prep game;

    public GlobalInputProcessor(LD36Prep game) {
        this.game = game;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.ESCAPE:
                game.message(new ExitGameMessage());
                break;
            case Input.Keys.F11:
                game.message(new ToggleFullscreenMessage());
                break;
        }
        return false;
    }

}