package com.group13.game.InteractablesLib;

import com.badlogic.gdx.math.Vector2;

public abstract class StudySpace implements Interactable {
    protected Vector2 position;

    public StudySpace(float x, float y) {
        position = new Vector2(x, y);
    }
}
