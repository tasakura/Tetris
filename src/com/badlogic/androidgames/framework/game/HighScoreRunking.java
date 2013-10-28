package com.badlogic.androidgames.framework.game;

import android.graphics.Color;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Screen;

public class HighScoreRunking extends Screen{
	String lines[] = new String[5];

	public HighScoreRunking(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawRect(0, 0, 481, 801, Color.WHITE);
		int y = 100;
		for (int i = 0; i < 5; i++) {
			g.drawTextAlp(lines[i], 20, y, Color.RED, 10);
			y += 50;
		}
		
	}

	@Override
	public void pause() {
		// TODO 自動生成されたメソッド・スタブ
		
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
