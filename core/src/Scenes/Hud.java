/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.TimeUtils;
import static com.badlogic.gdx.utils.TimeUtils.millis;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.RacingGame;
import java.util.Calendar;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author ROSY
 */
public class Hud {
    public Stage stage;
    private Viewport viewport;
    private BitmapFont font;
    
    private int minutes;
    private int seconds;
    private int milliseconds;
    
    private int lapCount;
    private int totalLaps;
    
    private Label timerLabel;
    private Label lapLabel;
    
    
    public Hud(SpriteBatch batch) {
        font = new BitmapFont(Gdx.files.internal("menu/button_font.fnt"), Gdx.files.internal("menu/button_font.png"),false);
                
        viewport = new FitViewport(RacingGame.V_WIDTH, RacingGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);
        
        Table table = new Table();
        table.top();
        table.setFillParent(true);
        
        //time format 
        String time;
        time = String.format("%02d, %02d, %03d",
            minutes, seconds, milliseconds
        );
        timerLabel = new Label(time, new Label.LabelStyle(font, Color.LIME));
        
        //lap format 
        String lap;
        lap = String.format("%02d / %02d",
            lapCount, totalLaps
        );        

        lapLabel = new Label(lap, new Label.LabelStyle(font, Color.LIME));
        
        
        table.add(timerLabel).expandX().padTop(20); //extend to end of screen
        table.add(lapLabel).expandX().padTop(20);
        table.row();
        
        stage.addActor(table);
    }
    
    public void updateTime(long startTime) {
        seconds = (((int) TimeUtils.timeSinceMillis(startTime)) / 1000) %60;
        minutes = (((int) TimeUtils.timeSinceMillis(startTime)) / (1000*60)) %60;
        milliseconds = ((int) TimeUtils.timeSinceMillis(startTime))%1000;
        
        String time;
        time = String.format("%02d : %02d.%03d",
            minutes, seconds, milliseconds
        );

        timerLabel.setText(time);
    }    
    
    public void updateLap() {
        lapLabel.setText(null);
    }
    
    public void updateSpeed() {
        
    }
    
    public void updateFuel() {
        
    }
    
    public void dispose() {
        stage.dispose(); 
        font.dispose();
        
    }
    
}
