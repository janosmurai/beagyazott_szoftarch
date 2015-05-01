package zatacka;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.plaf.ProgressBarUI;

import java.awt.font.*;

import zatacka.*;

public class GUI {
	
	private static final long serialVersionUID = 1L;
	private Control ctrl;
	private DrawPanel drawPanel;
	private MainMenu mainMenu;
	private PlayerCounter playerCounter;
	private int player_count = 0;
	int tmp = 0;
	int status = 0;
	int server_beta = 0;
	int client_beta = 0;
	
	
	enum TDirection {left, right, nothing}
	TDirection direction = TDirection.nothing;
	
	static Random randomGenerator = new Random();
	static int randx = randomGenerator.nextInt(300);
	static int randy = randomGenerator.nextInt(300);
	
	public static class ColoredPoint extends Point
	{
		private static final long serialVersionUID = 1L;
		public Color color;
		private int beta;
		
		ColoredPoint(int x_coordinate, int y_coordinate, Color color_point, int beta)
		{
			x = x_coordinate;
			y = y_coordinate;
			color = color_point;
			this.beta = beta;
		}
	}
	
	ColoredPoint p_c = new ColoredPoint(randx, randy, Color.RED, 0);
	ColoredPoint p_s = new ColoredPoint(randx, randy, Color.RED, 0);
	
	private class DrawPanel extends JFrame 
	{

		private static final long serialVersionUID = 1L;
		private GameField gameField = new GameField();
		Timer timer;
		
		DrawPanel()
		{
			switch(player_count)
			{
			case 1: 
				setSize(400, 400);
				break;
			case 2:
				setSize(400, 400);
				break;
			case 3:
				setSize(450, 450);
				break;
			case 4:
				setSize(500, 500);
				break;
			case 5:
				setSize(550, 550);
				break;
			case 6:
				setSize(600, 600);
				break;
			default:
				System.err.println("Incorrect number of players!");
				return;
			}
			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLayout(null);
			setTitle("Zatacka");
		
			gameField.setBackground(Color.black);
			gameField.setLocation(0, 0);
			gameField.setBounds(0, 0, getWidth(), getHeight());
			gameField.setVisible(true);
			add(gameField);
			
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

		}
		
		private class GameField extends JPanel
		{
			private static final long serialVersionUID = 1L;
			private ArrayList<ColoredPoint> points = new ArrayList<ColoredPoint>();
			
			GameField()
			{
				setBounds(0, 0, getWidth(), getHeight());
				setOpaque(true);
			}
			
			@Override
			protected void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				for (ColoredPoint p : points)  
				{
					g.setColor(p.color);
					g.fillOval(p.x, p.y, 7, 7);
				}
				
			}
			
			protected void GetNewPoint() 
			{
				
				if(status == 1)	//Server
				{
					p_s = ctrl.newPosition(p_s.x, p_s.y, direction, Color.red, p_s.beta);
					p_c = ctrl.newPosition(p_c.x, p_c.y, ctrl.client_dir, Color.blue, p_c.beta);
					points.add(p_s);
					points.add(p_c);
					gameField.repaint();
					ctrl.sendNewPoint(p_c);
					ctrl.sendNewPoint(p_s);
					
				}
				else if(status == 2) 	//Client
				{
					ctrl.sendKeyPressed(direction);
		
					points.add(ctrl.red_received);
					points.add(ctrl.blue_received);
					gameField.repaint();
				}	
			}
		}
			
		private void OnKeyPressed(KeyEvent e) 
		{
			if(e.getKeyCode() == KeyEvent.VK_SPACE) 
			{
					startGame();
					timer.start();
			}
			else if(e.getKeyCode() == KeyEvent.VK_RIGHT) 
			{
				direction = TDirection.right;
			}
			else if(e.getKeyCode() == KeyEvent.VK_LEFT)
			{
				direction = TDirection.left;
			}
			else 
			{
			}
			
		}
		private void OnKeyReleased(KeyEvent e)
		{
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) 
			{
				direction = TDirection.nothing;
			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT) 
			{
				direction = TDirection.nothing;
			}
		}
	}
	
	private class MainMenu extends JFrame
	{
		private static final long serialVersionUID = 1L;
		private JPanel sc_panel, color_panel, button_panel;
		private Color color = Color.black;
		
		MainMenu()
		{
			setSize(225, 300);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLayout(null);
			setTitle("Main menu");
			
			ButtonGroup server_client_group = new ButtonGroup();
			
			JRadioButton serverButton= new JRadioButton("Server");
			serverButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					status = 1;
				}
			});
			
			
			JRadioButton clientButton= new JRadioButton("Client");
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
			add(sc_panel);
			
			ButtonGroup color_group = new ButtonGroup();
			
			JRadioButton blueButton= new JRadioButton("Blue");
			blueButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					color = Color.blue;
				}
			});
			
			JRadioButton yellowButton= new JRadioButton("Yellow");
			yellowButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					color = Color.yellow;
				}
			});
			
			JRadioButton greenButton= new JRadioButton("Green");
			greenButton.setSelected(true);
			greenButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					color = Color.green;
				}
			});
			
			JRadioButton redButton= new JRadioButton("Red");
			redButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					color = Color.red;
				}
			});
			
			JRadioButton whiteButton= new JRadioButton("White");
			whiteButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					color = Color.white;
				}
			});
			
			JRadioButton orangeButton= new JRadioButton("Orange");
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
			add(color_panel);
			
			JButton button = new JButton("Ready!");
			button_panel = new JPanel();
			
			button.setVisible(true);
			button_panel.add(button);
			button_panel.setVisible(true);
			button_panel.setBounds(this.getWidth(), this.getHeight(), 200, 70);
			button_panel.setBorder(BorderFactory.createTitledBorder("Ready?"));
			
			button_panel.setLocation(5,185);
			button_panel.setVisible(true);
			
			add(button_panel);
			
			
			button.addActionListener(new ActionListener() 
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					CreatePlayer();
				}
			});
			
			
		}
		
		void CreatePlayer()
		{
			if(player_count != 1){
				System.out.println(status);
				if(status == 1)
				{
					ctrl.startServer(color);
				}
				else if(status == 2)
				{
					ctrl.startClient(color);
				}
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
			setSize(400, 200);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setTitle("Players");
			
			panel = new JPanel();
			panel.setLocation(0, 0);
			panel.setBounds(0, 0, getWidth(), getHeight());
			
			JLabel label = new JLabel("Number of players will be:");
			label.setVisible(true);
			panel.add(label);
			
			JTextField text = new JTextField("2", 30);
			
			text.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String input = text.getText();
					player_count = Integer.parseInt(input);
					ctrl.numberOfPlayers(player_count);
					setVisible(false);
					mainMenu.setVisible(true);
					drawPanel = new DrawPanel();
				}

			});
			
			panel.add(text);
			add(panel);
			setVisible(true);
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
	
	void startGame()
	{
		drawPanel.timer = new Timer (75, new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				drawPanel.gameField.GetNewPoint();	
			}
		});
	}
}
