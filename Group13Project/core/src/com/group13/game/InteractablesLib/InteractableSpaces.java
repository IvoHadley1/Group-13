package com.group13.game.InteractablesLib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.group13.game.Group13Game;
import com.group13.game.Player;

public abstract class InteractableSpaces implements Interactable {

    protected Vector2 position;
    protected float width;
    protected float height;
    private ShapeRenderer shapeRenderer;
    protected String name;

    // Check if the player is within a certain distance and the E key is pressed
    public boolean canInteract(Player player) {
        float distance = position.dst(player.getPosition()); // Assuming getPosition() returns a Vector2

        return distance < 15 && Gdx.input.isKeyPressed(Input.Keys.E);
    }

    @Override
    public void interact(Player player) {
        BitmapFont font = new BitmapFont();
        font.getData().setScale(3);
        Batch batch = new SpriteBatch();
        batch.begin();
        font.draw(batch, name, position.x + (width / 2) - (name.length() * 8), position.y + height + 90);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.E) && Group13Game.hourtimer < 24) {
            player.lockmovement();
            applyInteractions(player); // Make sure to pass the player object
            Group13Game.drawtextbox();
        }
    }

    // Define applyInteractions to take a Player parameter; implementation will be in subclasses
    public abstract void applyInteractions(Player player);

    public Vector2 getPosition() {
        return position;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
