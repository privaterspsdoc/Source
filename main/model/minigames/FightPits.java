package main.model.minigames;

import main.Server;
import main.event.Event;
import main.event.EventContainer;
import main.event.EventManager;
import main.model.players.Client;
import main.util.Misc;

/**
 * @author Sanity
 */

public class FightPits {

	public int[] playerInPits = new int[200];
	
	private int GAME_TIMER = 540;
	private int GAME_START_TIMER = 60;
	private int gameStartTimer = 60;
	public int playersRemaining = 0;
	public static boolean gamestarted = false;
	public boolean processStarted = false;
	
	public String pitsChampion = "Nobody";
	
	public void process() {
		EventManager.getSingleton().addEvent(new Event() {
			public void execute(EventContainer e) {	
				if (processStarted) {
					if (gameStartTimer > 0) {
						gameStartTimer--;
						updateWaitRoom();
					} 
					if (gameStartTimer == 0 && playersRemaining <= 1) {
						startGame();
					}
					if (playersRemaining == 1) {
						endPitsGame(getLastPlayerName());
					}
				}
			}
		}, 800);
	}
	
	public String getLastPlayerName() {
		for (int j = 0; j < playerInPits.length; j++) {
			if (playerInPits[j] > 0)
				return Server.playerHandler.players[playerInPits[j]].playerName;
		}	
		return "Nobody";
	}
	
	public void updateWaitRoom() {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c = (Client) Server.playerHandler.players[j];
				if (c.getPA().inPitsWait() || c.inPits) {
					if (gamestarted == false) {
						c.getPA().sendFrame126("Next Game Begins In : " +gameStartTimer, 2806);
						c.getPA().sendFrame126("Champion: JalYt-Ket-" +Misc.capitalize(pitsChampion), 2805);
						c.getPA().sendFrame36(560, 1);
						c.getPA().walkableInterface(2804);
					}
					if (gamestarted == true) {
						c.getPA().sendFrame126("Foes Remaining: " +playersRemaining, 2806);
						c.getPA().sendFrame126("Champion: JalYt-Ket-" +Misc.capitalize(pitsChampion), 2805);
						c.getPA().sendFrame36(560, 1);
						c.getPA().walkableInterface(2804);
				}
			}
		}
		}
	}
	
	public void startGame() {
		if (getWaitAmount() < 2) {
			gameStartTimer = GAME_START_TIMER;
			//System.out.println("Unable to start fight pits game due to lack of players.");
			return;
		}	
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null )  {
					Client c = (Client)Server.playerHandler.players[j];
					if (c.getPA().inPitsWait())
						addToPitsGame(j);
			}	
		}
		gamestarted = true;
		System.out.println("Fight Pits game started.");
		gameStartTimer = GAME_START_TIMER + GAME_TIMER;
	}
	
	public int getWaitAmount() {
		int count = 0;
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null )  {
					Client c = (Client)Server.playerHandler.players[j];
					if (c.getPA().inPitsWait())
						count++;
			}	
		}
		return count;
	}
	
	public void removePlayerFromPits(int playerId) {
		for (int j = 0; j < playerInPits.length; j++) {
			if (playerInPits[j] == playerId) {
				Client c = (Client)Server.playerHandler.players[playerInPits[j]];
				c.getPA().movePlayer(2399, 5173, 0);
				playerInPits[j] = -1;
				playersRemaining--;
				c.inPits = false;
				break;
			}
		}
	}
	
	public void endPitsGame(String champion) {
		boolean giveReward = false;
		if (playersRemaining == 1)
			giveReward = true;
		for (int j = 0; j < playerInPits.length; j++) {
			if (playerInPits[j] < 0)
				continue;
			if (Server.playerHandler.players[playerInPits[j]] == null)
				continue;
			Client c = (Client)Server.playerHandler.players[playerInPits[j]];
			c.getPA().movePlayer(2399, 5173, 0);
			c.inPits = false;
		}
		gamestarted = false;
		playerInPits = new int[200];	
		pitsChampion = champion;
		playersRemaining = 0;
		pitsSlot = 0;
		gameStartTimer = GAME_START_TIMER;
		System.out.println("Fight Pits game ended.");
	}
	
	private int pitsSlot = 0;
	public void addToPitsGame(int playerId) {
		if (Server.playerHandler.players[playerId] == null)
			return;
		playersRemaining++;
		Client c = (Client)Server.playerHandler.players[playerId];
		playerInPits[pitsSlot++] = playerId;
		c.getPA().movePlayer(2392 + Misc.random(12), 5139 + Misc.random(25), 0);
		c.inPits = true;
		gamestarted = true;	
	}
}