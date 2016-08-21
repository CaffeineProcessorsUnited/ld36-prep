package de.caffeineaddicted.ld36prep.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import de.caffeineaddicted.ld36prep.LD36Prep;
import de.caffeineaddicted.ld36prep.input.InGameInputProcessor;
import de.caffeineaddicted.ld36prep.map.Map;
import de.caffeineaddicted.ld36prep.map.WaveGenerator;
import de.caffeineaddicted.ld36prep.map.WaveGeneratorDefer;
import de.caffeineaddicted.ld36prep.units.*;
import de.caffeineaddicted.ld36prep.util.Assets;
import de.caffeineaddicted.ld36prep.util.MathUtils;
import de.caffeineaddicted.sgl.ui.screens.SGLScreen;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Malte Heinzelmann
 */
public class InGameScreen extends SGLScreen<LD36Prep> {

    private Stage stage;
    Drawable test;
    private float selectedTowerX, selectedTowerY;
    private float currentMouseX, currentMouseY;
    private boolean showPlacementHUD;
    private TowerSelectionHUD towerSelectionHUD;
    private Map map;
    private BitmapFont font;

    public int money = 500;
    public int score = 0;

    private float waitTimer;
    private float sleepTimer = 1.5f;
    private WaveGenerator waveGenerator;

    public InGameScreen(LD36Prep game) {
        super(game);
    }

    public void create() {
        game.debug("Creating InGameScreen");
        game.getScreenInput().addProcessor(this, new InGameInputProcessor(this));
        map = new Map(this, 40, 20, game.getCamera().viewportWidth, game.getCamera().viewportHeight);

        UnitEnemy unit1 = new UnitEnemy(this, UnitEnemy.Type.FEGGIT1);

        UnitEnemy unit2 = new UnitEnemy(this, UnitEnemy.Type.FEGGIT2);
        unit2.translateY(-100);

        UnitTower unit3 = new UnitTower(this, UnitTower.Type.FEGGIT3);
        unit3.translate(map.gridToPos(4, 8));

        showPlacementHUD = false;
        towerSelectionHUD = new TowerSelectionHUD(this);

        font = game.getAssets().get("uiskin.json", Skin.class).getFont("font_droid_sans_28pt_bold");

        waitTimer = 0;
        waveGenerator = new WaveGeneratorDefer(this, map);
        waveGenerator.setTickDeferTimer(1);
        waveGenerator.setTickWaitTimer(60);
        waveGenerator.setCurrentWaitTimer(50);
        waveGenerator.setMinSpawn(1);
        waveGenerator.setMaxSpawn(1);
    }

    public void render(float delta) {
        if (!isPaused()) {
            waitTimer += delta;
            waveGenerator.setMaxSpawn(waveGenerator.getWaveCount());
            waveGenerator.tick(delta);

            int alive = 0;
            for (UnitBase unit : UnitBase.units) {
                if (unit instanceof UnitEnemy) {
                    UnitEnemy enemy = (UnitEnemy) unit;
                    if (enemy.alive())
                        alive++;
                }
            }
            game.warning("" + alive);
            if (alive == 0) {
                waveGenerator.skipToNextWave();
            }

            for (UnitBase unit : UnitBase.units) {
                unit.tick(delta);
            }

            Iterator<Projectile> projectileIterator = Projectile.activeProjectiles.iterator();
            while (projectileIterator.hasNext()) {
                Projectile projectile = projectileIterator.next();
                if (projectile.tick(delta)) {
                    projectileIterator.remove();
                }
            }
        }
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        game.getBatch().begin();
        game.getBatch().setColor(new Color(1, 1, 1, 1));

        map.draw(game.getBatch());

        for (Projectile projectile : Projectile.activeProjectiles) {
            projectile.draw(game.getBatch());
        }
        for (UnitBase unit : UnitBase.units) {
            unit.draw(game.getBatch());
        }

        if (showPlacementHUD) {
            towerSelectionHUD.draw(game.getBatch());
        }

        font.draw(game.getBatch(), "Money: " + money, 10, game.getCamera().viewportHeight - 10);
        font.draw(game.getBatch(), "Score:" + score, 10, game.getCamera().viewportHeight - font.getCapHeight() - 20);
        font.draw(game.getBatch(), "Current wave:" + waveGenerator.getWaveCount(), 10, game.getCamera().viewportHeight - 2 * font.getCapHeight() - 30);
        font.draw(game.getBatch(), "Time to next wave:" + (int) waveGenerator.getRemainingTime(), 10, game.getCamera().viewportHeight - 3 * font.getCapHeight() - 40);

        game.getBatch().end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

    }

