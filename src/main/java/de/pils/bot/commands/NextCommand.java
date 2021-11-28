package de.pils.bot.commands;

import de.pils.bot.Vorlesung;
import de.pils.bot.VorlesungService;
import net.dv8tion.jda.api.entities.Message;

import java.io.IOException;
import java.text.ParseException;

public class NextCommand {

    public static void handleCommand(Message msg) throws ParseException, IOException {
        VorlesungService vorlesungService = new VorlesungService();
        Vorlesung nextVorlesung = vorlesungService.getNextVorlesung();

        msg.reply(nextVorlesung.toString()).queue();
    }
}
