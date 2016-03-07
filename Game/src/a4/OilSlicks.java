package a4;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	OilSlicks
 * Purpose: The OilSlicks object has attributes of width and length. it impacts the cars ability to turn.
 * 
 *****************************************************************************************************************************/
class OilSlicks implements IDrawable, ISelectable, ICollide, Fixed
{
	//declare our local varibles
	private float width;
	private float length;
	private boolean selected;
	private AffineTransform myRotation, myTranslation, myScaling;
	private Color color;//has-a color
	
	//constructors
	OilSlicks(float x, float y)
	{
		width = 0;
		length = 0;
		selected = false;
		myRotation = new AffineTransform();
		myTranslation = new AffineTransform();
		myScaling = new AffineTransform();	
		
		myTranslation.translate(x, y);
		myScaling.scale(1,-1);
		color = Color.BLACK;
	}
	
	//accessors and mutators
	public float getWidth()
	{
		return width;
	}
	
	public void setWidth(float w)
	{
		width = w;
	}
	
	public float getLength()
	{
		return length;
	}
	
	public void setLength(float l)
	{
		length = l;
	}
	public void setSelected(boolean yes) 
	{
		selected = yes;
	}

	public boolean isSelected() 
	{
		return selected;
	}
	//override the Object Class's toString() method and utilized the parents toString method.
	public String toString()
	{
		return("OilSlick: "+ super.toString() +"width=" + (int)width + " length=" + (int)length);
	}

	//here we draw our oilslick based on its size and location.
	public void draw(Graphics2D g) 
	{
		AffineTransform backup = g.getTransform();
		g.transform(myTranslation);
		g.transform(myRotation);
		g.transform(myScaling);
		g.setColor(this.getColor());
		g.fillOval(((int)(0 - width/2)), ((int)(0 - length/2)), ((int)width), ((int)length));
		if(selected)
		{
			g.setColor(Color.black);
			g.drawLine((int)(0 - width/2), ((int)0), (int)(0 + width/2), ((int)0));
			g.drawLine((int)0, (int)(0 - length/2), (int)(0), (int)(0 + length/2));
		}
		g.setTransform(backup);
	}

	//here we check to see if the user wanted to select the item.
	public boolean contains(Point2D worldPoint) 
	{
		Point2D p = null;
		try 
		{
			p = myTranslation.createInverse().transform(worldPoint, null);
		} 
		catch (NoninvertibleTransformException e) {}
		
		float difx = (float) Math.abs(p.getX());
		float dify = (float) Math.abs(p.getY());		
		float radius = (float) Math.sqrt(difx*difx + dify*dify);
		
		
		float thisx = width/2;
		float thisy = length/2;
		float rad = (float) Math.sqrt((thisy*thisy) + (thisx*thisx));
		
		if(rad > radius)
			return true;
		return false;
		
	}


	public boolean collidesWith(ICollide other) 
	{
		return false;
	}
	public void handleCollision(ICollide other) {}

	public AffineTransform getTranslation() 
	{
		return myTranslation;
	}

	public AffineTransform getRotation() 
	{
		return myRotation;
	}

	public AffineTransform getScale() 
	{
		return myScaling;
	}

	public Color getColor() 
	{
		return color;
	}

	public void setColor(float r, float g, float b) 
	{
		color = new Color(r,g,b);
		
	}

}
