package a4;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	GameWorldProxy
 * Purpose: GameWorldProxy exists to shield GameWorld from being modified by its observers. all methods have been overridden
 * 			and only pass along values and objects when allawable;
 * 
 *****************************************************************************************************************************/
public class GameWorldProxy implements IGameWorld, Observable
{

	private GameWorld realGameWorld;
	
	public GameWorldProxy(GameWorld gw)
	{
		realGameWorld = gw;
	}
	
	public void initLayout() {}

	public float getLives() 
	{
		return realGameWorld.getLives();
	}

	public void setLives(float l) {}

	public void setLastPylon(Float p) {}

	public float getTurnLimit() 
	{
		return realGameWorld.getTurnLimit();
	}

	public void setTurnLimit(float t) {}

	public float getClockTime() 
	{
		return realGameWorld.getClockTime();
	}

	public void setClockTime() {}

	public float getLastPylon() 
	{
		return realGameWorld.getLastPylon();
	}

	public boolean getSound() 
	{
		return realGameWorld.getSound();
	}

	public void setSound(boolean s) {}

	public GameCollection getCollection() 
	{
		return realGameWorld.getCollection();
	}

	@Override
	public void addObserver(Observer o) {}

	@Override
	public void notifyObservers() {}

	public boolean isPlaying() 
	{
		return realGameWorld.isPlaying();
	}

}
