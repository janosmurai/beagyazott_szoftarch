
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
	ColoredPoint red_received = new ColoredPoint(10, 10, Color.red, 0);
	ColoredPoint blue_received = new ColoredPoint(10, 10, Color.blue, 0);
	
	private Position position = new Position();		
	

	Control() {
		
		
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

	void sendNewPoint(ColoredPoint p){
		if(net == null)
			return;
		net.sendNewP(p);
	}
	
	void keyPressReceived(TDirection dir_received){
		if (gui == null)
			return;
		client_dir = dir_received;
	}
	
	void pointReceived(ColoredPoint p){
		if (gui == null)
			return;
		if(p.color.getRed() == 255)
		{
			red_received = p;
		}
		else if(p.color.getBlue() == 255)
		{
			blue_received = p;
		}
	}
	
	ColoredPoint newPosition(int x, int y, TDirection direction, Color color, int beta)
	{
		int[] pos_local=position.RePositioning(x, y, direction, beta);
		ColoredPoint newPoint = new ColoredPoint(pos_local[0], pos_local[1], color, pos_local[2]);
		return newPoint;
	}
	
	/*void nextGame(){
		int halal;
		int [] koord = position.RePositioning(getX(),getY(), client_dir);
		if(koord[0] <= 0 || koord[0] >= 380 || koord[1] <= 0 || koord[1] >= 355){
			halal = 1;
			}
		else {
			halal = 0;}
	}*/
	
	void numberOfPlayers(int player_count){
		//ide majd vmi, ami elmenti, hogy hány játékos lesz, nehogy elõbb elinduljon a játék 
	}
}
