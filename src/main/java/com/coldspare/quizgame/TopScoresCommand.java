/*package com.coldspare.quizgame;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.Map;

public class TopScoresCommand implements CommandExecutor {
    private final DatabaseManager databaseManager;

    public TopScoresCommand(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;
        try {
            Map<String, Integer> topScores = this.databaseManager.getTopScores(5);
            player.sendMessage("Top scores:");
            for (Map.Entry<String, Integer> entry : topScores.entrySet()) {
                player.sendMessage(entry.getKey() + ": " + entry.getValue());
            }
        } catch (SQLException e) {
            player.sendMessage("An error occurred while fetching the top scores.");
            e.printStackTrace();
        }
        return true;
    }
}*/
