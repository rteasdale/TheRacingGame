/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.RacingGame;

/**
 *
 * @author ROSY
 */
public class MainMenuScreen extends Stage implements Screen {
    private final RacingGame game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    
    private Viewport menuPort;
    
    private Stage stage;
    private Skin mode_button;
    private Image title;
    private Image image;
    
    private Texture menu_bg;
   
    private TextButtonStyle style;
    private TextButton onePlayerButton;
    private TextButton twoPlayersButton;
    private TextButton settingsButton;
    private TextButton quitButton;
    
    public MainMenuScreen(RacingGame game) {
        Gdx.app.log("MainMenuScreen", "constructor called");
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 512, 512);
        batch = new SpriteBatch();

    }

    @Override
    public void show() {
        Gdx.app.log("MainMenuScreen", "show called");
        menu_bg = new Texture(Gdx.files.internal("menu/menu_bg.jpeg")); //** texture is now the splash image **//
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(menu_bg, 0, 0);
        batch.end();
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
        Gdx.app.log("MainMenuScreen", "hide called");
    }

    @Override
    public void dispose() {
        batch.dispose();
        mode_button.dispose();
        stage.dispose();
    }
    
}
