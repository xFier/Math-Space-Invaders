package me.brandon.spaceinvaders;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.brandon.spaceinvaders.scenes.menu.MenuScene;
import me.brandon.spaceinvaders.utils.StaticData;

public class Main extends Application
{
	/**
	 * The group that contains all of the Menu objects & related data.
	 */
	private Group menuGroup;

	/**
	 * The group that contains all of the Menu objects & related data.
	 */
	private Group gameGroup;
	
	/**
	 * The group that contains all of the Game Over Page objects & related data.
	 */
	private Group gameOverGroup;
	
	/**
	 * The group that contains all of the High Scores Page objects & related data.
	 */
	private Group highScoresGroup;
	
	/**
	 * The group that contains all of the Mathematics Page objects & related data.
	 */
	private Group mathematicsGroup;
	
	/**
	 * The group that contains all of the Controls Page objects & related data.
	 */
	private Group controlsGroup;

	/**
	 * The primary stage, or the main game window.
	 */
	private Stage primaryStage;

	/**
	 * Default java method.
	 * 
	 * @param args 
	 */
	public static void main(String[] args)
	{
		//Launches the javaFX game, a JavaFX requirement.
		launch(args);
	}

	// Only abstract method that requires overriding in Application.
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		//Setting up all of the group variables.
		this.menuGroup = new Group();
		this.gameGroup = new Group();
		this.gameOverGroup = new Group();
		this.highScoresGroup = new Group();
		this.mathematicsGroup = new Group();
		this.controlsGroup = new Group();
		
		//Initializing the primary stage.
		this.primaryStage = primaryStage;
		//Configuring some values relating to the primary stage. Including game title.
		configureStage(primaryStage, "Space Invaders - Mathematics Edition");

		// Creates the MenuScene and places it in the rootGroup.
		Scene menuScene = new MenuScene(this);
		
		// Sets the current scene & shows the stage.
		primaryStage.setScene(menuScene);
		primaryStage.show();
	}

	/**
	 * Basic stage configuration including, title.
	 * 
	 * @param stage The stage to configure.
	 * @param stageTitle The title of the stage.
	 */
	public void configureStage(Stage stage, String stageTitle)
	{
		stage.setTitle(stageTitle);
		stage.setWidth(StaticData.SCREEN_WIDTH);
		stage.setHeight(StaticData.SCREEN_HEIGHT);
		stage.setResizable(false);
	}
	
	/**
	 * Resets all groups with a lone exception, when creating new objects of each scene (which goes into the group) javaFX doesn't allow for a group to have duplicate children, also prevents memory leaks by allowing the Java Garbage Collector to clean up the data.
	 * 
	 * @param exception The exception, or the active group.
	 */
	public void resetGroups(Group exception)
	{		
		if (exception != this.menuGroup)
			this.menuGroup = new Group();
		
		if (exception != this.gameGroup)
			this.gameGroup = new Group();
		
		if (exception != this.gameOverGroup)	
			this.gameOverGroup = new Group();
		
		if (exception != this.highScoresGroup)
			this.highScoresGroup = new Group();
		
		if (exception != this.mathematicsGroup)
			this.mathematicsGroup = new Group();
		
		if (exception != this.controlsGroup)
			this.controlsGroup = new Group();
	}

	//General getters & setters
	public Stage getPrimaryStage()
	{
		return primaryStage;
	}
	
	public void setPrimaryStage(Stage primaryStage)
	{
		this.primaryStage = primaryStage;
	}

	public Group getMenuGroup()
	{
		return this.menuGroup;
	}

	public void setMenuGroup(Group menuGroup)
	{
		this.menuGroup = menuGroup;
	}

	public Group getGameGroup()
	{
		return this.gameGroup;
	}

	public void setGameGroup(Group gameGroup)
	{
		this.gameGroup = gameGroup;
	}
	
	public Group getGameOverGroup()
	{
		return this.gameOverGroup;
	}

	public void setGameOverGroup(Group gameOverGroup)
	{
		this.gameOverGroup = gameOverGroup;
	}

	public Group getHighScoresGroup()
	{
		return this.highScoresGroup;
	}

	public void setHighScoresGroup(Group highScoresGroup)
	{
		this.highScoresGroup = highScoresGroup;
	}

	public Group getMathematicsGroup()
	{
		return this.mathematicsGroup;
	}

	public void setMathematicsGroup(Group mathematicsGroup)
	{
		this.mathematicsGroup = mathematicsGroup;
	}

	public Group getControlsGroup()
	{
		return this.controlsGroup;
	}

	public void setControlsGroup(Group controlsGroup)
	{
		this.controlsGroup = controlsGroup;
	}
}
