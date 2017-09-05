package main.world;



import main.*;
import main.util.*;
import main.event.CycleEvent;
import main.event.CycleEventContainer;
import main.event.CycleEventHandler;
import main.model.items.*;
import main.model.players.*;

public class PublicEvent {
	
	public static String randomChar[] = {"a", "b", "p", "d", "e", "f", "g", "h", "i", "k", "x", "1", "2", "3", "$","%","@", "8","9","0"};
	public static boolean firstEventInProgress = false;
	public static String firstString = "";
	public static int rewardId;
	public static int rewardAmount;
	public static int lastEventTimer = 0;
	public static int eventTimer= 0;
	public static boolean forceFirst = false;
	public static boolean isRunning = false;
	
	public static void forceFirst() {
		if(firstEventInProgress)
			return;
		forceFirst = true;
	}
	
	public static void processEntry(Client player, String command) {
		if(!firstEventInProgress)
			return;
		if(command.equals(firstString)) {
			firstEventInProgress = false;
			lastEventTimer = 0;
			player.getItems().addItem(rewardId, rewardAmount);
			if(rewardAmount == 1) {
				isRunning = false;
				serverMessage("<img=6><col=154360>"+player.playerName.substring(0,1).toUpperCase() + player.playerName.substring(1) + " has won a "+ItemAssistant.getItemName2(rewardId)+"!</col></img>");
			} else {
				String extra = "s";
				if(ItemAssistant.getItemName2(rewardId).endsWith("s"))
					extra = "";
				isRunning = false;
				serverMessage("<img=6><col=154360>"+player.playerName.substring(0,1).toUpperCase() + player.playerName.substring(1) + " has won "+rewardAmount+" "+ItemAssistant.getItemName2(rewardId)+""+extra+"!</col></img>");
			}
			rewardId = 0;
			rewardAmount = 0;
		}
	}
	
	public static String newFirstString() {
		String returns = "";
		while(returns.length() < 10)
			returns += randomChar[Misc.random(randomChar.length - 1)];
		return returns;
	}
	
	public static String constructFirstYell() {
		if(rewardAmount == 1)
			return "<img=6><col=154360>The first person to type </col><col=34495e>::"+firstString+"</col> <col=154360>will receive a "+ItemAssistant.getItemName2(rewardId)+"!</img></col>";
		if(ItemAssistant.getItemName2(rewardId).endsWith("s"))
			return "<img=6><col=154360>The first person to type </col><col=34495e>::"+firstString+"</col> <col=154360>will receive "+rewardAmount+" "+ItemAssistant.getItemName2(rewardId)+"!</img></col>";
		return "<img=6><col=154360>The first person to type </col><col=34495e>::"+firstString+"</col><col=154360> will receive "+rewardAmount+" "+ItemAssistant.getItemName2(rewardId)+"s!</img></col>";
	}
	
	public static void generateReward() {
		int rewardLevel = Misc.random(50);
		if(rewardLevel < 40) {
			rewardLevel = 1;
		} else if(rewardLevel < 49) {
			rewardLevel = 2;
		} else if(rewardLevel == 50) {
			rewardLevel = 3;
		} else {
			rewardLevel = 1;
		}
		switch(rewardLevel) {
			case 1:
				switch(Misc.random(3)) {
					case 0:
						rewardId = 995;
						rewardAmount = (Misc.random(3) + 1) * 500000;
					break;
					case 1:
						rewardId = 533;
						rewardAmount = (Misc.random(14) + 1) * 10;
					break;
					case 2:
						rewardId = 1624;
						rewardAmount = (Misc.random(9) + 1) * 10;
					break;
					case 3:
						rewardId = 1740;
						rewardAmount = (Misc.random(19) + 1) * 10;
					break;
					
				}
			break;
			case 2:
				switch(Misc.random(3)) {
					case 0:
						rewardId = 995;
						rewardAmount = (Misc.random(15) + 5) * 50000;
					break;
					case 1:
						rewardId = 537;
						rewardAmount = (Misc.random(14) + 6) * 10;
					break;
					case 2:
						rewardId = 1622;
						rewardAmount = (Misc.random(10) + 10) * 10;
					break;
					case 3:
						rewardId = 386;
						rewardAmount = (Misc.random(80) + 20) * 10;
					break;
					
				}
			break;
			case 3:
				switch(Misc.random(5)) {
					case 0:
						rewardId = 995;
						rewardAmount = (Misc.random(50) + 10) * 100000;
					break;
					case 1:
						rewardId = 537;
						rewardAmount = (Misc.random(100) + 30) * 10;
					break;
					case 2:
						rewardId = 4151;
						rewardAmount = 1;
					break;
					case 3:
						rewardId = 1053;
						rewardAmount = 1;
					break;
					case 4:
						rewardId = 1055;
						rewardAmount = 1;
					break;
					case 5:
						rewardId = 1057;
						rewardAmount = 1;
					break;
					case 6:
						rewardId = 6585;
						rewardAmount = 1;
					break;
				}
			break;
		}
	}
	
	public static void executeFirstEvent() {
		forceFirst = false;
		firstString = newFirstString();
		generateReward();
		firstEventInProgress = true;
		isRunning = true;
		lastEventTimer = 600;
		serverMessage(constructFirstYell());
	}
	
	public static void process() {
		if((Misc.random(12000) == 0 || forceFirst) && !firstEventInProgress)
			executeFirstEvent();
		if(lastEventTimer > 0)
			lastEventTimer --;
		if(lastEventTimer == 0)
			firstEventInProgress = false;
	}
	
	public static void serverMessage(String s) {
		for (int j = 0; j < main.model.players.PlayerHandler.players.length; j++) {
			if(main.model.players.PlayerHandler.players[j] != null) {
				Client c = (Client)main.model.players.PlayerHandler.players[j];
				c.sendMessage(s);
			}
		}
	}
	static int count1 = 11;
	static int i = 7;
	
	public static void eventEnded(Client c) {
		int Timer = count1 + i;
		//c.getPA().sendFrame126("vengtimer:" + Timer, 0);
			CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
				int count = 18;
				@Override
				public void execute(CycleEventContainer container) {
					if (count-- > 0) {
					if (count < 1 && isRunning == true) {
							container.stop();
						}
					}
				}
				@Override
				public void stop() {
					firstEventInProgress = false;
					isRunning = false;
					PublicEvent.lastEventTimer = 0;
					for (int j = 0; j < PlayerHandler.players.length; j++) {
						if (PlayerHandler.players[j] != null) {
							Client c2 = (Client)PlayerHandler.players[j];
						}
					}
					c.getPA().sendFrame126("", 0);
					c.sendMessage("<img=6><col=154360>The event is now over.</col>");
				} 
		
				
			}, 1);
		}
	}
	
