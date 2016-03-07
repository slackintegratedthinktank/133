package a4;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.border.LineBorder;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	ScoreView
 * Purpose: This is our Score view. It displays the status of some of our objects for the gameworld and car.
 * 
 *****************************************************************************************************************************/
public class ScoreView extends JPanel implements Observer 
{
	private JLabel time;
	private JLabel lives;
	private JLabel pylon;
	private JLabel fuel;
	private JLabel damage;
	private JLabel sound;
	
	public ScoreView(Observable world)
	{
		//here we register as an observer and add our items to the jpanel
		world.addObserver(this);
		time = new JLabel("Time: ");
		lives = new JLabel("Lives Left: ");
		pylon = new JLabel("Highest Player Pylon: ");
		fuel = new JLabel("Player Fuel Remaining: ");
		damage = new JLabel("Player Damage Level: ");
		sound = new JLabel("Sound: ");
		this.setLayout(new FlowLayout());
		this.add(time);
		this.add(lives);
		this.add(pylon);
		this.add(fuel);
		this.add(damage);
		this.add(sound);
		this.setVisible(true);
		this.setBorder(new LineBorder(Color.blue, 2));
	}
	
	//here we update the jlabels with the information from the gameworld.
	public void update(Observable o, Object arg) 
	{
		GameWorldProxy gC = ((GameWorldProxy)o);
			
		time.setText("Time: " + (int)gC.getClockTime());
		lives.setText("Lives Left: " + (int)gC.getLives());
		pylon.setText("Highest Player Pylon: " + (int)gC.getLastPylon());
		
		GameCollection collection = gC.getCollection();
		Iterator i = collection.getIterator();
		while(i.hasNext())
		{
			Object n = i.getNext();
			if(n instanceof Car)
				if(((Car)n).getType()) //look for the player car and get info
				{
					fuel.setText("Player Fuel Remaining: " + (int)((Car)n).getFuelLevel());
					damage.setText("Player Damage Level: " + (int)((Car)n).getDamageLevel());
				}
		}
		
		if(gC.getSound())
			sound.setText("Sound: ON");
		else
			sound.setText("Sound: OFF");	
	}

}
