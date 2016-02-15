/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import sun.net.www.ApplicationLaunchException;

/**
 *
 * @author ROSY
 */
public class RacingGame extends Game {

    public SpriteBatch batch; 
    
    @Override
    public void create() {
        
    }

    public enum GameState {
        GameState_Menu,
        GameState_321Go,
        GameState_Racing, 
        GameState_Pause,
        GameState_Results        
    }
}