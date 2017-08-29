package main.model.players.packets;

import main.model.players.Client;
import main.model.players.PacketType;
import main.model.players.PlayerHandler;
import main.model.players.skills.DuoSlayer;
import main.util.Misc;
import main.Server;

/**
 * @author Anonymoose.
 */

public class ItemOnPlayer implements PacketType {
	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int playerId = c.inStream.readUnsignedWord();
		int itemId = c.playerItems[c.inStream.readSignedWordBigEndian()] - 1;
		Client other = (Client) PlayerHandler.players[playerId];
		switch (itemId) {
		case 962:
			c.gfx0(176);
			c.startAnimation(451);
			c.sendMessage("You pull the Christmas Cracker...");
			other.sendMessage("You pull the Christmas Cracker...");
			c.getItems().deleteItem(962, 1);
			if(Misc.random(1) == 1) {
				other.forcedText = "I got the cracker!";
				other.forcedChatUpdateRequired = true;
				other.getItems().addItem((1038 + Misc.random(5)*2), 1);
			} else {
				c.forcedText = "I got the Cracker!";
				c.forcedChatUpdateRequired = true;	
				c.getItems().addItem((1038 + Misc.random(5)*2), 1);			
			}
			c.turnPlayerTo(other.absX, other.absY);
			break;
		case 4155:
		if ((itemId == 4155) && c.getItems().playerHasItem(4155)) {
			c.lastSlayerInvite = System.currentTimeMillis();
				//if (other.getIndex() == other.playerId) {
					if (other.dialogueAction > 0 || other.isBanking || other.inTrade
							 || other.duelStatus == 5 || other.inPcGame() || other.isInJail() || other.getPA().inPitsWait()) {
						c.sendMessage("This player is currently busy.");
						return;
					}
					if (other.inWild() || c.inWild()) {
						c.sendMessage("You can't do that in wildy.");
						return;
					}
					if (c == null || other == null)
						return;
					if (other.duoPartner == null) {
						DuoSlayer.getInstance().invite(c, other);
						other.asked = true;
					}
					else if (other.getDuoPartner() == c)
						c.sendMessage("You are already doing a Slayer Duo with "+ Misc.optimizeText(other.playerName) + ".");
					else
						c.sendMessage("Your partner already has a Slayer Dual partner.");
				//}
		}
		break;
		default:
			c.sendMessage("Nothing interesting happens.");
			break;
		}

	}

}