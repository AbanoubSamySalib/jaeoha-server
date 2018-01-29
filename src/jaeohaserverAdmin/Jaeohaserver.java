/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaeohaserverAdmin;

import ServerRegister.Server;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author omnia samy
 */
public class Jaeohaserver extends Application {

    FXMLDocumentController controller = new FXMLDocumentController();

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader= new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));;
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        
        Server server=new Server();
        FXMLDocumentController controller= loader.getController();
        controller.setReg(server.getReg());
        controller.setObj(server.getObj());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
