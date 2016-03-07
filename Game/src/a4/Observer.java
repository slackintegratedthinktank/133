package a4;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	Observer
 * Purpose: This is an interface declaring what an observer needs to function
 * 
 *****************************************************************************************************************************/
public interface Observer 
{
	public void update(Observable o, Object arg);
}
