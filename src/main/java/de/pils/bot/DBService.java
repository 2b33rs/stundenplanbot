package de.pils.bot;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;

public class DBService {

    private static final Dotenv dotenv = Dotenv.load();
    private static final String JDBC_STRING = dotenv.get("JDBC_STRING");
    private static final String DB_USER = dotenv.get("DB_USER");
    private static final String DB_PASSWORD = dotenv.get("DB_PASSWORD");

    private static Connection getDBConnection() {

        try {
            return DriverManager.getConnection(JDBC_STRING, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @SneakyThrows
    public static ArrayList<String> getMoodleUrlsByVorlesungAlias(String alias) {
        Connection connection = getDBConnection();

        PreparedStatement ps = connection.prepareStatement("select url from moodle_url join vorlesung_alias va on moodle_url.vorlesung_id = va.vorlesung_id where alias = ?");
        ps.setString(1, alias);

        ResultSet rs = ps.executeQuery();
        ArrayList<String> urls = new ArrayList<>();

        while (rs.next()) {
            String url = rs.getString("url");
            urls.add(url);
        }

        return urls;
    }

    @SneakyThrows
    public static ArrayList<String> getZoomUrlsByVorlesungAlias(String alias) {
        Connection connection = getDBConnection();

        PreparedStatement ps = connection.prepareStatement("select url from zoom_url join vorlesung_alias va on zoom_url.vorlesung_id = va.vorlesung_id where alias = ?");
        ps.setString(1, alias);

        ResultSet rs = ps.executeQuery();
        ArrayList<String> urls = new ArrayList<>();

        while (rs.next()) {
            String url = rs.getString("url");
            urls.add(url);
        }

        return urls;
    }

    @SneakyThrows
    public static boolean vorlesungAliasExists(String alias) {
        Connection connection = getDBConnection();

        PreparedStatement ps = connection.prepareStatement("select * from vorlesung_alias");

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String vorlesungAlias = rs.getString("alias");
            if (alias.equals(vorlesungAlias)) return true;
        }

        return false;
    }

    @SneakyThrows
    public static void addVorlesungAlias(String alias) {

        if (!vorlesungAliasExists(alias)) {
            Connection connection = getDBConnection();

            PreparedStatement ps = connection.prepareStatement("insert into vorlesung_alias (alias) values (?)");

            ps.setString(1, alias);

            ps.execute();
            connection.close();
        }
    }

    public static void main(String[] args) {
//        addVorlesungAlias("test456");
//        VorlesungService vorlesungService = new VorlesungService();
//        ArrayList<Vorlesung> vorlesungen = vorlesungService.getVorlesungen();
//
//        for (Vorlesung vorlesung : vorlesungen) {
//            addVorlesungAlias(vorlesung.getSubject());
//        }
    }

}
