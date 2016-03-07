package a4;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	IGameWorld
 * Purpose: To ensure that the GameWorldProxy class implements all methods.. 
 * 
 *****************************************************************************************************************************/
interface IGameWorld {

	public void initLayout();

	//return the number of lives remaining. used by Game for looping.
	public float getLives();

	public void setLives(float l);

	public void setLastPylon(Float p);

	public float getTurnLimit();

	public void setTurnLimit(float t);

	public float getClockTime();

	public void setClockTime();

	public float getLastPylon();

	public boolean getSound();

	public void setSound(boolean s);

	public GameCollection getCollection();

}