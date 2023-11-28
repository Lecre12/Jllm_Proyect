package jllmproyectadrian.model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author adria
 */
public class Conversation {
    
    private String message;
    private String answer;
    private Date date;

    public Conversation(String message, String answer, Date date){
        this.message = message;
        this.answer = answer;
        this.date = date;
    }
    
    public Conversation(String message, String answer, String date, String time){
        this.message = message;
        this.answer = answer;
        setDateFromStrings(date, time);
    }
    
    public Conversation(){
        
    }
    
    public int getConversationDay(){
        return date.getDay();
    }
    
    public int getConversationMonth(){
        return date.getMonth();
     }
     
    public int getConversationYear(){
        return date.getYear();
    }
     
    public int getConversationHour(){
        return date.getHour();
    }
      
    public int getConversationMinute(){
        return date.getMinute();
    }
       
    public int getConversationSecond(){
        return date.getSecond();
    }
    
    public String getMessage(){
        return message;
    }
    
    public String getAnswer(){
        return answer;
    }
    
    public void setMessage(String message){
        this.message = message;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getDate(){
        return this.date;
    }
    
    public void setDateFromDate(LocalDate date){
        this.date.setDateFromDate(date);
    }
    
    
    public void setTimeFromTime(LocalTime time){
        this.date.setTimeFromTime(time);
    }
    
    public void setDateFromStrings(String date, String time) {
        
        Date pastDate = new Date(date, time);
        this.date = pastDate;
    }

    
}
