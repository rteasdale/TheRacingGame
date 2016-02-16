/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import Test.Test2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.RacingGame;

/**
 *
 * @author ROSY
 */
public class TestCarMoving implements Screen {

    final RacingGame game;
    OrthographicCamera camera;
    
    FileHandle car = Gdx.files.internal("prius/prius_white.png");
    Sprite s = new Sprite(new Texture(car));
    Stage stage;
    

    public class MyActor extends Actor {
        @Override
        public void draw(Batch batch, float alpha) {
            batch.draw(s, 0,0);
        }
    }
    
    public TestCarMoving (final RacingGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(true);
        stage = new Stage(new ScreenViewport());
        
        MyActor actor = new MyActor();
        
        stage.addActor(actor);
    }
    

    
    @Override
    public void show() {
        
    }

    @Override
    public void render(float f) {
        
    if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            s.translateX(-1f);
        } else {
            s.translateX(-10.0f);
        }
    }
    
    if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
        if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT)) {
                s.translateX(1f);
        } else {
                s.translateX(10.0f);
        }
    }
    
    if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            s.translateY(10.0f);
        } else {
            s.translateY(10.0f);
        }
    }      
    
    if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            s.translateY(-10.0f);
        } else {
            s.translateY(-10.0f);
        }
    }        
        
        Gdx.gl.glClearColor(1, 1, 1, 1); //change background color to white
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
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
