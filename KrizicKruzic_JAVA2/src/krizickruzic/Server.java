/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krizickruzic;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 *
 * @author Phyrexian
 */
public class Server extends Thread {

    private static final Logger LOG = Logger.getLogger(Server.class.getName());
    ObjectInputStream inputStream = null;
    private List<UpdateGameStateListener> listeners = new ArrayList<>();

    Socket socket;

    public Server(Socket socket) throws IOException {     
        inputStream = new ObjectInputStream(socket.getInputStream());
        LOG.log(Level.INFO, "Created client processor for: {0}", socket);
        this.socket = socket;

    }

    @Override
    public void run()  {

        try {

            while (true) {

                Game game = (Game) inputStream.readObject();
                
                for(UpdateGameStateListener listener : listeners)
                 listener.onGameStateReceieve(game);
                
               

            }
        }catch (SocketException exc){
        
            System.out.println("Socket closed");
        
        } catch (SocketTimeoutException exc) {
            // you got the timeout
        } catch (EOFException exc) {
            // end of stream ---- OVje izbacuje iz while true petlje
        } catch (IOException exc) {
            // some other I/O error: print it, log it, etc.
            exc.printStackTrace(); // for example
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

      private void close(Socket clientSocket) {
        try {
            clientSocket.close();
        } catch (IOException ex1) {
            LOG.log(Level.WARNING, ex1.getMessage(), ex1);
        }
    }
      
    public void addListener(UpdateGameStateListener listener){
    listeners.add(listener);
    }
    

}
