package main.model.npcs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import main.model.items.*;
import main.model.npcs.dropsystem.*;
import main.Config;
import main.Server;
import main.clip.region.Region;
import main.event.CycleEvent;
import main.event.CycleEventContainer;
import main.event.CycleEventHandler;
import main.event.Event;
import main.event.EventContainer;
import main.event.EventManager;
import main.model.minigames.Barrows;
import main.model.npcs.pet.Pet;
import main.model.players.Client;
import main.model.players.Events;
import main.model.players.Player;
import main.model.players.PlayerHandler;
import main.model.players.skills.DuoSlayer;
import main.util.Misc;

public class NPCHandler {
	public static int maxNPCs = 10000;
	public static int maxListedNPCs = 10000;
	public static int maxNPCDrops = 10000;
	public static NPC npcs[] = new NPC[maxNPCs];

	public NPCHandler() {
		for (int i = 0; i < maxNPCs; i++) {
			npcs[i] = null;
			NPCDefinitions.getDefinitions()[i] = null;
		}

		loadNPCList("./Data/CFG/npc.cfg");
		loadAutoSpawn("./Data/CFG/spawn-config.cfg");
	}

	public static final int[] slayerReqs = { 1648, 5, 1600, 10, 1612, 15, 1631,
		20, 1620, 25, 1633, 30, 3153, 33, 1616, 40, 1643, 45, 1618, 50,
		1637, 52, 1623, 55, 1604, 60, 6220, 63, 1608, 70, 3068, 72, 9467,
		73, 1610, 75, 9465, 77, 9172, 78, 1613, 80, 1615, 85, 2783, 90,
		9463, 93, 1624, 65 };
	
