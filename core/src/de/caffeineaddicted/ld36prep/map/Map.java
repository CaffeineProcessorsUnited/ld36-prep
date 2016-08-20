package de.caffeineaddicted.ld36prep.map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TransformDrawable;
import de.caffeineaddicted.ld36prep.screens.InGameScreen;
import de.caffeineaddicted.ld36prep.units.Entity;
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

    public Map(InGameScreen screen, int cols, int rows) {
        super(screen);
        this.cols = cols;
        this.rows = rows;
        map = MapGenerator.genEmpty(cols, rows);
        addTexture("grass.png");
        addTexture("path.png");
        addTexture("start.png");
        addTexture("finish.png");
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
        return new Vector2(x / gridWidth, y / gridHeight);
    }

    public Vector2 posToGrid(Vector2 pos) {
        return posToGrid(pos.x, pos.y);
    }

    public float unitToPixel(float units) {
        return units * (MathUtils.distanceP2P(new Vector2(0, 0), gridToPos(1, 1)));
    }

    public void resize(float width, float height) {
        this.gridWidth = (int) width / this.cols;
        this.gridHeight = (int) height / this.rows;
    }

    @Override
    public void draw(Batch batch) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                screen.game.log(gridToPos(c, r).x + "," + gridToPos(c, r).y + ": " + map[c][r].name());
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
            }
        }
    }

}
