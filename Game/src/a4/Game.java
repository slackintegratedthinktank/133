package a4;

import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Scanner;

import javax.swing.*;

/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	game
 * Purpose: This is our control class. It creates the GameWorld and the GUI objects needed to communicate with the user
 * 
 *****************************************************************************************************************************/


class Game extends JFrame implements ActionListener
{
	private GameWorld world;
	private ScoreView score;
	private MapView map;
	private MenuPanel menu;
	private MenuBar bar;
	private Timer myTime = new Timer(20, this);
	private ClockTick tick;
	private ChangeSound sound;

	//constructor
	public Game() 
	{
		Accelerate accel;
		Brake brake;
		SteerLeft left;
		SteerRight right;
		AddOil oil;
		
		ChangeStrategies strat;
		
		world = new GameWorld(this);
		world.initLayout();
		bar = new MenuBar(world);
		score = new ScoreView(world);
		map = new MapView(world, 826, 1081);
		this.setResizable(false);
		menu = new MenuPanel(world);
		
		//build the frame
		this.setTitle("Pylon Race");
		this.setLayout(new BorderLayout());
		this.add(menu, BorderLayout.WEST);
		this.add(score, BorderLayout.NORTH);
		this.add(map, BorderLayout.CENTER);
		this.setSize(1200, 900);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setJMenuBar(bar);
		world.notifyObservers();
		
		//create our singleton instances of actions
		accel = Accelerate.getAccelerate();
		accel.setTarget(world);
		brake = Brake.getBrake();
		brake.setTarget(world);
		left = SteerLeft.getSteerLeft();
		left.setTarget(world);
		right = SteerRight.getSteerRight();
		right.setTarget(world);
		oil = AddOil.getAddOil();
		oil.setTarget(world);
		tick = ClockTick.getClockTick();
		tick.setTarget(world);
		strat = ChangeStrategies.getChangeStrategies();
		strat.setTarget(world);
		this.setVisible(true);
		world.mapSize(map.getSize().height, map.getSize().width);
		setTimer(true);
		
	}
	public Boolean getTimer()
	{
		return myTime.isRunning();
	}
	public void setTimer(boolean play)
	{
		if(play)
			myTime.start();
		else
			myTime.stop();
	}
	public MenuPanel getMenuPanel()
	{
		return menu;
	}
	public MapView getMapView()
	{
		return map;
	}

	public void actionPerformed(ActionEvent e) 
	{
		tick.actionPerformed(e);
	}
	
}
