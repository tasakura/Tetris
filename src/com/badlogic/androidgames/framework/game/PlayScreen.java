package com.badlogic.androidgames.framework.game;

import java.util.List;
import java.util.Random;

import android.graphics.Color;
import android.graphics.Point;
import android.view.MotionEvent;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.Screen;

public class PlayScreen extends Screen {

	enum GameState {
		Ready, Running, Paused, GameOver
	}

	GameState state = GameState.Ready;
	int score = 0;
	private World world;
	private Block block;
	private Block newxtBlock;
	private Random rand;
	private final int interval_x = 30;
	private final int interval_y = 30;
	private Point old_pos;
	private Point w_pos;
	private boolean flag = false;

	public PlayScreen(Game game) {
		super(game);
		old_pos = new Point(-1, -1);
		w_pos = new Point(-1, -1);
		rand = new Random();
		world = new World();
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
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			switch (event.type) {
			case MotionEvent.ACTION_DOWN:
				old_pos.x = event.x;
				old_pos.y = event.y;
				flag = true;
				break;

			case MotionEvent.ACTION_UP:
				if(flag) {
					block.turn();
					old_pos.x = -1;
					old_pos.y = -1;
				}
				break;

			case MotionEvent.ACTION_MOVE:
				if(0>old_pos.x-event.x) {
					w_pos.x += -(old_pos.x-event.x);
					if(w_pos.x>interval_x) {
						w_pos.x -= interval_x;
						block.move(Block.RIGHT, deltaTime);
						flag = false;
					}
				}
				else if(0<old_pos.x-event.x) {
					w_pos.x += old_pos.x-event.x;
					if(w_pos.x>interval_x) {
						w_pos.x -= interval_x;
						block.move(Block.LEFT, deltaTime);
						flag = false;
					}
				} else if(0>old_pos.y-event.y) {
					w_pos.y += -(old_pos.y-event.y);
					if(w_pos.y>interval_y) {
						w_pos.y -= interval_y;
						block.Down(deltaTime);
						flag = false;
					}
				} 
				block.move(Block.DOWN, deltaTime);
				old_pos.x = event.x;
				old_pos.y = event.y;
				break;
			}
		}

		world.update(deltaTime);
		boolean isFixed = block.move(Block.DOWN, deltaTime);
		if (isFixed) {
			newxtBlock = createBlock(world);
			block = newxtBlock;
		}
		world.delteLine();
	}

	private Block createBlock(World world) {
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
		Graphics g = game.getGraphics();
		g.drawRect(0, 0, 481, 801, Color.WHITE);
		g.drawLine(0, 642, 480, 642, 5, Color.BLACK);
		g.drawTextAlp("Touch", 20, 350, Color.GRAY, 150);
	}

	private void drawRunningUI() {
		Graphics g = game.getGraphics();
		g.drawRect(0, 0, 480, 800, Color.WHITE);
		g.drawLine(0, 642, 480, 642, 5, Color.BLACK);

		world.draw(g);
		block.draw(g);

		int _score = score;
		int i;
		for (i = 0; _score > 9; i++) {
			if (_score / 10 < 10)
				break;
			else {
				i++;
				_score /= 10;
			}
		}
		g.drawTextAlp("スコア:", 20, 700, Color.RED, 50);
		g.drawTextAlp("" + world.getScore(), 200 - i * 15, 700, Color.RED, 50);
		if (world.isGameover())
			state = GameState.GameOver;
	}

	private void drawPausedUI() {
	}

	private void drawGameOverUI() {
		Graphics g = game.getGraphics();
		g.drawTextAlp("GameOver", 0, 350, Color.RED, 100);
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
