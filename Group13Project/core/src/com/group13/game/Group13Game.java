package com.group13.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class Group13Game extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    Player theStudent;

    @Override
    public void create() {

        shapeRenderer = new ShapeRenderer();
        theStudent = new Player(100, 100);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        HandlePlayerActions();
    }

    private void HandlePlayerActions() {
        theStudent.draw(shapeRenderer);
        theStudent.update(Gdx.graphics.getDeltaTime());
        handleInput();
    }

    private void handleInput() {
        float moveX = 0;
        float moveY = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            moveY += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            moveY -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            moveX -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveX += 1;
        }

        theStudent.setMovementDirection(moveX, moveY);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

}
