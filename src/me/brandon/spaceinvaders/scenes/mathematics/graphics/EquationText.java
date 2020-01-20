package me.brandon.spaceinvaders.scenes.mathematics.graphics;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import me.brandon.spaceinvaders.utils.TextGraphics;

//Graphics
public class EquationText extends TextGraphics
{

	public EquationText(Canvas canvas, String textContent)
	{
		super(canvas, Color.RED, Font.font("Impact", 50), textContent);
	}

}
