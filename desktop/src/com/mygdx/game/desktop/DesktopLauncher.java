package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import Test.Test2;
import Test.Test1;

public class DesktopLauncher {
	public static void main (String[] arg) {
            LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
            new LwjglApplication(new Test2(), cfg);
	}
}
