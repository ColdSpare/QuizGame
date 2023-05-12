package com.coldspare.quizgame;

import org.bukkit.plugin.java.JavaPlugin;
import java.sql.SQLException;

public final class QuizGame extends JavaPlugin {
    private DatabaseManager databaseManager;
    private QuizLogic quizLogic;

    @Override
    public void onEnable() {
        getLogger().info("QuizGamePlugin has been enabled!");

        try {
            this.databaseManager = new DatabaseManager();
            this.quizLogic = new QuizLogic();
            this.databaseManager.connect();
        } catch (Exception e) {
            e.printStackTrace();
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        this.getCommand("startgame").setExecutor(new StartGameCommand(quizLogic));
        this.getCommand("score").setExecutor(new ScoreCommand(databaseManager));

        this.getServer().getPluginManager().registerEvents(new AnswerListener(quizLogic, databaseManager), this);
        this.getServer().getPluginManager().registerEvents(new InventoryClickListener(quizLogic, databaseManager), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("QuizGamePlugin has been disabled.");

        try {
            this.databaseManager.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