	public void multiAttackGfx(int i, int gfx) {
		if (npcs[i].projectileId < 0)
			return;
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c = (Client) PlayerHandler.players[j];
				if (c.heightLevel != npcs[i].heightLevel)
					continue;
				if (PlayerHandler.players[j].goodDistance(c.absX, c.absY,
						npcs[i].absX, npcs[i].absY, 15)) {
					int nX = NPCHandler.npcs[i].getX() + offset(i);
					int nY = NPCHandler.npcs[i].getY() + offset(i);
					int pX = c.getX();
					int pY = c.getY();
					int offX = (nY - pY) * -1;
					int offY = (nX - pX) * -1;
					c.getPA().createPlayersProjectile(nX, nY, offX, offY, 50,
							getProjectileSpeed(i), npcs[i].projectileId, 43,
							31, -c.getId() - 1, 65);
				}
			}
		}
	}
	
	public boolean isZombie(int j) {
        switch (NPCHandler.npcs[j].npcType) {
        	case 73:
		    case 74 :
		    case 75 :
		    case 76 :
		    case 77 :
		    case 1826 :
		    case 2837 :
		    case 2839 :
		    case 2840 :
		    case 2841 :
		    case 2842 :
		    case 2060 :
		    case 5399 :
		    case 5397 :
		    case 5398 :
		    case 5395 :
		    case 3622 :
		    case 3066 :
                        return true;
                default:
                        return false;
        }
}

	public static void spawnNpc2(int npcType, int x, int y, int heightLevel,
			int WalkingType, int HP, int maxHit, int attack, int defence) {
		// first, search for a free slot
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
				break;
			}
		}
		if (slot == -1) {
			// Misc.println("No Free Slot");
			return; // no free slot found
		}
		NPC newNPC = new NPC(slot, npcType);
		newNPC.absX = x;
		newNPC.absY = y;
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.heightLevel = heightLevel;
		newNPC.walkingType = WalkingType;
		newNPC.HP = HP;
		newNPC.MaxHP = HP;
		newNPC.maxHit = maxHit;
		newNPC.attack = attack;
		newNPC.defence = defence;
		npcs[slot] = newNPC;
	}

	public int spawnNpc3(Client c, int npcType, int x, int y, int heightLevel,
			int WalkingType, int HP, int maxHit, int attack, int defence,
			boolean attackPlayer, boolean headIcon, boolean summonFollow) {
		// first, search for a free slot
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
				break;
			}
		}
		if (slot == -1) {
			return -1;
		}
		NPC newNPC = new NPC(slot, npcType);
		newNPC.absX = x;
		newNPC.absY = y;
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.heightLevel = heightLevel;
		newNPC.walkingType = WalkingType;
		newNPC.HP = HP;
		newNPC.MaxHP = HP;
		newNPC.maxHit = maxHit;
		newNPC.attack = attack;
		newNPC.defence = defence;
		newNPC.spawnedBy = c.getId();
		newNPC.facePlayer(c.playerId);
		if (headIcon)
			c.getPA().drawHeadicon(1, slot, 0, 0);
		if (summonFollow) {
			newNPC.summoner = true;
			newNPC.summonedBy = c.playerId;
			c.summonId = npcType;
			c.hasNpc = true;
		}
		if (attackPlayer) {
			/*
			 * newNPC.underAttack = false; if(c != null) { newNPC.killerId =
			 * c.playerId; }
			 */
		}
		npcs[slot] = newNPC;
		followPlayer(slot, c.playerId);
		return slot;
	}

	public int summonItemId(int itemId) {
		if (itemId == 1555)
			return 761;
		if (itemId == 1556)
			return 762;
		if (itemId == 1557)
			return 763;
		if (itemId == 1558)
			return 764;
		if (itemId == 1559)
			return 765;
		if (itemId == 1560)
			return 766;
		if (itemId == 1561)
			return 768;
		if (itemId == 1562)
			return 769;
		if (itemId == 1563)
			return 770;
		if (itemId == 1564)
			return 771;
		if (itemId == 1565)
			return 772;
		if (itemId == 1566)
			return 773;
		if (itemId == 7585)
			return 3507;
		if (itemId == 7584)
			return 3506;
		if (itemId == 7583)
			return 3505;
		if (itemId == 12653)
			return 4000; // prince
		if (itemId == 12694)
			return 4003; // ele
		if (itemId == 12650)
			return 4001; // bandos
		if (itemId == 12649)
			return 4005; // arma
		if (itemId == 12652)
			return 4006; // zammy
		if (itemId == 12651)
			return 4007; // sara
		if (itemId == 12643)
			return 4008; // dag sup
		if (itemId == 12644)
			return 4009; // dag prime
		if (itemId == 12645)
			return 4010; // dag rex
		if (itemId == 12655)
			return 6305; // kraken
		return 0;
	}

	public boolean switchesAttackers(int i) {
		switch (npcs[i].npcType) {
		case 4056:
			return true;
		case 3068:
		case 3943:
		case 2551:
		case 2552:
		case 2553:
		case 2559:
		case 2560:
		case 3847:
		case 2561:
		case 2563:
		case 2564:
		case 2565:
		case 2892:
		case 2894:
		case 6218:
		case 1158:
		case 1160:
			return true;

		}

		return false;
	}

	public void multiAttackDamage(int i) {
		int max = getMaxHit(i);
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c = (Client) PlayerHandler.players[j];
				if (c.isDead || c.heightLevel != npcs[i].heightLevel)
					continue;
				if (PlayerHandler.players[j].goodDistance(c.absX, c.absY,
						npcs[i].absX, npcs[i].absY, 15)) {
					if (npcs[i].attackType == 2) {
						if (!c.prayerActive[16]) {
							if (Misc.random(500) + 200 > Misc.random(c
									.getCombat().mageDef())) {
								int dam = Misc.random(max);
								c.dealDamage(dam);
								c.handleHitMask(dam);
							} else {
								c.dealDamage(0);
								c.handleHitMask(0);
							}
						} else {
							c.dealDamage(0);
							c.handleHitMask(0);
						}
					} else if (npcs[i].attackType == 1) {
						if (!c.prayerActive[17]) {
							int dam = Misc.random(max);
							if (Misc.random(500) + 200 > Misc.random(c
									.getCombat().calculateRangeDefence())) {
								c.dealDamage(dam);
								c.handleHitMask(dam);
							} else {
								c.dealDamage(0);
								c.handleHitMask(0);
							}
						} else {
							c.dealDamage(0);
							c.handleHitMask(0);
						}
					}
					if (npcs[i].endGfx > 0) {
						c.gfx0(npcs[i].endGfx);
					}
				}
				c.getPA().refreshSkill(3);
			}
		}
	}

	public int getClosePlayer(int i) {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				if (j == npcs[i].spawnedBy)
					return j;
				if (goodDistance(PlayerHandler.players[j].absX,
						PlayerHandler.players[j].absY, npcs[i].absX,
						npcs[i].absY, 2 + distanceRequired(i)
								+ followDistance(i))
						|| isFightCaveNpc(i)) {
					if ((PlayerHandler.players[j].underAttackBy <= 0 && PlayerHandler.players[j].underAttackBy2 <= 0)
							|| PlayerHandler.players[j].inMulti())
						if (PlayerHandler.players[j].heightLevel == npcs[i].heightLevel)
							return j;
				}
			}
		}
		return 0;
	}

	public int getCloseRandomPlayer(int i) {
		ArrayList<Integer> players = new ArrayList<Integer>();
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				if (goodDistance(PlayerHandler.players[j].absX,
						PlayerHandler.players[j].absY, npcs[i].absX,
						npcs[i].absY, 2 + distanceRequired(i)
								+ followDistance(i))
						|| isFightCaveNpc(i)) {
					if ((PlayerHandler.players[j].underAttackBy <= 0 && PlayerHandler.players[j].underAttackBy2 <= 0)
							|| PlayerHandler.players[j].inMulti())
						if (PlayerHandler.players[j].heightLevel == npcs[i].heightLevel)
							players.add(j);
				}
			}
		}
		if (players.size() > 0)
			return players.get(Misc.random(players.size() - 1));
		else
			return 0;
	}

	public int npcSize(int i) {
		switch (npcs[i].npcType) {
		case 2883:
		case 2882:
		case 2881:
			return 3;
		}
		return 0;
	}

	public boolean isAggressive(int i) {
		switch (npcs[i].npcType) {
		case 4056:
		case 3499:
		case 3500:
		case 3501:
		case 3502:
		case 3943:
		case 5363:
		case 3847:
		//case 4056:
		case 1158:
		case 1160:
		case 2550:
		case 2551:
		case 2552:
		case 2553:
		case 2558:
		case 2559:
		case 2560:
		case 2561:
		case 2562:
		case 2563:
		case 2564:
		case 2565:
		case 2892:
		case 2894:
		case 2881:
		case 2882:
		case 2883:
		case 2035:
			// GWD
		case 6250:// Npcs That Give ArmaKC
		case 6230:
		case 6231:
		case 6229:
		case 6232:
		case 6240:
		case 6241:
		case 6242:
		case 6233:
		case 6234:
		case 6243:
		case 6244:
		case 6245:
		case 6246:
		case 6238:
		case 6239:
		case 6227:
		case 6625:
		case 6223:
		case 6222: // end of armadyl npcs
		case 122:// Npcs That Give BandosKC
		case 100:// Npcs That Give BandosKC
		case 6278:
		case 6277:
		case 6276:
		case 6283:
		case 6282:
		case 6281:
		case 6280:
		case 6279:
		case 6271:
		case 6272:
		case 6273:
		case 6274:
		case 6269:
		case 6270:
		case 6268:
		case 6265:
		case 6263:
		case 6261:
		//case 6260: // end of bandos npcs
		case 6221:
		case 6219:
		case 6220:
		case 6217:
		case 6216:
		case 6215:
		case 6214:
		case 6213:
		case 6212:
		case 6211:
		case 6218:
		case 6208:
		case 6206:
		case 6204:
		case 6203:
		case 6275:
		case 6257:// Npcs That Give SaraKC
		case 6255:
		case 6256:
		case 6258:
		case 6259:
		case 6254:
		case 6252:
		case 6248:
		case 6247:

			return true;
		}
		if (isZombie(i))
			return true;
		if (npcs[i].inWild() && npcs[i].MaxHP > 0)
			return true;
		if (isFightCaveNpc(i))
			return true;
		return false;
	}

	public boolean isFightCaveNpc(int i) {
		switch (npcs[i].npcType) {
		case 2627:
		case 2630:
		case 2631:
		case 2741:
		case 2743:
		case 2745:
			return true;
		}
		return false;
	}

	/**
	 * Summon npc, barrows, etc
	 **/
	public void spawnNpc(final Client c, int npcType, int x, int y,
			int heightLevel, int WalkingType, int HP, int maxHit, int attack,
			int defence, boolean attackPlayer, boolean headIcon) {
		// first, search for a free slot
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
				break;
			}
		}
		if (slot == -1) {
			// Misc.println("No Free Slot");
			return; // no free slot found
		}
		final NPC newNPC = new NPC(slot, npcType);
		newNPC.absX = x;
		newNPC.absY = y;
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.heightLevel = heightLevel;
		newNPC.walkingType = WalkingType;
		newNPC.HP = HP;
		newNPC.MaxHP = HP;
		newNPC.maxHit = maxHit;
		newNPC.attack = attack;
		newNPC.defence = defence;
		newNPC.spawnedBy = c.getId();
		if (headIcon)
			c.getPA().drawHeadicon(1, slot, 0, 0);
		if (attackPlayer) {
			newNPC.underAttack = true;
			if (c != null) {
	            if(Barrows.COFFIN_AND_BROTHERS[c.randomCoffin][1] != newNPC.npcType && (newNPC.npcType == 2025 || newNPC.npcType == 2026 || newNPC.npcType == 2027 || newNPC.npcType == 2028 || newNPC.npcType == 2029 || newNPC.npcType == 2030)) {
	            	newNPC.forceChat("You dare disturb my rest!");
	             }

	             if(Barrows.COFFIN_AND_BROTHERS[c.randomCoffin][1] == newNPC.npcType) {
	            	 newNPC.forceChat("You dare steal from us!");
	             }

	             if(npcType >= 4279 && npcType <= 4284) {
	            	 newNPC.forceChat("I\'m ALIVE!");
	             }

				newNPC.killerId = c.playerId;
			}
		}
		for (int[] Guard : Events.NPCs) {
			if (newNPC.npcType == Guard[2]) {
				newNPC.forceChat("Halt, Thief!");
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						container.stop();
					}

					@Override
					public void stop() {
						newNPC.isDead = true;
						newNPC.updateRequired = true;
						c.hasEvent = false;
					}
				}, 200);
			}
		}
		npcs[slot] = newNPC;
	}

	/**
	 * Emotes
	 **/

	public static int getAttackEmote(int i) {
		for (int npc = 2015; npc < 2021; npc++) {
			if(NPCHandler.npcs[i].npcType == npc) {
			return 	5554;
			}
			}
		for (int npc = 1958; npc < 1969; npc++) {
		if(NPCHandler.npcs[i].npcType == npc) {
		return 	5549;
		}
		}
		switch (NPCHandler.npcs[i].npcType) {	
		case 2044: //zulrah
		case 2042:
			return 5068;			
		case 2043:
			return 5807;			
		case 3068:// wyv
		if (npcs[i].attackType == 0)
		return 2989;
		if (npcs[i].attackType == 3)
		return 2989;		
		case 3499: //callisto
				return 4925;
		 case 3501: // Scorpia
            return 6254;
		case 3500: // Venenatis
            return 5327;  
		case 3502: // Vet'ion
            return 5485;
		case 3847:
			return 3992;
//list starts here
		case 1940:
			return 1142;
		case 1600:
		case 1601:
		case 1602:
		case 1603:
			return 227;
		case 1218:
		return 1263;
		case 5993://Experiment No.2
			return 6513;
		case 5902:
		    return 6319;
	 
		case 6261:
		case 6263:
		case 6265:
		    return 6154;
	 
		case 1560:
		case 1561:
		case 1562:
		case 1563:
		case 1564:
		case 1565:
		case 1566:
		    return 284;
	 
		case 1317:
		case 2267:
		case 2268:
		case 2269:
		case 3181:
		case 3182:
		case 3183:
		case 3184:
		case 3185:
		    return 451;
	 
		case 1973:
		    return 5499;
	 
		case 1976:
		    return 6562;
	 
		case 6279:
		case 6280:
		case 6281:
		case 6282:
		    return 6185;
	 
		case 5529:
		    return 5782;
	 
		case 6116:
		    return 6811;
	 
		case 5247:
		    return 5411;
	 
		case 6260:
		    return 7060;
	 
		case 5219:
		case 5218:
		case 5217:
		case 5216:
		case 5215:
		case 5214:
		case 5213:
		    return 5097;
	 
		case 5233:
		case 5232:
		case 5231:
		case 5230:
		    return 5395;
	 
		case 3761:
		case 3760:
		    return 3880;
	 
		case 3771:
		    return 3922;
	 
		case 3062:
		    return 2955;
	 
		case 131:
		    return 5669;
	 
		case 4404:
		case 4405:
		case 4406:
		    return 4266;
	 
		case 1019:
		    return 1029;
	 
		case 1020:
		    return 1035;
	 
		case 1021:
		    return 1040;
	 
		case 1022:
		    return 1044;
	 
		case 1676:
		    return 1626;
	 
		case 1677:
		    return 1616;
	 
		case 1678:
		    return 1612;
	 
		case 195:
		case 196:
		case 202:
		case 204:
		case 203:
		    return 412;
	 
		case 4353:
		case 4354:
		case 4355:
		    return 4234;
	 
		case 2709:
		case 2710:
		case 2711:
		case 2712:
		    return 1162;
	 
		case 1007:
		    return 811;
	 
		case 1608:
		    return 1512;
	 
		case 3058:
		    return 2930;
	 
		case 113:
		    return 128;
	 
		case 114:
		case 115:
		    return 359;
	 
		case 1265:
		    return 1312;
	 
		case 118:
		    return 99;
	 
		case 127:
		    return 185;
	 
		case 914:
		    return 197;
	 
		case 1620:
		case 1621:
		    return 1562;
	 
		case 678:
		    return 426;
	 
		case 1192:
		    return 1245;
	 
		case 119:
		    return 99;
	 
		case 2263:
		    return 2182;
	 
		case 3347:
		case 3346:
		    return 3326;
	 
		case 3063:
		    return 2930;
	 
		case 3061:
		    return 2958;
	 
		case 3066:
		    return 2927;
	 
		case 3452:
		    return 5411;
	 
		case 2783:
		    return 2731;
	 
		case 908:
		    return 128;
	 
		case 909:
		    return 143;
	 
		case 911:
		    return 64;
	 
		case 1624:
		case 1625:
		    return 1557;
	 
		case 3060:
		    return 2962;
	 
		case 2598:
		case 2599:
		case 2600:
		case 2601:
		case 2602:
		case 2603:
		case 2604:
		    return 2609;
	 
		case 2892:
		    return 2869;
	 
		case 2881:
		    return 2855;
	 
		case 2882:
		    return 2854;
	 
		case 2883:
		    return 2853;
	 
		case 2457:
		    return 2365;
	 
		case 1590:
		case 1591:
		case 1592:
		    return 80;
	 
		case 1341:
		    return 1341;
	 
		case 2606:
		    return 2609;
	 
		case 66:
		case 67:
		case 168:
		case 169:
		case 162:
		case 68:
		    return 190;
	 
		case 913:
		case 912:
		    return 1162;
	 
		case 160:
		case 161:
		    return 191;
	 
		case 163:
		case 164:
		    return 192;
	 
		case 438:
		case 439:
		case 440:
		case 441:
		case 442:
		case 443:
		    return 94;
	 
		case 391:
		case 392:
		case 393:
		case 394:
		case 395:
		case 396:
		    return 284;
	 
		case 413:
		case 414:
		case 415:
		case 416:
		case 417:
		case 418:
		    return 153;
	 
		case 3741:
		    return 3901;
	 
		case 3776:
		    return 3897;
	 
		case 3751:
		case 3750:
		case 3749:
		case 3748:
		case 3747:
		    return 3908;
	 
		case 1153:
		case 1154:
		case 1155:
		case 1156:
		case 1157:
		case 1158:
		    return 6223;
	 
		case 1160:
		    return 6235;
	 
		case 2734:
		case 2627:
		    return 2621;
	 
		case 2630:
		case 2738:
		case 2736:
		case 2629:
		    return 2625;
	 
		case 2631:
		case 2632:
		    return 2633;
	 
		case 2741:
		    return 2636;
	 
		case 60:
		case 62:
		case 64:
		case 59:
		case 61:
		case 63:
		case 134:
		case 1009:
		case 2035:
		    return 5327;
	 
		case 6006:
		    return 6547;
	 
		case 1612:
		    return 1523;
	 
		case 1648:
		case 1649:
		case 1650:
		case 1651:
		case 1652:
		case 1653:
		case 1654:
		case 1655:
		case 1656:
		case 1657:
		    return 1592;
	 
		case 1604:
		case 1605:
		case 1606:
		case 1607:
		    return 1507;
	 
		case 1618:
		case 1619:
		    return 1552;
	 
		case 1643:
		case 1644:
		case 1645:
		case 1646:
		case 1647:
		    return 429;
	 
		case 1613:
		    return 1528;
	 
		case 1610:
		case 1611:
		    return 1517;
	 
		case 1615:
		    return 1537;
	 
		case 1633:
		case 3406:
		    return 1582;
	 
		case 1459:
		    return 1402;
	 
		case 1456:
		    return 1394;
	 
		case 1125:
		    return 284;
	 
		case 1096:
		case 1097:
		case 1098:
		case 1106:
		case 1942:
		case 1108:
		case 1109:
		case 1110:
		case 1111:
		case 1112:
		case 1116:
		case 1117:
		case 1101:
		    return 284;
	 
		case 1095:
		    return 1142;
	 
		case 49:
		case 142:
		case 95:
		case 1951:
		    return 6581;
	 
		case 74:
		case 75:
		case 76:
		    return 5571;
	 
		case 73:
		case 751:
		case 77:
		    return 5568;
	 
		case 50:
		    return 91;
	 
		case 103:
		case 104:
		case 655:
		case 749:
		case 491:
		    return 5540;
	 
		case 708:
		    return 169;
	 
		case 397:
		    return 59;
	 
		case 172:
		    return 1162;
	 
		case 86:
		case 87:
		    return 4935;
	 
		case 47:
		    return 2705;
	 
		case 122:
		    return 164;
	 
		
	 
		case 141:
		    return 6579;
	 
		case 1593:
		    return 6562;
	 
		case 1954:
		case 152:
		case 45:
		case 1558:
		    return 75;
	 
		case 89:
		    return 6376;
	 
		case 133:
		    return 289;
	 
		case 1585:
		case 110:
		case 1582:
		case 1583:
		case 1584:
		case 1586:
		case 4291:
		case 4292:
		    return 4666;
	 
		case 111:
		case 4687:
		    return 4672;
	 
		case 4690:
		case 4691:
		case 4692:
		case 4693:
		case 117:
		case 116:
		case 112:
		case 1587:
		case 1588:
		    return 4652;
	 
		case 128:
		case 1479:
		    return 275;
	 
		case 2591:
		case 2620:
		    return 2609;
	 
		case 2610:
		case 2619:
		    return 2610;
	 
		case 2033:
		case 748:
		    return 138;
	 
		case 2031:
		    return 2070;
	 
		case 101:
		    return 309;
	 
		case 90:
		case 91:
		case 5359:
		case 5384:
		case 92:
		case 93:
		    return 5485;
	 
		case 1766:
		case 1767:
		case 81:
		    return 5849;
	 
		case 3065:
		    return 2721;
	 
		case 21:
		    return 451;
	 
		case 1017:
		case 2693:
		case 41:
		    return 5387;
	 
		case 82:
		case 83:
		case 752:
		case 3064:
		case 4694:
		case 4695:
		case 4696:
		case 4697:
		case 84:
		case 4702:
		case 4703:
		case 4704:
		case 4705:
		    return 64;
	 
		case 123:
		    return 163;
	 
		case 9:
		case 32:
		case 20:
		    return 451;
	 
		case 2456:
		    return 1343;
	 
		case 2455:
		case 2454:
		case 1338:
		case 1340:
		case 1342:
		    return 1341;
	 
		case 941:
		case 55:
		case 54:
		case 742:
		case 1589:
		case 52:
		case 53:
		case 4669:
		case 4670:
		case 4671:
		    return 91;
	 
		case 1092:
		case 19:
		    return 406;
	 
		case 125:
		    return 451;
	 
		case 78:
		case 412:
		    return 30;
	 
		case 105:
		case 1195:
		case 1196:
		    return 4922;
	 
		case 2889:
		    return 2859;
	 
		case 132:
		    return 220;
	 
		case 108:
		case 1477:
		    return 6254;
	 
		case 107:
		case 144:
		    return 6254;
	 
		case 2028:
		    return 2075;
	 
		case 2025:
		    return 729;
	 
		case 2026:
		    return 2067;
	 
		case 2027:
		    return 2080;
	 
		case 2029:
		    return 0x814;
	 
		case 2030:
		    return 2062;
			case 6212://Werewolf
			case 6213://Werewolf
			return 6536;
			
			case 6271://Ork
			case 6272://Ork
			case 6273://Ork
			case 6274://Ork
			return 4320;
			
			case 6285://Warped Terrorbird
			case 6293://Warped Terrorbird
			return 7108;
			
			case 6296://Warped Tortoise
			case 6297://Warped Tortoise
			return 7093;
		
			case 6763://Dried Zombie
			return 5578;

			case 5248://Queen Spawn
			return 5092;

			case 5452://Icelord 
			case 5453://Icelord
			case 5454://Icelord
			case 5455://Icelord
			return 5725;

			case 5627://Sorebones
			case 5628://Sorebones
			return 5647;

			case 5691://Undead Lumberjack
			case 5699://Undead Lumberjack
			case 5707://Undead Lumberjack 
			case 5715://Undead Lumberjack 
			case 5723://Undead Lumberjack 
			case 5731://Undead Lumberjack 
			case 5739://Undead Lumberjack 
			case 5747://Undead Lumberjack
			return 5970;		
					
			case 5750://Cave Bug
			return 6079;

			case 5906://A doubt
			return 6310;		

			
			
			case 3313://Tanglefoot
			return 3262;
			
			case 4397://Catablepon
			case 4398://Catablepon
			case 4399://Catablepon
			return 4273;
			
			case 4418://Gorak
			case 6218://Gorak
			return 4300;
			
			case 4463://Vampire Juvenate
			case 4464://Vampire Juvenate
			case 4465://Vampire Juvenate
			return 7183;
			
			case 4527://Suqah
			return 4387;
			
			case 4893://Giant Lobster
			return 6261;
			
			case 4971://Baby Roc
			return 5031;
			
			case 4972://Giant Roc
			return 5024;
			
			case 5176://Ogre Shaman
			case 5181://Ogre Shaman
			case 5184://Ogre Shaman 
			case 5187://Ogre Shaman 
			case 5190://Ogre Shaman 
			case 5193://Ogre Shaman 
			return 359;
			
			
			
			
			case 1831://Cave Slime
			return 1793;

		
			case 2497://Tribesman
			return 729;

			case 10100://Bulwark Beast
			return 13001;

		
			case 2361://Elf Warrior
			case 2362://Elf Warrior
			case 1183://Elf Warrior
			return 426;

			
			case 3495://Kalphite Queen 2
				if (npcs[i].attackType == 0)
			return 2075;
				else
			return 1979;	
		
			case 3200://Chaos Elemental
			return 3146;
			
			

			case 6222://Kree'arra
			return 3505;
			
			case 6225://Flockleader Geerin
			return 6953;
			
			case 6223://Wingman Skree
			return 6952;
			
			case 6227://Flight Kilisa
			return 6954;
	
			case 6247://Commander Zilyana
			return 6964;
			
			case 6248://Starlight
			return 6376;
			
			case 6250://Growler
			return 7018;
			
			case 6252://Bree
			return 7009;
			
			case 8281://Ballance Elemental
  			return 10680;
    
			case 8282://Ballance Elemental
			return 10669;
    
			case 8283://Ballance Elemental
			return 10681;
    
                        case 8597://Avatar Of Creation                    
            		case 9437://Decaying Avatar
    			return 11202;
			
			case 8596://Avatar Of Destruction
			return 11197;	
			
			case 3497://Gelatinnoth Mother
			case 3498://Gelatinnoth Mother
			return 1341;
   

			
			 case 7133://Bork
			 return 8754;
	
			
			 case 5666://Barrelchest
				 if (npcs[i].attackType == 0)
			 return 5894;
				 else
			 return 5895;
					
			 case 3340://Giant Mole
				 if(npcs[i].attackType == 7)
			 return 3311;
				 else if (npcs[i].attackType == 0) //melee
			 return 3312;
			
			case 2746://Yt-Hurkot
			return 2637;
			
			case 2607://Tzhaar-Xil
			return 2611;
			
			case 2743://Ket-Zek
			return 9264;
		
			case 7368://Void Shifter
			case 7369://Void Shifter
			return 8130;

			case 7371://Void Ravager
			return 8093;
			
			case 7352://Void Torcher
			return 8234;
			
		
			case 5228://Penance Runner
			return 5228;
		
			//Training & Misc
			case 1640://Jelly
            		return 8575;
					
           		
					  
           		 case 1250://Fiyr Shade
           		 return 1284;

		


			case 6753://Mummy
			return 5554;

			case 5250://Scarab Mage
			return 7621;
					
			case 7808://Mummy Warrior
			return 5554;

			case 7135://Ork Legion
			return 8760;
					
			
			case 2894://Spinolyp
			return 2868;
			
			case 2037://Skeleton
			return 5485;
				
			
			case 6270://Cyclops
			case 6269://Ice cyclops
			
			return 4652;
					
			case 6219:// Spiritual Warrior
			case 6255:// Spiritual Warrior
			return 451;

			case 13://Wizard
			return 711;
			
		
			case 1616://Basilisk
			return 1546;
			
		
			case 5363://Mithril Dragon
			return 80;
			
			case 124://Earth Warrior
			return 390;
			
			case 803://Monk
			return 422;

			case 51://Baby Dragon
			
			
			case 106://Bear
			return 41;
			
			
			case 102://Goblin
			case 100://Goblin
			case 1770:
			case 1771:
			case 1772:
			case 1773:
			case 2678:
			case 2679:
			case 1774:
			case 1775:
			case 1776:
				return 6188;
			
			case 2452://Giant Rock Crab
			return 1312;
			
			
			case 1472://Jungle Demon
			return 64;
			
			case 1267://Rock Crab
			return 1312;
			
			
		case 5234:
		case 5235:
		case 5236:
		case 5237: // Penance ranger
			return 5396;
		
		case 910:
		case 907:
			return 729;
		
		case 3835:
			return 6240;
		case 3836:
			return 6234;

		

		case 3496:
			if (npcs[i].attackType == 0)
				return 3508;
			else
				return 3507;
		
		case 3494:
			return 1750;
		case 3493:
			if (npcs[i].attackType == 0)
				return 3501;
			else
				return 3502; // end

		case 3581:
			return Misc.random(1) == 0 ? 5485 : 5487;
		
		case 3291:
			return 5485;
		case 1575:
			return 6581;
		case 1471:
			return 5519;
		case 94:
			return 5485;

		case 709:
		
		
		case 2550:
			if (npcs[i].attackType == 0)
				return 7060;
			else
				return 7063;
		
		case 6203:
			return 6945;
		case 6204:
		case 6206:
		case 6208:
			return 64;
		
		

			// bandos gwd
		
		case 5365: // skele
			return 260;

		
		case 178:
			return 451;

		

		case 2745:
			if (npcs[i].attackType == 2)
				return 2656;
			else if (npcs[i].attackType == 1)
				return 2652;
			else if (npcs[i].attackType == 0)
				return 2655;

		
		case 6254:
		case 6256:
		case 6257:
		case 6258:
		case 6214:
			return 806;
		case 6216:
			return 1582;
		
		case 6221:
			return 811;

		case 6229:
		case 6230:
		case 6231:
		case 6232:
		case 6233:
		case 6234:
		case 6235:
		case 6236:
		case 6237:
		case 6238:
		case 6239:
		case 6240:
		case 6241:
		case 6242:
		case 6243:
		case 6244:
		case 6245:
		case 6246:
			return 6953;
		case 6210:
			return 6581;
		case 6211:
			return 169;
		case 6268:
			return 2935;
		
		
		case 6283:
			return 6184;
		case 6276:
		case 6277:
			return 4320;
		case 6275:
			return 164;
		default:
			return 0x326;
		}
	}

	public int getDeadEmote(int i) {
		for (int npc = 2015; npc < 2021; npc++) {
			if(NPCHandler.npcs[i].npcType == npc) {
			return 	5555;
			}
			}
		for (int npc = 1958; npc < 1969; npc++) {
			if(NPCHandler.npcs[i].npcType == npc) {
			return 	5551;
			}
			}
		switch (npcs[i].npcType) {
		// pest control portals
		case 6142:
		case 6143:
		case 6144:
		case 6145:
			return -1;
		case 3068:// wyv
		return 2987;// wyv death
		case 2044:
		case 2043:
		case 2042:
			return 5072;
		case 3499:
			////bosslog(3499, i);
			return 4929;
		case 3501: // Scorpia
		//bosslog(3501, i);
            return 6256;		
		case 3500: // Venenatis
		//bosslog(3500, i);
		case 3502: // Vet'ion
		//bosslog(3502, i);
			return 5491;
		//case 4056:
			//return 5808;
		case 3847:
			//bosslog(3847, i);
			return 3993;
		case 6218:// Corporeal Beast
			return 10059;
		case 1940:
			return 286;
		case 1600:
		case 1601:
		case 1602:
		case 1603:
			return 228;
		case 1560:
		case 1561:
		case 1562:
		case 1563:
		case 1564:
		case 1565:
		case 1566:
		    return 287;
	 
		case 6261:
		case 6263:
		case 6265:
		    return 6156;
	 
		case 6279:
		case 6280:
		case 6281:
		case 6282:
		    return 6182;
	 
		case 5902:
		    return 6322;
	 
		case 1973:
		    return 5503;
	 
		case 1976:
		    return 6564;
	 
		case 5529:
		    return 5784;
	 
		case 6116:
		    return 6812;
	 
		case 5247:
		    return 5412;
	 
		case 5233:
		case 5232:
		case 5231:
		case 5230:
		    return 5397;
	 
		case 1019:
		    return 1031;
	 
		case 1020:
		    return 1037;
	 
		case 1021:
		    return 1041;
	 
		case 1022:
		    return 1048;
	 
		case 5219:
		case 5218:
		case 5217:
		case 5216:
		case 5215:
		case 5214:
		case 5213:
		    return 5098;
		case 4353:
		case 4354:
		case 4355:
		    return 4233;
	 
		case 113:
		    return 131;
	 
		case 114:
		case 115:
		    return 361;
	 
		case 3058:
		    return 2938;
	 
		case 3057:
		    return 2945;
	 
		case 3063:
		    return 2938;
	 
		case 131:
		    return 5671;
	 
		case 1608:
		    return 1513;
	 
		case 1676:
		    return 1628;
	 
		case 1677:
		    return 1618;
	 
		case 1678:
		    return 1614;
	 
		case 4404:
		case 4405:
		case 4406:
		    return 4265;
	 
		case 914:
		    return 196;
	 
		case 3065:
		    return 2728;
	 
		case 1620:
		case 1621:
		    return 1563;
	 
		case 3066:
		    return 2925;
	 
		case 1265:
		    return 1314;
	 
		case 118:
		    return 102;
	 
		case 2263:
		    return 2183;
	 
		case 2598:
		case 2599:
		case 2600:
		case 2601:
		case 2602:
		case 2603:
		case 2606:
		case 2591:
		case 2604:
		    return 2607;
	 
		case 3347:
		case 3346:
		    return 3327;
	 
		case 1192:
		    return 1246;
	 
		case 1624:
		case 1625:
		    return 1558;
	 
		case 2892:
		    return 2865;
	 
		case 127:
		    return 188;
	 
		case 119:
		    return 102;
	 
		case 2881:
		case 2882:
		case 2883:
		    return 2856;
	 
		case 1590:
		case 1591:
		case 1592:
		    return 92;
	 
		case 2783:
		    return 2733;
	 
		case 3452:
		    return 5412;
	 
		case 2889:
		    return 2862;
	 
		case 1354:
		case 1341:
		    return 1342;
	 
		case 3200:
		    return 3147;
	 
		case 2457:
		    return 2367;
	 
		case 66:
		case 67:
		case 168:
		case 169:
		case 162:
		case 68:
		    return 196;
	 
		case 160:
		case 161:
		    return 196;
	 
		case 163:
		case 164:
		    return 196;
	 
		case 1153:
		case 1154:
		case 1155:
		case 1156:
		case 1157:
		case 1158:
		    return 6230;
	 
		case 1160:
		    return 6234;
	 
		case 438:
		case 439:
		case 440:
		case 441:
		case 442:
		case 443:
		    return 97;
	 
		case 391:
		case 392:
		case 393:
		case 394:
		case 395:
		case 396:
		    return 287;
	 
		case 413:
		case 414:
		case 415:
		case 416:
		case 417:
		case 418:
		    return 156;
	 
		case 2745:
		    return 2654;
	 
		case 2743:
		    return 2646;
	 
		case 2734:
		case 2627:
		    return 2620;
	 
		case 2630:
		case 2629:
		case 2736:
		case 2738:
		    return 2627;
	 
		case 2631:
		case 2632:
		    return 2630;
	 
		case 2741:
		    return 2638;
	 
		case 908:
		    return 131;
	 
		case 909:
		    return 146;
	 
		case 911:
		    return 67;
	 
		case 60:
		case 59:
		case 61:
		case 63:
		case 64:
		case 134:
		case 2035:
		case 62:
		case 1009:
		    return 5329;
	 
		case 6006:
		    return 6537;
	 
		case 1612:
		    return 1524;
	 
		case 1648:
		case 1649:
		case 1650:
		case 1651:
		case 1652:
		case 1653:
		case 1654:
		case 1655:
		case 1656:
		case 1657:
		    return 1590;
	 
		case 1604:
		case 1605:
		case 1606:
		case 1607:
		    return 1508;
	 
		case 1618:
		case 1619:
		    return 1553;
	 
		case 1643:
		case 1644:
		case 1645:
		case 1646:
		case 1647:
		    return 2304;
	 
		case 1613:
		    return 1530;
	 
		case 1610:
		case 1611:
		    return 1518;
	 
		case 1615:
		    return 1538;
	 
		case 3406:
		case 1633:
		    return 1580;
	 
		case 1456:
		    return 1397;
	 
		case 122:
		    return 167;
	 
		case 1125:
		    return 287;
	 
		case 1096:
		case 1097:
		case 1098:
		case 1942:
		case 1108:
		case 1109:
		case 1110:
		case 1111:
		case 1112:
		case 1116:
		case 1117:
		case 1101:
		case 1106:
		case 1095:
		    return 287;
	 
		case 103:
		case 104:
		case 749:
		case 655:
		case 491:
		    return 5542;
	 
		case 49:
		case 142:
		case 95:
		case 1951:
		    return 6576;
	 
		case 74:
		case 75:
		case 76:
		    return 5569;
	 
		case 73:
		case 751:
		case 77:
		    return 5569;
	 
		
		
		case 50:
		    return 92;
	 
		case 708:
		case 3062:
		    return 172;
	 
		case 81:
		case 397:
		    return 5851;
	 
		case 86:
		case 87:
		    return 4933;
	 
		case 47:
		    return 2707;
	 
		case 2620:
		case 2610:
		case 2619:
		    return 2607;
	 
		case 89:
		    return 6377;
	 
		case 133:
		    return 292;
	 
		case 2033:
		case 748:
		    return 141;
	 
		case 2031:
		    return 2073;
	 
		case 101:
		    return 313;
	 
		case 105:
		case 1195:
		case 1196:
		    return 4930;
	 
		case 128:
		case 1479:
		    return 278;
	 
		case 132:
		    return 223;
	 
		case 108:
		case 1477:
		    return 6256;
	 
		case 144:
		case 107:
		    return 6256;
	 
		case 90:
		case 91:
		case 5359:
		case 5384:
		case 92:
		case 93:
		    return 5491;
	 
		case 82:
		case 3064:
		case 4694:
		case 4695:
		case 4696:
		case 4697:
		case 83:
		case 752:
		case 84:
		case 4702:
		case 4703:
		case 4704:
		case 4705:
		    return 67;
	 
		case 941:
		case 55:
		case 53:
		case 4669:
		case 4670:
		case 4671:
		case 742:
		case 1589:
		case 54:
		case 52:
		    return 92;
	 
		case 6260:
		    return 7062;
	 
		case 123:
		case 3061:
		    return 167;
	 
		case 141:
		    return 6570;
	 
		case 1593:
		    return 6564;
	 
		case 152:
		case 45:
		case 1558:
		case 1954:
		    return 78;
	 
		case 1459:
		    return 1404;
	 
		case 78:
		case 412:
		    return 36;
	 
		case 1766:
		case 1767:
		    return 0x03E;
	 
		case 1017:
		case 2693:
		case 41:
		    return 5389;
	 
		case 1585:
		case 110:
		case 1582:
		case 1583:
		case 1584:
		case 1586:
		case 4291:
		case 4292:
		    return 4673;
	 
		case 111:
		case 4687:
		    return 4673;
	 
		case 4690:
		case 4691:
		case 4692:
		case 4693:
		case 117:
		case 116:
		case 112:
		case 1587:
		case 1588:
		    return 4653;
	 
		case 2455:
		case 2454:
		case 2456:
		case 1338:
		case 1340:
		case 1342:
		    return 1342;
	 
		case 125:
		    return 843;
		case 5993://Experiment No.2
			return 6512;
			
			case 6212://Werewolf
			case 6213://Werewolf
			return 6537;
			
			case 6271://Ork
			case 6272://Ork
			case 6273://Ork
			case 6274://Ork
			return 4321;
			
			case 6285://Warped Terrorbird
			case 6293://Warped Terrorbird
			return 7109;
			
			case 6296://Warped Tortoise
			case 6297://Warped Tortoise
			return 7091;
	
			
			
			case 6763://Dried Zombie
			return 5569;

			case 5248://Queen Spawn
			return 5093;

			case 5452://Icelord 
			case 5453://Icelord
			case 5454://Icelord
			case 5455://Icelord
			return 5726;

			case 5627://Sorebones
			case 5628://Sorebones
			return 5649;

			case 5691://Undead Lumberjack
			case 5699://Undead Lumberjack
			case 5707://Undead Lumberjack 
			case 5715://Undead Lumberjack 
			case 5723://Undead Lumberjack 
			case 5731://Undead Lumberjack 
			case 5739://Undead Lumberjack 
			case 5747://Undead Lumberjack
			return 5972;		
					
			case 5750://Cave Bug
			return 6081;

			case 5906://A doubt
			return 6315;

			
			
			case 3313://Tanglefoot
			return 3263;
			
			case 4397://Catablepon
			case 4398://Catablepon
			case 4399://Catablepon
			return 4270;
			
			case 4418://Gorak
			return 4302;

			case 4527://Suqah
			return 4389;
			
			case 4893://Giant Lobster
			return 6267;
			
			case 4971://Baby Roc
			return 5033;
			
			case 4972://Giant Roc
			return 5037;
			
			case 5176://Ogre Shaman
			case 5181://Ogre Shaman
			case 5184://Ogre Shaman 
			case 5187://Ogre Shaman 
			case 5190://Ogre Shaman 
			case 5193://Ogre Shaman 
			return 361;
			
			

			case 1831://Cave Slime
			return 1792;

			case 907://Kolodion
			case 910://Kolodion
			case 2497://Tribesman
			return 714;

			

			case 10100://Bulwark Beast
			return 13005;

		

			


			
			
			
			
			case 1250://Fiyr Shade
			return 1285;
							
			case 9172://Aquanite
			return 12039;
			
			
			case 3497://Gelatinoth Mother
			case 3498://Gelatinoth Mother
			return 1342;
					   
			case 8777://Handcannonneer
			return 12181;
			
			case 5250://Scarab Mage
			return 7616;
			
			case 7808://Mummy Warrior
			return 5555;
			
			case 6753://Mummy
			return 5555;
			
			case 7797://Kurask Overlord
			return 9440;
			
			case 8324://Elite Black Knight
			return 836;

			case 10815://New Red Dragon
			case 10607://New Green Dragon
			case 10224://New Black Dragon
			return 13153;
			case 7133://Bork
			return 8756;
			
			case 7135://Ork Legion
			return 8761;
			
			case 3340://Giant Mole
			return 3310;
			
			case 8321://Elite Dark Mage
			return 2304;
			
			case 5666://Barrelchest
			return 5898;

			case 6247://Commander Zilyana
			return 6965;
			
			case 6248://Starlight
			return 6377;

			case 6250://Growler
			return 7016;
			
			case 6252://Bree
			return 7011;
			
			case 8133://Corpreal Beast
			return 10050;
			
			case 8349://Tormented Demon
			return 10924;

			case 2894://Spinolyp
			return 2865;
			
		
			case 6222://Kree'ara
			return 3503;
			
			case 6223://Wingman Skree
			case 6225://Flockleader Geerin
			case 6227://Flight Kilisa
			return 6956;
			
			case 2607://Tzhaar-Xil
			return 2607;
			
			
			
			case 2746://Yt-Hurkot
			return 2638;
			
			
			
			case 102://Goblin
			case 100://Goblin
			case 1770:
			case 1771:
			case 1772:
			case 1773:
			case 2678:
			case 2679:
			case 1774:
			case 1775:
			case 1776:
			return 6182;
			
			
			
			case 1626://Turoth
			case 1627://Turoth
			case 1628://Turoth
			case 1629://Turoth
			case 1630://Turoth
			case 1631://Turoth
			case 1632://Turoth
			return 1597;
						
			case 1616://Basilisk
			return 1548;
						
			
			case 1472://Jungle Demon
			return 67;
						
		
						
			case 51://Baby Dragon
			
			case 3376://Baby Black Dragon
			return 28;
			
			
			
			case 1634://Pyrefiend
			case 1635://Pyrefiend 
			case 1636://Pyrefiend
			return 1580;	
			case 106://Black Bear
			return 44;		
			case 58://Shadow Spider
			return 146;		
		case 2440:
		case 2443:
		case 2446:
			return -1;
		case 5234:
		case 5235:
		case 5236:
		case 5237: // Penance ranger
			return 5397;
		case 3835:
			return 6242;
		case 3836:
			return 6233;
		case 709:
		case 3496:
			return 3509;
		case 3493:
			return 3503;
		case 3494:
			return 1752;		
		case 6203:
			return 6946;
		case 6204:
		case 6206:
		case 6208:
			return 67;	
		case 6275:
			return 167;
		case 6276:
		case 6277:
			return 4321;		
		case 6211:
			return 172;		
		case 6219:
		case 6221:
		case 6254:
		case 6255:
		case 6256:
		case 6257:
		case 6258:
		case 6214:
			return 0x900;
		case 6216:
			return 1580;
		
		case 6268:
			return 2938;
		case 6269:
		case 6270:
			return 4653;
		case 6229:
		case 6230:
		case 6231:
		case 6232:
		case 6233:
		case 6234:
		case 6235:
		case 6236:
		case 6237:
		case 6238:
		case 6239:
		case 6240:
		case 6241:
		case 6242:
		case 6243:
		case 6244:
		case 6245:
		case 6246:
			return 6956;
		case 6210:
			return 6576;
		
			// bandos gwd
		case 2551:
		case 2552:
		case 2553:
			return 6156;
		case 2550:
			return 7062;
		case 3777:
		case 3778:
		case 3779:
		case 3780:
			return -1;		
		case 3581:
			return 5491;
	
		case 3291:
			return 5491;
		case 94:
			return 5491;
		case 1471:
			return 5521;
		case 1575:
			return 6576;
		default:
			return 2304;
		}
	}

	public static int getBlockEmote(int i) {
		if (npcs[i].npcType == 3776)
			return 3895;
		if (npcs[i].npcType == 3761)
			return 3881;
		if (npcs[i].npcType == 3760)
			return 3881;
		if (npcs[i].npcType == 3771)
			return 3921;
		if (npcs[i].npcType == 3751 || npcs[i].npcType == 3750
				|| npcs[i].npcType == 3749 || npcs[i].npcType == 3748
				|| npcs[i].npcType == 3747)
			return 3909;
		if (npcs[i].npcType == 3741)
			return 3902;

		switch (npcs[i].npcType) {
		case 78:
		case 412: // bat
			return 4916;
		case 6250: // sara lion
			return 7017;
		case 6248: // sara horse
			return 6375;
		case 6247: // sara boss
			return 6966;
		case 2060:// slash bash
			return 360;
		case 5902:// inadequacy
			return 6319;
		case 5421:// mutant tarn
			return 5618;
		case 5666:// barrelchest
			return 5897;
		case 5665:
		case 5664:// zombiepirate
			return 5890;
		case 3847: // stq
			return 3992;
		case 103:
		case 104:
		case 655:
		case 749:
		case 491: // ghost
			return 5541;
		case 3200: // chaos ele
			return 3148;
		case 115: // ogre
			return 360;
		case 3340: // giant mole
			return 3311;
		case 4972: // giant roc
			return 5026;
		case 4971: // baby roc
			return 5032;
		case 1608: // kurask
			return 1514;
		case 1616: // basilisk
			return 1547;
		case 1632: // turoth
			return 1596;

		case 1622: // rockslug
			return 1567;
		case 1600: // cave crawler
			return -1;

		case 6279:
		case 6280:
		case 6281:
		case 6282:
			return 6183;
		case 5529:
			return 5783;
			// bandos
		case 6260:
			return 7061;
		case 6261:
		case 6263:
		case 6265:
			return 6155;
		case 5247:
			return 5413;
		case 6116: // Seagull
			return 6810;
		case 5233:
		case 5232:
		case 5231:
		case 5230:
			return 5396;
		case 5219:
		case 5218:
		case 5217:
		case 5216:
		case 5215:
		case 5214:
		case 5213:
			return 5096; // Penance
		case 113:
			return 129;
		case 114:
			return 360;
		case 3058:
			return 2937;
		case 3061:
			return 2961;
		case 3063:
			return 2937;
		case 131:
			return 5670;
		case 1676:
			return 1627;
		case 1677:
			return 1617;
		case 1678:
			return 1613;
		case 1019:
			return 1030;
		case 1020:
			return 1036;
		case 1021:
			return 1042;
		case 1022:
			return 1046;
		case 914:
			return 194;
		case 4353:
		case 4354:
		case 4355:
			return 4232;
		case 4404:
		case 4405:
		case 4406:
			return 4267;
		case 3065:
			return 2720;
		case 1620:
		case 1621:
			return 1560;
		case 3066:
			return 2926;
		case 1265:
			return 1313;
		case 118:
			return 100;
		case 2263:
			return 2181;
		case 82:
		case 84:
		case 4702:
		case 4703:
		case 4704:
		case 4705:
		case 752:
		case 4694:
		case 4695:
		case 4696:
		case 4697:
		case 3064:
		case 83: // lesser
			return 65;
		case 3347:
		case 3346:
			return 3325;
		case 1192:
			return 1244;
		case 3062:
			return 2953;
		case 3060:
			return 2964;
		case 2892: // Spinolyp
		case 2894: // Spinolyp
		case 2896: // Spinolyp
			return 2869;
		case 1624:
		case 1625:
			return 1555;
		case 1354:
		case 1341:
		case 2455:// dagannoth
		case 2454:
		case 2456:
			return 1340;
		case 127:
			return 186;
		case 119:
			return 100;
		case 2881: // supreme
		case 2882: // prime
		case 2883: // rex
			return 2852;
		case 3452:// penance queen
			return 5413;
		case 2745:
			return 2653;
		case 2743:
			return 2645;
		case 1590:// metal dragon
		case 1591:
		case 1592:
		case 5363:
			return 89;
		case 2598:
		case 2599:
		case 2600:
		case 2610:
		case 2601:
		case 2602:
		case 2603:
		case 2606:// tzhaar-xil
		case 2591:
		case 2604:// tzhar-hur
			return 2606;
		case 66:
		case 67:
		case 168:
		case 169:
		case 162:
		case 68:// gnomes
			return 193;
		case 160:
		case 161:
			return 194;
		case 163:
		case 164:
			return 193;
		case 438:
		case 439:
		case 440:
		case 441:
		case 442: // Tree spirit
		case 443:
			return 95;
		case 391:
		case 392:
		case 393:
		case 394:
		case 395:// river troll
		case 396:
		case 1560:
		case 1566:
			return 285;
		case 413:
		case 414:
		case 415:
		case 416:
		case 417:// rock golem
		case 418:
			return 154;

		case 1153:
		case 1154:
		case 1155:
		case 1156:
		case 1157:
		case 1158: // kalphite
			return 6232;
		case 1160: // kalphite
			return 6237;
		case 2783:// dark beast
			return 2732;
		case 2734:
		case 2627:// tzhaar
			return 2622;
		case 2630:
		case 2629:
		case 2736:
		case 2738:
			return 2626;
		case 2631:
		case 2632:
			return 2629;
		case 2741:
			return 2635;

		case 908:
			return 129;
		case 909:
			return 147;
		case 911:
			return 65;

		case 1459:// monkey guard
			return 1403;

		case 1633: // pyrefiend
		case 3406:
			return 1581;

		case 1612:// banshee
			return 1525;

		case 1648:
		case 1649:
		case 1650:
		case 1651:
		case 1652:
		case 1653:
		case 1654:
		case 1655:
		case 1656:
		case 1657:// crawling hand
			return 1591;

		case 1604:
		case 1605:
		case 1606:
		case 1607:// aberrant specter
			return 1509;

		case 1618:
		case 1619:// bloodveld
			return 1550;

		case 1643:
		case 1644:
		case 1645:
		case 1646:
		case 1647:// infernal mage
			return 430;

		case 1613:// nechryael
			return 1529;

		case 1610:
		case 1611:// gargoyle
			return 1519;

		case 1615:// abyssal demon
			return 1537;

		case 1770:
		case 1771:
		case 1772:
		case 1773:
		case 101:
		case 2678:
		case 2679:
		case 1774:
		case 1775:
		case 1769:
		case 1776:// goblins
			return 6183;

		case 132: // monkey
			return 221;

		case 6006: // wolfman
			return 6538;

		case 1456:// monkey archer
			return 1395;

		case 108:// scorpion
		case 1477:
			return 6255;
		case 107:
		case 144:
			return 6255;

		case 1125:// dad
			return 285;

		case 1096:
		case 1097:
		case 1098:
		case 1942:
		case 1108:
		case 1109:
		case 1110:
		case 1111:
		case 1112:
		case 1116:
		case 1117:
		case 1101:// troll
		case 1106:
			return 285;
		case 1095:
			return 285;

		case 123:
		case 122:// hobgoblin
			return 165;

		case 49:// hellhound
		case 142:
		case 95:
		case 96:
		case 125:
			return 6578;
		case 141:
			return 6578;
		case 1593:
			return 6563;
		case 152:
		case 45:
		case 1558: // wolf
		case 1954:
			return 76;

		case 89:
			return 6375;
		case 133: // unicorns
			return 290;

		case 105:
		case 1195:
		case 1196:// bear
			return 4921;

		case 74:
		case 75:
		case 76:
			return 5574;

		case 73:
		case 751: // zombie
		case 77:
		case 419:
		case 420:
		case 421:
		case 422:
		case 423:
		case 424:
			return 5574;

		case 60:
		case 64:
		case 59:
		case 61:
		case 63:
		case 134:
		case 2035: // spider
		case 62:
		case 1009:
			return 5328;

		case 1585:
		case 110:
		case 1582:
		case 1583:
		case 1584:
		case 1586: // giant
		case 4291:
		case 4292:
			return 4671;
		case 111:
		case 4687:
			return 4671;
		case 4690:
		case 4691:
		case 4692:
		case 4693:
		case 117:
		case 116:
		case 112:
		case 1587:
		case 1588:
			return 4651;

		case 50: // kbd
			return 89;

		case 941:// green dragon
		case 55:
		case 742:
		case 1589:
		case 53:
		case 4669:
		case 4670:
		case 4671:
		case 52:
		case 54:
			return 89;
		case 2889:
			return 2860;
		case 81: // cow
		case 397:
			return 5850;

		case 708: // imp
			return 170;

		case 86:
		case 87:
			return 139;
		case 47:// rat
			return 2706;
		case 2457:
			return 2366;
		case 128: // snake
		case 1479:
			return 276;

		case 1017:
		case 2693:
		case 41: // chicken
			return 5388;

		case 90:
		case 91:
		case 5359:
		case 5384:
		case 92:
		case 93: // skeleton
			return 5489;

		/*case 3247: // Hobgoblin
		case 6270: // Cyclops
		case 6269: // Ice cyclops
		case 6219: // Spiritual Warrior
		case 6255: // Spiritual Warrior
		case 6229: // Spirtual Warrior arma
		case 6218: // Gorak
		case 6212: // Werewolf
		case 6220: // Spirtual Ranger
		case 6256: // Spirtual Ranger
		case 6257: // Spirtual Mage
		case 6221: // Spirtual Mage
		case 6276: // Spirtual Ranger
		case 6278: // Spirtual Mage
		case 6272: // Ork
		case 6274: // Ork
		case 6277: // Spirtual Warrior bandos
		case 6230: // Spirtual Ranger
		case 6233: // Aviansie
		case 6239: // Aviansie
		case 6232: // Aviansie
		case 6254: // Saradomin Priest
		case 6258: // Saradomin Knight
		case 6231: // Spirtual Mage
			return -1;*/

		default:
			return -1;
		}
	}
	
	/**
	 * Attack delays
	 **/
	public int getNpcDelay(int i) {
		switch (npcs[i].npcType) {
		case 2025:
		case 2028:
			return 7;

		case 2745:
			return 8;

		case 2558:
		case 2559:
		case 2560:
		case 2561:
		case 6260:
			return 6;
			// saradomin gw boss
		case 2562:
			return 2;

		case 1158:
		case 1160:
			return 6;

		case 53:
		case 54:
		case 55:
		case 941:
		case 1590:
		case 1591:
		case 1592:
			return 7;


		case 6222:
		case 6225:
		case 6223:
		case 6227:
		case 2550:
			return 6;

		case 6203:
			return 5;
		case 6204:
		case 6206:
		case 6208:
			return 6;
			// saradomin gw boss
		case 6247:
			return 2;
		default:
			return 5;
		}
	}

	/**
	 * Hit delays
	 **/
	public int getHitDelay(int i) {
		switch (npcs[i].npcType) {
		case 2881:
		case 2882:
		case 3200:
		case 2892:
		case 2894:
			return 3;

		case 2743:
		case 2631:
		case 2558:
		case 2559:
		case 2560:
			return 3;

		case 2745:
			if (npcs[i].attackType == 1 || npcs[i].attackType == 2)
				return 5;
			else
				return 2;

		case 2025:
			return 4;
		case 2028:
			return 3;

		default:
			return 2;
		}
	}

	/**
	 * Npc respawn time
	 **/
	public int getRespawnTime(int i) {
		if (isZombie(i)) 
			return -1;
		switch (npcs[i].npcType) {
		case 2881:
		case 2882:
		case 2883:
		case 2558:
		case 2559:
		case 2560:
		case 2561:
		case 2562:
		case 2563:
		case 2564:
		case 6260:
		case 6222:
		case 6203:
		case 6247:
		case 3068:
			return 60;
		case 3847:
		case 3943:
			return 60;
		case 3499:
		case 3500:
		case 3501:
		case 3502:
			return 60;
		case 6142:
		case 6143:
		case 6144:
		case 6145:
			return -1;
		default:
			return 25;
		}
	}

	public void newNPC(int npcType, int x, int y, int heightLevel,
			int WalkingType, int HP, int maxHit, int attack, int defence) {
		// first, search for a free slot
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
				break;
			}
		}

		if (slot == -1)
			return; // no free slot found

		NPC newNPC = new NPC(slot, npcType);
		newNPC.absX = x;
		newNPC.absY = y;
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.heightLevel = heightLevel;
		newNPC.walkingType = WalkingType;
		newNPC.HP = HP;
		newNPC.MaxHP = HP;
		newNPC.maxHit = maxHit;
		newNPC.attack = attack;
		newNPC.defence = defence;
		npcs[slot] = newNPC;
	}

	public void newNPCList(int npcType, String npcName, int combat, int HP) {

		NPCDefinitions newNPCList = new NPCDefinitions(npcType);
		newNPCList.setNpcName(npcName);
		newNPCList.setNpcCombat(combat);
		newNPCList.setNpcHealth(HP);
		NPCDefinitions.getDefinitions()[npcType] = newNPCList;
	}

	public void handleClipping(int i) {
		NPC npc = npcs[i];
		if (npc.moveX == 1 && npc.moveY == 1) {
			if ((Region
					.getClipping(npc.absX + 1, npc.absY + 1, npc.heightLevel) & 0x12801e0) != 0) {
				npc.moveX = 0;
				npc.moveY = 0;
				if ((Region
						.getClipping(npc.absX, npc.absY + 1, npc.heightLevel) & 0x1280120) == 0)
					npc.moveY = 1;
				else
					npc.moveX = 1;
			}
		} else if (npc.moveX == -1 && npc.moveY == -1) {
			if ((Region
					.getClipping(npc.absX - 1, npc.absY - 1, npc.heightLevel) & 0x128010e) != 0) {
				npc.moveX = 0;
				npc.moveY = 0;
				if ((Region
						.getClipping(npc.absX, npc.absY - 1, npc.heightLevel) & 0x1280102) == 0)
					npc.moveY = -1;
				else
					npc.moveX = -1;
			}
		} else if (npc.moveX == 1 && npc.moveY == -1) {
			if ((Region
					.getClipping(npc.absX + 1, npc.absY - 1, npc.heightLevel) & 0x1280183) != 0) {
				npc.moveX = 0;
				npc.moveY = 0;
				if ((Region
						.getClipping(npc.absX, npc.absY - 1, npc.heightLevel) & 0x1280102) == 0)
					npc.moveY = -1;
				else
					npc.moveX = 1;
			}
		} else if (npc.moveX == -1 && npc.moveY == 1) {
			if ((Region
					.getClipping(npc.absX - 1, npc.absY + 1, npc.heightLevel) & 0x128013) != 0) {
				npc.moveX = 0;
				npc.moveY = 0;
				if ((Region
						.getClipping(npc.absX, npc.absY + 1, npc.heightLevel) & 0x1280120) == 0)
					npc.moveY = 1;
				else
					npc.moveX = -1;
			}
		} // Checking Diagonal movement.

		if (npc.moveY == -1) {
			if ((Region.getClipping(npc.absX, npc.absY - 1, npc.heightLevel) & 0x1280102) != 0)
				npc.moveY = 0;
		} else if (npc.moveY == 1) {
			if ((Region.getClipping(npc.absX, npc.absY + 1, npc.heightLevel) & 0x1280120) != 0)
				npc.moveY = 0;
		} // Checking Y movement.
		if (npc.moveX == 1) {
			if ((Region.getClipping(npc.absX + 1, npc.absY, npc.heightLevel) & 0x1280180) != 0)
				npc.moveX = 0;
		} else if (npc.moveX == -1) {
			if ((Region.getClipping(npc.absX - 1, npc.absY, npc.heightLevel) & 0x1280108) != 0)
				npc.moveX = 0;
		} // Checking X movement.
	}

	public void process() {
		for (int i = 0; i < maxNPCs; i++) {
			if (npcs[i] == null)
				continue;
			npcs[i].clearUpdateFlags();
		}
		
		for (int i = 0; i < maxNPCs; i++) {
			if (npcs[i] != null) {
				NPC npc = npcs[i];
                        Client petOwner = (Client) PlayerHandler.players[npc.summonedBy];
                        if (petOwner == null && npc.summoned)
                        {
                                Pet.deletePet(npc);
                        }
                        if (petOwner != null && petOwner.isDead)
                        {
                                Pet.deletePet(npc);
                        }
                        if (petOwner != null && petOwner.getPetSummoned() && npc.summoned)
                        {
                                if (petOwner.goodDistance(npc.getX(), npc.getY(), petOwner.absX, petOwner.absY, 15))
                                {
                                        Server.npcHandler.followPlayer(i, petOwner.playerId);
                                }
                                else
                                {
                                        Pet.deletePet(npc);
                                        Pet.summonPet(petOwner, petOwner.petID, petOwner.absX, petOwner.absY - 1, petOwner.heightLevel);
                                }
                        }
				}
		}           
		for (int i = 0; i < maxNPCs; i++) {
			if (npcs[i] != null) {
				if (npcs[i].summoner == true) {
					Client c = (Client) PlayerHandler.players[npcs[i].spawnedBy];
					if (c != null && c.playerIndex < 1) {
						if (!npcs[i].underAttack) {
							if (!PlayerHandler.players[npcs[i].spawnedBy]
									.goodDistance(
											npcs[i].getX(),
											npcs[i].getY(),
											PlayerHandler.players[npcs[i].spawnedBy]
													.getX(),
											PlayerHandler.players[npcs[i].spawnedBy]
													.getY(), 2)
									&& c.npcIndex < 1)
								followPlayer(i, c.playerId);
						}
					} else {
						if (c != null && npcs[i].summoner == true) {
							if (!PlayerHandler.players[npcs[i].spawnedBy]
									.goodDistance(
											npcs[i].getX(),
											npcs[i].getY(),
											PlayerHandler.players[npcs[i].spawnedBy]
													.getX(),
											PlayerHandler.players[npcs[i].spawnedBy]
													.getY(), 5)
									&& c.playerIndex < 1 || c.npcIndex < 1) {
								followPlayer(i, c.playerId);
							}
						}

					}
				}
				if (npcs[i].actionTimer > 0) {
					npcs[i].actionTimer--;
				}

				if (npcs[i].freezeTimer > 0) {
					npcs[i].freezeTimer--;
				}

				if (npcs[i].hitDelayTimer > 0) {
					npcs[i].hitDelayTimer--;
				}

				if (npcs[i].hitDelayTimer == 1) {
					npcs[i].hitDelayTimer = 0;
					applyDamage(i);
				}

				if (npcs[i].attackTimer > 0) {
					npcs[i].attackTimer--;
				}

				if (npcs[i].spawnedBy > 0) { // delete summons npc
					if (PlayerHandler.players[npcs[i].spawnedBy] == null
							|| PlayerHandler.players[npcs[i].spawnedBy].heightLevel != npcs[i].heightLevel
							|| PlayerHandler.players[npcs[i].spawnedBy].respawnTimer > 0
							|| !PlayerHandler.players[npcs[i].spawnedBy]
									.goodDistance(
											npcs[i].getX(),
											npcs[i].getY(),
											PlayerHandler.players[npcs[i].spawnedBy]
													.getX(),
											PlayerHandler.players[npcs[i].spawnedBy]
													.getY(), 20)) {
						if (PlayerHandler.players[npcs[i].spawnedBy] != null) {
							for (int o = 0; o < PlayerHandler.players[npcs[i].spawnedBy].barrowsNpcs.length; o++) {
								if (npcs[i].npcType == PlayerHandler.players[npcs[i].spawnedBy].barrowsNpcs[o][0]) {
									if (PlayerHandler.players[npcs[i].spawnedBy].barrowsNpcs[o][1] == 1)
										PlayerHandler.players[npcs[i].spawnedBy].barrowsNpcs[o][1] = 0;
								}
							}
						}
						npcs[i] = null;
					}
				}
				if (npcs[i] == null)
					continue;
				if (npcs[i].lastX != npcs[i].getX()
						|| npcs[i].lastY != npcs[i].getY()) {
					npcs[i].lastX = npcs[i].getX();
					npcs[i].lastY = npcs[i].getY();
				}

				/**
				 * Attacking player
				 **/
				if (isAggressive(i) && !npcs[i].underAttack && !npcs[i].isDead
						&& !switchesAttackers(i)) {
					npcs[i].killerId = getCloseRandomPlayer(i);
				} else if (isAggressive(i) && !npcs[i].underAttack
						&& !npcs[i].isDead && switchesAttackers(i)) {
					npcs[i].killerId = getCloseRandomPlayer(i);
				}

				if (System.currentTimeMillis() - npcs[i].lastDamageTaken > 5000)
					npcs[i].underAttackBy = 0;

				if ((npcs[i].killerId > 0 || npcs[i].underAttack)
						&& !npcs[i].walkingHome && retaliates(npcs[i].npcType)) {
					if (!npcs[i].isDead) {
						int p = npcs[i].killerId;
						if (PlayerHandler.players[p] != null) {
							Client c = (Client) PlayerHandler.players[p];
							followPlayer(i, c.playerId);
							if (npcs[i] == null)
								continue;
							if (npcs[i].attackTimer == 0) {
								attackPlayer(c, i);
							}
						} else {
							npcs[i].killerId = 0;
							npcs[i].underAttack = false;
							npcs[i].facePlayer(0);
						}
					}
				}

				/**
				 * Random walking and walking home
				 **/
				if (npcs[i] == null)
					continue;
				if ((!npcs[i].underAttack || npcs[i].walkingHome)
						&& npcs[i].randomWalk && !npcs[i].isDead) {
					npcs[i].facePlayer(0);
					npcs[i].killerId = 0;
					if (npcs[i].spawnedBy == 0) {
						if ((npcs[i].absX > npcs[i].makeX
								+ Config.NPC_RANDOM_WALK_DISTANCE)
								|| (npcs[i].absX < npcs[i].makeX
										- Config.NPC_RANDOM_WALK_DISTANCE)
								|| (npcs[i].absY > npcs[i].makeY
										+ Config.NPC_RANDOM_WALK_DISTANCE)
								|| (npcs[i].absY < npcs[i].makeY
										- Config.NPC_RANDOM_WALK_DISTANCE)) {
							npcs[i].walkingHome = true;
						}
					}

					if (npcs[i].walkingHome && npcs[i].absX == npcs[i].makeX
							&& npcs[i].absY == npcs[i].makeY) {
						npcs[i].walkingHome = false;
					} else if (npcs[i].walkingHome) {
						npcs[i].moveX = GetMove(npcs[i].absX, npcs[i].makeX);
						npcs[i].moveY = GetMove(npcs[i].absY, npcs[i].makeY);
						handleClipping(i);
						npcs[i].getNextNPCMovement(i);
						handleClipping(i);
						npcs[i].updateRequired = true;
					}
					if (npcs[i].walkingType == 1) {
						if (Misc.random(3) == 1 && !npcs[i].walkingHome) {
							int MoveX = 0;
							int MoveY = 0;
							int Rnd = Misc.random(9);
							if (Rnd == 1) {
								MoveX = 1;
								MoveY = 1;
							} else if (Rnd == 2) {
								MoveX = -1;
							} else if (Rnd == 3) {
								MoveY = -1;
							} else if (Rnd == 4) {
								MoveX = 1;
							} else if (Rnd == 5) {
								MoveY = 1;
							} else if (Rnd == 6) {
								MoveX = -1;
								MoveY = -1;
							} else if (Rnd == 7) {
								MoveX = -1;
								MoveY = 1;
							} else if (Rnd == 8) {
								MoveX = 1;
								MoveY = -1;
							}

							if (MoveX == 1) {
								if (npcs[i].absX + MoveX < npcs[i].makeX + 1) {
									npcs[i].moveX = MoveX;
								} else {
									npcs[i].moveX = 0;
								}
							}

							if (MoveX == -1) {
								if (npcs[i].absX - MoveX > npcs[i].makeX - 1) {
									npcs[i].moveX = MoveX;
								} else {
									npcs[i].moveX = 0;
								}
							}

							if (MoveY == 1) {
								if (npcs[i].absY + MoveY < npcs[i].makeY + 1) {
									npcs[i].moveY = MoveY;
								} else {
									npcs[i].moveY = 0;
								}
							}

							if (MoveY == -1) {
								if (npcs[i].absY - MoveY > npcs[i].makeY - 1) {
									npcs[i].moveY = MoveY;
								} else {
									npcs[i].moveY = 0;
								}
							}

							int x = (npcs[i].absX + npcs[i].moveX);
							int y = (npcs[i].absY + npcs[i].moveY);

							// if (npcs[i].heightLevel, npcs[i].absX,
							// npcs[i].absY, x, y, 0))
							handleClipping(i);
							npcs[i].getNextNPCMovement(i);
							handleClipping(i);
							// else
							// {
							// npcs[i].moveX = 0;
							// npcs[i].moveY = 0;
							// }
							npcs[i].updateRequired = true;
						}
					}
				}

				if (npcs[i].isDead == true) {
					if (npcs[i].actionTimer == 0 && npcs[i].applyDead == false
							&& npcs[i].needRespawn == false) {
						npcs[i].updateRequired = true;
						npcs[i].facePlayer(0);
						npcs[i].killedBy = getNpcKillerId(i);
						npcs[i].animNumber = getDeadEmote(i); // dead emote
						npcs[i].animUpdateRequired = true;
						npcs[i].freezeTimer = 0;
						npcs[i].applyDead = true;
						killedBarrow(i);
						killedCrypt(i);
						if (isFightCaveNpc(i))
							killedTzhaar(i);
						npcs[i].actionTimer = 4; // delete time
						resetPlayersInCombat(i);
					} else if (npcs[i].actionTimer == 0
							&& npcs[i].applyDead == true
							&& npcs[i].needRespawn == false) {
						npcs[i].needRespawn = true;
						npcs[i].actionTimer = getRespawnTime(i); // respawn time
						dropItems(i);
						appendSlayerExperience(i);
						appendDuoSlayerExperience(i);
						appendKillCount(i);
						// appendJailKc(i);
						npcs[i].absX = npcs[i].makeX;
						npcs[i].absY = npcs[i].makeY;
						npcs[i].HP = npcs[i].MaxHP;
						npcs[i].animNumber = 0x328;
						npcs[i].updateRequired = true;
						npcs[i].animUpdateRequired = true;
						if (npcs[i].npcType == 2745) {
							handleJadDeath(i);
						}
						if (isZombie(i)) {
						}
					} else if (npcs[i].actionTimer == 0
							&& npcs[i].needRespawn == true
							&& npcs[i].npcType != 6142
							&& npcs[i].npcType != 6143
							&& npcs[i].npcType != 6144
							&& npcs[i].npcType != 6145) {
						Client player = (Client) PlayerHandler.players[npcs[i].spawnedBy];
						if (player != null) {
							npcs[i] = null;
						} else {
							int old1 = npcs[i].npcType;
							int old2 = npcs[i].makeX;
							int old3 = npcs[i].makeY;
							int old4 = npcs[i].heightLevel;
							int old5 = npcs[i].walkingType;
							int old6 = npcs[i].MaxHP;
							int old7 = npcs[i].maxHit;
							int old8 = npcs[i].attack;
							int old9 = npcs[i].defence;

							npcs[i] = null;
							newNPC(old1, old2, old3, old4, old5, old6, old7,
									old8, old9);
						}
					}
				}
			}
		}
	}

	private void killedBarrow(int i) {
		Client c = (Client)Server.playerHandler.players[npcs[i].killedBy];
		if(c != null) {
			for(int o = 0; o < c.barrowsNpcs.length; o++){
				if(npcs[i].npcType == c.barrowsNpcs[o][0]) {
					c.barrowsNpcs[o][1] = 2; // 2 for dead
					c.barrowsKillCount++;
				}
			}
		}
	}
	private void killedCrypt(int i) {
		Client c = (Client)Server.playerHandler.players[npcs[i].killedBy];
		if(c != null) {
			for(int o = 0; o < c.barrowCrypt.length; o++){
				if(npcs[i].npcType == c.barrowCrypt[o][0]) {
					c.barrowsKillCount++;
					c.getPA().sendFrame126(""+c.barrowsKillCount, 16137);
				}
			}
		}
	}

	public boolean getsPulled(int i) {
		switch (npcs[i].npcType) {
		case 6260:
			if (npcs[i].firstAttacker > 0)
				return false;
			break;
		}
		return true;
	}

	public boolean multiAttacks(int i) {
		switch (npcs[i].npcType) {
		case 3499: //callisto
			if (npcs[i].attackType == 1)
		return true;
		case 3943:
			if (npcs[i].attackType == 2)
		return true;
		case 3847: //kraken
				if (npcs[i].attackType == 1)
			return true;
		case 6222:// kree
			if (npcs[i].attackType == 1 || npcs[i].attackType == 2)
				return true;
		case 6218:// corp
			if (npcs[i].attackType == 2 || npcs[i].attackType == 1)
				return true;
		case 2558:
			return true;
		case 2562:
			if (npcs[i].attackType == 2)
				return true;
		case 6260:
			if (npcs[i].attackType == 1)
				return true;
		default:
			return false;
		}

	}

	/**
	 * Npc killer id?
	 **/

	public int getNpcKillerId(int npcId) {
		int oldDamage = 0;
		int count = 0;
		int killerId = 0;
		for (int p = 1; p < Config.MAX_PLAYERS; p++) {
			if (PlayerHandler.players[p] != null) {
				if (PlayerHandler.players[p].lastNpcAttacked == npcId) {
					if (PlayerHandler.players[p].totalDamageDealt > oldDamage) {
						oldDamage = PlayerHandler.players[p].totalDamageDealt;
						killerId = p;
					}
					PlayerHandler.players[p].totalDamageDealt = 0;
				}
			}
		}
		return killerId;
	}

	/**
	 * 
	 */

	private void killedTzhaar(int i) {
		final Client c2 = (Client) PlayerHandler.players[npcs[i].spawnedBy];
		c2.tzhaarKilled++;
		if (c2.tzhaarKilled == c2.tzhaarToKill) {
			c2.waveId++;
			if (c2 != null) {
				Server.fightCaves.spawnNextWave(c2);
			}

		}
	}

	public void handleJadDeath(int i) {
		Client c = (Client) PlayerHandler.players[npcs[i].spawnedBy];
		c.getItems().addItem(6570, 1);
		c.sendMessage("Congratulations on completing the fight caves minigame!");
		c.getPA().resetTzhaar();
		c.waveId = 300;
	}

	private void spawnSecondForm(final int i) {
		EventManager.getSingleton().addEvent(new Event() {
			@Override
			public void execute(EventContainer e) {

				spawnNpc2(1160, npcs[i].absX, npcs[i].absY, 0, 1, 255, 31, 600,
						300);
				e.stop();
			}
		}, 4000);
	}

	private void spawnFirstForm(final int i) {
		EventManager.getSingleton().addEvent(new Event() {
			@Override
			public void execute(EventContainer e) {

				spawnNpc2(1158, npcs[i].absX, npcs[i].absY, 0, 1, 255, 31, 600,
						300);
				e.stop();

			}

		}, 30000);
	}
	
	public static void appendAssaultCount(int i) {
		Client c = (Client) PlayerHandler.players[npcs[i].killedBy];
		if (c != null) {
			int[] assaultMonsters = { 253, 258 };
			for (int j : assaultMonsters) {
				if (npcs[i].npcType == 258) {
					c.assaultPoints += 2;
				}
				if (npcs[i].npcType == j) {
					c.assaultPoints++;
					c.sendMessage("@blu@Barbarian Assault Points: @red@" + c.assaultPoints);
					c.getPA().loadQuests();
				}
			}
		}
	}

	public void handleAssaultMG(int i) {
		Client c = (Client) PlayerHandler.players[npcs[i].killedBy];
		c.assaultPoints += 1;
		c.sendMessage("@red@You now have " + c.assaultPoints + " Assault Point(s).");
		c.getPA().sendFrame126(
				"@or1@Assault Points: @gre@" + c.assaultPoints + "", 29171);
	}
	
	public void defenderDrop(int i) {
		Client c = (Client) PlayerHandler.players[npcs[i].killedBy];
		
		int dropDef = Misc.random(12);
		int x = npcs[i].absX;
		int y = npcs[i].absY;
		
		if (dropDef == 1) {
		if (c.playerEquipment[c.playerShield] == 8844) {
			c.getItems().addItem(8845, 1);
			c.sendMessage("@blu@You receive an Iron Defender in your Inventory!");
		} 
		if (c.playerEquipment[c.playerShield] == 8845) {
			c.getItems().addItem(8846, 1);
			c.sendMessage("@blu@You receive a Steel Defender in your Inventory!");
		} 
		if (c.playerEquipment[c.playerShield] == 8846) {
			c.getItems().addItem(8847, 1);
			c.sendMessage("@blu@You receive a Black Defender in your Inventory!");
		} 
		if (c.playerEquipment[c.playerShield] == 8847) {
			c.getItems().addItem(8848, 1);
			c.sendMessage("@blu@You receive a Mithril Defender in your Inventory!");
		} 
		if (c.playerEquipment[c.playerShield] == 8848) {
			c.getItems().addItem(8849, 1);
			c.sendMessage("@blu@You receive an Adamant Defender in your Inventory!");
		} 
		if (c.playerEquipment[c.playerShield] == 8849) {
			c.getItems().addItem(8850, 1);
			c.sendMessage("@blu@You receive a Rune Defender in your Inventory!");
		} 
		if (c.playerEquipment[c.playerShield] == 8850) {
			c.getItems().addItem(12954, 1);
			c.sendMessage("@blu@You receive a Dragon Defender in your Inventory!");
		} 
		if (c.playerEquipment[c.playerShield] == 12594) {
			c.getItems().addItem(12954, 1);
			c.sendMessage("@blu@You receive a Dragon Defender in your Inventory!");
		} else {
				c.sendMessage("@blu@You receive a Bronze Defender wield it for a chance to obtain a better one.");
				c.getItems().addItem(8844, 1);
			}
		}
	}
	

    
        /*
         *
         * Zulrah
         *
         */
 
        boolean zulrahB;
        boolean zulrahR;
        boolean zulrahG;
 
        public void changeForm(Client c) {
			
				int B = 2044;
				int G = 2022;
				int R = 2043;
			
                /*
                 * If zulraB is true, then it searches for NPC_ID_FOR_B, else if zulrahG is true,
                 * it searches for NPC_ID_FOR_G, otherwise, it searches for NPC_ID_FOR_R
                 */
                NPC zulrah = getNpcForId((zulrahB ? 2044 : (zulrahG ? 2042 : 2043)));
               
                // Could not find the npc for some reason, don't attempt to modify it and return.
                if(zulrah == null)
                        return;
                // How often our NPC changes forms
                //boolean change = Misc.random(10) > 8;
                // No change, no need to continue.
                /*if(!change)
                        return;*/
				//c.RebuildNPCList = true;
                int changeBase = Misc.random(3);
				if (zulrah.HP <= 437 && zulrah.HP > 374) {
					zulrah.npcType = 2043;
					zulrahB = false;
                    zulrahG = false;
                    zulrahR = true;
					c.RebuildNPCList = true;
					
				} else if (zulrah.HP <= 374 && zulrah.HP > 311) {
					zulrah.npcType = 2044;
					zulrahB = true;
					zulrahG = false;
					zulrahR = false;
					c.RebuildNPCList = true;
					
				} else if (zulrah.HP <= 311 && zulrah.HP > 248) {
					zulrah.npcType = 2042;
					zulrahB = false;
                    zulrahG = true;
                    zulrahR = false;
					c.RebuildNPCList = true;
					
				} else if (zulrah.HP <= 248 && zulrah.HP > 185) {
					zulrah.npcType = 2044;
					zulrahB = true;
                    zulrahG = false;
                    zulrahR = false;
					c.RebuildNPCList = true;
					
				} else if (zulrah.HP <= 185 && zulrah.HP > 123) {
					zulrah.npcType = 2042;
					zulrahB = false;
                    zulrahG = true;
                    zulrahR = false;
					c.RebuildNPCList = true;
					
				} else if (zulrah.HP <= 123 && zulrah.HP > 61) {
					zulrah.npcType = 2044;
					zulrahB = true;
                    zulrahG = false;
                    zulrahR = false;
					c.RebuildNPCList = true;
					
				} else if (zulrah.HP <= 61 && zulrah.HP > 0) { //mage & ranged green
					zulrah.npcType = 2042;
					zulrahB = false;
                    zulrahG = true;
                    zulrahR = false;
					c.RebuildNPCList = true;
					
				}
                /*switch(changeBase) {
                        case 0:
                        case 1:
                                zulrah.npcType = 2044;
                                zulrahB = true;
                                zulrahG = false;
                                zulrahR = false;
								c.sendMessage("c");
                                break;
                        case 2:
                                zulrah.npcType = 2042;
                                zulrahB = false;
                                zulrahG = true;
                                zulrahR = false;
								c.sendMessage("c");
                                break;
                        case 3:
                                zulrah.npcType = 2043;
                                zulrahB = false;
                                zulrahG = false;
                                zulrahR = true;
								c.sendMessage("c");
                                break;
                }*/
                // Anim update if you use anims.
                zulrah.animUpdateRequired = true;
                // Updates the look of the npc.
                zulrah.updateRequired = true;
        }
 
        public void spawnZulrah(Client c, int npcType) {
                zulrahB = false;
                zulrahG = true;
                zulrahR = false;
                int x = 3087;
                int y = 3499;
                int trial = Misc.random(1);
                //NPCHandler.startAnimation(5071, 2044);
                Server.npcHandler.spawnNpc(c, npcType, x, y, 0, 0, 500, 41, 1, 1, true, false);
                if (trial == 0) {
                        changeForm(c);
                } else {
						changeForm(c);
				}
        }
 
 
        /*
         *
         * End
         *
         */
         
       
	   public static NPC getNpcForId(int npcId) {
                for(NPC n : NPCHandler.npcs) {
                        if(n != null && n.npcType == npcId) {
                                return n;
                        }
                }
                return null;
        }
		

	/*public void bosslog(int npcId, int i) {
		Client c = (Client) PlayerHandler.players[npcs[i].killedBy];
		switch (npcId) {
		case 3943:
			c.giantSeaSnakeKills += 1;
			c.sendMessage("@red@You now have " + c.giantSeaSnakeKills + " Giant Sea Snake Kills.");
		break;
		case 3499:
			c.callistoKills += 1;
			c.sendMessage("@red@You now have " + c.callistoKills + " Callisto Kills.");
		break;
		case 3500:
			c.venKills += 1;
			c.sendMessage("@red@You now have " + c.venKills + " Venenatis Kills.");
		break;
		case 3501:
			c.scorpiaKills += 1;
			c.sendMessage("@red@You now have " + c.scorpiaKills + " Scorpia Kills.");
		break;
		case 3502:
			c.vetKills += 1;
			c.sendMessage("@red@You now have " + c.vetKills + " Vet'ion Kills.");
		break;
		case 5363:
			c.mithDragKills += 1;
			c.sendMessage("@red@You now have " + c.mithDragKills + " Mithril Dragon Kills.");
		break;
		case 3847:
			c.krakenKills += 1;
			c.sendMessage("@red@You now have " + c.krakenKills
					+ " Kraken Kills.");
			break;

		case 2881:
			c.supremeKills += 1;
			c.sendMessage("@red@You now have " + c.supremeKills
					+ " Dagannoth Supreme Kills.");
			break;

		case 2882:
			c.primeKills += 1;
			c.sendMessage("@red@You now have " + c.primeKills
					+ " Dagannoth Prime Kills.");
			break;

		case 2883:
			c.rexKills += 1;
			c.sendMessage("@red@You now have " + c.rexKills
					+ " Dagannoth Rex Kills.");
			break;

		case 6247:
			c.saraKills += 1;
			c.sendMessage("@red@You now have " + c.saraKills
					+ " Command Zilyana Kills.");
			break;

		case 6222:
			c.armaKills += 1;
			c.sendMessage("@red@You now have " + c.armaKills
					+ " Kree'arra Kills.");
			break;

		case 6260:
			c.bandosKills += 1;
			c.sendMessage("@red@You now have " + c.bandosKills
					+ " General Graardor Kills.");
			break;

		case 6203:
			c.zammyKills += 1;
			c.sendMessage("@red@You now have " + c.zammyKills
					+ " K'ril Ts Kills.");
			break;

		case 1158:
		case 1160:
			c.kqKills += 1;
			c.sendMessage("@red@You now have " + c.kqKills
					+ " Kalphite Queen Kills.");
			break;

		case 5666:
			c.barrelKills += 1;
			c.sendMessage("@red@You now have " + c.barrelKills
					+ " Barrelchest Kills.");
			break;

		case 3200:
			c.chaosKills += 1;
			c.sendMessage("@red@You now have " + c.chaosKills
					+ " Chaos Elemental Kills.");
			break;

		case 50:
			c.kbdKills += 1;
			c.sendMessage("@red@You now have " + c.kbdKills
					+ " King Black Dragon Kills.");
			break;

		}

	}*/
	
	/**
	 * Duo Slayer Experience
	 **/
	public static void appendDuoSlayerExperience(int i) {
		Client c = (Client)PlayerHandler.players[npcs[i].killedBy];
		boolean k = false;
		int slayerXP = npcs[i].MaxHP * Config.SLAYER_EXPERIENCE;
		int partnerSlayerXP = slayerXP / 4;
		if (c != null) {
			if (c.duoTask == npcs[i].npcType) {
				k = true;
			}
			if (k) {
				c.duoTaskAmount--;
				c.getPA().addSkillXP(c, slayerXP, 18);
					if (c.getDuoPartner() != null)
						c.getDuoPartner().duoTaskAmount--;
					if (c.getDuoPartner() != null
							&& c.getDuoPartner().connectedFrom != c.connectedFrom) {
						c.getPA().addSkillXP(((Client) c.getDuoPartner()), partnerSlayerXP, 18);
					}
			}
			if (c.duoTaskAmount == 0) {
				final Player partner = c.getDuoPartner();
				DuoSlayer.getInstance().complete(c, partner);
			}
		}
	}

	/*public void SlayerLog(int npcId, int i) {
		Client c = (Client) PlayerHandler.players[npcs[i].killedBy];
		switch (npcId) {
		case 1648:// done
			c.crawlingHandKills += 1;
			// c.sendMessage("@red@ Your Crawling Hand Kill Count Is: " +
			// c.crawlingHandKills + " ");
			break;

		case 1612:// done
			c.bansheeKills += 1;
			c.sendMessage("@red@ Your Banshee Kill Count Is: " + c.bansheeKills
					+ " ");
			break;

		case 117:// done
			c.hillGiantKills += 1;
			// c.sendMessage("@red@ Your Hill Giant Kill Count Is: " +
			// c.HillGiantKills + " ");
			break;

		case 1265:// done
			//c.crabsKilled += 1;
			c.sendMessage("@red@ Your Rock Crab Kill Count Is: "
					+ c.crabsKilled + " ");
			break;

		case 103:// done
			c.ghostKills += 1;
			// c.sendMessage("@red@ Your Ghost Kill Count Is: " + c.GhostKills +
			// " ");
			break;

		case 78:// done
			c.giantBatKills += 1;
			// c.sendMessage("@red@ Your Giant Bat Kill Count Is: " +
			// c.GiantBatKills + " ");
			break;

		case 119:// done
			c.chaosDwarfKills += 1;
			// c.sendMessage("@red@ Your Chaos Dwarf Kill Count Is: " +
			// c.ChaosDwarfKills + " ");
			break;

		case 18:// done
			c.alKills += 1;
			// c.sendMessage("@red@ Your Al-Khalid Warriors Kill Count Is: " +
			// c.AlKills + " ");
			break;

		case 101:// done
			c.goblinKills += 1;
			// c.sendMessage("@red@ Your Goblins Kill Count Is: " +
			// c.GoblinKills + " ");
			break;

		case 181:// done
			c.chaosDruidKills += 1;
			// c.sendMessage("@red@ Your Chaos Druid Kill Count Is: " +
			// c.ChaosDruidKills + " ");
			break;

		case 1643:
			c.infernalMageKills += 1;
			c.sendMessage("@red@ Your Infernal Mage Kill Count Is: "
					+ c.infernalMageKills + " ");
			break;

		case 1618:
			c.bloodveldKills += 1;
			c.sendMessage("@red@ Your Bloodveld Kill Count Is: "
					+ c.bloodveldKills + " ");
			break;

		case 941:
			c.greenDragKills += 1;
			c.sendMessage("@red@ Your Green Dragon Kill Count Is: "
					+ c.greenDragKills + " ");
			break;

		case 82:
			//c.lesserDemonKills += 1;
			break;

		case 52:
			c.babyDragKills += 1;
			break;

		case 112:
			c.mossGiantKills += 1;
			break;

		case 125:
			c.iceWarriorKills += 1;
			break;

		case 1624:
			c.dustKills += 1;
			c.sendMessage("@red@ Your Dust Devil Kill Count Is: " + c.dustKills
					+ " ");
			break;

		case 1610:
			c.gargKills += 1;
			c.sendMessage("@red@ Your Gargoyle Kill Count Is: " + c.gargKills
					+ " ");
			break;

		case 1613:
			c.nechKills += 1;
			c.sendMessage("@red@ Your Nechryael Kill Count Is: " + c.nechKills
					+ " ");
			break;

		case 1615:
			c.abbyKills += 1;
			c.sendMessage("@red@ Your Abyssal Demon Kill Count Is: "
					+ c.abbyKills + " ");
			break;

		case 55:
			c.blueDragKills += 1;
			c.sendMessage("@red@ Your Blue Dragon Kill Count Is: "
					+ c.blueDragKills + " ");
			break;
			
		case 3068:
		c.WyvernKills++;
		c.sendMessage("@red@ Your Skeletal Wyvern Kill Count Is: "
					+ c.WyvernKills + " ");
		break;
		
		case 84:
			c.blackDemonKills += 1;
			c.sendMessage("@red@ Your Black Demon Kill Count Is: "
					+ c.blackDemonKills + " ");
			break;

		case 2783:
			c.darkBeastKills += 1;
			c.sendMessage("@red@ Your Dark Beast Kill Count Is: "
					+ c.darkBeastKills + " ");
			break;

		}
	}*/

	/**
	 * Dropping Items!
	 **/
	public void sendDrop(Client player, Drop drop, int i) {
		if(drop.getItemId() >= Config.ITEM_LIMIT){
			return;
		}
                if(Item.getItemName(drop.getItemId()) == null){
			return;
		}
                GameItem item = new GameItem(drop.getItemId(), 1).stackable ? new GameItem(
				drop.getItemId(), (drop.getMinAmount() * Config.DROP_RATE)
						+ Misc.random(drop.getExtraAmount() * Config.DROP_RATE))
				: new GameItem(drop.getItemId(), drop.getMinAmount()
						+ Misc.random(drop.getExtraAmount()));

		Server.itemHandler.createGroundItem(player, item.id, npcs[i].absX,
				npcs[i].absY, item.amount, player.playerId);

	}

	public void dropItems(int i) {
		Client killer = (Client)PlayerHandler.players[npcs[i].killedBy];
		Drop[] drops = NPCDrops.getDrops(npcs[i].npcType);
		if (drops == null)
			return;
		Client c = (Client) PlayerHandler.players[npcs[i].killedBy];
		if (c != null) {
			if (npcs[i].npcType == 912 || npcs[i].npcType == 913
					|| npcs[i].npcType == 914)
				c.magePoints += 1;
			Drop[] possibleDrops = new Drop[drops.length];
			int possibleDropsCount = 0;
			for (Drop drop : drops) {
				if (drop.getRate() == 100)
					sendDrop(killer, drop, i);
				else {
					if ((Misc.random(99) + 1) <= drop.getRate() * 1.0)
						possibleDrops[possibleDropsCount++] = drop;
				}
			}
			if (possibleDropsCount > 0)
				sendDrop(killer,
						possibleDrops[Misc.random(possibleDropsCount - 1)], i);

		}
	}

	// id of bones dropped by npcs
	public int boneDrop(int type) {
		switch (type) {
		case 1:// normal bones
		case 9:
		case 100:
		case 12:
		case 17:
		case 803:
		case 18:
		case 81:
		case 101:
		case 41:
		case 19:
		case 90:
		case 75:
		case 86:
		case 78:
		case 912:
		case 913:
		case 914:
		case 1648:
		case 1643:
		case 1618:
		case 1624:
		case 181:
		case 119:
		case 49:
		case 26:
		case 1341:
			return 526;
		case 3068:
		return 6812;
		case 117:
			return 532;// big bones
		case 50:// drags
		case 53:
		case 54:
		case 55:
		case 941:
		case 1590:
		case 1591:
		case 1592:
		case 5363:
			return 536;
		case 84:
		case 1615:
		case 1613:
		case 82:
		case 3200:
			return 592;
		case 2881:
		case 2882:
		case 2883:
			return 6729;
		default:
			return -1;
		}
	}

	public void appendKillCount(int i) {
		Client c = (Client) PlayerHandler.players[npcs[i].killedBy];
		if (c != null) {
			int[] kcMonsters = { 122, 49, 2558, 2559, 2560, 2561, 2550, 2551,
					2552, 2553, 2562, 2563, 2564, 2565 };
			for (int j : kcMonsters) {
				if (npcs[i].npcType == j) {
					if (c.killCount < 20) {
						// c.killCount++;
						// c.sendMessage("Killcount: " + c.killCount);
					} else {
						// c.sendMessage("You already have 20 kill count");
					}
					break;
				}
			}
		}
	}

	public int getStackedDropAmount(int itemId, int npcId) {
		switch (itemId) {
		case 995:
			switch (npcId) {
			case 1:
				return 50 + Misc.random(50);
			case 9:
				return 133 + Misc.random(100);
			case 1624:
				return 1000 + Misc.random(300);
			case 1618:
				return 1000 + Misc.random(300);
			case 1643:
				return 1000 + Misc.random(300);
			case 1610:
				return 1000 + Misc.random(1000);
			case 1613:
				return 1500 + Misc.random(1250);
			case 1615:
				return 3000;
			case 18:
				return 500;
			case 101:
				return 60;
			case 913:
			case 912:
			case 914:
				return 750 + Misc.random(500);
			case 1612:
				return 250 + Misc.random(500);
			case 1648:
				return 250 + Misc.random(250);
			case 90:
				return 200;
			case 82:
				return 1000 + Misc.random(455);
			case 52:
				return 400 + Misc.random(200);
			case 49:
				return 1500 + Misc.random(2000);
			case 1341:
				return 1500 + Misc.random(500);
			case 26:
				return 500 + Misc.random(100);
			case 20:
				return 750 + Misc.random(100);
			case 21:
				return 890 + Misc.random(125);
			case 117:
				return 500 + Misc.random(250);
			case 2607:
				return 500 + Misc.random(350);
			}
			break;
		case 11212:
			return 10 + Misc.random(4);
		case 565:
		case 561:
			return 10;
		case 560:
		case 563:
		case 562:
			return 15;
		case 555:
		case 554:
		case 556:
		case 557:
			return 20;
		case 892:
			return 40;
		case 886:
			return 100;
		case 6522:
			return 6 + Misc.random(5);

		}

		return 1;
	}



	public void appendSlayerExperience(int i) {
		int npc = 0;
		Client c = (Client) PlayerHandler.players[npcs[i].killedBy];
		if (c != null) {
			if (c.slayerTask == npcs[i].npcType) {
				c.taskAmount--;
				c.getPA().addSkillXP(npcs[i].MaxHP * Config.SLAYER_EXPERIENCE,
						18);
				c.getPA()
						.sendFrame126(
								"@or1@Task: @gre@"
										+ c.taskAmount
										+ " "
										+ Server.npcHandler.getNpcListName(c.slayerTask)
										+ " ", 29168);
				if (c.taskAmount <= 0) {
					if (c.combatLevel < 50) {
						c.getPA().addSkillXP(
								(npcs[i].MaxHP * 8) * Config.SLAYER_EXPERIENCE,
								18);
						c.slayerTask = -1;
						c.slayerPoints += 5;
						c.getPA().sendFrame126(
								"@or1@Slayer Points: @gre@" + c.slayerPoints
										+ " ", 29167);
						c.sendMessage("You completed your low level slayer task. Please see a slayer master to get a new one.");
						c.sendMessage("Also you have received @red@5 @bla@slayer points for this.");
						// c.getItems().addItem(995, 250000);
						if (c.playerEquipment[c.playerHat] == 13263) {
							c.slayerPoints += 12;
							c.getPA().addSkillXP(
									(npcs[i].MaxHP * 5)
											* Config.SLAYER_EXPERIENCE, 18);
							c.sendMessage("@red@You've recieved a bonus of xp and 12 slayer points because wearing slayer helmet.");
						}
					} else if (c.combatLevel >= 50 && c.combatLevel <= 90) {
						c.getPA()
								.addSkillXP(
										(npcs[i].MaxHP * 12)
												* Config.SLAYER_EXPERIENCE, 18);
						c.slayerTask = -1;
						c.slayerPoints += 10;
						c.getPA().sendFrame126(
								"@or1@Slayer Points: @gre@" + c.slayerPoints
										+ " ", 7339);
						c.sendMessage("You completed your @blu@medium@bla@ level slayer task. Please see a slayer master to get a new one.");
						c.sendMessage("Also you have received @blu@10 @bla@slayer points for this.");
						// c.getItems().addItem(995, 500000);
						if (c.playerEquipment[c.playerHat] == 13263) {
							c.slayerPoints += 12;
							c.getPA().addSkillXP(
									(npcs[i].MaxHP * 5)
											* Config.SLAYER_EXPERIENCE, 18);
							c.sendMessage("@red@You've recieved a bonus of xp and 12 slayer points because wearing slayer helmet.");
						}
					} else if (c.combatLevel > 90 && c.combatLevel <= 138) {
						c.getPA()
								.addSkillXP(
										(npcs[i].MaxHP * 12)
												* Config.SLAYER_EXPERIENCE, 18);
						c.slayerTask = -1;
						c.slayerPoints += 15;
						c.getPA().sendFrame126(
								"@or1@Slayer Points: @gre@" + c.slayerPoints
										+ " ", 7339);
						c.sendMessage("You completed your @red@high@bla@ level slayer task. Please see a slayer master to get a new one.");
						c.sendMessage("Also you have received @red@15 @bla@slayer points for this.");
						// c.getItems().addItem(995, 1000000);
						if (c.playerEquipment[c.playerHat] == 13263) {
							c.slayerPoints += 12;
							c.getPA().addSkillXP(
									(npcs[i].MaxHP * 5)
											* Config.SLAYER_EXPERIENCE, 18);
							c.sendMessage("@red@You've recieved a bonus of xp and 12 slayer points because wearing slayer helmet.");
						}
					} else if (c.combatLevel >= 100 && c.eliteTask == true) {
						c.getPA()
						.addSkillXP(
								(npcs[i].MaxHP * 12)
										* Config.SLAYER_EXPERIENCE, 18);
				c.slayerTask = -1;
				c.slayerPoints += 20;
				c.getPA().sendFrame126(
						"@or1@Slayer Points: @gre@" + c.slayerPoints
								+ " ", 7339);
				c.sendMessage("You completed your @red@elite@bla@ level slayer task. Please see a slayer master to get a new one.");
				c.sendMessage("Also you have received @red@20 @bla@slayer points for this.");
				c.eliteTask = false;
				// c.getItems().addItem(995, 1000000);
				if (c.playerEquipment[c.playerHat] == 13263) {
					c.slayerPoints += 22;
					c.getPA().addSkillXP(
							(npcs[i].MaxHP * 5)
									* Config.SLAYER_EXPERIENCE, 18);
					c.sendMessage("@red@You've recieved a bonus of xp and 22 slayer points because wearing slayer helmet.");
				}
					}
				}
			}
		}
	}
			
	
	/**
	 * Resets players in combat
	 */

	public void resetPlayersInCombat(int i) {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null)
				if (PlayerHandler.players[j].underAttackBy2 == i)
					PlayerHandler.players[j].underAttackBy2 = 0;
		}
	}

	/**
	 * Npc names
	 **/

	public String getNpcName(int npcId) {
		if (npcId <= -1) {
			return "None";
		}
		if (NPCDefinitions.getDefinitions()[npcId] == null) {
			return "None";
		}
		return NPCDefinitions.getDefinitions()[npcId].getNpcName();
	}

	/**
	 * Npc Follow Player
	 **/

	public int GetMove(int Place1, int Place2) {
		if ((Place1 - Place2) == 0) {
			return 0;
		} else if ((Place1 - Place2) < 0) {
			return 1;
		} else if ((Place1 - Place2) > 0) {
			return -1;
		}
		return 0;
	}

	public boolean followPlayer(int i) {
		if (npcs[i] == null)
			return false;
		switch (npcs[i].npcType) {
		case 2892:
		case 2894:
			/*
			 * case 2030: case 2029: case 2028: case 2027: case 2026: case 2025:
			 */
			return false;
		}
		return true;
	}

	public void followPlayer(int i, int playerId) {
		if (PlayerHandler.players[playerId] == null) {
			return;
		}
		if (PlayerHandler.players[playerId].respawnTimer > 0) {
			npcs[i].facePlayer(0);
			npcs[i].randomWalk = true;
			npcs[i].underAttack = false;
			return;
		}

		if (!followPlayer(i)) {
			npcs[i].facePlayer(playerId);
			return;
		}

		int playerX = PlayerHandler.players[playerId].absX;
		int playerY = PlayerHandler.players[playerId].absY;
		npcs[i].randomWalk = false;
		if (goodDistance(npcs[i].getX(), npcs[i].getY(), playerX, playerY,
				distanceRequired(i)))
			return;
		if ((npcs[i].spawnedBy > 0)
				|| ((npcs[i].absX < npcs[i].makeX + Config.NPC_FOLLOW_DISTANCE)
						&& (npcs[i].absX > npcs[i].makeX
								- Config.NPC_FOLLOW_DISTANCE)
						&& (npcs[i].absY < npcs[i].makeY
								+ Config.NPC_FOLLOW_DISTANCE) && (npcs[i].absY > npcs[i].makeY
						- Config.NPC_FOLLOW_DISTANCE))) {
			if (npcs[i].heightLevel == PlayerHandler.players[playerId].heightLevel) {
				if (PlayerHandler.players[playerId] != null && npcs[i] != null) {
					if (playerY < npcs[i].absY) {
						npcs[i].moveX = GetMove(npcs[i].absX, playerX);
						npcs[i].moveY = GetMove(npcs[i].absY, playerY);
					} else if (playerY > npcs[i].absY) {
						npcs[i].moveX = GetMove(npcs[i].absX, playerX);
						npcs[i].moveY = GetMove(npcs[i].absY, playerY);
					} else if (playerX < npcs[i].absX) {
						npcs[i].moveX = GetMove(npcs[i].absX, playerX);
						npcs[i].moveY = GetMove(npcs[i].absY, playerY);
					} else if (playerX > npcs[i].absX) {
						npcs[i].moveX = GetMove(npcs[i].absX, playerX);
						npcs[i].moveY = GetMove(npcs[i].absY, playerY);
					} else if (playerX == npcs[i].absX
							|| playerY == npcs[i].absY) {
						int o = Misc.random(3);
						switch (o) {
						case 0:
							npcs[i].moveX = GetMove(npcs[i].absX, playerX);
							npcs[i].moveY = GetMove(npcs[i].absY, playerY + 1);
							break;

						case 1:
							npcs[i].moveX = GetMove(npcs[i].absX, playerX);
							npcs[i].moveY = GetMove(npcs[i].absY, playerY - 1);
							break;

						case 2:
							npcs[i].moveX = GetMove(npcs[i].absX, playerX + 1);
							npcs[i].moveY = GetMove(npcs[i].absY, playerY);
							break;

						case 3:
							npcs[i].moveX = GetMove(npcs[i].absX, playerX - 1);
							npcs[i].moveY = GetMove(npcs[i].absY, playerY);
							break;
						}
					}
					int x = (npcs[i].absX + npcs[i].moveX);
					int y = (npcs[i].absY + npcs[i].moveY);
					npcs[i].facePlayer(playerId);
					handleClipping(i);
					npcs[i].getNextNPCMovement(i);
					handleClipping(i);
					/*
					 * else { npcs[i].moveX = 0; npcs[i].moveY = 0; }
					 */
					npcs[i].facePlayer(playerId);
					npcs[i].updateRequired = true;
				}
			}
		} else {
			npcs[i].facePlayer(0);
			npcs[i].randomWalk = true;
			npcs[i].underAttack = false;
		}
	}

	/**
	 * load spell
	 **/
	public void loadSpell2(int i) {
		npcs[i].attackType = 3;
		int random = Misc.random(3);
		if (random == 0) {
			npcs[i].projectileId = 393; // red
			npcs[i].endGfx = 430;
		} else if (random == 1) {
			npcs[i].projectileId = 394; // green
			npcs[i].endGfx = 429;
		} else if (random == 2) {
			npcs[i].projectileId = 395; // white
			npcs[i].endGfx = 431;
		} else if (random == 3) {
			npcs[i].projectileId = 396; // blue
			npcs[i].endGfx = 428;
		}
	}

	int r3 = 0;

	public void loadSpell(int i) {
		switch (npcs[i].npcType) {
		case 4056:
		int zulrah = Misc.random(1);
		if (zulrah == 0) {
			
		}
		break;

		case 3068:
			if (Misc.random(10) > 7) {
				npcs[i].projectileId = 395; // icy
				npcs[i].endGfx = 431;
				npcs[i].attackType = 3;
				startAnimation(2989, i);
			} else {
				startAnimation(2980, i);
				npcs[i].attackType = 0;
			}
			break;
		
		case 3499:
			int random5 = Misc.random(2);
			if (random5 == 0) {
				npcs[i].projectileId = 435; //red
				npcs[i].attackType = 3;
				npcs[i].endGfx = 402;
				npcs[i].forceChat("I WILL KILL YOU!");
			} else if (random5 == 1) {
				npcs[i].projectileId = 26; //red
				npcs[i].attackType = 2;
				npcs[i].endGfx = 267;
				npcs[i].forceChat("HAHAHAHA!");
			} else if (random5 == 2) {
				npcs[i].projectileId = 59; //melee
				npcs[i].endGfx = 354;
				npcs[i].attackType = 0;	
			} else if (random5 == 3) {
				npcs[i].attackType = 0;
				npcs[i].projectileId = 481;
				npcs[i].forceChat("THE TIME HAS COME!!!!!!! HAHAHAHA!");
			}
			break;
			
			case 3501: // scorpia
			int random45 = Misc.random(2);
			if (random45 == 0) {
				npcs[i].attackType = 0;
			} else if (random45 == 1) {
				npcs[i].projectileId = 359;
				npcs[i].attackType = 3;
			} else if (random45 == 2) {
				npcs[i].projectileId = 311;
				npcs[i].attackType = 0;
				npcs[i].forceChat("Skrrrrrrrrrrrrr!!!!");
				}
			break;
			
		case 3500: //venenatis
			int random4 = Misc.random(2);
			if (random4 == 0) {
				npcs[i].projectileId = 435; //red
				npcs[i].attackType = 3;
				npcs[i].endGfx = 402;
				npcs[i].forceChat("I WILL KILL YOU!");
			} else if (random4 == 1) {
				npcs[i].projectileId = 26; //red
				npcs[i].attackType = 2;
				npcs[i].endGfx = 267;
				npcs[i].forceChat("HAHAHAHA!");
			} else if (random4 == 2) {
				npcs[i].projectileId = 59; //melee
				npcs[i].endGfx = 354;
				npcs[i].attackType = 0;	
			} else if (random4 == 3) {
				npcs[i].attackType = 0;
				npcs[i].projectileId = 481;
				npcs[i].forceChat("THE TIME HAS COME!!!!!!! HAHAHAHA!");
			}
			break;
			
			case 3502:  //vetion
			int random25 = Misc.random(2);
			if (random25 == 0) {
				npcs[i].endGfx = 83;
				npcs[i].attackType = 0;
			} else if (random25 == 1) {
				npcs[i].endGfx = 346;
				npcs[i].attackType = 3;
			} else if (random25 == 2) {
				npcs[i].attackType = 0;
				npcs[i].projectileId = 364;
				npcs[i].forceChat("YOU.SHALL.REGRET.ATTACKING.ME!");
			}
		break;
		
		case 6222:// Kree'arra
			int random1 = Misc.random(1);
			npcs[i].attackType = 1 + random1;
			if (npcs[i].attackType == 1) {
				npcs[i].projectileId = 1197;
			} else {
				npcs[i].attackType = 2;
				npcs[i].projectileId = 1196;
			}
			break;
		case 6218:
			if (goodDistance(npcs[i].absX, npcs[i].absY,
					PlayerHandler.players[npcs[i].killerId].absX,
					PlayerHandler.players[npcs[i].killerId].absY, 2))
				r3 = Misc.random(2);
			else
				r3 = Misc.random(1);
			if (r3 == 0) {
				npcs[i].attackType = 2;
				npcs[i].endGfx = -1;
				npcs[i].projectileId = 1825;
			} else if (r3 == 1) {
				npcs[i].attackType = 1;
				npcs[i].endGfx = -1;
				npcs[i].projectileId = 1824;
			} else if (r3 == 2) {
				npcs[i].attackType = 0;
				npcs[i].gfx100(1834);
				npcs[i].projectileId = -1;
			}
			break;
		// kalphite queen form 1
		case 1158:
			for (int j = 0; j < PlayerHandler.players.length; j++) {
				if (PlayerHandler.players[j] != null) {
					int kq1;
					if (goodDistance(npcs[i].absX, npcs[i].absY,
							PlayerHandler.players[npcs[i].killerId].absX,
							PlayerHandler.players[npcs[i].killerId].absY, 1))
						kq1 = Misc.random(2);
					else
						kq1 = Misc.random(1);
					if (kq1 == 0) {
						npcs[i].attackType = 2; // mage
						npcs[i].gfx0(278);
						npcs[i].projectileId = 280;
						npcs[i].endGfx = 281;
					} else if (kq1 == 1) {
						npcs[i].attackType = 1; // range
						npcs[i].gfx0(-1);
						npcs[i].endGfx = -1;
						npcs[i].projectileId = 473;
					} else if (kq1 == 2) {
						npcs[i].attackType = 0; // melee
						npcs[i].projectileId = -1;
					}
				}
			}
			break;
		// kalphite queen form 2
		case 1160:
			for (int j = 0; j < PlayerHandler.players.length; j++) {
				if (PlayerHandler.players[j] != null) {
					int kq1;
					if (goodDistance(npcs[i].absX, npcs[i].absY,
							PlayerHandler.players[npcs[i].killerId].absX,
							PlayerHandler.players[npcs[i].killerId].absY, 1))
						kq1 = Misc.random(2);
					else
						kq1 = Misc.random(1);
					if (kq1 == 0) {
						npcs[i].attackType = 2; // mage
						npcs[i].gfx0(279);
						npcs[i].projectileId = 280;
						npcs[i].endGfx = 281;
					} else if (kq1 == 1) {
						npcs[i].attackType = 1; // range
						npcs[i].gfx0(-1);
						npcs[i].endGfx = -1;
						npcs[i].projectileId = 473;
					} else if (kq1 == 2) {
						npcs[i].attackType = 0; // melee
						npcs[i].projectileId = -1;
					}
				}
			}
			break;
		case 2892:
			npcs[i].projectileId = 94;
			npcs[i].attackType = 2;
			npcs[i].endGfx = 95;
			break;
		case 2894:
			npcs[i].projectileId = 298;
			npcs[i].attackType = 1;
			break;
		
		case 2044:
		int zulrahB = Misc.random(1);
		if (zulrahB == 0)
			npcs[i].projectileId = 394; // green
			npcs[i].endGfx = 429;
			npcs[i].attackType = 1;
		if (zulrahB == 1)
			npcs[i].projectileId = 393; // red
			npcs[i].endGfx = 430;
			npcs[i].attackType = 2;
		break;
		
		case 2042:
			npcs[i].projectileId = 394; // green
			npcs[i].endGfx = 429;
			npcs[i].attackType = 1;
		break;
		
		case 2043:
			npcs[i].projectileId = -1;
			npcs[i].endGfx = -1;
			npcs[i].attackType = 0;
		break;

		
		case 50:
		case 5363:
			int random = Misc.random(4);
			if (random == 0) {
				npcs[i].projectileId = 393; // red
				npcs[i].endGfx = 430;
				npcs[i].attackType = 3;
			} else if (random == 1) {
				npcs[i].projectileId = 394; // green
				npcs[i].endGfx = 429;
				npcs[i].attackType = 3;
			} else if (random == 2) {
				npcs[i].projectileId = 395; // white
				npcs[i].endGfx = 431;
				npcs[i].attackType = 3;
			} else if (random == 3) {
				npcs[i].projectileId = 396; // blue
				npcs[i].endGfx = 428;
				npcs[i].attackType = 3;
			} else if (random == 4) {
				npcs[i].projectileId = -1; // melee
				npcs[i].endGfx = -1;
				npcs[i].attackType = 0;
			}
			break;
		// arma npcs
		case 2561:
			npcs[i].attackType = 0;
			break;
		case 2560:
			npcs[i].attackType = 1;
			npcs[i].projectileId = 1190;
			break;
		case 2559:
			npcs[i].attackType = 2;
			npcs[i].projectileId = 1203;
			break;
		case 2558:
			random = Misc.random(1);
			npcs[i].attackType = 1 + random;
			if (npcs[i].attackType == 1) {
				npcs[i].projectileId = 1197;
			} else {
				npcs[i].attackType = 2;
				npcs[i].projectileId = 1198;
			}
			break;
		// sara npcs
		case 2562: // sara
			random = Misc.random(1);
			if (random == 0) {
				npcs[i].attackType = 2;
				npcs[i].endGfx = 1224;
				npcs[i].projectileId = -1;
			} else if (random == 1)
				npcs[i].attackType = 0;
			break;
		case 2563: // star
			npcs[i].attackType = 0;
			break;
		case 2564: // growler
			npcs[i].attackType = 2;
			npcs[i].projectileId = 1203;
			break;
		case 2565: // bree
			npcs[i].attackType = 1;
			npcs[i].projectileId = 9;
			break;
		// bandos npcs
		case 6260:
			random = Misc.random(2);
			if (random == 0 || random == 1)
				npcs[i].attackType = 0;
			else {
				npcs[i].attackType = 1;
				npcs[i].endGfx = 1211;
				npcs[i].projectileId = 288;
			}
			break;
		case 2551:
			npcs[i].attackType = 0;
			break;
		case 2552:
			npcs[i].attackType = 2;
			npcs[i].projectileId = 1203;
			break;
		case 2553:
			npcs[i].attackType = 1;
			npcs[i].projectileId = 1206;
			break;
		case 2025:
			npcs[i].attackType = 2;
			int r = Misc.random(3);
			if (r == 0) {
				npcs[i].gfx100(158);
				npcs[i].projectileId = 159;
				npcs[i].endGfx = 160;
			}
			if (r == 1) {
				npcs[i].gfx100(161);
				npcs[i].projectileId = 162;
				npcs[i].endGfx = 163;
			}
			if (r == 2) {
				npcs[i].gfx100(164);
				npcs[i].projectileId = 165;
				npcs[i].endGfx = 166;
			}
			if (r == 3) {
				npcs[i].gfx100(155);
				npcs[i].projectileId = 156;
			}
			break;
		case 2881:// supreme
			npcs[i].attackType = 1;
			npcs[i].projectileId = 298;
			break;

		case 2882:// prime
			npcs[i].attackType = 2;
			npcs[i].projectileId = 162;
			npcs[i].endGfx = 477;
			break;

			case 3847:
		int rand = Misc.random(2);
		if (rand == 0) {
				npcs[i].projectileId = 162; // green
				npcs[i].endGfx = 163;
				npcs[i].attackType = 2;
		} else if (rand == 1) {
				npcs[i].attackType = 1;
				npcs[i].endGfx = 166;
				npcs[i].projectileId = 165;
			
		} else if (rand == 2) {
				npcs[i].attackType = 0;
			}
			break;
		case 3943:
			int rando = Misc.random(2);
			if (rando == 0) {
				npcs[i].projectileId = 162; // green
				npcs[i].endGfx = 163;
				npcs[i].attackType = 2;
			} else if (rando == 1) {
				npcs[i].projectileId = 395; // white
				npcs[i].endGfx = 431;
				npcs[i].attackType = 1;
			}
			break;

		case 2028:
			npcs[i].attackType = 1;
			npcs[i].projectileId = 27;
			break;

		case 3200:
			int r2 = Misc.random(1);
			if (r2 == 0) {
				npcs[i].attackType = 1;
				npcs[i].gfx100(550);
				npcs[i].projectileId = 551;
				npcs[i].endGfx = 552;
			} else {
				npcs[i].attackType = 2;
				npcs[i].gfx100(553);
				npcs[i].projectileId = 554;
				npcs[i].endGfx = 555;
			}
			break;
		case 2745:
			int r3 = 0;
			if (goodDistance(npcs[i].absX, npcs[i].absY,
					PlayerHandler.players[npcs[i].spawnedBy].absX,
					PlayerHandler.players[npcs[i].spawnedBy].absY, 1))
				r3 = Misc.random(2);
			else
				r3 = Misc.random(1);
			if (r3 == 0) {
				npcs[i].attackType = 2;
				npcs[i].endGfx = 157;
				npcs[i].projectileId = 448;
			} else if (r3 == 1) {
				npcs[i].attackType = 1;
				npcs[i].endGfx = 451;
				npcs[i].projectileId = 443;
			} else if (r3 == 2) {
				npcs[i].attackType = 0;
				npcs[i].projectileId = -1;
			}
			break;
		case 2743:
			npcs[i].attackType = 2;
			npcs[i].projectileId = 445;
			npcs[i].endGfx = 446;
			break;

		case 2631:
			npcs[i].attackType = 1;
			npcs[i].projectileId = 443;
			break;
		}
	}

	/**
	 * Distanced required to attack
	 **/
	public int distanceRequired(int i) {
		
		switch (npcs[i].npcType) {
		case 4056:
			return 8;
		case 3499:
		case 3500:
		case 3501:
		case 3502:
			return 12;
		case 2025:
		case 2028:
			return 6;
		case 6222:
			return 20;
		case 6218:
		case 1158:
		case 1160:
			return 7;
		case 50:
		case 5363:
		case 2562:
			return 2;
		case 2881:// dag kings
		case 2882:
		case 3200:// chaos ele
		case 2743:
		case 2631:
		case 2745:
		case 3847:
			return 8;
		case 2883:// rex
			return 1;
		case 2552:
		case 2553:
		case 2556:
		case 2557:
		case 2558:
		case 2559:
		case 2560:
		case 2564:
		case 2565:
			return 9;
			// things around dags
		case 2892:
		case 3943:
		case 2894:
			return 10;
//start
		
//stop
		default:
			return 1;
		}
	}

	public int followDistance(int i) {
		switch (npcs[i].npcType) {
		case 6260:
		case 2551:
		case 2562:
		case 2563:
		case 6203:
		case 6222:
		case 6247:
			return 5;
		case 6261://bandos
		case 6265:
		case 6263:
		case 6204://zammy
		case 6206:
		case 6208:
		case 6250://sara
		case 6248:
		case 6252:
		case 6225://arma
		case 6227:
			return 2;
		//case 3847:
		//case 3943:
			//return 1;
		case 2883:
			return 4;
		case 2881:
		case 2882:
			return 1;
		case 6218:
			return 25;

		}
		return 0;

	}

	public int getProjectileSpeed(int i) {
		switch (npcs[i].npcType) {
		case 2881:
		case 2882:
		case 3200:
			return 85;

		case 6222:
			return 105;

		case 2745:
			return 130;

		case 50:
		case 5363:
		case 1158:
		case 1160:
			return 90;

		case 6218:
			return 105;

		case 2025:
			return 85;

		case 2028:
			return 80;

		default:
			return 85;
		}
	}

	/**
	 * NPC Attacking Player
	 **/
	 

	public void attackPlayer(Client c, int i) {
		if (npcs[i].lastX != npcs[i].getX() || npcs[i].lastY != npcs[i].getY()) {
			return;
		}
		if (npcs[i] != null) {
			if (npcs[i].isDead)
				return;
			if (!npcs[i].inMulti() && npcs[i].underAttackBy > 0
					&& npcs[i].underAttackBy != c.playerId) {
				npcs[i].killerId = 0;
				return;
			}
			if (!npcs[i].inMulti()
					&& (c.underAttackBy > 0 || (c.underAttackBy2 > 0 && c.underAttackBy2 != i))) {
				npcs[i].killerId = 0;
				return;
			}
			if (npcs[i].heightLevel != c.heightLevel) {
				npcs[i].killerId = 0;
				return;
			}
			npcs[i].facePlayer(c.playerId);
			boolean special = false;// specialCase(c,i);
			if (goodDistance(npcs[i].getX(), npcs[i].getY(), c.getX(),
					c.getY(), distanceRequired(i)) || special) {
				if (c.respawnTimer <= 0) {
					npcs[i].facePlayer(c.playerId);
					npcs[i].attackTimer = getNpcDelay(i);
					npcs[i].hitDelayTimer = getHitDelay(i);
					npcs[i].attackType = 0;
					if (special)
						loadSpell2(i);
					else
						loadSpell(i);
					if (npcs[i].attackType == 3)
						npcs[i].hitDelayTimer += 2;
					if (multiAttacks(i)) {
						multiAttackGfx(i, npcs[i].projectileId);
						startAnimation(getAttackEmote(i), i);
						npcs[i].oldIndex = c.playerId;
						return;
					}
					if (npcs[i].projectileId > 0) {
						int nX = NPCHandler.npcs[i].getX() + offset(i);
						int nY = NPCHandler.npcs[i].getY() + offset(i);
						int pX = c.getX();
						int pY = c.getY();
						int offX = (nY - pY) * -1;
						int offY = (nX - pX) * -1;
						c.getPA().createPlayersProjectile(nX, nY, offX, offY,
								50, getProjectileSpeed(i),
								npcs[i].projectileId, 43, 31, -c.getId() - 1,
								65);
					}
					c.underAttackBy2 = i;
					c.singleCombatDelay2 = System.currentTimeMillis();
					npcs[i].oldIndex = c.playerId;
					startAnimation(getAttackEmote(i), i);
					c.getPA().removeAllWindows();
					if (c.teleporting) {
						c.startAnimation(65535);
						c.teleporting = false;
						c.isRunning = false;
						c.gfx0(-1);
						c.startAnimation(-1);
					}
				}
			}
		}
	}

	public int offset(int i) {
		switch (npcs[i].npcType) {
		case 6218:
			return 2;
		case 1158:
		case 1160:
			return 2;
		case 50:
		case 5363:
			return 2;
		case 2881:
		case 2882:
			return 1;
		case 2745:
		case 2743:
			return 1;
		}
		return 0;
	}

	public boolean specialCase(Client c, int i) { // responsible for npcs that
													// much
		if (goodDistance(npcs[i].getX(), npcs[i].getY(), c.getX(), c.getY(), 8)
				&& !goodDistance(npcs[i].getX(), npcs[i].getY(), c.getX(),
						c.getY(), distanceRequired(i)))
			return true;
		return false;
	}

	public boolean retaliates(int npcType) {
		return npcType < 6142 || npcType > 6145
				&& !(npcType >= 2440 && npcType <= 2446);
	}

	public void applyDamage(int i) {
		if (npcs[i] != null) {
			if (PlayerHandler.players[npcs[i].oldIndex] == null) {
				return;
			}
			if (npcs[i].isDead)
				return;
			Client c = (Client) PlayerHandler.players[npcs[i].oldIndex];
			if (multiAttacks(i)) {
				multiAttackDamage(i);
				return;
			}
			if (c.playerIndex <= 0 && c.npcIndex <= 0)
				if (c.autoRet == 1)
					c.npcIndex = i;
			if (c.attackTimer <= 3 || c.attackTimer == 0 && c.npcIndex == 0 && c.oldNpcIndex == 0) {
				c.startAnimation(c.getCombat().getBlockEmote());
			}
			if (c.respawnTimer <= 0) {
				int damage = 0;
				if (npcs[i].attackType == 0) {
					damage = Misc.random(npcs[i].maxHit);
					if (10 + Misc.random(c.getCombat().calculateMeleeDefence()) > Misc
							.random(NPCHandler.npcs[i].attack)) {
						damage = 0;
					}
					if (c.prayerActive[18]) { // protect from melee
						if (npcs[i].npcType == 3499 || npcs[i].npcType == 3500) {
						damage = (damage / 2);
					} else 
					if (npcs[i].npcType == 1615) {
						damage = 0;
					} else
					if (npcs[i].npcType == 3502 || npcs[i].npcType == 3502) {
						damage = (damage / 2);
					} else
					if (npcs[i].npcType == 3847)  {
							damage = (damage / 2);
					} else
					if (npcs[i].npcType == 4056)  {
						damage = (damage / 2);
					} else
					if (npcs[i].npcType == 3943)  {
							damage = (damage / 2);
					} else
					if (npcs[i].npcType == 6247) {
						damage = (damage / 4);
					} else
					if (npcs[i].npcType == 6248) {
						damage = (damage / 2);
					} else
					if (npcs[i].npcType == 6250) {
						damage = (damage / 2);
					} else
					if (npcs[i].npcType == 6252) {
						damage = (damage / 2);
					} else
					if (npcs[i].npcType == 6203) {
						damage = (damage / 4);
					} else
					if (npcs[i].npcType == 6204) {
						damage = (damage / 2);
					} else
					if (npcs[i].npcType == 6206) {
						damage = (damage / 2);
					} else
					if (npcs[i].npcType == 6208) {
						damage = (damage / 4);
					} else
					if (npcs[i].npcType == 6260) {
						damage = (damage / 2);
					} else
					if (npcs[i].npcType == 6261) {
						damage = (damage / 2);
					} else
					if (npcs[i].npcType == 6263) {
						damage = (damage / 2);
					} else
					if (npcs[i].npcType == 6265) {
						damage = (damage / 2);
					} else 
						damage = 0;
					}
					if (c.playerLevel[3] - damage < 0) {
						damage = c.playerLevel[3];
					}
					
				}

				if (npcs[i].attackType == 1) { // range
					damage = Misc.random(npcs[i].maxHit);
					if (10 + Misc.random(c.getCombat().calculateRangeDefence()) > Misc
							.random(NPCHandler.npcs[i].attack)) {
						damage = 0;
					}
					if (c.prayerActive[17]) { // protect from range
					if (npcs[i].npcType == 6222) {
						damage = (damage / 4);
					}
					if (npcs[i].npcType == 3499 || npcs[i].npcType == 3500) {
						damage = (damage / 2);
					}
					if (npcs[i].npcType == 3502 || npcs[i].npcType == 3502) {
						damage = (damage / 2);
					}
					if (npcs[i].npcType == 4056)  {
						damage = (damage / 2);
					}
					if (npcs[i].npcType == 3847)  {
							damage = (damage / 2);
					}
					if (npcs[i].npcType == 3943)  {
							damage = (damage / 2);
					} else {
						damage = 0;
					}
					}
					if (c.playerLevel[3] - damage < 0) {
						damage = c.playerLevel[3];
					}
				}

				if (npcs[i].attackType == 2) { // magic
					damage = Misc.random(npcs[i].maxHit);
					boolean magicFailed = false;
					if (10 + Misc.random(c.getCombat().mageDef()) > Misc
							.random(NPCHandler.npcs[i].attack)) {
						damage = 0;
						magicFailed = true;
					}
					if (c.prayerActive[16]) { // protect from magic
						if (npcs[i].npcType == 3847)  {
							damage = (damage / 2);
						}
						if (npcs[i].npcType == 3499) {
						damage = (damage / 1);
						}
						if (npcs[i].npcType == 3502) {
						damage = (damage / 2);
						}
						if (npcs[i].npcType == 3501) {
							damage = (damage / 2);
						}
						if (npcs[i].npcType == 3500) {
							damage = (damage / 2);
						}
						if (npcs[i].npcType == 4056)  {
							damage = (damage / 2);
						}
						if (npcs[i].npcType == 3499)  {
							damage = (damage / 2);
						}
						if (npcs[i].npcType == 3943)  {
							damage = (damage / 2);
						} 
						if (npcs[i].npcType == 6222) {
							damage = (damage / 4);
					} else { 
						damage = 0;
						magicFailed = true;
						}
					}
					if (c.playerLevel[3] - damage < 0) {
						damage = c.playerLevel[3];
					}
					if (npcs[i].endGfx > 0
							&& (!magicFailed || isFightCaveNpc(i))) {
						c.gfx100(npcs[i].endGfx);
					} else {
						c.gfx100(85);
					}
				}

				if (npcs[i].attackType == 3) { //fire breath
					int anti = c.getPA().antiFire();
					if (anti == 0) {
						damage = Misc.random(30) + 10;
						c.sendMessage("You are badly burnt by the dragon fire!");
					}
					
					if(c.playerEquipment[c.playerShield] == 11284 && c.dfsCharge <= 49){
						c.dfsCharge += 1;
						c.gfx0(1164);
						c.startAnimation(6695);
						c.sendMessage("Your shield abosrbs the dragon's fire.");
					}
					if(c.playerEquipment[c.playerShield] == 11283 && c.dfsCharge <= 49){
						c.dfsCharge += 1;
						c.startAnimation(6696);
						c.gfx0(1164);
						c.sendMessage("Your shield abosrbs the dragon's fire.");
					}
					
					else if (anti == 1)
						damage = Misc.random(12);
					else if (anti == 2)
						damage = Misc.random(6);
					if (c.playerLevel[3] - damage < 0)
						damage = c.playerLevel[3];
					//c.gfx100(npcs[i].endGfx);
				}
				handleSpecialEffects(c, i, damage);
				c.logoutDelay = System.currentTimeMillis(); // logout delay
				// c.setHitDiff(damage);
				c.handleHitMask(damage);
				c.playerLevel[3] -= damage;
				c.getPA().refreshSkill(3);
				c.updateRequired = true;
				// c.setHitUpdateRequired(true);
			}
		}
	}

	public void handleSpecialEffects(Client c, int i, int damage) {
		if (npcs[i].npcType == 2892 || npcs[i].npcType == 2894) {
			if (damage > 0) {
				if (c != null) {
					if (c.playerLevel[5] > 0) {
						c.playerLevel[5]--;
						c.getPA().refreshSkill(5);
						c.getPA().appendPoison(12);
					}
				}
			}
		}

	}

	public static void startAnimation(int animId, int i) {
		npcs[i].animNumber = animId;
		npcs[i].animUpdateRequired = true;
		npcs[i].updateRequired = true;
	}

	public NPC[] getNPCs() {
		return npcs;
	}

	public boolean goodDistance(int objectX, int objectY, int playerX,
			int playerY, int distance) {
		return ((objectX - playerX <= distance && objectX - playerX >= -distance) && (objectY
				- playerY <= distance && objectY - playerY >= -distance));
	}

	public int getMaxHit(int i) {
		switch (npcs[i].npcType) {
		case 3068:
			if (npcs[i].attackType == 0)
				return 18;
			else
				return 50;
			case 3499:
			if (npcs[i].attackType == 1)
			return 58;
			else
			if (npcs[i].attackType == 0)
			return 60;
		case 6222:
			if (npcs[i].attackType == 1)
				return 71;
			else
				return 21;
		case 3847:
			if (npcs[i].attackType == 1)
		return 37;
		case 3943:
			if (npcs[i].attackType == 2)
			return 40;
		case 2558:
			if (npcs[i].attackType == 2)
				return 28;
			else
				return 68;
		case 1158:
		case 1160:
			return 31;
		case 6218:
			if (npcs[i].attackType == 2)
				return 69;
			if (npcs[i].attackType == 1)
				return 58;
			if (npcs[i].attackType == 0)
				return 51;
		case 2562:
			return 31;
		case 6260:
			return 36;
		}
		return 1;
	}

	public boolean loadAutoSpawn(String FileName) {
		String line = "";
		String token = "";
		String token2 = "";
		String token2_2 = "";
		String[] token3 = new String[10];
		boolean EndOfFile = false;
		int ReadMode = 0;
		BufferedReader characterfile = null;
		try {
			characterfile = new BufferedReader(new FileReader("./" + FileName));
		} catch (FileNotFoundException fileex) {
			Misc.println(FileName + ": file not found.");
			return false;
		}
		try {
			line = characterfile.readLine();
		} catch (IOException ioexception) {
			Misc.println(FileName + ": error loading file.");
			return false;
		}
		while (EndOfFile == false && line != null) {
			line = line.trim();
			int spot = line.indexOf("=");
			if (spot > -1) {
				token = line.substring(0, spot);
				token = token.trim();
				token2 = line.substring(spot + 1);
				token2 = token2.trim();
				token2_2 = token2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token3 = token2_2.split("\t");
				if (token.equals("spawn")) {
					newNPC(Integer.parseInt(token3[0]),
							Integer.parseInt(token3[1]),
							Integer.parseInt(token3[2]),
							Integer.parseInt(token3[3]),
							Integer.parseInt(token3[4]),
							getNpcListHP(Integer.parseInt(token3[0])),
							Integer.parseInt(token3[5]),
							Integer.parseInt(token3[6]),
							Integer.parseInt(token3[7]));

				}
			} else {
				if (line.equals("[ENDOFSPAWNLIST]")) {
					try {
						characterfile.close();
					} catch (IOException ioexception) {
					}
					return true;
				}
			}
			try {
				line = characterfile.readLine();
			} catch (IOException ioexception1) {
				EndOfFile = true;
			}
		}
		try {
			characterfile.close();
		} catch (IOException ioexception) {
		}
		return false;
	}

	public int getNpcListHP(int npcId) {
		if (npcId <= -1) {
			return 0;
		}
		if (NPCDefinitions.getDefinitions()[npcId] == null) {
			return 0;
		}
		return NPCDefinitions.getDefinitions()[npcId].getNpcHealth();

	}

	public String getNpcListName(int npcId) {
		if (npcId <= -1) {
			return "None";
		}
		if (NPCDefinitions.getDefinitions()[npcId] == null) {
			return "None";
		}
		return NPCDefinitions.getDefinitions()[npcId].getNpcName();
	}

	public boolean loadNPCList(String FileName) {
		String line = "";
		String token = "";
		String token2 = "";
		String token2_2 = "";
		String[] token3 = new String[10];
		boolean EndOfFile = false;
		int ReadMode = 0;
		BufferedReader characterfile = null;
		try {
			characterfile = new BufferedReader(new FileReader("./" + FileName));
		} catch (FileNotFoundException fileex) {
			Misc.println(FileName + ": file not found.");
			return false;
		}
		try {
			line = characterfile.readLine();
		} catch (IOException ioexception) {
			Misc.println(FileName + ": error loading file.");
			return false;
		}
		while (EndOfFile == false && line != null) {
			line = line.trim();
			int spot = line.indexOf("=");
			if (spot > -1) {
				token = line.substring(0, spot);
				token = token.trim();
				token2 = line.substring(spot + 1);
				token2 = token2.trim();
				token2_2 = token2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token3 = token2_2.split("\t");
				if (token.equals("npc")) {
					newNPCList(Integer.parseInt(token3[0]), token3[1],
							Integer.parseInt(token3[2]),
							Integer.parseInt(token3[3]));
				}
			} else {
				if (line.equals("[ENDOFNPCLIST]")) {
					try {
						characterfile.close();
					} catch (IOException ioexception) {
					}
					return true;
				}
			}
			try {
				line = characterfile.readLine();
			} catch (IOException ioexception1) {
				EndOfFile = true;
			}
		}
		try {
			characterfile.close();
		} catch (IOException ioexception) {
		}
		return false;
	}

}