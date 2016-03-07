package a4;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
/*****************************************************************************************************************************
 * Program: Assignment 4 - Implementation Strategy
 * Author: 	Jonathan Coffman
 * Due: 	05/18/2015
 * 
 * Class: 	ISelectable
 * Purpose: To outline for the GameWorld Objects what is required to be selectable.
 * 
 *****************************************************************************************************************************/
public interface ISelectable 
{
	public void setSelected(boolean yes);
	public boolean isSelected();
	public boolean contains(Point2D p);
	public void draw(Graphics2D g);
	
}
