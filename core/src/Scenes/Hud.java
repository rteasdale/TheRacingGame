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
    
    private Integer timer;
    private float timeCount;
    private Integer lap;
    private int position;
    
    private Label countdownLabel;
    private Label lapLabel;
    private Label positionLabel;
    
    
    public Hud(SpriteBatch batch) {
        font = new BitmapFont(Gdx.files.internal("menu/button_font.fnt"), Gdx.files.internal("menu/button_font.png"),false);
        timeCount = 0;
        
        viewport = new FitViewport(RacingGame.V_WIDTH, RacingGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);
        
        Table table = new Table();
        table.top();
        table.setFillParent(true);
        
        countdownLabel = new Label(String.format("%03d", timer), new Label.LabelStyle(font, Color.WHITE));
        lapLabel = new Label(String.format("%01d", timer), new Label.LabelStyle(font, Color.WHITE));
        
        table.add(countdownLabel).expandX().padTop(10); //extend to end of screen
        table.add(lapLabel).expandX().padTop(10);
        table.row();
        
        stage.addActor(table);
    }
    
    
    
}
