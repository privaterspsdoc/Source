package main.model.players;

import java.util.HashMap;
import java.util.Map;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Future;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;

import main.model.players.highscores.*;
import main.Config;
import main.Server;
import main.event.CycleEvent;
import main.event.CycleEventContainer;
import main.event.CycleEventHandler;
import main.event.Event;
import main.event.EventContainer;
import main.event.EventManager;
import main.model.items.Item;
import main.model.items.ItemAssistant;
import main.model.items.bank.Bank;
import main.model.minigames.Barrows;
import main.model.minigames.PestControl;
import main.model.npcs.NPCHandler;
import main.model.npcs.Zulrah;
import main.model.npcs.pet.Pet;
import main.model.players.clan.Clan;
import main.model.players.skills.Agility;
import main.model.players.skills.Cooking;
import main.model.players.skills.Crafting;
import main.model.players.skills.Farming;
import main.model.players.skills.Firemaking;
import main.model.players.skills.Fletching;
import main.model.players.skills.Herblore;
import main.model.players.skills.Hunter;
import main.model.players.skills.Prayer;
import main.model.players.skills.Runecrafting;
import main.model.players.skills.SkillInterfaces;
import main.model.players.skills.Slayer;
import main.model.players.skills.Smithing;
import main.model.players.skills.SmithingInterface;
import main.model.players.skills.Summoning;
import main.model.players.skills.Thieving;
import main.model.players.skills.Woodcutting;
import main.model.shops.ShopAssistant;
import main.net.Connection;
import main.net.Packet;
import main.net.Packet.Type;
import main.util.Misc;
import main.util.Stream;
import main.util.log.Alching;
import main.util.log.ClickItem;
import main.util.log.CommandsLog;
import main.util.log.DroppingItem;
import main.util.log.PMLog;
import main.util.log.PickupItem;
import main.util.log.Shopping;
import main.util.log.StakingLog;
import main.util.log.TradeLog;

public class Client extends Player {

	private int j;
	public boolean usingClaws;
	public int clawDamage;
	public int clawIndex;
	public int clawType = 0;
	public int clawDelay;
	public int tradeinWith;
	public boolean SSPLIT;
	public int specRestore = 0;

	public void antiFirePotion() {
		EventManager.getSingleton().addEvent(new Event() {
			public void execute(EventContainer c) {
				antiFirePot = false;
				sendMessage("Your resistance to dragon fire has worn off.");
				c.stop();
			}
		}, 300000);

	}

	public byte buffer[] = null;
	public Stream inStream = null, outStream = null;
	private Channel session;
	private Woodcutting woodcutting = new Woodcutting();
	private ItemAssistant itemAssistant = new ItemAssistant(this);
	private ShopAssistant shopAssistant = new ShopAssistant(this);
	private TradeAndDuel tradeAndDuel = new TradeAndDuel(this);
	private PlayerAssistant playerAssistant = new PlayerAssistant(this);
	private CombatAssistant combatAssistant = new CombatAssistant(this);
	private Barrows barrows = new Barrows(this);
	private PMLog PMLog = new PMLog(this);
	private Shopping Shopping = new Shopping(this);
	private PickupItem PickupItem = new PickupItem(this);
	private Alching Alching = new Alching(this);
	private ClickItem ClickItem = new ClickItem(this);
	private DroppingItem DroppingItem = new DroppingItem(this);
	private CommandsLog CommandsLog = new CommandsLog(this);
	private StakingLog StakingLog = new StakingLog(this);
	public Summoning Summoning = new Summoning(this);
	private ActionHandler actionHandler = new ActionHandler(this);
	private PlayerKilling playerKilling = new PlayerKilling(this);
	private DialogueHandler dialogueHandler = new DialogueHandler(this);
	private Queue<Packet> queuedPackets = new LinkedList<Packet>();
	private Potions potions = new Potions(this);
	private PotionMixing potionMixing = new PotionMixing(this);
	private Food food = new Food(this);
	private SkillInterfaces skillInterfaces = new SkillInterfaces(this);
	private TradeLog tradeLog = new TradeLog(this);
	private Bank bank = new Bank(this);
	private Pets pets = new Pets();
	private Zulrah zulrah = new Zulrah(this);

	/**
	 * Skill instances
	 */
	private Slayer slayer = new Slayer(this);
	private Hunter hunter = new Hunter(this);
	private Runecrafting runecrafting = new Runecrafting();
	private Agility agility = new Agility();
	private Cooking cooking = new Cooking();
	private Crafting crafting = new Crafting(this);

	private Fletching fletching = new Fletching(this);
	private Farming farming = new Farming(this);
	private Prayer prayer = new Prayer(this);
	private Thieving thieving = new Thieving();
	private Smithing smith = new Smithing(this);
	private SmithingInterface smithInt = new SmithingInterface(this);
	private Firemaking firemaking = new Firemaking();
	private Herblore herblore = new Herblore(this);
	// private ConnectedFrom connectedFrom = new ConnectedFrom(this);
	public Map<Integer, String> stringMap = new HashMap<Integer, String>();
	public int lowMemoryVersion = 0;
	public int timeOutCounter = 0;
	public int followPlayer;
	public int npcslot;
	public int summoningnpcid;
	public int timer;
	public int returnCode = 2;
	private Future<?> currentTask;
	public int totalstored;
	public int currentRegion = 0;
	public long lastRoll;
	public int diceItem;
	public int page;
	public boolean slayerHelmetEffect;
	public boolean inArdiCC;
	public int[] resourceItemId = new int[28];
	public int[] resourceItemAmount = new int[28];
	public boolean canDeposit = false;
	public boolean usedItemOnBag;
	public long lastBankDeposit;

	public Client(Channel s, int _playerId) {
		super(_playerId);
		this.session = s;
		outStream = new Stream(new byte[Config.BUFFER_SIZE]);
		outStream.currentOffset = 0;
		inStream = new Stream(new byte[Config.BUFFER_SIZE]);
		inStream.currentOffset = 0;
		buffer = new byte[Config.BUFFER_SIZE];
	}

	public void flushOutStream() {
		if (!session.isConnected() || disconnected
				|| outStream.currentOffset == 0)
			return;

		byte[] temp = new byte[outStream.currentOffset];
		System.arraycopy(outStream.buffer, 0, temp, 0, temp.length);
		Packet packet = new Packet(-1, Type.FIXED,
				ChannelBuffers.wrappedBuffer(temp));
		session.write(packet);
		outStream.currentOffset = 0;

	}

