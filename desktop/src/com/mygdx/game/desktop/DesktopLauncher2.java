/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.RacingGame;

/**
 *
 * @author ROSY
 */
public class DesktopLauncher2 {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();


        cfg.title = RacingGame.TITLE;
        cfg.width = RacingGame.V_WIDTH;
        cfg.height = RacingGame.V_HEIGHT;

//        cfg.title = Game.TITLE;
//        cfg.width = Game.V_WIDTH*Game.SCALE;
//        cfg.height = Game.V_HEIGHT*Game.SCALE;

        new LwjglApplication(new RacingGame(), cfg);
    }
}
