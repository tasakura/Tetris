package com.badlogic.androidgames.framework.game;

import android.graphics.Color;
import android.graphics.Point;

public class TShapeBlock extends Block {
	public TShapeBlock(World world) {
		super(world);
		init();
		block[1][1] = 1;
		block[2][0] = 1;
		block[2][1] = 1;
		block[2][2] = 1;
		color = Color.RED;
		pos = new Point(4, -4);
	}

}