	private Map<Integer, TinterfaceText> interfaceText = new HashMap<Integer, TinterfaceText>();

	public class TinterfaceText {
		public int id;
		public String currentState;

		public TinterfaceText(String s, int id) {
			this.currentState = s;
			this.id = id;
		}

	}

	public boolean checkPacket126Update(String text, int id) {
		if (interfaceText.containsKey(id)) {
			TinterfaceText t = interfaceText.get(id);
			if (text.equals(t.currentState)) {
				return false;
			}
		}
		interfaceText.put(id, new TinterfaceText(text, id));
		return true;
	}

	public void sendClan(String name, String message, String clan, int rights) {
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		message = message.substring(0, 1).toUpperCase() + message.substring(1);
		clan = clan.substring(0, 1).toUpperCase() + clan.substring(1);
		outStream.createFrameVarSizeWord(217);
		outStream.writeString(name);
		outStream.writeString(message);
		outStream.writeString(clan);
		outStream.writeWord(rights);
		outStream.endFrameVarSize();
	}

	public int maxstore = 0;

	/*
	 * public void storesummon(int npcType) { switch (npcType) {
	 * 
	 * case 6807: if (lastsummon > 0) { for (int i = 0; i <
	 * Server.npcHandler.maxNPCs; i++) { if (Server.npcHandler.npcs[i] != null)
	 * { if (Server.npcHandler.npcs[i].summon == true) { if
	 * (Server.npcHandler.npcs[i].spawnedBy == getId() &&
	 * Server.npcHandler.npcs[i].npcId == npcslot) {
	 * sendMessage("You are now storing items inside your npc");
	 * Summoning().store(); } } } }
	 * 
	 * } break;
	 * 
	 * } }
	 */

	public void firstslot() {
		for (summoningslot = 0; occupied[summoningslot] == true; summoningslot += 1) {

		}
	}

	public static int[][] bounties1 = { { 12746, 50000 }, { 12748, 100000 },
			{ 12749, 200000 }, { 12750, 400000 }, { 12751, 750000 },
			{ 12752, 1200000 }, { 12753, 1750000 }, { 12754, 2500000 },
			{ 12755, 3500000 }, { 12756, 5000000 } };

	public int bountyTotal;

	public void calculateTotal() {
		int value1 = 0;
		int value2 = 0;
		int value3 = 0;
		int value4 = 0;
		int value5 = 0;
		int value6 = 0;
		int value7 = 0;
		int value8 = 0;
		int value9 = 0;
		int value10 = 0;
		for (int i = 0; i < id.length; i++) {
			if (getItems().playerHasItem(bounties1[0][0], 1)
					|| getItems().playerHasItem(bounties1[1][0], 1)
					|| getItems().playerHasItem(bounties1[2][0], 1)
					|| getItems().playerHasItem(bounties1[3][0], 1)
					|| getItems().playerHasItem(bounties1[4][0], 1)
					|| getItems().playerHasItem(bounties1[5][0], 1)
					|| getItems().playerHasItem(bounties1[6][0], 1)
					|| getItems().playerHasItem(bounties1[7][0], 1)
					|| getItems().playerHasItem(bounties1[8][0], 1)
					|| getItems().playerHasItem(bounties1[9][0], 1)) {
				value1 = getItems().getItemAmount(bounties1[0][0])
						* bounties1[0][1];
				value2 = getItems().getItemAmount(bounties1[1][0])
						* bounties1[1][1];
				value3 = getItems().getItemAmount(bounties1[2][0])
						* bounties1[2][1];
				value4 = getItems().getItemAmount(bounties1[3][0])
						* bounties1[3][1];
				value5 = getItems().getItemAmount(bounties1[4][0])
						* bounties1[4][1];
				value6 = getItems().getItemAmount(bounties1[5][0])
						* bounties1[5][1];
				value7 = getItems().getItemAmount(bounties1[6][0])
						* bounties1[6][1];
				value8 = getItems().getItemAmount(bounties1[7][0])
						* bounties1[7][1];
				value9 = getItems().getItemAmount(bounties1[8][0])
						* bounties1[8][1];
				value10 = getItems().getItemAmount(bounties1[9][0])
						* bounties1[9][1];
			}
			bountyTotal = value1 + value2 + value3 + value4 + value5 + value6
					+ value7 + value8 + value9 + value10;
		}
	}

	public static int[] emblems = { 12746, 12748, 12749, 12750, 12751, 12752,
			12753, 12754, 12755, 12756 };
	public static int[][] pkpRewards = { { 12746, 1 }, { 12748, 2 },
			{ 12749, 3 }, { 12750, 4 }, { 12751, 6 }, { 12752, 8 },
			{ 12753, 10 }, { 12754, 12 }, { 12755, 15 }, { 12756, 20 } };

	public int pkpTotal;

	public void calculateTotalPKP() {
		int value1 = 0;
		int value2 = 0;
		int value3 = 0;
		int value4 = 0;
		int value5 = 0;
		int value6 = 0;
		int value7 = 0;
		int value8 = 0;
		int value9 = 0;
		int value10 = 0;
		for (int i = 0; i < id.length; i++) {
			if (getItems().playerHasItem(pkpRewards[0][0], 1)
					|| getItems().playerHasItem(pkpRewards[1][0], 1)
					|| getItems().playerHasItem(pkpRewards[2][0], 1)
					|| getItems().playerHasItem(pkpRewards[3][0], 1)
					|| getItems().playerHasItem(pkpRewards[4][0], 1)
					|| getItems().playerHasItem(pkpRewards[5][0], 1)
					|| getItems().playerHasItem(pkpRewards[6][0], 1)
					|| getItems().playerHasItem(pkpRewards[7][0], 1)
					|| getItems().playerHasItem(pkpRewards[8][0], 1)
					|| getItems().playerHasItem(pkpRewards[9][0], 1)) {
				value1 = getItems().getItemAmount(pkpRewards[0][0])
						* pkpRewards[0][1];
				value2 = getItems().getItemAmount(pkpRewards[1][0])
						* pkpRewards[1][1];
				value3 = getItems().getItemAmount(pkpRewards[2][0])
						* pkpRewards[2][1];
				value4 = getItems().getItemAmount(pkpRewards[3][0])
						* pkpRewards[3][1];
				value5 = getItems().getItemAmount(pkpRewards[4][0])
						* pkpRewards[4][1];
				value6 = getItems().getItemAmount(pkpRewards[5][0])
						* pkpRewards[5][1];
				value7 = getItems().getItemAmount(pkpRewards[6][0])
						* pkpRewards[6][1];
				value8 = getItems().getItemAmount(pkpRewards[7][0])
						* pkpRewards[7][1];
				value9 = getItems().getItemAmount(pkpRewards[8][0])
						* pkpRewards[8][1];
				value10 = getItems().getItemAmount(pkpRewards[9][0])
						* pkpRewards[9][1];
				pkpTotal = value1 + value2 + value3 + value4 + value5 + value6
						+ value7 + value8 + value9 + value10;
			}
		}
	}

