package me.brandon.spaceinvaders.scenes.gameover.graphics;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import me.brandon.spaceinvaders.utils.TextGraphics;

//Graphics
public class NameText extends TextGraphics
{

	public NameText(Canvas canvas, String textContent)
	{
		super(canvas, Color.WHITE, Font.font("Impact", 20), textContent);
	}

}
