package a4;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	Steerleft
 * Purpose: This is our Steerleft action class. It steers the car to the left.
 * 
 *****************************************************************************************************************************/
public class SteerLeft extends AbstractAction
{
	private static SteerLeft steerLeft;
	private GameWorld target;
	
	private SteerLeft()
	{
		super("Steer Left");
	}
	
	public static SteerLeft getSteerLeft()
	{
		if(steerLeft == null)
			steerLeft = new SteerLeft();
		return steerLeft;
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		Car car;
		GameCollection col = target.getCollection();
		Iterator i = col.getIterator();
		
		//look for the players car and turn
		while(i.hasNext())
		{
			Object o = i.getNext();
			if(o instanceof Car)
			{
				car = (Car) o;
				if(car.getType())
				{
					if(car.getTractionFlag())
						if(car.getSteeringDirection() < 40)
						{
							car.turnLeft();
							Wheel[] wheels = car.getWheels();
							for(Wheel w : wheels)
							{
								if(w.getStatus())
								{
									w.turnLeft();
								}
							}
						}
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
