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
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.RacingGame;

/**
 *
 * @author ROSY
 */
public class Hud {
    public Stage stage;
    private Viewport viewport;
    private BitmapFont font;
    
    private Image fuelgauge;
    private Image speedgauge;
    public Image needle;
    
    private int minutes;
    private int seconds;
    private int milliseconds;
    
    private int lapCount;
    private int totalLaps;
    
    private final Label timerLabel;
    private final Label lapLabel;
    
    
    public Hud(SpriteBatch batch) {
        font = new BitmapFont(Gdx.files.internal("menu/button_font.fnt"), Gdx.files.internal("menu/button_font.png"),false);      
        viewport = new FitViewport(RacingGame.V_WIDTH, RacingGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);
        
        /**Widgets*/
        speedgauge = new Image(new Texture("HUD/speedgauge.png"));
        speedgauge.setPosition(20, 20);
        needle = new Image(new Texture("HUD/speed_needle.png"));
        needle.setPosition(31, 142);
        needle.setRotation(238);
        
        fuelgauge = new Image(new Texture("HUD/fuelgauge.png"));

        //time format 
        String time;
        time = String.format("%02d : %02d : %03d",
            minutes, seconds, milliseconds
        );
        
        timerLabel = new Label(time, new Label.LabelStyle(font, Color.LIME));
        timerLabel.setPosition(150, 670);
        
        //lap format 
        String lap;
        lap = String.format("%02d / %02d",
            lapCount, totalLaps
        );        

        lapLabel = new Label(lap, new Label.LabelStyle(font, Color.LIME));
        lapLabel.setPosition(1000, 670);


        stage.addActor(timerLabel);
        stage.addActor(lapLabel);
        stage.addActor(speedgauge);
        stage.addActor(needle);
    }
    
    public void updateTime(long startTime) {
        seconds = (((int) TimeUtils.timeSinceMillis(startTime)) / 1000) %60;
        minutes = (((int) TimeUtils.timeSinceMillis(startTime)) / (1000*60)) %60;
        milliseconds = ((int) TimeUtils.timeSinceMillis(startTime))%1000;
        
        String time;
        time = String.format("%02d : %02d : %03d",
            minutes, seconds, milliseconds
        );

        timerLabel.setText(time);
    }    
    
    public void updateLap() {
        lapLabel.setText(null);
    }
    
    public void updateSpeed(float speed, Car car) {
        Gdx.app.log("Hud called", "updateSpeed");
        //Gdx.app.log("speed", Float.toString(speed));
        needle.setOrigin(needle.getWidth()/2, needle.getHeight()/2);
        
        //if car is accelerating
        if (car.getIsAccelerating()) {
            //set angular limit to gauge
            if (needle.getRotation() > -40) {
                needle.setRotation(238-speed*3);
            }
        }
        else {
            needle.setRotation(238-speed*3);
        }
    }
    
    public void updateFuel() {
        
    }
    
    public void dispose() {
        stage.dispose(); 
        font.dispose();
    }
    
}
