package me.olivejua.learningjava.solid.singleresponsibility;

public class Book {

    private String name;
    private String author;
    private String text;

    //constructor, getters and setters

    // methods that directly relate to the book properties
    public String replaceWordInText(String word){
        return text.replaceAll(word, text);
    }

    public boolean isWordInText(String word){
        return text.contains(word);
    }

    void printTextToConsole(){
        // our code for formatting and printing the text
    }
}
