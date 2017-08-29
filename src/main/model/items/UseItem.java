package main.model.items;

import main.Config;
import main.Server;
import main.model.players.Client;
import main.model.players.skills.Firemaking;
import main.util.Misc;

/**
 * @author Sanity
 * @author Ryan
 * @author Lmctruck30 Revised by Shawn Notes by Shawn
 */

public class UseItem {

	/**
	 * Using items on an object.
	 * 
	 * @param c
	 * @param objectID
	 * @param objectX
	 * @param objectY
	 * @param itemId
	 */
	public static void ItemonObject(Client c, int objectID, int objectX,
			int objectY, int itemId) {
		if (!c.getItems().playerHasItem(itemId, 1))
			return;
		switch (objectID) {
		case 15621:
			if (itemId == 1127) {
				c.sendMessage("You load this item onto the magic animator.");
				c.runePlate = true;
				c.getItems().deleteItem(1127, 1);
			}
			if (itemId == 1079) {
				c.sendMessage("You load this item onto the magic animator.");
				c.runeLegs = true;
				c.getItems().deleteItem(1079, 1);
			}
			if (itemId == 1163) {
				c.sendMessage("You load this item onto the magic animator.");
				c.runeHelm = true;
				c.getItems().deleteItem(1163, 1);
			}
			
			if (itemId == 1127 && c.runeHelm == true && c.runeLegs == true) {
				c.sendMessage("This was the third item needed, the armour set comes to life.");
				c.runePlate = false;
				c.runeLegs = false;
				c.runeHelm = false;
				c.getItems().deleteItem(1127, 1);
				Server.npcHandler.spawnNpc(c, 4284, 2857, 3538, 0, 1, 120, 15, 100, 100, true, true);
			}
			if (itemId == 1079 && c.runeHelm == true && c.runePlate == true) {
				c.sendMessage("This was the third item needed, the armour set comes to life.");
				c.runePlate = false;
				c.runeLegs = false;
				c.runeHelm = false;
				c.getItems().deleteItem(1079, 1);
				Server.npcHandler.spawnNpc(c, 4284, 2857, 3538, 0, 1, 120, 15, 100, 100, true, true);
			}
			if (itemId == 1163 && c.runePlate == true && c.runeLegs == true) {
				c.sendMessage("This was the third item needed, the armour set comes to life.");
				c.getItems().deleteItem(1163, 1);
				c.runePlate = false;
				c.runeLegs = false;
				c.runeHelm = false;
				Server.npcHandler.spawnNpc(c, 4284, 2857, 3538, 0, 1, 120, 15, 100, 100, true, true);
			}
		break;
		case 2783:
			c.getSmithingInt().showSmithInterface(itemId);
			break;
		case 8151:
		case 8389:
		case 8132:
		case 7848: // /flower patch catherby
			c.getFarming().checkItemOnObject(itemId);
			break;

			case 409:
				if (c.getPrayer().isBone(itemId) && c.usingAltar==false) {
					c.usingAltar=true;
					c.altarItemId=itemId;
					c.getOutStream().createFrame(27);
					}
			break;
		/*
		 * case 2728: case 12269: c.getCooking().itemOnObject(itemId); break;
		 */
		default:
			if (c.playerRights == 3)
				Misc.println("Player At Object id: " + objectID
						+ " with Item id: " + itemId);
			break;
		}

	}

