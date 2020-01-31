/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krizickruzic;

import java.rmi.Remote;
import java.rmi.RemoteException;
import javafx.scene.control.Label;

/**
 *
 * @author Phyrexian
 */
public interface ChatService extends Remote{
    
    String getName() throws RemoteException;
    void send(String message) throws RemoteException;
    
    
}
