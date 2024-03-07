package com.group13.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.group13.game.InteractablesLib.StudySpace;

public class Library extends StudySpace {
    public Library(float x, float y) {
        super(x, y);
    }

    @Override
    public void interact(Player player) {
        // Implement specific interaction logic for the library
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer) {
        // Implement specific drawing logic for the library
    }
}
