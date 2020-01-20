package me.brandon.spaceinvaders.scenes.game.text;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import me.brandon.spaceinvaders.utils.PlainText;

//Text
public class LivesText extends PlainText
{

	public LivesText(Group rootGroup, int lives)
	{
		super(rootGroup, Font.font("Impact", 20), Color.WHITE, "Lives left: " + lives);
	}
}