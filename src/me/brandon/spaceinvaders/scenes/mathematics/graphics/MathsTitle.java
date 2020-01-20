package me.brandon.spaceinvaders.scenes.mathematics.graphics;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import me.brandon.spaceinvaders.utils.TextGraphics;

//Graphics
public class MathsTitle extends TextGraphics
{

	public MathsTitle(Canvas canvas)
	{
		super(canvas, Color.WHITE, Font.font("Impact", 50), "Woah! Time for a question!");
	}

}
