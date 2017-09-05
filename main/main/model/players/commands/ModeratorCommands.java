package main.model.players.commands;

import main.Config;
import main.net.Connection;
import main.Server;
import main.model.players.Client;
import main.model.players.PlayerHandler;
import main.util.Misc;

public class ModeratorCommands {

	public static void execute(Client player, String command){
		/*if(command.startsWith("jail")) {
			if(player.inWild()) {
				player.sendMessage("<shad=15695415>You can't use this command whilst in the winderness.</col>");
				return;
			}  
			try {
				String playerToBan = command.substring(5);
				for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					if(PlayerHandler.players[i] != null) {
						if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
							Client c2 = (Client)PlayerHandler.players[i];
							int randomjail = Misc.random(3);
							if (randomjail == 1) {
								c2.getPA().startTeleport(2773, 2794, 0, "modern");

							}
							if (randomjail == 2) {
								c2.getPA().startTeleport(2775, 2796, 0, "modern");

							}
							if (randomjail == 3) {
								c2.getPA().startTeleport(2775, 2798, 0, "modern");

							}
							if (randomjail == 0) {
								c2.getPA().startTeleport(2775, 2800, 0, "modern");

							}
							c2.Jail = true;
							c2.sendMessage("You have been jailed by "+player.playerName+"");
							player.sendMessage("You have Jailed "+c2.playerName+".");
						} 
					}
				}
			} catch(Exception e) {
				player.sendMessage("Player Must Be Offline.");
			}
		}*/
		/*if (command.startsWith("disableyell")) {
			if (Server.yellDisabled == true) {
				player.sendMessage("Yell is already disabled.");
				return;
			}
			Server.yellDisabled = true;
			for(int i = 0; i < Config.MAX_PLAYERS; i++) {
				if(PlayerHandler.players[i] != null) {
					Client c2 = (Client)PlayerHandler.players[i];
					c2.sendMessage("Yell has been turned <col=15007744>Off</col> by "+player.playerName+".");
				}
			}
		} else if (command.startsWith("enableyell")) {
			if (Server.yellDisabled == false) {
				player.sendMessage("Yell is enabled already.");
				return;
			}
			Server.yellDisabled = false;
			for(int i = 0; i < Config.MAX_PLAYERS; i++) {
				if(PlayerHandler.players[i] != null) {
					Client c2 = (Client)PlayerHandler.players[i];
					c2.sendMessage("Yell has been turned <col=15007744>On</col> by "+player.playerName+".");
				}
			}
		}*/
		if (command.startsWith("mute")) {
			try {	
				String playerToBan = command.substring(5);
				Connection.addNameToMuteList(playerToBan);
				for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					if(PlayerHandler.players[i] != null) {
						if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
							Client c2 = (Client)PlayerHandler.players[i];
							c2.sendMessage("You have been muted by: " + player.playerName);
							break;
						} 
					}
				}
			} catch(Exception e) {
				player.sendMessage("Player Must Be Offline.");
			}			
		}		
		if (command.startsWith("unmute")) {
			try {	
				String playerToBan = command.substring(7);
				Connection.unMuteUser(playerToBan);
				player.sendMessage("You have Unmuted "+player.playerName+".");


			} catch(Exception e) {
				player.sendMessage("Player Must Be Offline.");
				
			}			
		}
		if (command.startsWith("xteleto")) {
			String name = command.substring(8);
			for (int i = 0; i < Config.MAX_PLAYERS; i++) {
				if (PlayerHandler.players[i] != null) {
					if (PlayerHandler.players[i].playerName.equalsIgnoreCase(name)) {
						player.getPA().movePlayer(PlayerHandler.players[i].getX(), PlayerHandler.players[i].getY(), PlayerHandler.players[i].heightLevel);
					}
				}
			}			
		}
		if (command.equalsIgnoreCase("mypos")) {
			player.sendMessage("X: "+player.absX+" Y: "+player.absY+" H: "+player.heightLevel);
		}
		if (command.startsWith("checkbank")) {
			try {
				String[] args = command.split(" ", 2);
				for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					Client o = (Client) PlayerHandler.players[i];
					if(PlayerHandler.players[i] != null) {
						if(PlayerHandler.players[i].playerName.equalsIgnoreCase(args[1])) {
             						player.getPA().otherBank(player, o);
										break;
						}
					}
				}
			} catch(Exception e) {
				player.sendMessage("Player Must Be Offline."); 
				}
		}

		if (command.startsWith("xteletome")) {
			try {	
				String playerToTele = command.substring(10);
				for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					
					Client c2 = (Client)PlayerHandler.players[i];
					if(PlayerHandler.players[i] != null) {
						if (c2.inWild()){
							player.sendMessage("That player is currently in the wilderness, you're unable to teleport that user.");
							return;
						}
						if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToTele)) {
							c2.sendMessage("You have been teleported to " + player.playerName);
							c2.getPA().movePlayer(player.getX(), player.getY(), player.heightLevel);
							break;
						} 
					}
				}
			} catch(Exception e) {
				player.sendMessage("Player Must Be Offline.");
			}			
		}
	}
	
}
