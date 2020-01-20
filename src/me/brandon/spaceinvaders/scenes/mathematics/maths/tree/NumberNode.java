package me.brandon.spaceinvaders.scenes.mathematics.maths.tree;

public class NumberNode implements BaseEquationNode
{
	//The number
	private double number;
	
	//Exists so we can define number later
	public NumberNode()
	{
		
	}
	
	//Constructor
	public NumberNode(double number)
	{
		this.number = number;
	}
	
	//Setter
	public void setNumber(double number)
	{
		this.number = number;
	}
	
	//Getter
	public double getNumber()
	{
		return this.number;
	}
	
	//Getter, requires overriding though.
	@Override
	public double getValue()
	{
		return this.number;
	}

}
