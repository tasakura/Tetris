package com.badlogic.androidgames.framework.game;

import android.graphics.Color;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.Graphics.PixmapFormat;

public class LoadingScreen extends Screen {

	public LoadingScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();
		Assets.buckground01 = g.newPixmap("buckground01.png", PixmapFormat.RGB565);
		Assets.line = g.newPixmap("line01.png", PixmapFormat.ARGB4444);
		Assets.font_T80 = g.newPixmap("font_T80.png", PixmapFormat.ARGB4444);
		Assets.font_O80 = g.newPixmap("font_O80.png", PixmapFormat.ARGB4444);
		Assets.font_U80 = g.newPixmap("font_U80.png", PixmapFormat.ARGB4444);
		Assets.font_C80 = g.newPixmap("font_C80.png", PixmapFormat.ARGB4444);
		Assets.font_H80 = g.newPixmap("font_H80.png", PixmapFormat.ARGB4444);
		Assets.font_G80 = g.newPixmap("font_G80.png", PixmapFormat.ARGB4444);
		Assets.font_A80 = g.newPixmap("font_A80.png", PixmapFormat.ARGB4444);
		Assets.font_M80 = g.newPixmap("font_M80.png", PixmapFormat.ARGB4444);
		Assets.font_E80 = g.newPixmap("font_E80.png", PixmapFormat.ARGB4444);
		Assets.font_V80 = g.newPixmap("font_V80.png", PixmapFormat.ARGB4444);
		Assets.font_R80 = g.newPixmap("font_R80.png", PixmapFormat.ARGB4444);
		Assets.font_S45 = g.newPixmap("font_S45.png", PixmapFormat.ARGB4444);
		Assets.font_C45 = g.newPixmap("font_C45.png", PixmapFormat.ARGB4444);
		Assets.font_O45 = g.newPixmap("font_O45.png", PixmapFormat.ARGB4444);
		Assets.font_R45 = g.newPixmap("font_R45.png", PixmapFormat.ARGB4444);
		Assets.font_E45 = g.newPixmap("font_E45.png", PixmapFormat.ARGB4444);

		Assets.font_045 = g.newPixmap("font_045.png", PixmapFormat.ARGB4444);
		Assets.font_145 = g.newPixmap("font_145.png", PixmapFormat.ARGB4444);
		Assets.font_245 = g.newPixmap("font_245.png", PixmapFormat.ARGB4444);
		Assets.font_345 = g.newPixmap("font_345.png", PixmapFormat.ARGB4444);
		Assets.font_445 = g.newPixmap("font_445.png", PixmapFormat.ARGB4444);
		Assets.font_545 = g.newPixmap("font_545.png", PixmapFormat.ARGB4444);
		Assets.font_645 = g.newPixmap("font_645.png", PixmapFormat.ARGB4444);
		Assets.font_745 = g.newPixmap("font_745.png", PixmapFormat.ARGB4444);
		Assets.font_845 = g.newPixmap("font_845.png", PixmapFormat.ARGB4444);
		Assets.font_945 = g.newPixmap("font_945.png", PixmapFormat.ARGB4444);
		
		Assets.image_Turn = g.newPixmap("image_Turn.png", PixmapFormat.ARGB4444);
		Assets.image_Right = g.newPixmap("image_Right.png", PixmapFormat.ARGB4444);
		Assets.image_Left = g.newPixmap("image_Left.png", PixmapFormat.ARGB4444);
		Assets.image_Down = g.newPixmap("image_Down.png", PixmapFormat.ARGB4444);
		Assets.bg_gameover = g.newPixmap("bg_gameover.png", PixmapFormat.ARGB4444);
		
		Assets.block_Bar = g.newPixmap("block_Bar.png", PixmapFormat.ARGB4444);
		Assets.block_LShape = g.newPixmap("block_LShape.png", PixmapFormat.ARGB4444);
		Assets.block_RLShape = g.newPixmap("block_RLShape.png", PixmapFormat.ARGB4444);
		Assets.block_RZShape = g.newPixmap("block_RZShape.png", PixmapFormat.ARGB4444);
		Assets.block_Square = g.newPixmap("block_Square.png", PixmapFormat.ARGB4444);
		Assets.block_TShape = g.newPixmap("block_TShape.png", PixmapFormat.ARGB4444);
		Assets.block_ZShape = g.newPixmap("block_ZShape.png", PixmapFormat.ARGB4444);
		game.setScreen(new PlayScreen(game));
//		game.setScreen(new HighScoreRunking(game));
	}

	@Override
	public void present(float deltaTime) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

}
