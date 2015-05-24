package zatacka;

import java.awt.Color;
import java.io.Serializable;

import zatacka.Gift.effect_on;
import zatacka.Gift.gift_type;

public class SendSocket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	GiftDummy sendGift = new GiftDummy(gift_type.fast, effect_on.self, 10,10);
	Player sendPlayer = new Player(10, 10, Color.black, 7);
	socket_type type = socket_type.gift;
	
	enum socket_type {
		gift,
		player
	}
	
	public SendSocket(Player player, GiftDummy gift, socket_type type) {
		sendGift = gift;
		sendPlayer = player;
		this.type = type;
	}

}
