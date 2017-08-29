package main.model.minigames;

import main.model.players.Client;
import main.util.Misc;

public class Barrows {

	public Client c;

	public Barrows(Client client) {
		this.c = client;
	}

	public static final int[][] COFFIN_AND_BROTHERS = new int[][] {
			{ 6823, 2030 }, { 6772, 2029 }, { 6822, 2028 }, { 6773, 2027 },
			{ 6771, 2026 }, { 6821, 2025 } };

	public static int getRandomCoffin() {
		return Misc.random(COFFIN_AND_BROTHERS.length - 1);
	}

	public static boolean selectCoffin(Client c, int coffinId) {
		if (c.randomCoffin == 0) {
			c.randomCoffin = getRandomCoffin();
		}

		if (COFFIN_AND_BROTHERS[c.randomCoffin][0] == coffinId) {
			c.getDH().sendDialogues(1, -1);
			return true;
		} else {
			return false;
		}
	}

}
