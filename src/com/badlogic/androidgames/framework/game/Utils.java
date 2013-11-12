package com.badlogic.androidgames.framework.game;

import android.content.ContentValues;

import com.badlogic.androidgames.framework.FileIO;
import com.badlogic.androidgames.framework.Pixmap;

public class Utils {

	private Pixmap[] list;
	public static boolean soundEnabled = true;

	public static void load(FileIO files) {
		String sql = "create table score_data("
				+ "_id integer primary key autoincrement,"
				+ "score integer default 0)";
		if (files.CreateDBandTable(sql))
			for (int i = 0; i < 5; i++) 
				addScore(files, 0);
	}

	public static boolean addScore(FileIO files, int score) {
		ContentValues val = new ContentValues();
		val.put("score", score);
		return files.writeFile(val);
	}

	public static String[][] readFile(FileIO files) {
		String[] columns = { "score" };
		String order = "score desc";
		return files.readFile(columns, null, null, order, 5);
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
		if (score == 0) {
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
			list[i] = w_list[count - 1];
		return list;
	}

}
