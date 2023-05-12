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

public class StartGameCommand implements CommandExecutor {
    private final QuizLogic quizLogic;

    public StartGameCommand(QuizLogic quizLogic) {
        this.quizLogic = quizLogic;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can start the game!");
            return true;
        }

        Player player = (Player) sender;
        Inventory inv = Bukkit.createInventory(null, 9, "Select Category");

        ItemStack science = new ItemStack(Material.BOOK);
        ItemMeta scienceMeta = science.getItemMeta();
        scienceMeta.setDisplayName(ChatColor.BLUE + ChatColor.BOLD.toString() + "SCIENCE");
        science.setItemMeta(scienceMeta);
        inv.setItem(1, science);

        ItemStack history = new ItemStack(Material.ANCIENT_DEBRIS);
        ItemMeta historyMeta = history.getItemMeta();
        historyMeta.setDisplayName(ChatColor.RED + ChatColor.BOLD.toString() + "HISTORY");
        history.setItemMeta(historyMeta);
        inv.setItem(3, history);

        ItemStack literature = new ItemStack(Material.WRITABLE_BOOK);
        ItemMeta literatureMeta = literature.getItemMeta();
        literatureMeta.setDisplayName(ChatColor.GREEN + ChatColor.BOLD.toString() + "LITERATURE");
        literature.setItemMeta(literatureMeta);
        inv.setItem(5, literature);

        ItemStack generalKnowledge = new ItemStack(Material.BRAIN_CORAL_BLOCK);
        ItemMeta generalKnowledgeMeta = generalKnowledge.getItemMeta();
        generalKnowledgeMeta.setDisplayName(ChatColor.YELLOW + ChatColor.BOLD.toString() + "GENERAL KNOWLEDGE");
        generalKnowledge.setItemMeta(generalKnowledgeMeta);
        inv.setItem(7, generalKnowledge);

        player.openInventory(inv);
        return true;
    }
}