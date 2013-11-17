package com.badlogic.androidgames.framework.game;

import java.util.List;

import android.graphics.Color;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.Input.TouchEvent;

public class TopMenuScreen extends Screen {

	public TopMenuScreen(Game game) {
		super(game);
		Utils.soundEnabled = true;
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();

		int len = touchEvents.size();

		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				if (inBounds(event, 180, 315, 290, 150)) {
					game.setScreen(new PlayScreen(game));
					if (Utils.soundEnabled)
						Assets.sound_enter.play(1);
				}
				if (inBounds(event, 183, 481, 290, 150)) {
					game.setScreen(new HighScoreRunking(game));
					if (Utils.soundEnabled)
						Assets.sound_enter.play(1);
				}
				if (inBounds(event, 183, 644, 290, 150)) {
					if (Utils.soundEnabled)
						Assets.sound_enter.play(1);
				}
			}
		}

	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawPixmap(Assets.image_top, 0, 0);
		
		/*touch判定*/
//		g.drawRect(180, 315, 300, 155, Color.RED, 100);
//		g.drawRect(180, 481, 300, 155, Color.RED, 100);
//		g.drawRect(180, 644, 300, 155, Color.RED, 100);
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
