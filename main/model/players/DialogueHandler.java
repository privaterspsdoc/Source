package main.model.players;

import main.Server;

public class DialogueHandler {

	private Client c;

	public DialogueHandler(Client client) {
		this.c = client;
	}

	/**
	 * Handles all talking
	 * 
	 * @param dialogue
	 *            The dialogue you want to use
	 * @param npcId
	 *            The npc id that the chat will focus on during the chat
	 */
	public void sendDialogues(int dialogue, int npcId) {
		c.talkingNpc = npcId;
		switch (dialogue) {
		
		case 400:
			sendStatement("Hello, would you like your barrows repaired?");
			c.nextChat = 401;
			break;
		case 401:	
			sendOption2("Yes, I really need it!", "No, I cannot afford it!");
			c.nextChat = 0;
			break;		
		case 23:
			c.getDH().sendStartInfo("As you collect your reward, you notice an aweful smell.", "You look below the remaining debris to the bottom of the", "chest. You see a trapdoor. You open it and it leads to a ladder", "that goes down a long ways.", "Continue?");
			break;
		case 24:
			c.getDH().sendStatement("Would you like to continue?");
			c.nextChat = 25;
			break;
		case 25:
			c.dialogueAction = 25;
			c.getDH().sendOption2("Yes, I'm not afraid of anything!", "No way, the smell itself turns me away.");
			break;
		case 26:
			c.getDH().sendStatement("This is a very dangerous minigame, are you sure?");
			c.nextChat = 27;
			break;
		case 27:
			c.dialogueAction = 27;
			sendOption2("Yes, I'm a brave warrior!", "Maybe I shouldn't, I could lose my items!");
			break;
		case 28:
			c.getDH().sendStatement("Congratulations, "+c.playerName+". You've completed the barrows challenge & your reward has been delivered.");
			c.nextChat = 0;
			break;
		case 29:
			sendStatement("Are you ready to visit the chest room?");
			c.nextChat = 30;
			c.dialogueAction = 29;
			break;
		case 30:
			sendOption2("Yes, I've killed all the other brothers!", "No, I still need to kill more brothers");
			c.nextChat = 0;
			break;
		case 4315:
			sendNpcChat3("Oh, so you want me to skull you " + c.playerName + "..", "Give me a second to find my magic's..", "", c.npcType, "Emblem Trader");
			c.nextChat = 4316;
			break;
			
		case 4316:
			sendNpcChat2("Alright, I found my magic's..", "So, you want me to skull you?", c.npcType, "Emblem Trader");
			c.nextChat = 4317;
			break;
			
		case 4317:
			sendOption2("Yes, I want to @red@skull @bla@myself!", "No, I do not wanna be skulled and lose all items on death.");
			c.dialogueAction = 4343;
		break;
		
		case 324:
			
		break;
			
		case 325:
			
		break;
		case 655:
			sendOption5("Rock Crabs", "Skeletal Wyverns (30 Wild)", "Coming Soon", "Coming Soon", "Coming Soon");
			c.teleAction = 691;
			break;
		case 630:
			sendOption5("Warriors Guild", "Barbarian Assault", "", "", "");
			c.teleAction = 35;
			break;
		case 2000:
			c.getDH().sendNpcChat3("Hello!", "I am the the warrior guild guardian", "Do you wish to enter to claim a defender?",
					c.talkingNpc, "Kamfreena");
			c.nextChat = 2001;
		break;
		case 2001:
			c.getDH().sendNpcChat2("Be sure to equip your current defender.", "It allows you to obtain a better defender from a drop.",
					c.talkingNpc, "Kamfreena");
			c.nextChat = 2002;
		break;
		case 2002:
			c.getDH().sendOption2("Yes, I'll enter for 10 warrior tokens.", "No thanks, maybe another time.");
			c.dialogueAction = 2000;
		break;
		case 270:
			c.nextChat = 280;
			sendNpcChat2("Hello there, I'm the duo slayer master.",
					"How can I help you?", 1597, "Duo Slayer Master");
			break;
		case 280:
			c.dialogueAction = 8203;
			sendOption3(
					"Assign us a duo slayer task, please!",
					"I want to cancel our slayer task. (-10 Duo Points)",
					"           Never Mind            "
					);
			break;
		case 2244:
			sendNpcChat1("Do you want change your spellbooks?", c.talkingNpc,
					"High Priest");
			c.nextChat = 2245;
			break;
		case 2245:
			c.getDH().sendOption3(
					"Teleport me to Lunar Island, for lunars spellbook!",
					"Teleport me to Desert Pyramid, for ancients spellbook!",
					"No thanks, i will stay here.");
			c.dialogueAction = 2245;
			c.nextChat = 0;
			break;
		case 69:
			c.getDH().sendNpcChat1(
					"Hello! Do you want to choose your clothes?", c.talkingNpc,
					"Thessalia");
			c.sendMessage("@red@You must right-click Thessalia to change your clothes.");
			c.nextChat = 0;
			break;
		case 6969:
			c.getDH().sendNpcChat2("I'm not working right now sir.",
					"If you wan't me to work, talk to Zaros give me a job.",
					c.talkingNpc, "Unemployed");
			c.sendMessage("This NPC do not have an action, if you have any suggestion for this NPC, post on forums.");
			c.nextChat = 0;
			break;
		/* LOGIN 1st TIME */
		case 769:
			c.getDH().sendNpcChat2("Welcome to Zaros!",
					"You must select your starter package.", c.talkingNpc,
					"Zaros");
			c.nextChat = 770;
			break;
		case 770:
			sendStatement("Remember we're on beta, we will have a reset before official release.");
			c.nextChat = 771;
			break;
		case 771:
			c.getDH().sendOption3("Master (levels & items)",
					"Zerker (levels & items)", "Pure (levels & items)");
			c.dialogueAction = 771;
			break;
		/* END LOGIN */
		case 691:
			c.getDH().sendNpcChat2("Welcome to 2007remake.",
					"Please, read what i've to tell you...", c.talkingNpc,
					"Zaros");
			c.nextChat = 692;
			// c.loggedIn = 1;
			break;
		case 692:
			sendOption2("Yes, lock my experience.", "No, don't lock my experience.");
			c.dialogueAction = 692;
			break;
		case 693:
			sendNpcChat4("We will have economy reset and,",
					"this commands will be removed too...",
					"Please, report glitches, and post suggestions",
					"on forums, for i can code, and we get 100% ready!",
					c.talkingNpc, "Zaros");
			c.sendMessage("@red@You're online in 2007remake pre-alpha.");
			c.sendMessage("@red@Pre-alpha's to find glitches, and post suggestions in forums...");
			c.sendMessage("@red@Then our developer 'Zaros' can code it, and we get official release in less time.");
			c.sendMessage("@red@Thanks for your attention sir.");
			c.nextChat = 0;
			break;
		/* AL KHARID */
		case 1022:
			c.getDH().sendPlayerChat1("Can I come through this gate?");
			c.nextChat = 1023;
			break;

		case 1023:
			c.getDH().sendNpcChat1(
					"You must pay a toll of 10 gold coins to pass.",
					c.talkingNpc, "Border Guard");
			c.nextChat = 1024;
			break;
		case 1024:
			c.getDH().sendOption3("Okay, I'll pay.",
					"Who does my money go to?", "No thanks, I'll walk around.");
			c.dialogueAction = 502;
			break;
		case 1025:
			c.getDH().sendPlayerChat1("Okay, I'll pay.");
			c.nextChat = 1026;
			break;
		case 1026:
			c.getDH().sendPlayerChat1("Who does my money go to?");
			c.nextChat = 1027;
			break;
		case 1027:
			c.getDH().sendNpcChat2("The money goes to the city of Al-Kharid.",
					"Will you pay the toll?", c.talkingNpc, "Border Guard");
			c.nextChat = 1028;
			break;
		case 1028:
			c.getDH().sendOption2("Okay, I'll pay.",
					"No thanks, I'll walk around.");
			c.dialogueAction = 508;
			break;
		case 1029:
			c.getDH().sendPlayerChat1("No thanks, I'll walk around.");
			c.nextChat = 0;
			break;

		case 1030:
			if (!c.getItems().playerHasItem(995, 10)) {
				c.getDH().sendPlayerChat1("I haven't got that much.");
				c.nextChat = 0;
			} else {
				c.getDH().sendNpcChat1(
						"As you wish. Don't get too close to the scorpions.",
						c.talkingNpc, "Border Guard");
				c.getItems().deleteItem2(995, 10);
				c.sendMessage("You pass the gate.");
				Special.movePlayer(c);
				Special.openKharid(c, c.objectId);
				c.turnPlayerTo(c.objectX, c.objectY);
				c.nextChat = 0;
			}
			break;

		case 1031:
			c.getDH().sendNpcChat1(
					"As you wish. Don't get too close to the scopions.",
					c.talkingNpc, "Border Guard");
			c.getItems().deleteItem2(995, 10);
			c.sendMessage("You pass the gate.");
			Special.movePlayer(c);
			Special.openKharid(c, c.objectId);
			c.turnPlayerTo(c.objectX, c.objectY);
			c.nextChat = 0;
			break;

		case 22:
			sendOption2("Pick the flowers", "Leave the flowers");
			c.nextChat = 0;
			c.dialogueAction = 22;
			break;
		/** Bank Settings **/
		case 1013:
			c.getDH().sendNpcChat1("Good day. How may I help you?",
					c.talkingNpc, "Banker");
			c.nextChat = 1014;
			break;
		case 1014:// bank open done, this place done, settings done, to do
					// delete pin
			c.getDH().sendOption3(
					"I'd like to access my bank account, please.",
					"I'd like to check my my P I N settings.",
					"What is this place?");
			c.dialogueAction = 251;
			break;
		/** What is this place? **/
		case 1015:
			c.getDH().sendPlayerChat1("What is this place?");
			c.nextChat = 1016;
			break;
		case 1016:
			c.getDH().sendNpcChat2("This is the bank of 2007remake.",
					"We have many branches in many towns.", c.talkingNpc,
					"Banker");
			c.nextChat = 0;
			break;
		case 200:
			c.getDH().sendOption5("Rock Crabs", "Slayer Monsters", "Slayer Tower", "Brimhaven Dungeon", "Taverley Dungeon");
			c.dialogueAction = 200;
		break;
		
		case 201:
			c.getDH().sendOption5("King Black Dragon", "Chaos Elemental", "Barrelchest Boss", "Godwars Dungeon", "More");
			c.dialogueAction = 201;
		break;
		
		case 202:
			c.getDH().sendOption4("Dagganoth Kings", "Kalphite Queen", "Giant Mole", "Coporeal Beast");
			c.dialogueAction = 202;
		break;
		
		case 203:
			c.getDH().sendOption5("Barrows", "Pest Control", "Jad Mini-Game", "Duel Arena", "Barbarian Assault");
			c.dialogueAction = 203;
		break;
		/**
		 * Note on P I N. In order to check your "Pin Settings. You must have
		 * enter your Bank Pin first
		 **/
		/** I don't know option for Bank Pin **/
		case 1017:
			c.getDH()
					.sendStartInfo(
							"Since you don't know your P I N, it will be deleted in @red@3 days@bla@. If you",
							"wish to cancel this change, you may do so by entering your P I N",
							"correctly next time you attempt to use your bank.",
							"", "", false);
			c.nextChat = 0;
			break;
		case 0:
			c.talkingNpc = -1;
			c.getPA().removeAllWindows();
			c.nextChat = 0;
			break;
		case 1:
			sendStatement("You found a hidden tunnel! Do you want to enter it?");
			c.dialogueAction = 1;
			c.nextChat = 2;
			break;
		case 2:
			sendOption2("Yea! I'm fearless!", "No way! That looks scary!");
			c.dialogueAction = 1;
			c.nextChat = 0;
			break;
		case 3:
			sendNpcChat4(
					"Hello!",
					"My name is Duradel and I am a master of the slayer skill.",
					"I can assign you a slayer task suitable to your combat level.",
					"Would you like a slayer task?", c.talkingNpc, "Duradel");
			c.nextChat = 4;
			break;
		case 5:
			sendNpcChat4("Hello adventurer...",
					"My name is Kolodion, the master of this mage bank.",
					"Would you like to play a minigame in order ",
					"to earn points towards recieving magic related prizes?",
					c.talkingNpc, "Kolodion");
			c.nextChat = 6;
			break;
		case 6:
			sendNpcChat4("The way the game works is as follows...",
					"You will be teleported to the wilderness,",
					"You must kill mages to recieve points,",
					"redeem points with the chamber guardian.", c.talkingNpc,
					"Kolodion");
			c.nextChat = 15;
			break;
		case 11:
			sendNpcChat4(
					"Hello!",
					"My name is Duradel and I am a master of the slayer skill.",
					"I can assign you a slayer task suitable to your combat level.",
					"Would you like a slayer task?", c.talkingNpc, "Duradel");
			c.nextChat = 12;
			break;
		case 12:
			sendOption4("Yes I would like a slayer task.", "Yes I'll take an elite task!", "", "No I would not like a slayer task.");
			c.dialogueAction = 5;
			c.nextChat = 0;
			break;
		case 13:
			sendNpcChat4(
					"Hello!",
					"My name is Duradel and I am a master of the slayer skill.",
					"I see I have already assigned you a task to complete.",
					"Would you like me to give you an easier task?",
					c.talkingNpc, "Duradel");
			c.nextChat = 14;
			break;
		case 14:
			sendOption2("Yes I would like an easier task.",
					"No I would like to keep my task.");
			c.dialogueAction = 6;
			c.nextChat = 0;
			break;
		case 15:
			sendOption2("Yes I would like to play",
					"No, sounds too dangerous for me.");
			c.dialogueAction = 7;
			break;
		case 16:
			sendOption2("I would like to reset my barrows brothers.",
					"I would like to fix all my barrows");
			c.dialogueAction = 8;
			break;
		case 17:
			sendOption5("Air", "Mind", "Water", "Earth", "More");
			c.dialogueAction = 10;
			c.dialogueId = 17;
			c.teleAction = -1;
			break;
		case 18:
			sendOption5("Fire", "Body", "Cosmic", "Astral", "More");
			c.dialogueAction = 11;
			c.dialogueId = 18;
			c.teleAction = -1;
			break;
		case 19:
			sendOption5("Nature", "Law", "Death", "Blood", "More");
			c.dialogueAction = 12;
			c.dialogueId = 19;
			c.teleAction = -1;
			break;
		case 54:
			sendOption2("Buy Vesta's Spear", "I can't afford that");
			c.dialogueAction = 51;
			break;
		case 56:
			sendStatement("Hello " + c.playerName + ", you currently have "
					+ c.pkPoints + " PK points.");
			break;

		case 57:
			c.getPA().sendFrame126("Teleport to shops?", 2460);
			c.getPA().sendFrame126("Yes.", 2461);
			c.getPA().sendFrame126("No.", 2462);
			c.getPA().sendFrame164(2459);
			c.dialogueAction = 27;
			break;
		
		case 1000:
		sendNpcChat4(
					"Hello!",
					"My name is Duradel and I am a master of the slayer skill.",
					"I see I have already assigned you a task to complete.",
					"Would you like me to reset the task for 5 slayer points?",
					c.talkingNpc, "Duradel");
			c.nextChat = 1001;
		break;
		
		case 1001:
		c.dialogueAction = 105;
		sendOption2("Yes please!", "No that is rather pricey.");
		c.nextChat = 0;
		break;

		/**
		 * Recipe for disaster - Sir Amik Varze
		 **/

		/*case 25:
			sendOption2("Yes", "No");
			c.rfdOption = true;
			c.nextChat = 0;
			break;
		case 26:
			sendPlayerChat1("Yes");
			c.nextChat = 28;
			break;
		case 27:
			sendPlayerChat1("No");
			c.nextChat = 29;
			break;

		case 29:
			c.getPA().removeAllWindows();
			c.nextChat = 0;
			break;
		case 30:
			sendNpcChat4("Congratulations!",
					"You have defeated all Recipe for Disaster bosses",
					"and have now gained access to the Culinaromancer's chest",
					"and the Culinaromancer's item store.", c.talkingNpc,
					"Sir Amik Varze");
			c.nextChat = 0;
			PlayerSave.saveGame(c);
			break;*/
		case 31:
			sendNpcChat4("", "You have been defeated!", "You made it to round "
					+ c.roundNpc, "", c.talkingNpc, "Sir Amik Varze");
			c.roundNpc = 0;
			c.nextChat = 0;
			break;

		/**
		 * Horror from the deep
		 **/
		case 32:
			sendNpcChat4("", "Would you like to start the quest",
					"Horror from the Deep?", "", c.talkingNpc, "Jossik");
			c.nextChat = 33;
			break;
		case 33:
			sendNpcChat4("", "You will have to be able to defeat a level-100 ",
					"Dagannoth mother with different styles of attacks.", "",
					c.talkingNpc, "Jossik");
			c.nextChat = 34;
			break;
		case 34:
			sendOption2("Yes I am willing to fight!",
					"No thanks, I am not strong enough.");
			c.horrorOption = true;
			break;
		case 35:
			sendPlayerChat1("Yes I am willing to fight!");
			c.nextChat = 37;
			break;
		case 36:
			sendPlayerChat1("No thanks, I am not strong enough.");
			c.nextChat = 0;
			break;
		case 37:
			c.horrorFromDeep = 1;
			c.height = (c.playerId * 4);
			c.getPA().movePlayer(2515, 10008, c.height);
			Server.npcHandler.spawnNpc(c, 1351, 2521, 10024, c.height, 0, 100,
					16, 75, 75, true, true);
			c.getPA().removeAllWindows();
			c.getPA().loadQuests();
			c.inHfd = true;
			break;

		/**
		 * Desert Treasure dialogue
		 */
		case 41:
			sendNpcChat4("", "Do you want to start the quest",
					"Desert treasure?", "", c.talkingNpc, "Archaeologist");
			c.nextChat = 42;
			break;
		case 42:
			sendNpcChat4("", "You will have to fight four high level bosses,",
					"after each boss you will be brought back",
					"here to refill your supplies if it is needed.",
					c.talkingNpc, "Archaeologist");
			c.nextChat = 43;
			break;
		case 43:
			sendOption2("Yes I want to fight!", "No thanks, I am not ready.");
			c.dtOption = true;
			break;
		case 44:
			sendPlayerChat1("Yes I want to fight!");
			c.nextChat = 51;
			break;
		case 45:
			sendPlayerChat1("No thanks, I am not ready.");
			c.nextChat = 0;
			break;

		case 48:
			sendOption2("Yes, I am ready!", "No, I am not ready.");
			c.dtOption2 = true;
			break;
		case 49:
			sendPlayerChat1("Yes, I am ready!");
			c.nextChat = 52;
			break;
		case 50:
			sendPlayerChat1("No, I am not ready.");
			c.nextChat = 0;
			break;
		case 51:
			c.desertT++;
			c.height = (c.playerId * 4);
			c.getPA().movePlayer(3310, 9376, c.height);
			Server.npcHandler.spawnNpc(c, 1977, 3318, 9376, c.height, 0, 130,
					40, 70, 90, true, true);
			c.getPA().removeAllWindows();
			c.getPA().loadQuests();
			c.inDt = true;
			break;

		/**
		 * Cook's Assistant
		 */
		case 100:
			sendNpcChat1("What am I to do?", c.talkingNpc, "Cook");
			c.nextChat = 101;
			break;
		case 101:
			sendOption4("What`s wrong?", "Can you make me a cake?",
					"You don`t look very happy.", "Nice hat!");
			c.caOption4a = true;
			c.caPlayerTalk1 = true;
			break;
		case 102:
			sendPlayerChat1("What`s wrong?");
			c.nextChat = 103;
			break;
		case 103:
			sendNpcChat3(
					"Oh dear, oh dear, oh dear, Im in a terrible terrible",
					"mess! It`s the Duke`s birthday today, and I should be",
					"making him a lovely big birthday cake.", c.talkingNpc,
					"Cook");
			c.nextChat = 104;
			break;
		case 104:
			sendNpcChat4(
					"I`ve forgotten to buy the ingredients. I`ll never get",
					"them in time now. He`ll sack me! What will I do? I have",
					"four children and a goat to look after. Would you help",
					"me? Please?", c.talkingNpc, "Cook");
			c.nextChat = 105;
			break;
		case 105:
			sendOption2("Im always happy to help an cook in distress.",
					"I can`t right now, Maybe later.");
			c.caOption2 = true;
			break;
		case 106:
			c.cooksA++;
			c.getPA().loadQuests();
			sendPlayerChat1("Yes, I`ll help you.");
			c.nextChat = 107;
			break;
		case 107:
			sendNpcChat2("Oh thank you, thank you. I need milk, an egg and",
					"flour. I`d be very grateful if you can get them for me.",
					c.talkingNpc, "Cook");
			c.nextChat = 108;
			break;
		case 108:
			sendOption4("Where do I find some flour.", "How about some milk?",
					"And eggs? where are they found?",
					"Actually, I know where to find these stuff.");
			c.caOption4c = true;
			c.caOption4b = true;
			break;
		case 109:
			sendNpcChat1(
					"How are you getting on with finding the ingredients?",
					c.talkingNpc, "Cook");
			c.nextChat = 110;
			break;
		case 110:
			sendPlayerChat1("Here's a bucket of milk.");
			c.getItems().deleteItem(1927, 1);
			c.nextChat = 111;
			break;
		case 111:
			sendPlayerChat1("Here's a pot of flour.");
			c.getItems().deleteItem(1933, 1);
			c.nextChat = 112;
			break;
		case 112:
			c.cooksA++;
			c.getPA().loadQuests();
			sendPlayerChat1("Here's a fresh egg.");
			c.getItems().deleteItem(1944, 1);
			c.nextChat = 113;
			break;
		case 113:
			sendNpcChat2("You've brough me everything I need! I am saved!",
					"Thank you!", c.talkingNpc, "Cook");
			c.nextChat = 0;
			break;
		/*
		 * case 114: sendPlayerChat1("So do I get to go the Duke's Party?");
		 * c.nextChat = 115; break; case 115:
		 * sendNpcChat2("I'm afraid not, only the big cheeses get to dine with the"
		 * , "Duke.", c.talkingNpc, "Cook"); c.nextChat = 116; break; case 116:
		 * sendPlayerChat2
		 * ("Well, maybe one day I'll be important enough to sit on",
		 * "the Duke's table."); c.nextChat = 117; break; case 117:
		 * sendNpcChat1("Maybe, but I won't be holding my breath.",
		 * c.talkingNpc, "Cook"); c.cooksA++; c.cooksA++;
		 * c.getPA().loadQuests(); c.getAA2().COOK2(); c.nextChat = 0; break;
		 */

		// ** Getting Items - Cook's Assistant **//
		case 118:
			sendNpcChat3("There`s a mill fairly close, Go North then West.",
					"Mill Lane Mill is just off the road to Draynor. I",
					"usually get my flour from there.", c.talkingNpc, "Cook");
			c.nextChat = 119;
			break;
		case 119:
			sendNpcChat2(
					"Talk to Millie, she`ll help, she`s a lovely girl and a fine",
					"Miller.", c.talkingNpc, "Cook");
			c.nextChat = 108;
			break;
		case 120:
			sendNpcChat2(
					"There is a cattle field on the other side of the river,",
					"just across the road from the Groats` Farm.",
					c.talkingNpc, "Cook");
			c.nextChat = 121;
			break;
		case 121:
			sendNpcChat3(
					"Talk to Gillie Groats, she looks after the Dairy Cows -",
					"She`ll tell you everything you need to know about",
					"milking cows!", c.talkingNpc, "Cook");
			c.nextChat = 108;
			break;
		case 122:
			sendNpcChat2("I normally get my eggs from the Groats` farm on the",
					"other side of the river.", c.talkingNpc, "Cook");
			c.nextChat = 123;
			break;
		case 123:
			sendNpcChat1("But any chicken should lay eggs.", c.talkingNpc,
					"Cook");
			c.nextChat = 108;
			break;
		case 124:
			sendPlayerChat1("Actually, I know where to find these stuff");
			c.nextChat = 0;
			break;
		case 125:
			sendPlayerChat1("You're a cook why, don't you bake me a cake?");
			c.nextChat = 126;
			break;
		case 126:
			sendNpcChat1("*sniff* Dont talk to me about cakes...",
					c.talkingNpc, "Cook");
			c.nextChat = 102;
			break;
		case 127:
			sendPlayerChat1("You don't look very happy.");
			c.nextChat = 128;
			break;
		case 128:
			sendNpcChat2(
					"No, I`m not. The world is caving in around me - I am",
					"overcome by dark feelings of impending doom.",
					c.talkingNpc, "Cook");
			c.nextChat = 129;
			break;
		case 129:
			sendOption2("What's wrong?",
					"I'd take off the rest of the day if I were you.");
			c.caOption2a = true;
			break;
		case 130:
			sendPlayerChat1("Nice hat!");
			c.nextChat = 131;
			break;
		case 131:
			sendNpcChat1(
					"Err thank you. It`s a pretty ordinary cook`s hat really.",
					c.talkingNpc, "Cook");
			c.nextChat = 132;
			break;
		case 132:
			sendPlayerChat1("Still, suits you. The trousers are pretty special too.");
			c.nextChat = 133;
			break;
		case 133:
			sendNpcChat1("It`s all standard cook`s issue uniform...",
					c.talkingNpc, "Cook");
			c.nextChat = 134;
			break;
		case 134:
			sendPlayerChat2(
					"The whole hat, apron, stripey trousers ensemble -",
					"it works. It makes you look like a real cook.");
			c.nextChat = 135;
			break;
		case 135:
			sendNpcChat2(
					"I am a real cook!, I haven`t got time to be chatting",
					"about Culinary Fashion. I`m in desperate need of help.",
					c.talkingNpc, "Cook");
			c.nextChat = 102;
			break;
		case 136:
			sendPlayerChat1("I'd take off the rest of the day if I were you.");
			c.nextChat = 137;
			break;
		case 137:
			sendNpcChat2(
					"No, that`s the worst thing I could do. I`d get in terrible",
					"trouble.", c.talkingNpc, "Cook");
			c.nextChat = 138;
			break;
		case 138:
			sendPlayerChat1("Well maybe you need to take a holiday...");
			c.nextChat = 139;
			break;
		case 139:
			sendNpcChat2(
					"That would be nice but the duke doesn`t allow holidays",
					"for core staff.", c.talkingNpc, "Cook");
			c.nextChat = 140;
			break;
		case 140:
			sendPlayerChat2("Hmm, why not run away to the sea and start a new",
					"life as a Pirate.");
			c.nextChat = 141;
			break;
		case 141:
			sendNpcChat2(
					"My wife gets sea sick, and i have an irrational fear of",
					"eyepatches. I don`t see it working myself.", c.talkingNpc,
					"Cook");
			c.nextChat = 142;
			break;
		case 142:
			sendPlayerChat1("I`m afraid I've run out of ideas.");
			c.nextChat = 143;
			break;
		case 143:
			sendNpcChat1("I know I`m doomed.", c.talkingNpc, "Cook");
			c.nextChat = 102;
			break;

		//

		case 144:
			sendNpcChat1("Nice day, isn't it?", c.talkingNpc, "");
			c.nextChat = 0;
			break;

		/*
		 * Doric's Quest
		 */

		case 300:
			sendNpcChat1("Why hello there adventurer, how can I help you?",
					c.talkingNpc, "Doric");
			c.nextChat = 301;
			break;

		case 301:
			sendOption3("I'm looking for a quest.", "Nice place you got here.",
					"Just passing by.");
			c.doricOption = true;
			break;

		case 299:
			sendPlayerChat1("I'm just passing by.");
			c.nextChat = 302;
			break;

		case 302:
			sendNpcChat1("Very well, so long.", c.talkingNpc, "Doric");
			c.nextChat = 0;
			break;

		case 303:
			sendPlayerChat1("Nice place you got here.");
			c.nextChat = 304;
			break;

		case 304:
			sendNpcChat1("Why thank you kind sir.", c.talkingNpc, "Doric");
			c.nextChat = 305;
			break;

		case 305:
			sendPlayerChat1("My pleasure.");
			c.nextChat = 0;
			break;

		case 306:
			sendPlayerChat1("I'm looking for a quest.");
			c.nextChat = 307;
			break;

		case 307:
			sendNpcChat2("A quest you say? Hmm...",
					"Can you run me a quick errand?", c.talkingNpc, "Doric");
			c.nextChat = 308;
			break;

		case 308:
			sendOption2("Of course.", "I need to go.");
			c.doricOption2 = true;
			break;

		case 309:
			sendPlayerChat1("I need to go.");
			c.nextChat = 0;
			break;

		case 310:
			sendPlayerChat1("Of course!");
			c.nextChat = 311;
			break;

		case 311:
			sendNpcChat3("Very good! I need some materials for a new ",
					"pickaxe I'm working on, is there any way you ",
					"could go get these?", c.talkingNpc, "Doric");
			c.nextChat = 312;
			break;

		case 312:
			sendPlayerChat1("Sure, what materials?");
			c.nextChat = 313;
			break;

		case 313:
			sendNpcChat3("6 lumps of clay,", "4 copper ores,",
					"and 2 iron ores.", c.talkingNpc, "Doric");
			c.nextChat = 314;
			break;

		case 314:
			sendPlayerChat1("Sounds good, I will be back soon!");
			c.nextChat = 315;
			c.doricQuest = 5;
			break;

		case 315:
			sendNpcChat1("Thank you adventurer, hurry back!", c.talkingNpc,
					"Doric");
			c.nextChat = 0;
			break;

		case 316:
			sendNpcChat1("Have you got all the materials yet?", c.talkingNpc,
					"Doric");
			c.nextChat = 317;
			break;

		case 317:
			sendPlayerChat1("Not all of them.");
			c.nextChat = 0;
			break;

		case 318:
			sendNpcChat1("Have you got all the materials yet?", c.talkingNpc,
					"Doric");
			c.nextChat = 319;
			break;

		case 319:
			sendPlayerChat1("Yep! Right here.");
			c.nextChat = 320;
			c.getItems().deleteItem(434, 6);
			c.getItems().deleteItem(436, 4);
			c.getItems().deleteItem(440, 2);
			break;

		case 320:
			sendNpcChat2("Thank you so much adventurer, heres a reward",
					"for any hardships you may have encountered.",
					c.talkingNpc, "Doric");
			c.nextChat = 0;
			c.sendMessage("Congradulations, you have completed Doric's Quest!");
			break;

		case 321:
			sendNpcChat1("Welcome to my home, feel free to use my anvils!",
					c.talkingNpc, "Doric");
			c.nextChat = 0;
			break;
		}
	}

