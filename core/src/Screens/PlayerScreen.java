/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.game.RacingGame;

/**
 *
 * @author ROSY
 */
public class PlayerScreen implements Screen {
    private RacingGame game;
    private boolean twoPlayers;
    
    private OrthographicCamera camera;
    private Stage stage;
    private Texture background;
    private BitmapFont font;

    private Image title;
    private ImageButton next_btn;
    private ImageButton back_btn;
    private ImageButton.ImageButtonStyle next_style;
    private ImageButton.ImageButtonStyle back_style;
    
    private Label racer1_title;
    private Label racer2_title;
    
    private TextField txt_field;
    private TextField.TextFieldStyle txt_style;
    
    private Label.LabelStyle lbl_style;
    
    private Image racer1;
    private Image racer2;
    
    private TextureAtlas buttons_atlas;
    private TextureAtlas atlas;
    private Skin skin;
    private Skin buttons_skin;    
    

    public PlayerScreen(RacingGame game, boolean twoPlayers) {
        Gdx.app.log("PlayerName", "constructor called");
        this.game = game;
        this.twoPlayers = twoPlayers;
        stage = new Stage();
        camera = new OrthographicCamera();
        Gdx.input.setInputProcessor(stage);
        
        /** BitmapFont */
        font = new BitmapFont(Gdx.files.internal("menu/button_font.fnt"), Gdx.files.internal("menu/button_font.png"),false);
        
        /** Atlas and skin */
        buttons_atlas = new TextureAtlas(Gdx.files.internal("menu/menubtns_atlas.txt"));
        buttons_skin= new Skin(buttons_atlas);
        
        atlas = new TextureAtlas(Gdx.files.internal("menu/uiskin.txt"));
        skin = new Skin(atlas);
        
        /** Styles */
        next_style = new ImageButton.ImageButtonStyle(buttons_skin.getDrawable("next_button"), null, null, null, null, null);
        back_style = new ImageButton.ImageButtonStyle(buttons_skin.getDrawable("back_button"), null, null, null, null, null);
        
        txt_style = new TextField.TextFieldStyle();
        txt_style.background = skin.getDrawable("textfield");
        txt_style.cursor = skin.getDrawable("cursor");
        txt_style.font = font;
        txt_style.fontColor = new Color(Color.WHITE);
        
        lbl_style = new Label.LabelStyle();
        lbl_style.font = font;
        lbl_style.fontColor = new Color(Color.WHITE);
    }
    
    @Override
    public void show() {
        Gdx.app.log("PlayerName", "show called");
        title = new Image(new Texture("menu/playername_title.png"));
        title.setPosition(280, 648);

        racer1 = new Image(new Texture(Gdx.files.internal("menu/player_name_box.png")));
        racer1.setPosition(500, 400);
        
        /**Labels*/
        racer1_title = new Label("Racer 1", lbl_style);
        racer1_title.setPosition(550, 500);
        
        txt_field = new TextField("Enter Name",txt_style);
        txt_field.setSize(290, 40);
        txt_field.setPosition(500, 455);

        /** Buttons */
        next_btn = new ImageButton(next_style);
        next_btn.setPosition(1016, 24);
        back_btn = new ImageButton(back_style);
        back_btn.setPosition(24, 24);
        
        //clear1 = new ImageButton(clear_style);
        
        /** Two players*/
        if (twoPlayers == true) { 
            racer2 = new Image(new Texture(Gdx.files.internal("menu/player_name_box.png")));
            racer2.setPosition(500, 200);
        
            racer2_title = new Label("Racer 2", lbl_style);
            racer2_title.setPosition(550, 300);
        
            stage.addActor(racer2);
            stage.addActor(racer2_title);            
        }
        
        
        stage.addActor(racer1);
        stage.addActor(racer1_title);
        stage.addActor(txt_field);
        stage.addActor(back_btn);
        stage.addActor(next_btn);
        stage.addActor(title);
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(3/255f,13/255f,128/255f,1); //set background color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);      
        
        stage.act();
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
        buttons_skin.dispose();
        buttons_atlas.dispose();
        skin.dispose();
        atlas.dispose();
        stage.dispose();
        game.dispose();
        
    }
    
}
