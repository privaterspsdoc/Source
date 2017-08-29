package main.model.players.packets;

import main.model.players.Client;
import main.model.players.PacketType;
import main.model.players.PlayerHandler;
import main.model.players.skills.SkillHandler;

/**
 * Walking packet
 **/
public class Walking implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		if (c.getAgility().doingAgility) {
			return;
		}
		/*
		if (c.openDuel) {
			System.out.println("debug5");
			return;
		}
		*/
		Client o = (Client) PlayerHandler.players[c.duelingWith];
		if (c.usingAltar)
			c.usingAltar = false;
		if (c.canChangeAppearance) {
			c.canChangeAppearance = false;
		}
		SkillHandler.isSkilling[12] = false;
		if (c.teleporting) {
			c.startAnimation(65535);
			c.teleporting = false;
			c.isRunning = false;
			c.gfx0(-1);
			c.startAnimation(-1);
		}
		/*
		 * if (c.loggedIn == 0) { c.getDH().sendDialogues(691, 2244); return; }
		 */
		c.walkingToItem = false;
		c.isWc = false;
		c.clickNpcType = 0;
		c.clickObjectType = 0;
        if(c.isShopping)
            c.isShopping = false;
		boolean bankCheck = false;
		if (c.isBanking)
			bankCheck  = false;
			c.isBanking = false;
		if (c.tradeStatus >= 0) {
			c.tradeStatus = 0;
		}
		if (c.inTrade) {
			c.getTradeAndDuel().declineTrade();
			c.sendMessage("You can't walk during a trade.");
			return;
		}
		if (packetType == 248 || packetType == 164) {
			c.clickObjectType = 0;
			c.clickNpcType = 0;
			c.faceUpdate(0);
			c.npcIndex = 0;
			c.playerIndex = 0;
			if (c.followId > 0 || c.followId2 > 0)
				c.getPA().resetFollow();
		}
		if (c.canWalk == false) {
			return;
		}
		c.getPA().removeAllWindows();
		if (c.duelRule[1] && c.duelStatus == 5) {
			if (PlayerHandler.players[c.duelingWith] != null) {
				if (!c.goodDistance(c.getX(), c.getY(),
						PlayerHandler.players[c.duelingWith].getX(),
						PlayerHandler.players[c.duelingWith].getY(), 1)
						|| c.attackTimer == 0) {
					c.sendMessage("Walking has been disabled in this duel!");
				}
			}
			c.playerIndex = 0;
			return;
		}
		if (c.stopPlayerSkill) {
			c.stopPlayerSkill = false;
		}

		if (c.freezeTimer > 0) {
			if (PlayerHandler.players[c.playerIndex] != null) {
				if (c.goodDistance(c.getX(), c.getY(),
						PlayerHandler.players[c.playerIndex].getX(),
						PlayerHandler.players[c.playerIndex].getY(), 1)
						&& packetType != 98) {
					c.playerIndex = 0;
					return;
				}
			}
			if (packetType != 98) {
				c.sendMessage("A magical force stops you from moving.");
				c.playerIndex = 0;
			}
			return;
		}

		if (System.currentTimeMillis() - c.lastSpear < 4000) {
			c.sendMessage("You have been stunned.");
			c.playerIndex = 0;
			return;
		}

		if (packetType == 98) {
			c.mageAllowed = true;
		}

		if (c.duelStatus == 6) {
			c.getTradeAndDuel().claimStakedItems();
			return;
		}

		if (c.duelStatus >= 1 && c.duelStatus <= 4) {
			c.duelStatus = 0;
			o.duelStatus = 0;
			// c.sendMessage("@red@The challange has been declined.");
			// o.sendMessage("@red@Other player has declined the challange.");
			// Misc.println("trade reset");
			o.getTradeAndDuel().declineDuel();
			c.getTradeAndDuel().declineDuel();
			// return;
		}

		if (c.respawnTimer > 3) {
			return;
		}
		if (c.inTrade) {
			return;
		}
		if (packetType == 248) {
			packetSize -= 14;
		}
		c.newWalkCmdSteps = (packetSize - 5) / 2;
		if (++c.newWalkCmdSteps > c.walkingQueueSize) {
			c.newWalkCmdSteps = 0;
			return;
		}

		c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;

		int firstStepX = c.getInStream().readSignedWordBigEndianA()
				- c.getMapRegionX() * 8;
		for (int i = 1; i < c.newWalkCmdSteps; i++) {
			c.getNewWalkCmdX()[i] = c.getInStream().readSignedByte();
			c.getNewWalkCmdY()[i] = c.getInStream().readSignedByte();
		}

		int firstStepY = c.getInStream().readSignedWordBigEndian()
				- c.getMapRegionY() * 8;
		c.setNewWalkCmdIsRunning((c.getInStream().readSignedByteC() == 1));
		// c.isResting = false;
		for (int i1 = 0; i1 < c.newWalkCmdSteps; i1++) {
			c.getNewWalkCmdX()[i1] += firstStepX;
			c.getNewWalkCmdY()[i1] += firstStepY;
		}
	}

}
