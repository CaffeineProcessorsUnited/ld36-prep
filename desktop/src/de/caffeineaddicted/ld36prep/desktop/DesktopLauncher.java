package de.caffeineaddicted.ld36prep.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.caffeineaddicted.sgl.ApplicationConfiguration;
import de.caffeineaddicted.ld36prep.LD36Prep;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        LD36Prep game = new LD36Prep();
        config.width = game.config().get(ApplicationConfiguration.Attribute.WIDTH);
        config.height = game.config().get(ApplicationConfiguration.Attribute.HEIGHT);
        config.resizable = false;
        new LwjglApplication(game, config);
    }
}
