package com.coldspare.quizgame;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class QuizLogic {
    private final List<Question> questions;
    private final Map<Player, Question> currentQuestions;
    private final Random random;

    public QuizLogic() {
        this.questions = new ArrayList<>();
        this.currentQuestions = new HashMap<>();
        this.random = new Random();

        this.questions.add(new Question(QuestionCategory.SCIENCE, "What is the atomic number of hydrogen?", "1"));
        this.questions.add(new Question(QuestionCategory.SCIENCE, "What is the speed of light in vacuum (in km/s)?", "300000"));
        this.questions.add(new Question(QuestionCategory.HISTORY, "Who discovered America?", "Christopher Columbus"));
        this.questions.add(new Question(QuestionCategory.HISTORY, "What is the date of the French Revolution?", "1789"));
        this.questions.add(new Question(QuestionCategory.LITERATURE, "Who is the author of the book '1984'?", "George Orwell"));
        this.questions.add(new Question(QuestionCategory.LITERATURE, "Who wrote 'Pride and Prejudice'?", "Jane Austen"));
        this.questions.add(new Question(QuestionCategory.GENERAL_KNOWLEDGE, "What is the capital of France?", "Paris"));
        this.questions.add(new Question(QuestionCategory.GENERAL_KNOWLEDGE, "What is the most spoken language in the world?", "Mandarin Chinese"));
    }

    public void start(Player player, QuestionCategory category) {
        // Filter questions by the chosen category
        List<Question> filteredQuestions = this.questions.stream()
                .filter(q -> q.getCategory() == category)
                .collect(Collectors.toList());

        if(filteredQuestions.size() > 0) {
            Question question = filteredQuestions.get(this.random.nextInt(filteredQuestions.size()));
            this.currentQuestions.put(player, question);
            player.sendMessage(ChatColor.GREEN + "Category: " + question.getCategory());
            player.sendMessage(ChatColor.YELLOW + "Question: " + question.getQuestion());
        } else {
            player.sendMessage(ChatColor.RED + "No questions available for the selected category.");
        }
    }

    public Question getCurrentQuestion(Player player) {
        return currentQuestions.get(player);
    }

    public void removeCurrentQuestion(Player player) {
        this.currentQuestions.remove(player);
    }
}
