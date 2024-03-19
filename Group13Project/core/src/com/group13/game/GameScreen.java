package com.group13.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class GameScreen implements Screen {
    private Group13Game gameLogic; // Assuming Group13Game holds the game logic

    public GameScreen(Game game) {
        // Assuming Group13Game can be initialized without arguments
        // Or modify it to take arguments if necessary
        gameLogic = new Group13Game();
        gameLogic.create(); // Call any initialization methods needed for your game
    }

    @Override
    public void show() {
        gameLogic.setupGame();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1); // White background
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameLogic.render(); // Delegate to the game logic render method
    }

    @Override
    public void resize(int width, int height) {
        // Delegate resize to the game logic if needed
        gameLogic.resize(width, height);
    }

    @Override
    public void pause() {
        // Delegate pause to the game logic if it has this method
    }

    @Override
    public void resume() {
        // Delegate resume to the game logic if it has this method
    }

    @Override
    public void hide() {
        // Called when this screen is no longer the current screen
    }

    @Override
    public void dispose() {
        // Delegate dispose to the game logic to clean up resources
        gameLogic.dispose();
    }
}
