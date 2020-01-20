package me.brandon.spaceinvaders.scenes.game.missile;

import javafx.scene.Group;
import javafx.scene.paint.Color;

/**
 * Spaceship Missile
 * 
 * @author Brandon Gous
 *
 */
public class SpaceshipMissile extends Missile
{
	
	//Constructor
	public SpaceshipMissile(Group rootGroup)
	{
		super(10, 25, rootGroup);
		
		//Missile styling
		this.missile.setFill(Color.LIMEGREEN);
		this.missile.setStroke(Color.LIMEGREEN);
		this.missile.setStrokeWidth(0);
		this.missile.setArcHeight(10);
		this.missile.setArcWidth(10);
	}
	
	//Done to get around JavaFX's Parent-Child hierachy & no duplicate parents or children. Which apparently applies here.
	@Override
	public void spawn(double x, double y)
	{
		super.spawn(x, y);
		this.rootGroup.getChildren().add(this.missile);
	}

}
