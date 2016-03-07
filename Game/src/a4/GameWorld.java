package a4;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	GameWorld
 * Purpose: To act as the model that contains all the theGameCollection needed to run the program. this includes Cars, Pylons, 
 * 			FuelCans, OilSlicks, and Birds. These theGameCollection are manipulated using this class via the Game() class. 
 * 
 *****************************************************************************************************************************/
class GameWorld implements Observable, IGameWorld
{
	//games theGameCollection and state variables
	private float clockTime, livesRemaining, lastPylon, turnLimit;
	private Boolean sound;
	//Object array
	private ArrayList<Observer> observers;
	private GameCollection theGameCollection;
	private GameWorldProxy gwp;
	private AudioClip gameplaySound;
	private Game game;
	private boolean playing;
	private float mapHeight;
	private float mapWidth;
	
	public GameWorld(Game g) 
	{
		super();
		game = g;
		playing = true;
	}

	public void initLayout()
	{
		//here I initialize our variables.
		livesRemaining = 3;
		lastPylon = 0;
		clockTime = 0;
		turnLimit = 40;
		sound = true;
		
		observers = new ArrayList<Observer>();
		
		//here we create our collection of game objects.
		theGameCollection = new GameCollection();
		theGameCollection.add(makePylon(200, 200, 1));
		theGameCollection.add(makePylon(700, 200, 2));
		theGameCollection.add(makePylon(700, 600, 3));
		theGameCollection.add(makePylon(300, 600, 4));

		//create my car objects and set it to the first pylon and default values.
		Car player = makePlayer(100,220);
		theGameCollection.add(player);
		
		NPC npc1 = makeNPC(270, 300, player);
		theGameCollection.add(npc1);

		NPC npc2 = makeNPC(140,500, player);
		theGameCollection.add(npc2);
		
		NPC npc3 = makeNPC(75, 500, player);
		theGameCollection.add(npc3);

		//here we loop twice and make birds, oilslicks, and fuelcans with random variables, this assignment requires 2 of each so i use a for loop
		//in the future i might implement methods for each of these or a more detailed constructor in order to simplify the process for larger
		//quantities or variations in the quantities.
		for(int i = 0; i < 2; i++)
		{
			Bird bird = makeBird();
			theGameCollection.add(bird);

			OilSlicks oilSlicks = makeOilSlicks();
			theGameCollection.add(oilSlicks);

			FuelCans fuelCans = makeFuelCans();
			theGameCollection.add(fuelCans);
		}	

		try
		{
			String seperator = File.separator;
			String fileName = "." + seperator + "sounds" + seperator + "Background.wav";
			File file = new File(fileName);
			if(file.exists())
				gameplaySound = Applet.newAudioClip(file.toURI().toURL());
			else
				throw new RuntimeException("Sound: file not found: " + file.toURI().toURL());
		}
		catch(Exception e)
		{
			throw new RuntimeException("Sound: malformed URL: " + e);	
		}
		
		gameplaySound.loop();
	}
	public AudioClip getAudio()
	{
		return gameplaySound;
	}

	//return the number of lives remaining. used by Game for looping.
	public float getLives()
	{
		return livesRemaining;
	}
	public void setLives(float l)
	{
		livesRemaining = l;
	}
	
	public void setLastPylon(Float p)
	{
		lastPylon = p;
	}	
	public float getTurnLimit()
	{
		return turnLimit;
	}
	public void setTurnLimit(float t)
	{
		turnLimit = t;
	}
	
	public float getClockTime()
	{
		return (clockTime);
	}
	public void setClockTime()
	{
		clockTime = (float) (clockTime+.02);
	}
	public float getLastPylon()
	{
		return lastPylon;
	}
	public boolean getSound()
	{
		return sound;
	}
	public void setSound(boolean s)
	{
		sound = s;
	}
	public Game getGame()
	{
		return game;
	}
	public boolean isPlaying()
	{
		return playing;
	}
	public void setPlaying()
	{
		playing = !playing;
		getGame().setTimer(playing);
		if(playing)
			gameplaySound.loop();
		else
			gameplaySound.stop();
			
	}
	public void mapSize(int height, int width) 
	{
		mapHeight = height;
		mapWidth = width;	
	}
	public float getMapHeight()
	{
		return mapHeight;
	}
    public float getMapWidth()
    {
    	return mapWidth;
    }
	public GameCollection getCollection()
	{
		return theGameCollection;
	}
	
