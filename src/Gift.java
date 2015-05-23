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
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Image img;
	public int pos_x;
	public int pos_y;
	public int img_r;
	
	public enum gift_type 
	{
		slow,
		fast,
		thin,
		thick,
		invert,
		dark, 
		clear, 
		fly,
		bonus_point
	}
	
	public enum effect_on
	{
		self,
		enemy,
		game_field
	}
	
	public gift_type g_type;
	public effect_on g_effect;

    public Gift(int game_field_height, int game_field_width)
    {
        setType();
        loadImage();
        setGiftSize();
        setGiftPos(game_field_height, game_field_width);
    }
    
    public void setType(){
    	int type = (int)(Math.random()*14);
    	switch (type) {
		case 0:
			g_type = gift_type.slow;
			g_effect = effect_on.self;
			break;
		case 1:
			g_type = gift_type.slow;
			g_effect = effect_on.enemy;
			break;
		case 2:
			g_type = gift_type.fast;
			g_effect = effect_on.self;
			break;
		case 3:
			g_type = gift_type.fast;
			g_effect = effect_on.enemy;
			break;
		case 4:
			g_type = gift_type.invert;
			g_effect = effect_on.self;
			break;
		case 5:
			g_type = gift_type.invert;
			g_effect = effect_on.enemy;
			break;
		case 6:
			g_type = gift_type.thin;
			g_effect = effect_on.self;
			break;
		case 7:
			g_type = gift_type.thin;
			g_effect = effect_on.enemy;
			break;
		case 8:
			g_type = gift_type.fly;
			g_effect = effect_on.self;
			break;
		case 9:
			g_type = gift_type.thick;
			g_effect = effect_on.self;
			break;
		case 10:
			g_type = gift_type.thick;
			g_effect = effect_on.enemy;
			break;
		case 11:
			g_type = gift_type.dark;
			g_effect = effect_on.game_field;
			break;
		case 12:
			g_type = gift_type.clear;
			g_effect = effect_on.game_field;
			break;
		case 13:
			g_type = gift_type.bonus_point;
			g_effect = effect_on.self;
			break;
		default:
			//This should never happen
			System.out.println("Unexpected gift type");
			break;
		}
    }
    
    private void loadImage() {

        img = new ImageIcon("c:/Users/murai/workspace/zatacka/zatacka/zatacka/Media/" + g_type + "_" + g_effect + ".png").getImage();
    }
    
    public void setGiftSize()
    {
    	Dimension d = new Dimension();
        d.width = img.getWidth(null);
        d.height = img.getHeight(null);
        setPreferredSize(d);        
        img_r = img.getWidth(null);
    }
    
    public void setGiftPos(int game_field_height, int game_field_width)
    {
    	pos_x = (int)(Math.random()*game_field_height);
    	pos_y = (int)(Math.random()*game_field_width);
    	
    	// Recalculate position if the edge is too near.
    	if((pos_x < 5) ||
    	   (pos_y < 5) ||
    	   (pos_x > (game_field_height - 5)) ||
    	   (pos_y > (game_field_width - 5)))
    	{
    		setGiftPos(game_field_height, game_field_width);
    	}
    }

}