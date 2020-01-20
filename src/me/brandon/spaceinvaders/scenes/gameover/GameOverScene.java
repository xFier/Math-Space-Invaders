package me.brandon.spaceinvaders.scenes.gameover;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.TextAlignment;
import me.brandon.spaceinvaders.Main;
import me.brandon.spaceinvaders.scenes.BaseScene;
import me.brandon.spaceinvaders.scenes.gameover.graphics.EnterNameText;
import me.brandon.spaceinvaders.scenes.gameover.graphics.GameOverText;
import me.brandon.spaceinvaders.scenes.gameover.graphics.NameText;
import me.brandon.spaceinvaders.scenes.gameover.graphics.PressEnterText;
import me.brandon.spaceinvaders.scenes.menu.MenuScene;
import me.brandon.spaceinvaders.utils.StaticData;
import me.brandon.spaceinvaders.utils.TextGraphics;

/**
 * Game Over Scene
 * 
 * @author Brandon Gous
 *
 */
public class GameOverScene extends BaseScene
{
	private Main application;
	private int wave;
	private int score;
	private long time;
	
	private TextGraphics gameOverText;
	private TextGraphics enterNameText;
	private TextGraphics nameText;
	
	//Extra canvas for the nameInput
	private Canvas nameTextCanvas;
	private String nameInput;
	private TextGraphics pressEnterText;

	//Constructor
	public GameOverScene(Main application, int wave, int score, long time)
	{
		super(application.getGameOverGroup());
		
		this.application = application;
		this.wave = wave;
		this.score = score;
		this.time = time;
		
		this.gameOverText = new GameOverText(this.canvas);
		this.enterNameText = new EnterNameText(this.canvas);
		this.nameTextCanvas = new Canvas(StaticData.SCREEN_WIDTH, StaticData.SCREEN_HEIGHT);
		this.rootGroup.getChildren().add(this.nameTextCanvas);
		this.nameInput = "";
		this.nameText = new NameText(this.nameTextCanvas, this.nameInput);
		this.pressEnterText = new PressEnterText(this.canvas);
		
		this.gameOverText.drawText(StaticData.SCREEN_WIDTH / 2, 100, TextAlignment.CENTER, VPos.CENTER);
		this.enterNameText.drawText(StaticData.SCREEN_WIDTH / 2, 200, TextAlignment.CENTER, VPos.CENTER);
		this.pressEnterText.drawText(StaticData.SCREEN_WIDTH / 2, 400, TextAlignment.CENTER, VPos.CENTER);
	
		//Initialize Key Press event method
		setOnKeyPressed(x -> handleNameInput(x));
	}
	
	//Key Presses
	public void handleNameInput(KeyEvent e)
	{
		KeyCode pressedKey = e.getCode();
		
		switch (pressedKey)
		{
			case A:
			case B:
			case C:
			case D:
			case E:
			case F:
			case G:
			case H:
			case I:
			case J:
			case K:
			case L:
			case M:
			case N:
			case O:
			case P:
			case Q:
			case R:
			case S:
			case T:
			case U:
			case V:
			case W:
			case X:
			case Y:
			case Z://Any alphabetical input
				if (this.nameInput.length() >= 20)//Character limit
					break;
				
				this.nameInput = this.nameInput + pressedKey.toString();//Adds the character pressed to the name input
				
				//Draws the new nameInput
				drawNameText();
				break;
			case NUMPAD0:
			case NUMPAD1:
			case NUMPAD2:
			case NUMPAD3:
			case NUMPAD4:
			case NUMPAD5:
			case NUMPAD6:
			case NUMPAD7:
			case NUMPAD8:
			case NUMPAD9:
			case DIGIT0:
			case DIGIT1:
			case DIGIT2:
			case DIGIT3:
			case DIGIT4:
			case DIGIT5:
			case DIGIT6:
			case DIGIT7:
			case DIGIT8:
			case DIGIT9://Any number press
				if (this.nameInput.length() >= 20)//Character limit
					break;
				
				//Adds the number pressed to the nameInput, only adds the last digit of the enum, so the number.
				this.nameInput = this.nameInput + pressedKey.toString().substring(pressedKey.toString().length() - 1, pressedKey.toString().length());
				
				//Draws the updated nameText
				drawNameText();
				break;
			case ENTER:
				if (this.nameInput.length() == 0)//Does not allow for an empty name
					break;
				
				//Saves the player's score
				saveScores(this.nameInput, this.wave, this.score, this.time);
				this.application.resetGroups(this.rootGroup);
				this.application.getPrimaryStage().setScene(new MenuScene(this.application));//Returns to main menu
				
				break;
			case BACK_SPACE:
				if (this.nameInput.length() <= 0)//Can't backspace nothing
					break;
				
				//Backspaces the last character
				this.nameInput = this.nameInput.substring(0, this.nameInput.length() - 1);
			
				//Re-draws the nameInput
				drawNameText();
				
				break;
			default:
				break;
		}
	}
	
	//Draws the nameText to the canvas
	public void drawNameText()
	{
		this.nameTextCanvas.getGraphicsContext2D().clearRect(0, 0, StaticData.SCREEN_WIDTH, StaticData.SCREEN_HEIGHT);
		this.nameText.setTextContent(this.nameInput);
		this.nameText.drawText(StaticData.SCREEN_WIDTH / 2, 300, TextAlignment.CENTER, VPos.CENTER);
	}
	
	//Saves the player's score
	public void saveScores(String name, int wave, int score, long time)
	{
		String fileName = System.getProperty("user.home") + File.separator + "spaceInvadersMathsScores.txt";//The file name & location
		BufferedWriter fileToWriteOn;
		
		try
		{
			fileToWriteOn = new BufferedWriter(new FileWriter(fileName, true));//The file we are writing to.
			fileToWriteOn.write(name + "/" + wave + "/" + score + "/" + time + System.getProperty("line.separator"));//Layout for a highscore
			if (fileToWriteOn != null)//If file exists and there isn't an error
			{
				fileToWriteOn.flush();//Flushes the stream
				fileToWriteOn.close();//Closes the file
			}
		}
		catch (IOException e)//Catches IOExceptions
		{
			e.printStackTrace();			
		}
		
	}

}
