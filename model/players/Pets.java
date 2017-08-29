package main.model.players;

import main.Server;
import main.model.npcs.NPCHandler;

/**
 * 
 * @author Tokashi
 *
 */

public class Pets {

	public void pickUp(Client c, int Type) {
		/*
		 * if (c.inWild){
		 * c.sendMessage("How did you get a pet in the wilderness?"); }
		 */
		for (int i = 0; i < NPCHandler.maxNPCs; i++) {
			if (NPCHandler.npcs[i] == null)
				continue;
		}
		for (int i = 0; i < NPCHandler.maxNPCs; i++) {
			if (NPCHandler.npcs[i] != null) {
				if (NPCHandler.npcs[i].npcType == Type) {
					if (NPCHandler.npcs[i].spawnedBy == c.playerId
							&& NPCHandler.npcs[i].spawnedBy > 0) {
						NPCHandler.npcs[i].absX = 0;
						NPCHandler.npcs[i].absY = 0;
						NPCHandler.npcs[i] = null;
						break;
					}
				}
			}
		}
	}

	static int[][] Pets = { { 3505, 7583 }, // Hell Kitten
			{ 3506, 7584 }, // Lazy Hellcat
			{ 766, 1560 }, // Hell Kitten
			{ 3507, 7585 }, // Wily hellcat
			{ 765, 1559 }, // Pet Kitten
			{ 764, 1558 }, // Pet Kitten
			{ 763, 1557 }, // Pet Kitten
			{ 762, 1556 }, // Pet Kitten
			{ 761, 1555 }, // Pet Kitten
			{ 768, 1561 }, // Pet Kitten
			{ 769, 1562 }, // Pet Kitten
			{ 770, 1563 }, // Pet Kitten
			{ 771, 1564 }, // Pet Kitten
			{ 772, 1565 }, // Pet Kitten
			{ 773, 1566 }, // Pet Kitten
			{ 4000, 12653 }, // Prince
			{ 4003, 12694 }, // Ele Jr
			{ 4001, 12650 }, // Bandos
			{ 4005, 12649 }, // Arma
			{ 4006, 12652 }, // Zammy
			{ 4007, 12651 }, // Sara
			{ 4008, 12643 }, // Dag Sup
			{ 4009, 12644 }, // Dag Pri
			{ 4010, 12645 }, // Dag Rex
			{ 6305, 12655 } // Pet Kraken
	};

	public static boolean isPet(int item, int pet) {
		for (int[] check : Pets) {
			if (item == check[1] || pet == check[0])
				return true;
		}
		return false;
	}

	public void pickUpClean(Client c, int id) {
		for (int i = 0; i < Pets.length; i++)
			if (Pets[i][0] == id)
				c.getItems().addItem(Pets[i][1], 1);
		for (int i = 0; i < NPCHandler.maxNPCs; i++) {
			if (NPCHandler.npcs[i] == null)
				continue;
			if (NPCHandler.npcs[i].npcType == id) {
				NPCHandler.npcs[i].absX = 0;
				NPCHandler.npcs[i].absY = 0;
			}
		}
		c.summonId = -1;
		c.hasNpc = false;
	}

	public boolean dropPet(Client c, int itemId) {
		if (c.hasNpc || c.summonId > 0) {
			c.sendMessage("You already have a follower");
			return false;
		}
		c.turnPlayerTo(c.absX, c.absY - 1);
		Server.npcHandler.spawnNpc3(c, Server.npcHandler.summonItemId(itemId),
				c.absX, c.absY - 1, c.heightLevel, 0, 120, 25, 200, 200, true,
				false, true);
		c.getItems().deleteItem(itemId, 1);
		c.hasNpc = true;
		return true;
	}
}