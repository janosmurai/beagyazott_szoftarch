package zatacka;

import java.awt.Color;
import java.awt.Point;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.Timer;

import zatacka.ColoredPoint;
import zatacka.Gift.gift_type;



public class Player extends ColoredPoint{
	private static final long serialVersionUID = 1L;
	
	public ColoredPoint p = new ColoredPoint(10, 10, Color.white, 7);
	public double beta = 1;
	public speed_type speed = speed_type.medium;
	public int score = 0;
	public enum TDirection {left, right, nothing};
	public boolean ongoingGame = false;
	public enum speed_type {slow, medium, fast};
	public boolean clear = false;
	public boolean invert = false;

	
	Player(int x_coordinate, int y_coordinate, Color color_point, int width) {
		super(x_coordinate, y_coordinate, color_point, width);
		p.x = x_coordinate;
		p.y = y_coordinate;
		p.color = color_point;
		p.width = width;
	}

	public void reset()
	{
		beta = 1;
		speed = speed_type.medium;
		p.width = 7;
		invert = false;
		clear = false;
	}
	public void handleGift(gift_type gift)
	{
		Timer timer = new Timer (5000, new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				invert = false;
			}
		});
	
		switch (gift)
		{
			case slow:
				speed = speed_type.slow;
				break;
			case fast:
				System.out.println(gift);
				speed = speed_type.fast;
				break;
			case thin:
				p.width /= 2;
				break;
			case thick:
				p.width *= 2;
				break;
			case invert:
				invert = true;
				timer.stop();
				timer.start();
				break;
			case bonus_point:
				score -= 1;
				break;
			default:
				break;
		}
	}

	

	
	
	
}
