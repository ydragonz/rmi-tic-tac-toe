package com.ydragonz.server;

import com.ydragonz.common.TicTacToeInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Naming;

public class TicTacToeServer extends UnicastRemoteObject implements TicTacToeInterface {
    private String[][] board;
    private String currentPlayer;

    public TicTacToeServer() throws RemoteException {
        super();
        resetGame();
    }

    @Override
    public String makeMove(int row, int col) throws RemoteException {
        if (row < 0 || row > 2 || col < 0 || col > 2) {
            return null; // Jogada inválida
        }

        if (board[row][col] == null) {
            board[row][col] = currentPlayer;
            String player = currentPlayer;
            currentPlayer = currentPlayer.equals("X") ? "O" : "X";
            return player;
        } else {
            return null;
        }
    }

    @Override
    public String checkWinner() throws RemoteException {
        // Verificar linhas
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != null && board[i][0].equals(board[i][1]) && board[i][0].equals(board[i][2])) {
                return board[i][0];
            }
        }

        // Verificar colunas
        for (int j = 0; j < 3; j++) {
            if (board[0][j] != null && board[0][j].equals(board[1][j]) && board[0][j].equals(board[2][j])) {
                return board[0][j];
            }
        }

        // Verificar diagonal principal
        if (board[0][0] != null && board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2])) {
            return board[0][0];
        }

        // Verificar diagonal secundária
        if (board[0][2] != null && board[0][2].equals(board[1][1]) && board[0][2].equals(board[2][0])) {
            return board[0][2];
        }

        // Verificar empate
        boolean allFilled = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == null) {
                    allFilled = false;
                    break;
                }
            }
            if (!allFilled) {
                break;
            }
        }

        if (allFilled) {
            return "Draw";
        }

        return null;
    }

    @Override
    public void resetGame() throws RemoteException {
        board = new String[3][3];
        currentPlayer = "X";
    }

    @Override
    public String[][] getBoard() throws RemoteException {
        return board;
    }



    public static void main(String[] args) {
        //System.out.println(System.getProperty("java.class.path"));  // Debug classpath
        try {
            TicTacToeServer server = new TicTacToeServer();
            Naming.rebind("//localhost/TicTacToeServer", server);

            System.out.println("Server ready.");
        } catch (Exception e) {
            System.out.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
