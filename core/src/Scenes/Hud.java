/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scenes;

import Screens.GameScreen;
import car.Car;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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
    private Sound countdown1;
    
    private ScreenAssets assets;
    private Texture speedgauge_texture;
    private Texture fuelgauge_texture;
    private Texture speedneedle_texture;
    private Texture fuelneedle_texture;
    
    private int lapCount;
    private int totalLap;
            
    private Image fuelgauge;
    private Image speedgauge;
    private Image needle;
    private Image needle2;
    private Image speedgaugeP2;
    private Image fuelgaugeP2;
    private Image needleP2;
    private Image needle2P2;
    
    private int minutes;
    private int seconds;
    private int milliseconds;
    private float totalTime = 6;
    
    private int count;
    private int i = 5;
    private final String GO = "GO !";

    private Label.LabelStyle lbl_style;    
    private Label timerLabel;
    private Label lapLabel;
    private Label countdownLbl;
    private Label finishLbl;
    private Label playerOne;
    private Label playerTwo;
    
    
    public Hud(SpriteBatch batch, boolean twoPlayers, boolean gamingState, boolean finishState, ScreenAssets assets, int totalLap) {
        this.twoPlayers = twoPlayers;
        this.assets = assets;
        this.gamingState = gamingState;
        this.totalLap = totalLap;
        
        if (!twoPlayers) {
            viewport = new FitViewport(RacingGame.V_WIDTH, RacingGame.V_HEIGHT, new OrthographicCamera());
        }
        
        if (twoPlayers) {
            viewport = new FitViewport(RacingGame.V_WIDTH/2, RacingGame.V_HEIGHT, new OrthographicCamera());
        }
        stage = new Stage(viewport, batch);
        //Gdx.input.setInputProcessor(stage);
        
        font = assets.manager.get(ScreenAssets.hud_font);

        //countdown1 = assets.manager.get(ScreenAssets.countdown_sound1);
        
        /**Textures*/
        speedgauge_texture = assets.manager.get(ScreenAssets.speed_gauge);
        fuelgauge_texture = assets.manager.get(ScreenAssets.fuel_gauge);
        speedneedle_texture = assets.manager.get(ScreenAssets.speed_needle);
        fuelneedle_texture = assets.manager.get(ScreenAssets.fuel_needle);
        
        /**Widgets*/
        speedgauge = new Image(speedgauge_texture);
        
        needle = new Image(speedneedle_texture);
        needle.setRotation(238);
        needle.setOrigin(needle.getWidth()/2, needle.getHeight()/2);
        
        needle2 = new Image(fuelneedle_texture);
        needle2.setRotation(12);
        needle2.setOrigin(needle2.getWidth()/2, needle2.getHeight()/2);
        
        fuelgauge = new Image(fuelgauge_texture);      
        
        /**Label style*/
        lbl_style = new Label.LabelStyle(font, Color.WHITE);
        
        //time format 
        String time;
        time = String.format("%02d : %02d : %03d",
            minutes, seconds, milliseconds
        );
        
        timerLabel = new Label(time, lbl_style);
 
        //total laps = maplap
        //lap format 
        String lap;
        lap = String.format("%2d / %2d",
            lapCount, totalLap
        );        

        lapLabel = new Label(lap, lbl_style);

        //countdown
        countdownLbl = new Label("", lbl_style);

        //finish
        finishLbl = new Label(" FINISH! ", lbl_style);
        
        //player label
        playerOne = new Label(" PLAYER 1 ", lbl_style);
        playerOne.setRotation(180);

        
        if (twoPlayers == false) {
        speedgauge.setPosition(20, 20);
        needle.setPosition(31, 142);
        needle2.setPosition(1056, 122);        
        fuelgauge.setPosition(1050, 10);  
        lapLabel.setPosition(1000, 670);
        timerLabel.setPosition(150, 670);
        countdownLbl.setPosition(RacingGame.V_WIDTH/2, RacingGame.V_HEIGHT/2);
        finishLbl.setPosition(RacingGame.V_WIDTH/2, RacingGame.V_HEIGHT/2);
        playerOne.setPosition(0, (RacingGame.V_HEIGHT/2)-40);
        }        
        
        if (twoPlayers == true) {
            speedgauge.setScale(0.8f);
            needle.setScale(0.8f);
            needle.setPosition(8, 119);
            fuelgauge.setScale(0.8f);
            fuelgauge.setPosition(450, 20);
            needle2.setScale(0.8f);
            needle2.setPosition(434, 110);
            timerLabel.setScale(0.8f);
            lapLabel.setScale(0.8f);
            countdownLbl.setScale(0.8f);
            
            timerLabel.setPosition(10, 670);
            lapLabel.setPosition(470, 670);
            countdownLbl.setPosition(viewport.getScreenWidth()/2, viewport.getScreenHeight()/2);
            finishLbl.setPosition(viewport.getScreenWidth()/2, viewport.getScreenHeight()/2);    
            
            playerTwo = new Label(" PLAYER 2 ", lbl_style); 
            playerOne.setPosition(0, viewport.getScreenHeight()/2);
            playerTwo.setPosition(0, viewport.getScreenHeight()/2);
        }        
        
        stage.addActor(timerLabel);
        stage.addActor(lapLabel);
        stage.addActor(speedgauge);
        stage.addActor(needle);
        stage.addActor(fuelgauge);
        stage.addActor(needle2);
        stage.addActor(countdownLbl);
        stage.addActor(playerOne);
        stage.addActor(playerTwo);

    }
    
    public void updateTime(long startTime) {
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

    
    public void updateLap(Car car) {

        String lap; 
        
       //lap format  
        lap = String.format("%2d / %2d",
            car.getLapNumber(), totalLap
        );          
        lapLabel.setText(lap);
        
    }
    
    public void updateCountDown(float delta) {
        totalTime -= delta;
        
        int sec = ((int)totalTime)%60;
        
        Timer.schedule(new Task(){
            @Override
            public void run() {
                startTime = TimeUtils.millis();
            }
        }, 7);         
        
        if (sec > 0) {
            countdownLbl.setText(Integer.toString(sec));
            //countdown1.play();
        }
        else if (sec == 0) {
            setGamingState(true);
            countdownLbl.setText(GO);

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
        
        //set angular limit to gauge
        if (needle.getRotation() >= -40) {
            needle.setRotation(240-(currentSpeed*1.5f));
        }
    }
    
    public void updateFuel(float fuel, Car car) {
        needle2.setRotation(166-(fuel*1.55f));
 
    }
    
    public void updateFinish(boolean finishState) {
        if (finishState == true) {
            
        }
    }
    
    public void dispose() {
//        stage.dispose(); 
//        font.dispose();
    }
    
}
