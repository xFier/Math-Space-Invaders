package me.brandon.spaceinvaders.scenes.menu;

import javafx.geometry.VPos;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.TextAlignment;
import me.brandon.spaceinvaders.Main;
import me.brandon.spaceinvaders.scenes.BaseScene;
import me.brandon.spaceinvaders.scenes.controls.ControlsScene;
import me.brandon.spaceinvaders.scenes.game.GameScene;
import me.brandon.spaceinvaders.scenes.highscores.HighScoresScene;
import me.brandon.spaceinvaders.scenes.menu.graphics.ControlsText;
import me.brandon.spaceinvaders.scenes.menu.graphics.HighScoresText;
import me.brandon.spaceinvaders.scenes.menu.graphics.LowerGameTitle;
import me.brandon.spaceinvaders.scenes.menu.graphics.PlayText;
import me.brandon.spaceinvaders.scenes.menu.graphics.PressCText;
import me.brandon.spaceinvaders.scenes.menu.graphics.PressXText;
import me.brandon.spaceinvaders.scenes.menu.graphics.PressZText;
import me.brandon.spaceinvaders.scenes.menu.graphics.UpperGameTitle;
import me.brandon.spaceinvaders.utils.StaticData;
import me.brandon.spaceinvaders.utils.TextGraphics;

/**
 * The Menu Scene.
 * 
 * @author Brandon Gous
 *
 */
public class MenuScene extends BaseScene
{
	//The main application, useful to have as a point of access for anything else.
	private Main application;
	
	//Graphics objects that are displayed on this scene.
	private TextGraphics upperGameTitle;
	private TextGraphics lowerGameTitle;
	private TextGraphics playText;
	private TextGraphics pressXText;
	private TextGraphics highScoresText;
	private TextGraphics pressZText;
	private TextGraphics controlsText;
	private TextGraphics pressCText;

	/**
	 * Constructor
	 * 
	 * @param application The main application/game.
	 */
	public MenuScene(Main application)
	{
		//Calling the super class constructor
		super(application.getMenuGroup());
		
		//Variable definement
		this.application = application;

		this.upperGameTitle = new UpperGameTitle(this.canvas);
		this.lowerGameTitle = new LowerGameTitle(this.canvas);
		this.playText = new PlayText(this.canvas);
		this.pressXText = new PressXText(this.canvas);
		this.highScoresText = new HighScoresText(this.canvas);
		this.pressZText = new PressZText(this.canvas);
		this.controlsText = new ControlsText(this.canvas);
		this.pressCText = new PressCText(this.canvas);
		
		//Drawing all the displayed text/graphics
		this.upperGameTitle.drawText(StaticData.SCREEN_WIDTH / 2, 250, TextAlignment.CENTER, VPos.BOTTOM);
		this.lowerGameTitle.drawText(StaticData.SCREEN_WIDTH / 2, 350, TextAlignment.CENTER, VPos.BOTTOM);
		this.playText.drawText((StaticData.SCREEN_WIDTH / 2) - 10, 500, TextAlignment.RIGHT, VPos.CENTER);
		this.pressXText.drawText((StaticData.SCREEN_WIDTH / 2) + 10, 500, TextAlignment.LEFT, VPos.CENTER);
		this.highScoresText.drawText((StaticData.SCREEN_WIDTH / 2) - 10, 540, TextAlignment.RIGHT, VPos.CENTER);
		this.pressZText.drawText((StaticData.SCREEN_WIDTH / 2) + 10, 540, TextAlignment.LEFT, VPos.CENTER);
		this.controlsText.drawText((StaticData.SCREEN_WIDTH / 2) - 10, 580, TextAlignment.RIGHT, VPos.CENTER);
		this.pressCText.drawText((StaticData.SCREEN_WIDTH / 2) + 10, 580, TextAlignment.LEFT, VPos.CENTER);
		
		//Sets up the method that is called when a key is pressed.
		setOnKeyPressed(e -> handleMenuChoices(e));
	}
	
	/**
	 * Handles menu choices & controls.
	 * 
	 * @param e The event.
	 */
	public void handleMenuChoices(KeyEvent e)
	{
		if (e.getCode().toString().equals("X"))
		{
			//Resets the other groups just before we start using another group. For redundancy mostly, but that redundancy stops errors.
			this.application.resetGroups(this.rootGroup);
			
			//Sets the scene we're currently using.
			this.application.getPrimaryStage().setScene(new GameScene(this.application));
		}
		else if (e.getCode().toString().equals("Z"))
		{
			//Resets the other groups just before we start using another group. For redundancy mostly, but that redundancy stops errors.
			this.application.resetGroups(this.rootGroup);
			
			//Sets the scene we're currently using.
			this.application.getPrimaryStage().setScene(new HighScoresScene(this.application));
		}
		else if (e.getCode().toString().equals("C"))
		{
			//Resets the other groups just before we start using another group. For redundancy mostly, but that redundancy stops errors.
			this.application.resetGroups(this.rootGroup);
			
			//Sets the scene we're currently using.
			this.application.getPrimaryStage().setScene(new ControlsScene(this.application));
		}
	}
}
