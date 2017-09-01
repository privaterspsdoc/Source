package main.model.minigames;

import main.Server;
import main.model.players.Client;

public class FightCaves {

	private final int[][] WAVES = {{ 2745 }};
	private int[][] coordinates = { { 2398, 5086 }, { 2387, 5095 },
			{ 2407, 5098 }, { 2417, 5082 }, { 2390, 5076 }, { 2410, 5090 } };

	public void spawnNextWave(Client c) {
		if (c != null) {
			if (c.waveId >= WAVES.length) {
				c.waveId = 0;
				return;
			}
			if (c.waveId < 0) {
				return;
			}
			// if (c.waveId == 1) {
			int npcAmount = WAVES[c.waveId].length;
			for (int j = 0; j < npcAmount; j++) {
				int npc = WAVES[c.waveId][j];
				int X = coordinates[j][0];
				int Y = coordinates[j][1];
				int H = c.heightLevel;
				int hp = getHp(npc);
				int max = getMax(npc);
				int atk = getAtk(npc);
				int def = getDef(npc);
				Server.npcHandler.spawnNpc(c, npc, X, Y, H, 0, hp, max, atk,
						def, true, false);
				c.waveId = 1;
				c.jadSpawn = true;
				c.tzhaarToKill = npcAmount;
				c.tzhaarKilled = 0;
			    }
			}
		}

	public int getHp(int npc) {
		switch (npc) {
		case 2745:
			return 250;
		}
		return 100;
	}

	public int getMax(int npc) {
		switch (npc) {
		case 2745:
			return 97;
		}
		return 5;
	}

	public int getAtk(int npc) {
		switch (npc) {
		case 2745:
			return 650;
		}
		return 100;
	}

	public int getDef(int npc) {
		switch (npc) {
		case 2745:
			return 500;
		}
		return 100;
	}

}