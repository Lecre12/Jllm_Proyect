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
    ArrayList <Conversation> conversations = new ArrayList <Conversation>();
    
    public void createConversation(String message, String answer, Date date){
        
        Conversation conv = new Conversation(message, answer, date);
        this.conversations.add(conv);
        
    }
    
    public ArrayList <Conversation> readConversation(){
        return this.conversations;
    }

    public void saveLastConversatio(Conversation conv) throws IOException{
        //Meter conversacion en una archivo de texto o como sea para poder guardar la conversacion anterior.
        FileTreat fileTreat = new FileTreat();
        fileTreat.writeRememberLastConversation(conv);
        
    }
    
    public ArrayList<Conversation> showLastConversation(){
        
        FileTreat fileTreat;
        fileTreat = new FileTreat();
        fileTreat.rememberLastConversation(this.conversations);

        return conversations;
    }
    
}
