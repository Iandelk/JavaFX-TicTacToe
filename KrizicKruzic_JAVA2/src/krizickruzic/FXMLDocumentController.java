/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krizickruzic;

import java.awt.Dialog;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author Phyrexian
 */
public class FXMLDocumentController implements Initializable, UpdateChatListener, UpdateGameStateListener {

    private Label label;
    @FXML
    private Button btn_00;
    @FXML
    private Button btn_01;
    @FXML
    private Button btn_02;
    @FXML
    private Button btn_10;
    @FXML
    private Button btn_11;
    @FXML
    private Button btn_12;
    @FXML
    private Button btn_20;
    @FXML
    private Button btn_21;
    @FXML
    private Button btn_22;
    @FXML
    private MenuItem menu_Restart;
    @FXML
    private MenuItem menu_Reset;
    @FXML
    private Label lbl_Info;
    @FXML
    private Label lbl_ScoreX;
    @FXML
    private Label lbl_ScoreO;
    @FXML
    private MenuItem menu_ChangePlayerX;
    @FXML
    private MenuItem menu_ChangePlayerO;
    @FXML
    private Button btn_Restart;
    @FXML
    private Label PlayerXScore;
    @FXML
    private Label PlayerOScore;

    public Game game;
    @FXML
    private MenuItem menuLoad;
    @FXML
    private MenuItem menuSave;

    String fileName = "";

    NetworkManager networkManager = null;

    @FXML
    private Label timer_seconds;
    @FXML
    private Label timer_minutes;

    static int seconds = 0;
    static int minutes = 0;
    static Boolean state = true;
    Socket clientSocket = null;
    Socket s = null;
    Boolean connected = false;
    Document xmlDocument;
    Boolean host = false;
    Boolean recordReplay = false;
    Replay replay;
    ChatService stub;
    ChatServiceImpl chat;

    Registry registry;

    int RMIPort = 1099;
    String ipHost;

    @FXML
    private MenuItem menu_Connect;

