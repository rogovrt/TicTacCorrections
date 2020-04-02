import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

public class CongratsWindowView extends BorderPane {
    private int defineDrawOrNot;
    private Label title;
    private Image image;
    private ButtonBar buttonBar;
    private Label winnerName;
    private Stage primaryStage;
    private TicTacViewModel ticTacViewModel;

    public CongratsWindowView(int i, TicTacViewModel ticTacViewModel){
        this.defineDrawOrNot = i;
        this.ticTacViewModel = ticTacViewModel;

        if (i != -1) {
            title = new Label("We had a winner! Congrats to ");
            title.setFont(new Font(24));
            winnerName = new Label();
            winnerName.setFont(new Font(24));
            Label l = new Label("!");
            l.setFont(new Font(24));
            HBox h = new HBox(title, winnerName, l);

            try (FileInputStream is = new FileInputStream("src/main/resourses/win.gif")) {
                image = new Image(is);
                ImageView iv = new ImageView(image);
                setCenter(iv);
            }
            catch (IOException e) {
                throw new UncheckedIOException(e);
            }

            buttonBar = new ButtonBar();
            initButtonBar();

            setTop(h);
            setBottom(buttonBar);
            setPadding(new Insets(4));
        }

        if (i == -1) {
            title = new Label("Sorry, guys. It's draw!");
            title.setFont(new Font(24));

            try (FileInputStream is = new FileInputStream("src/main/resourses/draw.gif")) {
                image = new Image(is);
                ImageView iv = new ImageView(image);
                setCenter(iv);
            }
            catch (IOException e) {
                throw new UncheckedIOException(e);
            }

            buttonBar = new ButtonBar();
            initButtonBar();

            setTop(title);
            setBottom(buttonBar);
            setPadding(new Insets(4));
        }
        primaryStage = new Stage();
        initPrimaryStage();
    }

    private void initButtonBar() {
        Button newGame = new Button("New Game");
        Button cancel = new Button("Exit");

        cancel.setOnMouseClicked(mouseEvent -> Platform.exit());
        newGame.setOnMouseClicked(mouseEvent -> {
                    initTicTacViewStage();
                    primaryStage.close();
        });

        buttonBar.getButtons().addAll(newGame, cancel);
    }

    private void initPrimaryStage() {
        Scene scene = new Scene(this, 500, 400);
        primaryStage.setMinHeight(400);
        primaryStage.setMaxHeight(400);
        primaryStage.setMinWidth(500);
        primaryStage.setMaxWidth(500);
        primaryStage.setTitle("End");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initTicTacViewStage() {
        Stage ticTacViewStage = ticTacViewModel.getMainStage();
        TicTacView ticTacView = new TicTacView(ticTacViewModel);
        Scene scene = new Scene(ticTacView, 640, 500);
        ticTacViewStage.setMinHeight(540);
        ticTacViewStage.setMaxHeight(540);
        ticTacViewStage.setMinWidth(680);
        ticTacViewStage.setMaxWidth(680);
        ticTacViewStage.setTitle("Tic-Tac");
        ticTacViewStage.setScene(scene);
        ticTacViewStage.show();
    }

    public void setWinnerName(String winnerName) {
        this.winnerName.setText(winnerName);
    }
}
