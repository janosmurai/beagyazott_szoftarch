
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
import java.util.Iterator;

import zatacka.*;
import zatacka.Gift.effect_on;
import zatacka.Player.TDirection;

class Control extends JFrame {
	
	
	private Network net = null;
	private GUI gui;
	int key_state = 0;
	TDirection client_dir = TDirection.nothing;
	public ArrayList<Player> playerList = new ArrayList<Player>();
	public ArrayList<ColoredPoint> receivedPoint = new ArrayList<ColoredPoint>();
	public boolean clear = false;
	Timer timer = new Timer (200, new ActionListener() 
	{
		public void actionPerformed(ActionEvent e) 
		{
			gui.drawPanel.panel.setVisible(false);
			gui.drawPanel.gameField.setVisible(true);
		}
	});
	

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
		if(gui.status == 1){
			//System.out.println(player.ongoingGame);
		}
		
		net.send(player);
		
	}

	void sendNewPoint(ColoredPoint p){
		if(net == null)
			return;
		net.sendNewP(p);
	}
	
	void playerReceived(Player playerRec){
		if (gui == null)
		{
			System.out.println("cica");
			return;
		}
		if(gui.status == 1)
		{
			//System.out.println(playerRec.p.x);
			boolean isColorExist = false;
			for(Player iteratorPlayer : playerList)
			{
				if(iteratorPlayer.p.color == playerRec.p.color) 
				{
					iteratorPlayer.p.x = playerRec.p.x;
					iteratorPlayer.p.y = playerRec.p.y;
					iteratorPlayer.p.direction = playerRec.p.direction;
					iteratorPlayer.ongoingGame = gui.player.ongoingGame;
					iteratorPlayer.clear = clear;
					isColorExist = true;
				}
			}
			
			clear = false;
			
			if(isColorExist == false)
			{
				playerList.add(playerRec);
			}
				
		}
		else if(gui.status == 2)
		{
			receivedPoint.add(playerRec.p);	
			if(playerRec.p.color.equals(gui.player.p.color))// == gui.player.p.color)
			{
				gui.player.p.x = playerRec.p.x;
				gui.player.p.y = playerRec.p.y;
				gui.player.ongoingGame = playerRec.ongoingGame;
				gui.player.clear = playerRec.clear;
			}
			
		}
		
			
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
		int selfcollisionCntr = 0;
		int gameFieldSize = gui.drawPanel.getWidth();    //Same as height
		Color hitman_color = Color.black;
		int i = 0;

		for(Player player : playerList)
		{
			ColoredPoint actualPoint = player.p; 
			for (ColoredPoint storedPoint : controlPoints  )
			{
				
				dis_x = storedPoint.x - actualPoint.x;
				dis_y = storedPoint.y - actualPoint.y;
				distance = Math.sqrt(Math.pow(dis_y, 2) + Math.pow(dis_x, 2));
				
				if(!(storedPoint.color.equals(actualPoint.color)) &&
						(distance <= (storedPoint.width + actualPoint.width)))
				{
					collisionCntr++;
					if (collisionCntr > 1)
					{
						hitman_color = actualPoint.color;
					}

				}
				
				if((storedPoint.color.equals(actualPoint.color)) &&
						(distance <= (storedPoint.width + actualPoint.width)) &&
						(distance >= actualPoint.width) &&
						(i < (controlPoints.size() - 100)))
				{
					selfcollisionCntr++;
					if (selfcollisionCntr > 1)
					{
						hitman_color = actualPoint.color;
					}

				}

				
				if((actualPoint.x < 0) ||
					(actualPoint.x > gameFieldSize) ||
					(actualPoint.y < 0) || 
					(actualPoint.y > gameFieldSize) ||
					(collisionCntr > 1) ||
					(selfcollisionCntr > 1))
					{
						hitman_color = actualPoint.color;
					}
					
				if(hitman_color != Color.black)
					{
						
						for(Player hitman: playerList)
					{
						if (hitman.p.color == hitman_color)
							{
								hitman.score = hitman.score + 1;
							}
							
						}

					gui.stopGame();
					return 1;
					
					}
				i += 1;
			}
		}
		return 0;
	}
	
	void catchGift()
	{
		int dis_x;
		int dis_y;
		double distance;
		int gift_i = 0;
		int player_i = 0;
		int player_j = 0;
		int pick_up_index = 0;
		boolean pick_up = false;
		
		ArrayList<Gift> existing_gift = gui.drawPanel.gameField.gifts;
		for(Player player : playerList)
		{
			ColoredPoint actualPoint = player.p;
			for(Gift gift : existing_gift)
			{
				dis_x = (gift.pos_x + (gift.img_r/2)) - actualPoint.x;
				dis_y = (gift.pos_y + (gift.img_r/2)) - actualPoint.y;
				distance = Math.sqrt(Math.pow(dis_x, 2) + Math.pow(dis_y, 2));
				
				if(distance < (gift.img_r/2))
				{
					if(gift.g_effect == effect_on.self)
					{
						player.handleGift(gift.g_type);
					}
					else if(gift.g_effect == effect_on.enemy)
					{
						for(Player sub_player : playerList)
						{
							if(player_i != player_j)
							{
								sub_player.handleGift(gift.g_type);
							}
							player_j += 1;
						}
					}
					else
					{
						if(gift.g_type == Gift.gift_type.clear)
						{
							clear = true;
						}
						if(gift.g_type == Gift.gift_type.dark)
						{
							timer.stop();
							timer.start();
							gui.drawPanel.panel.setVisible(true);
							gui.drawPanel.gameField.setVisible(false);
						}
					}
					pick_up = true;
					pick_up_index = gift_i;
				}
				gift_i += 1;
			}
			if(pick_up == true)
			{
				System.out.println("cica");
				existing_gift.remove(pick_up_index);
				pick_up = false;
			}
			player_i += 1;
		}
	}


}
