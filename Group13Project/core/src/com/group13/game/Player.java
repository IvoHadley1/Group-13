package com.group13.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.group13.game.InteractablesLib.Interactable;
import com.group13.game.InteractablesLib.InteractableSpaces;

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

    private float currentHunger; //set to 3 in morning, -1 when eating. penalty if not 0 at end of day

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

    private final int actiondistance = 25;
    private boolean canmove = true;

    private List<InteractableSpaces> collisions = new ArrayList<InteractableSpaces>();

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

    public float getCurrentHunger() {return currentHunger;}
    public void setCurrentHunger(float hunger) {currentHunger = hunger;}

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
        if (canmove){
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
            for (int i = 0; i< collisions.size(); i++) {
                if ((position.x - radius < collisions.get(i).getPosition().x + collisions.get(i).getWidth())
                        && (position.x - radius > collisions.get(i).getPosition().x - collisions.get(i).getWidth())
                        && (position.y - radius < collisions.get(i).getPosition().y + collisions.get(i).getHeight())
                        && (position.y - radius > collisions.get(i).getPosition().y - collisions.get(i).getHeight())) {
                    //then colliding
                    position.mulAdd(playerMovement, -playerSpeed * delta);
                }
            }

            //detect if close enough to interact

            for (int i = 0; i< collisions.size(); i++) {
                if ((position.x - radius < collisions.get(i).getPosition().x + collisions.get(i).getWidth() + actiondistance)
                        && (position.x - radius > collisions.get(i).getPosition().x - collisions.get(i).getWidth() - actiondistance)
                        && (position.y - radius < collisions.get(i).getPosition().y + collisions.get(i).getHeight() + actiondistance)
                        && (position.y - radius > collisions.get(i).getPosition().y - collisions.get(i).getHeight() - actiondistance)) {
                    collisions.get(i).interact(this);
                }
            }
        }
    }

    public void addcollision(InteractableSpaces newcollision){
        collisions.add(newcollision);
    }

    public void setMovementDirection(float x, float y) {
        playerMovement.set(x, y);
    }

    public void lockmovement(){
        canmove = false;
    }

    public void startmovement() {canmove = true;}
}
