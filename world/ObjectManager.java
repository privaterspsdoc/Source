package main.world;

import java.util.ArrayList;

import main.model.objects.Object;
import main.model.players.Client;
import main.model.players.PlayerHandler;
import main.util.Misc;

/**
 * @author Sanity
 */

public class ObjectManager {

	public ArrayList<Object> objects = new ArrayList<Object>();
	private ArrayList<Object> toRemove = new ArrayList<Object>();

	public void process() {
		for (final Object o : objects) {
			if (o.tick > 0) {
				o.tick--;
			} else {
				updateObject(o);
				toRemove.add(o);
			}
		}
		for (final Object o : toRemove) {
			/*
			 * if (o.objectId == 2732) { for (final Player player :
			 * PlayerHandler.players) { if (player != null) { final Client c =
			 * (Client)player; Server.itemHandler.createGroundItem(c, 592,
			 * o.objectX, o.objectY, 1, c.playerId); } } }
			 */
			if (isObelisk(o.newId)) {
				final int index = getObeliskIndex(o.newId);
				if (activated[index]) {
					activated[index] = false;
					teleportObelisk(index);
				}
			}
			objects.remove(o);
		}
		toRemove.clear();
	}

	public boolean objectExists(final int x, final int y) {
		for (Object o : objects) {
			if (o.objectX == x && o.objectY == y) {
				return true;
			}
		}
		return false;
	}

