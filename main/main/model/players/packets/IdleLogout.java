package main.model.players.packets;

import main.model.players.Client;
import main.model.players.PacketType;

public class IdleLogout implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		if(c.underAttackBy > 0 || c.underAttackBy2 > 0)
            return;
	}
}