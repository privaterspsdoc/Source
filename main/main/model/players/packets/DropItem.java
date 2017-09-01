package main.model.players.packets;

import main.Config;
import main.Server;
import main.model.npcs.pet.DropPet;
import main.model.npcs.pet.PetData;
import main.model.players.Client;
import main.model.players.PacketType;
import main.model.players.PlayerSave;

/**
 * Drop Item by Ardi
 **/
public class DropItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		boolean droppable = true;
		int itemId = c.getInStream().readUnsignedWordA();
		c.getInStream().readUnsignedByte();
		c.getInStream().readUnsignedByte();
		int slot = c.getInStream().readUnsignedWordA();
		if (System.currentTimeMillis() - c.alchDelay < 1800) {
			return;
		}
		if (c.playerItemsN[slot] != 0 && itemId != -1
				&& c.playerItems[slot] == itemId + 1) {
			if (!c.getItems().playerHasItem(itemId, 1, slot)) {
				return;
			}
		}
		if (c.arenas()) {
			c.sendMessage("You can't drop items inside the arena!");
			return;
		}
		if (c.inTrade) {
			c.sendMessage("You can't drop items while trading!");
			return;
		}
		if (!c.getItems().playerHasItem(itemId)) {
			return;
		}
		 for (int i = 0; i < PetData.petData.length; i++)
	        {
	                if (PetData.petData[i][1] == itemId)
	                {
	                	if (c.getPetSummoned())
	                    {
	                            droppable = false;
	                            break;
	                    }
	                }
	        }
		DropPet.getInstance().dropPetRequirements(c, itemId, slot);
		if (c.playerItemsN[slot] == 0 || itemId < 0
				|| c.playerItems[slot] != itemId + 1) {
			return;
		}
		for (int i : Config.UNDROPPABLE_ITEMS) {
			if (i == itemId) {
				droppable = false;
				break;
			}
		}
		if (c.playerItemsN[slot] != 0 && itemId != -1
				&& c.playerItems[slot] == itemId + 1) {
			if (droppable) {
				if (c.underAttackBy > 0) {
					if (c.getShops().getItemShopValue(itemId) > 1000) {
						c.sendMessage("You may not drop items worth more than 1000 while in combat.");
						return;
					}
				}
				Server.itemHandler.createGroundItem(c, itemId, c.getX(), c.getY(),
						c.playerItemsN[slot], c.getId());
				c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
				PlayerSave.saveGame(c);
				c.DroppingItem().writeDroppingItemLog(itemId,
						c.playerItemsN[slot]);
			} else {
				c.sendMessage("This items cannot be dropped.");
			}
		}

	}
}