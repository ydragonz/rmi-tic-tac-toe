package com.ydragonz.server;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StartRMIRegistry {
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Registry registry = LocateRegistry.createRegistry(1099);
                System.out.println("RMI Registry started on 1099.");
                while (true) {
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}