/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import Scenes.MusicPlayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.mygdx.game.RacingGame;
import handlers.ScreenAssets;

/**
 *
 * @author ROSY
 */
public class LoadingScreen implements Screen {
    private final RacingGame game; 
    private ScreenAssets assets;
    private boolean twoPlayers;
    private int mapNum;
    
    private Texture ASDW_controls;
    private Texture arrows_controls;
    
    private Image ASDW_Image;
    private Image arrows_Image;
    
    private MusicPlayer musicPlayer;
    
    private Stage stage;
    private Label progress_percentage;
    private Label.LabelStyle lbl_style;
    
    private Label OnePlayer;
    private Label TwoPlayer;
    
    private OrthographicCamera camera;
            
    private BitmapFont font;
    
    private final ShapeRenderer renderer;
    
    private float progress;
    
    public LoadingScreen(RacingGame game, boolean twoPlayers, int mapNum, ScreenAssets assets, MusicPlayer musicPlayer) {
        this.game = game;
        this.assets = assets;
        this.twoPlayers = twoPlayers;
        this.mapNum = mapNum;
        this.musicPlayer = musicPlayer;
        
        
        //stop music 
        musicPlayer.stopMusic();
        
        stage = new Stage();
        camera = new OrthographicCamera();
        
        font = assets.manager.get(ScreenAssets.font);

        renderer = new ShapeRenderer();
        progress = 0f;
        
        /**Styles*/
        lbl_style = new Label.LabelStyle();
        lbl_style.font = font;
        lbl_style.fontColor = new Color(Color.WHITE);        

        queueAssets();
        
    }
    
    @Override
    public void show() {
       progress_percentage = new Label("Loading : " + "(" + progress + ")", lbl_style);
       progress_percentage.setPosition(RacingGame.V_HEIGHT/2, RacingGame.V_WIDTH/2-510);
       
<<<<<<< Updated upstream
       OnePlayer = new Label("CONTROLS FOR PLAYER 1", lbl_style);
       OnePlayer.setPosition(500, 500);
=======
       OnePlayer = new Label("Controls for Player 1", lbl_style);
       OnePlayer.setPosition(650, 600);
>>>>>>> Stashed changes
       
       arrows_controls = assets.manager.get(ScreenAssets.arrows_controls);
       arrows_Image = new Image(arrows_controls);
       arrows_Image.setPosition(550, 400);
       
        if (twoPlayers) {
            //put 1P and 2P controls
            ASDW_controls = assets.manager.get(ScreenAssets.ASDW_controls);
            TwoPlayer = new Label("CONTROLS FOR PLAYER 2", lbl_style);
            ASDW_Image = new Image(ASDW_controls);
            
            arrows_Image.setPosition(700, 400);
            ASDW_Image.setPosition(300, 400);
            OnePlayer.setPosition(650, 500);
            TwoPlayer.setPosition(250, 500);
            
            stage.addActor(ASDW_Image);
            stage.addActor(TwoPlayer);
        }  
       
       stage.addActor(progress_percentage);
       stage.addActor(arrows_Image);
       stage.addActor(OnePlayer);
        
    }
    
    private void update(float delta) {
        progress = MathUtils.lerp(progress, assets.manager.getProgress(),0.1f);
        progress_percentage.setText("Loading " + "(" + Integer.toString((int)(progress*100)) + " % )");
        
        float delay = 5; // seconds

        Timer.schedule(new Task(){
            @Override
            public void run() {
            if (assets.manager.update() && progress >= assets.manager.getProgress() - 101f) {
                game.setScreen(new GameScreen(game, twoPlayers, mapNum, assets));
            }
            }
        }, delay);        
       
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(0f,0f,0f,1f); //set background color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        update(f);
        
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.WHITE);
        renderer.rect(350, RacingGame.V_HEIGHT/2-200, RacingGame.V_WIDTH/2-50, 20);
        
        renderer.setColor(Color.BLUE);
        renderer.rect(351, RacingGame.V_HEIGHT/2-199, progress * (RacingGame.V_WIDTH/2-52), 18);        
        renderer.end();

        stage.act();
        stage.draw();
    }
    
    private void queueAssets() {
        assets.loadGameScreen();
        assets.loadHUD();
        assets.manager.finishLoading();
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
//        font.dispose();
//        stage.dispose();
//        renderer.dispose();
    }
    
}