	/*
	 * Information Box
	 */

	public void sendStartInfo(String text, String text1, String text2,
			String text3, String title) {
		c.getPA().sendFrame126(title, 6180);
		c.getPA().sendFrame126(text, 6181);
		c.getPA().sendFrame126(text1, 6182);
		c.getPA().sendFrame126(text2, 6183);
		c.getPA().sendFrame126(text3, 6184);
		c.getPA().sendFrame164(6179);
	}

	/*
	 * Options
	 */

	public void sendOption(String s) {
		c.getPA().sendFrame126("Select an Option", 2470);
		c.getPA().sendFrame126(s, 2471);
		c.getPA().sendFrame126("Click here to continue", 2473);
		c.getPA().sendFrame164(13758);
	}

	public void sendOption2(String s, String s1) {
		c.getPA().sendFrame126("Select an Option", 2460);
		c.getPA().sendFrame126(s, 2461);
		c.getPA().sendFrame126(s1, 2462);
		c.getPA().sendFrame164(2459);
	}

	public void sendOption3(String s, String s1, String s2) {
		c.getPA().sendFrame126("Select an Option", 2470);
		c.getPA().sendFrame126(s, 2471);
		c.getPA().sendFrame126(s1, 2472);
		c.getPA().sendFrame126(s2, 2473);
		c.getPA().sendFrame164(2469);
	}

