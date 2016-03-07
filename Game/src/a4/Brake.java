package a4;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	Brake
 * Purpose: Action class that slows down the car
 * 
 *****************************************************************************************************************************/


public class Brake extends AbstractAction
{
	private static Brake brake;
	private GameWorld target;
	
	//hidden constructor
	private Brake() 
	{
		super("Brake");
	}
	
	//returns a singleton instance of brake
	public static Brake getBrake()
	{
		if(brake == null)
			brake = new Brake();
		return brake;
	}


	public void actionPerformed(ActionEvent e) 
	{
		Car car;
		GameCollection col = target.getCollection();
		Iterator i = col.getIterator();
		
		//look for the car
		while(i.hasNext())
		{
			Object o = i.getNext();
			if(o instanceof Car)
			{
				car = (Car) o;
				if(car.getType())
				{
					if(car.getSpeed() >= 2) //update the speed of the car
						car.setSpeed(car.getSpeed()-2);
					else
						car.setSpeed(0);
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
