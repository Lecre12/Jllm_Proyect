/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jllmproyectadrian.view;

import java.io.IOException;
import static java.lang.Math.random;
import jllmproyectadrian.controler.Controller;
import jllmproyectadrian.model.Conversation;
import static jllmproyectadrian.util.MessageUtils.readMessageScan;
import jllmproyectadrian.util.messages.RandomMessage;
import jllmproyectadrian.util.messages.Saludes;

/**
 * @author adria
 */
public class View {
    
    Controller c = new Controller();
    Saludes s = new Saludes();
    RandomMessage rm = new RandomMessage();
    
    public void initDataBase(){
        c.initDataBase();
    }
    
    public void principalMenu() throws IOException{
        
        System.out.println("Buenas, soy el chat jllm!");
        String option;
        boolean exit = false;
        
        while(!exit){
            
            System.out.println("Elija la opcion con la que quiere tener interaccion:");
            System.out.println("1. Fake_LLM");
            System.out.println("2. RandomCSV_LLM");
            System.out.println("3. Smart_LLM");
            System.out.println("Introduzca el numero que quiere o \"exit\" para salir");
            option = readMessageScan();
            if(option.equalsIgnoreCase("exit")){
                exit = true;
            }else{
                switch(option){
                    case "1":
                        option1Menu();
                        break;
                    case "2":
                        break;
                    case "3":
                        break;
                    default:
                        System.out.println("[ERROR] No se ha introducido ninguno de los tres numeros o exit...");
                        break;
                }
                
            }
            
        }
        
    }
    
    public void option1Menu() throws IOException{
        
        boolean exit = false;
        String option;
        
        while(!exit){
            System.out.println("Seleccione lo que quiere realizar en la opcion Fake_LLM:");
            System.out.println("1. Nuevo chat Fake_LLM");
            System.out.println("2. Restaurar ultima conversacion realizada");
            System.out.println("3. Listar conversaciones anteriores");
            System.out.println("Introduzca el numero que quiere o \"exit\" para salir");
            
            option = readMessageScan();
            if(option.equalsIgnoreCase("exit")){
                exit = true;
            }else{
                switch(option){
                    case "1":
                        createConversation();
                        break;
                    case "2":
                        for(Conversation conv : c.showLastConversation()){
                            System.out.print("[Yo" + conv.getConversationDay() + "/" + conv.getConversationMonth() + "/" + conv.getConversationYear() + " "
                                                + conv.getConversationHour() + ":" + conv.getConversationMinute() + ":" + conv.getConversationSecond() + "]: ");
                            System.out.println(conv.getMessage());
                            System.out.print("[PROGRAMA" + conv.getConversationDay() + "/" + conv.getConversationMonth() + "/" + conv.getConversationYear() + " "
                                                + conv.getConversationHour() + ":" + conv.getConversationMinute() + ":" + conv.getConversationSecond() + "]: ");
                            System.out.println(conv.getAnswer());
                        }
                        break;
                    case "3":
                        break;
                    default:
                        System.out.println("[ERROR] No se ha introducido ninguno de los tres números o exit...");
                        break;
                }
            }
        }
        
        
    }
    
    public void createConversation() throws IOException{
        
        System.out.println("Dime algo ☺(\"exit\" para salir y volver al menu):");
        
        boolean exit = false;
        int i = 0;
        
        while(!exit){    
            String message = readMessageScan();
            if(message.toLowerCase().contains("hola")){
                int salude = (int) (random() * 10);
                System.out.println(s.getSalude(salude));
                c.createConversation(message,s.getSalude(salude));
            }else if(message.equalsIgnoreCase("exit")){
                exit = true;
                continue;
            }else if(message.toLowerCase().contains("chiste")){
                int joke = (int) (random() * 10);
                System.out.println(rm.getRandomJoke(joke));
                c.createConversation(message, rm.getRandomJoke(joke));    
            }else if(message.toLowerCase().contains("conversacion")){
                //System.out.println(c.showLastConversation(fileTreat).size());
                //continue;
            }else{
                int index = (int) (random() * 10 + 10);
                System.out.println(rm.getRandomMessage(index));
                c.createConversation(message, rm.getRandomMessage(index));
            }
            c.saveLastConversatio(c.getLastConversation());
            i++;
        }
        
    }
    
   
}
