package a4;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	EnteredOil
 * Purpose: Action class that handles a car entering oil.
 * 
 *****************************************************************************************************************************/
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class EnteredOil extends AbstractAction
{
	private static EnteredOil enteredOil;
	private GameWorld target;
	private Car car;
	
	//hidden constructor
	private EnteredOil() 
	{
		super("Entered Oil Slick");
	}
	
	//returns singleton instance of enteredOil
	public static EnteredOil getEnteredOil()
	{
		if(enteredOil == null)
			enteredOil = new EnteredOil();
		return enteredOil;
	}
	

	public void actionPerformed(ActionEvent e) 
	{
		GameCollection gC = target.getCollection();
		Iterator i = gC.getIterator();
		
		while(i.hasNext()) //look for the player car
		{
			Object o = i.getNext();
			if(o instanceof Car)
			{
				if(((Car)o).getType())
				{
					((Car)o).setTractionFlag(false); //set traction
				}
			}
		}
		target.notifyObservers();
	}
	
	//here is a private action for when a specific car is passed.
	public void privateAction()
	{
		GameCollection gC = target.getCollection();
		Iterator i = gC.getIterator();
		
		while(i.hasNext()) //look for the player car
		{
			Object o = i.getNext();
			if(o instanceof Car)
			{
				if(((Car)o).getType())
				{
					((Car)o).setTractionFlag(false); //set traction
				}
			}
		}
		target.notifyObservers();
		
	}
	
	public void setTarget(GameWorld w)
	{
		target = w;
	}
	
	public void setTarget(Car c)
	{
		car = c;
	}


	
}
