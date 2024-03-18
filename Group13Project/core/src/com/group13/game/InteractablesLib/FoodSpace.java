package com.group13.game.InteractablesLib;

import com.badlogic.gdx.math.Vector2;
import com.group13.game.Group13Game;
import com.group13.game.Player;

import java.util.ArrayList;
import java.util.Random;

public abstract class FoodSpace extends InteractableSpaces {
    protected float hunger = 100;

    public FoodSpace(float x, float y, float new_width, float new_height, String new_name) {
        position = new Vector2(x, y); width = new_width; height = new_height; name = new_name;
    }

    @Override
    public void applyInteractions(Player player) {
        ArrayList<String> text = new ArrayList<String>();
        text.add("");
        text.add("Piazza");
        text.add("test");
        Group13Game.settext(text);
        GiveHunger(player);
        player.Ate();
    }

    public void GiveHunger(Player player){

    }

}
