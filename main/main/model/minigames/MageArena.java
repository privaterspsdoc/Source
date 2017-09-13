package main.model.minigames;

import main.Server;
import main.model.npcs.*;
import main.model.players.Client;
import main.model.players.Player;

/**
* Mage Arena
* @author Valhalla
*/

public class MageArena {

	public Client client;

	public MageArena(Client client) {
		this.client = client;
	}

	/*public void changeMaRound() {
		if(client != null) {
			client.spawned = false;
			switch(client.roundNpc) {
				case 1:
				client.roundNpc = 2;
				client.maRound = client.maRound == 0 ? 1 : client.maRound;
				break;

				case 2:
				client.roundNpc = 3;
				client.maRound = client.maRound == 1 ? 2 : client.maRound;
				break;

				case 3:
				client.roundNpc = 4;
				client.maRound = client.maRound == 2 ? 3 : client.maRound;
				break;

				case 4:
				client.roundNpc = 5;
				client.maRound = client.maRound == 3 ? 4 : client.maRound;
				break;
			}
		}
	}*/

	public void endMa() {
		if(client != null) {
			client.spawned = false;
			client.roundNpc = 0;
			client.getPA().movePlayer(3217, 9621, 0);
			client.height = 0;
		}
	}

        public long mDelay;
	public void startMa() {
		if(client != null) {
                        client.sendMessage("0  "+client.roundNpc +" "+ client.spawned);
			client.getPA().removeAllWindows();
			client.getPA().movePlayer(3104, 3934, 0);
			client.roundNpc = 2;
			client.spawned = true;
			Server.npcHandler.spawnNpc(client, 907, 3105, 3934, 0, 1, 3, 17, 70, 60, true, true);
                        }
	}

	public void showInformation() {
		for(int i = 8144; i < 8195; i++) {
			client.getPA().sendFrame126("", i);
		}
		client.getPA().sendFrame126("@dre@Mage Arena", 8144);
		client.getPA().sendFrame126("", 8145);
		if(client.maRound == 0) {
			client.getPA().sendFrame126("Talk to Kolodion at the Mage Bank", 8147);
			client.getPA().sendFrame126("to start this quest.", 8148);
		} else if(client.maRound == 1) {
			client.getPA().sendFrame126("@str@Talk to Kolodion at the Mage Bank", 8147);
			client.getPA().sendFrame126("@str@to start this quest.", 8148);
			client.getPA().sendFrame126("", 8149);
			client.getPA().sendFrame126("You've accepted his challenge!", 8150);
			client.getPA().sendFrame126("If you die speak to Kolodion again.", 8151);
		} else if(client.maRound == 2) {
			client.getPA().sendFrame126("@str@Talk to Kolodion at the Mage Bank", 8147);
			client.getPA().sendFrame126("@str@to start this quest.", 8148);
			client.getPA().sendFrame126("", 8149);
			client.getPA().sendFrame126("@str@You've accepted his challenge!", 8150);
			client.getPA().sendFrame126("@str@If you die speak to Kolodion again.", 8151);
			client.getPA().sendFrame126("", 8152);
			client.getPA().sendFrame126("You have completed this quest!", 8153);
		}
		client.getPA().showInterface(8134);
	}

}