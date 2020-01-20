package me.brandon.spaceinvaders.scenes.controls;

import javafx.geometry.VPos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.TextAlignment;
import me.brandon.spaceinvaders.Main;
import me.brandon.spaceinvaders.scenes.BaseScene;
import me.brandon.spaceinvaders.scenes.controls.graphics.ControlInformation;
import me.brandon.spaceinvaders.scenes.controls.graphics.ControlsTitle;
import me.brandon.spaceinvaders.scenes.controls.graphics.ReturnToMenu;
import me.brandon.spaceinvaders.scenes.menu.MenuScene;
import me.brandon.spaceinvaders.utils.StaticData;
import me.brandon.spaceinvaders.utils.TextGraphics;

/**
 * Controls Scene
 * 
 * @author Brandon Gous
 *
 */
public class ControlsScene extends BaseScene
{
	private Main application;
	
	private TextGraphics returnToMenu;
	private TextGraphics controlsTitle;
	private TextGraphics fireControlInformation;
	private TextGraphics moveLeftControlInformation;
	private TextGraphics moveRightControlInformation;
	private TextGraphics pauseControlInformation;
	
	public ControlsScene(Main application)
	{
		super(application.getControlsGroup());
		
		this.application = application;
		
		this.returnToMenu = new ReturnToMenu(this.canvas);
		this.returnToMenu.drawText(0, 0, TextAlignment.LEFT, VPos.TOP);
		this.controlsTitle = new ControlsTitle(this.canvas);
		this.controlsTitle.drawText(StaticData.SCREEN_WIDTH / 2, 100, TextAlignment.CENTER, VPos.CENTER);
		this.fireControlInformation = new ControlInformation(this.canvas, "Fire: SPACEBAR / S / W / UP ARROW / DOWN ARROW");
		this.moveLeftControlInformation = new ControlInformation(this.canvas, "Move left: A or LEFT ARROW");
		this.moveRightControlInformation = new ControlInformation(this.canvas, "Move right: D or RIGHT ARROW");
		this.fireControlInformation.drawText(50, 200, TextAlignment.LEFT, VPos.TOP);
		this.moveLeftControlInformation.drawText(50, 225, TextAlignment.LEFT, VPos.TOP);
		this.moveRightControlInformation.drawText(50, 250, TextAlignment.LEFT, VPos.TOP);
		this.pauseControlInformation = new ControlInformation(this.canvas, "Pause: ESCAPE");
		this.pauseControlInformation.drawText(50, 275, TextAlignment.LEFT, VPos.TOP);
	
		//Key press event
		setOnKeyPressed(x -> handleControls(x));
	}
	
	//Press escape to return to menu
	public void handleControls(KeyEvent e)
	{
		if (e.getCode() == KeyCode.ESCAPE)
		{			
			this.application.resetGroups(this.rootGroup);
			this.application.getPrimaryStage().setScene(new MenuScene(this.application));
		}
	}

}
