//Ide kéne valami cucc ami tárolja, hogy éppen mely pontok vannak foglalva.
//A szerver gépen mindig ezzel a class-al kéne kiszámolni az egyes játékos adatait
//Vagy lehet külön class kéne a lefoglalt tömböknek és külön az újrapozícionálásnak.
package zatacka;

import zatacka.GUI.TDirection;

public class Position {
	
	
	public int[] RePositioning(int prvPx, int prvPy, TDirection direction, int beta)
	{
		

		double dy = 0;
		double dx = 0;
		
		int[] posArray;
		posArray = new int[3];
		
		if(direction == TDirection.left)
		{
			beta=beta-9;

		}
		
		else if(direction == TDirection.right)
		{
			beta=beta+9;

			
		}
		
		dx = 4.2*Math.cos(Math.toRadians(beta));
		dy = 4*Math.sin(Math.toRadians(beta));

		
		dx = Math.round(dx);
		dy = Math.round(dy);
		

		
		dx = dx + prvPx;
		dy = dy + prvPy;

		
		posArray[0] = (int)dx;
		posArray[1] = (int)dy;
		posArray[2] = (int)beta;
		
		if(posArray[0] <= 0 || posArray[0] >= 380 || posArray[1] <= 0 || posArray[1] >= 355){
			System.out.println("Meghaltal");
			System.exit(0);
		}
		
		
		return posArray;
		
	}
}
