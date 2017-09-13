package main.world;



import main.*;
import main.util.*;
import main.event.CycleEvent;
import main.event.CycleEventContainer;
import main.event.CycleEventHandler;
import main.model.items.*;
import main.model.players.*;

public class PublicEvent {
	
	public static String randomChar[] = {"a", "b", "c", "d", "e", "m", "g", "h", "i", "k", "x", "1", "2", "3", "_","%","@", "5","9","0"};
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
			player.eventPoints += rewardAmount;
				isRunning = false;
                               if (rewardAmount == 1) {
				serverMessage("<col=a230ff>"+player.playerName.substring(0,1).toUpperCase() + player.playerName.substring(1) + " has claimed "+rewardAmount+" event point, better luck next time!</col></img>");
                                } else {
                                serverMessage("<col=a230ff>"+player.playerName.substring(0,1).toUpperCase() + player.playerName.substring(1) + " has claimed "+rewardAmount+" event points, better luck next time!</col></img>");
                               }
                               
			rewardId = 0;
			rewardAmount = 0;
		}
	}
	
	public static String newFirstString() {
		String returns = "";
		while(returns.length() < 5)
			returns += randomChar[Misc.random(randomChar.length - 1)];
		return returns;
	}
	
	public static String constructFirstYell(Client c) {
           
           eventEnded(c);
		if(rewardAmount == 1)
			return "<col=a230ff>The first person to type </col><col=34495e>::"+firstString+"</col> <col=a230ff>will receive "+rewardAmount+" point!</col>";
		if(ItemAssistant.getItemName2(rewardId).endsWith("s"))
			return "<col=a230ff>The first person to type </col><col=34495e>::"+firstString+"</col> <col=a230ff>will receive "+rewardAmount+" event points!</col>";
		return "<col=a230ff>The first person to type </col><col=34495e>::"+firstString+"</col><col=a230ff0> will receive "+rewardAmount+" event points!</col>";
	}
	
	public static void generateReward(Client c) {
		int rewardLevel = Misc.random(50);
		if(rewardLevel < 40) {
			rewardLevel = 1;
                }
		switch(rewardLevel) {
			case 1:
				switch(Misc.random(13)) {
					case 0:
						         rewardAmount += 15;
					break;
					
                                }
			break;

		}
	
        }
	
	public static void executeFirstEvent() {
            Client c = (Client)main.model.players.PlayerHandler.players[0];
		forceFirst = false;
		firstString = newFirstString();
		generateReward(c);
		firstEventInProgress = true;
		isRunning = true;
		lastEventTimer = 600;
		serverMessage(constructFirstYell(c));
	}
	
	public static void process() {
		if((Misc.random(5) == 0 || forceFirst) && !firstEventInProgress)
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
					//c.sendMessage("<col=a230ff>Event over, nobody was quick enough in time.");
				} 
		
				
			}, 1);
		}
	}
	
