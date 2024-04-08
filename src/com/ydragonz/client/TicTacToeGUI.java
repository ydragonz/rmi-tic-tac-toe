package com.ydragonz.client;

import com.ydragonz.common.TicTacToeInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.rmi.RemoteException;

public class TicTacToeGUI extends Application {
    private static TicTacToeInterface stub;
    private Button[][] buttons = new Button[3][3];

    public static void setServer(TicTacToeInterface stub) {
        TicTacToeGUI.stub = stub;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tick Tac Toe");

        GridPane grid = new GridPane();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button btn = new Button();
                btn.setPrefSize(100, 100);
                int finalI = i;
                int finalJ = j;
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            String player = stub.makeMove(finalI, finalJ);
                            if (player != null) {
                                btn.setText(player);
                                btn.setDisable(true);
                                updateBoard();

                                String winner = stub.checkWinner();
                                if (winner != null) {

                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Game Over");
                                    alert.setHeaderText(null);
                                    alert.setContentText("The winner is " + winner);
                                    alert.showAndWait();

                                    stub.resetGame();

                                    for (Button[] row : buttons) {
                                        for (Button button : row) {
                                            Platform.runLater(() -> {
                                                button.setDisable(false);
                                                button.setText("");
                                            });
                                        }
                                    }
                                }
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                });

                grid.add(btn, j, i);
                buttons[i][j] = btn;
            }
        }

        Scene scene = new Scene(grid, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
        new Thread(() -> {
            while (true) {
                Platform.runLater(() -> {
                    try {
                        updateBoard();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void updateBoard() throws RemoteException {
        String[][] board = stub.getBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String player = board[i][j];
                Button button = buttons[i][j];
                if (player != null) {
                    button.setText(player);
                    button.setDisable(true);
                } else {
                    button.setText("");
                    button.setDisable(false);
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}