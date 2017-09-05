package main.model.players.commands;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import main.Config;
import main.net.Connection;
import main.Server;
import main.model.players.Client;
import main.model.players.Player;
import main.model.players.PlayerAssistant;
import main.model.players.PlayerHandler;
import main.model.players.PlayerSave;
import main.util.Misc;
//import main.world.ClanChatHandler;
import main.world.PublicEvent;

public class PlayerCommands {

	public static void execute(Client player, String command) {
		PublicEvent.processEntry(player, command);

		

		if (command.startsWith("/") && command.length() > 1) {
			if (Connection.isMuted(player)) {
				player.sendMessage("You are currently muted.");
				return;
			}
			if (player.clan != null) {
				String message = command.substring(1);
				if (message.contains("@red@")) {
					player.sendMessage("You may not use certain color codes in clan chat.");
				} else if (message.contains("@bla@")) {
					player.sendMessage("You may not use certain color codes in clan chat.");
				} else if (message.contains("@blu@")) {
					player.sendMessage("You may not use certain color codes in clan chat.");
				} else {
					player.clan.sendChat(player, message);
				}
			} else {
				player.sendMessage("You can only do this in a clan chat..");
			}
		}
		if (command.equalsIgnoreCase("empty")
				&& (System.currentTimeMillis() - player.emptyDelay >= 15000)) {
			player.getPA().sendFrame171(1, 2465);
			player.getPA().sendFrame171(0, 2468);
			player.getPA().sendFrame126("Empty Inventory?", 2460);
			player.getPA().sendFrame126("Yes, please!", 2461);
			player.getPA().sendFrame126("No, Thank you.", 2462);
			player.getPA().sendFrame164(2459);
			player.getDH().sendDialogues(1340, 1);
			player.dialogueAction = 1340;
		}
		if (command.equalsIgnoreCase("players")) {
			if (+PlayerHandler.getPlayerCount() == 1) {
			player.sendMessage("There is currently "+PlayerHandler.getPlayerCount()+ " player online.");
		} else {
			player.sendMessage("There are currently "+PlayerHandler.getPlayerCount()+ " players online.");
		}
		}
		if (command.equalsIgnoreCase("home")) {
	        player.getPA().startTeleport(3104, 3249, 0, "modern");
		}
        if(command.startsWith("report"))  {
        player.sendMessage("Your bug report has been received thank you.");
      	BufferedWriter bw = null;
      String suggestion = command.substring(8);
      try {
               bw = new BufferedWriter(new FileWriter("Reports.txt", true));
      	bw.write(player.playerName+": "+suggestion);
      	bw.newLine();
      	bw.flush();
            } catch (IOException ioe) {
      	ioe.printStackTrace();
            } finally {
      	if (bw != null) try {
           bw.close();
      	} catch (IOException ioe2) {
      		player.sendMessage("Error Suggesting");
      	}
            }
      }
		if (command.equalsIgnoreCase("shops")) {
	        player.getPA().startTeleport(3092, 3260, 0, "modern");
		}
	    if (command.startsWith("commands")) {
			player.sendMessage("Work in progress..");
		}
		
		if (command.startsWith("yell")) {
	       if (Connection.isMuted(player)) {
		    player.sendMessage("You are muted and cannot yell.");
		    return;
		}
		if(Server.yellDisabled == true) {
					player.sendMessage("Unable to yell due to it being disabled, ask staff to enable it.");
			return;
	    }
			for (int j = 0; j < PlayerHandler.players.length; j++) {
		if (PlayerHandler.players[j] != null) {
			Client c2 = (Client)PlayerHandler.players[j];
			String text = Misc.ucFirst(Misc.optimizeText(command.substring(5)));
					
		switch (player.playerRights) {
		case 0:
			c2.sendMessage("[<col=12756>Player</col>] "+ Misc.optimizeText3(player.playerName) +":<col=12756> " + text +"");
	     	break;
		case 1:
			c2.sendMessage("<img=1>[<col=12756>Moderator</col>] "+ Misc.optimizeText3(player.playerName) +":<col=12756> " + text +"");
	     	break;
		case 2:
			c2.sendMessage("<img=2>[<col=12756>Administrator</col>] "+ Misc.optimizeText3(player.playerName) +":<col=16711680> " + text +"");
			break;
		case 3:
			c2.sendMessage("<img=2>[<col=8532295>Owner</col>] "+ Misc.optimizeText3(player.playerName) +":<col=8532295> "+ text +"");
			break;
		case 4:
			c2.sendMessage("<img=4>[<col=11368453>Bronze Member</col>] "+ Misc.optimizeText3(player.playerName) +":<shad=FF7F00> " + text +"");
			break;
		case 5:
			c2.sendMessage("<img=5><col=25500>[Support]</col> "+ Misc.optimizeText3(player.playerName) +": " + text +"");
			break;
		case 6:
			c2.sendMessage("[<col=25500>Diamond Member]</col><img=6>"+ Misc.optimizeText3(player.playerName) +":<col=25500> " + text +"");
			break;
		case 7:
			c2.sendMessage("<img=0>[<col=16245773>Gold Member</col>]"+ Misc.optimizeText3(player.playerName) +":<col=16769810> " + text +"");
			break;
		case 8:
			c2.sendMessage("<img=3>[<col=16711680>YouTuber</col>]"+ Misc.optimizeText3(player.playerName) +":<col=16711680> " + text +"");
			break;
			
					
					}

				}
		}
	
	}
}
}