	public void sendOption4(String s, String s1, String s2, String s3) {
		c.getPA().sendFrame126("Select an Option", 2481);
		c.getPA().sendFrame126(s, 2482);
		c.getPA().sendFrame126(s1, 2483);
		c.getPA().sendFrame126(s2, 2484);
		c.getPA().sendFrame126(s3, 2485);
		c.getPA().sendFrame164(2480);
	}

	public void sendOption5(String s, String s1, String s2, String s3, String s4) {
		c.getPA().sendFrame126("Select an Option", 2493);
		c.getPA().sendFrame126(s, 2494);
		c.getPA().sendFrame126(s1, 2495);
		c.getPA().sendFrame126(s2, 2496);
		c.getPA().sendFrame126(s3, 2497);
		c.getPA().sendFrame126(s4, 2498);
		c.getPA().sendFrame164(2492);
	}

	public void sendStartInfo(String text, String text1, String text2,
			String text3, String title, boolean send) {
		c.getPA().sendFrame126(title, 6180);
		c.getPA().sendFrame126(text, 6181);
		c.getPA().sendFrame126(text1, 6182);
		c.getPA().sendFrame126(text2, 6183);
		c.getPA().sendFrame126(text3, 6184);
		c.getPA().sendFrame164(6179);
	}

	/*
	 * Statements
	 */

