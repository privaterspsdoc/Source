package main.model.players.packets;

import main.model.players.Client;
import main.model.players.PacketType;
import main.net.Connection;

/**
 * Chat
 **/
public class Chat implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		c.setChatTextEffects(c.getInStream().readUnsignedByteS());
		c.setChatTextColor(c.getInStream().readUnsignedByteS());
		c.setChatTextSize((byte) (c.packetSize - 2));
		c.inStream.readBytes_reverseA(c.getChatText(), c.getChatTextSize(), 0);
		if (System.currentTimeMillis() < c.muteEnd) {
            c.sendMessage("You are muted for breaking a rule.");
			return;
		} else {
			c.muteEnd = 0;
		}
        if (Connection.isMuted(c))
        {
        	c.sendMessage("You are muted for breaking a rule.");
            return;
        }
		ReportHandler.addText(c.playerName, c.getChatText(), packetSize - 2);
        if (!Connection.isMuted(c))
        {
            c.setChatTextUpdateRequired(true);
        }
        else
        {
            c.sendMessage("You are muted for breaking a rule.");
            return;
        }
	}
}
