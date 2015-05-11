package zatacka;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import zatacka.GUI;
import zatacka.GUI.DrawPanel.GameField;


public class Gift extends JPanel{
    private static final ImageObserver ImageObserver = null;
	public Image slow;
	private GUI gui;
	
	void setGUI(GUI g) {
		gui = g;
	}
	
	

    public Gift() {
        
        loadImage();
        setGiftSize();
    }
 

    private void loadImage() {

        slow = new ImageIcon("C:/slow.jpg").getImage();
    }
    
    private void setGiftSize() {
        
        Dimension d = new Dimension();
        d.width = slow.getWidth(null);
        d.height = slow.getHeight(null);
        setPreferredSize(d);        
    }
    public void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        
        g2d.drawImage(slow, 200, 200, ImageObserver);
    }

    @Override
    public void paintComponent(Graphics g) {

      super.paintComponent(g);
      doDrawing(g);
    }

    public class DisplayImageEx extends JFrame {

        public DisplayImageEx() {

            initUI();
        }

        private void initUI() {

            add(new Gift());

            pack();
        }
    }
}