	public void sendStatement(String s) { // 1 line click here to continue chat
											// box interface
		c.getPA().sendFrame126(s, 357);
		c.getPA().sendFrame126("Click here to continue", 358);
		c.getPA().sendFrame164(356);
	}

	/*
	 * Npc Chatting
	 */

	private void sendNpcChat1(String s, int ChatNpc, String name) {
		c.getPA().sendFrame200(4883, 591);
		c.getPA().sendFrame126(name, 4884);
		c.getPA().sendFrame126(s, 4885);
		c.getPA().sendFrame75(ChatNpc, 4883);
		c.getPA().sendFrame164(4882);
	}

	public void sendNpcChat2(String s, String s1, int ChatNpc, String name) {
		c.getPA().sendFrame200(4888, 591);
		c.getPA().sendFrame126(name, 4889);
		c.getPA().sendFrame126(s, 4890);
		c.getPA().sendFrame126(s1, 4891);
		c.getPA().sendFrame75(ChatNpc, 4888);
		c.getPA().sendFrame164(4887);
	}

	private void sendNpcChat3(String s, String s1, String s2, int ChatNpc,
			String name) {
		c.getPA().sendFrame200(4894, 591);
		c.getPA().sendFrame126(name, 4895);
		c.getPA().sendFrame126(s, 4896);
		c.getPA().sendFrame126(s1, 4897);
		c.getPA().sendFrame126(s2, 4898);
		c.getPA().sendFrame75(ChatNpc, 4894);
		c.getPA().sendFrame164(4893);
	}

