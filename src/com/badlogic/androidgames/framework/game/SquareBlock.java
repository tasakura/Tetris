package com.badlogic.androidgames.framework.game;

import android.graphics.Color;
import android.graphics.Point;

public class SquareBlock extends Block {
	public SquareBlock(World world) {
		super(world);
		init();
		block[1][1] = 1;
		block[1][2] = 1;
		block[2][1] = 1;
		block[2][2] = 1;
		color = Color.GREEN;
		pos = new Point(4, -4);
	}

}
