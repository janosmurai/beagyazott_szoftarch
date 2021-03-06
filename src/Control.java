
package zatacka;

import javax.swing.ImageIcon;
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
import zatacka.SendSocket.socket_type;

class Control extends JFrame 
{
	
	
	private Network net = null;
	private GUI gui;
	int key_state = 0;
	TDirection client_dir = TDirection.nothing;
	public ArrayList<Player> playerList = new ArrayList<Player>();
	public ArrayList<ColoredPoint> receivedPoint = new ArrayList<ColoredPoint>();
	public ArrayList<Gift> receivedGift = new ArrayList<Gift>();
	public boolean clearing = false;
	public boolean flying_head = false;
	Timer timer = new Timer (1000, new ActionListener() 
	{
		public void actionPerformed(ActionEvent e) 
		{
			gui.drawPanel.panel.setVisible(false);
			gui.drawPanel.gameField.setVisible(true);
		}
	});
	Timer timer_fly = new Timer (6000, new ActionListener() 
	{
		public void actionPerformed(ActionEvent e) 
		{
			flying_head = false;
		}
	});
	

	Control() 
	{
		
		
	}
	
	void setGUI(GUI g) 
	{
		gui = g;
	}
	

	void startServer() 
	{
		
		if (net != null)
			net.disconnect();
		net = new SerialServer(this);
		net.connect("localhost");
	}

	void startClient() 
	{
		if (net != null)
			net.disconnect();
		net = new SerialClient(this);
		net.connect("localhost");
	}
	
	void send(SendSocket socket)
	{
		if(net == null)
			return;
		
		net.send(socket);
		
	}
	
	void giftReceived(GiftDummy giftRec)
	{
		Gift giftRecLoc = new Gift(10, 10);
		giftRecLoc.g_type = giftRec.g_type;
		giftRecLoc.g_effect = giftRec.g_effect;
		giftRecLoc.pos_x = giftRec.x;
		giftRecLoc.pos_y = giftRec.y;
		
		giftRecLoc.img = new ImageIcon("c:/workspace/zatacka/src/zatacka/Media/" + giftRec.g_type + "_" + giftRec.g_effect + ".png").getImage();
		
		boolean memberOf = false;
		int delete_index = 0;
		for(Gift gift : gui.drawPanel.gameField.gifts)
		{
			if((giftRecLoc.pos_x == gift.pos_x) && (giftRecLoc.pos_y == gift.pos_y) 
					&& (giftRecLoc.g_type == gift.g_type) && (giftRecLoc.g_effect == gift.g_effect))
			{
				memberOf = true;
				delete_index = gui.drawPanel.gameField.gifts.indexOf(gift);
			}
			
		}

		if(memberOf == true) 
		{
			gui.drawPanel.gameField.gifts.remove(delete_index);
			System.out.println(gui.drawPanel.gameField.gifts);
		}
		else
		{
			receivedGift.add(giftRecLoc);
		}
	}

	
	void playerReceived(Player playerRec)
	{
		if (gui == null)
		{
			return;
		}
		if(gui.status == 1)
		{
			boolean isColorExist = false;
			for(Player iteratorPlayer : playerList)
			{
				if(iteratorPlayer.p.color == playerRec.p.color) 
				{
					iteratorPlayer.p.x = playerRec.p.x;
					iteratorPlayer.p.y = playerRec.p.y;
					iteratorPlayer.p.direction = playerRec.p.direction;
					iteratorPlayer.ongoingGame = gui.player.ongoingGame;
					iteratorPlayer.clear = clearing;
					iteratorPlayer.flying_head = flying_head;
					isColorExist = true;
				}
			}
			
			if(isColorExist == false)
			{
				playerList.add(playerRec);
			}
			clearing = false;
				
		}
		else if(gui.status == 2)
		{
			receivedPoint.add(playerRec.p);	
			if(playerRec.p.color.equals(gui.player.p.color))
			{
				gui.player.p.x = playerRec.p.x;
				gui.player.p.y = playerRec.p.y;
				gui.player.ongoingGame = playerRec.ongoingGame;
				gui.player.clear = playerRec.clear;
				gui.player.flying_head = playerRec.flying_head;
			}
		}	
	}
	
	
	int collisionCheck()
	{
		ArrayList<ColoredPoint> controlPoints = gui.drawPanel.gameField.points;
		int dis_x = 0;
		int dis_y = 0;
		double distance = 0;
		int collisionCntr = 0;
		int selfcollisionCntr = 0;
		int gameFieldSize = gui.drawPanel.gameField.getWidth();    //Same as height
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
						(i < (controlPoints.size() - 13)))
				{
					selfcollisionCntr++;
					if (selfcollisionCntr > 1)
					{
						hitman_color = actualPoint.color;
					}

				}

				
				if((actualPoint.x < 0) ||
					(actualPoint.x > (gameFieldSize - 10)) ||
					(actualPoint.y < 0) || 
					(actualPoint.y > (gameFieldSize - 30)) ||
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
							clearing = true;
						}
						if(gift.g_type == Gift.gift_type.dark)
						{
							timer.stop();
							timer.start();
							gui.drawPanel.panel.setBackground(Color.black);
							pack();
							gui.drawPanel.panel.setVisible(true);
							gui.drawPanel.gameField.setVisible(false);
						}
						if(gift.g_type == Gift.gift_type.fly )
						{
							flying_head = true;
							timer_fly.stop();
							timer_fly.start();
						}
					}
					pick_up = true;
					pick_up_index = existing_gift.indexOf(gift);
					
					Gift delete_this = existing_gift.get(pick_up_index);
					Player not_used_player = new Player(10, 10, Color.black, 1);
					GiftDummy giftDummy = new GiftDummy(delete_this.g_type, delete_this.g_effect, delete_this.pos_x, delete_this.pos_y);
					SendSocket socket_gift = new SendSocket(not_used_player, giftDummy, socket_type.gift);
					send(socket_gift);
				}
				gift_i += 1;
			}			
			if(pick_up == true)
			{
				ArrayList<Gift> gift_copy = new ArrayList<Gift>();
				for (Gift tmp_gift: existing_gift)
				{
					if(pick_up_index != existing_gift.indexOf(tmp_gift))
					{
						gift_copy.add(tmp_gift);
					}
					else
					{
						/* leave out the picked up gift from the list*/
					}
				}
				existing_gift.clear();
				existing_gift.addAll(gift_copy);
				pick_up = false;
			}
			player_i += 1;
		}
	}


}
