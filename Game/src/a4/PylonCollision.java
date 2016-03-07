package a4;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	PylonCollision
 * Purpose: Action class that simulates a Pylon Collision
 * 
 *****************************************************************************************************************************/
public class PylonCollision extends AbstractAction
{
	private static PylonCollision pylonCollision;
	private GameWorld target;
	private Car car;
	private Pylons pylon;
	
	//hidden constructor
	private PylonCollision() 
	{
		super("Pylon Collision");
	}
	
	//returns the singleton instance of a pylon
	public static PylonCollision getPylonCollision()
	{
		if(pylonCollision == null)
			pylonCollision = new PylonCollision();
		return pylonCollision;
	}
	
	//implements the action
	public void actionPerformed(ActionEvent e) 
	{
		boolean cont = true;
			String input = JOptionPane.showInputDialog("Please Input a pylon number");  //prompt for a pylon number
			try
			{
				float pylonNum = Float.parseFloat(input);
				GameCollection o = target.getCollection();
				Iterator i = o.getIterator();
				boolean exit = false;
				while(i.hasNext() && exit == false) //look for the pylon
				{
					Object t = i.getNext();
					if(t instanceof Pylons)
					{
						if(((Pylons) t).getSequenceNumber() == pylonNum) //make sure it exists
						{
							target.setLastPylon(pylonNum);
							Car car;
							GameCollection col = target.getCollection(); 
							Iterator i2 = col.getIterator();
							
							//look for the car
							while(i2.hasNext())
							{
								Object o2 = i2.getNext();
								if(o2 instanceof Car)
								{
									car = (Car) o2;
									if(car.getType())
									{
										car.setPylon(pylonNum); //set its highest pylon
									}
								}
							}
							exit = true;
							cont = false;
						}
					}
				}	
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(null, "You did not enter a pylon number");
			}
		
		target.notifyObservers();
	}
	
	//here is a private action method for when a pylon and car and passed.
	public void privateAction()
	{
		if((pylon.getSequenceNumber() - car.getPylon()) ==1)
		{
			target.setLastPylon(pylon.getSequenceNumber());
			car.setPylon(pylon.getSequenceNumber());
		}
	}
	public void setTarget(GameWorld w)
	{
		target = w;
	}

	public void setTarget(Car c, Pylons p) 
	{
		car = c;
		pylon = p;
		
	}
	
}
