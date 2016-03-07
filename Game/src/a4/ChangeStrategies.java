package a4;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	Change Strategies
 * Purpose: Action class that changes an NPC car strategy between one of two, Direct or Race.
 * 
 *****************************************************************************************************************************/
public class ChangeStrategies extends AbstractAction
{
	private static ChangeStrategies changeStrategies;
	private GameWorld target;
	
	private ChangeStrategies()
	{
		super("Change Strategies");
	}
	
	public static ChangeStrategies getChangeStrategies()
	{
		if(changeStrategies == null)
			changeStrategies = new ChangeStrategies();
		return changeStrategies;
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		GameCollection gC = target.getCollection();
		Iterator i = gC.getIterator();
		Car player = new Car();
		//here I step through the collection and switch the strategies for each NPC
		while(i.hasNext())
		{
			Object o = i.getNext();
			if(o instanceof Car)
				if(((Car)o).getType())
					player = (Car)o;
			if(o instanceof NPC)
			{
				NPC c = (NPC) o;
				if(c.getStrategy() instanceof DirectStrategy)
				{
					c.setStrategy(new RaceStrategy(gC));
					c.getStrategy().apply(c,player);
				}
				else
					c.setStrategy(new DirectStrategy(gC));	
				c.getStrategy().apply(c, player);
			}
		}
		target.notifyObservers();
	}
	public void setTarget(GameWorld w)
	{
		target = w;
	}

	public void privateAction() 
	{
		GameCollection gC = target.getCollection();
		Iterator i = gC.getIterator();
		Car player = new Car();
		//here I step through the collection and switch the strategies for each NPC
		while(i.hasNext())
		{
			Object o = i.getNext();
			if(o instanceof Car)
				if(((Car)o).getType())
					player = (Car)o;
			if(o instanceof NPC)
			{
				NPC c = (NPC) o;
				if(c.getStrategy() instanceof DirectStrategy)
				{
					c.setStrategy(new RaceStrategy(gC));
					c.getStrategy().apply(c,player);
				}
				else
					c.setStrategy(new DirectStrategy(gC));	
				c.getStrategy().apply(c, player);
			}
		}
		target.notifyObservers();
		
	}

}
