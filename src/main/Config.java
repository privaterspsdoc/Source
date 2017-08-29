package main;

import main.util.Misc;

/**
 * The Configuration File Of The Server
 * 
 * @author Sanity Revision by Shawn Notes by Shawn
 */
public class Config {

	/**
	 * Enable or disable server debugging.
	 */
	public static final boolean SERVER_DEBUG = true;

	/**
	 * JSON Drop System
	 */
	public static final int DROP_RATE = 1;
	
	/**
	 * Enable/Disable Staking
	 */
	public static boolean stakeDisabled = false;
	
	/**
	 * Does server sends packet?
	 */
	 public static boolean sendServerPackets = false;
	 
	/**
	 * Your server name.
	 */
	public static final String SERVER_NAME = "Zaros";

	/**
	 * The welcome message displayed once logged in the server.
	 */
	public static final String WELCOME_MESSAGE = "Welcome to Zaros.";

	/**
	 * A URL to your server forums. Not necessary needed.
	 */
	public static final String FORUMS = "http://zaros-rsps.info/";

	/**
	 * The client version you are using. Not necessary needed.
	 */
	public static final int CLIENT_VERSION = 317;

	/**
	 * The delay it takes to type and or send a message.
	 */
	public static int MESSAGE_DELAY = 6000;

	/**
	 * Lowered to item.cfg limit, can be changed
	 */
	public static final int ITEM_LIMIT = 16000;

	/**
	 * An integer needed for the above code.
	 */
	public static final int MAXITEM_AMOUNT = Integer.MAX_VALUE;

	/**
	 * The size of a players bank.
	 */
	public static final int BANK_SIZE = 352;

	/**
	 * The max amount of players until your server is full.
	 */
	public static final int MAX_PLAYERS = 128;

	/**
	 * The delay of logging in from connections. Do not change this.
	 */
	public static final int CONNECTION_DELAY = 100;

	/**
	 * How many IP addresses can connect from the same network until the server
	 * rejects another.
	 */
	public static final int IPS_ALLOWED = 1000;

	/**
	 * Change to true if you want to stop the world --8. Can cause screen
	 * freezes on SilabSoft Clients. Change is not needed.
	 */
	public static final boolean WORLD_LIST_FIX = false;

	/**
	 * Items that can not be sold in any stores.
	 */
	public static final int[] ITEM_SELLABLE = { 1050, 1051, 1044, 1045, 1046, 1047, 1048, 1049, 1052, 3842, 3844, 3840, 8844, 8845,
			8846, 8847, 8848, 8849, 8850, 10551, 10548, 6570, 7462, 7461, 7460, 7459,
			7458, 7457, 7456, 7455, 7454, 9748, 9754, 9751, 9769, 9757, 9760,
			9763, 9802, 9808, 9784, 9799, 9805, 9781, 9796, 9793, 9775, 9772,
			9778, 9787, 9811, 9766, 9749, 9755, 9752, 9770, 9758, 9761, 9764,
			9803, 9809, 9785, 9800, 9806, 9782, 9797, 9794, 9776, 9773, 9779,
			9788, 9812, 9767, 9747, 9753, 9750, 9768, 9756, 9759, 9762, 9801,
			9807, 9783, 9798, 9804, 9780, 9795, 9792, 9774, 9771, 9777, 9786,
			9810, 9765, 8839, 8840, 8842, 11663, 11664, 11665, 10499, 995, 12158, 12159, 12163, 12160, 12653, 12694, 12650, 12649, 12651, 12652 , 12643, 12644, 12645, 12655  };

	/**
	 * Items that can not be traded or staked.
	 */
	public static final int[] ITEM_TRADEABLE = { 3842, 3844, 3840, 8844, 8845,
			8846, 8847, 8848, 8849, 8850, 10551, 6570, 7462, 7461, 7460, 7459,
			7458, 7457, 7456, 7455, 7454, 9748, 9754, 9751, 9769, 9757, 9760,
			9763, 9802, 9808, 9784, 9799, 9805, 9781, 9796, 9793, 9775, 9772,
			9778, 9787, 9811, 9766, 9749, 9755, 9752, 9770, 9758, 9761, 9764,
			9803, 9809, 9785, 9800, 9806, 9782, 9797, 9794, 9776, 9773, 9779,
			9788, 9812, 9767, 9747, 9753, 9750, 9768, 9756, 9759, 9762, 9801,
			9807, 9783, 9798, 9804, 9780, 9795, 9792, 9774, 9771, 9777, 9786,
			9810, 9765, 8839, 8840, 8842, 11663, 11664, 11665, 10499, 12158, 12159, 12163, 12160, 12162, 12225, 12653, 12694, 12650, 12649, 12651, 12652 , 12643, 12644, 12645, 12655, 19010, 19009, 12954, 15571, 15572, 15573, 15574 };

