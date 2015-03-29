//Ide kéne valami cucc ami tárolja, hogy éppen mely pontok vannak foglalva.
//A szerver gépen mindig ezzel a class-al kéne kiszámolni az egyes játékos adatait
//Vagy lehet külön class kéne a lefoglalt tömböknek és külön az újrapozícionálásnak.
package zatacka;

import zatacka.GUI.TDirection;

public class Position {
	
	void RePositioning(int prvPx, int prvPy, TDirection direction)
	{
		
		double szogX=(Math.toRadians(prvPx));
		double szogY=(Math.toRadians(prvPy));
		double fi=Math.acos(szogX/szogY);
		System.out.println(fi);
		
		if(prvPx == 0 || prvPy == 0 || prvPx == 600 || prvPy == 600){
			System.out.println("Meghaltál.");
			System.exit(0);
		}
		else y--;
		
		if(direction == TDirection.left || direction == TDirection.right){
	     	y++;
	     	y++;
				}
							
		if(direction == TDirection.left) {
				if(x > 0)
				x = x - 2;
		}

		if(direction == TDirection.right) {
			if(x < 1000)
				x = x + 2;
		}
	}
}
