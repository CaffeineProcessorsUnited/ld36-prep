package de.caffeineaddicted.ld36prep.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * @author Malte Heinzelmann
 */
public class Assets extends AssetManager {

    public void preload() {
        load("background.png", Texture.class);
        load("cpu.png", Texture.class);
        load("logo.png", Texture.class);
    }

    public void load() {
        load("theme.ogg", Music.class);
        load("uiskin.json", Skin.class);
        load("enemy.png", Texture.class);
        load("tower.png", Texture.class);
    }
}
