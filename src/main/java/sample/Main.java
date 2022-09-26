package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/******************************************************************************
 * Main class to run app and create primary stage.
 ******************************************************************************/
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/window.fxml"));
        primaryStage.setTitle("OAST Queue Theory Simulator");
        primaryStage.setScene(new Scene(root, 1600, 800));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
