package de.pils.bot;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

@Data
@AllArgsConstructor
public class Vorlesung {

    private int tag;
    private int block;
    private Date startTime;
    private Date endTime;
    private String subject;
    private String teacher;
    private String room;
    private String remark;
    private ArrayList<String> moodleUrls;
    private ArrayList<String> zoomUrls;

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EE dd.MM", Locale.GERMAN);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.GERMAN);

        StringBuilder reply = new StringBuilder();

        reply.append("\n")
                .append(this.getSubject())
                .append(" ")
                .append(this.getRemark())
                .append("\nProf: ")
                .append(this.getTeacher())
                .append("\nRaum: ")
                .append(this.getRoom())
                .append("\n")
                .append(dateFormat.format(this.getStartTime()))
                .append(" von ")
                .append(timeFormat.format(this.getStartTime()))
                .append(" bis ")
                .append(timeFormat.format(this.getEndTime()))
                .append(" Uhr");

        if (!moodleUrls.isEmpty() || !zoomUrls.isEmpty()) reply.append("\n\n");

        for (String moodleUrl : moodleUrls) {
            reply.append("Moodle: ").append(moodleUrl).append("\n");
        }

        if (!zoomUrls.isEmpty()) reply.append("\n");

        for (String zoomUrl : zoomUrls) {
            reply.append("Zoom: ").append(zoomUrl).append("\n");
        }

        return reply.toString().trim();
    }

}
