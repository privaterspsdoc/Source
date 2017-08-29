package main.util.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import main.model.players.Client;

public class CommandsLog {

	private Client c;

	public CommandsLog(Client Client) {
		this.c = Client;
	}

	/**
	 * Will write The Commands players are doing
	 * MONTH = 0 = January DAY OF MONTH = 30 || 31
	 */
	public void writeCommandLog(String command) {
		String filePath = "./Data/logs/CommandsLog/" + c.playerName + ".txt";
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
			bw.write("Player Command: " +command);
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