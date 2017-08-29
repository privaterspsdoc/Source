package main.model.players;

import main.Server;
import main.clip.region.Region;
import main.model.minigames.PestControl;
import main.model.minigames.Sailing;
import main.model.npcs.pet.Pet;
import main.model.objects.Object;
import main.model.objects.Objects;
import main.model.players.skills.Fishing;
import main.model.players.skills.Mining;
import main.model.players.skills.Runecrafting;
import main.model.players.skills.Thieving;
import main.model.players.skills.Woodcutting;
import main.util.Misc;
import main.util.ScriptManager;

public class ActionHandler {

	private Client c;

	public ActionHandler(Client Client) {
		this.c = Client;
	}

	public void firstClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		if (Woodcutting.playerTrees(c, objectType)) {
			Woodcutting.attemptData(c, objectType, obX, obY);
			return;
		}
		if (Mining.miningRocks(c, objectType)) {
			Mining.attemptData(c, objectType, obX, obY);
			return;
		}
		switch (objectType) {
		case 26969:
			c.getPA().sendFrame126("The Bank of Zaros - Deposit Box", 7421);
			c.getPA().sendFrame248(4465, 197);
			c.getItems().resetItems(7423);
		break;
		case 7050:
		if (c.absX >= 3053 && c.absX <= 3059 && c.absY >= 3284 && c.absY <= 3287) {
			c.getPA().movePlayer(3053, 3283, 0);
		} else if (c.absX >= 3053 && c.absX <= 3059 && c.absY <= 3283 && c.absX <= 3279) {
			c.getPA().movePlayer(3053, 3284, 0);
		}
		break;
		

		
		case 7049:
		if (c.absX <= 3052 && c.absX >= 3046 && c.absY <= 3283 && c.absY >= 3280) {
			c.getPA().movePlayer(3052, 3284, 0);
		} else if (c.absX <= 3052 && c.absX >= 3046 && c.absY >= 3284 && c.absX <= 3287) {
			c.getPA().movePlayer(3052, 3283, 0);
		}

		break;
		
		case 2623:
			if (c.absX <= 2923 && c.absX >= 2918 && c.absY <= 9804 && c.absY >= 9800) {
				c.getPA().movePlayer(2924, 9803, 0);
			} else if (c.absX >= 2924 && c.absX <= 2928  && c.absY <= 9804 && c.absY >= 9800) {
				c.getPA().movePlayer(2923, 9803, 0);
			}
		break;
		case 10596:
		c.sendMessage("You are now back in the mithril dragons area.");
		c.getPA().movePlayer(3056, 9555, 0);
		if (c.absX == 3056 && c.absY == 9562)
		c.getPA().movePlayer(3056, 9555, 0);
		c.sendMessage("You are now back in the mithril dragons area.");
		if (c.absX == 3055 && c.absY == 9562)
		c.getPA().movePlayer(3056, 9555, 0);
		c.sendMessage("You are now back in the mithril dragons area.");
		break;
		case 10595:
		c.getPA().movePlayer(3056, 9562, 0);
		c.sendMessage("Head north to reach the skeletal wyverns.");
		break;
		case 15638:
			if (c.absX == 2840 && c.absY == 3539) {
				c.getPA().movePlayer(2839, 3539, 0);
			}
		break;
		
		case 1738:
			if (c.absX >= 2838 && c.absX <= 2842 && c.absY >= 3536 && c.absY <= 3540 ) {
				c.getPA().movePlayer(2840, 3539, 2);
			} else 
				c.sendMessage("Try going to the furthest away side of the stairs they do work!");
		break;
			
		case 4493:
			c.getPA().movePlayer(3433, 3538, 1);
			break;
		case 4494:
			c.getPA().movePlayer(3438, 3537, 0);
		case 4495:
			c.getPA().movePlayer(3417, 3540, 2);
		case 4496:
			c.getPA().movePlayer(3412, 3540, 1);
		case 15641:
			if (c.absX >= 2847 && c.absX <= 2850  && c.absY <= 3542 && c.absY >= 3537) {
				c.getPA().movePlayer(2846, 3540, 2);
			}
			if (c.absX >= 2851 && c.absX <= 2857 && c.absY >= 3546 && c.absY <= 3547) {
				c.getPA().movePlayer(2854, 3545, 0);
			}
			if (c.absX >= 2851 && c.absX <= 2857 && c.absY <= 3545) {
				c.getPA().movePlayer(2854, 3546, 0);
			}
		break;
		case 15644:
			if (c.absX >= 2847 && c.absX <= 2850  && c.absY <= 3542 && c.absY >= 3537) {
				c.getPA().movePlayer(2846, 3541, 2);
			}
			if (c.absX >= 2851 && c.absX <= 2857 && c.absY >= 3546 && c.absY <= 3547) {
				c.getPA().movePlayer(2855, 3545, 0);
			}
			if (c.absX >= 2851 && c.absX <= 2857 && c.absY <= 3545) {
				c.getPA().movePlayer(2855, 3546, 0);
			}
		break;
		case 14235:
			if (c.objectX == 2657 && c.objectY == 2585) {
				Objects o = new Objects(14233, 2656, 2584, 0, 0, 0, 0);
		        Server.objectHandler.placeObject2(o);
		        Objects o2 = new Objects(14235, 2657, 2584, 0, 2, 0, 0);
		        Server.objectHandler.placeObject2(o2);
				Region.removeClipping(2656, 2584, 0);
				Region.removeClipping(2657, 2584, 0);
				Region.removeClipping(2656, 2585, 0);
				Region.removeClipping(2657, 2585, 0);
		        Objects o3 = new Objects(-1, 2656, 2585, 0, 2, 0, 0);
		        Server.objectHandler.placeObject2(o3);
		        Objects o4 = new Objects(-1, 2657, 2585, 0, 0, 0, 0);
		        Server.objectHandler.placeObject2(o4);
			} else 	if (c.objectX == 2657 && c.objectY == 2584) {
				Region.addObject(14233, 2656, 2585, 0, 0, 3);
		        Region.addObject(14235, 2657, 2585, 0, 0, 3);
				Objects o = new Objects(-1, 2656, 2584, 0, 0, 0, 0);
		        Server.objectHandler.placeObject2(o);
		        Objects o2 = new Objects(-1, 2657, 2584, 0, 2, 0, 0);
		        Server.objectHandler.placeObject2(o2);
		        Objects o3 = new Objects(14233, 2656, 2585, 0, 3, 0, 0);
		        Server.objectHandler.placeObject2(o3);
		        Objects o4 = new Objects(14235, 2657, 2585, 0, 3, 0, 0);
		        Server.objectHandler.placeObject2(o4);
			} else if (c.objectX == 2643 && c.objectY == 2592) {
				Objects o = new Objects(14233, 2642, 2593, 0, 1, 0, 0);
		        Server.objectHandler.placeObject2(o);
		        Objects o2 = new Objects(14235, 2642, 2592, 0, 3, 0, 0);
		        Server.objectHandler.placeObject2(o2);
				Region.removeClipping(2642, 2593, 0);
				Region.removeClipping(2642, 2592, 0);
				Region.removeClipping(2643, 2593, 0);
				Region.removeClipping(2643, 2592, 0);
		        Objects o3 = new Objects(-1, 2643, 2593, 0, 2, 0, 0);
		        Server.objectHandler.placeObject2(o3);
		        Objects o4 = new Objects(-1, 2643, 2592, 0, 0, 0, 0);
		        Server.objectHandler.placeObject2(o4);
			} else 	if (c.objectX == 2642 && c.objectY == 2592) {
				Region.addObject(14233, 2643, 2593, 0, 0, 0);
		        Region.addObject(14235, 2643, 2592, 0, 0, 0);
		        Objects o = new Objects(-1, 2642, 2593, 0, 1, 0, 0);
		        Server.objectHandler.placeObject2(o);
		        Objects o2 = new Objects(-1, 2642, 2592, 0, 3, 0, 0);
		        Server.objectHandler.placeObject2(o2);
		        Objects o3 = new Objects(14233, 2643, 2593, 0, 0, 0, 0);
		        Server.objectHandler.placeObject2(o3);
		        Objects o4 = new Objects(14235, 2643, 2592, 0, 0, 0, 0);
		        Server.objectHandler.placeObject2(o4);
			} else if (c.objectX == 2670 && c.objectY == 2593) {
				Objects o = new Objects(14233, 2671, 2592, 0, 3, 0, 0);
		        Server.objectHandler.placeObject2(o);
		        Objects o2 = new Objects(14235, 2671, 2593, 0, 1, 0, 0);
		        Server.objectHandler.placeObject2(o2);
				Region.removeClipping(2670, 2593, 0);
				Region.removeClipping(2670, 2592, 0);
				Region.removeClipping(2671, 2593, 0);
				Region.removeClipping(2671, 2592, 0);
		        Objects o3 = new Objects(-1, 2670, 2592, 0, 2, 0, 0);
		        Server.objectHandler.placeObject2(o3);
		        Objects o4 = new Objects(-1, 2670, 2593, 0, 0, 0, 0);
		        Server.objectHandler.placeObject2(o4);
			} else 	if (c.objectX == 2671 && c.objectY == 2593) {
				Region.addObject(14235, 2670, 2593, 0, 0, 2);
		        Region.addObject(14233, 2670, 2592, 0, 0, 2);
		        Objects o = new Objects(-1, 2671, 2593, 0, 1, 0, 0);
		        Server.objectHandler.placeObject2(o);
		        Objects o2 = new Objects(-1, 2671, 2592, 0, 3, 0, 0);
		        Server.objectHandler.placeObject2(o2);
		        Objects o3 = new Objects(14235, 2670, 2593, 0, 2, 0, 0);
		        Server.objectHandler.placeObject2(o3);
		        Objects o4 = new Objects(14233, 2670, 2592, 0, 2, 0, 0);
		        Server.objectHandler.placeObject2(o4);
			}
			break;
		case 14233:
			if (c.objectX == 2656 && c.objectY == 2585) {
				Objects o = new Objects(14233, 2656, 2584, 0, 0, 0, 0);
		        Server.objectHandler.placeObject2(o);
		        Objects o2 = new Objects(14235, 2657, 2584, 0, 2, 0, 0);
		        Server.objectHandler.placeObject2(o2);
				Region.removeClipping(2656, 2584, 0);
				Region.removeClipping(2657, 2584, 0);
				Region.removeClipping(2656, 2585, 0);
				Region.removeClipping(2657, 2585, 0);
		        Objects o3 = new Objects(-1, 2656, 2585, 0, 2, 0, 0);
		        Server.objectHandler.placeObject2(o3);
		        Objects o4 = new Objects(-1, 2657, 2585, 0, 0, 0, 0);
		        Server.objectHandler.placeObject2(o4);
			} else 	if (c.objectX == 2656 && c.objectY == 2584) {
				Region.addObject(14233, 2656, 2585, 0, 0, 0);
		        Region.addObject(14235, 2657, 2585, 0, 0, 0);
				Objects o = new Objects(-1, 2656, 2584, 0, 0, 0, 0);
		        Server.objectHandler.placeObject2(o);
		        Objects o2 = new Objects(-1, 2657, 2584, 0, 2, 0, 0);
		        Server.objectHandler.placeObject2(o2);
		        Objects o3 = new Objects(14233, 2656, 2585, 0, 3, 0, 0);
		        Server.objectHandler.placeObject2(o3);
		        Objects o4 = new Objects(14235, 2657, 2585, 0, 3, 0, 0);
		        Server.objectHandler.placeObject2(o4);
			} else if (c.objectX == 2643 && c.objectY == 2593) {
				Objects o = new Objects(14233, 2642, 2593, 0, 1, 0, 0);
		        Server.objectHandler.placeObject2(o);
		        Objects o2 = new Objects(14235, 2642, 2592, 0, 3, 0, 0);
		        Server.objectHandler.placeObject2(o2);
				Region.removeClipping(2642, 2593, 0);
				Region.removeClipping(2642, 2592, 0);
				Region.removeClipping(2643, 2593, 0);
				Region.removeClipping(2643, 2592, 0);
		        Objects o3 = new Objects(-1, 2643, 2593, 0, 2, 0, 0);
		        Server.objectHandler.placeObject2(o3);
		        Objects o4 = new Objects(-1, 2643, 2592, 0, 0, 0, 0);
		        Server.objectHandler.placeObject2(o4);
			} else 	if (c.objectX == 2642 && c.objectY == 2593) {
				Region.addObject(14233, 2643, 2593, 0, 0, 0);
		        Region.addObject(14235, 2643, 2592, 0, 0, 0);
		        Objects o = new Objects(-1, 2642, 2593, 0, 1, 0, 0);
		        Server.objectHandler.placeObject2(o);
		        Objects o2 = new Objects(-1, 2642, 2592, 0, 3, 0, 0);
		        Server.objectHandler.placeObject2(o2);
		        Objects o3 = new Objects(14233, 2643, 2593, 0, 0, 0, 0);
		        Server.objectHandler.placeObject2(o3);
		        Objects o4 = new Objects(14235, 2643, 2592, 0, 0, 0, 0);
		        Server.objectHandler.placeObject2(o4);
			} else if (c.objectX == 2670 && c.objectY == 2592) {
				Objects o = new Objects(14233, 2671, 2592, 0, 3, 0, 0);
		        Server.objectHandler.placeObject2(o);
		        Objects o2 = new Objects(14235, 2671, 2593, 0, 1, 0, 0);
		        Server.objectHandler.placeObject2(o2);
				Region.removeClipping(2670, 2593, 0);
				Region.removeClipping(2670, 2592, 0);
				Region.removeClipping(2671, 2593, 0);
				Region.removeClipping(2671, 2592, 0);
		        Objects o3 = new Objects(-1, 2670, 2592, 0, 2, 0, 0);
		        Server.objectHandler.placeObject2(o3);
		        Objects o4 = new Objects(-1, 2670, 2593, 0, 0, 0, 0);
		        Server.objectHandler.placeObject2(o4);
			} else 	if (c.objectX == 2671 && c.objectY == 2592) {
				Region.addObject(14235, 2670, 2593, 0, 0, 2);
		        Region.addObject(14233, 2670, 2592, 0, 0, 2);
		        Objects o = new Objects(-1, 2671, 2593, 0, 1, 0, 0);
		        Server.objectHandler.placeObject2(o);
		        Objects o2 = new Objects(-1, 2671, 2592, 0, 3, 0, 0);
		        Server.objectHandler.placeObject2(o2);
		        Objects o3 = new Objects(14235, 2670, 2593, 0, 2, 0, 0);
		        Server.objectHandler.placeObject2(o3);
		        Objects o4 = new Objects(14233, 2670, 2592, 0, 2, 0, 0);
		        Server.objectHandler.placeObject2(o4);
			}
		break;
		case 14296:
			if (c.objectX == 2644 && c.objectY == 2601) {
				if (c.absX == 2644 || c.absX == 2645) {
					c.getPA().climbLadder(true, 2643, 2601, 0);
				} else if (c.absX == 2643) {
					c.getPA().climbLadder(false, 2645, 2601, 0);
				}
			} else if (c.objectX == 2647 && c.objectY == 2586) {
				if (c.absY == 2587 || c.absY == 2586) {
					c.getPA().climbLadder(true, 2647, 2585, 0);
				} else if (c.absY == 2585) {
					c.getPA().climbLadder(false, 2647, 2587, 0);
				}
			} else if (c.objectX == 2666 && c.objectY == 2586) {
				if (c.absY == 2587 || c.absY == 2586) {
					c.getPA().climbLadder(true, 2666, 2585, 0);
				} else if (c.absY == 2585) {
					c.getPA().climbLadder(false, 2666, 2587, 0);
				}
			} else if (c.objectX == 2669 && c.objectY == 2601) {
				if (c.absX == 2668 || c.absX == 2669) {
					c.getPA().climbLadder(true, 2670, 2601, 0);
				} else if (c.absX == 2670) {
					c.getPA().climbLadder(false, 2668, 2601, 0);
				}
			}
			break;
		case 10230:
			c.getPA().movePlayer(2900, 4449, 0);
			break;
		case 10229:
			c.getPA().movePlayer(1912, 4367, 0);
			break;
		case 2285:
			if (c.doneNet1 == false) {
			c.getAgility().agilityCourse(c, 2285);
			c.doneNet1 = true;
		} else {
			c.sendMessage("You have already completed this obstacle");
			return;
		}
		break;
		case 2295:
			if (c.doneLog == false) {
				if (c.absX == 2474 && c.absY == 3436) {
			c.getAgility().agilityCourse(c, 2295);
			//c.doneLog = true;
		} else {
			c.sendMessage("You have already completed this obstacle");
			return;
			}
		}
		
		break;
		case 2313:
			if (c.doneBranch1 == false) {
			c.getAgility().agilityCourse(c, 2313);
			//c.doneBranch1 = true;
		} else {
			c.sendMessage("You have already completed this obstacle");
			return;
		}
		break;
		case 2312:
			if (c.doneRope == false) {
				if (c.absX == 2477 && c.absY == 3420) {
					c.getAgility().agilityCourse(c, 2312);
					//c.doneRope = true;
		} else {
			c.sendMessage("You have already completed this obstacle");
			return;
		}
		}
		break;
		case 2314:
			if (c.doneBranch2 == false) {
			c.getAgility().agilityCourse(c, 2314);
			//c.doneBranch2 = true;
		} else {
			c.sendMessage("You have already completed this obstacle");
			return;
		}
		break;
		case 2286:
			if (c.doneNet2 == false) {
			c.doneNet2 = true;
			c.getAgility().agilityCourse(c, 2286);
		} else {
			c.sendMessage("You have already completed this obstacle");
			return;
		}
		break;
		case 154:
		if (c.absX == 2483 && c.absY == 3431) {
			c.sendMessage("You're not in the right position to use the tunnel.");
			return;
		} else if (c.absX == 2485 && c.absY == 3431) {
			c.sendMessage("You're not in the right position to use the tunnel.");
			return;
		} else {
		if (System.currentTimeMillis() - c.lastTunnelClick > 2000) { //If the lastCommand timer is greater than 30,000 milliseconds (30 Seconds)
            c.lastTunnelClick = System.currentTimeMillis(); //Resets the lastCommand timer
           // c.sendMessage("You may click the tunnel every second."); //Sends a message
		if (c.doneTunnel == false) { 
			//c.doneTunnel = true;
			c.getAgility().agilityCourse(c, 154);
			}
		} else {
			//c.sendMessage("You have clicking this object less than 20 seconds ago.");
			return;
		}
		}
		break;
		case 4085:
		if (c.doneTunnel == false) { 
			//c.doneTunnel = true;
			c.getAgility().agilityCourse(c, 4085);
		} else {
			c.sendMessage("You have already completed this obstacle");
			return;
		}
		break;
		/*case 6163:
			Thieving.stealFromStall(c, 1897, 1, 18, 1, objectType, obX,
					obY, 2);
			break;
		case 6165:
			Thieving.stealFromStall(c, 950, 1, 54, 25, objectType, obX,
					obY, 0);
			break;
		case 6166:
			Thieving.stealFromStall(c, 1635, 1, 108, 50, objectType, obX,
					obY, 0);
			break;
		case 6164:
			Thieving.stealFromStall(c, 7650, 1, 180, 75, objectType, obX,
					obY, 3);
			break;
		case 6162:
			Thieving.stealFromStall(c, 1664, 1, 306, 93, objectType, obX, obY,
					obX == 2662 ? 2 : 1);
			break;*/
		case 1733:
			c.getPA().movePlayer(c.absX, c.absY + 6393, 0);
			break;

		case 1734:
			c.getPA().movePlayer(c.absX, c.absY - 6396, 0);
			break;
		case 14315:
			c.getPA().movePlayer(2661, 2639, 0);
			break;
		case 14314:
			c.getPA().movePlayer(2657, 2639, 0);
			break;
		case 245:
			this.c.getPA().movePlayer(this.c.absX, this.c.absY + 2, 2);
			break;
		case 246:
			this.c.getPA().movePlayer(this.c.absX, this.c.absY - 2, 1);
			break;
		case 272:
			this.c.getPA().movePlayer(this.c.absX, this.c.absY, 1);
			break;
		case 273:
			this.c.getPA().movePlayer(this.c.absX, this.c.absY, 0);
			break;
		
		// barrows
		// Chest
	      case 10284:
	          if(this.c.barrowsKillCount < 5) {
	             this.c.sendMessage("You haven\'t killed all the brothers");
	          }

	          if(this.c.barrowsKillCount == 5 && this.c.barrowsNpcs[this.c.randomCoffin][1] == 1) {
	             this.c.sendMessage("I have already summoned this npc.");
	          }

	          if(this.c.barrowsNpcs[this.c.randomCoffin][1] == 0 && this.c.barrowsKillCount == 5) {
	             Server.npcHandler.spawnNpc(this.c, this.c.barrowsNpcs[this.c.randomCoffin][0], 3551, 9693, 0, 0, 120, 30, 200, 200, true, true);
	             this.c.barrowsNpcs[this.c.randomCoffin][1] = 1;
	          }

	          if((this.c.barrowsKillCount > 5 || this.c.barrowsNpcs[this.c.randomCoffin][1] == 2) && this.c.getItems().freeSlots() >= 2) {
	             this.c.getPA().resetBarrows();
	             this.c.getItems().addItem(this.c.getPA().randomRunes(), Misc.random(100) + 100);
	             if(Misc.random(2) == 1) {
	                this.c.getItems().addItem(this.c.getPA().randomBarrows(), 1, "Adding Barrows");
	             }

	             this.c.getPA().startTeleport(3564, 3288, 0, "modern");
	          } else if(this.c.barrowsKillCount > 5 && this.c.getItems().freeSlots() <= 1) {
	             this.c.sendMessage("You need at least 2 inventory slot opened.");
	          }
	          break;
		// door

		case 6707: // verac
			c.getPA().movePlayer(3556, 3298, 0);
			break;

		case 6706: // torag
			c.getPA().movePlayer(3553, 3283, 0);
			break;

		case 6705: // karil stairs
			c.getPA().movePlayer(3565, 3276, 0);
			break;
	
		case 6704: // guthan stairs
			c.getPA().movePlayer(3578, 3284, 0);
			break;

		case 6703: // dharok stairs
			c.getPA().movePlayer(3574, 3298, 0);
			break;

		case 6702: // ahrim stairs
			c.getPA().movePlayer(3565, 3290, 0);
			break;
		case 6823:
			if(c.barrowsNpcs[0][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2030, c.getX(), c.getY()-1, 3, 0, 120, 25, 200, 200, true, true);
				c.barrowsNpcs[0][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;
			
		case 6772:
			if(c.barrowsNpcs[1][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2029, c.getX()+1, c.getY(), 3, 0, 120, 20, 200, 200, true, true);
				c.barrowsNpcs[1][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;
			
		case 6822:
			if(c.barrowsNpcs[2][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2028, c.getX(), c.getY()-1, 3, 0, 90, 17, 200, 200, true, true);
				c.barrowsNpcs[2][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;
			
		case 6773:
			if(c.barrowsNpcs[3][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2027, c.getX(), c.getY()-1, 3, 0, 120, 23, 200, 200, true, true);
				c.barrowsNpcs[3][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;
			
		case 6771:
			if(c.barrowsNpcs[4][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2026, c.getX()-1, c.getY(), 3, 0, 90, 19, 200, 200, true, true);
				c.barrowsNpcs[4][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			if (c.barrowsKillCount >= 5)
				c.getDH().sendDialogues(29, 2026);
			break;
			
		case 6821:
			if(c.barrowsNpcs[5][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2025, c.getX()-1, c.getY(), 3, 0, 90, 19, 200, 200, true, true);
				c.barrowsNpcs[5][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;

		
		/* Godwars Door */
		case 26426: // armadyl
			if (c.absX == 2839 && c.absY == 5295) {
				c.getPA().movePlayer(2839, 5296, 2);
			} else {
				c.getPA().movePlayer(2839, 5295, 2);
			}
			break;
		case 26425: // bandos
			if (c.absX == 2863 && c.absY == 5354) {
				c.getPA().movePlayer(2864, 5354, 2);
			} else {
				c.getPA().movePlayer(2863, 5354, 2);
			}
			break;
		case 26428: // bandos
			if (c.absX == 2925 && c.absY == 5332) {
				c.getPA().movePlayer(2925, 5331, 2);
			} else {
				c.getPA().movePlayer(2925, 5332, 2);
			}
			break;
		case 26427: // bandos
			if (c.absX == 2908 && c.absY == 5265) {
				c.getPA().movePlayer(2907, 5265, 0);
			} else {
				c.getPA().movePlayer(2908, 5265, 0);
			}
			break;

		case 5960:
			if (c.leverClicked == false) {
				c.getDH().sendDialogues(114, 9985);
				c.leverClicked = true;
			} else {
				c.getPA().startLeverTeleport(3090, 3956, 0, "lever");
			}
			break;
		case 3223:
		case 21764:
		case 411:
			if(c.specAmount < 10.0){
				if (c.specRestore > 0) return;
				c.specRestore = 600;
				c.specAmount = 10.0;
				c.getItems().addSpecialBar(c.playerEquipment[c.playerWeapon]);
				c.sendMessage("Your special attack has been restored, only every 10 minutes you can do this.");
			}
			if (c.playerLevel[5] < c.getPA().getLevelForXP(c.playerXP[5])) {
				c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]);
				c.getPA().refreshSkill(5);
			}
			c.playerLevel[3] = c.getPA().getLevelForXP(c.playerXP[3]);
			c.getPA().refreshSkill(3);
			c.startAnimation(645);
			c.specAmount = 10.0;
			c.getItems().addSpecialBar(c.playerEquipment[c.playerWeapon]);
			c.sendMessage("@red@You've recharged your special attack, prayer points and hitpoints.");
			break;
		case 5959:
			c.getPA().startLeverTeleport(2539, 4712, 0, "lever");
			break;
		case 1814:
			this.c.getPA().startLeverTeleport(3153, 3923, 0, "lever");
			break;
		case 1815:
			this.c.getPA().startLeverTeleport(3087, 3500, 0, "lever");
			break;
		/* Start Brimhavem Dungeon */
		case 2879:
			c.getPA().movePlayer(2542, 4718, 0);
			break;
		case 2878:
			c.getPA().movePlayer(2509, 4689, 0);
			break;
		case 5083:
			c.getPA().movePlayer(2713, 9564, 0);
			c.sendMessage("You enter the dungeon.");
			break;

		case 5103:
			if (c.absX == 2691 && c.absY == 9564) {
				c.getPA().movePlayer(2689, 9564, 0);
			} else if (c.absX == 2689 && c.absY == 9564) {
				c.getPA().movePlayer(2691, 9564, 0);
			}
			break;

		case 5106:
			if (c.absX == 2674 && c.absY == 9479) {
				c.getPA().movePlayer(2676, 9479, 0);
			} else if (c.absX == 2676 && c.absY == 9479) {
				c.getPA().movePlayer(2674, 9479, 0);
			}
			break;

		case 5105:
			if (c.absX == 2672 && c.absY == 9499) {
				c.getPA().movePlayer(2674, 9499, 0);
			} else if (c.absX == 2674 && c.absY == 9499) {
				c.getPA().movePlayer(2672, 9499, 0);
			}
			break;

		case 5107:
			if (c.absX == 2693 && c.absY == 9482) {
				c.getPA().movePlayer(2695, 9482, 0);
			} else if (c.absX == 2695 && c.absY == 9482) {
				c.getPA().movePlayer(2693, 9482, 0);
			}
			break;

		case 5104:
			if (c.absX == 2683 && c.absY == 9568) {
				c.getPA().movePlayer(2683, 9570, 0);
			} else if (c.absX == 2683 && c.absY == 9570) {
				c.getPA().movePlayer(2683, 9568, 0);
			}
			break;

		case 5100:
			if (c.absY <= 9567) {
				c.getPA().movePlayer(2655, 9573, 0);
			} else if (c.absY >= 9572) {
				c.getPA().movePlayer(2655, 9566, 0);
			}
			break;

		case 5099:
			if (c.absY <= 9493) {
				c.getPA().movePlayer(2698, 9500, 0);
			} else if (c.absY >= 9499) {
				c.getPA().movePlayer(2698, 9492, 0);
			}
			break;

		case 5088:
			if (c.absX <= 2682) {
				c.getPA().movePlayer(2687, 9506, 0);
			} else if (c.absX >= 2687) {
				c.getPA().movePlayer(2682, 9506, 0);
			}
			break;

		case 5110:
			c.getPA().movePlayer(2647, 9557, 0);
			c.sendMessage("You cross the lava");
			break;

		case 5111:
			c.getPA().movePlayer(2649, 9562, 0);
			c.sendMessage("You cross the lava");
			break;

		case 5084:
			c.getPA().movePlayer(2744, 3151, 0);
			c.sendMessage("You exit the dungeon.");
			break;
		/* End Brimhavem Dungeon */
		case 6481:
			c.getPA().movePlayer(3233, 9315, 0);
			break;
		case 17010:
			if (c.playerMagicBook == 0) {
				c.sendMessage("You switch spellbook to lunar magic.");
				c.setSidebarInterface(6, 29999);
				c.playerMagicBook = 2;
				c.autocasting = false;
				c.autocastId = -1;
				c.getPA().resetAutocast();
				break;
			}
			if (c.playerMagicBook == 1) {
				c.sendMessage("You switch spellbook to lunar magic.");
				c.setSidebarInterface(6, 29999);
				c.playerMagicBook = 2;
				c.autocasting = false;
				c.autocastId = -1;
				c.getPA().resetAutocast();
				break;
			}
			if (c.playerMagicBook == 2) {
				c.setSidebarInterface(6, 1151);
				c.playerMagicBook = 0;
				c.autocasting = false;
				c.sendMessage("You feel a drain on your memory.");
				c.autocastId = -1;
				c.getPA().resetAutocast();
				break;
			}
			break;

		case 1551:
			if (c.absX == 3252 && c.absY == 3266) {
				c.getPA().movePlayer(3253, 3266, 0);
			}
			if (c.absX == 3253 && c.absY == 3266) {
				c.getPA().movePlayer(3252, 3266, 0);
			}
			break;
		case 1553:
			if (c.absX == 3252 && c.absY == 3267) {
				c.getPA().movePlayer(3253, 3266, 0);
			}
			if (c.absX == 3253 && c.absY == 3267) {
				c.getPA().movePlayer(3252, 3266, 0);
			}
			break;

		case 2491:
			Mining.mineEss(c, objectType);
			break;
		case 3044:
			c.getSmithing().sendSmelting();
			break;
		// abyss rifts
		case 7129: // fire riff
			Runecrafting.craftRunesOnAltar(c, 14, 7, 554, 50, 60, 70);
			break;
		case 7130: // earth riff
			Runecrafting.craftRunesOnAltar(c, 9, 7, 557, 45, 55, 65);
			break;
		case 7131: // body riff
			Runecrafting.craftRunesOnAltar(c, 20, 8, 559, 55, 65, 75);
			break;
		case 7132: // cosmic riff
			Runecrafting.craftRunesOnAltar(c, 35, 10, 564, 72, 84, 96);
			break;
		case 7133: // nat riff
			Runecrafting.craftRunesOnAltar(c, 44, 9, 561, 60, 74, 91);
			break;
		case 7134: // chaos riff
			Runecrafting.craftRunesOnAltar(c, 35, 9, 562, 60, 70, 80);
			break;
		case 7135: // law riff
			Runecrafting.craftRunesOnAltar(c, 54, 10, 563, 65, 79, 93);
			break;
		case 7136: // death riff
			Runecrafting.craftRunesOnAltar(c, 65, 10, 560, 72, 84, 96);
			break;
		case 7137: // water riff
			Runecrafting.craftRunesOnAltar(c, 5, 6, 555, 30, 45, 60);
			break;
		case 7138: // soul riff
			Runecrafting.craftRunesOnAltar(c, 65, 10, 566, 72, 84, 96);
			break;
		case 7139: // air riff
			Runecrafting.craftRunesOnAltar(c, 1, 5, 556, 30, 45, 60);
			break;
		case 7140: // mind riff
			Runecrafting.craftRunesOnAltar(c, 1, 5, 558, 30, 45, 60);
			break;
		case 7141: // blood rift
			Runecrafting.craftRunesOnAltar(c, 77, 11, 565, 89, 94, 99);
			break;

		/* AL KHARID */
		case 2883:
		case 2882:
			c.getDH().sendDialogues(1023, 925);
			break;
		case 2412:
			Sailing.startTravel(c, 1);
			break;
		case 2414:
			Sailing.startTravel(c, 2);
			break;
		case 2083:
			Sailing.startTravel(c, 5);
			break;
		case 2081:
			Sailing.startTravel(c, 6);
			break;
		case 14304:
			Sailing.startTravel(c, 14);
			break;
		case 14306:
			Sailing.startTravel(c, 15);
			break;

		case 2213:
		case 3045:
		case 14367:
		case 11758:
		case 3193:
		case 10517:
		case 11402:
		case 26972:
		case 4483:
			c.getPA().openUpBank();
			bankCheck = true;
			break;
			
		case 8143:
			c.getFarming().pickHerb();
		break;

		/**
		 * Entering the Fight Caves.
		 */
		case 9356:
			if (c.waveId > 0) {
				c.waveId = -1;
				c.sendMessage("Click on the cave again to play the minigame.");
				return;
			}
			c.getPA().enterCaves();
			break;

		/**
		 * Clicking on the Ancient Altar.
		 */
		case 6552:
			if (c.playerMagicBook == 0) {
				c.playerMagicBook = 1;
				c.setSidebarInterface(6, 12855);
				c.autocasting = false;
				c.sendMessage("An ancient wisdom fills your mind.");
				c.getPA().resetAutocast();
			} else if (c.playerMagicBook == 1) {
				c.playerMagicBook = 2;
				c.setSidebarInterface(6, 29999);
				c.autocasting = false;
				c.sendMessage("A lunar wisdom fills your mind.");
				c.getPA().resetAutocast();
			} else {
				c.setSidebarInterface(6, 1151);
				c.playerMagicBook = 0;
				c.autocasting = false;
				c.sendMessage("You feel a drain on your memory.");
				c.autocastId = -1;
				c.getPA().resetAutocast();
			}
			break;

		/**
		 * Recharing prayer points.
		 */
		case 409:
			if (c.playerLevel[5] < c.getPA().getLevelForXP(c.playerXP[5])) {
				c.startAnimation(645);
				c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]);
				c.sendMessage("You recharge your prayer points.");
				c.getPA().refreshSkill(5);
			} else {
				c.sendMessage("You already have full prayer points.");
			}
			break;

		/**
		 * Aquring god capes.
		 */
		case 2873:
			if (!c.getItems().ownsCape()) {
				c.startAnimation(645);
				c.sendMessage("Saradomin blesses you with a cape.");
				c.getItems().addItem(2412, 1);
			} else {
				c.sendMessage("You already have a cape");
			}
			break;
		case 2875:
			if (!c.getItems().ownsCape()) {
				c.startAnimation(645);
				c.sendMessage("Guthix blesses you with a cape.");
				c.getItems().addItem(2413, 1);
			} else {
				c.sendMessage("You already have a cape");
			}
			break;
		case 2874:
			if (!c.getItems().ownsCape()) {
				c.startAnimation(645);
				c.sendMessage("Zamorak blesses you with a cape.");
				c.getItems().addItem(2414, 1);
			} else {
				c.sendMessage("You already have a cape");
			}
			break;

		/**
		 * Oblisks in the wilderness.
		 */
		case 14829:
		case 14830:
		case 14827:
		case 14828:
		case 14826:
		case 14831:
			Server.objectManager.startObelisk(objectType);
			break;

		/**
		 * Clicking certain doors.
		 */
		case 6749:
			if (obX == 3562 && obY == 9678) {
				c.getPA().object(3562, 9678, 6749, -3, 0);
				c.getPA().object(3562, 9677, 6730, -1, 0);
			} else if (obX == 3558 && obY == 9677) {
				c.getPA().object(3558, 9677, 6749, -1, 0);
				c.getPA().object(3558, 9678, 6730, -3, 0);
			}
			break;

		case 6730:
			if (obX == 3558 && obY == 9677) {
				c.getPA().object(3562, 9678, 6749, -3, 0);
				c.getPA().object(3562, 9677, 6730, -1, 0);
			} else if (obX == 3558 && obY == 9678) {
				c.getPA().object(3558, 9677, 6749, -1, 0);
				c.getPA().object(3558, 9678, 6730, -3, 0);
			}
			break;

		case 6727:
			if (obX == 3551 && obY == 9684) {
				c.sendMessage("You cant open this door..");
			}
			break;

		case 6746:
			if (obX == 3552 && obY == 9684) {
				c.sendMessage("You cant open this door..");
			}
			break;

		case 6748:
			if (obX == 3545 && obY == 9678) {
				c.getPA().object(3545, 9678, 6748, -3, 0);
				c.getPA().object(3545, 9677, 6729, -1, 0);
			} else if (obX == 3541 && obY == 9677) {
				c.getPA().object(3541, 9677, 6748, -1, 0);
				c.getPA().object(3541, 9678, 6729, -3, 0);
			}
			break;

		case 6729:
			if (obX == 3545 && obY == 9677) {
				c.getPA().object(3545, 9678, 6748, -3, 0);
				c.getPA().object(3545, 9677, 6729, -1, 0);
			} else if (obX == 3541 && obY == 9678) {
				c.getPA().object(3541, 9677, 6748, -1, 0);
				c.getPA().object(3541, 9678, 6729, -3, 0);
			}
			break;

		case 6726:
			if (obX == 3534 && obY == 9684) {
				c.getPA().object(3534, 9684, 6726, -4, 0);
				c.getPA().object(3535, 9684, 6745, -2, 0);
			} else if (obX == 3535 && obY == 9688) {
				c.getPA().object(3535, 9688, 6726, -2, 0);
				c.getPA().object(3534, 9688, 6745, -4, 0);
			}
			break;

		case 6745:
			if (obX == 3535 && obY == 9684) {
				c.getPA().object(3534, 9684, 6726, -4, 0);
				c.getPA().object(3535, 9684, 6745, -2, 0);
			} else if (obX == 3534 && obY == 9688) {
				c.getPA().object(3535, 9688, 6726, -2, 0);
				c.getPA().object(3534, 9688, 6745, -4, 0);
			}
			break;

		case 6743:
			if (obX == 3545 && obY == 9695) {
				c.getPA().object(3545, 9694, 6724, -1, 0);
				c.getPA().object(3545, 9695, 6743, -3, 0);
			} else if (obX == 3541 && obY == 9694) {
				c.getPA().object(3541, 9694, 6724, -1, 0);
				c.getPA().object(3541, 9695, 6743, -3, 0);
			}
			break;

		case 6724:
			if (obX == 3545 && obY == 9694) {
				c.getPA().object(3545, 9694, 6724, -1, 0);
				c.getPA().object(3545, 9695, 6743, -3, 0);
			} else if (obX == 3541 && obY == 9695) {
				c.getPA().object(3541, 9694, 6724, -1, 0);
				c.getPA().object(3541, 9695, 6743, -3, 0);
			}
			break;

		case 1516:
		case 1519:
			if (c.objectY == 9698) {
				if (c.absY >= c.objectY)
					c.getPA().walkTo(0, -1);
				else
					c.getPA().walkTo(0, 1);
				break;
			}
		case 1530:
		case 1531:
		case 1533:
		case 1534:
		case 11712:
		case 11711:
		case 11707:
		case 11708:
		case 6725:
		case 3198:
		case 3197:
			Server.objectHandler.doorHandling(objectType, c.objectX, c.objectY,
					0);
			break;

		case 5126:
			if (c.absY == 3554)
				c.getPA().walkTo(0, 1);
			else
				c.getPA().walkTo(0, -1);
			break;

		case 1759:
			if (c.objectX == 2884 && c.objectY == 3397)
				c.getPA().movePlayer(c.absX, c.absY + 6400, 0);
			break;
		case 1558:
			if (c.absX == 3041 && c.absY == 10308) {
				c.getPA().movePlayer(3040, 10308, 0);
			} else if (c.absX == 3040 && c.absY == 10308) {
				c.getPA().movePlayer(3041, 10308, 0);
			} else if (c.absX == 3040 && c.absY == 10307) {
				c.getPA().movePlayer(3041, 10307, 0);
			} else if (c.absX == 3041 && c.absY == 10307) {
				c.getPA().movePlayer(3040, 10307, 0);
			} else if (c.absX == 3044 && c.absY == 10341) {
				c.getPA().movePlayer(3045, 10341, 0);
			} else if (c.absX == 3045 && c.absY == 10341) {
				c.getPA().movePlayer(3044, 10341, 0);
			} else if (c.absX == 3044 && c.absY == 10342) {
				c.getPA().movePlayer(3045, 10342, 0);
			} else if (c.absX == 3045 && c.absY == 10342) {
				c.getPA().movePlayer(3044, 10343, 0);
			}
			break;
		case 1557:
			if (c.absX == 3023 && c.absY == 10312) {
				c.getPA().movePlayer(3022, 10312, 0);
			} else if (c.absX == 3022 && c.absY == 10312) {
				c.getPA().movePlayer(3023, 10312, 0);
			} else if (c.absX == 3023 && c.absY == 10311) {
				c.getPA().movePlayer(3022, 10311, 0);
			} else if (c.absX == 3022 && c.absY == 10311) {
				c.getPA().movePlayer(3023, 10311, 0);
			}
			break;
		case 2558:
			c.sendMessage("This door is locked.");
			break;

		case 9294:
			if (c.absX < c.objectX) {
				c.getPA().movePlayer(c.objectX + 1, c.absY, 0);
			} else if (c.absX > c.objectX) {
				c.getPA().movePlayer(c.objectX - 1, c.absY, 0);
			}
			break;

		case 9293:
			if (c.absX < c.objectX) {
				c.getPA().movePlayer(2892, 9799, 0);
			} else {
				c.getPA().movePlayer(2886, 9799, 0);
			}
			break;

		case 10529:
		case 10527:
			if (c.absY <= c.objectY)
				c.getPA().walkTo(0, 1);
			else
				c.getPA().walkTo(0, -1);
			break;

		case 733:
			c.startAnimation(451);

			if (c.objectX == 3158 && c.objectY == 3951) {
				new Object(734, c.objectX, c.objectY, c.heightLevel, 1, 10,
						733, 50);
			} else {
				new Object(734, c.objectX, c.objectY, c.heightLevel, 0, 10,
						733, 50);
			}
			break;

		default:
			ScriptManager.callFunc("objectClick1_" + objectType, c, objectType,
					obX, obY);
			break;

		/**
		 * Forfeiting a duel.
		 */
		case 3203:
			c.sendMessage("Forfeiting has been disabled.");
			break;

		}
	}

	public void secondClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;				
		switch (objectType) {
		case 2558:
        case 2557:
            if (System.currentTimeMillis() - c.lastLockPick < 1000 || c.freezeTimer > 0)
            {
            	return;
            }
            c.lastLockPick = System.currentTimeMillis();
            if (c.getItems().playerHasItem(1523, 1))
            {
                
                if (Misc.random(10) <= 3)
                {
                    c.sendMessage("You fail to pick the lock.");
                    break;
                }
                if (c.objectX == 3044 && c.objectY == 3956)
                {
                    if (c.absX == 3045)
                    {
                        c.getPA().walkTo(-1, 0);
                    }
                    else if (c.absX == 3044)
                    {
                        c.getPA().walkTo(1, 0);
                    }

                }
                else if (c.objectX == 3038 && c.objectY == 3956)
                {
                    if (c.absX == 3037)
                    {
                        c.getPA().walkTo(1, 0);
                    }
                    else if (c.absX == 3038)
                    {
                        c.getPA().walkTo(-1, 0);
                    }
                }
                else if (c.objectX == 3041 && c.objectY == 3959)
                {
                    if (c.absY == 3960)
                    {
                        c.getPA().walkTo(0, -1);
                    }
                    else if (c.absY == 3959)
                    {
                        c.getPA().walkTo(0, 1);
                    }
                }
                else if (c.objectX == 3191 && c.objectY == 3963)
                {
                    if (c.absY == 3963)
                    {
                        c.getPA().walkTo(0, -1);
                    }
                    else if (c.absY == 3962)
                    {
                        c.getPA().walkTo(0, 1);
                    }
                }
                else if (c.objectX == 3190 && c.objectY == 3957)
                {
                    if (c.absY == 3957)
                    {
                        c.getPA().walkTo(0, 1);
                    }
                    else if (c.absY == 3958)
                    {
                        c.getPA().walkTo(0, -1);
                    }
                }
            }
            else
            {
                c.sendMessage("I need a lockpick to pick this lock.");
            }
			break;
		case 17010:
			if (c.playerMagicBook == 0) {
				c.sendMessage("You switch spellbook to lunar magic.");
				c.setSidebarInterface(6, 29999);
				c.playerMagicBook = 2;
				c.autocasting = false;
				c.autocastId = -1;
				c.getPA().resetAutocast();
				break;
			}
			if (c.playerMagicBook == 1) {
				c.sendMessage("You switch spellbook to lunar magic.");
				c.setSidebarInterface(6, 29999);
				c.playerMagicBook = 2;
				c.autocasting = false;
				c.autocastId = -1;
				c.getPA().resetAutocast();
				break;
			}
			if (c.playerMagicBook == 2) {
				c.setSidebarInterface(6, 1151);
				c.playerMagicBook = 0;
				c.autocasting = false;
				c.sendMessage("You feel a drain on your memory.");
				c.autocastId = -1;
				c.getPA().resetAutocast();
				break;
			}
			break;
			/*
			* One stall that will give different amount of money depending on your thieving level, also different amount of xp.
			*/
			case 2561:
				if ((c.playerLevel[17]) >= 0 && (c.playerLevel[17] < 25)) { //level 1 of thieving
					Thieving.stealFromStall(c, 1897, 1, 20, 1, objectType, obX, obY, obX == 2667 ? 3 : 1);
				} else if ((c.playerLevel[17]) >= 25 && (c.playerLevel[17] < 50)) { //level 20 of  thieving
					Thieving.stealFromStall(c, 950, 1, 65, 25, objectType, obX, obY, obX == 2667 ? 3 : 1);
				} else if ((c.playerLevel[17]) >= 50 && (c.playerLevel[17] < 75)) { // level 40 of thieving
					Thieving.stealFromStall(c, 1635, 1, 150, 50, objectType, obX, obY, obX == 2667 ? 3 : 1);
				} else if ((c.playerLevel[17]) >= 75 && (c.playerLevel[17] < 90)) { //level 60 of thieving
					Thieving.stealFromStall(c, 7650, 1, 250, 75, objectType, obX, obY, obX == 2667 ? 3 : 1);
				} else if ((c.playerLevel[17]) >= 90 && (c.playerLevel[17] <= 99)) { //level 70 of thieving
					Thieving.stealFromStall(c, 1613, 1, 400, 90, objectType, obX, obY, obX == 2667 ? 3 : 1);
				} 
			break;
			
		case 2781:
		case 26814:
		case 11666:
		case 3044:
			c.getSmithing().sendSmelting();
			break;
		/*case 6166:
			Thieving.stealFromStall(c, 1635, 1, 400, 50, objectType, obX,
					obY, 2);
			break;
		case 6164:
			Thieving.stealFromStall(c, 7650, 1, 500, 75, objectType, obX,
					obY, 0);
			break;
		case 6165:
			Thieving.stealFromStall(c, 950, 1, 300, 25, objectType, obX,
					obY, 0);
			break;
		case 6162:
			Thieving.stealFromStall(c, 1613, 1, 600, 90, objectType, obX,
					obY, 3);
			break;
		case 6163:
			Thieving.stealFromStall(c, 1897, 1, 24, 1, objectType, obX, obY,
					obX == 2662 ? 2 : 1);
			break;

		case 14011:
			Thieving.stealFromStall(c, 995, 1000, 10, 1, objectType, obX, obY,
					3);
			break;
		case 7053:
			Thieving.stealFromStall(c, 995, 5000, 18, 10, objectType, obX,
					obY, obX == 3079 ? 2 : 1);
			break;*/
		case 2646:
			Flax.pickFlax(c, obX, obY);
			break;

		/**
		 * Opening the bank.
		 */
		case 2213:
		case 14367:
		case 11758:
		case 10517:
		case 26972:
			c.getPA().openUpBank();
			bankCheck = true;
			break;

		}
	}
	public boolean bankCheck = false;
	public void thirdClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		//c.sendMessage("Object type: " + objectType);	
		switch (objectType) {
		default:
			ScriptManager.callFunc("objectClick3_" + objectType, c, objectType,
					obX, obY);
			break;
		}
	}

	public void firstClickNpc(int npcType) {
		c.clickNpcType = 0;
		Pet.pickUpPetRequirements(c, npcType);
		c.npcClickIndex = 0;
		/*
		 * if(Fishing.fishingNPC(c, npcType)) { Fishing.fishingNPC(c, 1,
		 * npcType); return; }
		 */	

		if(c.getHunter().hasReqs(npcType)){
		c.getHunter().Checking(npcType);
		c.getPA().refreshSkill(21);
		}
	
		switch (npcType) {
		case 2024:
			c.getDH().sendDialogues(400, npcType);
			c.dialogueAction = 438;
		break;
		case 707:
		c.getDH().sendOption3("@red@Can you exchange my @blu@PvP Emblems @red@for @blu@Pk-points?",
					"@red@Can you exchange my @blu@PvP Emblems @red@for @blu@Cash?", "Exit");
		c.dialogueAction = 1758;
		break;
		case 1512:
			c.getShops().openShop(13);
		break;
		case 1597:
			c.getDH().sendDialogues(270, npcType);
		break;
		///pets pickup
		/*case 3506:
		case 3507:
		case 761:
		case 760:
		case 762:
		case 763:
		case 764:
		case 765:
		case 766:
		case 767:
		case 768:
		case 769:
		case 770:
		case 771:
		case 772:
		case 773:
		case 3505:
		case 4000:
		case 4001:
		case 4002:
		case 4003:
		case 4004:
		case 4005:
		case 4006:
		case 4007:
		case 4008:
		case 4009:
		case 4010:
		case 6305:
		c.getSummon().pickUpClean(c, c.summonId);
		c.hasNpc = false;
		c.summonId = 0;
			break;*/
		case 10:
			c.getShops().openShop(18);
			c.sendMessage("@red@You currently have " + c.votePoints + " Voting Points.");
		break;
		case 1282:
			Server.shopHandler = new main.world.ShopHandler();
			c.getShops().openShop(20);
			c.sendMessage("You can sell any of your items to me.");
		break;
		case 1552:
			c.getShops().openShop(16);
			c.sendMessage("You currently have @red@" + c.donPoints
					+ " @bla@donator points.");
			c.sendMessage("@red@Donator Points are currently: 1$ for 100");
		break;
		case 4289:
			c.getDH().sendDialogues(2000, c.npcType);
		break;
		case 3789:
			c.getShops().openShop(17);
		break;
		case 367:
			c.getPA().showInterface(23472);
		break;
		// FISHING
		case 319:
		case 329:
		case 323:
			// case 325:
		case 326:
		case 327:
		case 330:
		case 332:
		case 316: // NET + BAIT
			Fishing.attemptdata(c, 1);
			break;
			case 325:
			Fishing.attemptdata(c, 12);
			break;
		case 334:
		case 313: // NET + HARPOON
			Fishing.attemptdata(c, 3);
			break;
		case 322: // NET + HARPOON
			Fishing.attemptdata(c, 5);
			break;

		case 309: // LURE
		case 310:
		case 311:
		case 314:
		case 315:
		case 317:
		case 318:
		case 328:
		case 331:
			Fishing.attemptdata(c, 4);
			break;

		case 312:
		case 321:
		case 324: // CAGE + HARPOON
			Fishing.attemptdata(c, 8);
			break;

		case 944:
			c.getDH().sendOption5("Hill Giants", "Hellhounds", "Lesser Demons",
					"Chaos Dwarf", "-- Next Page --");
			c.teleAction = 7;
			break;
		case 659:
			c.getShops().openShop(11);
			break;

		case 528:
			c.getShops().openShop(1);
			break;

		case 1599:
		if (c.slayerTask <= 0) {
				c.getDH().sendDialogues(11, c.npcType);
				//c.sendMessage("Slayer will be enabled in some minutes.");
		} else {
				c.getDH().sendDialogues(1000, c.npcType);
		}
			break;

		case 953: // Banker
		case 2574: // Banker
		case 166: // Gnome Banker
		case 1702: // Ghost Banker
		case 494: // Banker
		case 495: // Banker
		case 496: // Banker
		case 497: // Banker
		case 498: // Banker
		case 499: // Banker
		case 567: // Banker
		case 1036: // Banker
		case 1360: // Banker
		case 2163: // Banker
		case 2164: // Banker
		case 2354: // Banker
		case 2355: // Banker
		case 2568: // Banker
		case 2569: // Banker
		case 2570: // Banker
			c.getDH().sendDialogues(1013, c.npcType);
			break;
		case 1986:
			c.getDH().sendDialogues(2244, c.npcType);
			break;
			
		case 2244:
			c.getDH().sendDialogues(692, c.npcType);
			break;

		case 644:
			c.getShops().openShop(9);
			c.sendMessage("You currently have @red@" + c.donPoints
					+ " @bla@donator points.");
			c.sendMessage("@red@Donator Points are currently: 1$ for 100");
			break;

		case 550:
			c.getShops().openShop(4);
			break;

		case 1785:
			//c.getShops().openShop(8);
			c.sendMessage("@blu@ The PvP store is currently under development.");
			break;

		case 1860:
			c.getShops().openShop(6);
			break;

		case 519:
			c.getShops().openShop(7);
			break;

		case 548:
			c.getDH().sendDialogues(69, c.npcType);
			break;

		case 2258:
			c.getDH().sendOption2("Teleport me to Runecrafting Abyss.",
					"I want to stay here, thanks.");
			c.dialogueAction = 2258;
			break;

		case 546:
			c.getShops().openShop(5);
			//c.getDH().sendOption4("Buy 1,000 barrage spells [2230k]", "Buy 1,000 vengeance spells [912k]", "Buy 1,000 of all runes [1788k]", "Open mage shop");
			//c.dialogueAction = 1658;
			break;

		case 549:
			c.getShops().openShop(3);
			break;

		case 541:
			c.getShops().openShop(2);
			break;

		case 198:
			c.getShops().openSkillCape();
			break;

		/**
		 * Make over mage.
		 */

		}
	}
	
	public void store(int i, int npcType)
	{


	switch(npcType) {
	case 6807:
	if(Server.npcHandler.npcs[i].npcId == c.summoningnpcid) {
	c.sendMessage("You are now storing items inside your npc");
		c.Summoning().store();
	}
	break;
	}
	}

	public void secondClickNpc(int npcType) {
		c.clickNpcType = 0;
		c.npcClickIndex = 0;				
		if (Thieving.pickpocketNPC(c, npcType)) {
			Thieving.attemptToPickpocket(c, npcType);
			return;
		}
		/*
		 * if(Fishing.fishingNPC(c, npcType)) { Fishing.fishingNPC(c, 2,
		 * npcType); return; }
		 */
		switch (npcType) {
		case 707:
			c.sendMessage("Currently under construction.");
		break;
		case 367:
			c.getShops().openShop(12);
		break;
		case 326:
		case 327:
		case 330:
		case 332:
		case 316: // BAIT + NET
			Fishing.attemptdata(c, 2);
			break;
		case 319:
		case 323:
		case 325: // BAIT + NET
			Fishing.attemptdata(c, 9);
			break;
		case 310:
		case 311:
		case 314:
		case 315:
		case 317:
		case 318:
		case 328:
		case 329:
		case 331:
		case 309: // BAIT + LURE
			Fishing.attemptdata(c, 6);
			break;
		case 312:
		case 321:
		case 324:// SWORDIES+TUNA-CAGE+HARPOON
			Fishing.attemptdata(c, 7);
			break;
		case 313:
		case 322:
		case 334: // NET+HARPOON
			Fishing.attemptdata(c, 10);
			break;
		case 546:
			c.getShops().openShop(5);
			break;
		case 1599:
			c.getShops().openShop(10);
			c.sendMessage("You currently have @red@" + c.slayerPoints
					+ " @bla@slayer points.");
			break;
		case 953: // Banker
		case 2574: // Banker
		case 166: // Gnome Banker
		case 1702: // Ghost Banker
		case 494: // Banker
		case 495: // Banker
		case 496: // Banker
		case 497: // Banker
		case 498: // Banker
		case 499: // Banker
		case 567: // Banker
		case 1036: // Banker
		case 1360: // Banker
		case 2163: // Banker
		case 2164: // Banker
		case 2354: // Banker
		case 2355: // Banker
		case 2568: // Banker
		case 2569: // Banker
		case 2570: // Banker
			c.getPA().openUpBank();
			bankCheck = true;
			break;

		case 1785:
			//c.getShops().openShop(8);
			break;

		case 550:
			c.getShops().openShop(4);
			break;

		case 3796:
			c.getShops().openShop(6);
			break;

		case 1860:
			c.getShops().openShop(6);
			break;

		case 519:
			c.getShops().openShop(7);
			break;

		case 548:
			c.getDH().sendDialogues(69, c.npcType);
			break;

		case 2258:
			c.sendMessage("This NPC do not have a shop so you cant trade him.");
			break;

		case 541:
			c.getShops().openShop(2);
			break;

		case 528:
			c.getShops().openShop(1);
			break;

		case 532:
			c.getShops().openShop(1);
			break;
		}
	}

	public void thirdClickNpc(int npcType) {		
		c.clickNpcType = 0;
		c.npcClickIndex = 0;
		switch (npcType) {
		case 707:
			c.getDH().sendDialogues(4315, c.npcType);
			/*
			 * 			c.headIconPk = 0;
			c.isSkulled = true;
			c.getPA().requestUpdates();
			c.sendMessage("You are now skulled.");
			 */
			break;
		case 1599:
			c.getShops().openShop(10);
			c.sendMessage("You currently have @red@" + c.slayerPoints
					+ " @bla@slayer points.");
			break;
		case 548:
			if (!c.appearanceUpdate()) {
				c.sendMessage("You must remove your equipment before changing your appearence.");
				c.canChangeAppearance = false;
			} else {
				c.getPA().showInterface(3559);
				c.canChangeAppearance = true;
			}
			break;

		case 836:
			c.getShops().openShop(103);
			break;

		}
	}

}