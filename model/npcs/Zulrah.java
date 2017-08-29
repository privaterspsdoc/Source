
package main.model.npcs;

import main.Server;
import main.model.players.Client;
import main.util.Misc;

public class Zulrah {

private Client c;

		public Zulrah(Client Client) {
			this.c = Client;
		}
		
		boolean zulrahB;
		boolean zulrahR;
		boolean zulrahG;
		
		public void changeForm() {
		
		int metamorhpis = Misc.random(3);
		
		if (metamorhpis == 0) {
			c.sendMessage("Zulrah has changed forms.");
		} else if (metamorhpis == 1) {
			c.sendMessage("Zulrah has changed forms.");
		} else if (metamorhpis == 2) {
			c.sendMessage("Zulrah has changed forms.");
		}
		
		}
		
		public void spawnZulrahB() {
			zulrahB = true;
			zulrahG = false;
			zulrahR = false;
			int x = 3330;
			int y = 3335;
			//NPCHandler.startAnimation(5071, 2044);
			Server.npcHandler.spawnNpc(c, 2044, x, y, 0, 0, 500, 41, 1, 1, true, false);
		}
		
		public void spawnZulrahR() {
			zulrahR = true;
			zulrahB = false;
			zulrahG = false;
			int x = 3330;
			int y = 3335;
			//NPCHandler.startAnimation(5071, 2043);
			Server.npcHandler.spawnNpc(c, 2043, x, y, 0, 0, 500, 41, 1, 1, true, false);
		}
		
		public void spawnZulrahG() {
			zulrahG = true;
			zulrahB = false;
			zulrahR = false;
			int x = 3330;
			int y = 3335;
			//NPCHandler.startAnimation(5071, 2042);
			Server.npcHandler.spawnNpc(c, 2042, x, y, 0, 0, 500, 41, 1, 1, true, false);
		}
		


	
}

	