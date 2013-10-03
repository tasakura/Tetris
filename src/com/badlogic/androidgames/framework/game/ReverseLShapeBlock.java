package com.badlogic.androidgames.framework.game;

import android.graphics.Point;

public class ReverseLShapeBlock extends Block {
	public ReverseLShapeBlock(World world) {
		super(world);
		init();
		block[2][0] = 1;
		block[1][2] = 1;
		block[2][1] = 1;
		block[2][2] = 1;

		pos = new Point(4, -4);
	}

}
