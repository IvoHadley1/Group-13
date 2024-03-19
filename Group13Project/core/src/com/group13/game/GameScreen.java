package com.group13.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

public class GameScreen implements Screen, InputProcessor {
    private Group13Game gameLogic; // Assuming Group13Game holds the game logic

    public GameScreen(Game game) {
        // Assuming Group13Game can be initialized without arguments
        // Or modify it to take arguments if necessary
        gameLogic = new Group13Game();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        gameLogic.startGame();
    }

    @Override
    public void render(float delta) {

        gameLogic.render(); // Delegate to the game logic render method
    }

    @Override
    public void resize(int width, int height) {
        // Delegate resize to the game logic if needed
        gameLogic.resize(width, height);
    }

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
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
