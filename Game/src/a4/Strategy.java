package a4;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	Strategy
 * Purpose: This is our Strategy interface, it states what is required to be considered a strategy.
 * 
 *****************************************************************************************************************************/
public interface Strategy 
{
	public void apply(NPC c, Car player);
}
