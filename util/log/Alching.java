package main.util.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import main.model.players.Client;

public class Alching {

	private Client c;

	public Alching(Client Client) {
		this.c = Client;
	}

	/**
	 * Will write High alching items
	 * DAY OF MONTH = 30 || 31
	 */
	public void AlchingHigh(int itemId) {
		Calendar C = Calendar.getInstance();
		try {
			BufferedWriter bItem = new BufferedWriter(new FileWriter(
					"./Data/logs/Alching/High/" + c.playerName + ".txt",
					true));
			try {
				bItem.newLine();
				bItem.write("Year : " + C.get(Calendar.YEAR) + "\tMonth : "
						+ C.get(Calendar.MONTH) + "\tDay : "
						+ C.get(Calendar.DAY_OF_MONTH)
						+ "\tHour : " + C.get(Calendar.HOUR)
						+ "\tMinute : " + C.get(Calendar.MINUTE)
						+ "\tSecond : " + C.get(Calendar.SECOND));
				bItem.newLine();
				bItem.write(c.playerName + " Has High Alched " + itemId + ", Quantity 1");
				bItem.newLine();
				bItem.write("--------------------------------------------------");
			} finally {
				bItem.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Will write what kind of item a player has traded with another player.
	 * MONTH = 0 = January DAY OF MONTH = 30 || 31
	 */
	public void AlchingLow(int itemId) {
		Calendar C = Calendar.getInstance();
		try {
			BufferedWriter bItem = new BufferedWriter(new FileWriter(
					"./Data/logs/Alching/Low/" + c.playerName + ".txt",
					true));
			try {
				bItem.newLine();
				bItem.write("Year : " + C.get(Calendar.YEAR) + "\tMonth : "
						+ C.get(Calendar.MONTH) + "\tDay : "
						+ C.get(Calendar.DAY_OF_MONTH)
						+ "\tHour : " + C.get(Calendar.HOUR)
						+ "\tMinute : " + C.get(Calendar.MINUTE)
						+ "\tSecond : " + C.get(Calendar.SECOND));
				bItem.newLine();
				bItem.write(c.playerName + " Has Low Alched " + itemId + ", Quantity 1");
				bItem.newLine();
				bItem.write("--------------------------------------------------");
			} finally {
				bItem.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}