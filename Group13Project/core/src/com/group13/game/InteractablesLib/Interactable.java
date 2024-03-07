package com.group13.game.InteractablesLib;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.group13.game.Player;

public interface Interactable {
    void interact(Player player);

    void draw(ShapeRenderer shapeRenderer);
}
