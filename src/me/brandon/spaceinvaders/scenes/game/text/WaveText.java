package me.brandon.spaceinvaders.scenes.game.text;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import me.brandon.spaceinvaders.utils.PlainText;

//Text
public class WaveText extends PlainText
{

	public WaveText(Group rootGroup, int wave)
	{
		super(rootGroup, Font.font("Impact", 20), Color.WHITE, "Wave: " + wave);
	}
}
