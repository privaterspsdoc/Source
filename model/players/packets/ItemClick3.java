package main.model.players.packets;

import main.Server;
import main.model.players.Client;
import main.model.players.PacketType;
import main.util.Misc;

/**
 * Item Click 3 Or Alternative Item Option 1
 * 
 * @author Ryan / Lmctruck30
 * 
 *         Proper Streams
 */

public class ItemClick3 implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int itemId11 = c.getInStream().readSignedWordBigEndianA();
		int itemId1 = c.getInStream().readSignedWordA();
		int itemId = c.getInStream().readSignedWordA();
		if (!c.getItems().playerHasItem(itemId, 1)) {
			return;
		}
		/*if (itemId >= DiceHandler.DICE_BAG && itemId <= 15100) {
			DiceHandler.putupDice(c, itemId);
		}*/
		if(!c.getItems().playerHasItem(itemId, 1)) {
            return;
        }
		switch (itemId) {
		case 15098:
		int diceRoll = Misc.random(100);
			if (System.currentTimeMillis() - c.diceDelay >= 5000) {
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client) Server.playerHandler.players[j];						
						if (c.clan != null) {
							c.clan.sendChat(c, "@red@[Dice]@bla@ I rolled a @red@"+diceRoll+" @bla@on the percentile dice.");
							c.diceDelay = System.currentTimeMillis();
						} else {
							c.sendMessage("You may only roll dice while in a clan chat.");
						}
					}
				}
			} else {
				c.sendMessage("You must wait a few seconds before rolling the dice.");
			}				
		break;
		
		case 12926:
		int dart = 11230;
		int amount = c.dartsLoaded;
		if (amount >= 1) {
			c.getItems().addItem(dart, amount);
			if (c.dartsLoaded == 1) {
			c.sendMessage("@red@You receive " + c.dartsLoaded + " dart from your blowpipe.");
			} else {
				c.sendMessage("@red@You receive " + c.dartsLoaded + " darts from your blowpipe.");
			}
			c.dartsLoaded -= amount;
		} else
			c.sendMessage("You don't have any darts in your blowpipe.");
		return;
		
		case 2552:
		case 2554:
		case 2556:
		case 2558:
		case 2560:
		case 2562:
		case 2564:
		case 2566:
			// c.getPA().ROD();
			c.getPA().startTeleport(3362, 3263, 0, "modern");
			break;
		
		case 11238:
			int babyLoot = c.getPA().randomBabyLoot();
			c.getItems().addItem(babyLoot, 1);
			c.getItems().deleteItem(11238, 1);
			c.sendMessage("You loot your baby impling jar.");
		break;
		
		case 11240:
			int youngLoot = c.getPA().randomYoungLoot();
			c.getItems().addItem(youngLoot, 1);
			c.getItems().deleteItem(11240, 1);
			c.sendMessage("You loot your young impling jar.");
		break;
		
		

		case 1712:
			c.getPA().handleGlory(itemId);
			c.isOperate = true;
			c.itemUsing = itemId;
			break;

		case 1710:
			c.getPA().handleGlory(itemId);
			c.itemUsing = itemId;
			c.isOperate = true;
			break;

		case 1708:
			c.getPA().handleGlory(itemId);
			c.itemUsing = itemId;
			c.isOperate = true;
			break;

		case 1706:
			c.getPA().handleGlory(itemId);
			c.itemUsing = itemId;
			c.isOperate = true;
			break;

		default:
			if (c.playerRights == 3)
				Misc.println(c.playerName + " - Item3rdOption: " + itemId
						+ " : " + itemId11 + " : " + itemId1);
			break;
		}

	}

}
