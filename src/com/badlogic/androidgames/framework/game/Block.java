package com.badlogic.androidgames.framework.game;

import com.badlogic.androidgames.framework.Graphics;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

public class Block {
	public static final int ROW = 4;
	public static final int COL = 4;

	private static final int TILE_SIZE = 40;

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

	private static final float TICK_INITIAL = 0.5f;
	private static final float TICK_INITIAL_2 = 0.05f;
	private static float tick = TICK_INITIAL; // 更新速度
	private static float tick_2 = TICK_INITIAL_2; // 更新速度
	private float tickTime = 0;
	private float tickTime2 = 0;

	protected int color= Color.BLUE;
	protected int[][] block = new int[ROW][COL]; // ブロックの形を格納
	protected Point pos;
	private World world;

	public Block(World world) {
		this.world = world;
		init();
		pos = new Point(4, -4);
	}

	public void init() {
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				block[i][j] = 0;
			}
		}
	}

	public void draw(Graphics g) {
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				if (block[i][j] == 1) {
					g.drawRect(((pos.x - 1) + j) * TILE_SIZE, (pos.y + i)
							* TILE_SIZE, TILE_SIZE, TILE_SIZE, color);
//					g.drawCircle(((pos.x - 1) + j) * TILE_SIZE+13, (pos.y + i)
//					* TILE_SIZE, 15, Color.BLACK);
				}
			}
		}
	}

	public boolean move(int dir, float deltaTime) {
		Point newPos;
		tickTime2 += deltaTime;
		while (tickTime2 > tick_2) {
			tickTime2 -= tick_2;
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

		tickTime += deltaTime;
		while (tickTime > tick) {
			tickTime -= tick;
			newPos = new Point(pos.x, pos.y + 1);
			if (world.isMovable(newPos, block))
				pos = newPos;
			else {
				world.fixBlock(pos, block);
				return true;
			}
		}
		return false;
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
		if (world.isMovable(pos, turnedBlock))
			block = turnedBlock;
	}

	public static int getRow() {
		return ROW;
	}

	public static int getCol() {
		return COL;
	}
}
