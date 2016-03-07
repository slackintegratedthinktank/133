package a4;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	Accelerate
 * Purpose: Action class that Accelerates the car based on user input
 * 
 *****************************************************************************************************************************/
public class Accelerate extends AbstractAction
{
	
	private static Accelerate accel;
	private GameWorld target;
	
	//constructor
	private Accelerate() 
	{
		super("Accelerate");
	}
	
	//returns the singleton instance of accelerate
	public static Accelerate getAccelerate()
	{
		if(accel == null)
			accel = new Accelerate();
		return accel;
	}

	//this performs the acceleration of the car.
	public void actionPerformed(ActionEvent e) 
	{
		Car car;
		GameCollection col = target.getCollection(); //return the collection of items
		Iterator i = col.getIterator();
		
		//look for the car
		while(i.hasNext())
		{
			Object o = i.getNext();
			if(o instanceof Car)
			{
				car = (Car) o;
				if(car.getType()) //update the cars speed 
				{
					if(car.getSpeed() < (car.getMaximumSpeed()))
						car.setSpeed((float)(car.getSpeed() + 3));
					else
						car.setSpeed(car.getMaximumSpeed());
				}
			}
		}
		target.notifyObservers();
	}
	
	public void setTarget(GameWorld w)
	{
		target = w;
	}

}
