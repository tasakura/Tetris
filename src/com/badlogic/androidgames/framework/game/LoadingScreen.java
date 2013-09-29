package com.badlogic.androidgames.framework.game;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Screen;

public class LoadingScreen extends Screen {

	public LoadingScreen(Game game) {
		super(game);
		game.setScreen(new PlayScreen(game));
	}

	@Override
	public void update(float deltaTime) {
	}

	@Override
	public void present(float deltaTime) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

}
