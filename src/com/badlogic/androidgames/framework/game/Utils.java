package com.badlogic.androidgames.framework.game;

import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Input.TouchEvent;

public class Utils {

	Pixmap[] list;

	public Utils() {
	}

	public Pixmap[] madeF_Touch() {
		list = new Pixmap[5];
		list[0] = Assets.font_T80;
		list[1] = Assets.font_O80;
		list[2] = Assets.font_U80;
		list[3] = Assets.font_C80;
		list[4] = Assets.font_H80;
		return list;
	}

	public Pixmap[] madeF_GameOver() {
		list = new Pixmap[8];
		list[0] = Assets.font_G80;
		list[1] = Assets.font_A80;
		list[2] = Assets.font_M80;
		list[3] = Assets.font_E80;
		list[4] = Assets.font_O80;
		list[5] = Assets.font_V80;
		list[6] = Assets.font_E80;
		list[7] = Assets.font_R80;
		return list;
	}

	public Pixmap[] madeF_Score() {
		list = new Pixmap[5];
		list[0] = Assets.font_S45;
		list[1] = Assets.font_C45;
		list[2] = Assets.font_O45;
		list[3] = Assets.font_R45;
		list[4] = Assets.font_E45;
		return list;
	}

	public Pixmap[] madeF_Control() {
		list = new Pixmap[4];
		list[0] = Assets.image_Left;
		list[1] = Assets.image_Turn;
		list[2] = Assets.image_Down;
		list[3] = Assets.image_Right;
		return list;
	}

	public Pixmap[] madeF_Number() {
		list = new Pixmap[10];
		list[0] = Assets.font_045;
		list[1] = Assets.font_145;
		list[2] = Assets.font_245;
		list[3] = Assets.font_345;
		list[4] = Assets.font_445;
		list[5] = Assets.font_545;
		list[6] = Assets.font_645;
		list[7] = Assets.font_745;
		list[8] = Assets.font_845;
		list[9] = Assets.font_945;
		return list;
	}

	public int[] getScorelist(int score) {
		if(score==0) {
			int[] list = new int[1]; 
			list[0] = 0;
			return list;
		}
		int count = 0;
		int[] w_list = new int[6];
		for (int i = 0; i < w_list.length; i++)
			w_list[i] = -1;

		for (count = 0; score > 0; count++) {
			w_list[count] = score % 10;
			score /= 10;
		}
		
		int list[] = new int[count];
		for (int i = 0; count > 0; i++, count--) 
			list[i] = w_list[count-1];
		return list;
	}
	
}
