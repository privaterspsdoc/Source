/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Discord;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import main.Server;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

/**
 *
 * @author Abdul Hacker King wtf LMAO???
 */
public class Listener implements IListener<MessageReceivedEvent> {

    private IDiscordClient client = Server.client;
    HashMap<Long, Date> muted = new HashMap<Long, Date>();

    public Listener(IDiscordClient discordClient) {
        EventDispatcher dispatcher = discordClient.getDispatcher(); // Gets the client's event dispatcher
        dispatcher.registerListener(this); // Registers this bot as an event listener
    }

    @Override
    public void handle(MessageReceivedEvent event) {
        // Gets the channel in which this message was sent.
        try {
            doReply(event);
            // Builds (sends) and new message in the channel that the original message was sent with the content of the original message.
            //new MessageBuilder(client).withChannel(channel).withContent(message.getContent()).build();
        } catch (RateLimitException e) { // RateLimitException thrown. The bot is sending messages too quickly!
            System.err.print("Sending messages too quickly!");
            e.printStackTrace();
        } catch (DiscordException e) { // DiscordException thrown. Many possibilities. Use getErrorMessage() to see what went wrong.
            System.err.print(e.getErrorMessage()); // Print the error message sent by Discord
            e.printStackTrace();
        } catch (MissingPermissionsException e) { // MissingPermissionsException thrown. The bot doesn't have permission to send the message!
            System.err.print("Missing permissions for channel!");
            e.printStackTrace();
        }
    }

    public void doReply(MessageReceivedEvent event) {
        IMessage message = event.getMessage(); // Gets the message from the event object NOTE: This is not the content of the message, but the object itself
        IChannel channel = message.getChannel();
        MessageBuilder reply = new MessageBuilder(client);
        String[] sp = message.toString().split(" ");
        boolean admin = false;
        Calendar time = Calendar.getInstance();
        if (channel.isPrivate()) { 
        reply.withChannel(channel).withContent("I am only a bot! If you have questions please use the support channel!").build();
            return;
        }
        if (muted.containsKey(event.getAuthor().getLongID())) {
            if (time.getTime().before(muted.get(event.getAuthor().getLongID()))) {
                message.delete();
                long idk = muted.get(event.getAuthor().getLongID()).getTime() - time.getTimeInMillis();
                idk = TimeUnit.MILLISECONDS.toMinutes(idk);
                event.getAuthor().getOrCreatePMChannel().sendMessage("You are muted for another " + idk + " minutes.");
            } else {
                event.getAuthor().getOrCreatePMChannel().sendMessage("You have been unmuted. Please respect the community rules.");
                muted.remove(event.getAuthor().getLongID());
            }

        }
        for (IRole rolesForUser : event.getGuild().getRolesForUser(event.getAuthor())) {
            if (rolesForUser.getLongID() == 322904034539339777L) {
                admin = true;
            }
        }

        if (message.toString().equals("!test")) {
            reply.withChannel(channel).withContent("hi! " + channel.getID()).build();
        }

        if (message.toString().equals("!cleanup")) {
            if (admin) {
                channel.bulkDelete();
                  reply.withChannel(channel).withContent("Cleaned up.").build();
            } else {
                message.delete();
                event.getAuthor().getOrCreatePMChannel().sendMessage("You may not delete messages.");
            }
        }
        if (sp[0].toString().equals("!mute")) {
            int t = Integer.valueOf(sp[2]);
            doMute(event, t);

        } else if (sp[0].toString().equals("!unmute")) {
            for (IUser users : message.getMentions()) {
                muted.remove(users.getLongID());
                sendMessage("Unmuted " + users.getName());
            }
        } else if (sp[0].toString().equals("!chid")) { 
            reply.withChannel(channel).withContent(channel.getID()).build();
        }
    }

    private void doMute(MessageReceivedEvent event, int time) {
        IMessage message = event.getMessage();
        for (IUser users : message.getMentions()) {
            Date date = new Date();
            Calendar cl = Calendar.getInstance();
            cl.setTime(date);
            cl.add(Calendar.MINUTE, time);
            muted.put(users.getLongID(), cl.getTime());
            sendMessage("Muted " + users.getName() + " for " + time + " minutes.");
        }
    }

    public void sendMessage(String message) {
        MessageBuilder reply = new MessageBuilder(client);
        IChannel channel = client.getChannelByID(322903075239100418l);
        reply.withChannel(channel).withContent(message).build();
    }
    
    public void discMsg(String message, String ch)  { 
      long c = 0;
     switch (ch) { 
         case "main":
             c = 322903075239100418l;
      break;
         case "event": 
             c = 323627514616610816l;
             break;
             
         case "staff" : 
             c = 322905411403644930l;
             break;
         default:
             c = 322903075239100418l;
             break;
     }
        MessageBuilder reply = new MessageBuilder(client);
        IChannel channel = client.getChannelByID(c);
        reply.withChannel(channel).withContent(message).build();
    }
}
