package com.badlogic.androidgames.framework.game;

import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Pixmap;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

public class Block {
	public static final int ROW = 4;
	public static final int COL = 4;

	// 移動方向
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;

	// ブロックの名前
	public static final int BAR = 0;
	public static final int Z_SHAPE = 1;
	public static final int SQUARE = 2;
	public static final int L_SHAPE = 3;
	public static final int REVERRSE_Z_SHAPE = 4;
	public static final int T_SHAPE = 5;
	public static final int REVERSE_L_SHAPE = 6;
	public static final int WALL = 7;

	private static final float TICK_INITIAL = 0.8f;
	private static final float TICK_INITIAL_2 = 0.01f;
	private static float tick = TICK_INITIAL; // 更新速度
	private static float tick_2 = TICK_INITIAL_2; // 更新速度
	private float tick_DownTime = 0;
	private float tick_SlideTime = 0;

	protected int imageNo;
	protected int color = Color.BLACK;
	private static final int TILE_SIZE = World.TILE_SIZE;
	protected int[][] block = new int[ROW][COL]; // ブロックの形を格納
	protected Point pos;
	private World world;

	public Block(World world) {
		this.world = world;
		init();
		imageNo = 6;
		pos = new Point(6, -4);
	}

	public void init() {
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				block[i][j] = 0;
			}
		}
	}

	public void draw(Graphics g) {
		int y_margin = 82;
		for (int y = 0; y < ROW; y++) {
			for (int x = 0; x < COL; x++) {
				if (block[y][x] == 1) {
					if (pos.y + y > -1)
						g.drawRect(5 + (((pos.x - 1) + x) * 1)
								+ (((pos.x - 1) + x) * TILE_SIZE), y_margin
								+ (pos.y + (y + 1)) * 1 + ((pos.y) + y)
								* TILE_SIZE, TILE_SIZE, TILE_SIZE, color, 200);
				}
			}
		}
	}

	public boolean move(int dir, float deltaTime) {
		Point newPos;
		tick_SlideTime += deltaTime;
		while (tick_SlideTime > tick_2) {
			tick_SlideTime -= tick_2;
			switch (dir) {
			case LEFT:
				newPos = new Point(pos.x - 1, pos.y);
				if (world.isMovable(newPos, block))
					pos = newPos;
				return false;

			case RIGHT:
				newPos = new Point(pos.x + 1, pos.y);
				if (world.isMovable(newPos, block))
					pos = newPos;
				return false;
			}
		}

		tick_DownTime += deltaTime;
		while (tick_DownTime > tick) {
			tick_DownTime -= tick;
			newPos = new Point(pos.x, pos.y + 1);
			if (world.isMovable(newPos, block)) {
				pos = newPos;
				if (Utils.soundEnabled)
					Assets.sound_down.play(1);
			} else {
				world.fixBlock(pos, block, imageNo);
				return true;
			}
		}
		return false;
	}

	
	public boolean Down() {
		Point newPos;
		tick_SlideTime = 0;
		newPos = new Point(pos.x, pos.y + 1);
		if (world.isMovable(newPos, block)) {
			pos = newPos;
			if (Utils.soundEnabled)
				Assets.sound_down.play(1);
		} else {
			world.fixBlock(pos, block, imageNo);
			return true;
		}
		return false;
	}

	public void Cancel() {
		Point newPos;
		newPos = new Point(pos.x, pos.y);
		if (world.isMovable(newPos, block))
			pos = newPos;
	}

	public void turn() {
		int[][] turnedBlock = new int[ROW][COL];

		// 回転したブロック
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				turnedBlock[j][ROW - 1 - i] = block[i][j];
			}
		}
		// 回転可能か調べる
		if (world.isMovable(pos, turnedBlock)) {
			block = turnedBlock;
			if (Utils.soundEnabled)
				Assets.sound_turn.play(1);
		}
	}

	public static int getRow() {
		return ROW;
	}

	public static int getCol() {
		return COL;
	}

	public static void setTick(float minus) {
		if (Block.tick > 0.07)
			Block.tick -= minus;
	}
}
