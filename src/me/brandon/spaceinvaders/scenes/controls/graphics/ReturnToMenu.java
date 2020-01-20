package me.brandon.spaceinvaders.scenes.controls.graphics;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import me.brandon.spaceinvaders.utils.TextGraphics;

//Graphics
public class ReturnToMenu extends TextGraphics
{

	public ReturnToMenu(Canvas canvas)
	{
		super(canvas, Color.WHITE, Font.font("Impact", 20), "Press ESCAPE to return to the menu.");
	}

}
