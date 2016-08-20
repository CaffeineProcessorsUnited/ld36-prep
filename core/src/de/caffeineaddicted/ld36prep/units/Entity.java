package de.caffeineaddicted.ld36prep.units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TransformDrawable;
import de.caffeineaddicted.ld36prep.LD36Prep;
import de.caffeineaddicted.ld36prep.map.Map;
import de.caffeineaddicted.ld36prep.screens.InGameScreen;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Malte Heinzelmann
 */
public abstract class Entity {
    protected InGameScreen screen;
    protected HashMap<String, Drawable> drawables;

    protected int width;
    protected int height;
    protected float x;
    protected float y;
    protected float scaleX = 1;
    protected float scaleY = 1;

    protected Vector2 center;
    protected Vector2 centerpoint;

    protected float rotation;

    public Entity(InGameScreen screen) {
        this.screen = screen;
        this.center = new Vector2();
        this.centerpoint = new Vector2();
        this.drawables = new HashMap<String, Drawable>();
    }

    protected void update() {
    }

    public float unitToPixel(float units) {
        return screen.getMap().unitToPixel(units);
    }

    private void updateCenter() {
        center.set(getWidth() / 2, getHeight() / 2);
        updateCenterPoint();
    }

    private void updateCenterPoint() {
        centerpoint.set(center.x + getX(), center.y + getY());
    }

    public void addTexture(String texture) {
        addTexture(texture, screen.game.getAssets().get(texture, Texture.class));
    }

    public void addTexture(String name, Texture texture) {
        addDrawable(name, new TextureRegionDrawable(new TextureRegion(texture)));
    }

    public void addDrawable(String name, Drawable drawable) {
        this.drawables.put(name, drawable);
    }

    public void clearDrawables() {
        this.drawables.clear();
    }

    public void draw(Batch batch, Drawable drawable, float x, float y, int width, int height, Vector2 center, float scaleX, float scaleY, float rotation) {
        if (drawable instanceof TransformDrawable) {
            if (scaleX != 1 || scaleY != 1 || rotation != 0) {
                ((TransformDrawable) drawable).draw(batch, x, y, center.x, center.y,
                        width, height, scaleX, scaleY, rotation);
                return;
            }
        }
        if (drawable != null) drawable.draw(batch, x, y, width * scaleX, height * scaleY);
    }

    public void draw(Batch batch, Drawable drawable, Vector2 pos, int width, int height, float scaleX, float scaleY) {
        if (drawable != null) drawable.draw(batch, pos.x, pos.y, width * scaleX, height * scaleY);
    }

    public void draw(Batch batch, Drawable drawable, Vector2 pos, int width, int height, Vector2 center, float scaleX, float scaleY, float rotation) {
        draw(batch, drawable, pos.x, pos.y, width, height, center, scaleX, scaleY, rotation);
    }

    public void draw(Batch batch, Drawable drawable) {
        draw(batch, drawable, x, y, width, height, center, scaleX, scaleY, rotation);
    }

    public void draw(Batch batch) {
        for (String key : drawables.keySet()) {
            draw(batch, drawables.get(key));
        }
    }

    public Vector2 getCenter() {
        return center;
    }

    public Vector2 getCenterPoint() {
        return centerpoint;
    }

    public int getWidth() {
        return width;
    }

    public Entity setWidth(int width) {
        this.width = width;
        updateCenter();
        return this;
    }

    public int getHeight() {
        return height;
    }

    public Entity setHeight(int height) {
        this.height = height;
        updateCenter();
        return this;
    }

    public Entity setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
        return this;
    }

    public float getX() {
        return x;
    }

    public Entity setX(float x) {
        this.x = x;
        updateCenterPoint();
        return this;
    }

    public float getY() {
        return y;
    }

    public Entity setY(float y) {
        this.y = y;
        updateCenterPoint();
        return this;
    }

    public Entity setPosition(float x, float y) {
        setX(x);
        setY(y);
        return this;
    }

    public Entity setCenterPosition(float x, float y) {
        setX(x - getWidth() / 2);
        setY(y - getHeight() / 2);
        return this;
    }

    public Entity translateX(float x) {
        return setX(getX() + x);
    }

    public Entity translateY(float y) {
        return setY(getY() + y);
    }

    public Entity translate(float x, float y) {
        return translate(new Vector2(x, y));
    }

    public Entity translate(Vector2 v) {
        translateX(v.x);
        translateY(v.y);
        return this;
    }

    public float getRotation() {
        return rotation;
    }

    public Entity setRotation (float degrees) {
        if (rotation != degrees) {
            rotation = degrees;
        }
        return this;
    }

    public Entity rotateBy(float amountInDegrees) {
        if (amountInDegrees != 0) {
            rotation = amountInDegrees;
        }
        return this;
    }

    public float getScaleX() {
        return scaleX;
    }

    public Entity setScaleX(float scaleX) {
        this.scaleX = scaleX;
        return this;
    }

    public float getScaleY() {
        return scaleY;
    }

    public Entity setScaleY(float scaleY) {
        this.scaleY = scaleY;
        return this;
    }

    public Entity setScale(float scale) {
        setScaleX(scale);
        setScaleY(scale);
        return this;
    }
}
