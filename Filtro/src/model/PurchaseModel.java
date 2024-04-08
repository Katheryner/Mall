package model;

import database.CRUD;
import database.ConfigDB;
import entity.Purchase;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseModel implements CRUD {
    @Override
    public Object insert(Object object) {
        Purchase objPurchase = (Purchase) object;
        Connection connection= ConfigDB.openConnection();


        try {
            String sql = "INSERT INTO purchases (date, amount, id_client, id_product) VALUES (?,?,?,?);";
            PreparedStatement objPreparedS = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);

            objPreparedS.setString(1,objPurchase.getDate());
            objPreparedS.setInt(2,objPurchase.getAmount());
            objPreparedS.setInt(3,objPurchase.getId_client());
            objPreparedS.setInt(4,objPurchase.getId_product());

            objPreparedS.execute();

            ResultSet objResult = objPreparedS.getGeneratedKeys();

            while (objResult.next()){
                objPurchase.setId_purchase(objResult.getInt(1));
            }
            objPreparedS.close();
            JOptionPane.showMessageDialog(null,"Purchase insertion was successful");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"error"+e.getMessage());

        }
        ConfigDB.closeConnection();
        return objPurchase;
    }

    @Override
    public boolean update(Object object) {
        Connection objConnection = ConfigDB.openConnection();
        Purchase objPurchase = (Purchase) object;
        boolean isUpdated=false;
        String sql  = "UPDATE purchases SET date = ?, amount = ?, id_client = ?, id_product WHERE id_purchase = ?;";

        try {
            PreparedStatement objPrepare = objConnection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);

            objPrepare.setString(1,objPurchase.getDate());
            objPrepare.setInt(2,objPurchase.getAmount());
            objPrepare.setInt(3,objPurchase.getId_client());
            objPrepare.setInt(4,objPurchase.getId_product());

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
        Purchase objPurchase = (Purchase) object;

        boolean isDeleted = false;
        Connection objConnection = ConfigDB.openConnection();
        try{
            String sql = "DELETE FROM purchases WHERE id_purchase = ?;";

            PreparedStatement objPrepare = objConnection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            objPrepare.setInt(1, objPurchase.getId_purchase());

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
        List<Object> listPurchases= new ArrayList<>();
        try{
            String sql = "SELECT * FROM purchases ORDER BY purchases.id_purchase ASC;";
            PreparedStatement objPrepare = (PreparedStatement) objConnection.prepareStatement(sql);

            ResultSet objResult = (ResultSet) objPrepare.executeQuery();
            while (objResult.next()){
                Purchase objPurchase = new Purchase();
                objPurchase.setId_purchase(objResult.getInt("id_purchase"));
                objPurchase.setAmount(objResult.getInt("amount"));
                objPurchase.setId_client(objResult.getInt("id_client"));
                objPurchase.setId_product(objResult.getInt("id_product"));

                listPurchases.add(objPurchase);
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Data acquisition Error"+ e.getMessage());
        }
        ConfigDB.closeConnection();
        return listPurchases;
    }

    @Override
    public Object findById(int id) {
        Connection objConnection = ConfigDB.openConnection();
        Purchase objPurchase = null;
        try{
            String sql = "SELECT * FROM purchases WHERE id_purchase = ?;";
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            objPrepare.setInt(1,id);
            ResultSet objResult = objPrepare.executeQuery();

            while (objResult.next()){
                objPurchase = new Purchase();
                objPurchase.setId_purchase(objResult.getInt("id_purchase"));
                objPurchase.setAmount(objResult.getInt("amount"));
                objPurchase.setId_client(objResult.getInt("id_client"));
                objPurchase.setId_product(objResult.getInt("id_product"));
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        ConfigDB.closeConnection();
        return objPurchase;
    }

}
