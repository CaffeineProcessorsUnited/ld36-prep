package de.caffeineaddicted.ld36prep.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.caffeineaddicted.ld36prep.ApplicationConfiguration;
import de.caffeineaddicted.ld36prep.LD36Prep;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = LD36Prep.getConfig().get(ApplicationConfiguration.Attribute.WIDTH);
        config.height = LD36Prep.getConfig().get(ApplicationConfiguration.Attribute.HEIGHT);
		new LwjglApplication(new LD36Prep(), config);
	}
}
