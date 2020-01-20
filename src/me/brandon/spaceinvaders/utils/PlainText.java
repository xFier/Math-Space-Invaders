package me.brandon.spaceinvaders.utils;

import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * An object for allowing fast & more efficient text creation.
 * 
 * @author Brandon Gous
 *
 */
public class PlainText extends Text
{
	private Group rootGroup;
	private Font font;
	private Paint fillColour;
	private String textContent;
	
	/**
	 * Constructor
	 * 
	 * @param rootGroup The group of where this text is displayed.
	 * @param font The font.
	 * @param fillColour The fill colour.
	 * @param textContent The content of the text.
	 */
	public PlainText(Group rootGroup, Font font, Paint fillColour, String textContent)
	{
		super(textContent);
		this.rootGroup = rootGroup;
		this.font = font;
		this.fillColour = fillColour;
		this.textContent = textContent;
	}
	
	/**
	 * Draws the text.
	 * 
	 * @param x The X coordinate.
	 * @param y The Y coordinate.
	 * @param verticalTextAlignment The vertical text alignment
	 */
	public void drawText(double x, double y, VPos verticalTextAlignment)
	{
		setFont(this.font);
		setFill(fillColour);
		//We don't set the horizontal text alignment here because it doesn't work for single line text, for some reason...
		setTextOrigin(verticalTextAlignment);
		setX(x);
		setY(y);
	}

	//Getters & Setters
	public Group getRootGroup()
	{
		return rootGroup;
	}

	public void setRootGroup(Group rootGroup)
	{
		this.rootGroup = rootGroup;
	}

	public Paint getFillColour()
	{
		return fillColour;
	}

	public void setFillColour(Paint fillColour)
	{
		this.fillColour = fillColour;
	}

	public String getTextContent()
	{
		return textContent;
	}

	public void setTextContent(String textContent)
	{
		this.textContent = textContent;
	}
	

}
