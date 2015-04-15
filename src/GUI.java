/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zatacka;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.plaf.ProgressBarUI;

import java.awt.font.*;

import zatacka.*;

public class GUI extends JFrame {

	Timer timer;
	JPanel panel;
	
	private static final long serialVersionUID = 1L;
	private Control ctrl;
	private DrawPanel drawPanel;
	int X = 10;
	int Y = 10;
	int key_state = 0;
	
	Position position = new Position();
	
	enum TDirection {left, right, nothing}
	TDirection direction = TDirection.nothing;

	GUI(Control c) {
		
		timer = new Timer (100, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MehetIdozitoActionEvent(e);
			}
		});
 
		addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				OnKeyPressed(e);
			}
			public void keyReleased(KeyEvent e) {OnKeyReleased(e);}
			public void keyTyped(KeyEvent e) {}
		});
		
		ctrl = c;
		setSize(400, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menu = new JMenu("Start");

		JMenuItem menuItem = new JMenuItem("Client");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ctrl.startClient();
			}
		});
		menu.add(menuItem);

		menuItem = new JMenuItem("Server");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ctrl.startServer();
			}
		});
		menu.add(menuItem);

		menuBar.add(menu);

		menuItem = new JMenuItem("Clear");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				drawPanel.points.clear();
				drawPanel.repaint();
			}
		});
		menuBar.add(menuItem);

		menuItem = new JMenuItem("Exit");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menuBar.add(menuItem);

		setJMenuBar(menuBar);
		
		

		drawPanel = new DrawPanel();
		drawPanel.setBounds(0, 0, 350, 350);
		drawPanel.setBorder(BorderFactory.createTitledBorder("Draw"));
		
		add(drawPanel);
		
		
		setVisible(true);
		
	}

	void addCircle(int x, int y) {
		JPanel panel_proba = new JPanel();
		panel_proba.setName("proba");
		panel_proba.setLayout(null);
		panel_proba.setBounds((X/20), (Y/2), 5, 5);
		panel_proba.setBackground(Color.RED);
		add(panel_proba);
		panel_proba.repaint();
	}
	
	private void OnKeyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			timer.start();
			key_state = 1;
		}
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			direction = TDirection.right;
			key_state = 2;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			direction = TDirection.left;
			key_state = 3;
		}
		else {
			key_state = 0;
		}
		ctrl.sendKeyPressed(key_state);
	}
	private void OnKeyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			direction = TDirection.nothing;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			direction = TDirection.nothing;
		}
		
	}
	
	private void MehetIdozitoActionEvent(ActionEvent e) {
		
		int[] pos_local = position.RePositioning(X, Y, direction);
		addCircle(X, Y);
		X = pos_local[1];
		Y = pos_local[0];
		

	}
	
	void addKey(int key){
		JLabel testLabel = new JLabel("proba");
		drawPanel.add(testLabel);
		add(testLabel);
		  
	}
	
	private class DrawPanel extends JPanel {

		private static final long serialVersionUID = 1L;
		private ArrayList<Point> points = new ArrayList<Point>();

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			for (Point p : points) {
				g.drawOval(100, 100, 10, 10);
			}
		}
	}
}
