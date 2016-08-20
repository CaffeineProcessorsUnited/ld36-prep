package de.caffeineaddicted.ld36prep.map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import de.caffeineaddicted.ld36prep.screens.InGameScreen;
import de.caffeineaddicted.ld36prep.units.Entity;
import de.caffeineaddicted.ld36prep.units.UnitBase;
import de.caffeineaddicted.ld36prep.units.UnitTower;
import de.caffeineaddicted.ld36prep.util.MathUtils;

/**
 * @author Malte Heinzelmann
 */
public class Map extends Entity {

    public enum GroundType {
        PATH, TOWER, START, FINISH
    }
    private final int cols;
    private final int rows;
    private int gridWidth;
    private int gridHeight;
    public boolean renderGrid = true;
    private GroundType[][] map;
    private float oneUnit;
    private Vector2 center;

    private int selectX = -1;
    private int selectY = -1;
    private int hoverX = -1;
    private int hoverY = -1;

    public Map(InGameScreen screen, int cols, int rows) {
        super(screen);
        this.cols = cols;
        this.rows = rows;
        map = MapGenerator.genEmpty(cols, rows);
        addTexture("grass.png");
        addTexture("path.png");
        addTexture("start.png");
        addTexture("finish.png");
        addTexture("hover.png");
    }

    public Map(InGameScreen screen, int cols, int rows, float width, float height) {
        this(screen, cols, rows);
        resize(width, height);
    }

    public void update() {
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
        return new Vector2((int) Math.floor(x / gridWidth), (int) Math.floor(y / gridHeight));
    }

    public Vector2 posToGrid(Vector2 pos) {
        return posToGrid(pos.x, pos.y);
    }

    public float unitToPixel(float units) {
        return units * oneUnit;
    }

    public void resize(float width, float height) {
        this.gridWidth = (int) width / this.cols;
        this.gridHeight = (int) height / this.rows;
        oneUnit = MathUtils.distanceP2P(new Vector2(0, 0), gridToPos(1, 1));
        center = new Vector2(gridWidth / 2, gridHeight / 2);
    }

    public Vector2 getCenterInGird(float x, float y) {
        return center.cpy().add(gridToPos(posToGrid(x, y)));
    }

    public Vector2 getCenter(float x, float y) {
        return center.cpy().add(gridToPos(x, y));
    }

    public void setSelected(float x, float y) {
        Vector2 pos = posToGrid(x, y);
        selectX = (int) pos.x;
        selectY = (int) pos.y;
    }

    public void setHovered(float x, float y) {
        Vector2 pos = posToGrid(x, y);
        hoverX = (int) pos.x;
        hoverY = (int) pos.y;
    }

    public boolean isInGrid(float gx, float gy, float x, float y) {
        Vector2 pos = posToGrid(x, y);
        return (gx == pos.x && gy == pos.y);
    }

    public Vector2 getStart() {
        Vector2 start = new Vector2();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (map[c][r] == GroundType.START) {
                    start.set(c, r);
                }
            }
        }
        return start;
    }

    public Vector2 getFinish() {
        Vector2 finish = new Vector2();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (map[c][r] == GroundType.FINISH) {
                    finish.set(c, r);
                }
            }
        }
        return finish;
    }

    @Override
    public void draw(Batch batch) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                //screen.game.log(gridToPos(c, r).x + "," + gridToPos(c, r).y + ": " + map[c][r].name());
                switch (map[c][r]) {
                    case START:
                        draw(batch, drawables.get("start.png"), gridToPos(c, r), gridWidth, gridHeight, 1, 1);
                        break;
                    case FINISH:
                        draw(batch, drawables.get("finish.png"), gridToPos(c, r), gridWidth, gridHeight, 1, 1);
                        break;
                    case PATH:
                        draw(batch, drawables.get("path.png"), gridToPos(c, r), gridWidth, gridHeight, 1, 1);
                        break;
                    case TOWER:
                        draw(batch, drawables.get("grass.png"), gridToPos(c, r), gridWidth, gridHeight, 1, 1);
                        break;
                }
                if (hoverX == c && hoverY == r) {
                    draw(batch, drawables.get("hover.png"), gridToPos(c, r), gridWidth, gridHeight, 1, 1);
                }
            }
        }
    }

}
