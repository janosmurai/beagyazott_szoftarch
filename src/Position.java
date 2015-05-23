package zatacka;


import java.awt.Color;

import zatacka.Player.TDirection;


public class Position {
	
	public Player RePositioning(Player player)
	{
		Player localPlayer = new Player(10, 10, Color.white, 7);
		double dy = 0;
		double dx = 0;
		int dbeta = 0;
		double speed = 0;
		
		switch (player.speed) {
		case slow:
			dbeta = 4;
			speed = 0.75;
			break;
		case medium:
			dbeta = 6;
			speed = 1;
			break;
		case fast:
			dbeta = 8;
			speed = 1.25;
			break;
		default:
			dbeta = 6;
			speed = 1;
			break;
		}
		
		if (player.invert == true)
		{
			dbeta *= -1;
		}
		if(player.p.direction == TDirection.left)
		{
			player.beta = player.beta - dbeta;

		}
		
		else if(player.p.direction == TDirection.right)
		{
			player.beta = player.beta + dbeta;

			
		}
		
		
		dx = speed*3.2*Math.cos(Math.toRadians(player.beta));
		dy = speed*3*Math.sin(Math.toRadians(player.beta));

		
		dx = Math.round(dx);
		dy = Math.round(dy);
		

		
		player.p.x += dx;
		player.p.y += dy;

		localPlayer.p.x = player.p.x;
		localPlayer.p.y = player.p.y;
		localPlayer.p.color = player.p.color;
		localPlayer.p.direction = player.p.direction;
		localPlayer.beta = player.beta;
		localPlayer.speed = player.speed;
		localPlayer.width = player.width;
		localPlayer.clear = player.clear;
		localPlayer.invert = player.invert;
		localPlayer.flying_head = player.flying_head;
		
		
		return localPlayer;
		
	}
	
}


