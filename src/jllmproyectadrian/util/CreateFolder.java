/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jllmproyectadrian.util;

import java.io.File;
import java.nio.file.Path;

/**
 *
 * @author adria
 */
public class CreateFolder {
    
    public static boolean createFolderIfNotExists(Path pathToFolder){
        
        File folder = pathToFolder.toFile();
        
        try {
            if (!folder.exists()) {
                if (folder.mkdirs()) {
                    return true; // La carpeta se creó con éxito
                } else {
                    return false; // No se pudo crear la carpeta
                }
            } else {
                return false; // La carpeta ya existe
            }
        } catch (SecurityException e) {
            System.out.println("Error de permisos al intentar crear la carpeta: " + e.getMessage());
            return false; // Error de permisos
        }
    }
    
}
