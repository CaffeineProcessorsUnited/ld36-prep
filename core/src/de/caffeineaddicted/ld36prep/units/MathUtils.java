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

    public static float distanceP2P(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public static float distanceP2P(Entity e1, Entity e2) {
        return MathUtils.distanceP2P(e1.getCenterPoint().x, e1.getCenterPoint().y, e2.getCenterPoint().x, e2.getCenterPoint().y);
    }
}