	//this is used to create random floats for colors. makes it easier to write color generating code.
	private float randGen()
	{
		Random rand = new Random();
		return rand.nextFloat();
	}
	
	public void resetCar(Car car)
	{
		//create my car objects and set it to the first pylon and default values.
		//create my car objects and set it to the first pylon and default values.
		theGameCollection.remove(car, null);
		car = makePlayer(100, 220);
		theGameCollection.add(car);
	}

	
	//here is my factory method to make a player car
	private Car makePlayer(int x, int y)
	{
		
		Car car = new Car(true);
		car.translate(x, y);
		car.setColor(randGen(), randGen(), randGen());
		car.setDamageLevel(0);
		car.setMaximumSpeed(100);
		car.setFuelLevel(100);
		car.setHeading(90);
		car.setSpeed(0);
		car.setSteeringDirection(0);
		return car;
	}
	
	//here is my factory method to create a NPC car
	private NPC makeNPC(int x, int y, Car p)
	{
		MapView mv = null;
		GameCollection gC = theGameCollection;
		Iterator i = gC.getIterator();
		while(i.hasNext())
		{
			Object o = i.getNext();
			if(o instanceof MapView)
				mv = (MapView)o;
		}
		
		
		RaceStrategy rs = new RaceStrategy(theGameCollection);
		DirectStrategy ds = new DirectStrategy(theGameCollection);
		NPC npc = new NPC();
		npc.translate(x, y);
		npc.setColor(randGen(), randGen(), randGen());
		npc.setDamageLevel(0);
		npc.setFuelLevel(100);
		npc.setSpeed(0);
		npc.setSteeringDirection(0);
		if(Math.random() <=.5)
			npc.setStrategy(rs);
		else
			npc.setStrategy(ds);
		npc.getStrategy().apply(npc, p);
		return npc;
	}
	
	private Pylons makePylon(int x, int y, int seq)
	{
		Pylons p = new Pylons(x,y,seq);
		p.setColor(randGen(), randGen(), randGen()); //see randGen() method below
		return p;
		
	}
	
	private Bird makeBird()
	{
		Bird bird = new Bird(); 
		bird.setColor(randGen(), randGen(), randGen());
		bird.translate((float) Math.random() * 1080,(float)Math.random()* 825);
		//bird.setLocation((int)(Math.random() * 1000), (int)(Math.random() * 1000));
		bird.setHeading((int)(Math.random() * 360));
		bird.setSpeed((int)(Math.random() * 30));
		bird.setSize((int)(Math.random() * 30));
		return bird;
	}
	
	private OilSlicks makeOilSlicks()
	{
		OilSlicks oilSlicks = new OilSlicks((int)(Math.random() * 1000), (int)(Math.random()*1000));
		oilSlicks.setLength((int)(Math.random() * 100));
		oilSlicks.setWidth((int)(Math.random() * 100));
		oilSlicks.setColor(randGen(), randGen(), randGen());
		return oilSlicks;
	}
	
	private FuelCans makeFuelCans()
	{
		FuelCans fuelCans = new FuelCans((int)(Math.random() * 1000), (int)(Math.random() * 1000));
		fuelCans.setSize((int)(Math.random()*100));
		fuelCans.setColor(randGen(), randGen(), randGen());	
		fuelCans.setSize((int)(Math.random() * 50));
		return fuelCans;
	}
	
	public void addObserver(Observer o) 
	{
		observers.add(o);
		
	}

	public void notifyObservers() 
	{
		gwp = new GameWorldProxy(this);
		for(int i = 0; i < observers.size(); i++)
		{	
			observers.get(i).update(gwp, 0);  //TODO
		}
	}
	
	public MapView getMap()
	{
		for(int i = 0; i < observers.size(); i++)
		{	
			if(observers.get(i) instanceof MapView)
				return (MapView) observers.get(i);
		}
		return null;
	}

	public Shockwave addShockwave() 
	{
		Shockwave s = null;
		Iterator i = theGameCollection.getIterator();
		while(i.hasNext())
		{
			Object o = i.getNext();
			if(o instanceof Car)
			{
				Car c = ((Car)o);
				if(c.getType())
				{
					s  = new Shockwave(c.getTranslation().getTranslateX(), c.getTranslation().getTranslateY());
				}
			}
		}
		
		 theGameCollection.add(s);
		 return s;
		
	}

}
