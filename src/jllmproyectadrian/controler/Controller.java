/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jllmproyectadrian.controler;

import java.util.ArrayList;
import jllmproyectadrian.model.Conversation;
import jllmproyectadrian.model.Date;
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
    
    public ArrayList<Conversation> showLastConversation(){        
        return m.rememberLastConversation();
    }
    
    public void saveLastConversatio(Conversation conv){
        this.m.saveLastConversatio(conv, m.getDataBase());
    }
    
    public Conversation getLastConversation(){
        return this.m.readConversation().get(m.readConversation().size() - 1);
    }
    
    public void initDataBase(){
        m.initDataBase();
    }
    
}