	public int emblemTotal;

	public int calculateEmblemTotal() {
		int value1 = 0;
		int value2 = 0;
		int value3 = 0;
		int value4 = 0;
		int value5 = 0;
		int value6 = 0;
		int value7 = 0;
		int value8 = 0;
		int value9 = 0;
		int value10 = 0;
		for (int i = 0; i < id.length; i++) {
			if (getItems().playerHasItem(pkpRewards[0][0], 1)
					|| getItems().playerHasItem(pkpRewards[1][0], 1)
					|| getItems().playerHasItem(pkpRewards[2][0], 1)
					|| getItems().playerHasItem(pkpRewards[3][0], 1)
					|| getItems().playerHasItem(pkpRewards[4][0], 1)
					|| getItems().playerHasItem(pkpRewards[5][0], 1)
					|| getItems().playerHasItem(pkpRewards[6][0], 1)
					|| getItems().playerHasItem(pkpRewards[7][0], 1)
					|| getItems().playerHasItem(pkpRewards[8][0], 1)
					|| getItems().playerHasItem(pkpRewards[9][0], 1)) {
				value1 = getItems().getItemAmount(pkpRewards[0][0]);
				value2 = getItems().getItemAmount(pkpRewards[1][0]);
				value3 = getItems().getItemAmount(pkpRewards[2][0]);
				value4 = getItems().getItemAmount(pkpRewards[3][0]);
				value5 = getItems().getItemAmount(pkpRewards[4][0]);
				value6 = getItems().getItemAmount(pkpRewards[5][0]);
				value7 = getItems().getItemAmount(pkpRewards[6][0]);
				value8 = getItems().getItemAmount(pkpRewards[7][0]);
				value9 = getItems().getItemAmount(pkpRewards[8][0]);
				value10 = getItems().getItemAmount(pkpRewards[9][0]);
			}
			emblemTotal = value1 + value2 + value3 + value4 + value5 + value6
					+ value7 + value8 + value9 + value10;
		}
		return emblemTotal;
	}

	public int countItems(int id) {
		switch (id) {
		case 12746:
		case 12748:
		case 12749:
		case 12750:
		case 12751:
		case 12752:
		case 12753:
		case 12754:
		case 12755:
		case 12756:
			return getItems().getItemAmount(id);
		}
		return 0;
	}

	public int id[] = { 12746, 12748, 12749, 12750, 12751, 12752, 12753, 12754,
			12755, 12756 };

	public void dropRandomEmblem(Client o, Client c) {
		Server.itemHandler.createGroundItem(o, 12746, c.getX(), c.getY(), 1,
				c.killerId);

	}

	public void upgradeEmblem() {
		if (getItems().playerHasItem(12755)) {
			getItems().deleteItem(12755, 1);
			getItems().addItem(12756, 1);
			sendMessage("Your Emblem has been upgraded to Tier 10");
			return;
		} else if (getItems().playerHasItem(12754)) {
			getItems().deleteItem(12754, 1);
			getItems().addItem(12755, 1);
			sendMessage("Your Emblem has been upgraded to Tier 9");
			return;
		} else if (getItems().playerHasItem(12753)) {
			getItems().deleteItem(12753, 1);
			getItems().addItem(12754, 1);
			sendMessage("Your Emblem has been upgraded to Tier 8");
			return;
		} else if (getItems().playerHasItem(12752)) {
			getItems().deleteItem(12752, 1);
			getItems().addItem(12753, 1);
			sendMessage("Your Emblem has been upgraded to Tier 7");
			return;
		} else if (getItems().playerHasItem(12751, 1)) {
			getItems().deleteItem(12751, 1);
			getItems().addItem(12752, 1);
			sendMessage("Your Emblem has been upgraded to Tier 6");
			return;
		} else if (getItems().playerHasItem(12750, 1)) {
			getItems().deleteItem(12750, 1);
			getItems().addItem(12751, 1);
			sendMessage("Your Emblem has been upgraded to Tier 5");
			return;
		} else if (getItems().playerHasItem(12749, 1)) {
			getItems().deleteItem(12749, 1);
			getItems().addItem(12750, 1);
			sendMessage("Your Emblem has been upgraded to Tier 4");
			return;
		} else if (getItems().playerHasItem(12748, 1)) {
			getItems().deleteItem(12748, 1);
			getItems().addItem(12749, 1);
			sendMessage("Your Emblem has been upgraded to Tier 3");
			return;
		} else if (getItems().playerHasItem(12746, 1)) {
			getItems().deleteItem(12746, 1);
			getItems().addItem(12748, 1);
			sendMessage("Your Emblem has been upgraded to Tier 2");
			return;
		}
	}

	public int summoningslot = 0;

	public int storeditems[] = new int[29];

	public boolean picking = false;

	public int amount[] = new int[29];
	public boolean occupied[] = new boolean[29];

	public boolean storing = false;

	public int attackingplayer;
	public int lastsummon;
	public boolean summon;

