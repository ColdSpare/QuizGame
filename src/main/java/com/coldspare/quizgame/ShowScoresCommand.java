/*package com.coldspare.quizgame;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.sql.SQLException;

public class ShowScoresCommand implements CommandExecutor {
    private final DatabaseManager databaseManager;

    public ShowScoresCommand(DatabaseManager databaseManager) {
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
            int score = this.databaseManager.loadScore(player.getName());
            player.sendMessage("Your score: " + score);
        } catch (SQLException e) {
            player.sendMessage("An error occurred while fetching your score.");
            e.printStackTrace();
        }
        return true;
    }
}*/
