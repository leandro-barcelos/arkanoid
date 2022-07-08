package com.poo.arkanoid;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.poo.arkanoid.Arkanoid;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(512, 480);
                config.setResizable(false);
                config.useVsync(true);
		new Lwjgl3Application(new Arkanoid(), config);
	}
}
