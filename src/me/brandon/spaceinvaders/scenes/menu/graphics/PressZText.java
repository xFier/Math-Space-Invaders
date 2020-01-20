package me.brandon.spaceinvaders.scenes.menu.graphics;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import me.brandon.spaceinvaders.utils.TextGraphics;

//Graphics object
public class PressZText extends TextGraphics
{
	
	public PressZText(Canvas canvas)
	{
		super(canvas, Color.LIME, Font.font("Arial", 20), "( Press Z )");
	}
}
