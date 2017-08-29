package main.model.players.packets;

import main.model.players.Client;
import main.model.players.PacketType;

/**
 * Bank X Items
 **/
public class BankX1 implements PacketType {

	public static final int PART1 = 135;
	public static final int PART2 = 208;
	public int XremoveSlot, XinterfaceID, XremoveID, Xamount;

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		if (packetType == 135) {
			c.xRemoveSlot = c.getInStream().readSignedWordBigEndian();
			c.xInterfaceId = c.getInStream().readUnsignedWordA();
			c.xRemoveId = c.getInStream().readSignedWordBigEndian();
		}
		if (c.xInterfaceId == 3900) {
			c.buyingX = true;
			c.outStream.createFrame(27);
			return;
		}  else {	
		if (c.xInterfaceId == 7423) {
				c.getItems().bankItem(c.xRemoveId, c.xRemoveSlot, Xamount);//Depo 1
				c.getItems().resetItems(7423);
		}}
		
		if (c.xInterfaceId == 3823) {
			c.sellingX = true;
			c.outStream.createFrame(27);
			return;
		}
		
		 if (c.buyingX) {
            if (Xamount <= 1000) {
                c.getShops().buyItem(c.xRemoveId, c.xRemoveSlot, Xamount);
            } else {
                c.sendMessage("You cannot buy more than 1000 At a time.");
            }
            c.xRemoveSlot = 0;
            c.xInterfaceId = 0;
            c.xRemoveId = 0;
            c.buyingX = false;
        }
            if(c.storing) {
                
                return;
		}
		
		 if (c.sellingX) {
            if (Xamount <= 1000) {
                c.getShops().sellItem(c.xRemoveId, c.xRemoveSlot, Xamount);
            } else {
                c.sendMessage("You cannot sell more than 1000 At a time.");
            }
            c.xRemoveSlot = 0;
            c.xInterfaceId = 0;
            c.xRemoveId = 0;
            c.sellingX = false;
        }
            if(c.storing) {
                
                return;
		}

		if (packetType == PART1) {
			synchronized (c) {
				c.getOutStream().createFrame(27);
			}
		}

	}
}
