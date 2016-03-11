package com.pineapplemelon.smugglerworlds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;
import screens.GameScreen;

public class SmugglerWorlds extends Game {
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		setScreen(new GameScreen());
	}

}
