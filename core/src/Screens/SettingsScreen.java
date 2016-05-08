/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import Scenes.MusicPlayer;
import Scenes.SoundPlayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
    
    private MusicPlayer musicPlayer;
    private Sound click; 
    
    private Texture title_texture; 
    
    private TextureAtlas atlas;
    private TextureAtlas menubtns_atlas;
    private ScreenAssets assets;
    
    private Image title;

    private Label SFXLbl;
    private Label musicLbl;
    
    private ImageButton OKButton;
    private ImageButton.ImageButtonStyle OK_style;    
    private Label.LabelStyle lbl_style;
    
    private static Slider SFXVolume;
    public static Slider musicVolume;
    private Slider.SliderStyle slider_style;
    
    private int musicSliderValue;
    private float musicVolumeValue;
    
    public SettingsScreen(RacingGame game, ScreenAssets assets, MusicPlayer musicPlayer) {
        this.game = game;
        this.assets = assets;
        this.musicPlayer = musicPlayer;
        this.musicVolumeValue = musicVolumeValue;
        
        click = assets.manager.get(ScreenAssets.click_sound);
    }
        
    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        
        font = assets.manager.get(ScreenAssets.font);
        
        atlas = assets.manager.get(ScreenAssets.settings_atlas);
        check_skin = new Skin(atlas);   
        
        menubtns_atlas = assets.manager.get(ScreenAssets.buttons_atlas);
        skin = new Skin(menubtns_atlas);

        title_texture = assets.manager.get(ScreenAssets.settingsTitle);
        
        /** Styles */
        lbl_style = new Label.LabelStyle(font, Color.WHITE);
        OK_style = new ImageButton.ImageButtonStyle(skin.getDrawable("OK_button"), null, null, null, null, null);
        slider_style = new Slider.SliderStyle(check_skin.getDrawable("slider"), check_skin.getDrawable("knob"));        
        
        OKButton = new ImageButton(OK_style);
        OKButton.setPosition(1064, 24);
        
        title = new Image(title_texture);
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
        SFXVolume.setValue(70);
        
        musicVolume = new Slider(0, 10, 4, false, slider_style);
        musicVolume.setSize(500, 40);
        musicVolume.setPosition(400, 300);
        musicVolume.setRange(0, 100);
        musicVolume.setValue(70);

        
        /** Listeners*/
        SFXVolume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                //change value of slider
                click.play();
                
            }
        });
        
        //slider: value from 0 to 100
        musicVolume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                click.play();
                musicPlayer.setVolumeValue(musicVolume.getValue()/100);
            }
        });
        
        OKButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                click.play();
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
    
    public void setCurrentVolume(float volume) {
        this.musicVolumeValue = volume;
    }
    
    public float getCurrentVolume() {
        return musicVolumeValue;
    }
    
    public void setMusicSliderValue(int value) {
        this.musicSliderValue = value;
    }
    
    public int getMusicSliderValue() {
        return musicSliderValue;
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
        click.dispose();
        skin.dispose();
        title_texture.dispose();
        check_skin.dispose();
        stage.dispose();
        font.dispose();
    }
    
    public static float getSFXPourcentage(){
        return SFXVolume.getPercent();
    }
        
    public static float getMusicPourcentage(){
        return musicVolume.getPercent();
    }
    
}
