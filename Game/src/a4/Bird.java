package a4;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	Bird
 * Purpose: Class that handles the Bird objects and its variables and fields. it extends the Moveable class and adds a size
 * 			variable. Accessors and mutators are the first methods followed by the toString method last.
 * 
 *****************************************************************************************************************************/


class Bird implements IDrawable, ICollide, ISelectable, Moveable
{
	private float size;
	private ArrayList<Object> colliding;
	private boolean selected;
	private AffineTransform myRotation, myTranslation, myScaling;
	private float heading;
	private float speed;
	private Color color;
	private static double pi = 3.141592654;
	
	//constructor
	Bird()
	{
		super(); //call the Moveable constructor.
		size = 0;
		selected = false;
		heading = 0;
		speed = 0;
		color = Color.BLACK;
		colliding = new ArrayList<Object>();
		myRotation = new AffineTransform();
		myTranslation = new AffineTransform();
		myScaling = new AffineTransform();
		myScaling.scale(1, -1);
		
	}
	
	//accessors and mutators.
	public void setSize(float s)
	{
		size = s;
	}
	public float getSize()
	{
		return size;
	}
	public void setSelected(boolean yes) 
	{
		selected = yes;
	}

	public boolean isSelected() 
	{
		return selected;
	}
	
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
		float xChange = (float) Math.cos((90 - heading)*(pi/180))*dist;
		float yChange = (float) Math.sin((90 - heading)*(pi/180))*dist;

		this.translate(xChange, yChange);
	}
	
	public void rotate(double radians)
	{
		myRotation.rotate(radians);
	}
	
	public void translate(double dx, double dy)
	{
		myTranslation.translate(dx, dy);
	}
	
	public void resetTransform()
	{
		myTranslation.setToIdentity();
		myRotation.setToIdentity();
		myScaling.setToIdentity();
	}
	
	//here we draw our bird in order to be displayed on the screen
	public void draw(Graphics2D g) 
	{
		AffineTransform backup = g.getTransform();
		
		g.transform(myTranslation);
		g.transform(myRotation);
		g.transform(myScaling);
		g.setColor(getColor());
		g.drawOval(((int)(0 - (.5*size))), ((int)(0 - (.5*size))), ((int)size), ((int)size));
		if(selected)
		{

			g.setColor(Color.black);
			g.drawLine(0, (int)(0 - size/2), 0, (int)(0 + size/2));
			g.drawLine((int)(0 - size/2), 0, (int)(0 + size/2), 0);
			g.setColor(Color.black);
		}
		g.setTransform(backup);
	}
	
	//override the Object Class's toString() method and utilized the parents toString method.
	public String toString()
	{
		return("Bird:     " + super.toString() + "heading=" + (int)this.getHeading() + " speed=" + (int)this.getSpeed() + " size=" + size);
	}

	public boolean hasCollision(Object o)
	{
		if(colliding.contains(o))
			return true;
		return false;
	}
	public void removeCollision(Object o)
	{
		colliding.remove(o);
	}
	public boolean collidesWith(ICollide other) 
	{
		if(other instanceof Car)
		{
			Car c = ((Car)other);
			float otherWidth = c.getWidth();
			float otherLength = c.getLength();
			float otherX = (float) c.getTranslation().getTranslateX();
			float otherY = (float) c.getTranslation().getTranslateY();
			float thisX = (float) myTranslation.getTranslateX();
			float thisY = (float) myTranslation.getTranslateY();
			
			
			//check overlap in the x direction
			if(otherX+otherWidth/2 > thisX && otherX-otherWidth/2 < thisX)
			{
				if(thisY < otherY + otherLength/2 && thisY > otherY-otherLength/2)
				{
					return true;
				}
			}

			
			// A________B    
			// |        |   here I outline which point we are inspecting in the square
			// |        |   to test against the circles center point and radius
			// |        |
			// C________D
			
			// Point A
			float cornerX = otherX-otherWidth/2;
			float cornerY = otherY-otherLength/2;
			float xTest = Math.abs((cornerX - thisX)*(cornerX - thisX));
			float yTest = Math.abs((cornerY - thisY)*(cornerY - thisY));
			float zTest = (float) Math.sqrt(xTest+yTest);
			if(zTest <= size/2)
				return true;
			
			// Point B
			cornerX = otherX+otherWidth/2;
			cornerY = otherY-otherLength/2;
			xTest = Math.abs((cornerX - thisX)*(cornerX - thisX));
			yTest = Math.abs((cornerY - thisY)*(cornerY - thisY));
			zTest = (float) Math.sqrt(xTest+yTest);
			if(zTest <= size/2)
				return true;
			
			// Point C
			cornerX = otherX-otherWidth/2;
			cornerY = otherY+otherLength/2;
			xTest = Math.abs((cornerX - thisX)*(cornerX - thisX));
			yTest = Math.abs((cornerY - thisY)*(cornerY - thisY));
			zTest = (float) Math.sqrt(xTest+yTest);
			if(zTest <= size/2)
					return true;	
			
			// Point D
			cornerX = otherX+otherWidth/2;
			cornerY = otherY+otherLength/2;
			xTest = Math.abs((cornerX - thisX)*(cornerX - thisX));
			yTest = Math.abs((cornerY - thisY)*(cornerY - thisY));
			zTest = (float) Math.sqrt(xTest+yTest);
			if(zTest <= size/2)
				return true;
		}
		return false;
	}

	public void addCollision(Object o)
	{
		colliding.add(o);
	}
	
	//here we are handing the collision with the object
	public void handleCollision(ICollide other) 
	{
		if(!colliding.contains(other))
		{
			BirdCollision bC = BirdCollision.getBirdCollision();
			bC.setTarget(this, other);
			bC.privateAction();
			colliding.add(other);
			
		}
		
	}
		
	//here we check to see if this bird contains the point passed to it
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
		
		if(diftotal < size/2)
			return true;
		return false;
		
	}

	public void setHeading(float h)
	{
		heading = h;
	}
	public float getHeading()
	{
		return heading;
	}
	public void setSpeed(float s)
	{
		speed = s;
	}
	public float getSpeed()
	{
		return speed;
	}
	public void setColor(float r, float g, float b)
	{
		color = new Color(r,g,b);
	}
	public Color getColor()
	{
		return color;
	}

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



	
}
