package com.badlogic.androidgames.framework.game;

import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.impl.AndroidGame;

public class MainActivity extends AndroidGame {

	public Screen getStartScreen() {
		return new LoadingScreen(this);
	}

}