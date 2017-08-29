package main;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.text.DecimalFormat;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.util.HashedWheelTimer;

import main.model.players.highscores.*;
import main.clip.region.ObjectDef;
import main.clip.region.Region;
import main.event.CycleEventHandler;
import main.event.Task;
import main.event.TaskScheduler;
import main.model.minigames.FightCaves;
import main.model.minigames.FightPits;
import main.model.minigames.PestControl;
import main.model.npcs.NPCHandler;
import main.model.npcs.dropsystem.NPCDrops;
import main.model.objects.Doors;
import main.model.objects.DoubleDoors;
import main.model.players.PlayerHandler;
import main.model.players.clan.ClanManager;
import main.net.Connection;
import main.net.PipelineFactory;
import main.util.log.Logger;
import main.world.ItemHandler;
import main.world.ObjectHandler;
import main.world.ObjectManager;
import main.world.PlayerManager;
import main.world.ShopHandler;
import main.world.StillGraphicsManager;
import main.world.WalkingCheck;

/**
 * The main class needed to start the server.
 */
public class Server {
	
	/**
	 * ClanChat
	 * Added by Valiant
	 */
	public static ClanManager clanManager = new ClanManager();

	/**
	 * Calls to manage the players on the server.
	 */
	public static PlayerManager playerManager = null;
	private static StillGraphicsManager stillGraphicsManager = null;

	/**
	 * Sleep mode of the server.
	 */
	public static boolean sleeping;

	/**
	 * Calls the rate in which an event cycles.
	 */
	public static final int cycleRate;

	/**
	 * Server updating.
	 */
	public static boolean UpdateServer = false;

	/**
	 * Calls in which the server was last saved.
	 */
	public static long lastMassSave = System.currentTimeMillis();

	/**
	 * Calls the usage of CycledEvents.
	 */
	private static long cycleTime, cycles, totalCycleTime, sleepTime;

	/**
	 * Used for debugging the server.
	 */
	private static DecimalFormat debugPercentFormat;

	/**
	 * Forced shutdowns.
	 */
	public static boolean shutdownServer = false;
	public static boolean shutdownClientHandler;

	public static boolean canLoadObjects = false;
	
	/**
	 * Used to identify the server port.
	 */
	public static int serverlistenerPort;

	/**
	 * Calls the usage of player items.
	 */
	public static ItemHandler itemHandler = new ItemHandler();

	/**
	 * Handles logged in players.
	 */
	public static PlayerHandler playerHandler = new PlayerHandler();

	/**
	 * Handles global NPCs.
	 */
	public static NPCHandler npcHandler = new NPCHandler();

	/**
	 * Handles global shops.
	 */
	public static ShopHandler shopHandler = new ShopHandler();

	/**
	 * Handles global objects.
	 */
	public static ObjectHandler objectHandler = new ObjectHandler();
	public static ObjectManager objectManager = new ObjectManager();

	/**
	 * Handles the fightpits minigame.
	 */
	public static FightPits fightPits = new FightPits();

	/**
	 * Handles the pestcontrol minigame.
	 */
	public static PestControl pestControl = new PestControl();

	/**
	 * Handles the fightcaves minigames.
	 */
	public static FightCaves fightCaves = new FightCaves();

	/**
	 * Handles the task scheduler.
	 */
	private static final TaskScheduler scheduler = new TaskScheduler();

	/**
	 * Gets the task scheduler.
	 */
	public static TaskScheduler getTaskScheduler() {
		return scheduler;
	}

	static {
		if (!Config.SERVER_DEBUG) {
			serverlistenerPort = 43594;
		} else {
			serverlistenerPort = 43594;
		}
		cycleRate = 600;
		shutdownServer = false;
		sleepTime = 0;
		debugPercentFormat = new DecimalFormat("0.0#%");
	}

	/**
	 * Starts the server.
	 */
	public static void main(java.lang.String args[]) throws NullPointerException, IOException {
		long startTime = System.currentTimeMillis();
		System.setOut(new Logger(System.out));
		System.setErr(new Logger(System.err));
		System.out.println("Starting the server...");
		ObjectDef.loadConfig();
		System.out.println("Loaded object data...");
		Region.load();
		System.out.println("Loaded region data...");
		WalkingCheck.load();
		System.out.println("Loaded clipping data...");
		Doors.getSingleton().load();
		DoubleDoors.getSingleton().load();
		System.out.println("Loaded door data...");
		NPCDrops.init();
		System.out.println("Loaded JSON NPC drops...");
		bind();
		System.out.println("Bound to port 43594...");
		playerManager = PlayerManager.getSingleton();
		playerManager.setupRegionPlayers();
		stillGraphicsManager = new StillGraphicsManager();
		Connection.initialize();
		System.out.println("Connected to network...");
		/*
		Highscores.process();
		if (Highscores.connected) {
		System.out.println("Connected to SQL database...");
		} else {
		System.out.println("Failed to connect to SQL database!");
		}
		*/
		//pestControl.process();
		long endTime = System.currentTimeMillis();
		long elapsed = endTime - startTime;
		System.out.println("Zaros RSPS is online. (Took " + elapsed + " milliseconds.)");

		/**
		 * Main server tick.
		 */
		scheduler.schedule(new Task() {
			@Override
			protected void execute() {
				itemHandler.process();
				playerHandler.process();
				npcHandler.process();
				shopHandler.process();
				CycleEventHandler.getSingleton().process();
				objectManager.process();
				fightPits.process();
				pestControl.process();
			}
		});

	}

	/**
	 * Logging execution.
	 */
	public static boolean playerExecuted = false;

	/**
	 * Gets the sleep mode timer and puts the server into sleep mode.
	 */
	public static long getSleepTimer() {
		return sleepTime;
	}

	/**
	 * Gets the Graphics manager.
	 */
	public static StillGraphicsManager getStillGraphicsManager() {
		return stillGraphicsManager;
	}

	/**
	 * Gets the Player manager.
	 */
	public static PlayerManager getPlayerManager() {
		return playerManager;
	}

	/**
	 * Gets the Object manager.
	 */
	public static ObjectManager getObjectManager() {
		return objectManager;
	}

	/**
	 * Java connection. Ports.
	 */
	private static void bind() {
		ServerBootstrap serverBootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool()));
						serverBootstrap.setPipelineFactory(new PipelineFactory(
						new HashedWheelTimer()));
						serverBootstrap.bind(new InetSocketAddress(serverlistenerPort));
	}
	
	public static TaskScheduler getScheduler() {
		return scheduler;
	}

}
