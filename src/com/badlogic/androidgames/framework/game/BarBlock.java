package com.badlogic.androidgames.framework.game;

import android.graphics.Color;
import android.graphics.Point;

public class BarBlock extends Block {
	
	public BarBlock(World world) {
		super(world);
		init();
	    block[0][1] = 1;
        block[1][1] = 1;
        block[2][1] = 1;
        block[3][1] = 1;
        imageNo = Block.BAR;
        color = Color.rgb(255,0,255);
	}
}
