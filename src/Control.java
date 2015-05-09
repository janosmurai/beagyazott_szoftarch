
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

import zatacka.*;
import zatacka.GUI.TDirection;

class Control extends JFrame {
	
	
	private Network net = null;
	private GUI gui;
	private Position pos;
	private Point p;
	private Point p2;
	int key_state = 0;
	TDirection client_dir = TDirection.nothing;
	public ArrayList<Player> playerList = new ArrayList<Player>();
	public ArrayList<ColoredPoint> receivedPoint = new ArrayList<ColoredPoint>();
	

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
	
	void sendPlayer(Player player){
		if(net == null)
			return;
		net.send(player);
	}

	void sendNewPoint(ColoredPoint p){
		if(net == null)
			return;
		net.sendNewP(p);
	}
	
	void playerReceived(Player player){
		if (gui == null)
			return;
		playerList.add(player);
	}
	
	void pointReceived(ColoredPoint p){
		if (gui == null)
			return;
		System.out.println(p.color);
		receivedPoint.add(p);
	}
	
	int collisionCheck()
	{
		ArrayList<ColoredPoint> controlPoints = gui.drawPanel.gameField.points;
		int dis_x = 0;
		int dis_y = 0;
		double distance = 0;
		int collisionCntr = 0;
		int gameFieldSize = gui.drawPanel.getWidth();    //Same as height

		for(Player player : playerList)
		{
			ColoredPoint actualPoint = player.p; 
			for (ColoredPoint storedPoint : controlPoints  )
			{
		
				dis_x = storedPoint.x - actualPoint.x;
				dis_y = storedPoint.y - actualPoint.y;
				distance = Math.sqrt(dis_y^2 + dis_x^2);
				/*
				System.out.println(dis_x);
				System.out.println(dis_y);
				System.out.println(distance);
				System.out.println(storedPoint);
				System.out.println(actualPoint);
				*/
				
				if((storedPoint.color != actualPoint.color) && (distance <= (storedPoint.width + actualPoint.width)))
				{
					//collisionCntr++;
				}
				//System.out.println(collisionCntr);
				
				if((actualPoint.x < 0) ||
					(actualPoint.x > gameFieldSize) ||
					(actualPoint.y < 0) || 
					(actualPoint.y > gameFieldSize) ||
					(collisionCntr >= 2))
				{
					//System.out.println("cica");
					gui.stopGame();
					return 1;
				}

			}
		}
		return 0;
	}
	/*
	void setPlayers(int player_count, ArrayList<Color> reservedColor){
		for(int i = 0; i < player_count; i++){
			Player player = new Player(10+i*10,10+i*10,reservedColor.get(i));
			playerList.add(player);
		}
		
	}*/

}
