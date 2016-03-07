package com.mygdx.game.desktop;


import Test.Game;
import Test.Test1;
import Test.Test2;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.CarTest;
import com.mygdx.game.RacingGame;



public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();

        cfg.title = Game.TITLE;
        cfg.width = Game.V_WIDTH*Game.SCALE;
        cfg.height = Game.V_HEIGHT*Game.SCALE;

        new LwjglApplication(new CarTest(), cfg);
    }
}
