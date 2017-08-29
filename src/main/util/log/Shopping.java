package main.util.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import main.model.players.Client;

public class Shopping {

	private Client c;

	public Shopping(Client Client) {
		this.c = Client;
	}

	/**
	 * Will write The Players Buying Items
	 * MONTH = 0 = January DAY OF MONTH = 30 || 31
	 */
	public void WriteBuyItem(int itemId, int Quantity) {
		String filePath = "./Data/logs/Shopping/Buy/" + c.playerName + ".txt";
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
			bw.write(c.playerName + " Has Bought item: " + itemId + ", Quantity " + Quantity);
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
	
	
	/**
	 * Will write The Players Buying Items
	 * MONTH = 0 = January DAY OF MONTH = 30 || 31
	 */
	public void WriteSellItem(int itemId, int Quantity) {
		String filePath = "./Data/logs/Shopping/Sell/" + c.playerName + ".txt";
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
			bw.write(c.playerName + " Has Sold item: " + itemId + ", Quantity " + Quantity);
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