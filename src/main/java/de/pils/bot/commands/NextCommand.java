package de.pils.bot.commands;

import de.pils.bot.Vorlesung;
import de.pils.bot.VorlesungService;
import net.dv8tion.jda.api.entities.Message;

public class NextCommand {

    public static void handleCommand(Message msg) {
        VorlesungService vorlesungService = new VorlesungService();
        Vorlesung nextVorlesung = vorlesungService.getNextVorlesung();

        String reply = nextVorlesung.toString();

        msg.reply(reply).queue();
    }
}
