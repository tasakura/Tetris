package com.badlogic.androidgames.framework.game;

import android.graphics.Point;

public class LShapeBlock extends Block {
	public LShapeBlock(World world) {
		super(world);
		init();
		block[0][1] = 1;
		block[1][1] = 1;
		block[2][1] = 1;
		block[2][2] = 1;

		pos = new Point(4, -4);
	}

}
