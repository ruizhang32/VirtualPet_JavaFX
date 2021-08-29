package Game_FX;

import Pets.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import static Game_FX.Main.currentNPPanel;

import java.io.*;

import static Game_FX.Main.currentGame;
import static Game_FX.NewPlayerController.newPlayerButtonList;

public class FrameMenuController{

    @FXML
    private MenuBar VPMenuBar;
    public static Game game;


    public void setQuit(ActionEvent event){
        System.exit(0);
    }

    public void setNewGame() throws IOException {
        BorderPane borderPane = FXMLLoader.load(getClass().getResource("Frame.fxml"));
        BorderPane panel = FXMLLoader.load(getClass().getResource("NewPlayerScene.fxml"));
        borderPane.setCenter(panel);

        Stage stage = (Stage) VPMenuBar.getScene().getWindow();
        stage.getScene().setRoot(borderPane);

        game = new Game();
        currentGame = game;

        stage.show();
    }

    public void setChangePlayer(ActionEvent event) throws IOException {
        BorderPane borderPane = FXMLLoader.load(getClass().getResource("Frame.fxml"));

        if(currentNPPanel != null) {
            if(currentGame.playerList.size() > 0){
                for(int i=0; i< currentGame.playerList.size(); i++){
                    newPlayerButtonList.get(i).setText("Name: " +  currentGame.playerList.get(i).getName() + "   " + "Play Days: " + currentGame.playerList.get(i).getDaysToWin());
                }
            }
            borderPane.setCenter(currentNPPanel);
        }
        else{
            BorderPane panel = FXMLLoader.load(getClass().getResource("NewPlayerScene.fxml"));
            currentNPPanel = panel;
            borderPane.setCenter(panel);
        }

        Stage stage = (Stage) VPMenuBar.getScene().getWindow();
        stage.getScene().setRoot(borderPane);

        stage.show();
    }

    public void setLoadGame() throws IOException, ClassNotFoundException {
        FileChooser fil_chooser = new FileChooser();
        Label label = new Label("no files selected");
        Stage stage = (Stage) VPMenuBar.getScene().getWindow();
        File file = fil_chooser.showOpenDialog(stage);
        if (file != null) {

            label.setText(file.getAbsolutePath()
                    + "  selected");
        }

        FileInputStream fis = new FileInputStream("myGame.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        game = (Game) ois.readObject();
        currentGame = game;

        BorderPane borderPane = FXMLLoader.load(getClass().getResource("Frame.fxml"));
        FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(getClass().getResource("NewPlayerScene.fxml"));
        BorderPane panel = loader1.load();
        currentNPPanel = panel;

        if(currentGame.playerList.size() > 0){
            for(int i=0; i< currentGame.playerList.size(); i++){
                newPlayerButtonList.get(i).setText("Name: " +  currentGame.playerList.get(i).getName() + "   " + "Play Days: " + currentGame.playerList.get(i).getDaysToWin());
                newPlayerButtonList.get(i).setFont(Font.font(15));
            }
        }
        borderPane.setCenter(currentNPPanel);
        stage.getScene().setRoot(borderPane);

        stage.show();
        ois.close();
        fis.close();
    }

    public void setSaveGame() throws IOException {
        FileOutputStream fos = new FileOutputStream("myGame.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(currentGame);
        oos.close();
        fos.close();
        System.out.println("Serialized data is saved in myGame.ser");
    }
}
