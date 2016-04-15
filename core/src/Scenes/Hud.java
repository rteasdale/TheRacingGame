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
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.RacingGame;
import java.util.Calendar;
import java.util.TimerTask;

/**
 *
 * @author ROSY
 */
public class Hud {
    public Stage stage;
    private Viewport viewport;
    private BitmapFont font;
    private Calendar calendar;
    
    private Timer timer;
    private float timeCount;
    private Integer lap;
    private int position;
    private int startTime; 
    private int endTime;
    
    private int minutes;
    private int seconds;
    private int milliseconds;
    
    private Label timerLabel;
    private Label lapLabel;
    private Label positionLabel;
    
    
    public Hud(SpriteBatch batch) {
        font = new BitmapFont(Gdx.files.internal("menu/button_font.fnt"), Gdx.files.internal("menu/button_font.png"),false);
                
        viewport = new FitViewport(RacingGame.V_WIDTH, RacingGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);
        
        Table table = new Table();
        table.top();
        table.setFillParent(true);
   
        timerLabel = new Label((Integer.toString(minutes) + " : " + Integer.toString(seconds)) + " : " + Integer.toString(milliseconds), new Label.LabelStyle(font, Color.BLUE));

        lapLabel = new Label(("a"), new Label.LabelStyle(font, Color.WHITE));
        
        table.add(timerLabel).expandX().padTop(20); //extend to end of screen
        table.add(lapLabel).expandX().padTop(20);
        table.row();
        
        stage.addActor(table);
    }
    
    public void update(float totalTime, float f) {
        minutes = ((int)totalTime) / 60;
        seconds = ((int)totalTime) % 60;
        
        totalTime+=f;
        milliseconds = ((int)totalTime) %1000;

        timerLabel.setText(Integer.toString(minutes) + " : " + Integer.toString(seconds) + " : " + Integer.toString(milliseconds));

    }
    
    public void dispose() {
        stage.dispose(); 
        font.dispose();
        
    }
    
}
