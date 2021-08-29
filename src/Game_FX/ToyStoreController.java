package Game_FX;

import Pets.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import static java.lang.Integer.parseInt;
import static Game_FX.NewPlayerController.currentPlayer;
import static Game_FX.Utils.*;

public class ToyStoreController extends AnchorPane {

    @FXML
    private Button toyButton0;
    @FXML
    private Button toyButton1;
    @FXML
    private Button toyButton2;
    @FXML
    private Button toyButton3;
    @FXML
    private Button toyButton4;
    @FXML
    private Button toyButton5;
    @FXML
    private Label welcomeLabel1;
    @FXML
    private Label welcomeLabel2;
    @FXML
    private Button okButton;

    private Toy newToy;
    private int index;
    private Button selectedButton = null;
    private String[] toyPriceList ={"20","50","120","100","20","20"};
    static String[] toyList = {"Stick", "Scratcher","TreeBranch","ToyHorse", "Roller", "HumanTouch" };

    public void setToyButton(ActionEvent event){
        resetSelectedButton(selectedButton,originalLengthB,originalLengthB);

        selectedButton = (Button) event.getSource();
        String toyButtonId = selectedButton.getId();
        index = parseInt(toyButtonId.substring(toyButtonId.length() - 1));

        Utils.setSelectedButton(selectedButton, selectedLengthB, selectedLengthB,selectedColourCode);

        okButton.setVisible(true);
        welcomeLabel1.setVisible(true);
        welcomeLabel2.setVisible(true);
        welcomeLabel1.setText("Price: " + toyPriceList[index]);
        welcomeLabel2.setText("Press 'OK' to buy.");
    }

    public void setOkButton() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        int balance = currentPlayer.getAccount();

        if(balance < 20) {
           showAlertWindow("You don't have enough money.");
        }
        else{
            newToy = (Toy) Class.forName("Pets."+ toyList[index]).newInstance();
            int newToyPrice = newToy.getPrice();
            if (balance < newToyPrice) {
                showAlertWindow("Sorry, you don't have enough money for " + newToy.getName() +".");
            }
            else{
                currentPlayer.getMyToyList().add(newToy);
                currentPlayer.setAccount(currentPlayer.getAccount() - newToy.getPrice());
                currentPlayer.setScore(currentPlayer.getScore()+10);
                showInfoWindow("Congrats, you got a " +newToy.getName());
            }
        }
        resetSelectedButton(selectedButton,originalLengthB,originalLengthB);
        okButton.setVisible(false);
        welcomeLabel1.setVisible(false);
        welcomeLabel2.setVisible(false);
    }
}
