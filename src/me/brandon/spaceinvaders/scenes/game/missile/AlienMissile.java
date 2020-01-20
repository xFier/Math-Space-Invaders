package me.brandon.spaceinvaders.scenes.game.missile;

import javafx.scene.Group;
import javafx.scene.paint.Color;

/**
 * Alien Missile
 * 
 * @author Brandon Gous
 *
 */
public class AlienMissile extends Missile
{
	//Constructor
	public AlienMissile(Group rootGroup)
	{
		super(2, 40, rootGroup);
		
		//Missile styling
		this.missile.setFill(Color.RED);
		this.missile.setStroke(Color.RED);
		this.missile.setStrokeWidth(0);
		this.missile.setArcHeight(30);
		this.missile.setArcWidth(10);
	}

	//Done to get around JavaFX's Parent-Child hierachy & no duplicate parents or children. Which apprently applies here.
	@Override
	public void spawn(double x, double y)
	{
		super.spawn(x, y);
		this.rootGroup.getChildren().add(this.missile);
	}
}
