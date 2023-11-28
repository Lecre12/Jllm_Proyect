/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jllmproyectadrian.view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.random;
import java.nio.file.Path;
import java.nio.file.Paths;
import jllmproyectadrian.controler.Controller;
import jllmproyectadrian.model.Conversation;
import static jllmproyectadrian.util.CreateFolder.createFolderIfNotExists;
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
            System.out.println("4. Exportar una conversacion");
            System.out.println("5. Importar una conversacion");
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
                        option2Menu();
                        break;
                    case "3":
                        break;
                    case "4":
                        String tableName = null;
                        showAllConversations();
                        tableName = menuForExport();
                        if(tableName.toLowerCase().equalsIgnoreCase("exit")){
                            break;
                        }
                        c.exportTable(tableName);
                        break;
                    case "5":
                        boolean canExit = false;
                        String tableNameReaded = null;
                        do{
                            System.out.println("Introduzca un titulo para su conversacion");
                            tableNameReaded = readMessageScan();
                            if(tableNameReaded == null){
                                canExit = false;
                                System.out.println("Tienes que poner un titulo para la conversacion");
                            }else if(!tableNameReaded.startsWith("^[^0-9].*")){
                                canExit = true;
                            }else{
                                tableNameReaded = "t" + tableNameReaded;
                                canExit = true;
                            }
                            if(tableNameReaded.contains(" ")){
                                tableNameReaded = tableNameReaded.replace(" ", "");
                            }
                        }while(!canExit);
                        if(tableNameReaded.toLowerCase().equalsIgnoreCase("exit")){
                            break;
                        }
                        c.importTable();
                        if(c.getConversation() == null){
                            System.out.println("[ERROR]: No hay ningun archivo en la carpeta de importacion");
                        }else{
                            c.saveConversationAsDay(tableNameReaded);
                        }
                        
                        break;
                    default:
                        System.out.println("[ERROR] No se ha introducido ninguno de los tres numeros o exit...");
                        break;
                }
                
            }
            
        }
        
    }
    
    private void option1Menu() throws IOException{
        
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
                        String tableName = null;  
                        tableName = c.getDatabase().locateLastTableAsDay(c.showLastConversation());
                        System.out.println(tableName);
                        c.deleteTable(tableName);
                        for(Conversation conv : c.showLastConversation()){
                            System.out.print("[Yo: " + conv.getConversationDay() + "/" + conv.getConversationMonth() + "/" + conv.getConversationYear() + " "
                                                + conv.getConversationHour() + ":" + conv.getConversationMinute() + ":" + conv.getConversationSecond() + "]: ");
                            System.out.println(conv.getMessage());
                            System.out.print("[PROGRAMA: " + conv.getConversationDay() + "/" + conv.getConversationMonth() + "/" + conv.getConversationYear() + " "
                                                + conv.getConversationHour() + ":" + conv.getConversationMinute() + ":" + conv.getConversationSecond() + "]: ");
                            System.out.println(conv.getAnswer());
                        }
                        continueConversation(true, null);
                        c.saveConversationAsDay(null);
                        break;
                    case "3":
                        showAllConversations();
                        int dev;
                        do{
                            dev = menuForDeleteTable();
                        }while(dev == -1);
                        break;
                    default:
                        System.out.println("[ERROR] No se ha introducido ninguno de los tres números o exit...");
                        break;
                }
            }
        }
        
        
    }
    
    private void option2Menu(){
        boolean exit = false;
        String option;
        
        while(!exit){
            System.out.println("Seleccione lo que quiere realizar en la opcion Fake_LLM:");
            System.out.println("1. Nuevo chat Random_CSV_LLM");
            System.out.println("2. Restaurar ultima conversacion realizada");
            System.out.println("3. Listar conversaciones anteriores");
            System.out.println("Introduzca el numero que quiere o \"exit\" para salir");
            
            option = readMessageScan();
            if(option.equalsIgnoreCase("exit")){
                exit = true;
            }else{
                switch(option){
                    case "1":
                        createConversationCsv();
                        break;
                    case "2":
                        String tableName = null;  
                        tableName = c.getDatabase().locateLastTableAsDay(c.showLastConversation());
                        System.out.println(tableName);
                        c.deleteTable(tableName);
                        for(Conversation conv : c.showLastConversation()){
                            System.out.print("[Yo: " + conv.getConversationDay() + "/" + conv.getConversationMonth() + "/" + conv.getConversationYear() + " "
                                                + conv.getConversationHour() + ":" + conv.getConversationMinute() + ":" + conv.getConversationSecond() + "]: ");
                            System.out.println(conv.getMessage());
                            System.out.print("[PROGRAMA: " + conv.getConversationDay() + "/" + conv.getConversationMonth() + "/" + conv.getConversationYear() + " "
                                                + conv.getConversationHour() + ":" + conv.getConversationMinute() + ":" + conv.getConversationSecond() + "]: ");
                            System.out.println(conv.getAnswer());
                        }
                        //continueConversation(true, null);
                        c.saveConversationAsDay(null);
                        break;
                    case "3":
                        showAllConversations();
                        int dev;
                        do{
                            dev = menuForDeleteTable();
                        }while(dev == -1);
                        break;
                    default:
                        System.out.println("[ERROR] No se ha introducido ninguno de los tres números o exit...");
                        break;
                }
            }
        }
    }
    
    public void createConversationCsv(){
        
        c.getDatabase().deleteLastConversation();
        System.out.println("Dime algo ☺(\"exit\" para salir y volver al menu):");
        
        Path folderPath = Paths.get(System.getProperty("user.home") + "\\Desktop\\JLLM\\CSV_FILES");
        if(createFolderIfNotExists(folderPath)){
            System.out.println("Exito al crear carpeta");
        }
        
        boolean exit = false;
        while(!exit){
            String message = readMessageScan();
            if(message.toLowerCase().contains("exit")){
                exit = true;
                continue;
            }else if(message.toLowerCase().contains("hola")){
                String salude = randomSaludeCsv();
                System.out.println(salude);
            }else if(message.toLowerCase().contains("chiste")){
                String joke = randomJokeCsv();
            }else{
                
            }
        }
        
    }
    
    public String randomSaludeCsv(){
        
        String salude = null;
        Path saludeFilePath = Paths.get(System.getProperty("user.home") + "\\Desktop\\JLLM\\CSV_FILES\\saludes.csv");
        
        if(!saludeFilePath.toFile().exists()){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(saludeFilePath.toFile()))) {
            
                for (int i = 0; i < s.getSaludesA().size(); i++) {
                    writer.write(s.getSaludesA().get(i) + "\n");
                }

            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        int targetLineNumber = (int) (random() * 10);
        try (BufferedReader br = new BufferedReader(new FileReader(saludeFilePath.toFile()))) {
            String line;
            int currentLineNumber = 0;
            while ((line = br.readLine()) != null && currentLineNumber < targetLineNumber - 1) {
                currentLineNumber++;
            }
            if (currentLineNumber == targetLineNumber - 1) {
                salude = line;
            }

        } catch (IOException e) {
             e.printStackTrace();
        }
        return salude;
    }
    
    public String randomMessage(){
        String message = null;
        Path saludeFilePath = Paths.get(System.getProperty("user.home") + "\\Desktop\\JLLM\\CSV_FILES\\randomMessages.csv");
        if(!saludeFilePath.toFile().exists()){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(saludeFilePath.toFile()))) {
            
                for (int i = 0; i < s.getSaludesA().size(); i++) {
                    writer.write(s.getSaludesA().get(i) + "\n");
                }

            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        
        return message;
    }
    
    public void createConversation(){
        
        c.getDatabase().deleteLastConversation();
        System.out.println("Dime algo ☺(\"exit\" para salir y volver al menu):");
        
        boolean exit = false;      
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
        }
        c.saveConversationAsDay(null);
        
    }
    
    public void continueConversation(boolean comeFromLast, String table){
        
        System.out.println("Continuemos por donde lo dejamos ☺(\"exit\" para salir y volver al menu):");
        /*for(Conversation conv : c.readSpecificConversation(table)){
            System.out.print("[Yo: " + conv.getConversationDay() + "/" + conv.getConversationMonth() + "/" + conv.getConversationYear() + " "
                                + conv.getConversationHour() + ":" + conv.getConversationMinute() + ":" + conv.getConversationSecond() + "]: ");
            System.out.println(conv.getMessage());
            System.out.print("[PROGRAMA: " + conv.getConversationDay() + "/" + conv.getConversationMonth() + "/" + conv.getConversationYear() + " "
                                + conv.getConversationHour() + ":" + conv.getConversationMinute() + ":" + conv.getConversationSecond() + "]: ");
            System.out.println(conv.getAnswer());
        }*/
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
        if(!comeFromLast){
            c.continueConversationAsDay(table);
        }
        
    }
    
    public void showAllConversations(){
        
        int i = 1;
        for(String tableName : c.getTablesNames()){
            System.out.println(i + ". " + tableName + " | Numero de mensajes: "
                    + c.getDatabase().getMaxId(tableName) + " | " 
                    + c.getDatabase().getFirstMessage(tableName).substring(0, Math.min(c.getDatabase().getFirstMessage(tableName).length(), 20)));
            i++;
        }  
    }
    
    public int menuForDeleteTable(){
        
        String option = null, table = null;
        System.out.println("Si desea borrar una conversacion ponga: \"numero conversacion delete\" (ejemplo: t1700917167 delete)");
        System.out.println("Si desea restaurar una conversacion ponga \"numero conversacion restore\" (ejemplo t1700917167 restore)");
        System.out.println("Escriba \"exit\" para salir al menu");
        option = readMessageScan();
        boolean exists = false;
        if(option.toLowerCase().contains("exit")){
            return 1;
        }
        
        for(String tableName : c.getTablesNames()){
            if(!option.toLowerCase().contains(tableName)){
            }else{
                table = tableName;
                exists = true;
            }
        }
        if(exists){
        } else {
            System.out.println("La tabla introducida no existe o no se ha escrito correctamente delete/restore");
            return -1;
        }
        
        if(option.toLowerCase().contains("restore")){
            System.out.println("Tabla restaurada correctamente\n");
            continueConversation(false, table);
        }else if(option.toLowerCase().contains("delete")){
            c.deleteTable(table);
            System.out.println("Tabla borrada correctamente\n");
        }
        
        
        return 0;
    }
    
    public void initJsonManager(){
        c.initJsonManager();
    }
    
    public String menuForExport(){
        
        String tableName = null;
        System.out.println("Escriba el nombre de la tabla a exportar:");
        tableName = readMessageScan();
        
        return tableName;
    }
   
}
