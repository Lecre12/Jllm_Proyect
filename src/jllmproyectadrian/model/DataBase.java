/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jllmproyectadrian.model;

import java.sql.Connection; 
import java.sql.Statement;
import java.sql.*;
import java.sql.DriverManager;  
import java.sql.SQLException; 

/**
 * @author adria
 */
public class DataBase {
    Connection connection;
    
    public DataBase(){
        
        System.out.println("H1");
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + System.getProperty("user.home") + "\\Desktop\\JLLM\\conversations.db");
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        /*Create tables lastConversation if nor exists*/
        System.out.println("H2");
        try {
            Statement stmt = connection.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS lastConversation(message VARCHAR(100) NOT NULL, answer VARCHAR(100) NOT NULL, date TEXT NOT NULL, id INT NOT NULL, PRIMARY KEY(id, date));");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("H3");
    }
    
    public void insertLastConversation(String message, String answer, Date date){
        int id = 0;
        ResultSet rs = null;
        //Get id
        try { 
            PreparedStatement  stmt = connection.prepareStatement("SELECT MAX(id) AS ultima_id FROM lastConversation;");
            rs = stmt.executeQuery();
            if(rs.next()){
                id = rs.getInt("ultima_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        /*Insert conversation into table last conversation*/
        try(PreparedStatement stmt = connection.prepareStatement("INSERT INTO lastConversation(message, answer, date, id)VALUES(?,?,?,?);");) { 
            stmt.setString(1, message);
            stmt.setString(2, answer);
            stmt.setString(3, date.getDay() + "/" + date.getMonth() + "/" + date.getYear());
            stmt.setInt(4, id + 1);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }    
        
        
    }
    
}
