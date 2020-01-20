package me.brandon.spaceinvaders.scenes.controls.graphics;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import me.brandon.spaceinvaders.utils.TextGraphics;

//Graphics
public class ControlsTitle extends TextGraphics
{

	public ControlsTitle(Canvas canvas)
	{
		super(canvas, Color.YELLOW, Font.font("Impact", 50), "CONTROLS");
	}

}
