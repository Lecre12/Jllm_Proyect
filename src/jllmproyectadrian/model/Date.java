/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jllmproyectadrian.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.TemporalQueries.localTime;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        this.date = localDate;
    }
    public LocalDate getDate(){
        return this.date;
    }
    
    public void setTime(String time){
        LocalTime localTime = null;
        if(time.length() == 8){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            localTime = LocalTime.parse(time, formatter);
        }else if (time.length() == 5){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:m:s");
            localTime = LocalTime.parse(time, formatter);
        }else if(time.length() < 8 && time.length() > 5){
            if(time.toCharArray()[1] == ':' && time.toCharArray()[4] == ':' && time.toCharArray()[6] != '\0'){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm:ss");
                localTime = LocalTime.parse(time, formatter);
            }else if(time.toCharArray()[1] == ':' && time.toCharArray()[3] == ':' && time.toCharArray()[5] != '\0'){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:m:ss");
                localTime = LocalTime.parse(time, formatter);
            }else if(time.toCharArray()[2] == ':' && time.toCharArray()[4] == ':' && time.toCharArray()[6] != '\0'){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:m:ss");
                localTime = LocalTime.parse(time, formatter);
            }else if(time.toCharArray()[2] == ':' && time.toCharArray()[5] == ':' && time.toCharArray()[7] == '\0'){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:s");
                localTime = LocalTime.parse(time, formatter);
            }else if(time.toCharArray()[1] == ':' && time.toCharArray()[4] == ':' && time.toCharArray()[6] == '\0'){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm:s");
                localTime = LocalTime.parse(time, formatter);
            }
        }
        
        this.time = localTime;   
    }
    
    public LocalTime getTime(){
        return this.time;
    }

}
