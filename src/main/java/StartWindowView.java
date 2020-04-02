import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class StartWindowView extends BorderPane {
    private Label title;
    private GridPane playersInfo;
    private ButtonBar buttonBar;
    private TicTacView ticTacView;
    private Stage ticTacViewStage;
    private Stage stage;

    public StartWindowView(Stage stage) {
        title = new Label("Enter player's nicknames\nUse maximum 10 symbols");
        playersInfo = new GridPane( );
        buttonBar = new ButtonBar( );
        this.stage = stage;

        ticTacViewStage = new Stage();
        ticTacView = new TicTacView(new TicTacViewModel(ticTacViewStage));
        initTicTacViewStage();

        initPlayersInfo();
        initButtonBar();
        setUserNames();

        setTop(title);
        setCenter(playersInfo);
        setBottom(buttonBar);
        setPadding(new Insets(4));
    }

    private void initPlayersInfo() {
        playersInfo.setPadding(new Insets(4));
        playersInfo.setHgap(20);
        playersInfo.setVgap(10);

        Label player1 = new Label("Player1");
        Label player2 = new Label("Player2");
        TextField player1Tf = new TextField("Player1");
        TextField player2Tf = new TextField("Player2");


        playersInfo.add(player1, 0, 0);
        playersInfo.add(player2, 0, 1);
        playersInfo.add(player1Tf, 1, 0);
        playersInfo.add(player2Tf, 1, 1);
    }

    private void initButtonBar() {
        Button play = new Button("Play");
        Button cancel = new Button("Cancel");

        cancel.setOnMouseClicked(mouseEvent -> Platform.exit());
        play.setOnMouseClicked(new EventHandler<MouseEvent>( ) {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.close();
                ticTacViewStage.show();
            }
        });
        buttonBar.getButtons( ).addAll(play, cancel);
    }

    private void initTicTacViewStage() {
        Scene scene = new Scene(ticTacView, 640, 500);
        ticTacViewStage.setMinHeight(540);
        ticTacViewStage.setMaxHeight(540);
        ticTacViewStage.setMinWidth(680);
        ticTacViewStage.setMaxWidth(680);
        ticTacViewStage.setTitle("Tic-Tac");
        ticTacViewStage.setScene(scene);
    }

    private void setUserNames() {
        ObservableList<Node> tf = playersInfo.getChildren();
        TextField tf1 = (TextField) tf.get(2);
        TextField tf2 = (TextField) tf.get(3);
        ObservableList<Node> l = ticTacView.getStatField().getChildren();
        Label l1 = (Label) l.get(1);
        Label l2 = (Label) l.get(2);
        tf1.textProperty().bindBidirectional(l1.textProperty());
        tf2.textProperty().bindBidirectional(l2.textProperty());
    }
}
