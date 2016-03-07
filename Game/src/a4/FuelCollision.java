package a4;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Random;

import javax.swing.AbstractAction;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	Fuel Collision
 * Purpose: Action class that 
 * 
 *****************************************************************************************************************************/
public class FuelCollision extends AbstractAction
{
	private static FuelCollision fuelCollision;
	private GameWorld target;
	private Car car;
	private FuelCans fuelCan;
	private AudioClip fuelSound;
	
	private FuelCollision() 
	{
		super("Pickup Fuel");
		//here we have our audio clip for picking up fuel
		try
		{
			String seperator = File.separator;
			String fileName = "." + seperator + "sounds" + seperator + "fuel.wav";
			File file = new File(fileName);
			if(file.exists())
				fuelSound = Applet.newAudioClip(file.toURI().toURL());
			else
				throw new RuntimeException("Sound: file not found: " + file.toURI().toURL());
		}
		catch(Exception e)
		{
			throw new RuntimeException("Sound: malformed URL: " + e);	
		}
	}
	
	public static FuelCollision getFuelCollision()
	{
		if(fuelCollision == null)
			fuelCollision = new FuelCollision();
		return fuelCollision;
	}

	public void actionPerformed(ActionEvent e) 
	{
		
		GameCollection gC = target.getCollection();
		Iterator i = gC.getIterator();
		float fuelSize = 0;
		boolean escape = true;
		while(i.hasNext() && escape) 
		{
			Object o = i.getNext();
			
			
			if(o instanceof FuelCans)
			{
				fuelSize = ((FuelCans)o).getSize();

				gC.remove(o, null); //remove the fuelcan from the arraylist

				//here I create a new fuel can and add it to the arraylist.
				FuelCans n = new FuelCans((int)(Math.random() * 1000), (int)(Math.random()*1000));
				n.setSize((int)(Math.random()*100));
				n.setColor(new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat());	
				n.setSize((int)(Math.random() * 50));
				gC.add(n);
				escape = false; //escape
			}
		}
		
		i = gC.getIterator();
		while(i.hasNext() && !escape)
		{
			Object o = i.getNext();
			if(o instanceof Car)
			{
				if(((Car)o).getType())
				{
					((Car)o).setFuelLevel(((Car)o).getFuelLevel() + fuelSize);
					if(((Car)o).getFuelLevel() > 100)
						((Car)o).setFuelLevel(100);
					escape = false;
				}
				
			}
			
			
		}
		target.notifyObservers();
	}
	
	//here i create a new method for when a specific fuel can and car and passed.
	public void privateAction()
	{
		if(target.getSound())
			fuelSound.play();
		
		float fuelSize = fuelCan.getSize();
		GameCollection gC = target.getCollection();
		gC.remove(fuelCan, null); //remove the fuelcan from the arraylist

		//here I create a new fuel can and add it to the arraylist.
		FuelCans n = new FuelCans((int)(Math.random() * 1000), (int)(Math.random()*1000));
		n.setSize((int)(Math.random()*100));
		n.setColor(new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat());	
		n.setSize((int)(Math.random() * 50));
		gC.add(n);
		
		car.setFuelLevel(car.getFuelLevel() + fuelSize);
		if(car.getFuelLevel() > 100)
			car.setFuelLevel(100);
		
	}
	public void setTarget(GameWorld w)
	{
		target = w;
	}
	
	public void setTarget(Car c, FuelCans fC)
	{
		car = c;
		fuelCan = fC;
		
	}
}