	/**
	 * Items that can not be dropped.
	 */
	public static final int[] UNDROPPABLE_ITEMS = {};

	/**
	 * Items that are listed as fun weapons for dueling.
	 */
	public static final int[] FUN_WEAPONS = { 2460, 2461, 2462, 2463, 2464,
			2465, 2466, 2467, 2468, 2469, 2470, 2471, 2471, 2473, 2474, 2475,
			2476, 2477 };

	/**
	 * If administrators can trade or not.
	 */
	public static final boolean ADMIN_CAN_TRADE = false;

	/**
	 * If administrators can sell items or not.
	 */
	public static final boolean ADMIN_CAN_SELL_ITEMS = false;

	/**
	 * If administrators can drop items or not.
	 */
	public static final boolean ADMIN_DROP_ITEMS = false;

	/**
	 * The starting location of your server.
	 */
	public static final int START_LOCATION_X = 3087;
	public static final int START_LOCATION_Y = 3506;

	/**
	 * The re-spawn point of when someone dies.
	 */
	public static final int RESPAWN_X = 3101;
	public static final int RESPAWN_Y = 3492;

	/**
	 * The re-spawn point of when a duel ends.
	 */
	public static final int DUELING_RESPAWN_X = 3362;
	public static final int DUELING_RESPAWN_Y = 3263;

	/**
	 * The point in where you spawn in a duel. Do not change this.
	 */
	public static final int RANDOM_DUELING_RESPAWN = 0;

	/**
	 * The level in which you can not teleport in the wild, and higher.
	 */
	public static final int NO_TELEPORT_WILD_LEVEL = 20;

	/**
	 * The timer in which you are skulled goes away. Seconds x2 Ex. 60x2=120
	 * Skull timer would be 1 minute.
	 */
	public static final int SKULL_TIMER = 1200;

	/**
	 * How long the teleport block effect takes.
	 */
	public static final int TELEBLOCK_DELAY = 20000;

	/**
	 * Single and multi player killing zones.
	 */
	public static final boolean SINGLE_AND_MULTI_ZONES = true;

	/**
	 * Wilderness levels and combat level differences. Used when attacking
	 * players.
	 */
	public static final boolean COMBAT_LEVEL_DIFFERENCE = true;

	/**
	 * Combat level requirements needed to wield items.
	 */
	public static final boolean itemRequirements = true;

	/**
	 * Combat experience rates.
	 */
	public static final int MELEE_EXP_RATE = 750;
	public static final int RANGE_EXP_RATE = 750;
	public static final int MAGIC_EXP_RATE = 750;

	/**
	 * Special server experience bonus rates. (Double experience weekend etc)
	 */
	public static final int SERVER_EXP_BONUS = 1;

	/**
	 * How fast the special attack bar refills.
	 */
	public static final int INCREASE_SPECIAL_AMOUNT = 17500;

	/**
	 * If you need more than one prayer point to use prayer.
	 */
	public static final boolean PRAYER_POINTS_REQUIRED = true;

	/**
	 * If you need a certain prayer level to use a certain prayer.
	 */
	public static final boolean PRAYER_LEVEL_REQUIRED = true;

	/**
	 * If you need a certain magic level to use a certain spell.
	 */
	public static final boolean MAGIC_LEVEL_REQUIRED = true;

	/**
	 * How long the god charge spell lasts.
	 */
	public static final int GOD_SPELL_CHARGE = 300000;

	/**
	 * If you need runes to use magic spells.
	 */
	public static final boolean RUNES_REQUIRED = true;

	/**
	 * If you need correct arrows to use with bows.
	 */
	public static final boolean CORRECT_ARROWS = true;

	/**
	 * If the crystal bow degrades.
	 */
	public static final boolean CRYSTAL_BOW_DEGRADES = false;

	/**
	 * How often the server saves data.
	 */
	public static final int SAVE_TIMER = 60; // Saves every one minute.

	/**
	 * How far NPCs can walk.
	 */
	public static final int NPC_RANDOM_WALK_DISTANCE = 5; // 5x5 square, NPCs
															// would be able to
															// walk 25 squares
															// around.

	/**
	 * How far NPCs can follow you when attacked.
	 */
	public static final int NPC_FOLLOW_DISTANCE = 10; // 10 squares

	/**
	 * NPCs that act as if they are dead. (For salve amulet, etc)
	 */
	public static final int[] UNDEAD_NPCS = { 90, 91, 92, 93, 94, 103, 104, 73,
			74, 75, 76, 77 };

