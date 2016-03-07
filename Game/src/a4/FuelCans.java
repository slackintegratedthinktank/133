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
 * Class: 	FuelCans
 * Purpose: Fuel Cans refuel our car by running into them. they have a size which determines how much fuel is in them.
 * 
 * 
 *****************************************************************************************************************************/
class FuelCans implements IDrawable, ISelectable, ICollide, Fixed
{
	private float size; //corresponds to the amount of fuel this can contains
	private boolean selected;
	private AffineTransform myRotation, myTranslation, myScaling;
	private Color color;//has-a color
	
	//constructor
	public FuelCans(float x, float y)
	{
		myRotation = new AffineTransform();
		myTranslation = new AffineTransform();
		myScaling = new AffineTransform();
		
		myTranslation.translate(x, y);
		myScaling.scale(1, -1);
		color = Color.BLACK;
		size = 30;
		selected = false;
	}
	
	//accessors and mutators
	public float getSize()
	{
		return size;
	}
	public void setSize(float r)
	{
		size = r;
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
		return("FuelCans: "+ super.toString() +"size=" + (int)size);
	}

	//here we draw our fuelcans, we also have the ability to place cross hatches on the selected ones.
	public void draw(Graphics2D g) 
	{
		AffineTransform backup = g.getTransform();

		g.transform(myTranslation);
		g.transform(myRotation);
		g.transform(myScaling);
		g.setColor(getColor());
		g.fillRect(((int)(0 - (size/2))), ((int)(0 - size/2)), ((int)size), ((int)size));
		g.setColor(Color.BLACK);
		g.drawString(String.valueOf(size), ((int)(0 - size/4)), 0);
		if(selected)
		{
			g.setColor(Color.black);
			g.drawLine((int)(0 - size/2), 0, (int)(0 + size/2), 0);
			g.drawLine(0, (int)(0 - size/2), 0, (int)(0 + size/2));
		}
		g.setTransform(backup);
	}

	//here we check if the point the user clicks is inside the fuelcan.
	public boolean contains(Point2D worldPoint) 
	{
		Point2D p = null;
		try 
		{
			p = myTranslation.createInverse().transform(worldPoint, null);
		} 
		catch (NoninvertibleTransformException e) {}
		
		if(p.getX() < 0-size/2 || p.getX() > 0+size/2 || p.getY() < 0 -size/2 || p.getY() > 0+size/2)
			return false;
		return true;
	}
	
	//unused collision since this is not moving.
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
