/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.RacingGame;
import handlers.ScreenAssets;

/**
 *
 * @author ROSY
 */
public class MainMenuScreen implements Screen {
    private RacingGame game;
    
    private Stage stage;
    private Texture background;
    
    private ScreenAssets assets;
    private Texture title_texture;
    
    private TextureAtlas buttons_atlas;
    private Skin buttons_skin;

    private Image title;
    private ImageButton onePlayerButton;
    private ImageButton twoPlayersButton;
    private ImageButton settingsButton;
    private ImageButton exitButton;
    private ImageButton credits;
    private ImageButton leaderboard;
    
    private ImageButtonStyle style_1P;
    private ImageButtonStyle style_2P;
    private ImageButtonStyle style_settings;
    private ImageButtonStyle style_exit;
    private ImageButtonStyle style_credits;
    private ImageButtonStyle style_leader;
    
    public MainMenuScreen(RacingGame game, ScreenAssets assets) {
        this.assets = assets;
        this.game = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage); //** stage is responsive **//
        
        /** Textures */
        title_texture = assets.manager.get(ScreenAssets.mainTitle);
        
        /** Background image*/
        background = assets.manager.get(ScreenAssets.background);
        
        /** Atlas and skin */
        buttons_atlas = assets.manager.get(ScreenAssets.buttons_atlas);
        buttons_skin = new Skin(buttons_atlas);
        
        loadAssets();
        
        /** Styles */
        style_1P = new ImageButtonStyle(buttons_skin.getDrawable("menu_singleP"), null, null, null, null, null);
        style_2P = new ImageButtonStyle(buttons_skin.getDrawable("menu_twoP"), null, null, null, null, null);
        style_settings = new ImageButtonStyle(buttons_skin.getDrawable("menu_settings"), null, null, null, null, null);
        style_exit = new ImageButtonStyle(buttons_skin.getDrawable("menu_exit"), null, null, null, null, null);
        style_credits = new ImageButtonStyle(buttons_skin.getDrawable("menu_credits"), null, null, null, null, null);
        style_leader = new ImageButtonStyle(buttons_skin.getDrawable("menu_leaderboard"), null, null, null, null, null);
        
    }
    
    private void loadAssets() {
        
    }

    @Override
    public void show() {
        //Gdx.app.log("MainMenuScreen", "show called");
        
        /** Position actors */
        title = new Image(title_texture);
        title.setPosition(280, 550);
        
        onePlayerButton = new ImageButton(style_1P);
        onePlayerButton.setPosition(450, 450);
        twoPlayersButton = new ImageButton(style_2P);
        twoPlayersButton.setPosition(450, 350);
        settingsButton = new ImageButton(style_settings);
        settingsButton.setPosition(450, 250);
        exitButton = new ImageButton(style_exit);
        exitButton.setPosition(450, 150);
        
        credits = new ImageButton(style_credits);
        credits.setPosition(1000, 50);
        leaderboard = new ImageButton(style_leader);
        leaderboard.setPosition(60, 40);
        
        /** Add actors to stage */
        stage.addActor(credits);
        stage.addActor(leaderboard);
        stage.addActor(exitButton);
        stage.addActor(settingsButton);
        stage.addActor(twoPlayersButton);
        stage.addActor(onePlayerButton);
        stage.addActor(title);
    }

    private void listeners() {
        /** Settings */ 
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                game.setScreen(new SettingsScreen(game));
            }
        });        
        
        /** One player mode */
        onePlayerButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                game.setScreen(new PlayerScreen(game, false, assets));
            }
        });  

        /** Two Players mode */
        twoPlayersButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                game.setScreen(new PlayerScreen(game, true, assets)); //true for two players
            }
        });  
        
        /** Exit */
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });        
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(3/255f,13/255f,128/255f,1); //set background color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        listeners();
        
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0);
        stage.getBatch().end();
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
        //Gdx.app.log("MainMenuScreen", "hide called");
    }

    @Override
    public void dispose() {
        Gdx.app.log("MainMenuScreen", "dispose called");
        game.dispose();
        stage.dispose();
        background.dispose();
        buttons_atlas.dispose();
        buttons_skin.dispose();
    }
    
}
