package me.brandon.spaceinvaders.scenes.mathematics.graphics;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import me.brandon.spaceinvaders.utils.TextGraphics;

//Graphics
public class EnterAnswerText extends TextGraphics
{

	public EnterAnswerText(Canvas canvas, String textContent)
	{
		super(canvas, Color.WHITE, Font.font("Impact", 30), textContent);
	}

}
