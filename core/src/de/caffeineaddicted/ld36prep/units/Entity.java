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
import com.badlogic.gdx.scenes.scene2d.utils.TransformDrawable;
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
    private float scaleX = 1;
    private float scaleY = 1;

    private Vector2 center;
    private Vector2 centerpoint;

    private float rotation;

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
        if (drawable instanceof TransformDrawable) {
            if (scaleX != 1 || scaleY != 1 || rotation != 0) {
                ((TransformDrawable)drawable).draw(batch, x, y, center.x, center.y,
                        width, height, scaleX, scaleY, rotation);
                return;
            }
        }
        if (drawable != null) drawable.draw(batch, x, y, width * scaleX, height * scaleY);
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

    public void setCenterPosition(float x, float y) {
        setX(x - getWidth() / 2);
        setY(y - getHeight() / 2);
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

    public float getRotation() {
        return rotation;
    }

    public void setRotation (float degrees) {
        if (rotation != degrees) {
            rotation = degrees;
        }
    }

    public void rotateBy(float amountInDegrees) {
        if (amountInDegrees != 0) {
            rotation = amountInDegrees;
        }
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public void setScale(float scale) {
        setScaleX(scale);
        setScaleY(scale);
    }
}
