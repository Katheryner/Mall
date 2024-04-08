package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConfigDB {
    public static Connection connection = null;
    public static Connection openConnection(){
        String url = "jdbc:mysql://b3wldgez5z61n3yl2o0m-mysql.services.clever-cloud.com:3306/b3wldgez5z61n3yl2o0m";
        String user ="uroqtwvoafjo7h21";
        String password ="wPZZJd4DyNqKz0WUhKRr";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url,user,password);
            System.out.println("Connected to database");
        }catch (SQLException e){
            System.out.println("Error in database\n"+e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Error in driver\n"+ e.getMessage());
        }
        return connection;
    }

    public static void closeConnection(){

        try{
            if (connection != null){
                connection.close();
            }

        }catch (Exception e){
            System.out.println("Error closing the database\n"+e.getMessage());
        }
    }

}