	private void sendNpcChat4(String s, String s1, String s2, String s3,
			int ChatNpc, String name) {
		c.getPA().sendFrame200(4901, 591);
		c.getPA().sendFrame126(name, 4902);
		c.getPA().sendFrame126(s, 4903);
		c.getPA().sendFrame126(s1, 4904);
		c.getPA().sendFrame126(s2, 4905);
		c.getPA().sendFrame126(s3, 4906);
		c.getPA().sendFrame75(ChatNpc, 4901);
		c.getPA().sendFrame164(4900);
	}

	/*
	 * Player Chating Back
	 */

	private void sendPlayerChat1(String s) {
		c.getPA().sendFrame200(969, 591);
		c.getPA().sendFrame126(c.playerName, 970);
		c.getPA().sendFrame126(s, 971);
		c.getPA().sendFrame185(969);
		c.getPA().sendFrame164(968);
	}

	private void sendPlayerChat2(String s, String s1) {
		c.getPA().sendFrame200(974, 591);
		c.getPA().sendFrame126(c.playerName, 975);
		c.getPA().sendFrame126(s, 976);
		c.getPA().sendFrame126(s1, 977);
		c.getPA().sendFrame185(974);
		c.getPA().sendFrame164(973);
	}

	private void sendPlayerChat3(String s, String s1, String s2) {
		c.getPA().sendFrame200(980, 591);
		c.getPA().sendFrame126(c.playerName, 981);
		c.getPA().sendFrame126(s, 982);
		c.getPA().sendFrame126(s1, 983);
		c.getPA().sendFrame126(s2, 984);
		c.getPA().sendFrame185(980);
		c.getPA().sendFrame164(979);
	}

	private void sendPlayerChat4(String s, String s1, String s2, String s3) {
		c.getPA().sendFrame200(987, 591);
		c.getPA().sendFrame126(c.playerName, 988);
		c.getPA().sendFrame126(s, 989);
		c.getPA().sendFrame126(s1, 990);
		c.getPA().sendFrame126(s2, 991);
		c.getPA().sendFrame126(s3, 992);
		c.getPA().sendFrame185(987);
		c.getPA().sendFrame164(986);
	}
}
