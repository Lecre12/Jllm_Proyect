package jllmproyectadrian;

import java.io.IOException;
import jllmproyectadrian.view.View;

/**
 * @author Adrian Lecrevisse Vizcaino
 */
public class Main {

    public static void main(String[] args) throws IOException {
        
        View view = new View();
        
        view.initDataBase();
        view.principalMenu();
        
    }
}
