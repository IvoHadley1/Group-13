package com.group13.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface Interactable {
    void interact(Player player);

    void draw(ShapeRenderer shapeRenderer);
}
