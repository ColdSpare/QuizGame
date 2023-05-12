package com.coldspare.quizgame;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.SQLException;

public class ScoreCommand implements CommandExecutor {
    private final DatabaseManager databaseManager;

    public ScoreCommand(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;
        Inventory inv = Bukkit.createInventory(null, 9, "Scores");

        ItemStack yourScore = new ItemStack(Material.NETHER_STAR);
        ItemMeta yourScoreMeta = yourScore.getItemMeta();
        try {
            yourScoreMeta.setDisplayName("Your Score: " + databaseManager.loadScore(player.getName()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        yourScore.setItemMeta(yourScoreMeta);
        inv.setItem(3, yourScore);

        ItemStack topScores = new ItemStack(Material.DRAGON_EGG);
        ItemMeta topScoresMeta = topScores.getItemMeta();
        topScoresMeta.setDisplayName("Top Scores");
        topScores.setItemMeta(topScoresMeta);
        inv.setItem(5, topScores);

        player.openInventory(inv);
        return true;
    }
}