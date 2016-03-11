package com.pineapplemelon.smugglerworlds.desktop;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pineapplemelon.smugglerworlds.SmugglerWorlds;
import com.pineapplemelon.smugglerworlds.utils.Constants;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Constants.APP_WIDTH;
		config.height = Constants.APP_HEIGHT;
		new LwjglApplication(new SmugglerWorlds(), config);
	}
}