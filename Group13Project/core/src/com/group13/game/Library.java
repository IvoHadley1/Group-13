package com.group13.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.group13.game.InteractablesLib.StudySpace;

public class Library extends StudySpace {
    public Library(float x, float y) {
        super(x, y);
        this.studyScore = 100; // if we want to override the studyScore, we can change this
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer) {
        // Implement specific drawing logic for the library
    }
}
