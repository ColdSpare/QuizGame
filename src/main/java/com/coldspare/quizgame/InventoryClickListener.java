package com.coldspare.quizgame;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.Map;

public class InventoryClickListener implements Listener {
    private final QuizLogic quizLogic;
    private final DatabaseManager databaseManager;

    public InventoryClickListener(QuizLogic quizLogic, DatabaseManager databaseManager) {
        this.quizLogic = quizLogic;
        this.databaseManager = databaseManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        if (event.getView().getTitle().equals("Select Category")) {
            event.setCancelled(true);
            if (clickedItem != null && clickedItem.hasItemMeta()) {
                String strippedColorName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
                quizLogic.start(player, QuestionCategory.valueOf(strippedColorName.toUpperCase().replace(" ", "_")));
            }
        } else if (event.getView().getTitle().equals("Scores")) {
            event.setCancelled(true);
            if (clickedItem != null && clickedItem.hasItemMeta() && clickedItem.getItemMeta().getDisplayName().equals("Top Scores")) {
                try {
                    Map<String, Integer> topScores = databaseManager.getTopScores(5);
                    player.sendMessage(ChatColor.BLUE + "Top scores:");
                    for (Map.Entry<String, Integer> entry : topScores.entrySet()) {
                        player.sendMessage(entry.getKey() + ": " + entry.getValue());
                    }
                } catch (SQLException e) {
                    player.sendMessage(ChatColor.RED + "An error occurred while fetching the top scores.");
                    e.printStackTrace();
                }
            }
        }
    }
}
