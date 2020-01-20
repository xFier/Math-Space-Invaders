package me.brandon.spaceinvaders.scenes.highscores;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import javafx.geometry.VPos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import me.brandon.spaceinvaders.Main;
import me.brandon.spaceinvaders.scenes.BaseScene;
import me.brandon.spaceinvaders.scenes.highscores.graphics.HighScoreTitle;
import me.brandon.spaceinvaders.scenes.highscores.graphics.HighScoresDataGraphics;
import me.brandon.spaceinvaders.scenes.highscores.graphics.PressEscapeToReturnText;
import me.brandon.spaceinvaders.scenes.menu.MenuScene;
import me.brandon.spaceinvaders.utils.StaticData;
import me.brandon.spaceinvaders.utils.TextGraphics;

/**
 * High Scores Scene
 * 
 * @author Brandon Gous
 *
 */
public class HighScoresScene extends BaseScene
{
	//Main application
	private Main application;
	
	//The list of highscores
	private ArrayList<HighScore> highScores;
	private TextGraphics highScoreTitle;
	private TextGraphics pressEscapeToReturnText;

	//Constructor
	public HighScoresScene(Main application)
	{
		super(application.getHighScoresGroup());
		
		this.application = application;
		
		//Initialises the list of high scores
		this.highScores = new ArrayList<HighScore>();
		
		//Loads the high scores
		loadHighScores(this.highScores);
		//Sorts the high scores into descending order
		bubbleSort(this.highScores);
		//Trims the high scores, leaves 20 at most
		trimScores(this.highScores, 20);
		
		this.highScoreTitle = new HighScoreTitle(this.canvas);
		this.highScoreTitle.drawText(StaticData.SCREEN_WIDTH / 2, 100, TextAlignment.CENTER, VPos.CENTER);
		this.pressEscapeToReturnText = new PressEscapeToReturnText(this.canvas);
		this.pressEscapeToReturnText.drawText(0, 0, TextAlignment.LEFT, VPos.TOP);
		
		new HighScoresDataGraphics(this.canvas, Color.WHITE, "Rank:").drawText(20, 200, TextAlignment.LEFT, VPos.BOTTOM);
		new HighScoresDataGraphics(this.canvas, Color.WHITE, "Name:").drawText(80, 200, TextAlignment.LEFT, VPos.BOTTOM);
		new HighScoresDataGraphics(this.canvas, Color.WHITE, "Wave:").drawText(300, 200, TextAlignment.LEFT, VPos.BOTTOM);
		new HighScoresDataGraphics(this.canvas, Color.WHITE, "Score:").drawText(380, 200, TextAlignment.LEFT, VPos.BOTTOM);
		new HighScoresDataGraphics(this.canvas, Color.WHITE, "Date:").drawText(520, 200, TextAlignment.LEFT, VPos.BOTTOM);
		
		//Loops through the high scores and places a row for each, with alternating colours for ease of reading
		for (int i = 0; i < this.highScores.size(); i++)
		{
			Color colour = (i % 2 == 0 ? Color.AQUA : Color.PALETURQUOISE);
			
			new HighScoresDataGraphics(this.canvas, colour, (i + 1) + ".").drawText(20, (200 + (30 * i)), TextAlignment.LEFT, VPos.TOP);
			new HighScoresDataGraphics(this.canvas, colour, this.highScores.get(i).getName()).drawText(80, (200 + (30 * i)), TextAlignment.LEFT, VPos.TOP);
			new HighScoresDataGraphics(this.canvas, colour, this.highScores.get(i).getWave() + "").drawText(300, (200 + (30 * i)), TextAlignment.LEFT, VPos.TOP);
			new HighScoresDataGraphics(this.canvas, colour, this.highScores.get(i).getScore() + "").drawText(380, (200 + (30 * i)), TextAlignment.LEFT, VPos.TOP);
			new HighScoresDataGraphics(this.canvas, colour, longToFormattedDate(this.highScores.get(i).getTime())).drawText(520, (200 + (30 * i)), TextAlignment.LEFT, VPos.TOP);
		}
		
		//Registers key press event method
		setOnKeyPressed(x -> handleControls(x));
	}
	
	//Loads high scores
	public void loadHighScores(ArrayList<HighScore> highScores)
	{
		//Reader with a buffer for data
		BufferedReader fileReader;
		//File name & location
		String fileName = System.getProperty("user.home") + File.separator + "spaceInvadersMathsScores.txt";
		
		String line = null;
		
		try
		{
			//Reads the file
			fileReader = new BufferedReader(new FileReader(fileName));
			
			//Does this while there are lines in the file
			while ((line = fileReader.readLine()) != null)
			{
				//Adds the high score from the file, converted to a HighScore object to the list
				highScores.add(parseHighScore(line));
			}
			
		}
		catch (IOException|NumberFormatException e)//Catches exceptions
		{
			e.printStackTrace();
		}
	}
	
	//Converts a string highScore into a HighScore Obect
	public HighScore parseHighScore(String line) throws NumberFormatException
	{
		String[] split = line.split("/", 4);//Splits up the line into 4 strings, splitting it every /, removing the /
		
		//Returns the HighScore Object
		return new HighScore(split[0], Integer.parseInt(split[1]), Integer.parseInt(split[2]), Long.parseLong(split[3]));
	}
	
	//Bubble sorts the highScores
	public void bubbleSort(ArrayList<HighScore> highScores)
	{
		boolean swapped = false;
		int lowerCheck = 0;
		
		//While the 2nd item to check is still in the list, indexing for lowerCheck starts at 0, so we obviously do less than highScores.size() which starts indexing at 1
		while(lowerCheck + 1 < highScores.size())
		{
			//Holds the first and second items here, so we can replace them in the highScores list without losing them to the garbage collector
			HighScore holdOne = highScores.get(lowerCheck);
			HighScore holdTwo = highScores.get(lowerCheck + 1);
			
			//If the first item is lower than the second item, we swap them. We want the highest stuff first in the list, descending order
			if (holdTwo.getScore() > holdOne.getScore())
			{
				//Swaps the two items around
				highScores.set(lowerCheck, holdTwo);
				highScores.set(lowerCheck + 1, holdOne);
				swapped = true;
			}
			//Increments lowerCheck so we can check the next two items
			lowerCheck++;
		}
		
		//If we've swapped something, we need to run through the list again, recursive functions here. Function calling itself
		if (swapped == true)
			bubbleSort(highScores);
		
		//If we make it here, the list has been sorted.
	}
	
	//Limits the number of scores in the highScores arrayList
	public void trimScores(ArrayList<HighScore> highScores, int max)
	{
		//Have to do this to avoid ConcurrentModificationExceptions
		for (Iterator<HighScore> i = highScores.iterator(); i.hasNext();)
		{
			HighScore highScore = i.next();
			
			if (!(highScores.indexOf(highScore) < max))//If the highScore is higher than the max number, we remove it.
				i.remove();
		}
	}
	
	//Formats a long timeStamp into a DD/MM/YYYY format
	public String longToFormattedDate(long time)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR);
	}
	
	//Handles returning to the main menu when ESCAPE is pressed.
	public void handleControls(KeyEvent e)
	{
		if (e.getCode() == KeyCode.ESCAPE)
		{
			this.application.resetGroups(this.rootGroup);
			this.application.getPrimaryStage().setScene(new MenuScene(this.application));
		}
	}
	

}
