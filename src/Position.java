//Ide kéne valami cucc ami tárolja, hogy éppen mely pontok vannak foglalva.
//A szerver gépen mindig ezzel a class-al kéne kiszámolni az egyes játékos adatait
//Vagy lehet külön class kéne a lefoglalt tömböknek és külön az újrapozícionálásnak.
package zatacka;


import java.awt.Color;

import zatacka.Player.TDirection;


public class Position {
	
	public Player RePositioning(Player player)
	{
		Player localPlayer = new Player(10, 10, Color.white);
		double dy = 0;
		double dx = 0;
		
		int[] posArray;
		posArray = new int[3];
		
		if(player.p.direction == TDirection.left)
		{
			player.beta = player.beta - 9;

		}
		
		else if(player.p.direction == TDirection.right)
		{
			player.beta = player.beta + 9;

			
		}
		
		dx = 4.2*Math.cos(Math.toRadians(player.beta));
		dy = 4*Math.sin(Math.toRadians(player.beta));

		
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
		
		
		return localPlayer;
		
	}
	
}


