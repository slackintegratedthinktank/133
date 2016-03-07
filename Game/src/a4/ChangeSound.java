package a4;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	Sound
 * Purpose: This is our Sound action class. it changes the settings for sound in our program.
 * 
 *****************************************************************************************************************************/
public class ChangeSound extends AbstractAction
{
	private static ChangeSound sound;
	private GameWorld target;
	
	//hidden constructor
	private ChangeSound()
	{
		super("Sound");
	}
	
	//returns a singleton instance of sound
	public static ChangeSound getSound()
	{
		if(sound == null)
			sound = new ChangeSound();
		return sound;
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		//toggles sound in GameWorld.
		target.setSound(!target.getSound());
		if(target.getSound())
			target.getAudio().loop();
		else
			target.getAudio().stop();
		target.notifyObservers();
		
	}
	public void setTarget(GameWorld w)
	{
		target = w;
	}
	
}
