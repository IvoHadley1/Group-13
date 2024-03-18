package com.group13.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.group13.game.InteractablesLib.FoodSpace;
import com.group13.game.InteractablesLib.StudySpace;

public class Food extends FoodSpace {
    public Food(float x, float y, float new_width, float new_height, String new_name) {
        super(x, y, new_width, new_height, new_name);
        this.hunger = 100; // if we want to override the studyScore, we can change this
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
