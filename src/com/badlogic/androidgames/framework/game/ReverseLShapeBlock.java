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
		imageNo = Block.REVERSE_L_SHAPE;
		color = Color.rgb(255,153,0);
	}

}
