package com.group13.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class GameScreen implements Screen {
    private Group13Game game;

    public GameScreen(Group13Game game) {
        this.game = game;

        // Initialization code that's currently in your create() method
        game.create();
    }

    @Override
    public void show() {
        // This method will be called when this screen is set, you can initialize your game world here
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Your game's rendering code goes here
        game.render();
    }

    @Override
    public void resize(int width, int height) {
        // Resize your game world here
        game.resize(width, height);
    }

    @Override
    public void pause() {
        // Handle pausing the game
    }

    @Override
    public void resume() {
        // Handle resuming the game
    }

    @Override
    public void hide() {
        // This method is called when this screen is no longer the current screen
    }

    @Override
    public void dispose() {
        // Dispose of assets and cleanup here
        game.dispose();
    }
}
