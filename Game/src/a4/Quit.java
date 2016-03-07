package a4;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	Quit
 * Purpose: This is our Quit action class that prompts the user to confirm and then quits the program.
 * 
 *****************************************************************************************************************************/
public class Quit extends AbstractAction
{
	private static Quit quit;
	
	//hidden constructor
	private Quit()
	{
		super("Quit");
	}
	
	//returns our singleton instance of quit
	public static Quit getQuit()
	{
		if(quit == null)
			quit = new Quit();
		return quit;
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		//confirms and quits
		int i = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?","Quit", JOptionPane.YES_NO_OPTION);
		if(i == 0)
			System.exit(0);
		
	}
	
}
