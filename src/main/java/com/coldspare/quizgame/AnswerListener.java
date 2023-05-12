package com.coldspare.quizgame;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AnswerListener implements Listener {
    private final QuizLogic quizLogic;
    private final DatabaseManager databaseManager;

    public AnswerListener(QuizLogic quizLogic, DatabaseManager databaseManager) {
        this.quizLogic = quizLogic;
        this.databaseManager = databaseManager;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        // Check if the player has a current question
        Question currentQuestion = quizLogic.getCurrentQuestion(event.getPlayer());
        if (currentQuestion == null) {
            return; // The player is not currently playing the game
        }

        // Check if the player's message is the correct answer
        if (event.getMessage().equalsIgnoreCase(currentQuestion.getAnswer())) {
            event.getPlayer().sendMessage(ChatColor.GREEN + "Correct!");
            // Increase the player's score
            try {
                databaseManager.incrementScore(event.getPlayer());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            event.getPlayer().sendMessage(ChatColor.RED + "Incorrect! The correct answer was: " + currentQuestion.getAnswer());
        }

        // Remove the current question for this player
        quizLogic.removeCurrentQuestion(event.getPlayer());
    }
}