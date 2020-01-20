package me.brandon.spaceinvaders.scenes.gameover.graphics;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import me.brandon.spaceinvaders.utils.TextGraphics;

//Graphics
public class GameOverText extends TextGraphics
{

	public GameOverText(Canvas canvas)
	{
		super(canvas, Color.RED, Font.font("Impact", 50), "GAME OVER!");
	}

}
