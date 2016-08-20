package de.caffeineaddicted.ld36prep.map;

import com.badlogic.gdx.math.Vector2;
import de.caffeineaddicted.ld36prep.util.MathUtils;

/**
 * @author Malte Heinzelmann
 */
public class Map {
    public enum GroundType {
        PATH, TOWER, START, FINISH
    }
    private final int cols;
    private final int rows;
    private float gridWidth;
    private float gridHeight;
    public boolean renderGrid = true;
    private GroundType[][] map;

    public Map(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
        map = MapGenerator.gen(cols, rows);
    }

    public Map(int cols, int rows, float width, float height) {
        this(cols, rows);
        resize(width, height);
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public Map.GroundType get(int x, int y) {
        return map[x][y];
    }

    public Vector2 gridToPos(float x, float y) {
        return gridToPos(new Vector2(x, y));
    }

    public Vector2 gridToPos(Vector2 grid) {
        return new Vector2(grid.x * gridWidth, grid.y * gridHeight);
    }

    public Vector2 posToGrid(float x, float y) {
        return new Vector2(x / gridWidth, y / gridHeight);
    }

    public Vector2 posToGrid(Vector2 pos) {
        return posToGrid(pos.x, pos.y);
    }

    public float unitToPixel(float units) {
        return units * (MathUtils.distanceP2P(new Vector2(0, 0), gridToPos(1, 1)));
    }

    public void resize(float width, float height) {
        this.gridWidth = width / this.cols;
        this.gridHeight = height / this.rows;
    }


}
