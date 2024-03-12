package com.group13.game.InteractablesLib;

import com.badlogic.gdx.math.Vector2;
import com.group13.game.Player;

import java.util.Random;

public abstract class RecreationalSpace implements Interactable {
    protected Vector2 position;
    protected float motivation;
    protected float motivationScore = 100;

    public RecreationalSpace(float x, float y) {
        position = new Vector2(x, y);
    }

    public void GiveMotivation(Player player){
        player.setCurrentMotivation(player.getCurrentMotivation() + this.motivation);
    };

    public void GiveMotivationScore(Player player) {
        Random random = new Random(); // Create a Random object for generating random numbers

        // Calculate the minimum motivation score based on player's current energy
        float energyDifference = (player.maxEnergy - player.getCurrentEnergy());
        float minMotivationScore = (this.motivationScore - energyDifference);

        // Ensure the minimum motivation score is not less than 0
        minMotivationScore = Math.max(minMotivationScore, 0);

        // Generate a random motivation score between minMotivationScore (inclusive) and this.motivationScore (exclusive)
        // To include motivationScore as well, you add 1 to the upper bound.
        float randomMotivationScore = minMotivationScore + random.nextFloat((this.motivationScore - minMotivationScore + 1));

        // Add the calculated random motivation score to the player
        player.addMotivationScore(randomMotivationScore);
    }


}
