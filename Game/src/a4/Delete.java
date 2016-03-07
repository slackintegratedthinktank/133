package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	Delete
 * Purpose: Abstract Action class for handling the delete action. this will remove the selected object from the game.
 * 
 *****************************************************************************************************************************/
public class Delete extends AbstractAction
{
	private static Delete delete;
	private GameWorld target;
	
	private Delete()
	{
		super("Delete");
	}
	
	public static Delete getDelete()
	{
		if(delete == null)
			delete = new Delete();
		return delete;
	}

	//this removes all selected items from the game.
	public void actionPerformed(ActionEvent e) 
	{
		GameCollection gC = target.getCollection();
		Iterator i = gC.getIterator();
		while(i.hasNext())
		{
			ISelectable o = (ISelectable) i.getNext();
			if(o.isSelected())
				gC.remove(o, i);
		}
		target.notifyObservers();
	}
	
	public void setTarget(GameWorld gw)
	{
		target = gw;
	}
	
	
	
	
}
