import controller.ClientController;
import controller.ProductController;
import controller.PurchaseController;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        String option;
        String message = """
                ....::::::   MENU   ::::::....
                1. Client menu.
                2. Product menu.
                3. Purchase menu.
                
                4. Exit.
                                
                ENTER THE OPTION TO CONTINUE...
                """;
        do {
            option = JOptionPane.showInputDialog(null, message);
            if (option == null) {
                break;
            }
            switch (option) {
                case "1":
                    ClientController.menu();
                    break;
                case "2":
                    ProductController.menu();
                    break;
                case "3":
                    PurchaseController.menu();
                    break;

            }
        } while (!option.equals("4"));
    }
    }
