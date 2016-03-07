package a4;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	CarBody
 * Purpose: To draw the car body for the car object based on the graphics object passed down from the car.
 * 
 *****************************************************************************************************************************/
public class CarBody 
{
	private AffineTransform myTranslation, myRotation, myScaling;
	

	public CarBody()
	{
		myTranslation = new AffineTransform();
		myTranslation.translate(2, 2);
		myRotation = new AffineTransform();
		myScaling = new AffineTransform();
		
	}
	
	public void rotate(double degrees)
	{
		myRotation.rotate(Math.toRadians(degrees));
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
	
	//here we apply CarBody translations to the graphics2d object passed by the car 
	public void Draw(Graphics2D g2d)
	{
		AffineTransform backup = g2d.getTransform();
		
		g2d.transform(myRotation);
		g2d.transform(myScaling);
		g2d.transform(myTranslation);
		
		g2d.setColor(Color.black);
		
		//here we draw lines that dictated the shape of the car body.
		g2d.drawLine(-10, -16, -7, 16);
		g2d.drawLine(10, -16, 7, 16);
		g2d.drawLine(-10, -16, 10, -16);
		g2d.drawLine(-7, 16, 7, 16);
		g2d.setTransform(backup);
		
	}	
}
