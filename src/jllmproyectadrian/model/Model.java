/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jllmproyectadrian.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author adria
 */
public class Model {
    DataBase dataBase;
    JsonManager jsonManager;
    ArrayList <Conversation> conversations = new ArrayList <Conversation>();
    
    public void createConversation(String message, String answer, Date date){
        
        Conversation conv = new Conversation(message, answer, date);
        this.conversations.add(conv);
        
    }
    
    public ArrayList <Conversation> readConversation(){
        return this.conversations;
    }
    
    public void initDataBase(){
        dataBase = new DataBase(); 
    }
    
    public DataBase getDataBase(){
        return this.dataBase;
    }
    
    public void saveLastConversatio(Conversation conv, DataBase database){ 
        dataBase.insertLastConversation(conv.getMessage(), conv.getAnswer(), conv.getDate());
    }
    
    public void deleteLastConversation(){
        dataBase.deleteLastConversation();
    }
    
    public void saveConversationAsDay(){
        dataBase.saveConversationAsDay(this.conversations);
    }
    
    public void continueConversationAsDay(String table){
        dataBase.continueConversationAsDay(table, this.conversations);
    }
    
    public void deleteTable(String table){
        dataBase.deleteTable(table);
    }
    
    public ArrayList<Conversation> rememberLastConversation(){
        int maxId;
        maxId = dataBase.getMaxId("lastConversation");
        this.conversations = dataBase.readLastConversation(maxId);
        return conversations;
    }
    
    public ArrayList<String> getTablesNames(){
        return dataBase.getAllTablesNames();
    }
    
    public String getFirstMessage(String table){
        return dataBase.getFirstMessage(table);
    }
    
    public ArrayList<Conversation> readSpecificConversation(String table){
        int maxId = dataBase.getMaxId(table);
        this.conversations = dataBase.readSpecificConversation(table, maxId);
        return conversations;
    }
    
    public void initJsonManager(){
        this.jsonManager = new JsonManager(dataBase);
    }
    
    public void exportTable(String tableName){
        initJsonManager();
        this.jsonManager.exportTable(tableName);
    }
    
    public void importTable(String tableName){
        initJsonManager();
        try {
            this.conversations = this.jsonManager.importTable(tableName);
        } catch (IOException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
