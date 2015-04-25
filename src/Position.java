//Ide kéne valami cucc ami tárolja, hogy éppen mely pontok vannak foglalva.
//A szerver gépen mindig ezzel a class-al kéne kiszámolni az egyes játékos adatait
//Vagy lehet külön class kéne a lefoglalt tömböknek és külön az újrapozícionálásnak.
package zatacka;

import zatacka.GUI.TDirection;

public class Position {
	
	int beta = 0;
	
	public int[] RePositioning(int prvPx, int prvPy, TDirection direction)
	{
		

		double dy = 0;
		double dx = 0;
		
		int[] posArray;
		posArray = new int[2];
		
		if(direction == TDirection.left)
		{
			beta=beta-7;

		}
		
		else if(direction == TDirection.right)
		{
			beta=beta+7;

			
		}
		
		System.out.println(beta);
		dx = 38*Math.cos(Math.toRadians(beta));
		dy = 4*Math.sin(Math.toRadians(beta));
		
		if(prvPx == 0 || prvPy == 0 || prvPx == 600 || prvPy == 600){
			System.out.println("Meghaltál.");
		}
		
		dx = Math.round(dx);
		dy = Math.round(dy);
		

		
		dx = dx + prvPx;
		dy = dy + prvPy;

		

		
		posArray[0] = (int)dx;
		posArray[1] = (int)dy;
		
		return posArray;
		
	}
}
