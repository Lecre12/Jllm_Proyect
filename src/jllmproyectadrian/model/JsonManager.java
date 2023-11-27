/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jllmproyectadrian.model;

import com.google.gson.Gson;
import java.util.ArrayList;

/**
 * @author adria
 */
public class JsonManager {
    
    DataBase dataBase = new DataBase();
    
    public JsonManager(DataBase dataB){
        
        this.dataBase = dataB;
        
    }
    
    public void exportTable(String table){
        ArrayList<Conversation> conversations = dataBase.readSpecificConversation(table, dataBase.getMaxId(table));
        
        for(Conversation conver : conversations){
            
            Gson gson = new Gson();
            String json = gson.toJson(conver);
            System.out.println(json);
            
        }
        
    }
    
}
