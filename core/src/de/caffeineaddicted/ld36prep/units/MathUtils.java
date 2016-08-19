package de.caffeineaddicted.ld36prep.units;

public class MathUtils {
    static public float clamp(float val, float min, float max) {
        if (val < min)
            return min;
        else if (val > max)
            return max;
        return val;
    }

    static public boolean intersectRect(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
        return !(
                x3 > x1
                        || x4 < x2
                        || y3 > y1
                        || y4 < y2
        );
    }

    static public boolean intersectCircleRect(float x1, float y1, float r, float x2, float y2, float x3, float y3) {
        float closestX = clamp(x1, x2, x3);
        float closestY = clamp(y1, y2, y3);

        float distX = x1 - closestX;
        float distY = y1 - closestY;
        float dist = distX * distX + distY * distY;
        return dist < r * r;
    }
}
