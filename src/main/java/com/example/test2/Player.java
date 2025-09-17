package com.example.test2;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int number;
    private String name;
    private List<Token> tokens;

    public Player(int number) {
        this.number = number;
        this.name = "Joueur " + number;
        this.tokens = new ArrayList<>();
    }

    public void addToken(Token token) {
        tokens.add(token);
    }

    public void removeToken(Token token) {
        tokens.remove(token);
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Token> getTokens() {
        return tokens;
    }
}