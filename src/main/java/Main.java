import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        StartWindowView startWindowView = new StartWindowView(primaryStage);
        Scene scene = new Scene(startWindowView, 300, 150);
        primaryStage.setMinHeight(150);
        primaryStage.setMaxHeight(150);
        primaryStage.setMinWidth(300);
        primaryStage.setMaxWidth(300);
        primaryStage.setTitle("Start");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}