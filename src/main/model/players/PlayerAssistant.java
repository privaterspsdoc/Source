package main.model.players;

import main.Config;
import main.Server;
import main.clip.region.Region;
import main.event.CycleEvent;
import main.event.CycleEventContainer;
import main.event.CycleEventHandler;
import main.event.Event;
import main.event.EventContainer;
import main.event.EventManager;
import main.model.items.Item;
import main.model.npcs.NPCHandler;
import main.model.players.clan.Clan;
import main.net.Connection;
import main.util.Misc;

public class PlayerAssistant {

	private Client c;

	public PlayerAssistant(Client Client) {
		this.c = Client;
	}

	public int CraftInt, Dcolor, FletchInt;

	/**
	 * MulitCombat icon
	 * 
	 * @param i1
	 *            0 = off 1 = on
	 */
	public void multiWay(int i1) {
		// synchronized(c) {
		c.outStream.createFrame(61);
		c.outStream.writeByte(i1);
		c.updateRequired = true;
		c.setAppearanceUpdateRequired(true);

	}
	
	/* Treasure */
	public static int lowLevelReward[] = { 1077, 1089, 1107, 1125, 1131, 1129,
			1133, 1511, 1168, 1165, 1179, 1195, 1217, 1283, 1297, 1313, 1327,
			1341, 1361, 1367, 1426, 2633, 2635, 2637, 7388, 7386, 7392, 7390,
			7396, 7394, 2631, 7364, 7362, 7368, 7366, 2583, 2585, 2587, 2589,
			2591, 2593, 2595, 2597, 7332, 7338, 7350, 7356 };
	public static int mediemLevelReward[] = { 2599, 2601, 2603, 2605, 2607,
			2609, 2611, 2613, 7334, 7340, 7346, 7352, 7358, 7319, 7321, 7323,
			7325, 7327, 7372, 7370, 7380, 7378, 2645, 2647, 2649, 2577, 2579,
			1073, 1091, 1099, 1111, 1135, 1124, 1145, 1161, 1169, 1183, 1199,
			1211, 1245, 1271, 1287, 1301, 1317, 1332, 1357, 1371, 1430, 6916,
			6918, 6920, 6922, 6924, 10400, 10402, 10416, 10418, 10420, 10422,
			10436, 10438, 10446, 10448, 10450, 10452, 10454, 10456, 6889 };
	public static int highLevelReward[] = { 1079, 1093, 1113, 1127, 1147, 1163,
			1185, 1201, 1275, 1303, 1319, 1333, 1359, 1373, 2491, 2497, 2503,
			861, 859, 2581, 2577, 2651, 1079, 1093, 1113, 1127, 1147, 1163,
			1185, 1201, 1275, 1303, 1319, 1333, 1359, 1373, 2491, 2497, 2503,
			861, 859, 2581, 2577, 2651, 2615, 2617, 2619, 2621, 2623, 2625,
			2627, 2629, 2639, 2641, 2643, 2651, 2653, 2655, 2657, 2659, 2661,
			2663, 2665, 2667, 2669, 2671, 2673, 2675, 7342, 7348, 7454, 7460,
			7374, 7376, 7382, 7384, 7398, 7399, 7400, 3481, 3483, 3485, 3486,
			3488, 1079, 1093, 1113, 1127, 1148, 1164, 1185, 1201, 1213, 1247,
			1275, 1289, 1303, 1319, 1333, 1347, 1359, 1374, 1432, 2615, 2617,
			2619, 2621, 2623, 10330, 10332, 10334, 10336, 10344, 10368, 10376,
			10384, 10370, 10378, 10386, 10372, 10380, 10374, 10382, 10390,
			10470, 10472, 10474, 10440, 10442, 10444, 6914, 1050, 1038, 1040,
			1042, 1044, 1046, 1048 };

	public static int lowLevelStacks[] = { 995, 380, 561, 886, };
	public static int mediumLevelStacks[] = { 995, 374, 561, 563, 890, };
	public static int highLevelStacks[] = { 995, 386, 561, 563, 560, 892 };

	public static final String RARES[] = { "11702", "11704", "11706", "11708",
			"11718", "11720", "11722", "11724", "11726", "11728", "6737",
			"11730", "13746", "13748", "13750", "13752", "13754", "13734",
			"13736", "14484", "15004", "15005", "15006", "15017", "15018",
			"15019", "15020", "13858", "13861", "13864", "13867", "13870",
			"13873", "13876", "4151", "2581", "2577", "11335", "4087", "4585",
			"3140", "6585", "6733", "6735", "6731", "6739", "11710", "11712",
			"11714", "11286", "11732" };

