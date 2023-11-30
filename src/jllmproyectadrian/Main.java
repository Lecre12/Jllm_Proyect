package jllmproyectadrian;

import java.io.IOException;
import jllmproyectadrian.view.View;

/**
 * @author Adrian Lecrevisse Vizcaino
 */
public class Main {

    public static void main(String[] args) throws IOException {
        boolean speak = false;
        View view = new View();
        if(!(args.length == 0)){
            if(args[0].equalsIgnoreCase("tts")){
                speak = true;
            }
        }
            
        view.initDataBase();
        view.principalMenu(speak);
        
    }
}
