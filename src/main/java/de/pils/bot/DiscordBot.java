package de.pils.bot;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class DiscordBot extends ListenerAdapter {

    private static final Dotenv dotenv = Dotenv.load();
    private static final String DISCORD_BOT_TOKEN = dotenv.get("DISCORD_BOT_TOKEN");

    public static void main(String[] args) throws LoginException {
        MessageListener messageListener = new MessageListener();

        JDA jda = JDABuilder.createDefault(DISCORD_BOT_TOKEN)
                .addEventListeners(messageListener)
                .build();

        jda.getPresence().setActivity(Activity.playing("Vorlesungsplan"));
    }

}
