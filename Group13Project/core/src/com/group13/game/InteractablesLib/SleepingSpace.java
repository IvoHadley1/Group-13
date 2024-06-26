package com.group13.game.InteractablesLib;

import com.badlogic.gdx.math.Vector2;
import com.group13.game.Group13Game;
import com.group13.game.Player;

import java.util.ArrayList;
import java.util.Random;

public abstract class SleepingSpace extends InteractableSpaces {
    protected float energy;
    protected float sleepScore = 100;

    public SleepingSpace(float x, float y, float new_width, float new_height, String new_name) {
        position = new Vector2(x, y);
        width = new_width;
        height = new_height;
        name = new_name;
    }

    @Override
    public void applyInteractions(Player player) {
        ArrayList<String> text = new ArrayList<String>();
        int time = Group13Game.getTime();
        if (time < 16) {
            text.add("");
            text.add("It's too early to sleep!");
            Group13Game.settext(text);
        } else {
            text.add("");
            text.add("You went to sleep until 9am.");
            Group13Game.settext(text);
            GiveEnergy(player);
            GiveSleepScore(player);
            player.Slept();
            Group13Game.endDay(player); // Call the endDay method to trigger day transition
        }
    }

    public void GiveEnergy(Player player) {
        player.setCurrentEnergy(player.getCurrentEnergy() + this.energy);
    }

    public void GiveSleepScore(Player player) {
        Random random = new Random();

        // Calculate the minimum sleep score based on player's current energy
        float energyDifference = (player.maxEnergy - player.getCurrentEnergy());
        float minSleepScore = this.sleepScore - energyDifference;

        // Ensure the minimum sleep score is not less than 0
        minSleepScore = Math.max(minSleepScore, 0);

        // Generate a random sleep score
        float randomSleepScore = (float) (minSleepScore + (Math.random() * (this.sleepScore - minSleepScore + 1)));

        // Add the calculated random sleep score to the player
        player.addMotivationScore(randomSleepScore);
    }

}
