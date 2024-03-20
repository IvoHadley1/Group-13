package com.group13.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.ArrayList;
import java.util.Arrays;


public class Group13Game extends Game {

    private ShapeRenderer shapeRenderer;

    Player theStudent;
    Gym theGym;
    Library theLibrary;
    Bed studentRoom;
    Food Piazza;

    private Texture texture;
    private static boolean showTextbox = false;
    private static ArrayList<String> textboxText = new ArrayList<String>();
    private static int currenttext = 0;

    private static float timer;
    private static int hourtimer;
    private String displaytimer;
    private static boolean freezetimer = false;
    private static boolean daytransition = false;
    private static int screenwipex = 0;
    private static int screenwipey = 0;
    private static float transitiontimer = 0;
    private ArrayList<String> dates = new ArrayList<String>(Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday", "End"));
    private int day;

    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    OrthographicCamera camera;

    @Override
    public void create() {
        // Initialize game-related objects and variables
        textboxText.add("test");
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera(1200, 600);
        camera.position.set(600, 300, 0);
        camera.update();
        day = -1;
        mapRenderer = setupMap();


        // Set the initial screen to MainMenuScreen
        setScreen(new MainMenuScreen(this));
    }

    public void startGame() {
        // Initialize game objects and start the game
        theStudent = new Player(100, 100);
        theGym = new Gym(3570, 1130, 30, 150, "Gym");
        theStudent.addcollision(theGym);
        theLibrary = new Library(400, 100, 450, 600, "CS Building");
        theStudent.addcollision(theLibrary);
        studentRoom = new Bed(630, 1650, 500, 200, "Dorm Room");
        theStudent.addcollision(studentRoom);
        Piazza = new Food(2150, 250, 150, 600, "The Piazza");
        theStudent.addcollision(Piazza);
        startDay();
    }

    //    public void setupGame() {
    //        theStudent = new Player(100, 100);
    //        theGym = new Gym(3570, 1130, 30, 150, "Gym");
    //        theStudent.addcollision(theGym);
    //        theLibrary = new Library(400, 100, 450, 600, "CS Building");
    //        theStudent.addcollision(theLibrary);
    //        studentRoom = new Bed(630, 1650, 500, 200, "Dorm Room");
    //        theStudent.addcollision(studentRoom);
    //        Piazza = new Food(2150, 250, 150, 600, "The Piazza");
    //        theStudent.addcollision(Piazza);
    //        mapRenderer = setupMap();
    //        startDay();
    //    }

    public OrthogonalTiledMapRenderer setupMap() {
        try {
            map = new TmxMapLoader().load("Map1.tmx");
            Gdx.app.log("MapLoader", "Map loaded successfully");
            return new OrthogonalTiledMapRenderer(map, 1);
        } catch (Exception e) {
            Gdx.app.error("MapLoader", "Error loading map", e);
            return null;
        }
    }

    @Override
    public void render() {
        if (getScreen() instanceof GameScreen) {

            camera.update();
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            theGym.draw(shapeRenderer);
            theLibrary.draw(shapeRenderer);
            studentRoom.draw(shapeRenderer);
            Piazza.draw(shapeRenderer);


            //genuinely no idea about what the error might be here ngl, it prints map rendered successfully but the map is purely white.
            if (mapRenderer != null) {
                mapRenderer.setView(camera);
                mapRenderer.render();
                //Gdx.app.log("MapRenderer", "Map rendered successfully");
            } else {
                //(Gdx.app.log("MapRenderer", "Map renderer is null");
            }

            HandlePlayerActions();

            if (showTextbox) {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(1, 1, 1, 1); // Yellow color
                shapeRenderer.rect(820, 100, 2000, 300);
                shapeRenderer.end();

                BitmapFont font = new BitmapFont();
                font.getData().setScale(7);
                font.setColor(Color.BLACK);
                Batch batch = new SpriteBatch();
                batch.begin();
                font.draw(batch, textboxText.get(currenttext), 850, 380);
                batch.end();

                if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
                    currenttext++;
                    if (currenttext >= textboxText.size()) {
                        hidetextbox();
                        if (textboxText.get(1) == "You went to sleep until 9am." || textboxText.get(1) == "You fell asleep!") {
                            endDay(theStudent);
                        } else {
                            theStudent.startmovement();
                        }
                    }
                }
            }

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.WHITE); // Yellow color
            if (dates.get(day) != "Wednesday") {
                shapeRenderer.rect(1540, 1845, 550, 120);
            } else {
                shapeRenderer.rect(1540, 1845, 620, 120);
            }
            shapeRenderer.end();

            Batch batch = new SpriteBatch();
            String timerimage = "";
            if (hourtimer < 16) {
                timerimage = "sun.png";
            } else if (hourtimer < 20) {
                timerimage = "evening.png";
            } else {
                timerimage = "moon.png";
            }
            texture = new Texture(Gdx.files.internal(timerimage));
            batch.begin();
            batch.draw(texture, 1555, 1855, 100, 100);
            batch.end();

            HandleTimer();

            if (daytransition) {
                HandleDayChange();
            }
        }

