package com.example.test2;

import javafx.scene.input.MouseEvent;
import java.util.List;
import java.util.ArrayList;
import javafx.application.Platform;

public class GameLogic {
    private GameBoard gameBoard;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Token selectedToken;
    private boolean mustCaptureAgain;
    private Token lastCapturedToken;
    private GameController gameController;

    public GameLogic(GameBoard gameBoard, GameController gameController) {
        this.gameBoard = gameBoard;
        this.gameController = gameController;
        this.player1 = new Player(1);
        this.player2 = new Player(2);
        this.currentPlayer = player1;
        this.mustCaptureAgain = false;
    }

    public void initializeGame() {
        // Placement initial des jetons du joueur 1 (2 premières lignes)
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < gameBoard.getCols(); col++) {
                Token token = new Token(row, col, player1);
                player1.addToken(token);
                gameBoard.placeToken(row, col, player1);

                // Ajouter l'événement de clic
                int finalRow = row;
                int finalCol = col;
                gameBoard.getCell(row, col).setOnMouseClicked(event ->
                        handleCellClick(finalRow, finalCol, event));
            }
        }

        // Placement initial des jetons du joueur 2 (2 dernières lignes)
        for (int row = gameBoard.getRows() - 2; row < gameBoard.getRows(); row++) {
            for (int col = 0; col < gameBoard.getCols(); col++) {
                Token token = new Token(row, col, player2);
                player2.addToken(token);
                gameBoard.placeToken(row, col, player2);

                // Ajouter l'événement de clic
                int finalRow = row;
                int finalCol = col;
                gameBoard.getCell(row, col).setOnMouseClicked(event ->
                        handleCellClick(finalRow, finalCol, event));
            }
        }

        // Ajouter les événements de clic pour les cellules vides
        for (int row = 0; row < gameBoard.getRows(); row++) {
            for (int col = 0; col < gameBoard.getCols(); col++) {
                if (gameBoard.getCell(row, col).getChildren().isEmpty()) {
                    int finalRow = row;
                    int finalCol = col;
                    gameBoard.getCell(row, col).setOnMouseClicked(event ->
                            handleCellClick(finalRow, finalCol, event));
                }
            }
        }

        // Mettre à jour les scores initiaux
        updateScores();
    }

    private void handleCellClick(int row, int col, MouseEvent event) {
        // Si on doit capturer à nouveau avec le même jeton
        if (mustCaptureAgain && selectedToken != null) {
            if (isValidCaptureMove(selectedToken, row, col)) {
                executeCaptureMove(selectedToken, row, col);
                checkForMultipleCaptures(selectedToken);
                return;
            }
            // Si le mouvement n'est pas une capture valide, on ignore
            return;
        }

        // Si aucun jeton n'est sélectionné
        if (selectedToken == null) {
            // Vérifier si la cellule contient un jeton du joueur courant
            for (Token token : currentPlayer.getTokens()) {
                if (token.getRow() == row && token.getCol() == col) {
                    selectedToken = token;
                    highlightPossibleMoves(token);
                    return;
                }
            }
        }
        // Si un jeton est déjà sélectionné
        else {
            // Vérifier si le déplacement est valide
            if (isValidMove(selectedToken, row, col)) {
                // Mouvement normal
                moveToken(selectedToken, row, col);
                switchPlayer();
            }
            // Vérifier si c'est un mouvement de capture
            else if (isValidCaptureMove(selectedToken, row, col)) {
                executeCaptureMove(selectedToken, row, col);
                checkForMultipleCaptures(selectedToken);
            }
            // Désélectionner le jeton
            clearHighlights();
            selectedToken = null;
        }
    }

    private void highlightPossibleMoves(Token token) {
        // Directions possibles (toutes les 8 directions)
        int[][] directions = {
                {-1, 0},  // avant
                {1, 0},   // arrière
                {0, -1},  // gauche
                {0, 1},   // droite
                {-1, -1}, // diagonale avant gauche
                {-1, 1},  // diagonale avant droite
                {1, -1},  // diagonale arrière gauche
                {1, 1}    // diagonale arrière droite
        };

        for (int[] dir : directions) {
            int newRow = token.getRow() + dir[0];
            int newCol = token.getCol() + dir[1];

            // Vérifier les mouvements simples
            if (isValidMove(token, newRow, newCol)) {
                gameBoard.highlightCell(newRow, newCol, "move");
            }

            // Vérifier les mouvements de capture (2 cases)
            int captureRow = token.getRow() + 2 * dir[0];
            int captureCol = token.getCol() + 2 * dir[1];

            if (isValidCaptureMove(token, captureRow, captureCol)) {
                gameBoard.highlightCell(captureRow, captureCol, "capture");
            }
        }
    }

    private void clearHighlights() {
        for (int row = 0; row < gameBoard.getRows(); row++) {
            for (int col = 0; col < gameBoard.getCols(); col++) {
                gameBoard.resetCellStyle(row, col);
            }
        }
    }

    // Les autres méthodes restent similaires mais avec des appels à updateScores()

    private void captureEnemyToken(int row, int col) {
        Player opponent = (currentPlayer.getNumber() == 1) ? player2 : player1;

        // Chercher et supprimer le jeton adverse
        List<Token> toRemove = new ArrayList<>();
        for (Token token : opponent.getTokens()) {
            if (token.getRow() == row && token.getCol() == col) {
                toRemove.add(token);
                gameBoard.removeToken(row, col);
                break;
            }
        }

        // Supprimer les jetons capturés
        opponent.getTokens().removeAll(toRemove);

        // Mettre à jour les scores
        updateScores();
    }

    private void updateScores() {
        Platform.runLater(() -> {
            gameController.updateScores(player1.getTokens().size(), player2.getTokens().size());
        });
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        mustCaptureAgain = false;
        lastCapturedToken = null;
        clearHighlights();

        // Mettre à jour l'indicateur de tour
        gameController.updateTurnIndicator(currentPlayer.getNumber(), false);

        // Vérifier s'il y a un gagnant
        checkForWinner();
    }

    private void checkForWinner() {
        if (player1.getTokens().isEmpty()) {
            Platform.runLater(() -> {
                gameController.showWinner(2);
            });
        } else if (player2.getTokens().isEmpty()) {
            Platform.runLater(() -> {
                gameController.showWinner(1);
            });
        }
    }

    // Les autres méthodes (isValidMove, isValidCaptureMove, etc.) restent inchangées
    private boolean isValidMove(Token token, int newRow, int newCol) {
        // Vérifier les limites du plateau
        if (newRow < 0 || newRow >= gameBoard.getRows() ||
                newCol < 0 || newCol >= gameBoard.getCols()) {
            return false;
        }

        // Vérifier si la case de destination est vide
        if (!gameBoard.getCell(newRow, newCol).getChildren().isEmpty()) {
            return false;
        }

        // Vérifier la distance (un pas dans n'importe quelle direction)
        int rowDiff = Math.abs(newRow - token.getRow());
        int colDiff = Math.abs(newCol - token.getCol());

        return (rowDiff == 1 && colDiff == 0) || // avant/arrière
                (rowDiff == 0 && colDiff == 1) || // gauche/droite
                (rowDiff == 1 && colDiff == 1);   // diagonales
    }

    private boolean isValidCaptureMove(Token token, int newRow, int newCol) {
        // Vérifier les limites du plateau
        if (newRow < 0 || newRow >= gameBoard.getRows() ||
                newCol < 0 || newCol >= gameBoard.getCols()) {
            return false;
        }

        // Vérifier si la case de destination est vide
        if (!gameBoard.getCell(newRow, newCol).getChildren().isEmpty()) {
            return false;
        }

        // Vérifier la distance (deux pas dans n'importe quelle direction)
        int rowDiff = newRow - token.getRow();
        int colDiff = newCol - token.getCol();

        // Vérifier que le mouvement est exactement de 2 cases
        if (Math.abs(rowDiff) != 2 && Math.abs(colDiff) != 2 &&
                Math.abs(rowDiff) != Math.abs(colDiff)) {
            return false;
        }

        // Vérifier qu'il y a un adversaire à sauter exactement au milieu
        int middleRow = token.getRow() + rowDiff / 2;
        int middleCol = token.getCol() + colDiff / 2;

        return hasEnemyToken(middleRow, middleCol, token.getPlayer());
    }

    private boolean hasEnemyToken(int row, int col, Player currentPlayer) {
        if (row < 0 || row >= gameBoard.getRows() || col < 0 || col >= gameBoard.getCols()) {
            return false;
        }

        // Vérifie si la cellule contient un jeton adverse
        if (!gameBoard.getCell(row, col).getChildren().isEmpty()) {
            Player opponent = (currentPlayer.getNumber() == 1) ? player2 : player1;
            for (Token token : opponent.getTokens()) {
                if (token.getRow() == row && token.getCol() == col) {
                    return true;
                }
            }
        }

        return false;
    }

    private void executeCaptureMove(Token token, int newRow, int newCol) {
        int rowDiff = newRow - token.getRow();
        int colDiff = newCol - token.getCol();

        // Trouver le jeton adverse entre la position actuelle et la nouvelle position
        int middleRow = token.getRow() + rowDiff / 2;
        int middleCol = token.getCol() + colDiff / 2;

        // Capturer le jeton adverse
        captureEnemyToken(middleRow, middleCol);

        // Déplacer le jeton
        moveToken(token, newRow, newCol);

        // Mémoriser le jeton qui vient de capturer
        lastCapturedToken = token;
    }



    private void checkForMultipleCaptures(Token token) {
        // Vérifier si le jeton peut capturer à nouveau
        boolean canCaptureAgain = false;

        int[][] directions = {
                {-2, 0},  // avant
                {2, 0},   // arrière
                {0, -2},  // gauche
                {0, 2},   // droite
                {-2, -2}, // diagonale avant gauche
                {-2, 2},  // diagonale avant droite
                {2, -2},  // diagonale arrière gauche
                {2, 2}    // diagonale arrière droite
        };

        for (int[] dir : directions) {
            int newRow = token.getRow() + dir[0];
            int newCol = token.getCol() + dir[1];

            if (isValidCaptureMove(token, newRow, newCol)) {
                canCaptureAgain = true;
                break;
            }
        }

        if (canCaptureAgain) {
            mustCaptureAgain = true;
            highlightPossibleMoves(token);
        } else {
            mustCaptureAgain = false;
            switchPlayer();
        }
    }

    private void moveToken(Token token, int newRow, int newCol) {
        // Supprimer le jeton de l'ancienne position
        gameBoard.removeToken(token.getRow(), token.getCol());

        // Mettre à jour la position du jeton
        token.setPosition(newRow, newCol);

        // Placer le jeton à la nouvelle position
        gameBoard.placeToken(newRow, newCol, token.getPlayer());

        // Réattacher l'événement de clic
        int finalRow = newRow;
        int finalCol = newCol;
        gameBoard.getCell(newRow, newCol).setOnMouseClicked(event ->
                handleCellClick(finalRow, finalCol, event));

        // Remettre le style normal à l'ancienne cellule
        gameBoard.getCell(token.getRow(), token.getCol()).setStyle("-fx-border-color: black;");
    }



}