package jllmproyectadrian;

import java.io.IOException;
import jllmproyectadrian.view.View;

/**
 * @author Adrian Lecrevisse Vizcaino
 */
public class Main {

    public static void main(String[] args) throws IOException {
        boolean speak = false;
        int mode = 0;
        int fileMode = 1;
        if(!(args.length == 0)){
            if(args[0].equalsIgnoreCase("fake")){
                mode = 1;
            }else if(args[0].equalsIgnoreCase("csv")){
                mode= 2;
            }else if(args[0].equalsIgnoreCase("smart")){
                mode = 3;
            }else if(args[0].equalsIgnoreCase("voz")){
               speak = true;
               mode = 0;
            }
            if(!(args.length == 1)){
                 if(args[1].equalsIgnoreCase("json")){
                    fileMode = 1;
                }else if(args[1].equalsIgnoreCase("xml")){
                    fileMode = 2;
                }
            }
               
            if(!(args.length == 2)){
                if(speak != true){
                    if(args[2].equalsIgnoreCase("consola")){
                        speak = false;
                    }else if(args[2].equalsIgnoreCase("voz")){
                        speak = true;
                    }
                }
            }
            
        }
        
        View view = new View();
        view.createFolder();
        view.setModel(mode);
        view.initDataBase();
        
        if(mode == 0){
            view.principalMenuNoInfo(speak);
        }else if(mode == 1 || mode == 2){
            view.managerMenuMode(speak, fileMode);
        }
            
        
        
        
    }
}
