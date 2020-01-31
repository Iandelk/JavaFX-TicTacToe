/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krizickruzic;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import static krizickruzic.FXMLDocumentController.state;

/**
 *
 * @author Phyrexian
 */
public class Game implements Serializable {

    public int player = 1;
    public String PlayerXName = "Player X";
    public String PlayerOName = "Player O";
    public int ScoreX = 0;
    public int ScoreO = 0;
    public int[][] gameField = new int[3][3];
    public int previousLoser = 0;

    public int CheckGameStatus() {

        int winRow = 0;
        int winCol = 0;
        int winDiag = 0;
        int draw = 0;

        winRow = checkRow(gameField);
        winCol = checkColumn(gameField);
        winDiag = checkDiagonal(gameField);
        draw = checkDraw(gameField);

        if (winRow == 1 || winCol == 1 || winDiag == 1) {
            return 1;
        } else if (winRow == 2 || winCol == 2 || winDiag == 2) {
            return 2;
        } else if (draw == -1) {
            return -1;
        }
        return 0;
    }

    private int checkRow(int[][] gameField) {
        int i;
        int res = 3;
        for (i = 0; i < 3; i++) {
            if (gameField[i][0] == gameField[i][1] && gameField[i][1] == gameField[i][2]) {
                res = gameField[i][0];
                if (res != 3) {
                }
                break;
            }
        }
        switch (res) {
            case 0:
                return 1;
            case 1:
                return 2;
            default:
                return 0;
        }
    }

    int checkColumn(int[][] gameField) {
        int i;
        int res = 3;
        for (i = 0; i < 3; i++) {
            if (gameField[0][i] == gameField[1][i] && gameField[1][i] == gameField[2][i]) {
                res = gameField[0][i];
                if (res != 3) {
                }

                break;
            }
        }
        switch (res) {
            case 0:
                return 1;
            case 1:
                return 2;
            default:
                return 0;
        }
    }

    int checkDiagonal(int gameField[][]) {
        int res = 3;
        if (gameField[0][0] == gameField[1][1] && gameField[1][1] == gameField[2][2]) {
            res = gameField[0][0];
            if (res != 3) {
            }

        }
        if (gameField[0][2] == gameField[1][1] && gameField[1][1] == gameField[2][0]) {
            res = gameField[0][2];
            if (res != 3) {
            }

        }
        switch (res) {
            case 0:
                return 1;
            case 1:
                return 2;
            default:
                return 0;
        }
    }

    private int checkDraw(int gameField[][]) {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameField[i][j] == 3) {
                    return 0;
                }
            }
        }

        return -1;
    }

    public void setScore(int player) {

        switch (player) {
            case 1:
                previousLoser = 1;
                ScoreO++;
                break;
            case 2:
                previousLoser = 0;
                ScoreX++;
                break;            
                
        }

       
    }
    
    public void swapPlayerTurn(){
    player = player == 1 ? 0 : 1;
    }

    public void resetScore() {
        ScoreX = 0;
        ScoreO = 0;
    }
    
    public int countLesser(){
        
        int counterO = 0;
        int counterX=0;
        
           for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
             
                if(gameField[i][j] == 0){
                    counterO++;
                }else if(gameField[i][j] == 1){
                    counterX++;
                }
                
            }
           }
           
           if(counterO < counterX)
               return 0;
           if(counterX < counterO)
               return 1;
           else
               return -1;
           
          
    }
        
    

}
