package com.badlogic.androidgames.framework.game;

import android.graphics.Color;
import android.graphics.Point;

public class ZShapeBlock extends Block {
	public ZShapeBlock(World world) {
		super(world);
		init();
	    block[0][2] = 1;
        block[1][2] = 1;
        block[1][1] = 1;
        block[2][1] = 1;
        imageNo = Block.Z_SHAPE;
        color = Color.rgb(255,255,0);
	}

}
