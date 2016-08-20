package de.caffeineaddicted.ld36prep.units;

public class MathUtils {
    public static float clamp(float val, float min, float max) {
        if (val < min)
            return min;
        else if (val > max)
            return max;
        return val;
    }

    public static boolean intersectRect(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
        return !(
                x3 > x2
                        || x4 < x1
                        || y3 > y2
                        || y4 < y1
        );
    }

    public static boolean intersectCircleRect(float x1, float y1, float r, float x2, float y2, float x3, float y3) {
        float closestX = clamp(x1, x2, x3);
        float closestY = clamp(y1, y2, y3);

        float distX = x1 - closestX;
        float distY = y1 - closestY;
        float dist = distX * distX + distY * distY;
        return dist < (r * r);
    }

    public static double angleToPoint(float x1, float y1, float x2, float y2) {
        double angle = Math.toDegrees(Math.atan2(x2 - x1, y2 - y1));
        return (angle < 0) ? angle + 360 : angle;
    }
}
