package com.group13.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.group13.game.InteractablesLib.RecreationalSpace;

public class Gym extends RecreationalSpace {
    public Gym(float x, float y) {
        super(x, y);
    }

    @Override
    public void GiveMotivation() {

    }

    @Override
    public void GiveMotivationScore() {
        
    }

    @Override
    public void interact(Player player) {
        // Implement specific interaction logic for the gym
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer) {
        // Implement specific drawing logic for the gym
    }
}

