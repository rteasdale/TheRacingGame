/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scenes;

import Screens.SettingsScreen;
import car.Car;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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
    private boolean fuelAlertLooped = false;
    private long startTime;
    private Sound countdown1;
    private Sound countdown2;
    private Sound fuelAlertSound;
    
    private boolean hasCollectedTime = false;
    
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
    private int s;
    private int m;
    private int millis;
    private float totalTime = 6;
    
    private int playerNum;
    
    private int count;
    boolean c2HasPlayed = false;
    boolean c1HasPlayed = false;
    private final String GO = "GO !";

    private Label.LabelStyle lbl_style;    
    private Label timerLabel;
    private Label lapLabel;
    private Label countdownLbl;
    private Label finishLbl;
    private Label playerOne;
    private Label playerTwo;
    
    private Label gameOver;
    private Label fuelAlert;
    

    
    
    public Hud(SpriteBatch batch, boolean twoPlayers, boolean gamingState, boolean finishState, int playerNum, ScreenAssets assets, int totalLap) {
        this.twoPlayers = twoPlayers;
        this.assets = assets;
        this.gamingState = gamingState;
        this.totalLap = totalLap;
        this.playerNum = playerNum;
        
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
        
        /**Sounds**/
        fuelAlertSound = assets.manager.get(ScreenAssets.out_of_fuel_alarm);
        countdown1 = assets.manager.get(ScreenAssets.countdown_sound1);
        countdown2 = assets.manager.get(ScreenAssets.countdown_sound2);
        
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
        playerOne = new Label(" PLAYER " + playerNum, lbl_style);
        
        //fuel alert 
        fuelAlert = new Label(" LOW FUEL ", lbl_style);
        
        //game over 
        gameOver = new Label(" GAME OVER ", lbl_style);
        
        if (twoPlayers == false) {
        speedgauge.setPosition(20, 20);
        needle.setPosition(31, 142);
        needle2.setPosition(1056, 122);        
        fuelgauge.setPosition(1050, 10);  
        lapLabel.setPosition(1000, 670);
        timerLabel.setPosition(150, 670);
        countdownLbl.setPosition(RacingGame.V_WIDTH/2, RacingGame.V_HEIGHT/2);
        finishLbl.setPosition(RacingGame.V_WIDTH/2, RacingGame.V_HEIGHT/2);
        playerOne.setPosition(0, 620);
        
        fuelAlert.setPosition(820, 30);
        
        }        
        
        if (twoPlayers == true) {
            speedgauge.setScale(0.8f);
            speedgauge.setPosition(20, 20);
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
            
            playerTwo = new Label(" PLAYER " + playerNum, lbl_style); 
            playerOne.setPosition(0, 620);
            playerTwo.setPosition(0, 620);
            stage.addActor(playerTwo);
            
            fuelAlert.setPosition(300, 30);
        }        
        
        stage.addActor(timerLabel);
        stage.addActor(lapLabel);
        stage.addActor(speedgauge);
        stage.addActor(needle);
        stage.addActor(fuelgauge);
        stage.addActor(needle2);
        stage.addActor(countdownLbl);
        stage.addActor(playerOne);

    }
    
    public void updateTime(long startTime, boolean finishState) {
        if (finishState == false) {
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
        
        if(finishState == true) {
            if (hasCollectedTime == false) {
                s = seconds;
                m = minutes;
                millis = milliseconds;
                hasCollectedTime = true;

            //time format 
            String fixedTime;
                fixedTime = String.format("%02d : %02d : %03d",
                    m, s, millis
                );        

            timerLabel.setText(fixedTime);
            } 
        }
        

    }

    public int getSeconds() {
        return s;
    }
    
    public int getMinutes() {
        return m;
    }
    
    public int getMillis() {
        return millis;
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
            if(!c1HasPlayed){
                try{
                    countdown1.loop(SettingsScreen.getSFXPourcentage());
                    System.out.println(SettingsScreen.getSFXPourcentage());
                }catch(NullPointerException e1){
                    
                    countdown1.loop(0.75f);
                }
            c1HasPlayed = true;
            }
        }
        else if (sec == 0) {
            countdown1.stop();
            setGamingState(true);
            countdownLbl.setText(GO);
            
            if(!c2HasPlayed){
                try{
            countdown2.play(SettingsScreen.getSFXPourcentage());
                }catch(NullPointerException e1){
                    countdown2.play(0.75f);
                }
            c2HasPlayed = true;
                }

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

        if (fuel <= 20) {

            stage.addActor(fuelAlert);

            if(fuelAlertLooped == false){
                try{
                fuelAlertSound.loop(SettingsScreen.getSFXPourcentage());
                }catch(NullPointerException e1){
                    fuelAlertSound.loop(0.75f);
                }
                fuelAlertLooped = true;
            }

        }
        
        else if (fuel > 20) {
            fuelAlertSound.stop();
            fuelAlertLooped = false;
            fuelAlert.remove();
        }
        
    }
    
    public void updateFinish(boolean twoPlayers) {
        if (twoPlayers == false) {
            finishLbl.setPosition(RacingGame.V_WIDTH/2, RacingGame.V_HEIGHT/2);
            stage.addActor(finishLbl);
        }
        
        if (twoPlayers == true) {
            finishLbl.setPosition(viewport.getScreenWidth()/2, viewport.getScreenHeight()/2);
            stage.addActor(finishLbl);
        }
    }
    
    public void updateGameOver() {
        gameOver.setPosition((RacingGame.V_WIDTH/2)-200, RacingGame.V_HEIGHT/2);
        stage.addActor(gameOver);
    }
    
    public void dispose() {
//        stage.dispose(); 
//        font.dispose();
    }
    
}
