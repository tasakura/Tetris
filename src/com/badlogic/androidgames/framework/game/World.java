package com.badlogic.androidgames.framework.game;

import java.util.Random;

import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Pixmap;

import android.graphics.Point;
public class World {
	static final int COL = 14; // 横
	static final int ROW = 15; // 縦
	static final int TILE_SIZE = 40; // マスのサイズ
	static final int SCORE_INCREMENT = 10;
	static final float TICK_INITIAL = 0.5f;
	private int fields[][];
	private int field_images[][];
	// static final float TICK_DECREMENT = 0.05f;

	private boolean gameover = false;
	public boolean gameOver = false;
	public int score = 0;
	private Pixmap[] blockImage_list;

	Random random = new Random();
	float tickTime = 0;

	static float tick = TICK_INITIAL; // 更新速度

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
				} else if (y == ROW - 1) {
					fields[y][x] = 1;
				} else {
					fields[y][x] = 0;
				}
			}
		}
	}
	
	public void init() {
		blockImage_list = new Pixmap[7];
		blockImage_list[Block.REVERSE_L_SHAPE] = Assets.block_RLShape;
		blockImage_list[Block.REVERRSE_Z_SHAPE] = Assets.block_RZShape;
		blockImage_list[Block.SQUARE] = Assets.block_Square;
		blockImage_list[Block.L_SHAPE] = Assets.block_LShape;
		blockImage_list[Block.T_SHAPE] = Assets.block_TShape;
		blockImage_list[Block.Z_SHAPE] = Assets.block_ZShape;
		blockImage_list[Block.BAR] = Assets.block_Bar;
	}

	public void draw(Graphics g) {
		for (int y = 0; y < ROW; y++) {
			for (int x = 0; x < COL; x++) {
				if (fields[y][x] == 1 && !(y == ROW - 1)) {
					g.drawPixmap(blockImage_list[field_images[y][x]], (x - 1) * TILE_SIZE, y * TILE_SIZE);
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
							gameover = true;
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
		return gameover;
	}

	// 落ち切ったブロックをボードに固定 pos=ブロックの位置 block=ブロック
	public void fixBlock(Point pos, int[][] block, int imageNo) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (block[i][j] == 1) {
					if (pos.y + i < 0)
						continue;
					fields[pos.y + i][pos.x + j] = 1;
					field_images[pos.y+i][pos.x+j] = imageNo;
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
					if(x==COL-2) score++;
				}
				for (int ty = y; ty > 0; ty--) {
					for (int tx = 1; tx < COL - 1; tx++) {
						fields[ty][tx] = fields[ty - 1][tx];
						field_images[ty][tx] = field_images[ty-1][tx];
					}
				}
			}
		}
	}
	
	/**
     * ブロックが積み上がってるか
     * @return 最上行まで積み上がってたらtrue
     */
	  public boolean isStacked() {
	        for (int x=1; x<COL-1; x++) {
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