	/**
	 * Using items on items.
	 * 
	 * @param c
	 * @param itemUsed
	 * @param useWith
	 */
	// public static void ItemonItem(Client c, int itemUsed, int useWith) {
	public static void ItemonItem(final Client c, final int itemUsed,
			final int useWith, final int itemUsedSlot, final int usedWithSlot) {
		c.Summoning.ItemonItem(itemUsed, useWith);	
		if (itemUsed == 227 || useWith == 227)
			c.getHerblore().handlePotMaking(itemUsed, useWith);
		if (c.getItems().getItemName(itemUsed).contains("(")
				&& c.getItems().getItemName(useWith).contains("("))
			c.getPotMixing().mixPotion2(itemUsed, useWith);
		if (itemUsed == 1733 || useWith == 1733)
			c.getCrafting().handleLeather(itemUsed, useWith);
		if (itemUsed == 1755 || useWith == 1755)
			c.getCrafting().handleChisel(itemUsed, useWith);
		if (itemUsed == 946 || useWith == 946)
			c.getFletching().handleLog(itemUsed, useWith);
		if (itemUsed == 53 || useWith == 53 || itemUsed == 52 || useWith == 52)
			c.getFletching().makeArrows(itemUsed, useWith);
		if ((itemUsed == 1540 && useWith == 11286)
				|| (itemUsed == 11286 && useWith == 1540)) {
			if (c.playerLevel[c.playerSmithing] >= 95) {
				c.getItems()
						.deleteItem(1540, c.getItems().getItemSlot(1540), 1);
				c.getItems().deleteItem(11286, c.getItems().getItemSlot(11286),
						1);
				c.getItems().addItem(11283, 1);
				c.sendMessage("You combine the two materials to create a dragonfire shield.");
				c.getPA().addSkillXP(500 * Config.SMITHING_EXPERIENCE,
						c.playerSmithing);
			} else {
				c.sendMessage("You need a smithing level of 95 to create a dragonfire shield.");
			}
		}
		if (itemUsed == 4151 && useWith == 12004 || itemUsed == 12004 && useWith == 4151) {
			c.getItems().makeAbbyTent();
		}
		if (itemUsed == 1755 && useWith == 6573 || itemUsed == 6573 && useWith == 1755) {
			c.getItems().onyxOption();
		} 
		if (itemUsed == 9142 && useWith == 9190 || itemUsed == 9190
				&& useWith == 9142) {
			if (c.playerLevel[c.playerFletching] >= 58) {
				int boltsMade = c.getItems().getItemAmount(itemUsed) > c
						.getItems().getItemAmount(useWith) ? c.getItems()
						.getItemAmount(useWith) : c.getItems().getItemAmount(
						itemUsed);
				c.getItems().deleteItem(useWith,
						c.getItems().getItemSlot(useWith), boltsMade);
				c.getItems().deleteItem(itemUsed,
						c.getItems().getItemSlot(itemUsed), boltsMade);
				c.getItems().addItem(9241, boltsMade);
				c.getPA().addSkillXP(
						boltsMade * 6 * Config.FLETCHING_EXPERIENCE,
						c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 58 to fletch this item.");
			}
		}
		if (itemUsed == 9143 && useWith == 9191 || itemUsed == 9191
				&& useWith == 9143) {
			if (c.playerLevel[c.playerFletching] >= 63) {
				int boltsMade = c.getItems().getItemAmount(itemUsed) > c
						.getItems().getItemAmount(useWith) ? c.getItems()
						.getItemAmount(useWith) : c.getItems().getItemAmount(
						itemUsed);
				c.getItems().deleteItem(useWith,
						c.getItems().getItemSlot(useWith), boltsMade);
				c.getItems().deleteItem(itemUsed,
						c.getItems().getItemSlot(itemUsed), boltsMade);
				c.getItems().addItem(9242, boltsMade);
				c.getPA().addSkillXP(
						boltsMade * 7 * Config.FLETCHING_EXPERIENCE,
						c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 63 to fletch this item.");
			}
		}
		if (itemUsed == 9143 && useWith == 9192 || itemUsed == 9192
				&& useWith == 9143) {
			if (c.playerLevel[c.playerFletching] >= 65) {
				int boltsMade = c.getItems().getItemAmount(itemUsed) > c
						.getItems().getItemAmount(useWith) ? c.getItems()
						.getItemAmount(useWith) : c.getItems().getItemAmount(
						itemUsed);
				c.getItems().deleteItem(useWith,
						c.getItems().getItemSlot(useWith), boltsMade);
				c.getItems().deleteItem(itemUsed,
						c.getItems().getItemSlot(itemUsed), boltsMade);
				c.getItems().addItem(9243, boltsMade);
				c.getPA().addSkillXP(
						boltsMade * 7 * Config.FLETCHING_EXPERIENCE,
						c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 65 to fletch this item.");
			}
		}
		if (itemUsed == 9144 && useWith == 9193 || itemUsed == 9193
				&& useWith == 9144) {
			if (c.playerLevel[c.playerFletching] >= 71) {
				int boltsMade = c.getItems().getItemAmount(itemUsed) > c
						.getItems().getItemAmount(useWith) ? c.getItems()
						.getItemAmount(useWith) : c.getItems().getItemAmount(
						itemUsed);
				c.getItems().deleteItem(useWith,
						c.getItems().getItemSlot(useWith), boltsMade);
				c.getItems().deleteItem(itemUsed,
						c.getItems().getItemSlot(itemUsed), boltsMade);
				c.getItems().addItem(9244, boltsMade);
				c.getPA().addSkillXP(
						boltsMade * 10 * Config.FLETCHING_EXPERIENCE,
						c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 71 to fletch this item.");
			}
		}
		if (itemUsed == 9144 && useWith == 9194 || itemUsed == 9194
				&& useWith == 9144) {
			if (c.playerLevel[c.playerFletching] >= 58) {
				int boltsMade = c.getItems().getItemAmount(itemUsed) > c
						.getItems().getItemAmount(useWith) ? c.getItems()
						.getItemAmount(useWith) : c.getItems().getItemAmount(
						itemUsed);
				c.getItems().deleteItem(useWith,
						c.getItems().getItemSlot(useWith), boltsMade);
				c.getItems().deleteItem(itemUsed,
						c.getItems().getItemSlot(itemUsed), boltsMade);
				c.getItems().addItem(9245, boltsMade);
				c.getPA().addSkillXP(
						boltsMade * 13 * Config.FLETCHING_EXPERIENCE,
						c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 58 to fletch this item.");
			}
		}
		if (itemUsed == 1601 && useWith == 1755 || itemUsed == 1755
				&& useWith == 1601) {
			if (c.playerLevel[c.playerFletching] >= 63) {
				c.getItems()
						.deleteItem(1601, c.getItems().getItemSlot(1601), 1);
				c.getItems().addItem(9192, 15);
				c.getPA().addSkillXP(8 * Config.FLETCHING_EXPERIENCE,
						c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 63 to fletch this item.");
			}
		}
		if (itemUsed == 1607 && useWith == 1755 || itemUsed == 1755
				&& useWith == 1607) {
			if (c.playerLevel[c.playerFletching] >= 65) {
				c.getItems()
						.deleteItem(1607, c.getItems().getItemSlot(1607), 1);
				c.getItems().addItem(9189, 15);
				c.getPA().addSkillXP(8 * Config.FLETCHING_EXPERIENCE,
						c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 65 to fletch this item.");
			}
		}
		if (itemUsed == 1605 && useWith == 1755 || itemUsed == 1755
				&& useWith == 1605) {
			if (c.playerLevel[c.playerFletching] >= 71) {
				c.getItems()
						.deleteItem(1605, c.getItems().getItemSlot(1605), 1);
				c.getItems().addItem(9190, 15);
				c.getPA().addSkillXP(8 * Config.FLETCHING_EXPERIENCE,
						c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 71 to fletch this item.");
			}
		}
		if (itemUsed == 1603 && useWith == 1755 || itemUsed == 1755
				&& useWith == 1603) {
			if (c.playerLevel[c.playerFletching] >= 73) {
				c.getItems()
						.deleteItem(1603, c.getItems().getItemSlot(1603), 1);
				c.getItems().addItem(9191, 15);
				c.getPA().addSkillXP(8 * Config.FLETCHING_EXPERIENCE,
						c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 73 to fletch this item.");
			}
		}
		if (itemUsed == 590 || useWith == 590) {
			Firemaking.attemptFire(c, itemUsed, useWith, c.absX, c.absY, false);
		}
		if (itemUsed == 1615 && useWith == 1755 || itemUsed == 1755
				&& useWith == 1615) {
			if (c.playerLevel[c.playerFletching] >= 73) {
				c.getItems()
						.deleteItem(1615, c.getItems().getItemSlot(1615), 1);
				c.getItems().addItem(9193, 15);
				c.getPA().addSkillXP(8 * Config.FLETCHING_EXPERIENCE,
						c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 73 to fletch this item.");
			}
		}
		if (itemUsed >= 11710 && itemUsed <= 11714 && useWith >= 11710
				&& useWith <= 11714) {
			if (c.getItems().hasAllShards()) {
				c.getItems().makeBlade();
			}
		}
		if (itemUsed == 2368 && useWith == 2366 || itemUsed == 2366
				&& useWith == 2368) {
			c.getItems().deleteItem(2368, c.getItems().getItemSlot(2368), 1);
			c.getItems().deleteItem(2366, c.getItems().getItemSlot(2366), 1);
			c.getItems().addItem(1187, 1);
		}

		if (c.getItems().isHilt(itemUsed) || c.getItems().isHilt(useWith)) {
			int hilt = c.getItems().isHilt(itemUsed) ? itemUsed : useWith;
			int blade = c.getItems().isHilt(itemUsed) ? useWith : itemUsed;
			if (blade == 11690) {
				c.getItems().makeGodsword(hilt);
			}
		}
		
		if (itemUsed == 13754 && useWith == 13734) {
			c.getItems().createBlessedSpiritShield();
		}
		
		if (itemUsed == 13734 && useWith == 13754) {
			c.getItems().createBlessedSpiritShield();
		}
		
		if (itemUsed == 13736 && useWith == 13746) {
			c.getItems().createArcane();
		}
		
		if (itemUsed == 13746 && useWith == 13736) {
			c.getItems().createArcane();
		}
		
		if (itemUsed == 13748 && useWith == 13736) {
			c.getItems().createDivine();
		}
		
		if (itemUsed == 13748 && useWith == 13736) {
			c.getItems().createDivine();
		}
		
		if (itemUsed == 13752 && useWith == 13736) {
			c.getItems().createSpectral();
		}
		
		if (itemUsed == 13752 && useWith == 13736) {
			c.getItems().createSpectral();
		}
		
		if (itemUsed == 13750 && useWith == 13736) {
			c.getItems().createElysian();
		}
		
		if (itemUsed == 13750 && useWith == 13736) {
			c.getItems().createElysian();
		}
		
		if (itemUsed == 11230 && useWith == 12926 || itemUsed == 12926 && useWith == 11230) {
			int dart = 11230;
			int amount = c.getItems().getItemAmount(dart);
			c.getItems().deleteItem(dart, amount);
			c.dartsLoaded += amount;
			if (amount <= 1) {
			c.sendMessage("@red@You load " + amount + " dart into your blowpipe.");
			} else {
				c.sendMessage("@red@You load " + amount + " darts into your blowpipe.");
			}
		
		}
		
		if (itemUsed == 12922 && useWith == 1755 || itemUsed == 1755 && useWith == 12922) {
			c.getItems().makeBlowpipe();
		}
		
		if (itemUsed == 12932  && useWith == 11791 || itemUsed == 11791 && useWith == 12932) {
			c.getItems().makeTSOTD();
		}
		
		if (itemUsed == 12932 && useWith == 11907 || itemUsed == 11907 && useWith == 12932) {
			c.getItems().makeSwamp();
		}
		
		if (itemUsed == 12927 && useWith == 1755 || itemUsed == 1755 && useWith == 12927) {
			c.getItems().makeSerp();
		}

		switch (itemUsed) {
		/*
		 * case 1511: case 1521: case 1519: case 1517: case 1515: case 1513:
		 * case 590: c.getFiremaking().checkLogType(itemUsed, useWith); break;
		 */

		default:
			if (c.playerRights == 3)
				Misc.println("Player used Item id: " + itemUsed
						+ " with Item id: " + useWith);
			break;
		}
	}

	/**
	 * Using items on NPCs.
	 * 
	 * @param c
	 * @param itemId
	 * @param npcId
	 * @param slot
	 */
	public static void ItemonNpc(Client c, int itemId, int npcId, int slot) {
		switch (itemId) {

		default:
			if (c.playerRights == 3)
				Misc.println("Player used Item id: " + itemId
						+ " with Npc id: " + npcId + " With Slot : " + slot);
			break;
		}

	}

}
