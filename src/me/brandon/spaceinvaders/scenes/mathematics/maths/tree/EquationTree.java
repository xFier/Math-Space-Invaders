package me.brandon.spaceinvaders.scenes.mathematics.maths.tree;

import java.util.ArrayList;
import java.util.Random;

import me.brandon.spaceinvaders.scenes.mathematics.maths.Operation;

public class EquationTree
{
	private OperationNode rootNode;
	private int maxDepth;
	private int maxOperations;
	private Random random;

	//Constructor
	public EquationTree(OperationNode rootNode, int wave)
	{
		this.rootNode = rootNode;
		this.maxDepth = (int) Math.min(Math.ceil(wave / 3.0), 5); // Max 5
		this.maxOperations = (int) Math.min(Math.ceil(wave / 2.0), 10);// Max 10
		this.random = new Random();
	}
	
	//Fills in the equation tree
	public void populateDepth()
	{
		//Root node is always a operation node, so we set it's left and right node, 50/50 chance of it being a NumberNode or OperationNode.
		//If it is a operationNode we pick a random value from Operations for the operator, subject to maxOperations, which limits some values
		this.rootNode.setLeftNode(this.random.nextBoolean() == true ? new NumberNode() : new OperationNode(Operation.values()[this.random.nextInt(this.maxOperations)]));
		this.rootNode.setRightNode(this.random.nextBoolean() == true ? new NumberNode() : new OperationNode(Operation.values()[this.random.nextInt(this.maxOperations)]));

		ArrayList<BaseEquationNode> nodesToPopulate = new ArrayList<>();
		
		//Initial two nodes need to be populated, at best they need numbers placed in them.
		nodesToPopulate.add(this.rootNode.getLeftNode());
		nodesToPopulate.add(this.rootNode.getRightNode());

		//Times we've looped, basically the tree depth
		int iterations = 0;

		do
		{
			// We would otherwise just add these directly to nodesToPopulate,
			// but to avoid concurrentModificationExceptions we need to use this
			// step between.
			ArrayList<BaseEquationNode> nodesToAdd = new ArrayList<>();

			for (BaseEquationNode node : nodesToPopulate)
			{
				if (node instanceof NumberNode)//Node is a number node, thus we know it just needs a number.
				{
					((NumberNode) node).setNumber(this.random.nextInt(10) + 1);//Sets the number between 1 and 10
				}
				else//Node is an operation node
				{
					if (iterations >= this.maxDepth - 2)// One less than
														// this.maxDepth -
					// 1 //Basically the very bottom
					// of the tree as defined by
					// maxDepth
					{
						((OperationNode) node).setLeftNode(new NumberNode());
						((OperationNode) node).setRightNode(new NumberNode());
					}
					else//Not the bottom of the tree, so we can pick a random node type, see above where we deal with the root node for how this works.
					{
						((OperationNode) node).setLeftNode(this.random.nextBoolean() == true ? new NumberNode() : new OperationNode(Operation.values()[this.random.nextInt(this.maxOperations)]));
						((OperationNode) node).setRightNode(this.random.nextBoolean() == true ? new NumberNode() : new OperationNode(Operation.values()[this.random.nextInt(this.maxOperations)]));
					}
					
					//Have to be operation nodes, so we add them to the list of stuff that's to be checked out next
					nodesToAdd.add(((OperationNode) node).getLeftNode());
					nodesToAdd.add(((OperationNode) node).getRightNode());
				}
			}
			
			//Checked all the nodes in here, so clear it
			nodesToPopulate.clear();
			
			//Add the new nodes to the list
			nodesToPopulate = nodesToAdd;
			
			//Increment iterations as we're one depth higher now
			iterations += 1;
		}
		while (!nodesToPopulate.isEmpty());//Until there's no nodes left to check out
	}

	public String parseEquation(OperationNode rootNode)
	{
		//%s is a replace-able variable when we format a string, so a place holder
		String initialString = "%s" + getOperationSymbol(rootNode.getOperation()) + "%s";
		
		//Variables that we replace %s with, sequentially.
		ArrayList<String> variablesToPlace = new ArrayList<>();
		ArrayList<BaseEquationNode> nodesToParse = new ArrayList<>();
		
		//Add the children to be parsed
		nodesToParse.add(rootNode.getLeftNode());
		nodesToParse.add(rootNode.getRightNode());

		do
		{
			ArrayList<BaseEquationNode> nodesToAdd = new ArrayList<>();

			for (BaseEquationNode node : nodesToParse)
			{
				if (node instanceof NumberNode)
				{//It's a number node we just get it's number and prepare to place that into the equation later
					variablesToPlace.add(((int) ((NumberNode) node).getNumber()) + "");
				}
				else
				{//It's an operation node, so we add a format to the variables list that will allow space for it's children nodes
					variablesToPlace.add("(%s" + getOperationSymbol(((OperationNode) node).getOperation()) + "%s)");
					
					//Prepares to check the nodes next loop
					nodesToAdd.add(((OperationNode) node).getLeftNode());
					nodesToAdd.add(((OperationNode) node).getRightNode());
				}
			}
			
			//Places all of the variables we have into the initialString
			initialString = String.format(initialString, variablesToPlace.toArray());
			
			//We've placed all the variables, so we can clear this in preperation for next cycle
			variablesToPlace.clear();
			//These nodes have been parsed, so we clear it
			nodesToParse.clear();
			//Next set of nodes to work with
			nodesToParse = nodesToAdd;
		}
		while (!nodesToParse.isEmpty());//Until we have parsed all the nodes

		//Any variables left over we need to place in the string
		initialString = String.format(initialString, variablesToPlace.toArray());

		return initialString;
	}
	
	//Converts the enum to their string counterpart
	public String getOperationSymbol(Operation operation)
	{
		switch (operation)
		{
			case ADD:
				return "+";
			case SUBTRACT:
				return "-";
			case DIVIDE:
				return "/";
			case MULTIPLY:
				return "*";
			case MOD:
				return "%";
			case POW:
				return "^";
			case SQRT:
				return "SQRT";
			case SIN:
				return "SIN";
			case COS:
				return "COS";
			case TAN:
				return "TAN";
			default:
				return null;
		}
	}

	//Getters & Setters
	public OperationNode getRootNode()
	{
		return rootNode;
	}

	public void setRootNode(OperationNode rootNode)
	{
		this.rootNode = rootNode;
	}

	public int getMaxDepth()
	{
		return maxDepth;
	}

	public int getMaxOperations()
	{
		return maxOperations;
	}
}
