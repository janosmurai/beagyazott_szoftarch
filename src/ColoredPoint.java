package zatacka;

import java.awt.Color;
import java.awt.Point;

import zatacka.Player.TDirection;

public class ColoredPoint extends Point
{
	private static final long serialVersionUID = 1L;
	public Color color;
	int width;
	public TDirection direction = TDirection.nothing;	//TODO: ezt �trakni a Playerbe
	
	ColoredPoint(int x_coordinate, int y_coordinate, Color color_point, int width)
	{
		x = x_coordinate;
		y = y_coordinate;
		color = color_point;
		this.width = width;
	}
}

