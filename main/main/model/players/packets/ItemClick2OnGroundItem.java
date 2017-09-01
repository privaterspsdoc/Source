package main.model.players.packets;

import main.model.players.Client;
import main.model.players.PacketType;
import main.model.players.skills.Firemaking;
import main.model.players.skills.LogData.logData;

public class ItemClick2OnGroundItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		final int itemX = c.getInStream().readSignedWordBigEndian();
		final int itemY = c.getInStream().readSignedWordBigEndianA();
		final int itemId = c.getInStream().readUnsignedWordA();
		System.out.println("ItemClick2OnGroundItem - " + c.playerName + " - "
				+ itemId + " - " + itemX + " - " + itemY);
		for (logData l : logData.values()) {
			if (itemId == l.getLogId()) {
				Firemaking.attemptFire(c, 590, itemId, itemX, itemY, true);
			}
		}
	}
}