package com.example.test2;

public class Token {
    private int row;
    private int col;
    private Player player;

    public Token(int row, int col, Player player) {
        this.row = row;
        this.col = col;
        this.player = player;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }
}