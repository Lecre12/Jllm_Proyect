/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jllmproyectadrian.model;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author adria
 */
public class Model {
    DataBase dataBase;
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
    
    public ArrayList<Conversation> rememberLastConversation(){
        int maxId;
        maxId = dataBase.getMaxId();
        this.conversations = dataBase.readLastConversation(maxId);
        return conversations;
    }
      
}
