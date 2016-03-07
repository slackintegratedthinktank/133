package a4;
import java.util.Vector;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	gameCollectio
 * Purpose: This is our collection of game objects
 * 
 *****************************************************************************************************************************/
public class GameCollection implements Collection
{
	//create our data structure
	private Vector<Object> theCollection;
	public GameCollection()
	{
		theCollection = new Vector<Object>();
	}
	
	//add an object to the collection
	public void add(Object newObject) 
	{
		theCollection.addElement(newObject);
		
	}
	
	//remove an object from the collection
	public void remove(Object remove, Iterator i)
	{
		theCollection.remove(remove);
		if(!(i==null))
			i.decrement();
		
	}
	
	
	//return an iterator for the collection
	public Iterator getIterator() 
	{
		
		return new GameCollectionIterator();
	}
	
	//our private class that creates an iterator that can be used to sten through the collection.
	private class GameCollectionIterator implements Iterator
	{
		private int currElementIndex;
		public GameCollectionIterator()
		{
			currElementIndex = -1;
		}
		
		//checks if there are more values.
		public boolean hasNext() 
		{
			if(theCollection.size() <= 0 || currElementIndex == theCollection.size() -1)
				return false;
			return true;
		}

		//returns the object thats next in the collection.
		public Object getNext() 
		{
			return (theCollection.get(++currElementIndex));
		}

		
		public void decrement() 
		{
			--currElementIndex;
			
		}
		
	}
}
