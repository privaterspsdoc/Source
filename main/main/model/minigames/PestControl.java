package main.model.minigames;

import main.model.npcs.NPCHandler;
import main.model.npcs.NPC;
import main.model.players.Client;
import main.model.players.PlayerHandler;
import main.Server;

/**
 * @author Sanity
 */

public class PestControl {

	public PestControl() {

	}

	public static final int GAME_TIMER = 42; // 5 minutes
	public static final int WAIT_TIMER = 7;

	public static int gameTimer = -1;
	public static int waitTimer = 15;
	public int properTimer = 0;

	public void process() {
		if (properTimer > 0) {
			properTimer--;
			return;
		} else {
			properTimer = 4;
		}
		if (waitTimer > 0)
			waitTimer--;
		else if (waitTimer == 0)
			startGame();
		if (gameTimer > 0) {
			gameTimer--;
			if (allPortalsDead()) {
				endGame(true);
				System.out.println("A game of Pest Control has been completed.");
			}
		} else if (gameTimer == 0)
			endGame(false);
			//killAllNPCS();
	}

	public void startGame() {
		if (playersInBoat() > 0) {
			gameTimer = GAME_TIMER;
			waitTimer = -1;
			// spawn npcs
			spawnNpcs();
			// move players into game
			for (int j = 0; j < PlayerHandler.players.length; j++) {
				if (PlayerHandler.players[j] != null) {
					if (PlayerHandler.players[j].inPcBoat()) {
						movePlayer(j);
					}
				}
			}
		} else {
			waitTimer = WAIT_TIMER;
			
			for (int j = 0; j < PlayerHandler.players.length; j++) {
				if (PlayerHandler.players[j] != null) {
					if (PlayerHandler.players[j].inPcBoat()) {
						Client c = (Client) PlayerHandler.players[j];
						c.sendMessage("There must be at least one player to start a game of pest control.");
					}
				}
			}
			
		}
	}

	public static int playersInBoat() {
		int count = 0;
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				if (PlayerHandler.players[j].inPcBoat()) {
					count++;
				}
			}
		}
		return count;
	}

	public void endGame(boolean won) {
		gameTimer = -1;
		waitTimer = WAIT_TIMER;
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				if (PlayerHandler.players[j].inPcGame()) {
					Client c = (Client) PlayerHandler.players[j];
					c.getPA().movePlayer(2657, 2639, 0);
					if (won && c.pcDamage > 15) {
						c.sendMessage("You earn four pest control points and 15,000 GP for defeating the monsters.");
						c.pcPoints += 4;
						c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]);
						c.playerLevel[5] = c.getLevelForXP(c.playerXP[5]);
						c.specAmount = 10;
						c.getItems().addItem(995, 15000);
						c.getPA().refreshSkill(3);
						c.getPA().refreshSkill(5);
					} else if (won) {
						c.sendMessage("The void knights notice your lack of zeal.");
						//c.sendMessage("Next time, try causing more damage.");
					} else {
						c.sendMessage("Sadly, you did not destroy all of the portals.");
					}
					c.pcDamage = 0;
					c.getItems().addSpecialBar(
					c.playerEquipment[c.playerWeapon]);
					c.getCombat().resetPrayers();
					killAllNPCS();
					killKnight();
				}
			}
		}
/*
		for (int j = 0; j < NPCHandler.npcs.length; j++) {
			if (NPCHandler.npcs[j] != null) {
				if (NPCHandler.npcs[j].npcType >= 3777
						&& NPCHandler.npcs[j].npcType <= 3780)
					NPCHandler.npcs[j] = null;
			}
		}
		*/
	}

	public boolean allPortalsDead() {
		int count = 0;
		for (int j = 0; j < NPCHandler.npcs.length; j++) {
			if (NPCHandler.npcs[j] != null) {
				if (NPCHandler.npcs[j].npcType >= 6142
						&& NPCHandler.npcs[j].npcType <= 6145)
					if (NPCHandler.npcs[j].needRespawn)
						count++;
			}
		}
		return count >= 4;
	}

	public void movePlayer(int index) {
		Client c = (Client) PlayerHandler.players[index];
		if (c.combatLevel < 50) {
			c.sendMessage("You need a combat level of 50 to enter this boat.");
			return;
		}
		c.getPA().movePlayer(2658, 2611, 0);
	}

	public void spawnNpcs() {
		NPCHandler.spawnNpc2(6142, 2628, 2591, 0, 0, 200, 0, 0, 100);
		NPCHandler.spawnNpc2(6143, 2680, 2588, 0, 0, 200, 0, 0, 100);
		NPCHandler.spawnNpc2(6144, 2669, 2570, 0, 0, 200, 0, 0, 100);
		NPCHandler.spawnNpc2(6145, 2645, 2569, 0, 0, 200, 0, 0, 100);
		NPCHandler.spawnNpc2(3782, 2657, 2592, 0, 0, 0, 0, 0, 0);
	}

	public static void killAllNPCS() {
        for (int j = 0; j < Server.npcHandler.npcs.length; j++) {
            if (Server.npcHandler.npcs[j] != null) {
                if (Server.npcHandler.npcs[j].npcType >= 6142 && Server.npcHandler.npcs[j].npcType <= 6145) {
                    Server.npcHandler.npcs[j] = null;
                }
            }
        }
	}
	public static void killKnight() {
        for (int j = 0; j < Server.npcHandler.npcs.length; j++) {
            if (Server.npcHandler.npcs[j] != null) {
                if (Server.npcHandler.npcs[j].npcType == 3782) {
                    Server.npcHandler.npcs[j] = null;
                }
            }
        }
	}
	
}