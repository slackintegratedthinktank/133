package a4;
import java.awt.event.ActionEvent;
import java.util.Random;

import javax.swing.AbstractAction;

/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	AddOil
 * Purpose: Action class that adds an oil slick to the game
 * 
 *****************************************************************************************************************************/
public class AddOil extends AbstractAction
{
	private static AddOil addOil;
	private GameWorld target;
	
	//private constructor
	private AddOil() 
	{
		super("Add Oil Slick");
	}
	
	//returns the singleton instance of addoil
	public static AddOil getAddOil()
	{
		if(addOil == null)
			addOil = new AddOil();
		return addOil;
	}
	
	
	public void actionPerformed(ActionEvent e) 
	{
		//create a new oil slick and add it to the collection.
		OilSlicks oilSlicks = new OilSlicks((int)(Math.random() * 1000), (int)(Math.random()*1000));
		oilSlicks.setLength((int)(Math.random() * 100));
		oilSlicks.setWidth((int)(Math.random() * 100));
		oilSlicks.setColor(new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat());
		target.getCollection().add(oilSlicks);
		target.notifyObservers();
	}
	public void setTarget(GameWorld w)
	{
		target = w;
	}

}
