package a4;
import java.awt.event.ActionEvent;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	BirdCollision
 * Purpose: Action class that handles a collision with a bird
 * 
 *****************************************************************************************************************************/
public class BirdCollision extends AbstractAction
{
	private static BirdCollision birdCollision;
	private GameWorld target;
	private Bird b;
	private Car c;
	
	//hidden constructor
	private BirdCollision() 
	{
		super("Bird Collision");
	}
	
	//returns a singleton instance of birdcollision
	public static BirdCollision getBirdCollision()
	{
		if(birdCollision == null)
			birdCollision = new BirdCollision();
		return birdCollision;
		
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		
		//look through the collection for the car 
		GameCollection gC = target.getCollection();
		Iterator i = gC.getIterator();
		while(i.hasNext())
		{
			Object o = i.getNext();
			if(o instanceof Car)
				if(((Car)o).getType())
				{
					if(((Car)o).getDamageLevel() < 95) //update the damage
					{
						((Car)o).collision(5); //update the max speed and the damage level
						if(((Car)o).getSpeed() > ((Car)o).getMaximumSpeed())
							((Car)o).setSpeed(((Car)o).getMaximumSpeed());
					}
					else //notify of max damage and reset the car
					{
						((Car)o).collision(5);
						target.setLives(target.getLives()-1);
						target.resetCar(((Car)o));
						JOptionPane.showMessageDialog(null, "Car Totalled!!!");
					}
				}
		}
		target.notifyObservers();
		
	}
	
	public void privateAction()
	{
		if(c.getDamageLevel() < 95) //update the damage
		{
			c.collision(5); //update the max speed and the damage level
			if(c.getSpeed() > c.getMaximumSpeed())
				c.setSpeed(c.getMaximumSpeed());
			
			b.addCollision(target.addShockwave());
		}
		else //notify of max damage and reset the car
		{
			c.collision(5);
			target.setLives(target.getLives()-1);
			target.resetCar(c);
			JOptionPane.showMessageDialog(null, "Car Totalled!!!");
		}
	}
	

	public void setTarget(GameWorld w)
	{
		target = w;
	}

	public void setTarget(Bird bird, ICollide other) 
	{
		b = bird;
		c = (Car)other;
		
	}
}