	public void removeObject(int x, int y) {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c = (Client) PlayerHandler.players[j];
				c.getPA().object(-1, x, y, 0, 10);
			}
		}
	}

	public void updateObject(Object o) {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c = (Client) PlayerHandler.players[j];
				c.getPA().object(o.newId, o.objectX, o.objectY, o.face, o.type);
			}
		}
	}

	public void placeObject(Object o) {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c = (Client) PlayerHandler.players[j];
				if (c.distanceToPoint(o.objectX, o.objectY) <= 60)
					c.getPA().object(o.objectId, o.objectX, o.objectY, o.face,
							o.type);
			}
		}
	}

	public Object getObject(int x, int y, int height) {
		for (Object o : objects) {
			if (o.objectX == x && o.objectY == y && o.height == height)
				return o;
		}
		return null;
	}

	public void loadObjects(Client c) {
		if (c == null)
			return;
		for (Object o : objects) {
			if (loadForPlayer(o, c))
				c.getPA().object(o.objectId, o.objectX, o.objectY, o.face,
						o.type);
		}
		loadCustomSpawns(c);
		if (c.distanceToPoint(2813, 3463) <= 60) {
			c.getFarming().updateHerbPatch();
		}
	}

	private int[][] customObjects = { {} };

	public static void loadCustomSpawns(Client c) {
		//donatorzone
		c.getPA().checkObjectSpawn(2213, 2334, 9806, 0, 10);
		//endofdonatorzone
		c.getPA().checkObjectSpawn(-1, 2847, 3540, 1, 10);
		c.getPA().checkObjectSpawn(-1, 2847, 3540, 1, 10);
		c.getPA().checkObjectSpawn(-1, 2847, 3541, 1, 10);
		c.getPA().checkObjectSpawn(2213, 2817, 3463, 1, 10);
		c.getPA().checkObjectSpawn(2213, 2855, 3441, 0, 10);
		c.getPA().checkObjectSpawn(2783, 3041, 9782, 0, 10);
		c.getPA().checkObjectSpawn(2783, 2851, 3427, 0, 10);
		//c.getPA().checkObjectSpawn(2091, 2860, 3430, 0, 10);
		//c.getPA().checkObjectSpawn(2094, 2858, 3432, 0, 10);
		//c.getPA().checkObjectSpawn(2093, 2857, 3428, 0, 10);
		//c.getPA().checkObjectSpawn(2103, 2855, 3431, 0, 10);
		//c.getPA().checkObjectSpawn(2097, 2854, 3434, 0, 10);
		//c.getPA().checkObjectSpawn(2105, 2851, 3432, 0, 10);
		//c.getPA().checkObjectSpawn(2098, 2852, 3430, 0, 10);
		c.getPA().checkObjectSpawn(2561, 3083, 3492, 1, 10); //bakery
		//c.getPA().checkObjectSpawn(6163, 3083, 3487, 1, 10); //bakery
		//c.getPA().checkObjectSpawn(6165, 3083, 3489, 1, 10); //clothes
		//c.getPA().checkObjectSpawn(6166, 3083, 3491, 1, 10); //crafting
		//c.getPA().checkObjectSpawn(6164, 3083, 3493, 1, 10); //silver
		//c.getPA().checkObjectSpawn(6162, 3083, 3495, 1, 10); //gem
		c.getPA().checkObjectSpawn(14860, 3044, 9776, 0, 10);
		c.getPA().checkObjectSpawn(14860, 3045, 9776, 0, 10);
		c.getPA().checkObjectSpawn(14860, 3046, 9776, 0, 10);
		c.getPA().checkObjectSpawn(3044, 3043, 9784, 1, 10);
		c.getPA().checkObjectSpawn(1276, 2840, 3439, 1, 10);
		//c.getPA().checkObjectSpawn(1308, 2846, 3437, 1, 10);
		//c.getPA().checkObjectSpawn(1309, 2841, 3443, 1, 10);
		//c.getPA().checkObjectSpawn(1307, 2844, 3440, 1, 10);
		c.getPA().checkObjectSpawn(1281, 2843, 3436, 1, 10);
		c.getPA().checkObjectSpawn(1306, 2724, 3457, 1, 10);
		/*c.getPA().checkObjectSpawn(2563, 3083, 3490, 3, 10);
		c.getPA().checkObjectSpawn(2565, 3083, 3492, 3, 10);
		c.getPA().checkObjectSpawn(2564, 3083, 3494, 3, 10);
		c.getPA().checkObjectSpawn(2562, 3083, 3496, 3, 10);*/
		c.getPA().checkObjectSpawn(2783, 3105, 3502, 3, 10);
		c.getPA().checkObjectSpawn(2783, 3105, 3502, 3, 10);
		c.getPA().checkObjectSpawn(2783, 3107, 3502, 3, 10);
		c.getPA().checkObjectSpawn(411, 2340, 9806, 2, 10);

		c.getPA().checkObjectSpawn(2732, 2606, 3401, 3, 10);

		c.getPA().checkObjectSpawn(-1, 2950, 3450, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3103, 3499, 0, 0);
		c.getPA().checkObjectSpawn(-1, 3046, 9756, 0, 0);
		c.getPA().checkObjectSpawn(1596, 3008, 3850, 1, 0);
		c.getPA().checkObjectSpawn(1596, 3008, 3849, -1, 0);
		c.getPA().checkObjectSpawn(1596, 3040, 10307, -1, 0);
		c.getPA().checkObjectSpawn(1596, 3040, 10308, 1, 0);
		c.getPA().checkObjectSpawn(1596, 3022, 10311, -1, 0);
		c.getPA().checkObjectSpawn(1596, 3022, 10312, 1, 0);
		c.getPA().checkObjectSpawn(1596, 3044, 10341, -1, 0);
		c.getPA().checkObjectSpawn(1596, 3044, 10342, 1, 0);
		c.getPA().checkObjectSpawn(6552, 3094, 3506, 2, 10);
		c.getPA().checkObjectSpawn(409, 3091, 3506, 2, 10);
		c.getPA().checkObjectSpawn(2090, 3043, 9750, 2, 10);
		c.getPA().checkObjectSpawn(2090, 3042, 9748, 2, 10);
		c.getPA().checkObjectSpawn(2094, 3049, 9749, 2, 10);
		c.getPA().checkObjectSpawn(2094, 3050, 9750, 2, 10);
		c.getPA().checkObjectSpawn(2213, 3047, 9779, 1, 10);
		c.getPA().checkObjectSpawn(2213, 3080, 9502, 1, 10);
		if (c.heightLevel == 0)
			c.getPA().checkObjectSpawn(2492, 2911, 3614, 1, 10);
		else
			c.getPA().checkObjectSpawn(-1, 2911, 3614, 1, 10);
	}

	public final int IN_USE_ID = 14825;

	public boolean isObelisk(int id) {
		for (int j = 0; j < obeliskIds.length; j++) {
			if (obeliskIds[j] == id)
				return true;
		}
		return false;
	}

	public int[] obeliskIds = { 14829, 14830, 14827, 14828, 14826, 14831 };
	public int[][] obeliskCoords = { { 3154, 3618 }, { 3225, 3665 },
			{ 3033, 3730 }, { 3104, 3792 }, { 2978, 3864 }, { 3305, 3914 } };
	public boolean[] activated = { false, false, false, false, false, false };

	public void startObelisk(int obeliskId) {
		int index = getObeliskIndex(obeliskId);
		if (index >= 0) {
			if (!activated[index]) {
				activated[index] = true;
				addObject(new Object(14825, obeliskCoords[index][0],
						obeliskCoords[index][1], 0, -1, 10, obeliskId, 16));
				addObject(new Object(14825, obeliskCoords[index][0] + 4,
						obeliskCoords[index][1], 0, -1, 10, obeliskId, 16));
				addObject(new Object(14825, obeliskCoords[index][0],
						obeliskCoords[index][1] + 4, 0, -1, 10, obeliskId, 16));
				addObject(new Object(14825, obeliskCoords[index][0] + 4,
						obeliskCoords[index][1] + 4, 0, -1, 10, obeliskId, 16));
			}
		}
	}

	public int getObeliskIndex(int id) {
		for (int j = 0; j < obeliskIds.length; j++) {
			if (obeliskIds[j] == id)
				return j;
		}
		return -1;
	}

	public void teleportObelisk(int port) {
		int random = Misc.random(5);
		while (random == port) {
			random = Misc.random(5);
		}
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c = (Client) PlayerHandler.players[j];
				int xOffset = c.absX - obeliskCoords[port][0];
				int yOffset = c.absY - obeliskCoords[port][1];
				if (c.goodDistance(c.getX(), c.getY(),
						obeliskCoords[port][0] + 2, obeliskCoords[port][1] + 2,
						1)) {
					c.getPA().startTeleport2(
							obeliskCoords[random][0] + xOffset,
							obeliskCoords[random][1] + yOffset, 0);
				}
			}
		}
	}

	public boolean loadForPlayer(Object o, Client c) {
		if (o == null || c == null)
			return false;
		return c.distanceToPoint(o.objectX, o.objectY) <= 60
				&& c.heightLevel == o.height;
	}

	public void addObject(Object o) {
		if (getObject(o.objectX, o.objectY, o.height) == null) {
			objects.add(o);
			placeObject(o);
		}
	}

}