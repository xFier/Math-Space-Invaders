package me.brandon.spaceinvaders.scenes.mathematics.maths.tree;

import me.brandon.spaceinvaders.scenes.mathematics.maths.Operation;

//An operation node, implementing the base node object.
public class OperationNode implements BaseEquationNode
{
	//The operation
	private Operation operation;
	
	//The left node
	private BaseEquationNode leftNode;
	
	//The right node
	private BaseEquationNode rightNode;
	
	//Constructor
	public OperationNode(Operation operation)
	{
		this(operation, null, null);
	}
	
	//Constructor
	public OperationNode(Operation operation, BaseEquationNode leftNode, BaseEquationNode rightNode)
	{
		this.operation = operation;
		this.leftNode = leftNode;
		this.rightNode = rightNode;
	}
	
	//Returns true if the node's children are null
	public boolean areChildrenNull()
	{
		if (this.leftNode == null && this.rightNode == null)
			return true;
		
		return false;
	}
	
	//Getters & Setters
	public Operation getOperation()
	{
		return operation;
	}

	public void setOperation(Operation operation)
	{
		this.operation = operation;
	}

	public BaseEquationNode getLeftNode()
	{
		return leftNode;
	}

	public void setLeftNode(BaseEquationNode leftNode)
	{
		this.leftNode = leftNode;
	}

	public BaseEquationNode getRightNode()
	{
		return rightNode;
	}

	public void setRightNode(BaseEquationNode rightNode)
	{
		this.rightNode = rightNode;
	}

	//Returns the value of the children nodes, OOP recursive function.
	@Override
	public double getValue()
	{
		double leftValue = this.leftNode.getValue();
		double rightValue = this.rightNode.getValue();
		
		switch (this.operation)
		{
			case ADD:
				return leftValue + rightValue;
			case SUBTRACT:
				return leftValue - rightValue;
			case DIVIDE:
				return leftValue / rightValue;
			case MULTIPLY:
				return leftValue * rightValue;
			case POW:
				return Math.pow(leftValue, rightValue);
			case MOD:
				return leftValue % rightValue;
			case SQRT:
				return leftValue * Math.sqrt(rightValue);
			case SIN:
				return leftValue * Math.sin(rightValue);
			case COS:
				return leftValue * Math.cos(rightValue);
			case TAN:				
				return leftValue * Math.tan(rightValue);
			default:
				return Double.NaN;
		}
	}
}
