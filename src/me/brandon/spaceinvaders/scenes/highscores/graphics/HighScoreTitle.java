package me.brandon.spaceinvaders.scenes.highscores.graphics;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import me.brandon.spaceinvaders.utils.TextGraphics;

//Graphics
public class HighScoreTitle extends TextGraphics
{

	public HighScoreTitle(Canvas canvas)
	{
		super(canvas, Color.LIME, Font.font("Impact", 50), "HIGH SCORES");
	}

}
