//Ide kéne valami cucc ami tárolja, hogy éppen mely pontok vannak foglalva.
//A szerver gépen mindig ezzel a class-al kéne kiszámolni az egyes játékos adatait
//Vagy lehet külön class kéne a lefoglalt tömböknek és külön az újrapozícionálásnak.
package zatacka;

import zatacka.GUI.TDirection;

public class Position {
	
	int beta = 0;
	
	public int[] RePositioning(int prvPx, int prvPy, TDirection direction)
	{
		
		//double szogX=(Math.toRadians(prvPx));
		//double szogY=(Math.toRadians(prvPy));
		//double fi=Math.acos(szogX/szogY);
		//System.out.println(fi);
		int alpha = 0;
		double dy = 0;
		double dx = 0;
		
		int[] posArray;
		posArray = new int[2];
		
		if(direction == TDirection.left)
		{
			beta--;
		}
		
		else if(direction == TDirection.right)
		{
			beta++;
		}
		
		if(beta < 90){
			alpha = 90 - beta;
		}
		
		else if(beta < 180){
			alpha = beta - 90;
		}
		
		else if(beta < 270){
			alpha = 270 - beta;
		}
		
		else if(beta < 360){
			alpha = beta - 270;
		}
		
		dx = 20*Math.sin(Math.toRadians(alpha));
		dy = 20*Math.cos(Math.toRadians(alpha));
		
		if(prvPx == 0 || prvPy == 0 || prvPx == 600 || prvPy == 600){
			System.out.println("Meghaltál.");
		}
		
		dx = Math.round(dx);
		dy = Math.round(dy);
		
		System.out.println((int)dx);
		System.out.println((int)dy);
		
		dx = dx + prvPx;
		dy = dy + prvPy;

		

		
		posArray[0] = (int)dy;
		posArray[1] = (int)dx;
		
		return posArray;
		
	}
}
