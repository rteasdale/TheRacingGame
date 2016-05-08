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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.RacingGame;
import com.sun.javafx.scene.control.skin.LabelSkin;
import handlers.ScreenAssets;

/**
 *
 * @author Administrateur
 */
public class CreditsScreen implements Screen{

    private RacingGame game;
    private Stage stage;
    private BitmapFont font;

    private Label author1;
    private Label author2;
    private Label musicLbl;
    private Label music1;
    private Label music2;
    private Label music3;
    private Label music4;
    private Label winningSound;
    private Label.LabelStyle lbl_style;
    
    public CreditsScreen(RacingGame game, ScreenAssets assets){
        this.game = game;
        
        stage = new Stage();
        
        font = assets.manager.get(ScreenAssets.font);
        
        lbl_style = new Label.LabelStyle();
        lbl_style.font = font;
        lbl_style.fontColor = new Color(Color.WHITE);
    }
    
    @Override
    public void show() {

        music1 = new Label("Menu: \n" + "Mason - Exceeder (Original Mix) ", lbl_style);
        music2 = new Label("MAP 1 - Classic: Wiggle (8 Bit Remix Cover Version) "
                + "[Tribute to Jason Derulo ft. Snoop Dogg] - 8 Bit Universe", lbl_style);
        music3 = new Label("MAP 2 - Snow: \n" + "Lensko - Cetus", lbl_style);
        music4 = new Label("MAP 3 - Space: \n" + "Asura - Dust and Daffodils", lbl_style);
        winningSound = new Label("Finish Sound - Mario Kart 64", lbl_style);

        Table table = new Table();
        
        table.add(new Label("Authors: ", lbl_style));
        table.add(new Label("Rosy Teasdale", lbl_style));
        table.add(new Label("Jonathan Cournoyer", lbl_style));
        table.row();
        table.add(new Label("Music: ", lbl_style));
        table.row();
        table.add(music1);
        table.add(music2);
        table.add(music3);
        table.add(music4);
        table.row();
        table.add(new Label("Sound: ", lbl_style));
        table.row();
        table.add(winningSound);
        
        table.setPosition(20, 20);
        stage.addActor(table);
        
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(3/255f,13/255f,128/255f,1); //set background color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.draw();
        stage.act();
        
        
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
        font.dispose();
        stage.dispose();
        
    }
    
}
