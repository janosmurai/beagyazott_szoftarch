package zatacka;

import java.awt.Color;
import java.awt.Point;

import zatacka.Player.TDirection;

public class ColoredPoint extends Point
{
	private static final long serialVersionUID = 1L;
	public Color color;
	int width = 7;
	public TDirection direction = TDirection.nothing;	//TODO: ezt átrakni a Playerbe
	
	ColoredPoint(int x_coordinate, int y_coordinate, Color color_point)
	{
		x = x_coordinate;
		y = y_coordinate;
		color = color_point;
	}
}

