package main.model.players.commands;

import main.Config;
import main.net.Connection;
import main.model.players.Client;
import main.model.players.PlayerHandler;

public class AdministratorCommands {

	public static void execute(Client player, String command){
		if (command.startsWith("unipmute")) {
			try {	
				String playerToBan = command.substring(9);
				for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					if(PlayerHandler.players[i] != null) {
						if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
							Connection.unIPMuteUser(PlayerHandler.players[i].connectedFrom);
							Connection.unMuteUser(PlayerHandler.players[i].playerName);
							player.sendMessage("You have unipmuted the user: "+PlayerHandler.players[i].playerName);
							break;
						} 
					}
				}
			} catch(Exception e) {
				player.sendMessage("Player Must Be Offline.");
			}			
		}
		if (command.startsWith("macban") && player.playerRights == 3) {
			try {
				String playerToBan = command.substring(7);
				for (int i = 0; i < PlayerHandler.players.length; i++) {
					Client c2 = (Client) PlayerHandler.players[i];
					if (PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName
								.equalsIgnoreCase(playerToBan)) {
							Connection.addMacToBanList(PlayerHandler.players[i]
									.getMacAddress());
							Connection.addMacToMacFile(PlayerHandler.players[i]
									.getMacAddress());
							c2.sendMessage("@red@["
									+ PlayerHandler.players[i].playerName
									+ "] has been MAC Banned");
							PlayerHandler.players[i].disconnected = true;
						}
					}
				}
			} catch (Exception ignored) {
			}
		}
		if (command.startsWith("tele")) {
			String[] arg = command.split(" ");
			if (arg.length > 3)
				player.getPA().movePlayer(Integer.parseInt(arg[1]),Integer.parseInt(arg[2]),Integer.parseInt(arg[3]));
			else if (arg.length == 3)
				player.getPA().movePlayer(Integer.parseInt(arg[1]),Integer.parseInt(arg[2]),player.heightLevel);
		}
		if (command.startsWith("checkinv")) {
			try {
				String[] args = command.split(" ", 2);
				for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					Client o = (Client) PlayerHandler.players[i];
					if(PlayerHandler.players[i] != null) {
						if(PlayerHandler.players[i].playerName.equalsIgnoreCase(args[1])) {
							player.getPA().otherInv(player, o);
							break;
						}
					}
				}
			} catch(Exception e) {
				player.sendMessage("Player Must Be Offline."); 
			}
		}

		
		if (command.startsWith("unban")) {
			try {	
				String playerToBan = command.substring(6);
				Connection.removeNameFromBanList(playerToBan);
				player.sendMessage(playerToBan + " has been unbanned.");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		if (command.startsWith("ban") && command.charAt(3) == ' ') {
			try {	
				String playerToBan = command.substring(4);
				Connection.addNameToBanList(playerToBan);
				Connection.addNameToFile(playerToBan);
				for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					if(PlayerHandler.players[i] != null) {
						if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
							PlayerHandler.players[i].disconnected = true;
							player.sendMessage("You have banned: "+playerToBan);
						} 
					}
				}
			} catch(Exception e) {
				player.sendMessage("Player is offline.");
			}
		}
		if (command.startsWith("ipban")) {
			try {
				String playerToBan = command.substring(6);
				for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					if(PlayerHandler.players[i] != null) {
						if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
							Connection.addIpToBanList(PlayerHandler.players[i].connectedFrom+" - " +PlayerHandler.players[i].playerName);
							Connection.addIpToFile(PlayerHandler.players[i].connectedFrom+" - " +PlayerHandler.players[i].playerName);
							player.sendMessage("You have IP banned the user: "+PlayerHandler.players[i].playerName+" with the host: "+PlayerHandler.players[i].connectedFrom);
							Client c2 = (Client)PlayerHandler.players[i];
							c2.disconnected = true;
							
						} 
					}
				}
			} catch(Exception e) {
				player.sendMessage("Player Must Be Offline.");
			}
		}

	}
	
}