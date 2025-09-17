package com.example.test2;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameController {
    private GameBoard gameBoard;
    private GameLogic gameLogic;
    private Label turnLabel;
    private Label captureLabel;
    private Label scoreLabel1;
    private Label scoreLabel2;
    private Stage primaryStage;

    public void startGame(Stage primaryStage) {
        this.primaryStage = primaryStage;
        gameBoard = new GameBoard();
        gameLogic = new GameLogic(gameBoard, this);

        // Charger l'icône de l'application
        try {
            Image icon = new Image(getClass().getResourceAsStream("/Icon.png"));
            primaryStage.getIcons().add(icon);
        } catch (Exception e) {
            System.out.println("Impossible de charger l'icône: " + e.getMessage());
            // Vous pouvez utiliser une icône par défaut ou ignorer l'erreur
        }

        // Titre du jeu
        Label titleLabel = new Label("Vaky Be");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.WHITE);

        // Informations sur le tour
        turnLabel = new Label("Tour du joueur 1");
        turnLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        turnLabel.setTextFill(Color.rgb(52, 152, 219));

        captureLabel = new Label("");
        captureLabel.setFont(Font.font("Arial", 14));
        captureLabel.setTextFill(Color.ORANGE);

        // Scores des joueurs
        scoreLabel1 = new Label("Joueur 1 (Bleu): 18 jetons");
        scoreLabel1.setFont(Font.font("Arial", 14));
        scoreLabel1.setTextFill(Color.rgb(52, 152, 219));

        scoreLabel2 = new Label("Joueur 2 (Rouge): 18 jetons");
        scoreLabel2.setFont(Font.font("Arial", 14));
        scoreLabel2.setTextFill(Color.rgb(231, 76, 60));

        // Bouton de redémarrage
        Button restartButton = new Button("Nouvelle Partie");
        restartButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        restartButton.setOnAction(e -> restartGame());

        // Panneau d'information en haut
        VBox infoBox = new VBox(10);
        infoBox.setAlignment(Pos.CENTER);
        infoBox.setPadding(new Insets(10, 0, 20, 0));
        infoBox.getChildren().addAll(titleLabel, turnLabel, captureLabel, scoreLabel1, scoreLabel2, restartButton);

        // Container pour centrer le plateau de jeu
        HBox centerContainer = new HBox();
        centerContainer.setAlignment(Pos.CENTER);
        centerContainer.getChildren().add(gameBoard.createBoard());

        // Panneau principal
        BorderPane root = new BorderPane();
        root.setCenter(centerContainer); // Plateau centré
        root.setTop(infoBox); // Informations en haut
        root.setStyle("-fx-background-color: #2c3e50; -fx-padding: 15;");

        Scene scene = new Scene(root, 900, 750);
        primaryStage.setTitle("Vaky Be");
        primaryStage.setScene(scene);
        primaryStage.show();

        gameLogic.initializeGame();
    }

    public void updateTurnIndicator(int playerNumber, boolean mustCaptureAgain) {
        if (playerNumber == 1) {
            turnLabel.setText("Tour du joueur 1 " + (mustCaptureAgain ? " - Capture multiple!" : ""));
            turnLabel.setTextFill(Color.rgb(52, 152, 219));
        } else {
            turnLabel.setText("Tour du joueur 2 " + (mustCaptureAgain ? " - Capture multiple!" : ""));
            turnLabel.setTextFill(Color.rgb(231, 76, 60));
        }

        if (mustCaptureAgain) {
            captureLabel.setText("Vous devez capturer à nouveau!");
            captureLabel.setTextFill(Color.ORANGE);
        } else {
            captureLabel.setText("");
        }
    }

    public void updateScores(int player1Tokens, int player2Tokens) {
        scoreLabel1.setText("Joueur 1 : " + player1Tokens + " jetons");
        scoreLabel2.setText("Joueur 2 : " + player2Tokens + " jetons");
    }

    public void showWinner(int winner) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Partie terminée");
        alert.setHeaderText("Félicitations !");
        alert.setContentText("Le joueur " + winner + " a gagné la partie !");

        // Personnalisation de l'alerte
        alert.getDialogPane().setStyle("-fx-background-color: #ecf0f1;");
        alert.showAndWait();

        // Option pour recommencer
        restartGame();
    }

    private void restartGame() {
        gameBoard = new GameBoard();
        gameLogic = new GameLogic(gameBoard, this);
        startGame(primaryStage);
    }
}