package main.model.players.commands;

import main.model.players.Client;
import main.model.players.PacketType;

public class CommandPacketHandler implements PacketType {
	
	@Override
	public void processPacket(Client player, int packetType, int packetSize) {
			String command = player.getInStream().readString();
			
			System.out.println(player.playerName + " has used the command: " + command);
		switch(player.playerRights){
		case 0:
			PlayerCommands.execute(player, command);
			break;
		case 1:
			PlayerCommands.execute(player, command);
			ModeratorCommands.execute(player, command);
			break;
		case 2:
			PlayerCommands.execute(player, command);
			ModeratorCommands.execute(player, command);
			AdministratorCommands.execute(player, command);
			break;
		case 3:
			PlayerCommands.execute(player, command);
			ModeratorCommands.execute(player, command);
			AdministratorCommands.execute(player, command);
			OwnerCommands.execute(player, command);
			DonatorCommands.execute(player, command);
			break;
		case 4:
			PlayerCommands.execute(player, command);
			DonatorCommands.execute(player, command);
			break;
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
			PlayerCommands.execute(player, command);
			break;
			
		}
	}

}