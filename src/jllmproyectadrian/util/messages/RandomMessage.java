/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jllmproyectadrian.util.messages;

import java.util.ArrayList;

/**
 * @author adria
 */
public class RandomMessage {
    
    private ArrayList <String> messages = new ArrayList<String>();
    
    public RandomMessage(){
        createArrayListJoke();
        createArrayListMessage();
    }
    
    public void createArrayListJoke(){
        
        this.messages.add("Aqui tienes un chiste. ¿Sabes como se despiden los quimicos?...Ácido un placer.");
        this.messages.add("¿Un chsite? Por supuesto.¿Cual es el animal mas antiguo?...La cebra, porque esta en blanco y negro.");
        this.messages.add("Un chiste esta a punto de salir por pantalla...Un hombreentra en una libreria y pregunta, \"¿Tienen libros para el cansancio?\""
                            + "Y el librero responde: \"Si, pero estan agotados\"");
        this.messages.add("¿Como se dice disparo en arabe?...Ahi-va-la-bala.");
        this.messages.add("Cual es el colmo de aladin?...Tener mal genio.");
        this.messages.add("Si se muere una pulga, a donde va?...Al pulgatorio.");
        this.messages.add("Una vez habia un programa tan tan inutil que... Ya no me acuerdo de como era el chiste, disculpame.");
        this.messages.add("-Estas obsesionado con la comida...-A que te refieres croquetamente?");
        this.messages.add("-Camarero este filete tiene muchos nervios.-Normal es la primera vez que se lo comen");
        this.messages.add("Si los zombis se desacen con el tiempo...Zombiodegradables?");
        this.messages.add("Chsite 1");
        
    }
    public String getRandomJoke(int index){
        return this.messages.get(index);
    }
    
    public void createArrayListMessage(){
        
        this.messages.add("Mensaje random 1");
        this.messages.add("Mensaje random 2");
        this.messages.add("Mensaje random 3");
        this.messages.add("Mensaje random 4");
        this.messages.add("Mensaje random 5");
        this.messages.add("Mensaje random 6");
        this.messages.add("Mensaje random 7");
        this.messages.add("Mensaje random 8");
        this.messages.add("Mensaje random 9");
        this.messages.add("Mensaje random 10");
        this.messages.add("Mensaje random 11");
        
    }
    
    public String getRandomMessage(int index){
        return this.messages.get(index);
    }
    
}