	public static final int PACKET_SIZES[] = { 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, // 0
			0, 0, 0, 0, 4, 0, 6, 2, 2, 0, // 10
			0, 2, 0, 6, 0, 12, 0, 0, 0, 0, // 20
			0, 0, 0, 0, 0, 8, 4, 0, 0, 2, // 30
			2, 6, 0, 6, 0, -1, 0, 0, 0, 0, // 40
			0, 0, 0, 12, 0, 0, 0, 8, 8, 12, // 50
			8, 8, 0, 0, 0, 0, 0, 0, 0, 0, // 60
			6, 0, 2, 2, 8, 6, 0, -1, 0, 6, // 70
			0, 0, 0, 0, 0, 1, 4, 6, 0, 0, // 80
			0, 0, 0, 0, 0, 3, 0, 0, -1, 0, // 90
			0, 13, 0, -1, 0, 0, 0, 0, 0, 0, // 100
			0, 0, 0, 0, 0, 0, 0, 6, 0, 0, // 110
			1, 0, 6, 0, 0, 0, -1, /* 0 */-1, 2, 6, // 120
			0, 4, 6, 8, 0, 6, 0, 0, 0, 2, // 130
			0, 0, 0, 0, 0, 6, 0, 0, 0, 0, // 140
			0, 0, 1, 2, 0, 2, 6, 0, 0, 0, // 150
			0, 0, 0, 0, -1, -1, 0, 0, 0, 0, // 160
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 170
			0, 8, 0, 3, 0, 2, 0, 0, 8, 1, // 180
			0, 0, 12, 0, 0, 0, 0, 0, 0, 0, // 190
			2, 0, 0, 0, 0, 0, 0, 0, 4, 0, // 200
			4, 0, 0, /* 0 */4, 7, 8, 0, 0, 10, 0, // 210
			0, 0, 0, 0, 0, 0, -1, 0, 6, 0, // 220
			1, 0, 0, 0, 6, 0, 6, 8, 1, 0, // 230
			0, 4, 0, 0, 0, 0, -1, 0, -1, 4, // 240
			0, 0, 6, 6, 0, 0, 0 // 250
	};

	public void homeTeleport(int x, int y, int h) {
		if (homeTele == 9) {
			startAnimation(4850);
		} else if (homeTele == 7) {
			startAnimation(4853);
			gfx0(802);
		} else if (homeTele == 5) {
			startAnimation(4855);
			gfx0(803);
		} else if (homeTele == 3) {
			startAnimation(4857);
			gfx0(804);
		} else if (homeTele == 1) {
			homeTeleDelay = 0;
			homeTele = 0;
			teleportToX = x;
			teleportToY = y;
			heightLevel = h;
		}
	}

	@Override
	public void destruct() {
		disconnected = true;
		if (session == null)
			return;
		if (underAttackBy > 0 || underAttackBy2 > 0)
			return;
		if (duelStatus == 6) {
			getTradeAndDuel().claimStakedItems();
		}
		/*
		if (duelStatus >= 1 && duelStatus <= 5) {
			//Client o = (Client) PlayerHandler.players[duelingWith];
			//o.getTradeAndDuel().duelVictory();
			//getTradeAndDuel().resetDuel();
			//getTradeAndDuel().resetDuelItems();
			//getTradeAndDuel().bothDeclineDuel();
			//saveCharacter = true;
			return;
		}
		*/

		if (disconnected == true) {
			if (inTrade) {
				getTradeAndDuel().declineTrade();
			}
			saveCharacter = true;
		}
		if (clan != null) {
			clan.removeMember(this);
		}
		if (inPits) {
			Server.fightPits.removePlayerFromPits(playerId);
		}
		RefreshAllSkills();
		CycleEventHandler.getSingleton().stopEvents(this);
		// disconnected = true;
		session.close();
		session = null;
		inStream = null;
		outStream = null;
		isActive = false;
		buffer = null;
		super.destruct();
	}

	public void calcCombat() {
		int mag = (int) ((getLevelForXP(playerXP[6])) * 1.5);
		int ran = (int) ((getLevelForXP(playerXP[4])) * 1.5);
		int attstr = (int) ((double) (getLevelForXP(playerXP[0])) + (double) (getLevelForXP(playerXP[2])));

		combatLevel = 0;
		if (ran > attstr) {
			combatLevel = (int) (((getLevelForXP(playerXP[1])) * 0.25)
					+ ((getLevelForXP(playerXP[3])) * 0.25)
					+ ((getLevelForXP(playerXP[5])) * 0.125) + ((getLevelForXP(playerXP[4])) * 0.4875));
		} else if (mag > attstr) {
			combatLevel = (int) (((getLevelForXP(playerXP[1])) * 0.25)
					+ ((getLevelForXP(playerXP[3])) * 0.25)
					+ ((getLevelForXP(playerXP[5])) * 0.125) + ((getLevelForXP(playerXP[6])) * 0.4875));
		} else {
			combatLevel = (int) (((getLevelForXP(playerXP[1])) * 0.25)
					+ ((getLevelForXP(playerXP[3])) * 0.25)
					+ ((getLevelForXP(playerXP[5])) * 0.125)
					+ ((getLevelForXP(playerXP[0])) * 0.325) + ((getLevelForXP(playerXP[2])) * 0.325));
		}
	}

	public void sendMessage(String s) {
		// synchronized (this) {
		if (getOutStream() != null) {
			outStream.createFrameVarSize(253);
			outStream.writeString(s);
			outStream.endFrameVarSize();
		}

	}

	public void globalMessage(String message) {
		for (Player p : PlayerHandler.players) {
			if (p == null)
				continue;
			Client c = (Client) p;
			c.sendMessage(message);
		}
	}

	public void setSidebarInterface(int menuId, int form) {
		// synchronized (this) {
		if (getOutStream() != null) {
			outStream.createFrame(71);
			outStream.writeWord(form);
			outStream.writeByteA(menuId);
		}

	}

