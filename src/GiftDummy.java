package zatacka;

import java.io.Serializable;

import zatacka.Gift.effect_on;
import zatacka.Gift.gift_type;

public class GiftDummy implements Serializable {

	private static final long serialVersionUID = 1L;
	public gift_type g_type;
	public effect_on g_effect;
	public int x;
	public int y;
	
	GiftDummy(gift_type type, effect_on effect, int coord_x, int coord_y)
	{
		g_type = type;
		g_effect = effect;
		x = coord_x;
		y = coord_y;
	}

}