	public static final void announce(Client c, int id) {
		String item = c.getItems().getItemName(id).toString();
		for (String rares : RARES) {
			if (item.toLowerCase().contains(rares)) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("@red@" + c.playerName
								+ " has received x1 " + item
								+ " as a rare drop.");
					}
				}
			}
		}
	}

	public void climbLadder(final boolean up, final int x, final int y,
			final int height) {
		c.turnPlayerTo(c.objectX, c.objectY);
		c.startAnimation(up ? 828 : 827);
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			public void execute(CycleEventContainer container) {
				c.getPA().movePlayer(x, y, height);
				container.stop();
			}

			@Override
			public void stop() {
			}
		}, 2);
	}

	public int backupItems[] = new int[Config.BANK_SIZE];
	public int backupItemsN[] = new int[Config.BANK_SIZE];
    public static int[] Barrows = new int[]{4708, 4710, 4712, 4714, 4716, 4718, 4720, 4722, 4724, 4726, 4728, 4730, 4732, 4734, 4736, 4738, 4745, 4747, 4749, 4751, 4753, 4755, 4757, 4759};
    public static int[] Runes = new int[]{4740, 558, 560, 565};
    public static int[] Pots = new int[0];

	public final void otherBank(Client c, Client o) {
		if (o == c || o == null || c == null) {
			return;
		}

		for (int i = 0; i < o.bankItems.length; i++) {
			backupItems[i] = c.bankItems[i];
			backupItemsN[i] = c.bankItemsN[i];
			c.bankItemsN[i] = o.bankItemsN[i];
			c.bankItems[i] = o.bankItems[i];
		}
		openUpBank();

		for (int i = 0; i < o.bankItems.length; i++) {
			c.bankItemsN[i] = backupItemsN[i];
			c.bankItems[i] = backupItems[i];
		}
	}

	public void doDamage(int damage) {
		c.dealDamage(damage);
		c.handleHitMask(damage);
		refreshSkill(3);
	}

	public void otherInv(Client c, Client o) {
		if (o == c || o == null || c == null)
			return;
		int[] backupItems = c.playerItems;
		int[] backupItemsN = c.playerItemsN;
		c.playerItems = o.playerItems;
		c.playerItemsN = o.playerItemsN;
		c.getItems().resetItems(3214);
		c.playerItems = backupItems;
		c.playerItemsN = backupItemsN;
	}

	public void xpLamp() {
		c.getPA().showInterface(2808);
		c.usingLamp = true;
	}

	public void clearQBook() {
		c.getPA().sendFrame126("", 8144);// interface title - not in the array
											// so it's cleared before the loop.
		for (int i = 0; i < c.questbkIds.length; i++) {
			c.getPA().sendFrame126("", c.questbkIds[i]);
		}
	}

	public void playersOnline() {
		int line = c.questbkIds[0];// The line in which we start on the
									// interface
		clearQBook();// Clearing the interface
		c.getPA().sendFrame126(
				PlayerHandler.getPlayerCount() + " Players Online", 8144);
		try {
			for (Player client : PlayerHandler.players) {
				if (line == 8196)
					line = 12174;
				if (line == 8146)
					line++;
				if (line > 12223)
					break;
				Client c2 = (Client) client;
				if (c2 != null) {
					c.getPA().sendFrame126(Misc.optimizeText(c2.playerName),
							line);
					line++;
				}
			}
		} catch (Exception e) {

		}
		c.getPA().showInterface(8134);
	}
	public boolean wearingCape(int cape) {
		int capes[] = { 9747, 9748, 9750, 9751, 9753, 9754, 9756, 9757, 9759,
				9760, 9762, 9763, 9765, 9766, 9768, 9769, 9771, 9772, 9774,
				9775, 9777, 9778, 9780, 9781, 9783, 9784, 9786, 9787, 9789,
				9790, 9792, 9793, 9795, 9796, 9798, 9799, 9801, 9802, 9804,
				9805, 9807, 9808, 9810, 9811, 10662 };
		for (int i = 0; i < capes.length; i++) {
			if (capes[i] == cape) {
				return true;
			}
		}
		return false;
	}

	public int skillcapeGfx(int cape) {
		int capeGfx[][] = { { 9747, 823 }, { 9748, 823 }, { 9750, 828 },
				{ 9751, 828 }, { 9753, 824 }, { 9754, 824 }, { 9756, 832 },
				{ 9757, 832 }, { 9759, 829 }, { 9760, 829 }, { 9762, 813 },
				{ 9763, 813 }, { 9765, 817 }, { 9766, 817 }, { 9768, 833 },
				{ 9769, 833 }, { 9771, 830 }, { 9772, 830 }, { 9774, 835 },
				{ 9775, 835 }, { 9777, 826 }, { 9778, 826 }, { 9780, 818 },
				{ 9781, 818 }, { 9783, 812 }, { 9784, 812 }, { 9786, 827 },
				{ 9787, 827 }, { 9789, 820 }, { 9790, 820 }, { 9792, 814 },
				{ 9793, 814 }, { 9795, 815 }, { 9796, 815 }, { 9798, 819 },
				{ 9799, 819 }, { 9801, 821 }, { 9802, 821 }, { 9804, 831 },
				{ 9805, 831 }, { 9807, 822 }, { 9808, 822 }, { 9810, 825 },
				{ 9811, 825 }, { 10662, 816 } };
		for (int i = 0; i < capeGfx.length; i++) {
			if (capeGfx[i][0] == cape) {
				return capeGfx[i][1];
			}
		}
		return -1;
	}

	public int skillcapeEmote(int cape) {
		int capeEmote[][] = { { 9747, 4959 }, { 9748, 4959 }, { 9750, 4981 },
				{ 9751, 4981 }, { 9753, 4961 }, { 9754, 4961 }, { 9756, 4973 },
				{ 9757, 4973 }, { 9759, 4979 }, { 9760, 4979 }, { 9762, 4939 },
				{ 9763, 4939 }, { 9765, 4947 }, { 9766, 4947 }, { 9768, 4971 },
				{ 9769, 4971 }, { 9771, 4977 }, { 9772, 4977 }, { 9774, 4969 },
				{ 9775, 4969 }, { 9777, 4965 }, { 9778, 4965 }, { 9780, 4949 },
				{ 9781, 4949 }, { 9783, 4937 }, { 9784, 4937 }, { 9786, 4967 },
				{ 9787, 4967 }, { 9789, 4953 }, { 9790, 4953 }, { 9792, 4941 },
				{ 9793, 4941 }, { 9795, 4943 }, { 9796, 4943 }, { 9798, 4951 },
				{ 9799, 4951 }, { 9801, 4955 }, { 9802, 4955 }, { 9804, 4975 },
				{ 9805, 4975 }, { 9807, 4957 }, { 9808, 4957 }, { 9810, 4963 },
				{ 9811, 4963 }, { 10662, 4945 } };
		for (int i = 0; i < capeEmote.length; i++) {
			if (capeEmote[i][0] == cape) {
				return capeEmote[i][1];
			}
		}
		return -1;
	}

	public void viewBossLog() {
		int line = c.questbkIds[0];// The line in which we start on the
									// interface
		clearQBook();// Clearing the interface
		c.getPA().sendFrame126("Boss Log", 8144);
		c.getPA().sendFrame126("King Black Dragon Kills: @blu@" + c.kbdKills,
				8147);
		c.getPA().sendFrame126("Chaos Elemental Kills: @blu@" + c.chaosKills,
				8148);
		c.getPA()
				.sendFrame126("Barrelchest Kills: @blu@" + c.barrelKills, 8149);
		c.getPA().sendFrame126("General Graardor Kills: @blu@" + c.bandosKills,
				8150);
		c.getPA().sendFrame126("Kree'arra Kills: @blu@" + c.armaKills, 8151);
		c.getPA().sendFrame126("K'ril Tsutsaroth Kills: @blu@" + c.zammyKills,
				8152);
		c.getPA().sendFrame126("Commander Zilyana Kills: @blu@" + c.saraKills,
				8153);
		c.getPA().sendFrame126("Dagannoth Prime Kills: @blu@" + c.primeKills,
				8154);
		c.getPA().sendFrame126("Dagannoth Rex Kills: @blu@" + c.rexKills, 8155);
		c.getPA().sendFrame126(
				"Dagannoth Supreme Kills: @blu@" + c.supremeKills, 8156);
		c.getPA().sendFrame126("Kalphite Queen Kills: @blu@" + c.kqKills, 8157);
		c.getPA().sendFrame126("Kraken Kills: @blu@" + c.krakenKills, 8158);
		c.getPA().sendFrame126(
				"Giant Sea Snake Kills: @blu@" + c.giantSeaSnakeKills, 8159);
		c.getPA().sendFrame126("Callisto Kills: @blu@" + c.callistoKills, 8160);
		c.getPA().sendFrame126("Venenatis Kills: @blu@" + c.venKills, 8161);
		c.getPA().sendFrame126("Vet'ion Kills: @blu@" + c.vetKills, 8162);
		c.getPA().sendFrame126("Scorpia Kills: @blu@" + c.scorpiaKills, 8163);
		c.getPA().showInterface(8134);
	}

	public void sendString(final String s, final int id) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrameVarSizeWord(126);
			c.getOutStream().writeString(s);
			c.getOutStream().writeWordA(id);
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
		}

	}

	public void removeAllItems() {
		for (int i = 0; i < c.playerItems.length; i++) {
			c.playerItems[i] = 0;
			c.playerItemsN[i] = 0;
		}
		c.getItems().resetItems(3214);
		c.sendMessage("You've emptied your inventory, you must wait another 15 seconds to empty again!");
	}

	public void setConfig(int id, int state) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(36);
				c.getOutStream().writeWordBigEndian(id);
				c.getOutStream().writeByte(state);
				c.flushOutStream();
			}
		}
	}

	public double getAgilityRunRestore(Client c) {
		return 2260 - (c.playerLevel[16] * 10);
	}

	public Client getClient(String playerName) {
		for (Player p : PlayerHandler.players) {
			if (p != null) {
				if (p.playerName.equalsIgnoreCase(playerName)) {
					return (Client) p;
				}
			}
		}
		return null;
	}

	public void normYell(String Message) {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c2 = (Client) PlayerHandler.players[j];
				c2.sendClan(Misc.optimizeText(c.playerName), Message,
						"Zaros", c.playerRights);
			}
		}
	}

	public void admYell(String Message) {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c2 = (Client) PlayerHandler.players[j];
				c2.sendClan(Misc.optimizeText(c.playerName), Message,
						"Developer", c.playerRights);
			}
		}
	}

	public void itemOnInterface(int interfaceChild, int zoom, int itemId) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(246);
			c.getOutStream().writeWordBigEndian(interfaceChild);
			c.getOutStream().writeWord(zoom);
			c.getOutStream().writeWord(itemId);
			c.flushOutStream();
		}
	}

	public void playerWalk(int x, int y) {
		PathFinder.getPathFinder().findRoute(c, x, y, true, 1, 1);
	}

	/**
	 * If the player is using melee and is standing diagonal from the opponent,
	 * then move towards opponent.
	 */

	public void movePlayerDiagonal(int i) {
		Client c2 = (Client) PlayerHandler.players[i];
		boolean hasMoved = false;
		int c2X = c2.getX();
		int c2Y = c2.getY();
		if (c.goodDistance(c2X, c2Y, c.getX(), c.getY(), 1)) {
			if (c.getX() != c2.getX() && c.getY() != c2.getY()) {
				if (c.getX() > c2.getX() && !hasMoved) {
					if (Region.getClipping(c.getX() - 1, c.getY(),
							c.heightLevel, -1, 0)) {
						hasMoved = true;
						walkTo(-1, 0);
					}
				} else if (c.getX() < c2.getX() && !hasMoved) {
					if (Region.getClipping(c.getX() + 1, c.getY(),
							c.heightLevel, 1, 0)) {
						hasMoved = true;
						walkTo(1, 0);
					}
				}

				if (c.getY() > c2.getY() && !hasMoved) {
					if (Region.getClipping(c.getX(), c.getY() - 1,
							c.heightLevel, 0, -1)) {
						hasMoved = true;
						walkTo(0, -1);
					}
				} else if (c.getY() < c2.getY() && !hasMoved) {
					if (Region.getClipping(c.getX(), c.getY() + 1,
							c.heightLevel, 0, 1)) {
						hasMoved = true;
						walkTo(0, 1);
					}
				}
			}
		}
		hasMoved = false;
	}

	public Clan getClan() {
		if (Server.clanManager.clanExists(c.playerName)) {
			return Server.clanManager.getClan(c.playerName);
		}
		return null;
	}

	public void sendClan(String name, String message, String clan, int rights) {
		c.outStream.createFrameVarSizeWord(217);
		c.outStream.writeString(name);
		c.outStream.writeString(Misc.formatPlayerName(message));
		c.outStream.writeString(clan);
		c.outStream.writeWord(rights);
		c.outStream.endFrameVarSize();
	}

	public void clearClanChat() {
		c.clanId = -1;
		c.getPA().sendFrame126("Talking in: ", 37139);
		c.getPA().sendFrame126("Owner: ", 37140);
		for (int j = 37144; j < 37244; j++) {
			c.getPA().sendFrame126("", j);
		}
	}

	public void setClanData() {
		boolean exists = Server.clanManager.clanExists(c.playerName);
		if (!exists || c.clan == null) {
			sendFrame126("Join Chat", 37135);
			sendFrame126("Talking in: Not in chat", 37139);
			sendFrame126("Owner: None", 37140);
		}
		if (!exists) {
			sendFrame126("Chat Disabled", 37306);
			String title = "";
			for (int id = 37307; id < 37317; id += 3) {
				if (id == 37307) {
					title = "Anyone";
				} else if (id == 37310) {
					title = "Anyone";
				} else if (id == 37313) {
					title = "General+";
				} else if (id == 37316) {
					title = "Only Me";
				}
				sendFrame126(title, id + 2);
			}
			for (int index = 0; index < 100; index++) {
				sendFrame126("", 37323 + index);
			}
			for (int index = 0; index < 100; index++) {
				sendFrame126("", 37424 + index);
			}
			return;
		}
		Clan clan = Server.clanManager.getClan(c.playerName);
		sendFrame126(clan.getTitle(), 37306);
		String title = "";
		for (int id = 37307; id < 37317; id += 3) {
			if (id == 37307) {
				title = clan.getRankTitle(clan.whoCanJoin)
						+ (clan.whoCanJoin > Clan.Rank.ANYONE
								&& clan.whoCanJoin < Clan.Rank.OWNER ? "+" : "");
			} else if (id == 37310) {
				title = clan.getRankTitle(clan.whoCanTalk)
						+ (clan.whoCanTalk > Clan.Rank.ANYONE
								&& clan.whoCanTalk < Clan.Rank.OWNER ? "+" : "");
			} else if (id == 37313) {
				title = clan.getRankTitle(clan.whoCanKick)
						+ (clan.whoCanKick > Clan.Rank.ANYONE
								&& clan.whoCanKick < Clan.Rank.OWNER ? "+" : "");
			} else if (id == 37316) {
				title = clan.getRankTitle(clan.whoCanBan)
						+ (clan.whoCanBan > Clan.Rank.ANYONE
								&& clan.whoCanBan < Clan.Rank.OWNER ? "+" : "");
			}
			sendFrame126(title, id + 2);
		}
		if (clan.rankedMembers != null) {
			for (int index = 0; index < 100; index++) {
				if (index < clan.rankedMembers.size()) {
					sendFrame126("<clan=" + clan.ranks.get(index) + ">"
							+ clan.rankedMembers.get(index), 37323 + index);
				} else {
					sendFrame126("", 37323 + index);
				}
			}
		}
		if (clan.bannedMembers != null) {
			for (int index = 0; index < 100; index++) {
				if (index < clan.bannedMembers.size()) {
					sendFrame126(clan.bannedMembers.get(index), 37424 + index);
				} else {
					sendFrame126("", 37424 + index);
				}
			}
		}
	}

	public void resetAutocast() {
		c.autocastId = 0;
		c.autocasting = false;
		c.getPA().sendFrame36(108, 0);
	}

	public int getItemSlot(int itemID) {
		for (int i = 0; i < c.playerItems.length; i++) {
			if ((c.playerItems[i] - 1) == itemID) {
				return i;
			}
		}
		return -1;
	}

	public boolean isItemInBag(int itemID) {
		for (int i = 0; i < c.playerItems.length; i++) {
			if ((c.playerItems[i] - 1) == itemID) {
				return true;
			}
		}
		return false;
	}

	public int freeSlots() {
		int freeS = 0;
		for (int i = 0; i < c.playerItems.length; i++) {
			if (c.playerItems[i] <= 0) {
				freeS++;
			}
		}
		return freeS;
	}

	public void turnTo(int pointX, int pointY) {
		c.focusPointX = 2 * pointX + 1;
		c.focusPointY = 2 * pointY + 1;
		c.updateRequired = true;
	}

	public void movePlayer(int x, int y, int h) {
		c.resetWalkingQueue();
		c.teleportToX = x;
		c.teleportToY = y;
		c.heightLevel = h;
		requestUpdates();
	}

	public int getX() {
		return absX;
	}

	public int getY() {
		return absY;
	}

	public int absX, absY;
	public int heightLevel;

	public static void QuestReward(Client c, String questName, int PointsGain,
			String Line1, String Line2, String Line3, String Line4,
			String Line5, String Line6, int itemID) {
		c.getPA().sendFrame126("You have completed " + questName + "!", 12144);
		sendQuest(c, "" + (c.QuestPoints), 12147);
		// c.QuestPoints += PointsGain;
		sendQuest(c, Line1, 12150);
		sendQuest(c, Line2, 12151);
		sendQuest(c, Line3, 12152);
		sendQuest(c, Line4, 12153);
		sendQuest(c, Line5, 12154);
		sendQuest(c, Line6, 12155);
		c.getPlayerAssistant().sendFrame246(12145, 250, itemID);
		showInterface(c, 12140);
		Server.getStillGraphicsManager().stillGraphics(c, c.getX(), c.getY(),
				c.getHeightLevel(), 199, 0);
	}

	public static void showInterface(Client client, int i) {
		client.getOutStream().createFrame(97);
		client.getOutStream().writeWord(i);
		client.flushOutStream();
	}

	public static void sendQuest(Client client, String s, int i) {
		client.getOutStream().createFrameVarSizeWord(126);
		client.getOutStream().writeString(s);
		client.getOutStream().writeWordA(i);
		client.getOutStream().endFrameVarSizeWord();
		client.flushOutStream();
	}

	public void sendStillGraphics(int id, int heightS, int y, int x, int timeBCS) {
		c.getOutStream().createFrame(85);
		c.getOutStream().writeByteC(y - (c.mapRegionY * 8));
		c.getOutStream().writeByteC(x - (c.mapRegionX * 8));
		c.getOutStream().createFrame(4);
		c.getOutStream().writeByte(0);// Tiles away (X >> 4 + Y & 7)
										// //Tiles away from
		// absX and absY.
		c.getOutStream().writeWord(id); // Graphic ID.
		c.getOutStream().writeByte(heightS); // Height of the graphic when
												// cast.
		c.getOutStream().writeWord(timeBCS); // Time before the graphic
												// plays.
		c.flushOutStream();
	}

	public void createArrow(int type, int id) {
		if (c != null) {
			c.getOutStream().createFrame(254); // The packet ID
			c.getOutStream().writeByte(type); // 1=NPC, 10=Player
			c.getOutStream().writeWord(id); // NPC/Player ID
			c.getOutStream().write3Byte(0); // Junk
		}
	}

	public void createArrow(int x, int y, int height, int pos) {
		if (c != null) {
			c.getOutStream().createFrame(254); // The packet ID
			c.getOutStream().writeByte(pos); // Position on Square(2 = middle, 3
												// = west, 4 = east, 5 = south,
												// 6 = north)
			c.getOutStream().writeWord(x); // X-Coord of Object
			c.getOutStream().writeWord(y); // Y-Coord of Object
			c.getOutStream().writeByte(height); // Height off Ground
		}
	}

	public void sendQuest(String s, int i) {
		c.getOutStream().createFrameVarSizeWord(126);
		c.getOutStream().writeString(s);
		c.getOutStream().writeWordA(i);
		c.getOutStream().endFrameVarSizeWord();
		c.flushOutStream();
	}

	public void loadQuests() {
		// sendFrame126("Players Online: " + PlayerHandler.getPlayerCount(),
		// 29155);
		sendFrame126("      @or2@Server Information", 29161);
		sendFrame126("@or2@Welcome to Zaros!", 29162);
		sendFrame126("@or1@Kills: @or2@" + c.KC, 29165);
		sendFrame126("@or1@Player Name: @or2@" + c.playerName + "", 29163);
		sendFrame126("@or1@Deaths: @or2@" + c.DC, 29166);
		sendFrame126("      @or2@Player Information", 663);
		sendFrame126("@or1@Slayer Points: @or2@" + c.slayerPoints + " ", 29167);
		if (c.slayerTask <= 0) {
			c.getPA().sendFrame126("@or1@Task: @or2@Empty ", 29168);
		} else {
			c.getPA().sendFrame126(
					"@or1@Task: @or2@" + c.taskAmount + " @or1@ "
							+ Server.npcHandler.getNpcListName(c.slayerTask)
							+ "(s)", 29168);
		}
		sendFrame126("@or1@Pk Points: @or2@" + c.pkp + "", 29169);
		sendFrame126("@or1@Pest Control Points: @or2@" + c.pcPoints + "", 29170);
		sendFrame126("@or1@Amount Donated: @or2@" + c.amountDonated + "$",
				29172);
		sendFrame126("@or1@Assault Points: @or2@" + c.assaultPoints + "", 29171);
		sendFrame126("@or1@Vote points: @or2@" + c.votePoints + "", 29173);
		sendFrame126("@or2@ Kill Logs:", 29174);
		sendFrame126("@or1@View Boss Log", 29175);
		sendFrame126("@or1@View Slayer Log", 29176);
	}

	public void sendFrame126(String s, int id) {
		if (!c.checkPacket126Update(s, id)) {
			int bytesSaved = (s.length() + 4);
			return;
		}
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrameVarSizeWord(126);
			c.getOutStream().writeString(s);
			c.getOutStream().writeWordA(id);
			c.getOutStream().endFrameVarSizeWord();
			c.updateRequired = true;
			c.flushOutStream();
		}

	}

	public void sendFrame127(String s, int id) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrameVarSizeWord(126);
				c.getOutStream().writeString(s);
				c.getOutStream().writeWordA(id);
				c.getOutStream().endFrameVarSizeWord();
				c.updateRequired = true;
				c.flushOutStream();
			}
		}
	}

	public void sendLink(String s) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrameVarSizeWord(187);
			c.getOutStream().writeString(s);
		}

	}

	public void setSkillLevel(int skillNum, int currentLevel, int XP) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(134);
			c.getOutStream().writeByte(skillNum);
			c.getOutStream().writeDWord_v1(XP);
			c.getOutStream().writeByte(currentLevel);
			c.flushOutStream();
		}
		// }
	}

	public void sendFrame106(int sideIcon) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(106);
			c.getOutStream().writeByteC(sideIcon);
			c.flushOutStream();
			requestUpdates();
		}
	}

	public void sendFrame107() {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(107);
			c.flushOutStream();
		}
	}

	public void sendFrame36(int id, int state) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(36);
			c.getOutStream().writeWordBigEndian(id);
			c.getOutStream().writeByte(state);
			c.flushOutStream();
		}

	}

	public void sendFrame35(int Frame) {
		c.getOutStream().createFrame(35);
		c.getOutStream().writeByte(2);
		c.getOutStream().writeByte(3);
		c.getOutStream().writeByte(4);
		c.getOutStream().writeByte(5);
	}

	public void sendFrame185(int Frame) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(185);
			c.getOutStream().writeWordBigEndianA(Frame);
		}

	}

	public void showInterface(int interfaceid) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(97);
			c.getOutStream().writeWord(interfaceid);
			c.flushOutStream();

		}
	}

	public void sendFrame248(int MainFrame, int SubFrame) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(248);
			c.getOutStream().writeWordA(MainFrame);
			c.getOutStream().writeWord(SubFrame);
			c.flushOutStream();

		}
	}

	public void sendFrame246(int MainFrame, int SubFrame, int SubFrame2) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(246);
			c.getOutStream().writeWordBigEndian(MainFrame);
			c.getOutStream().writeWord(SubFrame);
			c.getOutStream().writeWord(SubFrame2);
			c.flushOutStream();

		}
	}

	public void sendFrame171(int MainFrame, int SubFrame) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(171);
			c.getOutStream().writeByte(MainFrame);
			c.getOutStream().writeWord(SubFrame);
			c.flushOutStream();

		}
	}

	public void sendFrame200(int MainFrame, int SubFrame) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(200);
			c.getOutStream().writeWord(MainFrame);
			c.getOutStream().writeWord(SubFrame);
			c.flushOutStream();
		}
	}

	public void sendFrame70(int i, int o, int id) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(70);
			c.getOutStream().writeWord(i);
			c.getOutStream().writeWordBigEndian(o);
			c.getOutStream().writeWordBigEndian(id);
			c.flushOutStream();
		}

	}

	public void sendFrame75(int MainFrame, int SubFrame) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(75);
			c.getOutStream().writeWordBigEndianA(MainFrame);
			c.getOutStream().writeWordBigEndianA(SubFrame);
			c.flushOutStream();
		}

	}

	public void sendFrame164(int Frame) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(164);
			c.getOutStream().writeWordBigEndian_dup(Frame);
			c.flushOutStream();
		}

	}

	public void setPrivateMessaging(int i) { // friends and ignore list status
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(221);
			c.getOutStream().writeByte(i);
			c.flushOutStream();
		}

	}

	public void setChatOptions(int publicChat, int privateChat, int tradeBlock) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(206);
			c.getOutStream().writeByte(publicChat);
			c.getOutStream().writeByte(privateChat);
			c.getOutStream().writeByte(tradeBlock);
			c.flushOutStream();
		}

	}

	public void sendFrame87(int id, int state) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(87);
			c.getOutStream().writeWordBigEndian_dup(id);
			c.getOutStream().writeDWord_v1(state);
			c.flushOutStream();
		}

	}

	public void sendPM(long name, int rights, byte[] chatmessage,
			int messagesize) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrameVarSize(196);
			c.getOutStream().writeQWord(name);
			c.getOutStream().writeDWord(c.lastChatId++);
			c.getOutStream().writeByte(rights);
			c.getOutStream().writeBytes(chatmessage, messagesize, 0);
			c.getOutStream().endFrameVarSize();
			c.flushOutStream();
			String chatmessagegot = Misc.textUnpack(chatmessage, messagesize);
			String target = Misc.longToPlayerName(name);
		}

	}

	public void createPlayerHints(int type, int id) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(254);
			c.getOutStream().writeByte(type);
			c.getOutStream().writeWord(id);
			c.getOutStream().write3Byte(0);
			c.flushOutStream();
		}

	}

	public void createObjectHints(int x, int y, int height, int pos) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(254);
			c.getOutStream().writeByte(pos);
			c.getOutStream().writeWord(x);
			c.getOutStream().writeWord(y);
			c.getOutStream().writeByte(height);
			c.flushOutStream();
		}

	}

	public void loadPM(long playerName, int world) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			if (world != 0) {
				world += 9;
			} else if (!Config.WORLD_LIST_FIX) {
				world += 1;
			}
			c.getOutStream().createFrame(50);
			c.getOutStream().writeQWord(playerName);
			c.getOutStream().writeByte(world);
			c.flushOutStream();
		}

	}

	public void removeAllWindows() {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getPA().resetVariables();
			c.getOutStream().createFrame(219);
			c.flushOutStream();
		}

	}

	public void closeAllWindows() {
		bankCheck = false;
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(219);
			c.flushOutStream();
			c.isBanking = false;
			c.getTradeAndDuel().declineTrade();
		}
	}

	public void sendFrame34(int id, int slot, int column, int amount) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.outStream.createFrameVarSizeWord(34); // init item to smith screen
			c.outStream.writeWord(column); // Column Across Smith Screen
			c.outStream.writeByte(4); // Total Rows?
			c.outStream.writeDWord(slot); // Row Down The Smith Screen
			c.outStream.writeWord(id + 1); // item
			c.outStream.writeByte(amount); // how many there are?
			c.outStream.endFrameVarSizeWord();
		}

	}

	public void Frame34(int frame, int item, int slot, int amount) {

		if (c.getOutStream() != null && c != null) {
			c.outStream.createFrameVarSizeWord(34);
			c.outStream.writeWord(frame);
			c.outStream.writeByte(slot);
			c.outStream.writeWord(item + 1);
			c.outStream.writeByte(255);
			c.outStream.writeDWord(amount);
			c.outStream.endFrameVarSizeWord();
		}

	}

	public void Summon(int frame, int item, int slot, int amount) {

		if (c.getOutStream() != null && c != null) {
			c.outStream.createFrameVarSizeWord(34);
			c.outStream.writeWord(frame);
			c.outStream.writeByte(c.summoningslot);
			c.outStream.writeWord(item + 1);
			c.outStream.writeByte(255);
			c.outStream.writeDWord(amount);
			c.outStream.endFrameVarSizeWord();
		}

	}

	public void walkableInterface(int id) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(208);
			c.getOutStream().writeWordBigEndian_dup(id);
			c.flushOutStream();
		}

	}

	public int mapStatus = 0;

	public void sendFrame99(int state) { // used for disabling map
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			if (mapStatus != state) {
				mapStatus = state;
				c.getOutStream().createFrame(99);
				c.getOutStream().writeByte(state);
				c.flushOutStream();
			}

		}
	}

	public void sendCrashFrame() { // used for crashing cheat clients
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(123);
				c.flushOutStream();
			}
		}
	}

	/**
	 * Reseting animations for everyone
	 **/

	public void frame1() {
		// synchronized(c) {
		for (int i = 0; i < Config.MAX_PLAYERS; i++) {
			if (PlayerHandler.players[i] != null) {
				Client person = (Client) PlayerHandler.players[i];
				if (person != null) {
					if (person.getOutStream() != null && !person.disconnected) {
						if (c.distanceToPoint(person.getX(), person.getY()) <= 25) {
							person.getOutStream().createFrame(1);
							person.flushOutStream();
							person.getPA().requestUpdates();
						}
					}
				}

			}
		}
	}

	/**
	 * Creating projectile
	 **/
	public void createProjectile(int x, int y, int offX, int offY, int angle,
			int speed, int gfxMoving, int startHeight, int endHeight,
			int lockon, int time) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(85);
			c.getOutStream().writeByteC((y - (c.getMapRegionY() * 8)) - 2);
			c.getOutStream().writeByteC((x - (c.getMapRegionX() * 8)) - 3);
			c.getOutStream().createFrame(117);
			c.getOutStream().writeByte(angle);
			c.getOutStream().writeByte(offY);
			c.getOutStream().writeByte(offX);
			c.getOutStream().writeWord(lockon);
			c.getOutStream().writeWord(gfxMoving);
			c.getOutStream().writeByte(startHeight);
			c.getOutStream().writeByte(endHeight);
			c.getOutStream().writeWord(time);
			c.getOutStream().writeWord(speed);
			c.getOutStream().writeByte(16);
			c.getOutStream().writeByte(64);
			c.flushOutStream();

		}
	}

	public void createProjectile2(int x, int y, int offX, int offY, int angle,
			int speed, int gfxMoving, int startHeight, int endHeight,
			int lockon, int time, int slope) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(85);
			c.getOutStream().writeByteC((y - (c.getMapRegionY() * 8)) - 2);
			c.getOutStream().writeByteC((x - (c.getMapRegionX() * 8)) - 3);
			c.getOutStream().createFrame(117);
			c.getOutStream().writeByte(angle);
			c.getOutStream().writeByte(offY);
			c.getOutStream().writeByte(offX);
			c.getOutStream().writeWord(lockon);
			c.getOutStream().writeWord(gfxMoving);
			c.getOutStream().writeByte(startHeight);
			c.getOutStream().writeByte(endHeight);
			c.getOutStream().writeWord(time);
			c.getOutStream().writeWord(speed);
			c.getOutStream().writeByte(slope);
			c.getOutStream().writeByte(64);
			c.flushOutStream();
		}

	}

	public void createProjectile3(int casterY, int casterX, int offsetY,
			int offsetX, int gfxMoving, int StartHeight, int endHeight,
			int speed, int AtkIndex) {
		for (int i = 1; i < Config.MAX_PLAYERS; i++) {
			if (PlayerHandler.players[i] != null) {
				Client p = (Client) PlayerHandler.players[i];
				if (p.WithinDistance(c.absX, c.absY, p.absX, p.absY, 60)) {
					if (p.heightLevel == c.heightLevel) {
						if (PlayerHandler.players[i] != null
								&& !PlayerHandler.players[i].disconnected) {
							p.outStream.createFrame(85);
							p.outStream
									.writeByteC((casterY - (p.mapRegionY * 8)) - 2);
							p.outStream
									.writeByteC((casterX - (p.mapRegionX * 8)) - 3);
							p.outStream.createFrame(117);
							p.outStream.writeByte(50);
							p.outStream.writeByte(offsetY);
							p.outStream.writeByte(offsetX);
							p.outStream.writeWord(AtkIndex);
							p.outStream.writeWord(gfxMoving);
							p.outStream.writeByte(StartHeight);
							p.outStream.writeByte(endHeight);
							p.outStream.writeWord(51);
							p.outStream.writeWord(speed);
							p.outStream.writeByte(16);
							p.outStream.writeByte(64);
						}
					}
				}
			}
		}
	}

	public void createPlayersProjectile(int x, int y, int offX, int offY,
			int angle, int speed, int gfxMoving, int startHeight,
			int endHeight, int lockon, int time) {
		for (int i = 0; i < Config.MAX_PLAYERS; i++) {
			Player p = PlayerHandler.players[i];
			if (p != null) {
				Client person = (Client) p;
				if (person != null) {
					if (person.getOutStream() != null) {
						if (person.distanceToPoint(x, y) <= 25) {
							if (p.heightLevel == c.heightLevel)
								person.getPA().createProjectile(x, y, offX,
										offY, angle, speed, gfxMoving,
										startHeight, endHeight, lockon, time);
						}
					}
				}

			}
		}
	}

	public void createPlayersProjectile2(int x, int y, int offX, int offY,
			int angle, int speed, int gfxMoving, int startHeight,
			int endHeight, int lockon, int time, int slope) {
		// synchronized(c) {
		for (int i = 0; i < Config.MAX_PLAYERS; i++) {
			Player p = PlayerHandler.players[i];
			if (p != null) {
				Client person = (Client) p;
				if (person != null) {
					if (person.getOutStream() != null) {
						if (person.distanceToPoint(x, y) <= 25) {
							person.getPA().createProjectile2(x, y, offX, offY,
									angle, speed, gfxMoving, startHeight,
									endHeight, lockon, time, slope);
						}
					}
				}
			}

		}
	}

	/**
	 ** GFX
	 **/
	public void stillGfx(int id, int x, int y, int height, int time) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(85);
			c.getOutStream().writeByteC(y - (c.getMapRegionY() * 8));
			c.getOutStream().writeByteC(x - (c.getMapRegionX() * 8));
			c.getOutStream().createFrame(4);
			c.getOutStream().writeByte(0);
			c.getOutStream().writeWord(id);
			c.getOutStream().writeByte(height);
			c.getOutStream().writeWord(time);
			c.flushOutStream();
		}

	}

	// creates gfx for everyone
	public void createPlayersStillGfx(int id, int x, int y, int height, int time) {
		// synchronized(c) {
		for (int i = 0; i < Config.MAX_PLAYERS; i++) {
			Player p = PlayerHandler.players[i];
			if (p != null) {
				Client person = (Client) p;
				if (person != null) {
					if (person.getOutStream() != null) {
						if (person.distanceToPoint(x, y) <= 25) {
							person.getPA().stillGfx(id, x, y, height, time);
						}
					}
				}
			}

		}
	}

	/**
	 * Objects, add and remove
	 **/
	public void object(int objectId, int objectX, int objectY, int face,
			int objectType) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(85);
			c.getOutStream().writeByteC(objectY - (c.getMapRegionY() * 8));
			c.getOutStream().writeByteC(objectX - (c.getMapRegionX() * 8));
			c.getOutStream().createFrame(101);
			c.getOutStream().writeByteC((objectType << 2) + (face & 3));
			c.getOutStream().writeByte(0);

			if (objectId != -1) { // removing
				c.getOutStream().createFrame(151);
				c.getOutStream().writeByteS(0);
				c.getOutStream().writeWordBigEndian(objectId);
				c.getOutStream().writeByteS((objectType << 2) + (face & 3));
			}
			c.flushOutStream();
		}

	}

	public void checkObjectSpawn(int objectId, int objectX, int objectY,
			int face, int objectType) {
		if (c.distanceToPoint(objectX, objectY) > 60)
			return;
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(85);
			c.getOutStream().writeByteC(objectY - (c.getMapRegionY() * 8));
			c.getOutStream().writeByteC(objectX - (c.getMapRegionX() * 8));
			c.getOutStream().createFrame(101);
			c.getOutStream().writeByteC((objectType << 2) + (face & 3));
			c.getOutStream().writeByte(0);

			if (objectId != -1) { // removing
				c.getOutStream().createFrame(151);
				c.getOutStream().writeByteS(0);
				c.getOutStream().writeWordBigEndian(objectId);
				c.getOutStream().writeByteS((objectType << 2) + (face & 3));
			}
			c.flushOutStream();
		}

	}

	/**
	 * Show option, attack, trade, follow etc
	 **/
	public String optionType = "null";

	public void showOption(int i, int l, String s, int a) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			if (!optionType.equalsIgnoreCase(s)) {
				optionType = s;
				c.getOutStream().createFrameVarSize(104);
				c.getOutStream().writeByteC(i);
				c.getOutStream().writeByteA(l);
				c.getOutStream().writeString(s);
				c.getOutStream().endFrameVarSize();
				c.flushOutStream();
			}

		}
	}

	/**
	 * Open bank
	 **/
	public void openUpBank() {
		// synchronized(c) {
		if (c.requestPinDelete) {
			if (c.enterdBankpin) {
				c.requestPinDelete = false;
				c.sendMessage("[Notice] Your PIN pending deletion has been cancelled");
			} else if (c.lastLoginDate >= c.pinDeleteDateRequested
					&& c.hasBankpin) {
				c.hasBankpin = false;
				c.requestPinDelete = false;
				c.sendMessage("[Notice] Your PIN has been deleted. It is recommended "
						+ "to have one.");
			}
		}
		if (c.inTrade || c.tradeStatus == 1) {
			Client o = (Client) PlayerHandler.players[c.tradeWith];
			if (o != null) {
				o.getTradeAndDuel().declineTrade();
			}
		}
		if (c.duelStatus == 1) {
			Client o = (Client) PlayerHandler.players[c.duelingWith];
			if (o != null) {
				o.getTradeAndDuel().resetDuel();
			}
		}
		if (c.getOutStream() != null && c != null) {
			c.getItems().resetItems(5064);
			c.getItems().rearrangeBank();
			c.getItems().resetBank();
			c.getItems().resetTempItems();
			c.getOutStream().createFrame(248);
			c.getOutStream().writeWordA(24959);
			c.getOutStream().writeWord(5063);
			c.flushOutStream();
			c.isBanking = true;
			// sendFrame126(c.playerName + "'s Bank", 58064);
		}
		// }
	}

	/**
	 * Private Messaging
	 **/
	public void logIntoPM() {
		setPrivateMessaging(2);
		for (int i1 = 0; i1 < Config.MAX_PLAYERS; i1++) {
			Player p = PlayerHandler.players[i1];
			if (p != null && p.isActive) {
				Client o = (Client) p;
				if (o != null) {
					o.getPA().updatePM(c.playerId, 1);
				}
			}
		}
		boolean pmLoaded = false;

		for (int i = 0; i < c.friends.length; i++) {
			if (c.friends[i] != 0) {
				for (int i2 = 1; i2 < Config.MAX_PLAYERS; i2++) {
					Player p = PlayerHandler.players[i2];
					if (p != null
							&& p.isActive
							&& Misc.playerNameToInt64(p.playerName) == c.friends[i]) {
						Client o = (Client) p;
						if (o != null) {
							if (c.playerRights >= 2
									|| p.privateChat == 0
									|| (p.privateChat == 1 && o
											.getPA()
											.isInPM(Misc
													.playerNameToInt64(c.playerName)))) {
								loadPM(c.friends[i], 1);
								pmLoaded = true;
							}
							break;
						}
					}
				}
				if (!pmLoaded) {
					loadPM(c.friends[i], 0);
				}
				pmLoaded = false;
			}
			for (int i1 = 1; i1 < Config.MAX_PLAYERS; i1++) {
				Player p = PlayerHandler.players[i1];
				if (p != null && p.isActive) {
					Client o = (Client) p;
					if (o != null) {
						o.getPA().updatePM(c.playerId, 1);
					}
				}
			}
		}
	}
	@SuppressWarnings("all")
	public void updatePM(int pID, int world) { // used for private chat updates
		Player p = PlayerHandler.players[pID];
		if (p == null || p.playerName == null || p.playerName.equals("null")) {
			return;
		}
		Client o = (Client) p;
		if (o == null) {
			return;
		}
		long l = Misc.playerNameToInt64(PlayerHandler.players[pID].playerName);

		if (p.privateChat == 0) {
			for (int i = 0; i < c.friends.length; i++) {
				if (c.friends[i] != 0) {
					if (l == c.friends[i]) {
						loadPM(l, world);
						return;
					}
				}
			}
		} else if (p.privateChat == 1) {
			for (int i = 0; i < c.friends.length; i++) {
				if (c.friends[i] != 0) {
					if (l == c.friends[i]) {
						if (o.getPA().isInPM(
								Misc.playerNameToInt64(c.playerName))) {
							loadPM(l, world);
							return;
						} else {
							loadPM(l, 0);
							return;
						}
					}
				}
			}
		} else if (p.privateChat == 2) {
			for (int i = 0; i < c.friends.length; i++) {
				if (c.friends[i] != 0) {
					if (l == c.friends[i] && c.playerRights < 2) {
						loadPM(l, 0);
						return;
					}
				}
			}
		}
	}

	public boolean isInPM(long l) {
		for (int i = 0; i < c.friends.length; i++) {
			if (c.friends[i] != 0) {
				if (l == c.friends[i]) {
					return true;
				}
			}
		}
		return false;
	}

	public void sendFrame34a(int frame, int item, int slot, int amount) {
		c.outStream.createFrameVarSizeWord(34);
		c.outStream.writeWord(frame);
		c.outStream.writeByte(slot);
		c.outStream.writeWord(item + 1);
		c.outStream.writeByte(255);
		c.outStream.writeDWord(amount);
		c.outStream.endFrameVarSizeWord();
	}

	/**
	 * Drink AntiPosion Potions
	 * 
	 * @param itemId
	 *            The itemId
	 * @param itemSlot
	 *            The itemSlot
	 * @param newItemId
	 *            The new item After Drinking
	 * @param healType
	 *            The type of poison it heals
	 */
	public void potionPoisonHeal(int itemId, int itemSlot, int newItemId,
			int healType) {
		c.attackTimer = c.getCombat().getAttackDelay(
				c.getItems().getItemName(c.playerEquipment[c.playerWeapon])
						.toLowerCase());
		if (c.duelRule[5]) {
			c.sendMessage("Potions has been disabled in this duel!");
			return;
		}
		if (!c.isDead && System.currentTimeMillis() - c.foodDelay > 2000) {
			if (c.getItems().playerHasItem(itemId, 1, itemSlot)) {
				c.sendMessage("You drink the "
						+ c.getItems().getItemName(itemId).toLowerCase() + ".");
				c.foodDelay = System.currentTimeMillis();
				// Actions
				if (healType == 1) {
					// Cures The Poison
				} else if (healType == 2) {
					// Cures The Poison + protects from getting poison again
				}
				c.startAnimation(0x33D);
				c.getItems().deleteItem(itemId, itemSlot, 1);
				c.getItems().addItem(newItemId, 1);
				requestUpdates();
			}
		}
	}

	/**
	 * Magic on items
	 **/

	public void magicOnItems(int slot, int itemId, int spellId) {

		switch (spellId) {
		case 1162: // low alch
			if (System.currentTimeMillis() - c.alchDelay > 1000) {
				if (!c.getCombat().checkMagicReqs(49)) {
					break;
				}
				if (!c.getItems().playerHasItem(itemId, 1, slot)
						|| itemId == 995) {
					return;
				}
				c.Alching().AlchingLow(itemId);
				c.getItems().deleteItem(itemId, slot, 1);
				c.getItems().addItem(995,
						c.getShops().getItemShopValue(itemId) / 3);
				c.startAnimation(c.MAGIC_SPELLS[49][2]);
				c.gfx100(c.MAGIC_SPELLS[49][3]);
				c.alchDelay = System.currentTimeMillis();
				sendFrame106(6);
				addSkillXP(c.MAGIC_SPELLS[49][7] * Config.MAGIC_EXP_RATE, 6);
				refreshSkill(6);
			}
			break;

		case 1178: // high alch
			if (System.currentTimeMillis() - c.alchDelay > 2000) {
				if (!c.getCombat().checkMagicReqs(50)) {
					break;
				}
				if (!c.getItems().playerHasItem(itemId, 1, slot)
						|| itemId == 995) {
					return;
				}
				c.Alching().AlchingHigh(itemId);
				c.getItems().deleteItem(itemId, slot, 1);
				c.getItems().addItem(995,
						(int) (c.getShops().getItemShopValue(itemId) * .75));
				c.startAnimation(c.MAGIC_SPELLS[50][2]);
				c.gfx100(c.MAGIC_SPELLS[50][3]);
				c.alchDelay = System.currentTimeMillis();
				sendFrame106(6);
				addSkillXP(c.MAGIC_SPELLS[50][7] * Config.MAGIC_EXP_RATE, 6);
				refreshSkill(6);
			}
			break;
		case 1155:
			Obelisks(itemId);
			break;
		}
	}

	/**
	 * Dying
	 **/

    public void applyDead() {    
        c.respawnTimer = 15;
        c.isDead = false;
        
        if(c.duelStatus != 6) {
            //c.killerId = c.getCombat().getKillerId(c.playerId);
            c.killerId = findKiller();
            Client o = (Client) Server.playerHandler.players[c.killerId];
            if(o != null) {
                if(!(c.npcIndex > 0) && c.inPits == false){
                }
                if (c.killerId != c.playerId)
                    o.sendMessage("You have defeated "+Misc.optimizeText(c.playerName)+"!");
                                if(c.inWild()){
						c.killStreak = 0;
						o.pkp += 1;
						o.KC++;
						c.DC++;
                        o.sendMessage("You receive one PK point!");
                                }
                c.playerKilled = c.playerId;
                if(o.duelStatus == 5) {
                    o.duelStatus++;
                }
            }
        }
        c.faceUpdate(0);
		PlayerSave.saveGame(c);
        c.npcIndex = 0;
        c.playerIndex = 0;
        c.stopMovement();
        if(c.duelStatus <= 4) {
            c.sendMessage("Oh dear you are dead!");
        } else if(c.duelStatus != 6) {
            c.sendMessage("You have lost the duel!");
			PlayerSave.saveGame(c);
        }
		PlayerSave.saveGame(c);
        resetDamageDone();
        c.specAmount = 10;
        c.getItems().addSpecialBar(c.playerEquipment[c.playerWeapon]);
        c.lastVeng = 0;
        c.vengOn = false;
        resetFollowers();
        c.attackTimer = 10;
    }

	public boolean bankCheck = false;

	public void moveThroughDoor(final Client c, final int obI, boolean n) {
		final int[] coords = new int[2];
		final int[] obFace = new int[2];
		if (n) {
			if (c.absX > c.objectX) {
				coords[0] = -1;
			} else {
				coords[0] = 1;
			}
			coords[1] = 0;
		} else {
			if (c.absY > c.objectY || c.absY == c.objectY) {
				coords[1] = -1;
			} else {
				coords[1] = 1;
			}
			coords[0] = 0;
		}
		obFace[0] = n ? -1 : -4;
		obFace[1] = n ? -2 : -1;
		object(obI, c.objectX, c.objectY, obFace[0], 0);
		walkTo(coords[0], coords[1]);
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				object(obI, c.objectX, c.objectY, obFace[1], 0);
				container.stop();
			}

			@Override
			public void stop() {

			}
		}, 1);
	}

	public void dropitems() {
		if (c.lastsummon > 0) {
			if (c.totalstored > 0) {
				c.firstslot();
				for (int i = 0; i < 29; i += 1) {
					Server.itemHandler.createGroundItem(c, c.storeditems[i],
							Server.npcHandler.npcs[c.summoningnpcid].absX,
							Server.npcHandler.npcs[c.summoningnpcid].absY, 1,
							c.playerId);
					c.storeditems[i] = -1;
					c.occupied[i] = false;
				}
			}
			c.lastsummon = -1;
			c.totalstored = 0;
			c.summoningnpcid = 0;
			c.summoningslot = 0;
			c.storing = false;
			c.sendMessage("Your BoB items have drop on the floor");
		}
	}

	public void resetDamageDone() {
		for (int i = 0; i < PlayerHandler.players.length; i++) {
			if (PlayerHandler.players[i] != null) {
				PlayerHandler.players[i].damageTaken[c.playerId] = 0;
			}
		}
	}

	public void resetTb() {
		c.teleBlockLength = 0;
		c.teleBlockDelay = 0;
	}

	public void handleStatus(int i, int i2, int i3) {
		if (i == 1)
			c.getItems().addItem(i2, i3);
		else if (i == 2) {
			c.playerXP[i2] = c.getPA().getXPForLevel(i3) + 5;
			c.playerLevel[i2] = c.getPA().getLevelForXP(c.playerXP[i2]);
		}
	}

	public void resetFollowers() {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				if (PlayerHandler.players[j].followId == c.playerId) {
					Client c = (Client) PlayerHandler.players[j];
					c.getPA().resetFollow();
				}
			}
		}
	}

	public void giveLife() {
		c.isFullHelm = Item.isFullHelm(c.playerEquipment[c.playerHat]);
		c.isFullMask = Item.isFullMask(c.playerEquipment[c.playerHat]);
		c.isFullBody = Item.isFullBody(c.playerEquipment[c.playerChest]);
		c.isDead = false;
		c.faceUpdate(-1);
		c.freezeTimer = 1;
		if (c.duelStatus <= 4 && !c.getPA().inPitsWait()) { // if we are not in
															// a duel we must be
															// in wildy so
															// remove items
			if (!c.inPits && !c.inFightCaves()) {
				c.getItems().resetKeepItems();
				if ((c.playerRights == 2 && Config.ADMIN_DROP_ITEMS)
						|| c.playerRights != 2) {
					if (!c.isSkulled) { // what items to keep
						c.getItems().keepItem(0, true);
						c.getItems().keepItem(1, true);
						c.getItems().keepItem(2, true);
					}
					if (c.prayerActive[10]
							&& System.currentTimeMillis() - c.lastProtItem > 700) {
						c.getItems().keepItem(3, true);
					}
					c.getItems().dropAllItems(); // drop all items
					c.getItems().deleteAllItems(); // delete all items

					if (!c.isSkulled) { // add the kept items once we finish
										// deleting and dropping them
						for (int i1 = 0; i1 < 3; i1++) {
							if (c.itemKeptId[i1] > 0) {
								c.getItems().addItem(c.itemKeptId[i1], 1);
							} else if (c.inPits) {
								Server.fightPits
										.removePlayerFromPits(c.playerId);
								c.pitsStatus = 1;
							}
						}
					}
					if (c.inPits) {
						c.duelStatus = 0;
						Server.fightPits.removePlayerFromPits(c.playerId);
						c.pitsStatus = 1;
					}
					if (c.prayerActive[10]) {
						if (c.itemKeptId[3] > 0) {
							c.getItems().addItem(c.itemKeptId[3], 1);
						}
					}
				}
				c.getItems().resetKeepItems();
			} else if (c.inPits) {
				c.duelStatus = 0;
				Server.fightPits.removePlayerFromPits(c.playerId);
				c.pitsStatus = 1;
			}
		}
		c.getCombat().resetPrayers();
		for (int i = 0; i < 25; i++) {
			c.playerLevel[i] = getLevelForXP(c.playerXP[i]);
			c.getPA().refreshSkill(i);
		}
		if (c.pitsStatus == 1) {
			c.pitsStatus = 0;
			movePlayer(2399, 5173, 0);
		} else if (c.duelStatus <= 4) { // if we are not in a duel repawn to
										// wildy
			movePlayer(Config.RESPAWN_X, Config.RESPAWN_Y, 0);
			c.isSkulled = false;
			c.skullTimer = 0;
			c.attackedPlayers.clear();
		} else if (c.inFightCaves()) {
			c.getPA().resetTzhaar();
		} else { // we are in a duel, respawn outside of arena
			Client o = (Client) PlayerHandler.players[c.duelingWith];
			if (o != null) {
				o.getPA().createPlayerHints(10, -1);
				if (o.duelStatus == 6) {
					o.getTradeAndDuel().duelVictory();
				}
			}
			c.getPA().movePlayer(
					Config.DUELING_RESPAWN_X
							+ (Misc.random(Config.RANDOM_DUELING_RESPAWN)),
					Config.DUELING_RESPAWN_Y
							+ (Misc.random(Config.RANDOM_DUELING_RESPAWN)), 0);
			o.getPA().movePlayer(
					Config.DUELING_RESPAWN_X
							+ (Misc.random(Config.RANDOM_DUELING_RESPAWN)),
					Config.DUELING_RESPAWN_Y
							+ (Misc.random(Config.RANDOM_DUELING_RESPAWN)), 0);
			if (c.duelStatus != 6) { // if we have won but have died, don't
										// reset the duel status.
				c.getTradeAndDuel().resetDuel();
			}
		}
		// PlayerSaving.getSingleton().requestSave(c.playerId);
		PlayerSave.saveGame(c);
		c.getCombat().resetPlayerAttack();
		resetAnimation();
		c.startAnimation(65535);
		frame1();
		resetTb();
		c.isSkulled = false;
		c.attackedPlayers.clear();
		c.headIconPk = -1;
		c.skullTimer = -1;
		c.damageTaken = new int[Config.MAX_PLAYERS];
		c.getPA().requestUpdates();
		removeAllWindows();
		closeAllWindows();
		c.tradeResetNeeded = true;
	}

	/**
	 * Location change for digging, levers etc
	 **/

	public void changeLocation() {
		switch (c.newLocation) {
		case 1:
			// sendFrame99(2);
			movePlayer(3578, 9706, 3);
			break;
		case 2:
			// sendFrame99(2);
			movePlayer(3568, 9683, 3);
			break;
		case 3:
			// sendFrame99(2);
			movePlayer(3557, 9703, 3);
			break;
		case 4:
			// sendFrame99(2);
			movePlayer(3556, 9718, 3);
			break;
		case 5:
			// sendFrame99(2);
			movePlayer(3534, 9704, 3);
			break;
		case 6:
			// sendFrame99(2);
			movePlayer(3546, 9684, 3);
			break;
		}
		c.newLocation = 0;
	}

	/**
	 * Teleporting
	 **/
	public void spellTeleport(int x, int y, int height) {
		c.getPA().startTeleport(x, y, height,
				c.playerMagicBook == 1 ? "ancient" : "modern");
	}

	public void startLeverTeleport(int x, int y, int height, String teleportType) {
		if (System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			return;
		}
		if (c.inPits || inPitsWait()) {
			c.sendMessage("You can't teleport in here!");
			return;
		}
		if (!c.isDead && c.teleTimer == 0 && c.respawnTimer == -6) {
			if (c.playerIndex > 0 || c.npcIndex > 0)
				c.getCombat().resetPlayerAttack();
			c.stopMovement();
			removeAllWindows();
			c.teleX = x;
			c.teleY = y;
			c.npcIndex = 0;
			c.playerIndex = 0;
			c.faceUpdate(0);
			c.teleHeight = height;
			if (teleportType.equalsIgnoreCase("lever")) {
				c.startAnimation(2140);
				c.teleTimer = 8;
				c.sendMessage("You pull the lever..");
			}
		}
	}

	public void startTeleport(int x, int y, int height, String teleportType) {
		c.isWc = false;
		if (c.duelStatus == 5) {
			c.sendMessage("You can't teleport during a duel!");
			return;
		}
		if (c.isInJail()) {
			c.sendMessage("You cannot teleport out of jail.");
			return;
		}
		if (c.inPcGame()) {
			c.sendMessage("You cannot teleport out of this minigame.");
			return;
		}
		if (c.inPcBoat()) {
			c.sendMessage("You cannot teleport out the boat.");
			return;
		}
		/*
		 * if (c.inWild() && c.underAttackBy > 0 &&
		 * !c.getItems().playerHasItem(563, 2)) { c.sendMessage(
		 * "You need 2 law runes to teleport during a combat in the wilderness!"
		 * ); return; } if (c.inWild() && c.underAttackBy > 0 &&
		 * c.getItems().playerHasItem(563, 2)) { c.getItems().deleteItem(563,
		 * c.getItems().getItemSlot(563), 2); }
		 */
		/*
		 * if(c.isNewPlayer) { return; }
		 */
		if (c.duelStatus >= 1 && c.duelStatus <= 4) {
			Client o = (Client) PlayerHandler.players[c.duelingWith];
			c.duelStatus = 0;
			o.duelStatus = 0;
			// c.sendMessage("@red@The challange has been declined.");
			// o.sendMessage("@red@Other player has declined the challange.");
			// Misc.println("trade reset");
			o.getTradeAndDuel().declineDuel();
			c.getTradeAndDuel().declineDuel();
			// return;
		}
		if (c.inWild() && c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL) {
			c.sendMessage("You can't teleport above level "
					+ Config.NO_TELEPORT_WILD_LEVEL + " in the wilderness.");
			return;
		}
		if (System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			return;
		}
		if (!c.isDead && c.teleTimer == 0 && c.respawnTimer == -6) {
			if (c.playerIndex > 0 || c.npcIndex > 0)
				c.getCombat().resetPlayerAttack();
			c.stopMovement();
			removeAllWindows();
			c.teleX = x;
			c.teleY = y;
			c.npcIndex = 0;
			c.playerIndex = 0;
			c.faceUpdate(0);
			c.teleHeight = height;
			if (teleportType.equalsIgnoreCase("modern")) {
				c.startAnimation(714);
				c.teleTimer = 11;
				c.teleGfx = 308;
				c.teleEndAnimation = 715;
			}

			if (teleportType.equalsIgnoreCase("ancient")) {
				c.startAnimation(1979);
				c.teleGfx = 0;
				c.teleTimer = 9;
				c.teleEndAnimation = 0;
				c.gfx0(392);
			}

		}
	}

	public void teleTabTeleport(int x, int y, int height, String teleportType) {
		if (c.inPits) {
			c.sendMessage("You can't teleport during Fight Pits.");
			return;
		}
		/*
		 * if (!c.startPack) { c.sendMessage(
		 * "You must select your starter package before doing any action.");
		 * return; }
		 */
		if (c.getPA().inPitsWait()) {
			c.sendMessage("You can't teleport during Fight Pits.");
			return;
		}
		if (c.inTrade) {
			c.sendMessage("You can't teleport during a trade!");
			return;
		}
		if (c.duelStatus >= 1 && c.duelStatus <= 4) {
			Client o = (Client) PlayerHandler.players[c.duelingWith];
			c.duelStatus = 0;
			o.duelStatus = 0;
			// c.sendMessage("@red@The challange has been declined.");
			// o.sendMessage("@red@Other player has declined the challange.");
			// Misc.println("trade reset");
			o.getTradeAndDuel().declineDuel();
			c.getTradeAndDuel().declineDuel();
			// return;
		}
		if (c.duelStatus == 5) {
			c.sendMessage("You can't teleport during a duel!");
			return;
		}
		if (c.inWild() && c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL) {
			c.sendMessage("You can't teleport above level "
					+ Config.NO_TELEPORT_WILD_LEVEL + " in the wilderness.");
			return;
		}
		if (System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			return;
		}

		if (!c.isDead && c.teleTimer == 0 && c.respawnTimer == -6) {
			if (c.playerIndex > 0 || c.npcIndex > 0)
				c.getCombat().resetPlayerAttack();
			c.stopMovement();
			removeAllWindows();
			c.teleX = x;
			c.teleY = y;
			c.npcIndex = 0;
			c.playerIndex = 0;
			c.faceUpdate(0);
			c.teleHeight = height;
			if (teleportType.equalsIgnoreCase("teleTab")) {
				c.startAnimation(4731);
				c.teleEndAnimation = 0;
				c.teleTimer = 8;
				c.gfx0(678);
			}
		}
	}

	public void startTeleport2(int x, int y, int height) {
		if (c.duelStatus == 5) {
			c.sendMessage("You can't teleport during a duel!");
			return;
		}
		if (c.inPcGame()) {
			c.sendMessage("You cannot teleport out of this minigame.");
			return;
		}
		if (c.inPcBoat()) {
			c.sendMessage("You cannot teleport out the boat.");
			return;
		}
		/*
		 * if(c.isNewPlayer) { return; }
		 */
		/*
		 * if (!c.startPack) { c.sendMessage(
		 * "You must select your starter package before doing any action.");
		 * return; }
		 */
		if (c.duelStatus >= 1 && c.duelStatus <= 4) {
			Client o = (Client) PlayerHandler.players[c.duelingWith];
			c.duelStatus = 0;
			o.duelStatus = 0;
			// c.sendMessage("@red@The challange has been declined.");
			// o.sendMessage("@red@Other player has declined the challange.");
			// Misc.println("trade reset");
			o.getTradeAndDuel().declineDuel();
			c.getTradeAndDuel().declineDuel();
			// return;
		}
		if (System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			return;
		}
		if (!c.isDead && c.teleTimer == 0) {
			c.stopMovement();
			removeAllWindows();
			c.teleX = x;
			c.teleY = y;
			c.npcIndex = 0;
			c.playerIndex = 0;
			c.faceUpdate(0);
			c.teleHeight = height;
			c.startAnimation(714);
			c.teleTimer = 11;
			c.teleGfx = 308;
			c.teleEndAnimation = 715;

		}
	}

	public void processTeleport() {
		c.teleportToX = c.teleX;
		c.teleportToY = c.teleY;
		c.heightLevel = c.teleHeight;
		if (c.teleEndAnimation > 0) {
			c.startAnimation(c.teleEndAnimation);
		}
	}

	/**
	 * Following
	 **/

    public void followPlayer() {
        if (PlayerHandler.players[c.followId] == null
                        || PlayerHandler.players[c.followId].isDead) {
			c.followId = 0;
                return;
        }
        if (c.freezeTimer > 0) {
                return;
        }
        
        if(inPitsWait()) {
			c.followId = 0;
		}
        
        if (c.isDead || c.playerLevel[3] <= 0)
                return;

        int otherX = PlayerHandler.players[c.followId].getX();
        int otherY = PlayerHandler.players[c.followId].getY();

        boolean sameSpot = (c.absX == otherX && c.absY == otherY);

        boolean hallyDistance = c.goodDistance(otherX, otherY, c.getX(),
                        c.getY(), 2);
		boolean withinDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 2);
        boolean rangeWeaponDistance = c.goodDistance(otherX, otherY, c.getX(),
                        c.getY(), 4);
        boolean bowDistance = c.goodDistance(otherX, otherY, c.getX(),
                        c.getY(), 6);
        boolean mageDistance = c.goodDistance(otherX, otherY, c.getX(),
                        c.getY(), 7);

        boolean castingMagic = (c.usingMagic || c.mageFollow || c.autocasting || c.spellId > 0)
                        && mageDistance;
        boolean playerRanging = (c.usingRangeWeapon)
                        && rangeWeaponDistance;
        boolean playerBowOrCross = (c.usingBow) && bowDistance;

		if(!c.goodDistance(otherX, otherY, c.getX(), c.getY(), 25)) {
			c.followId = 0;
			return;
		}
		if(c.goodDistance(otherX, otherY, c.getX(), c.getY(), 1)) {
			if (otherX != c.getX() && otherY != c.getY()) {
				stopDiagonal(otherX, otherY);
				return;
			}
		}
		
		if((c.usingBow || c.mageFollow || (c.playerIndex > 0 && c.autocastId > 0)) && bowDistance && !sameSpot) {
			return;
		}

		if(c.getCombat().usingHally() && hallyDistance && !sameSpot) {
			return;
		}

		if(c.usingRangeWeapon && rangeWeaponDistance && !sameSpot) {
			return;
		}
		
		c.faceUpdate(c.followId+32768);
        if (otherX == c.absX && otherY == c.absY) {
                int r = Misc.random(3);
                switch (r) {
                case 0:
                        walkTo(0, -1);
                        break;
                case 1:
                        walkTo(0, 1);
                        break;
                case 2:
                        walkTo(1, 0);
                        break;
                case 3:
                        walkTo(-1, 0);
                        break;
                }
        } else if (c.isRunning2 && !withinDistance) {
                if (otherY > c.getY() && otherX == c.getX()) {
                        playerWalk(otherX, otherY - 1);
                } else if (otherY < c.getY() && otherX == c.getX()) {
                        playerWalk(otherX, otherY + 1);
                } else if (otherX > c.getX() && otherY == c.getY()) {
                        playerWalk(otherX - 1, otherY);
                } else if (otherX < c.getX() && otherY == c.getY()) {
                        playerWalk(otherX + 1, otherY);
                } else if (otherX < c.getX() && otherY < c.getY()) {
                        playerWalk(otherX + 1, otherY + 1);
                } else if (otherX > c.getX() && otherY > c.getY()) {
                        playerWalk(otherX - 1, otherY - 1);
                } else if (otherX < c.getX() && otherY > c.getY()) {
                        playerWalk(otherX + 1, otherY - 1);
                } else if (otherX > c.getX() && otherY < c.getY()) {
                        playerWalk(otherX + 1, otherY - 1);
                }
        } else {
                if (otherY > c.getY() && otherX == c.getX()) {
                        playerWalk(otherX, otherY - 1);
                } else if (otherY < c.getY() && otherX == c.getX()) {
                        playerWalk(otherX, otherY + 1);
                } else if (otherX > c.getX() && otherY == c.getY()) {
                        playerWalk(otherX - 1, otherY);
                } else if (otherX < c.getX() && otherY == c.getY()) {
                        playerWalk(otherX + 1, otherY);
                } else if (otherX < c.getX() && otherY < c.getY()) {
                        playerWalk(otherX + 1, otherY + 1);
                } else if (otherX > c.getX() && otherY > c.getY()) {
                        playerWalk(otherX - 1, otherY - 1);
                } else if (otherX < c.getX() && otherY > c.getY()) {
                        playerWalk(otherX + 1, otherY - 1);
                } else if (otherX > c.getX() && otherY < c.getY()) {
                        playerWalk(otherX - 1, otherY + 1);
                }
        }
        c.faceUpdate(c.followId+32768);
    }

	public void followNpc() {
		if(Server.npcHandler.npcs[c.followId] == null || Server.npcHandler.npcs[c.followId].isDead) {
			c.followId = 0;
			return;
		}		
		if(c.freezeTimer > 0) {
			return;
		}
		if (c.isDead || c.playerLevel[3] <= 0)
			return;
		
		int otherX = Server.npcHandler.npcs[c.followId2].getX();
		int otherY = Server.npcHandler.npcs[c.followId2].getY();
		boolean withinDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 2);
		boolean goodDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 1);
		boolean hallyDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 2);
		boolean bowDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 8);
		boolean rangeWeaponDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 4);
		boolean sameSpot = c.absX == otherX && c.absY == otherY;
		if(!c.goodDistance(otherX, otherY, c.getX(), c.getY(), 25)) {
			c.followId2 = 0;
			return;
		}
		if(c.goodDistance(otherX, otherY, c.getX(), c.getY(), 1)) {
			if (otherX != c.getX() && otherY != c.getY()) {
				stopDiagonal(otherX, otherY);
				return;
			}
		}
		
		if((c.usingBow || c.mageFollow || (c.npcIndex > 0 && c.autocastId > 0)) && bowDistance && !sameSpot) {
			return;
		}

		if(c.getCombat().usingHally() && hallyDistance && !sameSpot) {
			return;
		}

		if(c.usingRangeWeapon && rangeWeaponDistance && !sameSpot) {
			return;
		}
		
		c.faceUpdate(c.followId);
		if (otherX == c.absX && otherY == c.absY) {
			int r = Misc.random(3);
			switch (r) {
				case 0:
					walkTo(0,-1);
				break;
				case 1:
					walkTo(0,1);
				break;
				case 2:
					walkTo(1,0);
				break;
				case 3:
					walkTo(-1,0);
				break;			
			}		
		} else if(c.isRunning2 && !withinDistance) {
			/*if(otherY > c.getY() && otherX == c.getX()) {
				walkTo(0, getMove(c.getY(), otherY - 1) + getMove(c.getY(), otherY - 1));
			} else if(otherY < c.getY() && otherX == c.getX()) {
				walkTo(0, getMove(c.getY(), otherY + 1) + getMove(c.getY(), otherY + 1));
			} else if(otherX > c.getX() && otherY == c.getY()) {
				walkTo(getMove(c.getX(), otherX - 1) + getMove(c.getX(), otherX - 1), 0);
			} else if(otherX < c.getX() && otherY == c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(), otherX + 1), 0);
			} else if(otherX < c.getX() && otherY < c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(), otherX + 1), getMove(c.getY(), otherY + 1) + getMove(c.getY(), otherY + 1));
			} else if(otherX > c.getX() && otherY > c.getY()) {
				walkTo(getMove(c.getX(), otherX - 1) + getMove(c.getX(), otherX - 1), getMove(c.getY(), otherY - 1) + getMove(c.getY(), otherY - 1));
			} else if(otherX < c.getX() && otherY > c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(), otherX + 1), getMove(c.getY(), otherY - 1) + getMove(c.getY(), otherY - 1));
			} else if(otherX > c.getX() && otherY < c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(), otherX + 1), getMove(c.getY(), otherY - 1) + getMove(c.getY(), otherY - 1));
			} 
		} else {
			if(otherY > c.getY() && otherX == c.getX()) {
				walkTo(0, getMove(c.getY(), otherY - 1));
			} else if(otherY < c.getY() && otherX == c.getX()) {
				walkTo(0, getMove(c.getY(), otherY + 1));
			} else if(otherX > c.getX() && otherY == c.getY()) {
				walkTo(getMove(c.getX(), otherX - 1), 0);
			} else if(otherX < c.getX() && otherY == c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1), 0);
			} else if(otherX < c.getX() && otherY < c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1), getMove(c.getY(), otherY + 1));
			} else if(otherX > c.getX() && otherY > c.getY()) {
				walkTo(getMove(c.getX(), otherX - 1), getMove(c.getY(), otherY - 1));
			} else if(otherX < c.getX() && otherY > c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1), getMove(c.getY(), otherY - 1));
			} else if(otherX > c.getX() && otherY < c.getY()) {
				walkTo(getMove(c.getX(), otherX - 1), getMove(c.getY(), otherY + 1));
			}*/
			if(otherY > c.getY() && otherX == c.getX()) {
				//walkTo(0, getMove(c.getY(), otherY - 1) + getMove(c.getY(), otherY - 1));
				playerWalk(otherX, otherY - 1);
			} else if(otherY < c.getY() && otherX == c.getX()) {
				//walkTo(0, getMove(c.getY(), otherY + 1) + getMove(c.getY(), otherY + 1));
				playerWalk(otherX, otherY + 1);
			} else if(otherX > c.getX() && otherY == c.getY()) {
				//walkTo(getMove(c.getX(), otherX - 1) + getMove(c.getX(), otherX - 1), 0);
				playerWalk(otherX - 1, otherY);
			} else if(otherX < c.getX() && otherY == c.getY()) {
				//walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(), otherX + 1), 0);
				playerWalk(otherX + 1, otherY);
			} else if(otherX < c.getX() && otherY < c.getY()) {
				//walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(), otherX + 1), getMove(c.getY(), otherY + 1) + getMove(c.getY(), otherY + 1));
				playerWalk(otherX + 1, otherY + 1);
			} else if(otherX > c.getX() && otherY > c.getY()) {
				//walkTo(getMove(c.getX(), otherX - 1) + getMove(c.getX(), otherX - 1), getMove(c.getY(), otherY - 1) + getMove(c.getY(), otherY - 1));
				playerWalk(otherX - 1, otherY - 1);
			} else if(otherX < c.getX() && otherY > c.getY()) {
				//walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(), otherX + 1), getMove(c.getY(), otherY - 1) + getMove(c.getY(), otherY - 1));
				playerWalk(otherX + 1, otherY - 1);
			} else if(otherX > c.getX() && otherY < c.getY()) {
				//walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(), otherX + 1), getMove(c.getY(), otherY - 1) + getMove(c.getY(), otherY - 1));
				playerWalk(otherX + 1, otherY - 1);
			}
		} else {
			if(otherY > c.getY() && otherX == c.getX()) {
				//walkTo(0, getMove(c.getY(), otherY - 1));
				playerWalk(otherX, otherY - 1);
			} else if(otherY < c.getY() && otherX == c.getX()) {
				//walkTo(0, getMove(c.getY(), otherY + 1));
				playerWalk(otherX, otherY + 1);
			} else if(otherX > c.getX() && otherY == c.getY()) {
				//walkTo(getMove(c.getX(), otherX - 1), 0);
				playerWalk(otherX - 1, otherY);
			} else if(otherX < c.getX() && otherY == c.getY()) {
				//walkTo(getMove(c.getX(), otherX + 1), 0);
				playerWalk(otherX + 1, otherY);
			} else if(otherX < c.getX() && otherY < c.getY()) {
				//walkTo(getMove(c.getX(), otherX + 1), getMove(c.getY(), otherY + 1));
				playerWalk(otherX + 1, otherY + 1);
			} else if(otherX > c.getX() && otherY > c.getY()) {
				//walkTo(getMove(c.getX(), otherX - 1), getMove(c.getY(), otherY - 1));
				playerWalk(otherX - 1, otherY - 1);
			} else if(otherX < c.getX() && otherY > c.getY()) {
				//walkTo(getMove(c.getX(), otherX + 1), getMove(c.getY(), otherY - 1));
				playerWalk(otherX + 1, otherY - 1);
			} else if(otherX > c.getX() && otherY < c.getY()) {
				//walkTo(getMove(c.getX(), otherX - 1), getMove(c.getY(), otherY + 1));
				playerWalk(otherX - 1, otherY + 1);
			}
		}
		c.faceUpdate(c.followId);
	}

	public int getRunningMove(int i, int j) {
		if (j - i > 2)
			return 2;
		else if (j - i < -2)
			return -2;
		else
			return j - i;
	}

	public void sendStatement(String s) {
		sendFrame126(s, 357);
		sendFrame126("Click here to continue", 358);
		sendFrame164(356);
	}

	public void resetFollow() {
		c.followId = 0;
		c.followId2 = 0;
		c.mageFollow = false;
		c.outStream.createFrame(174);
		c.outStream.writeWord(0);
		c.outStream.writeByte(0);
		c.outStream.writeWord(1);
	}

	public void walkTo(int i, int j) {
		c.newWalkCmdSteps = 0;
		if (++c.newWalkCmdSteps > 50)
			c.newWalkCmdSteps = 0;
		int k = c.getX() + i;
		k -= c.mapRegionX * 8;
		c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
		int l = c.getY() + j;
		l -= c.mapRegionY * 8;

		for (int n = 0; n < c.newWalkCmdSteps; n++) {
			c.getNewWalkCmdX()[n] += k;
			c.getNewWalkCmdY()[n] += l;
		}
	}

	public void walkTo2(int i, int j) {
		if (c.freezeDelay > 0)
			return;
		c.newWalkCmdSteps = 0;
		if (++c.newWalkCmdSteps > 50)
			c.newWalkCmdSteps = 0;
		int k = c.getX() + i;
		k -= c.mapRegionX * 8;
		c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
		int l = c.getY() + j;
		l -= c.mapRegionY * 8;

		for (int n = 0; n < c.newWalkCmdSteps; n++) {
			c.getNewWalkCmdX()[n] += k;
			c.getNewWalkCmdY()[n] += l;
		}
	}

	public void stopDiagonal(int otherX, int otherY) {
		if (c.freezeDelay > 0)
			return;
		if (c.freezeTimer > 0) // player can't move
			return;
		c.newWalkCmdSteps = 1;
		int xMove = otherX - c.getX();
		int yMove = 0;
		if (xMove == 0)
			yMove = otherY - c.getY();
		/*
		 * if (!clipHor) { yMove = 0; } else if (!clipVer) { xMove = 0; }
		 */

		int k = c.getX() + xMove;
		k -= c.mapRegionX * 8;
		c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
		int l = c.getY() + yMove;
		l -= c.mapRegionY * 8;

		for (int n = 0; n < c.newWalkCmdSteps; n++) {
			c.getNewWalkCmdX()[n] += k;
			c.getNewWalkCmdY()[n] += l;
		}

	}

	public void walkToCheck(int i, int j) {
		if (c.freezeDelay > 0)
			return;
		c.newWalkCmdSteps = 0;
		if (++c.newWalkCmdSteps > 50)
			c.newWalkCmdSteps = 0;
		int k = c.getX() + i;
		k -= c.mapRegionX * 8;
		c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
		int l = c.getY() + j;
		l -= c.mapRegionY * 8;

		for (int n = 0; n < c.newWalkCmdSteps; n++) {
			c.getNewWalkCmdX()[n] += k;
			c.getNewWalkCmdY()[n] += l;
		}
	}

	public int getMove(int place1, int place2) {
		if (System.currentTimeMillis() - c.lastSpear < 4000)
			return 0;
		if ((place1 - place2) == 0) {
			return 0;
		} else if ((place1 - place2) < 0) {
			return 1;
		} else if ((place1 - place2) > 0) {
			return -1;
		}
		return 0;
	}

	public boolean fullVeracs() {
		return c.playerEquipment[c.playerHat] == 4753
				&& c.playerEquipment[c.playerChest] == 4757
				&& c.playerEquipment[c.playerLegs] == 4759
				&& c.playerEquipment[c.playerWeapon] == 4755;
	}

	public boolean fullGuthans() {
		return c.playerEquipment[c.playerHat] == 4724
				&& c.playerEquipment[c.playerChest] == 4728
				&& c.playerEquipment[c.playerLegs] == 4730
				&& c.playerEquipment[c.playerWeapon] == 4726;
	}

	/**
	 * reseting animation
	 **/
	public void resetAnimation() {
		c.getCombat().getPlayerAnimIndex(
				c.getItems().getItemName(c.playerEquipment[c.playerWeapon])
						.toLowerCase());
		c.startAnimation(c.playerStandIndex);
		requestUpdates();
	}

	public void requestUpdates() {
		c.updateRequired = true;
		c.setAppearanceUpdateRequired(true);
	}

	public void Obelisks(int id) {
		if (!c.getItems().playerHasItem(id)) {
			c.getItems().addItem(id, 1);
		}
	}

	public void resetAgility() {
		c.doneLog = false;
		c.doneNet1 = false;
		c.doneBranch1 = false;
		c.doneRope = false;
		c.doneBranch2 = false;
		c.doneNet2 = false;
		c.doneTunnel = false;
	}

	public void totallevelsupdate() {
		int totalLevel = (getLevelForXP(c.playerXP[0])
				+ getLevelForXP(c.playerXP[1]) + getLevelForXP(c.playerXP[2])
				+ getLevelForXP(c.playerXP[3]) + getLevelForXP(c.playerXP[4])
				+ getLevelForXP(c.playerXP[5]) + getLevelForXP(c.playerXP[6])
				+ getLevelForXP(c.playerXP[7]) + getLevelForXP(c.playerXP[8])
				+ getLevelForXP(c.playerXP[9]) + getLevelForXP(c.playerXP[10])
				+ getLevelForXP(c.playerXP[11]) + getLevelForXP(c.playerXP[12])
				+ getLevelForXP(c.playerXP[13]) + getLevelForXP(c.playerXP[14])
				+ getLevelForXP(c.playerXP[15]) + getLevelForXP(c.playerXP[16])
				+ getLevelForXP(c.playerXP[17]) + getLevelForXP(c.playerXP[18])
				+ getLevelForXP(c.playerXP[19]) + getLevelForXP(c.playerXP[20]));
		long totalExp = (c.playerXP[0] + c.playerXP[1] + c.playerXP[2]
				+ c.playerXP[3] + c.playerXP[4] + c.playerXP[5] + c.playerXP[6]
				+ c.playerXP[7] + c.playerXP[8] + c.playerXP[9]
				+ c.playerXP[10] + c.playerXP[11] + c.playerXP[12]
				+ c.playerXP[13] + c.playerXP[14] + c.playerXP[15]
				+ c.playerXP[16] + c.playerXP[17] + c.playerXP[18]
				+ c.playerXP[19] + c.playerXP[20]);
		// c.getPA().sendFrame126("Total Level:", 31199);
		// c.getPA().sendFrame126(""+totalLevel+"", 31200);
		// c.getPA().sendFrame126("Total Exp: "+totalExp+"", 31198);
		sendFrame126("Total Lvl: " + totalLevel, 3984);
	}

	public void levelUp(int skill) {
		int totalLevel = (getLevelForXP(c.playerXP[0])
				+ getLevelForXP(c.playerXP[1]) + getLevelForXP(c.playerXP[2])
				+ getLevelForXP(c.playerXP[3]) + getLevelForXP(c.playerXP[4])
				+ getLevelForXP(c.playerXP[5]) + getLevelForXP(c.playerXP[6])
				+ getLevelForXP(c.playerXP[7]) + getLevelForXP(c.playerXP[8])
				+ getLevelForXP(c.playerXP[9]) + getLevelForXP(c.playerXP[10])
				+ getLevelForXP(c.playerXP[11]) + getLevelForXP(c.playerXP[12])
				+ getLevelForXP(c.playerXP[13]) + getLevelForXP(c.playerXP[14])
				+ getLevelForXP(c.playerXP[15]) + getLevelForXP(c.playerXP[16])
				+ getLevelForXP(c.playerXP[17]) + getLevelForXP(c.playerXP[18])
				+ getLevelForXP(c.playerXP[19]) + getLevelForXP(c.playerXP[20]) + getLevelForXP(c.playerXP[21]));
		long totalExp = (c.playerXP[0] + c.playerXP[1] + c.playerXP[2]
				+ c.playerXP[3] + c.playerXP[4] + c.playerXP[5] + c.playerXP[6]
				+ c.playerXP[7] + c.playerXP[8] + c.playerXP[9]
				+ c.playerXP[10] + c.playerXP[11] + c.playerXP[12]
				+ c.playerXP[13] + c.playerXP[14] + c.playerXP[15]
				+ c.playerXP[16] + c.playerXP[17] + c.playerXP[18]
				+ c.playerXP[19] + c.playerXP[20] + c.playerXP[21]
				+ c.playerXP[22] + c.playerXP[23] + c.playerXP[24]);

		// c.getPA().sendFrame126("Total Level:", 31199);
		// c.getPA().sendFrame126(""+totalLevel+"", 31200);
		// c.getPA().sendFrame126("Total Exp: "+totalExp+"", 31198);
		sendFrame126("Total Lvl: " + totalLevel, 3984);
		switch (skill) {

		case 0:
			sendFrame126("Congratulations, you just advanced an attack level!",
					6248);
			sendFrame126("Your attack level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6249);
			c.sendMessage("Congratulations, you just advanced an attack level.");
			sendFrame164(6247);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@NEWS@bla@]@red@ @bla@The player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 @blu@Attack@bla@.");
					}
				}
			}
			break;

		case 1:
			sendFrame126("Congratulations, you just advanced a defence level!",
					6254);
			sendFrame126("Your defence level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6255);
			c.sendMessage("Congratulations, you just advanced a defence level.");
			sendFrame164(6253);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@NEWS@bla@]@red@ @bla@The player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 @blu@Defence@bla@.");
					}
				}
			}
			break;

		case 2:
			sendFrame126(
					"Congratulations, you just advanced a strength level!",
					6207);
			sendFrame126("Your strength level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6208);
			c.sendMessage("Congratulations, you just advanced a strength level.");
			sendFrame164(6206);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@NEWS@bla@]@red@ @bla@The player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 @blu@Strength@bla@.");
					}
				}
			}
			break;

		case 3:
			sendFrame126(
					"Congratulations, you just advanced a hitpoints level!",
					6217);
			sendFrame126("Your hitpoints level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6218);
			c.sendMessage("Congratulations, you just advanced a hitpoints level.");
			sendFrame164(6216);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@NEWS@bla@]@red@ @bla@The player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 @blu@Hitpoints@bla@.");
					}
				}
			}
			break;

		case 4:
			c.sendMessage("Congratulations, you just advanced a ranging level.");
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@NEWS@bla@]@red@ @bla@The player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 @blu@Ranged@bla@.");
					}
				}
			}
			break;

		case 5:
			sendFrame126("Congratulations, you just advanced a prayer level!",
					6243);
			sendFrame126("Your prayer level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6244);
			c.sendMessage("Congratulations, you just advanced a prayer level.");
			sendFrame164(6242);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@NEWS@bla@]@red@ @bla@The player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 @blu@Prayer@bla@.");
					}
				}
			}
			break;

		case 6:
			sendFrame126("Congratulations, you just advanced a magic level!",
					6212);
			sendFrame126("Your magic level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6213);
			c.sendMessage("Congratulations, you just advanced a magic level.");
			sendFrame164(6211);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@NEWS@bla@]@red@ @bla@The player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 @blu@Magic@bla@.");
					}
				}
			}
			break;

		case 7:
			sendFrame126("Congratulations, you just advanced a cooking level!",
					6227);
			sendFrame126("Your cooking level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6228);
			c.sendMessage("Congratulations, you just advanced a cooking level.");
			sendFrame164(6226);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@NEWS@bla@]@red@ @bla@The player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 @blu@Cooking@bla@.");
					}
				}
			}
			break;

		case 8:
			sendFrame126(
					"Congratulations, you just advanced a woodcutting level!",
					4273);
			sendFrame126("Your woodcutting level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 4274);
			c.sendMessage("Congratulations, you just advanced a woodcutting level.");
			sendFrame164(4272);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@NEWS@bla@]@red@ @bla@The player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 @blu@Woodcutting@bla@.");
					}
				}
			}
			break;

		case 9:
			sendFrame126(
					"Congratulations, you just advanced a fletching level!",
					6232);
			sendFrame126("Your fletching level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6233);
			c.sendMessage("Congratulations, you just advanced a fletching level.");
			sendFrame164(6231);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@NEWS@bla@]@red@ @bla@The player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 @blu@Fletching@bla@.");
					}
				}
			}
			break;

		case 10:
			sendFrame126("Congratulations, you just advanced a fishing level!",
					6259);
			sendFrame126("Your fishing level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6260);
			c.sendMessage("Congratulations, you just advanced a fishing level.");
			sendFrame164(6258);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@NEWS@bla@]@red@ @bla@The player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 @blu@Fishing@bla@.");
					}
				}
			}
			break;

		case 11:
			sendFrame126(
					"Congratulations, you just advanced a fire making level!",
					4283);
			sendFrame126("Your firemaking level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 4284);
			c.sendMessage("Congratulations, you just advanced a fire making level.");
			sendFrame164(4282);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@NEWS@bla@]@red@ @bla@The player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 @blu@Firemaking@bla@.");
					}
				}
			}
			break;

		case 12:
			sendFrame126(
					"Congratulations, you just advanced a crafting level!",
					6264);
			sendFrame126("Your crafting level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6265);
			c.sendMessage("Congratulations, you just advanced a crafting level.");
			sendFrame164(6263);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@NEWS@bla@]@red@ @bla@The player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 @blu@Crafting@bla@.");
					}
				}
			}
			break;

		case 13:
			sendFrame126(
					"Congratulations, you just advanced a smithing level!",
					6222);
			sendFrame126("Your smithing level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6223);
			c.sendMessage("Congratulations, you just advanced a smithing level.");
			sendFrame164(6221);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@NEWS@bla@]@red@ @bla@The player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 @blu@Smithing@bla@.");
					}
				}
			}
			break;

		case 14:
			sendFrame126("Congratulations, you just advanced a mining level!",
					4417);
			sendFrame126("Your mining level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 4438);
			c.sendMessage("Congratulations, you just advanced a mining level.");
			sendFrame164(4416);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@NEWS@bla@]@red@ @bla@The player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 @blu@Mining@bla@.");
					}
				}
			}
			break;

		case 15:
			sendFrame126(
					"Congratulations, you just advanced a herblore level!",
					6238);
			sendFrame126("Your herblore level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6239);
			c.sendMessage("Congratulations, you just advanced a herblore level.");
			sendFrame164(6237);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@NEWS@bla@]@red@ @bla@The player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 @blu@Herblore@bla@.");
					}
				}
			}
			break;

		case 16:
			sendFrame126("Congratulations, you just advanced a agility level!",
					4278);
			sendFrame126("Your agility level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 4279);
			c.sendMessage("Congratulations, you just advanced an agility level.");
			sendFrame164(4277);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@NEWS@bla@]@red@ @bla@The player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 @blu@Agility@bla@.");
					}
				}
			}
			break;

		case 17:
			sendFrame126(
					"Congratulations, you just advanced a thieving level!",
					4263);
			sendFrame126("Your theiving level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 4264);
			c.sendMessage("Congratulations, you just advanced a thieving level.");
			sendFrame164(4261);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@NEWS@bla@]@red@ @bla@The player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 @blu@Thieving@bla@.");
					}
				}
			}
			break;

		case 18:
			sendFrame126("Congratulations, you just advanced a slayer level!",
					12123);
			sendFrame126("Your slayer level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 12124);
			c.sendMessage("Congratulations, you just advanced a slayer level.");
			sendFrame164(12122);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@NEWS@bla@]@red@ @bla@The player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 @blu@Slayer@bla@.");
					}
				}
			}
			break;

		case 19:
			sendFrame126("Congratulations, you just advanced a farming level!",
					4268);
			sendFrame126("Your farming level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 4269);
			c.sendMessage("Congratulations, you just advanced a farming level.");
			sendFrame164(4267);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@NEWS@bla@]@red@ @bla@The player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 @blu@Farming@bla@.");
					}
				}
			}
			break;

		case 20:
			sendFrame126(
					"Congratulations, you just advanced a runecrafting level!",
					4268);
			sendFrame126("Your runecrafting level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 4269);
			c.sendMessage("Congratulations, you just advanced a runecrafting level.");
			sendFrame164(4267);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@NEWS@bla@]@red@ @bla@The player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 @blu@Runecrafting@bla@.");
					}
				}
			}
			break;
		case 21:
			sendFrame126("Congratulations, you just advanced a Hunter level!",
					4268);
			sendFrame126("Your Hunter level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 4269);
			c.sendMessage("Congratulations, you just advanced a Hunter level.");
			sendFrame164(4267);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@NEWS@bla@]@red@ @bla@The player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 @blu@Hunter@bla@.");
					}
				}
			}
			break;

		case 22:
			sendFrame126(
					"Congratulations, you just advanced a Summoning level!",
					4268);
			sendFrame126("Your Summoning level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 4269);
			c.sendMessage("Congratulations, you just advanced a Summoning level.");
			sendFrame164(4267);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@NEWS@bla@]@red@ @bla@The player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 @blu@Summoning@bla@.");
					}
				}
			}
			break;
		}
		c.dialogueAction = 0;
		c.nextChat = 0;
	}

	public void refreshSkill(int i) {
		c.calcCombat();
		c.getPA().totallevelsupdate();
		switch (i) {
		case 0:// attack
			sendFrame126("" + c.playerLevel[0] + "", 31114);
			sendFrame126("" + getLevelForXP(c.playerXP[0]) + "", 31115);
			sendFrame126("Exp: " + c.playerXP[0] + "", 31113);
			break;

		case 1:// defence
			sendFrame126("" + c.playerLevel[1] + "", 31124);
			sendFrame126("" + getLevelForXP(c.playerXP[1]) + "", 31125);
			sendFrame126("Exp: " + c.playerXP[1] + "", 31123);
			break;

		case 2:// strength
			sendFrame126("" + c.playerLevel[2] + "", 31119);
			sendFrame126("" + getLevelForXP(c.playerXP[2]) + "", 31120);
			sendFrame126("Exp: " + c.playerXP[2] + "", 31118);
			break;

		case 3:// hitpoints
			sendFrame126("" + c.playerLevel[3] + "", 31159);
			sendFrame126("" + getLevelForXP(c.playerXP[3]) + "", 31160);
			sendFrame126("Exp: " + c.playerXP[3] + "", 31158);
			break;

		case 4:// ranged
			sendFrame126("" + c.playerLevel[4] + "", 31129);
			sendFrame126("" + getLevelForXP(c.playerXP[4]) + "", 31130);
			sendFrame126("Exp: " + c.playerXP[4] + "", 31128);
			break;

		case 5:// prayer
			sendFrame126("" + c.playerLevel[5] + "", 31134);
			sendFrame126("" + getLevelForXP(c.playerXP[5]) + "", 31135);
			sendFrame126("Exp: " + c.playerXP[5] + "", 31133);
			// prayer interface
			sendFrame126("" + c.playerLevel[5] + "/"
					+ getLevelForXP(c.playerXP[5]) + "", 687);// Prayer frame
			break;

		case 6:// magic
			sendFrame126("" + c.playerLevel[6] + "", 31139);
			sendFrame126("" + getLevelForXP(c.playerXP[6]) + "", 31140);
			sendFrame126("Exp: " + c.playerXP[6] + "", 31138);
			break;

		case 7:// cooking
			sendFrame126("" + c.playerLevel[7] + "", 31219);
			sendFrame126("" + getLevelForXP(c.playerXP[7]) + "", 31220);
			sendFrame126("Exp:" + c.playerXP[7] + "", 31218);
			break;

		case 8:// woodcutting
			sendFrame126("" + c.playerLevel[8] + "", 31229);
			sendFrame126("" + getLevelForXP(c.playerXP[8]) + "", 31230);
			sendFrame126("Exp: " + c.playerXP[8] + "", 31228);
			break;

		case 9:// fletching
			sendFrame126("" + c.playerLevel[9] + "", 31184);
			sendFrame126("" + getLevelForXP(c.playerXP[9]) + "", 31185);
			sendFrame126("Exp: " + c.playerXP[9] + "", 31183);
			break;

		case 10:// fishing
			sendFrame126("" + c.playerLevel[10] + "", 31214);
			sendFrame126("" + getLevelForXP(c.playerXP[10]) + "", 31215);
			sendFrame126("Exp: " + c.playerXP[10] + "", 31213);
			break;

		case 11:// firemaking
			sendFrame126("" + c.playerLevel[11] + "", 31224);
			sendFrame126("" + getLevelForXP(c.playerXP[11]) + "", 31225);
			sendFrame126("Exp: " + c.playerXP[11] + "", 31223);
			break;

		case 12:// crafting
			sendFrame126("" + c.playerLevel[12] + "", 31179);
			sendFrame126("" + getLevelForXP(c.playerXP[12]) + "", 31180);
			sendFrame126("Exp: " + c.playerXP[12] + "", 31178);
			break;

		case 13:// smithing
			sendFrame126("" + c.playerLevel[13] + "", 31209);
			sendFrame126("" + getLevelForXP(c.playerXP[13]) + "", 31210);
			sendFrame126("Exp: " + c.playerXP[13] + "", 31208);
			break;

		case 14:// mining
			sendFrame126("" + c.playerLevel[14] + "", 31204);
			sendFrame126("" + getLevelForXP(c.playerXP[14]) + "", 31205);
			sendFrame126("Exp: " + c.playerXP[14] + "", 31203);
			break;

		case 15:// herblore
			sendFrame126("" + c.playerLevel[15] + "", 31169);
			sendFrame126("" + getLevelForXP(c.playerXP[15]) + "", 31170);
			sendFrame126("Exp: " + c.playerXP[15] + "", 31168);
			break;

		case 16:// agility
			sendFrame126("" + c.playerLevel[16] + "", 31164);
			sendFrame126("" + getLevelForXP(c.playerXP[16]) + "", 31165);
			sendFrame126("Exp: " + c.playerXP[16] + "", 31163);
			break;

		case 17:// thieving
			sendFrame126("" + c.playerLevel[17] + "", 31174);
			sendFrame126("" + getLevelForXP(c.playerXP[17]) + "", 31175);
			sendFrame126("Exp: " + c.playerXP[17] + "", 31173);
			break;

		case 18:// slayer
			sendFrame126("" + c.playerLevel[18] + "", 31189);
			sendFrame126("" + getLevelForXP(c.playerXP[18]) + "", 31190);
			sendFrame126("Exp: " + c.playerXP[18] + "", 31188);
			break;

		case 19:// farming
			sendFrame126("" + c.playerLevel[19] + "", 31234);
			sendFrame126("" + getLevelForXP(c.playerXP[19]) + "", 31235);
			sendFrame126("Exp: " + c.playerXP[19] + "", 31233);
			break;

		case 20:// runecrfating
			sendFrame126("" + c.playerLevel[20] + "", 31144);
			sendFrame126("" + getLevelForXP(c.playerXP[20]) + "", 31145);
			sendFrame126("Exp: " + c.playerXP[20] + "", 31143);
			break;

		case 24:// dungeoneering
			sendFrame126("", 31154);
			sendFrame126("", 31155);
			// sendFrame126("", 31153);
			break;

		case 21:// hunter
			sendFrame126("" + c.playerLevel[21] + "", 31194);
			sendFrame126("" + getLevelForXP(c.playerXP[21]) + "", 31195);
			sendFrame126("Exp: " + c.playerXP[21] + "", 31193);
			break;

		case 23:// summoning
			sendFrame126("", 31239);
			sendFrame126("", 31240);
			// sendFrame126("", 31238);
			break;

		case 22:// construction
			sendFrame126("" + c.playerLevel[22] + "", 31149);
			sendFrame126("" + getLevelForXP(c.playerXP[22]) + "", 31150);
			sendFrame126("Exp: " + c.playerXP[22] + "", 31148);
			break;

		}
	}

	public int getXPForLevel(int level) {
		int points = 0;
		int output = 0;

		for (int lvl = 1; lvl <= level; lvl++) {
			points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
			if (lvl >= level)
				return output;
			output = (int) Math.floor(points / 4);
		}
		return 0;
	}

	public int getLevelForXP(int exp) {
		int points = 0;
		int output = 0;
		if (exp > 13034430)
			return 99;
		for (int lvl = 1; lvl <= 99; lvl++) {
			points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
			output = (int) Math.floor(points / 4);
			if (output >= exp) {
				return lvl;
			}
		}
		return 0;
	}

	public boolean addSkillXP(int amount, int skill) {
		if (amount + c.playerXP[skill] < 0 || c.playerXP[skill] > 500000000) {
			if (c.playerXP[skill] > 500000000) {
				c.playerXP[skill] = 500000000;
			}
			return false;
		}
		if (skill == 11) {
			amount *= 20;
		}
		if (skill == 12) {
			amount *= 20;
		}
		int oldLevel = getLevelForXP(c.playerXP[skill]);
		c.playerXP[skill] += amount;
		if(c.playerEquipment[3] == 6570) {
			c.playerXP[skill] += amount;
		}
		amount *= Config.SERVER_EXP_BONUS;	
		if (oldLevel < getLevelForXP(c.playerXP[skill])) {
			if (c.playerLevel[skill] < c.getLevelForXP(c.playerXP[skill]) && skill != 3 && skill != 5)
				c.playerLevel[skill] = c.getLevelForXP(c.playerXP[skill]);
			levelUp(skill);
			c.gfx100(199);
			requestUpdates();
		}
		setSkillLevel(skill, c.playerLevel[skill], c.playerXP[skill]);
		refreshSkill(skill);
		return true;
	}

	public boolean addSkillXP(Client c, int amount, int skill) {
		if (amount + c.playerXP[skill] < 0 || c.playerXP[skill] > 200000000) {
			if (c.playerXP[skill] > 200000000) {
				c.playerXP[skill] = 200000000;
			}
			return false;
		}
		amount *= Config.SERVER_EXP_BONUS;
		int oldLevel = getLevelForXP(c.playerXP[skill]);
		c.playerXP[skill] += amount;
		if (oldLevel < getLevelForXP(c.playerXP[skill])) {
			if (c.playerLevel[skill] < c.getLevelForXP(c.playerXP[skill])
					&& skill != 3 && skill != 5)
				c.playerLevel[skill] = c.getLevelForXP(c.playerXP[skill]);
			levelUp(skill);
			c.gfx100(199);
			requestUpdates();
		}
		setSkillLevel(skill, c.playerLevel[skill], c.playerXP[skill]);
		refreshSkill(skill);
		return true;
	}

	public static int barrowsRewards[] = { 4708, 4710, 4712, 4714, 4716, 4718,
			4720, 4722, 4724, 4726, 4728, 4730, 4732, 4734, 4736, 4738, 4745,
			4747, 4749, 4751, 4753, 4755, 4757, 4759 };
	public static int BabyLoot[] = { 1734, 2355, 1607, 946, 2347, 1438, 1759 };
	public static int YoungLoot[] = { 855, 1353, 1097, 1157, 2359, 1436, 453,
			1523, 1761, 361 };

	public int randomBarrows() {
		return barrowsRewards[(int) (Math.random() * barrowsRewards.length)];
	}

	public int randomBabyLoot() {
		return BabyLoot[(int) (Math.random() * BabyLoot.length)];
	}

	public int randomYoungLoot() {
		return YoungLoot[(int) (Math.random() * YoungLoot.length)];
	}

	public int randomRunes() {
		return Runes[(int) (Math.random() * Runes.length)];
	}

	public int randomPots() {
		return Pots[(int) (Math.random() * Pots.length)];
	}

	/**
	 * Show an arrow icon on the selected player.
	 * 
	 * @Param i - Either 0 or 1; 1 is arrow, 0 is none.
	 * @Param j - The player/Npc that the arrow will be displayed above.
	 * @Param k - Keep this set as 0
	 * @Param l - Keep this set as 0
	 */
	public void drawHeadicon(int i, int j, int k, int l) {
		// synchronized(c) {
		c.outStream.createFrame(254);
		c.outStream.writeByte(i);

		if (i == 1 || i == 10) {
			c.outStream.writeWord(j);
			c.outStream.writeWord(k);
			c.outStream.writeByte(l);
		} else {
			c.outStream.writeWord(k);
			c.outStream.writeWord(l);
			c.outStream.writeByte(j);
		}

	}

	public int getNpcId(int id) {
		for (int i = 0; i < NPCHandler.maxNPCs; i++) {
			if (NPCHandler.npcs[i] != null) {
				if (NPCHandler.npcs[i].npcId == id) {
					return i;
				}
			}
		}
		return -1;
	}

	public void removeObject(int x, int y) {
		object(-1, x, x, 10, 10);
	}

	private void objectToRemove(int X, int Y) {
		object(-1, X, Y, 10, 10);
	}

	private void objectToRemove2(int X, int Y) {
		object(-1, X, Y, -1, 0);
	}

	public void removeObjects() {
		objectToRemove(2638, 4688);
		objectToRemove2(2635, 4693);
		objectToRemove2(2634, 4693);
	}

	public void handleGlory(int gloryId) {
		c.getDH().sendOption4("Edgeville", "Al Kharid", "Karamja", "Mage Bank");
		c.sendMessage("You rub the amulet...");
		c.usingGlory = true;
	}

	public void resetVariables() {
		// c.getCrafting().resetCrafting();
		c.usingGlory = false;
	}

	public boolean inPitsWait() {
		return c.getX() <= 2404 && c.getX() >= 2394 && c.getY() <= 5175
				&& c.getY() >= 5169;
	}

	public void castleWarsObjects() {
		object(-1, 2373, 3119, -3, 10);
		object(-1, 2372, 3119, -3, 10);
	}

	public int antiFire() {
		int anti = 0;
		if (c.antiFirePot)
			anti = 2;
		if (c.playerEquipment[c.playerShield] == 1540 || c.prayerActive[12]
				|| c.playerEquipment[c.playerShield] == 11283)
			anti = 2;
		return anti;
	}

	public boolean checkForFlags() {
		int[][] itemsToCheck = { { 995, 100000000 }, { 35, 5 }, { 667, 5 },
				{ 2402, 5 }, { 746, 5 }, { 4151, 150 }, { 565, 100000 },
				{ 560, 100000 }, { 555, 300000 }, { 11235, 10 } };
		for (int j = 0; j < itemsToCheck.length; j++) {
			if (itemsToCheck[j][1] < c.getItems().getTotalCount(
					itemsToCheck[j][0]))
				return true;
		}
		return false;
	}

	/*
	 * Vengeance
	 */
	public void castVeng() {
		if (c.playerLevel[6] < 94) {
			c.sendMessage("You need a magic level of 94 to cast this spell.");
			return;
		}
		if (c.playerLevel[1] < 40) {
			c.sendMessage("You need a defence level of 40 to cast this spell.");
			return;
		}
		if (!c.getItems().playerHasItem(9075, 4)
				|| !c.getItems().playerHasItem(557, 10)
				|| !c.getItems().playerHasItem(560, 2)) {
			c.sendMessage("You don't have the required runes to cast this spell.");
			return;
		}
		if (System.currentTimeMillis() - c.lastCast < 30000) {
			c.sendMessage("You can only cast vengeance every 30 seconds.");
			return;
		}
		if (c.vengOn) {
			c.sendMessage("You already have vengeance casted.");
			return;
		}
		if (c.duelRule[4]) {
			c.sendMessage("You can't cast this spell because magic has been disabled.");
			return;
		}
		c.startAnimation(4410);
		c.gfx100(726);
		c.getItems().deleteItem2(9075, 4);
		c.getItems().deleteItem2(557, 10);
		c.getItems().deleteItem2(560, 2);
		addSkillXP(10000, 6);
		refreshSkill(6);
		c.vengOn = true;
		c.lastCast = System.currentTimeMillis();
	}
	
	public void fixAllBarrows() {
		int totalCost = 0;
		int cashAmount = c.getItems().getItemAmount(995);
		for (int j = 0; j < c.playerItems.length; j++) {
			boolean breakOut = false;
			for (int i = 0; i < c.getItems().brokenBarrows.length; i++) {
				if (c.playerItems[j]-1 == c.getItems().brokenBarrows[i][1]) {					
					if (totalCost + 80000 > cashAmount) {
						breakOut = true;
						c.sendMessage("You have run out of money.");
						break;
					} else {
						totalCost += 80000;
					}
					c.playerItems[j] = c.getItems().brokenBarrows[i][0]+1;
				}		
			}
			if (breakOut)		
				break;
		}
		if (totalCost > 0)
			c.getItems().deleteItem(995, c.getItems().getItemSlot(995), totalCost);		
	}

	public void vengMe() {
		if (System.currentTimeMillis() - c.lastVeng > 30000) {
			if (c.getItems().playerHasItem(557, 10)
					&& c.getItems().playerHasItem(9075, 4)
					&& c.getItems().playerHasItem(560, 2)) {
				c.vengOn = true;
				c.lastVeng = System.currentTimeMillis();
				c.startAnimation(4410);
				c.gfx100(726);
				c.getItems().deleteItem(557, c.getItems().getItemSlot(557), 10);
				c.getItems().deleteItem(560, c.getItems().getItemSlot(560), 2);
				c.getItems()
						.deleteItem(9075, c.getItems().getItemSlot(9075), 4);
			} else {
				c.sendMessage("You do not have the required runes to cast this spell. (9075 for astrals)");
			}
		} else {
			c.sendMessage("You must wait 30 seconds before casting this again.");
		}
	}

	public void addStarter() {
		if (c.playerRights == 6) {
			c.sendMessage("Sorry, Iron Men don't recieve starter items.");
			return;
		}
		if (!Connection.hasRecieved1stStarter(PlayerHandler.players[c.playerId].connectedFrom)) {
			c.getItems().addItem(995, 250000);
			c.joinSupportCC();
			Connection.addIpToStarterList1(PlayerHandler.players[c.playerId].connectedFrom);
			Connection.addIpToStarter1(PlayerHandler.players[c.playerId].connectedFrom);
		} else if (Connection.hasRecieved1stStarter(PlayerHandler.players[c.playerId].connectedFrom)&& 
				!Connection.hasRecieved2ndStarter(PlayerHandler.players[c.playerId].connectedFrom)) {
			c.getItems().addItem(995, 250000);
			c.joinSupportCC();
			Connection.addIpToStarterList2(PlayerHandler.players[c.playerId].connectedFrom);
			Connection.addIpToStarter2(PlayerHandler.players[c.playerId].connectedFrom);
		} else if (Connection.hasRecieved1stStarter(PlayerHandler.players[c.playerId].connectedFrom)
				&& Connection.hasRecieved2ndStarter(PlayerHandler.players[c.playerId].connectedFrom)) {
		}
	}

	public void sendFrame34P2(int item, int slot, int frame, int amount) {
		c.outStream.createFrameVarSizeWord(34);
		c.outStream.writeWord(frame);
		c.outStream.writeByte(slot);
		c.outStream.writeWord(item + 1);
		c.outStream.writeByte(255);
		c.outStream.writeDWord(amount);
		c.outStream.endFrameVarSizeWord();
	}

	public int getWearingAmount() {
		int count = 0;
		for (int j = 0; j < c.playerEquipment.length; j++) {
			if (c.playerEquipment[j] > 0)
				count++;
		}
		return count;
	}

	public void useOperate(int itemId) {
		switch (itemId) {
		case 1712:
		case 1710:
		case 1708:
		case 1706:
			c.getPA().handleGlory(itemId);
			c.itemUsing = itemId;
			c.isOperate = true;
			break;
		case 11283:
			if (c.playerIndex > 0 && c.dfsCharge >= 1) {
				c.getCombat().handleDfs();
			} else if (c.npcIndex > 0 && c.dfsCharge >= 1) {
				c.getCombat().handleDfsNPC();
			}
			break;
		case 11284:
			if (c.playerIndex > 0 && c.dfsCharge >= 1) {
				c.getCombat().handleDfs();
			} else if (c.npcIndex > 0 && c.dfsCharge >= 1) {
				c.getCombat().handleDfsNPC();
			}
			break;
		/*
		 * case 11283:
		 * c.sendMessage("Your Dragonfire shield currently has "+c.dfsCharge +
		 * " Charges."); break; case 11284: if (c.playerIndex > 0 && c.dfsCharge
		 * >= 1) { c.getCombat().handleDfs();
		 * 
		 * } else if (c.npcIndex > 0 && c.dfsCharge >= 1) {
		 * c.getCombat().handleDfsNPC(); }else{
		 * c.sendMessage("Your Dragonfire shield currently has "+c.dfsCharge +
		 * " Charges."); } break;
		 */
		case 2552:
		case 2554:
		case 2556:
		case 2558:
		case 2560:
		case 2562:
		case 2564:
		case 2566:
			c.getPA().startTeleport(3362, 3263, 0, "modern");
			break;
		}
	}

	public void getSpeared(int otherX, int otherY) {
		int x = c.absX - otherX;
		int y = c.absY - otherY;
		if (x > 0)
			x = 1;
		else if (x < 0)
			x = -1;
		if (y > 0)
			y = 1;
		else if (y < 0)
			y = -1;
		// moveCheck(x,y);
		c.lastSpear = System.currentTimeMillis();
	}

	public void moveCheck(int xMove, int yMove) {
		movePlayer(c.absX + xMove, c.absY + yMove, c.heightLevel);
	}

	public int findKiller() {
		int killer = c.playerId;
		int damage = 0;
		for (int j = 0; j < Config.MAX_PLAYERS; j++) {
			if (PlayerHandler.players[j] == null)
				continue;
			if (j == c.playerId)
				continue;
			if (c.goodDistance(c.absX, c.absY, PlayerHandler.players[j].absX,
					PlayerHandler.players[j].absY, 40)
					|| c.goodDistance(c.absX, c.absY + 9400,
							PlayerHandler.players[j].absX,
							PlayerHandler.players[j].absY, 40)
					|| c.goodDistance(c.absX, c.absY,
							PlayerHandler.players[j].absX,
							PlayerHandler.players[j].absY + 9400, 40))
				if (c.damageTaken[j] > damage) {
					damage = c.damageTaken[j];
					killer = j;
				}
		}
		return killer;
	}

	public void resetTzhaar() {
		c.waveId = -1;
		c.tzhaarToKill = -1;
		c.tzhaarKilled = -1;
		c.getPA().movePlayer(2438, 5168, 0);
	}

	public void enterCaves() {
		c.getPA().movePlayer(2413, 5117, c.playerId * 4);
		c.waveId = 0;
		c.tzhaarToKill = -1;
		c.tzhaarKilled = -1;
		EventManager.getSingleton().addEvent(new Event() {
			public void execute(EventContainer e) {
				Server.fightCaves.spawnNextWave((Client) Server.playerHandler.players[c.playerId]);
				e.stop();
			}
		}, 10000);
	}

	public void appendPoison(int damage) {
		if (System.currentTimeMillis() - c.lastPoisonSip > c.poisonImmune) {
			c.sendMessage("You have been poisoned.");
			c.poisonDamage = damage;
		}
	}

	public boolean checkForPlayer(int x, int y) {
		for (Player p : PlayerHandler.players) {
			if (p != null) {
				if (p.getX() == x && p.getY() == y)
					return true;
			}
		}
		return false;
	}

	public void checkPouch(int i) {
		if (i < 0)
			return;
		c.sendMessage("This pouch has " + c.pouches[i] + " rune ess in it.");
	}

	public void fillPouch(int i) {
		if (i < 0)
			return;
		int toAdd = c.POUCH_SIZE[i] - c.pouches[i];
		if (toAdd > c.getItems().getItemAmount(1436)) {
			toAdd = c.getItems().getItemAmount(1436);
		}
		if (toAdd > c.POUCH_SIZE[i] - c.pouches[i])
			toAdd = c.POUCH_SIZE[i] - c.pouches[i];
		if (toAdd > 0) {
			c.getItems().deleteItem(1436, toAdd);
			c.pouches[i] += toAdd;
		}
	}

	public void emptyPouch(int i) {
		if (i < 0)
			return;
		int toAdd = c.pouches[i];
		if (toAdd > c.getItems().freeSlots()) {
			toAdd = c.getItems().freeSlots();
		}
		if (toAdd > 0) {
			c.getItems().addItem(1436, toAdd);
			c.pouches[i] -= toAdd;
		}
	}

	public void handleLoginText() {
		c.getPA().sendFrame126("Monsters", 13037);
		c.getPA().sendFrame126("Mini-Games", 13047);
		c.getPA().sendFrame126("Bosses", 13055);
		c.getPA().sendFrame126("Wilderness Teleports", 13063);
		c.getPA().sendFrame126("Skilling Telepors", 13071);
		c.getPA().sendFrame126("Monsters", 1300);
		c.getPA().sendFrame126("Mini-Games", 1325);
		c.getPA().sendFrame126("Bosses", 1350);
		c.getPA().sendFrame126("Wilderness Teleports", 1382);
		c.getPA().sendFrame126("Skilling Teleports", 1415);
		c.getPA().sendFrame126("Cities Teleport", 1454);
		c.getPA().sendFrame126("Trollheim", 7457);
		// c.getPA().sendFrame126("13097", 13097);
		// c.getPA().sendFrame126("13089", 13089);
		// c.getPA().sendFrame126("13081", 13081);
		/*
		 * //modern c.getPA().sendFrame126("Teleport name", 1300); //varrock
		 * c.getPA().sendFrame126("Description", 1301); //varrock description
		 * c.getPA().sendFrame126("Teleport name", 1325); //lumbridge
		 * c.getPA().sendFrame126("Description", 1326); //lumbridge description
		 * c.getPA().sendFrame126("Teleport name", 1350); //falador
		 * c.getPA().sendFrame126("Description", 1351); //falador description
		 * c.getPA().sendFrame126("Teleport name", 1382); //camelot
		 * c.getPA().sendFrame126("Description", 1383); //camelot description
		 * c.getPA().sendFrame126("Teleport name", 1415); //ardougne
		 * c.getPA().sendFrame126("Description", 1416); //ardougne description
		 * c.getPA().sendFrame126("Teleport name", 1454); //watchtower
		 * c.getPA().sendFrame126("Description", 1455); //watchtower description
		 * c.getPA().sendFrame126("Teleport name", 7457); //trollheim
		 * c.getPA().sendFrame126("Description", 7458); //trollheim description
		 * c.getPA().sendFrame126("Teleport name", 18472); //ape atoll
		 * c.getPA().sendFrame126("Description", 18473); //ape atoll description
		 * 
		 * //ancients c.getPA().sendFrame126("Teleport name", 13037); //paddewwa
		 * c.getPA().sendFrame126("Monster Teleport", 13038); //paddewwa
		 * description c.getPA().sendFrame126("Teleport name", 13047);
		 * //senntisten c.getPA().sendFrame126("Description", 13048);
		 * //senntisten description c.getPA().sendFrame126("Teleport name",
		 * 13055); //kharyll c.getPA().sendFrame126("Description", 13056);
		 * //kharyll description c.getPA().sendFrame126("Teleport name", 13063);
		 * //lassar c.getPA().sendFrame126("Description", 13064); //lassar
		 * description c.getPA().sendFrame126("Teleport name", 13071);
		 * //dareeyak c.getPA().sendFrame126("Description", 13072); //dareeyak
		 * description
		 */
		c.getPA().sendFrame126("Cities Teleport", 13081); // carrallanger
		/*
		 * c.getPA().sendFrame126("Description", 13082); //carralanger
		 * description c.getPA().sendFrame126("Teleport name", 13089);
		 * //annakarl c.getPA().sendFrame126("Description", 13090); //annakarl
		 * description c.getPA().sendFrame126("Teleport name", 13097);
		 * //ghorrock c.getPA().sendFrame126("Description", 13098); //ghorrock
		 * description
		 */

	}

	/*
	 * public void handleLoginText() {
	 * c.getPA().sendFrame126("Training Teleport", 13037);
	 * c.getPA().sendFrame126("Duel Arena", 13047);
	 * c.getPA().sendFrame126("Boss Teleport", 13055);
	 * c.getPA().sendFrame126("Wilderness Teleport", 13063);
	 * c.getPA().sendFrame126("Ardougne Teleport", 13071);
	 * c.getPA().sendFrame126("Training Teleport", 1300);
	 * c.getPA().sendFrame126("Duel Arena", 1325);
	 * c.getPA().sendFrame126("Boss Teleport", 1350);
	 * c.getPA().sendFrame126("Wilderness Teleport", 1382);
	 * c.getPA().sendFrame126("Cities Teleport", 1415); }
	 */

	public void handleWeaponStyle() {
		if (c.fightMode == 0) {
			c.getPA().sendFrame36(43, c.fightMode);
		} else if (c.fightMode == 1) {
			c.getPA().sendFrame36(43, 3);
		} else if (c.fightMode == 2) {
			c.getPA().sendFrame36(43, 1);
		} else if (c.fightMode == 3) {
			c.getPA().sendFrame36(43, 2);
		}
	}

	public boolean banMac(Client macBan) {
		try {
			Connection.addMacToBanList(macBan.getMacAddress());
			Connection.addMacToMacFile(macBan.getMacAddress());
			macBan.disconnected = true;
			return true;
		} catch (Exception e) {
			c.sendMessage("Could not ban MAC!");
			return false;
		}
	}

	public int randomBarrowsReward() {
		return barrowsRewards[Misc.random(barrowsRewards.length - 1)];
	}

    public void resetBarrows() {
        this.c.barrowsNpcs[0][1] = 0;
        this.c.barrowsNpcs[1][1] = 0;
        this.c.barrowsNpcs[2][1] = 0;
        this.c.barrowsNpcs[3][1] = 0;
        this.c.barrowsNpcs[4][1] = 0;
        this.c.barrowsNpcs[5][1] = 0;
        this.c.barrowsKillCount = 0;
        this.c.randomCoffin = Misc.random(3) + 1;
    }

}
