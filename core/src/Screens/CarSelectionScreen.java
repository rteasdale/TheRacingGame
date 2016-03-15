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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.RacingGame;
import javafx.scene.control.ComboBox;

/**
 *
 * @author ROSY
 */
public class CarSelectionScreen implements Screen {
    private final RacingGame game;

    private OrthographicCamera camera;
    private Texture background;
    private Stage stage;
    
    private Image title;
    private ImageButton selectNextCarButton;
    private Button selectPreviousCarButton;
    private TextArea carDescriptionBox;
    private ComboBox selectCarColor;
    
    private Skin skin;
    
    
    public CarSelectionScreen(RacingGame game) {
        Gdx.app.log("CarSelection", "constructor called");
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage); //** stage is responsive **//

        skin = new Skin();

    }

    @Override
    public void show() {
        Gdx.app.log("CarSelection", "show called");
        background = new Texture(Gdx.files.internal("menu/menu_bg.png"));
        
        title = new Image(new Texture(Gdx.files.internal("menu/carselection_title.png")));
        title.setX(300);
        title.setY(648);
        
        selectNextCarButton = new ImageButton();
        
        carDescriptionBox = new TextArea("Description", skin);
        
        stage.addActor(title);
    }

    @Override
    public void render(float delta) {
        
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.getBatch().begin();
        //stage.act();
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

    }

    @Override
    public void dispose() {
        stage.dispose();
        stage.getBatch().dispose();
    }
    
}
