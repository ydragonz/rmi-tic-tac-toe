package com.ydragonz.client;

import com.ydragonz.common.TicTacToeInterface;
import javafx.application.Application;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TicTacToeClient {
    private TicTacToeClient() {}


    public static void main(String[] args) {
        System.out.println(System.getProperty("java.class.path"));
        try {

            Registry registry = LocateRegistry.getRegistry(null);
            TicTacToeInterface stub = (TicTacToeInterface) registry.lookup("TicTacToeServer");

            TicTacToeGUI.setServer(stub);
            Application.launch(TicTacToeGUI.class, args);

            System.out.println("Client connected.");
        } catch (Exception e) {
            System.out.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
