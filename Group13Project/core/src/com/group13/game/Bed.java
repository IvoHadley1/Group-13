package com.group13.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.group13.game.InteractablesLib.SleepingSpace;

public class Bed extends SleepingSpace {
    public Bed(float x, float y, float new_width, float new_height, String new_name) {
        super(x, y, new_width, new_height, new_name);
        this.sleepScore = 100; // if we want to override the sleepScore, we can change this
        this.energy = 100; // set the energy we want to give to the player
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer) {
        // placeholder simple shape render
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1); // Yellow color
        shapeRenderer.rect(this.position.x, this.position.y, this.width, this.height);
        shapeRenderer.end();
    }
}
