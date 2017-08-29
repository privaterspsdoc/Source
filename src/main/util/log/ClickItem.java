package main.util.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import main.model.players.Client;

public class ClickItem {

	private Client c;

	public ClickItem(Client Client) {
		this.c = Client;
	}

	/**
	 * Will write The Players clicking item
	 * MONTH = 0 = January DAY OF MONTH = 30 || 31
	 */
	public void WriteClickItem(int itemId) {
		String filePath = "./Data/logs/ClickItem/" + c.playerName + ".txt";
		BufferedWriter bw = null;
		Calendar C = Calendar.getInstance();
		try 
		{				
			bw = new BufferedWriter(new FileWriter(filePath, true));
			bw.newLine();
			bw.write("Year : " + C.get(Calendar.YEAR) + "\tMonth : "
					+ C.get(Calendar.MONTH) + "\tDay : "
					+ C.get(Calendar.DAY_OF_MONTH)
					+ "\tHour : " + C.get(Calendar.HOUR)
					+ "\tMinute : " + C.get(Calendar.MINUTE)
					+ "\tSecond : " + C.get(Calendar.SECOND));
			bw.newLine();
			bw.write(c.playerName + " Has clicked item: " + itemId);
			bw.newLine();
			bw.write("--------------------------------------------------");
			bw.flush();
		} 
		catch (IOException ioe) 
		{
			ioe.printStackTrace();
		} 
		finally 
		{
			if (bw != null)
			{
				try 
				{
					bw.close();
				} 
				catch (IOException ioe2) 
				{
				}
			}
		}
	}
}