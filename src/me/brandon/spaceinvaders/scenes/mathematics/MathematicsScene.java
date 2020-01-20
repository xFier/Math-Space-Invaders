package me.brandon.spaceinvaders.scenes.mathematics;

import java.text.DecimalFormat;
import java.util.Random;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.TextAlignment;
import me.brandon.spaceinvaders.Main;
import me.brandon.spaceinvaders.scenes.BaseScene;
import me.brandon.spaceinvaders.scenes.game.GameScene;
import me.brandon.spaceinvaders.scenes.mathematics.graphics.EnterAnswerText;
import me.brandon.spaceinvaders.scenes.mathematics.graphics.EquationText;
import me.brandon.spaceinvaders.scenes.mathematics.graphics.MathsInformation;
import me.brandon.spaceinvaders.scenes.mathematics.graphics.MathsTitle;
import me.brandon.spaceinvaders.scenes.mathematics.maths.Operation;
import me.brandon.spaceinvaders.scenes.mathematics.maths.tree.EquationTree;
import me.brandon.spaceinvaders.scenes.mathematics.maths.tree.OperationNode;
import me.brandon.spaceinvaders.utils.StaticData;
import me.brandon.spaceinvaders.utils.TextGraphics;

/**
 * The Mathematics Question Scene
 * 
 * @author Brandon Gous
 *
 */
public class MathematicsScene extends BaseScene
{
	private Main application;
	private int wave;
	private GameScene gameScene;
	
	private TextGraphics mathsTitle;
	private TextGraphics mathsInformation;
	private TextGraphics mathsEquation;
	
	//Special canvas block used here so we can clear it independently of everything else we display, allowing for real-time updates of inputs.
	private Canvas answerCanvas;
	private TextGraphics enterAnswerText;
	private String answerText;
	
	//Equation tree object
	private EquationTree equationTree;
	
	/**
	 * Constructor
	 * 
	 * @param application The main application/game.
	 * @param wave The current game wave.
	 * @param gameScene The game scene, we take this so we still have hold of it and can move back to the exact same one when done on this scene.
	 */
	public MathematicsScene(Main application, int wave, GameScene gameScene)
	{
		super(application.getMathematicsGroup());
		
		this.application = application;
		this.wave = wave;
		this.gameScene = gameScene;
		
		//Defining the equation tree, the root node is null here initially and set after so we can get the maximum available operations for this point in the game.
		this.equationTree = new EquationTree(null, this.wave);
		this.equationTree.setRootNode(new OperationNode(Operation.values()[new Random().nextInt(this.equationTree.getMaxOperations())]));
		this.equationTree.populateDepth();
		
		this.mathsTitle = new MathsTitle(this.canvas);
		this.mathsTitle.drawText(StaticData.SCREEN_WIDTH / 2, 100, TextAlignment.CENTER, VPos.CENTER);
		
		this.mathsInformation = new MathsInformation(this.canvas);
		this.mathsInformation.drawText(StaticData.SCREEN_WIDTH / 2, 150, TextAlignment.CENTER, VPos.CENTER);
		
		this.mathsEquation = new EquationText(this.canvas, this.equationTree.parseEquation(this.equationTree.getRootNode()));
		this.mathsEquation.drawText(StaticData.SCREEN_WIDTH / 2, 200, TextAlignment.CENTER, VPos.CENTER);
		
		this.answerCanvas = new Canvas(StaticData.SCREEN_WIDTH, StaticData.SCREEN_HEIGHT);
		this.rootGroup.getChildren().add(this.answerCanvas);
		this.answerText = "";
		this.enterAnswerText = new EnterAnswerText(this.answerCanvas, this.answerText);
		
		//Registers the key press event method
		setOnKeyPressed(x -> handleInput(x));
	}
	
