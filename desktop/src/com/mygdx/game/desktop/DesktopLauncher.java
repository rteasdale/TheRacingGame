package com.mygdx.game.desktop;


import Test.Game;
import Test.Test2;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.RacingGame;



public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();

        cfg.title = RacingGame.TITLE;
        cfg.width = RacingGame.V_WIDTH;
        cfg.height = RacingGame.V_HEIGHT;
        
<<<<<<< Updated upstream
        new LwjglApplication(new Test2(), cfg);
=======
<<<<<<< Updated upstream
        new LwjglApplication(new RacingGame(), cfg);
=======
        cfg.title = Game.TITLE;
        cfg.width = Game.V_WIDTH*Game.SCALE;
        cfg.height = Game.V_HEIGHT*Game.SCALE;
        
        new LwjglApplication(new Test1(), cfg);
>>>>>>> Stashed changes
>>>>>>> Stashed changes
    }
}
