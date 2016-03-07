package a4;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	Fixed
 * Purpose: an Abstract class that contains has all of the variables and methods needed by objects of type Fixed.
 * 
 *****************************************************************************************************************************/
import java.awt.Color;
import java.awt.geom.AffineTransform;

interface Fixed
{
	public AffineTransform getTranslation();
	public AffineTransform getRotation();
	public AffineTransform  getScale();
	public Color getColor();
	public void setColor(float r, float g, float b);

}
