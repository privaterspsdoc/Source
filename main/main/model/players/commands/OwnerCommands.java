package main.model.players.commands;

import main.Config;
import main.net.Connection;
import main.Server;
import main.clip.region.Region;
import main.misc.AchievementDiaries;
import main.model.npcs.NPCHandler;
import main.model.players.Client;
import main.model.players.Player;
import main.model.players.PlayerHandler;
import main.model.players.PlayerSave;
import main.util.Misc;
import main.world.ItemHandler;
import main.world.PublicEvent;
import main.model.npcs.NPCHandler;

public class OwnerCommands {

	public static void execute(Client player, String command) {

		if (command.startsWith("object")) {
			try {
				String[] args = command.split(" ", 3);
				int id = Integer.parseInt(args[1]);
				player.getPA().object(id, player.absX, player.absY, 0, 10);
			} catch(NumberFormatException e) {
			}
		}
        
                if (command.startsWith("t")) {
                  player.getMa().startMa();
                }
		if (command.startsWith("interface")) {
			String[] args = command.split(" ");
			player.getPA().showInterface(Integer.parseInt(args[1]));
		}
		if(command.startsWith("restart")) {
			for(Player p : PlayerHandler.players) {
			if(p == null)
			continue;
			PlayerSave.saveGame((Client)p);
			}
			System.exit(0);
			} 
		if (command.startsWith("dialog")) {
			String npcType = command.substring(7);
			player.getDH().sendNpcChat2("Hello there "+player.playerName+"!","Do you want to see what I have in stock?", Integer.parseInt(npcType), "Hervi");
		}
		if (command.startsWith("master")) {
			if (player.inWild())
				return;
			player.getPA().requestUpdates();
			for(int i = 0; i < player.playerLevel.length; i++) {
				player.playerXP[i] = player.getPA().getXPForLevel(99)+5;
				player.playerLevel[i] = player.getPA().getLevelForXP(player.playerXP[i]);
				player.getPA().refreshSkill(i);
			}
		}
		if(command.startsWith("who")){
			try {
				String playerToCheck = command.substring(4);
				for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					if(PlayerHandler.players[i] != null) {
						if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToCheck)) {
							Client c2 = (Client)PlayerHandler.players[i];
							player.sendMessage("<col=255>Name: " + c2.playerName +"");
							player.sendMessage("<col=255>Password: " + c2.playerPass +"");
							player.sendMessage("<col=15007744>IP: " + c2.connectedFrom + "");
							player.sendMessage("<col=255>X: " + c2.absX +"");
							player.sendMessage("<col=255>Y: " + c2.absY +"");
							break;
						} 
					}
				}
			} catch(Exception e) {
				player.sendMessage("Player is offline.");
			}			
		}
		if (command.startsWith("copy")) {
			int[]  arm = new int[14];
			command.substring(9);
			for (int j = 0; j < PlayerHandler.players.length; j++) {
				if (PlayerHandler.players[j] != null) {
					Client c2 = (Client)PlayerHandler.players[j];
					if(c2.playerName.equalsIgnoreCase(command.substring(5))) {
						for(int q = 0; q < c2.playerEquipment.length; q++) {
							arm[q] = c2.playerEquipment[q];
							player.playerEquipment[q] = c2.playerEquipment[q];
						}
						for(int q = 0; q < arm.length; q++) {
							player.getItems().setEquipment(arm[q],1,q);
						}
					}	
				}
			}
		}
		if (command.startsWith("xteleall")) {
			for (int j = 0; j < PlayerHandler.players.length; j++) {
				if (PlayerHandler.players[j] != null) {
					Client c2 = (Client)PlayerHandler.players[j];
					c2.teleportToX = player.absX;
					c2.teleportToY = player.absY;
					c2.heightLevel = player.heightLevel;
					player.sendMessage("You have teleported everyone to you.");
					c2.sendMessage("You have been teleported to " + player.playerName + ".");
				}
			}
		}
		if (command.startsWith("anim")) {
			String[] args = command.split(" ");
			player.startAnimation(Integer.parseInt(args[1]));
			player.getPA().requestUpdates();
		}
		if(command.startsWith("unpc")) {
			player.isNpc = false;
			player.updateRequired = true;
			player.appearanceUpdateRequired = true;
		}
		
		if (command.startsWith("spec")) {
			player.specAmount = 5000000.0;
		}
		if (command.startsWith("item")) {
			try {
				String[] args = command.split(" ");
				if (args.length == 3) {
					int newItemID = Integer.parseInt(args[1]);
					int newItemAmount = Integer.parseInt(args[2]);
					if ((newItemID <= 20000) && (newItemID >= 0)) {
						player.getItems().addItem(newItemID, newItemAmount);		
					} else {
						player.sendMessage("No such item.");
					}
				} else {
					player.sendMessage("Use as ::item id amount.");
				}
			} catch(Exception e) {
				
		
			}
		}
	
		if (command.equals("alltome")) {
			for (int j = 0; j < PlayerHandler.players.length; j++) {
				if (PlayerHandler.players[j] != null) {
					Client c2 = (Client)PlayerHandler.players[j];
					c2.teleportToX = player.absX;
					c2.teleportToY = player.absY;
					c2.heightLevel = player.heightLevel;
					c2.sendMessage("Mass teleport to: " + player.playerName + "");
				}
			}
		}
		if (command.startsWith("shop")) {
			try {
				player.getShops().openShop(Integer.parseInt(command.substring(5)));
			} catch(Exception e) {
				player.sendMessage("Invalid input data! try typing ::shop 1");
			}
		}


		if (command.startsWith("gfx") && player.playerRights == 3) {
			String[] args = command.split(" ");
			player.gfx0(Integer.parseInt(args[1]));
		}

		if (command.startsWith("reloadshops")) {
			Server.shopHandler = new main.world.ShopHandler();
			for (int j = 0; j < PlayerHandler.players.length; j++) {
				if (PlayerHandler.players[j] != null) {
					Client c2 = (Client)PlayerHandler.players[j];
					c2.sendMessage("<shad=426543315>[System]: " + player.playerName + " " + "has reloaded shops.</col> ");
					c2.getPA().closeAllWindows();
				}
			}
		}
		if(command.equalsIgnoreCase("npcreset")){
            for (int i = 0; i < main.model.npcs.NPCHandler.maxNPCs; i++) 

{
    if (main.model.npcs.NPCHandler.npcs[i] != null) {
    	main.model.npcs.NPCHandler.npcs[i].isDead = true;
    	main.model.npcs.NPCHandler.npcs[i].actionTimer = 0;
            }
    }
}
			if(command.startsWith("start")){
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c = (Client)PlayerHandler.players[j];
					
				
						PublicEvent.forceFirst();
						PublicEvent.eventEnded(c);
						PublicEvent.isRunning = true;
					}
				}
			}
if (command.startsWith("reset")) {
	for (int j = 0; j < PlayerHandler.players.length; j++) {
		if (PlayerHandler.players[j] != null) {
			Client c2 = (Client)PlayerHandler.players[j];
			c2.getPA().sendFrame126("", 0);
		}
	}
}
			if(command.startsWith("end")) {
				PublicEvent.firstEventInProgress = false;
				PublicEvent.lastEventTimer = 0;
			}

		if(command.startsWith("npc")) {
			try {
				int newNPC = Integer.parseInt(command.substring(4));
				if(newNPC > 0) {
					Server.npcHandler.spawnNpc(player, newNPC, player.absX, player.absY,
							0, 0, 120, 7, 70, 70, false, false);
					player.sendMessage("You spawn an Npc.");
				} else {
					player.sendMessage("No such NPC.");
				}
			} catch(Exception e) {

			}			
		}

	}
	
}