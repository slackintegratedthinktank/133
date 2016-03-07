package a4;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	Play
 * Purpose: The PLay action which starts the clock back up and unselects objects.
 * 
 *****************************************************************************************************************************/
public class Play extends AbstractAction
{
	private static Play play;
	private GameWorld target;
	private Play()
	{
		super("Play");
	}
	public static Play getPlay()
	{
		if(play == null)
			play = new Play();
		return play;
	}

	public void actionPerformed(ActionEvent e) 
	{
		if(!target.getGame().getTimer())
			target.getGame().setTimer(true);
		target.setPlaying();
		
		GameCollection gC = target.getCollection();
		Iterator i = gC.getIterator();
		
		while(i.hasNext())
		{
			ISelectable o = (ISelectable) i.getNext();
			if(o.isSelected())
				o.setSelected(false);
		}
		target.notifyObservers();
		
	}
	
	public void setTarget(GameWorld gw)
	{
		target = gw;
	}
	

}
