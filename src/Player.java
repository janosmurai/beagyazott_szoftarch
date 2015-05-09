package zatacka;

import java.awt.Color;
import java.awt.Point;



import zatacka.ColoredPoint;
import zatacka.GUI.TDirection;



public class Player {
	
	private static final long serialVersionUID = 1L;
	
	public ColoredPoint p;
	
	int beta = 1;
	int speed = 1;
	int result = 1;
	int width = 1;
	TDirection direction = TDirection.nothing;
	
	Player(int x, int y, Color color)
	{
		p = new ColoredPoint(x, y, color);
	}
	
	
	
}
