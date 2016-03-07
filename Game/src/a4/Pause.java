package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	Pause
 * Purpose: The Action for pausing the game whick stops the timer and enables extra features.
 * 
 *****************************************************************************************************************************/
public class Pause extends AbstractAction
{
	private static Pause pause;
	private GameWorld target;
	private Pause()
	{
		super("Pause");
	}
	
	public static Pause getPause()
	{
		if(pause == null)
			pause = new Pause();
		return pause;
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		if(!target.getGame().getTimer())
			target.getGame().setTimer(false);
		target.setPlaying();
		target.notifyObservers();
		
	}
	public void setTarget(GameWorld gw)
	{
		target = gw;
	}

}
