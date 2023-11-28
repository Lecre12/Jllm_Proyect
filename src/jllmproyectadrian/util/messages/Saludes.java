/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jllmproyectadrian.util.messages;

import java.util.ArrayList;
import jllmproyectadrian.model.Date;

/**
 * @author adria
 */
public class Saludes {
    
    private ArrayList <String> saludes = new ArrayList<String>();
    Date date = new Date();
    
    public Saludes(){
        createArrayList();
    }
    
    public void createArrayList(){
        
        this.saludes.add("Hola buenas, me alegro de que recurras a escribirme ♥.");
        this.saludes.add("Este programa poco parecido a una ia te saluda.");
        this.saludes.add("Un placer conocerte, soy el programa JLLM.");
        this.saludes.add("Hola muy buenas, un placer conocerte.");
        this.saludes.add("Bienvenido al chat, hoy sera un gran dia, ¿qué se le propone?");
        this.saludes.add("Hola, hoy dia " + date.getDay() + "/" + date.getMonth() + ", parece buen momento para empezar una conversacion, ¿no crees?");
        this.saludes.add("Holis");
        this.saludes.add("Hola2");
        this.saludes.add("Hola3");
        this.saludes.add("Hola4");
        this.saludes.add("Hola5");
        
    }
    
    public String getSalude(int index){
        return this.saludes.get(index);
    }
    
    public ArrayList<String> getSaludesA(){
        return this.saludes;
    }
    
    
}
