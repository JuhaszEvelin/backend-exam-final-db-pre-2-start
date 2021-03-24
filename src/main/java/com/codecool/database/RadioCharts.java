package com.codecool.database;


import java.sql.*;

public class RadioCharts {

    private final String DB_URL;
    private final String DB_USER;
    private final String DB_PASSWORD;

    public RadioCharts(String DB_URL, String DB_USER, String DB_PASSWORD) {
        this.DB_URL = DB_URL;
        this.DB_USER = DB_USER;
        this.DB_PASSWORD = DB_PASSWORD;
    }

    public String getMostPlayedSong() {
        String query = "SELECT song " +
                "FROM music_broadcast " +
                "GROUP BY song " +
                "ORDER BY SUM(times_aired) DESC, song DESC";

        System.out.println(getResult(query, "song"));
        return getResult(query, "song");
    }

    public String getMostActiveArtist() {
        String query = "SELECT artist, COUNT( DISTINCT song) as songs " +
                "FROM music_broadcast " +
                "GROUP BY artist, song " +
                "ORDER BY song DESC";

        return getResult(query, "artist");
    }

    private String getResult(String query, String column) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                return (rs.getString(column));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}
