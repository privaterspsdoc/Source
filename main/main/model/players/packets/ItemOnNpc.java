package main.model.players.packets;

import main.model.items.UseItem;
import main.model.npcs.NPCHandler;
import main.model.players.Client;
import main.model.players.PacketType;

public class ItemOnNpc implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int itemId = c.getInStream().readSignedWordA();
		int i = c.getInStream().readSignedWordA();
		int slot = c.getInStream().readSignedWordBigEndian();
		int npcId = NPCHandler.npcs[i].npcType;
		if (!c.getItems().playerHasItem(itemId, 1)) {
			return;
		}
		UseItem.ItemonNpc(c, itemId, npcId, slot);
	}
}
