package a4;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	SteerRight
 * Purpose: This is our steerRight action object. It controlls the steering of a car
 * 
 *****************************************************************************************************************************/
public class SteerRight extends AbstractAction
{

	private static SteerRight steerRight;
	private GameWorld target;
	
	//hidden constructor
	private SteerRight() 
	{
		super("Steer Right");
	}
	
	//returns our singleton instance of steerRight
	public static SteerRight getSteerRight()
	{
		if(steerRight == null)
			steerRight = new SteerRight();
		return steerRight;
	}
	

	public void actionPerformed(ActionEvent e) 
	{
		Car car;
		GameCollection col = target.getCollection();
		Iterator i = col.getIterator();
		
		//this looks for the players car and steers it to the right
		while(i.hasNext())
		{
			Object o = i.getNext();
			if(o instanceof Car)
			{
				car = (Car) o;
				if(car.getType())
				{
					if(car.getTractionFlag())
						if(car.getSteeringDirection() > -40)
						{
							car.turnRight();
							
							Wheel[] wheels = car.getWheels();
							for(Wheel w : wheels)
							{
								if(w.getStatus())
								{
									w.turnRight();
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
