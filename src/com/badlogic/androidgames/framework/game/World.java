package com.badlogic.androidgames.framework.game;

import java.util.Random;

import com.badlogic.androidgames.framework.Graphics;

import android.graphics.Color;
import android.util.Log;

public class World {
	static final int COL = 12; // 横
	static final int ROW = 16; // 縦
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
//				if (x == 0 || x == COL - 1) {
//					fields[y][x] = 1;
//				} else if (y == ROW - 1) {
//					fields[y][x] = 1;
//				} else {
					fields[y][x] = 0;
//				}
			}
		}
	}

	public void draw(Graphics g) {
		for (int y = 0; y < ROW; y++) {
			for (int x = 0; x < COL; x++) {
				if (fields[y][x] == 1) {
					g.drawRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE,	TILE_SIZE, Color.BLUE, 255);
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
}
