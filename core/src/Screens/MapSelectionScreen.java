/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.RacingGame;
import handlers.ScreenAssets;

/**
 *
 * @author ROSY
 */
public class MapSelectionScreen implements Screen {
    private RacingGame game;
    private Stage stage;
    private ScreenAssets assets;
    private Texture title_texture;
    
    private String[] group;
    private String[] map;
    private String[] mapTitles;
    private FileHandle file;
    private int currentMap = 0;
    private BitmapFont font;
    
    private final boolean twoPlayers;
    
    private Image preview;
    private Image title;
    private ImageButton ready_btn;
    private ImageButton back_btn;
    private ImageButton selectNextMapButton;
    private ImageButton selectPreviousMapButton;
    
    private Label map_title;
    
    private Label.LabelStyle lbl_style;
    private ImageButton.ImageButtonStyle ready_style;
    private ImageButton.ImageButtonStyle back_style;
    private ImageButton.ImageButtonStyle nextmap_style;
    private ImageButton.ImageButtonStyle prevmap_style;   
    
    private TextureAtlas buttons_atlas;
    private Skin buttons_skin;    


    public MapSelectionScreen(RacingGame game, boolean twoPlayers, ScreenAssets assets) {
        //Gdx.app.log("Map Selection", "constructor called");
        this.game = game;
        this.twoPlayers = twoPlayers;
        this.assets = assets;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage); //** stage is responsive **//
        
        /**Texture*/
        title_texture = assets.manager.get(ScreenAssets.mapTitle);
        
        file = new FileHandle("data/maps.txt");
        group = file.readString().split("\n");
        mapTitles = group[0].split(",");
        map = group[1].split(",");

        /** BitmapFont */
        font = assets.manager.get(ScreenAssets.font);
        
        /** Atlas and skin */
        buttons_atlas = assets.manager.get(ScreenAssets.buttons_atlas);
        buttons_skin= new Skin(buttons_atlas);    
        
        /** Styles */
        ready_style = new ImageButton.ImageButtonStyle(buttons_skin.getDrawable("READY_button"), null, null, null, null, null);
        back_style = new ImageButton.ImageButtonStyle(buttons_skin.getDrawable("back_button"), null, null, null, null, null);
        nextmap_style = new ImageButton.ImageButtonStyle(buttons_skin.getDrawable("nextarrow_big"), null, null, null, null, null);
        prevmap_style = new ImageButton.ImageButtonStyle(buttons_skin.getDrawable("backarrow_big"), null, null, null, null, null);
 
        lbl_style = new Label.LabelStyle();
        lbl_style.font = font;
        lbl_style.fontColor = new Color(Color.WHITE);        
        
    }
    
    @Override
    public void show() {
        //Gdx.app.log("Map Selection", "show called");
        /** Title */
        title = new Image(title_texture);
        title.setPosition(280, 648);        
        
        /** Buttons */
        ready_btn = new ImageButton(ready_style);
        ready_btn.setPosition(1016, 24);
        back_btn = new ImageButton(back_style);
        back_btn.setPosition(24, 24);
        
        selectPreviousMapButton = new ImageButton(prevmap_style);
        selectPreviousMapButton.setX(150);
        selectPreviousMapButton.setY(300);
        selectNextMapButton = new ImageButton(nextmap_style);
        selectNextMapButton.setX(1058);
        selectNextMapButton.setY(300);      
        
        /** Map Preview*/
        preview = new Image(new Texture(Gdx.files.internal(map[0])));
        preview.setPosition(320, 160);
        preview.setSize(650, 380);
        stage.addActor(preview);   
        
        /** Map Title*/
        map_title = new Label(mapTitles[0], lbl_style);
        map_title.setPosition(350, 130);
        stage.addActor(map_title);
        
        stage.addActor(selectPreviousMapButton);
        stage.addActor(selectNextMapButton);
        stage.addActor(ready_btn);
        stage.addActor(back_btn);
        stage.addActor(title); 
 
        listeners();

    }
    
    private void listeners() {
        /**Listeners*/
        ready_btn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                //if two players, generate game screen with 2 cars
                if (twoPlayers == true) {
                game.setScreen(new LoadingScreen(game, true, currentMap, assets)); //Change two players value 
                }
                if (twoPlayers == false) {
                game.setScreen(new LoadingScreen(game, false, currentMap, assets));
                }
            }
        });
        
        back_btn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                if(twoPlayers == false) {
                    //game.setScreen(new CarSelectionScreen(game, twoPlayers, 1, PlayerScreen.playerNameP1));
                }
                else {
                    //game.setScreen(new CarSelectionScreen(game, twoPlayers, 1, PlayerScreen.playerNameP1));
                }
            }
        });
        
        selectNextMapButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                if (currentMap < 2) {
                currentMap++;
                Gdx.app.log("Current map", map[currentMap]);
                map_title.setText(mapTitles[currentMap]);
                preview.remove();
                preview = new Image(new Texture(Gdx.files.internal(map[currentMap])));
                preview.setPosition(320, 160);
                preview.setSize(650, 380);
                stage.addActor(preview);    
                }
                else {
                currentMap = 0;
                Gdx.app.log("Current map", map[0]);
                /** Preview */
                map_title.setText(mapTitles[0]);
                preview.remove();
                preview = new Image(new Texture(Gdx.files.internal(map[0])));
                preview.setPosition(320, 160);
                preview.setSize(650, 380);
                stage.addActor(preview);                    
                }                
            }
        });
        
        selectPreviousMapButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                if (currentMap > 0) {
                currentMap--;
                //System.out.println(currentMap);
                Gdx.app.log("Current map", map[currentMap]);
                map_title.setText(mapTitles[currentMap]);
                preview.remove();
                preview = new Image(new Texture(Gdx.files.internal(map[currentMap])));
                preview.setPosition(320, 160);
                preview.setSize(650, 380);
                stage.addActor(preview);    
                }
                else {
                currentMap = 2;
                Gdx.app.log("Current map", map[2]);
                /** Preview */
                map_title.setText(mapTitles[2]);
                preview.remove();
                preview = new Image(new Texture(Gdx.files.internal(map[2])));
                preview.setPosition(320, 160);
                preview.setSize(650, 380);
                stage.addActor(preview);                    
                }                
            }
        });        
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
        Gdx.app.log("Map Selection", "dispose called");
        font.dispose();
        stage.dispose();
        buttons_atlas.dispose();
        buttons_skin.dispose();
    }
    
}