    public void touchDown(int screenX, int screenY, int pointer, int button) {
        switch (button) {
            case Input.Buttons.LEFT:
                map.setSelected(screenX, screenY);
                break;
            case Input.Buttons.RIGHT:
                Vector2 p = map.posToGrid(screenX, screenY);
                Vector2 pos = map.gridToPos(p);
                Vector2 posnext = map.gridToPos(p.cpy().add(1, 1));
                selectedTowerX = pos.x;
                selectedTowerY = pos.y;
                showPlacementHUD = true;
                towerSelectionHUD.setSelectedSlice(-1);
                Vector2 hudpos = map.getCenterInGird(selectedTowerX, selectedTowerY);
                towerSelectionHUD.setCenterPosition(hudpos.x, hudpos.y);

                if (!UnitBase.GetUnitsInRect(pos.x + 1, pos.y + 1, posnext.x - 1, posnext.y - 1).isEmpty())
                    showPlacementHUD = false;
                break;
        }
    }

    public void touchUp(int screenX, int screenY, int pointer, int button){
        if(button == 1) {
            placeTower(screenX, screenY);
            showPlacementHUD = false;
        } else {
            upgradeTower(screenX, screenY);
        }
    }

    public void touchMoved(int screenX, int screenY){
        currentMouseX = screenX;
        currentMouseY = screenY;
        Vector2 pos = map.getCenterInGird(selectedTowerX, selectedTowerY);

        float angle = (float) MathUtils.angleToPoint(pos.x, pos.y, screenX, screenY);
        int slice = MathUtils.selectedSlice(angle, UnitTower.Type.values().length);
        towerSelectionHUD.setSelectedSlice(slice);
    }

    public void mouseMoved(int screenX, int screenY){
        map.setHovered(screenX, screenY);
    }

    public void placeTower(int screenX, int screenY){
        if (MathUtils.distanceP2P(screenX, screenY, selectedTowerX, selectedTowerY) > towerSelectionHUD.getWidth() * 0.5) {
            Vector2 pos = map.posToGrid(selectedTowerX, selectedTowerY);
            Vector2 posnext = map.gridToPos(pos.x + 1, pos.y + 1);
            if (!UnitBase.GetUnitsInRect(selectedTowerX + 1, selectedTowerY + 1, posnext.x - 1, posnext.y - 1).isEmpty())
                return;

            int selectedPiece = towerSelectionHUD.getSelectedSlice();
            if (selectedPiece >= 0) {
                UnitTower.Type type = UnitTower.Type.values()[selectedPiece];
                if (type.get(0).price <= money) {
                    money -= type.get(0).price;
                    UnitTower tower = new UnitTower(this, type);
                    Vector2 towerpos = map.getCenterInGird(selectedTowerX, selectedTowerY);
                    tower.setCenterPosition(towerpos.x, towerpos.y);
                }
            }
        }
    }

    public void upgradeTower(int screenX, int screenY){
        Vector2 p = map.posToGrid(screenX, screenY);
        Vector2 pos = map.gridToPos(p);
        Vector2 posnext = map.gridToPos(p.x + 1, p.y + 1);
        ArrayList<UnitBase> units = UnitBase.GetUnitsInRect(pos.x + 1, pos.y + 1, posnext.x - 1, posnext.y - 1);
        if(units.isEmpty())
            return;
        for(UnitBase unit: units){
            if(unit instanceof UnitTower){
                UnitTower tower = (UnitTower) unit;
                tower.levelup();
            }
        }
    }

    public Map getMap() {
        return map;
    }

    @Override
    public void resize (int width, int height) {
        map.resize(game.getCamera().viewportWidth, game.getCamera().viewportHeight);
    }

}