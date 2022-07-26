package com.poo.arkanoid;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(800  , 600);
        config.setResizable(false);
        config.setWindowIcon("icon.png");
        config.useVsync(true);
        new Lwjgl3Application(new Arkanoid(), config);
    }
}
