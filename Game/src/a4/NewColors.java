package a4;
import java.awt.event.ActionEvent;
import java.util.Random;

import javax.swing.AbstractAction;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	NewColors
 * Purpose: This action creates new colors for each of our objects that can change colors.
 * 
 *****************************************************************************************************************************/
public class NewColors extends AbstractAction
{
	private static NewColors newColors;
	private GameWorld target;
	
	//hidden constructor
	private NewColors() 
	{
		super("Get New Colors");
	}
	
	//returns our singleton instance of newColors.
	public static NewColors getNewColors()
	{
		if(newColors == null)
			newColors = new NewColors();
		return newColors;
	}
	

	public void actionPerformed(ActionEvent e) 
	{
		GameCollection collect = target.getCollection();
		Iterator i = collect.getIterator();
		while(i.hasNext())
		{
			Object o = i.getNext();
			if(o instanceof Car)
				((Car)o).setColor(new Random().nextFloat(),new Random().nextFloat(),new Random().nextFloat()); //assign car new color
			else if(o instanceof OilSlicks)
				((OilSlicks) o).setColor(new Random().nextFloat(),new Random().nextFloat(),new Random().nextFloat()); //assign each other object a new color except pylons
			else if(o instanceof FuelCans)
				((FuelCans) o).setColor(new Random().nextFloat(),new Random().nextFloat(),new Random().nextFloat());	
		}
		target.notifyObservers();
	}
	public void setTarget(GameWorld w)
	{
		target = w;
	}

}
