package de.caffeineaddicted.ld36prep.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import de.caffeineaddicted.ld36prep.messages.TogglePauseGameMessage;
import de.caffeineaddicted.ld36prep.screens.InGameScreen;
import de.caffeineaddicted.sgl.input.SGLInputProcessor;

/**
 * Created by niels on 20.08.16.
 */
public class InGameInputProcessor extends SGLInputProcessor {
    private InGameScreen screen;

    public InGameInputProcessor(InGameScreen screen) {
        this.screen = screen;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.SPACE:
                screen.game.message(new TogglePauseGameMessage());
                break;
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenY = Gdx.graphics.getHeight() - screenY;
        screen.touchDown(screenX, screenY, pointer, button);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenY = Gdx.graphics.getHeight() - screenY;
        screen.touchUp(screenX, screenY, pointer, button);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        screenY = Gdx.graphics.getHeight() - screenY;
        screen.touchMoved(screenX, screenY);
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        screenY = Gdx.graphics.getHeight() - screenY;
        screen.touchMoved(screenX, screenY);
        screen.mouseMoved(screenX, screenY);
        return true;
    }


}
