//Ide kéne valami cucc ami tárolja, hogy éppen mely pontok vannak foglalva.
//A szerver gépen mindig ezzel a class-al kéne kiszámolni az egyes játékos adatait
//Vagy lehet külön class kéne a lefoglalt tömböknek és külön az újrapozícionálásnak.
package zatacka;


import java.awt.Color;

import zatacka.GUI.TDirection;


public class Position {
	
	public ColoredPoint RePositioning(Player player, TDirection direction)
	{
		ColoredPoint localPoint = new ColoredPoint(10, 10, Color.white);
		double dy = 0;
		double dx = 0;
		
		int[] posArray;
		posArray = new int[3];
		
		if(direction == TDirection.left)
		{
			player.beta = player.beta - 9;

		}
		
		else if(direction == TDirection.right)
		{
			player.beta = player.beta + 9;

			
		}
		
		dx = 4.2*Math.cos(Math.toRadians(player.beta));
		dy = 4*Math.sin(Math.toRadians(player.beta));

		
		dx = Math.round(dx);
		dy = Math.round(dy);
		

		
		player.p.x += dx;
		player.p.y += dy;

		localPoint.x = player.p.x;
		localPoint.y = player.p.y;
		localPoint.color = player.p.color;
		System.out.println(localPoint.color);
		
		
		return localPoint;
		
	}
	
}


