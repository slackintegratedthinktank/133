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
 * Class: 	Car
 * Purpose: Manage the object known as car. It extends the class Moveable() and adds additional information needed to operate
 * 			the cars movement and control surfaces implemented by the interface ISteerable. 
 * 
 *****************************************************************************************************************************/

class Car  implements ISteerable, IDrawable, ICollide, ISelectable, Moveable
{

	//declare our class variables
	private float width, length, steeringDirection, maximumSpeed, fuelLevel, damageLevel, lastPylon, heading, speed;
	private boolean tractionFlag, type;
	private ArrayList<Object> colliding;
	private boolean selected;
	private AffineTransform myRotation, myTranslation, myScaling;
	private Color color;
	private static double pi = 3.141592654; //declare a constant for pi.
	private Wheel [] wheels;
	private CarBody body;
	private Axle front;
	private Axle rear;


	//generic constructor
	public Car()
	{
		super();
		type = false;
		width = 15;
		length = 25;
		steeringDirection = 0;
		maximumSpeed = 100;
		fuelLevel = 100;
		damageLevel = 0;
		tractionFlag = true;
		selected = false;
		colliding = new ArrayList<Object>();
		
		//here are our transforms to adjust where the object is drawn
		myRotation = new AffineTransform();
		myTranslation = new AffineTransform();
		myScaling = new AffineTransform();	
		myScaling.scale(1, -1);
		
		
		//here we create the component objects for drawing our car
		body = new CarBody();
		front = new Axle(true);
		front.translate(0, 7);
		rear = new Axle(false);
		rear.translate(0, -7);

		
	}
	public Car(boolean isPlayer)
	{
		super();
		type = isPlayer;
		width = 20;
		length = 25;
		steeringDirection = 0;
		maximumSpeed = 100;
		fuelLevel = 100;
		damageLevel = 0;
		tractionFlag = true;
		selected = false;
		colliding = new ArrayList<Object>();		
		myRotation = new AffineTransform();
		myTranslation = new AffineTransform();
		myScaling = new AffineTransform();
		myScaling.scale(1, -1);	
		rotate(Math.toRadians(90));
		body = new CarBody();
		front = new Axle(true);
		front.translate(-7,12);
		rear = new Axle(false);
		rear.translate(-7, -8);
		
	}

	//these are our accessors and mutators for the object variables. it also inherits the superclass of Moveable and implemets the interface of ISteerable.
	public float getWidth() 
	{
		return width;
	}

	public void setWidth(float w) 
	{
		width = w;
	}
	
	public AffineTransform getTranslation()
	{
		return myTranslation;
	}
	
	public AffineTransform getRotation()
	{
		return myRotation;
	}
	
	public AffineTransform  getScale()
	{
		return myScaling;
	}

	public float getLength() {
		return length;
	}

	public void setLength(float l) {
		length = l;
	}

	public float getSteeringDirection() {
		return steeringDirection;
	}

	public void setSteeringDirection(float sD) {
		steeringDirection = sD;
	}

	public float getMaximumSpeed() {
		return maximumSpeed;
	}
	public void setMaximumSpeed(float s)
	{
		maximumSpeed = s;
	}

	public float getFuelLevel() {
		return fuelLevel;
	}

	public void setFuelLevel(float fL) {
		fuelLevel = fL;
	}

	public float getDamageLevel() {
		return damageLevel;
	}

	public void setDamageLevel(float dL) {
		damageLevel = dL;
	}

	public boolean getTractionFlag()
	{
		return tractionFlag;
	}

	public void setTractionFlag(boolean t)
	{
		tractionFlag = t;
	}

	public boolean getType()
	{
		return type;
	}
	
	public Wheel[] getWheels()
	{
		Wheel[] wheels = new Wheel[4];
		wheels[0] = front.getWheel(0);
		wheels[1] = front.getWheel(1);
		wheels[2] = rear.getWheel(0);
		wheels[3] = rear.getWheel(1);
		return wheels;
	}
	
