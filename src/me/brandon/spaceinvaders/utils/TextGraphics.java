package me.brandon.spaceinvaders.utils;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * An object used for the efficient creation of text graphics on a canvas.
 * 
 * @author Brandon Gous
 *
 */
public class TextGraphics
{
	private Canvas canvas;
	private Paint fillColour;
	private Font font;
	private String textContent;
	private GraphicsContext graphicsContext;
	
	/**
	 * Constucts the object.
	 * 
	 * @param canvas The canvas to be drawn on.
	 * @param fillColour The fill colour of the text.
	 * @param font The font of the text.
	 * @param textContent The content of the text.
	 */
	public TextGraphics(Canvas canvas, Paint fillColour, Font font, String textContent)
	{
		this.canvas = canvas;
		this.fillColour = fillColour;
		this.font = font;
		this.textContent = textContent;
	}
	
	/**
	 * Draws the text on the defined canvas.
	 * Sets some data regarding drawn text.
	 * 
	 * @param x The X coordinate.
	 * @param y The Y coordinate.
	 * @param horizontalTextAlignment The horizontal text alignment.
	 * @param veritcalTextAlignment The vertical text alignment.
	 */
	public void drawText(double x, double y, TextAlignment horizontalTextAlignment, VPos veritcalTextAlignment)
	{
		this.graphicsContext = this.canvas.getGraphicsContext2D();
		
		
		this.graphicsContext.setFill(this.fillColour);
		this.graphicsContext.setLineWidth(0);
		this.graphicsContext.setFont(this.font);
		this.graphicsContext.setTextAlign(horizontalTextAlignment);
		this.graphicsContext.setTextBaseline(veritcalTextAlignment);
		
		this.graphicsContext.fillText(this.textContent, x, y);
		
	}

	// Getters & Setters below.
	public Canvas getCanvas()
	{
		return canvas;
	}

	public GraphicsContext getGraphicsContext()
	{
		return graphicsContext;
	}

	public void setGraphicsContext(GraphicsContext graphicsContext)
	{
		this.graphicsContext = graphicsContext;
	}

	public void setCanvas(Canvas canvas)
	{
		this.canvas = canvas;
	}

	public Paint getFillColour()
	{
		return fillColour;
	}

	public void setFillColour(Paint fillColour)
	{
		this.fillColour = fillColour;
	}

	public Font getFont()
	{
		return font;
	}

	public void setFont(Font font)
	{
		this.font = font;
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
