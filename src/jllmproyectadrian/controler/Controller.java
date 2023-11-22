/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jllmproyectadrian.controler;

import java.io.IOException;
import java.util.ArrayList;
import jllmproyectadrian.model.Conversation;
import jllmproyectadrian.model.Date;
import jllmproyectadrian.model.FileTreat;
import jllmproyectadrian.model.Model;

/**
 * @author adria
 */
public class Controller {
    
    Model m = new Model();
    
    public void createConversation(String message, String answer){
        Date date = new Date();
        this.m.createConversation(message, answer, date);
    }
    
    public ArrayList<Conversation> readConversation(){
        return this.m.readConversation();
    }
    
    public ArrayList<Conversation> showLastConversation(FileTreat fileTreat){
        
        Conversation conv = new Conversation();
        fileTreat.rememberLastConversation(m.readConversation());
        
        return m.showLastConversation();
    }
    
    public void saveLastConversatio(Conversation conv) throws IOException{
        this.m.saveLastConversatio(conv);
    }
    
    public void writeConversationIn() throws IOException{
        for(Conversation conv : m.readConversation()){
            saveLastConversatio(conv);
        }
    }
}
