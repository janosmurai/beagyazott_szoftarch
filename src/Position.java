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
			beta=beta-9;

		}
		
		else if(direction == TDirection.right)
		{
			beta=beta+9;

			
		}
		
		dx = 4.2*Math.cos(Math.toRadians(beta));
		dy = 4*Math.sin(Math.toRadians(beta));
		
		//System.out.println(dx);
		//System.out.println(dy);
		
		
		dx = Math.round(dx);
		dy = Math.round(dy);


		
		dx = dx + prvPx;
		dy = dy + prvPy;



		
		posArray[0] = (int)dx;
		posArray[1] = (int)dy;
		
		if(posArray[0] <= 0 || posArray[0] >= 380 || posArray[1] <= 0 || posArray[1] >= 355){
			System.out.println("Meghaltál");
			System.exit(0);
		}
		
		
		int newpositionx[] = new int [1000];
		int newpositiony[] = new int [1000];
			for(int i=1;i<1000;i++){
			newpositionx[i]=(int) dx;
			}
		for(int j=0;j<1000;j++){
			newpositiony[j]=(int) dy;
		}
		
		//for(int k=0;k<1000;k++){
		//System.out.println(newpositionx[k]);}
		
		return posArray;
		
	}
}