	/**
	 * Control Management
	 * 
	 * @param e Event
	 */
	public void handleInput(KeyEvent e)
	{
		KeyCode pressedKey = e.getCode();
		
		switch (pressedKey)
		{
			case DIGIT0:
			case DIGIT1:
			case DIGIT2:
			case DIGIT3:
			case DIGIT4:
			case DIGIT5:
			case DIGIT6:
			case DIGIT7:
			case DIGIT8:
			case DIGIT9:
			case NUMPAD0:
			case NUMPAD1:
			case NUMPAD2:
			case NUMPAD3:
			case NUMPAD4:
			case NUMPAD5:
			case NUMPAD6:
			case NUMPAD7:
			case NUMPAD8:
			case NUMPAD9://For any numeric input
				if (this.answerText.length() >= 20) //Character limit on the input
					break;
				//Sets the answerText to itself + the last character of the enum, in this case the actual digit pressed
				this.answerText = this.answerText + pressedKey.toString().substring(pressedKey.toString().length() - 1, pressedKey.toString().length());
				
				//Draws the new answerText
				drawAnswerText();
				
				break;
			case MINUS:
			case PERIOD://For - or .
				if (this.answerText.length() >= 20) //Character limit
					break;
				
				//Adds the pressed key to the answerText, we use a conditional here so we don't add "MINUS" or "PERIOD" to the text, but it's actual counterpart
				this.answerText = this.answerText + (pressedKey == KeyCode.MINUS ? "-" : ".");
				
				//Draws the new answerText
				drawAnswerText();
				
				break;
			case ENTER:
				if (this.answerText.length() == 0) //Stops an empty input
					break; 
				
				//Compares the user's answer in the format of a double rounded to 3 decimal places to the answer to the equation tree in the format of a double rounded to 3 decimal places
				if (Double.parseDouble(new DecimalFormat("#0.000").format(Double.parseDouble(this.answerText))) == Double.parseDouble(new DecimalFormat("#0.000").format(this.equationTree.getRootNode().getValue())))
				{
					//For all aliens
					for (int width = 0; width < StaticData.ALIEN_WIDTH; width++)
					{
						for (int height = 0; height < StaticData.ALIEN_HEIGHT; height++)
						{
							//If the alien is dead/non existent
							if (this.gameScene.getAliens()[width][height] == null)
								continue; //Skip
							
							//Moves the alien upwards
							this.gameScene.getAliens()[width][height].setY(this.gameScene.getAliens()[width][height].getY() - 40);
						}
					}
					
					//Increases the player's score
					this.gameScene.setScore(this.gameScene.getScore() + 50);
				}
				else//If answer is incorrect
				{
					//For all aliens
					for (int width = 0; width < StaticData.ALIEN_WIDTH; width++)
					{
						for (int height = 0; height < StaticData.ALIEN_HEIGHT; height++)
						{
							if (this.gameScene.getAliens()[width][height] == null) //If the alien is dead
								continue;//Skip
							
							//Move the alien downwards
							this.gameScene.getAliens()[width][height].setY(this.gameScene.getAliens()[width][height].getY() + 80);
						}
					}
				}
				//Resets other groups
				this.application.resetGroups(this.rootGroup);
				
				//Starts the gameLoop & Animations again, from the same point they stopped
				this.gameScene.getTimeline().play();
				
				//Sets the scene back to the game
				this.application.getPrimaryStage().setScene(this.gameScene);
				
				break;
			case BACK_SPACE:
				if (this.answerText.length() <= 0)//Stops you backspacing non-existant characters in the input
					break;
				
				//Removes the character at the end of the string, AKA backspacing
				this.answerText = this.answerText.substring(0, this.answerText.length() - 1);
			
				//Draws the updated answerText
				drawAnswerText();
				
				break;
			default:
				break;
			
		}
	}
	
	//Draws the answer text on the canvas
	public void drawAnswerText()
	{		
		this.answerCanvas.getGraphicsContext2D().clearRect(0, 0, StaticData.SCREEN_WIDTH, StaticData.SCREEN_HEIGHT);
		this.enterAnswerText.setTextContent(this.answerText);
		this.enterAnswerText.drawText(StaticData.SCREEN_WIDTH / 2, 300, TextAlignment.CENTER, VPos.CENTER);
	}
}