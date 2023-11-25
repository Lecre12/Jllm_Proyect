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
import java.util.ArrayList;

/**
 * @author adria
 */
public class DataBase {
    Connection connection;
    
    public DataBase(){
        
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + System.getProperty("user.home") + "\\Desktop\\JLLM\\conversations.db");
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        /*Create tables lastConversation if nor exists*/
        try {
            Statement stmt = connection.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS lastConversation(message VARCHAR(100) NOT NULL, answer VARCHAR(100) NOT NULL, date VARCHAR(10) NOT NULL, time VARCHAR(10) NOT NULL, id INT NOT NULL, PRIMARY KEY(id, date, time));");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void insertLastConversation(String message, String answer, Date date){
        
        /*Insert conversation into table last conversation*/
        try(PreparedStatement stmt = connection.prepareStatement("INSERT INTO lastConversation(message, answer, date, time, id)VALUES(?,?,?,?,?);");) { 
            stmt.setString(1, message);
            stmt.setString(2, answer);
            stmt.setString(3, date.getYear() + "-" + date.getMonth() + "-" + date.getDay());
            stmt.setString(4, date.getHour() + ":" + date.getMinute() + ":" + date.getSecond());
            stmt.setInt(5, getMaxId() + 1);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public ArrayList <Conversation> readLastConversation(int maxId){
        
        ArrayList <Conversation> conversations = new ArrayList<>();
        ResultSet rs = null;
        String date, time;
        
        for(int i = 1; i <= maxId; i++){
            try { 
                PreparedStatement  stmt = connection.prepareStatement("SELECT * FROM lastConversation WHERE id = ?");
                stmt.setInt(1, i);
                rs = stmt.executeQuery();
                if(rs.next()){
                    Conversation conver = new Conversation();
                    conver.setMessage(rs.getString("message"));
                    conver.setAnswer(rs.getString("answer"));
                    date = rs.getString("date");
                    time = rs.getString("time");
                    conver.setDate(date, time); 
                    conversations.add(conver);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            
        }
        
        return conversations;
    }
    
    public int getMaxId(){
        ResultSet rs = null;
        int id = 0;
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
        return id;
    }
    
}
