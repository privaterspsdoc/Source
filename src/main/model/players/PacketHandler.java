package main.model.players;

import main.Config;
import main.model.players.clan.InterfaceAction;
import main.model.players.clan.JoinChat;
import main.model.players.clan.ReceiveString;
import main.model.players.packets.AttackPlayer;
import main.model.players.packets.Bank10;
import main.model.players.packets.Bank5;
import main.model.players.packets.BankAll;
import main.model.players.packets.BankX1;
import main.model.players.packets.BankX2;
import main.model.players.packets.ChallengePlayer;
import main.model.players.packets.ChangeAppearance;
import main.model.players.packets.ChangeRegions;
import main.model.players.packets.Chat;
import main.model.players.packets.ClickItem;
import main.model.players.packets.ClickNPC;
import main.model.players.packets.ClickObject;
import main.model.players.packets.ClickingButtons;
import main.model.players.packets.ClickingInGame;
import main.model.players.packets.ClickingStuff;
import main.model.players.packets.Commands;
import main.model.players.packets.Dialogue;
import main.model.players.packets.DropItem;
import main.model.players.packets.FollowPlayer;
import main.model.players.packets.IdleLogout;
import main.model.players.packets.ItemClick2;
import main.model.players.packets.ItemClick2OnGroundItem;
import main.model.players.packets.ItemClick3;
import main.model.players.packets.ItemOnGroundItem;
import main.model.players.packets.ItemOnItem;
import main.model.players.packets.ItemOnNpc;
import main.model.players.packets.ItemOnObject;
import main.model.players.packets.ItemOnPlayer;
import main.model.players.packets.MagicOnFloorItems;
import main.model.players.packets.MagicOnItems;
import main.model.players.packets.MoveItems;
import main.model.players.packets.PickupItem;
import main.model.players.packets.PrivateMessaging;
import main.model.players.packets.RemoveItem;
import main.model.players.packets.Report;
import main.model.players.packets.SilentPacket;
import main.model.players.packets.Trade;
import main.model.players.packets.Walking;
import main.model.players.packets.WearItem;

public class PacketHandler {

	private static PacketType packetId[] = new PacketType[256];

	static {

		SilentPacket u = new SilentPacket();
		packetId[3] = u;
		packetId[202] = u;
		packetId[77] = u;
		packetId[86] = u;
		packetId[78] = u;
		packetId[36] = u;
		packetId[226] = u;
		packetId[246] = u;
		packetId[14] = new ItemOnPlayer();
		//packetId[62] = new ItemOnPlayer();
		//packetId[206] = new ItemOnPlayer();
		packetId[148] = u;
		packetId[183] = u;
		packetId[230] = u;
		packetId[136] = u;
		packetId[189] = u;
		packetId[152] = u;
		packetId[200] = u;
		packetId[85] = u;
		packetId[165] = u;
		packetId[238] = u;
		packetId[150] = u;
		packetId[253] = new ItemClick2OnGroundItem();
		packetId[218] = new Report();
		packetId[40] = new Dialogue();
		ClickObject co = new ClickObject();
		packetId[132] = co;
		packetId[252] = co;
		packetId[70] = co;
		packetId[57] = new ItemOnNpc();
		ClickNPC cn = new ClickNPC();
		packetId[72] = cn;
		packetId[131] = cn;
		packetId[155] = cn;
		packetId[17] = cn;
		packetId[21] = cn;
		packetId[16] = new ItemClick2();
		packetId[75] = new ItemClick3();
		packetId[122] = new ClickItem();
		packetId[241] = new ClickingInGame();
		packetId[4] = new Chat();
		packetId[236] = new PickupItem();
		packetId[87] = new DropItem();
		packetId[185] = new ClickingButtons();
		packetId[130] = new ClickingStuff();
		packetId[103] = new Commands();
		packetId[214] = new MoveItems();
		packetId[237] = new MagicOnItems();
		packetId[181] = new MagicOnFloorItems();
		packetId[202] = new IdleLogout();
		AttackPlayer ap = new AttackPlayer();
		packetId[73] = ap;
		packetId[249] = ap;
		packetId[128] = new ChallengePlayer();
		packetId[39] = new Trade();
		packetId[139] = new FollowPlayer();
		packetId[41] = new WearItem();
		packetId[145] = new RemoveItem();
		packetId[117] = new Bank5();
		packetId[43] = new Bank10();
		packetId[129] = new BankAll();
		packetId[101] = new ChangeAppearance();
		PrivateMessaging pm = new PrivateMessaging();
		packetId[188] = pm;
		packetId[126] = pm;
		packetId[215] = pm;
		packetId[59] = pm;
		packetId[95] = pm;
		packetId[133] = pm;
		packetId[135] = new BankX1();
		packetId[208] = new BankX2();
		Walking w = new Walking();
		packetId[98] = w;
		packetId[164] = w;
		packetId[248] = w;
		packetId[53] = new ItemOnItem();
		packetId[192] = new ItemOnObject();
		packetId[25] = new ItemOnGroundItem();
		ChangeRegions cr = new ChangeRegions();
		PacketHandler.packetId[60] = new JoinChat();
		PacketHandler.packetId[127] = new ReceiveString();
		PacketHandler.packetId[213] = new InterfaceAction();
		packetId[121] = cr;
		packetId[210] = cr;
	}

	public static void processPacket(Client c, int packetType, int packetSize) {
        PacketType p = packetId[packetType];
        if(p != null && packetType > 0 && packetType < 257 && packetType == c.packetType && packetSize == c.packetSize) {
            if (Config.sendServerPackets && c.playerRights == 3) {
                c.sendMessage("PacketType: " + packetType + ". PacketSize: " + packetSize + ".");
            }
            try {
                p.processPacket(c, packetType, packetSize);
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(c.playerName + "is sending invalid PacketType: " + packetType + ". PacketSize: " + packetSize);
        }
    }

}
