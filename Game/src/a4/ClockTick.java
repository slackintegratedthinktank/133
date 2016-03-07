package a4;
import java.applet.*;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	ClockTick
 * Purpose: Action class that advances time and moves the moveable objects
 * 
 *****************************************************************************************************************************/
public class ClockTick extends AbstractAction
{
	private static ClockTick clockTick;
	private GameWorld target;
	private float elapsedMsec, wheelTimer;
	private float lastHeading;
	private AudioClip deathSound;
	
	private ClockTick() 
	{
		super("Tick");
		elapsedMsec = 20;
		lastHeading = 0;
		wheelTimer = 20;
		try
		{
			//grab our audio file
			String seperator = File.separator;
			String fileName = "." + seperator + "sounds" + seperator + "death.wav";
			File file = new File(fileName);
			if(file.exists())
				deathSound = Applet.newAudioClip(file.toURI().toURL());
			else
				throw new RuntimeException("Sound: file not found: " + file.toURI().toURL());
		}
		catch(Exception e)
		{
			throw new RuntimeException("Sound: malformed URL: " + e);	
		}
	}
	
	public static ClockTick getClockTick()
	{
		if(clockTick == null)
			clockTick = new ClockTick();
		return clockTick;
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		elapsedMsec = 20;
		wheelTimer = wheelTimer + 20;
		
		GameCollection gC = target.getCollection();
		Iterator i = gC.getIterator();
		Car car = null;
		while(i.hasNext())
		{
			Object o = i.getNext();
			if(o instanceof Car)
			{
				car = (Car)o;
				
				if(car.getTractionFlag()) //make sure the car has traction
				{
					car.setHeading(car.getHeading() + car.getSteeringDirection()); //update the heading if so
				}
				if(lastHeading == car.getHeading() && car.getType() && wheelTimer > 300)
				{
					wheelTimer = 20;
					Wheel[] wheels = car.getWheels();
					for(Wheel w : wheels)
					{
						if(w.getStatus())
							w.resetRotation();
					}
				}
				car.setSteeringDirection(0); //reset the steering direction
				car.move(elapsedMsec); //move the car
				car.setFuelLevel((float)(car.getFuelLevel() - .1)); //remove fuel
				if(car.getFuelLevel() <= 0 && car.getType()) //check for fuel level and reset the car if empty.
				{
					target.resetCar(car);
					car.emptyCollisions();
					if(car.getType() && (target.getLives() >0)) //if its the players car
					{
						JOptionPane.showMessageDialog(null, "You ran out of gas!!");
						target.setLives(target.getLives()-1);
						if(target.getSound())
							deathSound.play();
					}
					else if(target.getLives() == 0) //here we account for the final blow
					{
						if(target.getSound())
							deathSound.play();
						JOptionPane.showMessageDialog(null, "You are out of lives!");
						System.exit(0);
					}
				}
				if(car.getType())
					lastHeading = car.getHeading();
			}
			else if(o instanceof Bird)
				((Bird) o).move(elapsedMsec);
			else if(o instanceof Shockwave)
			{
				if(((Shockwave)o).getTime() < 20000)
					((Shockwave)o).move(elapsedMsec);
				else
					target.getCollection().remove(o, i);
			}

		}

		//find the users car, we will need it later
		i = gC.getIterator();
		while(i.hasNext())
		{
			Object o = i.getNext();
			if(o instanceof Car && !(o instanceof NPC))
				car = (Car)o;
		}
		
		//here we check for collsions. we only need to check moving objects.
		i = gC.getIterator();
		boolean collide;
		while(i.hasNext())
		{
			ICollide d = (ICollide)i.getNext();
			if(d instanceof Bird) //here is the bird collision. we test it in the Bird class.
			{
				collide = d.collidesWith(car);
				if(collide)
					d.handleCollision(car);
				else
				{
					if(((Bird)d).hasCollision(car))
						((Bird)d).removeCollision(car);
				}
			}
			else if(d instanceof NPC) //here is an NPC collision with a pylon.
			{
				NPC npc = ((NPC)d);
				Iterator npcIterator = gC.getIterator();
				while(npcIterator.hasNext())
				{
					ICollide next = (ICollide) npcIterator.getNext();
					if(next instanceof Pylons)
					{
						collide = npc.collidesWith(next);
						if(collide)
							npc.handleCollision(next);
					}
				}
			}
			//here we are checking the users car for collisions.
				collide = car.collidesWith(d);
				if(collide)
					car.handleCollision(d);	
				else
				{
					if(car.hasCollision(d))
						car.removeCollision(d);
				}
		}
		
		//here we check if the user has won
		
		Iterator winning = gC.getIterator();
		boolean notWinner = false;
		while(winning.hasNext())
		{
			Object o = winning.getNext();
			if(o instanceof Pylons)
			{
				if(((Pylons)o).getSequenceNumber()>target.getLastPylon())
					notWinner = true;
			}
		}
		if(!notWinner)
		{
			JOptionPane.showMessageDialog(null, "You Won!");
			System.exit(0);
		}
		
		
		target.setClockTime(); //progress the clock
		target.notifyObservers();
	}
	
	
	public void setTarget(GameWorld w)
	{
		target = w;
	}
	
}
