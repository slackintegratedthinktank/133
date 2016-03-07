package a4;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	CarCollision
 * Purpose: Action class that handles a collision with a car, it takes a random NPC and maps a collision with it.
 * 
 *****************************************************************************************************************************/
public class CarCollision extends AbstractAction
{
	private static CarCollision carCollision;
	private GameWorld target;
	private Car car;
	private NPC other;
	private AudioClip collisionSound;
	
	private CarCollision() 
	{
		super("Car Collision");
		
		//here we handle the audio for the collision.
		try
		{
			String seperator = File.separator;
			String fileName = "." + seperator + "sounds" + seperator + "car_crash.wav";
			File file = new File(fileName);
			if(file.exists())
				collisionSound = Applet.newAudioClip(file.toURI().toURL());
			else
				throw new RuntimeException("Sound: file not found: " + file.toURI().toURL());
		}
		catch(Exception e)
		{
			throw new RuntimeException("Sound: malformed URL: " + e);	
		}
	}
	
	public static CarCollision getCarCollision()
	{
		if(carCollision == null)
			carCollision = new CarCollision();
		return carCollision;
	}
	

	public void actionPerformed(ActionEvent e) 
	{
		GameCollection gC = target.getCollection();
		Iterator i = gC.getIterator();
		boolean npcChanged = false;
		int whichNPC = (int) (Math.random() *3);
		while(i.hasNext())
		{
			Object o = i.getNext();
			if(o instanceof Car)
			{
				if(((Car)o).getType())
				{
					if(((Car)o).getDamageLevel() < 95)
					{
						((Car)o).collision(5); //update the max speed and the damage level
						if(((Car)o).getSpeed() > ((Car)o).getMaximumSpeed())
							((Car)o).setSpeed(((Car)o).getMaximumSpeed());
						target.addShockwave();
					}
					else
					{
						((Car)o).collision(5);
						target.setLives(target.getLives()-1);
						target.resetCar(((Car)o));
						JOptionPane.showMessageDialog(null, "Car Totalled!!!");
						target.addShockwave();
					}
				}
				
				if(!(((Car)o).getType()) && !npcChanged) //damage a random NPC car
				{
					if (whichNPC == 0) 
					{
						if (((Car)o).getDamageLevel() < 99) 
						{
							((Car)o).collision(1); //update the max speed and the damage level
							if (((Car)o).getSpeed() > ((Car)o).getMaximumSpeed())
								((Car)o).setSpeed(((Car)o).getMaximumSpeed());
						}
						npcChanged = true;
					}
					else
						whichNPC--;
				}
			}
		}
		target.notifyObservers();
	}

	//private action for when a car and NPC are passed
	public void privateAction() 
	{
		if(target.getSound())
		{
			collisionSound.play();
		}
		
		if(car.getDamageLevel() < 95)
		{
			car.collision(5); //update the max speed and the damage level
			if(car.getSpeed() > car.getMaximumSpeed())
				car.setSpeed(car.getMaximumSpeed());
			car.addCollision(target.addShockwave());
		}
		else
		{
			car.collision(5);
			target.setLives(target.getLives()-1);
			target.resetCar(car);
			car.addCollision(target.addShockwave());
			JOptionPane.showMessageDialog(null, "Car Totalled!!!");
		}
		if (other.getDamageLevel() < 99) 
		{
			other.collision(1); //update the max speed and the damage level
			if (other.getSpeed() > other.getMaximumSpeed())
				other.setSpeed(other.getMaximumSpeed());
		}
		if(Math.random() * 4 < 2)
		{
			GameCollection gC = target.getCollection();
			Random rand = new Random();
			OilSlicks oilSlicks = new OilSlicks((float)car.getTranslation().getTranslateX(), (float)car.getTranslation().getTranslateY());
			oilSlicks.setLength((int)(Math.random() * 100));
			oilSlicks.setWidth((int)(Math.random() * 100));
			oilSlicks.setColor(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
			gC.add(oilSlicks);
			
		}

	}
	public void setTarget(GameWorld w)
	{
		target = w;
	}

	public void setTarget(Car c, NPC o) 
	{
		car = c;
		other = o;
		
	}

	
}
