package com.badlogic.androidgames.framework.game;

import java.util.Random;

import com.badlogic.androidgames.framework.Graphics;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

public class World {
	static final int COL = 14; // 横
	static final int ROW = 17; // 縦
	static final int TILE_SIZE = 40; // マスのサイズ
	static final int SCORE_INCREMENT = 10;
	static final float TICK_INITIAL = 0.5f;
	private int fields[][];
	// static final float TICK_DECREMENT = 0.05f;

	public boolean gameOver = false;
	public int score = 0;

	Random random = new Random();
	float tickTime = 0;

	static float tick = TICK_INITIAL; // 更新速度

	public World() {
		fields = new int[ROW][COL];
		FomatFields();
	}

	public void FomatFields() {
		for (int y = 0; y < ROW; y++) {
			for (int x = 0; x < COL; x++) {
				// 壁を作る
				if (x == 0 || x == COL - 1) {
					fields[y][x] = 1;
				} else if (y == ROW - 1) {
					fields[y][x] = 1;
				} else {
					fields[y][x] = 0;
				}
			}
		}
	}

	public void draw(Graphics g) {
		for (int y = 0; y < ROW; y++) {
			for (int x = 0; x < COL; x++) {
				if (fields[y][x] == 1 && !(y == ROW - 1)) {
					g.drawRect((x-1) * TILE_SIZE, y * TILE_SIZE, TILE_SIZE,
							TILE_SIZE, Color.BLUE, 255);
				}
			}
		}
	}

	public void update(float deltaTime) {
		if (gameOver)
			return;

		tickTime += deltaTime;

		while (tickTime > tick) {
			tickTime -= tick;
		}
	}

	// ブロックが移動できるか調べる
	public boolean isMovable(Point newPos, int[][] block) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (block[i][j] == 1) {
					if (newPos.y + i < 0) {
						if (newPos.x + j <= 0 || newPos.x + j >= COL - 1) {
							return false;
						}
					} else if (fields[newPos.y + i][newPos.x + j] == 1) {
						return false;
					}
				}
			}
		}
		return true;
	}

	// 落ち切ったブロックをボードに固定 pos=ブロックの位置 block=ブロック
	public void fixBlock(Point pos, int[][] block) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (block[i][j] == 1) {
					if (pos.y + i < 0)
						continue;
					fields[pos.y + i][pos.x + j] = 1;
				}
			}
		}
	}

	// ブロックが揃った行の削除
	public void delteLine() {
		for (int y = 0; y < ROW - 1; y++) {
			int count = 0;
			for (int x = 1; x < COL - 1; x++) {
				if (fields[y][x] == 1)
					count++;
			}
			if (count == COL - 2) {
				for (int x = 1; x < COL - 1; x++) {
					fields[y][x] = 0;
				}
				for (int ty = y; ty > 0; ty--) {
					for (int tx = 1; tx < COL - 1; tx++) {
						fields[ty][tx] = fields[ty - 1][tx];
					}
				}
			}
		}
	}

}
