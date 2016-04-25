/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scenes;

import car.Car;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.RacingGame;
import handlers.ScreenAssets;

/**
 *
 * @author ROSY
 */
public class Hud {
    public Stage stage;
    private boolean twoPlayers;
    private Viewport viewport;
    private BitmapFont font;
    private boolean gamingState;
    private long startTime;
    
    private ScreenAssets assets;
    private Texture speedgauge_texture;
    private Texture fuelgauge_texture;
    private Texture speedneedle_texture;
    private Texture fuelneedle_texture;
            
    private Image fuelgauge;
    private Image speedgauge;
    private Image needle;
    private Image needle2;
    
    private int minutes;
    private int seconds;
    private int milliseconds;
    private float totalTime = 6;
    
    private int count;
    private int i = 5;
    private final String GO = "GO !";
    
    private int lapCount;
    private int totalLaps;
    
    private Label.LabelStyle lbl_style;    
    private Label timerLabel;
    private Label lapLabel;
    public Label countdownLbl;
    
    
    public Hud(SpriteBatch batch, boolean twoPlayers, boolean gamingState, ScreenAssets assets) {
        this.twoPlayers = twoPlayers;
        this.assets = assets;
        this.gamingState = gamingState;
        viewport = new FitViewport(RacingGame.V_WIDTH, RacingGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);
        
        font = new BitmapFont(Gdx.files.internal("menu/countdown.fnt"), Gdx.files.internal("menu/countdown.png"),false);  
        
        /**Textures*/
        speedgauge_texture = assets.manager.get(ScreenAssets.speed_gauge);
        fuelgauge_texture = assets.manager.get(ScreenAssets.fuel_gauge);
        speedneedle_texture = assets.manager.get(ScreenAssets.speed_needle);
        fuelneedle_texture = assets.manager.get(ScreenAssets.fuel_needle);
        
        /**Widgets*/
        speedgauge = new Image(speedgauge_texture);
        speedgauge.setPosition(20, 20);
        
        needle = new Image(speedneedle_texture);
        needle.setPosition(31, 142);
        needle.setRotation(238);
        
        needle2 = new Image(fuelneedle_texture);
        needle2.setPosition(1056, 122);
        needle2.setRotation(12);
        
        fuelgauge = new Image(fuelgauge_texture);
        fuelgauge.setPosition(1050, 10);        
        
        /**Label style*/
        lbl_style = new Label.LabelStyle(font, Color.LIME);
        
        //time format 
        String time;
        time = String.format("%02d : %02d : %03d",
            minutes, seconds, milliseconds
        );
        
        timerLabel = new Label(time, lbl_style);
        timerLabel.setPosition(150, 670);
        
        //lap format 
        String lap;
        lap = String.format("%02d / %02d",
            lapCount, totalLaps
        );        

        lapLabel = new Label(lap, lbl_style);
        lapLabel.setPosition(1000, 670);

        //countdown
        countdownLbl = new Label("", lbl_style);
        countdownLbl.setPosition(RacingGame.V_WIDTH/2, RacingGame.V_HEIGHT/2);
        
        stage.addActor(timerLabel);
        stage.addActor(lapLabel);
        stage.addActor(speedgauge);
        stage.addActor(needle);
        stage.addActor(fuelgauge);
        stage.addActor(needle2);
        
        stage.addActor(countdownLbl);

    }
    
    public void updateTime() {
        seconds = (((int) TimeUtils.timeSinceMillis(startTime)) / 1000) %60;
        minutes = (((int) TimeUtils.timeSinceMillis(startTime)) / (1000*60)) %60;
        milliseconds = ((int) TimeUtils.timeSinceMillis(startTime))%1000;
        
        //time format 
        String time;
        time = String.format("%02d : %02d : %03d",
            minutes, seconds, milliseconds
        );
        
        timerLabel.setText(time);
        
    }    
    
    public void updateLap() {
        lapLabel.setText(null);
    }
    
    public void updateCountDown(float delta) {
        totalTime -= delta;
        
        int sec = ((int)totalTime)%60;
        
        if (sec > 0) {
            countdownLbl.setText(Integer.toString(sec));
        }
        else if (sec == 0) {
            countdownLbl.setText(GO);
            setGamingState(true);
            startTime = TimeUtils.millis();
            
        Timer.schedule(new Task() {
            @Override
            public void run() {
                countdownLbl.remove();
            }

        }, 3);
        
        }

    }
    
    public boolean getGamingState() {
        return gamingState;
        
    }
    
    public void setGamingState(boolean gamingState) {
        this.gamingState = gamingState;
    }
    
    public void updateSpeed(float currentSpeed, Car car) {
        needle.setOrigin(needle.getWidth()/2, needle.getHeight()/2);
        //set angular limit to gauge
        if (needle.getRotation() > -40) {
            needle.setRotation(240-currentSpeed*1.5f);
        }
    }
    
    public void updateFuel(float fuel, Car car) {
        needle2.setOrigin(needle2.getWidth()/2, needle2.getHeight()/2);
        
        if (fuel >= 0 && fuel <= car.getMaxFuelCapacity()) {
            needle2.setRotation(250-fuel);
        }
 
    }
    
    public void dispose() {
        stage.dispose(); 
        font.dispose();
    }
    
}
