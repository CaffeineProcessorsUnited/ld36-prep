package de.caffeineaddicted.ld36prep.units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import de.caffeineaddicted.ld36prep.LD36Prep;

/**
 * @author Malte Heinzelmann
 */
public abstract class Entity {
    protected LD36Prep game;
    protected Drawable drawable;

    private int width;
    private int height;
    private float x;
    private float y;

    private Vector2 center;
    private Vector2 centerpoint;

    public Entity(LD36Prep game) {
        this.game = game;
        this.center = new Vector2();
        this.centerpoint = new Vector2();
    }

    protected void update() {
    }

    private void updateCenter() {
        center.set(getWidth() / 2, getHeight() / 2);
        updateCenterPoint();
    }

    private void updateCenterPoint() {
        centerpoint.set(center.x + getX(), center.y + getY());
    }

    public void setTexture(Texture texture) {
        setDrawable(new TextureRegionDrawable(new TextureRegion(texture)));
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public void draw(Batch batch) {
        drawable.draw (batch, x, y, width, height);
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

    public void setWidth(int width) {
        this.width = width;
        updateCenter();
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        updateCenter();
    }

    public void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
        updateCenterPoint();
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
        updateCenterPoint();
    }

    public void setPosition(float x, float y) {
        setX(x);
        setY(y);
    }

    public void translateX(float x) {
        setX(getX() + x);
    }

    public void translateY(float y) {
        setY(getY() + y);
    }

    public void translate(float x, float y) {
        translateX(x);
        translateY(y);
    }

    public void rotate(float angle) {
        // TODO: how to rotate Drawable
    }
}
