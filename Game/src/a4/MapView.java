package a4;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	MapView
 * Purpose: This is one of our Views. It is currently used to display the basic text that we are printing up from assignment 1's
 * 			map command.
 * 
 *****************************************************************************************************************************/
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class MapView extends JPanel implements Observer, MouseWheelListener, MouseMotionListener, MouseListener
{
	private GameCollection gC;
	private GameWorld target;
	private AffineTransform worldtoND, ndToScreen, theVTM;
	private double winRight, winTop, winLeft, winBottom;
	private int previousX, previousY;
	private AffineTransform backup;
	private Graphics2D thisGraphics;
	
	public MapView(Observable world, double height, double width)
	{
		world.addObserver(this);
		target = (GameWorld) world;
		this.setBorder(new LineBorder(Color.blue, 3));
		this.setVisible(true);
		this.setSize((int)width, (int)height);
		winRight = width;
		winTop = height;
		winLeft = 0;
		winBottom = 0;
		this.addMouseWheelListener(this);
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
	}
	//our update method to repaint the screen 
	public void update(Observable o, Object arg) 
	{
		gC = ((GameWorldProxy)o).getCollection();
		repaint();	
	}
	
	//here are the zoom functions that adjust the boundaries.
	public void zoomIn()
	{
		double h = winTop - winBottom;
		double w = winRight - winLeft;
		winLeft = winLeft + w*0.05;
		winRight = winRight - w*0.05;
		winTop = winTop - h*0.05;
		winBottom = winBottom + h*0.05;
	}
	public void zoomOut()
	{
		double h = winTop - winBottom;
		double w = winRight - winLeft;
		winLeft -= w*0.05;
		winRight +=w*0.05;
		winTop += h*0.05;
		winBottom -= h*0.05;
	}
	
	//here are all the pan functions that adjust the windows boundaries
	public void panLeft()
	{
		winLeft = winLeft - 5;
		winRight = winRight - 5;
	}
	public void panRight()
	{
		winLeft = winLeft + 5;
		winRight = winRight + 5;
	}
	public void panUp()
	{
		winTop = winTop + 5;
		winBottom = winBottom +5;
	}
	public void panDown()
	{
		winTop = winTop - 5;
		winBottom = winBottom - 5;
	}
	
	public AffineTransform getVTM()
	{
		return theVTM;
	}
	//here we paint each object
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		thisGraphics = g2d;
		backup = g2d.getTransform();
		
		worldtoND = buildWorldToNDXform();
		ndToScreen = buildNDToScreenXform();
		theVTM = (AffineTransform) ndToScreen.clone();
		theVTM.concatenate(worldtoND);
		g2d.transform(theVTM);
		
		Iterator i = gC.getIterator();
		while(i.hasNext())
		{
			((IDrawable) i.getNext()).draw(g2d);
		}
		
		g2d.setTransform(backup);
	}
	
	//here I create the Normalized device to screen view transform.
	private AffineTransform buildNDToScreenXform() 
	{
		AffineTransform thisAT = new AffineTransform();
		double panelWidth = this.getWidth();
		double panelHeight = this.getHeight();
		
		thisAT.translate(1, panelHeight);
		thisAT.scale(panelWidth, panelHeight);
		
		
		return thisAT;
	}
	
	//here I create the world to normalized device transform
	private AffineTransform buildWorldToNDXform() 
	{
		AffineTransform thisAT = new AffineTransform();
		thisAT.scale(1/(winRight-winLeft), -1/(winTop-winBottom));
		thisAT.translate(-winLeft, -winBottom);
		return thisAT;
	}
	
	public void mouseClicked(MouseEvent e) 
	{
		Graphics2D backup = thisGraphics;
		//we check to make sure that the game is paused and then begin handling selected items.
		if(!target.isPlaying())
		{
			worldtoND = buildWorldToNDXform();
			ndToScreen = buildNDToScreenXform();
			theVTM = (AffineTransform) ndToScreen.clone();
			theVTM.concatenate(worldtoND);
			thisGraphics.transform(theVTM);
			AffineTransform inverse = null;
			try
			{
			 inverse = thisGraphics.getTransform().createInverse();
			}
			catch(NoninvertibleTransformException e1)
			{}
			Point2D pos = e.getPoint();
			Point2D p =  inverse.transform(pos, null);
			
			
			if(e.isControlDown())
			{
				GameCollection gC = target.getCollection();
				Iterator i = gC.getIterator();
				while(i.hasNext())
				{
					ISelectable o = (ISelectable) i.getNext();
					if(o.contains(p))
					{
						o.setSelected(true);
					}
				}
			}
			else //this is if control is not help down.
			{
				GameCollection gC = target.getCollection();
				Iterator i = gC.getIterator();
				while(i.hasNext())
				{
					ISelectable o = (ISelectable) i.getNext();
					if(o.contains(p))
						o.setSelected(true);
					else
						o.setSelected(false);
				}
			}
			target.notifyObservers();
		}
		thisGraphics = backup;
	}
	
	//here I control the zoom function and ensure it only happens when the game is being played.
	public void mouseWheelMoved(MouseWheelEvent e) 
	{
		if(target.isPlaying())
		{
			if(e.getWheelRotation() < 0)
				zoomIn();
			else
				zoomOut();
		}
	}
	
	//here I store the previous coordinates
	public void mousePressed(MouseEvent e)
	{
		previousX = e.getX();
		previousY = e.getY();
	}

	//here I handle the mouseDragged event and check which direction it was dragged and pan
	public void mouseDragged(MouseEvent e) 
	{
		
		if(target.isPlaying())
		{
			if(e.getX()<previousX)
				panRight();
			else if(e.getX() > previousX)
				panLeft();
			if(e.getY() < previousY)
				panDown();
			else if(e.getY() > previousY)
				panUp();
		}
		mousePressed(e);  //call the mouse pressed event to updated the previous coordinates.
	}
	
	//left over methods for mouseListener
	public void mouseMoved(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	

}
