package me.brandon.spaceinvaders.scenes.mathematics.graphics;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import me.brandon.spaceinvaders.utils.TextGraphics;

//Graphics
public class MathsInformation extends TextGraphics
{

	public MathsInformation(Canvas canvas)
	{
		super(canvas, Color.WHITE, Font.font("Impact", 20), "Solve the below question!");
	}

}
