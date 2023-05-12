package com.coldspare.quizgame;

public class Question {
    private final QuestionCategory category;
    private final String question;
    private final String answer;

    public Question(QuestionCategory category, String question, String answer) {
        this.category = category;
        this.question = question;
        this.answer = answer;
    }

    public QuestionCategory getCategory() {
        return category;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}