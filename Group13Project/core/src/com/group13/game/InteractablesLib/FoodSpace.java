package com.group13.game.InteractablesLib;

import com.badlogic.gdx.math.Vector2;
import com.group13.game.Group13Game;
import com.group13.game.Player;

import java.util.ArrayList;
import java.util.Random;

public abstract class FoodSpace extends InteractableSpaces {
    protected float hunger = 100;
    protected float eatingScore = 100;

    public FoodSpace(float x, float y, float new_width, float new_height, String new_name) {
        position = new Vector2(x, y);
        width = new_width;
        height = new_height;
        name = new_name;
    }

    @Override
    public void applyInteractions(Player player) {
        ArrayList<String> text = new ArrayList<String>();
        text.add("");
        text.add("You went to get food at the Piazza!");
        Group13Game.addTime(0, 30);
        Group13Game.settext(text);
        GiveHunger(player);
        GiveEatingScore(player);
        player.Ate();
    }

    public void GiveHunger(Player player) {
        player.setCurrentHunger(player.getCurrentHunger() + this.hunger);
    }

    public void GiveEatingScore(Player player) {
        Random random = new Random(); // Create a Random object for generating random numbers

        // Calculate the minimum eating score based on player's current energy
        float energyDifference = (player.maxEnergy - player.getCurrentEnergy());
        float minEatingScore = (this.eatingScore - energyDifference);

        // Ensure the minimum eating score is not less than 0
        minEatingScore = Math.max(minEatingScore, 0);

        // Generate a random eating score
        float randomEatingScore = minEatingScore + random.nextFloat((this.eatingScore - minEatingScore + 1));

        // Add the calculated random eating score to the player
        player.addEatingScore(randomEatingScore);
    }

}
