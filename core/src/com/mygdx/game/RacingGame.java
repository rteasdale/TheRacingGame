/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author ROSY
 */
public class RacingGame extends Game {

    public SpriteBatch batch; 
    public BitmapFont font;
    
    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new TestCarMoving(this));
    }

    @Override
    public void render() {
        super.render();
    }
    
    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
    
    public enum GameState {
        GameState_Menu,
        GameState_321Go,
        GameState_Racing, 
        GameState_Pause,
        GameState_Results        
    }
}