package com.mygdx.game.desktop;

import Test.Game;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import com.mygdx.game.RacingGame;

public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();


        cfg.title = RacingGame.TITLE;
        cfg.width = RacingGame.V_WIDTH;
        cfg.height = RacingGame.V_HEIGHT;
        cfg.addIcon("Tire.png", Files.FileType.Internal);
        cfg.fullscreen =false;

        new LwjglApplication(new RacingGame(), cfg);
    }
}
