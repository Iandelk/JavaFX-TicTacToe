/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krizickruzic;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Phyrexian
 */
public class NetworkManager {

    ObjectOutputStream outputStream = null;

    public NetworkManager(Socket socket) throws IOException {
        outputStream = new ObjectOutputStream(socket.getOutputStream());

    }

    public void send(Game game){

        try {
            outputStream.writeObject(game);
            outputStream.flush();

        } catch (IOException ex) {
            
     
            Logger.getLogger(NetworkManager.class.getName()).log(Level.SEVERE, null, ex);
        }
      

    }



}
