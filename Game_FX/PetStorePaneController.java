package Game_FX;

import Pets.*;
import Pets.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static java.lang.Integer.parseInt;
import static Game_FX.NewPlayerController.currentPlayer;
import static Game_FX.Utils.*;

public class PetStorePaneController extends AnchorPane{

    @FXML
    private Label welcomeLabel1;
    @FXML
    private Label welcomeLabel2;
    @FXML
    private TextField petNameTF;
    @FXML
    private Button nameOkButton;
    @FXML
    private Text petNameText;
    @FXML
    private Label finishShoppingLabel;
    @FXML
    private Button petButton0;
    @FXML
    private Button petButton1;
    @FXML
    private Button petButton2;
    @FXML
    private Button petButton3;
    @FXML
    private Button petButton4;
    @FXML
    private Button petButton5;

    private int index;
    private Button selectedButton = null;
    private String[] petList = {"Dog", "Cat", "Koala", "Panda", "GunieaPig", "Alpaca"};

    public void setPetButton(ActionEvent event){
        petNameTF.setVisible(false);
        finishShoppingLabel.setVisible(false);
        petNameText.setVisible(false);
        resetSelectedButton(selectedButton,originalLengthB,originalLengthB);

        selectedButton = (Button) event.getSource();
        String petButtonId = selectedButton.getId();
        index = parseInt(petButtonId.substring(petButtonId.length()-1));
        int balance = currentPlayer.getAccount();
        int petListLen = currentPlayer.getMyPetList().size();

        Utils.setSelectedButton(selectedButton,selectedLengthB,selectedLengthB,selectedColourCode);

        if(petListLen == 3){
            showAlertWindow("You already have 3 pets");
        }
        else if(petListLen < 3) {
            if (balance < 30) {
                showAlertWindow("You don't have enough money.");
            } else {
                welcomeLabel1.setVisible(true);
                welcomeLabel1.setText("Give a name for your pet !!!");
                welcomeLabel2.setVisible(false);
                petNameTF.setVisible(true);
                nameOkButton.setVisible(true);
            }
        }
    }

    public void setNameOkButton(ActionEvent event) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        Player currentPlayer = (Player) stage.getUserData();
        Pet newPet = null;

        String petName = petNameTF.getText();

        switch(index){
            case(0):{ newPet = new Dog(petName); break;}
            case(1):{ newPet = new Cat(petName); break;}
            case(2):{ newPet = new Koala(petName); break;}
            case(3):{ newPet = new Panda(petName); break;}
            case(4):{ newPet = new GuineaPig(petName); break;}
            case(5):{ newPet = new Alpaca(petName); break;}
        }

        assert newPet != null;
        currentPlayer.setAccount(currentPlayer.getAccount() - newPet.getPrice());
        currentPlayer.getMyPetList().add(newPet);
        currentPlayer.setScore(currentPlayer.getScore() + 10);

        showInfoWindow("Congrats, you got a " + newPet.getSpecies() + ".");

        resetSelectedButton(selectedButton,originalLengthB,originalLengthB);
        nameOkButton.setVisible(false);
        welcomeLabel1.setVisible(false);
        petNameTF.setVisible(false);
        petNameTF.setText("");
    }
}
