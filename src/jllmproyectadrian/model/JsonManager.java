/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jllmproyectadrian.model;

import com.google.gson.Gson;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author adria
 */
public class JsonManager {
    
    DataBase dataBase = new DataBase();
    
    public JsonManager(DataBase dataB){
        
        this.dataBase = dataB;
        
    }
    
    public void exportTable(String table){
        boolean success = false;
        Path filePath;
        filePath = Paths.get(System.getProperty("user.home") + "\\Desktop\\JLLM\\EXPORTED_CONVERSATIONS\\" + table + ".json");
        try {
            if(filePath.toFile().exists()){
                filePath.toFile().delete();
            }
            Files.createFile(filePath);
        } catch (IOException ex) {
            Logger.getLogger(JsonManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<Conversation> conversations = dataBase.readSpecificConversation(table, dataBase.getMaxId(table));
        
        for(Conversation conver : conversations){
            String json = "{\"message\":\"" + conver.getMessage() + "\", \"answer\":\"" + conver.getAnswer() + "\", \"date\":\"" + 
                    String.format("%02d", conver.getConversationDay()) + "-" + String.format("%02d", conver.getConversationMonth()) + "-" + String.format("%02d", conver.getConversationYear()) +
                    "\", \"time\":\"" + String.format("%02d", conver.getConversationHour()) + ":" + String.format("%02d", conver.getConversationMinute()) + ":" + 
                    String.format("%02d", conver.getConversationSecond()) + "\"}\n";
            try {
                Files.write(filePath, json.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
                success = true;
            } catch (IOException ex) {
                Logger.getLogger(JsonManager.class.getName()).log(Level.SEVERE, null, ex);
                success = false;
            }
            
        }
        if(success){
            System.out.println("Conversacion exportada con exito ☻");
        }
        
        
    }
    
}
