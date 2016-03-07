package a4;
import java.awt.GridLayout;

import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	MenuPanel
 * Purpose: This is our MenuPanel object on the left side of the interface. it is used by the user to cause actions in the 
 * 			program
 * 
 *****************************************************************************************************************************/
public class MenuPanel extends JPanel implements Observer
{
	
	private JButton quit;
	private BirdCollision birdCollision;
	private CarCollision carCollision;
	private ClockTick clockTick;
	private EnteredOil enteredOil;
	private ExitOil exitedOil;
	private FuelCollision fuelCollision;
	private PylonCollision pylonCollision;
	private Quit quiting;
	private ChangeStrategies strat;
	private Play playing;
	private JButton play;
	private Pause paused;
	private JButton delete;
	private Delete deleting;
	private JButton addPylon;
	private AddPylon addP;
	private JButton addFuel;
	private AddFuelCan addF;
	
	
	public MenuPanel(GameWorld w)
	{
		
		this.setBorder(new TitledBorder("Options: "));
		GridLayout gL = new GridLayout(5,2);
		this.setLayout(gL);
		
		w.addObserver(this);
		
		//here we get all of our commands.
		carCollision = CarCollision.getCarCollision();
		carCollision.setTarget(w);


		pylonCollision = PylonCollision.getPylonCollision();
		pylonCollision.setTarget(w);


		birdCollision = BirdCollision.getBirdCollision();
		birdCollision.setTarget(w);

		fuelCollision = FuelCollision.getFuelCollision();
		fuelCollision.setTarget(w);

		enteredOil = EnteredOil.getEnteredOil();
		enteredOil.setTarget(w);
		
		exitedOil = ExitOil.getExitOil();
		exitedOil.setTarget(w);
		
		strat = ChangeStrategies.getChangeStrategies();
		strat.setTarget(w);

		clockTick = ClockTick.getClockTick();
		clockTick.setTarget(w);
		
		playing = Play.getPlay();
		playing.setTarget(w);
		
		paused = Pause.getPause();
		paused.setTarget(w);
		
		deleting = Delete.getDelete();
		deleting.setTarget(w);
		
		addP = AddPylon.getAddPylon();
		addP.setTarget(w);
		
		addF = AddFuelCan.getAddFuelCan();
		addF.setTarget(w);
		
		//here we add the buttons and the associated actions.
		this.add(quit = new JButton());
		quiting = Quit.getQuit();
		quit.setAction(quiting);
		quit.setFocusable(false);
		
		this.add(play = new JButton());
		play.setAction(paused);
		play.setFocusable(false);
		
		this.add(delete = new JButton());
		delete.setAction(deleting);
		delete.setFocusable(false);
		
		this.add(addPylon = new JButton());
		addPylon.setAction(addP);
		addPylon.setFocusable(false);
		
		this.add(addFuel = new JButton());
		addFuel.setAction(addF);
		addFuel.setFocusable(false);
		
		
		
		//here we create our KeyBindings
		Accelerate accel = Accelerate.getAccelerate();
		int mapName = JComponent.WHEN_IN_FOCUSED_WINDOW;
		
		getInputMap(mapName).put(KeyStroke.getKeyStroke("UP"), "Accelerate");
		ActionMap amap = this.getActionMap();
		amap.put("Accelerate", accel);
		
		Brake brake = Brake.getBrake();
		getInputMap(mapName).put(KeyStroke.getKeyStroke("DOWN"), "Brake");
		amap.put("Brake", brake);
		
		getInputMap(mapName).put(KeyStroke.getKeyStroke("LEFT"), "Left");
		SteerLeft left = SteerLeft.getSteerLeft();;
		amap.put("Left", left);
		
		getInputMap(mapName).put(KeyStroke.getKeyStroke("RIGHT"), "Right");
		SteerRight right = SteerRight.getSteerRight();
		amap.put("Right", right);
		
		getInputMap(mapName).put(KeyStroke.getKeyStroke('o'), "Oil");
		AddOil oil = AddOil.getAddOil();
		amap.put("Oil", oil);
		
		getInputMap(mapName).put(KeyStroke.getKeyStroke('t'), "Tick");
		amap.put("Tick", clockTick);
		
		getInputMap(mapName).put(KeyStroke.getKeyStroke('q'), "Quit");
		amap.put("Quit", quiting);
		
		getInputMap(mapName).put(KeyStroke.getKeyStroke("SPACE"), "Strategy");			
		amap.put("Strategy", strat);
		
		getInputMap(mapName).put(KeyStroke.getKeyStroke("BACK_SPACE"), "Delete");
		amap.put("Delete", deleting);
		
		this.requestFocus();
		this.setVisible(true);
		
	}

	//here we update the buttons visibility and in turn lock or unlock the events.
	public void update(Observable o, Object arg) 
	{
		if(((GameWorldProxy)o).isPlaying())
		{
			addPylon.setEnabled(false);
			addFuel.setEnabled(false);
			delete.setEnabled(false);
			play.setAction(paused);
		}
		else
		{
			addPylon.setEnabled(true);
			addFuel.setEnabled(true);
			delete.setEnabled(true);
			play.setAction(playing);
		}
		
		
		
	}

}
