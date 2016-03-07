package a4;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	About
 * Purpose: Action class that displays basic information about the game and its creator (me)
 * 
 *****************************************************************************************************************************/

public class About extends AbstractAction
{
	private static About about;
	
	//constructor
	private About()
	{
		super("About");
	}
	
	//returns the singleton object about
	public static About getAbout()
	{
		if(about == null)
			about = new About();
		return about;
	}

	//displays a message pane containing the information.
	public void actionPerformed(ActionEvent e) 
	{
		
		JOptionPane.showMessageDialog(null, "Author: Jonathan Coffman Course: CSc133 Assignment 2");
		
	}
	
}
