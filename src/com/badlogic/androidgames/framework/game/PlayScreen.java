package com.badlogic.androidgames.framework.game;

import java.util.List;
import java.util.Random;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.Input.KeyEvent;
import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.game.Utils;

public class PlayScreen extends Screen {
	enum GameState {
		Ready, Running, Paused, GameOver
	}

	GameState state = GameState.Ready;
	private World world;
	private Block block;
	private Block[] nextBlock;
	private Utils utils;
	private Point old_pos;
	private Point w_pos;
	private boolean flag_onTouch = false; // 画面をタップしているか返す

	public PlayScreen(Game game) {
		super(game);
		old_pos = new Point(-1, -1);
		w_pos = new Point(-1, -1);
		world = new World();
		utils = new Utils();
		block = createBlock(world);
		nextBlock = new Block[2]; // 表示するブロック数+1
		for (int i = 1; i < nextBlock.length; i++)
			nextBlock[i] = createBlock(world);
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		List<KeyEvent> keyEvents = game.getInput().getKeyEvents();
		game.getInput().getKeyEvents();
		if (state == GameState.Ready)
			updateReady(touchEvents, deltaTime);
		if (state == GameState.Running)
			updateRunning(touchEvents, keyEvents, deltaTime);
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

	private void updateRunning(List<TouchEvent> touchEvents,
			List<KeyEvent> keyEvents, float deltaTime) {
		int interval_x = 40;
		int interval_y = 40;
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (isBounds(event, 0, 80, 480, 554)) {
				switch (event.type) {
				case MotionEvent.ACTION_DOWN:
					old_pos.x = event.x;
					old_pos.y = event.y;
					flag_onTouch = true;
					return;

				case MotionEvent.ACTION_UP:
					if (flag_onTouch) {
						block.turn();
						old_pos.x = -1;
						old_pos.y = -1;
					}
					return;

				case MotionEvent.ACTION_MOVE:
					if (0 > old_pos.x - event.x) {
						w_pos.x += -(old_pos.x - event.x);
						if (w_pos.x > interval_x) {
							w_pos.x -= interval_x;
							block.move(Block.RIGHT, deltaTime);
							flag_onTouch = false;
						}
					} else if (0 < old_pos.x - event.x) {
						w_pos.x += old_pos.x - event.x;
						if (w_pos.x > interval_x) {
							w_pos.x -= interval_x;
							block.move(Block.LEFT, deltaTime);
							flag_onTouch = false;
						}
					} else if (0 > old_pos.y - event.y) {
						w_pos.y += -(old_pos.y - event.y);
						if (w_pos.y > interval_y) {
							w_pos.y -= interval_y;
							block.Down();
							flag_onTouch = false;
						}
					}
					block.move(Block.DOWN, deltaTime);
					old_pos.x = event.x;
					old_pos.y = event.y;
					return;
				}
			}

			// ボタン部分
			switch (event.type) {
			case MotionEvent.ACTION_DOWN:
				if (isBounds(event, 117 * 0 + 6, 633, 115, 80))
					block.move(Block.LEFT, deltaTime);
				if (isBounds(event, 117 * 1 + 4 + 2, 633, 115, 80))
					block.turn();
				if (isBounds(event, 117 * 2 + 4 + 2, 633, 115, 80))
					block.Down();
				if (isBounds(event, 117 * 3 + 4 + 2, 633, 115, 80))
					block.move(Block.RIGHT, deltaTime);
				if (isBounds(event, 403, 6, 69, 69)) {
					state = GameState.Paused;
					if (Utils.soundEnabled)
						Assets.sound_enter.play(1);
				}
				return;
			}
		}

		world.update(deltaTime);
		boolean isFixed = block.move(Block.DOWN, deltaTime);
		if (isFixed) {
			nextBlock[0] = nextBlock[1];
			block = nextBlock[0];
			for (int i = 1; i < nextBlock.length; i++)
				nextBlock[i] = createBlock(world);
		}
		world.delteLine();
		if (world.isStacked()) {
			state = GameState.GameOver;
			Utils.addScore(game.getFileIO(), world.score);
		}
	}

	private Block createBlock(World world) {
		Random rand = new Random();
		int blockNo = rand.nextInt(7);
		switch (blockNo) {
		case Block.BAR:
			return new BarBlock(world);

		case Block.Z_SHAPE:
			return new ZShapeBlock(world);

		case Block.SQUARE:
			return new SquareBlock(world);

		case Block.L_SHAPE:
			return new LShapeBlock(world);

		case Block.REVERRSE_Z_SHAPE:
			return new ReverseZShapeBlock(world);

		case Block.T_SHAPE:
			return new TShapeBlock(world);

		case Block.REVERSE_L_SHAPE:
			return new ReverseLShapeBlock(world);
		}
		return null;
	}

