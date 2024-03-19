package com.group13.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class TutorialScreen implements Screen {

    private Stage stage;
    private Group13Game game;

    public TutorialScreen(Group13Game game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        labelStyle.font.getData().setScale(1.5f);
        labelStyle.fontColor = Color.WHITE;
        labelStyle.background = null;

        Label tutorialLabel = new Label("Tutorial", labelStyle);
        tutorialLabel.setAlignment(Align.center);
        tutorialLabel.setWrap(true);
        table.add(tutorialLabel).width(600).pad(20).row();

        Label tutorialText = getLabel(labelStyle);
        table.add(tutorialText).width(600).pad(20).row();

        TextButtonStyle buttonStyle = new TextButtonStyle();
        buttonStyle.font = new BitmapFont();
        buttonStyle.font.getData().setScale(1.5f);
        buttonStyle.fontColor = Color.WHITE;
        buttonStyle.overFontColor = Color.LIGHT_GRAY;
        buttonStyle.downFontColor = Color.DARK_GRAY;

        TextButton backButton = new TextButton("Back", buttonStyle);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        table.add(backButton).size(400, 60).pad(20);
    }

    private static Label getLabel(Label.LabelStyle labelStyle) {
        Label tutorialText = new Label(
                "- **Time Management:** Plan your day; every action from studying to exercising consumes time.\n\n" +
                        "- **Energy & Motivation:** Vital for performing tasks; low levels reduce activity effectiveness.\n\n" +
                        "- **Study to Boost Scores:** Hit the books in the library to increase your study score.\n\n" +
                        "- **Eat to Replenish Energy:** Visit The Piazza to eat and recover energy, affecting other activities.\n\n" +
                        "- **Exercise for Motivation:** Workout at the gym to raise your motivation, enhancing overall performance.\n\n" +
                        "- **Sleep to Refresh:** End the day in your dorm to restore energy and improve next day's potential.", labelStyle);
        tutorialText.setAlignment(Align.center);
        tutorialText.setWrap(true);
        return tutorialText;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(54f / 255f, 57f / 255f, 63f / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}