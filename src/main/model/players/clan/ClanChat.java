package main.model.players.clan;

import main.model.players.Client;
import main.model.players.PacketType;
import main.util.Misc;

/*
 * Project Insanity - Evolved v.3
 * ClanChat.java
 */

public class ClanChat implements PacketType {

	@Override
	public void processPacket(final Client c, final int packetType, final int packetSize) {
		String textSent = Misc.longToPlayerName2(c.getInStream().readQWord());
		textSent = textSent.replaceAll("_", " ");
		// Server.clanChat.handleClanChat(c, textSent);
	}

}
