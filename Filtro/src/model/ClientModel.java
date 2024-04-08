package model;

import database.CRUD;
import database.ConfigDB;
import entity.Client;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientModel implements CRUD {

    @Override
    public Object insert(Object object) {
        Client objClient = (Client) object;
        Connection connection= ConfigDB.openConnection();


        try {
            String sql = "INSERT INTO clients (name, last_name, email) VALUES (?,?,?);";
            PreparedStatement objPreparedS = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);

            objPreparedS.setString(1,objClient.getName());
            objPreparedS.setString(2,objClient.getLast_name());
            objPreparedS.setString(3,objClient.getEmail());

            objPreparedS.execute();

            ResultSet objResult = objPreparedS.getGeneratedKeys();

            while (objResult.next()){
                objClient.setId_client(objResult.getInt(1));
            }
            objPreparedS.close();
            JOptionPane.showMessageDialog(null,"Client insertion was successful");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"error");
            System.out.println("Error adding client "+ e.getMessage());
        }
        ConfigDB.closeConnection();
        return objClient;
    }

    @Override
    public boolean update(Object object) {
        Connection objConnection = ConfigDB.openConnection();
        Client objClient = (Client) object;
        boolean isUpdated=false;
        String sql  = "UPDATE clients SET name = ?, last_name = ?, email = ? WHERE id_client = ?;";

        try {
            PreparedStatement objPrepare = objConnection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);

            objPrepare.setString(1,objClient.getName());
            objPrepare.setString(2,objClient.getLast_name());
            objPrepare.setString(3,objClient.getEmail());

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
        Client objClient = (Client) object;

        boolean isDeleted = false;
        Connection objConnection = ConfigDB.openConnection();
        try{
            String sql = "DELETE FROM clients WHERE id_client = ?;";

            PreparedStatement objPrepare = objConnection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            objPrepare.setInt(1, objClient.getId_client());

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
        List<Object> listClients= new ArrayList<>();
        try{
            String sql = "SELECT * FROM clients ORDER BY clients.id_client ASC;";
            PreparedStatement objPrepare = (PreparedStatement) objConnection.prepareStatement(sql);

            ResultSet objResult = (ResultSet) objPrepare.executeQuery();
            while (objResult.next()){
                Client objClient = new Client();
                objClient.setId_client(objResult.getInt("id_client"));
                objClient.setName(objResult.getString("name"));
                objClient.setLast_name(objResult.getString("last_name"));
                objClient.setEmail(objResult.getString("email"));

                listClients.add(objClient);
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Data acquisition Error"+ e.getMessage());
        }
        ConfigDB.closeConnection();
        return listClients;
    }

    @Override
    public Object findById(int id) {

        Connection objConnection = ConfigDB.openConnection();
        Client objClient = null;
        try{
            String sql = "SELECT * FROM clients WHERE id_client = ?;";
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            objPrepare.setInt(1,id);
            ResultSet objResult = objPrepare.executeQuery();

            while (objResult.next()){
                objClient = new Client();
                objClient.setId_client(objResult.getInt("id_client"));
                objClient.setName(objResult.getString("name"));
                objClient.setLast_name(objResult.getString("last_name"));
                objClient.setEmail(objResult.getString("email"));
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        ConfigDB.closeConnection();
        return objClient;
    }
    public List<Object> findByName(String name){
        Connection connection = ConfigDB.openConnection();
        List<Object> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients WHERE name =?;";
        try{
            PreparedStatement prepare = connection.prepareStatement(sql);
            prepare.setString(1, name);
            ResultSet result = prepare.executeQuery();

            while(result.next()){
                clients.add(new Client(result.getInt("id_client"),
                        result.getString("name"),
                        result.getString("last_name"),
                        result.getString("email")));
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Error database"+ e.getMessage());
        }
        ConfigDB.closeConnection();
        return clients;
    }
}
