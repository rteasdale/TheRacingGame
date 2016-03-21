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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
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

    private OrthographicCamera camera;
    private Texture background;
    private final Stage stage;
    
    private final TextureAtlas atlas;
    private Skin skin;

    private Image title;
    private ImageButton onePlayerButton;
    private ImageButton twoPlayersButton;
    private ImageButton settingsButton;
    private ImageButton exitButton;
    
    private ImageButtonStyle style_1P;
    private ImageButtonStyle style_2P;
    private ImageButtonStyle settings_style;
    private ImageButtonStyle exit_style;
    
    public MainMenuScreen(RacingGame game) {
        Gdx.app.log("MainMenuScreen", "constructor called");
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage); //** stage is responsive **//

        atlas = new TextureAtlas(Gdx.files.internal("menu/menubtns_atlas.txt"));
        
        skin = new Skin(atlas);

        /** Styles*/
        style_1P = new ImageButton.ImageButtonStyle(skin.getDrawable("menu_singleP"), null, null, null, null, null);
        style_2P = new ImageButton.ImageButtonStyle(skin.getDrawable("menu_twoP"), null, null, null, null, null);
        
        
    }

    @Override
    public void show() {
        Gdx.app.log("MainMenuScreen", "show called");
        background = new Texture(Gdx.files.internal("menu/menu_bg.png"));
        
        title = new Image(new Texture(Gdx.files.internal("menu/menu_title.png")));
        title.setX(300);
        title.setY(550);
        
        onePlayerButton = new ImageButton(style_1P);
        onePlayerButton.setX(700);
        onePlayerButton.setY(400);

        
        
        stage.addActor(onePlayerButton);
        stage.addActor(title);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        
        stage.getBatch().begin();
        stage.act();
        stage.getBatch().draw(background, 0, 0);
        stage.getBatch().end();
        stage.draw();


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
        Gdx.app.log("MainMenuScreen", "show called");
        stage.dispose();
        stage.getBatch().dispose();
        skin.dispose();
    }
    
}
