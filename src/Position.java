//Ide k�ne valami cucc ami t�rolja, hogy �ppen mely pontok vannak foglalva.
//A szerver g�pen mindig ezzel a class-al k�ne kisz�molni az egyes j�t�kos adatait
//Vagy lehet k�l�n class k�ne a lefoglalt t�mb�knek �s k�l�n az �jrapoz�cion�l�snak.
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
			System.out.println("Meghalt�l.");
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
