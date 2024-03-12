package com.group13.game.InteractablesLib;

import com.badlogic.gdx.math.Vector2;
import com.group13.game.Player;

import java.util.Random;

public abstract class StudySpace implements Interactable {
    protected Vector2 position;
    protected float studyScore = 100;

    public StudySpace(float x, float y) {
        position = new Vector2(x, y);
    }

    public void GiveStudyScore(Player player){
        Random random = new Random();

        // Calculate the minimum study score based on player's current energy
        float energyDifference = (player.maxEnergy - player.getCurrentEnergy());
        float minStudyScore = (this.studyScore - energyDifference);

        // Ensure the minimum study score is not less than 0
        minStudyScore = Math.max(minStudyScore, 0);

        // Generate a random study score
        float randomStudyScore = minStudyScore + random.nextFloat((this.studyScore - minStudyScore + 1));

        // Add the calculated random study score to the player
        player.addMotivationScore(randomStudyScore);
    };

}
