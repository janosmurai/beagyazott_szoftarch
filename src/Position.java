//Ide k�ne valami cucc ami t�rolja, hogy �ppen mely pontok vannak foglalva.
//A szerver g�pen mindig ezzel a class-al k�ne kisz�molni az egyes j�t�kos adatait
//Vagy lehet k�l�n class k�ne a lefoglalt t�mb�knek �s k�l�n az �jrapoz�cion�l�snak.
package zatacka;


import java.awt.Color;

import zatacka.Player.TDirection;


public class Position {
	
	public Player RePositioning(Player player)
	{
		Player localPlayer = new Player(10, 10, Color.white);
		double dy = 0;
		double dx = 0;
		
		
		if(player.p.direction == TDirection.left)
		{
			player.beta = player.beta - 2;

		}
		
		else if(player.p.direction == TDirection.right)
		{
			player.beta = player.beta + 2;

			
		}
		
		dx = player.speed*1.05*Math.cos(Math.toRadians(player.beta));
		dy = player.speed*Math.sin(Math.toRadians(player.beta));

		
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


