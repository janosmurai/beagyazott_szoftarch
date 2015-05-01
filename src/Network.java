/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zatacka;

import java.awt.Point;

import zatacka.Control;
import zatacka.GUI.ColoredPoint;
import zatacka.GUI.TDirection;

/**
 *
 * @author Predi
 */
abstract class Network {

	protected Control ctrl;

	Network(Control c) {
		ctrl = c;
	}

	abstract void connect(String ip);

	abstract void disconnect();

	abstract void send(TDirection direction);
	
	abstract void sendNewP(ColoredPoint p); 

}
