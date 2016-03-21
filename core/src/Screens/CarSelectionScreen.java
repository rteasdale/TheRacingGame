/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.RacingGame;
import javafx.scene.control.ComboBox;

/**
 *
 * @author ROSY
 */
public class CarSelectionScreen implements Screen{
    private final RacingGame game;

    private OrthographicCamera camera;
    private Texture background;
    private final Stage stage;

    private Image title;
    private ImageButton selectNextCarButton;
    private ImageButton selectPreviousCarButton;
    private TextArea carDescriptionBox;
    private ComboBox selectCarColor;
    private ImageButton next_btn;
    private ImageButton back_btn;
    
    private final TextureAtlas atlas;
    private Skin skin;
     
    private ImageButtonStyle next_style;
    private ImageButtonStyle back_style;
    private ImageButtonStyle nextcar_style;
    private ImageButtonStyle prevcar_style;
    

    public CarSelectionScreen(RacingGame game) {
        Gdx.app.log("CarSelection", "constructor called");
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage); //** stage is responsive **//

        atlas = new TextureAtlas(Gdx.files.internal("menu/menubtns_atlas.txt"));
        
        skin = new Skin(atlas);

        /** Styles*/
        next_style = new ImageButtonStyle(skin.getDrawable("next_button"), null, null, null, null, null);
        back_style = new ImageButtonStyle(skin.getDrawable("back_button"), null, null, null, null, null);
        nextcar_style = new ImageButtonStyle(skin.getDrawable("nextarrow_small"), null, null, null, null, null);
        prevcar_style = new ImageButtonStyle(skin.getDrawable("backarrow_small"), null, null, null, null, null);
        
       
    }

    @Override
    public void show() {
        Gdx.app.log("CarSelection", "show called");
        background = new Texture(Gdx.files.internal("menu/menu_bg.png"));
        
        
        selectCarColor = new ComboBox();
        
        title = new Image(new Texture(Gdx.files.internal("menu/carselection_title.png")));
        title.setX(160);
        title.setY(640);
        
        next_btn = new ImageButton(next_style);
        next_btn.setX(160);
        next_btn.setY(32);
        
        back_btn = new ImageButton(back_style);
        next_btn.setX(1120);
        next_btn.setY(32);
        
        selectNextCarButton = new ImageButton(nextcar_style);
        selectNextCarButton.setX(96);
        selectNextCarButton.setY(640);
        
        selectPreviousCarButton = new ImageButton(prevcar_style);
        
        stage.addActor(next_btn);
        stage.addActor(back_btn);
        stage.addActor(title);
        
        /** Listeners */
//        next_btn.addListener(new ChangeListener() {
//            @Override
//            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
//                System.out.println("Clicked!");
//            }
//        });       
        
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

    }

    @Override
    public void dispose() {
        Gdx.app.log("Car Selection", "show called");
        stage.dispose();
        stage.getBatch().dispose();
        skin.dispose();
    }
    
}
