package zatacka;

import java.awt.Color;

public class SendSocket {
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
