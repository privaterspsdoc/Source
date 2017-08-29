package main.model.players.packets;

import main.Config;
import main.Server;
import main.model.items.GameItem;
import main.model.npcs.NPCHandler;
import main.model.players.Client;
import main.model.players.Player;
import main.model.players.DiceHandler;
import main.model.players.PacketType;
import main.model.players.PlayerHandler;
import main.model.players.skills.Cooking;
import main.model.players.skills.DuoSlayer;
import main.util.Misc;
import main.model.players.clan.Clan;

/**
 * Clicking most buttons
 **/
public class ClickingButtons implements PacketType {

	@Override
	public void processPacket(final Client c, int packetType, int packetSize) {
		int actionButtonId = Misc.hexToInt(c.getInStream().buffer, 0,
				packetSize);
		if (c.isDead)
			return;
		if (c.playerRights == 3)
			Misc.println(c.playerName + " - actionbutton: " + actionButtonId);
		switch (actionButtonId) {
		case 3189:
			if (c.splitChat == false) {
			c.getPA().sendFrame36(502, 1);
			c.getPA().sendFrame36(287, 1);
			c.splitChat = true;
			} else if (c.splitChat == true) {
			c.getPA().sendFrame36(502, 0);
			c.getPA().sendFrame36(287, 0);
			c.splitChat = false;
			}
		break;
		case 3145:
			c.sendMessage(":togglexp:");
			break;
		case 3147:
			if (c.resize == false) {
				c.sendMessage(":resize:");
			c.resize = true;
			} else if (c.resize == true) {
			c.sendMessage(":fixed:");
			c.resize = false;
			}
			break;
		/* Lamp */
		case 10252:
			c.antiqueSelect = 0;
			c.sendMessage("You select Attack");
			break;
		case 10253:
			c.antiqueSelect = 2;
			c.sendMessage("You select Strength");
			break;
		case 10254:
			c.antiqueSelect = 4;
			c.sendMessage("You select Ranged");
			break;
		case 10255:
			c.antiqueSelect = 6;
			c.sendMessage("You select Magic");
			break;
		case 11000:
			c.antiqueSelect = 1;
			c.sendMessage("You select Defence");
			break;
		case 11001:
			c.antiqueSelect = 3;
			c.sendMessage("You select Hitpoints");
			break;
		case 11002:
			c.antiqueSelect = 5;
			c.sendMessage("You select Prayer");
			break;
		case 11003:
			c.antiqueSelect = 16;
			c.sendMessage("You select Agility");
			break;
		case 11004:
			c.antiqueSelect = 15;
			c.sendMessage("You select Herblore");
			break;
		case 11005:
			c.antiqueSelect = 17;
			c.sendMessage("You select Thieving");
			break;
		case 11006:
			c.antiqueSelect = 12;
			c.sendMessage("You select Crafting");
			break;
		case 11007:
			c.antiqueSelect = 20;
			c.sendMessage("You select Runecrafting");
			break;
		case 47002:
			c.antiqueSelect = 18;
			c.sendMessage("You select Slayer");
			break;
		case 54090:
			c.antiqueSelect = 19;
			c.sendMessage("You select Farming");
			break;
		case 11008:
			c.antiqueSelect = 14;
			c.sendMessage("You select Mining");
			break;
		case 11009:
			c.antiqueSelect = 13;
			c.sendMessage("You select Smithing");
			break;
		case 11010:
			c.antiqueSelect = 10;
			c.sendMessage("You select Fishing");
			break;
		case 11011:
			c.antiqueSelect = 7;
			c.sendMessage("You select Cooking");
			break;
		case 11012:
			c.antiqueSelect = 11;
			c.sendMessage("You select Firemaking");
			break;
		case 11013:
			c.antiqueSelect = 8;
			c.sendMessage("You select Woodcutting");
			break;
		case 11014:
			c.antiqueSelect = 9;
			c.sendMessage("You select Fletching");
			break;
		case 11015:
			if (c.usingLamp) {
				if (c.antiqueLamp && !c.normalLamp) {
					c.usingLamp = false;
					c.getPA().addSkillXP(13100000, c.antiqueSelect);
					c.getItems().deleteItem2(4447, 1);
					c.sendMessage("The lamp mysteriously vanishes...");
					c.getPA().closeAllWindows();
				}
				if (c.normalLamp && !c.antiqueLamp) {
					c.usingLamp = false;
					c.getPA().addSkillXP(1000000, c.antiqueSelect);
					c.getItems().deleteItem2(2528, 1);
					c.sendMessage("The lamp mysteriously vanishes...");
					c.getPA().closeAllWindows();
				}
			} else {
				c.sendMessage("You must rub a lamp to gain the experience.");
				return;
			}
			break;
		/* End lamp */
//clan
			/*
		case 145009:
			if (c.clanId >= 0) {
				c.sendMessage("You are already in a clan chat!");
				break;
			}
			if (c.getOutStream() != null) {
				c.getOutStream().createFrame(187);
				c.flushOutStream();
				//c.clanId = 5;
			}
			break;
		case 145012:
			c.getPA().showInterface(37300);
			break;
			*/
		
//end clan
		case 91182:
			c.Summoning().ItemonItem(2859, 12225);
			// c.getItems().makeSpiritWolf(2859, 12225);
			// c.getPA().refreshSkill(22);
			break;

		case 91183:
			c.Summoning().ItemonItem(2138, 12225);
			c.getPA().refreshSkill(22);
			break;

		case 91184:
			c.Summoning().ItemonItem(6291, 12225);
			break;

		case 91185:
			c.Summoning().ItemonItem(440, 12225);
			break;

		case 91186:
			c.Summoning().ItemonItem(12168, 12225);
			break;

		case 91187:
			c.Summoning().ItemonItem(3138, 12225);
			break;

		case 91188:
			// c.Summoning().ItemonItem(12164, 12225);
			c.sendMessage("[Pouch not available.]");
			break;

		case 91189:
			// c.Summoning().ItemonItem(12165, 12225);
			c.sendMessage("[Pouch not available.]");
			break;

		case 91190:
			// c.Summoning().ItemonItem(, 12225);
			c.sendMessage("[Pouch not available.]");
			break;

		case 91191:
			// c.Summoning().ItemonItem(12167, 12225);
			c.sendMessage("[Pouch not available.]");
			break;

		case 91192:
			c.Summoning().ItemonItem(2349, 12225);
			break;

		case 91193:
			//c.Summoning().ItemonItem(249, 12225);
			c.sendMessage("[Pouch not available.]");
			break;

		case 91194:
			c.Summoning().ItemonItem(2351, 12225);
			// c.sendMessage("[Pouch not available.]");
			break;

		case 91195:
			c.sendMessage("[Pouch not available.]");
			// c.Summoning().ItemonItem(13403, 12225);
			break;

		case 91196:
			c.Summoning().ItemonItem(9978, 12225);
			break;

		case 91197:
			c.Summoning().ItemonItem(2353, 12225);
			break;

		case 91198:
			// c.Summoning().ItemonItem(, 12225);
			c.sendMessage("[Pouch not available.]");
			break;

		case 91199:
			// c.Summoning().ItemonItem(, 12225);
			c.sendMessage("[Pouch not available.]");
			break;

		case 91200:
			c.sendMessage("[Pouch not available.]");
			// c.Summoning().ItemonItem(, 12225);
			break;

		case 91201:
			c.Summoning().ItemonItem(2359, 12225);
			break;

		case 91202:
			c.sendMessage("[Pouch not available.]");
			// c.Summoning().ItemonItem(, 12225);
			break;

		case 91203:
			c.sendMessage("[Pouch not available.]");
			// c.Summoning().ItemonItem(, 12225);
			break;

		case 91204:
			c.sendMessage("[Pouch not available.]");
			// c.Summoning().ItemonItem(, 12225);
			break;

		case 91205:
			c.sendMessage("[Pouch not available.]");
			// c.Summoning().ItemonItem(, 12225);
			break;

		case 91206:
			// c.sendMessage("[Pouch not available.]");
			c.Summoning().ItemonItem(2361, 12225);
			break;

		case 91207:
			c.sendMessage("[Pouch not available.]");
			// c.Summoning().ItemonItem(, 12225);
			break;

		case 91208:
			c.sendMessage("[Pouch not available.]");
			// c.Summoning().ItemonItem(, 12225);
			break;

		case 91209:
			c.sendMessage("[Pouch not available.]");
			// c.Summoning().ItemonItem(, 12225);
			break;

		case 91210:
			c.sendMessage("[Pouch not available.]");
			// c.Summoning().ItemonItem(, 12225);
			break;

		case 91211:
			c.Summoning().ItemonItem(2363, 12225);
			break;

		case 91212:
			c.sendMessage("[Pouch not available.]");
			// c.Summoning().ItemonItem(, 12225);
			break;

		case 91213:
			c.sendMessage("[Pouch not available.]");
			// c.Summoning().ItemonItem(, 12225);
			break;

		case 91214:
			c.sendMessage("[Pouch not available.]");
			// c.Summoning().ItemonItem(, 12225);
			break;

		case 91215:
			c.Summoning().ItemonItem(1444, 12225);
			break;

		case 91216:
			c.sendMessage("[Pouch not available.]");
			// c.Summoning().ItemonItem(, 12225);
			break;

		case 91217:
			c.Summoning().ItemonItem(10818, 12225);
			break;

		case 91218:
			c.Summoning().ItemonItem(1119, 12225);
			break;
			
		case 226170:
		case 89236:
		for (int i = 0; i < c.playerEquipment.length; i++) {
			int itemId = c.playerEquipment[i];
			int itemAmount = c.playerEquipmentN[i];
			c.getItems().removeItem(itemId, i);
			c.getItems().bankItem(itemId, c.getItems().getItemSlot(itemId), itemAmount);
		}
		c.sendMessage("You successfully bank all your worn items.");
		break;

		case 89223: // Bank All
		case 226162:
		case 86000:
			for (int i = 0; i < c.playerItems.length; i++) {
				c.getItems().bankItem(c.playerItems[i], i, c.playerItemsN[i]);
			}
			c.sendMessage("You successfully bank your inventory.");
			break;

		/* Quest Tab by Ardi */
		case 28164: // 1st
		case 113235:
			c.sendMessage("Your username is: @red@" + c.playerName + "@bla@.");
			break;
		case 113241: // 2nd
			c.sendMessage("You have @red@" + c.pkp + " @bla@Pk Points.");
			break;
		case 28165: // 3rd
		case 113237:
			c.sendMessage("You have killed: @red@" + c.KC + " @bla@times.");
			break;
		case 28166: // 4th
		case 113238:
			c.sendMessage("You have died: @red@" + c.DC + " @bla@times.");
			break;
		case 113240:
			if (c.slayerTask <= 0) {
				c.sendMessage("You do not have a task, please talk with a slayer master.");
			} else {
				c.forcedText = "I must slay another " + c.taskAmount + " "
						+ Server.npcHandler.getNpcListName(c.slayerTask) + ".";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			}
			break;
			
		case 154:
			if(c.getPA().wearingCape(c.playerEquipment[c.playerCape])) {
			c.stopMovement();
			c.gfx0(c.getPA().skillcapeGfx(c.playerEquipment[c.playerCape]));
			c.startAnimation(c.getPA().skillcapeEmote(c.playerEquipment[c.playerCape]));
			} else {
			c.sendMessage("You must be wearing a skillcape to do this emote.");
			}
			break;
		/* End Quest */

		case 15163:
							if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(444, 1) && (c.playerLevel[13] >= 40)) {
						c.sendMessage("You begin to smelt a gold bar.");
						c.getItems().deleteItem(444, 1);
						c.startAnimation(899);
						c.getItems().addItem(2357, 1);
						c.getPA().addSkillXP(23 * Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need atleast 40 smithing and a gold ore to smelt a gold bar.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
				break;
				
						case 15162:
							if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(444, 5) && (c.playerLevel[13] >= 40)) {
						c.sendMessage("You begin to smelt 5 gold bars.");
						c.getItems().deleteItem(444, 5);
						c.startAnimation(899);
						c.getItems().addItem(2357, 5);
						c.getPA().addSkillXP(23 *5* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need atleast 40 smithing and 5 gold ore to smelt 5 gold bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
				break;
				
						case 15161:
							if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(444, 10) && (c.playerLevel[13] >= 40)) {
						c.sendMessage("You begin to smelt 10 gold bars.");
						c.getItems().deleteItem(444, 10);
						c.startAnimation(899);
						c.getItems().addItem(2357, 10);
						c.getPA().addSkillXP(23 *10* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need atleast 40 smithing and 10 gold ore to smelt 10 gold bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
				break;
				
						case 15160:
							if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(444, 28) && (c.playerLevel[13] >= 40)) {
						c.sendMessage("You begin to smelt 28 gold bars.");
						c.getItems().deleteItem(444, 28);
						c.startAnimation(899);
						c.getItems().addItem(2357, 28);
						c.getPA().addSkillXP(23 *28* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need atleast 40 smithing and 28 gold ore to smelt 28 gold bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
				break;
				
						case 15147:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(436, 1) && c.getItems().playerHasItem(438, 1)) {
						c.sendMessage("You begin to smelt a bronze bar.");
						c.getItems().deleteItem(436, 1);
						c.getItems().deleteItem(438, 1);
						c.startAnimation(899);
						c.getItems().addItem(2349, 1);
						c.getPA().addSkillXP(6 * Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need a copper and tin ore to smelt a bronze bar.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
				break;
			
			case 15146:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(436, 5) && c.getItems().playerHasItem(438, 5)) {
						c.sendMessage("You begin to smelt 5 bronze bars.");
						c.getItems().deleteItem(436, 5);
						c.getItems().deleteItem(438, 5);
						c.startAnimation(899);
						c.getItems().addItem(2349, 5);
						c.getPA().addSkillXP(6 *5* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need 5 copper and 5 tin ore to smelt 5 bronze bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			
			case 15247:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(436, 10) && c.getItems().playerHasItem(438, 10)) {
						c.sendMessage("You begin to smelt 10 bronze bars.");
						c.getItems().deleteItem(436, 10);
						c.getItems().deleteItem(438, 10);
						c.startAnimation(899);
						c.getItems().addItem(2349, 10);
						c.getPA().addSkillXP(6 *10* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need 10 copper and 10 tin ore to smelt 10 bronze bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			
			case 9110:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(436, 14) && c.getItems().playerHasItem(438, 14)) {
						c.sendMessage("You begin to smelt 14 bronze bars.");
						c.getItems().deleteItem(436, 14);
						c.getItems().deleteItem1(438, 14);
						c.startAnimation(899);
						c.getItems().addItem(2349, 14);
						c.getPA().addSkillXP(6 *14* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need 14 copper and 14 tin ore to smelt 14 bronze bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			
			case 15151:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(440, 1) && (c.playerLevel[13] >= 15)) {
						c.sendMessage("You begin to smelt an iron bar.");
						c.getItems().deleteItem1(440, 1);
						c.startAnimation(899);
						c.getItems().addItem(2351, 1);
						c.getPA().addSkillXP(13 * Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 15 smithing and an iron ore to smelt an iron bar.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			
			case 15149:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(440, 10) && (c.playerLevel[13] >= 15)) {
						c.sendMessage("You begin to smelt 10 iron bars.");
						c.getItems().deleteItem1(440, 10);
						c.startAnimation(899);
						c.getItems().addItem(2351, 10);
						c.getPA().addSkillXP(13 *10* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 15 smithing and 10 iron ore to smelt an iron bar.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			
			case 15150:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(440, 5) && (c.playerLevel[13] >= 15)) {
						c.sendMessage("You begin to smelt 5 iron bars.");
						c.getItems().deleteItem1(440, 5);
						c.startAnimation(899);
						c.getItems().addItem(2351, 5);
						c.getPA().addSkillXP(13 *5* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 15 smithing and 5 iron ore to smelt an iron bar.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			
			case 15148:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(440, 28) && (c.playerLevel[13] >= 15)) {
						c.sendMessage("You begin to smelt 28 iron bars.");
						c.getItems().deleteItem1(440, 28);
						c.startAnimation(899);
						c.getItems().addItem(2351, 28);
						c.getPA().addSkillXP(13 *28* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 15 smithing and 28 iron ore to smelt an iron bar.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			
			case 15159:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(453, 2) && c.getItems().playerHasItem(440, 1) && (c.playerLevel[13] >= 30)) {
						c.sendMessage("You begin to smelt a steel bar.");
						c.getItems().deleteItem1(440, 1);
						c.getItems().deleteItem1(453, 2);
						c.startAnimation(899);
						c.getItems().addItem(2353, 1);
						c.getPA().addSkillXP(18 * Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 30 smithing an iron ore and 2 coal to smelt a steel bar.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			
			case 15158:
					if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(453, 10) && c.getItems().playerHasItem(440, 5) && (c.playerLevel[13] >= 30)) {
						c.sendMessage("You begin to smelt 5 steel bars.");
						c.getItems().deleteItem1(440, 5);
						c.getItems().deleteItem1(453, 10);
						c.startAnimation(899);
						c.getItems().addItem(2353, 5);
						c.getPA().addSkillXP(18 *5* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 30 smithing 5 iron ore and 10 coal to smelt 5 steel bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			
			case 15157:
					if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(453, 20) && c.getItems().playerHasItem(440, 8) && (c.playerLevel[13] >= 30)) {
						c.sendMessage("You begin to smelt 10 steel bars.");
						c.getItems().deleteItem1(440, 8);
						c.getItems().deleteItem1(453, 20);
						c.startAnimation(899);
						c.getItems().addItem(2353, 10);
						c.getPA().addSkillXP(18 *10* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 30 smithing 8 iron ore and 20 coal to smelt 10 steel bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			
			case 15156:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(453, 20) && c.getItems().playerHasItem(440, 8) && (c.playerLevel[13] >= 30)) {
						c.sendMessage("You begin to smelt 10 steel bars.");
						c.getItems().deleteItem1(440, 8);
						c.getItems().deleteItem1(453, 20);
						c.startAnimation(899);
						c.getItems().addItem(2353, 10);
						c.getPA().addSkillXP(18 *10* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 30 smithing 8 iron ore and 20 coal to smelt 10 steel bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			
			case 29017:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(447, 1) && (c.playerLevel[13] >= 50)) {
						c.sendMessage("You begin to smelt a mithril bar.");
						c.getItems().deleteItem1(447, 1);
						c.startAnimation(899);
						c.getItems().addItem(2359, 1);
						c.getPA().addSkillXP(30 * Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 50 smithing and a mithril ore to smelt a mithril bar.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			
			case 29016:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(447, 5) && (c.playerLevel[13] >= 50)) {
						c.sendMessage("You begin to smelt 5 mithril bars.");
						c.getItems().deleteItem1(447, 5);
						c.startAnimation(899);
						c.getItems().addItem(2359, 5);
						c.getPA().addSkillXP(30 *5* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 50 smithing and 5 mithril ore to smelt 5 mithril bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			
			case 24253:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(447, 10) && (c.playerLevel[13] >= 50)) {
						c.sendMessage("You begin to smelt 10 mithril bars.");
						c.getItems().deleteItem1(447, 10);
						c.startAnimation(899);
						c.getItems().addItem(2359, 10);
						c.getPA().addSkillXP(30 *10* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 50 smithing and 10 mithril ore to smelt 10 mithril bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			
			case 16062:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(447, 28) && (c.playerLevel[13] >= 50)) {
						c.sendMessage("You begin to smelt 28 mithril bars.");
						c.getItems().deleteItem1(447, 28);
						c.startAnimation(899);
						c.getItems().addItem(2359, 28);
						c.getPA().addSkillXP(30 *28* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 50 smithing and 28 mithril ore to smelt a mithril bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			case 29022:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(449, 1) && (c.playerLevel[13] >= 70)) {
						c.sendMessage("You begin to smelt an adamant bar.");
						c.getItems().deleteItem1(449, 1);
						c.startAnimation(899);
						c.getItems().addItem(2361, 1);
						c.getPA().addSkillXP(38 * Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 70 smithing and an adamant ore to smelt an adamant bar.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;

			case 29020:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(449, 5) && (c.playerLevel[13] >= 70)) {
						c.sendMessage("You begin to smelt 5 adamant bars.");
						c.getItems().deleteItem1(449, 5);
						c.startAnimation(899);
						c.getItems().addItem(2361, 5);
						c.getPA().addSkillXP(38 *5* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 70 smithing and 5 adamant ore to smelt 5 adamant bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			case 29019:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(449, 10) && (c.playerLevel[13] >= 70)) {
						c.sendMessage("You begin to smelt 10 adamant bars.");
						c.getItems().deleteItem1(449, 10);
						c.startAnimation(899);
						c.getItems().addItem(2361, 10);
						c.getPA().addSkillXP(38 *10* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 70 smithing and 10 adamant ore to smelt 10 adamant bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			case 29018:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(449, 28) && (c.playerLevel[13] >= 70)) {
						c.sendMessage("You begin to smelt 28 adamant bars.");
						c.getItems().deleteItem1(449, 28);
						c.startAnimation(899);
						c.getItems().addItem(2361, 28);
						c.getPA().addSkillXP(38 *28* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 70 smithing and 28 adamant ore to smelt 28 adamant bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			case 29026:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(451, 1) && (c.playerLevel[13] >= 85)) {
						c.sendMessage("You begin to smelt a rune bar..");
						c.getItems().deleteItem1(451, 1);
						c.startAnimation(899);
						c.getItems().addItem(2363, 1);
						c.getPA().addSkillXP(50 * Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 85 smithing and a rune ore to smelt a rune bar.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			
			case 29025:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(451, 5) && (c.playerLevel[13] >= 85)) {
						c.sendMessage("You begin to smelt 5 rune bars..");
						c.getItems().deleteItem1(451, 5);
						c.startAnimation(899);
						c.getItems().addItem(2363, 5);
						c.getPA().addSkillXP(50 *5* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 85 smithing and 5 rune ore to smelt 5 rune bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
				
			case 29024:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(451, 10) && (c.playerLevel[13] >= 85)) {
						c.sendMessage("You begin to smelt 10 rune bars..");
						c.getItems().deleteItem1(451, 10);
						c.startAnimation(899);
						c.getItems().addItem(2363, 10);
						c.getPA().addSkillXP(50 *10* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 85 smithing and 10 rune ore to smelt 10 rune bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
				
			case 29023:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(451, 28) && (c.playerLevel[13] >= 85)) {
						c.sendMessage("You begin to smelt 28 rune bars..");
						c.getItems().deleteItem1(451, 28);
						c.startAnimation(899);
						c.getItems().addItem(2363, 28);
						c.getPA().addSkillXP(50 *28* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 85 smithing and 28 rune ore to smelt 28 rune bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;

		case 53152:
			Cooking.getAmount(c, 1);
			break;
		case 53151:
			Cooking.getAmount(c, 5);
			break;
		case 53150:
			Cooking.getAmount(c, 10);
			break;
		case 53149:
			Cooking.getAmount(c, 28);
			break;

		case 121135: // attack
			c.getSI().attackComplex(1);
			c.getSI().selected = 0;
			break;
		case 121140: // strength
			c.getSI().strengthComplex(1);
			c.getSI().selected = 1;
			break;
		case 121145: // Defence
			c.getSI().defenceComplex(1);
			c.getSI().selected = 2;
			break;
		case 121150: // range
			c.getSI().rangedComplex(1);
			c.getSI().selected = 3;
			break;
		case 121155: // prayer
			c.getSI().prayerComplex(1);
			c.getSI().selected = 4;
			break;
		case 121160: // mage
			c.getSI().magicComplex(1);
			c.getSI().selected = 5;
			break;
		case 121165: // runecrafting
			c.getSI().runecraftingComplex(1);
			c.getSI().selected = 6;
			break;
		case 121180: // hp
			c.getSI().hitpointsComplex(1);
			c.getSI().selected = 7;
			break;
		case 121185: // agility
			c.getSI().agilityComplex(1);
			c.getSI().selected = 8;
			break;
		case 121190: // herblore
			c.getSI().herbloreComplex(1);
			c.getSI().selected = 9;
			break;
		case 121195: // theiving
			c.getSI().thievingComplex(1);
			c.getSI().selected = 10;
			break;
		case 121200: // crafting
			c.getSI().craftingComplex(1);
			c.getSI().selected = 11;
			break;
		case 121205: // fletching
			c.getSI().fletchingComplex(1);
			c.getSI().selected = 12;
			break;
		case 121210:// slayer
			c.getSI().slayerComplex(1);
			c.getSI().selected = 13;
			break;
		case 121235: // fishing
			c.getSI().fishingComplex(1);
			c.getSI().selected = 16;
			break;
		case 121240: // cooking
			c.getSI().cookingComplex(1);
			c.getSI().selected = 17;
			break;
		case 121245: // firemaking
			c.getSI().firemakingComplex(1);
			c.getSI().selected = 18;
			break;
		case 121250: // woodcut
			c.getSI().woodcuttingComplex(1);
			c.getSI().selected = 19;
			break;
		case 121255: // farming
			c.getSI().farmingComplex(1);
			c.getSI().selected = 20;
			break;

		case 34142: // tab 1
			c.getSI().menuCompilation(1);
			break;

		case 34119: // tab 2
			c.getSI().menuCompilation(2);
			break;

		case 34120: // tab 3
			c.getSI().menuCompilation(3);
			break;

		case 34123: // tab 4
			c.getSI().menuCompilation(4);
			break;

		case 34133: // tab 5
			c.getSI().menuCompilation(5);
			break;

		case 34136: // tab 6
			c.getSI().menuCompilation(6);
			break;

		case 34139: // tab 7
			c.getSI().menuCompilation(7);
			break;

		case 34155: // tab 8
			c.getSI().menuCompilation(8);
			break;

		case 34158: // tab 9
			c.getSI().menuCompilation(9);
			break;

		case 34161: // tab 10
			c.getSI().menuCompilation(10);
			break;

		case 59199: // tab 11
			c.getSI().menuCompilation(11);
			break;

		case 59202: // tab 12
			c.getSI().menuCompilation(12);
			break;
		case 59203: // tab 13
			c.getSI().menuCompilation(13);
			break;

		case 150:
			if (c.autoRet == 0)
				c.autoRet = 1;
			else
				c.autoRet = 0;
			break;
		// 1st tele option
		case 9190:
			if (c.teleAction == 35){
				c.getPA().spellTeleport(2866, 3546, 0);
				c.sendMessage("You teleport to the Warriors Guild.");
				c.sendMessage("Talk to the women at upstairs to enter the cyclops room.");
				c.sendMessage("You'll need to get some tokens via the animated armour to enter.");
				c.getPA().closeAllWindows();
			}
			if (c.teleAction == 16) {
				//c.getPA().spellTeleport(3301, 3840, 0);
				//c.sendMessage("You teleport to Callisto");
				c.sendMessage("Callisto is currently disabled, sorry guys.");
				c.getPA().closeAllWindows();
			}
			if (c.teleAction == 45) {
				c.getPA().spellTeleport(2725, 3485, 0);
				c.getPA().closeAllWindows();
				c.sendMessage("You teleport to the woodcutting area.");
			}
			if (c.teleAction == 691) {
				c.getPA().spellTeleport(2678, 3718, 0);
				c.sendMessage("You teleport to the Rock Crabs.");
				c.getPA().closeAllWindows();
			}
			if (c.teleAction == 1) {
				// TAV DUN
				c.getPA().startTeleport2(2884, 9798, 0);
				c.sendMessage("You teleport to Taverly Dungeon.");
				//c.getPA().spellTeleport(2678, 3718, 0);
				//c.sendMessage("You teleport to the Rock Crabs.");
				c.getPA().closeAllWindows();
			}
			if (c.teleAction == 15) {
				c.getPA().spellTeleport(1914, 4367, 0);
				c.sendMessage("You teleport to the Daggonoth Kings.");
				c.sendMessage("Climb down the ladders to access the lair.");
				c.getPA().closeAllWindows();
			} else if (c.teleAction == 2) {
				// barrows
				c.getPA().spellTeleport(3565, 3314, 0);
				c.sendMessage("You teleport to the barrows minigame.");
				// c.getItems().addItem(952, 1);
				c.getPA().closeAllWindows();
			} else if (c.teleAction == 3) {
				c.getPA().spellTeleport(2273, 4687, 0);
				c.sendMessage("You teleport to The King Black Dragon.");
				c.getPA().closeAllWindows();
			}
			if (c.teleAction == 12) {
				c.getPA().spellTeleport(3302, 9361, 0);
			}
			if (c.dialogueAction == 200) {
				c.sendMessage("You teleport to Rock Crabs.");
				c.getPA().movePlayer(2672, 3709, 0);
			}
			if (c.dialogueAction == 201) {
				c.sendMessage("You teleport to the Godwars Dungeon.");
				c.getPA().movePlayer(2872, 5318, 2);
			}
			if (c.teleAction == 11) {
				c.getPA().spellTeleport(3228, 9392, 0);
			}
			if (c.teleAction == 10) {
				c.getPA().spellTeleport(2705, 9487, 0);
			}
			if (c.teleAction == 9) {
				c.getPA().spellTeleport(3226, 3263, 0);
			}
			if (c.teleAction == 8) {
				c.getPA().spellTeleport(3293, 3178, 0);
			}
			if (c.teleAction == 7) {
				c.getPA().spellTeleport(3118, 9851, 0);
			}
			if (c.teleAction == 1) {
				// rock crabs
				c.getPA().spellTeleport(2678, 3718, 0);
			} else if (c.teleAction == 2) {
				// barrows
				// c.getPA().spellTeleport(3565, 3314, 0);
				// c.getItems().addItem(952, 1);
			} else if (c.teleAction == 3) {
				// kbd
				c.getPA().spellTeleport(2273, 4687, 0);
			} else if (c.teleAction == 4) {
				// varrock wildy
				c.getPA().spellTeleport(2539, 4716, 0);
			} else if (c.teleAction == 5) {
				c.getPA().spellTeleport(3046, 9779, 0);
			} else if (c.teleAction == 20) {
				// lum
				c.getPA().spellTeleport(3222, 3218, 0);// 3222 3218
				c.sendMessage("You teleport to lumbridge.");
				c.getPA().closeAllWindows();
			} else {
				DiceHandler.handleClick(c, actionButtonId);
			}

			if (c.dialogueAction == 10) {
				c.getPA().spellTeleport(2845, 4832, 0);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 11) {
				c.getPA().spellTeleport(2786, 4839, 0);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 12) {
				c.getPA().spellTeleport(2398, 4841, 0);
				c.dialogueAction = -1;
			}
			break;
		// mining - 3046,9779,0
		// smithing - 3079,9502,0

		// 2nd tele option
		case 9191:
			if (c.teleAction == 691) {
				c.getPA().spellTeleport(3045, 9541, 0);
				c.sendMessage("Head into the north-east cave to go to skeletal wyverns.");
				c.getPA().closeAllWindows();
			}
			if (c.teleAction == 35) {
				c.getPA().spellTeleport(2606, 3155, 0);
				c.sendMessage("Welcome to Barbarian Assault.");
				c.getPA().closeAllWindows();
			}
			if (c.teleAction == 16) {
				c.getPA().spellTeleport(3307, 3733, 0);
				c.sendMessage("You teleport to Venenatis.");
				c.getPA().closeAllWindows();
			}
			if (c.teleAction == 45) {
				c.getPA().spellTeleport(2820, 3460, 0);
				c.sendMessage("You teleport to the farming area.");
				c.sendMessage("You can purchase farming tools from the general store.");
				c.getPA().closeAllWindows();
			}
			if (c.teleAction == 12) {
				c.getPA().spellTeleport(2908, 9694, 0);
			}
			if (c.teleAction == 15) {
				c.getPA().spellTeleport(3486, 9484, 4);
				c.sendMessage("You teleport to the Kalphite Queens Lair.");
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 200) { //TODO
				c.getDH().sendDialogues(655, 0);
				//c.getPA().startTeleport2(2884, 9798, 0);
				//c.sendMessage("You teleport to Taverly Dungeon.");
			}
			if (c.dialogueAction == 201) {
				c.getPA().startTeleport2(2272, 4680, 0);
				c.sendMessage("You teleport to the King Black Dragon.");
			}
			if (c.teleAction == 11) {
				c.getPA().spellTeleport(3237, 9384, 0);
			}
			if (c.teleAction == 10) {
				c.getPA().spellTeleport(3219, 9366, 0);
			}
			if (c.teleAction == 9) {
				c.getPA().spellTeleport(2916, 9800, 0);
			}
			if (c.teleAction == 8) {
				c.getPA().spellTeleport(2903, 9849, 0);
			}
			if (c.teleAction == 7) {
				c.getPA().spellTeleport(2859, 9843, 0);
			}

			/*
			 * if (c.teleAction == 1) { //rock crabs
			 * c.getPA().spellTeleport(2676, 3715, 0); } else if (c.teleAction
			 * == 2) { //taverly dungeon c.getPA().spellTeleport(2884, 9798, 0);
			 * } else if (c.teleAction == 3) { //kbd
			 * c.getPA().spellTeleport(3007, 3849, 0); } else if (c.teleAction
			 * == 4) { //west lv 10 wild c.getPA().spellTeleport(2979, 3597, 0);
			 * } else if (c.teleAction == 5) {
			 * c.getPA().spellTeleport(3079,9502,0); }
			 */
			if (c.teleAction == 1) {
				// hill giants
				c.getDH().sendOption5("Hill Giants", "Hellhounds",
				"Lesser Demons", "Chaos Dwarf", "-- Next Page --");
				c.teleAction = 7;
				//c.getPA().spellTeleport(3115, 3483, 0);
				//c.sendMessage("Talk to the Combat Instructer.");
			} else if (c.teleAction == 2) {
				// pest control
				c.getPA().spellTeleport(2658, 2650, 0);
				c.sendMessage("You teleport to the Pest Control mini-game.");
				c.getPA().closeAllWindows();
			} else if (c.teleAction == 3) {
				// choas ele
				// c.sendMessage("King Black Dragon has been disabled.");
				c.getPA().spellTeleport(3279, 3910, 0);
				c.sendMessage("You teleport to the Chaos Elemental Beware.");
				c.getPA().closeAllWindows();
			} else if (c.teleAction == 4) {
				// graveyard
				c.getPA().spellTeleport(2979, 3597, 0);
				c.getPA().resetAutocast();
			} else if (c.teleAction == 5) {
				c.getPA().spellTeleport(3079, 9502, 0);

			} else if (c.teleAction == 20) {
				c.getPA().spellTeleport(3210, 3424, 0);// 3210 3424
				c.sendMessage("You teleport to varrock.");
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 10) {
				c.getPA().spellTeleport(2796, 4818, 0);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 11) {
				c.getPA().spellTeleport(2527, 4833, 0);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 12) {
				c.getPA().spellTeleport(2464, 4834, 0);
				c.dialogueAction = -1;
			}
		break;
			// 3rd tele option

		case 9192:
			if (c.teleAction == 16) {
				//c.getPA().spellTeleport(3221, 3946, 0);
				//c.sendMessage("You teleport to Scorpia.");
				c.sendMessage("Scorpia has been disabled for now.");
				c.getPA().closeAllWindows();
			}
			if (c.teleAction == 45) {
				c.getPA().spellTeleport(2470, 3436, 0);
				c.sendMessage("You teleport to the Agility Area.");
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 200) {
				c.getPA().startTeleport2(2647, 9956, 0);
				c.sendMessage("You teleport to Brimhaven Dungeon.");
			}
			if (c.teleAction == 15) {
				// c.getPA().spellTeleport(1763, 5198, 0);
				c.getPA().spellTeleport(2345, 3690, 0);
				c.sendMessage("You teleport to the Kraken Boss.");
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 201) {
				c.getPA().startTeleport2(3298, 3910, 0);
				c.sendMessage("You teleport to the Chaos Elemental.");
			}
			if (c.teleAction == 12) {
				c.getPA().spellTeleport(2739, 5088, 0);
			}
			if (c.teleAction == 11) {
				c.getPA().spellTeleport(3280, 9372, 0);
			}
			if (c.teleAction == 10) {
				c.getPA().spellTeleport(3241, 9364, 0);
			}
			if (c.teleAction == 9) {
				c.getPA().spellTeleport(3159, 9895, 0);
			}
			if (c.teleAction == 8) {
				c.getPA().spellTeleport(2912, 9831, 0);
			}
			if (c.teleAction == 7) {
				c.getPA().spellTeleport(2843, 9555, 0);
			}
			/*
			 * if (c.teleAction == 1) { //experiments
			 * c.getPA().spellTeleport(3555, 9947, 0); } else if (c.teleAction
			 * == 2) { //brimhavem dung c.getPA().spellTeleport(2709, 9564, 0);
			 * } else if (c.teleAction == 3) { //dag kings
			 * c.getPA().spellTeleport(2479, 10147, 0); } else if (c.teleAction
			 * == 4) { //easts lv 18 c.getPA().spellTeleport(3351, 3659, 0); }
			 * else if (c.teleAction == 5) {
			 * c.getPA().spellTeleport(2813,3436,0); }
			 */
			if (c.teleAction == 1) {
				c.getPA().spellTeleport(3429, 3538, 0);
				c.sendMessage("You teleport to the Slayer Tower.");
				c.getPA().closeAllWindows();
			}
			if (c.teleAction == 2) {
				c.getPA().spellTeleport(2438, 5168, 0);
				c.sendMessage("To fight Jad, enter the cave.");
				c.getPA().closeAllWindows();
			}
			if (c.teleAction == 3) {
				c.getPA().spellTeleport(2523, 3044, 0);
				c.sendMessage("You teleport to the Barrelchest boss.");
				c.getPA().closeAllWindows();
				// c.sendMessage("Climb down the ladder to get into the lair.");
			}
			if (c.teleAction == 4) {
				// Hillz
				c.getPA().spellTeleport(3351, 3659, 0);
				c.getPA().resetAutocast();
			} else if (c.teleAction == 5) {
				c.getPA().spellTeleport(2813, 3436, 0);
			} else if (c.teleAction == 20) {
				c.getPA().spellTeleport(2757, 3477, 0);
				c.sendMessage("You teleport to camelot.");
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 10) {
				c.getPA().spellTeleport(2713, 4836, 0);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 11) {
				c.getPA().spellTeleport(2162, 4833, 0);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 12) {
				c.getPA().spellTeleport(2207, 4836, 0);
				c.dialogueAction = -1;
			}
			break;

		// 4th tele option
		case 9193:
			if (c.teleAction == 16) {
				c.getPA().spellTeleport(3215, 3755, 0);
				c.sendMessage("You teleport to Vet'ion.");
				c.getPA().closeAllWindows();
			}
			if (c.teleAction == 45) {
				c.getPA().spellTeleport(2608, 4775, 0);
				c.sendMessage("You teleport to the hunter area.");
				c.getPA().closeAllWindows();
			}
			if (c.teleAction == 20) {
				c.getPA().spellTeleport(2964, 3378, 0);
				c.sendMessage("You teleport to falador.");
				c.getPA().closeAllWindows();
			}
			if (c.teleAction == 12) {
				c.getDH().sendOption5("GarGoyle", "Bloodveld", "Banshee",
						"-- Previous Page --", "-- Next Page --");
				c.teleAction = 11;
				break;
			}
			if (c.teleAction == 15) {
				c.getPA().spellTeleport(3054, 9543, 0);
				c.sendMessage("You teleport to the Mithril Dragons. Goodluck!");
				c.getPA().closeAllWindows();
			}
			if (c.teleAction == 11) {
				c.getDH().sendOption5("Black Demon", "Dust Devils",
						"Nechryael", "-- Previous Page --", "-- Next Page --");
				c.teleAction = 10;
				break;
			}
			if (c.teleAction == 10) {
				c.getDH()
						.sendOption5("Goblins", "Baby blue dragon",
								"Moss Giants", "-- Previous Page --",
								"-- Next Page --");
				c.teleAction = 9;
				break;
			}
			if (c.teleAction == 9) {
				c.getDH().sendOption5("Al-kharid warrior", "Ghosts",
						"Giant Bats", "-- Previous Page --", "-- Next Page --");
				c.teleAction = 8;
				break;
			}
			if (c.teleAction == 8) {
				c.getDH().sendOption5("Hill Giants", "Hellhounds",
						"Lesser Demons", "Chaos Dwarf", "-- Next Page --");
				c.teleAction = 7;
				break;
			}
			if (c.teleAction == 7) {
				c.getPA().spellTeleport(2923, 9759, 0);
			}
			if (c.teleAction == 1) {
				// brimhaven dungeon
				c.getPA().spellTeleport(2710, 9466, 0);
				c.sendMessage("You teleport to Brimhaven Dungeon.");
				c.getPA().closeAllWindows();
			} else if (c.teleAction == 2) {
				// duel arena
				c.getPA().spellTeleport(3366, 3266, 0);
				c.sendMessage("You teleport to the Duel Arena");
				c.getPA().closeAllWindows();
			} else if (c.teleAction == 3) {
				// Godwars
				// c.getPA().spellTeleport(3565, 3314, 0);
				c.getDH().sendOption4("Armadyl", "Bandos", "Zamorak",
						"Saradomin");
				c.teleAction = 13; // godwars teleaction13

			} else if (c.teleAction == 4) {
				// Fala
				c.getPA().resetAutocast();
				c.getPA().spellTeleport(3321, 3895, 0);

			} else if (c.teleAction == 5) {
				c.getPA().spellTeleport(2724, 3484, 0);
				c.sendMessage("For magic logs, try north of the duel arena.");
			}
			if (c.dialogueAction == 10) {
				c.getPA().spellTeleport(2660, 4839, 0);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 11) {
				// c.getPA().spellTeleport(2527, 4833, 0); astrals here
				// c.getRunecrafting().craftRunes(2489);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 12) {
				// c.getPA().spellTeleport(2464, 4834, 0); bloods here
				// c.getRunecrafting().craftRunes(2489);
				c.dialogueAction = -1;
			}
			break;
		// 5th tele option
		case 9194: 
			if (c.dialogueAction == 200) {
				c.getDH().sendDialogues(655, 0);
			}
			if (c.teleAction == 45) {
				c.getDH().sendOption2("Fishing", "Mining + Smithing");
				c.teleAction = 46;
			}
			if (c.teleAction == 20) {
				//c.getPA().spellTeleport(3492, 3484, 0);
				//c.sendMessage("You teleport to canifis.");
				//c.getPA().closeAllWindows();
				c.getDH().sendOption4("Ardougne", "Canifis", "Draynor", "Yanille");
				c.teleAction = 21;
			}
			if (c.dialogueAction == 200) {
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 201) {
				c.getDH().sendDialogues(202, 0);
			}
			if (c.teleAction == 16) {
				c.getPA().spellTeleport(3059, 3951, 0);
				c.sendMessage("You teleport to the Giant Sea Snake Boss, Goodluck.");
				c.getPA().closeAllWindows();
			}
			if (c.teleAction == 15) {
				c.getDH().sendOption5("Callisto@red@[WILD]", "Venenatis@red@[WILD]", "Scorpia@red@[WILD]", "Vet'ion@red@[WILD]", "Giant Sea Snake@red@[WILD]");
				c.teleAction = 16;
				break;
			}

			if (c.teleAction == 8) {
				c.getDH()
						.sendOption5("Goblins", "Baby blue dragon",
								"Moss Giants", "-- Previous Page --",
								"-- Next Page --");
				c.teleAction = 9;
				break;
			}
			if (c.teleAction == 9) {
				c.getDH().sendOption5("Black Demon", "Dust Devils",
						"Nechryael", "-- Previous Page --", "-- Next Page --");
				c.teleAction = 10;
				break;
			}
			if (c.teleAction == 11) {
				c.getDH().sendOption5("Infernal Mage", "Dark Beasts",
						"Abyssal Demon", "-- Previous Page --", "");
				c.teleAction = 12;
				break;
			}
			if (c.teleAction == 10) {
				c.getDH().sendOption5("GarGoyle", "Bloodveld", "Banshee",
						"-- Previous Page --", "-- Next Page --");
				c.teleAction = 11;
				break;
			}
			if (c.teleAction == 7) {
				c.getDH().sendOption5("Al-kharid warrior", "Ghosts",
						"Giant Bats", "-- Previous Page --", "-- Next Page --");
				c.teleAction = 8;
				break;
			}
			/*
			 * if (c.teleAction == 1) { //island c.getPA().spellTeleport(2895,
			 * 2727, 0); } else if (c.teleAction == 2) { //last minigame spot
			 * c.sendMessage("Suggest something for this spot on the forums!");
			 * c.getPA().closeAllWindows(); } else if (c.teleAction == 3) {
			 * //last monster spot
			 * c.sendMessage("Suggest something for this spot on the forums!");
			 * c.getPA().closeAllWindows(); } else if (c.teleAction == 4) {
			 * //dark castle multi easts c.getPA().spellTeleport(3037, 3652, 0);
			 * } else if (c.teleAction == 5) {
			 * c.getPA().spellTeleport(2812,3463,0); }
			 */
			if (c.teleAction == 1) {
				// traverly
				// c.getPA().spellTeleport(3297, 9824, 0);
				// c.sendMessage("@red@There's just frost dragons, if you want to kill green dragons you must go wilderness.");
				c.getDH().sendDialogues(655, 0);
				// 2884 9798
			} else if (c.teleAction == 2) {
				c.getDH().sendDialogues(630, 0);
				/*c.getPA().spellTeleport(2866, 3546, 0);
				c.sendMessage("You teleport to the Warriors Guild.");
				c.sendMessage("Talk to the women at upstairs to enter the cyclops room.");
				c.sendMessage("You'll need to get some tokens via the animated armour to enter.");
				c.getPA().closeAllWindows();*/
				// last minigame spot
				// c.sendMessage("Suggest something for this spot on the forums!");
				// c.getPA().closeAllWindows();
				// c.getPA().spellTeleport(2876, 3546, 0);
			} else if (c.teleAction == 3) {
				c.getDH().sendOption5("Daggonoth Kings", "Kalphite Queen",
						"Kraken", "Mithril Dragons @red@[WILD]", "More");
				c.teleAction = 15;
				// last monster spot
				// c.sendMessage("Suggest something for this spot on the forums!");
				// c.getPA().closeAllWindows();
			} else if (c.teleAction == 4) {
				// ardy lever
				c.getPA().spellTeleport(3037, 3652, 0);
				c.getPA().resetAutocast();
			} else if (c.teleAction == 5) {
				c.getPA().spellTeleport(2812, 3463, 0);
			}
			if (c.dialogueAction == 10 || c.dialogueAction == 11) {
				c.dialogueId++;
				c.getDH().sendDialogues(c.dialogueId, 0);
			} else if (c.dialogueAction == 12) {
				c.dialogueId = 17;
				c.getDH().sendDialogues(c.dialogueId, 0);
			}
			break;

		case 34185:
		case 34184:
		case 34183:
		case 34182:
		case 34189:
		case 34188:
		case 34187:
		case 34186:
		case 34193:
		case 34192:
		case 34191:
		case 34190:
			// if (c.craftingLeather)
			// c.getCrafting().handleCraftingClick(actionButtonId);
			if (c.getFletching().fletching)
				c.getFletching().handleFletchingClick(actionButtonId);
			c.getPA().closeAllWindows();
			break;

		// case 58253:
		case 108005:
			c.getPA().showInterface(15106);
			// c.getItems().writeBonus();
			break;
		case 108006: // items kept on death
			c.getPA().sendFrame126("Items Kept On Death", 17103);
			c.StartBestItemScan(c);
			c.EquipStatus = 0;
			for (int k = 0; k < 4; k++)
				c.getPA().sendFrame34a(10494, -1, k, 1);
			for (int k = 0; k < 39; k++)
				c.getPA().sendFrame34a(10600, -1, k, 1);
			if (c.WillKeepItem1 > 0)
				c.getPA().sendFrame34a(10494, c.WillKeepItem1, 0,
						c.WillKeepAmt1);
			if (c.WillKeepItem2 > 0)
				c.getPA().sendFrame34a(10494, c.WillKeepItem2, 1,
						c.WillKeepAmt2);
			if (c.WillKeepItem3 > 0)
				c.getPA().sendFrame34a(10494, c.WillKeepItem3, 2,
						c.WillKeepAmt3);
			if (c.WillKeepItem4 > 0 && c.prayerActive[10])
				c.getPA().sendFrame34a(10494, c.WillKeepItem4, 3, 1);
			for (int ITEM = 0; ITEM < 28; ITEM++) {
				if (c.playerItems[ITEM] - 1 > 0
						&& !(c.playerItems[ITEM] - 1 == c.WillKeepItem1 && ITEM == c.WillKeepItem1Slot)
						&& !(c.playerItems[ITEM] - 1 == c.WillKeepItem2 && ITEM == c.WillKeepItem2Slot)
						&& !(c.playerItems[ITEM] - 1 == c.WillKeepItem3 && ITEM == c.WillKeepItem3Slot)
						&& !(c.playerItems[ITEM] - 1 == c.WillKeepItem4 && ITEM == c.WillKeepItem4Slot)) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1,
							c.EquipStatus, c.playerItemsN[ITEM]);
					c.EquipStatus += 1;
				} else if (c.playerItems[ITEM] - 1 > 0
						&& (c.playerItems[ITEM] - 1 == c.WillKeepItem1 && ITEM == c.WillKeepItem1Slot)
						&& c.playerItemsN[ITEM] > c.WillKeepAmt1) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1,
							c.EquipStatus,
							c.playerItemsN[ITEM] - c.WillKeepAmt1);
					c.EquipStatus += 1;
				} else if (c.playerItems[ITEM] - 1 > 0
						&& (c.playerItems[ITEM] - 1 == c.WillKeepItem2 && ITEM == c.WillKeepItem2Slot)
						&& c.playerItemsN[ITEM] > c.WillKeepAmt2) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1,
							c.EquipStatus,
							c.playerItemsN[ITEM] - c.WillKeepAmt2);
					c.EquipStatus += 1;
				} else if (c.playerItems[ITEM] - 1 > 0
						&& (c.playerItems[ITEM] - 1 == c.WillKeepItem3 && ITEM == c.WillKeepItem3Slot)
						&& c.playerItemsN[ITEM] > c.WillKeepAmt3) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1,
							c.EquipStatus,
							c.playerItemsN[ITEM] - c.WillKeepAmt3);
					c.EquipStatus += 1;
				} else if (c.playerItems[ITEM] - 1 > 0
						&& (c.playerItems[ITEM] - 1 == c.WillKeepItem4 && ITEM == c.WillKeepItem4Slot)
						&& c.playerItemsN[ITEM] > 1) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1,
							c.EquipStatus, c.playerItemsN[ITEM] - 1);
					c.EquipStatus += 1;
				}
			}
			for (int EQUIP = 0; EQUIP < 14; EQUIP++) {
				if (c.playerEquipment[EQUIP] > 0
						&& !(c.playerEquipment[EQUIP] == c.WillKeepItem1 && EQUIP + 28 == c.WillKeepItem1Slot)
						&& !(c.playerEquipment[EQUIP] == c.WillKeepItem2 && EQUIP + 28 == c.WillKeepItem2Slot)
						&& !(c.playerEquipment[EQUIP] == c.WillKeepItem3 && EQUIP + 28 == c.WillKeepItem3Slot)
						&& !(c.playerEquipment[EQUIP] == c.WillKeepItem4 && EQUIP + 28 == c.WillKeepItem4Slot)) {
					c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP],
							c.EquipStatus, c.playerEquipmentN[EQUIP]);
					c.EquipStatus += 1;
				} else if (c.playerEquipment[EQUIP] > 0
						&& (c.playerEquipment[EQUIP] == c.WillKeepItem1 && EQUIP + 28 == c.WillKeepItem1Slot)
						&& c.playerEquipmentN[EQUIP] > 1
						&& c.playerEquipmentN[EQUIP] - c.WillKeepAmt1 > 0) {
					c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP],
							c.EquipStatus,
							c.playerEquipmentN[EQUIP] - c.WillKeepAmt1);
					c.EquipStatus += 1;
				} else if (c.playerEquipment[EQUIP] > 0
						&& (c.playerEquipment[EQUIP] == c.WillKeepItem2 && EQUIP + 28 == c.WillKeepItem2Slot)
						&& c.playerEquipmentN[EQUIP] > 1
						&& c.playerEquipmentN[EQUIP] - c.WillKeepAmt2 > 0) {
					c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP],
							c.EquipStatus,
							c.playerEquipmentN[EQUIP] - c.WillKeepAmt2);
					c.EquipStatus += 1;
				} else if (c.playerEquipment[EQUIP] > 0
						&& (c.playerEquipment[EQUIP] == c.WillKeepItem3 && EQUIP + 28 == c.WillKeepItem3Slot)
						&& c.playerEquipmentN[EQUIP] > 1
						&& c.playerEquipmentN[EQUIP] - c.WillKeepAmt3 > 0) {
					c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP],
							c.EquipStatus,
							c.playerEquipmentN[EQUIP] - c.WillKeepAmt3);
					c.EquipStatus += 1;
				} else if (c.playerEquipment[EQUIP] > 0
						&& (c.playerEquipment[EQUIP] == c.WillKeepItem4 && EQUIP + 28 == c.WillKeepItem4Slot)
						&& c.playerEquipmentN[EQUIP] > 1
						&& c.playerEquipmentN[EQUIP] - 1 > 0) {
					c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP],
							c.EquipStatus, c.playerEquipmentN[EQUIP] - 1);
					c.EquipStatus += 1;
				}
			}
			c.ResetKeepItems();
			c.getPA().showInterface(17100);
			break;

		case 59004:
			c.getPA().removeAllWindows();
			break;

		case 9178:
			if (c.teleAction == 21) {
				c.getPA().spellTeleport(Config.ARDOUGNE_X, Config.ARDOUGNE_Y, 0);
				c.sendMessage("You teleport to Ardougne.");
				c.getPA().closeAllWindows();
			}
			/*if (c.teleAction == 45) {
				c.getPA().spellTeleport(2849, 3432, 0);
				c.sendMessage("You teleport to the general skilling area.");
				c.getPA().closeAllWindows();
			}*/
			if (c.teleAction == 45) {
				c.getPA().spellTeleport(2725, 3485, 0);
				c.getPA().closeAllWindows();
				c.sendMessage("You teleport to the woodcutting area.");
			}
			if (c.dialogueAction == 5) {
				if (c.slayerTask <= 0) {
					c.getSlayer().giveTask();
					c.getPA()
							.sendFrame126(
									"@or1@Task: @gre@"
											+ c.taskAmount
											+ " "
											+ Server.npcHandler.getNpcListName(c.slayerTask)
											+ " ", 7383);
					c.getPA().closeAllWindows();
				} else {
					c.sendMessage("You already have a slayer task, please reset your task before getting a new one");
					}
				}
			if (c.teleAction == 13) {
				 c.getPA().spellTeleport(2839, 5293, 2);
				//c.getPA().spellTeleport(2900, 3617, 4);
				c.sendMessage("You must know it's not easy, get a team to own that boss!");
			} else if (c.dialogueAction == 202) {
				c.getPA().spellTeleport(0, 0, 0);
			}
			if (c.dialogueAction == 1658) {
				if (!c.getItems().playerHasItem(995, 2230000)) {
					c.sendMessage("You must have 2,230,000 coins to buy this package.");
					c.getPA().removeAllWindows();
					c.dialogueAction = 0;
					break;
				}
				c.dialogueAction = 0;
				c.getItems().addItemToBank(560, 4000);
				c.getItems().addItemToBank(565, 2000);
				c.getItems().addItemToBank(555, 6000);
				c.getItems().deleteItem(995, c.getItems().getItemSlot(995),
						2230000);
				c.sendMessage("@red@The runes has been added to your bank.");
				c.getPA().removeAllWindows();
				break;
			}
			if (c.usingGlory)
				// c.getPA().useCharge();
				c.getPA().startTeleport(Config.EDGEVILLE_X, Config.EDGEVILLE_Y,
						0, "modern");
			if (c.dialogueAction == 2)
				c.getPA().startTeleport(3428, 3538, 0, "modern");
			if (c.dialogueAction == 3)
				c.getPA().startTeleport(Config.EDGEVILLE_X, Config.EDGEVILLE_Y,
						0, "modern");
			if (c.dialogueAction == 4)
				c.getPA().startTeleport(3565, 3314, 0, "modern");
			if (c.dialogueAction == 20) {
				c.getPA().startTeleport(2897, 3618, 4, "modern");
				c.sendMessage("You teleport to varrock");
				c.getPA().closeAllWindows();
				c.killCount = 0;
			} else if (c.teleAction == 2) {
				// barrows
				c.getPA().spellTeleport(3565, 3314, 0);
			}

			if (c.caOption4a) {
				c.getDH().sendDialogues(102, c.npcType);
				c.caOption4a = false;
			}
			if (c.caOption4c) {
				c.getDH().sendDialogues(118, c.npcType);
				c.caOption4c = false;
			}
			break;

		case 9179:
			if (c.teleAction == 45) {
				c.getPA().spellTeleport(2820, 3460, 0);
				c.sendMessage("You teleport to the farming area.");
				c.sendMessage("You can purchase farming tools from the general store.");
				c.getPA().closeAllWindows();
			}
			if (c.teleAction == 21) {
				c.getPA().spellTeleport(3492, 3484, 0);
				c.sendMessage("You teleport to Canifis.");
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 5) {
				if (c.combatLevel >= 100 && c.slayerTask <= 0) {
					c.getSlayer().giveEliteTask(4);
					c.getPA()
							.sendFrame126(
									"@or1@Task: @gre@"
											+ c.taskAmount
											+ " "
											+ Server.npcHandler.getNpcListName(c.slayerTask)
											+ " ", 7383);
					c.getPA().closeAllWindows();
				} else {
					c.sendMessage("You already have a slayer task, please reset your task before getting a new one");
					}
				}
			if (c.teleAction == 15) {
				c.getPA().spellTeleport(3486, 9484, 4);
				c.sendMessage("You teleport to the Kalphite Queen.");
				c.getPA().closeAllWindows();
			}
			if (c.teleAction == 13) {
				 c.getPA().spellTeleport(2860, 5354, 2);
				//c.getPA().spellTeleport(2900, 3617, 8);
				c.sendMessage("You must know it's not easy, get a team to own that boss!");
			} else if (c.dialogueAction == 202) {
				c.getPA().spellTeleport(2518, 3038, 0);
			}
			if (c.dialogueAction == 1658) {
				if (!c.getItems().playerHasItem(995, 912000)) {
					c.sendMessage("You must have 912,000 coins to buy this package.");
					c.getPA().removeAllWindows();
					c.dialogueAction = 0;
					break;
				}
				c.dialogueAction = 0;
				c.getItems().addItemToBank(560, 2000);
				c.getItems().addItemToBank(9075, 4000);
				c.getItems().addItemToBank(557, 10000);
				c.getItems().deleteItem(995, c.getItems().getItemSlot(995),
						912000);
				c.sendMessage("@red@The runes has been added to your bank.");
				c.getPA().removeAllWindows();
				break;
			}
			if (c.usingGlory)
				// c.getPA().useCharge();
				c.getPA().startTeleport(Config.AL_KHARID_X, Config.AL_KHARID_Y,
						0, "modern");
			if (c.dialogueAction == 2)
				c.getPA().startTeleport(2884, 3395, 0, "modern");
			if (c.dialogueAction == 3)
				c.getPA().startTeleport(3243, 3513, 0, "modern");
			if (c.dialogueAction == 4)
				c.getPA().startTeleport(2444, 5170, 0, "modern");
			if (c.dialogueAction == 20) {
				c.getPA().startTeleport(2897, 3618, 12, "modern");
				c.killCount = 0;
			} else if (c.teleAction == 2) {
				// assault
				c.getPA().spellTeleport(2605, 3153, 0);
			} else if (c.dialogueAction == 202) {
				c.getPA().spellTeleport(0, 0, 0);
			}
			if (c.caOption4c) {
				c.getDH().sendDialogues(120, c.npcType);
				c.caOption4c = false;
			}
			if (c.caPlayerTalk1) {
				c.getDH().sendDialogues(125, c.npcType);
				c.caPlayerTalk1 = false;
			}
			break;

		case 9180:
			if (c.teleAction == 45) {
				c.getPA().spellTeleport(2470, 3436, 0);
				c.sendMessage("You teleport to the Agility Area.");
				c.getPA().closeAllWindows();
			}
			if (c.teleAction == 21) {
				c.getPA().spellTeleport(3079, 3250, 0);
				c.sendMessage("You teleport to Draynor Village.");
				c.getPA().closeAllWindows();
			}
			if (c.teleAction == 13) {
				 c.getPA().spellTeleport(2925, 5334, 2);
				//c.getPA().spellTeleport(2900, 3617, 12);
				c.sendMessage("You must know it's not easy, get a team to own that boss!");
			}
			if (c.dialogueAction == 1658) {
				if (!c.getItems().playerHasItem(995, 1788000)) {
					c.sendMessage("You must have 1,788,000 coins to buy this package.");
					c.getPA().removeAllWindows();
					c.dialogueAction = 0;
					break;
				}
				c.dialogueAction = 0;
				c.getItems().addItemToBank(556, 1000);
				c.getItems().addItemToBank(554, 1000);
				c.getItems().addItemToBank(558, 1000);
				c.getItems().addItemToBank(557, 1000);
				c.getItems().addItemToBank(555, 1000);
				c.getItems().addItemToBank(560, 1000);
				c.getItems().addItemToBank(565, 1000);
				c.getItems().addItemToBank(566, 1000);
				c.getItems().addItemToBank(9075, 1000);
				c.getItems().addItemToBank(562, 1000);
				c.getItems().addItemToBank(561, 1000);
				c.getItems().addItemToBank(563, 1000);
				c.getItems().deleteItem(995, c.getItems().getItemSlot(995),
						1788000);
				c.sendMessage("@red@The runes has been added to your bank.");
				c.getPA().removeAllWindows();
				break;
			}
			if (c.usingGlory)
				// c.getPA().useCharge();
				c.getPA().startTeleport(Config.KARAMJA_X, Config.KARAMJA_Y, 0,
						"modern");
			if (c.dialogueAction == 2)
				c.getPA().startTeleport(2471, 10137, 0, "modern");
			if (c.dialogueAction == 3)
				c.getPA().startTeleport(3363, 3676, 0, "modern");
			if (c.dialogueAction == 4)
				c.getPA().startTeleport(2659, 2676, 0, "modern");
			if (c.dialogueAction == 20) {
				c.getPA().startTeleport(2897, 3618, 8, "modern");
				c.killCount = 0;
			} else if (c.teleAction == 2) {
				// duel arena
				c.getPA().spellTeleport(3366, 3266, 0);
			}
			if (c.caOption4c) {
				c.getDH().sendDialogues(122, c.npcType);
				c.caOption4c = false;
			}
			if (c.caPlayerTalk1) {
				c.getDH().sendDialogues(127, c.npcType);
				c.caPlayerTalk1 = false;
			}
			break;

		case 9181:
			/*if (c.teleAction == 45) {
				c.getPA().spellTeleport(2608, 4775, 0);
				c.sendMessage("You teleport to the hunter area.");
				c.getPA().closeAllWindows();
			}*/
			if (c.teleAction == 45) {
				c.getDH().sendOption2("Fishing", "Mining + Smithing");
				c.teleAction = 46;
			}
			if (c.teleAction == 21) {
				c.getPA().spellTeleport(2605, 3093, 0);
				c.sendMessage("You teleport to Yanille.");
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 5) {
				c.getPA().closeAllWindows();
			}
			if (c.teleAction == 13) {
				 c.getPA().spellTeleport(2912, 5266, 0);
				//c.getPA().spellTeleport(2900, 3617, 0);
				c.sendMessage("You must know it's not easy, get a team to own that boss!");
			}
			if (c.dialogueAction == 1658) {
				c.getShops().openShop(5);
				c.dialogueAction = 0;
			} else if (c.dialogueAction == 202) {
				c.getPA().closeAllWindows();
			}
			if (c.usingGlory)
				// c.getPA().useCharge();
				c.getPA().startTeleport(Config.MAGEBANK_X, Config.MAGEBANK_Y,
						0, "modern");
			if (c.dialogueAction == 2)
				c.getPA().startTeleport(2669, 3714, 0, "modern");
			if (c.dialogueAction == 3)
				c.getPA().startTeleport(2540, 4716, 0, "modern");
			if (c.dialogueAction == 4) {
				c.getPA().startTeleport(3366, 3266, 0, "modern");
				c.sendMessage("Dueling is at your own risk. Refunds will not be given for items lost due to glitches.");
			} else if (c.teleAction == 2) {
				// tzhaar
				c.getPA().spellTeleport(2444, 5170, 0);
			}
			if (c.dialogueAction == 20) {
				// c.getPA().startTeleport(3366, 3266, 0, "modern");
				// c.killCount = 0;
				c.sendMessage("This will be added shortly");
			}
			if (c.caOption4c) {
				c.getDH().sendDialogues(124, c.npcType);
				c.caOption4c = false;
			}
			if (c.caPlayerTalk1) {
				c.getDH().sendDialogues(130, c.npcType);
				c.caPlayerTalk1 = false;
			}
			break;

		case 1093:
		case 1094:
		case 1097:
			if (c.autocastId > 0) {
				c.getPA().resetAutocast();
			} else {
				if (c.playerEquipment[c.playerWeapon] == 11907) {
					c.sendMessage("You cannot change the trident's attack type.");
					c.spellId = 52;
					c.autocasting = true;
					return;
				}
				if (c.playerMagicBook == 1) {
					if (c.playerEquipment[c.playerWeapon] == 4675 || c.playerEquipment[c.playerWeapon] == 11791 || c.playerEquipment[c.playerWeapon] == 12904 || c.playerEquipment[c.playerWeapon] == 13867)
						c.setSidebarInterface(0, 1689);
					else
						c.sendMessage("You can't autocast ancients without an ancient staff.");
				} else if (c.playerMagicBook == 0) {
					if (c.playerEquipment[c.playerWeapon] == 4170) {
						c.setSidebarInterface(0, 12050);
					} else {
						c.setSidebarInterface(0, 1829);
					}
				}

			}
			break;

		case 9157:
			if (c.dialogueAction == 4343) {
				c.headIconPk = 0;
				c.isSkulled = true;
				c.getPA().requestUpdates();
				c.sendMessage("You are now skulled.");
				c.getPA().closeAllWindows();
				break;
			}
			if (c.dialogueAction == 438) {
				c.getPA().fixAllBarrows();
				c.getPA().removeAllWindows();
			} else if (c.dialogueAction == 420) {
				c.getPA().startTeleport(2462 + Misc.random(2), 4775 - Misc.random(3), 0, "modern"); 
			} else if (c.dialogueAction == 440) {
				c.getPA().startTeleport(2784, 10057, 0, "modern"); 
			}
		if (c.dialogueAction == 1340) {
			if (System.currentTimeMillis() - c.emptyDelay >= 15000);
			c.emptyDelay = System.currentTimeMillis();
			c.sendMessage("You've emptied your inventory, you must wait another 15 seconds to empty again.");
			c.getPA().removeAllItems();
			c.getPA().closeAllWindows();
		} else if (c.dialogueAction == 29) {
			if (c.barrowsKillCount < 5) {
				c.sendMessage("You still have to kill the following brothers:");
				if (c.barrowsNpcs[2][1] == 0) {
					c.sendMessage("- Karils");
				}
				if (c.barrowsNpcs[3][1] == 0) {
					c.sendMessage("- Guthans");
				}
				if (c.barrowsNpcs[1][1] == 0) {
					c.sendMessage("- Torags");
				}
				if (c.barrowsNpcs[5][1] == 0) {
					c.sendMessage("- Ahrims");
				}
				if (c.barrowsNpcs[0][1] == 0) {
					c.sendMessage("- Veracs");
				}
				c.getPA().removeAllWindows();
				return;
			} else {
			c.getPA().movePlayer(3551, 9694, 0);
			c.sendMessage("You teleport to the chest.");
			c.getPA().removeAllWindows();
			}
		}
			if (c.teleAction == 46) {
				c.getPA().spellTeleport(2604, 3402, 0);
				c.getPA().closeAllWindows();
				c.sendMessage("You teleport to the Fishing Area.");
			}
			if (c.dialogueAction == 2000) {
				if (c.getItems().playerHasItem(8851, 1)) {
					c.getItems().deleteItem(8851, 10);
					c.getPA().closeAllWindows();
					c.getPA().movePlayer(2849, 3541, 2);
					c.sendMessage("You enter the Warriors Guild for 10 tokens, good luck on your defender.");
				} else {
					c.sendMessage("You don't have enough tokens.");
					c.getPA().closeAllWindows();
				}
			}

			if (c.dialogueAction == 105) {
			if (c.slayerPoints >= 5) {
				c.slayerPoints -= 5;
				c.taskAmount = -1; // vars
				c.slayerTask = 0; // vars
				c.sendMessage("Your slayer task has been reset sucessfully.");
				c.getPA().sendFrame126("@or1@Task: @gre@Empty", 7383);
				c.getPA().closeAllWindows();
			} else {
				c.sendMessage("You don't have enough slayer points.");
				c.getPA().closeAllWindows();
			}
		} else if (c.dialogueAction == 25) {
				c.getDH().sendDialogues(26, 0);
				return;
		}
			if (c.dialogueAction == 46) {
				if (c.getItems().playerHasItem (6573, 1)) {
				c.getItems().deleteItem(6573, 1);
				c.getItems().addItem(6585, 1);
				c.getPA().closeAllWindows();
				c.sendMessage("You make an amulet of fury.");
				} else {
					c.sendMessage("You don't have any onyx's.");
					return;
				}
			}
			if (c.dialogueAction == 1) {
				if (c.barrowsKillCount >= 5) {
					c.getPA().removeAllWindows();
					c.getPA().movePlayer(3551, 9694, 0);
			} else {
				c.getPA().removeAllWindows();
				c.sendMessage("You haven't killed all the brothers.");
				}
			}
			if (c.dialogueAction == 2258) {
				c.getPA().startTeleport(3039, 4834, 0, "modern"); // first click
																	// teleports
																	// second
																	// click
																	// open
																	// shops
			}
			if (c.newPlayerAct == 1) {
				//c.isNewPlayer = false;
				c.newPlayerAct = 0;
				c.getPA().startTeleport(Config.EDGEVILLE_X, Config.EDGEVILLE_Y,
						0, "modern");
				c.getPA().removeAllWindows();
			}
			if (c.dialogueAction == 5) {
			if (c.slayerTask <= 0) {
				c.getSlayer().giveTask();
				c.getPA()
						.sendFrame126(
								"@or1@Task: @gre@"
										+ c.taskAmount
										+ " "
										+ Server.npcHandler.getNpcListName(c.slayerTask)
										+ " ", 7383);
				c.getPA().closeAllWindows();
			} else {
				c.sendMessage("You already have a slayer task, please reset your task before getting a new one");
				}
			}
			if (c.dialogueAction == 6) {
				c.sendMessage("Slayer will be enabled in some minutes.");
				// c.getSlayer().generateTask();
				// c.getPA().sendFrame126("@whi@Task: @gre@"+Server.npcHandler.getNpcListName(c.slayerTask)+
				// " ", 7383);
				// c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 508) {
				c.getDH().sendDialogues(1030, 925);
				return;
			}
			if (c.doricOption2) {
				c.getDH().sendDialogues(310, 284);
				c.doricOption2 = false;
			}
			if (c.rfdOption) {
				c.getDH().sendDialogues(26, -1);
				c.rfdOption = false;
			}
			if (c.horrorOption) {
				c.getDH().sendDialogues(35, -1);
				c.horrorOption = false;
			}
			if (c.dtOption) {
				c.getDH().sendDialogues(44, -1);
				c.dtOption = false;
			}
			if (c.dtOption2) {
				if (c.lastDtKill == 0) {
					c.getDH().sendDialogues(65, -1);
				} else {
					c.getDH().sendDialogues(49, -1);
				}
				c.dtOption2 = false;
			}

			if (c.caOption2) {
				c.getDH().sendDialogues(106, c.npcType);
				c.caOption2 = false;
			}
			if (c.caOption2a) {
				c.getDH().sendDialogues(102, c.npcType);
				c.caOption2a = false;
			}

			if (c.dialogueAction == 1) {
				c.getDH().sendDialogues(38, -1);
			}
			break;

		case 9167:
			if (c.dialogueAction == 1758) {
				c.calculateTotalPKP();
				for (int i : Client.emblems) {
					c.getItems().deleteItem(12746, 28);
					c.getItems().deleteItem(12748, 28);
					c.getItems().deleteItem(12749, 28);
					c.getItems().deleteItem(12748, 28);
					c.getItems().deleteItem(12750, 28);
					c.getItems().deleteItem(12751, 28);
					c.getItems().deleteItem(12752, 28);
					c.getItems().deleteItem(12753, 28);
					c.getItems().deleteItem(12754, 28);
					c.getItems().deleteItem(12755, 28);
					c.getItems().deleteItem(12756, 28);
				}
				if (c.pkpTotal != 0)
					c.pkPoints += c.pkpTotal;
				c.sendMessage("@red@You have gained " + c.pkpTotal
						+ " PK Points");
				c.pkpTotal = 0;
				c.getPA().closeAllWindows();
				break;
			}
			if (c.dialogueAction == 8203) {
				DuoSlayer.getInstance().assignDuo(c);
			} 
			if (c.dialogueAction == 2245) {
				c.getPA().startTeleport(2110, 3915, 0, "modern");
				c.sendMessage("High Priest teleported you to @red@Lunar Island@bla@.");
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 508) {
				c.getDH().sendDialogues(1030, 925);
				return;
			}
			if (c.dialogueAction == 502) {
				c.getDH().sendDialogues(1030, 925);
				return;
			}
			if (c.dialogueAction == 251) {
				c.getPA().openUpBank();
			}
			if (c.doricOption) {
				c.getDH().sendDialogues(306, 284);
				c.doricOption = false;
			}
			break;
		case 9168:
			if (c.dialogueAction == 1758) {
				c.calculateTotal();
				for (int i : Client.emblems) {
					c.getItems().deleteItem(12746, 28);
					c.getItems().deleteItem(12748, 28);
					c.getItems().deleteItem(12749, 28);
					c.getItems().deleteItem(12748, 28);
					c.getItems().deleteItem(12750, 28);
					c.getItems().deleteItem(12751, 28);
					c.getItems().deleteItem(12752, 28);
					c.getItems().deleteItem(12753, 28);
					c.getItems().deleteItem(12754, 28);
					c.getItems().deleteItem(12755, 28);
					c.getItems().deleteItem(12756, 28);
				}
			if (c.dialogueAction == 8203) {
				DuoSlayer.getInstance().cancelTask(c);
			}
			if (c.dialogueAction == 2245) {
				c.getPA().startTeleport(3230, 2915, 0, "modern");
				c.sendMessage("High Priest teleported you to @red@Desert Pyramid@bla@.");
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 508) {
				c.getDH().sendDialogues(1027, 925);
				return;
			}
			if (c.dialogueAction == 502) {
				c.getDH().sendDialogues(1027, 925);
				return;
			}
			if (c.doricOption) {
				c.getDH().sendDialogues(303, 284);
				c.doricOption = false;
			}
		}
			break;
		case 9169:
			if (c.dialogueAction == 1758) {
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 8203) {
				c.getPA().removeAllWindows();
			}
			if (c.dialogueAction == 2245) {
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 508) {
				c.nextChat = 0;
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 502) {
				c.nextChat = 0;
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 251) {
				c.getDH().sendDialogues(1015, 494);
			}
			if (c.doricOption) {
				c.getDH().sendDialogues(299, 284);
			}
			break;

		case 9158:
			if (c.dialogueAction == 4343) {
				c.getPA().removeAllWindows();
				break;
			}
			if (c.dialogueAction == 2000) {
				c.getPA().closeAllWindows();
				c.sendMessage("Hopefully you come back later.");
			}
			if  (c.dialogueAction == 1340) { 
				if (System.currentTimeMillis() - c.emptyDelay >= 15000);
				c.sendMessage("You decided not to empty your inventory!");
				c.getPA().closeAllWindows();
			}
			if (c.teleAction == 46) {
				c.getPA().spellTeleport(3043, 9781, 0);
				c.getPA().closeAllWindows();
				c.sendMessage("You teleport to the Mining Area");
			}
			if (c.dialogueAction == 105) {
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 46) {
				if (c.getItems().playerHasItem(11128, 1)) {
				c.getItems().deleteItem(6573, 1);
				c.getItems().addItem(11128, 1);
				c.getPA().closeAllWindows();
				c.sendMessage("You make a berserker necklace");
				} else {
					c.sendMessage("You don't have any onyx's.");
				}
			}
			if (c.dialogueAction == 162) {
				c.dialogueAction = 0;
				c.getPA().closeAllWindows();
			}

			if (c.newPlayerAct == 1) {
				c.newPlayerAct = 0;
				c.sendMessage("@red@There is nothing to do in Crandor, i must teleport home and start playing Zaros.");
				c.getPA().removeAllWindows();
			}
			if (c.doricOption2) {
				c.getDH().sendDialogues(309, 284);
				c.doricOption2 = false;
			}
			/*
			 * if (c.dialogueAction == 8) { c.getPA().fixAllBarrows(); } else {
			 * c.dialogueAction = 0; c.getPA().removeAllWindows(); }
			 */
			if (c.dialogueAction == 27) {
				c.getPA().removeAllWindows();
			}
			if (c.caOption2a) {
				c.getDH().sendDialogues(136, c.npcType);
				c.caOption2a = false;
			}
			break;
		/* VENG */
		case 118098:
			c.getPA().castVeng();
			break;
		/** Specials **/
		case 29188:
			c.specBarId = 7636; // the special attack text - sendframe126(S P E
								// C I A L A T T A C K, c.specBarId);
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 29163:
			c.specBarId = 7611;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 33033:
			c.specBarId = 8505;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 29038:
			if (c.playerEquipment[c.playerWeapon] == 4153) {
				c.specBarId = 7486;
				c.getCombat().handleGmaulPlayer();
				c.getItems().updateSpecialBar();
			} else {
				c.specBarId = 7486;
				c.usingSpecial = !c.usingSpecial;
				c.getItems().updateSpecialBar();
			}
			break;

		case 29063:
			if (c.inDuelArena()) {
				c.sendMessage("You can't use dragon battleaxe special attack in Duel Arena, sorry sir.");
			} else {
				if (c.getCombat().checkSpecAmount(
						c.playerEquipment[c.playerWeapon])) {
					c.gfx0(246);
					c.forcedChat("Raarrrrrgggggghhhhhhh!");
					c.startAnimation(1056);
					c.playerLevel[2] = c.getLevelForXP(c.playerXP[2])
							+ (c.getLevelForXP(c.playerXP[2]) * 15 / 100);
					c.getPA().refreshSkill(2);
					c.getItems().updateSpecialBar();
				} else {
					c.sendMessage("You don't have the required special energy to use this attack.");
				}
			}
			break;

		case 48023:
			c.specBarId = 12335;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 29138:
			c.specBarId = 7586;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 29113:
			c.specBarId = 7561;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 113228:
			c.setSidebarInterface(2, 29265);
			break;

		case 114083:
			c.setSidebarInterface(2, 638);
			break;

		case 113247:
			c.getPA().viewBossLog();
			break;

		case 29238:
			c.specBarId = 7686;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		/** Dueling **/
		case 26065: // no forfeit
		case 26040:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(0);
			break;

		case 26066: // no movement
		case 26048:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(1);
			break;

		case 26069: // no range
		case 26042:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(2);
			break;

		case 26070: // no melee
		case 26043:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(3);
			break;

		case 26071: // no mage
		case 26041:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(4);
			break;

		case 26072: // no drinks
		case 26045:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(5);
			break;

		case 26073: // no food
		case 26046:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(6);
			break;

		case 26074: // no prayer
		case 26047:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(7);
			break;

		case 26076: // obstacles
		case 26075:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(8);
			break;

		case 2158: // fun weapons
		case 2157:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(9);
			break;

		case 30136: // sp attack
		case 30137:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(10);
			break;

		case 53245: // no helm
			c.duelSlot = 0;
			c.getTradeAndDuel().selectRule(11);
			break;

		case 53246: // no cape
			c.duelSlot = 1;
			c.getTradeAndDuel().selectRule(12);
			break;

		case 53247: // no ammy
			c.duelSlot = 2;
			c.getTradeAndDuel().selectRule(13);
			break;

		case 53249: // no weapon.
			c.duelSlot = 3;
			c.getTradeAndDuel().selectRule(14);
			break;

		case 53250: // no body
			c.duelSlot = 4;
			c.getTradeAndDuel().selectRule(15);
			break;

		case 53251: // no shield
			c.duelSlot = 5;
			c.getTradeAndDuel().selectRule(16);
			break;

		case 53252: // no legs
			c.duelSlot = 7;
			c.getTradeAndDuel().selectRule(17);
			break;

		case 53255: // no gloves
			c.duelSlot = 9;
			c.getTradeAndDuel().selectRule(18);
			break;

		case 53254: // no boots
			c.duelSlot = 10;
			c.getTradeAndDuel().selectRule(19);
			break;

		case 53253: // no rings
			c.duelSlot = 12;
			c.getTradeAndDuel().selectRule(20);
			break;

		case 53248: // no arrows
			c.duelSlot = 13;
			c.getTradeAndDuel().selectRule(21);
			break;
		case 26018:	
	if(c.inDuelArena()) {
		Client o = (Client) Server.playerHandler.players[c.duelingWith];
		if(o == null) {
			c.getTradeAndDuel().declineDuel();
			o.getTradeAndDuel().declineDuel();
			return;
		}
			
		
		if(c.duelRule[2] && c.duelRule[3] && c.duelRule[4]) {
			c.sendMessage("You won't be able to attack the player with the rules you have set.");
			break;
		}
		c.duelStatus = 2;
		if(c.duelStatus == 2) {
			c.getPA().sendFrame126("Waiting for other player...", 6684);
			o.getPA().sendFrame126("Other player has accepted.", 6684);
		}
		if(o.duelStatus == 2) {
			o.getPA().sendFrame126("Waiting for other player...", 6684);
			c.getPA().sendFrame126("Other player has accepted.", 6684);
		}
		
		if(c.duelStatus == 2 && o.duelStatus == 2) {
			c.canOffer = false;
			o.canOffer = false;
			c.duelStatus = 3;
			o.duelStatus = 3;
			c.getTradeAndDuel().confirmDuel();
			o.getTradeAndDuel().confirmDuel();
		}
	} else {
			Client o = (Client) Server.playerHandler.players[c.duelingWith];
			c.getTradeAndDuel().declineDuel();
			o.getTradeAndDuel().declineDuel();
			c.sendMessage("You can't stake out of Duel Arena.");
	}
		break;

		case 25120:
	if(c.inDuelArena()) {	
		if(c.duelStatus == 5) {
			break;
		}
		Client o1 = (Client) Server.playerHandler.players[c.duelingWith];
		if(o1 == null) {
			c.getTradeAndDuel().declineDuel();
			return;
		}

		c.duelStatus = 4;
		if(o1.duelStatus == 4 && c.duelStatus == 4) {				
			c.getTradeAndDuel().startDuel();
			o1.getTradeAndDuel().startDuel();
			o1.duelCount = 4;
			c.duelCount = 4;
			c.duelDelay = System.currentTimeMillis();
			o1.duelDelay = System.currentTimeMillis();
		} else {
			c.getPA().sendFrame126("Waiting for other player...", 6571);
			o1.getPA().sendFrame126("Other player has accepted", 6571);
		}
	} else {
			Client o = (Client) Server.playerHandler.players[c.duelingWith];
			c.getTradeAndDuel().declineDuel();
			o.getTradeAndDuel().declineDuel();
			c.sendMessage("You can't stake out of Duel Arena.");
	}
		break;

		case 4169: // god spell charge
			c.usingMagic = true;
			if (!c.getCombat().checkMagicReqs(48)) {
				break;
			}

			if (System.currentTimeMillis() - c.godSpellDelay < Config.GOD_SPELL_CHARGE) {
				c.sendMessage("You still feel the charge in your body!");
				break;
			}
			c.godSpellDelay = System.currentTimeMillis();
			c.sendMessage("You feel charged with a magical power!");
			c.gfx100(c.MAGIC_SPELLS[48][3]);
			c.startAnimation(c.MAGIC_SPELLS[48][2]);
			c.usingMagic = false;
			break;

		/*
		 * case 152: c.isRunning2 = !c.isRunning2; int frame = c.isRunning2 ==
		 * true ? 1 : 0; c.getPA().sendFrame36(173,frame); break;
		 */
		case 152:
			c.isRunning2 = !c.isRunning2;
			// c.getPA().setConfig(173, c.isRunning ? 0 : 1);
			int frame = c.isRunning2 == true ? 1 : 0;
			c.getPA().sendFrame36(173, frame);
			break;
		case 9154:
			long buttonDelay = 0;
			if (System.currentTimeMillis() - buttonDelay > 2000) {
				c.logout();
				buttonDelay = System.currentTimeMillis();
			}
			break;
			
		case 226154:
			if (c.takeAsNote == false) {
				c.takeAsNote = true;
			} else if (c.takeAsNote == true) {
				c.takeAsNote = false;
			}
			break;

		case 21010:
			c.takeAsNote = true;
			break;

		case 21011:
			c.takeAsNote = false;
			break;

		// home teleports
		case 4171:
		case 117048:
		case 75010:
		case 50056:
			if (c.duelStatus == 5) {
				c.sendMessage("You can't teleport in during a duel.");
			} else {
				c.getPA().startTeleport(3087, 3499, 0, "modern");
			}
			break;
		/*
		 * case 4171: case 50056: case 117048: if (c.homeTeleDelay <= 0) {
		 * c.homeTele = 10; } else if (c.homeTeleDelay <= 0) { c.homeTele = 10;
		 * }
		 */
		/*
		 * if (c.reset == false) { c.HomePort(); //String type =
		 * c.playerMagicBook == 0 ? "modern" : "ancient";
		 * //c.getPA().startTeleport(Config.EDGEVILLE_X, Config.EDGEVILLE_Y, 0,
		 * type); } else if (c.reset == true) { c.resetHomePort(); }
		 */

		/*
		 * case 50235: case 4140: c.getPA().startTeleport(Config.VARROCK_X,
		 * Config.VARROCK_Y, 0, "modern"); c.teleAction = 1; break;
		 * 
		 * case 4143: case 50245: c.getPA().startTeleport(Config.LUMBY_X,
		 * Config.LUMBY_Y, 0, "modern"); c.teleAction = 2; break;
		 * 
		 * case 50253: case 4146: c.getPA().startTeleport(Config.FALADOR_X,
		 * Config.FALADOR_Y, 0, "modern"); c.teleAction = 3; break;
		 * 
		 * 
		 * case 51005: case 4150: c.getPA().startTeleport(Config.CAMELOT_X,
		 * Config.CAMELOT_Y, 0, "modern"); c.teleAction = 4; break;
		 * 
		 * case 51013: case 6004: c.getPA().startTeleport(Config.ARDOUGNE_X,
		 * Config.ARDOUGNE_Y, 0, "modern"); c.teleAction = 5; break;
		 * 
		 * 
		 * case 51023: case 6005: c.getPA().startTeleport(Config.WATCHTOWER_X,
		 * Config.WATCHTOWER_Y, 0, "modern"); c.teleAction = 6; break;
		 * 
		 * 
		 * case 51031: case 29031: c.getPA().startTeleport(Config.TROLLHEIM_X,
		 * Config.TROLLHEIM_Y, 0, "modern"); c.teleAction = 7; break;
		 * 
		 * case 72038: case 51039: //c.getPA().startTeleport(Config.TROLLHEIM_X,
		 * Config.TROLLHEIM_Y, 0, "modern"); //c.teleAction = 8; break;
		 */
		/*
		 * case 50235: case 4140: case 117112: c.getDH().sendOption5("Cows",
		 * "Rock Crabs", "Experiments", "Earth warriors", "idk"); c.teleAction =
		 * 1; break;
		 * 
		 * case 4143: case 50245: case 117123:
		 * c.getDH().sendOption5("Varrock Dungeon", "Taverly Dungeon",
		 * "Brimhaven Dungeon", "idk", "idk"); c.teleAction = 2;
		 * //c.getPA().startTeleport(3094, 3478, 0, "modern");
		 * //c.sendMessage("NOTHING!"); break;
		 * 
		 * case 50253: case 4146: case 117131: c.getPA().startTeleport(3366,
		 * 3275, 0, "modern"); break;
		 * 
		 * case 51005: case 4150: case 117154:
		 * c.getDH().sendOption5("Edgeville", "Wests Dragons (10 Wild))",
		 * "Easts Dragons (18 Wild)", "Multi-Easts (44 Wild)",
		 * "Dark Castle Multi (17 Wild)"); c.teleAction = 4; break;
		 * 
		 * case 51013: case 6004: case 117162: if
		 * (c.getItems().playerHasItem(555, 2) &&
		 * c.getItems().playerHasItem(563, 2) && c.playerLevel[6] > 50) {
		 * c.getPA().startTeleport(2662, 3305, 0, "modern");
		 * c.getItems().deleteItem2(555, 2); c.getItems().deleteItem2(563, 2); }
		 * else {
		 * c.sendMessage("You don't have the required items and or level."); }
		 * break;
		 * 
		 * case 51023: case 6005: if (c.getItems().playerHasItem(557, 2) &&
		 * c.getItems().playerHasItem(563, 2) && c.playerLevel[6] > 57) {
		 * c.getPA().startTeleport(2549, 3112, 0, "modern");
		 * c.getItems().deleteItem2(557, 2); c.getItems().deleteItem2(563, 2); }
		 * else {
		 * c.sendMessage("You don't have the required items and or level."); }
		 * break;
		 * 
		 * case 29031: if (c.getItems().playerHasItem(554, 2) &&
		 * c.getItems().playerHasItem(563, 2) && c.playerLevel[6] > 60) {
		 * c.getPA().startTeleport(2888, 3674, 0, "modern");
		 * c.getItems().deleteItem2(563, 2); c.getItems().deleteItem2(554, 2); }
		 * else {
		 * c.sendMessage("You don't have the required items and or level."); }
		 * break;
		 * 
		 * case 72038: case 51039: case 117186: if
		 * (c.getItems().playerHasItem(554, 2) &&
		 * c.getItems().playerHasItem(555, 2) && c.getItems().playerHasItem(563,
		 * 2) && c.getItems().playerHasItem(1963, 1) && c.playerLevel[6] > 63) {
		 * c.getPA().startTeleport(2755, 2784, 0, "modern");
		 * c.getItems().deleteItem2(554, 2); c.getItems().deleteItem2(555, 2);
		 * c.getItems().deleteItem2(563, 2); c.getItems().deleteItem2(1963, 1);
		 * } else {
		 * c.sendMessage("You don't have the required items and or level."); }
		 * break;
		 */

		/* TELEPORTS */
		case 50235:
		case 4140:
		case 117112:
			c.getDH().sendOption5("Taverly Dungeon", "Slayer Monsters",
					"Slayer Tower", "Brimhaven Dungeon", "Next");
			c.teleAction = 1;
			break;

		case 4143:
		case 50245:
		case 117123:
			c.getDH().sendOption5("Barrows", "Pest Control", "Jad Mini-Game",
					"Duel Arena", "More");
			c.teleAction = 2;
			break;

		case 50253:
		case 117131:
		case 4146:
			c.getDH().sendOption5("King Black Dragon",
					"Chaos Elemental @red@(Wild)", "Barrelchest Boss",
					"Godwars", "More");
			c.teleAction = 3;
			break;

		case 94142:
			if (c.lastsummon > 0) {
				c.firstslot();
				for (int i = 0; i < 29; i += 1) {
					Server.itemHandler.createGroundItem(c, c.storeditems[i],
							NPCHandler.npcs[c.summoningnpcid].absX,
							NPCHandler.npcs[c.summoningnpcid].absY, 1,
							c.playerId);
					c.storeditems[i] = -1;
					c.occupied[i] = false;
				}
				c.lastsummon = -1;
				c.totalstored = 0;
				c.summoningnpcid = 0;
				c.summoningslot = 0;
				c.sendMessage("Your BoB items have drop on the floor");
			} else {
				c.sendMessage("You do not have a familiar currently spawned");
			}
			break;

		case 66126: // Summoning Special Moves
			if (c.summonSpec < 1) {
				if (c.lastsummon == 7344) {
					final int damage = Misc.random(30) + 10;
					if (c.npcIndex > 0) {
						NPCHandler.npcs[c.npcIndex].hitUpdateRequired2 = true;
						NPCHandler.npcs[c.npcIndex].updateRequired = true;
						NPCHandler.npcs[c.npcIndex].hitDiff2 = damage;
						NPCHandler.npcs[c.npcIndex].HP -= damage;
						c.sendMessage("Your Steel Titan Damages your Opponent.");
						c.startAnimation(1914);
					} else if (c.oldPlayerIndex > 0 || c.playerIndex > 0) {
						Server.playerHandler.players[c.playerIndex].playerLevel[3] -= damage;
						Server.playerHandler.players[c.playerIndex].hitDiff2 = damage;
						Server.playerHandler.players[c.playerIndex].hitUpdateRequired2 = true;
						Server.playerHandler.players[c.playerIndex].updateRequired = true;
						// o.sendMessage("Your opponent's steal titan causes you damage.");
						c.sendMessage("Your Steel Titan Damages your Opponent.");
						c.startAnimation(1914);
					}
				} else if (c.lastsummon == 7340) {
					final int damage = Misc.random(25) + 5;
					if (c.npcIndex > 0) {
						NPCHandler.npcs[c.npcIndex].hitUpdateRequired2 = true;
						NPCHandler.npcs[c.npcIndex].updateRequired = true;
						NPCHandler.npcs[c.npcIndex].hitDiff2 = damage;
						NPCHandler.npcs[c.npcIndex].HP -= damage;
						c.sendMessage("Your Geyser Titan Damages your Opponent.");
						c.startAnimation(1914);
					} else if (c.oldPlayerIndex > 0 || c.playerIndex > 0) {
						Server.playerHandler.players[c.playerIndex].playerLevel[3] -= damage;
						Server.playerHandler.players[c.playerIndex].hitDiff2 = damage;
						Server.playerHandler.players[c.playerIndex].hitUpdateRequired2 = true;
						Server.playerHandler.players[c.playerIndex].updateRequired = true;
						// o.sendMessage("Your opponent's steal titan causes you damage.");
						c.sendMessage("Your Geyser Titan Damages your Opponent.");
						c.startAnimation(1914);
					}
				} else if (c.lastsummon == 7356) {
					final int damage = Misc.random(20) + 5;
					if (c.npcIndex > 0) {
						NPCHandler.npcs[c.npcIndex].hitUpdateRequired2 = true;
						NPCHandler.npcs[c.npcIndex].updateRequired = true;
						NPCHandler.npcs[c.npcIndex].hitDiff2 = damage;
						NPCHandler.npcs[c.npcIndex].HP -= damage;
						c.sendMessage("Your Fire Titan Damages your Opponent.");
						c.startAnimation(1914);
					} else if (c.oldPlayerIndex > 0 || c.playerIndex > 0) {
						Server.playerHandler.players[c.playerIndex].playerLevel[3] -= damage;
						Server.playerHandler.players[c.playerIndex].hitDiff2 = damage;
						Server.playerHandler.players[c.playerIndex].hitUpdateRequired2 = true;
						Server.playerHandler.players[c.playerIndex].updateRequired = true;
						// o.sendMessage("Your opponent's steal titan causes you damage.");
						c.sendMessage("Your Fire Titan Damages your Opponent.");
						c.startAnimation(1914);
					}
				} else if (c.lastsummon == 7350) {
					final int damage = Misc.random(19) + 4;
					if (c.npcIndex > 0) {
						NPCHandler.npcs[c.npcIndex].hitUpdateRequired2 = true;
						NPCHandler.npcs[c.npcIndex].updateRequired = true;
						NPCHandler.npcs[c.npcIndex].hitDiff2 = damage;
						NPCHandler.npcs[c.npcIndex].HP -= damage;
						c.sendMessage("Your Abyssal Titan Damages your Opponent.");
						c.startAnimation(1914);
					} else if (c.oldPlayerIndex > 0 || c.playerIndex > 0) {
						Server.playerHandler.players[c.playerIndex].playerLevel[3] -= damage;
						Server.playerHandler.players[c.playerIndex].hitDiff2 = damage;
						Server.playerHandler.players[c.playerIndex].hitUpdateRequired2 = true;
						Server.playerHandler.players[c.playerIndex].updateRequired = true;
						// o.sendMessage("Your opponent's steal titan causes you damage.");
						c.sendMessage("Your Abyssal Titan Damages your Opponent.");
						c.startAnimation(1914);
					}
				} else if (c.lastsummon == 7358) {
					final int damage = Misc.random(17) + 4;
					if (c.npcIndex > 0) {
						NPCHandler.npcs[c.npcIndex].hitUpdateRequired2 = true;
						NPCHandler.npcs[c.npcIndex].updateRequired = true;
						NPCHandler.npcs[c.npcIndex].hitDiff2 = damage;
						NPCHandler.npcs[c.npcIndex].HP -= damage;
						c.sendMessage("Your Moss Titan Damages your Opponent.");
						c.startAnimation(1914);
					} else if (c.oldPlayerIndex > 0 || c.playerIndex > 0) {
						Server.playerHandler.players[c.playerIndex].playerLevel[3] -= damage;
						Server.playerHandler.players[c.playerIndex].hitDiff2 = damage;
						Server.playerHandler.players[c.playerIndex].hitUpdateRequired2 = true;
						Server.playerHandler.players[c.playerIndex].updateRequired = true;
						// o.sendMessage("Your opponent's steal titan causes you damage.");
						c.sendMessage("Your Moss Titan Damages your Opponent.");
						c.startAnimation(1914);
					}
				} else if (c.lastsummon == 6874) {
					c.getItems().addItem(15272, 3);
					c.sendMessage("Your Pak Yack's Special Supplys you with Food!");
				} else if (c.lastsummon == 6823) {
					c.playerLevel[3] += 25;
					if (c.playerLevel[3] > c.getLevelForXP(c.playerXP[3]))
						c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]);
					c.sendMessage("Your Unicorn's Special Heals you for 250 HP!");
					c.getPA().refreshSkill(3);
				} else if (c.lastsummon == 6814) {
					c.playerLevel[3] += 13;
					if (c.playerLevel[3] > c.getLevelForXP(c.playerXP[3]))
						c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]);
					c.sendMessage("Your Bunyip's Special Heals you for 130 HP!");

					c.getPA().refreshSkill(3);
				} else if (c.lastsummon == 6870) {
					c.playerLevel[3] += 15;
					c.playerLevel[6] += 6;
					c.sendMessage("Your Wolpertinger's Special Heals you for 150 HP!");
					c.sendMessage("Your Wolpertinger's Increases and Restores your Magic!");
					if (c.playerLevel[6] > c.getLevelForXP(c.playerXP[6]))
						c.playerLevel[6] = c.getLevelForXP(c.playerXP[6]) + 6;
					if (c.playerLevel[3] > c.getLevelForXP(c.playerXP[3]))
						c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]);
					c.getPA().refreshSkill(3);
					c.getPA().refreshSkill(6);
				} else {
					c.sendMessage("You have no familiar with special attack spawned!");
				}
				c.summonSpec = 240;
			} else {
				c.sendMessage("You must wait at least 4 Minutes before using this again.");
			}
			break;

		case 77036:
			if (c.lastsummon > 0) {
				c.firstslot();
				for (int i = 0; i < 29; i += 1) {
					Server.itemHandler.createGroundItem(c, c.storeditems[i],
							NPCHandler.npcs[c.summoningnpcid].absX,
							NPCHandler.npcs[c.summoningnpcid].absY, 1,
							c.playerId);
					c.storeditems[i] = -1;
					c.occupied[i] = false;
				}
				c.lastsummon = -1;
				c.totalstored = 0;
				c.summoningnpcid = 0;
				c.summoningslot = 0;
				c.sendMessage("Your BoB items have drop on the floor");
			} else {
				c.sendMessage("You do not have a npc currently spawned");
			}
			break;

		case 51005:
		case 117154:
		case 4150:
			c.getDH().sendOption5("Mage Bank @gre@(SAFE)",
					"Wests Dragons @red@(10 Wildy)",
					"East Dragons @red@(18 Wildy)",
					"Multi-Easts @red@(44 Wild)",
					"Dark Castle Multi @red@(17 Wild)");
			c.teleAction = 4;
			break;
			
		case 51023:
		case 6005:
		case 117186:
			c.getDH().sendOption5("Lumbridge", "Varrock", "Camelot", "Falador",
					"More");
			c.teleAction = 20;
			break;

		case 51013:
		case 6004:
		case 117162:
			c.getDH().sendOption4("Woodcutting", "Farming", "Agility", "More");
			c.teleAction = 45;
			break;

		case 9125: //Accurate
		case 6221: // range accurate
		case 22230: //punch (unarmed)
		case 48010: //flick (whip)
		case 21200: //spike (pickaxe)
		case 1080: //bash (staff)
		case 6168: //chop (axe)
		case 6236: //accurate (long bow)
		case 17102: //accurate (darts)
		case 8234: //stab (dagger)
			c.fightMode = 0;
			if (c.autocasting) {
				c.getPA().resetAutocast();
			}
			break;

		case 9126: //Defensive
		case 48008: //deflect (whip)
		case 22228: //block (unarmed)
		case 21201: //block (pickaxe)
		case 1078: //focus - block (staff)
		case 6169: //block (axe)
		case 33019: //fend (hally)
		case 18078: //block (spear)
		case 8235: //block (dagger)
			c.fightMode = 1;
			if (c.autocasting) {
				c.getPA().resetAutocast();
			}
			break;

		case 9127: // Controlled
		case 48009: //lash (whip)
		case 33018: //jab (hally)
		case 6234: //longrange (long bow)
		case 6219: //longrange
		case 18077: //lunge (spear)
		case 18080: //swipe (spear)
		case 18079: //pound (spear)
		case 17100: //longrange (darts)
			c.fightMode = 3;
			if (c.autocasting) {
				c.getPA().resetAutocast();
			}
			break;

		case 9128: //Aggressive
		case 6220: // range rapid
		case 22229: //kick (unarmed)
		case 21203: //impale (pickaxe)
		case 21202: //smash (pickaxe)
		case 1079: //pound (staff)
		case 6171: //hack (axe)
		case 6170: //smash (axe)
		case 33020: //swipe (hally)
		case 6235: //rapid (long bow)
		case 17101: //repid (darts)
		case 8237: //lunge (dagger)
		case 8236: //slash (dagger)
			c.fightMode = 2;
			if (c.autocasting) {
				c.getPA().resetAutocast();
			}
			break;

		/** Prayers **/
		case 21233: // thick skin
			c.getCombat().activatePrayer(0);
			break;
		case 21234: // burst of str
			c.getCombat().activatePrayer(1);
			break;
		case 21235: // charity of thought
			c.getCombat().activatePrayer(2);
			break;
		case 70080: // range
			c.getCombat().activatePrayer(3);
			break;
		case 70082: // mage
			c.getCombat().activatePrayer(4);
			break;
		case 21236: // rockskin
			c.getCombat().activatePrayer(5);
			break;
		case 21237: // super human
			c.getCombat().activatePrayer(6);
			break;
		case 21238: // improved reflexes
			c.getCombat().activatePrayer(7);
			break;
		case 21239: // hawk eye
			c.getCombat().activatePrayer(8);
			break;
		case 21240:
			c.getCombat().activatePrayer(9);
			break;
		case 21241: // protect Item
			c.getCombat().activatePrayer(10);
			break;
		case 70084: // 26 range
			c.getCombat().activatePrayer(11);
			break;
		case 70086: // 27 mage
			c.getCombat().activatePrayer(12);
			break;
		case 21242: // steel skin
			c.getCombat().activatePrayer(13);
			break;
		case 21243: // ultimate str
			c.getCombat().activatePrayer(14);
			break;
		case 21244: // incredible reflex
			c.getCombat().activatePrayer(15);
			break;
		case 21245: // protect from magic
			c.getCombat().activatePrayer(16);
			break;
		case 21246: // protect from range
			c.getCombat().activatePrayer(17);
			break;
		case 21247: // protect from melee
			c.getCombat().activatePrayer(18);
			break;
		case 70088: // 44 range
			c.getCombat().activatePrayer(19);
			break;
		case 70090: // 45 mystic
			c.getCombat().activatePrayer(20);
			break;
		case 2171: // retrui
			c.getCombat().activatePrayer(21);
			break;
		case 2172: // redem
			c.getCombat().activatePrayer(22);
			break;
		case 2173: // smite
			c.getCombat().activatePrayer(23);
			break;
		case 70092: // chiv
			c.getCombat().activatePrayer(24);
			break;
		case 70094: // piety
			c.getCombat().activatePrayer(25);
			break;

		case 13092:
			if (c.inWild()) {
				c.getTradeAndDuel().declineTrade();
				break;
			}
			if (c.inMithWild()) {
				c.getTradeAndDuel().declineTrade();
				break;
			}
			if (System.currentTimeMillis() - c.lastButton < 400) {

				c.lastButton = System.currentTimeMillis();

				break;

			} else {

				c.lastButton = System.currentTimeMillis();

			}
			Client ot = (Client) PlayerHandler.players[c.tradeWith];
			if (ot == null) {
				c.getTradeAndDuel().declineTrade();
				c.sendMessage("Trade declined as the other player has disconnected.");
				break;
			}
			c.getPA().sendFrame126("Waiting for other player...", 3431);
			ot.getPA().sendFrame126("Other player has accepted", 3431);
			c.goodTrade = true;
			ot.goodTrade = true;

			for (GameItem item : c.getTradeAndDuel().offeredItems) {
				if (item.id > 0) {
					if (ot.getItems().freeSlots() < c.getTradeAndDuel().offeredItems
							.size()) {
						c.sendMessage(ot.playerName
								+ " only has "
								+ ot.getItems().freeSlots()
								+ " free slots, please remove "
								+ (c.getTradeAndDuel().offeredItems.size() - ot
										.getItems().freeSlots()) + " items.");
						ot.sendMessage(c.playerName
								+ " has to remove "
								+ (c.getTradeAndDuel().offeredItems.size() - ot
										.getItems().freeSlots())
								+ " items or you could offer them "
								+ (c.getTradeAndDuel().offeredItems.size() - ot
										.getItems().freeSlots()) + " items.");
						c.goodTrade = false;
						ot.goodTrade = false;
						c.getPA().sendFrame126("Not enough inventory space...",
								3431);
						ot.getPA().sendFrame126(
								"Not enough inventory space...", 3431);
						break;
					} else {
						c.getPA().sendFrame126("Waiting for other player...",
								3431);
						ot.getPA().sendFrame126("Other player has accepted",
								3431);
						c.goodTrade = true;
						ot.goodTrade = true;
					}
				}
			}
			if (c.inTrade && !c.tradeConfirmed && ot.goodTrade && c.goodTrade) {
				c.tradeConfirmed = true;
				if (ot.tradeConfirmed) {
					c.getTradeAndDuel().confirmScreen();
					ot.getTradeAndDuel().confirmScreen();
					break;
				}

			}

			break;

		case 13218:
			if (System.currentTimeMillis() - c.lastButton < 400) {

				c.lastButton = System.currentTimeMillis();

				break;

			} else {

				c.lastButton = System.currentTimeMillis();

			}

			Client ot1 = (Client) PlayerHandler.players[c.tradeWith];
				c.tradeAccepted = true;
				if (ot1 == null) {
					c.getTradeAndDuel().declineTrade();
					c.sendMessage("Trade declined as the other player has disconnected.");
					break;
				}

				if (c.inTrade && c.tradeConfirmed && ot1.tradeConfirmed
						&& !c.tradeConfirmed2) {
					c.tradeConfirmed2 = true;
					if (ot1.tradeConfirmed2) {
						c.acceptedTrade = true;
						ot1.acceptedTrade = true;
						c.getTradeAndDuel().giveItems();
						ot1.getTradeAndDuel().giveItems();
						break;
					}
					ot1.getPA()
							.sendFrame126("Other player has accepted.", 3535);
					c.getPA().sendFrame126("Waiting for other player...", 3535);
				}

			break;
		/* Rules Interface Buttons */
		case 125011: // Click agree
			if (!c.ruleAgreeButton) {
				c.ruleAgreeButton = true;
				c.getPA().sendFrame36(701, 1);
			} else {
				c.ruleAgreeButton = false;
				c.getPA().sendFrame36(701, 0);
			}
			break;
		case 125003:// Accept
			if (c.ruleAgreeButton) {
				c.getPA().showInterface(3559);
				c.newPlayer = false;
			} else if (!c.ruleAgreeButton) {
				c.sendMessage("You need to click on you agree before you can continue on.");
			}
			break;
		case 125006:// Decline
			c.sendMessage("You have chosen to decline, Client will be disconnected from the server.");
			break;
		/* End Rules Interface Buttons */
		/* Player Options */
		case 74176:
			if (!c.mouseButton) {
				c.mouseButton = true;
				c.getPA().sendFrame36(500, 1);
				c.getPA().sendFrame36(170, 1);
			} else if (c.mouseButton) {
				c.mouseButton = false;
				c.getPA().sendFrame36(500, 0);
				c.getPA().sendFrame36(170, 0);
			}
			break;
		case 100234:
			if (c.splitChat == false) {
				c.getPA().sendFrame36(502,  1);
				c.getPA().sendFrame36(287,  1);
				c.splitChat = true;
			} else if (c.splitChat == true) {
				c.getPA().sendFrame36(502,  0);
				c.getPA().sendFrame36(287,  0);
				c.splitChat = false;
			}
			break;
		case 74180:
			if (!c.chatEffects) {
				c.chatEffects = true;
				c.getPA().sendFrame36(501, 1);
				c.getPA().sendFrame36(171, 0);
			} else {
				c.chatEffects = false;
				c.getPA().sendFrame36(501, 0);
				c.getPA().sendFrame36(171, 1);
			}
			break;
		case 74188:
			if (!c.acceptAid) {
				c.acceptAid = true;
				c.getPA().sendFrame36(503, 1);
				c.getPA().sendFrame36(427, 1);
			} else {
				c.acceptAid = false;
				c.getPA().sendFrame36(503, 0);
				c.getPA().sendFrame36(427, 0);
			}
			break;
		case 74192:
			if (!c.isRunning2) {
				c.isRunning2 = true;
				c.getPA().sendFrame36(504, 1);
				c.getPA().sendFrame36(173, 1);
			} else {
				c.isRunning2 = false;
				c.getPA().sendFrame36(504, 0);
				c.getPA().sendFrame36(173, 0);
			}
			break;
		case 74201:// brightness1
			c.getPA().sendFrame36(505, 1);
			c.getPA().sendFrame36(506, 0);
			c.getPA().sendFrame36(507, 0);
			c.getPA().sendFrame36(508, 0);
			c.getPA().sendFrame36(166, 1);
			break;
		case 74203:// brightness2
			c.getPA().sendFrame36(505, 0);
			c.getPA().sendFrame36(506, 1);
			c.getPA().sendFrame36(507, 0);
			c.getPA().sendFrame36(508, 0);
			c.getPA().sendFrame36(166, 2);
			break;

		case 74204:// brightness3
			c.getPA().sendFrame36(505, 0);
			c.getPA().sendFrame36(506, 0);
			c.getPA().sendFrame36(507, 1);
			c.getPA().sendFrame36(508, 0);
			c.getPA().sendFrame36(166, 3);
			break;

		case 74205:// brightness4
			c.getPA().sendFrame36(505, 0);
			c.getPA().sendFrame36(506, 0);
			c.getPA().sendFrame36(507, 0);
			c.getPA().sendFrame36(508, 1);
			c.getPA().sendFrame36(166, 4);
			break;
		case 74206:// area1
			c.getPA().sendFrame36(509, 1);
			c.getPA().sendFrame36(510, 0);
			c.getPA().sendFrame36(511, 0);
			c.getPA().sendFrame36(512, 0);
			break;
		case 74207:// area2
			c.getPA().sendFrame36(509, 0);
			c.getPA().sendFrame36(510, 1);
			c.getPA().sendFrame36(511, 0);
			c.getPA().sendFrame36(512, 0);
			break;
		case 74208:// area3
			c.getPA().sendFrame36(509, 0);
			c.getPA().sendFrame36(510, 0);
			c.getPA().sendFrame36(511, 1);
			c.getPA().sendFrame36(512, 0);
			break;
		case 74209:// area4
			c.getPA().sendFrame36(509, 0);
			c.getPA().sendFrame36(510, 0);
			c.getPA().sendFrame36(511, 0);
			c.getPA().sendFrame36(512, 1);
			break;
		case 168:
			c.startAnimation(855);
			break;
		case 169:
			c.startAnimation(856);
			break;
		case 162:
			c.startAnimation(857);
			break;
		case 164:
			c.startAnimation(858);
			break;
		case 165:
			c.startAnimation(859);
			break;
		case 161:
			c.startAnimation(860);
			break;
		case 170:
			c.startAnimation(861);
			break;
		case 171:
			c.startAnimation(862);
			break;
		case 163:
			c.startAnimation(863);
			break;
		case 167:
			c.startAnimation(864);
			break;
		case 172:
			c.startAnimation(865);
			break;
		case 166:
			c.startAnimation(866);
			break;
		case 52050:
			c.startAnimation(2105);
			break;
		case 52051:
			c.startAnimation(2106);
			break;
		case 52052:
			c.startAnimation(2107);
			break;
		case 52053:
			c.startAnimation(2108);
			break;
		case 52054:
			c.startAnimation(2109);
			break;
		case 52055:
			c.startAnimation(2110);
			break;
		case 52056:
			c.startAnimation(2111);
			break;
		case 52057:
			c.startAnimation(2112);
			break;
		case 52058:
			c.startAnimation(2113);
			break;
		case 43092:
			c.startAnimation(0x558);
			break;
		case 2155:
			c.startAnimation(0x46B);
			break;
		case 25103:
			c.startAnimation(0x46A);
			break;
		case 25106:
			c.startAnimation(0x469);
			break;
		case 2154:
			c.startAnimation(0x468);
			break;
		case 52071:
			c.startAnimation(0x84F);
			break;
		case 52072:
			c.startAnimation(0x850);
			break;
		case 59062:
			c.startAnimation(2836);
			break;
		case 72032:
			c.startAnimation(3544);
			break;
		case 72033:
			c.startAnimation(3543);
			break;
		case 72254:
			c.startAnimation(6111);
			break;
		/* END OF EMOTES */
		case 49022:
			if (c.dialogueAction == 879) {
				if (c.getPotentialPartner() != null) {
					if (!c.asked) {
						return;
					}
					DuoSlayer.getInstance().accept(c,
							c.getPotentialPartner());
					c.asked = false;
				} else {
					c.sendMessage("You do not have a open request.");
				}
			}
			c.dialogueAction = 0;
			break;
			
		case 49024:
			if (c.dialogueAction == 879) {
				if (!c.asked) {
					return;
				}
				DuoSlayer.getInstance().decline(c,
						c.getPotentialPartner());
				c.asked = false;
			}
			c.dialogueAction = 0;
			break;
		case 24017:
			c.getPA().resetAutocast();
			// c.sendFrame246(329, 200, c.playerEquipment[c.playerWeapon]);
			c.getItems()
					.sendWeapon(
							c.playerEquipment[c.playerWeapon],
							c.getItems().getItemName(
									c.playerEquipment[c.playerWeapon]));
			// c.setSidebarInterface(0, 328);
			// c.setSidebarInterface(6, c.playerMagicBook == 0 ? 1151 :
			// c.playerMagicBook == 1 ? 12855 : 1151);
			break;
		}
		if (c.isAutoButton(actionButtonId))
			c.assignAutocast(actionButtonId);
	}

}
