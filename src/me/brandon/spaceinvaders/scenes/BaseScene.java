package me.brandon.spaceinvaders.scenes;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import me.brandon.spaceinvaders.utils.StaticData;

/**
 * A base scene handling some tedious setup required for any scene.
 * 
 * @author Brandon Gous
 *
 */
public class BaseScene extends Scene
{
	
	//The root group that this scene is contained in.
	protected Group rootGroup;
	
	//The canvas that is drawn on.
	protected Canvas canvas;

	/**
	 * Constructor
	 * 
	 * @param rootGroup The group in which this scene is contained.
	 */
	public BaseScene(Group rootGroup)
	{
		super(rootGroup);
		
		this.rootGroup = rootGroup;
		
		this.canvas = new Canvas(StaticData.SCREEN_WIDTH, StaticData.SCREEN_HEIGHT);
	
		this.rootGroup.getChildren().add(this.canvas);
		//Sets the background image of every scene
		this.canvas.getGraphicsContext2D().drawImage(new Image("me/brandon/spaceinvaders/resources/spaceinvaders-background.png"), 0, 0);
	}
}
