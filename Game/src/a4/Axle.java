package a4;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	Axle
 * Purpose: Here is a heirarchical object class for the players car axle.
 * 
 *****************************************************************************************************************************/
public class Axle 
{
	private AffineTransform myTranslation, myRotation, myScaling;
	private float length, height;
	private Wheel[] wheels;
	
	//our constructor
	public Axle(boolean front)
	{
		length = 17;
		height = 2;
		myTranslation = new AffineTransform();
		myTranslation.translate(0, 0);
		myRotation = new AffineTransform();
		myScaling = new AffineTransform();
		wheels = new Wheel[2];
		Wheel left = new Wheel(-3, -2, front);
		wheels[0] = left;
		Wheel right = new Wheel(18, -2, front);
		wheels[1] = right;
		
	}
	
	//handle rotations on the rotation AffineTransform
	public void rotate(double degrees)
	{
		myRotation.rotate(Math.toRadians(degrees));
	}
	
	//handle the scaling of the scale Affine Transform
	public void scale(double x, double y)
	{
		myScaling.scale(x, y);
	}
	
	//handle the translation of the translate AffineTransform
	public void translate(double x, double y)
	{
		myTranslation.translate(x, y);
	}
	
	//here we can reset the AffineTransforms to their identities
	public void setIdentity()
	{
		myTranslation.setToIdentity();
		myRotation.setToIdentity();
		myScaling.setToIdentity();
	}
	
	//here we draw the objects by appending the AffineTransforms and then drawing on the passed Graphics2D object
	public void Draw(Graphics2D g2d)
	{
		AffineTransform backup = g2d.getTransform();
		
		g2d.transform(myRotation);
		g2d.transform(myScaling);
		g2d.transform(myTranslation);
		
		g2d.drawRect(0, 0, (int)length, (int)height);
		for(Wheel w : wheels)
		{
			w.Draw(g2d);
		}
		g2d.setTransform(backup);  //restore the original graphics trasform.
		
	}

	public Wheel getWheel(int i) 
	{
		return wheels[i];
	}	
}
