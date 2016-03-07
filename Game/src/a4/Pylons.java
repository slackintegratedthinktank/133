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
 * Class: 	Pylons
 * Purpose: Pylons are our games waypoints. They have attributes radius and sequenceNumber. Each pylon object makes up a 
 * 			waypoint on the track. 
 * 
 *****************************************************************************************************************************/
class Pylons implements IDrawable, ISelectable, ICollide, Fixed
{
	//declare our private variables
	private float radius;
	private float sequenceNumber;
	private boolean selected;
	private Color color;
	private AffineTransform myRotation, myTranslation, myScaling;
	
	//constructors
	public Pylons(float x, float y, float s)
	{
		color = Color.BLACK;
		radius = 20;
		sequenceNumber = s;
		selected = false;
		myRotation = new AffineTransform();
		myTranslation = new AffineTransform();
		myScaling = new AffineTransform();
		myTranslation.translate(x, y);
		myScaling.scale(1, -1);
		
	}
	
	//Accessors and Mutators
	public float getRadius()
	{
		return radius;
	}
	public void setRadius(float r)
	{
		radius = r;
	}
	
	public float getSequenceNumber()
	{
		return sequenceNumber;
	}
	public void setSequenceNumber(float r)
	{
		sequenceNumber = r;
	}
	
	
	//override the Object Class's toString() method and utilized the parents toString method.
	public String toString()
	{
		return("Pylon:    " + super.toString() + "radius=" + (int)radius + " seqNum=" + (int)sequenceNumber);
	}

	public void draw(Graphics2D g) 
	{
		AffineTransform backup = g.getTransform();
		
		g.transform(myTranslation);
		g.transform(myRotation);
		g.transform(myScaling);
		g.setColor(getColor());
		g.fillOval(((int)(0 - radius)), ((int)(0 - radius)), ((int)radius*2), ((int)radius*2));
		g.setColor(Color.BLACK);
		g.drawString(String.valueOf(sequenceNumber), ((int)(0 - radius/2)), ((int)(0 + radius/2)));
		if(selected)
		{
			g.setColor(Color.BLACK);
			g.drawLine((int)(0 - radius), 0, (int)(0 + radius), 0);
			g.drawLine(0, (int)(0 - radius), 0, (int)(0 + radius));
		}
		
		g.setTransform(backup);
	}

	public void setSelected(boolean yes) 
	{
		selected = yes;
		
	}

	public boolean isSelected() 
	{
		return selected;
	}

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
		
		float diftotal = (float) Math.sqrt((difx*difx)+(dify*dify));
		
		if(diftotal < radius)
			return true;
		return false;
		
	}

	public boolean collidesWith(ICollide other) {
		return false;}

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
