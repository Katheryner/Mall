package model;

import database.CRUD;
import database.ConfigDB;
import entity.Product;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductModel implements CRUD {
    @Override
    public Object insert(Object object) {
        Product objProduct = (Product) object;
        Connection connection= ConfigDB.openConnection();

        try {
            String sql = "INSERT INTO products (name_product, price, id_store,stock) VALUES (?,?,?,?);";
            PreparedStatement objPreparedS = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);

            objPreparedS.setString(1,objProduct.getName_product());
            objPreparedS.setDouble(2,objProduct.getPrice());
            objPreparedS.setInt(3,objProduct.getId_store());
            objPreparedS.setInt(4,objProduct.getStock());

            objPreparedS.execute();

            ResultSet objResult = objPreparedS.getGeneratedKeys();

            while (objResult.next()){
                objProduct.setId_product(objResult.getInt(1));
            }
            objPreparedS.close();
            JOptionPane.showMessageDialog(null,"Product insertion was successful");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"error"+e.getMessage());

        }
        ConfigDB.closeConnection();
        return objProduct;
    }

    @Override
    public boolean update(Object object) {
        Connection objConnection = ConfigDB.openConnection();
        Product objProduct = (Product) object;
        boolean isUpdated=false;
        String sql  = "UPDATE products SET name_product = ?, price = ?, id_store = ?, stock= ? WHERE id_product = ?;";

        try {
            PreparedStatement objPrepare = objConnection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);

            objPrepare.setString(1,objProduct.getName_product());
            objPrepare.setDouble(2,objProduct.getPrice());
            objPrepare.setInt(3,objProduct.getId_store());
            objPrepare.setInt(4,objProduct.getStock());

            int rowAffected  = objPrepare.executeUpdate();
            if (rowAffected > 0){
                isUpdated= true;
                JOptionPane.showMessageDialog(null,"The update was successful.");
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }finally {
            ConfigDB.closeConnection();
        }
        return isUpdated;
    }

    @Override
    public boolean delete(Object object) {
        Product objProduct = (Product) object;

        boolean isDeleted = false;
        Connection objConnection = ConfigDB.openConnection();
        try{
            String sql = "DELETE FROM products WHERE id_product = ?;";

            PreparedStatement objPrepare = objConnection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            objPrepare.setInt(1, objProduct.getId_product());

            int totalAffectedRows = objPrepare.executeUpdate();

            if (totalAffectedRows >0 ){
                isDeleted = true;
                JOptionPane.showMessageDialog(null,"The delete was successful");
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        ConfigDB.closeConnection();
        return isDeleted;
    }

    @Override
    public List<Object> findAll() {
        Connection objConnection = ConfigDB.openConnection();
        List<Object> listProducts= new ArrayList<>();
        try{
            String sql = "SELECT * FROM products ORDER BY products.id_product ASC;";
            PreparedStatement objPrepare = (PreparedStatement) objConnection.prepareStatement(sql);

            ResultSet objResult = (ResultSet) objPrepare.executeQuery();
            while (objResult.next()){
                Product objProduct = new Product();
                objProduct.setId_product(objResult.getInt("id_product"));
                objProduct.setName_product(objResult.getString("name_product"));
                objProduct.setPrice(objResult.getDouble("price"));
                objProduct.setId_store(objResult.getInt("id_store"));
                objProduct.setStock(objResult.getInt("stock"));


                listProducts.add(objProduct);
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Data acquisition Error"+ e.getMessage());
        }
        ConfigDB.closeConnection();
        return listProducts;
    }

    @Override
    public Object findById(int id) {
        Connection objConnection = ConfigDB.openConnection();
        Product objProduct = null;
        try{
            String sql = "SELECT * FROM products WHERE id_product = ?;";
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            objPrepare.setInt(1,id);
            ResultSet objResult = objPrepare.executeQuery();

            while (objResult.next()){
                objProduct = new Product();
                objProduct.setId_product(objResult.getInt("id_product"));
                objProduct.setName_product(objResult.getString("name_product"));
                objProduct.setPrice(objResult.getDouble("price"));
                objProduct.setId_store(objResult.getInt("id_store"));
                objProduct.setStock(objResult.getInt("stock"));
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        ConfigDB.closeConnection();
        return objProduct;
    }
    public List<Object> findByName(String name){
        Connection connection = ConfigDB.openConnection();
        List<Object> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name_product =?;";
        try{
            PreparedStatement prepare = connection.prepareStatement(sql);
            prepare.setString(1, name);
            ResultSet result = prepare.executeQuery();

            while(result.next()){
                products.add(new Product(result.getInt("id_product"),
                        result.getString("name_product"),
                        result.getDouble("price"),
                        result.getInt("id_store"),
                        result.getInt("stock")));
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Error database"+ e.getMessage());
        }
        ConfigDB.closeConnection();
        return products;
    }
    public List<Object> findByStore(String store){
        Connection connection = ConfigDB.openConnection();
        List<Object> products = new ArrayList<>();
        String sql = "SELECT stores.name_store, products.name_product,products.stock FROM stores INNER JOIN products ON stores.id_store = products.id_store WHERE products.name_product =?;";
        try{
            PreparedStatement prepare = connection.prepareStatement(sql);
            prepare.setString(1, store);
            ResultSet result = prepare.executeQuery();

            while(result.next()){
                products.add(new Product(result.getInt("id_product"),
                        result.getString("name_product"),
                        result.getDouble("price"),
                        result.getInt("id_store"),
                        result.getInt("stock")));
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Error database"+ e.getMessage());
        }
        ConfigDB.closeConnection();
        return products;
    }
}
