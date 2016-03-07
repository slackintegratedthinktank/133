package a4;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	ExitOil
 * Purpose: Action class that removes the tractionflag
 * 
 *****************************************************************************************************************************/
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class ExitOil extends AbstractAction
{
	private static ExitOil exitOil;
	private GameWorld target;
	private Car car;
	
	//hidden constructor
	private ExitOil() 
	{
		super("Exit Oil Slick");
	}
	
	//returns singleton instance of exitOil
	public static ExitOil getExitOil()
	{
		if(exitOil == null)
			exitOil = new ExitOil();
		return exitOil;
	}

	public void actionPerformed(ActionEvent e) 
	{
		GameCollection gC = target.getCollection();
		Iterator i = gC.getIterator();
		
		while(i.hasNext()) //look for our car
		{
			Object o = i.getNext();
			if(o instanceof Car)
			{
				if(((Car)o).getType())
				{
					((Car)o).setTractionFlag(true); //update tractionflag
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
	public void privateAction()
	{
		GameCollection gC = target.getCollection();
		Iterator i = gC.getIterator();
		
		while(i.hasNext()) //look for our car
		{
			Object o = i.getNext();
			if(o instanceof Car)
			{
				if(((Car)o).getType())
				{
					((Car)o).setTractionFlag(true); //update tractionflag
				}
			}
		}
	}
}
