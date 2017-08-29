package main.util.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import main.model.players.Client;
import main.model.players.PlayerHandler;

public class StakingLog {

	private Client c;

	public StakingLog(Client Client) {
		this.c = Client;
	}

	/**
	 * Will write The Won Staking Logs
	 * MONTH = 0 = January DAY OF MONTH = 30 || 31
	 */
	public void writeWonStakingLog() {
		Client o = (Client) PlayerHandler.players[c.duelingWith];
		String filePath = "./Data/logs/StakingLog/Won/" + c.playerName + ".txt";
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
			bw.write(c.playerName + " Staked with " + o.playerName);
			bw.newLine();
			bw.write(c.playerName + " Staking Items: " + c.getTradeAndDuel().stakedItems);
			bw.newLine();
			bw.write(o.playerName + " Staking Items: " + c.getTradeAndDuel().otherStakedItems);
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
	 * Will write The Lost Staking Logs
	 * MONTH = 0 = January DAY OF MONTH = 30 || 31
	 */
	public void writeLostStakingLog() {
		Client o = (Client) PlayerHandler.players[c.duelingWith];
		String filePath = "./Data/logs/StakingLog/Lost/" + c.playerName + ".txt";
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
			bw.write(c.playerName + " Staked with " + o.playerName);
			bw.newLine();
			bw.write(c.playerName + " Staking Items: " + c.getTradeAndDuel().stakedItems);
			bw.newLine();
			bw.write(o.playerName + " Staking Items: " + c.getTradeAndDuel().otherStakedItems);
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