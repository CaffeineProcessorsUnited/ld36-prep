package de.caffeineaddicted.ld36prep.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import de.caffeineaddicted.sgl.ApplicationConfiguration;
import de.caffeineaddicted.ld36prep.LD36Prep;

public class HtmlLauncher extends GwtApplication {

    private LD36Prep game = new LD36Prep();

    @Override
    public GwtApplicationConfiguration getConfig() {
        return new GwtApplicationConfiguration(
                game.config().get(ApplicationConfiguration.Attribute.WIDTH, Integer.class),
                game.config().get(ApplicationConfiguration.Attribute.HEIGHT, Integer.class)
        );
    }

    @Override
    public ApplicationListener createApplicationListener() {
        return game;
    }
}