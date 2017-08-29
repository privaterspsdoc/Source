package main.model.players.clan;

import java.util.Collections;
import java.util.LinkedList;

import main.Config;
import main.Server;
import main.model.players.Client;
import main.model.players.Player;
import main.model.players.PlayerHandler;
import main.model.players.PlayerSave;
import main.util.Misc;
/**
 * This class stores all information about the clan. This includes active
 * members, banned members, ranked members and their ranks, clan title, and clan
 * founder. All clan joining, leaving, and moderation/setup is also handled in
 * this class.
 * 
 * @author Galkon
 * 
 */
public class Clan {

	/**
	 * Adds a member to the clan.
	 * 
	 * @param player
	 */
	public void addMember(Client player) {
		if (isBanned(player.playerName)) {
			player.sendMessage("You are currently banned from this clan chat.");
			return;
		}
		if (whoCanJoin > Rank.ANYONE && !isFounder(player.playerName)) {
			if (getRank(player.playerName) < whoCanJoin) {
				player.sendMessage("You do not have the required rank to join this clan chat.");
				return;
			}
		}
		player.clan = this;
		player.lastClanChat = getFounder();
		activeMembers.add(player.playerName);
		player.getPA().sendString("Leave Chat", 37135);
		player.getPA().sendString("Talking in: <col=FFFF64>" + getTitle() + "</col>", 37139);
		player.getPA().sendString(
		"Owner: <col=FFFFFF>" + Misc.formatPlayerName(getFounder()) + "</col>", 37140);
		//player.sendMessage("Now talking in clan chat <col=FFFF64><shad=0>" + getTitle() + "</shad></col>.");
		updateMembers();
	}

	/**
	 * Removes the player from the clan.
	 * 
	 * @param player
	 */
	public void removeMember(Client player) {
		for (int index = 0; index < activeMembers.size(); index++) {
			if (activeMembers.get(index).equalsIgnoreCase(player.playerName)) {
				player.clan = null;
				resetInterface(player);
				player.getPA().sendString("Join Chat", 37135);
				activeMembers.remove(index);
			}
		}
		updateMembers();
	}

	/**
	 * Removes the player from the clan.
	 * 
	 * @param player
	 */
	public void removeMember(String name) {
		for (int index = 0; index < activeMembers.size(); index++) {
			if (activeMembers.get(index).equalsIgnoreCase(name)) {
				Client player = (Client)PlayerHandler.getPlayer(name);
				if (player != null) {
					player.clan = null;
					resetInterface(player);
					activeMembers.remove(index);
				}
			}
		}
		updateMembers();
	}

	/**
	 * Updates the members on the interface for the player.
	 * 
	 * @param player
	 */
	public void updateInterface(Client paramClient) {
		paramClient.getPA().sendFrame126(
				"Talking in: <col=FFFF64>" + getTitle() + "</col>", 37139);
		paramClient.getPA().sendFrame126(
				"Owner: <col=FFFFFF>" + Misc.formatPlayerName(getFounder())
						+ "</col>", 37140);
		Collections.sort(this.activeMembers);
		for (int i = 0; i < 100; i++)
			if (i < this.activeMembers.size())
				paramClient.getPA().sendFrame126(
				"<clan=" + getRank(this.activeMembers.get(i)) + ">" +
				Misc.formatPlayerName(this.activeMembers.get(i)), 37144 + i);
			else
				paramClient.getPA().sendFrame126("", 37144 + i);
				
	}

	/**
	 * Updates the interface for all members.
	 */
	public void updateMembers() {
		for (int index = 0; index < Config.MAX_PLAYERS; index++) {
			Client player = (Client) PlayerHandler.players[index];
			if (player != null && activeMembers != null) {
				if (activeMembers.contains(player.playerName)) {
					updateInterface(player);
				}
			}
		}
	}

	/**
	 * Resets the clan interface.
	 * 
	 * @param player
	 */
	public void resetInterface(Client paramClient) {
		paramClient.getPA().sendFrame126("Join Chat", 37135);
		paramClient.getPA().sendFrame126("Talking in: Not in chat", 37139);
		paramClient.getPA().sendFrame126("Owner: None", 37140);
		for (int i = 0; i < 100; i++)
			paramClient.getPA().sendFrame126("", 37144 + i);
	}

