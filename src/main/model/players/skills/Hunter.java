package main.model.players.skills;

import java.util.HashMap;

import main.model.items.Item;
import main.model.players.Client;
import main.util.Misc;

public class Hunter {
	
	/**
	 * @author Imsuperman05
	 * @version 1.5;
	 * 
	 * I give permission for RiiPiiNFtW to release this
	 * on rune-server.org.
	 * 
	 * I have no use for it anymore, and I want to
	 * give back to the rune-server community that
	 * I have treated horribly.
	 * 
	 * We have some room to improve.
	 * C&P suggestions from forums onto here.
	 * 
	 */

	final Client c;

	public Hunter(final Client Client) {
		c = Client;
	}
	
	/**
	 * Constant variables that can be changed at any time
	 * instead of changing every single calling method
	 * handling it.
	 */
	int HunterAnim = 6999;
	int netNeeded = 10010;
	int trapNeeded = 10008;
	
	static enum Implings {
		/**
		 * Baby Impling.
		 */
		BABY( "Baby Impling", 11238, 1500, 1, 6055 ),
		/**
		 * Young Impling.
		 */
		YOUNG( "Young Impling", 11240, 3500, 17, 6056 ),
		/**
		 * Gourmet Impling.
		 */
		GOURMET( "Gourmet Impling", 11244, 5000, 34, 6057),
		/**
		 * Earth Impling.
		 */
		EARTH( "Earth Impling", 11244, 5000, 34, 6058 ),
		/**
		 * Essence Impling.
		 */
		ESSENCE( "Essence Impling", 11246, 6000, 40, 6059 ),
		/**
		 * Electic Impling.
		 */
		ELECTIC( "Electic Impling", 11248, 8000, 50, 6060),
		/**
		 * Nature Impling.
		 */
		NATURE( "Nature Impling", 11250, 10000, 58,6061 ),
		/**
		 * Magpie Impling.
		 */
		MAGPIE( "Magpie Impling", 11252, 12500, 65, 6062 ),
		/**
		 * Ninja Impling.
		 */
		NINJA( "Ninja Impling", 11254, 14000, 74, 6063 );
		
		/**
		 * Variables.
		 */
		String name;
		int JarAdded, XPAdded, LevelNeed, Npc;
		
		/**
		 * Creating the HashMap.
		 */
		static HashMap<Integer, Implings> hunted = new HashMap<Integer, Implings>();
		
		/**
		 * Populates the map.
		 */
		static {
			for(Implings i : Implings.values())
				Implings.hunted.put(i.JarAdded, i);
		}

		/**
		 * Creating the Impling.
		 * @param name
		 * @param JarAdded
		 * @param XPAdded
		 * @param LevelNeed
		 * @param Npc
		 */
		Implings(String name, int JarAdded, int XPAdded, int LevelNeed, int Npc) {
			this.name = name;
			this.JarAdded = JarAdded;
			this.XPAdded = XPAdded;
			this.LevelNeed = LevelNeed;
			this.Npc = Npc;
		}
	}

	static enum Otherhunternpcs {
		/**
		 * Black warlock.
		 */
		BLACKWARLOCK( "Black Warlock", 10014, 18000, 85, 5082 ),
		/**
		 * Snowy Knight,
		 */
		SNOWY( "Snowy Knight", 10016, 15000, 75, 5083 ),
		/**
		 * Sapphire Glacialis.
		 */
		SAPPHIRE( "Sapphire Glacialis", 10018, 7500, 45, 5084 ),
		/**
		 * Ruby Harvast.
		 */
		RUBY( "Ruby Harvast", 10020, 5000, 30, 5085);
		
		/**
		 * Variables.
		 */
		String name;
		int JarAdded, XPAdded, LevelNeed, Npc;
		
		/**
		 * Creating the HashMap.
		 */
		static HashMap<Integer, Otherhunternpcs> hunted = new HashMap<Integer, Otherhunternpcs>();
		
		/**
		 * Populates the map.
		 */
		static {
			for(Otherhunternpcs i : Otherhunternpcs.values())
				Otherhunternpcs.hunted.put(i.JarAdded, i);
		}

