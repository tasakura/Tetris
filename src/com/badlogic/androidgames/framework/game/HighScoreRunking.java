package com.badlogic.androidgames.framework.game;

import java.util.List;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.game.Utils;

public class HighScoreRunking extends Screen{
	private String[][] list;
	private Pixmap[] numbers;
	Utils utils;

	public HighScoreRunking(Game game) {
		super(game);
		utils = new Utils();
		list = Utils.readFile(game.getFileIO());
		numbers = utils.madeF_Number();
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();

		int len = touchEvents.size();

		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				if (inBounds(event, 410, 720, 70, 70)) {
					game.setScreen(new TopMenuScreen(game));
					return;
				}
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawPixmap(Assets.image_score, 0, 0);
		for (int i = 0; i < list.length; i++) {
			for (int j = 0; j < list[0].length && list[i][j] != null; j++) {
				int[] list_score = utils.getScorelist(Integer.parseInt(list[i][j]));	
				for (int k = 0; k < list_score.length; k++) {
					g.drawPixmap(numbers[list_score[k]], 350-k*55, 70+160*i);
				}
			}
		}
	}

	private boolean inBounds(TouchEvent event, int x, int y, int width,
			int height) {
		if (event.x > x && event.x < x + width - 1 && event.y > y
				&& event.y < y + height - 1)
			return true;
		else
			return false;
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
