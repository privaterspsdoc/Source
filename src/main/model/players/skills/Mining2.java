package main.model.players.skills;

import main.model.players.Client;


public class Mining2 extends SkillHandler {
	
	public enum oreData {
		Copper1(2090, 436, 1, 5),
		Copper2(2091, 436, 1, 5),
		Tin1(2094, 438, 1, 5),
		Tin2(2095, 438, 1, 5);
	
	private int objectId;
	private int oreId;
	private int amountXP;
	private int timeToMine;
	
	private oreData(int objectId, int oreId, int amountXP, int timeToMine) {
		this.objectId = objectId;
		this.oreId = oreId;
		this.amountXP = amountXP;
		this.timeToMine = timeToMine;
	}
	
	public int getObjectId() {
		return objectId;
	}
	
	public int getOreId() {
		return oreId;
	}
	
	public int getXP() {
		return amountXP;
	}
	
	public int getTime() {
		return timeToMine;
	}
}
	
	public enum pickData {
		BRONZE(1265, 1),
		IRON(1267, 1),
		STEEL(1269, 6),
		MITHRIL(1273, 21),
		ADDY(1271, 31),
		RUNE(1275, 41);
		
	private int pickaxeId;
	private int levelReq;
		
	private pickData(int pickaxeId, int levelReq) {
		this.pickaxeId = pickaxeId;
		this.levelReq = levelReq;
		
		}
	
	public int getId() {
		return pickaxeId;
	}
	
	public int getLevel() {
		return levelReq;
	}
	
	}

	
	
	public static boolean canUsePick(Client c, int getLevel) {
		if (getLevel <= c.playerLevel[c.playerMining]) {
			return true;
		} else {			
			return false;
		}
	}
	
	public static int getPickaxeId() {
		return 1;
	}
	
	public static int gLevel() {
		return 1;
	}
	
	public static boolean hasPickaxe(Client c, int pickaxeId) {
	 if (c.getItems().playerHasItem(pickaxeId)) {
		 return true;
	 } else {
		 return false;
	 }
	}
	
	
	public static void startMining(final Client c, int objectId) {
		if (!noInventorySpace(c, "mining")) {
			return;
		}
		
		if (hasPickaxe(c, getPickaxeId()) && !canUsePick(c, gLevel())) {
			return;
		}
		
		if (!hasPickaxe(c, getPickaxeId())) {
			c.sendMessage("Lol");
			return;
		}
		
	}

}
