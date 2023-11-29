/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jllmproyectadrian.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import java.nio.charset.StandardCharsets;
import static jllmproyectadrian.util.CreateFolder.createFolderIfNotExists;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
        Path folderPath = Paths.get(System.getProperty("user.home") + "\\Desktop\\JLLM\\IMPORTED_CONVERSATIONS");
        if(createFolderIfNotExists(folderPath)){
            System.out.println("Exito al crear carpeta");
        }
        
        folderPath = Paths.get(System.getProperty("user.home") + "\\Desktop\\JLLM\\EXPORTED_CONVERSATIONS");
        if(createFolderIfNotExists(folderPath)){
            System.out.println("Exito al crear carpeta");
        }
        
        
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
    
    public ArrayList<Conversation> importTable() throws FileNotFoundException, IOException{
        
        ArrayList<Conversation> conversations = new ArrayList<>();
        Path folderPath;
        folderPath = Paths.get(System.getProperty("user.home") + "\\Desktop\\JLLM\\IMPORTED_CONVERSATIONS");
        Path jsonFilePath;
        File jsonFile = null;
        if (folderPath.toFile().exists() && folderPath.toFile().isDirectory()) {
            // Obtiene la lista de archivos en el directorio
            File[] files = folderPath.toFile().listFiles();

            // Itera sobre los archivos para encontrar los que tengan extensión .json
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".json")) {
                        jsonFile = file;
                    }
                }
            }
        } else {
            System.out.println("La carpeta especificada no existe o no es un directorio válido.");
        }
        
        if(jsonFile == null){
            return null;
        }
        
        jsonFilePath = jsonFile.toPath();
        boolean failed = true;
        try (BufferedReader br = new BufferedReader(new FileReader(jsonFilePath.toFile()))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                row++;
                try {
                    Conversation conver = new Conversation();
                    Gson gson = new Gson();
                    
                    JsonReader reader = new JsonReader(new StringReader(line.toString()));
                    reader.setLenient(true);
                    
                    JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

                    // Obtener valores específicos del JsonObject
                    String message = jsonObject.get("message").getAsString();
                    String answer = jsonObject.get("answer").getAsString();
                    String dateString = jsonObject.get("date").getAsString();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate date = LocalDate.parse(dateString, formatter);
                    String timeString = jsonObject.get("time").getAsString();
                    formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    LocalTime time = LocalTime.parse(timeString, formatter);
                    conver.setMessage(message);
                    conver.setAnswer(answer);
                    conver.setDateFromStrings(date.toString(), time.toString());
                    conversations.add(conver);
                    failed = false;
                    
                } catch (Exception ex) {
                    System.err.println("Error procesando línea " + row + ": " + ex.getMessage());
                    failed = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!failed){
            System.out.println("Importacion realizada con exito");
            jsonFile.delete();
        }
        
        return conversations;
    }
    
}
