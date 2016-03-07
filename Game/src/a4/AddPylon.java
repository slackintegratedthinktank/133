package a4;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	AddPylon
 * Purpose: Action class that adds a Pylon of the users choosing
 * 
 *****************************************************************************************************************************/
public class AddPylon extends AbstractAction implements MouseListener
{
	private static AddPylon addPylon;
	private GameWorld target;
	
	private AddPylon()
	{
		super("Add Pylon");
	}
	
	public static AddPylon getAddPylon()
	{
		if(addPylon == null)
			addPylon = new AddPylon();
		return addPylon;
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		//here we ensure the game is paused and modify the mouse listeners to allow this action.
		if(!target.isPlaying())
		{
			target.getGame().getMapView().removeMouseListener(target.getMap());
			target.getGame().getMapView().addMouseListener(this);
		}
	}
	
	public void setTarget(GameWorld gW)
	{
		target = gW;
	}

	//here we handle the mouse clicks once the user has selected the add pylon option.
	public void mouseClicked(MouseEvent e) 
	{

		String s = "";
		try 
		{
			s = s + JOptionPane.showInputDialog("What Pylon number would you like?"); //prompt for the pylon number
				if(s.matches("[1234567890]"))
					if(Integer.parseInt(s) > target.getLastPylon())
					{
						AffineTransform rotate = target.getMap().getVTM();
						Point2D orig = new Point();
						Point2D newpt = new Point();
						orig.setLocation(e.getX(), e.getY());
						rotate.transform(orig, newpt);
						
						Pylons p = new Pylons((float)newpt.getX(), (float)newpt.getY(), Integer.parseInt(s));
						p.setColor(new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat());
						target.getCollection().add(p);
						
					} //create a new pylon and exit
		} 
		catch (Exception e1) 
		{
		}
		target.notifyObservers(); //here we update the observers and reset the listener.
		target.getGame().getMapView().removeMouseListener(this);
		target.getGame().getMapView().addMouseListener(target.getMap());
	}


	//here are our unused mouseListener methods.
	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}
	

}
