/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jllmproyectadrian.model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author adria
 */
public class Date {
    
    private LocalDate date;
    private LocalTime time;
    
    public Date(){
        
        this.date = LocalDate.now();
        this.time = LocalTime.now();
        
    }
    
    public Date(String date, String time){
        setDate(date);
        setTime(time);
        this.date = getDate();
        this.time = getTime();
    }
    
    public int getDay(){
        return date.getDayOfMonth();
    }
    
    public int getMonth(){
        return date.getMonthValue();
    }
    
    public int getYear(){
        return date.getYear();
    }
    
    public int getHour(){
        return time.getHour();
    }
    
    public int getMinute(){
        return time.getMinute();
    }
    
    public int getSecond(){
        return time.getSecond();
    }
    
    public void setDate(String date){
        LocalDate localDate = LocalDate.parse(date);
        this.date = localDate;
    }
    public LocalDate getDate(){
        return this.date;
    }
    
    public void setTime(String time){
        LocalTime localTime = LocalTime.parse(time);
        this.time = localTime;   
    }
    
    public LocalTime getTime(){
        return this.time;
    }

}
