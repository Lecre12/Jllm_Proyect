/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jllmproyectadrian.util;

import java.util.Scanner;

/**
 * @author adria
 */
public class MessageUtils {
    public static String readMessageScan(){
        Scanner scn = new Scanner(System.in);
        String message = scn.nextLine();
        return message;
    }
    
}
