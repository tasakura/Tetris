package com.badlogic.androidgames.framework.game;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.Graphics.PixmapFormat;
import com.badlogic.androidgames.framework.game.Utils;

public class LoadingScreen extends Screen {

	public LoadingScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();
		Assets.buckground01 = g.newPixmap("buckground01.png", PixmapFormat.RGB565);
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
		Assets.image_top = g.newPixmap("image_top.png", PixmapFormat.ARGB4444);
		Assets.image_score = g.newPixmap("image_score.png", PixmapFormat.ARGB4444);
		Assets.Retry_button = g.newPixmap("Retry_button.png", PixmapFormat.ARGB4444);
		Assets.Top_button = g.newPixmap("Top_button.png", PixmapFormat.ARGB4444);
		Assets.Resume_button = g.newPixmap("Resume_button.png", PixmapFormat.ARGB4444);
		Assets.tap_image = g.newPixmap("tap.png", PixmapFormat.ARGB4444);
		Assets.gameover_image = g.newPixmap("gameover.png", PixmapFormat.ARGB4444);
		Utils.load(game.getFileIO());
		game.setScreen(new TopMenuScreen(game));
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
