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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.RacingGame;
import handlers.ScreenAssets;

/**
 *
 * @author ROSY
 */
public class SettingsScreen implements Screen {
    private final RacingGame game;
    private Stage stage;
    private BitmapFont font;
    private Skin skin;
    private Skin check_skin;
    
    private TextureAtlas atlas;
    private ScreenAssets assets;
  
    private int musicVol;
    private int SFXVol;
    
    private Image title;
    private Image image;
    
    private Label controlsLbl;
    private Label SFXLbl;
    private Label musicLbl;
    
    private ImageButton OKButton;
    private ImageButton.ImageButtonStyle OK_style;    
    private Label.LabelStyle lbl_style;


    private Slider SFXVolume;
    private Slider musicVolume;
    private Slider.SliderStyle slider_style;
    
    public SettingsScreen(RacingGame game) {
        this.game = game;
    }
        
    @Override
    public void show() {
        stage = new Stage();
        font = new BitmapFont(Gdx.files.internal("menu/button_font.fnt"), Gdx.files.internal("menu/button_font.png"),false);
        
        
        skin = new Skin(new TextureAtlas(Gdx.files.internal("menu/menubtns_atlas.txt")));
        
        atlas = new TextureAtlas(Gdx.files.internal("menu/settings_atlas.txt"));
        check_skin = new Skin(atlas);       
        Gdx.input.setInputProcessor(stage);
        
        /** Styles */
        lbl_style = new Label.LabelStyle(font, Color.WHITE);
        OK_style = new ImageButton.ImageButtonStyle(skin.getDrawable("OK_button"), null, null, null, null, null);
        slider_style = new Slider.SliderStyle(check_skin.getDrawable("slider"), check_skin.getDrawable("knob"));        
        
        OKButton = new ImageButton(OK_style);
        OKButton.setPosition(1064, 24);
        
        title = new Image(new Texture(Gdx.files.internal("menu/settings_title.png")));
        title.setPosition(376, 624);
        
        /** Labels */
        SFXLbl = new Label("SFX", lbl_style);
        SFXLbl.setPosition(400, 450);
        musicLbl = new Label("MUSIC", lbl_style);
        musicLbl.setPosition(400, 350);
        
        /**Slider*/
        SFXVolume = new Slider(0, 10, 4, false, slider_style);
        SFXVolume.setSize(500, 40);
        SFXVolume.setPosition(400, 400);
        SFXVolume.setRange(0, 100);
        musicVolume = new Slider(0, 10, 4, false, slider_style);
        musicVolume.setSize(500, 40);
        musicVolume.setPosition(400, 300);
        musicVolume.setRange(0, 100);
        
        /** Listeners*/
        SFXVolume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                //change value of slider
            }
        });
        
        musicVolume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                //change value of slider
            }
        });
        
        OKButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                //save values of sliders
                game.setScreen(new MainMenuScreen(game, assets));
            }
        });

        stage.addActor(SFXVolume);
        stage.addActor(musicVolume);
        stage.addActor(OKButton);
        stage.addActor(musicLbl);
        stage.addActor(SFXLbl);
        stage.addActor(title);

    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(3/255f,13/255f,128/255f,0); //set background color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act();
        stage.draw();             
    }

    @Override
    public void resize(int width, int height) {
        
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
        game.dispose();
        skin.dispose();
        check_skin.dispose();
        stage.dispose();
        font.dispose();
        
    }
    
}
