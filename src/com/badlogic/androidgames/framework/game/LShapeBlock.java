package com.badlogic.androidgames.framework.game;

import android.graphics.Color;
import android.graphics.Point;

public class LShapeBlock extends Block {
	public LShapeBlock(World world) {
		super(world);
		init();
		block[0][1] = 1;
		block[1][1] = 1;
		block[2][1] = 1;
		block[2][2] = 1;
		image = Assets.block_LShape;
		imageNo = Block.L_SHAPE;
		pos = new Point(4, -4);
		color = Color.rgb(0,0,255);
	}

}
