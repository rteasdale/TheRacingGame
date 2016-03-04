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

<<<<<<< Updated upstream
=======
<<<<<<< HEAD
        cfg.title = RacingGame.TITLE;
        cfg.width = RacingGame.V_WIDTH;
        cfg.height = RacingGame.V_HEIGHT;
        
        new LwjglApplication(new Test2(), cfg);

        new LwjglApplication(new RacingGame(), cfg);

=======
>>>>>>> origin/master
>>>>>>> Stashed changes
        cfg.title = Game.TITLE;
        cfg.width = Game.V_WIDTH*Game.SCALE;
        cfg.height = Game.V_HEIGHT*Game.SCALE;
        
<<<<<<< Updated upstream
        new LwjglApplication(new CarTest(), cfg);
=======
<<<<<<< HEAD
        new LwjglApplication(new Test2(), cfg);
=======
        new LwjglApplication(new CarTest(), cfg);
>>>>>>> origin/master
>>>>>>> Stashed changes

    }
}
