/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jllmproyectadrian.model;

import io.github.jonelo.jAdapterForNativeTTS.engines.SpeechEngine;
import io.github.jonelo.jAdapterForNativeTTS.engines.SpeechEngineNative;
import io.github.jonelo.jAdapterForNativeTTS.engines.Voice;
import io.github.jonelo.jAdapterForNativeTTS.engines.VoicePreferences;
import io.github.jonelo.jAdapterForNativeTTS.engines.exceptions.SpeechEngineCreationException;
import java.io.IOException;
import java.util.List;



/**
 * @author adria
 */
public class Narrator {
    
    public void sayString(String text){
        try {
            SpeechEngine speechEngine = SpeechEngineNative.getInstance();
            List<Voice> voices = speechEngine.getAvailableVoices();

            // We want to find a voice according our preferences
            VoicePreferences voicePreferences = new VoicePreferences();
            Voice voice = speechEngine.findVoiceByPreferences(voicePreferences);

            // simple fallback just in case our preferences didn't match any voice
            if (voice == null) {
                System.out.printf("Warning: Voice has not been found by the voice preferences %s%n", voicePreferences);
                voice = voices.get(0); // it is guaranteed that the speechEngine supports at least one voice
                System.out.printf("Using \"%s\" instead.%n", voice);
            }

            speechEngine.setVoice(voice.getName());
            speechEngine.say(text);

        } catch (SpeechEngineCreationException | IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
}
