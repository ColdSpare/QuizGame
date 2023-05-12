package com.coldspare.quizgame;

import org.bukkit.entity.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class DatabaseManager {
    private String DATABASE_URL;
    private String DATABASE_USER;
    private String DATABASE_PASSWORD;

    private Connection connection;

    public DatabaseManager() {
        // load properties from config.properties file
        Properties prop = new Properties();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("config.properties not found in the classpath");
            }

            // get the property value and assign them
            DATABASE_URL = prop.getProperty("database.url");
            DATABASE_USER = prop.getProperty("database.user");
            DATABASE_PASSWORD = prop.getProperty("database.password");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void connect() throws SQLException {
        try {
            Class.forName("org.mariadb.jdbc.Driver");  // Load driver
        } catch (ClassNotFoundException e) {
            throw new SQLException(e);
        }
        this.connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }

    public void disconnect() throws SQLException {
        if (this.connection != null && !this.connection.isClosed()) {
            this.connection.close();
        }
    }

    public void saveScore(String playerName, int score) throws SQLException {
        String query = "REPLACE INTO scores (player_name, score) VALUES (?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(query);
        statement.setString(1, playerName);
        statement.setInt(2, score);
        statement.executeUpdate();
    }

    public int loadScore(String playerName) throws SQLException {
        String query = "SELECT score FROM scores WHERE player_name = ?";
        PreparedStatement statement = this.connection.prepareStatement(query);
        statement.setString(1, playerName);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("score");
        } else {
            return 0;
        }
    }

    public void incrementScore(Player player) throws SQLException {
        String sql = "UPDATE scores SET score = score + 1 WHERE player_name = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, player.getName());
            int updatedRows = stmt.executeUpdate();
            if (updatedRows == 0) {
                // no player in database then insert new row
                saveScore(player.getName(), 1);
            }
        }
    }


    public Map<String, Integer> getTopScores(int limit) throws SQLException {
        Map<String, Integer> topScores = new LinkedHashMap<>();
        String query = "SELECT player_name, score FROM scores ORDER BY score DESC LIMIT ?";
        PreparedStatement statement = this.connection.prepareStatement(query);
        statement.setInt(1, limit);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            topScores.put(resultSet.getString("player_name"), resultSet.getInt("score"));
        }
        return topScores;
    }
}
