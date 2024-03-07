package com.group13.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.group13.game.InteractablesLib.SleepingSpace;

public class Bed extends SleepingSpace {
    public Bed(float x, float y) {
        super(x, y);
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
