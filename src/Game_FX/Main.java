package Game_FX;

import Pets.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    private static Game game;
    static Game currentGame;
    static BorderPane currentNPPanel;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Frame.fxml"));
        BorderPane borderPane = loader.load();

        BorderPane npBorderPanel = FXMLLoader.load(getClass().getResource("NewPlayerScene.fxml"));
        borderPane.setCenter(npBorderPanel);

        primaryStage.setScene(new Scene(borderPane, 800, 580));

        game = new Game();
        currentGame = game;
        currentNPPanel = npBorderPanel;
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
