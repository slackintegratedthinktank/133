package a4;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	Wheel
 * Purpose: This is our Strategy interface, it states what is required to be considered a strategy.
 * 
 *****************************************************************************************************************************/
public class Wheel 
{
	private AffineTransform myTranslation, myRotation, myScaling;
	private boolean front;
	private float width,height;
	private int turning;

	public Wheel(double x, double y, boolean status)
	{
		width = 8;
		height = 3;
		front = status;
		turning = 0;
		myTranslation = new AffineTransform();
		myTranslation.translate(x, y);
		myRotation = new AffineTransform();
		myScaling = new AffineTransform();
		
	}
	
	public boolean getStatus()
	{
		return front;
	}
	
	public int getTurning()
	{
		return turning;
	}
	public void resetRotation()
	{
		myRotation.setToIdentity();
		turning = 0;
	}
	
	//handle the turning
	public void turnLeft()
	{
		myRotation.setToIdentity();
		if(turning != 1)
			myRotation.rotate(Math.toRadians(-30));
		turning = 1;
	}
	
	public void turnRight()
	{
		myRotation.setToIdentity();
		if(turning != 2)
			myRotation.rotate(Math.toRadians(30));
		turning = 2;
	}
	
	public void scale(double x, double y)
	{
		myScaling.scale(x, y);
	}
	public void translate(double x, double y)
	{
		myTranslation.translate(x, y);
	}
	public void setIdentity()
	{
		myTranslation.setToIdentity();
		myRotation.setToIdentity();
		myScaling.setToIdentity();
	}
	
	//draw after applying the local transforms
	public void Draw(Graphics2D g2d)
	{
		AffineTransform backup = g2d.getTransform();
		g2d.transform(myTranslation);	
		g2d.transform(myRotation);
		g2d.transform(myScaling);
		
		g2d.drawRect(0, 0, (int)height, (int)width);
		
		
		turning = 0;
		
		g2d.setTransform(backup);
		
	}
}
