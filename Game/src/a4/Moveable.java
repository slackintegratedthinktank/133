package a4;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	Moveable
 * Purpose: an Abstract class that contains has all of the variables and methods needed by objects of type moveable.
 * 
 *****************************************************************************************************************************/
import java.awt.Color;
import java.awt.geom.AffineTransform;

interface Moveable
{
	//move() moves the object in the direction  of the heading and applies speed to it then assigns that value to the new location. we must covert to radians first however.
	 void move(float elapsedMsec);
	
	//accessors and mutators that are common to the moveable type objects
	public void setHeading(float h);
	public float getHeading();
	public void setSpeed(float s);
	public float getSpeed();
	public void setColor(float r, float g, float b);
	
	public void rotate(double radians);
	
	public void translate(double dx, double dy);
	
	public void resetTransform();
	public Color getColor();
	public AffineTransform getTranslation();
	
	public AffineTransform getRotation();

	public AffineTransform  getScale();

	//override the object class's toString method.
	public String toString();
	
}
