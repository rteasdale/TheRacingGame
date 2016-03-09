/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.RacingGame;

/**
 *
 * @author ROSY
 */
public class GameScreen implements Screen, InputProcessor {
    
    
    RacingGame game;
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private OrthographicCamera camera;
    
    private Sprite sprite;
    private SpriteBatch sb;

    
    public GameScreen(RacingGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        sprite = new Sprite(new Texture("golf/golf_yellow.png"));
        sb = new SpriteBatch(); 
        
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        tiledMap = new TmxMapLoader().load("map1.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);  
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
     
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();   
        
        sb.begin();
        sprite.draw(sb);
        sb.end();
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
    }
    
    public boolean keyDown(int keycode) {
        return false;
    }

    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.LEFT)
            camera.translate(-40,0);
        if(keycode == Input.Keys.RIGHT)
            camera.translate(40,0);
        if(keycode == Input.Keys.UP)
            camera.translate(0,40);
        if(keycode == Input.Keys.DOWN)
            camera.translate(0,-40);
        return false;
    }

    public boolean keyTyped(char character) {
       
        return false;
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    Vector3 clickCoordinates = new Vector3(screenX,screenY,0);
    Vector3 position = camera.unproject(clickCoordinates);
    sprite.setPosition(position.x, position.y);
    return true;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    public boolean scrolled(int amount) {
        return false;
    }
    
}
