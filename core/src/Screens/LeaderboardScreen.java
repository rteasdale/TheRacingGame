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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.RacingGame;

/**
 *
 * @author ROSY
 */
public class LeaderboardScreen implements Screen {
    private RacingGame game;
    
    private Table table;
    private Stage stage;
    
    private Skin skin; 
    
    private final BitmapFont font;
    private Label label;
    private final Label.LabelStyle lbl_style;
    
    public LeaderboardScreen(RacingGame game) {
        this.game = game;
        table = new Table();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        
        /**TextureAtlas and skin */ 
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("menu/uiskin.txt"));
        skin = new Skin(atlas);
        
        /** BitmapFont */
        font = new BitmapFont(Gdx.files.internal("menu/button_font.fnt"), Gdx.files.internal("menu/button_font.png"),false);   
        
        /**Style*/
        lbl_style = new Label.LabelStyle();
        lbl_style.font = font;
        lbl_style.fontColor = new Color(Color.WHITE);
        
    }
    
    @Override
    public void show() {
        table.setSkin(skin);
        /** Position number*/
        for (int i = 1; i <= 10; i++) {
            label = new Label(Integer.toString(i), lbl_style);
            Label l = new Label("Best Time", lbl_style);
            table.add(label).pad(10);
            table.add(l);
            table.row();
        }
        

//        table.add("time");
        table.setPosition(100, 300);
        stage.addActor(table);
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(3/255f,13/255f,128/255f,1); //set background color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);   
        
        stage.act();
        stage.draw();        
    }

    @Override
    public void resize(int i, int i1) {
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
        stage.dispose();
        
    }
    
}
