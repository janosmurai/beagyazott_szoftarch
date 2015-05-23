package zatacka;

import java.awt.Color;
import java.awt.Point;



import java.io.Serializable;

import zatacka.ColoredPoint;
import zatacka.Gift.gift_type;



public class Player extends ColoredPoint{
	private static final long serialVersionUID = 1L;
	
	public ColoredPoint p = new ColoredPoint(10, 10, Color.white);
	public int beta = 1;
	public int speed = 1;
	public int result = 1;
	public enum TDirection {left, right, nothing}
	public boolean ongoingGame = false;

	
	Player(int x_coordinate, int y_coordinate, Color color_point) {
		super(x_coordinate, y_coordinate, color_point);
		p.x = x_coordinate;
		p.y = y_coordinate;
		p.color = color_point;
	}

	public void handleGift(gift_type gift)
	{
	
		switch (gift)
		{
			case slow:
				speed /= 2;
				break;
			case fast:
				System.out.println(gift);
				speed *= 2;
				break;
			case thin:
				p.width /= 2;
				break;
			case thick:
				p.width *= 2;
				break;
			case invert:
				//Ezzel még baj lesz
				break;
			case bonus_point:
				result += 1;
				break;
			default:
				break;
		}
		System.out.println(speed);
		System.out.println(p.width);
		System.out.println(result);
	}

	

	
	
	
}
