package controller;

import entity.Client;
import entity.Product;
import model.ClientModel;
import model.ProductModel;

import javax.swing.*;
import java.util.List;

public class ProductController {
    public static ProductModel instanceModel() {
        return new ProductModel();
    }

    public static void create(){
        try{
            Product objProduct = new Product();

            String name = JOptionPane.showInputDialog(null,"Insert name product");
            double price = Double.parseDouble(JOptionPane.showInputDialog(null,"Insert price"));
            int idStore = Integer.parseInt(JOptionPane.showInputDialog(null,"Insert id Store"));
            int stock = Integer.parseInt(JOptionPane.showInputDialog(null,"Insert stock"));
            objProduct.setName_product(name);
            objProduct.setPrice(price);
            objProduct.setId_store(idStore);
            objProduct.setStock(stock);

            objProduct =(Product) instanceModel().insert(objProduct);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"error"+ e.getMessage());
        }
    }
    public static void delete(){
        String listProducts = "...::Product list::...";

        for (Object obj: instanceModel().findAll()){
            Product objProduct = (Product) obj;
            listProducts += objProduct.toString() + "\n";
        }
        int confirm = 1;
        int isDelete = Integer.parseInt(JOptionPane.showInputDialog(listProducts + "Enter the ID of the product to delete"));
        Product objProduct = (Product) instanceModel().findById(isDelete);
        if (objProduct == null){
            JOptionPane.showMessageDialog(null, "Product not found");
        } else {
            confirm = JOptionPane.showConfirmDialog(null, "Are you sure want to delete the product?");
            if (confirm == 0){
                instanceModel().delete(objProduct);
            }
        }
    }

    public static String listAllProducts() {
        String list= "List \n";
        list +="...All PRODUCTS...\n";
        if (!instanceModel().findAll().isEmpty()) {
            for (Object object : instanceModel().findAll()) {
                Product products = (Product) object;
                list += products.toString()+"\n";
            }
        }
        return list;
    }

    public static void showAllProducts(){
        JOptionPane.showMessageDialog(null,listAllProducts());
    }
    public static void update(){

        try {
            int number = Integer.parseInt(JOptionPane.showInputDialog(null, listAllProducts() + "\nEnter id to update"));
            Product product = (Product) instanceModel().findById(number);
            String name = JOptionPane.showInputDialog(null, "Enter the new name ", product.getName_product());
            double price = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter the price ", product.getPrice()));
            int idStore = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the id store", product.getId_store()));
            int stock = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the stock", product.getStock()));

            product.setName_product(name);
            product.setPrice(price);
            product.setId_store(idStore);
            product.setStock(stock);

            if (instanceModel().update(product)) {
                JOptionPane.showMessageDialog(null, "Update successful");
            } else {
                JOptionPane.showMessageDialog(null, "Update failed");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Enter valid data"+ e.getMessage());
        }
    }


    public static void findProductsbyID(){
        try {
            int number = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter id to find an product"));
            Product product = (Product) instanceModel().findById(number);
            if (product != null) {
                JOptionPane.showMessageDialog(null, product);
            } else {
                JOptionPane.showMessageDialog(null, "This product doesn't exist");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error"+e.getMessage());
        }
    }
    public static String findByName(){
        String list = "List Products";
        try {
            String name = JOptionPane.showInputDialog(null,"Enter product's name  to find");
            List<Object> listAllProducts = instanceModel().findByName(name.toLowerCase());
            if (!listAllProducts.isEmpty()){
                for (Object obj : listAllProducts){
                    Product product = (Product) obj;
                    list += "\nID " + product.getId_product() +
                            "\nName "+product.getName_product()+
                            "\nPrice "+ product.getPrice() +
                            "\nId Store "+product.getId_store();
                }
                return list;
            } else {
                JOptionPane.showMessageDialog(null, "This product doesn't exist");
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error"+ e.getMessage());
        }
        return list = "Not found name";
    }
    public static String findByStore(){
        String list = "List Products";
        try {
            String name = JOptionPane.showInputDialog(null,"Enter product's name to find store");
            List<Object> listAllProducts = instanceModel().findByStore(name.toLowerCase());
            if (!listAllProducts.isEmpty()){
                for (Object obj : listAllProducts){
                    Product product = (Product) obj;
                    list += "\nID " + product.getId_product() +
                            "\nName "+product.getName_product()+
                            "\nPrice "+ product.getPrice() +
                            "\nid Store "+ product.getId_store() +
                            "\nStock "+product.getStock();
                }
                return list;
            } else {
                JOptionPane.showMessageDialog(null, "This product doesn't exist");
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error"+ e.getMessage());
        }
        return list = "Not found product";
    }

    public static void menu(){
        String option;
        String message = """
                            ....::::::   CLIENTS MENU   ::::::....
                            1. Show products.
                            2. Create product.
                            3. Update product.
                            4. Delete product.
                            5. Find product by id.
                            6. Find product by name.
                            7. Find product by store.
                            8. Exit.
                                            
                            ENTER THE OPTION TO CONTINUE...
                            """;
        do {
            option = JOptionPane.showInputDialog(null, message);
            if (option == null) {
                break;
            }
            switch (option){
                case "1":
                    showAllProducts();
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
                    findProductsbyID();
                    break;
                case "6":
                    JOptionPane.showMessageDialog(null,findByName());
                    break;
                case "7":
                    JOptionPane.showMessageDialog(null,findByStore());
                    break;
            }
        } while (!option.equals("8"));
    }
}
