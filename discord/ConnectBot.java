/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Discord;

import main.Server;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;

/**
 *
 * @author root
 */
public class ConnectBot {

    public static final String token = "MzIyMjczNDY2NTUwMzIxMTUy.DBqPbQ.stpWRa3J9BuuBBPQmmXBovtpqJs";
    private Listener b;

    public void start() {
        ClientBuilder builder = new ClientBuilder();
        builder.withToken(ConnectBot.token);
        Server.client = builder.login();
        b = new Listener(Server.client) {
        };
    }

    public void logout(IDiscordClient client) {
        client.logout();
    }

    public String getToken() {
        return token;
    }

    public Listener getBot() {
        return b;
    }

}
