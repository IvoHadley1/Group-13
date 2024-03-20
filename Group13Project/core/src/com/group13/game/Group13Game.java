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
import java.util.Objects;


public class Group13Game extends Game {

    private ShapeRenderer shapeRenderer;
    int screenwidth;
    int screenheight;
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
    public static int hourtimer;
    private String displaytimer;
    private static boolean freezetimer = false;
    private static boolean daytransition = false;
    private static int screenwipex;
    private static int screenwipey;
    private static float transitiontimer = 0;
    private ArrayList<String> dates = new ArrayList<String>(Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday", "End"));
    private static int day;

    private static TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    OrthographicCamera camera;

    private boolean shouldEndDay = false;
    private static float popupTimer = 0;
    private static boolean isPopupShown = false;


    @Override
    public void create() {
        screenwipex = 0;
        screenwipey = 0;
        screenwidth = Gdx.graphics.getWidth();
        screenheight = Gdx.graphics.getHeight();

        // Initialize game-related objects and variables
        textboxText.add("test");
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera(1200, 600);
        camera.position.set(600, 300, 0);
        camera.update();
        day = 0;
        mapRenderer = setupMap();


        // Set the initial screen to MainMenuScreen
        setScreen(new MainMenuScreen(this));
    }

    public static TiledMap getmap() {
        return map;
    }

    public void startGame() {
        // Initialize game objects and start the game
        theStudent = new Player(10, 10);
        theGym = new Gym((float) (screenwidth - 10), (float) (screenheight / 1.8), (float) screenwidth / 20, (float) screenheight / 10, "Gym");
        theStudent.addcollision(theGym);
        theLibrary = new Library((float) (screenwidth / 9), (float) (screenheight / 13), (float) (screenwidth / 7), (float) (screenheight / 3.5), "CS Building");
        theStudent.addcollision(theLibrary);
        studentRoom = new Bed((float) (screenwidth / 6), (float) (screenheight / 1.25), (float) screenwidth / 7, (float) screenheight / 7, "Dorm Room");
        theStudent.addcollision(studentRoom);
        Piazza = new Food((float) (screenwidth / 1.7), (float) screenheight / 8, (float) screenwidth / 14, (float) screenheight / 3, "The Piazza");
        theStudent.addcollision(Piazza);
        startDay();
    }

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
        super.render();
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

            if (shouldEndDay) {
                endDay(theStudent);
                shouldEndDay = false;
            }

            // Logic to automatically hide the popup after 1 second
            if (isPopupShown) {
                popupTimer += Gdx.graphics.getDeltaTime();
                if (popupTimer >= 1) { // 1 second has elapsed
                    hidetextbox(theStudent); // Call your method to hide the popup
                    isPopupShown = false;
                    popupTimer = 0; // Reset the timer
                }
            }

            if (showTextbox) {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(Color.WHITE);
                shapeRenderer.rect((float) (screenwidth / 9), (float) (screenheight / 9), (float) (screenwidth / 1.3), (float) screenheight / 5);
                shapeRenderer.end();

                BitmapFont font = new BitmapFont();
                font.getData().setScale((float) screenwidth / 400);
                font.setColor(Color.BLACK);
                Batch batch = new SpriteBatch();
                batch.begin();
                font.draw(batch, textboxText.get(currenttext), (float) (screenwidth / 8.5), (float) (screenheight / 3.5));
                batch.end();

                if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
                    currenttext++;
                    if (currenttext >= textboxText.size()) {
                        hidetextbox(theStudent);
                        if (Objects.equals(textboxText.get(1), "You went to sleep until 9am.") || Objects.equals(textboxText.get(1), "You fell asleep!")) {
                            endDay(theStudent);
                        } else {
                            theStudent.startmovement();
                        }
                    }
                }
            }

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.WHITE); // Yellow color
            if (!Objects.equals(dates.get(day), "Wednesday")) {
                shapeRenderer.rect((float) (screenwidth / 2.62), (float) (screenheight / 1.125), (float) (screenwidth / 3.6), (float) screenheight / 12);
            } else {
                shapeRenderer.rect((float) (screenwidth / 2.62), (float) (screenheight / 1.125), (float) (screenwidth / 3.1), (float) screenheight / 12);
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
            batch.draw(texture, (float) (screenwidth / 2.6), (float) (screenheight / 1.11), (float) (screenwidth / 25), (float) (screenheight / 16));
            batch.end();

            HandleTimer();

            if (daytransition) {
                HandleDayChange();
            }
        }

        //System.out.println(getScreen());
        super.render();

    }

    private void startDay() {
        hourtimer = 9;
        timer = 0;
        theStudent.setPosition((int) (screenwidth / 6.5), (int) (screenheight / 1.45));
    }

    private void HandleDayChange() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK); // Yellow color
        shapeRenderer.rect(screenwipex, 0, (float) (screenwidth * 1.5), (float) (screenheight * 1.5));
        shapeRenderer.end();
        freezetimer = true;
        showTextbox = false;

        if (!Objects.equals(dates.get(day), "End")) {
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
            batch.draw(texture, (float) (screenwipex + (screenwidth / 2.5)), (float) (screenwipey + (screenheight / 5)), (float) (screenwidth / 3.5), (float) (screenheight / 2.3));
            font.draw(batch, dates.get(day), screenwipex + ((float) screenwidth / 2) - (dates.get(day).length() * 15), screenwipey + ((float) screenheight / 6));
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
            day++;
        }
        if (!Objects.equals(dates.get(day), "End")) {
            if (transitiontimer >= 3) {
                screenwipex -= 200;
            }
            if (screenwipex <= -4000) {
                daytransition = false;
                theStudent.startmovement();
                freezetimer = false;
            }
        } else {
            //final day
            theStudent.calculateFinalScore();
            float[] scores = theStudent.getscores();

            BitmapFont font = new BitmapFont();
            font.getData().setScale((float) screenwidth / 350);
            font.setColor(Color.BLACK);

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.WHITE); // Yellow color
            shapeRenderer.rect((float) (screenwidth / 3.5), (float) (screenwipey), (float) (screenwidth / 2.5), (float) (screenheight / 2));
            shapeRenderer.end();

            Batch batch = new SpriteBatch();
            batch.begin();
            font.draw(batch, "Times Studied: " + scores[0], (float) (screenwidth / 3.2), screenwipey + (float) (screenheight / 2.2));
            font.draw(batch, "Times Slept: " + scores[1], (float) (screenwidth / 3.2), screenwipey + (float) (screenheight / 2.7));
            font.draw(batch, "Times Relaxed: " + scores[2], (float) (screenwidth / 3.2), screenwipey + (float) (screenheight / 3.5));
            font.draw(batch, "Times Eaten " + scores[3], (float) (screenwidth / 3.2), screenwipey + (float) (screenheight / 5));
            font.draw(batch, "Final Score " + scores[4], (float) (screenwidth / 3.15), screenwipey + (float) (screenheight / 9));
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
        } else {
            displaytimer = Integer.toString(hourtimer) + ":" + String.valueOf((int) timer);
        }

        //draw timer
        BitmapFont font = new BitmapFont();
        font.getData().setScale((float) screenwidth / 450);
        Batch batch = new SpriteBatch();
        batch.begin();
        font.setColor(Color.BLACK);
        font.draw(batch, dates.get(day) + " " + displaytimer, (float) (screenwidth / 2.32), (float) (screenheight / 1.05));
        batch.end();

        if (hourtimer >= 23 && !freezetimer) {
            //past midnight!
            freezetimer = true;
            theStudent.lockmovement();
            settext(new ArrayList<>(Arrays.asList("It's too late at night!", "You fell asleep!")));
            drawtextbox();
            endDay(theStudent); // Directly call endDay instead of setting shouldEndDay flag
        }
    }

    private void HandlePlayerActions() {
        theStudent.draw();
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
        isPopupShown = true;
        popupTimer = 0; // Reset timer whenever the textbox is shown
    }

    public static void hidetextbox(Player player) {
        showTextbox = false;
        freezetimer = false;
        player.startmovement();
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
        screenwipex = (int) (Gdx.graphics.getWidth() * 1.5);
        screenwipey = 0;
        transitiontimer = 0;
        hourtimer = 9; // Reset hourtimer to 9
        timer = 0; // Reset timer to 0
    }
}
