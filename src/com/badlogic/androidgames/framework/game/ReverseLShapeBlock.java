package com.badlogic.androidgames.framework.game;

import android.graphics.Color;
import android.graphics.Point;

public class ReverseLShapeBlock extends Block {
	public ReverseLShapeBlock(World world) {
		super(world);
		init();
		block[0][1] = 1;
		block[0][2] = 1;
		block[1][1] = 1;
		block[2][1] = 1;
		color = Color.YELLOW;
		pos = new Point(4, -4);
	}

}
