package com.badlogic.androidgames.framework.game;

import java.util.List;
import java.util.Random;
import android.graphics.Point;
import android.view.MotionEvent;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Screen;

public class PlayScreen extends Screen {
	enum GameState {
		Ready, Running, Paused, GameOver
	}

	GameState state = GameState.Ready;
	private World world;
	private Block block;
	private Block nextBlock;
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
		int interval_x = 40;
		int interval_y = 40;
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (isBounds(event, 0, 0, 480, 560)) {
				switch (event.type) {
				case MotionEvent.ACTION_DOWN:
					old_pos.x = event.x;
					old_pos.y = event.y;
					flag_onTouch = true;
					break;

				case MotionEvent.ACTION_UP:
					if (flag_onTouch) {
						block.turn();
						old_pos.x = -1;
						old_pos.y = -1;
					}
					break;

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
							block.Down(deltaTime);
							flag_onTouch = false;
						}
					}
					block.move(Block.DOWN, deltaTime);
					old_pos.x = event.x;
					old_pos.y = event.y;
					break;
				}
			}

			// ボタン部分
			switch (event.type) {
			case MotionEvent.ACTION_DOWN:
				if (isBounds(event, 0, 560, 112, 90))
					block.move(Block.LEFT, deltaTime);
				if (isBounds(event, 112, 560, 124, 90))
					block.turn();
				if (isBounds(event, 236, 560, 124, 90))
					block.Down();
				if (isBounds(event, 360, 560, 124, 90))
					block.move(Block.RIGHT, deltaTime);
				break;
			}
		}

		world.update(deltaTime);
		boolean isFixed = block.move(Block.DOWN, deltaTime);
		if (isFixed) {
			nextBlock = createBlock(world);
			block = nextBlock;
		}
		world.delteLine();
		if (world.isStacked()) {
			state = GameState.GameOver;
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

	}

	private void updatePaused(List<TouchEvent> touchEvents) {

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
		g.drawPixmap(Assets.line, 0, 562);

		// コントロール(ボタン)の描画
		int width_cont = 60;// ボタンのサイズ
		int margin = 65; // ボタンの間
		int start_x = 20; // 配置開始位置
		Pixmap[] controls = utils.madeF_Control();
		for (int i = 0; i < controls.length; i++)
			g.drawPixmap(controls[i], (width_cont + margin) * i + start_x, 570);

		// 「SCORE」(文字)の描画
		Pixmap[] fonts_score = utils.madeF_Score();
		for (int i = 0; i < fonts_score.length; i++)
			g.drawPixmap(fonts_score[i], (i * 45), 660);

		// 「スコア」(点数)の描画
		int[] list_score = utils.getScorelist(world.getScore());
		int x_score = 435 - (45 * list_score.length - 1);
		Pixmap[] fonts_number = utils.madeF_Number();
		for (int i = 0; i < list_score.length; i++)
			g.drawPixmap(fonts_number[list_score[i]], x_score + (i * 45), 660);
	}

	private void drawReadyUI() {
		Graphics g = game.getGraphics();
		Pixmap[] fonts_touch = utils.madeF_Touch();
		for (int i = 0; i < fonts_touch.length; i++)
			g.drawPixmap(fonts_touch[i], 10 + (i * 95), 270);
	}

	private void drawRunningUI() {
		Graphics g = game.getGraphics();
		world.draw(g);
		block.draw(g);
		if (world.isGameover())
			state = GameState.GameOver;
		/*************** controlerに着色 *******************/
		// g.drawRect(0, 560, 112, 90, Color.RED, 100);
		// g.drawRect(112, 560, 124, 90, Color.BLUE, 100);
		// g.drawRect(236, 560, 124, 90, Color.GREEN, 100);
		// g.drawRect(360, 560, 124, 90, Color.YELLOW, 100);
		/************************************************/
	}

	private void drawPausedUI() {
	}

	private void drawGameOverUI() {
		int w_y = -500;
		Pixmap[] fonts_gameover = utils.madeF_GameOver();
		Graphics g = game.getGraphics();
		if (w_y <= 100)
			w_y += 20;
		world.draw(g);
		g.drawPixmap(Assets.bg_gameover, 10, w_y);
		for (int i = 0; i < fonts_gameover.length; i++) {
			if (i < 4)
				g.drawPixmap(fonts_gameover[i], 10 + (i * 95), 200);
			else
				g.drawPixmap(fonts_gameover[i], 10 + ((i - 3) * 95), 330);

		}
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
