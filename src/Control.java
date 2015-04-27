
/*

class Controld {

	private GUI gui;
	private Network net = null;

	Control() {
	}

	void setGUI(GUI g) {
		gui = g;
	}

	void startServer() {
		if (net != null)
			net.disconnect();
		net = new SerialServer(this);
		net.connect("localhost");
	}

	void startClient() {
		if (net != null)
			net.disconnect();
		net = new SerialClient(this);
		net.connect("localhost");
	}

	void sendClick(Point p) {
		// gui.addPoint(p); //for drawing locally
		if (net == null)
			return;
		net.send(p);
	}

	void clickReceived(Point p) {
		if (gui == null)
			return;
		gui.addPoint(p);
	}
}*/

package zatacka;

import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Point;

import zatacka.GUI;
import zatacka.GUI.TDirection;
import zatacka.GUI.ColoredPoint;

class Control extends JFrame {
	
	
	private Network net = null;
	private GUI gui;
	private Point p;
	private Point p2;
	static int x = 100;
	static int y = 10;
	int key_state = 0;
	
	private Position position = new Position();		
	

	Control() {
		
		/*
		panel = new JPanel();
		panel.setBounds(280, 500, 5, 5);
		panel.setBackground(Color.red);
*/
		
	}
	
	void setGUI(GUI g) {
		gui = g;
	}
	
	

	void startServer(Color color) {
		if (net != null)
			net.disconnect();
		net = new SerialServer(this);
		net.connect("localhost");
	}

	void startClient(Color color) {
		if (net != null)
			net.disconnect();
		net = new SerialClient(this);
		net.connect("localhost");
	}

	void sendClick(Point p) {
		// gui.addPoint(p); //for drawing locally
		if (net == null)
			return;
		//net.send(p);
	}
	
	void sendKeyPressed(int key_state){
		if(net == null)
			return;
		net.send(key_state);
	}

	//void clickReceived(Point p) {
		//if (gui == null)
		//	return;
		//gui.addPoint(p);
	//}
	
	void keyPressReceived(int received){
		if (gui == null)
			return;
		//gui.addKey(received);
	}
	
	
	
	ColoredPoint newPosition(TDirection direction)
	{
		int[] pos_local=position.RePositioning(x, y, direction);
		Color color = Color.white; // ezt majd be kell állítani a játékos színe alapján
		ColoredPoint newPoint = new ColoredPoint(pos_local[0], pos_local[1], color);
		return newPoint;
	}
	
	void numberOfPlayers(int player_count){
		//ide majd vmi, ami elmenti, hogy hány játékos lesz, nehogy elõbb elinduljon a játék 
	}
}
