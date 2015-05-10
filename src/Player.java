package zatacka;

import java.awt.Color;
import java.awt.Point;



import java.io.Serializable;

import zatacka.ColoredPoint;



public class Player extends ColoredPoint{
	private static final long serialVersionUID = 1L;
	
	public ColoredPoint p = new ColoredPoint(10, 10, Color.white);
	public int beta = 1;
	public int speed = 1;
	public int result = 1;
	public int width = 1;
	public enum TDirection {left, right, nothing}
	public boolean ongoingGame = false;

	
	Player(int x_coordinate, int y_coordinate, Color color_point) {
		super(x_coordinate, y_coordinate, color_point);
		// TODO Auto-generated constructor stub
		p.x = x_coordinate;
		p.y = y_coordinate;
		p.color = color_point;
	}

	

	

	
	
	
}
