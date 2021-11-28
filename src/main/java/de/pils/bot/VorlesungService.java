package de.pils.bot;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class VorlesungService {


//    public static void main(String[] args) throws IOException, ParseException, LoginException {
//
//        ArrayList<de.pils.bot.Vorlesung> vorlesungen = getVorlesungen();
//
//        de.pils.bot.Vorlesung nextVorlesung = getNextVorlesung(vorlesungen);
//        System.out.println(nextVorlesung);
//
//    }

    @SneakyThrows
    public Vorlesung getNextVorlesung() {

        ArrayList<Vorlesung> vorlesungen = getVorlesungen();

        Date now = new Date();

//        String string = "11.11.2021";
//        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
//        now = format.parse(string);

        ArrayList<Vorlesung> futureVorlesungen = new ArrayList<>();

        for (Vorlesung vorlesung : vorlesungen) {

            if (vorlesung.getStartTime().after(now)) {
                futureVorlesungen.add(vorlesung);

                //System.out.println(vorlesung.getStartTime());

            }
        }

        futureVorlesungen.sort(Comparator.comparing(Vorlesung::getStartTime));
//        futureVorlesungen.forEach(System.out::println);
        if (!futureVorlesungen.isEmpty()) {
            return futureVorlesungen.get(0);
        } else {
            return null;
        }
    }

    @SneakyThrows
    public ArrayList<Vorlesung> getHeutigeVorlesungen() {

        ArrayList<Vorlesung> vorlesungen = getVorlesungen();
        Date now = new Date();

//        String string = "29.11.2021";
//        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
//        now = format.parse(string);

        ArrayList<Vorlesung> todayVorlesungen = new ArrayList<>();
        Calendar calendarNOW = Calendar.getInstance();
        calendarNOW.setTime(now);

        for (Vorlesung vorlesung : vorlesungen) {

            Calendar calendarVor = Calendar.getInstance();
            calendarVor.setTime(vorlesung.getStartTime());


            if (calendarVor.get(Calendar.DAY_OF_WEEK) == calendarNOW.get(Calendar.DAY_OF_WEEK)) {
                todayVorlesungen.add(vorlesung);
                //System.out.println(vorlesung.getStartTime());
            }
        }

        todayVorlesungen.sort(Comparator.comparing(Vorlesung::getStartTime));

        return todayVorlesungen;
    }


    public ArrayList<Vorlesung> getVorlesungen() throws IOException, ParseException {

        //String timetable = TimetableService.getTimetableHTML();
        String woche = "0";
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (day == Calendar.SUNDAY) {
            woche = "1";
        }

        Document doc = Jsoup.connect("https://www.hs-offenburg.de/index.php?id=6627&class=class&iddV=23006E34-D2C0-43BE-8A86-FA9677B14131&week=" + woche).get();

        Elements newsHeadlines = doc.select("#mp-itn b a");


        Element timetable = doc.select("table[class=timetable]").first();

        Element thead = timetable.select("thead").first();

        Date monday = new Date();

        for (Element th : thead.select("th")) {

            String dateString = th.text();
            if (dateString.startsWith("Mo")) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
                monday = sdf.parse(dateString.split(" ")[1]);
                break;
            }
        }
//        System.out.println(monday.toString());

        Element tbody = timetable.select("tbody").first();

//        Elements blocks = tbody.select("tbody > tr");

        Elements blocks = tbody.children();
        ArrayList<Vorlesung> vorlesungen = new ArrayList<>();

        for (int i = 0; i < blocks.size(); i++) {

            Element block = blocks.get(i);
//            Elements kurse = block.select("tr > td");
            Elements kurse = block.children();


            for (int j = 1; j < kurse.size(); j++) {

                Element kurs = kurse.get(j);

                Element kursTable = kurs.selectFirst("table");

                // System.out.println("Block: " + (i + 1) + "\tTag: " + j);
                if (kursTable == null) {
//                    System.out.println("Frei");
                    continue;
                } else {

                    String subject = kursTable.selectFirst("div[class=lesson-subject]").text();
                    String teacher = kursTable.selectFirst("div[class=lesson-teacher]").text();
                    String room = kursTable.selectFirst("div[class=lesson-room]").text();
                    String remark = kursTable.selectFirst("div[class=lesson-remark]").text();
                    String time = kurs.selectFirst("div").attr("title");

                    String startTimeString = time.split(" ")[1];
                    String endTimeString = time.split(" ")[3];


                    Date endTime = getDate(monday, j - 1, endTimeString);
                    Date startTime = getDate(monday, j - 1, startTimeString);


//                    System.out.println(time);
//                    System.out.println("Subject: " + subject + " Teacher: " + teacher);

                    ArrayList<String> moodleUrls = DBService.getMoodleUrlsByVorlesungAlias(subject);
                    ArrayList<String> zoomUrls = DBService.getZoomUrlsByVorlesungAlias(subject);

                    Vorlesung vorlesung = new Vorlesung(
                            j,
                            i + 1,
                            startTime,
                            endTime,
                            subject,
                            teacher,
                            room,
                            remark,
                            moodleUrls,
                            zoomUrls
                    );
                    vorlesungen.add(vorlesung);
                }

            }
        }

        return vorlesungen;
    }

    @NotNull
    private Date getDate(Date monday, int j, String timeString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date = sdf.parse(timeString);
        Calendar hourCalendar = Calendar.getInstance();
        hourCalendar.setTime(date);

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(monday);
        calendar.add(Calendar.DAY_OF_MONTH, j);
        calendar.set(Calendar.HOUR_OF_DAY, hourCalendar.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, hourCalendar.get(Calendar.MINUTE));

//        startTime.setDate(monday.getDate() + j);
//        endTime.setDate(monday.getDate() + j);

        return calendar.getTime();
    }
}
