
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
import java.util.ArrayList;

import zatacka.GUI;
import zatacka.GUI.TDirection;
import zatacka.GUI.ColoredPoint;
import zatacka.GUI.DrawPanel.GameField;

class Control extends JFrame {
	
	
	private Network net = null;
	private GUI gui;
	private Position pos;
	private Point p;
	private Point p2;
	public int nextgame = 0;
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
	
	void nextGame(){
		ArrayList<ColoredPoint> points = gui.drawPanel.gameField.points;
		
		ColoredPoint p_s = gui.p_s;
		ColoredPoint p_c = gui.p_c;
		//System.out.println(p.x);

		for (ColoredPoint p : points){
			
			if(p.x == p_s.x || p.y == p_s.y || p.x == p_c.x || p.y == p_c.y){
				nextgame = 1;
				System.out.println(p.x);
			}
			
		}		
		
		if(p_s.x <= 0 || p_s.x >= 380 || p_s.y <= 0 || p_s.y >= 355 || p_c.x <= 0 || p_c.x >= 380 || p_c.y <= 0 || p_c.y >= 355){
			nextgame = 1;
		System.exit(0);
			}
		else {
			nextgame = 0;}

}
	
	void numberOfPlayers(int player_count){
		//ide majd vmi, ami elmenti, hogy hány játékos lesz, nehogy elõbb elinduljon a játék 
	}
}
