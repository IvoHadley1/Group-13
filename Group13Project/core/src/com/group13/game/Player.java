package com.group13.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.group13.game.InteractablesLib.InteractableSpaces;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final float playerSpeed;
    private final Vector2 playerMovement;
    private Vector2 position;
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

    private final int radius;

    private final int actiondistance;
    private boolean canmove = true;

    private List<InteractableSpaces> collisions = new ArrayList<InteractableSpaces>();

    //Animation Stuff
    private static final int FrameCols = 6, FrameRows = 1;
    Texture walkSheet;
    Animation<TextureRegion> walkAnimation;
    float stateTime;

    public Player(float x, float y) {
        radius = Gdx.graphics.getWidth() / 40;
        position = new Vector2(x, y);
        this.currentEnergy = this.maxEnergy;
        this.currentMotivation = 100;
        this.playerMovement = new Vector2(0, 0);
        this.playerSpeed = (float) Gdx.graphics.getWidth() / 6;

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

        actiondistance = Gdx.graphics.getWidth() / 40;

        walkSheet = new Texture(Gdx.files.internal("playeranim.png"));
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / FrameCols, walkSheet.getHeight() / FrameRows);
        TextureRegion[] walkFrames = new TextureRegion[FrameRows * FrameCols];
        int index = 0;
        for (int i = 0; i < FrameRows; i++) {
            for (int j = 0; j < FrameCols; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        walkAnimation = new Animation<TextureRegion>(0.2f, walkFrames);
        stateTime = 0f;
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

    public float getCurrentHunger() {
        return currentHunger;
    }

    public void setCurrentHunger(float hunger) {
        currentHunger = hunger;
    }

    public void addEatingScore(float points) {
        this.eatingScore += points;
    }

    public float getSleepScore() {
        return sleepScore;
    }

    public void addSleepScore(int points) {
        this.sleepScore = Math.max(0, Math.min(sleepScore + points, 700));
    }

    // This method will be called from your render method in the main game screen
    public void draw() {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        TextureRegion stillFrame = walkAnimation.getKeyFrame(0.25f);
        SpriteBatch batch = new SpriteBatch();
        batch.begin();
        if (playerMovement.len() == 0) {
            batch.draw(stillFrame, position.x, position.y, (float) Gdx.graphics.getWidth() / 25, (float) Gdx.graphics.getHeight() / 17);
        } else {
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                currentFrame.flip(true, false);
            }
            batch.draw(currentFrame, position.x, position.y, (float) Gdx.graphics.getWidth() / 25, (float) Gdx.graphics.getHeight() / 17);
            if (Gdx.input.isKeyPressed(Input.Keys.A)) { //needed to flip it back afterwards
                currentFrame.flip(true, false);
            }
        }
        batch.end();
    }

    public void update(float delta) {
        // Normalize the movement vector
        if (playerMovement.len() > 0) {
            playerMovement.nor();
        }

        // Calculate next position based on current movement
        Vector2 nextPosition = new Vector2(position).mulAdd(playerMovement, playerSpeed * delta);

        // Adjust collision box to be 25% smaller than interaction area
        float collisionRadius = radius * 0.75f;

        boolean collisionDetected = false;
        for (InteractableSpaces space : collisions) {
            // Calculate a reduced collision area for the player
            float reducedWidth = space.getWidth() * 0.75f;
            float reducedHeight = space.getHeight() * 0.75f;
            Vector2 reducedPosition = new Vector2(
                    space.getPosition().x + space.getWidth() * 0.125f, // Adjust position to account for reduced size
                    space.getPosition().y + space.getHeight() * 0.125f
            );

            // Check collision with the adjusted (reduced) areas
            if (nextPosition.x - collisionRadius < reducedPosition.x + reducedWidth &&
                    nextPosition.x + collisionRadius > reducedPosition.x &&
                    nextPosition.y - collisionRadius < reducedPosition.y + reducedHeight &&
                    nextPosition.y + collisionRadius > reducedPosition.y) {
                collisionDetected = true;
                break;
            }
        }

        // Update the player's position based on the movement vector and speed
        if (!collisionDetected && canmove) {
            position = nextPosition;

            // Prevent the player from moving off-screen
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
        }

        //detect if close enough to interact

        for (InteractableSpaces collision : collisions) {
            if ((position.x - radius < collision.getPosition().x + collision.getWidth())
                    && (position.x + radius > collision.getPosition().x - actiondistance)
                    && (position.y - radius < collision.getPosition().y + collision.getHeight())
                    && (position.y + radius > collision.getPosition().y - actiondistance)) {
                collision.interact(this);
            }
        }

    }

    public void addcollision(InteractableSpaces newcollision) {
        collisions.add(newcollision);
    }

    public void setMovementDirection(float x, float y) {
        playerMovement.set(x, y);
    }

    public void lockmovement() {
        canmove = false;
    }

    public void startmovement() {
        canmove = true;
    }

    public void setPosition(int x, int y) {
        position = new Vector2(x, y);
    }

    public float[] getscores() {
        float[] scores = new float[5];
        scores[0] = timesStudied;
        scores[1] = timesSlept;
        scores[2] = timesActivity;
        scores[3] = timesEaten;
        scores[4] = finalScore;
        return scores;
    }
}
