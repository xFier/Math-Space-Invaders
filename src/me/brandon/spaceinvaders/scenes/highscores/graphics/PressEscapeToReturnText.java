package me.brandon.spaceinvaders.scenes.highscores.graphics;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import me.brandon.spaceinvaders.utils.TextGraphics;

//Graphics
public class PressEscapeToReturnText extends TextGraphics
{

	public PressEscapeToReturnText(Canvas canvas)
	{
		super(canvas, Color.WHITE, Font.font("Impact", 20), "Press ESCAPE to return to the menu.");
	}

}