		/**
		 * Creating the Other hunted npcs.
		 * @param name
		 * @param JarAdded
		 * @param XPAdded
		 * @param LevelNeed
		 * @param Npc
		 */
		Otherhunternpcs(String name, int JarAdded, int XPAdded, int LevelNeed, int Npc) {
			this.name = name;
			this.JarAdded = JarAdded;
			this.XPAdded = XPAdded;
			this.LevelNeed = LevelNeed;
			this.Npc = Npc;
		}
	}
	
	static enum Chinchompas {
		RED( "Red Chinchompa", 9977, 175, 80, 5080 ),
		NORMAL("Chinchompa", 9976, 100, 50, 5079);	
		
		/**
		 * Variables.
		 */
		String name;
		int JarAdded, XPAdded, LevelNeed, Npc;
		
		/**
		 * Creating the HashMap.
		 */
		static HashMap<Integer, Chinchompas> hunted = new HashMap<Integer, Chinchompas>();
		
		/**
		 * Populates the map.
		 */
		static {
			for(Chinchompas i : Chinchompas.values())
				Chinchompas.hunted.put(i.JarAdded, i);
		}

		/**
		 * Creating the Other hunted npcs.
		 * @param name
		 * @param JarAdded
		 * @param XPAdded
		 * @param LevelNeed
		 * @param Npc
		 */
		
		Chinchompas(String name, int JarAdded, int XPAdded, int LevelNeed, int Npc) {
			this.name = name;
			this.JarAdded = JarAdded;
			this.XPAdded = XPAdded;
			this.LevelNeed = LevelNeed;
			this.Npc = Npc;
		}
	}
	
	
	

	/**
	 * Boolean to check everything.
	 * @param NpcClicked
	 * @return
	 */
	public boolean hasReqs(int NpcClicked) {
		for(Otherhunternpcs o : Otherhunternpcs.values()) {
		if(NpcClicked == o.Npc) {
			if(c.playerLevel[21] >= o.LevelNeed) {
				if(c.playerEquipment[c.playerWeapon] == netNeeded) {
					if (c.getItems().freeSlots() > 0) {
							return true;
					} else {
						c.sendMessage("You have no space..");
						return false;
					}
				} else {
					c.sendMessage("You need a butterfly net to catch this imp pimp!");
					return false;
				}
			} else {
				c.sendMessage("You need a Hunter level of " + o.LevelNeed + " to catch this imp pimp!");
				return false;
				}
			}
		}
		for(Chinchompas o : Chinchompas.values()) {
		if(NpcClicked == o.Npc) {
			if(c.playerLevel[21] >= o.LevelNeed) {
				if(c.getItems().playerHasItem(trapNeeded)) {
					if (c.getItems().freeSlots() > 0) {
							return true;
					} else {
						c.sendMessage("You have no space..");
						return false;
					}
				} else {
					c.sendMessage("You need a box trap to catch this chinchompa!");
					return false;
				}
			} else {
				c.sendMessage("You need a Hunter level of " + o.LevelNeed + " to catch this chinchompa!");
				return false;
				}
			}
		}
		for(Implings o : Implings.values()) {
			if(NpcClicked == o.Npc) {
				if(c.playerLevel[21] >= o.LevelNeed) {
					if(c.playerEquipment[c.playerWeapon] == netNeeded) {
						if (c.getItems().freeSlots() > 0) {
								return true;
						} else {
							c.sendMessage("You have no space..");
							return false;
						}
					} else {
						c.sendMessage("You need a butterfly net to catch this imp pimp!");
						return false;
					}
				} else {
					c.sendMessage("You need a Hunter level of " + o.LevelNeed + " to catch this imp pimp!");
					return false;
					}
				}
			}
		return false;
	}
	