	public void autoMessage(String q) {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c2 = (Client) PlayerHandler.players[j];
				c2.sendMessage(q);
			}
		}
	}

	public void joinSupportCC() {
		if (clan == null) {
			Clan localClan = Server.clanManager.getClan("Support");
			if (localClan != null)
				localClan.addMember(this);
			else if ("help".equalsIgnoreCase(this.playerName))
				Server.clanManager.create(this);
			else {
				sendMessage("The staff team has temporarily disabled the support chat.");
			}
			inArdiCC = true;
		}
	}

	public void loginFrames() {
		// log out text
		getPA().sendFrame126("When you have finished playing", 2450);
		getPA().sendFrame126("Zaros please use the logout", 2451);
		getPA().sendFrame126("button below to logout safely.", 2452);
		//end logout text
		//brightness slider
		getPA().sendFrame36(166, 4);
		//end brighness slider
	}
	@Override
	public void initialize() {
		//Highscores.process();
		loginFrames();
		this.getPA().clearClanChat();
		getPA().setClanData();
		getPA().loadQuests();
		getPA().sendFrame127(runEnergy + "%", 149);
		isFullHelm = Item.isFullHelm(playerEquipment[playerHat]);
		isFullMask = Item.isFullMask(playerEquipment[playerHat]);
		isFullBody = Item.isFullBody(playerEquipment[playerChest]);
		getPA().sendFrame36(173, isRunning2 ? 1 : 0);
		sendMessage("Welcome to Zaros.");
		calcCombat();
		Pet.ownerLoggedIn(this);
		outStream.createFrame(249);
		outStream.writeByteA(1);
		outStream.writeWordBigEndianA(playerId);
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (j == playerId)
				continue;
			if (PlayerHandler.players[j] != null) {
				if (PlayerHandler.players[j].playerName
						.equalsIgnoreCase(playerName))
					disconnected = true;
			}
		}
		for (int i = 0; i < 25; i++) {
			getPA().setSkillLevel(i, playerLevel[i], playerXP[i]);
			getPA().refreshSkill(i);
			getPA().refreshSkill(21);
			getPA().refreshSkill(22);
		}
		for (int p = 0; p < PRAYER.length; p++) { // reset prayer glows
			prayerActive[p] = false;
			getPA().sendFrame36(PRAYER_GLOW[p], 0);
		}
		getPA().handleWeaponStyle();
		getPA().handleLoginText();
		accountFlagged = getPA().checkForFlags();
		getPA().sendFrame36(108, 0);// resets autocast button
		getPA().sendFrame36(172, 1);
		getPA().sendFrame107(); // reset screen
		getPA().setChatOptions(0, 0, 0); // reset private messaging options
		//setSidebarInterface(1, 3917);
		setSidebarInterface(1, 3917);
		//setSidebarInterface(1, 31110);
		setSidebarInterface(2, 638);
		setSidebarInterface(3, 3213);
		setSidebarInterface(4, 1644);
		setSidebarInterface(5, 5608);
		if (playerMagicBook == 0) {
			setSidebarInterface(6, 1151); // modern
		}
		if (playerMagicBook == 1) {
			setSidebarInterface(6, 12855); // ancient
		}
		if (playerMagicBook == 2) {
			setSidebarInterface(6, 29999); // ancient
		}
		if (splitChat == false) {
			getPA().sendFrame36(502, 0);
			getPA().sendFrame36(287, 0);
			}
			if (splitChat == true) {
			getPA().sendFrame36(502, 1);
			getPA().sendFrame36(287, 1);
			}
		if (resize == false) {
			sendMessage(":fixed:");
			}
		if (resize == true) {
			sendMessage(":resize:");
			}
		correctCoordinates();
		setSidebarInterface(7, 37128);
		setSidebarInterface(8, 5065);
		setSidebarInterface(9, 5715); //ignore
		setSidebarInterface(10, 2449); //logout
		// setSidebarInterface(7, 19017);
		setSidebarInterface(11, 904); // wrench tab
		setSidebarInterface(12, 147); // run tab
		setSidebarInterface(13, -1); //music
		setSidebarInterface(0, 2423);
		setSidebarInterface(15, 2449);
		setSidebarInterface(16, 3213);
		if (lastsummon > 0) {
			Summoning().SummonNewNPC(lastsummon);
		}
		getPA().showOption(4, 0, "Follow", 4);
		getPA().showOption(5, 0, "Trade with", 3);
		getItems().resetItems(3214);
		getItems().sendWeapon(playerEquipment[playerWeapon],
				getItems().getItemName(playerEquipment[playerWeapon]));
		getItems().resetBonus();
		getItems().getBonus();
		getItems().writeBonus();
		getItems().setEquipment(playerEquipment[playerHat], 1, playerHat);
		getItems().setEquipment(playerEquipment[playerCape], 1, playerCape);
		getItems().setEquipment(playerEquipment[playerAmulet], 1, playerAmulet);
		getItems().setEquipment(playerEquipment[playerArrows],
				playerEquipmentN[playerArrows], playerArrows);
		getItems().setEquipment(playerEquipment[playerChest], 1, playerChest);
		getItems().setEquipment(playerEquipment[playerShield], 1, playerShield);
		getItems().setEquipment(playerEquipment[playerLegs], 1, playerLegs);
		getItems().setEquipment(playerEquipment[playerHands], 1, playerHands);
		getItems().setEquipment(playerEquipment[playerFeet], 1, playerFeet);
		getItems().setEquipment(playerEquipment[playerRing], 1, playerRing);
		getItems().setEquipment(playerEquipment[playerWeapon],
				playerEquipmentN[playerWeapon], playerWeapon);
		getCombat().getPlayerAnimIndex(
				getItems().getItemName(playerEquipment[playerWeapon])
						.toLowerCase());
		getPA().logIntoPM();
		getItems().addSpecialBar(playerEquipment[playerWeapon]);
		saveTimer = Config.SAVE_TIMER;
		saveCharacter = true;
		Misc.println("Player count: "+ PlayerHandler.getPlayerCount() + "");
		handler.updatePlayer(this, outStream);
		handler.updateNPC(this, outStream);
		flushOutStream();
		getPA().resetFollow();
		getPA().clearClanChat();
		getPA().resetFollow();
		getPA().setClanData();
		Connection.appendStarters();
		Connection.appendStarters2();
		if (addStarter)
			getPA().addStarter();
		//this.joinSupportCC();
		if (hasNpc == true) {
			if (summonId == 4000) {
				getItems().addItem(12653, 1);
			}
			if (summonId == 4003) {
				getItems().addItem(12694, 1);
			}
			if (summonId == 4001) {
				getItems().addItem(12650, 1);
			}
			if (summonId == 4005) {
				getItems().addItem(12649, 1);
			}
			if (summonId == 4006) {
				getItems().addItem(12652, 1);
			}
			if (summonId == 4007) {
				getItems().addItem(12651, 1);
			}
			if (summonId == 4008) {
				getItems().addItem(12643, 1);
			}
			if (summonId == 4009) {
				getItems().addItem(12644, 1);
			}
			if (summonId == 4010) {
				getItems().addItem(12645, 1);
			}
			if (summonId == 6305) {
				getItems().addItem(12655, 1);
			}
			summonId = 0;
			hasNpc = false;
			this.sendMessage("Your pet has been added to your inventory.");
		}
		if (autoRet == 1)
			getPA().sendFrame36(172, 1);
		else
			getPA().sendFrame36(172, 0);
	}

	public void RefreshAllSkills() {
		for (int i = 0; i < 25; i++) {
			getPA().setSkillLevel(i, playerLevel[i], playerXP[i]);
			getPA().refreshSkill(i);
		}
	}

	@Override
	public void update() {
		handler.updatePlayer(this, outStream);
		handler.updateNPC(this, outStream);
		flushOutStream();

	}

	public void wildyWarning() {
		getPA().showInterface(1908);
	}

	public void logout() {
		//Highscores.save(this);
		if (clan != null) {
			clan.removeMember(this);
		}
		Connection.removeIpFromLoginList(this.getConnectedFrom());
		if (duelStatus >= 1 && duelStatus <= 5) {
			sendMessage("You cannot logout during a duel.");
			getTradeAndDuel().bothDeclineDuel();
			saveCharacter = true;
			return;
		}
		if (underAttackBy > 0 || underAttackBy2 > 0)
			return;
		// synchronized (this) {
		if (System.currentTimeMillis() - logoutDelay > 10000) {
			outStream.createFrame(109);
			CycleEventHandler.getSingleton().stopEvents(this);
			properLogout = true;
			ConnectedFrom.addConnectedFrom(this, connectedFrom);
		} else {
			sendMessage("You must wait a few seconds from being out of combat to logout.");
		}

	}

	public int packetSize = 0, packetType = -1;
