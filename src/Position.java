//Ide k�ne valami cucc ami t�rolja, hogy �ppen mely pontok vannak foglalva.
//A szerver g�pen mindig ezzel a class-al k�ne kisz�molni az egyes j�t�kos adatait
//Vagy lehet k�l�n class k�ne a lefoglalt t�mb�knek �s k�l�n az �jrapoz�cion�l�snak.
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


