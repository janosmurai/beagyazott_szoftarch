package zatacka;

import java.awt.Color;
import java.io.Serializable;

public class SendSocket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Gift sendGift = new Gift(10,10);
	Player sendPlayer = new Player(10, 10, Color.black, 7);
	socket_type type = socket_type.gift;
	
	enum socket_type {
		gift,
		player
	}
	
	public SendSocket(Player player, Gift gift, socket_type type) {
		sendGift = gift;
		sendPlayer = player;
		this.type = type;
	}

}
