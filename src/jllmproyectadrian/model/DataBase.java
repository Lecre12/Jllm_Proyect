/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jllmproyectadrian.model;

import java.io.File;
import java.sql.Connection; 
import java.sql.Statement;
import java.sql.*;
import java.sql.DriverManager;  
import java.sql.SQLException; 
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author adria
 */
public class DataBase {
    Connection connection;
    
    public DataBase(){
        
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + System.getProperty("user.home") + File.separator + "Desktop"+ File.separator +"JLLM"+ File.separator +"conversations.db"); 
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        /*Create tables lastConversation if nor exists*/
        try {
            Statement stmt = connection.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS lastConversation(message VARCHAR(100) NOT NULL, answer VARCHAR(100) NOT NULL, date VARCHAR(10) NOT NULL,"
                    + " time VARCHAR(10) NOT NULL, id INT NOT NULL, PRIMARY KEY(id, date, time));");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void insertLastConversation(String message, String answer, Date date){
        
        /*Insert conversation into table last conversation*/
        try(PreparedStatement stmt = connection.prepareStatement("INSERT INTO lastConversation(message, answer, date, time, id)VALUES(?,?,?,?,?);");) { 
            stmt.setString(1, message);
            stmt.setString(2, answer);
            stmt.setString(3, String.format("%04d", date.getYear()) + "-" + String.format("%02d", date.getMonth()) + "-" + String.format("%02d", date.getDay()));
            stmt.setString(4, String.format("%02d", date.getHour()) + ":" + String.format("%02d", date.getMinute()) + ":" + String.format("%02d", date.getSecond()));
            stmt.setInt(5, getMaxId("lastConversation") + 1);
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
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM lastConversation WHERE id = ?");
                stmt.setInt(1, i);
                rs = stmt.executeQuery();
                if(rs.next()){
                    Conversation conver = new Conversation();
                    conver.setMessage(rs.getString("message"));
                    conver.setAnswer(rs.getString("answer"));
                    date = rs.getString("date");
                    time = rs.getString("time");
                    conver.setDateFromStrings(date, time); 
                    conversations.add(conver);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }finally{
                if(rs != null){
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
            
        }
        
        return conversations;
    }
    
    public int getMaxId(String table){
        ResultSet rs = null;
        int id = 0;
        //Get id
        try { 
            PreparedStatement  stmt = connection.prepareStatement("SELECT MAX(id) AS ultima_id FROM " + table +";");
            rs = stmt.executeQuery();
            if(rs.next()){
                id = rs.getInt("ultima_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }
    
    public void deleteLastConversation(){
        
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + System.getProperty("user.home") + File.separator + "Desktop"+ File.separator +"JLLM"+ File.separator +"conversations.db");
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        try { 
            PreparedStatement  stmt = connection.prepareStatement("DROP TABLE lastConversation");
            stmt.execute();
            Statement stmt1 = connection.createStatement();
            stmt1.executeUpdate("CREATE TABLE IF NOT EXISTS lastConversation(message VARCHAR(100) NOT NULL, answer VARCHAR(100) NOT NULL, date VARCHAR(10) NOT NULL,"
                    + " time VARCHAR(10) NOT NULL, id INT NOT NULL, PRIMARY KEY(id, date, time));");
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
    }
    
    public void saveConversationAsDay(ArrayList<Conversation> conversations, String tableName){
        
        long epoch = epoch = System.currentTimeMillis()/1000;;
        String create;
        if(tableName == null){
            create = "CREATE TABLE t"+ epoch + "(message VARCHAR(100) NOT NULL, answer VARCHAR(100) NOT NULL, date VARCHAR(10) NOT NULL,"
                    + " time VARCHAR(10) NOT NULL, id INT NOT NULL, PRIMARY KEY(id, date, time));";
        }else{
            create = "CREATE TABLE t"+ tableName + "(message VARCHAR(100) NOT NULL, answer VARCHAR(100) NOT NULL, date VARCHAR(10) NOT NULL,"
                    + " time VARCHAR(10) NOT NULL, id INT NOT NULL, PRIMARY KEY(id, date, time));";
        }
        
        
        //String create = "CREATE TABLE t"+ epoch + "(message VARCHAR(100) NOT NULL, answer VARCHAR(100) NOT NULL, date VARCHAR(10) NOT NULL, time VARCHAR(10) NOT NULL, id INT NOT NULL, PRIMARY KEY(id, date, time));";
        try {
            Statement stmt1 = connection.createStatement();
            stmt1.execute(create);
            int i = 1;
            for(Conversation conv : conversations){
                if(tableName == null){
                    try(PreparedStatement stmt = connection.prepareStatement("INSERT INTO t" + epoch + "(message, answer, date, time, id)VALUES(?,?,?,?,?);");) { 
                        stmt.setString(1, conv.getMessage());
                        stmt.setString(2, conv.getAnswer());
                        stmt.setString(3, String.format("%04d", conv.getDate().getYear()) + "-" +  String.format("%02d", conv.getDate().getMonth()) + "-" + String.format("%02d",conv.getDate().getDay()));
                        stmt.setString(4, String.format("%02d",conv.getDate().getHour()) + ":" + String.format("%02d",conv.getDate().getMinute()) + ":" + String.format("%02d",conv.getDate().getSecond()));
                        stmt.setInt(5, i);
                        i++;
                        stmt.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                     try(PreparedStatement stmt = connection.prepareStatement("INSERT INTO t" + tableName + "(message, answer, date, time, id)VALUES(?,?,?,?,?);");) { 
                        stmt.setString(1, conv.getMessage());
                        stmt.setString(2, conv.getAnswer());
                        stmt.setString(3, String.format("%04d", conv.getDate().getYear()) + "-" +  String.format("%02d", conv.getDate().getMonth()) + "-" + String.format("%02d",conv.getDate().getDay()));
                        stmt.setString(4, String.format("%02d",conv.getDate().getHour()) + ":" + String.format("%02d",conv.getDate().getMinute()) + ":" + String.format("%02d",conv.getDate().getSecond()));
                        stmt.setInt(5, i);
                        i++;
                        stmt.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                
            }
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
    }
    
    public ArrayList<Conversation> readSpecificConversation(String table, int maxId){
        
        ArrayList<Conversation> conversations = new ArrayList<>();
        ResultSet rs = null;
        String date, time;
        
        for(int i = 1; i <= maxId; i++){
            try { 
                PreparedStatement  stmt = connection.prepareStatement("SELECT * FROM " + table + " WHERE id = ?");
                stmt.setInt(1, i);
                rs = stmt.executeQuery();
                if(rs.next()){
                    Conversation conver = new Conversation();
                    conver.setMessage(rs.getString("message"));
                    conver.setAnswer(rs.getString("answer"));
                    date = rs.getString("date");
                    time = rs.getString("time");
                    conver.setDateFromStrings(date, time); 
                    conversations.add(conver);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }finally{
                if(rs != null){
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }
        
        return conversations;
    }
    
    public ArrayList<String> getAllTablesNames(){
        
        ArrayList<String> tableNames = new ArrayList<>();
        ResultSet rs = null;
        
        try {
            Statement stmt1 = connection.createStatement();
            rs = stmt1.executeQuery("SELECT name FROM sqlite_master WHERE type = 'table' AND name!='lastConversation';");
            while(rs.next()){
                tableNames.add(rs.getString("name"));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return tableNames;
    }
    
    public void continueConversationAsDay(String table, ArrayList<Conversation> conversations){
        
        for(Conversation conv : conversations){
            try(PreparedStatement stmt = connection.prepareStatement("INSERT INTO " + table + "(message, answer, date, time, id)VALUES(?,?,?,?,?);");) { 
                stmt.setString(1, conv.getMessage());
                stmt.setString(2, conv.getAnswer());
                stmt.setString(3, String.format("%04d", conv.getDate().getYear()) + "-" +  String.format("%02d", conv.getDate().getMonth()) + "-" + String.format("%02d",conv.getDate().getDay()));
                stmt.setString(4, String.format("%02d",conv.getDate().getHour()) + ":" + String.format("%02d",conv.getDate().getMinute()) + ":" + String.format("%02d",conv.getDate().getSecond()));
                stmt.setInt(5, getMaxId(table) + 1);
                stmt.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }  
    }
    
    public void deleteTable(String table){
        
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + System.getProperty("user.home") + File.separator + "Desktop"+ File.separator +"JLLM"+ File.separator +"conversations.db");
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        try { 
            PreparedStatement  stmt = connection.prepareStatement("DROP TABLE " + table);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
    }
    
    public String getFirstMessage(String table){
        
        String message = null;
        ResultSet rs = null;
        try {
            Statement stmt1 = connection.createStatement();
            rs = stmt1.executeQuery("SELECT message FROM "+ table +" WHERE id=1;");
            if(rs.next()){
                message = rs.getString("message");
            }
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        
        return message;
    }
    
    /*public String locateLastTableAsDay(ArrayList<Conversation> lastConver){
        
        ResultSet rs = null;
        String tableName = null;
        
        for(String table : getAllTablesNames()){
            
            try(Statement stmt = connection.createStatement()){
                rs = stmt.executeQuery("SELECT name AS nombre_tabla FROM sqlite_master WHERE type='table' AND EXISTS(SELECT 1 FROM lastConversation" +
                    " AS table1 JOIN " + table + " AS table2 ON table1.message = table2.message AND table1.answer = table2.answer AND table1.date = table2.date AND table1.time = table2.time " +
                    "AND table1.id = table2.id WHERE sqlite_master.name != 'lastConversation');");
                if(rs.next()){
                    tableName = rs.getString("nombre_tabla");
                }
                if(tableName!=null){
                    rs.close();
                    return tableName;
                }
                
            }catch(SQLException e){
                throw new RuntimeException(e);
            }finally{
                if(rs != null){
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            
            
        }
        return null;
    }*/
    public String locateLastTableAsDay(ArrayList<Conversation> lastConver) {
    ResultSet rs = null;
    String tableName = null;

        for (String table : getAllTablesNames()) {
            try (Statement stmt = connection.createStatement()) {
                String query = "SELECT '" + table + "' AS table_name " +
                               "WHERE NOT EXISTS (SELECT * FROM lastConversation EXCEPT SELECT * FROM " + table + " UNION ALL SELECT * FROM " +
                                table + " EXCEPT SELECT * FROM lastConversation)";
                rs = stmt.executeQuery(query);

                if (rs.next()) {
                    tableName = rs.getString("table_name");
                }

                if (tableName != null) {
                    return tableName;
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    return null;
    }

    
}
