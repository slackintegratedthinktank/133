package a4;
import javax.swing.*;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	MenuBar
 * Purpose: This is our MenuBar object. it contains the menu items and is linked to actions based on the menu's input.
 * 
 *****************************************************************************************************************************/
public class MenuBar extends JMenuBar	
{
	private JCheckBoxMenuItem sound;
	private ChangeSound sounds;
	private Quit quits;
	private About abouts;
	private AddOil addOils;
	private FuelCollision fuelCollision;
	private NewColors newColor;
	
	
	public MenuBar(GameWorld w)
	{
		//here we create out menu item and its objects and add Actions that we created
		JMenu file = new JMenu("File");
		JMenuItem newGame = new JMenuItem("New");
		JMenuItem save = new JMenuItem("Save");
		file.add(newGame);
		file.add(save);
		
		sound = new JCheckBoxMenuItem("Sound");
		sound.setSelected(w.getSound());
		file.add(sound);
		sounds = ChangeSound.getSound();
		sounds.setTarget(w);
		sound.setAction(sounds);
		
		JMenuItem about = new JMenuItem("About");
		file.add(about);
		abouts = About.getAbout();
		about.setAction(abouts);
		
		
		JMenuItem quit = new JMenuItem("Quit");
		file.add(quit);
		quits = Quit.getQuit();
		quit.setAction(quits);
		
		this.add(file);		
		
		
		
		//same with the commands menu
		JMenu commands = new JMenu("Commands");
		
		JMenuItem addOil = new JMenuItem("Add Oil Slick");
		commands.add(addOil);
		addOil.setMnemonic('o');
		addOils = AddOil.getAddOil();
		addOils.setTarget(w);
		addOil.setAction(addOils);
		
		
		JMenuItem pickupFuel = new JMenuItem();
		pickupFuel.setText("Pickup Fuel");
		commands.add(pickupFuel);
		pickupFuel.setMnemonic('f');
		fuelCollision = FuelCollision.getFuelCollision();
		fuelCollision.setTarget(w);
		pickupFuel.setAction(fuelCollision);
		
		JMenuItem newColors = new JMenuItem("New Colors");
		commands.add(newColors);
		newColors.setMnemonic('n');
		newColor = NewColors.getNewColors();
		newColor.setTarget(w);
		newColors.setAction(newColor);
		
		this.add(commands);
	}
	

}
