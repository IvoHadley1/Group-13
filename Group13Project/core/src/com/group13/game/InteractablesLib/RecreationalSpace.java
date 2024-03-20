package com.group13.game.InteractablesLib;

import com.badlogic.gdx.math.Vector2;
import com.group13.game.Group13Game;
import com.group13.game.Player;

import java.util.ArrayList;
import java.util.Random;

public abstract class RecreationalSpace extends InteractableSpaces {
    protected float motivation;
    protected float motivationScore = 100;

    public RecreationalSpace(float x, float y, float new_width, float new_height, String new_name) {
        position = new Vector2(x, y); width = new_width; height = new_height; name = new_name;
    }

    @Override
    public void applyInteractions(Player player) {
        ArrayList<String> text = new ArrayList<String>();
        text.add("");
        text.add("You went to the gym!");
        Group13Game.addTime(2, 0);
        Group13Game.settext(text);
        GiveMotivation(player);
        GiveMotivationScore(player);
        player.DidActivity();
    }

    public void GiveMotivation(Player player) {
        player.setCurrentMotivation(player.getCurrentMotivation() + this.motivation);
    }

    public void GiveMotivationScore(Player player) {
        Random random = new Random(); // Create a Random object for generating random numbers

        // Calculate the minimum motivation score based on player's current energy
        float energyDifference = (player.maxEnergy - player.getCurrentEnergy());
        float minMotivationScore = (this.motivationScore - energyDifference);

        // Ensure the minimum motivation score is not less than 0
        minMotivationScore = Math.max(minMotivationScore, 0);

        // Generate a random motivation score between minMotivationScore (inclusive) and this.motivationScore (exclusive)
        // To include motivationScore as well, you add 1 to the upper bound.
        float randomMotivationScore = minMotivationScore + (random.nextFloat() * (this.motivationScore - minMotivationScore + 1));

        // Add the calculated random motivation score to the player
        player.addMotivationScore(randomMotivationScore);
    }

}
