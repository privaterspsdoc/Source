package main.model.players.skills;

import main.Server;
import main.model.players.Client;
import main.util.Misc;

/**
 * Slayer.java
 * 
 * @author Sanity
 * 
 **/

public class Slayer {

	private Client c;

	public Slayer(Client c) {
		this.c = c;
	}

	public int[] lowTasks = { 1612, 117, 1265, 103, 78, 119, 18, 101, 1265, 181 };
	public int[] lowReqs = { 15, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
	public int[] medTasks = { 1643, 1618, 941, 119, 82, 52, 1612, 117, 1265, 112, 125 };
	public int[] medReqs = { 45, 50, 1, 1, 1, 1, 15, 1, 1, 1, 1, 1 };
	public int[] highTasks = { 1624, 3068, 1610, 1613, 1615, 55, 84, 49, 1618, 941, 82, 2783 };
	public int[] highReqs = { 65, 50, 75, 80, 85, 1, 1, 1, 50, 1, 1, 90 };
	public int[] eliteTasks =  { 3499, 3500, 3501, 3502, 3943, 3847, 3200, 2881, 2882, 2883, 6260, 6222, 6247, 6203 };
	public int[] eliteReqs = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

	public void giveTask() {
		if (c.combatLevel < 50)
			giveTask(1);
		else if (c.combatLevel >= 50 && c.combatLevel <= 90)
			giveTask(2);
		else if (c.combatLevel > 90 && c.combatLevel <= 126)
			giveTask(3);
		else
			giveTask(2);
	}

	public void giveTask2() {
		for (int j = 0; j < lowTasks.length; j++) {
			if (lowTasks[j] == c.slayerTask) {
				c.sendMessage("You already have a slayer task!");
				return;
			}
		}
		giveTask(1);
	}
	
	public void giveEliteTask(int taskLevel) {
		int given = 0;
		int random = 0;
		if (taskLevel == 4) {
			random = (int) (Math.random() * (eliteTasks.length - 1));
			given = eliteTasks[random];
		}	
			c.slayerTask = given;
			c.taskAmount = Misc.random(5) + 8;
			c.sendMessage("You have been assigned to kill " + c.taskAmount + " "
					+ Server.npcHandler.getNpcListName(given)
					+ " as an elite slayer task.");
			c.getPA().loadQuests();
	}

	public void giveTask(int taskLevel) {
		int given = 0;
		int random = 0;
		if (taskLevel == 1) {
			random = (int) (Math.random() * (lowTasks.length - 1));
			given = lowTasks[random];
		} else if (taskLevel == 2) {
			random = (int) (Math.random() * (medTasks.length - 1));
			given = medTasks[random];
		} else if (taskLevel == 3) {
			random = (int) (Math.random() * (highTasks.length - 1));
			given = highTasks[random];
		}
		if (!canDoTask(taskLevel, random)) {
			giveTask(taskLevel);
			return;
		}
		c.slayerTask = given;
		c.taskAmount = Misc.random(25) + 25;
		c.sendMessage("You have been assigned to kill " + c.taskAmount + " "
				+ Server.npcHandler.getNpcListName(given)
				+ " as a slayer task.");
		c.getPA().loadQuests();
	}
	
	public boolean canDoTask(int taskLevel, int random) {
		if (taskLevel == 1) {
			return c.playerLevel[c.playerSlayer] >= lowReqs[random];
		} else if (taskLevel == 2) {
			return c.playerLevel[c.playerSlayer] >= medReqs[random];
		} else if (taskLevel == 3) {
			return c.playerLevel[c.playerSlayer] >= highReqs[random];
		} else if (taskLevel == 4) {
			return c.playerLevel[c.playerSlayer] >= eliteReqs[random];
		}
		return false;
	}
}