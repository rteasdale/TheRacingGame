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
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
    
    private MainMenuScreen mainMenuScreen;
    
    private Stage stage;
    private Label progress_percentage;
    private Label.LabelStyle lbl_style;
    private OrthographicCamera camera;
            
    private BitmapFont font;
    
    private final ShapeRenderer renderer;
    
    private float progress;
    
    public LoadingScreen(RacingGame game, boolean twoPlayers, int mapNum, ScreenAssets assets) {
        this.game = game;
        this.assets = assets;
        this.twoPlayers = twoPlayers;
        this.mapNum = mapNum;
        
        mainMenuScreen.stopMenuMusic();
        
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
       
       stage.addActor(progress_percentage);
        
    }
    
    private void update(float delta) {
        progress = MathUtils.lerp(progress, assets.manager.getProgress(),0.1f);
        progress_percentage.setText("Loading " + "(" + Integer.toString((int)(progress*100)) + " % )");
        
        float delay = 2; // seconds

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
        font.dispose();
        stage.dispose();
        //renderer.dispose();
    }
    
}
