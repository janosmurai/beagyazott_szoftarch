package zatacka;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop.Action;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowListener;
import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.plaf.ProgressBarUI;

import zatacka.Player.TDirection;

import java.awt.font.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import zatacka.Gift;

public class GUI {
	
	private static final long serialVersionUID = 1L;
	private Control ctrl;
	public DrawPanel drawPanel;
	private MainMenu mainMenu;
	private PlayerCounter playerCounter;
	private int player_count = 0;
	int status = 0;
	int cntr = 0;
	int rounds = 5;
	File soundFile = new File("C:/Users/Lõrinc/workspace/zatacka/src/zatacka/Media/backgroundmusic.wav");

	
	
	public ArrayList<Color> reservedColor = new ArrayList<Color>();
	
	
	
	Player player = new Player((int)(Math.random() * 100),(int)(Math.random() * 100),Color.black, 7);
	
	Position position = new Position();
	
	public class DrawPanel extends JFrame 
	{
		
		private static final long serialVersionUID = 1L;
		public JPanel panel;
		public GameField gameField = new GameField();
		Timer timer;
		
		
		DrawPanel()
		{
			switch(player_count)
			{
			case 1: 
				setPreferredSize(new Dimension(600,600));
				break;
			case 2:
				setPreferredSize(new Dimension(600,600));
				break;
			case 3:
				setPreferredSize(new Dimension(900,900));
				break;
			case 4:
				setPreferredSize(new Dimension(1000,1000));
				break;
			default:
				System.err.println("Incorrect number of players!");
				return;
			}
			

			panel = new JPanel();
			panel.setSize(getWidth(), getHeight());
			panel.setBackground(Color.black);
			panel.setVisible(false);
			add(panel);
			pack();
			//setUndecorated(true);
			gameField.setSize(getWidth(), getHeight());
			add(gameField);
			pack();
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLayout(null);
			setTitle("Zatacka");
			setLocationRelativeTo(null);
		
			gameField.setVisible(true);
			gameField.setBackground(Color.black);
			gameField.setLocation(0, 0);
			
			addKeyListener(new KeyListener() 
			{
				public void keyPressed(KeyEvent e)
				{
					OnKeyPressed(e);
				}
				public void keyReleased(KeyEvent e) 
				{
					OnKeyReleased(e);
				}
				public void keyTyped(KeyEvent e) {}
			});

KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0, false);
			AbstractAction escapeAction = new AbstractAction() {
						private static final long serialVersionUID = 1L;
						public void actionPerformed(ActionEvent e) {
							System.exit(getDefaultCloseOperation());
						}
					};
			getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke,"ESCAPE");
			getRootPane().getActionMap().put("ESCAPE", escapeAction);

		}
		
		public class GameField extends JPanel
		{
			private static final long serialVersionUID = 1L;
			public ArrayList<ColoredPoint> points = new ArrayList<ColoredPoint>();
			public ArrayList<Gift> gifts = new ArrayList<Gift>();
			ColoredPoint newPoint = new ColoredPoint(10, 10, Color.BLACK, 7);
			
			GameField()
			{

			}
			
			@Override
			protected void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				for (ColoredPoint p : points)  
				{
					//System.out.println(p.color);
					g.setColor(p.color);
					g.fillOval(p.x, p.y, p.width, p.width);		//TODO: with beállítása
				}
				Graphics2D g2d = (Graphics2D) g;
				for (Gift gift : gifts)
				{
					g2d.drawImage(gift.img, gift.pos_x, gift.pos_y, null);
				}
				
			}
			
			public void GetNewPoint() 
			{
				if(status == 1)	//Server
				{
					ctrl.collisionCheck();	
					ctrl.catchGift();
					for(Player iplayer : ctrl.playerList)
					{
						Player tmp_player = new Player(10, 10, Color.black, 7);
						tmp_player = position.RePositioning(iplayer);
						tmp_player.ongoingGame = player.ongoingGame;
						tmp_player.p.width = iplayer.p.width;
						System.out.println(tmp_player.p.width + " " + iplayer.p.width);
						points.add(tmp_player.p);
						//System.out.println(iplayer.p.x);
						
						ctrl.sendPlayer(tmp_player);
					}
					
					gameField.repaint();
					
				}
				else if(status == 2) 	//Client
				{
					Player test_player = new Player(player.p.x, player.p.y, player.p.color, 7);
					test_player.p.direction = player.p.direction;
					ctrl.sendPlayer(test_player);
					
					for(ColoredPoint coloredPoint : ctrl.receivedPoint)
					{
						points.add(coloredPoint);
						
					}
					ctrl.receivedPoint.clear();
					
					gameField.repaint();
				}
				
			}
			
			public void getNewGift()
			{
				Gift new_gift = new Gift(drawPanel.gameField.getHeight(), drawPanel.gameField.getWidth());
				gifts.add(new_gift);
			}

		}
			
		public void OnKeyPressed(KeyEvent e) 
		{
			
			if(e.getKeyCode() == KeyEvent.VK_SPACE) 
			{
					if(player.ongoingGame == false){
						drawPanel.gameField.points.clear();
						drawPanel.gameField.repaint();
						player.p.x = (int)(Math.random()*drawPanel.getWidth());
						player.p.y = (int)(Math.random()*drawPanel.getHeight());
		
						startGame();
						
					}
				
			}
			else if(e.getKeyCode() == KeyEvent.VK_RIGHT) 
			{
				player.p.direction = TDirection.right;
				

			}
			else if(e.getKeyCode() == KeyEvent.VK_LEFT)
			{
				player.p.direction = TDirection.left;
			
			}
			
		}
		private void OnKeyReleased(KeyEvent e)
		{
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) 
			{
				player.p.direction = TDirection.nothing; 
			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT) 
			{
				player.p.direction = TDirection.nothing;
			}
		}
	}
	
	private class MainMenu extends JFrame
	{
		private static final long serialVersionUID = 1L;
		private JPanel sc_panel, color_panel, button_panel/*, panel*/;
		private Color color = Color.black;
		
		MainMenu()
		{
			setSize(210, 260);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLayout(null);
	//setTitle("Main menu");
			setUndecorated(true);
			setLocationRelativeTo(null);
			//panel = new JPanel();
			//panel.setSize(getWidth(), getHeight());
			//panel.setBackground(Color.black);
			//add(panel);
		
			ButtonGroup server_client_group = new ButtonGroup();
			
			JRadioButton serverButton= new JRadioButton("Server");
			//serverButton.setBackground(Color.black);
			//serverButton.setForeground(Color.white);
			serverButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					status = 1;
				}
			});
			
			
			JRadioButton clientButton= new JRadioButton("Client");
			//clientButton.setBackground(Color.black);
			//clientButton.setForeground(Color.white);
			clientButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					status = 2;
				}
			});
			server_client_group.add(serverButton);
			server_client_group.add(clientButton);
			
			sc_panel = new JPanel();
			sc_panel.setBounds(this.getWidth(), this.getHeight(), 200, 60);
			sc_panel.setBorder(BorderFactory.createTitledBorder("Choose mode!"));
			
			sc_panel.add(serverButton);
			sc_panel.add(clientButton);
			
			sc_panel.setVisible(true);
			sc_panel.setLocation(5, 5);
			//sc_panel.setBackground(Color.black);
			add(sc_panel);
			
			ButtonGroup color_group = new ButtonGroup();
			
			JRadioButton blueButton= new JRadioButton("Blue");
			//blueButton.setBackground(Color.black);
			//blueButton.setForeground(Color.white);
			blueButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					color = Color.blue;
					//TODO
				}
			});
			
			JRadioButton yellowButton= new JRadioButton("Yellow");
			//yellowButton.setBackground(Color.black);
			//yellowButton.setForeground(Color.white);
			yellowButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					color = Color.yellow;
				}
			});
			
			JRadioButton greenButton= new JRadioButton("Green");
			//greenButton.setBackground(Color.black);
			//greenButton.setForeground(Color.white);
			greenButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					color = Color.green;
				}
			});
			
			JRadioButton redButton= new JRadioButton("Red");
			//redButton.setBackground(Color.black);
			//redButton.setForeground(Color.white);
			redButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					color = Color.red;
				}
			});
			
			JRadioButton whiteButton= new JRadioButton("White");
			//whiteButton.setBackground(Color.black);
			//whiteButton.setForeground(Color.white);
			whiteButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					color = Color.white;
				}
			});
			
			JRadioButton orangeButton= new JRadioButton("Orange");
			//orangeButton.setBackground(Color.black);
			//orangeButton.setForeground(Color.white);
			orangeButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					color = Color.orange;
				}
			});
			
			color_group.add(greenButton);
			color_group.add(blueButton);
			color_group.add(yellowButton);
			color_group.add(redButton);
			color_group.add(whiteButton);
			color_group.add(orangeButton);
			
			color_panel = new JPanel();
			color_panel.setBounds(this.getWidth(), this.getHeight(), 200, 100);
			color_panel.setBorder(BorderFactory.createTitledBorder("Choose your color!"));
			
			color_panel.add(greenButton);
			color_panel.add(blueButton);
			color_panel.add(yellowButton);
			color_panel.add(redButton);
			color_panel.add(whiteButton);
			color_panel.add(orangeButton);
			
			color_panel.setVisible(true);
			color_panel.setLocation(5, 75);
			//color_panel.setBackground(Color.black);
			add(color_panel);
			
			JButton button = new JButton("Ready!");
			button_panel = new JPanel();
			
			button.setVisible(true);
			//button.setBackground(Color.black);
			button_panel.add(button);
			button_panel.setVisible(true);
			button_panel.setBounds(this.getWidth(), this.getHeight(), 200, 70);
			button_panel.setBorder(BorderFactory.createTitledBorder("Ready?"));
			
			button_panel.setLocation(5,185);
			button_panel.setVisible(true);
			//button_panel.setBackground(Color.black);
			add(button_panel);
				
			
			button.addActionListener(new ActionListener() 
		{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					if(status == 0)
					{
						JOptionPane.showMessageDialog(null, "You have to choose server or client mode!");
					}
					if(color.equals(Color.black))
					{
						JOptionPane.showMessageDialog(null, "You have to choose a color!");
					}
					else
					{
						player.p.color = color;
						CreatePlayer();
					}
				}
			});
			KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0, false);
			AbstractAction escapeAction = new AbstractAction() {
						private static final long serialVersionUID = 1L;
						public void actionPerformed(ActionEvent e) {
							System.exit(getDefaultCloseOperation());
						}
					};
			getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke,"ESCAPE");
			getRootPane().getActionMap().put("ESCAPE", escapeAction);
			
			
		}
		
		void CreatePlayer()
		{
			if(player_count != 1)
			{
				if(status == 1)
				{
					ctrl.startServer(color);
					ctrl.playerList.add(player);
				}
				else if(status == 2)
				{
					ctrl.startClient(color);
				}
			}
			else
			{
				ctrl.playerList.add(player);
			}
			
			mainMenu.setVisible(false);
			
			drawPanel.setVisible(true);
		}
	}

	private class PlayerCounter extends JFrame
	{
		private static final long serialVersionUID = 1L;
		private JPanel panel;
		
		PlayerCounter()
		{
			setSize(600, 250);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setUndecorated(true);
			
			
		    setContentPane(new JLabel(new ImageIcon("C:/Users/Lõrinc/workspace/zatacka/src/zatacka/Media/design.jpg")));
		    setLayout(new FlowLayout());
			
			panel = new JPanel();
			panel.setLocation(0, 0);
			panel.setBounds(0, 0, getWidth(), getHeight());
			
			JLabel label = new JLabel("Number of players will be:");
			label.setVisible(true);
			panel.add(label);
			
			JTextField text = new JTextField("1", 30);
			
			text.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String input = text.getText();
					player_count = Integer.parseInt(input);
					setVisible(false);
					mainMenu.setVisible(true);
					drawPanel = new DrawPanel();
				}

			});
			
			panel.add(text);
			add(panel);
			setVisible(true);
			setLocationRelativeTo(null);

			KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0, false);
			AbstractAction escapeAction = new AbstractAction() {
						private static final long serialVersionUID = 1L;
						public void actionPerformed(ActionEvent e) {
							System.exit(getDefaultCloseOperation());
						}
					};
			getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke,"ESCAPE");
			getRootPane().getActionMap().put("ESCAPE", escapeAction);
		}
	}

	GUI(Control c) 
	{
		ctrl = c;
			
		mainMenu = new MainMenu();
		
		mainMenu.setVisible(false);
		
		playerCounter = new PlayerCounter();
		
		playerCounter.setVisible(true);
		
	}
	
	 public void backGroundMusic() {
		    try {
	          AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);    
	          Clip clip = AudioSystem.getClip();
	          clip.open(audioIn);
	          clip.start();
	          cntr++;
	
	      } catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      }
	 }
	 
	void startGame()
	{
		for (Player others : ctrl.playerList)
		{
			if(others.color == player.color) 
			{
				player.score = others.score;
			}
		}
		if(rounds == 0)
		{
			String result = " You won! :)";
			for (Player others : ctrl.playerList)
			{
				if(others.score < player.score)
				{
					result = " You lose. :(";
				}
			}
			JOptionPane.showMessageDialog(null, "End of game, your score: " + player.score + result);
		}
		else
		{
			player.ongoingGame = true;
		drawPanel.timer = new Timer (30, new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				drawPanel.gameField.GetNewPoint();
				if(Math.random() > 0.98)
				{
					drawPanel.gameField.getNewGift();
				}
			}
		});
		drawPanel.timer.start();
		if(cntr < 1){
		//backGroundMusic();
		}
	}
	}
	/*
	 public void crashSound() {
		 
	      try {
	                 
	          File soundFile = new File("C:/Users/Lõrinc/workspace/zatacka/src/zatacka/Media/sound.wav"); 
	          AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);    
	          Clip clip2 = AudioSystem.getClip();
	         clip2.open(audioIn);
	         clip2.start();
	      } catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      }
	   }*/
	
	void stopGame()
	{
		player.ongoingGame = false;
		drawPanel.timer.stop();
		rounds--;
		//crashSound();
	}
}
