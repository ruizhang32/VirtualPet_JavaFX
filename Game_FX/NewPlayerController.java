package Game_FX;

import Pets.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.lang.Integer.parseInt;
import static Game_FX.Utils.*;
import static Game_FX.Main.currentGame;

public class NewPlayerController implements Initializable {

    @FXML
    private Button newPlayerButton1;
    @FXML
    private Button newPlayerButton2;
    @FXML
    private Button newPlayerButton0;
    @FXML
    private Label addNewPlayerLabel;
    @FXML
    private TextField newPlayerNameTF;
    @FXML
    private TextField daysToPlayFX;
    @FXML
    private Button addNewPlayerButton;

    static Player currentPlayer;
    static ArrayList<Button> newPlayerButtonList ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        newPlayerButtonList = new ArrayList<>(3);
        newPlayerButtonList.add(newPlayerButton0);
        newPlayerButtonList.add(newPlayerButton1);
        newPlayerButtonList.add(newPlayerButton2);

        changeButtonEffect(newPlayerButton0);
        changeButtonEffect(newPlayerButton1);
        changeButtonEffect(newPlayerButton2);
        changeButtonEffect(addNewPlayerButton);
    }

    public void newPlayerButtonHandler(ActionEvent event) throws IOException, InterruptedException {
        Button button = (Button) event.getSource();

        if (!button.getText().contains("New Player")){
            String playerSelected = button.getText().split(" ")[1];

            for (int i=0; i< currentGame.playerList.size();i++) {
                if(currentGame.playerList.get(i).getName().equalsIgnoreCase(playerSelected)){
                    currentPlayer = currentGame.playerList.get(i);
                }
            }

            BorderPane mainBorderPane = FXMLLoader.load(getClass().getResource("Frame.fxml"));
            FXMLLoader loader1 = new FXMLLoader();
            loader1.setLocation(getClass().getResource("PlayScene.fxml"));
            BorderPane playSceneBorderPanel =loader1.load();
            mainBorderPane.setCenter(playSceneBorderPanel);

            PlaySceneController controller = loader1.getController();
            controller.showPlaySceneData(currentPlayer);

            Stage stage = (Stage) button.getScene().getWindow();
            stage.getScene().setRoot(mainBorderPane);
            stage.setUserData(currentPlayer);
            stage.show();
        }else{
            showAlertWindow("Create a new player first!");
        }
    }

    public void setAddNewPlayerButton(ActionEvent event){
        int playDays = 0;
        String newPlayerName = newPlayerNameTF.getText();
        String daysToPlay = daysToPlayFX.getText();

        if(isNumeric(daysToPlay)) {
            playDays = parseInt(daysToPlay);
        }

        if(!nameValidation(newPlayerName)) showAlertWindow("Invalid input\na-z, A-Z, 0-9,-_., length>=3");
        if(newPlayerName.contains("New Player")) showAlertWindow("Invalid name");
        if(!daysToPlayValidation(playDays)) showAlertWindow(addNewPlayerLabel.getText() +"\n"+ "Invalid input\nshould be 3-7days");
        if(nameValidation(newPlayerName) && daysToPlayValidation(playDays)) {
            String newPlayerInfo = "Name: " + newPlayerName + "   " + "Play Days: " + daysToPlay;
            Player player = new Player(newPlayerName, playDays);
            currentGame.playerList.add(player);

            for(Button button : newPlayerButtonList){
                if(newPlayerButtonList.stream().noneMatch(b -> b.getText().contains("New Player")))
                    showAlertWindow("You already have 3 players !!!");
                else if (button.getText().contains("New Player")){
                    button.setFont(Font.font(15));
                    button.setText(newPlayerInfo);
                    newPlayerNameTF.setText("");
                    daysToPlayFX.setText("");
                    break;
                }
            }
        }
    }

    private boolean nameValidation(String newPlayerName){
        Pattern userNameRex = Pattern.compile("^[a-zA-Z0-9._-]{3,}$");
        Matcher matcher = userNameRex.matcher(newPlayerName);
        return (matcher.matches());
    }

    private boolean daysToPlayValidation(int daysToPlay){
        return (daysToPlay >= 3 && daysToPlay <= 7);
    }
}