private void godwarsinterface() {
	getPA(). sendFrame126("@cya@" + bandosGwdKC, 16217);
	
	getPA(). sendFrame126("@cya@" + armaGwdKC, 16216);
	
	getPA(). sendFrame126("@cya@" + saraGwdKC, 16218);
	
	getPA(). sendFrame126("@cya@" + zamGwdKC, 16219);
}
	@Override
	public void process() {
		if (inTheArena() && duelStatus == 0) {
			sendMessage("You have been moved to the challenge area, possibly due to a bug.");
			getPA().movePlayer(3366, 3268, 0);
		}
		godwarsinterface();
		getPA().sendFrame126(
		"Players Online: " + PlayerHandler.getPlayerCount(), 29155);
		if (clawDelay > 0) {
			clawDelay--;
		}
		if (clawDelay == 1) {
			delayedDamage = (clawDamage / 4);
			delayedDamage2 = (clawDamage / 4) + 1;
			if (clawType == 2) {
				getCombat().applyNpcMeleeDamage(clawIndex, 1, clawDamage / 4);
			}
			if (clawType == 1) {
				getCombat()
						.applyPlayerMeleeDamage(clawIndex, 1, clawDamage / 4);
			}
			if (clawType == 2) {
				getCombat().applyNpcMeleeDamage(clawIndex, 2,
						(clawDamage / 4) + 1);
			}
			if (clawType == 1) {
				getCombat().applyPlayerMeleeDamage(clawIndex, 2,
						(clawDamage / 4) + 1);
			}
			clawDelay = 0;
			specEffect = 0;
			previousDamage = 0;
			usingClaws = false;
			clawType = 0;
		}
		if (runEnergy < 100) {
			if (System.currentTimeMillis() > getPA().getAgilityRunRestore(this)
					+ lastRunRecovery) {
				runEnergy++;
				lastRunRecovery = System.currentTimeMillis();
				getPA().sendFrame126(runEnergy + "%", 149);
			}
		}

		if (System.currentTimeMillis() - restoreStatsDelay > (zerkOn ? 90000
				: 60000)) {
			restoreStatsDelay = System.currentTimeMillis();
			for (int level = 0; level < playerLevel.length; level++) {
				if (playerLevel[level] < getLevelForXP(playerXP[level])) {
					if (level != 5) {
						playerLevel[level] += 1;
						getPA().setSkillLevel(level, playerLevel[level],
								playerXP[level]);
						getPA().refreshSkill(level);
					}
				} else if (playerLevel[level] > getLevelForXP(playerXP[level])
						&& level != 3) {
					playerLevel[level] -= 1;
					getPA().setSkillLevel(level, playerLevel[level],
							playerXP[level]);
					getPA().refreshSkill(level);
				}
			}
		}

		if (System.currentTimeMillis() - duelDelay > 1000 && duelCount > 0) {
			if (duelCount != 1) {
				forcedChat("" + (--duelCount));
				duelDelay = System.currentTimeMillis();
			} else {
				damageTaken = new int[Config.MAX_PLAYERS];
				forcedChat("FIGHT!");
				duelCount = 0;
			}
		}
		if (System.currentTimeMillis() - specDelay > Config.INCREASE_SPECIAL_AMOUNT) {
			specDelay = System.currentTimeMillis();
			if (specAmount < 10) {
				specAmount += 1;
				if (specAmount > 10)
					specAmount = 10;
				getItems().addSpecialBar(playerEquipment[playerWeapon]);
			}
		}

		if (summonSpec > 0) {
			summonSpec--;
		}

		if (followId > 0) {
			getPA().followPlayer();
		} else if (followId2 > 0) {
			getPA().followNpc();
		}
		getCombat().handlePrayerDrain();
		if (System.currentTimeMillis() - singleCombatDelay > 3300) {
			underAttackBy = 0;
		}
		if (System.currentTimeMillis() - singleCombatDelay2 > 3300) {
			underAttackBy2 = 0;
		}
		if (this.inWild()) {
			final int modY = this.absY > 6400 ? this.absY - 6400 : this.absY;
			this.wildLevel = (modY - 3520) / 8 + 1;
			getPA().walkableInterface(197);
			if (Config.SINGLE_AND_MULTI_ZONES) {
				if (this.inMulti()) {
					this.getPA().sendFrame126("@yel@Level: " + this.wildLevel, 199);
				} else {
					this.getPA().sendFrame126("@yel@Level: " + this.wildLevel, 199);
				}
			} else {
				this.getPA().multiWay(-1);
				this.getPA().sendFrame126("@yel@Level: " + this.wildLevel, 199);
			}
			this.getPA().showOption(3, 0, "Attack", 1);
		} else if (this.isInBarrows()) {
			this.getPA().sendFrame126("Kill Count: " + this.barrowsKillCount,
					4536);
			this.getPA().walkableInterface(4535);
			this.getPA().showOption(3, 0, "null", 1);
			this.getPA().showOption(2, 0, "null", 1);
		} else if (gwdCoords()) {
			getPA().walkableInterface(16210);
/*			
			} else if (!gwdCoords()) {
	          getPA().walkableInterface(-1);
	          */
		} else if (inPcBoat()) {
				getPA().sendFrame126(
						"Next Departure: " + PestControl.waitTimer + "", 21120);
				getPA().sendFrame126(
						"Players Ready: " + PestControl.playersInBoat() + "", 21121);
				getPA().sendFrame126("(1-25 Players to Start)", 21122);
				getPA().sendFrame126("Pest Points: " + pcPoints + "", 21123);
				getPA().walkableInterface(21119);
		} else if (inPcGame() && PestControl.gameTimer < 0) {
			getPA().movePlayer(3086, 3493, 0);
			sendMessage("Sorry for moving you, but you logged into a forbidden area.");
		} else if (inPcGame()) {
			for (j = 0; j < NPCHandler.npcs.length; j++) {
				if (NPCHandler.npcs[j] != null) {
					if (NPCHandler.npcs[j].npcType == 6142) {
						if (NPCHandler.npcs[j].isDead) {
							getPA().sendFrame126("Dead", 21111);
						} else
							getPA().sendFrame126(
									"" + NPCHandler.npcs[j].HP + "", 21111);
					}
					if (NPCHandler.npcs[j].npcType == 6143) {
						if (NPCHandler.npcs[j].isDead) {
							getPA().sendFrame126("Dead", 21112);
						} else
							getPA().sendFrame126(
									"" + NPCHandler.npcs[j].HP + "", 21112);
					}
					if (NPCHandler.npcs[j].npcType == 6144) {
						if (NPCHandler.npcs[j].isDead) {
							getPA().sendFrame126("Dead", 21113);
						} else
							getPA().sendFrame126(
									"" + NPCHandler.npcs[j].HP + "", 21113);
					}
					if (NPCHandler.npcs[j].npcType == 6145) {
						if (NPCHandler.npcs[j].isDead) {
							getPA().sendFrame126("Dead", 21114);
						} else
							getPA().sendFrame126(
									"" + NPCHandler.npcs[j].HP + "", 21114);
					}
				}
			}
			getPA().sendFrame126("0", 21115);
			if (pcDamage < 15) {
				getPA().sendFrame126("@red@" + pcDamage + "", 21116);
			} else {
				getPA().sendFrame126("@gre@" + pcDamage + "", 21116);
			}
			getPA().sendFrame126(
					"Time remaining: " + PestControl.gameTimer + "", 21117);
			getPA().walkableInterface(21100);
		} else if (inDuelArena()) {
			getPA().walkableInterface(201);
			if (duelStatus == 5) {
				getPA().showOption(3, 0, "Attack", 1);
			} else {
				getPA().showOption(3, 0, "Challenge", 1);
			}
		} else if (inCwGame || inPits) {
			getPA().showOption(3, 0, "Attack", 1);
		} else if (getPA().inPitsWait()) {
			getPA().showOption(3, 0, "Null", 1);
		} else if (!inCwWait) {
			getPA().sendFrame99(0);
			getPA().walkableInterface(-1);
			getPA().showOption(3, 0, "Null", 1);
		} else if (inPits) {
			getPA().showOption(3, 0, "Attack", 1);
		} else if (getPA().inPitsWait()) {
			getPA().showOption(3, 0, "Null", 1);
		}

		if (!hasMultiSign && inMulti()) {
			hasMultiSign = true;
			getPA().multiWay(1);
		}

		if (hasMultiSign && !inMulti()) {
			hasMultiSign = false;
			getPA().multiWay(-1);
		}

		if (skullTimer > 0) {
			skullTimer--;
			if (skullTimer == 1) {
				isSkulled = false;
				attackedPlayers.clear();
				headIconPk = -1;
				skullTimer = -1;
				getPA().requestUpdates();
			}
		}

		if (isDead && respawnTimer == -6) {
			getPA().applyDead();
		}

		if (specRestore > 0) {
			specRestore--;
		}

		if (respawnTimer == 7) {
			respawnTimer = -6;
			getPA().giveLife();
		} else if (respawnTimer == 12) {
			respawnTimer--;
			startAnimation(0x900);
			poisonDamage = -1;
		}

		if (respawnTimer > -6) {
			respawnTimer--;
		}
		if (freezeTimer > -6) {
			freezeTimer--;
			if (frozenBy > 0) {
				if (PlayerHandler.players[frozenBy] == null) {
					freezeTimer = -1;
					frozenBy = -1;
				} else if (!goodDistance(absX, absY,
						PlayerHandler.players[frozenBy].absX,
						PlayerHandler.players[frozenBy].absY, 20)) {
					freezeTimer = -1;
					frozenBy = -1;
				}
			}
		}

		if (hitDelay > 0) {
			hitDelay--;
		}

		if (teleTimer > 0) {
			teleTimer--;
			if (!isDead) {
				if (teleTimer == 1 && newLocation > 0) {
					teleTimer = 0;
					getPA().changeLocation();
				}
				if (teleTimer == 5) {
					teleTimer--;
					getPA().processTeleport();
				}
				if (teleTimer == 9 && teleGfx > 0) {
					teleTimer--;
					gfx100(teleGfx);
				}
			} else {
				teleTimer = 0;
			}
		}

		if (hitDelay == 1) {
			if (oldNpcIndex > 0) {
				getCombat().delayedHit(this, oldNpcIndex);
			}
			if (oldPlayerIndex > 0) {
				getCombat().playerDelayedHit(this, oldPlayerIndex);
			}
		}

		if (attackTimer > 0) {
			attackTimer--;
		}

		if (attackTimer == 1) {
			if (npcIndex > 0 && clickNpcType == 0) {
				getCombat().attackNpc(npcIndex);
			}
			if (playerIndex > 0) {
				getCombat().attackPlayer(playerIndex);
			}
		} else if (attackTimer <= 0 && (npcIndex > 0 || playerIndex > 0)) {
			if (npcIndex > 0) {
				attackTimer = 0;
				getCombat().attackNpc(npcIndex);
			} else if (playerIndex > 0) {
				attackTimer = 0;
				getCombat().attackPlayer(playerIndex);
			}
		}

	}
	@SuppressWarnings("all")
	public Map getStringMap() {
		return stringMap;
	}
	@SuppressWarnings("all")
	private String redOrGreen(boolean green) {
		return green ? "@gre@" : "@red@";
	}

	public void setCurrentTask(Future<?> task) {
		currentTask = task;
	}

	public Future<?> getCurrentTask() {
		return currentTask;
	}

	public synchronized Stream getInStream() {
		return inStream;
	}

	public synchronized int getPacketType() {
		return packetType;
	}

	public synchronized int getPacketSize() {
		return packetSize;
	}

	public synchronized Stream getOutStream() {
		return outStream;
	}

	public ItemAssistant getItems() {
		return itemAssistant;
	}

	public PlayerAssistant getPA() {
		return playerAssistant;
	}

	public PMLog PMLog() {
		return PMLog;
	}

	public Alching Alching() {
		return Alching;
	}

	public ClickItem ClickItem() {
		return ClickItem;
	}

	public Shopping Shopping() {
		return Shopping;
	}

	public PickupItem PickupItem() {
		return PickupItem;
	}

	public DroppingItem DroppingItem() {
		return DroppingItem;
	}

	public CommandsLog CommandsLog() {
		return CommandsLog;
	}

	public StakingLog StakingLog() {
		return StakingLog;
	}

	public DialogueHandler getDH() {
		return dialogueHandler;
	}

	public ShopAssistant getShops() {
		return shopAssistant;
	}

	public TradeAndDuel getTradeAndDuel() {
		return tradeAndDuel;
	}

	public CombatAssistant getCombat() {
		return combatAssistant;
	}

	public ActionHandler getActions() {
		return actionHandler;
	}

	public PlayerKilling getKill() {
		return playerKilling;
	}

	public Channel getSession() {
		return session;
	}

	public TradeLog getTradeLog() {
		return tradeLog;
	}

	public Bank getBank() {
		return bank;
	}

	public Potions getPotions() {
		return potions;
	}

	public PotionMixing getPotMixing() {
		return potionMixing;
	}

	public Food getFood() {
		return food;
	}

	public boolean appearanceUpdate() {
		if ((playerEquipment[playerHat] == -1)
				&& (playerEquipment[playerCape] == -1)
				&& (playerEquipment[playerAmulet] == -1)
				&& (playerEquipment[playerChest] == -1)
				&& (playerEquipment[playerShield] == -1)
				&& (playerEquipment[playerLegs] == -1)
				&& (playerEquipment[playerHands] == -1)
				&& (playerEquipment[playerFeet] == -1)
				&& (playerEquipment[playerWeapon] == -1)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isBusy = false;
	private boolean isBusyHP = false;
	public boolean isBusyFollow = false;

	public boolean checkBusy() {
		/*
		 * if (getCombat().isFighting()) { return true; }
		 */
		if (isBusy) {
			// actionAssistant.sendMessage("You are too busy to do that.");
		}
		return isBusy;
	}

	public boolean checkBusyHP() {
		return isBusyHP;
	}

	public boolean checkBusyFollow() {
		return isBusyFollow;
	}

	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}

	public boolean isBusy() {
		return isBusy;
	}

	public void setBusyFollow(boolean isBusyFollow) {
		this.isBusyFollow = isBusyFollow;
	}

	public void setBusyHP(boolean isBusyHP) {
		this.isBusyHP = isBusyHP;
	}

	public boolean isBusyHP() {
		return isBusyHP;
	}

	public boolean isBusyFollow() {
		return isBusyFollow;
	}

	public boolean canWalk = true;
	public boolean hasChosenIronMan;
	public boolean isChosing;

	public boolean canWalk() {
		return canWalk;
	}

	public void setCanWalk(boolean canWalk) {
		this.canWalk = canWalk;
	}

	public PlayerAssistant getPlayerAssistant() {
		return playerAssistant;
	}

	public SkillInterfaces getSI() {
		return skillInterfaces;
	}

	public Zulrah getZulrah() {
		return zulrah;
	}

	/**
	 * Skill Constructors
	 */
	public Slayer getSlayer() {
		return slayer;
	}

	public Hunter getHunter() {
		return hunter;
	}

	public Summoning Summoning() {
		return Summoning;
	}

	public Woodcutting getWoodcutting() {
		return woodcutting;
	}

	public Runecrafting getRunecrafting() {
		return runecrafting;
	}

	public Cooking getCooking() {
		return cooking;
	}

	public Agility getAgility() {
		return agility;
	}

	public Crafting getCrafting() {
		return crafting;
	}

	public Farming getFarming() {
		return farming;
	}

	public Thieving getThieving() {
		return thieving;
	}

	public Herblore getHerblore() {
		return herblore;
	}

	public String getConnectedFrom() {
		return connectedFrom;
	}

	public Pets getSummon() {
		return pets;
	}

	public Smithing getSmithing() {
		return smith;
	}

	public SmithingInterface getSmithingInt() {
		return smithInt;
	}

	public Firemaking getFiremaking() {
		return firemaking;
	}

	public Fletching getFletching() {
		return fletching;
	}

	public Prayer getPrayer() {
		return prayer;
	}

	/**
	 * End of Skill Constructors
	 */

	public void queueMessage(Packet arg1) {
		synchronized (queuedPackets) {
			queuedPackets.add(arg1);
		}
	}

	@Override
	public boolean processQueuedPackets() {
		synchronized (queuedPackets) {
			Packet p = null;
			while ((p = queuedPackets.poll()) != null) {
				inStream.currentOffset = 0;
				packetType = p.getOpcode();
				packetSize = p.getLength();
				inStream.buffer = p.getPayload().array();
				if (packetType > 0) {
					PacketHandler.processPacket(this, packetType, packetSize);
				}
			}
		}
		return true;
	}

	/*
	 * public void queueMessage(Packet arg1) { //synchronized(queuedPackets) {
	 * //if (arg1.getId() != 41) queuedPackets.add(arg1); //else
	 * //processPacket(arg1); //} }
	 * 
	 * public synchronized boolean processQueuedPackets() { Packet p = null;
	 * synchronized(queuedPackets) { p = queuedPackets.poll(); } if(p == null) {
	 * return false; } inStream.currentOffset = 0; packetType = p.getOpcode();
	 * packetSize = p.getLength(); inStream.buffer = p.getPayload().array();
	 * if(packetType > 0) { //sendMessage("PacketType: " + packetType);
	 * PacketHandler.processPacket(this, packetType, packetSize); }
	 * timeOutCounter = 0; return true; }
	 * 
	 * public synchronized boolean processPacket(Packet p) { synchronized (this)
	 * { if(p == null) { return false; } inStream.currentOffset = 0; packetType
	 * = p.getOpcode(); packetSize = p.getLength(); inStream.buffer =
	 * p.getPayload().array(); if(packetType > 0) { //sendMessage("PacketType: "
	 * + packetType); PacketHandler.processPacket(this, packetType, packetSize);
	 * } timeOutCounter = 0; return true; } }
	 */

	public void correctCoordinates() {
		if (inPcGame()) {
			getPA().movePlayer(2657, 2639, 0);
		}
		if (inFightCaves()) {
			getPA().movePlayer(absX, absY, playerId * 4);
			sendMessage("Your wave will start in 10 seconds.");
			CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					Server.fightCaves
							.spawnNextWave((Client) PlayerHandler.players[playerId]);
					container.stop();
				}

				@Override
				public void stop() {

				}
			}, 20);

		}

	}

	public Barrows getBarrows() {
		return barrows;
	}

}