
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
	int key_state = 0;
	TDirection client_dir = TDirection.nothing;
	
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
	
	void sendKeyPressed(TDirection direction){
		if(net == null)
			return;
		net.send(direction);
	}

	//void clickReceived(Point p) {
		//if (gui == null)
		//	return;
		//gui.addPoint(p);
	//}
	
	void keyPressReceived(TDirection dir_received){
		if (gui == null)
			return;
		//System.out.println(dir_received);
		client_dir = dir_received;
	}
	
	
	
	ColoredPoint newPosition(int x, int y, TDirection direction, Color color)
	{
		int[] pos_local=position.RePositioning(x, y, direction);
		ColoredPoint newPoint = new ColoredPoint(pos_local[0], pos_local[1], color);
		return newPoint;
	}
	
	void nextGame(){
		int halal;
		int [] koord = position.RePositioning(getX(),getY(), client_dir);
		if(koord[0] <= 0 || koord[0] >= 380 || koord[1] <= 0 || koord[1] >= 355){
			halal = 1;
			}
		else {
			halal = 0;}
	}
	
	void numberOfPlayers(int player_count){
		//ide majd vmi, ami elmenti, hogy hány játékos lesz, nehogy elõbb elinduljon a játék 
	}
}
