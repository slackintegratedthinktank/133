package a4;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	ClockTick
 * Purpose: Strategy class that aims the car at the players car
 * 
 *****************************************************************************************************************************/
public class DirectStrategy implements Strategy
{
	private GameCollection g;
	public DirectStrategy()
	{
		
	}
	public DirectStrategy(GameCollection gC)
	{
		g = gC;
	}
	public void apply(NPC c, Car player) 
	{
		
		//here we gather the required locations and differences.
		float px = (float) player.getTranslation().getTranslateX();
		float py = (float) player.getTranslation().getTranslateY();
		float cx = (float) c.getTranslation().getTranslateX();
		float cy = (float) c.getTranslation().getTranslateY();
		float quad = 0;
		float run = px - cx;
		float rise = py - cy;
			
		//determine which quadrant the player car is in from the NPC
		if(run > 0 && rise > 0)
			quad = 1;
		else if(run > 0 && rise < 0)
			quad = 2;
		else if(run < 0 && rise < 0)
			quad = 3;
		else if(run < 0 && rise > 0)
			quad = 4;
		
		
		//determine the angle from the y axis we are turning.
		run = Math.abs(run);
		rise = Math.abs(rise);
		float angle = (float) (Math.atan(run/rise));
		angle = (float) (angle * 180/3.14);
		
		//assign a heading based on the players quadrant.
		if(quad == 1)
			c.setHeading(angle);
		else if(quad == 2)
			c.setHeading(180-angle);
		else if(quad == 3)
			c.setHeading(180+angle);
		else if(quad == 4)
			c.setHeading(360-angle);
		
		
		c.setSpeed(25);
	}
	
	public String toString()
	{
		return(" Strategy is Direct");
	}
}
