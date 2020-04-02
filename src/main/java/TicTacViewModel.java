import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;

public class TicTacViewModel {
    private Image cross;
    private Image circle;
    private boolean whoseTurn;
    private List<Integer> emptyPositions;
    private int numberOfMotion;
    private List<Integer> positionsOfCross;
    private List<Integer> positionsOfCircles;
    private List<Integer> winningCombinations;
    private SimpleIntegerProperty numberOfPointsPlayer1;
    private SimpleIntegerProperty numberOfPointsPlayer2;
    private SimpleStringProperty player1Name;
    private SimpleStringProperty player2Name;
    private Stage mainStage;

    public TicTacViewModel(Stage stage) {

        mainStage = stage;

        try (FileInputStream is = new FileInputStream("src/main/resourses/cross.png");) {
            cross = new Image(is, 160.0d, 160.0d, false, false);
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        try (FileInputStream is1 = new FileInputStream("src/main/resourses/circle.png");) {
            circle = new Image(is1, 160.0d, 160.0d, false, false);
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        whoseTurn = true;

        emptyPositions = new ArrayList<>(9);
        initControl();

        numberOfMotion = 0;

        positionsOfCross = new ArrayList<>(9);
        positionsOfCircles = new ArrayList<>(9);

        winningCombinations = new ArrayList<>(15);
        initWinningCombinations();

        player1Name = new SimpleStringProperty();
        player2Name = new SimpleStringProperty();

        initStat();
    }

    private void initStat() {
        numberOfPointsPlayer1 = new SimpleIntegerProperty(0);
        numberOfPointsPlayer2 = new SimpleIntegerProperty(0);
    }

    private void initControl() {
        for (int i = 0; i < 9; ++i)
            emptyPositions.add(i, -1);
    }

    private void initWinningCombinations() {
        for (int i = 0; i < 9; ++i)
            winningCombinations.add(i);
        winningCombinations.add(2); winningCombinations.add(4); winningCombinations.add(6); winningCombinations.add(0); winningCombinations.add(4); winningCombinations.add(8);
        for (int i = 0; i < 3; ++i) {
            winningCombinations.add(i); winningCombinations.add(i + 3); winningCombinations.add(i + 6);
        }
    }

    public void onClick(GridPane gameField, int ci, int ri) {
        int num = ri * 3 + ci;
        if (emptyPositions.get(num) == -1) {
            numberOfMotion++;
            if (whoseTurn) {
                gameField.add(new ImageView(cross), ci, ri);
                emptyPositions.set(num, 1);
                positionsOfCross.add(num);
                whoseTurn = false;
            }
            else {
                gameField.add(new ImageView(circle), ci, ri);
                emptyPositions.set(num, 0);
                positionsOfCircles.add(num);
                whoseTurn = true;
            }
            if (numberOfMotion >= 5)
                whoWin();
        }
    }

    private void clearData() {
        positionsOfCross.clear();
        positionsOfCircles.clear();
        numberOfMotion = 0;
        whoseTurn = true;
        initControl();
    }

    private void whoWin() {
        for (int i = 0; i < 24; i += 3) {
            if (positionsOfCross.containsAll(winningCombinations.subList(i, i + 3))) {
                initEndOfParty(1);
            }
            if (positionsOfCircles.containsAll(winningCombinations.subList(i, i + 3))) {
                initEndOfParty(0);
            }
        }
        if (numberOfMotion == 9)
            initEndOfParty(-1);
    }

    private void initEndOfParty(int c) {
        CongratsWindowView congratsWindowView = new CongratsWindowView(c, this);
        if (c == -1) {
            numberOfPointsPlayer1.set(numberOfPointsPlayer1.getValue() + 1);
            numberOfPointsPlayer2.set(numberOfPointsPlayer2.getValue() + 1);
        }
        if (c == 0) {
            congratsWindowView.setWinnerName(getPlayer1Name());
            numberOfPointsPlayer2.set(numberOfPointsPlayer2.getValue() + 3);
        }
        if (c == 1) {
            congratsWindowView.setWinnerName(getPlayer2Name());
            numberOfPointsPlayer1.set(numberOfPointsPlayer1.getValue() + 3);
        }
        mainStage.close();
        clearData();
    }

    public String getPlayer1Name() {
        return player1Name.get( );
    }

    public SimpleStringProperty player1NameProperty() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name.get( );
    }

    public SimpleStringProperty player2NameProperty() {
        return player2Name;
    }

    public SimpleIntegerProperty numberOfPointsPlayer1Property() {
        return numberOfPointsPlayer1;
    }

    public SimpleIntegerProperty numberOfPointsPlayer2Property() {
        return numberOfPointsPlayer2;
    }

    public Stage getMainStage() {
        return mainStage;
    }
}