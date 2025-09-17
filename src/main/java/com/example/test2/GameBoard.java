package com.example.test2;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.layout.StackPane;
import javafx.scene.effect.DropShadow;

public class GameBoard {
    private static final int ROWS = 5;
    private static final int COLS = 9;
    private StackPane[][] cells;
    private GridPane gridPane;

    public GridPane createBoard() {
        gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: #2c3e50; -fx-padding: 10;");
        gridPane.setAlignment(Pos.CENTER); // Centrer le gridPane lui-même
        cells = new StackPane[ROWS][COLS];

        // Effet d'ombre pour les jetons
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.rgb(0, 0, 0, 0.7));
        shadow.setRadius(5);
        shadow.setSpread(0.2);

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                StackPane cell = new StackPane();
                cell.setPrefSize(70, 70); // Légèrement réduit pour mieux s'afficher
                cell.setMaxSize(70, 70);

                // Alternance de couleurs pour le damier
                if ((row + col) % 2 == 0) {
                    cell.setStyle("-fx-background-color: #ecf0f1; -fx-border-color: #34495e; -fx-border-width: 1;");
                } else {
                    cell.setStyle("-fx-background-color: #bdc3c7; -fx-border-color: #34495e; -fx-border-width: 1;");
                }

                cells[row][col] = cell;
                gridPane.add(cell, col, row);
            }
        }

        return gridPane;
    }

    public void placeToken(int row, int col, Player player) {
        Circle token = new Circle(25); // Taille ajustée

        if (player.getNumber() == 1) {
            // Joueur 1 - Bleu avec dégradé
            token.setFill(Color.rgb(52, 152, 219));
            token.setStroke(Color.rgb(41, 128, 185));
            token.setStrokeWidth(2);
        } else {
            // Joueur 2 - Rouge avec dégradé
            token.setFill(Color.rgb(231, 76, 60));
            token.setStroke(Color.rgb(192, 57, 43));
            token.setStrokeWidth(2);
        }

        // Effet d'ombre
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.rgb(0, 0, 0, 0.7));
        shadow.setRadius(5);
        token.setEffect(shadow);

        cells[row][col].getChildren().clear();
        cells[row][col].getChildren().add(token);
    }

    public void removeToken(int row, int col) {
        cells[row][col].getChildren().clear();
        // Réinitialiser le style de la cellule
        if ((row + col) % 2 == 0) {
            cells[row][col].setStyle("-fx-background-color: #ecf0f1; -fx-border-color: #34495e; -fx-border-width: 1;");
        } else {
            cells[row][col].setStyle("-fx-background-color: #bdc3c7; -fx-border-color: #34495e; -fx-border-width: 1;");
        }
    }

    public void highlightCell(int row, int col, String type) {
        if (type.equals("move")) {
            cells[row][col].setStyle("-fx-background-color: #2ecc71; -fx-border-color: #27ae60; -fx-border-width: 2;");
        } else if (type.equals("capture")) {
            cells[row][col].setStyle("-fx-background-color: #e74c3c; -fx-border-color: #c0392b; -fx-border-width: 2;");
        }
    }

    public void resetCellStyle(int row, int col) {
        if ((row + col) % 2 == 0) {
            cells[row][col].setStyle("-fx-background-color: #ecf0f1; -fx-border-color: #34495e; -fx-border-width: 1;");
        } else {
            cells[row][col].setStyle("-fx-background-color: #bdc3c7; -fx-border-color: #34495e; -fx-border-width: 1;");
        }
    }

    public StackPane getCell(int row, int col) {
        if (row >= 0 && row < ROWS && col >= 0 && col < COLS) {
            return cells[row][col];
        }
        return null;
    }

    public int getRows() {
        return ROWS;
    }

    public int getCols() {
        return COLS;
    }
}