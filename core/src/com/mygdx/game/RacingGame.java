/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import Screens.GameScreen;
import Screens.MainMenuScreen;
import Screens.TestCarMoving;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author ROSY
 */
public class RacingGame extends Game {
    public SpriteBatch batch; 
    
    public static final String TITLE = "Fuel2D";
    public static final int V_WIDTH = 1280; //virtual dimensions
    public static final int V_HEIGHT = 720;
    public OrthographicCamera camera;
    
    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new TestCarMoving(this));
    }

    @Override
    public void render() {
        super.render(); //delegates render method to the active screen
    }
    
    @Override
    public void dispose() {
        batch.dispose();
    }
}