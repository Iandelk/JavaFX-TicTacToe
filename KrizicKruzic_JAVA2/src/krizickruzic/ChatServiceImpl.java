/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krizickruzic;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author Phyrexian
 */
public class ChatServiceImpl implements ChatService {

     private String name;
     private List<UpdateChatListener> listeners = new ArrayList<>();
    
    public ChatServiceImpl(String name) throws RemoteException {
        this.name = name;
    }
    public void addListener(UpdateChatListener listener){
    listeners.add(listener);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void send(String message) {
   
      
        for(UpdateChatListener listener : listeners)
            listener.onMessageReceive(message);
        
     
    }
    
 
}
