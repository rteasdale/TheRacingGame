/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;

/**
 *
 * @author ROSY
 */
public class TheRacingGame extends Game {

    @Override
    public void create() {
        
        setScreen(new GameScreen());
        
    }
}