package me.brandon.spaceinvaders.scenes.controls.graphics;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import me.brandon.spaceinvaders.utils.TextGraphics;

//Graphics
public class ControlInformation extends TextGraphics
{

	public ControlInformation(Canvas canvas, String textContent)
	{
		super(canvas, Color.WHITE, Font.font("Impact", 20), textContent);
	}

}
