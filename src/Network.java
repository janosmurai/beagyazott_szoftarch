package zatacka;

import java.awt.Point;

import zatacka.Control;
import zatacka.Player.TDirection;

abstract class Network {

	protected Control ctrl;

	Network(Control c) {
		ctrl = c;
	}

	abstract void connect(String ip);

	abstract void disconnect();

	abstract void send(Player player);
	
	abstract void sendNewP(ColoredPoint p); 

}
