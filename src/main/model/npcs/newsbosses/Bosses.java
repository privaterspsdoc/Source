package main.model.npcs.newsbosses;

import java.util.HashMap;
import java.util.Map;

public class Bosses {
	
private static Map<Integer, Boss> bosses = new HashMap<>();
	
	//public static final Vetion VETION = new Vetion(4175);
	//public static final Scorpia SCORPIA = new Scorpia(4172);
	//public static final Callisto CALLISTO = new Callisto(4174);
	//public static final Veneatis VENEATIS = new Veneatis(4173);
	//public static final Glod GLOD = new Glod(5996);
	//public static final Zulrah ZULRAH = new Zulrah(4056);
	//public static final Cave_Kraken CAVE_KRAKEN = new Cave_Kraken(3848);
	//public static final Jungle_Demon JUNGLE_DEMON = new Jungle_Demon(1472);

	static {
		//bosses.put(CALLISTO.npcId, CALLISTO);
		//bosses.put(SCORPIA.npcId, SCORPIA);
		//bosses.put(VETION.npcId, VETION);
		//bosses.put(VENEATIS.npcId, VENEATIS);
		//bosses.put(GLOD.npcId, GLOD);
		//bosses.put(ZULRAH.npcId, ZULRAH);
		//bosses.put(CAVE_KRAKEN.npcId, CAVE_KRAKEN);
		//bosses.put(JUNGLE_DEMON.npcId, JUNGLE_DEMON);
		
	}
	
	public static Boss get(int npcId) {
		if (!bosses.containsKey(npcId))
			return null;
		return bosses.get(npcId);
	}
	
	/*public static boolean isBoss(int npcId) {
		return Objects.requireNonNull(get(npcId)) != null;
	}*/

}