	/**
	 * Sends a message to the clan.
	 * 
	 * @param player
	 * @param message
	 */
	public void sendChat(Client paramClient, String paramString) {
		if (getRank(paramClient.playerName) < this.whoCanTalk) {
			paramClient.sendMessage("You do not have the required rank to talk on this channel.");
			return;
		}
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c = (Client)PlayerHandler.players[j];
				if ((c != null) && (this.activeMembers.contains(c.playerName)))
					c.sendMessage("@bla@[<col=A32900>" + getTitle()
							+ "@bla@] @bla@"
							+ Misc.optimizeText(paramClient.playerName)
							+ ": <col=A32900>" + paramString + "");
			}
		}
	}

	/**
	 * Sends a message to the clan.
	 * 
	 * @param player
	 * @param message
	 */
	public void sendMessage(String message) {
		for (int index = 0; index < 512; index++) {
			Client p = (Client)PlayerHandler.players[index];
			if (p != null) {
				if (activeMembers.contains(p.playerName)) {
					p.sendMessage(message);
				}
			}
		}
	}

	/**
	 * Sets the rank for the specified name.
	 * 
	 * @param name
	 * @param rank
	 */
	public void setRank(String name, int rank) {
		if (rankedMembers.contains(name)) {
			ranks.set(rankedMembers.indexOf(name), rank);
		} else {
			rankedMembers.add(name);
			ranks.add(rank);
		}
		save();
	}

	/**
	 * Demotes the specified name.
	 * 
	 * @param name
	 */
	public void demote(String name) {
		if (!rankedMembers.contains(name)) {
			return;
		}
		int index = rankedMembers.indexOf(name);
		rankedMembers.remove(index);
		ranks.remove(index);
		save();
	}

	/**
	 * Gets the rank of the specified name.
	 * 
	 * @param name
	 * @return
	 */
	public int getRank(String name) {
		name = Misc.formatPlayerName(name);
		if (rankedMembers.contains(name)) {
			return ranks.get(rankedMembers.indexOf(name));
		}
		if (isFounder(name)) {
			return Rank.OWNER;
		}
		if (PlayerSave.isFriend(getFounder(), name)) {
			return Rank.FRIEND;
		}
		return -1;
	}

	/**
	 * Can they kick?
	 * 
	 * @param name
	 * @return
	 */
	public boolean canKick(String name) {
		if (isFounder(name)) {
			return true;
		}
		if (getRank(name) >= whoCanKick) {
			return true;
		}
		return false;
	}

	/**
	 * Can they ban?
	 * 
	 * @param name
	 * @return
	 */
	public boolean canBan(String name) {
		if (isFounder(name)) {
			return true;
		}
		if (getRank(name) >= whoCanBan) {
			return true;
		}
		return false;
	}

	/**
	 * Returns whether or not the specified name is the founder.
	 * 
	 * @param name
	 * @return
	 */
	public boolean isFounder(String name) {
		if (getFounder().equalsIgnoreCase(name)) {
			return true;
		}
		return false;
	}

	/**
	 * Returns whether or not the specified name is a ranked user.
	 * 
	 * @param name
	 * @return
	 */
	public boolean isRanked(String name) {
		name = Misc.formatPlayerName(name);
		if (rankedMembers.contains(name)) {
			return true;
		}
		return false;
	}

	/**
	 * Returns whether or not the specified name is banned.
	 * 
	 * @param name
	 * @return
	 */
	public boolean isBanned(String name) {
		name = Misc.formatPlayerName(name);
		if (bannedMembers.contains(name)) {
			return true;
		}
		return false;
	}

	/**
	 * Kicks the name from the clan chat.
	 * 
	 * @param name
	 */
	public void kickMember(String name) {
		if (!activeMembers.contains(name)) {
			return;
		}
		if (name.equalsIgnoreCase(getFounder())) {
			return;
		}
		removeMember(name);
		Client player = (Client)PlayerHandler.getPlayer(name);
		if (player != null) {
			player.sendMessage("You have been kicked from the clan chat.");
		}
		sendMessage("A player has been kicked from the channel!");
	}

	/**
	 * Bans the name from entering the clan chat.
	 * 
	 * @param name
	 */
	public void banMember(String name) {
		name = Misc.formatPlayerName(name);
		if (bannedMembers.contains(name)) {
			return;
		}
		if (name.equalsIgnoreCase(getFounder())) {
			return;
		}
		if (isRanked(name)) {
			return;
		}
		removeMember(name);
		bannedMembers.add(name);
		save();
		Client player = (Client)PlayerHandler.getPlayer(name);
		if (player != null) {
			player.sendMessage("You have been kicked from the clan chat.");
		}
	}

	/**
	 * Unbans the name from the clan chat.
	 * 
	 * @param name
	 */
	public void unbanMember(String name) {
		name = Misc.formatPlayerName(name);
		if (bannedMembers.contains(name)) {
			bannedMembers.remove(name);
			save();
		}
	}

	/**
	 * Saves the clan.
	 */
	public void save() {
		Server.clanManager.save(this);
		updateMembers();
	}

	/**
	 * Deletes the clan.
	 */
	public void delete() {
		for (String name : activeMembers) {
			removeMember(name);
			Client player = (Client)PlayerHandler.getPlayer(name);
			player.sendMessage("The clan you were in has been deleted.");
		}
		Server.clanManager.delete(this);
	}

	/**
	 * Creates a new clan for the specified player.
	 * 
	 * @param player
	 */
	public Clan(Player player) {
		setTitle("Not Set");
		setFounder(player.playerName.toLowerCase());
	}

	/**
	 * Creates a new clan for the specified title and founder.
	 * 
	 * @param title
	 * @param founder
	 */
	public Clan(String title, String founder) {
		setTitle(title);
		setFounder(founder);
	}

	/**
	 * Gets the founder of the clan.
	 * 
	 * @return
	 */
	public String getFounder() {
		return founder;
	}

	/**
	 * Sets the founder.
	 * 
	 * @param founder
	 */
	public void setFounder(String founder) {
		this.founder = founder;
	}

	/**
	 * Gets the title of the clan.
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 * 
	 * @param title
	 * @return
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * The title of the clan.
	 */
	public String title;

	/**
	 * The founder of the clan.
	 */
	public String founder;

	/**
	 * The active clan members.
	 */
	public LinkedList<String> activeMembers = new LinkedList<String>();

	/**
	 * The banned members.
	 */
	public LinkedList<String> bannedMembers = new LinkedList<String>();

	/**
	 * The ranked clan members.
	 */
	public LinkedList<String> rankedMembers = new LinkedList<String>();

	/**
	 * The clan member ranks.
	 */
	public LinkedList<Integer> ranks = new LinkedList<Integer>();

	/**
	 * The clan ranks.
	 * 
	 * @author Galkon
	 * 
	 */
	public static class Rank {
		public final static int ANYONE = -1;
		public final static int FRIEND = 0;
		public final static int RECRUIT = 1;
		public final static int CORPORAL = 2;
		public final static int SERGEANT = 3;
		public final static int LIEUTENANT = 4;
		public final static int CAPTAIN = 5;
		public final static int GENERAL = 6;
		public final static int OWNER = 7;
	}

	/**
	 * Gets the rank title as a string.
	 * 
	 * @param rank
	 * @return
	 */
	public String getRankTitle(int rank) {
		switch (rank) {
		case -1:
			return "Anyone";
		case 0:
			return "Friend";
		case 1:
			return "Recruit";
		case 2:
			return "Corporal";
		case 3:
			return "Sergeant";
		case 4:
			return "Lieutenant";
		case 5:
			return "Captain";
		case 6:
			return "General";
		case 7:
			return "Only Me";
		}
		return "";
	}

	/**
	 * Sets the minimum rank that can join.
	 * 
	 * @param rank
	 */
	public void setRankCanJoin(int rank) {
		whoCanJoin = rank;
	}

	/**
	 * Sets the minimum rank that can talk.
	 * 
	 * @param rank
	 */
	public void setRankCanTalk(int rank) {
		whoCanTalk = rank;
	}

	/**
	 * Sets the minimum rank that can kick.
	 * 
	 * @param rank
	 */
	public void setRankCanKick(int rank) {
		whoCanKick = rank;
	}

	/**
	 * Sets the minimum rank that can ban.
	 * 
	 * @param rank
	 */
	public void setRankCanBan(int rank) {
		whoCanBan = rank;
	}

	/**
	 * The ranks privileges require (joining, talking, kicking, banning).
	 */
	public int whoCanJoin = Rank.ANYONE;
	public int whoCanTalk = Rank.ANYONE;
	public int whoCanKick = Rank.GENERAL;
	public int whoCanBan = Rank.OWNER;

}