	/**
	 * Much simpler.
	 * @param NpcClicked
	 */
	public void Checking(int NpcClicked) {
		for(Otherhunternpcs o : Otherhunternpcs.values()) {
				if(NpcClicked == o.Npc) {
						CatchOthers(o.name, o.JarAdded, o.XPAdded, o.LevelNeed);
			}
		}
		for(Chinchompas o : Chinchompas.values()) {
				if(NpcClicked == o.Npc) {
						CatchChinchompas(o.name, o.JarAdded, o.XPAdded, o.LevelNeed);
			}
		}
		for(Implings i : Implings.values()) {
				if(NpcClicked == i.Npc) {
						CatchImps(i.name, i.JarAdded, i.XPAdded, i.LevelNeed);
			}
		}
	}
	/**
	 * Cathing Imps method.
	 * @param ImpName
	 * @param JarAdded
	 * @param XPAdded
	 * @param LevelNeeded
	 */
	
	public void CatchImps(String ImpName, int JarAdded, int XPAdded, int LevelNeeded) {
			if (System.currentTimeMillis() - c.foodDelay >= 1500) {
				int RandomImp = Misc.random(20);
				switch(RandomImp) {
				/*case 10:
					c.sendMessage("You catch a MEDium imp, DOUBLE EXP FOR MY DOG");
					c.startAnimation(HunterAnim);
					c.getPA().addSkillXP(XPAdded * 2, 21);
				break;
				case 15:
					c.sendMessage("You catch a HUGE ASS imp, but not big enough!");
					c.startAnimation(HunterAnim);
					c.getPA().addSkillXP(XPAdded * 3, 21);
				break;
				case 20:
					c.sendMessage("YOU CATCH THE BIGGEST MTFKING IMP BRO.");
					c.startAnimation(HunterAnim);
					c.getPA().addSkillXP(XPAdded * 4, 21);
					break;
				case 8:
					c.sendMessage("Oh noes!");
					EvilChicken.spawnEvil(c);
				break;*/
				default:
						c.sendMessage(new StringBuilder() .append("You catch a regular ")
								.append(ImpName) .append(", and receive a ") 
								.append(Item.getItemName(JarAdded).toLowerCase()) .append(".").toString());
							c.getPA().addSkillXP(XPAdded, 21);
							c.startAnimation(HunterAnim);
							c.getItems().addItem(JarAdded, 1);
						break;
					}
				c.foodDelay = System.currentTimeMillis();
			}
	}
	
	public void CatchChinchompas(String ChinchompasName, int JarAdded, int XPAdded, int LevelNeeded) {
		if (System.currentTimeMillis() - c.foodDelay >= 1500) {
					//c.startAnimation(1461);
					c.getItems().addItem(JarAdded, 1);
					c.getPA().addSkillXP(XPAdded, 21);
					c.sendMessage("You catch a " + ChinchompasName);

				}
			}

	
	/**
	 * Catching other hunted npcs that aren't implings.
	 * @param ImpName
	 * @param JarAdded
	 * @param XPAdded
	 * @param LevelNeeded
	 */
	public void CatchOthers(String ImpName, int JarAdded, int XPAdded, int LevelNeeded) {
			if (System.currentTimeMillis() - c.foodDelay >= 1500) {
				int RandomImp = Misc.random(20);
				switch(RandomImp) {
				case 10:
					c.sendMessage("You catch a MEDium thing, DOUBLE EXP FOR MY DOG");
					c.startAnimation(HunterAnim);
					c.getPA().addSkillXP(XPAdded * 2, 21);
				break;
				case 15:
					c.sendMessage("You catch a HUGE ASS thing, but not big enough!");
					c.startAnimation(HunterAnim);
					c.getPA().addSkillXP(XPAdded * 3, 21);
				break;
				case 20:
					c.sendMessage("YOU CATCH THE BIGGEST MTFKING THING BRO.");
					c.startAnimation(HunterAnim);
					c.getPA().addSkillXP(XPAdded * 4, 21);
					break;
				/*case 8:
					c.sendMessage("Oh noes!");
					EvilChicken.spawnEvil(c);
				break;*/
				default:
						c.sendMessage(new StringBuilder() .append("You catch a regular ")
								.append(ImpName) .append(", and receive a ") 
								.append(Item.getItemName(JarAdded).toLowerCase()) .append(".").toString());
							c.getPA().addSkillXP(XPAdded, 21);
							c.startAnimation(HunterAnim);
							c.getItems().addItem(JarAdded, 1);
						break;
					}
				c.foodDelay = System.currentTimeMillis();
			}
		}
}