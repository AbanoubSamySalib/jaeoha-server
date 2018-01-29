/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaeohaserverAdmin;

import databaseclasses.Users;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import rmi.ServerImpl.ServerImpl;
import rmi.interfaces.ClientInterface;

/**
 * responsible for component that appear to admin and call from it ServerFunctions class  
 * @author omnia samy
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private BorderPane pane;
    @FXML
    private Label label;
    @FXML
    private BarChart<?, ?> barchart;
    @FXML
    private PieChart piechart;
    @FXML
    private AnchorPane anchor;
    @FXML
    private Button stopStart;
    @FXML
    private AnchorPane line;
    @FXML
    private TableView<Users> table;
    @FXML
    private Button change;
    @FXML
    private VBox vbox;
    @FXML
    private Label labeln = new Label("send notification");
    @FXML
    private Label userdata;
    @FXML
    private TextArea textArea = new TextArea();
    @FXML
    private Button send = new Button("send");
    @FXML
    private Label lOnline;
    @FXML
    private Label lOffline;

    private int flagstart = 0;
    private int flagstop = 0;
    private static double xOffset = 0;
    private static double yOffset = 0;

    ArrayList<Integer> counter = new ArrayList<Integer>();
    ArrayList<String> countrynames = new ArrayList<String>();

    ServerFunctions server = new ServerFunctions();

    int flagC = 0;//this mean the the button doesnt pressed
    int start_stop = 0;
    private Registry reg;
    private ServerImpl obj;

    /**
     *
     * @return remote object from server 
     */
    public ServerImpl getObj() {
        return obj;
    }

    /**
     *
     * @param obj set the object of server implementation (remote object)
     */
    public void setObj(ServerImpl obj) {
        this.obj = obj;
    }

    /**
     *
     * @return object from registry 
     */
    public Registry getReg() {
        return reg;
    }

    /**
     *
     * @param reg object from registry
     */
    public void setReg(Registry reg) {
        this.reg = reg;
    }

    @FXML
    private void startButtonAction() {
        flagstart = 1;
    }

    @FXML
    private void stopButtonAction() {
        flagstop = 1;
        System.out.println("stop");
    }

    /**
     * responsible for switch between table and notification form in scene that admin can send to online users 
     * @param event listener to mouse event 
     */
    @FXML
    public void onChange(MouseEvent event) {
        if (flagC == 0) {
            flagC = 1;
        }//flag=1 means textarea
        else {
            flagC = 0;
        }
        vbox.getChildren().clear();
        if (flagC == 0) {
            change.setText("Send notification");
            vbox.getChildren().addAll(userdata, table);
        } else {
            change.setText("users information");
            textArea.setPrefSize(200, 300);
            labeln.setId("lableNotfication");
            send.setPrefSize(80, 50);
            vbox.getChildren().addAll(labeln, textArea, send);
        }
    }

    /**
     * stop connection and exit admin screen
     */
    @FXML
    private void onExit() {
        server.stopConnection();
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage s = (Stage) line.getScene().getWindow();
                s.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    public void handle(WindowEvent we) {
                        onExit();
                    }
                });
            }
        });
        pane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage s = (Stage) pane.getScene().getWindow();
                xOffset = s.getX() - event.getScreenX();
                yOffset = s.getY() - event.getScreenY();
            }
        });

        pane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage s = (Stage) pane.getScene().getWindow();
                s.setX(event.getScreenX() + xOffset);
                s.setY(event.getScreenY() + yOffset);
            }
        });

        table();
        xyChart();
        server.pieChart(piechart);

        send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                try {
                    if (!textArea.getText().trim().equals("")) {
                        obj.announceAllUsers(textArea.getText());
                        textArea.clear();
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        stopStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (start_stop == 0) {//0 y3ny hoa kan 48al w hyd5ol ytfeh//stop
                    start_stop = 1;//start case

                    stopStart.setText("start");
                    stopStart.setStyle("-fx-background-color:#4CAF50");

                    try {

                        HashMap<Integer, ClientInterface> map = new HashMap<>();
                        map = obj.getMap();
                        ArrayList<Users> onlineUsers = new ArrayList<>();
                        onlineUsers = obj.getOnlineUsers();
                        System.err.println("ou:" + onlineUsers.size());
                        for (int i = 0; i < onlineUsers.size(); i++) {
                            map.get(onlineUsers.get(i).getId()).stopScene();
                            System.err.println("tmam");
                        }
                        UnicastRemoteObject.unexportObject(obj, true);
                        reg.unbind("chat");

                    } catch (RemoteException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (NotBoundException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {

                    try {
                        //sstart
                        start_stop = 0;
                        stopStart.setText("stop");
                        stopStart.setStyle("-fx-background-color:#FF5722");
                        HashMap<Integer, ClientInterface> map = new HashMap<>();
                        map = obj.getMap();
                        ArrayList<Users> onlineUsers = new ArrayList<>();
                        onlineUsers = obj.getOnlineUsers();
                        UnicastRemoteObject.exportObject(obj, 1099);
                        reg.rebind("chat", obj);
                        System.err.println("ou:" + onlineUsers.size());
                        for (int i = 0; i < onlineUsers.size(); i++) {
                            System.err.println("tmam");
                            map.get(onlineUsers.get(i).getId()).RecieveServerRemoteObject();
                        }

                        // UnicastRemoteObject.exportObject(obj,1099);
                    } catch (RemoteException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });

    }

    /**
     * use server object to send reference from barchart on scene to ServerFunctions class to
     * update data displayed on it
     */
    public void xyChart() {
        server.xychart(barchart);
    }

    /**
     * responsible for draw table and make its structure  and use server object to send reference 
     * from table on scene to ServerFunctions class to update data displayed on table 
     */
    public void table() {
        server.table(table, lOnline, lOffline);
        table.setEditable(true);

        TableColumn userNameCol = new TableColumn("User name");
        userNameCol.setMinWidth(100);
        userNameCol.setCellValueFactory(
                new PropertyValueFactory<Users, String>("userName"));

        TableColumn emailCol = new TableColumn("Email");
        emailCol.setMinWidth(100);
        emailCol.setCellValueFactory(
                new PropertyValueFactory<Users, String>("email"));

        TableColumn phoneCol = new TableColumn("Phone");
        phoneCol.setMinWidth(100);
        phoneCol.setCellValueFactory(
                new PropertyValueFactory<Users, String>("phone"));

        TableColumn countryCol = new TableColumn("Country");
        countryCol.setMinWidth(100);
        countryCol.setCellValueFactory(
                new PropertyValueFactory<Users, String>("country"));

        TableColumn genderCol = new TableColumn("Gender");
        genderCol.setMinWidth(100);
        genderCol.setCellValueFactory(
                new PropertyValueFactory<Users, String>("gender"));

        TableColumn statusCol = new TableColumn("Status");
        statusCol.setMinWidth(100);
        statusCol.setCellValueFactory(
                new PropertyValueFactory<Users, String>("status"));

        TableColumn activeCol = new TableColumn("Active");
        activeCol.setMinWidth(100);
        activeCol.setCellValueFactory(
                new PropertyValueFactory<Users, Integer>("active"));

        table.getColumns().addAll(userNameCol, emailCol, phoneCol, countryCol,
                genderCol, statusCol, activeCol);
    }
}
