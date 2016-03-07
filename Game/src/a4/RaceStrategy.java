package a4;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	RaceStrategy
 * Purpose: This is our Race Strategy object that is used to set NPC's on a course to race the player
 * 
 *****************************************************************************************************************************/
public class RaceStrategy implements Strategy
{
	private GameCollection g;
	public RaceStrategy()
	{
		
	}
	public RaceStrategy(GameCollection gC)
	{
		g = gC;
	}
	public void apply(NPC c, Car player) 
	{
		float pylon = c.getPylon();
		GameCollection gC = g;
		Iterator i = gC.getIterator();
		while(i.hasNext())
		{
			Object o = i.getNext();
			if(o instanceof Pylons)
			{
				float i1 = ((Pylons)o).getSequenceNumber();
				if(i1 == pylon+1)
				{
					Pylons p  = (Pylons)o;
					float px = (float) p.getTranslation().getTranslateX();
					float py = (float) p.getTranslation().getTranslateY();
					float cx = (float) c.getTranslation().getTranslateX();
					float cy = (float) c.getTranslation().getTranslateY();
					float quad = 0;
					float run = px - cx;
					float rise = py - cy;
						
					if(run > 0 && rise > 0)
						quad = 1;
					else if(run > 0 && rise < 0)
						quad = 2;
					else if(run < 0 && rise < 0)
						quad = 3;
					else if(run < 0 && rise > 0)
						quad = 4;
					
					run = Math.abs(run);
					rise = Math.abs(rise);
						
					float angle = (float) (Math.atan(run/rise));
					angle = (float) (angle * 180/3.14);
					
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
			}
		}
		
	}
	public String toString()
	{
		return(" Strategy is Race");
	}

}