    private static final Logger LOG = Logger.getLogger(FXMLDocumentController.class.getName());
    @FXML
    private CheckMenuItem menu_host;
    @FXML
    private Label lbl_XNetwork;
    private Label lblONetwork;
    @FXML
    private Label lbl_ONetwork;
    @FXML
    private MenuItem menu_view_Replay;
    @FXML
    private CheckMenuItem menu_record;
    @FXML
    private TextField txtChat;
    @FXML
    private Button btn_Chat;
    @FXML
    private ListView<String> lvChat;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hideChat();
        game = new Game();
        replay = new Replay();
        resetGameField();
        lbl_Info.setText(game.PlayerXName + "'s turn!");
        startTimer();

    }

    @FXML
    private void On_btn_00_Click(ActionEvent event) {
        int X = 0;
        int Y = 0;
        game.gameField[X][Y] = game.player;
        recordReplay(X, Y);
        GameFlow(event);

    }

    @FXML
    private void On_btn_01_Click(ActionEvent event) {
        int X = 0;
        int Y = 1;
        game.gameField[X][Y] = game.player;
        recordReplay(X, Y);
        GameFlow(event);

    }

    @FXML
    private void On_btn_02_Click(ActionEvent event) {
        int X = 0;
        int Y = 2;
        game.gameField[X][Y] = game.player;
        recordReplay(X, Y);
        GameFlow(event);

    }

    @FXML
    private void On_btn_10_Click(ActionEvent event) {
        int X = 1;
        int Y = 0;
        game.gameField[X][Y] = game.player;
        recordReplay(X, Y);
        GameFlow(event);

    }

    @FXML
    private void On_btn_11_Click(ActionEvent event) {
        int X = 1;
        int Y = 1;
        game.gameField[X][Y] = game.player;
        recordReplay(X, Y);
        GameFlow(event);

    }

    @FXML
    private void On_btn_12_Click(ActionEvent event) {
        int X = 1;
        int Y = 2;
        game.gameField[X][Y] = game.player;
        recordReplay(X, Y);
        GameFlow(event);

    }

    @FXML
    private void On_btn_20_Click(ActionEvent event) {
        int X = 2;
        int Y = 0;
        game.gameField[X][Y] = game.player;
        recordReplay(X, Y);
        GameFlow(event);

    }

    @FXML
    private void On_btn_21_Click(ActionEvent event) {
        int X = 2;
        int Y = 1;
        game.gameField[X][Y] = game.player;
        recordReplay(X, Y);
        GameFlow(event);

    }

    @FXML
    private void On_btn_22_Click(ActionEvent event) {
        int X = 2;
        int Y = 2;
        game.gameField[X][Y] = game.player;
        recordReplay(X, Y);
        GameFlow(event);

    }

    @FXML
    private void On_menu_Restart(ActionEvent event) {
        restartGame();
    }

    @FXML
    private void on_menu_Reset(ActionEvent event) {
        resetGameScore();
    }

    @FXML
    private void On_btn_Restart_Click(ActionEvent event) {
        restartGame();
    }

    @FXML
    private void on_menu_ChangePlayerX_Click(ActionEvent event) {

        changeName(game.PlayerXName, "X");
    }

    @FXML
    private void on_menu_ChangePlayerO_Click(ActionEvent event) {

        changeName(game.PlayerOName, "O");

    }

    private void updateNetworkGame() {

        if (networkManager != null) {
            networkManager.send(game);

        }
    }

    private void awaitTurn() {
        if (networkManager != null) {
            disableAllButtons();
        }
    }

    private void updateButton(Button btn) {
        if (game.player == 1) {
            btn.setText("X");

        } else {
            btn.setText("O");

        }

        btn.setDisable(true);
    }

    void updateUI(int gameStatus) {

        switch (gameStatus) {

            case 1:
                lbl_Info.setText(game.PlayerOName + " Wins!");
                disableAllButtons();
                btn_Restart.setVisible(checkIfNetworkHost(gameStatus));
                lbl_ScoreO.setText(Integer.toString(game.ScoreO));
                state = false;

                break;
            case 2:
                lbl_Info.setText(game.PlayerXName + " Wins!");
                disableAllButtons();
                btn_Restart.setVisible(checkIfNetworkHost(gameStatus));
                lbl_ScoreX.setText(Integer.toString(game.ScoreX));
                state = false;

                break;
            case -1:
                lbl_Info.setText("It's a DRAW!");
                btn_Restart.setVisible(checkIfNetworkHost(gameStatus));
                state = false;

                break;
            default:
                if (game.player == 1) {
                    lbl_Info.setText(game.PlayerXName + "'s turn!");
                } else {
                    lbl_Info.setText(game.PlayerOName + "'s turn!");
                }
                break;

        }

    }

    private void disableAllButtons() {

        btn_00.setDisable(true);
        btn_01.setDisable(true);
        btn_02.setDisable(true);
        btn_10.setDisable(true);
        btn_11.setDisable(true);
        btn_12.setDisable(true);
        btn_20.setDisable(true);
        btn_21.setDisable(true);
        btn_22.setDisable(true);

    }

    private void enableAllButtons() {

        btn_00.setDisable(false);
        btn_00.setText("");
        btn_01.setDisable(false);
        btn_01.setText("");
        btn_02.setDisable(false);
        btn_02.setText("");
        btn_10.setDisable(false);
        btn_10.setText("");
        btn_11.setDisable(false);
        btn_11.setText("");
        btn_12.setDisable(false);
        btn_12.setText("");
        btn_20.setDisable(false);
        btn_20.setText("");
        btn_21.setDisable(false);
        btn_21.setText("");
        btn_22.setDisable(false);
        btn_22.setText("");

        btn_Restart.setVisible(false);

    }

    private void GameFlow(ActionEvent event) {
        updateButton((Button) event.getSource());
        game.swapPlayerTurn();
        game.setScore(game.CheckGameStatus());
        updateUI(game.CheckGameStatus());
        updateNetworkGame();
        awaitTurn();
    }

    private void resetGameScore() {
        game.resetScore();
        lbl_ScoreX.setText(game.ScoreX + "");
        lbl_ScoreO.setText(game.ScoreO + "");
    }

    private Button getbuttonByID(int X, int Y) {
        Button button = (Button) lbl_Info.getScene().lookup("#btn_" + String.valueOf(X) + "" + String.valueOf(Y));

        return button;
    }

    private void resetGameField() {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                game.gameField[i][j] = 3;
            }
        }
        int test[][] = game.gameField;
    }

    private void changeName(String playerName, String player) {
        TextInputDialog dialog = new TextInputDialog(playerName);
        dialog.setTitle("Player " + player);
        dialog.setHeaderText("Change name of Player " + player);
        dialog.setContentText("Please enter a new name: ");
        Optional<String> result = dialog.showAndWait();

        if (player.equals("X")) {
            if (result.isPresent()) {
                if (result.get().trim().isEmpty()) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error!");
                    alert.setHeaderText("Name must contain atleast one character!");
                    alert.setContentText("You haven't entered a valid name.");

                    alert.showAndWait();
                } else if (result.get().equals(PlayerXScore.getText())) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error!");
                    alert.setHeaderText("The name must be unique!");
                    alert.setContentText("That name is already taken.");

                    alert.showAndWait();
                } else {
                    game.PlayerXName = result.get();
                    PlayerXScore.setText(game.PlayerXName);
                }
            }
        } else if (player.equals("O")) {
            if (result.isPresent()) {
                if (result.get().trim().isEmpty()) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error!");
                    alert.setHeaderText("Name must contain atleast one character!");
                    alert.setContentText("You haven't entered a valid name.");
                    alert.showAndWait();
                } else if (result.get().equals(PlayerXScore.getText())) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error!");
                    alert.setHeaderText("The name must be unique!");
                    alert.setContentText("That name is already taken.");

                    alert.showAndWait();
                } else {
                    game.PlayerOName = result.get();
                    PlayerOScore.setText(game.PlayerOName);
                }
            }

        }

    }

    public void restartGame() {

        if (recordReplay) {
            try {
                replay.saveXmlDocument(xmlDocument);
            } catch (TransformerException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        recordReplay = false;
        btn_Restart.setVisible(false);
        resetGameField();
        setUpGame(game);
        if (game.previousLoser == 0 && host) {
            enableAllButtons();
        } else if (game.previousLoser == 1 && !host) {
            enableAllButtons();
        }
        restartTimer();
        startTimer();
        setTurnOnRestart();

    }

    private void setTurnOnRestart() {
        if (game.player == 1) {
            lbl_Info.setText(game.PlayerXName + "'s turn!");
            // if(host)disableAllButtons();
        } else {
            //     if(!host)disableAllButtons();
            lbl_Info.setText(game.PlayerOName + "'s turn!");
        }
    }

    public void setUpGame(Game game) {

        lbl_ScoreX.setText(Integer.toString(game.ScoreX));
        lbl_ScoreO.setText(Integer.toString(game.ScoreO));

        PlayerXScore.setText(game.PlayerXName);
        PlayerOScore.setText(game.PlayerOName);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                switch (game.gameField[i][j]) {
                    case 1:
                        getbuttonByID(i, j).setText("X");
                        getbuttonByID(i, j).setDisable(true);
                        break;
                    case 0:
                        getbuttonByID(i, j).setText("O");
                        getbuttonByID(i, j).setDisable(true);

                        break;
                    default:
                        getbuttonByID(i, j).setText("");
                        getbuttonByID(i, j).setDisable(false);

                        break;
                }

            }
        }

    }

    @FXML
    private void On_Menu_Load(ActionEvent event) {
        Stage stage = (Stage) lbl_Info.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extentionFilter = new FileChooser.ExtensionFilter("SER files (*.ser)", "*.ser");
        fileChooser.getExtensionFilters().add(extentionFilter);
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        fileChooser.setInitialDirectory(new File(currentPath));

        fileChooser.setTitle("Load Saved Game");
        File chosenFile = fileChooser.showOpenDialog(stage);

        if (chosenFile != null) {
            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(chosenFile))) {

                while (true) {
                    Object procitaniObjekt = ois.readObject();
                    resetGameField();
                    game = (Game) procitaniObjekt;
                    setUpGame(game);
                    updateUI(game.CheckGameStatus());

                }

            } catch (IOException | ClassNotFoundException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setHeaderText("Error!");
                alert.setContentText(e.getMessage());
            }
        }

    }

    @FXML
    private void On_Menu_Save(ActionEvent event) {

        TextInputDialog dialog = new TextInputDialog(fileName);
        dialog.setTitle("Save File");
        dialog.setHeaderText("Name your saved game");
        dialog.setContentText("Please enter a new file name name: ");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            fileName = result.get();
        }

        try {

            ObjectOutputStream oos = new ObjectOutputStream(
                    new BufferedOutputStream(new FileOutputStream(
                            fileName + ".ser")));
            System.out.println(game);
            oos.writeObject(game);
            oos.close();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Saved!");
            alert.setHeaderText("Saved!");
            alert.setContentText("The game has been saved!");

        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Error!");
            alert.setContentText(e.getMessage());
        }

    }

    private void restartTimer() {

        state = false;

        seconds = 0;
        minutes = 0;

        timer_seconds.setText("00");
        timer_minutes.setText("00");

    }

    private void startTimer() {

        state = true;

        Thread t = new Thread() {

            public void run() {

                while (true) {

                    if (state == true) {

                        try {

                            sleep(1000);

                            if (seconds > 60) {
                                seconds = 0;
                                minutes++;
                            }

                            seconds++;

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {

                                    if (seconds < 10) {
                                        timer_seconds.setText("0" + seconds);
                                    } else {
                                        timer_seconds.setText("" + seconds);
                                    }
                                    if (minutes < 10) {
                                        timer_minutes.setText("0" + minutes);
                                    } else {
                                        timer_minutes.setText("" + minutes);
                                    }
                                }
                            });

                        } catch (Exception e) {

                        }

                    } else {
                        break;
                    }

                }

            }

        };
        t.setDaemon(true);
        t.start();
    }

    @FXML
    private void on_menu_Connect_Click(ActionEvent event) {
        if (!connected) {
            TextInputDialog dialog = new TextInputDialog("127.0.0.1:12345");
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            dialog.setTitle("Connect..");
            dialog.setHeaderText("Connect to a multiplayer game.");
            dialog.setContentText("Please enter an ip address and socket: ");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String ipNSocket[] = String.valueOf(result.get()).split(":");
                String ip = ipNSocket[0];
                String socket = ipNSocket[1];
                try {
                    connect(ip, Integer.valueOf(socket));
                    menu_Connect.setText("Disconnect...");
                    connected = true;
                } catch (IOException ex) {
                    menu_Connect.setText("Connect...");
                    connected = false;
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {

            if (s != null) {
                try {
                    s.close();
                    Disconnect();
                    menu_Connect.setText("Disconnect...");
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Disconnected");
                    alert.setHeaderText("Success!");
                    alert.setContentText("Disconnected from Host!");
                    alert.show();

                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    private void connect(String ip, int socket) throws IOException {

        try {
            s = new Socket(ip, socket);
            LOG.log(Level.INFO, "Client connected to server:  (Connect method) {0}", s);
            restartGame();
            resetGameScore();
            showChat();
            game.player = 1;
            setClientHelperLabels();
            networkManager = new NetworkManager(s);
            chat = new ChatServiceImpl(game.PlayerXName + ": ");
            chat.addListener(this);

            registry = LocateRegistry.getRegistry();

            stub = (ChatService) UnicastRemoteObject.exportObject(chat, 0);
            registry.rebind("client", stub);

            Server server = new Server(s);
            server.addListener(this);
            server.setDaemon(true);
            server.start();

        } catch (Exception exception) {
            System.out.println(exception);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error while connecting!");
            alert.setContentText("Could not connect to " + ip + ":" + socket + "!");
            alert.show();
        }

    }

    private Boolean checkIfNetworkHost(int gameStatus) {
        int player;
        if (host) {
            player = 0;
        } else {
            player = 1;
        }
        if (gameStatus == 1 || gameStatus == 2) {

            return (networkManager == null || game.previousLoser == player);
        } else {

            return (networkManager == null || player == game.countLesser());
        }
    }

    private void Disconnect() {
        hideChat();
        connected = false;
        clearHelperLabels();
        networkManager = null;
        host = false;
        restartGame();
        resetGameScore();

    }

    @FXML
    private void On_menu_host_multiplayer_Click(ActionEvent event) throws InterruptedException {
        Alert alertConnecting = new Alert(AlertType.NONE);
        alertConnecting.initModality(Modality.APPLICATION_MODAL);
        if (menu_host.isSelected()) {

            try {

                ServerSocket s = new ServerSocket(12345);
                s.setSoTimeout(30000);

                LOG.info("Server waiting for client...");

                alertConnecting.setTitle("Waiting..");
                alertConnecting.setHeaderText("Hosting..");
                alertConnecting.setContentText("Awaiting connection...");
                alertConnecting.initModality(Modality.NONE);
                alertConnecting.getButtonTypes().add(ButtonType.CANCEL);
                alertConnecting.show();

                clientSocket = s.accept();

                alertConnecting.hide();
                alertConnecting.getButtonTypes().remove(ButtonType.CANCEL);

                restartGame();
                resetGameScore();
                showChat();
                networkManager = new NetworkManager(clientSocket);

                host = true;

                chat = new ChatServiceImpl(game.PlayerOName + ": ");
                chat.addListener(this);
                registry = LocateRegistry.createRegistry(RMIPort);

                stub = (ChatService) UnicastRemoteObject.exportObject(chat, 0);
                registry.rebind("server", stub);

                setHostHelperLabels();
                Server server = new Server(clientSocket);
                server.addListener(this);
                server.setDaemon(true);
                server.start();
                disableAllButtons();
                LOG.log(Level.INFO, "Server accepted client: (from enable Multiplayer method) {0}", clientSocket);

            } catch (SocketTimeoutException ste) {
                alertConnecting.hide();
                alertConnecting.getButtonTypes().remove(ButtonType.CANCEL);

                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setHeaderText("Timeout!");
                alert.setContentText("Socket timed out!");

            } catch (IOException ex) {
                System.out.println(ex);
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setHeaderText("Error!");
                alert.setContentText("Error while connecting to client!");
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {

            if (clientSocket != null) {
                try {
                    clientSocket.close();
                    Disconnect();
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Disconnected");
                    alert.setHeaderText("Success!");
                    alert.setContentText("Stopped hosting!");
                    alert.show();

                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    private void setClientHelperLabels() {

        lbl_XNetwork.setText("You");
        lbl_ONetwork.setText("Opponent");
    }

    private void setHostHelperLabels() {

        lbl_XNetwork.setText("Opponent");
        lbl_ONetwork.setText("You");
    }

    private void clearHelperLabels() {

        lbl_XNetwork.setText("");
        lbl_ONetwork.setText("");

    }

    @FXML
    private void on_menu_view_Replay_Click(ActionEvent event) {
        Stage stage = (Stage) lbl_Info.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extentionFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extentionFilter);
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        fileChooser.setInitialDirectory(new File(currentPath));

        fileChooser.setTitle("Replay game");
        File chosenFile = fileChooser.showOpenDialog(stage);

        if (chosenFile != null) {
            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(chosenFile);

                showReplay(doc);
            } catch (SAXParseException ex) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setHeaderText("File format incorrect.");
                alert.setContentText("File must be a KrizicKruzic replay file.");
                alert.show();
            } catch (SAXException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    private void On_menu_record_Click(ActionEvent event) {
        recordReplay = menu_record.isSelected();
        if (recordReplay) {
            try {
                xmlDocument = replay.createDocument();
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void recordReplay(int X, int Y) {

        if (recordReplay) {

            replay.appendTurn(xmlDocument, X, Y, game.player);

        }

    }

    private void showReplay(Document doc) {

        restartGame();
        resetGameScore();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                NodeList nList = doc.getElementsByTagName("Turn");
                for (int i = 0; i < nList.getLength(); i++) {

                    Node nNode = nList.item(i);

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element eElement = (Element) nNode;
                        game.player = Integer.parseInt(eElement.getElementsByTagName("player").item(0).getTextContent());
                        game.gameField[Integer.parseInt(eElement.getElementsByTagName("posX").item(0).getTextContent())][Integer.parseInt(eElement.getElementsByTagName("posY").item(0).getTextContent())]
                                = game.player;

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                setUpGame(game);
                                game.swapPlayerTurn();

                                updateUI(game.CheckGameStatus());
                            }
                        });

                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }

        });
        t.start();

    }

    @FXML
    private void on_btn_Chat_Click(ActionEvent event) {

        ChatService chatService;

        if (host) {

            try {
                chatService = (ChatService) registry.lookup("client");
                if (chatService != null) {
                    if (txtChat.toString().trim() != "") {
                        lvChat.getItems().add(game.PlayerOName + ": " + txtChat.getText());
                        chatService.send(game.PlayerOName + ": " + txtChat.getText());
                        txtChat.clear();
                    }
                }
            } catch (RemoteException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotBoundException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {

            try {
                chatService = (ChatService) registry.lookup("server");
                if (chatService != null) {
                    if (txtChat.toString().trim() != "") {
                        lvChat.getItems().add(game.PlayerXName + ": " + txtChat.getText());
                        chatService.send(game.PlayerXName + ": " + txtChat.getText());
                        txtChat.clear();
                    }
                }
            } catch (RemoteException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotBoundException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    @Override
    public void onMessageReceive(String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                lvChat.getItems().add(message);
            }
        });

    }

    private void hideChat() {
        btn_Chat.setVisible(false);
        lvChat.setVisible(false);
        txtChat.setVisible(false);
    }

    private void showChat() {
        btn_Chat.setVisible(true);
        lvChat.setVisible(true);
        lvChat.getItems().add("-----------Chat-----------");
        txtChat.setVisible(true);
    }

    @FXML
    private void on_txt_chat_Action(ActionEvent event) {

        ChatService chatService;

        if (host) {

            try {
                chatService = (ChatService) registry.lookup("client");
                if (chatService != null) {
                    if (txtChat.toString().trim() != "") {
                        lvChat.getItems().add(game.PlayerOName + ": " + txtChat.getText());
                        chatService.send(game.PlayerOName + ": " + txtChat.getText());
                        txtChat.clear();
                    }
                }
            } catch (RemoteException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotBoundException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {

            try {
                chatService = (ChatService) registry.lookup("server");
                if (chatService != null) {
                    if (txtChat.toString().trim() != "") {
                        lvChat.getItems().add(game.PlayerXName + ": " + txtChat.getText());
                        chatService.send(game.PlayerXName + ": " + txtChat.getText());
                        txtChat.clear();
                    }
                }
            } catch (RemoteException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotBoundException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    @Override
    public void onGameStateReceieve(Game GameUpdated) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                game = GameUpdated;
                setUpGame(game);
                updateUI(game.CheckGameStatus());

            }

        });
    }

}
