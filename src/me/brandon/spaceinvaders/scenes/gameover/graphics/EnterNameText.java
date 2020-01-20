package me.brandon.spaceinvaders.scenes.gameover.graphics;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import me.brandon.spaceinvaders.utils.TextGraphics;

//Graphics
public class EnterNameText extends TextGraphics
{

	public EnterNameText(Canvas canvas)
	{
		super(canvas, Color.WHITE, Font.font("Impact", 30), "Please type your name:");
	}

}
