/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author ROSY
 */
public class GameScreen extends ScreenAdapter {

    private SpriteBatch batch;
    private Sprite evil;
    
    @Override
    public void show() {
        batch = new SpriteBatch();
        evil = new Sprite(new Texture("car.png"));
    }

    private int carX = 0, carY = 0;
     
    @Override
    public void render(float delta) {
        
    /** move sprite */
    
    if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            evil.translateX(-1f);
        } else {
            evil.translateX(-10.0f);
        }
    }
    
    if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
        if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT)) {
                evil.translateX(1f);
        } else {
                evil.translateX(10.0f);
        }
    }
    
    if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            evil.translateY(10.0f);
        } else {
            evil.translateY(10.0f);
        }
    }      
    
    if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            evil.translateY(-10.0f);
        } else {
            evil.translateY(-10.0f);
        }
    }
    
    batch.begin();
    evil.draw(batch);
    batch.end();
    Gdx.gl.glClearColor(0,0,0,0); //screen color
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);        
    batch.begin();
    batch.draw(evil, carX, carY);
    batch.end();
    
    }
    
    
}
