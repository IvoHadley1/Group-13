package com.group13.game;

import com.badlogic.gdx.math.Vector2;

public abstract class RecreationalSpace implements Interactable {
    protected Vector2 position;

    public RecreationalSpace(float x, float y) {
        position = new Vector2(x, y);
    }

}
