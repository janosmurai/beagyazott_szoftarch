/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zatacka;

import java.awt.Point;

import zatacka.Control;

public class Main {

	static Point p;
	
	public static void main(String[] args) {
		Control c = new Control();
		GUI g = new GUI(c);
		c.setGUI(g);
	}
}
