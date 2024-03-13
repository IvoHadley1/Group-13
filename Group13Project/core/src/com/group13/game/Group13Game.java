package com.group13.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.group13.game.InteractablesLib.Interactable;
import com.group13.game.InteractablesLib.StudySpace;

import java.awt.*;


public class Group13Game extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    Player theStudent;
    Gym theGym;
    Library theLibrary;
    Bed studentRoom;

    @Override
    public void create() {

        shapeRenderer = new ShapeRenderer();
        theStudent = new Player(100, 100);
        // added for debug theGym = new Gym(50, 50);
        // can change values as needed
        theGym = new Gym(500,500, 30, 30);
        theStudent.addcollision(500,500,30,30);

        theLibrary = new Library(550, 500, 30, 30);
        theStudent.addcollision(550,500,30,30);

        studentRoom = new Bed(600,500, 30, 30);
        theStudent.addcollision(600,500,30,30);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        HandlePlayerActions();

        theGym.draw(shapeRenderer);
        theLibrary.draw(shapeRenderer);
        studentRoom.draw(shapeRenderer);
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
