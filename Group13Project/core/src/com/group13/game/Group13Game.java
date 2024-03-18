package com.group13.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;
import com.group13.game.InteractablesLib.Interactable;
import com.group13.game.InteractablesLib.StudySpace;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class Group13Game extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    Player theStudent;
    Gym theGym;
    Library theLibrary;
    Bed studentRoom;

    Food Piazza;

    private static boolean showTextbox = false;
    private static ArrayList<String> textboxText =new ArrayList<String>();
    private static int currenttext = 0;

    private float timer;
    private int hourtimer;
    private String displaytimer;

    @Override
    public void create() {

        textboxText.add("test");

        shapeRenderer = new ShapeRenderer();
        theStudent = new Player(100, 100);
        // added for debug theGym = new Gym(50, 50);
        // can change values as needed
        theGym = new Gym(300,500, 30, 30, "Gym");
        theStudent.addcollision(theGym);

        theLibrary = new Library(500, 500, 30, 30, "Library");
        theStudent.addcollision(theLibrary);

        studentRoom = new Bed(700,500, 30, 30, "Dorm Room");
        theStudent.addcollision(studentRoom);

        Piazza = new Food(900, 500, 30, 30, "The Piazza");
        theStudent.addcollision(Piazza);

        startDay();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        HandlePlayerActions();
        HandleTimer();

        theGym.draw(shapeRenderer);
        theLibrary.draw(shapeRenderer);
        studentRoom.draw(shapeRenderer);
        Piazza.draw(shapeRenderer);

        if (showTextbox){
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1, 1, 1, 1); // Yellow color
            shapeRenderer.rect(100, 50, 1000, 150);
            shapeRenderer.end();

            BitmapFont font = new BitmapFont();
            font.getData().setScale(3);
            font.setColor(Color.BLACK);
            Batch batch = new SpriteBatch();
            batch.begin();
            font.draw(batch, textboxText.get(currenttext), 120, 180);
            batch.end();

            if (Gdx.input.isKeyJustPressed(Input.Keys.E)){
                currenttext++;
                if (currenttext >= textboxText.size()){
                    hidetextbox();
                    theStudent.startmovement();
                }
            }
        }
    }

    private void startDay(){
        hourtimer = 9;
        timer = 0;
    }

    private void HandleTimer(){
        timer += Gdx.graphics.getDeltaTime();
        if (timer > 60){
            timer = 0;
            hourtimer++;
        }
        if (timer < 10){ displaytimer = Integer.toString(hourtimer) + ":0" + String.valueOf((int) timer);}
        else {displaytimer = Integer.toString(hourtimer) + ":" + String.valueOf((int) timer);}

        //draw timer
        BitmapFont font = new BitmapFont();
        font.getData().setScale(3);
        Batch batch = new SpriteBatch();
        batch.begin();
        font.draw(batch, displaytimer, 550, 760);
        batch.end();
    }

    private void HandlePlayerActions() {
        theStudent.draw(shapeRenderer);
        theStudent.update(Gdx.graphics.getDeltaTime());
        handleInput();
    }

    private void handleInput() {
        float moveX = 0;
        float moveY = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            moveY += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            moveY -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            moveX -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveX += 1;
        }

        theStudent.setMovementDirection(moveX, moveY);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

    public static void drawtextbox(){
        showTextbox = true;
    }

    public static void hidetextbox(){
        showTextbox = false;
    }

    public static void settext(ArrayList<String> text){
        currenttext = 0;
        textboxText = text;
    }
}
