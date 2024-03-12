package com.group13.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.group13.game.InteractablesLib.RecreationalSpace;

public class Gym extends RecreationalSpace {
    public Gym(float x, float y) {
        super(x, y);
        this.motivationScore = 100; // if we want to override the motivationScore, we can change this
        this.motivation = 25; // set the motivation we want to give to the player
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer) {
        // Implement specific drawing logic for the gym
    }
}

