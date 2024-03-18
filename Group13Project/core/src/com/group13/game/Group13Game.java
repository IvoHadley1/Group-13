package com.group13.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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
import java.util.Arrays;


public class Group13Game extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    Player theStudent;
    Gym theGym;
    Library theLibrary;
    Bed studentRoom;
    Food Piazza;
    private Texture texture;
    private static boolean showTextbox = false;
    private static ArrayList<String> textboxText =new ArrayList<String>();
    private static int currenttext = 0;

    private static float timer;
    private static int hourtimer;
    private String displaytimer;
    private static boolean freezetimer = false;
    private static boolean daytransition = false;
    private static int screenwipex = 0;
    private static int screenwipey = 0;
    private static float transitiontimer = 0;
    private ArrayList<String> dates = new ArrayList<String>(Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));
    private int day;

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

        day = -1;

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
                    if (textboxText.get(1) == "You went to sleep until 9am." || textboxText.get(1) == "You fell asleep!"){
                        endDay();
                    }
                    else{
                        theStudent.startmovement();
                    }
                }
            }
        }

        Batch batch = new SpriteBatch();
        String timerimage = "";
        if (hourtimer < 16) {timerimage = "sun.png";}
        else if (hourtimer < 20) {timerimage = "evening.png";}
        else {timerimage = "moon.png";}
        texture = new Texture(Gdx.files.internal(timerimage));
        batch.begin();
        batch.draw(texture, 415, 717, 50, 50);
        batch.end();

        if (daytransition) {
            HandleDayChange();
        }
    }

    private void startDay(){
        hourtimer = 9;
        timer = 0;
        day++;
    }

    private void HandleDayChange(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK); // Yellow color
        shapeRenderer.rect(screenwipex, 0, 1300, 1000);
        shapeRenderer.end();
        freezetimer = true;

        BitmapFont font = new BitmapFont();
        font.getData().setScale(5);
        font.setColor(Color.WHITE);

        Batch batch = new SpriteBatch();
        String timerimage = "";
        if (hourtimer > 9) {timerimage = "moon.png";}
        else {timerimage = "sun.png";}
        texture = new Texture(Gdx.files.internal(timerimage));
        batch.begin();
        batch.draw(texture, screenwipex + 550, screenwipey + 400, 200, 200);
        font.draw(batch, dates.get(day), screenwipex + 560 - (dates.get(day).length() * 8), screenwipey + 300);
        batch.end();

        //funky animation stuff
        if (screenwipex > 0){screenwipex -= 70;}
        else{transitiontimer += Gdx.graphics.getDeltaTime();}
        if (transitiontimer >= 1 && (screenwipey <= 0 || screenwipey > 50)){screenwipey -= 70;}
        if (screenwipey < -500){screenwipey = 1000; startDay();}
        if (transitiontimer >= 3){screenwipex -= 70;}
        if (screenwipex <= -1000){daytransition = false; theStudent.startmovement(); freezetimer = false;}
    }

    private void HandleTimer(){
        if (!freezetimer){
            timer += Gdx.graphics.getDeltaTime();
        }
        if (timer > 60){
            timer = timer - 60;
            hourtimer++;
        }
        if (timer < 10){ displaytimer = Integer.toString(hourtimer) + ":0" + String.valueOf((int) timer);}
        else {displaytimer = Integer.toString(hourtimer) + ":" + String.valueOf((int) timer);}

        //draw timer
        BitmapFont font = new BitmapFont();
        font.getData().setScale(3);
        Batch batch = new SpriteBatch();
        batch.begin();
        font.draw(batch, dates.get(day) + " " + displaytimer, 480, 760);
        batch.end();

        if (hourtimer > 23 && !freezetimer){
            //past midnight!
            freezetimer = true;
            theStudent.lockmovement();
            settext(new ArrayList<>(Arrays.asList("It's too late at night!","You fell asleep!")));
            drawtextbox();
        }
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
        freezetimer = true;
    }

    public static void hidetextbox(){
        showTextbox = false;
        freezetimer = false;
    }

    public static void settext(ArrayList<String> text){
        currenttext = 0;
        textboxText = text;
    }

    public static void addTime(int hours, float minutes){
        hourtimer += hours;
        timer += minutes;
    }

    public static int getTime(){
        return hourtimer;
    }

    public static void endDay(){
        daytransition = true;
        screenwipex = 1500;
        screenwipey = 0;
        transitiontimer = 0;
    }
}
