/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.RacingGame;
import handlers.ScreenAssets;

/**
 *
 * @author Administrateur
 */
public class CreditsScreen implements Screen{

    private final RacingGame game;
    private Stage stage;
    private BitmapFont font;
    private final ScreenAssets assets;

    private Label music1;
    private Label music2;
    private Label music3;
    private Label music4;
    private Label winningSound;
    private Label.LabelStyle lbl_style;
    
    private TextureAtlas buttons_atlas;
    private Skin buttons_skin;
    
    private ImageButton return_mainmenu;
    private ImageButton.ImageButtonStyle image_style;
    
    private Sound click;
    
    public CreditsScreen(RacingGame game, ScreenAssets assets){
        this.game = game;
        this.assets = assets;      
    }
    
    @Override
    public void show() {
        stage = new Stage();
        
        Gdx.input.setInputProcessor(stage);
        
        click = assets.manager.get(ScreenAssets.click_sound2);
        
        font = assets.manager.get(ScreenAssets.font);
        
        /**TextureAtlas and skin */ 
        buttons_atlas = assets.manager.get(ScreenAssets.buttons_atlas);
        buttons_skin = new Skin(buttons_atlas);        
        
        lbl_style = new Label.LabelStyle();
        lbl_style.font = font;
        lbl_style.fontColor = new Color(Color.WHITE);
        
        image_style = new ImageButton.ImageButtonStyle();
        image_style.imageUp = buttons_skin.getDrawable("pause_return");
        
        /**Music labels*/
        music1 = new Label("Menu: \n" + "Mason - Exceeder (Original Mix) ", lbl_style);
        music2 = new Label("MAP 1 - Classic: \n" + "Wiggle (8 Bit Remix Cover Version)\n" + "8 Bit Universe", lbl_style);
        music3 = new Label("MAP 2 - Snow: \n" + "Lensko - Cetus", lbl_style);
        music4 = new Label("MAP 3 - Space: \n" + "Asura - Dust and Daffodils", lbl_style);
        winningSound = new Label("Finish Sound - Mario Kart 64", lbl_style);

        /**Table*/
        Table table = new Table();
        
        table.add(new Label("Authors: ", lbl_style)).pad(20);
        table.row();
        table.add(new Label("Rosy Teasdale", lbl_style));
        table.row();
        table.add(new Label("Jonathan Cournoyer", lbl_style));
        table.row();
        table.add(new Label("Music: ", lbl_style)).pad(20);
        table.row();
        table.add(music1).pad(5);
        table.row();
        table.add(music2).pad(5);
        table.row();
        table.add(music3).pad(5);
        table.row();
        table.add(music4).pad(5);
        table.row();
        table.add(new Label("Sound: ", lbl_style)).pad(20);
        table.row();
        table.add(winningSound);

        
        return_mainmenu = new ImageButton(image_style);
        return_mainmenu.setPosition(900, 10);
        
        table.setPosition(640,400);
        stage.addActor(table);
        stage.addActor(return_mainmenu);
        
        listeners();
        
    }

    private void listeners() {
        return_mainmenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                click.play();
                game.setScreen(new MainMenuScreen(game, assets));
            }
        });
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
        buttons_atlas.dispose();
        buttons_skin.dispose();
        
    }
    
}
