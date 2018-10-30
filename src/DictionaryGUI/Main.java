package DictionaryGUI;
/**
 * class Main run application
 * @author Do Thi Hong Ngat
 * @version 3.0
 * @since 2018-09-28
 */
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import static DictionaryGUI.Controller.ExportCustomDictionary;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Designs.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());
        primaryStage.setTitle("English - Vietnamses Dictionary");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                ExportCustomDictionary();
            }
        });
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
