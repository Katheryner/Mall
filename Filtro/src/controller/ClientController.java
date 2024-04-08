package controller;

import database.ConfigDB;
import entity.Client;
import model.ClientModel;

import javax.swing.*;
import java.util.List;


public class ClientController {
    public static ClientModel instanceModel() {
        return new ClientModel();
    }

    public static void create(){
        Client objClient = new Client();

        String name = JOptionPane.showInputDialog(null,"Insert name");
        String lastName = JOptionPane.showInputDialog(null,"Insert last name");
        String email = JOptionPane.showInputDialog(null,"Insert email");
        objClient.setName(name);
        objClient.setLast_name(lastName);
        objClient.setEmail(email);


        objClient =(Client) instanceModel().insert(objClient);
    }
    public static void delete(){
        String listClients = "...::Client list::...";

        for (Object obj: instanceModel().findAll()){
            Client objClient = (Client) obj;
            listClients += objClient.toString() + "\n";
        }
        int confirm = 1;
        int isDelete = Integer.parseInt(JOptionPane.showInputDialog(listClients + "Enter the ID of the client to delete"));
        Client objClient = (Client) instanceModel().findById(isDelete);
        if (objClient == null){
            JOptionPane.showMessageDialog(null, "Client not found");
        } else {
            confirm = JOptionPane.showConfirmDialog(null, "Are you sure want to delete the client?");
            if (confirm == 0){
                instanceModel().delete(objClient);
            }
        }
    }


    public static String listAllClients() {
        String list= "List \n";
        list +="...All CLIENTS...\n";
        if (!instanceModel().findAll().isEmpty()) {
            for (Object object : instanceModel().findAll()) {
                Client clients = (Client) object;
                list += clients.toString()+"\n";
            }
        }
        return list;
    }

    public static void showAllClients(){
        JOptionPane.showMessageDialog(null,listAllClients());
    }
    public static void update(){

        try {
            int number = Integer.parseInt(JOptionPane.showInputDialog(null, listAllClients() + "\nEnter id to update"));
            Client client = (Client) instanceModel().findById(number);
            String name = JOptionPane.showInputDialog(null, "Enter the new name ", client.getName());
            String last = JOptionPane.showInputDialog(null, "Enter the last name ", client.getLast_name());
            String email = JOptionPane.showInputDialog(null, "Enter the email", client.getEmail());
            client.setName(name);
            client.setLast_name(last);
            client.setEmail(email);

            if (instanceModel().update(client)) {
                JOptionPane.showMessageDialog(null, "Update successful");
            } else {
                JOptionPane.showMessageDialog(null, "Update failed");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Enter valid data"+ e.getMessage());
        }
        }


    public static void findClientsbyID(){
        try {
            int number = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter id to find an client"));
            Client client = (Client) instanceModel().findById(number);
            if (client != null) {
                JOptionPane.showMessageDialog(null, client);
            } else {
                JOptionPane.showMessageDialog(null, "This client doesn't exist");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error"+e.getMessage());
        }
    }
    public static String findByName(){
        String list = "List Clients";
        try {
            String name = JOptionPane.showInputDialog(null,"Enter client's name  to find");
            List<Object> listAllClients = instanceModel().findByName(name.toLowerCase());
            if (!listAllClients.isEmpty()){
                for (Object obj : listAllClients){
                    Client client = (Client) obj;
                    list += "\nID " + client.getId_client() +
                            "\nName "+client.getName()+
                            "\nLast Name "+ client.getLast_name() +
                            "\nEmail "+client.getEmail();
                }
                return list;
            } else {
                JOptionPane.showMessageDialog(null, "This client doesn't exist");
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error"+ e.getMessage());
        }
        return list = "Not found name";
    }

    public static void menu(){
        String option;
        String message = """
                            ....::::::   CLIENTS MENU   ::::::....
                            1. Show clients.
                            2. Create client.
                            3. Update client.
                            4. Delete client.
                            5. Find client by id.
                            6. Find client by name.
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
                    showAllClients();
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
                    findClientsbyID();
                    break;
                case "6":
                    JOptionPane.showMessageDialog(null,findByName());
                    break;
            }
        } while (!option.equals("7"));
    }
}