	public void emptyCollisions()
	{
		colliding.clear();
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
	
	
	//here are the methods from the steerable interface that manipulate out car variables based on the game world.

	//this increases the speed of the car by 5 units
	public void accelerate() 
	{
		this.setSpeed((float)(this.getSpeed() + .2));
	}

	//this method turns the steeringDirection to the left, or (+)
	public void turnLeft() 
	{
		steeringDirection = steeringDirection - 5;
		rotate(-1*Math.toRadians(steeringDirection));
	}

	//this method turns the steeringDirection to the right, or (-)
	public void turnRight() 
	{
		steeringDirection = steeringDirection + 5;
		rotate(-1*Math.toRadians(steeringDirection));	
	}
	public void setPylon(float p)
	{
		lastPylon = p;
	}
	public float getPylon()
	{
		return lastPylon;
	}
	
	public CarBody getbody()
	{
		return body;
	}
	
	public Axle getFront()
	{
		return front;
	}
	
	public Axle getRear()
	{
		return rear;
	}

	//this method applies a brake by decreasing speed by 5 units
	public void applyBrake() 
	{
		this.setSpeed(this.getSpeed() - 5);
	}

	public void addCollision(Object o)
	{
		colliding.add(o);
	}
	
	//this method applies a collision by adding the damage amount passed to it and then adjusts the maximum speed.
	public void collision(float d) 
	{
		damageLevel = damageLevel + d;
		maximumSpeed = (50*((100 - damageLevel)/100));
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
		
		if(o instanceof OilSlicks)
		{
			ExitOil eO = ExitOil.getExitOil();
			eO.setTarget(this);
			eO.privateAction();
		}
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
		
		translate(xChange,yChange);
		
	}
	
	
	//Here are our methods to modify the local Transformations.
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

	//her is our draw method to place the car on the screen based on its coordinates.
	public void draw(Graphics2D g) 
	{
		AffineTransform backup = g.getTransform();
		g.transform(myTranslation);
		g.transform(myRotation);
		g.transform(myScaling);

		g.setColor(getColor());
		
		//here we draw each component by passing the graphics2D object down to them after applying the cars transforms
		body.Draw(g);
		front.Draw(g);
		rear.Draw(g);

		if(selected)
		{
			g.setColor(Color.black);
			g.drawLine((int)(0 - length/2), 0, (int) (length/2), 0);
			g.drawLine(0, (int)(0 - width/2), 0, (int)(width/2));
		}
		
		g.setTransform(backup);
	}


	//here we handle the collisions involved with the car object running into other objects
	public boolean collidesWith(ICollide other) 
	{
		if(other instanceof Car)
		{
			Car c = ((Car)other);
			float otherX = (float) c.getTranslation().getTranslateX();
			float otherY = (float) c.getTranslation().getTranslateY();
			float otherLength = c.getLength();
			float otherWidth = c.getWidth();

			//check overlap in the x direction
			if(otherX+otherWidth < this.getTranslation().getTranslateX() || otherX > this.getTranslation().getTranslateX()+width)
			{
				return false;
			}
			//check overlap in the y direction
			else if(this.getTranslation().getTranslateY() > otherY + otherLength || otherY > this.getTranslation().getTranslateY()+length)
			{
				return false;
			}
			
			
			return true;
		}
		if(other instanceof Pylons)
		{
			Pylons p = ((Pylons)other);
			

			// A________B    
			// |        |   here I outline which point we are inspecting in the square
			// |        |   to test against the circles center point and radius
			// |        |
			// C________D

			// Point A
			float cornerX = (float) (this.getTranslation().getTranslateX()-width/2);
			float cornerY = (float) (this.getTranslation().getTranslateY()-length/2);
			Point point = new Point();
			point.setLocation(cornerX, cornerY);
			if(p.contains(point))
				return true;



			// Point B
			cornerX = (float) (this.getTranslation().getTranslateX()+width/2);
			cornerY = (float) (this.getTranslation().getTranslateY()-length/2);
			point = new Point();
			point.setLocation(cornerX, cornerY);
			if(p.contains(point))
				return true;

			// Point C
			cornerX = (float) (this.getTranslation().getTranslateX()-width/2);
			cornerY = (float) (this.getTranslation().getTranslateY()+length/2);
			point = new Point();
			point.setLocation(cornerX, cornerY);
			if(p.contains(point))
				return true;	

			// Point D
			cornerX = (float) (this.getTranslation().getTranslateX()+width/2);
			cornerY = (float) (this.getTranslation().getTranslateY()+length/2);
			point = new Point();
			point.setLocation(cornerX, cornerY);
			if(p.contains(point))
				return true;
			
			return false;
		}

		//check if the oil slick collides with the car
		if(other instanceof OilSlicks)
		{
			OilSlicks p = ((OilSlicks)other);
			Point c = new Point();
			c.setLocation(this.getTranslation().getTranslateX()+width/2,this.getTranslation().getTranslateY()+length/2);
			if(p.contains(c))
				return true;
			
			c.setLocation(this.getTranslation().getTranslateX()+width/2,this.getTranslation().getTranslateY()-length/2);
			if(p.contains(c))
				return true;

			c.setLocation(this.getTranslation().getTranslateX()-width/2,this.getTranslation().getTranslateY()+length/2);
			if(p.contains(c))
				return true;
			
			c.setLocation(this.getTranslation().getTranslateX()+width/2,this.getTranslation().getTranslateY()-length/2);
			if(p.contains(c))
				return true;
		}
		
		//here we check against a fuel can.
		if(other instanceof FuelCans)
		{
			FuelCans c = ((FuelCans)other);
			float otherX = (float) c.getTranslation().getTranslateX();
			float otherY = (float) c.getTranslation().getTranslateY();
			float otherLength = c.getSize();
			float otherWidth = c.getSize();

			//check overlap in the x direction
			if(otherX+otherWidth < this.getTranslation().getTranslateX() || otherX > this.getTranslation().getTranslateX()+width)
			{
				return false;
			}
			//check overlap in the y direction
			else if(this.getTranslation().getTranslateY() > otherY + otherLength || otherY > this.getTranslation().getTranslateY()+length)
			{
				return false;
			}
			
			return true;
		}
		return false;
	}
	

	//handle which objects are involved in the collision
	public void handleCollision(ICollide other) 
	{
		if(!colliding.contains(other))
		{
			//here we check against each type of object and call an action event for each.
			if(other instanceof NPC)
			{
				CarCollision cC = CarCollision.getCarCollision();
				cC.setTarget(this, ((NPC)other));
				cC.privateAction();
				colliding.add(other);
			}
			else if(other instanceof Pylons)
			{
				PylonCollision pC = PylonCollision.getPylonCollision();
				pC.setTarget(this, ((Pylons)other));
				pC.privateAction();
				colliding.add(other);
			}
			else if(other instanceof OilSlicks)
			{
				EnteredOil eO = EnteredOil.getEnteredOil();
				eO.setTarget(this);
				eO.privateAction();
				colliding.add(other);
			}
			else if(other instanceof FuelCans)
			{
				FuelCollision fC = FuelCollision.getFuelCollision();
				fC.setTarget(this, ((FuelCans)other));
				fC.privateAction();
				colliding.add(other);
			}
		}


	}

	public void setSelected(boolean yes) 
	{
		if(!type)
			selected = yes;
		else
			selected = false;
		
	}

	public boolean isSelected() 
	{
		return selected;
	}

	//here we check if the worldpoint is contained inside the car by translating it to local coordinates
	public boolean contains(Point2D worldPoint) 
		{
			Point2D p = null;
			try 
			{
				p = myTranslation.createInverse().transform(worldPoint, null);
			} 
			catch (NoninvertibleTransformException e) {}
		
		if(p.getX() < 0-width/2 || p.getX() > 0+width/2 || p.getY() < 0 -length/2 || p.getY() > 0+length/2)
			return false;
		return true;
	}

	//override the Object Class's toString() method and utilized the parents toString method.
	public String toString()
	{
		return("Car:      "+ " color=[" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + "] " + "heading=" + (int)this.getHeading() + " speed=" + this.getSpeed()
				+ " width=" + (int)width + " length=" + (int)length + "\n          maxSpeed=" + maximumSpeed + " steeringDirection=" 
				+ (int)steeringDirection + " fuelLevel=" + (int)fuelLevel + " damage=" + (int)damageLevel);
	}


}
