/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import Screens.CarSelectionScreen;
import Screens.GameScreen;
import Screens.PlayScreen;

import Screens.MainMenuScreen;
import Screens.MapSelectionScreen;

import Screens.PlayScreen;
import Screens.PlayerScreen;
import Screens.SettingsScreen;
import Screens.SplashScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.TimeUtils;

/**
 *
 * @author ROSY
 */
public class RacingGame extends Game {
    public static final String TITLE = "Fuel2D";
    public static final int V_WIDTH = 1280; //virtual dimensions
    public static final int V_HEIGHT = 720;
    public OrthographicCamera camera;
    
    private int rendCount;
    private long startTime;
    private long endTime;
    
    @Override
    public void create() {
        Gdx.app.log("RacingGame", "App created");
        startTime = TimeUtils.millis();

        setScreen(new PlayerScreen(this, true)); //** start SpashSreen, with Game parameter **//
    
        //this.setScreen(new CarSelectionScreen(this));
    }

    @Override
    public void render() {
        super.render(); //delegates render method to the active screen
        rendCount++;
    }
    
    @Override
    public void dispose() {
        Gdx.app.log("RacingGame", "App rendered " + rendCount + " times");
        Gdx.app.log("RacingGame", "App ended");
        endTime = TimeUtils.millis();
        Gdx.app.log("RacingGame", "App running for " + (endTime-startTime)/1000 + " seconds.");
    }   
    
}