        //Bruh, even in game screen I can somehow click on tutorial or play buttons???
        //System.out.println(getScreen());
        super.render();

    }

    private void startDay() {
        hourtimer = 9;
        timer = 0;
        day++;
        theStudent.setPosition(690, 1550);
    }

    private void HandleDayChange() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK); // Yellow color
        shapeRenderer.rect(screenwipex, 0, 4000, 2000);
        shapeRenderer.end();
        freezetimer = true;
        showTextbox = false;

        if (dates.get(day) != "End"){
            BitmapFont font = new BitmapFont();
            font.getData().setScale(8);
            font.setColor(Color.WHITE);

            Batch batch = new SpriteBatch();
            String timerimage = "";
            if (hourtimer > 9) {
                timerimage = "moon.png";
            } else {
                timerimage = "sun.png";
            }
            texture = new Texture(Gdx.files.internal(timerimage));
            batch.begin();
            batch.draw(texture, screenwipex + 1650, screenwipey + 800, 600, 600);
            font.draw(batch, dates.get(day), screenwipex + 1800 - (dates.get(day).length() * 15), screenwipey + 600);
            batch.end();
        }

        //funky animation stuff
        if (screenwipex > 0) {
            screenwipex -= 200;
        } else {
            transitiontimer += Gdx.graphics.getDeltaTime();
        }
        if (transitiontimer >= 1 && (screenwipey <= 0 || screenwipey > 200)) {
            screenwipey -= 100;
        }
        if (screenwipey < -1000) {
            screenwipey = 2000;
            startDay();
        }
        if (dates.get(day) != "End") {
            if (transitiontimer >= 3) {
                screenwipex -= 200;
            }
            if (screenwipex <= -4000) {
                daytransition = false;
                theStudent.startmovement();
                freezetimer = false;
            }
        }
        else{
            //final day
            theStudent.calculateFinalScore();
            float[] scores = theStudent.getscores();

            BitmapFont font = new BitmapFont();
            font.getData().setScale(7);
            font.setColor(Color.BLACK);

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.WHITE); // Yellow color
            shapeRenderer.rect(1200, screenwipey + 300, 1000, 1000);
            shapeRenderer.end();

            Batch batch = new SpriteBatch();
            batch.begin();
            font.draw(batch, "Times Studied: " + scores[0], 1250, screenwipey + 1150);
            font.draw(batch, "Times Slept: " + scores[1], 1250, screenwipey + 1000);
            font.draw(batch, "Times Relaxed: " + scores[2], 1250, screenwipey + 850);
            font.draw(batch, "Times Eaten " + scores[3], 1250, screenwipey + 700);
            font.draw(batch, "Final Score " + scores[4], 1250, screenwipey + 550);
            batch.end();
        }
    }

    private void HandleTimer() {
        if (!freezetimer) {
            timer += Gdx.graphics.getDeltaTime() / 2;
        }
        if (timer > 60) {
            timer = timer - 60;
            hourtimer++;
        }
        if (timer < 10) {
            displaytimer = Integer.toString(hourtimer) + ":0" + String.valueOf((int) timer);
        }
        else {
            displaytimer = Integer.toString(hourtimer) + ":" + String.valueOf((int) timer);
        }

        //draw timer
        BitmapFont font = new BitmapFont();
        font.getData().setScale(4);
        Batch batch = new SpriteBatch();
        batch.begin();
        font.setColor(Color.BLACK);
        font.draw(batch, dates.get(day) + " " + displaytimer, 1675, 1930);
        batch.end();

        if (hourtimer > 23 && !freezetimer) {
            //past midnight!
            freezetimer = true;
            theStudent.lockmovement();
            settext(new ArrayList<>(Arrays.asList("It's too late at night!", "You fell asleep!")));
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

    public static void drawtextbox() {
        showTextbox = true;
        freezetimer = true;
    }

    public static void hidetextbox() {
        showTextbox = false;
        freezetimer = false;
    }

    public static void settext(ArrayList<String> text) {
        currenttext = 0;
        textboxText = text;
    }

    public static void addTime(int hours, float minutes) {
        hourtimer += hours;
        timer += minutes;
    }

    public static int getTime() {
        return hourtimer;
    }

    public static void endDay(Player student) {
        student.UpdateScorePercentages();
        daytransition = true;
        screenwipex = 4500;
        screenwipey = 0;
        transitiontimer = 0;
    }
}
