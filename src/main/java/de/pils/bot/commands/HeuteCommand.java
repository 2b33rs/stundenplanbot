package de.pils.bot.commands;

import de.pils.bot.Vorlesung;
import de.pils.bot.VorlesungService;
import net.dv8tion.jda.api.entities.Message;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class HeuteCommand {

    public static void handleCommand(Message msg) throws ParseException, IOException {
        VorlesungService vorlesungService = new VorlesungService();
        ArrayList<Vorlesung> heutigeVorlesungen = vorlesungService.getHeutigeVorlesungen();

        if (heutigeVorlesungen.isEmpty()) {

            msg.reply("Heute ist keine Vorlesung 🍻").queue();

        } else {

            String reply = "";
            for (Vorlesung vorlesung : heutigeVorlesungen) {
                reply += vorlesung;
                reply += "\n";
            }

            msg.reply(reply).queue();
        }
    }
}
