package a4;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	NPC
 * Purpose: This extends our Car class and adds the ability for a strategy to be assigned.
 * 
 *****************************************************************************************************************************/
public class NPC extends Car implements IDrawable
{
	private Strategy strategy;
	private ChangeStrategies cS;
	private float elapsedTime;
	
	public NPC()
	{
		super(); //creates a car as a NPC
		cS = ChangeStrategies.getChangeStrategies();
		this.getFront().translate(-8, 0);
		this.getRear().translate(-8, 0);
		
		
	}
	
	public void setStrategy(Strategy s)
	{
		strategy = s;
	}
	public void invokeStrategy(Car p)
	{
		strategy.apply(this, p);
	}
	public String toString()
	{
		return("NPC "+ super.toString() + strategy.toString());
	}
	
	public void draw(Graphics2D g)
	{
		
		AffineTransform backup = g.getTransform();
		
		this.getRotation().setToIdentity();
		this.rotate(Math.toRadians(-1*this.getHeading()));
		
		
		g.transform(this.getTranslation());
		g.transform(this.getRotation());
		g.transform(this.getScale());
		g.setColor(getColor());
		
			g.drawRect(((int)(0 - (getWidth()/2))), ((int)(0 - (getLength()/2))), ((int)this.getWidth()), ((int)this.getLength()));
			this.getFront().Draw(g);
			this.getRear().Draw(g);
			
			if(this.isSelected())
			{
				g.setColor(Color.black);
				g.drawLine((int)(0 - this.getWidth()/2), 0, (int)(0 + this.getWidth()/2), 0);
				g.drawLine(0, (int)(0 - this.getLength()/2), 0, (int)(0 + this.getLength()/2));
			}
			
			g.setTransform(backup);
	}
	
	public void move(float elapsedMsec)
	{
		elapsedTime = elapsedTime + elapsedMsec;
		if(elapsedTime / 1000 >= 10)
			elapsedTime = 0;
		if(elapsedTime == 0)
			cS.privateAction();
		
		super.move(elapsedMsec);
		if(this.getTranslation().getTranslateX() > 1081)
			this.getTranslation().setToTranslation(1081, this.getTranslation().getTranslateY());
		if(this.getTranslation().getTranslateX() < 0)
			this.getTranslation().setToTranslation(1, this.getTranslation().getTranslateY());
		if(this.getTranslation().getTranslateY() > 826)
			this.getTranslation().setToTranslation(this.getTranslation().getTranslateX(),825);
		if(this.getTranslation().getTranslateY() < 0)
			this.getTranslation().setToTranslation(this.getTranslation().getTranslateX(),0);
	}
	
	public Strategy getStrategy() 
	{
		return strategy;
	}
	
}
