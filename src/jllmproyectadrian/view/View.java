/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jllmproyectadrian.view;

import java.io.BufferedWriter;
import java.io.FileWriter;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Math.random;
import java.nio.charset.StandardCharsets;
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
        
        createFolderIfNotExists(Paths.get(System.getProperty("user.home") + "\\Desktop\\JLLM"));
        
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
                return;
            }else{
                switch(option){
                    case "1":
                        if(option1Menu() == 1){
                            return;
                        }
                        break;
                    case "2":
                        if(option2Menu() == 1){
                            return;
                        }
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
    
    private int option1Menu() throws IOException{
        
        boolean exit = false;
        String option;
        
        while(!exit){
            System.out.println("Seleccione lo que quiere realizar en la opcion Fake_LLM:");
            System.out.println("1. Nuevo chat Fake_LLM");
            System.out.println("2. Restaurar ultima conversacion realizada");
            System.out.println("3. Listar conversaciones anteriores");
            System.out.println("Introduzca el numero que quiere o \"exit\" para volver al anterior menu o \"exit all\" para salir completamente del programa");
            
            option = readMessageScan();
            if(option.equalsIgnoreCase("exit")){
                exit = true;
                c.removeAllInformation();
                if(option.equalsIgnoreCase("exit all")){
                    return 1;
                }
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
                            dev = menuForDeleteTable(1);
                            if(dev == 2){
                                return 1;
                            }
                        }while(dev == -1);
                        break;
                    default:
                        System.out.println("[ERROR] No se ha introducido ninguno de los tres números o exit...");
                        break;
                }
            }
        }
        
        return 0;
    }
    
    private int option2Menu(){
        boolean exit = false;
        String option;
        
        while(!exit){
            System.out.println("Seleccione lo que quiere realizar en la opcion Fake_LLM:");
            System.out.println("1. Nuevo chat Random_CSV_LLM");
            System.out.println("2. Restaurar ultima conversacion realizada");
            System.out.println("3. Listar conversaciones anteriores");
            System.out.println("Introduzca el numero que quiere o \"exit\" para volver al anterior menu o \"exit all\" para salir completamente del programa");
            
            option = readMessageScan();
            if(option.equalsIgnoreCase("exit")){
                exit = true;
                c.removeAllInformation();
                if(option.equalsIgnoreCase("exit all")){
                    return 1;
                }
            }else {
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
                        c.removeAllInformation();
                        continueConversationCsv(true, null);
                        c.saveConversationAsDay(null);
                        break;
                    case "3":
                        showAllConversations();
                        int dev;
                        do{
                            dev = menuForDeleteTable(2);
                            if(dev == 2){
                                return 1;
                            }
                        }while(dev == -1);
                        break;
                    default:
                        System.out.println("[ERROR] No se ha introducido ninguno de los tres números o exit...");
                        break;
                }
            }
        }
        return 0;
    }
    
    public void createConversationCsv(){
        
        c.removeAllInformation();
        c.getDatabase().deleteLastConversation();
        System.out.println("Dime algo ☺(\"exit\" para salir y volver al menu):");
        
        Path folderPath = Paths.get(System.getProperty("user.home") + "\\Desktop\\JLLM\\CSV_FILES");
        if(createFolderIfNotExists(folderPath)){
            System.out.println("Exito al crear carpeta");
        }
        
        boolean exit = false;
        while(!exit){
            String message = readMessageScan();
            String answer = null;
            if(message.toLowerCase().contains("exit")){
                exit = true;
                continue;
            }else if(message.toLowerCase().contains("hola")){
                System.out.println(answer=randomMessageCsv("salude"));
                if(!answer.equalsIgnoreCase("no encontrado")){
                   c.createConversation(message, answer); 
                }
                
            }else if(message.toLowerCase().contains("chiste")){
                System.out.println(answer=randomMessageCsv("joke"));
                if(!answer.equalsIgnoreCase("no encontrado")){
                    c.createConversation(message, answer);
                }
            }else{
                System.out.println(answer=randomMessageCsv("random"));
                if(!answer.equalsIgnoreCase("no encontrado")){
                    c.createConversation(message, answer);
                }
            }
            if(!answer.equalsIgnoreCase("no encontrado")){
                c.saveLastConversatio(c.getLastConversation());
            }
        }
        c.saveConversationAsDay(null);
    }
    
    public String randomMessageCsv(String type){
        
        String message = null;
        Path messagesFilePath = Paths.get(System.getProperty("user.home") + "\\Desktop\\JLLM\\CSV_FILES\\messages.csv");
        String searchType = type;
        String searchNumber;
        searchNumber = ""+((int) (random() * 10));
        
        try (CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(messagesFilePath.toFile()), StandardCharsets.UTF_8))) {
        String[] nextRecord;
            while ((nextRecord = reader.readNext()) != null) {
            boolean typeMatch = false;
            boolean numberMatch = false;

            // Verificar cada columna para el tipo y el número
            for (String columnValue : nextRecord) {
                if (columnValue.startsWith(searchType)) {
                    typeMatch = true;
                }else if(columnValue.endsWith(searchNumber)) {
                    numberMatch = true;
                }
                
            }
            if (typeMatch && numberMatch) {
                message = nextRecord[2];
                break;
            }
        }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace(); 
        }
        if(message == null){
            return "no encontrado";
        }
        return message;
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
        c.removeAllInformation();
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
        c.removeAllInformation();
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
    
    public void continueConversationCsv(boolean comeFromLast, String table){
        System.out.println("Continuemos por donde lo dejamos ☺(\"exit\" para salir y volver al menu):");
        c.removeAllInformation();
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
            String answer = null;
            if(message.toLowerCase().contains("hola")){
                System.out.println(answer=randomMessageCsv("salude"));
                c.createConversation(message, answer);
            }else if(message.equalsIgnoreCase("exit")){
                exit = true;
                continue;
            }else if(message.toLowerCase().contains("chiste")){
                System.out.println(answer=randomMessageCsv("joke"));
                c.createConversation(message, answer); 
            }else{
                System.out.println(answer=randomMessageCsv("random"));
                c.createConversation(message, answer);
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
    
    public int menuForDeleteTable(int typeOfAi){
        
        String option = null, table = null;
        System.out.println("Si desea borrar una conversacion ponga: \"numero conversacion delete\" (ejemplo: t1700917167 delete)");
        System.out.println("Si desea restaurar una conversacion ponga \"numero conversacion restore\" (ejemplo t1700917167 restore)");
        System.out.println("Escriba \"exit\" para salir al menu o \"exit all\" para salir del programa completamente");
        option = readMessageScan();
        boolean exists = false;
        c.removeAllInformation();
        if(option.toLowerCase().contains("exit")){
            if(option.toLowerCase().equalsIgnoreCase("exit all")){
                return 2;
            }
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
            if(typeOfAi == 1){
                continueConversation(false, table);
            }else if(typeOfAi == 2){
                continueConversationCsv(false, table);
            }
            
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
        System.out.println("Escriba el nombre de la tabla a exportar(\"exit\" para volver al menu):");
        tableName = readMessageScan();
        
        return tableName;
    }
   
}
