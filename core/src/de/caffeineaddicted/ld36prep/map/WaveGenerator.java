package de.caffeineaddicted.ld36prep.map;

import de.caffeineaddicted.ld36prep.screens.InGameScreen;
import de.caffeineaddicted.ld36prep.util.MathUtils;

/**
 * Created by Niels on 21.08.2016.
 */
abstract public class WaveGenerator {
    protected Map map;
    protected InGameScreen screen;
    protected int remainingSpawns;
    private int minSpawn;
    private int maxSpawn;
    private int waveCount;
    private float tickWaitTimer, currentWaitTimer;
    private float tickDeferTimer, currentDeferTimer;
    private State state;

    public WaveGenerator(InGameScreen screen, Map map) {
        this.map = map;
        this.screen = screen;
        minSpawn = maxSpawn = 0;
        tickWaitTimer = -1;
        currentWaitTimer = 0;
        this.state = State.NEXTWAVE;
        waveCount = 0;
    }

    public void tick(float delta) {
        if (tickWaitTimer < 0)
            return;
        if (state == State.NEXTWAVE) {
            currentWaitTimer += delta;
            if (currentWaitTimer > tickWaitTimer) {
                currentWaitTimer -= tickWaitTimer;
                remainingSpawns = MathUtils.random(getMinSpawn(), getMaxSpawn());

                state = State.DEFER;
                waveCount++;
            }
        } else if (state == State.DEFER) {
            currentDeferTimer += delta;
            if (currentDeferTimer > tickDeferTimer) {
                currentDeferTimer -= tickDeferTimer;
                spawn();
                if (remainingSpawns < 0)
                    state = State.NEXTWAVE;
            }
        }
    }

    abstract protected void spawn();

    public int getMinSpawn() {
        return minSpawn;
    }

    public void setMinSpawn(int minSpawn) {
        this.minSpawn = minSpawn;
    }

    public int getMaxSpawn() {
        return Math.max(minSpawn, maxSpawn);
    }

    public void setMaxSpawn(int maxSpawn) {
        this.maxSpawn = maxSpawn;
    }

    public float getTickWaitTimer() {
        return tickWaitTimer;
    }

    public void setTickWaitTimer(float tickWaitTimer) {
        this.tickWaitTimer = tickWaitTimer;
    }

    public float getCurrentWaitTimer() {
        return currentWaitTimer;
    }

    public void setCurrentWaitTimer(float currentWaitTimer) {
        this.currentWaitTimer = currentWaitTimer;
    }

    public float getCurrentDeferTimer() {
        return currentDeferTimer;
    }

    public void setCurrentDeferTimer(float currentDeferTimer) {
        this.currentDeferTimer = currentDeferTimer;
    }

    public float getTickDeferTimer() {
        return tickDeferTimer;
    }

    public void setTickDeferTimer(float tickDeferTimer) {
        this.tickDeferTimer = tickDeferTimer;
    }

    public float getRemainingTime() {
        if (state == State.NEXTWAVE) {
            return Math.max(0.f, getTickWaitTimer() - getCurrentWaitTimer() + getTickDeferTimer());
        }
        return Math.max(0.f, remainingSpawns * getTickDeferTimer() - getCurrentDeferTimer());
    }

    public int getWaveCount() {
        return waveCount;
    }

    public void skipToNextWave() {
        if (state == State.NEXTWAVE) {
            setCurrentWaitTimer(Math.max(getCurrentWaitTimer(), getTickWaitTimer() - 10));
        }
    }

    private enum State {
        NEXTWAVE,
        DEFER
    }
}
