package com.group13.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.group13.game.InteractablesLib.RecreationalSpace;

public class Gym extends RecreationalSpace {
    public Gym(float x, float y, float new_width, float new_height) {
        super(x, y, new_width, new_height);
        this.motivationScore = 100; // if we want to override the motivationScore, we can change this
        this.motivation = 25; // set the motivation we want to give to the player
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

