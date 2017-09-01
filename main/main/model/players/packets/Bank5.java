package main.model.players.packets;

import main.model.players.Client;
import main.model.players.PacketType;

/**
 * Bank 5 Items
 **/
public class Bank5 implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int interfaceId = c.getInStream().readSignedWordBigEndianA();
		int removeId = c.getInStream().readSignedWordBigEndianA();
		int removeSlot = c.getInStream().readSignedWordBigEndian();
		switch (interfaceId) {
		case 7423:
			//System.out.println("sadasd1a");
		if (c.storing) {
		return;
		}
		c.getItems().bankItem(removeId, removeSlot, 5);
		c.getItems().resetItems(7423);
		break;
		case 1119:
		case 1120:
		case 1121:
		case 1122:
		case 1123:
			//System.out.println("sada2sda");
			c.getSmithing().readInput(c.playerLevel[c.playerSmithing],
					Integer.toString(removeId), c, 5);
			break;

		case 3900:
			//System.out.println("sad3asda");
			c.getShops().buyItem(removeId, removeSlot, 1);
			break;

		case 3823:
			//System.out.println("sad4asda");
			if(!c.getItems().playerHasItem(removeId))
                return;
			c.getShops().sellItem(removeId, removeSlot, 1);
			break;

		case 5064:
			//System.out.println("sad5asda");
			if(!c.getItems().playerHasItem(removeId))
                return;
			if (c.inTrade) {
				c.getTradeAndDuel().declineTrade(true);
			}
			c.getItems().bankItem(removeId, removeSlot, 5);
			break;

		case 5382:
			//System.out.println("sada6sda");
			c.getItems().fromBank(removeId, removeSlot, 5);
			break;

		case 3322:
			//System.out.println("sadas7da");
			if (c.duelStatus <= 0) {
				c.getTradeAndDuel().tradeItem(removeId, removeSlot, 5);
			} else {
				c.getTradeAndDuel().stakeItem(removeId, removeSlot, 5);
			}
			break;

		case 3415:
			//System.out.println("sadasd8a");
			if (c.duelStatus <= 0) {
				c.getTradeAndDuel().fromTrade(removeId, removeSlot, 5);
			}
			break;

		case 6669:
			//System.out.println("sada9sda");
			c.getTradeAndDuel().fromDuel(removeId, removeSlot, 5);
			break;

		}
	}

}
