package com.badlogic.androidgames.framework.game;

import com.badlogic.androidgames.framework.Graphics;

import android.graphics.Color;
import android.graphics.Point;

public class World {
	static final int COL = 14; // 横
	static final int ROW = 15; // 縦
	static final int TILE_SIZE = 38; // マスのサイズ
	static final int SCORE_INCREMENT = 10;
	static final float TICK_INITIAL = 0.5f;
	static float tick = TICK_INITIAL; // 更新速度
	private int fields[][];
	private int field_images[][];
	private int[] blockColor_list;

	public int score = 0;
	public boolean gameOver = false;
	private float tickTime = 0;
	private double minus = 0.02;

	public World() {
		fields = new int[ROW][COL];
		field_images = new int[ROW][COL];
		FomatFields();
		init();
	}

	public void FomatFields() {
		for (int y = 0; y < ROW; y++) {
			for (int x = 0; x < COL; x++) {
				// 壁を作る
				if (x == 0 || x == COL - 1) {
					fields[y][x] = 1;
					field_images[y][x] = Block.WALL;
				} else if (y == ROW - 1) {
					fields[y][x] = 1;
					field_images[y][x] = Block.WALL;
				} else {
					fields[y][x] = 0;
				}
			}
		}
	}

	public void init() {
		blockColor_list = new int[7];
		blockColor_list[Block.REVERSE_L_SHAPE] = Color.rgb(255, 153, 0);
		blockColor_list[Block.REVERRSE_Z_SHAPE] = Color.rgb(255, 102, 0);
		blockColor_list[Block.SQUARE] = Color.rgb(153, 51, 0);
		blockColor_list[Block.L_SHAPE] = Color.rgb(0, 0, 255);
		blockColor_list[Block.T_SHAPE] = Color.rgb(102, 0, 153);
		blockColor_list[Block.Z_SHAPE] = Color.rgb(255, 255, 0);
		blockColor_list[Block.BAR] = Color.rgb(255, 0, 255);
	}

	public void draw(Graphics g) {
		int y_margin = 82;
		for (int y = 0; y < ROW; y++) {
			for (int x = 0; x < COL; x++) {
				// if (fields[y][x] == 1) { 壁ブロック未表示の場合
				if (fields[y][x] == 1 && !(y == ROW - 1) && !(x == 0)
						&& !(x == COL - 1)) {
					g.drawRect((x - 1) * TILE_SIZE + 5 + (x - 1), y * TILE_SIZE
							+ y_margin + (y + 1), TILE_SIZE, TILE_SIZE,
							blockColor_list[field_images[y][x]], 200);
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
						} else if (fields[0][newPos.x + j] == 1) {
							gameOver = true;
						}
					} else if (fields[newPos.y + i][newPos.x + j] == 1) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public boolean isGameover() {
		return gameOver;
	}

	// 落ち切ったブロックをボードに固定 pos=ブロックの位置 block=ブロック
	public void fixBlock(Point pos, int[][] block, int imageNo) {
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if (block[y][x] == 1) {
					if (pos.y + y < 0)
						continue;
					fields[pos.y + y][pos.x + x] = 1;
					field_images[pos.y + y][pos.x + x] = imageNo;
				}
			}
		}
	}

	// ブロックが揃った行の削除
	public void delteLine() {
		for (int y = 0; y < ROW - 1; y++) {
			int count = 0;
			for (int x = 1; x < COL - 1; x++) {
				if (fields[y][x] == 1) {
					count++;
				}
			}
			if (count == COL - 2) {
				for (int x = 1; x < COL - 1; x++) {
					fields[y][x] = 0;
					if (x == COL - 2) {
						score++;
						Block.setTick((float) minus);
					}
				}
				for (int ty = y; ty > 0; ty--) {
					for (int tx = 1; tx < COL - 1; tx++) {
						fields[ty][tx] = fields[ty - 1][tx];
						field_images[ty][tx] = field_images[ty - 1][tx];
					}
				}
			}
		}
	}

	// ブロックが積み上がっているか　最上行まで積み上がったらtrue
	public boolean isStacked() {
		for (int x = 1; x < COL - 1; x++) {
			if (fields[0][x] == 1) {
				return true;
			}
		}
		return false;
	}

	public int getScore() {
		return score;
	}

}
