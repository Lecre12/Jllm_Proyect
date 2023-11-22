/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jllmproyectadrian.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * @author adria
 */
public class FileTreat {
    
    private Path fileLocation = Paths.get(System.getProperty("user.home"), "Desktop", "JLLM\\rememberLastConversation.csv");
    
    public FileTreat(){
        
    }
    
    public Path getFileLocation(){
        return this.fileLocation;
    }
    
    public void writeRememberLastConversation(Conversation conv) throws IOException{
        
        File csvFile = fileLocation.toFile();
        
        if(csvFile.createNewFile()){
            System.out.println(csvFile+" File Created");
        }else System.out.println("File "+csvFile+" already exists");
        
        
        
        try(FileWriter fileWriter = new FileWriter(fileLocation.toFile(), true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter)){
            
            printWriter.print(conv.getMessage() + ",");
            
            printWriter.print(conv.getAnswer() + ",");
            
            printWriter.print(conv.getConversationDay() + ",");
            
            printWriter.print(conv.getConversationMonth() + ",");
            
            printWriter.print(conv.getConversationYear() + ",");
            
            printWriter.print(conv.getConversationHour() + ",");
            
            printWriter.print(conv.getConversationMinute() + ",");
            
            printWriter.print(conv.getConversationSecond());
            printWriter.println("");
            
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }
    
    public boolean rememberLastConversation(ArrayList<Conversation> conversation){
        
        Date date = new Date();
        try{
            if(Files.size(fileLocation) > 0){
            } else {
                return false;
            }
        }catch(IOException  e){
            return false;
        }
     
        
        try(BufferedReader br = new BufferedReader(new FileReader(fileLocation.toFile()))){
            String line;
            
            while((line = br.readLine()) != null){
                String[] field = line.split(",");
                
                Conversation conv = new Conversation();
                conv.setMessage(field[0]);
                conv.setAnswer(field[1]);
                conv.setDate(date);
                conversation.add(conv);
            }
        }catch(IOException  e){
            e.printStackTrace();
        }
        
        
        return true;
    }
    
}
