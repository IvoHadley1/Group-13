package com.group13.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private final float playerSpeed;
    private final Vector2 playerMovement;
    private final Vector2 position;
    public final float maxEnergy = 100;

    private float currentEnergy;
    private float currentMotivation;

    private float studyScore;
    private float motivationScore;
    private float sleepScore;
    private float eatingScore;
    private float finalScore;

    private int timesStudied;
    private int timesSlept;
    private int timesActivity;
    private int timesEaten;

    private float studyScorePercentage;
    private float sleepingScorePercentage;
    private float motivationScorePercentage;
    private float eatingScorePercentage;

    private final int radius = 15;

    private List<List<Float>> collisionvalues = new ArrayList<List<Float>>();

    public Player(float x, float y) {
        position = new Vector2(x, y);
        this.currentEnergy = this.maxEnergy;
        this.currentMotivation = 100;
        this.playerMovement = new Vector2(0, 0);
        this.playerSpeed = 250f;

        this.studyScore = 0;
        this.motivationScore = 0;
        this.sleepScore = 0;
        this.eatingScore = 0;
        this.finalScore = 0;

        this.timesActivity = 0;
        this.timesEaten = 0;
        this.timesSlept = 0;
        this.timesStudied = 0;

        this.studyScorePercentage = 0;
        this.sleepingScorePercentage = 0;
        this.motivationScorePercentage = 0;
        this.eatingScorePercentage = 0;
    }

    // Update Current Score Percentages

    public void UpdateScorePercentages() {
        this.studyScorePercentage = calculateScorePercentage(studyScore, timesStudied);
        this.eatingScorePercentage = calculateScorePercentage(eatingScore, timesEaten);
        this.sleepingScorePercentage = calculateScorePercentage(sleepScore, timesSlept);
        this.motivationScorePercentage = calculateScorePercentage(motivationScore, timesActivity);
    }

    private float calculateScorePercentage(float totalScore, int count) {
        return count > 0 ? totalScore / count : 0;
    }

    public void calculateFinalScore() {
        this.finalScore = (studyScorePercentage + sleepingScorePercentage + motivationScorePercentage + eatingScorePercentage) / 4;
    }
    
    // Activity counters

    public void DidActivity() {
        this.timesActivity += 1;
    }

    public void Ate() {
        this.timesEaten += 1;
    }

    public void Slept() {
        this.timesSlept += 1;
    }

    public void Studied() {
        this.timesStudied += 1;
    }

    // Getters and Setters

    public Vector2 getPosition() {
        return position;
    }

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

    public float getStudyScore() {
        return studyScore;
    }

    public void addStudyScore(float points) {
        this.studyScore += points;
    }

    public float getMotivationScore() {
        return motivationScore;
    }

    public void addMotivationScore(float points) {
        this.motivationScore += points;
    }

    public float getSleepScore() {
        return sleepScore;
    }

    public void addSleepScore(int points) {
        this.sleepScore = Math.max(0, Math.min(sleepScore + points, 700));
    }

    // This method will be called from your render method in the main game screen
    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1); // Yellow color
        shapeRenderer.circle(position.x, position.y, radius);
        shapeRenderer.end();
    }

    public void update(float delta) {
        // Normalize the movement vector
        if (playerMovement.len() > 0) {
            playerMovement.nor();
        }

        // Update the player's position based on the movement vector and speed
        position.mulAdd(playerMovement, playerSpeed * delta);

        // Prevent movement off screen
        if (position.x < radius) {
            position.x = radius;
        }
        if (position.y < radius) {
            position.y = radius;
        }
        if (position.x > Gdx.graphics.getWidth() - radius) {
            position.x = Gdx.graphics.getWidth() - radius;
        }
        if (position.y > Gdx.graphics.getHeight() - radius) {
            position.y = Gdx.graphics.getHeight() - radius;
        }

        // Collision detection
        for (List<Float> collisionvalue : collisionvalues) {
            if (((position.x - radius < collisionvalue.get(0) + collisionvalue.get(2))
                    && (position.x - radius > collisionvalue.get(0) - collisionvalue.get(2))
                    && (position.y - radius < collisionvalue.get(1) + collisionvalue.get(3))
                    && (position.y - radius > collisionvalue.get(1) - collisionvalue.get(3)))) {
                //then colliding
                position.mulAdd(playerMovement, -playerSpeed * delta);
            }
        }
    }

    public void addcollision(float x, float y, float width, float height){
        List<Float> newcollision = new ArrayList<Float>();
        newcollision.add(x);
        newcollision.add(y);
        newcollision.add(width);
        newcollision.add(height);
        collisionvalues.add(newcollision);
    }

    public void setMovementDirection(float x, float y) {
        playerMovement.set(x, y);
    }


}
