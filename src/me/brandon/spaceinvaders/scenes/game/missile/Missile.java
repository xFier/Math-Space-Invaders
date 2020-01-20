package me.brandon.spaceinvaders.scenes.game.missile;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

/**
 * Missile
 * 
 * @author Brandon Gous
 *
 */
public class Missile
{
	protected boolean active;
	protected Rectangle missile;
	protected Group rootGroup;
	
	//Constructor
	public Missile(double width, double height, Group rootGroup)
	{
		this.missile = new Rectangle(width, height);
		this.rootGroup = rootGroup;
	}
	
	//Spawn missile at location
	public void spawn(double x, double y)
	{
		this.missile.setX(x);
		this.missile.setY(y);
		this.active = true;
	}
	
	//Despawn missile
	public void despawn()
	{
		this.rootGroup.getChildren().remove(this.missile);
		this.active = false;
	}
	
	//Getters & Setters
	public Rectangle getMissile()
	{
		return this.missile;
	}
	
	public boolean isActive()
	{
		return this.active;
	}
	

}
