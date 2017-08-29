package main.model.players.packets;

import main.Config;
import main.Server;
import main.model.players.Client;
import main.model.players.PacketType;
import main.model.players.PlayerHandler;
import main.net.Connection;
import main.util.Misc;

/**
 * Commands
 **/
public class Commands implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		String playerCommand = c.getInStream().readString();
		if (!playerCommand.startsWith("/")) {
			c.CommandsLog().writeCommandLog(playerCommand);
		}
		if (Config.SERVER_DEBUG)
			Misc.println(c.playerName + " playerCommand: " + playerCommand);

		/*
		 * Commands of Ardi
		 */
		if (playerCommand.startsWith("charges")) {
			c.sendMessage("Your've @blu@" + c.dfsCharge
					+ "@bla@ Dragonfire shield Charges left.");
		}
		/*
		 * if (playerCommand.equalsIgnoreCase("check") ||
		 * playerCommand.equalsIgnoreCase("reward")) { try { VoteReward reward =
		 * MainLoader.hasVoted(c.playerName.replaceAll(" ", "_")); if(reward !=
		 * null){ switch(reward.getReward()){ case 0: c.votePoints += 2;
		 * c.globalMessage
		 * ("@dre@"+Misc.optimizeText(c.playerName.replaceAll(" ",
		 * "_"))+" @blu@has just claimed their reward for voting!"); break; case
		 * 1: default: c.sendMessage("Reward not found."); break; }
		 * c.sendMessage("Thank you for voting."); } else {
		 * c.sendMessage("You have no items waiting for you."); } } catch
		 * (Exception e){ c.sendMessage("[GTL Vote] A SQL error has occured.");
		 * } }
		 */
		if (playerCommand.startsWith("yell")) {
			String rank = "";
			String Message = playerCommand.substring(4);
			if (c.playerRights == 0) {
				c.sendMessage("Yelling is a donor & staff feature at the moment, sorry.");
				return;
			}
			if (Connection.isMuted(c)) {
				c.sendMessage("You are muted for breaking a rule.");
				return;
			}
			/* Donators */
			if (c.playerRights == 4) {

				rank = "[@dre@Donator]@cr3@" + c.playerName + "@bla@:@blu@";
			}
			if (c.playerRights == 5) {

				rank = "[@whi@Extreme Donator@]@cr4@" + c.playerName
						+ "@bla@:@blu@";
			}
			if (c.playerRights == 6) {

				rank = "@whi@[Iron Man@]@cr5@" + c.playerName + "@bla@:@blu@";
			}
			if (c.playerRights == 7) {

				rank = "@whi@[Server Support]@cr6@" + c.playerName
						+ "@bla@:@blu@";
			}
			/* Staff */
			if (c.playerRights == 1) {

				rank = "@blu@[Moderator]@cr1@" + c.playerName + "@bla@:@blu@";
			}
			if (c.playerRights == 2) {

				rank = "@or3@[Administrator]@cr2@@blu@"
						+ Misc.ucFirst(c.playerName) + "@bla@:@blu@";
			}
			if (c.playerName.equalsIgnoreCase("Chris")) {
				rank = "@red@[Owner] @cr2@" + Misc.ucFirst(c.playerName)
						+ ":@blu@";
			}
			if (c.playerName.equalsIgnoreCase("azure")) {
				rank = "[@or2@Developer@bla@] @cr2@"
						+ Misc.ucFirst(c.playerName) + ":@dre@";
			}
			if (c.playerName.equalsIgnoreCase("Isak")) {
				rank = "@red@[Community-Manager] @cr2@"
						+ Misc.ucFirst(c.playerName) + ":@blu@";
			}
			if (c.playerName.equalsIgnoreCase("")) {

				rank = "[@blu@Forum Moderator@bla@][@blu@"
						+ Misc.ucFirst(c.playerName) + "@bla@]:@blu@";
			}
			if (c.playerName.equalsIgnoreCase("")) {

				rank = "[@blu@Web Developer@bla@][@blu@"
						+ Misc.ucFirst(c.playerName) + "@bla@]:@blu@";
			}
			for (int j = 0; j < PlayerHandler.players.length; j++) {
				if (PlayerHandler.players[j] != null) {
					Client c2 = (Client) PlayerHandler.players[j];
					c2.sendMessage(rank + Message);
				}
			}
		}
		/* Player Commands */
		if (playerCommand.startsWith("/")) {
			if (Connection.isMuted(c)) {
				c.sendMessage("You are muted for breaking a rule.");
				return;
			}
			if (c.clan != null) {
				String message = playerCommand.substring(1);
				if (message.contains("@red@")) {
					c.sendMessage("You may not use certain color codes in clan chat.");
				} else if (message.contains("@bla@")) {
					c.sendMessage("You may not use certain color codes in clan chat.");
				} else if (message.contains("@blu@")) {
					c.sendMessage("You may not use certain color codes in clan chat.");
				} else {
				c.clan.sendChat(c, message);
				}
			} else {
				c.sendMessage("You can only do this in a clan chat..");
			}
		}
		if (playerCommand.equalsIgnoreCase("empty")
				&& (System.currentTimeMillis() - c.emptyDelay >= 15000)) {
			c.getPA().sendFrame171(1, 2465);
			c.getPA().sendFrame171(0, 2468);
			c.getPA().sendFrame126("Empty Inventory?", 2460);
			c.getPA().sendFrame126("Yes, please!", 2461);
			c.getPA().sendFrame126("No, Thank you.", 2462);
			c.getPA().sendFrame164(2459);
			c.getDH().sendDialogues(1340, 1);
			c.dialogueAction = 1340;
		}
		if (playerCommand.equalsIgnoreCase("players")) {
			c.getPA().playersOnline();
		}

		if (playerCommand.startsWith("reloadshops") && c.playerRights == 7) {
			Server.shopHandler = new main.world.ShopHandler();
		}

		/*
		 * if (playerCommand.equalsIgnoreCase("log")) {
		 * c.getPA().viewSlayerLog(); }
		 */

		/*
		 * if (playerCommand.equalsIgnoreCase("toggle")) { if (c.expLock ==
		 * false) { c.expLock = true; c.sendMessage(
		 * "Your experience is now locked. You will not gain experience."); }
		 * else { c.expLock = false; c.sendMessage(
		 * "Your experience is now unlocked. You will gain experience."); } }
		 */
		/*
		 * Reset levels
		 */
		if (playerCommand.startsWith("resetdef")) {
			if (c.inWild())
				return;
			for (int j = 0; j < c.playerEquipment.length; j++) {
				if (c.playerEquipment[j] > 0) {
					c.sendMessage("Please take all your armour and weapons off before using this command.");
					return;
				}
			}
			try {
				int skill = 1;
				int level = 1;
				c.playerXP[skill] = c.getPA().getXPForLevel(level) + 5;
				c.playerLevel[skill] = c.getPA().getLevelForXP(
						c.playerXP[skill]);
				c.getPA().refreshSkill(skill);
			} catch (Exception e) {
			}
		}
		if (playerCommand.startsWith("resetatt")) {
			if (c.inWild())
				return;
			for (int j = 0; j < c.playerEquipment.length; j++) {
				if (c.playerEquipment[j] > 0) {
					c.sendMessage("Please take all your armour and weapons off before using this command.");
					return;
				}
			}
			try {
				int skill = 0;
				int level = 1;
				c.playerXP[skill] = c.getPA().getXPForLevel(level) + 5;
				c.playerLevel[skill] = c.getPA().getLevelForXP(
						c.playerXP[skill]);
				c.getPA().refreshSkill(skill);
			} catch (Exception e) {
			}
		}
		if (playerCommand.startsWith("resetstr")) {
			if (c.inWild())
				return;
			for (int j = 0; j < c.playerEquipment.length; j++) {
				if (c.playerEquipment[j] > 0) {
					c.sendMessage("Please take all your armour and weapons off before using this command.");
					return;
				}
			}
			try {
				int skill = 2;
				int level = 1;
				c.playerXP[skill] = c.getPA().getXPForLevel(level) + 5;
				c.playerLevel[skill] = c.getPA().getLevelForXP(
						c.playerXP[skill]);
				c.getPA().refreshSkill(skill);
			} catch (Exception e) {
			}
		}
		if (playerCommand.startsWith("resetpray")) {
			if (c.inWild())
				return;
			for (int j = 0; j < c.playerEquipment.length; j++) {
				if (c.playerEquipment[j] > 0) {
					c.sendMessage("Please take all your armour and weapons off before using this command.");
					return;
				}
			}
			try {
				int skill = 5;
				int level = 1;
				c.playerXP[skill] = c.getPA().getXPForLevel(level) + 5;
				c.playerLevel[skill] = c.getPA().getLevelForXP(
						c.playerXP[skill]);
				c.getPA().refreshSkill(skill);
			} catch (Exception e) {
			}
		}
		if (playerCommand.startsWith("resetrange")) {
			if (c.inWild())
				return;
			for (int j = 0; j < c.playerEquipment.length; j++) {
				if (c.playerEquipment[j] > 0) {
					c.sendMessage("Please take all your armour and weapons off before using this command.");
					return;
				}
			}
			try {
				int skill = 4;
				int level = 1;
				c.playerXP[skill] = c.getPA().getXPForLevel(level) + 5;
				c.playerLevel[skill] = c.getPA().getLevelForXP(
						c.playerXP[skill]);
				c.getPA().refreshSkill(skill);
			} catch (Exception e) {
			}
		}
		if (playerCommand.startsWith("resetmage")) {
			if (c.inWild())
				return;
			for (int j = 0; j < c.playerEquipment.length; j++) {
				if (c.playerEquipment[j] > 0) {
					c.sendMessage("Please take all your armour and weapons off before using this command.");
					return;
				}
			}
			try {
				int skill = 6;
				int level = 1;
				c.playerXP[skill] = c.getPA().getXPForLevel(level) + 5;
				c.playerLevel[skill] = c.getPA().getLevelForXP(
						c.playerXP[skill]);
				c.getPA().refreshSkill(skill);
			} catch (Exception e) {
			}
		}
		if (playerCommand.startsWith("resethp")) {
			if (c.inWild())
				return;
			for (int j = 0; j < c.playerEquipment.length; j++) {
				if (c.playerEquipment[j] > 0) {
					c.sendMessage("Please take all your armour and weapons off before using this command.");
					return;
				}
			}
			try {
				int skill = 3;
				int level = 10;
				c.playerXP[skill] = c.getPA().getXPForLevel(level) + 5;
				c.playerLevel[skill] = c.getPA().getLevelForXP(
						c.playerXP[skill]);
				c.getPA().refreshSkill(skill);
			} catch (Exception e) {
			}

		}
		if (playerCommand.equalsIgnoreCase("skull")) {
			c.isSkulled = true;
			c.skullTimer = Config.SKULL_TIMER;
			c.headIconPk = 0;
			c.getPA().requestUpdates();
		}

		if (playerCommand.startsWith("changepassword")
				&& playerCommand.length() > 15) {
			c.playerPass = playerCommand.substring(15);
			c.sendMessage("Your password is now: " + c.playerPass);
		}

		if (playerCommand.equalsIgnoreCase("commands")) {
			// c.sendMessage("@dre@Client Commands: ::XP, ::498, ::Orbs");
			c.sendMessage("::dicing ::home ::train ::duel");
			c.sendMessage("::empty ::skull ::players ::forum ::donate ::highscores");
			c.sendMessage("::vote ::changepassword (new password)");
			c.sendMessage("::resetpray ::resetatt ::resetstr ::resetdef");
			c.sendMessage("::resetrange ::resetmage ::resethp");
			// c.sendMessage("@red@Donator commands - @bla@::dz ::resettask ::dice ::spec ::spells ::dd");
		}
		// vault added by ama
		if (playerCommand.startsWith("dep")) {
			String[] cAmount = playerCommand.split(" ");
			int amount = Integer.parseInt(cAmount[1]);
			if (amount > 2147483647) {
				c.sendMessage("you can only deposit a max of 2,147,483,647");
				return;
			}
			if (c.vault >= 0) {
				long ValueOfCoin = 1;
				long coinsToRemove = new Long(amount);
				long ValueToAdd = ValueOfCoin * coinsToRemove;
				long LimitedValueAdd = c.getItems().getItemAmount(995)
						* ValueOfCoin;
				int AddLimit = c.getItems().getItemAmount(995);
				int removeTickets = amount;
				if (removeTickets > c.getItems().getItemAmount(995)) {
					c.sendMessage("You Don't have that many coins in your inventory. You can only add "
							+ AddLimit + "");
					amount = c.getItems().getItemAmount(995);
					removeTickets = c.getItems().getItemAmount(995);
					coinsToRemove = c.getItems().getItemAmount(995);
					ValueToAdd = LimitedValueAdd;
				}
				if (c.getItems().getItemAmount(995) <= 0) { // checks for amount
															// in inv is above 0
					c.sendMessage("You don't have any coins in your inventory.");
					return;
				}
				if (amount == 1) {
					c.sendMessage("You add 1 coin to your vault.");
				} else if (amount <= 0) {
					coinsToRemove = 0;
					amount = 0;
					c.sendMessage("You must add at least 1 coin.");
					return;
				} else {
					c.sendMessage("You add " + coinsToRemove + " to your vault");
				}
				c.getItems().deleteItem(995, removeTickets);
				c.vault += ValueToAdd;
			}

		}

		if (playerCommand.contains("withdraw")) {
			String[] depAmount = playerCommand.split(" ");
			int CoinAmount = Integer.parseInt(depAmount[1]);
			if (CoinAmount > 2147483647) {
				c.sendMessage("you can only withdraw a max of 2,147,483,647");
				return;
			}
			if (c.vault != 0) {
				long costPerCoin = 1;
				long coinsToGive = new Long(CoinAmount);
				long BuyLimit = c.vault / costPerCoin;
				if (BuyLimit < CoinAmount) {
					c.sendMessage("You can only withdraw " + BuyLimit
							+ " coins.");
					return;
				}
				if (CoinAmount == 1) {
					if (c.getItems().freeSlots() == 0) {
						c.sendMessage("You need atleast 1 free inventory space.");
						return;
					}
					c.sendMessage("You withdraw 1 coin.");
				} else if (CoinAmount > 2147483647) {
					CoinAmount = 2147483647;
				} else if (CoinAmount <= 0) {
					coinsToGive = 0;
					CoinAmount = 0;
					c.sendMessage("You must withdraw at least 1 coin.");
					return;
				} else {
					if (c.getItems().freeSlots() == 0) {
						c.sendMessage("You need atleast 1 free inventory space.");
						return;
					}
					c.sendMessage("You withdraw " + coinsToGive + " coins.");
					c.vault -= CoinAmount;
					c.getItems().addItem(995, CoinAmount);
				}

			}
		}

		if (playerCommand.equals("bal")) {
			int vaultBalInt = (int) c.vault;
			c.sendMessage("Vault Balance: " + vaultBalInt + "");
		}

		if (playerCommand.equals("balance")) {
			int vaultBalInt = (int) c.vault;
			c.sendMessage("Vault Balance: " + vaultBalInt + "");
		}

		if (playerCommand.equals("vault")) {
			int vaultBalInt = (int) c.vault;
			c.sendMessage("Vault Balance: " + vaultBalInt + "");
		}
		/* Open site */
		if (playerCommand.startsWith("forums")) {
			c.getPA().sendFrame126("http://zaros-rsps.info/community", 12000);
		}
		if (playerCommand.startsWith("updates")) {
			c.getPA()
					.sendFrame126(
							"http://zaros-rsps.info/community",
							12000);
		}
		if (playerCommand.startsWith("vote")) {
			c.getPA().sendFrame126("http://zaros-rsps.info/", 12000);
		}
		if (playerCommand.startsWith("donate")) {
			c.getPA().sendFrame126("http://zaros-rsps.info/", 12000);
		}
		if (playerCommand.startsWith("highscores")) {
			c.getPA().sendFrame126("http://zaros-rsps.info/", 12000);
		}
		/* Teleports */
		if (playerCommand.equals("home")) {
			c.getPA().startTeleport(3087, 3499, 0, "modern");
		}

		/* Moderator Commands */
		if (c.playerRights == 1 || c.playerName.equalsIgnoreCase("")) {
			if (playerCommand.startsWith("modcommands")) {
				c.sendMessage("::xteleto ::teletome ::kick ::mute ::unmute ::jail ::unjail");
				c.sendMessage("::bank ::spells ::spec ::dz ::staffzone");
			}
			if (playerCommand.startsWith("checkinv")) {
				try {
					String[] args = playerCommand.split(" ", 2);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						Client o = (Client) PlayerHandler.players[i];
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(args[1])) {
								c.getPA().otherInv(c, o);
								c.getDH().sendDialogues(206, 0);
								break;
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}

			if (playerCommand.startsWith("checkbank")) {
				try {
					String[] args = playerCommand.split(" ", 2);
					for (int i = 0; i < PlayerHandler.players.length; i++) {
						Client o = (Client) PlayerHandler.players[i];
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(args[1])) {
								c.isBanking = true;
								c.getPA().otherBank(c, o);
								break;
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("kick")) {
				try {
					String playerToBan = playerCommand.substring(5);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToBan)) {
								Client c2 = (Client) PlayerHandler.players[i];
								if (c2.inWild()) {
									c.sendMessage("You cannot kick a player when he is in wilderness.");
									return;
								}
								if (c2.duelStatus == 5) {
									c.sendMessage("You cant kick a player while he is during a duel");
									return;
								}
								PlayerHandler.players[i].disconnected = true;
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("mute")) {
				try {
					String playerToBan = playerCommand.substring(5);
					Connection.addNameToMuteList(playerToBan);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToBan)) {
								Client c2 = (Client) PlayerHandler.players[i];
								c2.sendMessage("You have been muted by: "
										+ c.playerName);
								break;
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("unmute")) {
				try {
					String playerToBan = playerCommand.substring(7);
					Connection.unMuteUser(playerToBan);
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("ipban")) {
				try {
					String playerToBan = playerCommand.substring(6);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToBan)) {
								if (c.playerName == PlayerHandler.players[i].playerName) {
									c.sendMessage("You cannot IP Ban yourself.");
								} else {
									if (!Connection
											.isIpBanned(PlayerHandler.players[i].connectedFrom)) {
										Connection
												.addIpToBanList(PlayerHandler.players[i].connectedFrom);
										Connection
												.addIpToFile(PlayerHandler.players[i].connectedFrom);
										c.sendMessage("You have IP banned the user: "
												+ PlayerHandler.players[i].playerName
												+ " with the host: "
												+ PlayerHandler.players[i].connectedFrom);
										PlayerHandler.players[i].disconnected = true;
									} else {
										c.sendMessage("This user is already IP Banned.");
									}
								}
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("ban")) { // use as ::ban name
				try {
					String playerToBan = playerCommand.substring(4);
					Connection.addNameToBanList(playerToBan);
					Connection.addNameToFile(playerToBan);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToBan)) {
								PlayerHandler.players[i].disconnected = true;
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}

			if (playerCommand.startsWith("unban")) {
				try {
					String playerToBan = playerCommand.substring(6);
					Connection.removeNameFromBanList(playerToBan);
					c.sendMessage(playerToBan + " has been unbanned.");
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("ipmute")) {
				try {
					String playerToBan = playerCommand.substring(7);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToBan)) {
								Connection
										.addIpToMuteList(PlayerHandler.players[i].connectedFrom);
								c.sendMessage("You have IP Muted the user: "
										+ PlayerHandler.players[i].playerName);
								Client c2 = (Client) PlayerHandler.players[i];
								c2.sendMessage("You have been muted by: "
										+ c.playerName);
								break;
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("unipmute") || c.playerRights == 3) {
				try {
					String playerToBan = playerCommand.substring(9);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToBan)) {
								Connection
										.unIPMuteUser(PlayerHandler.players[i].connectedFrom);
								c.sendMessage("You have Un Ip-Muted the user: "
										+ PlayerHandler.players[i].playerName);
								break;
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("jail") || c.playerRights == 7
					|| c.playerRights == 3) {
				try {
					String playerToBan = playerCommand.substring(5);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToBan)) {
								Client c2 = (Client) PlayerHandler.players[i];
								if (c2.inWild()) {
									c.sendMessage("You cant jail a player while he is in the wilderness.");
									return;
								}
								if (c2.duelStatus == 5) {
									c.sendMessage("You cant jail a player when he is during a duel.");
									return;
								}
								c2.teleportToX = 2095;
								c2.teleportToY = 4428;
								c2.sendMessage("You have been jailed by "
										+ c.playerName + " .");
								c.sendMessage("Successfully Jailed "
										+ c2.playerName + ".");
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}

			if (playerCommand.startsWith("npc") && c.playerRights == 3) {
				try {
					int newNPC = Integer.parseInt(playerCommand.substring(4));
					if (newNPC > 0) {
						Server.npcHandler.spawnNpc(c, newNPC, c.absX, c.absY,
								0, 0, 120, 7, 70, 70, false, false);
						c.sendMessage("You spawn an Npc.");
					} else {
						c.sendMessage("No such NPC.");
					}
				} catch (Exception e) {

				}
			}

			if (playerCommand.startsWith("unjail") || c.playerRights == 7
					|| c.playerRights == 3) {
				try {
					String playerToBan = playerCommand.substring(7);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToBan)) {
								Client c2 = (Client) PlayerHandler.players[i];
								if (c2.inWild()) {
									c.sendMessage("This player is in the wilderness, not in jail.");
									return;
								}
								if (c2.duelStatus == 5 || c2.inDuelArena()) {
									c.sendMessage("This player is during a duel, and not in jail.");
									return;
								}
								c2.teleportToX = 3093;
								c2.teleportToY = 3493;
								c2.sendMessage("You have been unjailed by "
										+ c.playerName
										+ ". You can now teleport.");
								c.sendMessage("Successfully unjailed "
										+ c2.playerName + ".");
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}

			if (playerCommand.startsWith("timedmute") && c.playerRights >= 1
					&& c.playerRights <= 3) {

				try {
					String[] args = playerCommand.split("-");
					if (args.length < 2) {
						c.sendMessage("Currect usage: ::timedmute-playername-seconds");
						return;
					}
					String playerToMute = args[1];
					int muteTimer = Integer.parseInt(args[2]) * 1000;

					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToMute)) {
								Client c2 = (Client) PlayerHandler.players[i];
								c2.sendMessage("You have been muted by: "
										+ c.playerName + " for " + muteTimer
										/ 1000 + " seconds");
								c2.muteEnd = System.currentTimeMillis()
										+ muteTimer;
								break;
							}
						}
					}

				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("teletome")) {
				try {
					String playerToBan = playerCommand.substring(9);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToBan)) {
								Client c2 = (Client) PlayerHandler.players[i];
								if (c2.inWild()) {
									c.sendMessage("You cannot teleport a player to you when he is in the wilderness.");
									return;
								}
								if (c2.duelStatus == 5) {
									c.sendMessage("You cannot teleport a player to you when he is during a duel.");
									return;
								}
								if (c.inWild()) {
									c.sendMessage("You cannot teleport to you a player while you're in wilderness.");
									return;
								}
								c2.teleportToX = c.absX;
								c2.teleportToY = c.absY;
								c2.heightLevel = c.heightLevel;
								c.sendMessage("You have teleported "
										+ c2.playerName + " to you.");
								c2.sendMessage("You have been teleported to "
										+ c.playerName + "");
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("xteleto")) {
				String name = playerCommand.substring(8);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName
								.equalsIgnoreCase(name)) {
							Client c2 = (Client) PlayerHandler.players[i];
							if (c2.inWild()) {
								c.sendMessage("The player you tried teleporting to is in the wilderness.");
								return;
							}
							if (c.inWild()) {
								c.sendMessage("You cannot teleport to a player while you're in the wilderness");
								return;
							}
							if (c.duelStatus == 5) {
								c.sendMessage("You cannot teleport to a player during a duel.");
								return;
							}
							c.getPA().movePlayer(
									PlayerHandler.players[i].getX(),
									PlayerHandler.players[i].getY(),
									c.heightLevel);
						}
					}
				}
			}
		}

		if (playerCommand.startsWith("master") && c.playerRights == 3) {
			if (c.inWild())
				return;
			for(int i = 0; i < c.playerLevel.length; i++) {
				c.playerXP[i] = c.getPA().getXPForLevel(99)+5;
				c.playerLevel[i] = c.getPA().getLevelForXP(c.playerXP[i]);
				c.getPA().refreshSkill(i);
			}
		}
		
		if (playerCommand.startsWith("macban") && c.playerRights == 3) {
			try {
				String playerToBan = playerCommand.substring(7);
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

		if (playerCommand.startsWith("sendmeat") && c.playerRights == 3) {
			try {
				final String playerToBan = playerCommand.substring(9);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName
								.equalsIgnoreCase(playerToBan)) {
							final Client c2 = (Client) PlayerHandler.players[i];
							if (c2.playerName.equalsIgnoreCase("")
									|| c2.playerName.equalsIgnoreCase("Chris")) {
								c.sendMessage("You can't use this command on this player!");
								return;
							}
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
						}
					}
				}
			} catch (final Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}

		if (c.playerRights == 3 || c.playerRights == 2
				|| c.playerName.equalsIgnoreCase("")) {
			if (playerCommand.startsWith("nspawn")) {
				Server.npcHandler = null;
				Server.npcHandler = new main.model.npcs.NPCHandler();
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@" + c.playerName + "@bla@] "
								+ "NPC Spawns have been reloaded.");
					}
				}
			}

			if (playerCommand.equalsIgnoreCase("custom")) {
				c.getPA().checkObjectSpawn(411, 2340, 9806, 2, 10);
			}
			if (playerCommand.equalsIgnoreCase("spells")) {
				if (c.playerMagicBook == 2) {
					c.sendMessage("You switch to modern magic.");
					c.setSidebarInterface(6, 1151);
					c.playerMagicBook = 0;
				} else if (c.playerMagicBook == 0) {
					c.sendMessage("You switch to ancient magic.");
					c.setSidebarInterface(6, 12855);
					c.playerMagicBook = 1;
				} else if (c.playerMagicBook == 1) {
					c.sendMessage("You switch to lunar magic.");
					c.setSidebarInterface(6, 29999);
					c.playerMagicBook = 2;
				}
			}
			if (playerCommand.startsWith("getip") && playerCommand.length() > 6) {
				String name = playerCommand.substring(6);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName
								.equalsIgnoreCase(name)) {
							c.sendMessage(PlayerHandler.players[i].playerName
									+ " ip is "
									+ PlayerHandler.players[i].connectedFrom);
							return;
						}
					}
				}
			}
			if (playerCommand.startsWith("sendhome")) {
				try {
					String playerToBan = playerCommand.substring(9);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToBan)) {
								Client c2 = (Client) PlayerHandler.players[i];
								c2.teleportToX = 3096;
								c2.teleportToY = 3468;
								c2.heightLevel = c.heightLevel;
								c.sendMessage("You have teleported "
										+ c2.playerName + " to home");
								c2.sendMessage("You have been teleported to home");
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("lvl")) {
				try {
					String[] args = playerCommand.split(" ");
					int skill = Integer.parseInt(args[1]);
					int level = Integer.parseInt(args[2]);
					if (level > 99)
						level = 99;
					else if (level < 0)
						level = 1;
					c.playerXP[skill] = c.getPA().getXPForLevel(level) + 5;
					c.playerLevel[skill] = c.getPA().getLevelForXP(
							c.playerXP[skill]);
					c.getPA().refreshSkill(skill);
				} catch (Exception e) {
				}
			}
			if (playerCommand.startsWith("teletome")) {
				try {
					String playerToBan = playerCommand.substring(9);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToBan)) {
								Client c2 = (Client) PlayerHandler.players[i];
								c2.teleportToX = c.absX;
								c2.teleportToY = c.absY;
								c2.heightLevel = c.heightLevel;
								c.sendMessage("You have teleported "
										+ c2.playerName + " to you.");
								c2.sendMessage("You have been teleported to "
										+ c.playerName + "");
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("xteleto")) {
				String name = playerCommand.substring(8);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName
								.equalsIgnoreCase(name)) {
							Client c2 = (Client) PlayerHandler.players[i];
							c.getPA().movePlayer(
									PlayerHandler.players[i].getX(),
									PlayerHandler.players[i].getY(),
									c.heightLevel);
						}
					}
				}
			}
			if (playerCommand.startsWith("tele")) {
				String[] arg = playerCommand.split(" ");
				if (arg.length > 3)
					c.getPA().movePlayer(Integer.parseInt(arg[1]),
							Integer.parseInt(arg[2]), Integer.parseInt(arg[3]));
				else if (arg.length == 3)
					c.getPA().movePlayer(Integer.parseInt(arg[1]),
							Integer.parseInt(arg[2]), c.heightLevel);
			}
			if (playerCommand.startsWith("getid")) {
				String a[] = playerCommand.split(" ");
				String name = "";
				int results = 0;
				for (int i = 1; i < a.length; i++)
					name = name + a[i] + " ";
				name = name.substring(0, name.length() - 1);
				c.sendMessage("Searching: " + name);
				for (int j = 0; j < Server.itemHandler.ItemList.length; j++) {
					if (Server.itemHandler.ItemList[j] != null)
						if (Server.itemHandler.ItemList[j].itemName
								.replace("_", " ").toLowerCase()
								.contains(name.toLowerCase())) {
							c.sendMessage("@red@"
									+ Server.itemHandler.ItemList[j].itemName
											.replace("_", " ") + " - "
									+ Server.itemHandler.ItemList[j].itemId);
							results++;
						}
				}

				c.sendMessage(results + " results found...");
			}
			if (playerCommand.startsWith("mute")) {
				try {
					String playerToBan = playerCommand.substring(5);
					Connection.addNameToMuteList(playerToBan);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToBan)) {
								Client c2 = (Client) PlayerHandler.players[i];
								c2.sendMessage("You have been muted by: "
										+ c.playerName);
								break;
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("unmute")) {
				try {
					String playerToBan = playerCommand.substring(7);
					Connection.unMuteUser(playerToBan);
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("bank")) {
				c.getPA().openUpBank();
			}
			if (playerCommand.startsWith("item")) {
				try {
					String[] args = playerCommand.split(" ");
					if (args.length == 3) {
						int newItemID = Integer.parseInt(args[1]);
						int newItemAmount = Integer.parseInt(args[2]);
						if ((newItemID <= 20200) && (newItemID >= 0)) {
							c.getItems().addItem(newItemID, newItemAmount);
						} else {
							c.sendMessage("No such item.");
						}
					} else {
						c.sendMessage("Use as ::pickup 995 200");
					}
				} catch (Exception e) {

				}
			}
			if (playerCommand.equalsIgnoreCase("mypos")) {
				c.sendMessage("X: " + c.absX);
				c.sendMessage("Y: " + c.absY);
				c.sendMessage("H: " + c.heightLevel);
			}
			if (playerCommand.startsWith("interface")) {
				try {
					String[] args = playerCommand.split(" ");
					int a = Integer.parseInt(args[1]);
					c.getPA().showInterface(a);
				} catch (Exception e) {
					c.sendMessage("::interface ####");
				}
			}
			if (playerCommand.startsWith("gfx")) {
				String[] args = playerCommand.split(" ");
				c.gfx0(Integer.parseInt(args[1]));
			}
			if (playerCommand.equals("spec")) {
				c.specAmount = 10.0;
			}
			if (playerCommand.startsWith("object")) {
				String[] args = playerCommand.split(" ");
				c.getPA().object(Integer.parseInt(args[1]), c.absX, c.absY, 0,
						10);
			}
			if (playerCommand.startsWith("falem")) {
				String[] args = playerCommand.split(" ");
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.forcedChat(args[1]);
						c2.forcedChatUpdateRequired = true;
						c2.updateRequired = true;
					}
				}
			}

			if (playerCommand.startsWith("npc")) {
				try {
					int newNPC = Integer.parseInt(playerCommand.substring(4));
					if (newNPC > 0) {
						Server.npcHandler.spawnNpc(c, newNPC, c.absX, c.absY,
								0, 0, 120, 7, 70, 70, false, false);
						c.sendMessage("You spawn a Npc.");
					} else {
						c.sendMessage("No such NPC.");
					}
				} catch (Exception e) {

				}
			}
			if (playerCommand.startsWith("ipban")) {
				try {
					String playerToBan = playerCommand.substring(6);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToBan)) {
								if (c.playerName == PlayerHandler.players[i].playerName) {
									c.sendMessage("You cannot IP Ban yourself.");
								} else {
									if (!Connection
											.isIpBanned(PlayerHandler.players[i].connectedFrom)) {
										Connection
												.addIpToBanList(PlayerHandler.players[i].connectedFrom);
										Connection
												.addIpToFile(PlayerHandler.players[i].connectedFrom);
										c.sendMessage("You have IP banned the user: "
												+ PlayerHandler.players[i].playerName
												+ " with the host: "
												+ PlayerHandler.players[i].connectedFrom);
										PlayerHandler.players[i].disconnected = true;
									} else {
										c.sendMessage("This user is already IP Banned.");
									}
								}
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}

			if (playerCommand.startsWith("info")) {
				String player = playerCommand.substring(5);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName
								.equalsIgnoreCase(player)) {
							c.sendMessage("ip: "
									+ PlayerHandler.players[i].connectedFrom);
						}
					}
				}
			}

			if (playerCommand.startsWith("ban")) { // use as ::ban name
				try {
					String playerToBan = playerCommand.substring(4);
					Connection.addNameToBanList(playerToBan);
					Connection.addNameToFile(playerToBan);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToBan)) {
								PlayerHandler.players[i].disconnected = true;
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}

			if (playerCommand.startsWith("unban")) {
				try {
					String playerToBan = playerCommand.substring(6);
					Connection.removeNameFromBanList(playerToBan);
					c.sendMessage(playerToBan + " has been unbanned.");
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("ipmute")) {
				try {
					String playerToBan = playerCommand.substring(7);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToBan)) {
								Connection
										.addIpToMuteList(PlayerHandler.players[i].connectedFrom);
								c.sendMessage("You have IP Muted the user: "
										+ PlayerHandler.players[i].playerName);
								Client c2 = (Client) PlayerHandler.players[i];
								c2.sendMessage("You have been muted by: "
										+ c.playerName);
								break;
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("unipmute")) {
				try {
					String playerToBan = playerCommand.substring(9);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToBan)) {
								Connection
										.unIPMuteUser(PlayerHandler.players[i].connectedFrom);
								c.sendMessage("You have Un Ip-Muted the user: "
										+ PlayerHandler.players[i].playerName);
								break;
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("kick") || c.playerRights == 7) {
				try {
					String playerToBan = playerCommand.substring(5);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToBan)) {
								PlayerHandler.players[i].disconnected = true;
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("demoted")) { // use as ::prm name
				try {
					String playerToG = playerCommand.substring(8);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToG)) {
								PlayerHandler.players[i].playerRights = 0;
								PlayerHandler.players[i].disconnected = true;
								c.sendMessage("You've demoted the user:  "
										+ PlayerHandler.players[i].playerName
										+ " IP: "
										+ PlayerHandler.players[i].connectedFrom);
							}
						}
					}
				} catch (Exception e) {
					// c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("givemod")) { // use as ::prm name
				try {
					String playerToG = playerCommand.substring(8);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToG)) {
								PlayerHandler.players[i].playerRights = 1;
								PlayerHandler.players[i].disconnected = true;
								c.sendMessage("You've promoted to moderator the user:  "
										+ PlayerHandler.players[i].playerName
										+ " IP: "
										+ PlayerHandler.players[i].connectedFrom);
							}
						}
					}
				} catch (Exception e) {
					// c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("givepts")) { // use as ::prm name
				try {
					String playerToG = playerCommand.substring(8);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToG)) {
								PlayerHandler.players[i].donPoints += 500;
								// PlayerHandler.players[i].disconnected = true;
								c.sendMessage("You've give donator points to the user:  "
										+ PlayerHandler.players[i].playerName
										+ " IP: "
										+ PlayerHandler.players[i].connectedFrom);
							}
						}
					}
				} catch (Exception e) {
					// c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("givebro")) { // use as ::prm name
				try {
					String playerToG = playerCommand.substring(8);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToG)) {
								PlayerHandler.players[i].playerRights = 4;
								PlayerHandler.players[i].disconnected = true;
								c.sendMessage("You've promoted to bronze donator the user:  "
										+ PlayerHandler.players[i].playerName
										+ " IP: "
										+ PlayerHandler.players[i].connectedFrom);
							}
						}
					}
				} catch (Exception e) {
					// c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("givesil")) { // use as ::prm name
				try {
					String playerToG = playerCommand.substring(8);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToG)) {
								PlayerHandler.players[i].playerRights = 5;
								PlayerHandler.players[i].disconnected = true;
								c.sendMessage("You've promoted to silver donator the user:  "
										+ PlayerHandler.players[i].playerName
										+ " IP: "
										+ PlayerHandler.players[i].connectedFrom);
							}
						}
					}
				} catch (Exception e) {
					// c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("givegol")) { // use as ::prm name
				try {
					String playerToG = playerCommand.substring(8);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToG)) {
								PlayerHandler.players[i].playerRights = 6;
								PlayerHandler.players[i].disconnected = true;
								c.sendMessage("You've promoted to gold donator the user:  "
										+ PlayerHandler.players[i].playerName
										+ " IP: "
										+ PlayerHandler.players[i].connectedFrom);
							}
						}
					}
				} catch (Exception e) {
					// c.sendMessage("Player Must Be Offline.");
				}
			}

			if (playerCommand.startsWith("update")) {
				String[] args = playerCommand.split(" ");
				int a = Integer.parseInt(args[1]);
				PlayerHandler.updateSeconds = a;
				PlayerHandler.updateAnnounced = false;
				PlayerHandler.updateRunning = true;
				PlayerHandler.updateStartTime = System.currentTimeMillis();
			}
			if (playerCommand.startsWith("emote")) {
				String[] args = playerCommand.split(" ");
				c.startAnimation(Integer.parseInt(args[1]));
				c.getPA().requestUpdates();
			}
			if (playerCommand.equalsIgnoreCase("red")) {
				c.headIconPk = (1);
				c.getPA().requestUpdates();
			}
			if (playerCommand.startsWith("meleemaxhit")) {
				c.sendMessage("Melee Max Hit: "
						+ c.getCombat().calculateMeleeMaxHit() + "");
			}
			if (playerCommand.startsWith("reloadshops")) {
				Server.shopHandler = new main.world.ShopHandler();
			}

			if (playerCommand.startsWith("checkinv")) {
				try {
					String[] args = playerCommand.split(" ", 2);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						Client o = (Client) PlayerHandler.players[i];
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(args[1])) {
								c.getPA().otherInv(c, o);
								c.getDH().sendDialogues(206, 0);
								break;
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}

			if (playerCommand.startsWith("checkbank")) {
				try {
					String[] args = playerCommand.split(" ", 2);
					for (int i = 0; i < PlayerHandler.players.length; i++) {
						Client o = (Client) PlayerHandler.players[i];
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(args[1])) {
								c.isBanking = true;
								c.getPA().otherBank(c, o);
								break;
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
		}
		/* Bronze donator commands */
		if (c.playerRights == 1 || c.playerRights == 2 || c.playerRights == 3
				|| c.playerRights == 4 || c.playerRights == 5
				|| c.playerRights == 7) {
			if (playerCommand.equals("dz")) {
				c.getPA().startTeleport(2337, 9804, 0, "modern");
				c.sendMessage("Donator zone is currently in development.");
			}
			if (playerCommand.equals("dshop")) {
				c.getShops().openShop(11);
			}
			/*
			 * if (playerCommand.startsWith("resettask")) { c.taskAmount = -1;
			 * //vars c.slayerTask = 0; //vars
			 * c.sendMessage("Your slayer task has been reseted sucessfully.");
			 * c.getPA().sendFrame126("@whi@Task: @gre@Empty", 7383); }
			 */
		}

		/* Silver donator commands */
		if (c.playerRights == 5 || c.playerRights == 1
				|| c.playerName.equalsIgnoreCase("")) {
			if (playerCommand.equals("spec")) {
				if (System.currentTimeMillis() - c.specCom >= 60000) {
					if (c.inWild()) {
						c.sendMessage("You cannot restore special attack in the wilderness!");
						return;
					}
					if (c.duelStatus == 5) {
						c.sendMessage("You cannot restore your special attack during a duel.");
						return;
					}
					c.specCom = System.currentTimeMillis();
					c.specAmount = 10.0;
					c.getItems().addSpecialBar(
							c.playerEquipment[c.playerWeapon]);
				} else {
					c.sendMessage("You must wait 60 seconds to restore your special attack.");
				}
			}

			if (playerCommand.startsWith("giveitem") && c.playerRights == 3) {
				try {
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						String a[] = playerCommand.split("_");
						if (a.length == 4) {
							String playerToGiveItem = a[1];
							int newItemId = Integer.parseInt(a[2]);
							int newItemAmount = Integer.parseInt(a[3]);
							if (PlayerHandler.players[i] != null) {
								if (PlayerHandler.players[i].playerName
										.equalsIgnoreCase(playerToGiveItem)) {
									Client c2 = (Client) PlayerHandler.players[i];
									if (c2.getItems().freeSlots() > newItemAmount - 1) {
										c2.getItems().addItem(newItemId,
												newItemAmount);
										c2.sendMessage("You have just been given "
												+ newItemAmount
												+ " of item: "
												+ c2.getItems().getItemName(
														newItemId)
												+ " by: "
												+ Misc.optimizeText(c.playerName));
									} else {
										c2.getItems().addBankItem(newItemId,
												newItemAmount);
										c2.sendMessage("You have just been given "
												+ newItemAmount
												+ " of item: "
												+ c2.getItems().getItemName(
														newItemId)
												+ " by: "
												+ Misc.optimizeText(c.playerName));
										c2.sendMessage("It is in your bank because you didn't have enough space in your inventory.");
									}
									c.sendMessage("You have just given "
											+ newItemAmount
											+ " of item number: "
											+ c.getItems().getItemName(
													newItemId) + ".");
									return;
								}
							}
						} else {
							c.sendMessage("Wrong usage: (Ex:(::giveitem_playerName_itemId_itemAmount)(::giveitem_player_995_1))");
							return;
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}

			if (playerCommand.equalsIgnoreCase("spells")) {
				if (c.inWild()) {
					c.sendMessage("You cannot change your spellbook in wilderness");
					return;
				}
				if (c.duelStatus == 5) {
					c.sendMessage("You cannot change your spellbook during a duel.");
					return;
				}
				if (c.playerMagicBook == 2) {
					c.sendMessage("You switch to modern magic.");
					c.setSidebarInterface(6, 1151);
					c.playerMagicBook = 0;
				} else if (c.playerMagicBook == 0) {
					c.sendMessage("You switch to ancient magic.");
					c.setSidebarInterface(6, 12855);
					c.playerMagicBook = 1;
				} else if (c.playerMagicBook == 1) {
					c.sendMessage("You switch to lunar magic.");
					c.setSidebarInterface(6, 29999);
					c.playerMagicBook = 2;
				}
			}
		}

	}
}