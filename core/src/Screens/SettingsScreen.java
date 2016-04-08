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
  
    private int musicVol;
    private int SFXVol;
    
    private Image title;
    private Image image;
    
    private Label controlsLbl;
    private Label SFXLbl;
    private Label musicLbl;
    
    private ImageButton OKButton;
    private ImageButton ASDWBtn;
    private ImageButton arrowsBtn;
    private ImageButton.ImageButtonStyle OK_style;    
    private Label.LabelStyle lbl_style;
    private ImageButton.ImageButtonStyle ASDW_style;
    private ImageButton.ImageButtonStyle arrows_style;
   
    private CheckBox controls1Check;
    private CheckBox controls2Check;
    private ButtonGroup<CheckBox> buttonGroup;
    private CheckBox.CheckBoxStyle check_style;

    private Slider SFXVolume;
    private Slider musicVolume;
    private Slider.SliderStyle slider_style;
    
    public SettingsScreen(RacingGame game) {
        this.game = game;
        stage = new Stage();
        font = new BitmapFont(Gdx.files.internal("menu/button_font.fnt"), Gdx.files.internal("menu/button_font.png"),false);
        skin = new Skin(new TextureAtlas(Gdx.files.internal("menu/menubtns_atlas.txt")));
        
        atlas = new TextureAtlas(Gdx.files.internal("menu/settings_atlas.txt"));
        check_skin = new Skin(atlas);       
        Gdx.input.setInputProcessor(stage);
        
        /** Styles */
        lbl_style = new Label.LabelStyle(font, Color.WHITE);
        OK_style = new ImageButton.ImageButtonStyle(skin.getDrawable("OK_button"), null, null, null, null, null);
        //ASDW_style = new ImageButton.ImageButtonStyle(check_skin.getDrawable("ASDW_controls"), check_skin.getDrawable("ASDW_controls_chosen"), null, null, null, null);
        //arrows_style = new ImageButton.ImageButtonStyle(check_skin.getDrawable("arrowkeys_controls"), check_skin.getDrawable("arrowkeys_controls_chosen"), null, null, null, null);

//        check_style = new CheckBox.CheckBoxStyle();
//        check_style.font = font;
//        check_style.fontColor = new Color(Color.WHITE);
//        check_style.checkboxOff = check_skin.getDrawable("checkbox");
//        check_style.checkboxOn = check_skin.getDrawable("checkbox2");
        
        slider_style = new Slider.SliderStyle(check_skin.getDrawable("slider"), check_skin.getDrawable("knob"));
    }
        
    @Override
    public void show() {
        OKButton = new ImageButton(OK_style);
        OKButton.setPosition(1064, 24);
        
        title = new Image(new Texture(Gdx.files.internal("menu/settings_title.png")));
        title.setPosition(376, 624);
        
        /** Labels */
//        controlsLbl = new Label("CONTROLS", lbl_style);
//        controlsLbl.setPosition(200, 550);
        SFXLbl = new Label("SFX", lbl_style);
        SFXLbl.setPosition(300, 450);
        musicLbl = new Label("MUSIC", lbl_style);
        musicLbl.setPosition(300, 350);
        
//        ASDWBtn = new ImageButton(ASDW_style);
//        ASDWBtn.setPosition(100, 100);
//        arrowsBtn = new ImageButton(arrows_style);
//        arrowsBtn.setPosition(200, 200);
        
//        /* Check box*/
//        controls1Check = new CheckBox("AWSD", check_style);
//        controls1Check.setPosition(210, 500);
//        controls2Check = new CheckBox("UP-DOWN-RIGHT-LEFT", check_style);
//        controls2Check.setPosition(410, 500);
        
        /**Slider*/
        SFXVolume = new Slider(0, 10, 4, false, slider_style);
        SFXVolume.setSize(300, 40);
        SFXVolume.setPosition(300, 400);
        musicVolume = new Slider(0, 10, 4, false, slider_style);
        musicVolume.setSize(300, 40);
        musicVolume.setPosition(300, 300);
        
        SFXVolume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                //change value of sider
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
                game.setScreen(new MainMenuScreen(game));
            }
        });

        stage.addActor(SFXVolume);
        stage.addActor(musicVolume);
//        stage.addActor(controls2Check);
//        stage.addActor(controls1Check);
        stage.addActor(OKButton);
        stage.addActor(musicLbl);
        stage.addActor(SFXLbl);
//        stage.addActor(controlsLbl);
        stage.addActor(title);

    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(3/255f,13/255f,128/255f,0); //set background color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act(f);
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
