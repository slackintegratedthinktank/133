package a4;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	AddFuelCan
 * Purpose: Action class that allows the addition of a fuel can to the game world.
 * 
 *****************************************************************************************************************************/
public class AddFuelCan extends AbstractAction implements MouseListener
{
	private static AddFuelCan addFuel;
	private GameWorld target;
	
	private AddFuelCan()
	{
		super("Add Fuel");
	}
	
	public static AddFuelCan getAddFuelCan()
	{
		if(addFuel == null)
			addFuel = new AddFuelCan();
		return addFuel;
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		//here w make sure that they game is paused then modify the mouse listeners to accomodate this action
		if(!target.isPlaying())
		{
			target.getMap().removeMouseListener(target.getMap());
			target.getGame().getMapView().addMouseListener(this);
		}
	}
	
	public void setTarget(GameWorld gW)
	{
		target = gW;
	}

	//here we handle the mouse clicks once the user has selected the add fuel cans option.
	public void mouseClicked(MouseEvent e) 
	{
		String s = "";
		try
		{
			s = JOptionPane.showInputDialog("What size fuel can would you like?"); //prompt for the size of the can
			if(s.matches("[1234567890][1234567890]"))
				if(Integer.parseInt(s) > target.getLastPylon())
				{
					FuelCans f = new FuelCans((float)e.getX(), (float)e.getY());
					f.setSize(Float.parseFloat(s));
					f.setColor(new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat());
					target.getCollection().add(f);

				} //create a new fuel can and exit
		}
		catch(Exception e1)
		{
		}
		target.notifyObservers(); //here we update the observers and reset the listener.
		target.getGame().getMapView().removeMouseListener(this);
		target.getMap().addMouseListener(target.getMap());
	}

	//here are the unused mouseEvent methods.
	public void mousePressed(MouseEvent e) {}
	
	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}
	

}
