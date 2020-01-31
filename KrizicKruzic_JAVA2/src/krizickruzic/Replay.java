/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krizickruzic;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Phyrexian
 */
public class Replay {
    
    Element turns = null;
    
     public Document createDocument() throws ParserConfigurationException {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();

            turns = doc.createElement("Turns");
            
  
            doc.appendChild(turns);

            return doc;
    }
     
     public void appendTurn(Document doc, int X, int Y, int playerTurn){
         
         Element turnElement = makeNewTurnElement(doc, X, Y, playerTurn);
         turns.appendChild(turnElement);
         
         
     }
     
      public Element makeNewTurnElement(Document doc, int X, int Y, int playerTurn) {
            Element turn = doc.createElement("Turn");

         
            
            Element player = doc.createElement("player");
            player.appendChild(doc.createTextNode(String.valueOf(playerTurn)));
            turn.appendChild(player);

            Element posX = doc.createElement("posX");
            posX.appendChild(doc.createTextNode(String.valueOf(X)));
            turn.appendChild(posX);

            Element posY = doc.createElement("posY");
            posY.appendChild(doc.createTextNode(String.valueOf(Y)));
            turn.appendChild(posY);

            
            return turn;
    }
      
        public void saveXmlDocument(Document doc) throws TransformerConfigurationException, TransformerException {
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");            
             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd-HH_mm_ss");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("replay_"+LocalDateTime.now().format(formatter)+".xml"));
            
            transformer.transform(source, result);
            transformer.transform(source, new StreamResult(System.out));
    }

    
}
