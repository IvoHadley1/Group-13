package com.group13.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private Vector2 position;
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
}
