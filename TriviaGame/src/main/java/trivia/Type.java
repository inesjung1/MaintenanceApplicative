package trivia;

import java.util.LinkedList;

public class Type {

    private String name;

    private LinkedList<String> questions;

    public Type(String name) {
        this.name = name;
        this.questions = new LinkedList<String>();
    }

    public String getName() {
        return name;
    }

    public void addQuestion(int question) {
        questions.add(this.name + " Question: " + question);
    }

    //enlever la premiere question
    public LinkedList<String> removeFirstQuestion() {
        questions.removeFirst();
        return questions;
    }
}
