package a4;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	iCollide
 * Purpose: iCollide outlines the necessities for a collidable object.
 * 
 *****************************************************************************************************************************/
public interface ICollide 
{
	public boolean collidesWith(ICollide other);
	public void handleCollision(ICollide other);
}
