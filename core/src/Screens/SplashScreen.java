/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.RacingGame;

/**
 *
 * @author ROSY
 */
public class SplashScreen extends InputListener implements Screen {
    
    private final RacingGame game;
    private OrthographicCamera camera;
    
    private Stage stage;
    private Texture splash_image;
    private BitmapFont font;
    
    private long startTime;
    private int rendCount;
    
    private Label.LabelStyle style_lbl;
    private Label lbl;
    
    public SplashScreen(RacingGame game) {
        Gdx.app.log("SplashScreen", "constructor called");
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        
        stage = new Stage();
        font = new BitmapFont(Gdx.files.internal("menu/button_font.fnt"), Gdx.files.internal("menu/button_font.png"),false);
        style_lbl = new Label.LabelStyle(font, Color.WHITE);
    }

    @Override
    public void show() {
//startTime = TimeUtils.millis();
        Gdx.app.log("SplashScreen", "show called");
        splash_image = new Texture(Gdx.files.internal("menu/splashscreen_image.jpeg"));     
        
        lbl = new Label("Press SPACE to start", style_lbl);
        lbl.setPosition(500, 100);
        
        stage.addActor(lbl);
        
        /** Actions */
        float fadeTime = .5f;
        lbl.addAction(Actions.alpha(0)); //make text transparent
        lbl.addAction(Actions.repeat(50, Actions.sequence(Actions.fadeIn(fadeTime), Actions.fadeOut(fadeTime))));
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(3/255f,13/255f,128/255f,1); //set background color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getBatch().begin();
        stage.getBatch().draw(splash_image, 0, 0);
        stage.getBatch().end();
        stage.act(f);
        stage.draw();
        
        /** Listeners */
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            //CHANGE BACK INTO MENU LATER!!
            game.setScreen(new PlayScreen(game));
            System.out.println("SPACE");
        }
        
        rendCount++;
//        if (TimeUtils.millis()>(startTime+5000))
//        game.setScreen(new MainMenuScreen(game));
    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        Gdx.app.log("SplashScreen", "hide called");
        Gdx.app.log("Splash Screen", "rendered " + rendCount + " times.");
    }

    @Override
    public void dispose() {
        Gdx.app.log("SplashScreen", "dispose called");
        game.dispose();
        splash_image.dispose();
        stage.dispose();
        stage.getBatch().dispose();
        font.dispose();
    }
    
}
