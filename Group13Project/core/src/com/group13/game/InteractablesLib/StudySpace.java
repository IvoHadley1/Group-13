package com.group13.game.InteractablesLib;

import com.badlogic.gdx.math.Vector2;
import com.group13.game.Group13Game;
import com.group13.game.Player;

import java.util.ArrayList;
import java.util.Random;

public abstract class StudySpace extends InteractableSpaces {
    protected float studyScore = 100;

    public StudySpace(float x, float y, float new_width, float new_height, String new_name) {
        position = new Vector2(x, y); width = new_width; height = new_height; name = new_name;
    }

    @Override
    public void applyInteractions(Player player) {
        ArrayList<String> text = new ArrayList<String>();
        text.add("");
        text.add("You studied in the CS building for a \nfew hours!");
        Group13Game.addTime(3,0);
        Group13Game.settext(text);
        GiveStudyScore(player);
        player.Studied();
    }


    public void GiveStudyScore(Player player) {
        Random random = new Random();

        // Calculate the minimum study score based on player's current energy
        float energyDifference = (player.maxEnergy - player.getCurrentEnergy());
        float minStudyScore = (this.studyScore - energyDifference);

        // Ensure the minimum study score is not less than 0
        minStudyScore = Math.max(minStudyScore, 0);

        // Generate a random study score
        float randomStudyScore = (float) (minStudyScore + (Math.random() * (this.studyScore - minStudyScore + 1)));

        // Add the calculated random study score to the player
        player.addMotivationScore(randomStudyScore);
    }

}
