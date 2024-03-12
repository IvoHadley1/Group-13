package com.group13.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.group13.game.InteractablesLib.SleepingSpace;

public class Bed extends SleepingSpace {
    public Bed(float x, float y) {
        super(x, y);
        this.sleepScore = 100; // if we want to override the sleepScore, we can change this
        this.energy = 100; // set the energy we want to give to the player
    }

    @Override
    public void interact(Player player) {
        // Implement specific interaction logic for the dorm
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer) {
        // Implement specific drawing logic for the dorm
    }
}
