package com.badlogic.androidgames.framework.game;

import java.util.List;

import android.graphics.Color;
import android.util.Log;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Screen;

public class PlayScreen extends Screen {

	enum GameState {
		Ready, Running, Paused, GameOver
	}

	GameState state = GameState.Running;
	World world;

	public PlayScreen(Game game) {
		super(game);
		world = new World();
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		if (state == GameState.Ready)
			updateReady(touchEvents, deltaTime);
		if (state == GameState.Running)
			updateRunning(touchEvents, deltaTime);
		if (state == GameState.Paused)
			updatePaused(touchEvents);
		if (state == GameState.GameOver) {
			updateGameOver(touchEvents);
		}
	}


	private void updateReady(List<TouchEvent> touchEvents, float deltaTime) {
		if (touchEvents.size() > 0)
			state = GameState.Running;
	}

	private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				if (event.x < 64 && event.y < 64) {
//					state = GameState.GameOver;
				}
			}
		}
		world.update(deltaTime);
	}

	private void updateGameOver(List<TouchEvent> touchEvents) {
		// TODO 自動生成されたメソッド・スタブ

	}

	private void updatePaused(List<TouchEvent> touchEvents) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	@Override
	public void present(float deltaTime) {
		if (state == GameState.Ready)
			drawReadyUI();
		if (state == GameState.Running)
			drawRunningUI();
		if (state == GameState.Paused)
			drawPausedUI();
		if (state == GameState.GameOver)
			drawGameOverUI();
	}


	private void drawReadyUI() {
	}

	private void drawRunningUI() {
		Graphics g = game.getGraphics();
		g.drawRect(0, 0, 481, 801, Color.WHITE);
		world.draw(g);
		Log.d("test04", "test04");
	}
	
	private void drawPausedUI() {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	private void drawGameOverUI() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void pause() {
		if (state == GameState.Running)
			state = GameState.Paused;

		if (world.gameOver) {
			// Settings.addScore(world.score);
			// Settings.save(game.getFileIO());
		}
	}

	@Override
	public void resume() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void dispose() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
