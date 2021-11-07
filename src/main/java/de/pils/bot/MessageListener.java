package de.pils.bot;

import lombok.SneakyThrows;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;

public class MessageListener extends ListenerAdapter {

    @SneakyThrows
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        System.out.println(msg);

        VorlesungService vorlesungService = new VorlesungService();

        if (msg.getContentRaw().equals("!ping")) {
            MessageChannel channel = event.getChannel();
            long time = System.currentTimeMillis();
            channel.sendMessage("Pong!") /* => RestAction<Message> */
                    .queue(response /* => Message */ -> {
                        response.editMessageFormat("Pong: %d ms", System.currentTimeMillis() - time).queue();
                    });
        } else if (msg.getContentRaw().equals("!next") || msg.getContentRaw().equals("!n") || msg.getContentRaw().equals("!nächste")) {

            Vorlesung nextVorlesung = vorlesungService.getNextVorlesung();

            msg.reply(nextVorlesung.toString()).queue();

        } else if (msg.getContentRaw().equals("!h") || msg.getContentRaw().equals("!heute") || msg.getContentRaw().equals("!today") || msg.getContentRaw().equals("!t0")) {
            ArrayList<Vorlesung> heutigeVorlesungen = vorlesungService.getHeutigeVorlesungen();

            String reply = "";
            for (Vorlesung vorlesung : heutigeVorlesungen) {
                reply += vorlesung;
                reply += "\n";
            }

            msg.reply(reply).queue();

        }
    }
}
