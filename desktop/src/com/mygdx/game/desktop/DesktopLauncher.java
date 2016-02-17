package com.mygdx.game.desktop;

import Test.Test1;
import Test.Test2;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.RacingGame;


public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
<<<<<<< Updated upstream
        new LwjglApplication(new RacingGame(), cfg);
=======
        new LwjglApplication(new Test1(), cfg);
>>>>>>> Stashed changes
    }
}
