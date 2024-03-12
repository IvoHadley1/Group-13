package com.group13.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private final float playerSpeed;
    private final Vector2 playerMovement;
    private final Vector2 position;
    private float currentEnergy;
    private float currentMotivation;
    private int studyScore;
    private int motivationScore;
    private int sleepScore;

    public Player(float x, float y) {
        position = new Vector2(x, y);
        this.currentEnergy = 100;
        this.currentMotivation = 100;
        this.studyScore = 0;
        this.motivationScore = 0;
        this.sleepScore = 0;
        this.playerMovement = new Vector2(0, 0);
        this.playerSpeed = 250f;
    }

    // Getters and Setters

    public float getCurrentEnergy() {
        return currentEnergy;
    }

    public void setCurrentEnergy(float currentEnergy) {
        this.currentEnergy = Math.max(0, Math.min(currentEnergy, 100));
    }

    public float getCurrentMotivation() {
        return currentMotivation;
    }

    public void setCurrentMotivation(float currentMotivation) {
        this.currentMotivation = Math.max(0, Math.min(currentMotivation, 100));
    }

    public int getStudyScore() {
        return studyScore;
    }

    public void addStudyScore(int points) {
        this.studyScore = Math.max(0, Math.min(studyScore + points, 700));
    }

    public int getMotivationScore() {
        return motivationScore;
    }

    public void addMotivationScore(int points) {
        this.motivationScore = Math.max(0, Math.min(motivationScore + points, 700));
    }

    public int getSleepScore() {
        return sleepScore;
    }

    public void addSleepScore(int points) {
        this.sleepScore = Math.max(0, Math.min(sleepScore + points, 700));
    }

    // This method will be called from your render method in the main game screen
    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1); // Yellow color
        shapeRenderer.circle(position.x, position.y, 15);
        shapeRenderer.end();
    }

    public void update(float delta) {
        // Normalize the movement vector
        if (playerMovement.len() > 0) {
            playerMovement.nor();
        }

        // Update the player's position based on the movement vector and speed
        position.mulAdd(playerMovement, playerSpeed * delta);

        if (position.x < 0){ position.x = 0; }
        else if (position.y < 0){ position.y = 0; }
        else if (position.x > Gdx.graphics.getWidth()){ position.x = Gdx.graphics.getWidth(); }
        else if (position.y > Gdx.graphics.getHeight()){ position.y = Gdx.graphics.getHeight(); }
    }

    public void setMovementDirection(float x, float y) {
        playerMovement.set(x, y);
    }


}
