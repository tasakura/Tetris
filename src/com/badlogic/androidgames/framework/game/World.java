package com.badlogic.androidgames.framework.game;

import java.util.Random;

public class World {
	static final int WORLD_WIDTH = 10;
	static final int WORLD_HEIGHT = 13;
	static final int SCORE_INCREMENT = 10;
	static final float TICK_INITIAL = 0.5f; // ���Ԋ��o
	static final float TICK_DECREMENT = 0.05f; // TICK_INITIAL�����

	public boolean gameOver = false;;
	public int score = 0;

	boolean fields[][] = new boolean[WORLD_WIDTH][WORLD_HEIGHT];
	Random random = new Random();
	float tickTime = 0;
	static float tick = TICK_INITIAL; // 更新速度

	public World() {
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
