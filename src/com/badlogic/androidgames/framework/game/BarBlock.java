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
        image = Assets.block_Bar;
        imageNo = Block.BAR;
        pos = new Point(4, -4);
        color = Color.rgb(255,0,255);
	}
}
