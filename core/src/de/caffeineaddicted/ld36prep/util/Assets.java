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
        load("tower1.png", Texture.class);
        load("tower2.png", Texture.class);
        load("tower3.png", Texture.class);
        load("projectile.png", Texture.class);
        load("TowerHUD.png", Texture.class);
        load("TowerHUD_highlight_0.png", Texture.class);
        load("TowerHUD_highlight_1.png", Texture.class);
        load("TowerHUD_highlight_2.png", Texture.class);
        load("grass.png", Texture.class);
        load("path.png", Texture.class);
        load("start.png", Texture.class);
        load("finish.png", Texture.class);
        load("hover.png", Texture.class);
    }
}
