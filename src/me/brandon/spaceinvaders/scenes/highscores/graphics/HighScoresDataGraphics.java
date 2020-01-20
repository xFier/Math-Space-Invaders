package me.brandon.spaceinvaders.scenes.highscores.graphics;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import me.brandon.spaceinvaders.utils.TextGraphics;

//Graphics
public class HighScoresDataGraphics extends TextGraphics
{

	public HighScoresDataGraphics(Canvas canvas, Paint colour, String textContent)
	{
		super(canvas, colour, Font.font("Impact", 20), textContent);
	}

}
