package a4;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	Observable
 * Purpose: This is an interface defining what an observable class needs
 * 
 *****************************************************************************************************************************/
public interface Observable 
{
	public void addObserver(Observer o);
	public void notifyObservers();
}
