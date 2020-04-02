import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TicTacView extends HBox {
    private GridPane gameField;
    private GridPane statField;
    private TicTacViewModel ticTacViewModel;


    public TicTacView(TicTacViewModel ticTacViewModel) {
        gameField = new GridPane();
        statField = new GridPane();
        this.ticTacViewModel = ticTacViewModel;

        initGameField();
        initStatField();

        getChildren().addAll(gameField, statField);
    }

    private void initStatField() {
        statField.setPadding(new Insets(5));
        statField.setHgap(20);
        statField.setVgap(10);

        Label title = new Label("Statistic");
        Label p1 = new Label("Player1");
        Label p2 = new Label("Player2");

        Label res1 = new Label();
        res1.textProperty().bind(ticTacViewModel.numberOfPointsPlayer1Property().asString());
        Label res2 = new Label();
        res2.resize(80, 10);
        res2.textProperty().bind(ticTacViewModel.numberOfPointsPlayer2Property().asString());
        p1.textProperty().bindBidirectional(ticTacViewModel.player1NameProperty());
        p2.textProperty().bindBidirectional(ticTacViewModel.player2NameProperty());

        statField.add(title, 0, 0);
        statField.add(p1, 0, 1);
        statField.add(p2, 1, 1);
        statField.add(res1, 0, 2);
        statField.add(res2, 1, 2);

        GridPane.setColumnSpan(title, 2);
    }

    private void initGameField() {
        gameField.setPadding(new Insets(9));

        for (int i = 0; i < 321; i += 160) {
            for (int j = 0; j < 321; j += 160)
                initRectangle(i, j);
        }

        gameField.setGridLinesVisible(true);
    }

    private void initRectangle(double x, double y) {
        Rectangle r = new Rectangle(160, 160, Color.WHITE);
        r.setX(x);
        r.setY(y);
        gameField.add(r, (int)(y / 160), (int)(x / 160));
        r.setOnMouseClicked(mouseEvent -> {
            ticTacViewModel.onClick(gameField, (int)(y / 160), (int)(x / 160));
        });
    }

    public GridPane getStatField() {
        return statField;
    }
}