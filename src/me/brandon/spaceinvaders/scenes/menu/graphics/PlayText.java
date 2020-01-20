package me.brandon.spaceinvaders.scenes.menu.graphics;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import me.brandon.spaceinvaders.utils.TextGraphics;

//Graphics object
public class PlayText extends TextGraphics
{

	public PlayText(Canvas canvas)
	{
		super(canvas, Color.RED, Font.font("Arial", 40), "PLAY");
	}

}
