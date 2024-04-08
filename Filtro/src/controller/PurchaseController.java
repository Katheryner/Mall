package controller;

import entity.Client;
import entity.Purchase;
import model.ClientModel;
import model.PurchaseModel;

import javax.swing.*;
import java.util.List;

public class PurchaseController {
    public static PurchaseModel instanceModel() {
        return new PurchaseModel();
    }

    public static void create(){
        Purchase objPurchase = new Purchase();
        ClientController objClientC = new ClientController();
        ClientModel objClientM = new ClientModel();

        String date = JOptionPane.showInputDialog(null,"Insert date");
        int amount = Integer.parseInt(JOptionPane.showInputDialog(null,"Insert amount"));
        int id_client = Integer.parseInt(JOptionPane.showInputDialog(null,objClientM.findAll()+"Insert id client"));
        int id_product = Integer.parseInt(JOptionPane.showInputDialog(null,"Insert id product"));
        objPurchase.setDate(date);
        objPurchase.setAmount(amount);
        objPurchase.setId_product(id_product);
        objPurchase.setId_client(id_client);


        objPurchase =(Purchase) instanceModel().insert(objPurchase);
    }
    public static void delete(){
        String listClients = "...::Purchase list::...";

        for (Object obj: instanceModel().findAll()){
            Purchase objPurchase = (Purchase) obj;
            listClients += objPurchase.toString() + "\n";
        }
        int confirm = 1;
        int isDelete = Integer.parseInt(JOptionPane.showInputDialog(listClients + "Enter the ID of the purchase to delete"));
        Purchase objPurchase = (Purchase) instanceModel().findById(isDelete);
        if (objPurchase == null){
            JOptionPane.showMessageDialog(null, "Purchase not found");
        } else {
            confirm = JOptionPane.showConfirmDialog(null, "Are you sure want to delete the purchase?");
            if (confirm == 0){
                instanceModel().delete(objPurchase);
            }
        }
    }


    public static String listAllPurchase() {
        String list= "List \n";
        list +="...All PURCHASE...\n";
        if (!instanceModel().findAll().isEmpty()) {
            for (Object object : instanceModel().findAll()) {
                Purchase purchase = (Purchase) object;
                list += purchase.toString()+"\n";
            }
        }
        return list;
    }

    public static void showAllPurchase(){
        JOptionPane.showMessageDialog(null,listAllPurchase());
    }
    public static void update(){

        try {
            int number = Integer.parseInt(JOptionPane.showInputDialog(null, listAllPurchase() + "\nEnter id to update"));
            Purchase purchase = (Purchase) instanceModel().findById(number);
            String date = JOptionPane.showInputDialog(null, "Enter the new date ", purchase.getDate());
            int amount = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the amount", purchase.getAmount()));
            int idClient = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the id client", purchase.getId_client()));
            int idProduct = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the id product", purchase.getId_product()));
            purchase.setDate(date);
            purchase.setAmount(amount);
            purchase.setId_client(idClient);
            purchase.setId_product(idProduct);

            if (instanceModel().update(purchase)) {
                JOptionPane.showMessageDialog(null, "Update successful");
            } else {
                JOptionPane.showMessageDialog(null, "Update failed");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Enter valid data"+ e.getMessage());
        }
    }


    public static void findPurchasebyID(){
        try {
            int number = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter id to find an purchase"));
            Purchase purchase = (Purchase) instanceModel().findById(number);
            if (purchase != null) {
                JOptionPane.showMessageDialog(null, purchase);
            } else {
                JOptionPane.showMessageDialog(null, "This purchase doesn't exist");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error"+e.getMessage());
        }
    }


    public static void menu(){
        String option;
        String message = """
                            ....::::::   PURCHASE MENU   ::::::....
                            1. Show purchase.
                            2. Create purchase.
                            3. Update purchase.
                            4. Delete purchase.
                            5. Find purchase by id.
                            6. Find purchase by name.
                            7. Exit.
                                            
                            ENTER THE OPTION TO CONTINUE...
                            """;
        do {
            option = JOptionPane.showInputDialog(null, message);
            if (option == null) {
                break;
            }
            switch (option){
                case "1":
                    showAllPurchase();
                    break;
                case "2":
                    create();
                    break;
                case "3":
                    update();
                    break;
                case "4":
                    delete();
                    break;
                case "5":
                    findPurchasebyID();
                    break;
                case "6":

                    break;
            }
        } while (!option.equals("7"));
    }
}
