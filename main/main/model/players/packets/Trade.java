package main.model.players.packets;

import main.Config;
import main.model.players.Client;
import main.model.players.PacketType;

/**
 * Trading
 */
public class Trade implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int tradeId = c.getInStream().readSignedWordBigEndian();
		c.getPA().resetFollow();
        if(tradeId < 1)
            return;
		if (c.arenas()) {
			c.sendMessage("You can't trade inside the arena!");
			return;
		}

		if (c.inTrade) {
			c.getTradeAndDuel().declineTrade(true);
		}

		if (c.playerRights == 2 && !Config.ADMIN_CAN_TRADE) {
			c.sendMessage("Trading as an admin has been disabled.");
			return;
		}
		if (tradeId != c.playerId)
			c.getTradeAndDuel().requestTrade(tradeId);
	}

}