	private void updateGameOver(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			switch (event.type) {
			case MotionEvent.ACTION_UP:
				if (isBounds(event, 90, 450, 300, 80)) {
					if (Utils.soundEnabled)
						Assets.sound_enter.play(1);
					game.setScreen(new PlayScreen(game));
				}
				if (isBounds(event, 90, 600, 300, 80)) {
					if (Utils.soundEnabled)
						Assets.sound_enter.play(1);
					game.setScreen(new TopMenuScreen(game));
				}
				break;
			}
		}
	}

	private void updatePaused(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			switch (event.type) {
			case MotionEvent.ACTION_UP:
				if (isBounds(event, 90, 200, 300, 80)) {
					if (Utils.soundEnabled)
						Assets.sound_enter.play(1);
					state = GameState.Running;
				}
				if (isBounds(event, 90, 350, 300, 80)) {
					if (Utils.soundEnabled)
						Assets.sound_enter.play(1);
					game.setScreen(new PlayScreen(game));
				}
				if (isBounds(event, 90, 500, 300, 80)) {
					if (Utils.soundEnabled)
						Assets.sound_enter.play(1);
					game.setScreen(new TopMenuScreen(game));
				}
				break;
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		drawAlwaysUI(); // 背景やボタンなどの全画面で表示したいモノ
		if (state == GameState.Ready)
			drawReadyUI();
		if (state == GameState.Running)
			drawRunningUI();
		if (state == GameState.Paused)
			drawPausedUI();
		if (state == GameState.GameOver)
			drawGameOverUI();
	}

	private void drawAlwaysUI() {
		Graphics g = game.getGraphics();
		g.drawPixmap(Assets.buckground01, 0, 0);

		// 「スコア」(点数)の描画
		int[] list_score = utils.getScorelist(world.getScore());
		int x_margin = 280;
		int x_score = x_margin - (45 * list_score.length - 1);
		Pixmap[] fonts_number = utils.madeF_Number();
		for (int i = 0; i < list_score.length; i++)
			g.drawPixmap(fonts_number[list_score[i]], x_score + (i * 45) + 10,
					25);
	}

	private void drawReadyUI() {
		Graphics g = game.getGraphics();
		g.drawPixmap(Assets.tap_image, 0, 250);
	}

	private void drawRunningUI() {
		Graphics g = game.getGraphics();
		world.draw(g);
		block.draw(g);
		if (world.isGameover())
			state = GameState.GameOver;

		int x_margin = 338;
		int y_margin = 25;
		int blocksize = 11;
		/* 次のブロックの表示 */
		for (int i = 1; i < nextBlock.length; i++) {
			for (int y = 0; y < Block.ROW; y++) {
				for (int x = 0; x < Block.COL; x++) {
					if (nextBlock[i].block[y][x] == 1) {
						g.drawRect(x_margin + x * (blocksize + 1), y_margin + y
								* (blocksize + 1), blocksize, blocksize,
								nextBlock[i].color, 200);
					}
				}
			}
		}

		/*************** controlerに着色 *******************/
		// g.drawRect(117*0+6, 633, 115, 80, Color.RED, 100);
		// g.drawRect(117*1+4+2, 633, 115, 80, Color.BLUE, 100);
		// g.drawRect(117*2+4+2, 633, 115, 80, Color.GREEN, 100);
		// g.drawRect(117*3+4+2, 633, 115, 80, Color.YELLOW, 100);
		// g.drawRect( 403, 6, 69, 69, Color.GRAY, 100);
		/************************************************/
	}

	private void drawPausedUI() {
		Graphics g = game.getGraphics();
		g.drawPixmap(Assets.Resume_button, 90, 200);
		g.drawPixmap(Assets.Retry_button, 90, 350);
		g.drawPixmap(Assets.Top_button, 90, 500);
	}

	private void drawGameOverUI() {
		Graphics g = game.getGraphics();
		world.draw(g);
		g.drawPixmap(Assets.gameover_image, 0, 200);
		g.drawPixmap(Assets.Retry_button, 90, 450);
		g.drawPixmap(Assets.Top_button, 90, 600);
	}

	// タップ時の当たり判定 目標がタップされた場合true、違う場合false
	private boolean isBounds(TouchEvent event, int x, int y, int width,
			int height) {
		if (event.x > x && event.x < x + width - 1 && event.y > y
				&& event.y < y + height - 1)
			return true;
		else
			return false;
	}

	@Override
	public void pause() {
		if (state == GameState.Running)
			state = GameState.Paused;
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
