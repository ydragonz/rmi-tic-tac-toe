package com.ydragonz.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TicTacToeInterface extends Remote {

    String makeMove(int row, int col) throws RemoteException;
    String checkWinner() throws RemoteException;
    void resetGame() throws RemoteException;
    String[][] getBoard() throws RemoteException;
}
