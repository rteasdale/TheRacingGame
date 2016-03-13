/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.RacingGame;

/**
 *
 * @author ROSY
 */
public class SplashScreen extends InputListener implements Screen {
    
    private final RacingGame game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Texture splash_image;
    private BitmapFont font;
    
    private long startTime;
    private int rendCount;
    
    private Stage stage;
    private Label lbl;
    
    public SplashScreen(RacingGame game) {
        Gdx.app.log("SplashScreen", "constructor called");
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        batch = new SpriteBatch();
        font = new BitmapFont();
    }

    @Override
    public void show() {
        Gdx.app.log("SplashScreen", "show called");
        splash_image = new Texture(Gdx.files.internal("menu/splashscreen_image.jpeg")); //** texture is now the splash image **//
        //startTime = TimeUtils.millis();
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.setScreen(new MainMenuScreen(game));
        }
        
        batch.begin();
        batch.draw(splash_image,0,0);
        font.draw(batch, "Press space to start", 400, 200);
        batch.end();
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
        splash_image.dispose();
        font.dispose();
        batch.dispose();
    }
    
}
