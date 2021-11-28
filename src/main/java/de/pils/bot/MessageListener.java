package de.pils.bot;

import de.pils.bot.commands.HeuteCommand;
import de.pils.bot.commands.NextCommand;
import de.pils.bot.commands.PingCommand;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {

    @SneakyThrows
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        System.out.println("Got new message: " + msg);

        if (msg.getContentRaw().equals("!ping")) {
            PingCommand.handleCommand(event);

        } else if (msg.getContentRaw().equals("!next") || msg.getContentRaw().equals("!n") || msg.getContentRaw().equals("!nächste")) {
            NextCommand.handleCommand(msg);

        } else if (msg.getContentRaw().equals("!h") || msg.getContentRaw().equals("!heute") || msg.getContentRaw().equals("!today") || msg.getContentRaw().equals("!t0")) {
            HeuteCommand.handleCommand(msg);
        }

    }

}