	/**
	 * What NPCs drop when dead.
	 */
	
	


	/**
	 * Glory locations.
	 */
	public static final int EDGEVILLE_X = 3087;
	public static final int EDGEVILLE_Y = 3500;
	public static final String EDGEVILLE = "";
	public static final int AL_KHARID_X = 3293;
	public static final int AL_KHARID_Y = 3174;
	public static final String AL_KHARID = "";
	public static final int KARAMJA_X = 2948;
	public static final int KARAMJA_Y = 3147;
	public static final String KARAMJA = "";
	public static final int MAGEBANK_X = 2538;
	public static final int MAGEBANK_Y = 4716;
	public static final String MAGEBANK = "";

	/**
	 * Teleport spells.
	 **/
	/*
	 * Modern spells
	 */
	public static final int VARROCK_X = 3210;
	public static final int VARROCK_Y = 3424;
	public static final String VARROCK = "";

	public static final int LUMBY_X = 3222;
	public static final int LUMBY_Y = 3218;
	public static final String LUMBY = "";

	public static final int FALADOR_X = 2964;
	public static final int FALADOR_Y = 3378;
	public static final String FALADOR = "";

	public static final int CAMELOT_X = 2757;
	public static final int CAMELOT_Y = 3477;
	public static final String CAMELOT = "";

	public static final int ARDOUGNE_X = 2662;
	public static final int ARDOUGNE_Y = 3305;
	public static final String ARDOUGNE = "";

	public static final int WATCHTOWER_X = 3087;
	public static final int WATCHTOWER_Y = 3500;
	public static final String WATCHTOWER = "";

	public static final int TROLLHEIM_X = 3243;
	public static final int TROLLHEIM_Y = 3513;
	public static final String TROLLHEIM = "";

	/*
	 * Ancient spells
	 */
	public static final int PADDEWWA_X = 3098;
	public static final int PADDEWWA_Y = 9884;

	public static final int SENNTISTEN_X = 3322;
	public static final int SENNTISTEN_Y = 3336;

	public static final int KHARYRLL_X = 3492;
	public static final int KHARYRLL_Y = 3471;

	public static final int LASSAR_X = 3006;
	public static final int LASSAR_Y = 3471;

	public static final int DAREEYAK_X = 3161;
	public static final int DAREEYAK_Y = 3671;

	public static final int CARRALLANGAR_X = 3156;
	public static final int CARRALLANGAR_Y = 3666;

	public static final int ANNAKARL_X = 3288;
	public static final int ANNAKARL_Y = 3886;

	public static final int GHORROCK_X = 2977;
	public static final int GHORROCK_Y = 3873;
	//

	/**
	 * Timeout time.
	 */
	public static final int TIMEOUT = 20;

	/**
	 * Cycle time.
	 */
	public static final int CYCLE_TIME = 600;

	/**
	 * Buffer size.
	 */
	public static final int BUFFER_SIZE = 512;

	/**
	 * Slayer Variables.
	 */
	public static final int[][] SLAYER_TASKS = { { 1, 87, 90, 4, 5 }, // Low
																		// tasks
			{ 6, 7, 8, 9, 10 }, // Medium tasks
			{ 11, 12, 13, 14, 15 }, // High tasks
			{ 1, 1, 15, 20, 25 }, // Low requirements
			{ 30, 35, 40, 45, 50 }, // Medium requirements
			{ 60, 75, 80, 85, 90 } }; // High requirements

	/**
	 * Skill experience multipliers.
	 */
	public static final int WOODCUTTING_EXPERIENCE = 35;
	public static final int MINING_EXPERIENCE = 40;
	public static final int SMITHING_EXPERIENCE = 40;
	public static final int FARMING_EXPERIENCE = 40;
	public static final int FIREMAKING_EXPERIENCE = 40;
	public static final int HERBLORE_EXPERIENCE = 30;
	public static final int FISHING_EXPERIENCE = 55;
	public static final int AGILITY_EXPERIENCE = 120;
	public static final int PRAYER_EXPERIENCE = 85;
	public static final int RUNECRAFTING_EXPERIENCE = 40;
	public static final int HUNTER_EXPERIENCE = 35;
	public static final int CRAFTING_EXPERIENCE = 2;
	public static final int THIEVING_EXPERIENCE = 1;
	public static final int SLAYER_EXPERIENCE = 20;
	public static final int COOKING_EXPERIENCE = 35;
	public static final int FLETCHING_EXPERIENCE = 40;
	public static final int SUMMONING_EXPERIENCE = 40;
}
// 18/4/2015
// added cut gems, lowered onyx rate for tzhaars

// 19/4/2015
// Added more dagannoth boss drops