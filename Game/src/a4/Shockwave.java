package a4;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Vector;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	Shockwave
 * Purpose: This is our Score view. It displays the status of some of our objects for the gameworld and car.
 * 
 *****************************************************************************************************************************/
public class Shockwave implements IDrawable, ICollide, ISelectable
{
	private AffineTransform myTranslation, myRotation, myScaling;
	private Point2D p1,p2,p3,p4;
	private Vector<Point2D> controlPointVector;
	private float heading, speed, time;
	
	//constructor
	public Shockwave(double d, double e)
	{
		myTranslation = new AffineTransform();
		myRotation = new AffineTransform();
		myScaling = new AffineTransform();
		myTranslation.translate(d, e);
		heading = (float) (Math.random()*360);
		speed = (float) (Math.random()*100);
		time = 0;
		
		controlPointVector = new Vector<Point2D>();
		
		//create our random control points
		p1 = new Point();
		p1.setLocation(Math.random()*100, Math.random()*100);
		p2 = new Point();
		p2.setLocation(Math.random()*100, Math.random()*100);
		p3 = new Point();
		p3.setLocation(Math.random()*100, Math.random()*100);
		p4 = new Point();
		p4.setLocation(Math.random()*100, Math.random()*100);
		controlPointVector.add(p1);
		controlPointVector.add(p2);
		controlPointVector.add(p3);
		controlPointVector.add(p4);
	}
	
	//move the points
	public void move(float elapsedMsec)
	{
		//this keeps the heading withi 0 and 360
				if(heading > 360)
					heading = heading -360;
				if(heading < 0)
					heading = 360 + heading; 
				
				//here we update the location after using cosine and sine after converting to radians due to the built in Math methods requirements
				//for radians. we then multiply be the speed and adjust x. We also pass the moveable pbject with this information to "unlock" the method
				//in location. This keeps the locations from changing in Fixed object types.
				float dist = speed * (elapsedMsec/1000);
				float xChange = (float) Math.cos((90 - heading)*(3.1415/180))*dist;
				float yChange = (float) Math.sin((90 - heading)*(3.1415/180))*dist;
				
				myTranslation.translate(xChange,yChange);
				
				time = time + 20;
	}
	
	public float getTime()
	{
		return time;
	}
	
	
	public void draw(Graphics2D g) 
	{
		AffineTransform backup = g.getTransform();
		g.transform(myTranslation);
		g.transform(myRotation);
		g.transform(myScaling);
		g.setColor(Color.BLUE);
		
		//draw the points with connecting lines first
		g.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
		g.drawLine((int)p2.getX(), (int)p2.getY(), (int)p3.getX(), (int)p3.getY());
		g.drawLine((int)p3.getX(), (int)p3.getY(), (int)p4.getX(), (int)p4.getY());
		
		//draw the bezier curve
		drawBezierCurve(controlPointVector, 0, g);
		
		g.setTransform(backup);
		
	}
	
	//recursive method that breaks the line segments between points down until they are extremely close together
	//or we have looped too much so we dont hangup the program.
	private void drawBezierCurve(Vector<Point2D> cpv, float level, Graphics2D g2D)
	{
		
		if(straightEnough(cpv) || level >= 1000)
		{
			g2D.drawLine(((int)cpv.get(0).getX()), ((int)cpv.get(0).getY()), ((int)cpv.get(3).getX()), ((int)cpv.get(3).getY()));
		}
		else
		{
			//here we call recursively after breaking the control points into two arrays
			Vector<Point2D> leftCPV = new Vector<Point2D>();
			Vector<Point2D> rightCPV = new Vector<Point2D>();
			subdivideCurve(cpv, leftCPV, rightCPV);
			drawBezierCurve(leftCPV, ++level, g2D);
			drawBezierCurve(rightCPV, ++level, g2D);
		}
		
			
	}
	
	//test if the distances between points is close enough
	private boolean straightEnough(Vector<Point2D> cpv) 
	{
		float length1 = (float) Math.sqrt(cpv.get(0).getX()*cpv.get(1).getX() + cpv.get(0).getY()*cpv.get(1).getY());
		float length2 = (float) Math.sqrt(cpv.get(1).getX()*cpv.get(2).getX() + cpv.get(1).getY()*cpv.get(2).getY());
		float length3 = (float) Math.sqrt(cpv.get(2).getX()*cpv.get(3).getX() + cpv.get(2).getY()*cpv.get(3).getY());
		float d1 = length1 + length2 + length3;
		float d2 = (float) Math.sqrt(cpv.get(0).getX()*cpv.get(3).getX() + cpv.get(0).getY()*cpv.get(3).getY());
		
		if(Math.abs(length1-d2) < .01)
				return true;
		else
			return false;
	}
	
	//here we break the control point vector into two separate vectors that are half the original
	private void subdivideCurve(Vector<Point2D> old,Vector<Point2D> left,Vector<Point2D> right)
	{
		double tempx = 0;
		double tempy = 0;
		Point2D tempPoint = new Point();
		//here we set the lefts first point
		tempPoint.setLocation(old.get(0).getX(), old.get(0).getY());
		left.addElement(tempPoint);
		
		//here we set the lefts second point
		tempx = (old.get(0).getX() + old.get(1).getX()) / 2;
		tempy = (old.get(0).getY() + old.get(1).getY()) / 2;
		Point2D a = new Point();
		a.setLocation(tempx, tempy);
		left.add(a);
		
		//here we set the lefts third point
		tempx = (left.get(1).getX()/2) + ((old.get(1).getX()+old.get(2).getX()) / 4);
		tempy = (left.get(1).getY()/2) + ((old.get(1).getY()+old.get(2).getY()) / 4);
		Point2D b = new Point();
		b.setLocation(tempx, tempy);
		left.addElement(b);
		
		//here we set the rights fourth point
		Point2D c = new Point();
		c.setLocation(old.get(3).getX(), old.get(3).getY());
		right.add(0, c);
		
		//here we set the rights third point
		tempx = (old.get(2).getX() + old.get(3).getX()) /2;
		tempy = (old.get(2).getY() + old.get(3).getY()) /2;
		Point2D d = new Point();
		d.setLocation(tempx, tempy);
		right.add(0, d);
		
		//here we set the rights second point
		tempx = (old.get(1).getX() + old.get(2).getX())/4 + (right.get(0).getX() /2);
		tempy = (old.get(1).getY() + old.get(2).getY())/4 + (right.get(0).getY() /2);
		Point2D e = new Point();
		e.setLocation(tempx, tempy);
		right.add(0,e);
		
		//here we set the lefts fourth point
		tempx = (left.get(2).getX() + right.get(0).getX())/2;
		tempy = (left.get(2).getY() + right.get(0).getY())/2;
		Point2D f = new Point();
		f.setLocation(tempx, tempy);
		left.addElement(f);
		
		//here we set the rights first point
		Point2D g = new Point();
		g.setLocation(left.get(3).getX(), left.get(3).getY());
		right.add(0, g);
		
	}
	

	public boolean collidesWith(ICollide other) 
	{return false;}

	public void handleCollision(ICollide other) 
	{}

	public void setSelected(boolean yes) {}
	
	public boolean isSelected() 
	{return false;}

	public boolean contains(Point2D p) 
	{return false;}
	
}
