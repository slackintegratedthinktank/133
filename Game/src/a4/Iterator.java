package a4;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	iterator
 * Purpose: interface to create an iterator
 * 
 *****************************************************************************************************************************/
public interface Iterator 
{
	public boolean hasNext();
	public Object getNext();
	public void decrement();
}
