package com.group13.game.InteractablesLib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.group13.game.Player;

public abstract class InteractableSpaces implements Interactable {
    
    protected Vector2 position;
    protected float width;
    protected float height;

    // Check if the player is within a certain distance and the E key is pressed
    public boolean canInteract(Player player) {
        float distance = position.dst(player.getPosition()); // Assuming getPosition() returns a Vector2

        return distance < 15 && Gdx.input.isKeyPressed(Input.Keys.E);
    }

    @Override
    public void interact(Player player) {
        if (canInteract(player)) {
            applyInteractions(player); // Make sure to pass the player object
        }

        player.UpdateScorePercentages();
    }

    // Define applyInteractions to take a Player parameter; implementation will be in subclasses
    public abstract void applyInteractions(Player player);

}
